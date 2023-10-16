package com.unlam.tpi.servicio;

import org.springframework.transaction.annotation.Transactional;

import com.unlam.tpi.modelo.persistente.Orden;
import com.unlam.tpi.modelo.pojo.PuedeOperarResultado;
import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;

public interface PosicionServicio {

	@Transactional
	ValuacionTotalRespuesta getValuacionTotal();

	@Transactional
	PuedeOperarResultado puedeOperar(Orden orden);

	@Transactional
	void acreditarDinero();

}
