package com.unlam.tpi.infraestructura.repositorio;

import java.time.LocalDateTime;
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

@Repository
public class HistoricoRendimientoRepositorioImpl implements HistoricoRendimientoRepositorioCustomizado {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<HistoricoRendimientos> buscarPorSimboloYFecha(String simbolo, LocalDateTime fecha) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<HistoricoRendimientos> criteriaQuery = criteriaBuilder.createQuery(HistoricoRendimientos.class);
		Root<HistoricoRendimientos> root = criteriaQuery.from(HistoricoRendimientos.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate simboloPredicado = criteriaBuilder.equal(root.get("simbolo"), simbolo);
		Predicate fechaPredicado = criteriaBuilder.equal(root.get("fecha"), fecha);
		criteriaQuery.where(deletedPredicado, simboloPredicado, fechaPredicado);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<HistoricoRendimientos> obtenerRendimientosHistoricosPorSimbolo(String simboloInstrumento,
			Long usuarioOid) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<HistoricoRendimientos> criteriaQuery = criteriaBuilder.createQuery(HistoricoRendimientos.class);
		Root<HistoricoRendimientos> root = criteriaQuery.from(HistoricoRendimientos.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate simboloPredicado = criteriaBuilder.equal(root.get("simbolo"), simboloInstrumento);
		Predicate usuarioPredicado = criteriaBuilder.equal(root.get("usuarioOid"), usuarioOid);
		criteriaQuery.where(deletedPredicado, simboloPredicado, usuarioPredicado);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
