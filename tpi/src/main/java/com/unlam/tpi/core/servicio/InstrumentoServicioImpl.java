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
import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.interfaces.InstrumentoRepositorio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.core.modelo.HistoricoInstrumentoRespuesta;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.delivery.dto.InstrumentoMapper;

@Service
@Transactional
public class InstrumentoServicioImpl implements InstrumentoServicio {

	@Autowired
	InstrumentoRepositorio instrumentoRepositorio;

	@Autowired
	PuntasServicio puntasServicio;

	@Autowired
	HistoricoServicio historicoServicio;

	@Override
	public List<HistoricoInstrumentoRespuesta> getHistoricoInstrumento(String simbolo) {

		Instrumento instrumento = obtenerInstrumentoPorSimbolo(simbolo);

		List<HistoricoInstrumentoRespuesta> listaHistoricoInstrumentoRespuesta = new ArrayList<>();

		String json = historicoServicio.getHistoricoMongo("mensual", instrumento.getCategoriaInstrumento(), simbolo);

		JsonArray historicoArray = InstrumentoMapper.armarHistoricoArray(json);

		for (JsonElement elemento : historicoArray) {
			HistoricoInstrumentoRespuesta historicoInstrumentoRespuesta = new HistoricoInstrumentoRespuesta();
			JsonObject objeto = elemento.getAsJsonObject();

			String tiempo = objeto.get("fecha").getAsString();
			String precioDeApertura = objeto.get("apertura").getAsString();
			String maximo = objeto.get("maximo").getAsString();
			String minimo = objeto.get("minimo").getAsString();
			String precioDeCierre = objeto.get("cierre").getAsString();

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
		for (Instrumento instrumento : listaInstrumentos) {
			BigDecimal variacion = instrumento.getVariacionPorcentual();
			if (variacion.compareTo(new BigDecimal(-2)) >= 0 && variacion.compareTo(new BigDecimal(2)) <= 0) {
				instrumento.setCategoriaPerfil("Conservador");
			} else if (variacion.compareTo(new BigDecimal(-5)) >= 0 && variacion.compareTo(new BigDecimal(5)) <= 0) {
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
