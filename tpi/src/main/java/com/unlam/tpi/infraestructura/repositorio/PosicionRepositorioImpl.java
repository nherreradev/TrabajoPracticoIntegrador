package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.core.interfaces.PosicionRepositorioCustomizada;
import com.unlam.tpi.core.modelo.Posicion;

@Repository
public class PosicionRepositorioImpl implements PosicionRepositorioCustomizada {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Posicion> getPosicionEnEfectivo(Long oidUsuario) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
		Root<Posicion> root = criteriaQuery.from(Posicion.class);

		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate esEfectivoPredicado = criteriaBuilder.equal(root.get("esEfectivo"), true);
		Predicate usuarioPredicado = criteriaBuilder.equal(root.get("usuarioOid"), oidUsuario);

		criteriaQuery.where(deletedPredicado, esEfectivoPredicado, usuarioPredicado);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Posicion> obtenerTodosLosMovimientosAsociadosAUnSimbolo(String simboloInstrumento, Long usuarioOid) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
		Root<Posicion> root = criteriaQuery.from(Posicion.class);

		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate simboloInstrumentoPredicado = criteriaBuilder.equal(root.get("simboloInstrumento"),
				simboloInstrumento);
		Predicate usuarioPredicado = criteriaBuilder.equal(root.get("usuarioOid"), usuarioOid);

		criteriaQuery.where(deletedPredicado, simboloInstrumentoPredicado, usuarioPredicado);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Posicion obtenerPosicionPorConceptoYUsuario(String concepto, Long oidUsuario) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
		Root<Posicion> root = criteriaQuery.from(Posicion.class);

		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate usuarioIdPredicado = criteriaBuilder.equal(root.get("usuarioOid"), oidUsuario);
		Predicate conceptoPredicado = criteriaBuilder.equal(root.get("concepto"), concepto);

		criteriaQuery.where(deletedPredicado, usuarioIdPredicado, conceptoPredicado);

		List<Posicion> listaPosicion = entityManager.createQuery(criteriaQuery).getResultList();
		if (listaPosicion.size() == 0) {
			return null;
		}
		return listaPosicion.get(0);

	}

	@Override
	public List<Posicion> obtenerTodosLosTitulos(Long usuarioOid) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
		Root<Posicion> root = criteriaQuery.from(Posicion.class);

		Predicate deletedPredicado = criteriaBuilder.equal(root.get("deleted"), false);
		Predicate noEsEfectivoPredicado = criteriaBuilder.equal(root.get("esEfectivo"), false);
		Predicate usuarioPredicado = criteriaBuilder.equal(root.get("usuarioOid"), usuarioOid);

		criteriaQuery.where(deletedPredicado, noEsEfectivoPredicado, usuarioPredicado);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Posicion> getPosicionByUsuarioOid(Long oidUsuario) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Posicion> criteriaQuery = criteriaBuilder.createQuery(Posicion.class);
		Root<Posicion> root = criteriaQuery.from(Posicion.class);

		Predicate filtroUsuario = criteriaBuilder.equal(root.get("usuarioOid"), oidUsuario);

		criteriaQuery.where(filtroUsuario);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
