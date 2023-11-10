package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorioCustomizada {

	List<Posicion> getPosicionByUsuarioOid(Long oidUsuario);
	
	List<Posicion> getPosicionEnEfectivo();

	List<Posicion> getTitulosDisponiblesPorSimbolo(String simboloInstrumento);
	
	Posicion obtenerPosicionPorConcepto(String concepto);
}
