package com.unlam.tpi.repositorio;

import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.unlam.tpi.model.Usuario;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio {

	private static final Logger logger = LogManager.getLogger(UsuarioRepositorioImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public void guardarUsuario(String nombreUsuario) {
		try {
			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(nombreUsuario);
			entityManager.persist(usuario);
		} catch (Exception e) {
			logger.error("Error persistiendo usuario " + e);
		}
	}

}
