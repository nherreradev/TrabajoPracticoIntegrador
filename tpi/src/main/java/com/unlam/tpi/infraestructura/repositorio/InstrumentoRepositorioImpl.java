package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.core.interfaces.InstrumentoRepositorioCustomizado;
import com.unlam.tpi.core.modelo.Instrumento;

@Repository
public class InstrumentoRepositorioImpl implements InstrumentoRepositorioCustomizado {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Instrumento encontrarPorSimbolo(String simbolo) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instrumento> criteriaQuery = criteriaBuilder.createQuery(Instrumento.class);
		Root<Instrumento> root = criteriaQuery.from(Instrumento.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate simboloInstrumentoPredicado = criteriaBuilder.equal(root.get("simbolo"), simbolo);
		criteriaQuery.where(deletedPredicado, simboloInstrumentoPredicado);
		List<Instrumento> resultados = entityManager.createQuery(criteriaQuery).getResultList();
		if (resultados.isEmpty()) {
			return null;
		}
		return resultados.get(0);
	}

	@Override
	public List<Instrumento> obtenerInstrumentosAlAzar() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instrumento> criteriaQuery = criteriaBuilder.createQuery(Instrumento.class);
		Root<Instrumento> root = criteriaQuery.from(Instrumento.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		criteriaQuery.where(deletedPredicado);
		TypedQuery<Instrumento> query = entityManager.createQuery(criteriaQuery);
		query.setMaxResults(4);
		List<Instrumento> resultados = query.getResultList();
		if (resultados.isEmpty()) {
			return null;
		}
		return resultados;
	}

	@Override
	public Instrumento obtenerInstrumentoPorID(Long coProductoID) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instrumento> criteriaQuery = criteriaBuilder.createQuery(Instrumento.class);
		Root<Instrumento> root = criteriaQuery.from(Instrumento.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate oidPredicado = criteriaBuilder.equal(root.get("oid"), coProductoID);
		criteriaQuery.where(deletedPredicado, oidPredicado);
		List<Instrumento> resultados = entityManager.createQuery(criteriaQuery).getResultList();
		if (resultados.isEmpty()) {
			return null;
		}
		return resultados.get(0);
	}

	@Override
	public Instrumento obtenerInstrumentoPorTipoPerfil(String tipoPerfil) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instrumento> criteriaQuery = criteriaBuilder.createQuery(Instrumento.class);
		Root<Instrumento> root = criteriaQuery.from(Instrumento.class);
		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate oidPredicado = criteriaBuilder.equal(root.get("categoriaPerfil"), tipoPerfil);
		criteriaQuery.where(deletedPredicado, oidPredicado);
		List<Instrumento> resultados = entityManager.createQuery(criteriaQuery).getResultList();
		if (resultados.isEmpty()) {
			return null;
		}
		return resultados.get(0);
	}

}
