package com.unlam.tpi.infraestructura.jasper;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.GeneracionJasper;
import com.unlam.tpi.core.modelo.ServiceException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class GeneracionJasperImpl implements GeneracionJasper {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public byte[] obtenerCertificado(String nombreUsuario) {
		try {
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
		} catch (Exception e) {
			throw new ServiceException("Error al generar el certificado para el usaurio: " + nombreUsuario, e);
		}
	}

	private Connection getConnection() throws SQLException {
		Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry()
				.getService(ConnectionProvider.class).getConnection();
		return connection;
	}
}
