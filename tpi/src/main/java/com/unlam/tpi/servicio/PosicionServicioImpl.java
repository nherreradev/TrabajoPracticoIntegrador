package com.unlam.tpi.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.constantes.OrdenConstantes;
import com.unlam.tpi.constantes.PanelesDePreciosConstantes;
import com.unlam.tpi.helpers.CalculosHabituales;
import com.unlam.tpi.modelo.persistente.Orden;
import com.unlam.tpi.modelo.persistente.Posicion;
import com.unlam.tpi.modelo.persistente.Puntas;
import com.unlam.tpi.modelo.pojo.PuedeOperarResultado;
import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;
import com.unlam.tpi.repositorio.PosicionRepositorio;

@Service
public class PosicionServicioImpl implements PosicionServicio {

	@Autowired
	PosicionRepositorio PosicionRepositorio;

	@Override
	public ValuacionTotalRespuesta getValuacionTotal() {
		ValuacionTotalRespuesta valuacionTotalRespuesta = new ValuacionTotalRespuesta();
		List<Posicion> posicionTotal = PosicionRepositorio.findAll();

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
		if (OrdenConstantes.COMPRA.equals(orden.getSentido())) {
			List<Posicion> posicionEfectivo = PosicionRepositorio.getPosicionEnEfectivo();
			BigDecimal totalDisponibleEnEfectivo = this.calcularPosicionMoneda(posicionEfectivo);
			completarPrecioDeLaOrden(orden);
			BigDecimal montoOrden = orden.getPrecio().multiply(orden.getCantidad());
			if (CalculosHabituales.esMasGrandeQue(montoOrden, totalDisponibleEnEfectivo)) {
				puedeOperarResultado.setPuedeOperar(false);
				puedeOperarResultado.setDisponible(totalDisponibleEnEfectivo);
			} else {
				puedeOperarResultado.setPuedeOperar(true);
				Posicion posicionTitulos = new Posicion();
				completarPosicionDeTitulos(orden, posicionTitulos);
				PosicionRepositorio.save(posicionTitulos);

				Posicion posicionDinero = new Posicion();
				completarPosicionDeDinero(orden, posicionDinero);
				PosicionRepositorio.save(posicionDinero);

			}
		} else {
			List<Posicion> titulosEnPosicionLista = PosicionRepositorio
					.getTitulosDisponiblesPorSimbolo(orden.getSimboloInstrumento());
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
					PosicionRepositorio.save(posicionDinero);

					Posicion posiciontitulos = new Posicion();
					completarPosicionDeDinero(orden, posiciontitulos);
					PosicionRepositorio.save(posiciontitulos);

				}
			}
		}
		return puedeOperarResultado;
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
				throw new ServiceException("La orden que quiere capturar no se encuentra disponible en el panel o no tiene precio");
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

	private Map<String, BigDecimal> obtenerCantidadPorInstrumento(List<Posicion> posicionTotal) {
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
}
