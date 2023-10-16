package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Seccion;

public interface SeccionRepositorio  extends JpaRepository<Seccion, Long>{
	
	public Seccion findByNombre(String nombre);
	
	public Seccion findByOid(Long oid);

}
