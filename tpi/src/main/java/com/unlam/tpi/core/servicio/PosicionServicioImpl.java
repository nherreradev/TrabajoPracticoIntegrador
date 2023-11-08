package com.unlam.tpi.core.servicio;

import static com.unlam.tpi.core.modelo.OrdenConstantes.COMPRA;
import static com.unlam.tpi.infraestructura.helpers.CalculosHabituales.esMasGrandeQue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoDeGananciaPerdidaDiariaServicio;
import com.unlam.tpi.core.interfaces.PosicionRepositorio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.CargaCreditoConstantes;
import com.unlam.tpi.core.modelo.HistoricoDeGananciaPerdidaDiaria;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.OrdenConstantes;
import com.unlam.tpi.core.modelo.PanelesDePreciosConstantes;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.ResponsePorcentajeDiario;
import com.unlam.tpi.core.modelo.ResponsePorcentajeUnificado;
import com.unlam.tpi.core.modelo.ResponseTotalPorInstrumentoYPorDia;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;
import com.unlam.tpi.infraestructura.helpers.CalculosHabituales;

@Service
public class PosicionServicioImpl implements PosicionServicio {

	@Autowired
	PosicionRepositorio posicionRepositorio;

	@Autowired
	HistoricoDeGananciaPerdidaDiariaServicio historicoDeGananciaPerdidaDiariaServicio;

	@Override
	public ValuacionTotalRespuesta getValuacionTotal() {
		ValuacionTotalRespuesta valuacionTotalRespuesta = new ValuacionTotalRespuesta();
		List<Posicion> posicionTotal = posicionRepositorio.findAll();

		BigDecimal totalCartera = BigDecimal.ZERO;

		BigDecimal totalMonedas = calcularPosicionMoneda(posicionTotal);
		valuacionTotalRespuesta.setTotalMonedas(totalMonedas.toString());

		BigDecimal totalInstrumentos = calcularPosicionEnInstrumentos(posicionTotal);
		valuacionTotalRespuesta.setTotalInstrumentos(totalInstrumentos.toString());

		valuacionTotalRespuesta.setCantidadPorInstrumento(obtenerCantidadPorInstrumento(posicionTotal));

		totalCartera = totalMonedas.add(totalInstrumentos);
		valuacionTotalRespuesta.setTotalCartera(totalCartera.toString());

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
				completarPosicionDeTitulos(orden, posicionTitulos);
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
			if (instrumentosPorCantidad.containsKey(orden.getSimboloInstrumento())) {
				if (CalculosHabituales.esMasGrandeQue(cantidadTitulosAVender, totalTitulosEnPosicion)) {
					puedeOperarResultado.setPuedeOperar(false);
					puedeOperarResultado.setDisponible(totalTitulosEnPosicion);
				} else {
					puedeOperarResultado.setPuedeOperar(true);

					Posicion posicionDinero = new Posicion();
					completarPosicionDeTitulos(orden, posicionDinero);
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
		try {
			Posicion posicionBuscada = posicionRepositorio.obtenerPosicionPorConcepto(request.getConcepto());
			if (posicionBuscada == null
					|| posicionBuscada.getConcepto() != CargaCreditoConstantes.PREMIO_PREGUNTAS_OBJETIVAS) {
				Posicion posicion = new Posicion();
				posicion.setCantidad(request.getCantidadPorAcreditar());
				posicion.setEsEfectivo(true);
				posicion.setMonedaOid(1L);
				posicion.setUsuarioOid(1L);
				posicion.setConcepto(request.getConcepto());
				posicionRepositorio.save(posicion);
			}
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException("Ocurrio un error al acreditar el dinero");
		}
	}

	private void completarPosicionDeDinero(Orden orden, Posicion posicionDinero) {

		boolean esCompra = orden.getSentido().equals(OrdenConstantes.COMPRA) ? true : false;

		if (esCompra) {
			posicionDinero.setCantidad(orden.getCantidad().multiply(orden.getPrecio()).multiply(new BigDecimal(-1)));
			posicionDinero.setEsEfectivo(true);
			posicionDinero.setFecha_posicion(LocalDate.now());
			posicionDinero.setMonedaOid(orden.getMonedaOid());
			posicionDinero.setPrecio(null);
			posicionDinero.setUsuarioOid(1L);/* A futuro aca hay que sacar el usuario del contexto */
			posicionDinero.setSimboloInstrumento(orden.getSimboloInstrumento());
			posicionDinero.setConcepto("DINERO-COMPLE");
		} else {
			posicionDinero.setCantidad(orden.getCantidad().multiply(orden.getPrecio()));
			posicionDinero.setEsEfectivo(true);
			posicionDinero.setFecha_posicion(LocalDate.now());
			posicionDinero.setMonedaOid(orden.getMonedaOid());
			posicionDinero.setPrecio(null);
			posicionDinero.setUsuarioOid(1L);/* A futuro aca hay que sacar el usuario del contexto */
			posicionDinero.setSimboloInstrumento(orden.getSimboloInstrumento());
			posicionDinero.setConcepto("DINERO-COMPLE");
		}

	}

	private void completarPosicionDeTitulos(Orden orden, Posicion posicion) {

		boolean esCompra = orden.getSentido().equals(OrdenConstantes.COMPRA) ? true : false;

		if (!esCompra) {
			posicion.setCantidad(orden.getCantidad().multiply(new BigDecimal(-1)));
			posicion.setConcepto("TITULOS-ORIG");
		} else {
			posicion.setCantidad(orden.getCantidad());
			posicion.setConcepto("TITULOS-ORIG");
		}

		posicion.setEsEfectivo(false);
		posicion.setFecha_posicion(LocalDate.now());
		posicion.setMonedaOid(orden.getMonedaOid());
		posicion.setPrecio(orden.getPrecio());
		posicion.setPrecioAlMomentoDeCompra(orden.getPrecio());
		posicion.setUsuarioOid(1L);/* A futuro aca hay que sacar el usuario del contexto */
		posicion.setSimboloInstrumento(orden.getSimboloInstrumento());
	}

	private void completarPrecioDeLaOrden(Orden orden) {
		switch (orden.getCategoriaInstrumento()) {
		case PanelesDePreciosConstantes.ACCIONES:
			if (PanelPreciosImpl.panelAcciones.containsKey(orden.getSimboloInstrumento())) {
				if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
					Puntas puntas = PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioCompra = puntas != null && puntas.getPrecioCompra() != null
							? puntas.getPrecioCompra()
							: null;
					orden.setPrecio(precioCompra);
				} else {
					Puntas puntas = PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas();
					BigDecimal precioVenta = puntas != null && puntas.getPrecioVenta() != null ? puntas.getPrecioVenta()
							: null;
					orden.setPrecio(precioVenta);
				}
			} else {
				throw new ServiceException(
						"La orden que quiere capturar no se encuentra disponible en el panel o no tiene precio");
			}

			break;

		case PanelesDePreciosConstantes.BONOS:
			if (PanelPreciosImpl.panelBonos.containsKey(orden.getSimboloInstrumento())) {
				if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
					orden.setPrecio(PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas()
							.getPrecioCompra());
				} else {
					orden.setPrecio(PanelPreciosImpl.panelAcciones.get(orden.getSimboloInstrumento()).getPuntas()
							.getPrecioVenta());
				}
			} else {
				throw new ServiceException("La orden que quiere capturar no se encuentra disponible en el panel");
			}

			break;

		default:
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
		for (Posicion posicion : posicionTotal) {
			if (!posicion.getEsEfectivo()) {
				totalInstrumentos = totalInstrumentos.add(posicion.getPrecio().multiply(posicion.getCantidad()));
			}
		}
		return totalInstrumentos;
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
	public ResponseTotalPorInstrumentoYPorDia calcularPorcentajeGananciaPerdida(String token) {
		/*
		 * Deberia traerme Cantidad y precio original de compra por instrumento recibido
		 * por usuario
		 */

		ResponseTotalPorInstrumentoYPorDia ResponseTotalPorInstrumentoYPorDia = new ResponseTotalPorInstrumentoYPorDia();

		Map<String, ResponsePorcentajeDiario> instrumentosTotalesPorDia = new HashMap<>();

		List<Posicion> posicion = posicionRepositorio.obtenerTodosLosTitulos();

		List<Posicion> posicionEnCartera = obtenerSoloPosicionesEnCartera(posicion);

		if (posicionEnCartera != null && !posicionEnCartera.isEmpty()) {

			String simboloActual = null;
			BigDecimal costoTotalDeLasCompras = new BigDecimal(0);
			BigDecimal precioActualDelInstrumento = new BigDecimal(2000); // pendiente de traer del panel
			BigDecimal cantidadTotalDeInstrumentosQueTengo = new BigDecimal(0);
			BigDecimal valorActualDeLaInversion = new BigDecimal(0);
			BigDecimal gananciaTotalOPerdidaMonto = new BigDecimal(0);
			BigDecimal gananciaTotalOPerdidaPorcentaje = new BigDecimal(0);

			/* Calcular el total gastado basandome en el precio original */

			for (Posicion posicion2 : posicionEnCartera) {

				String simbolo = posicion2.getSimboloInstrumento();

				if (simboloActual == null) {
					simboloActual = simbolo;
				}

				ResponsePorcentajeDiario responsePorcentaje = new ResponsePorcentajeDiario();

				costoTotalDeLasCompras = costoTotalDeLasCompras
						.add(posicion2.getPrecioAlMomentoDeCompra().multiply(posicion2.getCantidad()));
				cantidadTotalDeInstrumentosQueTengo = cantidadTotalDeInstrumentosQueTengo.add(posicion2.getCantidad());

				valorActualDeLaInversion = cantidadTotalDeInstrumentosQueTengo.multiply(precioActualDelInstrumento);

				gananciaTotalOPerdidaMonto = valorActualDeLaInversion.subtract(costoTotalDeLasCompras);

				gananciaTotalOPerdidaPorcentaje = (gananciaTotalOPerdidaMonto.divide(costoTotalDeLasCompras, 2,
						RoundingMode.HALF_UP)).multiply(new BigDecimal(100));

				responsePorcentaje.setSimbolo(posicion2.getSimboloInstrumento());
				responsePorcentaje.setTotalGananciaDelDia(gananciaTotalOPerdidaMonto);
				responsePorcentaje.setTotalPorcentajeDelDia(gananciaTotalOPerdidaPorcentaje);
				responsePorcentaje.setSimbolo(posicion2.getSimboloInstrumento());
				responsePorcentaje.setCostoTotalDeLasComprasDelDia(costoTotalDeLasCompras);
				responsePorcentaje.setFecha(posicion2.getFecha_posicion());

				String key = posicion2.getSimboloInstrumento() + "+" + posicion2.getFecha_posicion();

				if (instrumentosTotalesPorDia.containsKey(key)) {
					ResponsePorcentajeDiario responseMap = instrumentosTotalesPorDia.get(key);

					responseMap.setTotalGananciaDelDia(
							responseMap.getTotalGananciaDelDia().add(gananciaTotalOPerdidaMonto));

					responseMap.setCostoTotalDeLasComprasDelDia(
							responseMap.getCostoTotalDeLasComprasDelDia().add(costoTotalDeLasCompras));
					instrumentosTotalesPorDia.put(key, responseMap);

					responseMap.setTotalPorcentajeDelDia(
							(responseMap.getTotalGananciaDelDia().divide(responseMap.getCostoTotalDeLasComprasDelDia(),
									2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));

					costoTotalDeLasCompras = BigDecimal.ZERO;
					cantidadTotalDeInstrumentosQueTengo = BigDecimal.ZERO;
					valorActualDeLaInversion = BigDecimal.ZERO;
					gananciaTotalOPerdidaMonto = BigDecimal.ZERO;
					gananciaTotalOPerdidaPorcentaje = BigDecimal.ZERO;

				} else {
					instrumentosTotalesPorDia.put(key, responsePorcentaje);
					costoTotalDeLasCompras = BigDecimal.ZERO;
					cantidadTotalDeInstrumentosQueTengo = BigDecimal.ZERO;
					valorActualDeLaInversion = BigDecimal.ZERO;
					gananciaTotalOPerdidaMonto = BigDecimal.ZERO;
					gananciaTotalOPerdidaPorcentaje = BigDecimal.ZERO;
				}
			}
		}

		Map<String, ResponsePorcentajeUnificado> instrumentosUnificados = totalesPorInstrumento(
				instrumentosTotalesPorDia);

		ResponseTotalPorInstrumentoYPorDia.setInstrumentosDiarios(instrumentosTotalesPorDia);
		ResponseTotalPorInstrumentoYPorDia.setInstrumentosDiariosUnificados(instrumentosUnificados);

		guardarCierresDiarios(instrumentosTotalesPorDia);

		return ResponseTotalPorInstrumentoYPorDia;

	}

	private void guardarCierresDiarios(Map<String, ResponsePorcentajeDiario> instrumentosTotalesPorDia) {
		String fechaHoy = LocalDate.now().toString();

		for (Map.Entry<String, ResponsePorcentajeDiario> entry : instrumentosTotalesPorDia.entrySet()) {
			String fechaObjeto = entry.getKey().split("\\+")[1];

			if (!fechaObjeto.equals(fechaHoy)) {

				ResponsePorcentajeDiario responsePorcentajeDiario = entry.getValue();
				HistoricoDeGananciaPerdidaDiaria historicoDeGananciaPerdidaDiaria = new HistoricoDeGananciaPerdidaDiaria();
				historicoDeGananciaPerdidaDiaria.setSimbolo(responsePorcentajeDiario.getSimbolo());
				historicoDeGananciaPerdidaDiaria
						.setTotalGananciaDelDia(responsePorcentajeDiario.getTotalGananciaDelDia());
				historicoDeGananciaPerdidaDiaria
						.setTotalPorcentajeDelDia(responsePorcentajeDiario.getTotalPorcentajeDelDia());
				historicoDeGananciaPerdidaDiaria.setFecha(responsePorcentajeDiario.getFecha());

				historicoDeGananciaPerdidaDiariaServicio.guardar(historicoDeGananciaPerdidaDiaria);

			}

		}
	}

	private Map<String, ResponsePorcentajeUnificado> totalesPorInstrumento(
			Map<String, ResponsePorcentajeDiario> responseMapaDeTotales) {

		Map<String, ResponsePorcentajeUnificado> totalGananciaPorInstrumentoUnificado = new HashMap<>();

		for (String key : responseMapaDeTotales.keySet()) {
			ResponsePorcentajeDiario responsePorcentajeDiario = responseMapaDeTotales.get(key);
			String simbolo = responsePorcentajeDiario.getSimbolo();
			BigDecimal totalGananciaDelDia = responsePorcentajeDiario.getTotalGananciaDelDia();
			BigDecimal costoTotalDeCompra = responsePorcentajeDiario.getCostoTotalDeLasComprasDelDia();

			ResponsePorcentajeUnificado responsePorcentajeUnificado = new ResponsePorcentajeUnificado();
			responsePorcentajeUnificado.setTotalGananciaUnificado(totalGananciaDelDia);
			responsePorcentajeUnificado.setSimbolo(simbolo);

			if (totalGananciaPorInstrumentoUnificado.containsKey(simbolo)) {
				BigDecimal totalGananciaUnificado = totalGananciaPorInstrumentoUnificado.get(simbolo)
						.getTotalGananciaUnificado();
				totalGananciaUnificado = totalGananciaUnificado.add(totalGananciaDelDia);
				responsePorcentajeUnificado.setTotalGananciaUnificado(totalGananciaUnificado);
				totalGananciaPorInstrumentoUnificado.put(simbolo, responsePorcentajeUnificado);
			} else {
				totalGananciaPorInstrumentoUnificado.put(simbolo, responsePorcentajeUnificado);
			}

		}

		return totalGananciaPorInstrumentoUnificado;

	}

	private List<Posicion> obtenerSoloPosicionesEnCartera(List<Posicion> todasLasPosiciones) {

		Map<String, BigDecimal> cantidadesPorSimbolo = new HashMap<>();

		for (Posicion posicion2 : todasLasPosiciones) {
			String simbolo = posicion2.getSimboloInstrumento();
			BigDecimal cantidad = posicion2.getCantidad();

			BigDecimal cantidadAcumulada = cantidadesPorSimbolo.getOrDefault(simbolo, BigDecimal.ZERO);
			cantidadAcumulada = cantidadAcumulada.add(cantidad);
			cantidadesPorSimbolo.put(simbolo, cantidadAcumulada);
		}

		List<Posicion> posicionesConCantidadesPositivas = todasLasPosiciones.stream().filter(
				posicion -> cantidadesPorSimbolo.get(posicion.getSimboloInstrumento()).compareTo(BigDecimal.ZERO) > 0)
				.sorted(Comparator.comparing(Posicion::getFecha_posicion)
						.thenComparing(Posicion::getSimboloInstrumento))
				.collect(Collectors.toList());

		return posicionesConCantidadesPositivas;
	}
}
