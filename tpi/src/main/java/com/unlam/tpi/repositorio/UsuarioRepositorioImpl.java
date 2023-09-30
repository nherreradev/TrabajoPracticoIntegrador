package com.unlam.tpi.repositorio;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unlam.tpi.modelo.persistente.Usuario;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio {

	
	@Autowired
	private EntityManager entityManager;

	@Override
	public void guardarUsuario(String nombreUsuario) {
		try {
			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(nombreUsuario);
			entityManager.persist(usuario);
		} catch (Exception e) {
			throw e;
		}
	}
}
