package com.unlam.tpi.core.servicio;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.delivery.dto.PerfilInversorDTO;
import com.unlam.tpi.delivery.dto.TipoNivelConocimiento;
import com.unlam.tpi.delivery.dto.TipoPerfilInversor;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.infraestructura.arquitectura.ServiceException;
import com.unlam.tpi.infraestructura.modelo.PerfilInversor;
import com.unlam.tpi.infraestructura.modelo.Usuario;
import com.unlam.tpi.infraestructura.repositorio.PerfilInversorRepositorio;

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
	private UsuarioServicio usuarioServicio;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO perfilInversorDTO) {
		try {
			validacionPerfilSubjetivo(perfilInversorDTO);
			TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
					perfilInversorDTO.getToleranciaRiesgo());
			PerfilInversor perfilInversor = crearPerfilInversor(perfilInversorDTO, tipoPerfilInversor);
			guardar(perfilInversor);
			return obtenerPorNombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al calcular el perfil inversor subjetivo", e);
		}
	}

	private void validacionPerfilSubjetivo(PerfilInversorDTO perfilInversorDTO) {
		if (perfilInversorDTO.getUsuarioDTO() == null) {
			throw new ServiceException("Error al obtener obtener el usuario");
		}
		if (perfilInversorDTO.getToleranciaRiesgo() == null) {
			throw new ServiceException("Error al obtener la tolerancia al riesgo");
		}
		if (perfilInversorDTO.getHorizonteTemporal() == null) {
			throw new ServiceException("Error al obtener el horizonte temporal");
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
		return 2 < horizonteTemporal && (toleranciaRiesgo <= 40 && horizonteTemporal <= 4
				|| toleranciaRiesgo <= 35 && horizonteTemporal <= 6 || toleranciaRiesgo <= 28 && horizonteTemporal <= 9
				|| toleranciaRiesgo <= 26 && horizonteTemporal <= 12
				|| toleranciaRiesgo <= 24 && horizonteTemporal <= 18);
	}

	private boolean esConservador(Integer horizonteTemporal, Integer toleranciaRiesgo) {
		return toleranciaRiesgo <= 10 || toleranciaRiesgo <= 24 && horizonteTemporal <= 2
				|| toleranciaRiesgo <= 18 && horizonteTemporal <= 4 || toleranciaRiesgo <= 15 && horizonteTemporal <= 6
				|| toleranciaRiesgo <= 12 && horizonteTemporal <= 9
				|| toleranciaRiesgo <= 11 && horizonteTemporal <= 12;
	}

	@Override
	public PerfilInversorDTO resultadoNivelConocimiento(PerfilInversorDTO perfilInversorDTO) {
		try {
			validacionNivelConocimiento(perfilInversorDTO);
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
			validacionNivelConocimiento(perfilInversorDTO);
			TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
					perfilInversorDTO.getResultadoPerfilado());
			PerfilInversor perfilInversor = crearPerfilInversor(perfilInversorDTO, tipoPerfilInversor);
			guardar(perfilInversor);
			return obtenerPorNombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error al calcular el perfil inversor", e);
		}
	}

	private void validacionNivelConocimiento(PerfilInversorDTO perfilInversorDTO) {
		validacionPerfilSubjetivo(perfilInversorDTO);
		if (perfilInversorDTO.getNivelConocimiento() == null) {
			throw new ServiceException("Error al obtener el nivel de conocimiento");
		}
	}

	private PerfilInversor crearPerfilInversor(PerfilInversorDTO perfilInversorDTO,
			TipoPerfilInversor tipoPerfilInversor) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio()
				.findByUsuario_NombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
		if (perfilInversor == null) {
			perfilInversor = new PerfilInversor();
			Usuario usuario = getUsuarioServicio()
					.ObtenerUsuarioPorNombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
			perfilInversor.setUsuario(usuario);
		}
		perfilInversor.setTipoPerfilSubjetivo(tipoPerfilInversor);
		perfilInversor.setPerfilInversor(tipoPerfilInversor);
		if (perfilInversorDTO.getToleranciaRiesgo() != null) {
			perfilInversor.setToleranciaRiesgo(perfilInversorDTO.getToleranciaRiesgo());
		}
		if (perfilInversorDTO.getHorizonteTemporal() != null) {
			perfilInversor.setHorizonteTemporal(perfilInversorDTO.getHorizonteTemporal());
		}
		if (perfilInversorDTO.getNivelConocimiento() != null) {
			perfilInversor.setNivelConocimiento(perfilInversorDTO.getNivelConocimiento());
		}
		return perfilInversor;
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
	public void guardar(PerfilInversor perfilInversor) {
		try {
			getPerfilInversorRepositorio().save(perfilInversor);
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
			filterMap.put("p_nombre_usuario", nombreUsuario);
			filterMap.put("p_logo_path", propertiesStreamLogo.getFile());
			filterMap.put("p_cat_agresivo_path", propertiesStreamAgregsivo.getFile());
			filterMap.put("p_cat_moderado_path", propertiesStreamModerado.getFile());
			filterMap.put("p_cat_conservador_path", propertiesStreamConservador.getFile());
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

	public PerfilInversorDTO obtenerPorNombreUsuario(String nombreUsuario) {
		try {
			PerfilInversor perfilInversor = getPerfilInversorRepositorio().findByUsuario_NombreUsuario(nombreUsuario);
			if (perfilInversor == null) {
				throw new ServiceException("Error al obtener el perfil inversor para el usuario: " + nombreUsuario);
			}
			PerfilInversorDTO perfilInversordto = PerfilInversorDTO.entidadADTO(perfilInversor);
			perfilInversordto.setUsuarioDTO(UsuarioDTO.entidadADTO(perfilInversor.getUsuario()));
			return perfilInversordto;
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

	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

}
