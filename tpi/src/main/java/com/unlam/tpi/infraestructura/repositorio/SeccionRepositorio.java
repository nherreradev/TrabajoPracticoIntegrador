package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Seccion;

public interface SeccionRepositorio  extends JpaRepository<Seccion, Long>{
	
	public Seccion findByNombre(String nombre);
	
	public Seccion findByOid(Long oid);

}
