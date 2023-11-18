package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.servicio.HistoricoServicioImpl;
import com.unlam.tpi.delivery.controlador.HistoricoControladorImpl;
import com.unlam.tpi.infraestructura.repositorio.HistoricoRepositorio;

@ExtendWith(MockitoExtension.class)
public class HistoricoServicioTest {

	@Mock
	private HistoricoServicioImpl historicoServicio;

	@InjectMocks
	private HistoricoControladorImpl historicoControlador;
	
	@Mock
    private HistoricoRepositorio historicoRepositorio;

    @Mock
    private ListaPreciosServicio listaPreciosServicio;

    @Mock
    private RestTemplate restTemplate;

    String JSON = "{\"titulos\": [{\"simbolo\":\"5913\",\"puntas\":null,\"ultimoPrecio\":0.0,\"variacionPorcentual\":0.0,\"apertura\":0.0,\"maximo\":0.0,\"minimo\":0.0,\"ultimoCierre\":0.0,\"volumen\":0.0,\"cantidadOperaciones\":0.0,\"fecha\":\"2023-09-21T03:00:00.87\",\"tipoOpcion\":null,\"precioEjercicio\":null,\"fechaVencimiento\":null,\"mercado\":\"1\",\"moneda\":\"1\",\"descripcion\":\"OCCIDENTAL PETROLEUM CORP. (OXY)\",\"plazo\":\"T2\",\"laminaMinima\":1,\"lote\":1}]}";
    		
	@Test
	void testGuardarHistorico() {
		FechaRequestHistorico fechaRequestHistorico = new FechaRequestHistorico();
		fechaRequestHistorico.setFechaDesde(LocalDate.now());
		fechaRequestHistorico.setInstrumento("Acciones");

		ResponseEntity<String> responseEntity = historicoControlador.GuardarHistorico(fechaRequestHistorico);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Operación completada", responseEntity.getBody());
		verify(historicoServicio, times(1)).guardarHistorico(eq(fechaRequestHistorico), any());
	}

	@Test
	void testGuardarHistoricoConObjetoNulo() {
		// Act
		ResponseEntity<String> responseEntity = historicoControlador.GuardarHistorico(null);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("El objeto fechaRequestHistorico es nulo", responseEntity.getBody());
		verify(historicoServicio, never()).guardarHistorico(any(), any());
	}

	@Test
	void testGetHistorico() {
		String rango = "mensual";
		String instrumento = "acciones";

		ResponseEntity<String> responseEntity = historicoControlador.GetHistorico(rango, instrumento);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Operación completada", responseEntity.getBody());
		verify(historicoServicio, times(1)).getHistoricoMongo(eq(rango), eq(instrumento));
	}

	@Test
	void testGetHistoricoConParametrosNulos() {
		ResponseEntity<String> responseEntity = historicoControlador.GetHistorico(null, null);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Request incorrecto", responseEntity.getBody());
		verify(historicoServicio, never()).getHistoricoMongo(any(), any());
	}
	
}
