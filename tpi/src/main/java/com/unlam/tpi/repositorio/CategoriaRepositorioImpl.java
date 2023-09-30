package com.unlam.tpi.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.unlam.tpi.model.Categoria;
import com.unlam.tpi.model.Contenido;

@Repository
@Transactional
public class CategoriaRepositorioImpl implements CategoriaRepositorio{

	@PersistenceContext
	EntityManager entityManager;
 
	@Override
	public void guardar(Categoria entity) {
		entityManager.persist(entity);
	}

	@Override
	public Categoria obtener(Long oid) {
		return entityManager.find(Categoria.class, oid);
	}

	@Override
	public Categoria modificar(Categoria entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void borrar(Long id) {
		Categoria entity = obtener(id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public List<Categoria> listar() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Categoria> query = cb.createQuery(Categoria.class);
		Root<Categoria> categoria = query.from(Categoria.class);
		query.select(categoria);
		return entityManager.createQuery(query).getResultList();
//	    entityManager.createQuery("SELECT e FROM " + Categoria.class.getName() + " e", Categoria.class).getResultList();
	}

	
}