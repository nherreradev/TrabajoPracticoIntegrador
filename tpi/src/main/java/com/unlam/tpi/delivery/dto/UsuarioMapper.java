package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;

public class UsuarioMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Usuario dTOaEntidad(UsuarioDTO usuario) {
		try {
			return mapper.map(usuario, Usuario.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir UsuarioDTO a usuario", e);
		}
	}

	public static UsuarioDTO entidadADTO(Usuario usuario) {
		try {
			return mapper.map(usuario, UsuarioDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir usuario a UsuarioDTO", e);
		}
	}

	public static List<CategoriaDTO> entidadDTOLista(List<UsuarioDTO> usuarios) {
		try {
			return usuarios.stream().map(usuario -> mapper.map(usuario, CategoriaDTO.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error en convertir una lista usuario a lista UsuarioDTO", e);
		}
	}

	public static Usuario UsuarioRest2UsuarioModel(UsuarioRestDTO usuarioRestDTO) {
		try {
			return mapper.map(usuarioRestDTO, Usuario.class);
		} catch (Exception e) {
			throw new ServiceException("Error en convertir UsuarioDTO a usuario", e);
		}
	}
}
