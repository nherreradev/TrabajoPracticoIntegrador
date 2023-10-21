package com.unlam.tpi.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
	
	@Transactional
	public Map<String, BigDecimal> obtenerCantidadPorInstrumento(List<Posicion> posicionTotal);

	@Transactional
	List<Posicion> obtenerPosicionTotal();

	@Transactional
	void actualizarPosicion(Posicion posicion);

}
