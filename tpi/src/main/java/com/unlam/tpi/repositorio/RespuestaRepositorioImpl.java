package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.Respuesta;

@Repository
@Transactional
public class RespuestaRepositorioImpl implements RespuestaRepositorio {

	@PersistenceContext
	EntityManager entityManager;
 
	@Override
	public void guardar(Respuesta entity) {
		entityManager.persist(entity);
	}

	@Override
	public Respuesta obtener(Long oid) {
		return entityManager.find(Respuesta.class, oid);
	}

	@Override
	public Respuesta modificar(Respuesta entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void borrar(Long id) {
		Respuesta entity = obtener(id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public List<Respuesta> listar() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Respuesta> query = cb.createQuery(Respuesta.class);
		Root<Respuesta> Contenido = query.from(Respuesta.class);
		query.select(Contenido);
		return entityManager.createQuery(query).getResultList();
//	    return entityManager.createQuery("SELECT e FROM " + Respuesta.class.getName() + " e", Respuesta.class).getResultList();
	}


}