package com.unlam.tpi.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.unlam.tpi.modelo.persistente.Orden;
import com.unlam.tpi.modelo.persistente.Posicion;
import com.unlam.tpi.modelo.pojo.PuedeOperarResultado;
import com.unlam.tpi.modelo.pojo.RequestCargaDeDinero;
import com.unlam.tpi.modelo.rest.ValuacionTotalRespuesta;

public interface PosicionServicio {

	@Transactional
	ValuacionTotalRespuesta getValuacionTotal();

	@Transactional
	PuedeOperarResultado puedeOperar(Orden orden);

	@Transactional
	void acreditarDinero(RequestCargaDeDinero posicionParcial);

}
