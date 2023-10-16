package com.unlam.tpi.servicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.CategoriaDTO;
import com.unlam.tpi.interfaces.CategoriaServicio;
import com.unlam.tpi.modelo.persistente.Categoria;
import com.unlam.tpi.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

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
	public void cargaDesdeExcel(MultipartFile excelCategoria) {
		List<Categoria> listaCategoria = new ArrayList<>();
		Map<Integer, String> encabezado = new HashMap<>();
		XSSFWorkbook libro;
		try {
			libro = new XSSFWorkbook(excelCategoria.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_CATEGORIA);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja categoria");
			}
			for (Row fila : hoja) {
				Categoria categoria = new Categoria();
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
							switch (nombreColumna) {
							case COLUMNA_NOMBRE:
								categoria.setNombre(columna.getStringCellValue());
							case COLUMNA_DESCRIPCION:
								categoria.setDescripcion(columna.getStringCellValue());
							default:
								break;
							}
						}
					}
				}
				if (categoria.getNombre() != null && categoria.getDescripcion() != null) {
					listaCategoria.add(categoria);
				}
			}
			if (0 < listaCategoria.size()) {
				getCategoriaRepositorio().saveAll(listaCategoria);
			}
		} catch (IOException e) {
			throw new ServiceException("Error al leer el excel de categorias", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la categoria", e);
		}
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
				throw new ServiceException("Error al obtener la categoria por nombre "+nombre);
			}
			return categoria;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la categoria " +nombre, e);
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
