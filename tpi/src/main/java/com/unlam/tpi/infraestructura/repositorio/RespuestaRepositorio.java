package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Respuesta;

public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long>{
	
	public Respuesta findByNombre(String nombre);
	
	public Respuesta findByNombreAndInstrumento(String nombre, String instrumento);
	
	public Respuesta findByOid(Long oid);

}
