package com.unlam.tpi.core.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.OrdenRepositorio;
import com.unlam.tpi.core.interfaces.OrdenServicio;
import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.modelo.Orden;
import com.unlam.tpi.core.modelo.PuedeOperarResultado;
import com.unlam.tpi.core.modelo.ServiceException;

@Service
public class OrdenServicioImpl implements OrdenServicio {

	@Autowired
	OrdenRepositorio ordenRepositorio;

	@Autowired
	PosicionServicio posicionServicio;

	@Override
	public void capturarOrden(Orden orden) {
		crearOrden(orden);
	}

	public Orden crearOrden(Orden orden) {
		preCreacion(orden);
		return ordenRepositorio.save(orden);
	}

	private void preCreacion(Orden orden) {
		preValidaciones(orden);
	}

	private void preValidaciones(Orden orden) {
		puedeOperar(orden);
	}

	public void puedeOperar(Orden orden) throws ServiceException {
		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);
		if (!puedeOperarResultado.getPuedeOperar()) {
			throw new ServiceException("Puede operar hasta: " + puedeOperarResultado.getDisponible());
		}
	}
}
