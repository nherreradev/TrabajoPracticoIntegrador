package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Categoria;

public interface CategoriaRepositorio  extends JpaRepository<Categoria, Long>{

}
