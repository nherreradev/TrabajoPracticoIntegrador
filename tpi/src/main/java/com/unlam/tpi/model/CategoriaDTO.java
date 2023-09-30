package com.unlam.tpi.model;

import org.json.JSONObject;

public class CategoriaDTO  extends JSONObject{

	private Long oid;
	
	private String nombre;
	
	private String descripcion;

	
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}
