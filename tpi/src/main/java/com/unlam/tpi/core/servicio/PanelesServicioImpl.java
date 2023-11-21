package com.unlam.tpi.core.servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.ListaPreciosIOL;
import com.unlam.tpi.core.interfaces.PanelPrecios;
import com.unlam.tpi.core.interfaces.PanelesServicio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.delivery.dto.InstrumentoMapper;

@Service
public class PanelesServicioImpl implements PanelesServicio {

	@Autowired
	PanelPrecios panelPrecios;

	@Autowired
	PosicionServicio posicionServicio;

	@Autowired
	InstrumentoServicio instrumentoServicio;

	@Autowired
	PuntasServicio puntasServicio;

	@Autowired
	ListaPreciosIOL listaPrecioServicio;

	public static List<Instrumento> listaInstrumentosAccionesAux = new ArrayList<>();
	public static List<Instrumento> listaInstrumentosBonosAux = new ArrayList<>();
	public static List<Instrumento> listaInstrumentosCedearsAux = new ArrayList<>();

	private final RestTemplate restTemplate;

	public PanelesServicioImpl() {
		this.restTemplate = new RestTemplate();
	}

	public PanelesServicioImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public PanelesServicioImpl(RestTemplate restTemplate, PanelPrecios panelPrecios) {
		this.restTemplate = restTemplate;
		this.panelPrecios = panelPrecios;
	}

	@Override
	public Map<String, Instrumento> getPanelDeAcciones() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio.getListaPrecioMongo(PanelesDePreciosConstantes.ACCIONES);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosAccionesAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosAccionesAux.addAll(listaInstrumentos);
		panelPrecios.agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return PanelPreciosImpl.panelAcciones;
	}

	@Override
	public Map<String, Instrumento> getPanelDeBonos() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio.getListaPrecioMongo(PanelesDePreciosConstantes.BONOS);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosBonosAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosBonosAux.addAll(listaInstrumentos);
		panelPrecios.agregarInstrumentosAlPanelDeBonos(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return PanelPreciosImpl.panelBonos;
	}

	@Override
	public Map<String, Instrumento> getPanelDeCedears() {
		Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
		List<Instrumento> listaInstrumentos = listaPrecioServicio.getListaPrecioMongo(PanelesDePreciosConstantes.CEDEARS);
		determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos, listaInstrumentosCedearsAux);
		recalcularPosicionTotalSegunVariacionDePrecios(listaInstrumentos);
		listaInstrumentosCedearsAux.addAll(listaInstrumentos);
		panelPrecios.agregarInstrumentosAlPanelDeCedears(listaInstrumentos);
		instrumentoServicio.persistirInstrumentos(listaInstrumentos);
		return PanelPreciosImpl.panelCedears;
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

	public ResponseEntity<String> getInstrumentos(String url) {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		return responseEntity;
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

}
