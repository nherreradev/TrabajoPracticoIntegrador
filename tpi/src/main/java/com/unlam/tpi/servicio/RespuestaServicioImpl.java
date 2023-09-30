package com.unlam.tpi.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.model.Respuesta;
import com.unlam.tpi.repositorio.RespuestaRepositorio;

@Service
@Transactional
public class RespuestaServicioImpl implements RespuestaServicio {

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	@Override
	public void guardar(Respuesta respuesta) {
		getRespuestaRepositorio().guardar(respuesta);
	}

	@Override
	public Respuesta obtener(Long id) {
		return getRespuestaRepositorio().obtener(id);
	}

	@Override
	public Respuesta modificar(Respuesta respuesta) {
		return getRespuestaRepositorio().modificar(respuesta);
	}

	@Override
	public void borrar(Long id) {
		getRespuestaRepositorio().borrar(id);		
	}

	@Override
	public List<Respuesta> listar() {
		return getRespuestaRepositorio().listar();
	}
	
	public RespuestaRepositorio getRespuestaRepositorio() {
		return respuestaRepositorio;
	}

	public void setRespuestaRepositorio(RespuestaRepositorio respuestaRepositorio) {
		this.respuestaRepositorio = respuestaRepositorio;
	}
}
