package com.unlam.tpi.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.core.interfaces.HistoricoRendimientoRepositorioCustomizado;
import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;
import com.unlam.tpi.core.modelo.Posicion;

@Repository
public class HistoricoRendimientoRepositorioImpl implements HistoricoRendimientoRepositorioCustomizado {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<HistoricoRendimientos> buscarPorSimboloYFecha(String simbolo, LocalDate fecha) {

		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<HistoricoRendimientos> criteriaQuery = criteriaBuilder
					.createQuery(HistoricoRendimientos.class);
			Root<HistoricoRendimientos> root = criteriaQuery.from(HistoricoRendimientos.class);

			Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate simboloPredicado = criteriaBuilder.equal(root.get("simbolo"), simbolo);
			Predicate fechaPredicado = criteriaBuilder.equal(root.get("fecha"), fecha);

			criteriaQuery.where(deletedPredicado, simboloPredicado, fechaPredicado);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<HistoricoRendimientos> obtenerRendimientosHistoricosPorSimbolo(String token,
			String simboloInstrumento) {

		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<HistoricoRendimientos> criteriaQuery = criteriaBuilder
					.createQuery(HistoricoRendimientos.class);
			Root<HistoricoRendimientos> root = criteriaQuery.from(HistoricoRendimientos.class);

			Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
			Predicate simboloPredicado = criteriaBuilder.equal(root.get("simbolo"), simboloInstrumento);

			criteriaQuery.where(deletedPredicado, simboloPredicado);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			throw e;
		}
	}

}
