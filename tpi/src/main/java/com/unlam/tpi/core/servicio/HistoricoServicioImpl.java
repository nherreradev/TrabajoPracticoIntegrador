package com.unlam.tpi.core.servicio;

import java.net.URI;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.delivery.dto.InstrumentoMapper;
import com.unlam.tpi.infraestructura.repositorio.HistoricoRepositorio;

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

	// 1 será un mes,
	// 2 serán 3 meses
	// 3 serán 6 meses
	@Override
	public String GetHistoricoMongo(String rango, String instrumento) {
		List<String> response = null;
		Integer index = null;
		switch (rango) {
			case "mensual":
				response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("mensual", instrumento);
				return response.toString();
			case "trimestral":
				response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("trimestral", instrumento);
				return response.toString();
			case "semestral":
				response = this.historicoRepositorio.getInstrumentoPorRangoFechaSinId("semestral", instrumento);
				return response.toString();
		}
		return null;
	}

	private Integer DeterminarIndexRandomDelArray(List<String> response) {
		return null;
	}

	@Override
	public void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento) {
		String rango = DeterminarRangoDeFecha(fechaRequestHistorico);
		if (rango == null) {
			System.out.println("Rango de fecha no válido");
		}
		String historico = ConsultarHistoricoIOL(fechaRequestHistorico, instrumento, rango);
		EliminarLlavesYGuardarTransaccion(rango, instrumento, historico);
	}

	private void EliminarLlavesYGuardarTransaccion(String rango, String instrumento, String historico) {
		int IndexCorcheteAbertura = historico.toString().indexOf('{');
		int IndexCorcheteCierre = historico.toString().lastIndexOf('}');
		if (IndexCorcheteAbertura != -1 && IndexCorcheteCierre != -1 && IndexCorcheteAbertura < IndexCorcheteCierre) {
			String jsonToSave = historico.toString().substring(IndexCorcheteAbertura, IndexCorcheteCierre + 1);
			this.historicoRepositorio.GuardarHistoricoInstrumento(rango, instrumento, jsonToSave);
		}
	}

	private String ConsultarHistoricoIOL(FechaRequestHistorico fechaRequestHistorico, String instrumento,
										 String rango) {
		List<String> simbolos = ObtenerSimbolosDeInstrumentos(instrumento);
		String mercado = ObtenerMercado(instrumento);
		ResponseEntity<String> res = null;
		for (String simbolo : simbolos) {
			String url = ArmarURL(fechaRequestHistorico, mercado, simbolo);
			res = RealizarPeticionIOL(url);
			return res.getBody().toString();
		}
		return null;
	}

	private ResponseEntity<String> RealizarPeticionIOL(String url) {
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiODM4MmM5NDYtOTdhMS00OGY1LWFiNWItMGMzNjczMDZhOTlhIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE3MDAyNjAyNDMsImV4cCI6MTcwMDI2MTE0MywiaWF0IjoxNzAwMjYwMjQzLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.YPVPJiKXKKRjRcVFtQ4a6CXuKGbXi5HAjfJve3erhMz8a26nEUq9JeGOzgKSrsQDVZmTYOSXoOgkzRtmEIlIgxi0vzFzP5AJ8ndY5KKVA3igoXTH_QXebcG1MIMLqKcYqkK1nCaplPPFjoflizvNP9QP06bxQHJl6UyCVqyCZwrAuD1-evPcdKlko6-EUSeDTrE4PtxBAlPnU7Svvl22DLM_cvuLGxu1yU35JvaueqgaMF9O8NVE3RrOGRGI0jrZwLwWE7_ZYMkdl8rU-qIEWmB7S1cMjZGbZYX6mC1m2XtWVgj9KMzTk231M_SqexZvhDaSxWdkGzJVroiMp1md8Q";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		RequestEntity<?> requestEntity;
		ResponseEntity<String> responseEntity = null;
		requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
		responseEntity = restTemplate.exchange(requestEntity, String.class);
		return responseEntity;
	}

	private String ArmarURL(FechaRequestHistorico fechaRequestHistorico, String mercado, String simbolo) {
		return "https://api.invertironline.com/api/v2/" + mercado + "/Titulos/" + simbolo
				+ "/Cotizacion/seriehistorica/" + fechaRequestHistorico.getFecha_desde() + "/"
				+ fechaRequestHistorico.getMeses_atras() + "/ajustada";
	}

	private String ObtenerMercado(String instrumento) {
		return "bCBA";
	}

	private List<String> ObtenerSimbolosDeInstrumentos(String tipoInstrumento) {
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

	private String DeterminarRangoDeFecha(FechaRequestHistorico fechaRequestHistorico) {
		Period period = Period.between(fechaRequestHistorico.getFecha_desde(), fechaRequestHistorico.getMeses_atras());
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