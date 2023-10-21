package com.unlam.tpi.servicio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.PerfilInversorDTO;
import com.unlam.tpi.dto.TipoNivelConocimiento;
import com.unlam.tpi.dto.TipoPerfilInversor;
import com.unlam.tpi.interfaces.PerfilInversorServicio;
import com.unlam.tpi.modelo.persistente.PerfilInversor;
import com.unlam.tpi.repositorio.PerfilInversorRepositorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class PerfilInversorServicioImpl implements PerfilInversorServicio {

	@Autowired
	private PerfilInversorRepositorio perfilInversorRepositorio;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO perfilInversorDTO) {
		try {
			TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
					perfilInversorDTO.getToleranciaRiesgo());
			perfilInversorDTO.setTipoPerfilSubjetivo(tipoPerfilInversor);
			perfilInversorDTO.setPerfilInversor(tipoPerfilInversor);
			guardar(perfilInversorDTO);
			return perfilInversorDTO;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al calcular el perfil inversor subjetivo", e);
		}
	}

	private TipoPerfilInversor calcularPerfilInversor(Integer horizonteTemporal, Integer toleranciaRiesgo) {
		if (esConservador(horizonteTemporal, toleranciaRiesgo)) {
			return TipoPerfilInversor.CONSERVADOR;
		} else if (esModerado(horizonteTemporal, toleranciaRiesgo)) {
			return TipoPerfilInversor.MODERADO;
		} else {
			return TipoPerfilInversor.AGRESIVO;
		}
	}

	private boolean esModerado(Integer horizonteTemporal, Integer toleranciaRiesgo) {
		return 2 < horizonteTemporal && 
				(toleranciaRiesgo <= 40 && horizonteTemporal <= 4
				|| toleranciaRiesgo <= 35 && horizonteTemporal <= 6 
				|| toleranciaRiesgo <= 28 && horizonteTemporal <= 9
				|| toleranciaRiesgo <= 26 && horizonteTemporal <= 12
				|| toleranciaRiesgo <= 24 && horizonteTemporal <= 18);
	}

	private boolean esConservador(Integer horizonteTemporal, Integer toleranciaRiesgo) {
		return toleranciaRiesgo <= 10 || toleranciaRiesgo <= 24 && horizonteTemporal <= 2
				|| toleranciaRiesgo <= 18 && horizonteTemporal <= 4 
				|| toleranciaRiesgo <= 15 && horizonteTemporal <= 6
				|| toleranciaRiesgo <= 12 && horizonteTemporal <= 9
				|| toleranciaRiesgo <= 11 && horizonteTemporal <= 12;
	}

	@Override
	public PerfilInversorDTO resultadoNivelConocimiento(PerfilInversorDTO perfilInversorDTO) {
		try {
			TipoNivelConocimiento tipoNivelConocimiento = evaluarNivelConocimiento(perfilInversorDTO);
			perfilInversorDTO.setTipoNivelConocimiento(tipoNivelConocimiento);
			guardar(perfilInversorDTO);
			return perfilInversorDTO;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al calcular el nivel de conocimiento", e);
		}
	}

	private TipoNivelConocimiento evaluarNivelConocimiento(PerfilInversorDTO perfilObjetivoDTO) {
		if (perfilObjetivoDTO.getNivelConocimiento() <= 15) {
			return TipoNivelConocimiento.PRINCIPIANTE;
		} else if (perfilObjetivoDTO.getNivelConocimiento() <= 30) {
			return TipoNivelConocimiento.INTERMEDIO;
		} else if (perfilObjetivoDTO.getNivelConocimiento() <= 35) {
			return TipoNivelConocimiento.AVANZADO;
		} else {
			return TipoNivelConocimiento.EXPERTO;
		}
	}

	@Override
	public PerfilInversorDTO resultadoPerfilInversor(PerfilInversorDTO perfilInversorDTO) {
		try {
			TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
					perfilInversorDTO.getResultadoPerfilado());
			perfilInversorDTO.setPerfilInversor(tipoPerfilInversor);
			guardar(perfilInversorDTO);
			return perfilInversorDTO;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al calcular el perfil inversor", e);
		}
	}

	@Override
	public void guardar(PerfilInversorDTO perfilInversorDTO) {
		try {
			PerfilInversor persistente = PerfilInversorDTO.dTOaEntidad(perfilInversorDTO);
			getPerfilInversorRepositorio().save(persistente);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al guardar el perfil inversor", e);
		}
	}

	@Override
	public byte[] obtenerCertificado(String nombreUsuario) throws JRException, SQLException {
		byte[] bytes = null;
		try {
			URL propertiesStream = getClass().getResource("/perfil_inversor.jrxml");
			URL propertiesStreamLogo = getClass().getResource("/MercadoJR-logo.png");
			URL propertiesStreamAgregsivo = getClass().getResource("/cat_agresivo.jpg");
			URL propertiesStreamModerado = getClass().getResource("/cat_moderado.jpg");
			URL propertiesStreamConservador = getClass().getResource("/cat_conservador.jpg");
			JasperReport jasperReport = JasperCompileManager.compileReport(propertiesStream.getFile());
			Map<String, Object> filterMap = new HashMap<>();
			filterMap.put("p_nombre_usuario",nombreUsuario);
			filterMap.put("p_logo_path",propertiesStreamLogo.getFile());
			filterMap.put("p_cat_agresivo_path",propertiesStreamAgregsivo.getFile());
			filterMap.put("p_cat_moderado_path",propertiesStreamModerado.getFile());
			filterMap.put("p_cat_conservador_path",propertiesStreamConservador.getFile());
			JasperFillManager.fillReport(jasperReport, filterMap, getConnection());
			bytes = JasperRunManager.runReportToPdf(jasperReport, filterMap);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al generar el certificado perfil inversor", e);
		}
		return bytes;
	}
	
	@Override
	public PerfilInversorDTO obtener(Long id) {
		try {
			return PerfilInversorDTO.entidadADTO(getPerfilInversorRepositorio().getReferenceById(id));
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al obtener el perfil inversor", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getPerfilInversorRepositorio().deleteById(id);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al borrar el perfil inversor", e);
		}
	}

	@Override
	public List<PerfilInversorDTO> listar() {
		try {
			return PerfilInversorDTO.entidadDTOLista(getPerfilInversorRepositorio().findAll());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al listar Perfil inversor.", e);
		}
	}

	public PerfilInversorRepositorio getPerfilInversorRepositorio() {
		return perfilInversorRepositorio;
	}

	public void setPerfilInversorRepositorio(PerfilInversorRepositorio perfilInversorRepositorio) {
		this.perfilInversorRepositorio = perfilInversorRepositorio;
	}

	private Connection getConnection() throws SQLException {
		Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
				.getService(ConnectionProvider.class).getConnection();
		return connection;
	}
}
