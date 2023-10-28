package com.unlam.tpi.core.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.infraestructura.modelo.Instrumento;
import com.unlam.tpi.infraestructura.modelo.Puntas;
import com.unlam.tpi.infraestructura.repositorio.PuntasRepositorio;

@Service
public class PuntasServicioImpl implements PuntasServicio {

	@Autowired
	PuntasRepositorio puntasRepositorio;

	@Override
	public void guardarPuntas(Instrumento instrumento) {
		Puntas puntas = instrumento.getPuntas() != null ? instrumento.getPuntas() : null;
		if (puntas != null) {
			//puntas.setInstrumento(instrumento);
			puntasRepositorio.save(puntas);
		}
	}

}