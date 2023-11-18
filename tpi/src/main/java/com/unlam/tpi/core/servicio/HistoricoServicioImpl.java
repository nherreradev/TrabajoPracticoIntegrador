package com.unlam.tpi.core.servicio;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.delivery.dto.HistoricoInstrumentoDTO;
import com.unlam.tpi.delivery.dto.InstrumentoMapper;
import com.unlam.tpi.infraestructura.repositorio.HistoricoRepositorio;

import net.sf.jasperreports.engine.util.ConcurrentMapping.Mapper;

@Service
public class HistoricoServicioImpl implements HistoricoServicio {
	@Autowired
	HistoricoRepositorio historicoRepositorio;
	@Autowired
	ListaPreciosServicio listaPreciosServicio;
	@Autowired
	private final RestTemplate restTemplate;

	public HistoricoServicioImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String getHistoricoMongo(String rango, String instrumento, String simbolo) {
		List<String> response = null;

		switch (rango) {
		case "mensual":
			response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("mensual", instrumento, simbolo);
			return response.toString();
		case "trimestral":
			response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("trimestral", instrumento, simbolo);
			return response.toString();
		case "semestral":
			response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("semestral", instrumento, simbolo);
			return response.toString();
		default:
			throw new IllegalArgumentException("Rango de fecha no válido: " + rango);
		}
	}

	@Override
	public void guardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento) {

		String rango = determinarRangoDeFecha(fechaRequestHistorico);

		List<String> simbolos = obtenerSimbolosDeInstrumentos(instrumento);

		for (String simbolo : simbolos) {
			String historico = consultarHistoricoIOL(simbolo, fechaRequestHistorico, instrumento, rango);
			guardarTransaccion(rango, instrumento, historico, simbolo);
		}

		/*
		 * String rango = determinarRangoDeFecha(fechaRequestHistorico); if (rango ==
		 * null) { System.out.println("Rango de fecha no válido"); } String historico =
		 * consultarHistoricoIOL(fechaRequestHistorico, instrumento, rango);
		 * guardarTransaccion(rango, instrumento, historico, simbolo);
		 */
	}

	private void guardarTransaccion(String rango, String instrumento, String historico, String simbolo) {

		List<HistoricoInstrumentoDTO> listaHistoricoDTO = new ArrayList<>();

		JsonArray jsonArray = JsonParser.parseString(historico).getAsJsonArray();
		for (JsonElement elemento : jsonArray) {
			HistoricoInstrumentoDTO historicoInstrumentoDTO = new HistoricoInstrumentoDTO();
			historicoInstrumentoDTO.setSimbolo(simbolo);
			JsonPrimitive apertura = elemento.getAsJsonObject().getAsJsonPrimitive("apertura");
			JsonPrimitive cierreAnterior = elemento.getAsJsonObject().getAsJsonPrimitive("cierreAnterior");
			JsonPrimitive maximo = elemento.getAsJsonObject().getAsJsonPrimitive("maximo");
			JsonPrimitive minimo = elemento.getAsJsonObject().getAsJsonPrimitive("minimo");
			JsonPrimitive fechahora = elemento.getAsJsonObject().getAsJsonPrimitive("fechaHora");

			boolean contiene17 = fechahora.toString().contains("T17");

			if (contiene17) {
				completarDTO(historicoInstrumentoDTO, apertura, cierreAnterior, maximo, minimo, fechahora);
				listaHistoricoDTO.add(historicoInstrumentoDTO);
			}

		}

		this.historicoRepositorio.guardarHistoricoInstrumento(rango, instrumento, listaHistoricoDTO);

	}

	private void completarDTO(HistoricoInstrumentoDTO historicoInstrumentoDTO, JsonPrimitive apertura,
			JsonPrimitive cierreAnterior, JsonPrimitive maximo, JsonPrimitive minimo, JsonPrimitive fechaHora) {
		if (chequearJson(apertura)) {
			historicoInstrumentoDTO.setApertura(apertura.getAsBigDecimal());

		}
		if (chequearJson(cierreAnterior)) {
			historicoInstrumentoDTO.setCierre(cierreAnterior.getAsBigDecimal());

		}
		if (chequearJson(maximo)) {
			historicoInstrumentoDTO.setMaximo(maximo.getAsBigDecimal());

		}
		if (chequearJson(minimo)) {
			historicoInstrumentoDTO.setMinimo(minimo.getAsBigDecimal());

		}

		if (fechaHora != null && fechaHora.isString()) {
			historicoInstrumentoDTO.setFechaHora(fechaHora.getAsString());
		}

	}

	private boolean chequearJson(JsonPrimitive apertura) {
		return apertura != null && apertura.isNumber();
	}

	private String consultarHistoricoIOL(String simbolo, FechaRequestHistorico fechaRequestHistorico,
			String instrumento, String rango) {

		String mercado = ObtenerMercado(instrumento);
		String url = armarURL(fechaRequestHistorico, mercado, simbolo);
		return realizarPeticionIOL(url).getBody().toString();

	}

	private ResponseEntity<String> realizarPeticionIOL(String url) {
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxMjQ3ODkzIiwiSUQiOiIxMjQ3ODkzIiwianRpIjoiZGJkMDE1YTctY2I4OS00MjNhLWJmMTAtODI1NTVjMjlhMWI1IiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE3MDAzMjk5OTYsImV4cCI6MTcwMDMzMDg5NiwiaWF0IjoxNzAwMzI5OTk2LCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.bokM7KdlI-g4KyD-W4b9vMFiyo4OjrqoHlfDlqLVBJIMOahveAeaTQd5VtVta-zWZd-Pjq9culUnO6YTuaaGJmjG60D2b9B5kA2O7EY8BAO_Qd3TaoUbq4Qo4Qol3FuxsBkWFhZCZqKNiwkWW7QmiN7j4etiC8ThmXhoi7kU2398cgKqqG_soWlYG7N8bE49GrZDT_ludwOXrsppTnScidHxWGh6NNK8d-K5aVgvila84kK7QZwIMrfLXfmxBjBHsWACgw477FEmh6mILpk_VC7MLW0UI34sCTwymOLnM0NY1cS2Jf07iNC0mMhYNhcG9AK-FTXINh8RBZVV6-fC1g";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		RequestEntity<?> requestEntity;
		ResponseEntity<String> responseEntity = null;
		requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
		responseEntity = restTemplate.exchange(requestEntity, String.class);
		return responseEntity;
	}

	private String armarURL(FechaRequestHistorico fechaRequestHistorico, String mercado, String simbolo) {
		return "https://api.invertironline.com/api/v2/" + mercado + "/Titulos/" + simbolo
				+ "/Cotizacion/seriehistorica/" + fechaRequestHistorico.getFechaDesde() + "/"
				+ fechaRequestHistorico.getMesesAtras() + "/sinajustar";
	}

	private String ObtenerMercado(String instrumento) {
		return "bCBA";
	}

	private List<String> obtenerSimbolosDeInstrumentos(String tipoInstrumento) {
		List<String> ArraySimbolos = new ArrayList<>();
		String listaPreciosJson = listaPreciosServicio.getListaPrecioMongo(tipoInstrumento);
		List<Instrumento> listaInstrumentos = InstrumentoMapper
				.convertirListaDeJsonAListaDeIntrumentos(listaPreciosJson);
		for (Instrumento instrumento : listaInstrumentos) {
			if (instrumento.getPuntas() != null) {
				ArraySimbolos.add(instrumento.getSimbolo());
			}
		}
		return ArraySimbolos;
	}

	private String determinarRangoDeFecha(FechaRequestHistorico fechaRequestHistorico) {
		Period period = Period.between(fechaRequestHistorico.getFechaDesde(), fechaRequestHistorico.getMesesAtras());
		int mesesDiferencia = Math.abs(period.getMonths());
		switch (mesesDiferencia) {
		case 1:
			return "mensual";
		case 3:
			return "trimetral";
		case 6:
			return "semestral";
		default:
			return null;
		}
	}
}