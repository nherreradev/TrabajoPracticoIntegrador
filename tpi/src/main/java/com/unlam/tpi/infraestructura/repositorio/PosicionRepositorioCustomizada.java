package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorioCustomizada {

	List<Posicion> getPosicionEnEfectivo();

	List<Posicion> getTitulosDisponiblesPorSimbolo(String simboloInstrumento);
	
	Posicion obtenerPosicionPorConcepto(String concepto);
}
