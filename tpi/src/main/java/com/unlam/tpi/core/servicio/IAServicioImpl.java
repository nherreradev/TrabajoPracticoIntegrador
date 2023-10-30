package com.unlam.tpi.core.servicio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.IARepositorio;
import com.unlam.tpi.core.interfaces.IAServicio;
import com.unlam.tpi.core.modelo.ServiceException;

@Service
public class IAServicioImpl implements IAServicio {

	@Autowired
	IARepositorio iARepositorio;

	@Override
	public void generarTXT(String tipo) {

		List<Tuple> result = iARepositorio.generarTxt(tipo);

		FileWriter fileWriter;

		try {
			fileWriter = new FileWriter(tipo+".txt");
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

}
