package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Seccion;

public interface SeccionRepositorio  extends JpaRepository<Seccion, Long>{
	
	public Seccion findByNombre(String nombre);
	
	public Seccion findByOid(Long oid);

}
