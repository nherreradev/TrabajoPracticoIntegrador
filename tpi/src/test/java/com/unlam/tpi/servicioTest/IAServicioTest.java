package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PortafolioSugerenciaServicio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.infraestructura.api.RecomendacionPortafolioApiImpl;

@ExtendWith(MockitoExtension.class)
class IAServicioTest {

	@InjectMocks
	RecomendacionPortafolioApiImpl IAServicioImpl;

	@Mock
	InstrumentoServicio instrumentoServicio;
	
	@Mock
	PortafolioSugerenciaServicio portafolioSugerenciaServicio;

	@Test
	void testPuedoObtenerElPortafolioSugerido() throws IOException, KeyManagementException, NoSuchAlgorithmException {

		String json = "[{\"coPurchaseProductID\":73,\"score\":9.279588E-07},{\"coPurchaseProductID\":51,\"score\":9.279586E-07},{\"coPurchaseProductID\":68,\"score\":9.279586E-07},{\"coPurchaseProductID\":41,\"score\":9.2795847E-07},{\"coPurchaseProductID\":63,\"score\":9.2795847E-07}]";

		String tipoPerfil = "Moderado";
		int idProducto = 150;
		Long coProductoID = 73L;
		
		Instrumento instrumento = new Instrumento();

		when(portafolioSugerenciaServicio.obtenerRecomendacion(tipoPerfil, idProducto)).thenReturn(json);
		
		when(instrumentoServicio.obtenerInstrumentoPorID(coProductoID)).thenReturn(instrumento);
		
		List<Instrumento> portafolioSugerido = IAServicioImpl.obtenerPortafolioSugerido(tipoPerfil, idProducto);

		assertNotNull(portafolioSugerido);
		assertTrue(!portafolioSugerido.isEmpty());
		
	}

}
