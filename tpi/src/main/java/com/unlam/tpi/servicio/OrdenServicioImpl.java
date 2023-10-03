package com.unlam.tpi.servicio;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.ServiceException;
import com.unlam.tpi.constantes.OrdenConstantes;
import com.unlam.tpi.dto.OrdenDTO;
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
	public OrdenDTO capturarOrden(OrdenDTO ordenDTO) {

		crearOrden(ordenDTO);

		return null;
	}

	private void crearOrden(OrdenDTO ordenDTO) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Orden orden = modelMapper.map(ordenDTO, Orden.class);
			preCreacion(orden);
			ordenRepositorio.save(orden);
		} catch (Exception e) {
			throw e;
		}
	}

	private void preCreacion(Orden orden) {
		preValidaciones(orden);

	}

	private void preValidaciones(Orden orden) {
		try {
			puedeOperar(orden);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private void puedeOperar(Orden orden) throws ServiceException {

		PuedeOperarResultado puedeOperarResultado = posicionServicio.puedeOperar(orden);
		if (!puedeOperarResultado.getPuedeOperar()) {
			throw new ServiceException("Puede operar hasta: " + puedeOperarResultado.getMontoDisponible());
		}

	}

}
