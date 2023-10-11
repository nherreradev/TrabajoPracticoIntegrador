package com.unlam.tpi.repositorio;

import java.util.List;

import com.unlam.tpi.modelo.persistente.Posicion;

public interface PosicionRepositorioCustomizada {

	List<Posicion> getPosicionEnEfectivo();

	List<Posicion> getTitulosDisponiblesPorSimbolo(String simboloInstrumento);
}
