package com.unlam.tpi.infraestructura.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.unlam.tpi.core.interfaces.IARepositorio;
import com.unlam.tpi.core.modelo.Instrumento;

@Repository
public class IARepositorioImpl implements IARepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tuple> generarTxt(String tipoPerfil) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Instrumento> ins1 = criteriaQuery.from(Instrumento.class);
		Root<Instrumento> ins2 = criteriaQuery.from(Instrumento.class);

		// Condiciones para unir las tablas y filtrar
		Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(ins1.get("categoriaPerfil"), tipoPerfil),
				criteriaBuilder.equal(ins2.get("categoriaPerfil"), tipoPerfil),
				criteriaBuilder.notEqual(ins2.get("oid"), ins1.get("oid")));

		criteriaQuery.multiselect(ins2.get("oid").alias("producto"), ins1.get("oid").alias("co_producto"));
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();

	}

}
