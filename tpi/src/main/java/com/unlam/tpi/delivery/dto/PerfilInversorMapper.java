package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.ServiceException;

public class PerfilInversorMapper {

	private static ModelMapper mapper = new ModelMapper();
	private static TypeMap<PerfilInversor, PerfilInversorDTO> propertyMapper = mapper
			.createTypeMap(PerfilInversor.class, PerfilInversorDTO.class);

	public static PerfilInversor dTOaEntidad(PerfilInversorDTO perfilInversor) {
		return mapper.map(perfilInversor, PerfilInversor.class);
	}

	public static PerfilInversorDTO entidadADTO(PerfilInversor perfilInversor) {
		propertyMapper.addMappings(mapper -> mapper.map(pi -> pi.getUsuario(), PerfilInversorDTO::setUsuarioDTO));
		return mapper.map(perfilInversor, PerfilInversorDTO.class);
	}

	public static List<PerfilInversorDTO> entidadDTOLista(List<PerfilInversor> perfilInversorLista) {
		return perfilInversorLista.stream().map(perfilInversor -> mapper.map(perfilInversor, PerfilInversorDTO.class))
				.collect(Collectors.toList());
	}

	public static List<PerfilInversor> traductorDeListaDTOaEntidad(List<PerfilInversorDTO> perfilInversorLista)
			throws ServiceException {
		return perfilInversorLista.stream().map(perfilInversor -> mapper.map(perfilInversor, PerfilInversor.class))
				.collect(Collectors.toList());
	}
}