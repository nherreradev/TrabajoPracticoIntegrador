package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Respuesta;

public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long>{
	
	public Respuesta findByNombre(String nombre);
	
	public Respuesta findByOid(Long oid);

}
