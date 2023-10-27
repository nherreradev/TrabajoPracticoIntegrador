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

import com.unlam.tpi.delivery.dto.CategoriaDTO;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;
import com.unlam.tpi.infraestructura.modelo.Categoria;
import com.unlam.tpi.infraestructura.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	private static final int FILA_ENCABEZADO = 0;
	private static final String COLUMNA_DESCRIPCION = "descripcion";
	private static final String COLUMNA_NOMBRE = "nombre";
	private static final String HOJA_CATEGORIA = "categoria";

	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	@Override
	public void guardar(CategoriaDTO categoria) {
		try {
			Categoria persistente = CategoriaDTO.dTOaEntidad(categoria);
			getCategoriaRepositorio().save(persistente);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la categoria", e);
		}
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelPregunta) {
		List<Categoria> listaCategoria = new ArrayList<>();
		Map<Integer, String> encabezado = new HashedMap<>();
		XSSFWorkbook libro;
		Categoria categoria;
		try {
			libro = new XSSFWorkbook(excelPregunta.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_CATEGORIA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja categoria");
			}
			for (Row fila : hoja) {
				categoria = new Categoria();
				leerColumna(encabezado, categoria, fila);
				agregarCategoriaALista(listaCategoria, categoria);
			}
			guardarCategoriaLista(listaCategoria);
		} catch (IOException e) {
			throw new ServiceException("Error al guardar la categoria", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la categoria", e);
		}
	}

	private void guardarCategoriaLista(List<Categoria> listaCategoria) {
		if (listaCategoria.isEmpty()) {
			throw new ServiceException("No hay categorias para guardar");
		}
		getCategoriaRepositorio().saveAll(listaCategoria);
	}

	private void leerColumna(Map<Integer, String> encabezado, Categoria categoria, Row fila) {
		Iterator<Cell> iteadorColumna = fila.iterator();
		while (iteadorColumna.hasNext()) {
			Cell columna = iteadorColumna.next();
			crearEncabezado(encabezado, columna);
			crearCategoria(encabezado, categoria, columna);
		}
	}

	private Boolean esEncabezado(Cell columna) {
		return columna.getRowIndex() == FILA_ENCABEZADO;
	}

	private void crearCategoria(Map<Integer, String> encabezado, Categoria categoria, Cell columna) {
		if (!esEncabezado(columna) && columnaEsValida(encabezado, columna)) {
			String nombreColumna = encabezado.get(columna.getColumnIndex());
			parsearExcelACategoria(categoria, columna, nombreColumna);
		}
	}

	private void agregarCategoriaALista(List<Categoria> listaCategoria, Categoria categoria) {
		if (categoriaEsValida(categoria)) {
			categoria = agregarModificarCategoria(categoria);
			listaCategoria.add(categoria);
		}
	}

	private void crearEncabezado(Map<Integer, String> encabezado, Cell columna) {
		if (esEncabezado(columna) && !columnaEsValida(encabezado, columna)) {
			encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
		}
	}

	private boolean columnaEsValida(Map<Integer, String> encabezado, Cell columna) {
		return encabezado.containsKey(columna.getColumnIndex());
	}

	private void parsearExcelACategoria(Categoria categoria, Cell columna, String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_NOMBRE:
			String nombre = columna.getStringCellValue().trim();
			if (esStringValido(nombre)) {
				categoria.setNombre(nombre);
			}
			break;
		case COLUMNA_DESCRIPCION:
			String descripcion = columna.getStringCellValue().trim();
			if (esStringValido(descripcion)) {
				categoria.setDescripcion(descripcion);
			}
			break;
		}
	}

	private Boolean esStringValido(String nombre) {
		return !nombre.isBlank();
	}

	private Boolean categoriaEsValida(Categoria categoria) {
		if (categoria.getNombre() != null) {
			return true;
		}
		return false;

	}

	private Categoria agregarModificarCategoria(Categoria categoria) {
		Categoria categoriaExistente = getCategoriaRepositorio().findByNombre(categoria.getNombre());
		if (categoriaExistente != null) {
			categoriaExistente.setDescripcion(categoria.getDescripcion());
			return categoriaExistente;
		}
		return categoria;
	}

	@Override
	public CategoriaDTO getCategoriaDTOPorID(Long id) {
		try {
			Categoria categoria = getCategoriaRepositorio().findByOid(id);
			if (categoria == null) {
				throw new ServiceException("Error al obtener la categoria");
			}
			return CategoriaDTO.entidadADTO(categoria);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la categoria", e);
		}
	}

	@Override
	public CategoriaDTO getCategoriaDTOPorNombre(String nombre) {
		try {
			Categoria categoria = getCategoriaPorNombre(nombre);
			return CategoriaDTO.entidadADTO(categoria);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la categoria", e);
		}
	}

	@Override
	public Categoria getCategoriaPorNombre(String nombre) {
		try {
			Categoria categoria = getCategoriaRepositorio().findByNombre(nombre);
			if (categoria == null) {
				throw new ServiceException("Error al obtener la categoria por nombre " + nombre);
			}
			return categoria;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la categoria " + nombre, e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getCategoriaRepositorio().deleteById(id);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la categoria", e);
		}
	}

	@Override
	public List<CategoriaDTO> listar() {
		try {
			List<Categoria> categoriaList = getCategoriaRepositorio().findAll();
			if (categoriaList == null || categoriaList.size() == 0) {
				throw new ServiceException("Error al obtener la lista de Categorias");
			}
			return CategoriaDTO.entidadDTOLista(categoriaList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la lista de Categorias", e);
		}
	}

	public CategoriaRepositorio getCategoriaRepositorio() {
		return categoriaRepositorio;
	}

	public void setCategoriaRepositorio(CategoriaRepositorio categoriaRepositorio) {
		this.categoriaRepositorio = categoriaRepositorio;
	}

}