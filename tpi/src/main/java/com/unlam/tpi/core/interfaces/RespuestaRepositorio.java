package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Respuesta;

public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long>{
	
	public Respuesta findByCodigo(String codigo);
	
	public Respuesta findByNombreAndInstrumento(String nombre, String instrumento);
	
	public Respuesta findByCodigoAndInstrumento(String codigo, String instrumento);
	
	public Respuesta findByOid(Long oid);

	public void deleteByCodigo(String codigo);
	
	public Respuesta findByNombre(String nombre);
}
