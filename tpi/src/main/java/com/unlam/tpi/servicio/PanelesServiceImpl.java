package com.unlam.tpi.servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.constantes.PanelesDePreciosConstantes;
import com.unlam.tpi.mocks.Mock;
import com.unlam.tpi.modelo.persistente.Instrumento;

@Service
public class PanelesServiceImpl implements PanelesService {

	@Autowired
	PanelPrecios panelPrecios;
	
	@Autowired
    private ListaPreciosServicio listaPreciosServicio;
	

	List<Instrumento> listaInstrumentosAux = new ArrayList<>();

	private final RestTemplate restTemplate;

	public PanelesServiceImpl() {
		this.restTemplate = new RestTemplate();
	}

	public PanelesServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public PanelesServiceImpl(RestTemplate restTemplate, PanelPrecios panelPrecios) {
		this.restTemplate = restTemplate;
		this.panelPrecios = panelPrecios;
	}

	@Override
	public Map<String, Instrumento> getPanelDeAcciones() {
		ResponseEntity<String> respuestaJson = postApiAcciones();
		
		try {
			Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
			List<Instrumento> listaInstrumentos = convertirListaDeJsonAListaDeIntrumentos(respuestaJson);
			for (Instrumento instrumento : listaInstrumentos) {
				instrumento.setCategoriaInstrumento(PanelesDePreciosConstantes.ACCIONES);
			}
			
			determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos);
			listaInstrumentosAux.addAll(listaInstrumentos);
			panelPrecios.agregarInstrumentosAlPanelDeAcciones(listaInstrumentos);
			return PanelPreciosImpl.panelAcciones;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Instrumento> getPanelDeBonos() {
		ResponseEntity<String> respuestaJson = postApiBonos();
		try {
			Map<String, Instrumento> mapaInstrumentosAux = new HashMap<>();
			List<Instrumento> listaInstrumentos = convertirListaDeJsonAListaDeIntrumentos(respuestaJson);

			for (Instrumento instrumento : listaInstrumentos) {
				instrumento.setCategoriaInstrumento(PanelesDePreciosConstantes.BONOS);
			}
			
			determinarFlashDeCompraVenta(mapaInstrumentosAux, listaInstrumentos);
			panelPrecios.agregarInstrumentosAlPanelDeBonos(listaInstrumentos);
			return PanelPreciosImpl.panelBonos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Instrumento> convertirListaDeJsonAListaDeIntrumentos(ResponseEntity<String> responseEntity) {
		List<Instrumento> listaInstrumentos = new ArrayList<>();
		Gson gson = new Gson();
		String json = responseEntity.getBody();
		//String json = Mock.jsonMock;
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		JsonArray titulos = jsonObject.getAsJsonArray(PanelesDePreciosConstantes.TITULOS);

		for (int i = 0; i < titulos.size(); i++) {
			JsonObject jsonInstrumento = titulos.get(i).getAsJsonObject();
			Instrumento instrumento = gson.fromJson(jsonInstrumento, Instrumento.class);
			instrumento.setCategoriaInstrumento(PanelesDePreciosConstantes.ACCIONES);
			listaInstrumentos.add(instrumento);
		}
		return listaInstrumentos;
	}

	@Override
	public ResponseEntity<String> postApiAcciones() {
		String url = "https://api.mercadojunior.com.ar/list/precios/acciones";
		return getInstrumentos(url);
	}

	private ResponseEntity<String> postApiBonos() {
		String url = "https://api.mercadojunior.com.ar/list/precios/bonos";
		return getInstrumentos(url);
	}

	public ResponseEntity<String> getInstrumentos(String url) {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		return responseEntity;
	}

	private void determinarFlashDeCompraVenta(Map<String, Instrumento> mapaInstrumentosAux,
			List<Instrumento> listaInstrumentos) {
		if (listaInstrumentosAux != null && !listaInstrumentosAux.isEmpty()) {
			for (Instrumento instrumento : listaInstrumentosAux) {
				mapaInstrumentosAux.put(instrumento.getSimbolo(), instrumento);
			}

			for (Instrumento instrumento : listaInstrumentos) {
				if (instrumento.getPuntas() != null) {
					String simbolo = instrumento.getSimbolo();
					BigDecimal precioCompraNuevo = instrumento.getPuntas().getPrecioCompra();
					BigDecimal precioVentaNuevo = instrumento.getPuntas().getPrecioVenta();

					Instrumento instrumentoAux = mapaInstrumentosAux.get(simbolo);

					if (instrumentoAux != null) {
						if (instrumentoAux.getPuntas() != null) {
							BigDecimal precioCompraViejo = instrumentoAux.getPuntas().getPrecioCompra();
							int comparacion = precioCompraNuevo.compareTo(precioCompraViejo);

							// Establecer el valor de flash en función de la comparación
							if (comparacion > 0) {
								// El precio subió
								instrumento.setFlashCompra(1);
							} else if (comparacion < 0) {
								// El precio bajó
								instrumento.setFlashCompra(-1);
							} else {
								// El precio se mantuvo igual
								instrumento.setFlashCompra(0);
							}
						}
					}

					if (instrumentoAux != null) {
						if (instrumentoAux.getPuntas() != null) {
							BigDecimal precioVentaViejo = instrumentoAux.getPuntas().getPrecioVenta();
							int comparacion = precioVentaNuevo.compareTo(precioVentaViejo);

							if (comparacion > 0) {
								// El precio subió
								instrumento.setFlashVenta(1);
							} else if (comparacion < 0) {
								// El precio bajó
								instrumento.setFlashVenta(-1);
							} else {
								// El precio se mantuvo igual
								instrumento.setFlashVenta(0);
							}
						}
					}
				}
			}
		}
	}
}
