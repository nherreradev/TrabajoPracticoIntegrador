package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.model.Pregunta;

@Repository
@Transactional
public class PreguntaRepositorioImpl implements PreguntaRepositorio {

	@PersistenceContext
	EntityManager entityManager;
 
	@Override
	public void guardar(Pregunta entity) {
		entityManager.persist(entity);
	}

	@Override
	public Pregunta obtener(Long oid) {
		return entityManager.find(Pregunta.class, oid);
	}

	@Override
	public Pregunta modificar(Pregunta entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void borrar(Long id) {
		Pregunta entity = obtener(id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public List<Pregunta> listar() {
	     return entityManager.createQuery("SELECT e FROM " + Pregunta.class.getName() + " e", Pregunta.class).getResultList();
	}
	
}