package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.PerfilInversor;

public interface PerfilInversorRepositorio extends JpaRepository<PerfilInversor, Long>{

	public PerfilInversor findByUsuario_NombreUsuario(String nombreUsuario);
	public PerfilInversor findByUsuario_Oid(Long id);
}
