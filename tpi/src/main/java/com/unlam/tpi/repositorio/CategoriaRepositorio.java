package com.unlam.tpi.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.model.Categoria;

public interface CategoriaRepositorio {
	
    public void guardar(Categoria categoria);

    public Categoria obtener(Long oid);

    public Categoria modificar(Categoria categoria);

    public void borrar(Long id);

	public List<Categoria> listar();
	
}
