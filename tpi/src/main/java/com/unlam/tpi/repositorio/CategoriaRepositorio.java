package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Categoria;

public interface CategoriaRepositorio  extends JpaRepository<Categoria, Long>{
	
	public Categoria findByNombre(String nombre);

	public Categoria findByOid(Long oid);
}
