package com.unlam.tpi.core.servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.ListaPreciosAPI;
import com.unlam.tpi.core.interfaces.PanelesServicio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;
import com.unlam.tpi.core.modelo.Posicion;

@Service
public class PanelesServicioImpl implements PanelesServicio {

	public static List<Instrumento> listaInstrumentosAccionesAux = new ArrayList<>();
	public static List<Instrumento> listaInstrumentosBonosAux = new ArrayList<>();
	public static List<Instrumento> listaInstrumentosCedearsAux = new ArrayList<>();

	public static Map<String, Instrumento> panelAcciones = new HashMap<>();
	public static Map<String, Instrumento> panelBonos = new HashMap<>();
	public static Map<String, Instrumento> panelCedears = new HashMap<>();

	@Autowired
	PosicionServicio posicionServicio;

	@Autowired
	InstrumentoServicio instrumentoServicio;

	@Autowired
	PuntasServicio puntasServicio;

	@Autowired
	ListaPreciosAPI listaPrecioServicio;

	@Override
	public Map<String, Instrumento> getPanelDeAcciones() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio
				.getListaPrecio(PanelesDePreciosConstantes.ACCIONES);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosAccionesAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosAccionesAux.addAll(listaInstrumentos);
		agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return panelAcciones;
	}

	@Override
	public Map<String, Instrumento> getPanelDeBonos() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio.getListaPrecio(PanelesDePreciosConstantes.BONOS);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosBonosAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosBonosAux.addAll(listaInstrumentos);
		agregarInstrumentosAlPanelDeBonos(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return panelBonos;
	}

	@Override
	public Map<String, Instrumento> getPanelDeCedears() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio
				.getListaPrecio(PanelesDePreciosConstantes.CEDEARS);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosCedearsAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosCedearsAux.addAll(listaInstrumentos);
		agregarInstrumentosAlPanelDeCedears(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return panelCedears;
	}

	private void recalcularPosicionTotalSegunVariacionDePrecios(List<Instrumento> listaInstrumentos) {
		List<Posicion> posicionTotal = posicionServicio.obtenerPosicionTotal();
		for (Instrumento instrumento : listaInstrumentos) {
			if (instrumento.getFlashVenta() != 0) {
				for (Posicion posicion : posicionTotal) {
					if (posicion.getSimboloInstrumento() != null && instrumento.getSimbolo() != null
							&& !posicion.getEsEfectivo()) {
						if (posicion.getSimboloInstrumento().equals(instrumento.getSimbolo())) {
							posicion.setPrecioActualDeVenta(
									instrumento.getPuntas() != null ? instrumento.getPuntas().getPrecioVenta() : null);
							posicionServicio.actualizarPosicion(posicion);
						}
					}
				}
			}
		}
	}

	public void determinarFlashDeCompraVenta(Map<String, Instrumento> mapaInstrumentosAux,
			List<Instrumento> listaInstrumentos, List<Instrumento> listaInstrumentosAux) {
		if (listaInstrumentosAux != null && !listaInstrumentosAux.isEmpty()) {
			for (Instrumento instrumento : listaInstrumentosAux) {
				mapaInstrumentosAux.put(instrumento.getSimbolo(), instrumento);
			}
			for (Instrumento instrumento : listaInstrumentos) {
				if (instrumento.getPuntas() != null) {
					String simbolo = instrumento.getSimbolo();
					BigDecimal precioCompraNuevo = instrumento.getPuntas().getPrecioCompra();
					BigDecimal precioVentaNuevo = instrumento.getPuntas().getPrecioVenta();
					Instrumento instrumentoAux = mapaInstrumentosAux.get(simbolo);
					if (instrumentoAux != null) {
						if (instrumentoAux.getPuntas() != null) {
							BigDecimal precioCompraViejo = instrumentoAux.getPuntas().getPrecioCompra();
							int comparacion = precioCompraNuevo.compareTo(precioCompraViejo);

							// Establecer el valor de flash en función de la comparación
							if (comparacion > 0) {
								// El precio subió
								instrumento.setFlashCompra(1);
							} else if (comparacion < 0) {
								// El precio bajó
								instrumento.setFlashCompra(-1);
							} else {
								// El precio se mantuvo igual
								instrumento.setFlashCompra(0);
							}
						}
					}
					if (instrumentoAux != null) {
						if (instrumentoAux.getPuntas() != null) {
							BigDecimal precioVentaViejo = instrumentoAux.getPuntas().getPrecioVenta();
							int comparacion = precioVentaNuevo.compareTo(precioVentaViejo);

							if (comparacion > 0) {
								// El precio subió
								instrumento.setFlashVenta(1);
							} else if (comparacion < 0) {
								// El precio bajó
								instrumento.setFlashVenta(-1);
							} else {
								// El precio se mantuvo igual
								instrumento.setFlashVenta(0);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void agregarInstrumentosAlPanelDeAcciones(List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			if (instrumento.getPuntas() != null) {
				panelAcciones.put(instrumento.getSimbolo(), instrumento);
			}
		}
	}

	@Override
	public void agregarInstrumentosAlPanelDeBonos(List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			if (instrumento.getPuntas() != null && instrumento.getPuntas().getPrecioCompra() != null
					&& instrumento.getPuntas().getPrecioVenta() != null) {
				panelBonos.put(instrumento.getSimbolo(), instrumento);
			}
		}
	}

	@Override
	public void agregarInstrumentosAlPanelDeCedears(List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			if (instrumento.getPuntas() != null && instrumento.getPuntas().getPrecioCompra() != null
					&& instrumento.getPuntas().getPrecioVenta() != null) {
				panelCedears.put(instrumento.getSimbolo(), instrumento);
			}
		}
	}

}
