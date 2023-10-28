package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Categoria;

public interface CategoriaRepositorio  extends JpaRepository<Categoria, Long>{
	
	public Categoria findByNombre(String nombre);

	public Categoria findByOid(Long oid);
}
