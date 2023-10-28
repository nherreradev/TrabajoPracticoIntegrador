package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.PerfilInversor;

public interface PerfilInversorRepositorio extends JpaRepository<PerfilInversor, Long>{

	public PerfilInversor findByUsuario_NombreUsuario(String nombreUsuario);
}
