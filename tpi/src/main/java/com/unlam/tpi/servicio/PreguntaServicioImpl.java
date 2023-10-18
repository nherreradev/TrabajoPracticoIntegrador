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
import com.unlam.tpi.dto.PreguntaDTO;
import com.unlam.tpi.dto.TipoComponente;
import com.unlam.tpi.modelo.persistente.Categoria;
import com.unlam.tpi.interfaces.CategoriaServicio;
import com.unlam.tpi.interfaces.PreguntaServicio;
import com.unlam.tpi.interfaces.SeccionServicio;
import com.unlam.tpi.modelo.persistente.Pregunta;
import com.unlam.tpi.modelo.persistente.Seccion;
import com.unlam.tpi.repositorio.PreguntaRepositorio;

@Service
public class PreguntaServicioImpl implements PreguntaServicio {

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
		try {
			libro = new XSSFWorkbook(excelPregunta.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_PREGUNTA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja pregunta");
			}
			for (Row fila : hoja) {
				Pregunta pregunta = new Pregunta();
				Iterator<Cell> iteadorColumna = fila.iterator();
				while (iteadorColumna.hasNext()) {
					Cell columna = iteadorColumna.next();
					if (columna.getRowIndex() == 0) {
						if (!encabezado.containsKey(columna.getColumnIndex())) {
							encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
						}
					} else {
						if (encabezado.containsKey(columna.getColumnIndex())) {
							String nombreColumna = encabezado.get(columna.getColumnIndex());
							convertirExcelAPregunta(categoriaMap, seccionMap, pregunta, columna, nombreColumna);
						}
					}
				}
				if (preguntaEsValida(pregunta)) {
					listaPregunta.add(agregarModificarPregunta(pregunta));
				}
			}
			if (0 < listaPregunta.size()) {
				getPreguntaRepositorio().saveAll(listaPregunta);
			}
		} catch (IOException e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
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
		if (pregunta.getEnunciado() != null && pregunta.getDescripcion() != null && pregunta.getCategoria() != null
				&& pregunta.getTipoComponente() != null && pregunta.getSeccion() != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void convertirExcelAPregunta(Map<String, Categoria> categoriaMap, Map<String, Seccion> seccionMap,
			Pregunta pregunta, Cell columna, String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_ENUNCIADO:
			pregunta.setEnunciado(columna.getStringCellValue());
			break;
		case COLUMNA_DESCRIPCION:
			pregunta.setDescripcion(columna.getStringCellValue());
			break;
		case COLUMNA_TIPOCOMPONENTE:
			pregunta.setTipoComponente(TipoComponente.valueOf(columna.getStringCellValue()));
			break;
		case COLUMNA_CATEGORIA:
			String nombreCategoria = columna.getStringCellValue();
			agregarCategoria(categoriaMap, pregunta, nombreCategoria);
			break;
		case COLUMNA_SECCION:
			String nombreSeccion = columna.getStringCellValue();
			agregarSeccion(seccionMap, pregunta, nombreSeccion);
			break;
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
		if (!nombreSeccion.isBlank()) {
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
