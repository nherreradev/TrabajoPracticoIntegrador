package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Categoria;

public interface CategoriaRepositorio  extends JpaRepository<Categoria, Long>{
	
	public Categoria findByNombre(String nombre);

	public Categoria findByOid(Long oid);
}