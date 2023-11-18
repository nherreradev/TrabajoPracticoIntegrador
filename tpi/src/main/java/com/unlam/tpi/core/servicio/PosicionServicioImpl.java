package com.unlam.tpi.core.servicio;

import static com.unlam.tpi.core.modelo.OrdenConstantes.COMPRA;
import static com.unlam.tpi.infraestructura.helpers.CalculosHabituales.esMasGrandeQue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoRendimientoServicio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PosicionRepositorio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.CargaCreditoConstantes;
import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.OrdenConstantes;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;
import com.unlam.tpi.core.modelo.RendimientoResponse;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;
import com.unlam.tpi.infraestructura.helpers.CalculosHabituales;

@Service
public class PosicionServicioImpl implements PosicionServicio {

	@Autowired
	PosicionRepositorio posicionRepositorio;

	@Autowired
	HistoricoRendimientoServicio historicoRendimientoServicio;

	@Autowired
	InstrumentoServicio instrumentoServicio;

	@Override
	public ValuacionTotalRespuesta getValuacionTotal(Long oidUsuario) {
		ValuacionTotalRespuesta valuacionTotalRespuesta = new ValuacionTotalRespuesta();
		List<Posicion> posicionTotal = posicionRepositorio.getPosicionByUsuarioOid(oidUsuario);

		BigDecimal premioPreguntasObjetivas = new BigDecimal(5000);

		BigDecimal totalCartera = BigDecimal.ZERO;
		BigDecimal porcentajeGananciaPerdida = BigDecimal.ZERO;

		BigDecimal totalMonedas = calcularPosicionMoneda(posicionTotal);
		valuacionTotalRespuesta.setTotalMonedas(totalMonedas.toString());

		BigDecimal totalInstrumentos = calcularPosicionEnInstrumentos(posicionTotal);
		valuacionTotalRespuesta.setTotalInstrumentos(totalInstrumentos.toString());

		valuacionTotalRespuesta.setCantidadPorInstrumento(obtenerCantidadPorInstrumento(posicionTotal));

		totalCartera = totalMonedas.add(totalInstrumentos);
		valuacionTotalRespuesta.setTotalCartera(totalCartera.toString());

		porcentajeGananciaPerdida = ((totalCartera.subtract(premioPreguntasObjetivas)).multiply(new BigDecimal(100)))
				.divide(premioPreguntasObjetivas);
		valuacionTotalRespuesta.setProcentajeGananciaPerdida(porcentajeGananciaPerdida.toString());

		return valuacionTotalRespuesta;
	}

	@Override
	public PuedeOperarResultado puedeOperar(Orden orden) {
		PuedeOperarResultado puedeOperarResultado = new PuedeOperarResultado();
		if (COMPRA.equals(orden.getSentido())) {
			List<Posicion> posicionEfectivo = posicionRepositorio.getPosicionEnEfectivo();
			BigDecimal totalDisponibleEnEfectivo = this.calcularPosicionMoneda(posicionEfectivo);
			completarPrecioDeLaOrden(orden);
			BigDecimal montoOrden = orden.getPrecio().multiply(orden.getCantidad());
			if (esMasGrandeQue(montoOrden, totalDisponibleEnEfectivo)) {
				puedeOperarResultado.setPuedeOperar(false);
				puedeOperarResultado.setDisponible(totalDisponibleEnEfectivo);
			} else {
				puedeOperarResultado.setPuedeOperar(true);
				Posicion posicionTitulos = new Posicion();
				completarPrecioDeLaOrden(orden);
				completarPosicionDeTitulos(orden, posicionTitulos, null);
				posicionRepositorio.save(posicionTitulos);

				Posicion posicionDinero = new Posicion();
				completarPosicionDeDinero(orden, posicionDinero);
				posicionRepositorio.save(posicionDinero);

			}
		} else {
			List<Posicion> titulosEnPosicionLista = posicionRepositorio
					.obtenerTodosLosMovimientosAsociadosAUnSimbolo(orden.getSimboloInstrumento());
			completarPrecioDeLaOrden(orden);
			Map<String, BigDecimal> instrumentosPorCantidad = obtenerCantidadPorInstrumento(titulosEnPosicionLista);
			BigDecimal cantidadTitulosAVender = orden.getCantidad();
			BigDecimal totalTitulosEnPosicion = instrumentosPorCantidad.get(orden.getSimboloInstrumento());
			if(totalTitulosEnPosicion == null) {
				puedeOperarResultado.setPuedeOperar(false);
				puedeOperarResultado.setDisponible(BigDecimal.ZERO);
				return puedeOperarResultado;
			}
			BigDecimal titulosQueMeQuedarianEnCartera = totalTitulosEnPosicion.subtract(cantidadTitulosAVender);

			if (instrumentosPorCantidad.containsKey(orden.getSimboloInstrumento())) {
				if (CalculosHabituales.esMasGrandeQue(cantidadTitulosAVender, totalTitulosEnPosicion)) {
					puedeOperarResultado.setPuedeOperar(false);
					puedeOperarResultado.setDisponible(totalTitulosEnPosicion);
				} else {

					puedeOperarResultado.setPuedeOperar(true);
					Posicion posicionDinero = new Posicion();

					completarPosicionDeTitulos(orden, posicionDinero, titulosQueMeQuedarianEnCartera);
					posicionRepositorio.save(posicionDinero);

					Posicion posiciontitulos = new Posicion();
					completarPosicionDeDinero(orden, posiciontitulos);
					posicionRepositorio.save(posiciontitulos);

				}
			}
		}
		return puedeOperarResultado;
	}

	@Override
	public void acreditarDinero(RequestCargaDeDinero request) {
		Posicion posicionBuscada = posicionRepositorio.obtenerPosicionPorConceptoYUsuario(request.getConcepto(),
				request.getUsuarioOid());
		if (posicionBuscada == null || !CargaCreditoConstantes.PREMIO_PREGUNTAS_OBJETIVAS
				.equals(posicionBuscada != null ? posicionBuscada.getConcepto() : null)) {
			Posicion posicion = new Posicion();
			posicion.setCantidad(request.getCantidadPorAcreditar());
			posicion.setEsEfectivo(true);
			posicion.setMonedaOid(1L);
			posicion.setUsuarioOid(request.getUsuarioOid());
			posicion.setConcepto(request.getConcepto());
			posicionRepositorio.save(posicion);
		}
	}

	private void completarPosicionDeDinero(Orden orden, Posicion posicionDinero) {

		boolean esCompra = orden.getSentido().equals(OrdenConstantes.COMPRA) ? true : false;

		if (esCompra) {
			posicionDinero.setCantidad(orden.getCantidad().multiply(orden.getPrecio()).multiply(new BigDecimal(-1)));
			posicionDinero.setEsEfectivo(true);
			posicionDinero.setFecha_posicion(LocalDateTime.now());
			posicionDinero.setMonedaOid(orden.getMonedaOid());
			posicionDinero.setPrecioActualDeVenta(null);
			posicionDinero.setUsuarioOid(orden.getUsuarioOid());
			posicionDinero.setSimboloInstrumento(orden.getSimboloInstrumento());
			posicionDinero.setConcepto("DINERO-COMPLE");
		} else {
			posicionDinero.setCantidad(orden.getCantidad().multiply(orden.getPrecio()));
			posicionDinero.setEsEfectivo(true);
			posicionDinero.setFecha_posicion(LocalDateTime.now());
			posicionDinero.setMonedaOid(orden.getMonedaOid());
			posicionDinero.setPrecioActualDeVenta(null);
			posicionDinero.setUsuarioOid(orden.getUsuarioOid());
			posicionDinero.setSimboloInstrumento(orden.getSimboloInstrumento());
			posicionDinero.setConcepto("DINERO-COMPLE");
		}

	}

	private void completarPosicionDeTitulos(Orden orden, Posicion posicion, BigDecimal titulosQueMeQuedarianEnCartera) {

		boolean esCompra = orden.getSentido().equals(OrdenConstantes.COMPRA) ? true : false;

		if (!esCompra) {
			if (titulosQueMeQuedarianEnCartera.compareTo(BigDecimal.ZERO) <= 0) {
				posicion.setLiquidoExistenciaDelSimbolo(true);
			}
			posicion.setCantidad(orden.getCantidad().multiply(new BigDecimal(-1)));
			posicion.setConcepto("TITULOS-ORIG");
		} else {
			posicion.setCantidad(orden.getCantidad());
			posicion.setConcepto("TITULOS-ORIG");
		}

		posicion.setEsEfectivo(false);
		posicion.setFecha_posicion(LocalDateTime.now());
		posicion.setMonedaOid(orden.getMonedaOid());
		posicion.setPrecioActualDeVenta(orden.getPrecio());
		posicion.setPrecioAlMomentoDeCompra(orden.getPrecio());
		posicion.setUsuarioOid(orden.getUsuarioOid());
		posicion.setSimboloInstrumento(orden.getSimboloInstrumento());
	}

	private void completarPrecioDeLaOrden(Orden orden) {
		switch (orden.getCategoriaInstrumento()) {
		case PanelesDePreciosConstantes.ACCIONES:
			if (PanelPreciosImpl.panelAcciones.containsKey(orden.getSimboloInstrumento())) {
				if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
					Puntas puntas = PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioVenta = puntas != null && puntas.getPrecioVenta() != null ? puntas.getPrecioVenta()
							: null;
					orden.setPrecio(precioVenta);
				} else {
					Puntas puntas = PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioCompra = puntas != null && puntas.getPrecioCompra() != null
							? puntas.getPrecioCompra()
							: null;
					orden.setPrecio(precioCompra);
				}
			} else {
				throw new ServiceException(
						"La orden que quiere capturar no se encuentra disponible en el panel o no tiene precio");
			}

			break;

		case PanelesDePreciosConstantes.BONOS:
			if (PanelPreciosImpl.panelBonos.containsKey(orden.getSimboloInstrumento())) {
				if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
					Puntas puntas = PanelPreciosImpl.panelBonos.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioVenta = puntas != null && puntas.getPrecioCompra() != null
							? puntas.getPrecioVenta()
							: null;
					orden.setPrecio(precioVenta);
				} else {
					Puntas puntas = PanelPreciosImpl.panelBonos.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioCompra = puntas != null && puntas.getPrecioVenta() != null
							? puntas.getPrecioCompra()
							: null;
					orden.setPrecio(precioCompra);
				}
			} else {
				throw new ServiceException(
						"La orden que quiere capturar no se encuentra disponible en el panel o no tiene precio");
			}

			break;

		case PanelesDePreciosConstantes.CEDEARS:
			if (PanelPreciosImpl.panelCedears.containsKey(orden.getSimboloInstrumento())) {
				if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
					Puntas puntas = PanelPreciosImpl.panelCedears.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioVenta = puntas != null && puntas.getPrecioCompra() != null
							? puntas.getPrecioVenta()
							: null;
					orden.setPrecio(precioVenta);
				} else {
					Puntas puntas = PanelPreciosImpl.panelCedears.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioCompra = puntas != null && puntas.getPrecioVenta() != null
							? puntas.getPrecioCompra()
							: null;
					orden.setPrecio(precioCompra);
				}
			} else {
				throw new ServiceException(
						"La orden que quiere capturar no se encuentra disponible en el panel o no tiene precio");
			}

			break;
		}
	}

	@Override
	public Map<String, BigDecimal> obtenerCantidadPorInstrumento(List<Posicion> posicionTotal) {
		Map<String, BigDecimal> instrumentosPorCantidad = new HashMap<>();
		for (Posicion posicion : posicionTotal) {

			if (posicion.getSimboloInstrumento() != null && !posicion.getEsEfectivo()) {
				String simbolo = posicion.getSimboloInstrumento();
				BigDecimal cantidad = posicion.getCantidad();

				if (instrumentosPorCantidad.containsKey(simbolo)) {
					BigDecimal cantidadActual = instrumentosPorCantidad.get(simbolo);
					BigDecimal cantidadAcumulada = cantidadActual.add(cantidad);
					instrumentosPorCantidad.put(simbolo, cantidadAcumulada);
				} else {

					instrumentosPorCantidad.put(simbolo, cantidad);
				}
			}
		}
		return instrumentosPorCantidad;
	}

	@Override
	public List<Posicion> obtenerPosicionTotal() {
		return posicionRepositorio.findAll();
	}

	@Override
	public void actualizarPosicion(Posicion posicion) {
		/* JPARepository si la entidad existe hace una actualizacion */
		posicionRepositorio.save(posicion);

	}

	private BigDecimal calcularPosicionEnInstrumentos(List<Posicion> posicionTotal) {
		BigDecimal totalInstrumentos = BigDecimal.ZERO;
		Map<String, BigDecimal> posicionesPorSimbolos = new HashMap<>();
		for (Posicion posicion : posicionTotal) {

			if (posicion.getSimboloInstrumento() != null && !posicion.getEsEfectivo()) {
				if (!posicionesPorSimbolos.containsKey(posicion.getSimboloInstrumento())) {
					posicionesPorSimbolos.put(posicion.getSimboloInstrumento(), posicion.getCantidad());
				} else {
					BigDecimal cantidad = posicionesPorSimbolos.get(posicion.getSimboloInstrumento());
					cantidad = cantidad.add(posicion.getCantidad());
					posicionesPorSimbolos.put(posicion.getSimboloInstrumento(), cantidad);
				}
			}

		}

		for (Map.Entry<String, BigDecimal> entry : posicionesPorSimbolos.entrySet()) {
			String simbolo = entry.getKey();
			BigDecimal cantidad = entry.getValue();

			if (cantidad.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal precioActual = obtenerPrecioActualDelPanel(simbolo);
				totalInstrumentos = totalInstrumentos.add(cantidad.multiply(precioActual));
			}

		}

		return totalInstrumentos;
	}

	private BigDecimal obtenerPrecioActualDelPanel(String simbolo) {

		BigDecimal precioActual = BigDecimal.ZERO;

		if (PanelPreciosImpl.panelAcciones.containsKey(simbolo)) {
			Instrumento instrumento = PanelPreciosImpl.panelAcciones.get(simbolo);

			BigDecimal precioVenta = instrumento != null && instrumento.getPuntas() != null
					? instrumento.getPuntas().getPrecioVenta()
					: null;

			precioActual = precioVenta;

		} else if (PanelPreciosImpl.panelBonos.containsKey(simbolo)) {
			Instrumento instrumento = PanelPreciosImpl.panelBonos.get(simbolo);

			BigDecimal precioVenta = instrumento != null && instrumento.getPuntas() != null
					? instrumento.getPuntas().getPrecioVenta()
					: null;

			precioActual = precioVenta;

		} else if (PanelPreciosImpl.panelCedears.containsKey(simbolo)) {
			Instrumento instrumento = PanelPreciosImpl.panelCedears.get(simbolo);

			BigDecimal precioVenta = instrumento != null && instrumento.getPuntas() != null
					? instrumento.getPuntas().getPrecioVenta()
					: null;

			precioActual = precioVenta;

		}

		return precioActual;
	}

	private BigDecimal calcularPosicionMoneda(Iterable<Posicion> posicionTotal) {
		BigDecimal totalMonedas = BigDecimal.ZERO;
		for (Posicion posicion : posicionTotal) {
			if (posicion.getEsEfectivo()) {
				totalMonedas = totalMonedas.add(posicion.getCantidad());
			}
		}
		return totalMonedas;
	}

	@Override
	public RendimientoActualResponse calcularRendimientoActual(Long usuarioOid) {

		RendimientoActualResponse rendimientoActualResponse = new RendimientoActualResponse();
		Map<String, RendimientoResponse> mapaRendimientos = new HashMap<>();
		List<Posicion> posicion = posicionRepositorio.obtenerTodosLosTitulos(usuarioOid);
		List<Posicion> posicionEnCartera = obtenerSoloPosicionesEnCartera(posicion);
		String simboloAux = "";

		if (posicionEnCartera != null && !posicionEnCartera.isEmpty()) {

			BigDecimal costoTotalDeLasCompras = new BigDecimal(0);

			BigDecimal cantidadTotalDeInstrumentosQueTengo = new BigDecimal(0);
			BigDecimal valorActualDeLaInversion = new BigDecimal(0);
			BigDecimal gananciaTotalOPerdidaMonto = new BigDecimal(0);
			BigDecimal gananciaTotalOPerdidaPorcentaje = new BigDecimal(0);

			Instrumento instrumentoDelPanel = null;

			for (Posicion posicion2 : posicionEnCartera) {

				if (!posicion2.getSimboloInstrumento().equals(simboloAux)) {
					costoTotalDeLasCompras = BigDecimal.ZERO;
					cantidadTotalDeInstrumentosQueTengo = BigDecimal.ZERO;
					valorActualDeLaInversion = BigDecimal.ZERO;
					gananciaTotalOPerdidaMonto = BigDecimal.ZERO;
					gananciaTotalOPerdidaPorcentaje = BigDecimal.ZERO;
				}

				Instrumento instrumentoObtenido = instrumentoServicio
						.obtenerInstrumentoPorSimbolo(posicion2.getSimboloInstrumento());

				switch (instrumentoObtenido.getCategoriaInstrumento()) {
				case "acciones":
					instrumentoDelPanel = PanelPreciosImpl.panelAcciones.get(posicion2.getSimboloInstrumento());
					break;

				case "bonos":
					instrumentoDelPanel = PanelPreciosImpl.panelBonos.get(posicion2.getSimboloInstrumento());
					break;

				default:
					break;
				}

				BigDecimal precioActualDelInstrumento = instrumentoDelPanel != null
						&& instrumentoDelPanel.getPuntas() != null
						&& instrumentoDelPanel.getPuntas().getPrecioVenta() != null
								? instrumentoDelPanel.getPuntas().getPrecioVenta()
								: BigDecimal.ZERO;

				RendimientoResponse rendimientoResponse = new RendimientoResponse();

				costoTotalDeLasCompras = costoTotalDeLasCompras
						.add(posicion2.getPrecioAlMomentoDeCompra().multiply(posicion2.getCantidad()));
				cantidadTotalDeInstrumentosQueTengo = cantidadTotalDeInstrumentosQueTengo.add(posicion2.getCantidad());
				valorActualDeLaInversion = cantidadTotalDeInstrumentosQueTengo.multiply(precioActualDelInstrumento);
				gananciaTotalOPerdidaMonto = valorActualDeLaInversion.subtract(costoTotalDeLasCompras);
				gananciaTotalOPerdidaPorcentaje = (gananciaTotalOPerdidaMonto.divide(costoTotalDeLasCompras, 2,
						RoundingMode.HALF_UP)).multiply(new BigDecimal(100));

				completarRendimiento(costoTotalDeLasCompras, gananciaTotalOPerdidaMonto,
						gananciaTotalOPerdidaPorcentaje, posicion2, rendimientoResponse,
						cantidadTotalDeInstrumentosQueTengo, valorActualDeLaInversion);

				String key = posicion2.getSimboloInstrumento();

				if (mapaRendimientos.containsKey(key)) {

					mapaRendimientos.remove(key);

					mapaRendimientos.put(key, rendimientoResponse);

				} else {
					mapaRendimientos.put(key, rendimientoResponse);

				}

				simboloAux = key;

			}
		}

		rendimientoActualResponse.setRendimientosActuales(mapaRendimientos);

		return rendimientoActualResponse;

	}

	@Override
	public List<HistoricoRendimientosResponse> obtenerRendimientosHistoricosPorSimbolo(String simboloInstrumento,
			Long usuarioOid) {
		return historicoRendimientoServicio.obtenerRendimientosHistoricosPorSimbolo(simboloInstrumento, usuarioOid);
	}

	private void completarRendimiento(BigDecimal costoTotalDeLasCompras, BigDecimal gananciaTotalOPerdidaMonto,
			BigDecimal gananciaTotalOPerdidaPorcentaje, Posicion posicion2, RendimientoResponse rendimientoResponse,
			BigDecimal cantidadDeInstrumentos, BigDecimal valorActualDeLaInversion) {
		rendimientoResponse.setSimbolo(posicion2.getSimboloInstrumento());
		rendimientoResponse.setRendimientoTotal(gananciaTotalOPerdidaMonto);
		rendimientoResponse.setRendimientoTotalPorcentaje(gananciaTotalOPerdidaPorcentaje);
		rendimientoResponse.setCantidadDeTitulos(cantidadDeInstrumentos);
		rendimientoResponse.setValorActualDeLaInversion(valorActualDeLaInversion);
		rendimientoResponse.setFecha(posicion2.getFecha_posicion());
	}

	public void guardarCierresDiarios(Map<String, RendimientoResponse> mapaRendimientos, Long usuarioOid) {

		for (Map.Entry<String, RendimientoResponse> entry : mapaRendimientos.entrySet()) {

			RendimientoResponse rendimientoResponse = entry.getValue();
			HistoricoRendimientos historicoRendimientos = new HistoricoRendimientos();
			historicoRendimientos.setSimbolo(rendimientoResponse.getSimbolo());
			historicoRendimientos.setRendimientoTotal(rendimientoResponse.getRendimientoTotal());
			historicoRendimientos.setRendimientoTotalPorcentaje(rendimientoResponse.getRendimientoTotalPorcentaje());
			historicoRendimientos.setFecha(rendimientoResponse.getFecha());
			historicoRendimientos.setCantidadDeTitulos(rendimientoResponse.getCantidadDeTitulos());
			historicoRendimientos.setValorInversion(rendimientoResponse.getValorActualDeLaInversion());
			historicoRendimientos.setUsuarioOid(usuarioOid);

			historicoRendimientoServicio.guardar(historicoRendimientos);

		}

	}

	private List<Posicion> obtenerSoloPosicionesEnCartera(List<Posicion> todasLasPosiciones) {

		List<Posicion> listaFiltrada = new ArrayList<>();

		Map<String, List<Posicion>> posicionesPorSimbolo = new LinkedHashMap<>();

		for (Posicion posicion2 : todasLasPosiciones) {
			if (!posicionesPorSimbolo.containsKey(posicion2.getSimboloInstrumento())) {
				posicionesPorSimbolo.put(posicion2.getSimboloInstrumento(), new ArrayList<Posicion>());
				posicionesPorSimbolo.get(posicion2.getSimboloInstrumento()).add(0, posicion2);
			} else {
				posicionesPorSimbolo.get(posicion2.getSimboloInstrumento()).add(0, posicion2);
			}
		}

		for (Map.Entry<String, List<Posicion>> entry : posicionesPorSimbolo.entrySet()) {
			List<Posicion> posiciones = entry.getValue();

			for (Posicion posicion : posiciones) {

				if (!Boolean.TRUE.equals(posicion.liquidoExistenciaDelSimbolo())) {
					listaFiltrada.add(posicion);
				} else {
					break;
				}

			}
		}

		return listaFiltrada;
	}

}
