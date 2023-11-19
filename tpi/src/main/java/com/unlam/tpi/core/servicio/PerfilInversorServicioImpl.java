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

import com.unlam.tpi.core.interfaces.PerfilInversorRepositorio;
import com.unlam.tpi.core.interfaces.PerfilInversorServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.PerfilInversorDTO;
import com.unlam.tpi.delivery.dto.PerfilInversorMapper;
import com.unlam.tpi.delivery.dto.TipoNivelConocimiento;
import com.unlam.tpi.delivery.dto.TipoPerfilInversor;
import com.unlam.tpi.delivery.dto.UsuarioMapper;

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
		validacionPerfilSubjetivo(perfilInversorDTO);
		TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
				perfilInversorDTO.getToleranciaRiesgo());
		PerfilInversor perfilInversor = crearPerfilInversorSubjetivo(perfilInversorDTO, tipoPerfilInversor);
		return PerfilInversorMapper.entidadADTO(perfilInversor);
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
		validacionNivelConocimiento(perfilInversorDTO);
		TipoNivelConocimiento tipoNivelConocimiento = obtenerNivelConocimiento(perfilInversorDTO);
		perfilInversorDTO.setTipoNivelConocimiento(tipoNivelConocimiento);
		guardar(perfilInversorDTO);
		return perfilInversorDTO;
	}

	private TipoNivelConocimiento obtenerNivelConocimiento(PerfilInversorDTO perfilObjetivoDTO) {
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
		validacionNivelConocimiento(perfilInversorDTO);
		TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
				perfilInversorDTO.getResultadoPerfilado());
		TipoNivelConocimiento tipoNivelConocimiento = obtenerNivelConocimiento(perfilInversorDTO);
		PerfilInversor perfilInversor = crearPerfilInversorObjetivo(perfilInversorDTO, tipoPerfilInversor,
				tipoNivelConocimiento);
		return PerfilInversorMapper.entidadADTO(perfilInversor);
	}

	private void validacionNivelConocimiento(PerfilInversorDTO perfilInversorDTO) {
		validacionPerfilSubjetivo(perfilInversorDTO);
		if (perfilInversorDTO.getNivelConocimiento() == null) {
			throw new ServiceException("Error al obtener el nivel de conocimiento");
		}
	}

	private PerfilInversor crearPerfilInversorSubjetivo(PerfilInversorDTO perfilInversorDTO,
			TipoPerfilInversor tipoPerfilInversor) {
		PerfilInversor perfilInversor = crearPerfilInversor(perfilInversorDTO, tipoPerfilInversor);
		perfilInversor.setTipoPerfilSubjetivo(tipoPerfilInversor);
		perfilInversor = guardar(perfilInversor);
		return perfilInversor;
	}

	private PerfilInversor crearPerfilInversorObjetivo(PerfilInversorDTO perfilInversorDTO,
			TipoPerfilInversor tipoPerfilInversor, TipoNivelConocimiento tipoNivelConocimiento) {
		PerfilInversor perfilInversor = crearPerfilInversor(perfilInversorDTO, tipoPerfilInversor);
		perfilInversor.setNivelConocimiento(perfilInversorDTO.getNivelConocimiento());
		perfilInversor.setTipoNivelConocimiento(tipoNivelConocimiento);
		perfilInversor = guardar(perfilInversor);
		return perfilInversor;
	}

	private PerfilInversor crearPerfilInversor(PerfilInversorDTO perfilInversorDTO,
			TipoPerfilInversor tipoPerfilInversor) {
		PerfilInversor perfilInversor = obtenerPerfilInversorPorNombreDeUsuario(perfilInversorDTO);
		if (perfilInversor == null) {
			perfilInversor = crearNuevoPerfilInversor(perfilInversorDTO);
		}
		perfilInversor.setPerfilInversor(tipoPerfilInversor);
		perfilInversor.setToleranciaRiesgo(perfilInversorDTO.getToleranciaRiesgo());
		perfilInversor.setHorizonteTemporal(perfilInversorDTO.getHorizonteTemporal());
		return perfilInversor;
	}

	private PerfilInversor obtenerPerfilInversorPorNombreDeUsuario(PerfilInversorDTO perfilInversorDTO) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio()
				.findByUsuario_NombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
		return perfilInversor;
	}

	private PerfilInversor crearNuevoPerfilInversor(PerfilInversorDTO perfilInversorDTO) {
		PerfilInversor perfilInversor = new PerfilInversor();
		Usuario usuario = getUsuarioServicio()
				.obtenerUsuarioPorNombreUsuario(perfilInversorDTO.getUsuarioDTO().getNombreUsuario());
		perfilInversor.setUsuario(usuario);
		return perfilInversor;
	}

	@Override
	public void guardar(PerfilInversorDTO perfilInversorDTO) {
		PerfilInversor persistente = PerfilInversorMapper.dTOaEntidad(perfilInversorDTO);
		getPerfilInversorRepositorio().save(persistente);
	}

	@Override
	public PerfilInversor guardar(PerfilInversor perfilInversor) {
		return getPerfilInversorRepositorio().save(perfilInversor);
	}

	@Override
	public byte[] obtenerCertificado(String nombreUsuario) throws JRException, SQLException {

		byte[] bytes = null;
		URL propertiesStream = getClass().getResource("/jasper/perfil_inversor.jrxml");
		URL propertiesStreamLogo = getClass().getResource("/jasper/MercadoJR-logo.png");
		URL propertiesStreamAgregsivo = getClass().getResource("/jasper/cat_agresivo.jpg");
		URL propertiesStreamModerado = getClass().getResource("/jasper/cat_moderado.jpg");
		URL propertiesStreamConservador = getClass().getResource("/jasper/cat_conservador.jpg");
		JasperReport jasperReport = JasperCompileManager.compileReport(propertiesStream.getFile());
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("p_nombre_usuario", nombreUsuario);
		filterMap.put("p_logo_path", propertiesStreamLogo.getFile());
		filterMap.put("p_cat_agresivo_path", propertiesStreamAgregsivo.getFile());
		filterMap.put("p_cat_moderado_path", propertiesStreamModerado.getFile());
		filterMap.put("p_cat_conservador_path", propertiesStreamConservador.getFile());
		JasperFillManager.fillReport(jasperReport, filterMap, getConnection());
		bytes = JasperRunManager.runReportToPdf(jasperReport, filterMap);
		return bytes;
	}

	@Override
	public PerfilInversorDTO obtener(Long id) {
		return PerfilInversorMapper.entidadADTO(getPerfilInversorRepositorio().getReferenceById(id));
	}

	public PerfilInversorDTO obtenerPorNombreUsuario(String nombreUsuario) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio().findByUsuario_NombreUsuario(nombreUsuario);
		if (perfilInversor == null) {
			throw new ServiceException("Error al obtener el perfil inversor para el usuario: " + nombreUsuario);
		}
		PerfilInversorDTO perfilInversordto = PerfilInversorMapper.entidadADTO(perfilInversor);
		perfilInversordto.setUsuarioDTO(UsuarioMapper.entidadADTO(perfilInversor.getUsuario()));
		return perfilInversordto;
	}

	@Override
	public void borrar(Long id) {
		getPerfilInversorRepositorio().deleteById(id);
	}

	@Override
	public List<PerfilInversorDTO> listar() {
		return PerfilInversorMapper.entidadDTOLista(getPerfilInversorRepositorio().findAll());
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

	@Override
	public PerfilInversorDTO listarporUsuario(Long id) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio().findByUsuario_Oid(id);
		if (perfilInversor == null) {
			return null;
		}
		return PerfilInversorMapper.entidadADTO(perfilInversor);
	}

}