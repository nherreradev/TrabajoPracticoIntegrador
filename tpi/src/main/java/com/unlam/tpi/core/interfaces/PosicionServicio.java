package com.unlam.tpi.core.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.RequestCargaDeDinero;
import com.unlam.tpi.core.modelo.RendimientoResponse;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;
import com.unlam.tpi.core.modelo.ValuacionTotalRespuesta;

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

	@Transactional
	public RendimientoActualResponse calcularRendimientoActual(String token);

}
