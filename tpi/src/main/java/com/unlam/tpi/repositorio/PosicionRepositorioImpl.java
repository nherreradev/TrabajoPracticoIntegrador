package com.unlam.tpi.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
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

			Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate esEfectivoPredicate = criteriaBuilder.equal(root.get("esEfectivo"), true);

			criteriaQuery.where(deletedPredicate, esEfectivoPredicate);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * private Criteria getCriteria(DetachedCriteria dc, Class<Posicion> class1) {
	 * 
	 * try { Criteria c = dc.getExecutableCriteria(obtenerSession()); return c; }
	 * catch (Exception e) { throw e; }
	 * 
	 * }
	 * 
	 * private Session obtenerSession() { return sessionFactory.getCurrentSession();
	 * }
	 */

}
