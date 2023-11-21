package com.unlam.tpi.servicioTest;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.unlam.tpi.infraestructura.api.ListaPreciosAPIImpl;

@ExtendWith(MockitoExtension.class)
public class ListaPreciosServiceTest {

	@InjectMocks
	private ListaPreciosAPIImpl listaPreciosAPI;

	@Test
	public void ValidoQueElRequestParaObtenerAccionesMeDevuelvaStatusOK() {
		Map<String, Boolean> expect = new HashMap<>();
		expect.put("acciones", true);
		ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);
		Map<String, Boolean> ResponseOk = listaPreciosAPI.validateResponse(mockResponse, "acciones");
		assertEquals(true, ResponseOk.get("acciones"));
	}

	@Test
	public void ValidoQueElRequestParaObtenerBonosMeDevuelvaStatusOK() {
		Map<String, Boolean> expect = new HashMap<>();
		expect.put("bonos", true);
		ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);
		Map<String, Boolean> ResponseOk = listaPreciosAPI.validateResponse(mockResponse, "bonos");
		assertEquals(true, ResponseOk.get("bonos"));
	}

}
