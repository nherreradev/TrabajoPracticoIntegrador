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
import com.unlam.tpi.dto.SeccionDTO;
import com.unlam.tpi.interfaces.SeccionServicio;
import com.unlam.tpi.modelo.persistente.Seccion;
import com.unlam.tpi.repositorio.SeccionRepositorio;

@Service
public class SeccionServicioImpl implements SeccionServicio {

	private static final String COLUMNA_DESCRIPCION = "descripcion";
	private static final String COLUMNA_NOMBRE = "nombre";
	private static final String HOJA_SECCION = "seccion";

	@Autowired
	SeccionRepositorio seccionRepositorio;

	@Override
	public void guardar(SeccionDTO seccion) {
		try {
			Seccion persistente = SeccionDTO.dTOaEntidad(seccion);
			getSeccionRepositorio().save(persistente);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la pregunta", e);
		}
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelSeccion) {
		List<Seccion> listaSeccion = new ArrayList<>();
		Map<Integer, String> encabezado = new HashMap<>();
		XSSFWorkbook libro;
		try {
			libro = new XSSFWorkbook(excelSeccion.getInputStream());
			XSSFSheet hoja = libro.getSheet(HOJA_SECCION);
			if (hoja == null) {
				throw new ServiceException("Error al importar excel verifique que exista la hoja seccion");
			}
			for (Row fila : hoja) {
				Seccion seccion = new Seccion();
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
							convertirExcelASeccion(seccion, columna, nombreColumna);
						}
					}
				}
				if (seccionEsValida(seccion)) {
					listaSeccion.add(agregarModificarSeccion(seccion));
				}
			}
			if (0 < listaSeccion.size()) {
				getSeccionRepositorio().saveAll(listaSeccion);
			}
		} catch (IOException e) {
			throw new ServiceException("Error al leer el excel de secciones", e);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar la seccion", e);
		}
	}

	private void convertirExcelASeccion(Seccion seccion, Cell columna, String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_NOMBRE:
			seccion.setNombre(columna.getStringCellValue());
		case COLUMNA_DESCRIPCION:
			seccion.setDescripcion(columna.getStringCellValue());
		default:
			break;
		}
	}

	private Boolean seccionEsValida(Seccion seccion) {
		if (seccion.getNombre() != null) {
			return true;
		}
		return false;
	}

	private Seccion agregarModificarSeccion(Seccion seccion) {
		Seccion seccionExistente = getSeccionRepositorio().findByNombre(seccion.getNombre());
		if (seccionExistente != null) {
			seccionExistente.setDescripcion(seccion.getDescripcion());
			return seccionExistente;
		}
		return seccion;
	}

	@Override
	public SeccionDTO getSeccionDTOPorID(Long id) {
		try {
			Seccion seccion = getSeccionRepositorio().findByOid(id);
			if (seccion == null) {
				throw new ServiceException("Error al obtener la seccion");
			}
			return SeccionDTO.entidadADTO(seccion);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la seccion", e);
		}
	}

	@Override
	public SeccionDTO getSeccionDTOPorNombre(String nombre) {
		try {
			Seccion seccion = getSeccionPorNombre(nombre);
			return SeccionDTO.entidadADTO(seccion);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la seccion", e);
		}
	}

	@Override
	public Seccion getSeccionPorNombre(String nombre) {
		try {
			Seccion seccion = getSeccionRepositorio().findByNombre(nombre);
			if (seccion == null) {
				throw new ServiceException("Error al obtener la seccion por nombre");
			}
			return seccion;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la seccion", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getSeccionRepositorio().deleteById(id);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la seccion", e);
		}
	}

	@Override
	public List<SeccionDTO> listar() {
		try {
			List<Seccion> seccionList = getSeccionRepositorio().findAll();
			if (seccionList == null || seccionList.size() == 0) {
				throw new ServiceException("Error al obtener la lista de secciones");
			}
			return SeccionDTO.entidadDTOLista(seccionList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar la seccion", e);
		}
	}

	public SeccionRepositorio getSeccionRepositorio() {
		return seccionRepositorio;
	}

	public void setSeccionRepositorio(SeccionRepositorio seccionRepositorio) {
		this.seccionRepositorio = seccionRepositorio;
	}

}
