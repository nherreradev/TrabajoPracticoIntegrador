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

		return valuacionTotalRespuesta;
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
