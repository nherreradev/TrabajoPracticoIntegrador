package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;

public class UsuarioMapper {

	private static ModelMapper mapper = new ModelMapper();

	public static Usuario dTOaEntidad(UsuarioDTO usuario) {
		return mapper.map(usuario, Usuario.class);
	}

	public static UsuarioDTO entidadADTO(Usuario usuario) {
		return mapper.map(usuario, UsuarioDTO.class);
	}

	public static List<CategoriaDTO> entidadDTOLista(List<UsuarioDTO> usuarios) {
		return usuarios.stream().map(usuario -> mapper.map(usuario, CategoriaDTO.class)).collect(Collectors.toList());
	}

	public static Usuario UsuarioRest2UsuarioModel(UsuarioRest usuarioRestDTO) {
		return mapper.map(usuarioRestDTO, Usuario.class);
	}
	
	public static String ObtenerBodyToken(String token) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(token);
			return jsonNode.get("token").asText();
		} catch (Exception e) {
			throw new ServiceException("Error al convertir a json");
		}
		
	}
}