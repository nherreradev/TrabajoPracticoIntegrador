package com.unlam.tpi.servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unlam.tpi.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Override
	public void guardarUsuario(String nombreUsuario) {
		try {
			getUsuarioRepositorio().guardarUsuario(nombreUsuario);
		} catch (Exception e) {
			throw e;
		}
		
	}

	public UsuarioRepositorio getUsuarioRepositorio() {
		return usuarioRepositorio;
	}

	public void setUsuarioRepositorio(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

}
