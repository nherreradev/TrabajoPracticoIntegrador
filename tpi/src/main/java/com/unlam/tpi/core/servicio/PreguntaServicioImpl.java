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

import com.unlam.tpi.core.interfaces.CategoriaServicio;
import com.unlam.tpi.core.interfaces.PreguntaServicio;
import com.unlam.tpi.core.interfaces.SeccionServicio;
import com.unlam.tpi.core.modelo.Categoria;
import com.unlam.tpi.core.modelo.Pregunta;
import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.delivery.dto.PreguntaDTO;
import com.unlam.tpi.delivery.dto.TipoComponente;
import com.unlam.tpi.infraestructura.repositorio.PreguntaRepositorio;

@Service
public class PreguntaServicioImpl implements PreguntaServicio {

	private static final int FILA_ENCABEZADO = 0;

	private static final String COLUMNA_SECCION = "seccion";

	private static final String COLUMNA_CATEGORIA = "categoria";

	private static final String COLUMNA_TIPOCOMPONENTE = "tipocomponente";

	private static final String COLUMNA_DESCRIPCION = "descripcion";

	private static final String COLUMNA_ENUNCIADO = "enunciado";

	private static final String HOJA_PREGUNTA = "pregunta";

	@Autowired
	PreguntaRepositorio preguntaRepositorio;

	@Autowired
	CategoriaServicio categoriaServicio;

	@Autowired
	SeccionServicio seccionServicio;

	@Override
	public void guardar(PreguntaDTO pregunta) {
		try {
			Pregunta persistente = PreguntaDTO.dTOaEntidad(pregunta);
			getPreguntaRepositorio().save(persistente);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelPregunta) {
		List<Pregunta> listaPregunta = new ArrayList<>();
		Map<Integer, String> encabezado = new HashedMap<>();
		Map<String, Categoria> categoriaMap = new HashedMap<>();
		Map<String, Seccion> seccionMap = new HashedMap<>();
		XSSFWorkbook libro;
		Pregunta pregunta;
		try {
			libro = new XSSFWorkbook(excelPregunta.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_PREGUNTA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja pregunta");
			}
			for (Row fila : hoja) {
				pregunta = new Pregunta();
				leerColumna(encabezado, categoriaMap, seccionMap, pregunta, fila);
				agregarPreguntaALista(listaPregunta, pregunta);
			}
			guardarPreguntaLista(listaPregunta);
		} catch (IOException e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	private void leerColumna(Map<Integer, String> encabezado, Map<String, Categoria> categoriaMap,
			Map<String, Seccion> seccionMap, Pregunta pregunta, Row fila) {
		Iterator<Cell> iteadorColumna = fila.iterator();
		while (iteadorColumna.hasNext()) {
			Cell columna = iteadorColumna.next();
			crearEncabezado(encabezado, columna);
			crearPregunta(encabezado, categoriaMap, seccionMap, pregunta, columna);
		}
	}

	private void agregarPreguntaALista(List<Pregunta> listaPregunta, Pregunta pregunta) {
		if (preguntaEsValida(pregunta)) {
			pregunta = agregarModificarPregunta(pregunta);
			listaPregunta.add(pregunta);
		}
	}

	private void guardarPreguntaLista(List<Pregunta> listaPregunta) {
		if (listaPregunta.isEmpty()) {
			throw new ServiceException("No hay preguntas para guardar");
		}
		getPreguntaRepositorio().saveAll(listaPregunta);
	}

	private void crearEncabezado(Map<Integer, String> encabezado, Cell columna) {
		if (esEncabezado(columna) && !columnaEsValida(encabezado, columna)) {
			encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
		}
	}

	private Boolean esEncabezado(Cell columna) {
		return columna.getRowIndex() == FILA_ENCABEZADO;
	}

	private boolean columnaEsValida(Map<Integer, String> encabezado, Cell columna) {
		return encabezado.containsKey(columna.getColumnIndex());
	}

	private void crearPregunta(Map<Integer, String> encabezado, Map<String, Categoria> categoriaMap,
			Map<String, Seccion> seccionMap, Pregunta pregunta, Cell columna) {
		if (!esEncabezado(columna) && columnaEsValida(encabezado, columna)) {
			String nombreColumna = encabezado.get(columna.getColumnIndex());
			parsearExcelAPregunta(categoriaMap, seccionMap, pregunta, columna, nombreColumna);
		}
	}

	private Pregunta agregarModificarPregunta(Pregunta pregunta) {
		Pregunta preguntaExistente = getPreguntaRepositorio().findByEnunciado(pregunta.getEnunciado());
		if (preguntaExistente != null) {
			preguntaExistente.setCategoria(pregunta.getCategoria());
			preguntaExistente.setDescripcion(pregunta.getDescripcion());
			preguntaExistente.setTipoComponente(pregunta.getTipoComponente());
			preguntaExistente.setSeccion(pregunta.getSeccion());
			return preguntaExistente;
		}
		return pregunta;
	}

	private Boolean preguntaEsValida(Pregunta pregunta) {
		if (pregunta.getEnunciado() != null && pregunta.getCategoria() != null
				&& pregunta.getTipoComponente() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void parsearExcelAPregunta(Map<String, Categoria> categoriaMap, Map<String, Seccion> seccionMap,
			Pregunta pregunta, Cell columna, String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_ENUNCIADO:
			String enunciado = columna.getStringCellValue().trim();
			if (esStringValido(enunciado)) {
				pregunta.setEnunciado(enunciado);
			}
			break;
		case COLUMNA_DESCRIPCION:
			String descripcion = columna.getStringCellValue().trim();
			if (esStringValido(descripcion)) {
				pregunta.setDescripcion(descripcion);
			}
			break;
		case COLUMNA_TIPOCOMPONENTE:
			String tipoComponenteNombre = columna.getStringCellValue().trim();
			if (esStringValido(tipoComponenteNombre)) {
				TipoComponente tipoComponente = parseStringATipoComponente(tipoComponenteNombre);
				pregunta.setTipoComponente(tipoComponente);
			}
			break;
		case COLUMNA_CATEGORIA:
			String nombreCategoria = columna.getStringCellValue().trim();
			agregarCategoria(categoriaMap, pregunta, nombreCategoria);
			break;
		case COLUMNA_SECCION:
			String nombreSeccion = columna.getStringCellValue().trim();
			if (esStringValido(nombreSeccion)) {
				agregarSeccion(seccionMap, pregunta, nombreSeccion);
			}
		}
	}

	private Boolean esStringValido(String nombre) {
		return !nombre.isBlank();
	}

	private TipoComponente parseStringATipoComponente(String tipoComponente) {
		try {
			return TipoComponente.valueOf(tipoComponente);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("El TipoComponente con nombre: " + tipoComponente + " no es valido.");
		}
	}

	private void agregarCategoria(Map<String, Categoria> categoriaMap, Pregunta pregunta, String nombreCategoria) {
		if (nombreCategoria.isBlank()) {
			throw new ServiceException("Categoria obligatoria. Debe agregar una categoria a la pregunta");
		}
		if (categoriaMap.containsKey(nombreCategoria)) {
			pregunta.setCategoria(categoriaMap.get(nombreCategoria));
		} else {
			Categoria categoria = getCategoriaServicio().getCategoriaPorNombre(nombreCategoria);
			if (categoria == null) {
				throw new ServiceException("Error al obtener la categoria con nombre: " + nombreCategoria);
			}
			categoriaMap.put(nombreCategoria, categoria);
			pregunta.setCategoria(categoriaMap.get(nombreCategoria));
		}
	}

	private void agregarSeccion(Map<String, Seccion> seccionMap, Pregunta pregunta, String nombreSeccion) {
		if (seccionMap.containsKey(nombreSeccion)) {
			pregunta.setSeccion(seccionMap.get(nombreSeccion));
		} else {
			Seccion seccion = getSeccionServicio().getSeccionPorNombre(nombreSeccion);
			if (seccion == null) {
				throw new ServiceException("Error al obtener la sección con nombre: " + nombreSeccion);
			}
			seccionMap.put(nombreSeccion, seccion);
			pregunta.setSeccion(seccionMap.get(nombreSeccion));
		}
	}

	@Override
	public PreguntaDTO getPreguntaDTOPorID(Long id) {
		try {
			Pregunta pregunta = getPreguntaRepositorio().getReferenceById(id);
			if (pregunta == null) {
				throw new ServiceException("Error al obtener la pregunta");
			}
			return PreguntaDTO.entidadADTO(pregunta);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la pregunta", e);
		}
	}

	@Override
	public PreguntaDTO getPreguntaDTOPorEnunciado(String nombre) {
		try {
			Pregunta pregunta = getPreguntaPorEnunciado(nombre);
			if (pregunta == null) {
				throw new ServiceException("Error al obtener la pregunta: " + nombre);
			}
			return PreguntaDTO.entidadADTO(pregunta);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la pregunta: " + nombre, e);
		}
	}

	@Override
	public Pregunta getPreguntaPorEnunciado(String enunciado) {
		try {
			Pregunta pregunta = getPreguntaRepositorio().findByEnunciado(enunciado);
			if (pregunta == null) {
				throw new ServiceException("Error al obtener la pregunta: " + enunciado);
			}
			return pregunta;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la pregunta: " + enunciado, e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getPreguntaRepositorio().deleteById(id);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la pregunta", e);
		}
	}

	@Override
	public List<PreguntaDTO> listar() {
		try {
			return PreguntaDTO.entidadDTOLista(getPreguntaRepositorio().findAll());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al listar las preguntas", e);
		}
	}

	@Override
	public List<PreguntaDTO> listarPorCategoria(String categoria) {
		try {
			return PreguntaDTO.entidadDTOLista(getPreguntaRepositorio().findByCategoria_Nombre(categoria));
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al listar las preguntas", e);
		}
	}

	public PreguntaRepositorio getPreguntaRepositorio() {
		return preguntaRepositorio;
	}

	public void setPreguntaRepositorio(PreguntaRepositorio preguntaRepositorio) {
		this.preguntaRepositorio = preguntaRepositorio;
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}

}
