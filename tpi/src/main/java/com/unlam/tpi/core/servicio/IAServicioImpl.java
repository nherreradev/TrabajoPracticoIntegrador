package com.unlam.tpi.core.servicio;

import java.io.FileWriter;
import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.IARepositorio;
import com.unlam.tpi.core.interfaces.IAServicio;
import com.unlam.tpi.core.interfaces.InstrumentoServicio;
import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.ServiceException;

@Service
@Transactional
public class IAServicioImpl implements IAServicio {

	@Autowired
	IARepositorio iARepositorio;

	@Autowired
	InstrumentoServicio instrumentoServicio;

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
	public List<Instrumento> obtenerPortafolioSugerido(String tipoPerfil) {
		return instrumentoServicio.obtenerInstrumentosAlAzar();
	}

}
