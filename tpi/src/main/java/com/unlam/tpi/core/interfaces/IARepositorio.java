package com.unlam.tpi.core.interfaces;

import java.util.List;

import javax.persistence.Tuple;

public interface IARepositorio {

	public List<Tuple> generarTxt(String tipoPerfil);

}
