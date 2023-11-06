package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorioCustomizada {

	List<Posicion> getPosicionEnEfectivo();

	List<Posicion> obtenerTodosLosMovimientosAsociadosAUnSimbolo(String simboloInstrumento);
	
	Posicion obtenerPosicionPorConcepto(String concepto);
	
	List<Posicion> obtenerTodosLosTitulos();
}
