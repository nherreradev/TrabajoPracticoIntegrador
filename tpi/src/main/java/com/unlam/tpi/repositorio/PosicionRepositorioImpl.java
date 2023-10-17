package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.unlam.tpi.modelo.persistente.Posicion;

@Repository
public class PosicionRepositorioImpl implements PosicionRepositorioCustomizada {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Posicion> getPosicionEnEfectivo() {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
			Root<Posicion> root = criteriaQuery.from(Posicion.class);

			Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate esEfectivoPredicado = criteriaBuilder.equal(root.get("esEfectivo"), true);

			criteriaQuery.where(deletedPredicado, esEfectivoPredicado);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Posicion> getTitulosDisponiblesPorSimbolo(String simboloInstrumento) {

		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
			Root<Posicion> root = criteriaQuery.from(Posicion.class);

			Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate simboloInstrumentoPredicado = criteriaBuilder.equal(root.get("simboloInstrumento"), simboloInstrumento);

			criteriaQuery.where(deletedPredicado, simboloInstrumentoPredicado);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Posicion obtenerPosicionPorConcepto(String concepto) {

		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
			Root<Posicion> root = criteriaQuery.from(Posicion.class);

			Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate conceptoPredicado = criteriaBuilder.equal(root.get("concepto"), concepto);

			criteriaQuery.where(deletedPredicado, conceptoPredicado);

			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

}
