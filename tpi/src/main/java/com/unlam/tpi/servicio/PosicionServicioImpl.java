package com.unlam.tpi.servicio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.modelo.persistente.Instrumento;
import com.unlam.tpi.modelo.persistente.Posicion;
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

		BigDecimal totalMonedas = calcularPosicionMoneda(posicionTotal);
		valuacionTotalRespuesta.setTotalMonedas(totalMonedas.toString());

		BigDecimal totalInstrumentos = calcularPosicionEnInstrumentos(posicionTotal);
		valuacionTotalRespuesta.setTotalInstrumentos(totalInstrumentos.toString());

		BigDecimal totalCartera = calcularPosicionEnInstrumentos(posicionTotal);
		totalCartera = totalMonedas.add(totalInstrumentos);
		valuacionTotalRespuesta.setTotalCartera(totalCartera.toString());

		return valuacionTotalRespuesta;
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
