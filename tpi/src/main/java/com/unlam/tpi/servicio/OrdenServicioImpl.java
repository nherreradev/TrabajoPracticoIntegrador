package com.unlam.tpi.servicio;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.OrdenDTO;
import com.unlam.tpi.interfaces.OrdenServicio;
import com.unlam.tpi.interfaces.PosicionServicio;
import com.unlam.tpi.modelo.persistente.Orden;
import com.unlam.tpi.modelo.pojo.PuedeOperarResultado;
import com.unlam.tpi.repositorio.OrdenRepositorio;

@Service
public class OrdenServicioImpl implements OrdenServicio {

	@Autowired
	OrdenRepositorio ordenRepositorio;

	@Autowired
	PosicionServicio posicionServicio;

	@Override
	public void capturarOrden(OrdenDTO ordenDTO) {
		try {
			crearOrden(ordenDTO);
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException("Se genero un error al capturar la orden");
		}
	}

	public Orden crearOrden(OrdenDTO ordenDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Orden orden = modelMapper.map(ordenDTO, Orden.class);
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
