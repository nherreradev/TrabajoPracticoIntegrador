package com.unlam.tpi.core.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.unlam.tpi.infraestructura.modelo.Orden;
import com.unlam.tpi.infraestructura.modelo.Posicion;
import com.unlam.tpi.infraestructura.modelo.PuedeOperarResultado;
import com.unlam.tpi.infraestructura.modelo.RequestCargaDeDinero;
import com.unlam.tpi.infraestructura.modelo.ValuacionTotalRespuesta;

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
