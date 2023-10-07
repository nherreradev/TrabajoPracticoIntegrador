package com.unlam.tpi.dto;


import org.json.JSONObject;

public class RespuestaDTO extends JSONObject{

	private Long oid;
	
	private String nombre;
	
	private Integer valor;
	
	private Integer orden;

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

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}