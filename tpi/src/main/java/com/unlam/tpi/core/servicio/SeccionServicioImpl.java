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

import com.unlam.tpi.core.interfaces.SeccionRepositorio;
import com.unlam.tpi.core.interfaces.SeccionServicio;
import com.unlam.tpi.core.modelo.Seccion;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.delivery.dto.SeccionDTO;
import com.unlam.tpi.delivery.dto.SeccionMapper;

@Service
public class SeccionServicioImpl implements SeccionServicio {

	private static final int FILA_ENCABEZADO = 0;
	private static final String COLUMNA_DESCRIPCION = "descripcion";
	private static final String COLUMNA_NOMBRE = "nombre";
	private static final String HOJA_SECCION = "seccion";

	@Autowired
	SeccionRepositorio seccionRepositorio;

	@Override
	public void guardar(SeccionDTO seccion) {
		Seccion persistente = SeccionMapper.dTOaEntidad(seccion);
		getSeccionRepositorio().save(persistente);
	}

	@Override
	public void cargaDesdeExcel(MultipartFile excelPregunta) throws IOException {
		List<Seccion> listaSeccion = new ArrayList<>();
		Map<Integer, String> encabezado = new HashedMap<>();
		XSSFWorkbook libro;
		Seccion seccion;
		libro = new XSSFWorkbook(excelPregunta.getInputStream());
		XSSFSheet hoja = libro.getSheet(HOJA_SECCION);
		if (hoja == null) {
			throw new ServiceException("Error al importar excel verifique que exista la hoja seccion");
		}
		for (Row fila : hoja) {
			seccion = new Seccion();
			leerColumna(encabezado, seccion, fila);
			agregarSeccionALista(listaSeccion, seccion);
		}
		guardarSeccionLista(listaSeccion);
	}

	private void leerColumna(Map<Integer, String> encabezado, Seccion seccion, Row fila) {
		Iterator<Cell> iteadorColumna = fila.iterator();
		while (iteadorColumna.hasNext()) {
			Cell columna = iteadorColumna.next();
			crearEncabezado(encabezado, columna);
			crearSeccion(encabezado, seccion, columna);
		}
	}

	private void crearEncabezado(Map<Integer, String> encabezado, Cell columna) {
		if (esEncabezado(columna) && !columnaEsValida(encabezado, columna)) {
			encabezado.put(columna.getColumnIndex(), columna.getStringCellValue());
		}
	}

	private void crearSeccion(Map<Integer, String> encabezado, Seccion seccion, Cell columna) {
		if (!esEncabezado(columna) && columnaEsValida(encabezado, columna)) {
			String nombreColumna = encabezado.get(columna.getColumnIndex());
			parsearExcelSeccion(seccion, columna, nombreColumna);
		}
	}

	private boolean columnaEsValida(Map<Integer, String> encabezado, Cell columna) {
		return encabezado.containsKey(columna.getColumnIndex());
	}

	private void agregarSeccionALista(List<Seccion> listaSeccion, Seccion seccion) {
		if (seccionEsValida(seccion)) {
			seccion = agregarModificarSeccion(seccion);
			listaSeccion.add(seccion);
		}
	}

	private Boolean esEncabezado(Cell columna) {
		return columna.getRowIndex() == FILA_ENCABEZADO;
	}

	private void guardarSeccionLista(List<Seccion> listaSeccion) {
		if (listaSeccion.isEmpty()) {
			throw new ServiceException("No hay secciones para guardar");
		}
		getSeccionRepositorio().saveAll(listaSeccion);
	}

	private void parsearExcelSeccion(Seccion seccion, Cell columna, String nombreColumna) {
		switch (nombreColumna) {
		case COLUMNA_NOMBRE:
			String nombre = columna.getStringCellValue().trim();
			if (esStringValido(nombre)) {
				seccion.setNombre(nombre);
			}
			break;
		case COLUMNA_DESCRIPCION:
			String descripcion = columna.getStringCellValue().trim();
			if (esStringValido(descripcion)) {
				seccion.setDescripcion(descripcion);
			}
		}
	}

	private Boolean esStringValido(String nombre) {
		return !nombre.isBlank();
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
		Seccion seccion = getSeccionRepositorio().findByOid(id);
		if (seccion == null) {
			throw new ServiceException("Error al obtener la seccion");
		}
		return SeccionMapper.entidadADTO(seccion);
	}

	@Override
	public SeccionDTO getSeccionDTOPorNombre(String nombre) {
		Seccion seccion = getSeccionPorNombre(nombre);
		return SeccionMapper.entidadADTO(seccion);
	}

	@Override
	public Seccion getSeccionPorNombre(String nombre) {
		Seccion seccion = getSeccionRepositorio().findByNombre(nombre);
		if (seccion == null) {
			throw new ServiceException("Error al obtener la seccion por nombre");
		}
		return seccion;
	}

	@Override
	public void borrar(Long id) {
		getSeccionRepositorio().deleteById(id);
	}

	@Override
	public List<SeccionDTO> listar() {
		List<Seccion> seccionList = getSeccionRepositorio().findAll();
		if (seccionList == null || seccionList.size() == 0) {
			throw new ServiceException("Error al obtener la lista de secciones");
		}
		return SeccionMapper.entidadDTOLista(seccionList);
	}

	public SeccionRepositorio getSeccionRepositorio() {
		return seccionRepositorio;
	}

	public void setSeccionRepositorio(SeccionRepositorio seccionRepositorio) {
		this.seccionRepositorio = seccionRepositorio;
	}

}
