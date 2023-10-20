package com.unlam.tpi.servicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.RespuestaDTO;
import com.unlam.tpi.modelo.persistente.Pregunta;
import com.unlam.tpi.interfaces.PreguntaServicio;
import com.unlam.tpi.interfaces.RespuestaServicio;
import com.unlam.tpi.modelo.persistente.Respuesta;
import com.unlam.tpi.repositorio.RespuestaRepositorio;

@Service
public class RespuestaServicioImpl implements RespuestaServicio {

	private static final String COLUMNA_PREGUNTA = "pregunta";

	private static final String COLUMNA_ORDEN = "orden";

	private static final String COLUMNA_VALOR = "valor";

	private static final String COLUMNA_NOMBRE = "nombre";

	private static final String COLUMNA_INSTRUMENTO = "instrumento";

	private static final String HOJA_RESPUESTA = "respuesta";

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	@Autowired
	PreguntaServicio preguntaServicio;

	@Override
	public void guardar(RespuestaDTO respuesta) {
		try {
			Respuesta persistente = RespuestaDTO.dTOaEntidad(respuesta);
			getRespuestaRepositorio().save(persistente);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		}
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelPregunta) {
		List<Respuesta> listaRespuesta = new ArrayList<>();
		Map<Integer, String> encabezado = new HashedMap<>();
		Map<String, Pregunta> preguntaMap = new HashedMap<>();
		XSSFWorkbook libro;
		try {
			libro = new XSSFWorkbook(excelPregunta.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_RESPUESTA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja respuesta");
			}
			for (Row fila : hoja) {
				Respuesta respuesta = new Respuesta();
				Iterator<Cell> iteadorColumna = fila.iterator();
				while (iteadorColumna.hasNext()) {
					Cell columna = iteadorColumna.next();
					if (columna.getRowIndex() == 0) {
						crearEncabezado(encabezado, columna);
					} else {
						if (encabezado.containsKey(columna.getColumnIndex())) {
							String nombreColumna = encabezado.get(columna.getColumnIndex());
							convertirExcelARespuesta(preguntaMap, respuesta, columna, nombreColumna);
						}
					}
				}
				if (respuestaEsValida(respuesta)) {
					listaRespuesta.add(respuesta);
				}
			}
			if (0 < listaRespuesta.size()) {
				getRespuestaRepositorio().saveAll(listaRespuesta);
			}
		} catch (IOException e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		}
	}

	
	private void agregarModificarRespuesta() {
		
	}
	
	private void crearEncabezado(Map<Integer, String> encabezado, Cell columna) {
		if (!encabezado.containsKey(columna.getColumnIndex())) {
			encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
		}
	}

	private boolean respuestaEsValida(Respuesta respuesta) {
		 if(respuesta.getNombre() != null && respuesta.getValor() != null
				&& respuesta.getOrden() != null && respuesta.getPregunta() != null) {
			 return Boolean.TRUE;
		 }
		 return Boolean.FALSE;
	}

	private void convertirExcelARespuesta(Map<String, Pregunta> preguntaMap, Respuesta respuesta, Cell columna,
			String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_INSTRUMENTO:
			respuesta.setInstrumento(columna.getStringCellValue());
			break;
		case COLUMNA_NOMBRE:
			respuesta.setNombre(columna.getStringCellValue());
			break;
		case COLUMNA_VALOR:
			respuesta.setValor(Integer.valueOf((int) columna.getNumericCellValue()));
			break;
		case COLUMNA_ORDEN:
			respuesta.setOrden(Integer.valueOf((int) columna.getNumericCellValue()));
			break;
		case COLUMNA_PREGUNTA:
			String enunciado = columna.getStringCellValue();
			if (preguntaMap.containsKey(enunciado)) {
				respuesta.setPregunta(preguntaMap.get(enunciado));
			} else {
				Pregunta pregunta = getPreguntaServicio().getPreguntaPorEnunciado(enunciado);
				if (pregunta == null) {
					throw new ServiceException("Error al buscar la pregunta: " + enunciado);
				}
				preguntaMap.put(enunciado, pregunta);
				respuesta.setPregunta(preguntaMap.get(enunciado));
			}
			break;
		}
	}

	@Override
	public RespuestaDTO getRespuestaDTOPorNombre(String nombre) {
		try {
			Respuesta respuesta = getRespuestaPorNombre(nombre);
			if(respuesta==null) {
				throw new ServiceException("Error al obtener la respuesta: "+nombre);
			}
			return RespuestaDTO.entidadADTO(respuesta);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta: "+nombre, e);
		}
	}
	
	@Override
	public Respuesta getRespuestaPorNombre(String nombre) {
		try {
			Respuesta respuesta = getRespuestaRepositorio().findByNombre(nombre);
			if (respuesta == null) {
				throw new ServiceException("Error al obtener la respuesta: "+nombre);
			}
			return respuesta;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta: "+nombre, e);
		}
	}
	
	@Override
	public RespuestaDTO getRespuestaDTOPorID(Long id) {
		try {
			Respuesta respuesta = getRespuestaRepositorio().findByOid(id);
			if (respuesta == null) {
				throw new ServiceException("Error al obtener la respuesta");
			}
			return RespuestaDTO.entidadADTO(respuesta);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getRespuestaRepositorio().deleteById(id);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la respuesta", e);
		}
	}

	@Override
	public List<RespuestaDTO> listar() {
		try {
			return RespuestaDTO.entidadDTOLista(getRespuestaRepositorio().findAll());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al listar las respuestas", e);
		}
	}

	public RespuestaRepositorio getRespuestaRepositorio() {
		return respuestaRepositorio;
	}

	public void setRespuestaRepositorio(RespuestaRepositorio respuestaRepositorio) {
		this.respuestaRepositorio = respuestaRepositorio;
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

}
