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
import com.unlam.tpi.delivery.dto.RespuestaMapper;

@Service
public class RespuestaServicioImpl implements RespuestaServicio {

	private static final int FILA_ENCABEZADO = 0;
	private static final String COLUMNA_PREGUNTA = "pregunta";
	private static final String COLUMNA_ORDEN = "orden";
	private static final String COLUMNA_VALOR = "valor";
	private static final String COLUMNA_NOMBRE = "nombre";
	private static final String COLUMNA_INSTRUMENTO = "instrumento";
	private static final String HOJA_RESPUESTA = "respuesta";
	private static final String COLUMNA_CODIGO = "codigo";	

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	@Autowired
	PreguntaServicio preguntaServicio;

	@Override
	public void guardar(RespuestaDTO respuesta) {
		Respuesta persistente = RespuestaMapper.dTOaEntidad(respuesta);
		getRespuestaRepositorio().save(persistente);
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException {
		List<Respuesta> listaRespuesta = new ArrayList<>();
		Map<Integer, String> encabezado = new HashedMap<>();
		Map<String, Pregunta> preguntaMap = new HashedMap<>();
		XSSFWorkbook libro;
		Respuesta respuesta;
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
			respuestaExistente = getRespuestaRepositorio().findByCodigoAndInstrumento(respuesta.getCodigo(),
					respuesta.getInstrumento());
		} else {
			respuestaExistente = getRespuestaRepositorio().findByCodigo(respuesta.getCodigo());
		}
		if (respuestaExistente != null) {
			respuestaExistente.setNombre(respuesta.getNombre());
			respuestaExistente.setOrden(respuesta.getOrden());
			respuestaExistente.setValor(respuesta.getValor());
			respuestaExistente.setPregunta(respuesta.getPregunta());
			return respuestaExistente;
		}
		return respuesta;
	}

	private boolean respuestaEsValida(Respuesta respuesta) {
		if (respuesta.getCodigo() != null && respuesta.getNombre() != null && respuesta.getValor() != null
				&& respuesta.getOrden() != null && respuesta.getPregunta() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void parsearExcelARespuesta(Map<String, Pregunta> preguntaMap, Respuesta respuesta, Cell columna,
			String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_CODIGO:
			String codigo = columna.getStringCellValue().trim();
			if (esStringValido(codigo)) {
				respuesta.setCodigo(codigo);
			}
			break;
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
			String codigo_pregunta = columna.getStringCellValue().trim();
			if (esStringValido(codigo_pregunta)) {
				Pregunta pregunta = obtenerPregunta(preguntaMap, respuesta, codigo_pregunta);
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

	private Pregunta obtenerPregunta(Map<String, Pregunta> preguntaMap, Respuesta respuesta, String codigo) {
		if (preguntaMap.containsKey(codigo)) {
			return preguntaMap.get(codigo);
		}
		Pregunta pregunta = getPreguntaServicio().getPreguntaPorCodigo(codigo);
		if (pregunta == null) {
			throw new ServiceException("Error al buscar la pregunta: " + codigo);
		}
		preguntaMap.put(codigo, pregunta);
		return pregunta;
	}

	@Override
	public RespuestaDTO getRespuestaDTOPorCodigo(String codigo) {
		Respuesta respuesta = getRespuestaPorCodigo(codigo);
		if (respuesta == null) {
			throw new ServiceException("Error al obtener la respuesta: " + codigo);
		}
		return RespuestaMapper.entidadADTO(respuesta);
	}

	@Override
	public RespuestaDTO getRespuestaDTOPorNombre(String nombre) {
		Respuesta respuesta = getRespuestaPorNombre(nombre);
		if (respuesta == null) {
			throw new ServiceException("Error al obtener la respuesta: " + nombre);
		}
		return RespuestaMapper.entidadADTO(respuesta);
	}
	
	@Override
	public Respuesta getRespuestaPorCodigo(String codigo) {
		Respuesta respuesta = getRespuestaRepositorio().findByCodigo(codigo);
		if (respuesta == null) {
			throw new ServiceException("Error al obtener la respuesta: " + codigo);
		}
		return respuesta;
	}

	@Override
	public Respuesta getRespuestaPorNombre(String nombre) {
		Respuesta respuesta = getRespuestaRepositorio().findByNombre(nombre);
		if (respuesta == null) {
			throw new ServiceException("Error al obtener la respuesta: " + nombre);
		}
		return respuesta;
	}
	
	@Override
	public RespuestaDTO getRespuestaDTOPorID(Long id) {
		Respuesta respuesta = getRespuestaRepositorio().findByOid(id);
		if (respuesta == null) {
			throw new ServiceException("Error al obtener la respuesta");
		}
		return RespuestaMapper.entidadADTO(respuesta);
	}

	@Override
	public void borrar(Long id) {
		getRespuestaRepositorio().deleteById(id);
	}
	
	@Override
	public void borrar(String codigo) {
		getRespuestaRepositorio().deleteByCodigo(codigo);
	}

	@Override
	public List<RespuestaDTO> listar() {
		return RespuestaMapper.entidadDTOLista(getRespuestaRepositorio().findAll());
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
