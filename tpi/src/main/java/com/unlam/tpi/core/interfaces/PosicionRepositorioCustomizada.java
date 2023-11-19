package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.Posicion;

public interface PosicionRepositorioCustomizada {

	List<Posicion> getPosicionByUsuarioOid(Long oidUsuario);
	
	List<Posicion> getPosicionEnEfectivo(Long oidUsuario);

	List<Posicion> obtenerTodosLosMovimientosAsociadosAUnSimbolo(String simboloInstrumento, Long oidUsuario);
	
	Posicion obtenerPosicionPorConceptoYUsuario(String concepto, Long oidUsuario);
	
	List<Posicion> obtenerTodosLosTitulos(Long usuarioOid);
}
