package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.model.Categoria;
import com.unlam.tpi.model.Contenido;

@Repository
@Transactional
public class ContenidoRepositorioImpl implements ContenidoRepositorio {

	@PersistenceContext
	EntityManager entityManager;
 
	@Override
	public void guardar(Contenido entity) {
		entityManager.persist(entity);
	}

	@Override
	public Contenido obtener(Long oid) {
		return entityManager.find(Contenido.class, oid);
	}

	@Override
	public Contenido modificar(Contenido entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void borrar(Long id) {
		Contenido entity = obtener(id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public List<Contenido> listar() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contenido> query = cb.createQuery(Contenido.class);
		Root<Contenido> Contenido = query.from(Contenido.class);
		query.select(Contenido);
		return entityManager.createQuery(query).getResultList();
//	     return entityManager.createQuery("SELECT e FROM " + Contenido.class.getName() + " e", Contenido.class).getResultList();
	}

}
