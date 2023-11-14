package com.unlam.tpi.core.servicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.interfaces.InstrumentoRepositorio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.core.modelo.HistoricoInstrumentoRespuesta;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.ServiceException;

@Service
@Transactional
public class InstrumentoServicioImpl implements InstrumentoServicio {

	@Autowired
	InstrumentoRepositorio instrumentoRepositorio;

	@Autowired
	PuntasServicio puntasServicio;

	@Override
	public List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo) {

		List<HistoricoInstrumentoRespuesta> listaHistoricoInstrumentoRespuesta = new ArrayList<>();

		String json = "[\n" + "\n" + "{\n" + "\"time\": \"2018-10-19\",\n" + "\"open\": 180.34,\n"
				+ "\"high\": 180.99,\n" + "\"low\": 178.57,\n" + "\"close\": 179.85\n" + "},\n" + "\n" + "{\n"
				+ "\"time\": \"2018-10-20\",\n" + "\"open\": 181.00,\n" + "\"high\": 182.50,\n" + "\"low\": 179.75,\n"
				+ "\"close\": 180.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-21\",\n" + "\"open\": 182.25,\n"
				+ "\"high\": 183.75,\n" + "\"low\": 181.50,\n" + "\"close\": 183.00\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-22\",\n" + "\"open\": 183.50,\n" + "\"high\": 184.75,\n" + "\"low\": 182.00,\n"
				+ "\"close\": 184.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-23\",\n" + "\"open\": 184.00,\n"
				+ "\"high\": 185.50,\n" + "\"low\": 183.75,\n" + "\"close\": 185.25\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-24\",\n" + "\"open\": 185.25,\n" + "\"high\": 186.75,\n" + "\"low\": 184.50,\n"
				+ "\"close\": 186.00\n" + "},\n" + "{\n" + "\"time\": \"2018-10-25\",\n" + "\"open\": 185.75,\n"
				+ "\"high\": 187.00,\n" + "\"low\": 185.25,\n" + "\"close\": 186.50\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-26\",\n" + "\"open\": 186.75,\n" + "\"high\": 188.00,\n" + "\"low\": 186.00,\n"
				+ "\"close\": 187.25\n" + "},\n" + "{\n" + "\"time\": \"2018-10-27\",\n" + "\"open\": 187.50,\n"
				+ "\"high\": 188.50,\n" + "\"low\": 187.00,\n" + "\"close\": 188.00\n" + "},\n" + "{\n"
				+ "\"time\": \"2018-10-28\",\n" + "\"open\": 188.25,\n" + "\"high\": 189.00,\n" + "\"low\": 187.75,\n"
				+ "\"close\": 188.50\n" + "}\n" + "\n" + "]";

		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

		for (JsonElement elemento : jsonArray) {
			HistoricoInstrumentoRespuesta historicoInstrumentoRespuesta = new HistoricoInstrumentoRespuesta();
			JsonObject objeto = elemento.getAsJsonObject();

			String tiempo = objeto.get("time").getAsString();
			String precioDeApertura = objeto.get("open").getAsString();
			String maximo = objeto.get("high").getAsString();
			String minimo = objeto.get("low").getAsString();
			String precioDeCierre = objeto.get("close").getAsString();

			historicoInstrumentoRespuesta = new HistoricoInstrumentoRespuesta();
			historicoInstrumentoRespuesta.setTiempo(tiempo);
			historicoInstrumentoRespuesta.setPrecioDeApertura(precioDeApertura);
			historicoInstrumentoRespuesta.setMaximo(maximo);
			historicoInstrumentoRespuesta.setMinimo(minimo);
			historicoInstrumentoRespuesta.setPrecioDeCierre(precioDeCierre);

			listaHistoricoInstrumentoRespuesta.add(historicoInstrumentoRespuesta);

		}

		return listaHistoricoInstrumentoRespuesta;
	}

	@Override
	public void persistirInstrumentos(List<Instrumento> listaInstrumentos) {
		try {
			for (Instrumento instrumento : listaInstrumentos) {

				BigDecimal variacion = instrumento.getVariacionPorcentual();

				if (variacion.compareTo(new BigDecimal(-2)) >= 0 && variacion.compareTo(new BigDecimal(2)) <= 0) {
					instrumento.setCategoriaPerfil("Conservador");
				} else if (variacion.compareTo(new BigDecimal(-5)) >= 0
						&& variacion.compareTo(new BigDecimal(5)) <= 0) {
					instrumento.setCategoriaPerfil("Moderado");
				} else {
					instrumento.setCategoriaPerfil("Agresivo");
				}

				Instrumento instrumentoBuscado = instrumentoRepositorio.encontrarPorSimbolo(instrumento.getSimbolo());

				if (instrumentoBuscado != null) {
					instrumentoBuscado.setCategoriaPerfil(instrumento.getCategoriaPerfil());
					instrumentoBuscado.setVariacionPorcentual(instrumento.getVariacionPorcentual());
					instrumentoRepositorio.save(instrumentoBuscado);
				} else {
					instrumentoRepositorio.save(instrumento);
				}
			}
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException("Error al persistir instrumentos", e);
		}
	}

	@Override
	public List<Instrumento> obtenerInstrumentosAlAzar() {
		return instrumentoRepositorio.obtenerInstrumentosAlAzar();
	}

	@Override
	public Instrumento obtenerInstrumentoPorID(Long coProductoID) {
		return instrumentoRepositorio.obtenerInstrumentoPorID(coProductoID);
		
	}

	@Override
	public Instrumento obtenerInstrumentoPorTipoPerfil(String tipoPerfil) {
		return instrumentoRepositorio.obtenerInstrumentoPorTipoPerfil(tipoPerfil);
		
	}

	@Override
	public Instrumento obtenerInstrumentoPorSimbolo(String simboloInstrumento) {
		return instrumentoRepositorio.encontrarPorSimbolo(simboloInstrumento);
	}
}
