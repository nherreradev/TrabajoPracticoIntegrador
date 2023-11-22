package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.GeneracionJasper;
import com.unlam.tpi.core.interfaces.PerfilInversorRepositorio;
import com.unlam.tpi.core.interfaces.PerfilInversorServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.core.modelo.TipoNivelConocimiento;
import com.unlam.tpi.core.modelo.TipoPerfilInversor;
import com.unlam.tpi.core.modelo.Usuario;

@Service
public class PerfilInversorServicioImpl implements PerfilInversorServicio {

	@Autowired
	private PerfilInversorRepositorio perfilInversorRepositorio;

	@Autowired
	private UsuarioServicio usuarioServicio;

	@Autowired
	private GeneracionJasper generacionJasper;

	@Override
	public PerfilInversor resultadoPerfilSubjetivo(PerfilInversor perfilInversor) {
		validacionPerfilSubjetivo(perfilInversor);
		TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversor.getHorizonteTemporal(),
				perfilInversor.getToleranciaRiesgo());
		PerfilInversor perfilInversorSubjetivo = crearPerfilInversorSubjetivo(perfilInversor, tipoPerfilInversor);
		return perfilInversorSubjetivo;
	}

	private void validacionPerfilSubjetivo(PerfilInversor perfilInversorDTO) {
		if (perfilInversorDTO.getUsuario() == null) {
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
	public PerfilInversor resultadoNivelConocimiento(PerfilInversor perfilInversor) {
		validacionNivelConocimiento(perfilInversor);
		TipoNivelConocimiento tipoNivelConocimiento = obtenerNivelConocimiento(perfilInversor);
		perfilInversor.setTipoNivelConocimiento(tipoNivelConocimiento);
		return guardar(perfilInversor);
	}

	private TipoNivelConocimiento obtenerNivelConocimiento(PerfilInversor perfilObjetivo) {
		if (perfilObjetivo.getNivelConocimiento() <= 15) {
			return TipoNivelConocimiento.PRINCIPIANTE;
		} else if (perfilObjetivo.getNivelConocimiento() <= 30) {
			return TipoNivelConocimiento.INTERMEDIO;
		} else if (perfilObjetivo.getNivelConocimiento() <= 35) {
			return TipoNivelConocimiento.AVANZADO;
		} else {
			return TipoNivelConocimiento.EXPERTO;
		}
	}

	@Override
	public PerfilInversor resultadoPerfilInversor(PerfilInversor perfilInversor) {
		validacionNivelConocimiento(perfilInversor);
		TipoPerfilInversor tipoPerfilInversor = calcularPerfilInversor(perfilInversor.getHorizonteTemporal(),
				calcularPerfilInverso(perfilInversor.getToleranciaRiesgo(), perfilInversor.getNivelConocimiento()));
		TipoNivelConocimiento tipoNivelConocimiento = obtenerNivelConocimiento(perfilInversor);
		PerfilInversor perfilInversorObjetivo = crearPerfilInversorObjetivo(perfilInversor, tipoPerfilInversor,
				tipoNivelConocimiento);
		return perfilInversorObjetivo;
	}

	private void validacionNivelConocimiento(PerfilInversor perfilInversor) {
		validacionPerfilSubjetivo(perfilInversor);
		if (perfilInversor.getNivelConocimiento() == null) {
			throw new ServiceException("Error al obtener el nivel de conocimiento");
		}
	}

	private Integer calcularPerfilInverso(Integer toleranciaRiesgo, Integer nivelConocimiento) {
		if (toleranciaRiesgo != null && nivelConocimiento != null) {
			return (toleranciaRiesgo + nivelConocimiento) / 2;
		}
		return 0;
	}
	
	private PerfilInversor crearPerfilInversorSubjetivo(PerfilInversor perfilInversor,
			TipoPerfilInversor tipoPerfilInversor) {
		PerfilInversor perfilInversorSubjetivo = crearPerfilInversor(perfilInversor, tipoPerfilInversor);
		perfilInversorSubjetivo.setTipoPerfilSubjetivo(tipoPerfilInversor);
		return guardar(perfilInversorSubjetivo);
	}

	private PerfilInversor crearPerfilInversorObjetivo(PerfilInversor perfilInversor,
			TipoPerfilInversor tipoPerfilInversor, TipoNivelConocimiento tipoNivelConocimiento) {
		PerfilInversor perfilInversorObjetivo = crearPerfilInversor(perfilInversor, tipoPerfilInversor);
		perfilInversorObjetivo.setNivelConocimiento(perfilInversor.getNivelConocimiento());
		perfilInversorObjetivo.setTipoNivelConocimiento(tipoNivelConocimiento);
		return guardar(perfilInversor);
	}

	private PerfilInversor crearPerfilInversor(PerfilInversor perfilInversor, TipoPerfilInversor tipoPerfilInversor) {
		PerfilInversor perfilInversorObtenido = obtenerPerfilInversorPorNombreDeUsuario(perfilInversor);
		if (perfilInversorObtenido == null) {
			perfilInversorObtenido = crearNuevoPerfilInversor(perfilInversor);
		}
		perfilInversorObtenido.setPerfilInversor(tipoPerfilInversor);
		perfilInversorObtenido.setToleranciaRiesgo(perfilInversor.getToleranciaRiesgo());
		perfilInversorObtenido.setHorizonteTemporal(perfilInversor.getHorizonteTemporal());
		return perfilInversorObtenido;
	}

	private PerfilInversor obtenerPerfilInversorPorNombreDeUsuario(PerfilInversor perfilInversor) {
		return getPerfilInversorRepositorio()
				.findByUsuario_NombreUsuario(perfilInversor.getUsuario().getNombreUsuario());
	}

	private PerfilInversor crearNuevoPerfilInversor(PerfilInversor perfilInversorDTO) {
		PerfilInversor perfilInversor = new PerfilInversor();
		Usuario usuario = getUsuarioServicio()
				.obtenerUsuarioPorNombreUsuario(perfilInversorDTO.getUsuario().getNombreUsuario());
		perfilInversor.setUsuario(usuario);
		return perfilInversor;
	}

	@Override
	public PerfilInversor guardar(PerfilInversor perfilInversor) {
		return getPerfilInversorRepositorio().save(perfilInversor);
	}

	@Override
	public byte[] obtenerCertificado(String nombreUsuario) {
		return getGeneracionJasper().obtenerCertificado(nombreUsuario);
	}

	@Override
	public PerfilInversor obtener(Long id) {
		return getPerfilInversorRepositorio().getReferenceById(id);
	}

	public PerfilInversor obtenerPorNombreUsuario(String nombreUsuario) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio().findByUsuario_NombreUsuario(nombreUsuario);
		if (perfilInversor == null) {
			throw new ServiceException("Error al obtener el perfil inversor para el usuario: " + nombreUsuario);
		}
		perfilInversor.setUsuario(perfilInversor.getUsuario());
		return perfilInversor;
	}

	@Override
	public void borrar(Long id) {
		getPerfilInversorRepositorio().deleteById(id);
	}

	@Override
	public List<PerfilInversor> listar() {
		return getPerfilInversorRepositorio().findAll();
	}

	public PerfilInversorRepositorio getPerfilInversorRepositorio() {
		return perfilInversorRepositorio;
	}

	public void setPerfilInversorRepositorio(PerfilInversorRepositorio perfilInversorRepositorio) {
		this.perfilInversorRepositorio = perfilInversorRepositorio;
	}

	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public GeneracionJasper getGeneracionJasper() {
		return generacionJasper;
	}

	public void setGeneracionJasper(GeneracionJasper generacionJasper) {
		this.generacionJasper = generacionJasper;
	}

	@Override
	public PerfilInversor listarporUsuario(Long id) {
		PerfilInversor perfilInversor = getPerfilInversorRepositorio().findByUsuario_Oid(id);
		if (perfilInversor == null) {
			return null;
		}
		return perfilInversor;
	}

}