package com.unlam.tpi.core.servicio;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.core.interfaces.IARepositorio;
import com.unlam.tpi.core.interfaces.IAServicio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.interfaces.PortafolioSugerenciaServicio;
import com.unlam.tpi.core.modelo.HistoricoInstrumentoRespuesta;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.ServiceException;

@Service
@Transactional
public class IAServicioImpl implements IAServicio {

	@Autowired
	IARepositorio iARepositorio;

	@Autowired
	InstrumentoServicio instrumentoServicio;

	@Autowired
	PortafolioSugerenciaServicio portafolioSugerenciaServicio;

	@Override
	public void generarTXT(String tipo) {

		List<Tuple> result = iARepositorio.generarTxt(tipo);

		FileWriter fileWriter;

		try {
			fileWriter = new FileWriter(tipo + ".txt");
			fileWriter.write("ProductID\tProductID_Copurchased\n");
			for (Tuple tuple : result) {
				String producto = tuple.get("producto").toString();
				String coProducto = tuple.get("co_producto").toString();
				fileWriter.write(producto + "\t" + coProducto + "\n");

			}
			fileWriter.close();
		} catch (Exception e) {
			throw new ServiceException("Error al generar archivos para la IA");
		}
	}

	@Override
	public List<Instrumento> obtenerPortafolioSugeridoFake(String tipoPerfil) {
		return instrumentoServicio.obtenerInstrumentosAlAzar();
	}

	@Override
	public List<Instrumento> obtenerPortafolioSugerido(String tipoPerfil, String url, int idProducto) {
		try {

			int idProductoAEnviar = 0;

			if (idProducto == 0) {
				Instrumento instrumentoPorPerfil = instrumentoServicio.obtenerInstrumentoPorTipoPerfil(tipoPerfil);
				idProductoAEnviar = instrumentoPorPerfil.getOid().intValue();
			} else {
				idProductoAEnviar = idProducto;
			}

			List<Instrumento> portafolioSugerido = new ArrayList<>();

			String json = portafolioSugerenciaServicio.obtenerRecomendacion(tipoPerfil, url, idProductoAEnviar);

			JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

			for (JsonElement elemento : jsonArray) {
				JsonObject objeto = elemento.getAsJsonObject();
				Long coProductoID = objeto.get("coPurchaseProductID").getAsLong();
				Instrumento instrumento = instrumentoServicio.obtenerInstrumentoPorID(coProductoID);
				portafolioSugerido.add(instrumento);
			}

			return portafolioSugerido;

		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener portafolio sugerido", e);
		}
	}

}
