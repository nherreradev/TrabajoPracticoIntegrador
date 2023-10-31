package com.unlam.tpi.core.servicio;

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

import com.unlam.tpi.core.interfaces.PreguntaServicio;
import com.unlam.tpi.core.interfaces.RespuestaRepositorio;
import com.unlam.tpi.core.interfaces.RespuestaServicio;
import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.core.modelo.Respuesta;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.delivery.dto.RespuestaDTO;

@Service
public class RespuestaServicioImpl implements RespuestaServicio {

	private static final int FILA_ENCABEZADO = 0;
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
		Respuesta respuesta;
		try {
			libro = new XSSFWorkbook(excelPregunta.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_RESPUESTA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja respuesta");
			}
			for (Row fila : hoja) {
				respuesta = new Respuesta();
				leerColumna(encabezado, preguntaMap, respuesta, fila);
				agregarRespuestaALista(listaRespuesta, respuesta);
			}
			guardarRespuestaLista(listaRespuesta);
		} catch (IOException e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la respuesta", e);
		}
	}

	private void leerColumna(Map<Integer, String> encabezado, Map<String, Pregunta> preguntaMap, Respuesta respuesta,
			Row fila) {
		Iterator<Cell> iteadorColumna = fila.iterator();
		while (iteadorColumna.hasNext()) {
			Cell columna = iteadorColumna.next();
			crearEncabezado(encabezado, columna);
			crearRespuesta(encabezado, preguntaMap, respuesta, columna);
		}
	}

	private void agregarRespuestaALista(List<Respuesta> listaRespuesta, Respuesta respuesta) {
		if (respuestaEsValida(respuesta)) {
			respuesta = agregarModificarRespuesta(respuesta);
			listaRespuesta.add(respuesta);
		}
	}

	private void crearEncabezado(Map<Integer, String> encabezado, Cell columna) {
		if (esEncabezado(columna) && !columnaEsValida(encabezado, columna)) {
			encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
		}
	}

	private Boolean esEncabezado(Cell columna) {
		return columna.getRowIndex() == FILA_ENCABEZADO;
	}

	private void crearRespuesta(Map<Integer, String> encabezado, Map<String, Pregunta> preguntaMap, Respuesta respuesta,
			Cell columna) {
		if (!esEncabezado(columna) && columnaEsValida(encabezado, columna)) {
			String nombreColumna = encabezado.get(columna.getColumnIndex());
			parsearExcelARespuesta(preguntaMap, respuesta, columna, nombreColumna);
		}
	}

	private boolean columnaEsValida(Map<Integer, String> encabezado, Cell columna) {
		return encabezado.containsKey(columna.getColumnIndex());
	}

	private void guardarRespuestaLista(List<Respuesta> listaRespuesta) {
		if (listaRespuesta.isEmpty()) {
			throw new ServiceException("No hay respuestas para guardar");
		}
		getRespuestaRepositorio().saveAll(listaRespuesta);
	}

	private Respuesta agregarModificarRespuesta(Respuesta respuesta) {
		Respuesta respuestaExistente = null;
		if (respuesta.getInstrumento() != null) {
			respuestaExistente = getRespuestaRepositorio().findByNombreAndInstrumento(respuesta.getNombre(),
					respuesta.getInstrumento());
		} else {
			respuestaExistente = getRespuestaRepositorio().findByNombre(respuesta.getNombre());
		}
		if (respuestaExistente != null) {
			respuestaExistente.setOrden(respuesta.getOrden());
			respuestaExistente.setValor(respuesta.getValor());
			respuestaExistente.setPregunta(respuesta.getPregunta());
			return respuestaExistente;
		}
		return respuesta;
	}

	private boolean respuestaEsValida(Respuesta respuesta) {
		if (respuesta.getNombre() != null && respuesta.getValor() != null && respuesta.getOrden() != null
				&& respuesta.getPregunta() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void parsearExcelARespuesta(Map<String, Pregunta> preguntaMap, Respuesta respuesta, Cell columna,
			String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_INSTRUMENTO:
			String instrumento = columna.getStringCellValue().trim();
			if (esStringValido(instrumento)) {
				respuesta.setInstrumento(instrumento);
			}
			break;
		case COLUMNA_NOMBRE:
			String nombre = columna.getStringCellValue().trim();
			if (esStringValido(nombre)) {
				respuesta.setNombre(nombre);
			}
			break;
		case COLUMNA_VALOR:
			int valor = (int) columna.getNumericCellValue();
			if (esEnteroValido(valor)) {
				respuesta.setValor(valor);
			}
			break;
		case COLUMNA_ORDEN:
			int orden = (int) columna.getNumericCellValue();
			if (esEnteroValido(orden)) {
				respuesta.setOrden(orden);
			}
			break;
		case COLUMNA_PREGUNTA:
			String enunciado = columna.getStringCellValue().trim();
			if (esStringValido(enunciado)) {
				Pregunta pregunta = obtenerPregunta(preguntaMap, respuesta, enunciado);
				respuesta.setPregunta(pregunta);
			}
		}
	}

	private Boolean esStringValido(String nombre) {
		return !nombre.isBlank();
	}

	private Boolean esEnteroValido(int valor) {
		return valor >= 0;
	}

	private Pregunta obtenerPregunta(Map<String, Pregunta> preguntaMap, Respuesta respuesta, String enunciado) {
		if (preguntaMap.containsKey(enunciado)) {
			return preguntaMap.get(enunciado);
		}
		Pregunta pregunta = getPreguntaServicio().getPreguntaPorEnunciado(enunciado);
		if (pregunta == null) {
			throw new ServiceException("Error al buscar la pregunta: " + enunciado);
		}
		preguntaMap.put(enunciado, pregunta);
		return pregunta;
	}

	@Override
	public RespuestaDTO getRespuestaDTOPorNombre(String nombre) {
		try {
			Respuesta respuesta = getRespuestaPorNombre(nombre);
			if (respuesta == null) {
				throw new ServiceException("Error al obtener la respuesta: " + nombre);
			}
			return RespuestaDTO.entidadADTO(respuesta);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta: " + nombre, e);
		}
	}

	@Override
	public Respuesta getRespuestaPorNombre(String nombre) {
		try {
			Respuesta respuesta = getRespuestaRepositorio().findByNombre(nombre);
			if (respuesta == null) {
				throw new ServiceException("Error al obtener la respuesta: " + nombre);
			}
			return respuesta;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la respuesta: " + nombre, e);
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
