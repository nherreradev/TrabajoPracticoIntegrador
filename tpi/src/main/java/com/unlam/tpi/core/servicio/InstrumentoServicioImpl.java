package com.unlam.tpi.core.servicio;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoPrecioAPI;
import com.unlam.tpi.core.interfaces.InstrumentoRepositorio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PuntasServicio;
import com.unlam.tpi.core.modelo.HistoricoInstrumento;
import com.unlam.tpi.core.modelo.Instrumento;

@Service
@Transactional
public class InstrumentoServicioImpl implements InstrumentoServicio {

	@Autowired
	InstrumentoRepositorio instrumentoRepositorio;

	@Autowired
	PuntasServicio puntasServicio;

	@Autowired
	HistoricoPrecioAPI historicoServicio;

	@Override
	public List<HistoricoInstrumento> getHistoricoInstrumento(String simbolo) {
		Instrumento instrumento = obtenerInstrumentoPorSimbolo(simbolo);
		List<HistoricoInstrumento> listaHistoricoInstrumentoRespuesta = historicoServicio
				.getHistoricoMongo("mensual", instrumento.getCategoriaInstrumento(), simbolo);
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
