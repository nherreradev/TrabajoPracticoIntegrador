package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.model.Contenido;
import com.unlam.tpi.model.Nivel;

@Repository
@Transactional
public class NivelRepositorioImpl implements NivelRepositorio {

	@PersistenceContext
	EntityManager entityManager;
 
	@Override
	public void guardar(Nivel entity) {
		entityManager.persist(entity);
	}

	@Override
	public Nivel obtener(Long oid) {
		return entityManager.find(Nivel.class, oid);
	}

	@Override
	public Nivel modificar(Nivel entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void borrar(Long id) {
		Nivel entity = obtener(id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	@Override
	public List<Nivel> listar() {
	     return entityManager.createQuery("SELECT e FROM " + Nivel.class.getName() + " e", Nivel.class).getResultList();
	}
	
}