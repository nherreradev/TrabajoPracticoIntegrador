package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.InstrumentoRepositorio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.servicio.InstrumentoServicioImpl;


@ExtendWith(MockitoExtension.class)
class InstrumentoServicioTest {
	
	@InjectMocks
	InstrumentoServicioImpl instrumentoServicioImpl;
	
	@Mock
	InstrumentoRepositorio instrumentoRepositorio;

	@Test
	void testPuedoActualizarLaCategoriaDeUnInstrumento() {
		
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		Instrumento instrumento = new Instrumento();
		instrumento.setVariacionPorcentual(new BigDecimal(0.5));
		instrumento.setSimbolo("ALUA");
		instrumento.setCategoriaPerfil("Moderado");
		listaInstrumentos.add(instrumento);
		
		
		when(instrumentoRepositorio.encontrarPorSimbolo(instrumento.getSimbolo())).thenReturn(new Instrumento());
		
		instrumentoServicioImpl.persistirInstrumentos(listaInstrumentos);
		
		verify(instrumentoRepositorio).save(any(Instrumento.class));
		
	}
	
	@Test
	void testPuedoGuardarUnNuevoInstrumento() {
		
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		Instrumento instrumento = new Instrumento();
		instrumento.setVariacionPorcentual(new BigDecimal(0.5));
		instrumento.setSimbolo("ALUA");
		instrumento.setCategoriaPerfil("Conservador");
		listaInstrumentos.add(instrumento);
		
		
		when(instrumentoRepositorio.encontrarPorSimbolo(instrumento.getSimbolo())).thenReturn(null);
		
		instrumentoServicioImpl.persistirInstrumentos(listaInstrumentos);
		
		verify(instrumentoRepositorio).save(any(Instrumento.class));
		
	}

}
