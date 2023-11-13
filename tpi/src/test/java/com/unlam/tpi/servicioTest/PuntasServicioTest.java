package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.PuntasRepositorio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.Posicion;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.servicio.PuntasServicioImpl;

@ExtendWith(MockitoExtension.class)
class PuntasServicioTest {

	@InjectMocks
	PuntasServicioImpl PuntasServicio;
	
	@Mock
	PuntasRepositorio puntasRepositorio;
	
	@Test
	void testPuedoGuardarPuntas() {
		
		Instrumento instrumento =  new Instrumento();
		Puntas puntas = new Puntas();
		puntas.setPrecioCompra(new BigDecimal(100));
		puntas.setPrecioVenta(new BigDecimal(90));
		instrumento.setPuntas(puntas);
		
		PuntasServicio.guardarPuntas(instrumento);
		
		verify(puntasRepositorio).save(any(Puntas.class));
		
	}

}
