package com.unlam.tpi.delivery.dto;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.Orden;

public class OrdenMapper {

	public static Orden ordenDTOAOrden(OrdenDTO ordenDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Orden orden = modelMapper.map(ordenDTO, Orden.class);
		return orden;
	}
}
