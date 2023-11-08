package com.unlam.tpi.core.servicio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoDeGananciaPerdidaDiariaRepositorio;
import com.unlam.tpi.core.interfaces.HistoricoDeGananciaPerdidaDiariaServicio;
import com.unlam.tpi.core.modelo.HistoricoDeGananciaPerdidaDiaria;

@Service
@Transactional
public class HistoricoDeGananciaPerdidaDiariaServicioImpl implements HistoricoDeGananciaPerdidaDiariaServicio {

	@Autowired
	HistoricoDeGananciaPerdidaDiariaRepositorio historicoDeGananciaPerdidaDiariaRepositorio;

	@Override
	public void guardar(HistoricoDeGananciaPerdidaDiaria historicoDeGananciaPerdidaDiaria) {
		historicoDeGananciaPerdidaDiariaRepositorio.save(historicoDeGananciaPerdidaDiaria);

	}

}
