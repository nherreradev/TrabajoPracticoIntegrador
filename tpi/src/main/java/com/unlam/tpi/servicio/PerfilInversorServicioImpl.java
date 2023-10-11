package com.unlam.tpi.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.arquitectura.ServiceException;
import com.unlam.tpi.dto.PerfilInversorDTO;
import com.unlam.tpi.enums.TipoNivelConocimiento;
import com.unlam.tpi.enums.TipoPerfilInversor;
import com.unlam.tpi.helpers.TraductorGenerico;
import com.unlam.tpi.modelo.persistente.PerfilInversor;
import com.unlam.tpi.repositorio.PerfilInversorRepositorio;

@Service
public class PerfilInversorServicioImpl implements PerfilInversorServicio {

	@Autowired
	private PerfilInversorRepositorio perfilInversorRepositorio;

	@Override
	public PerfilInversorDTO resultadoPerfilSubjetivo(PerfilInversorDTO perfilInversorDTO) {
		try {
			TipoPerfilInversor tipoPerfilInversor = evaluarPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
					perfilInversorDTO.getToleranciaRiesgo());
			perfilInversorDTO.setTipoPerfilSubjetivo(tipoPerfilInversor);
			perfilInversorDTO.setPerfilInversor(tipoPerfilInversor);
			guardar(perfilInversorDTO);
			return perfilInversorDTO;
		} catch (ServiceException e) {
			throw e;
		}catch (Exception e) {
			throw new ServiceException("Error al calcular el perfil inversor subjetivo", e);
		}
	}

	private TipoPerfilInversor evaluarPerfilInversor(Integer horizonteTemporal, Integer toleranciaRiesgo) {
		if ((horizonteTemporal <= 4 && toleranciaRiesgo <= 18) || (horizonteTemporal <= 6 && toleranciaRiesgo <= 15)
				|| (horizonteTemporal <= 9 && toleranciaRiesgo <= 12)
				|| (horizonteTemporal <= 12 && toleranciaRiesgo <= 11)
				|| (horizonteTemporal <= 18 && toleranciaRiesgo <= 10)) {
			return TipoPerfilInversor.CONSERVADOR;
		} else if ((horizonteTemporal <= 4 && toleranciaRiesgo <= 40)
				|| (horizonteTemporal <= 6 && toleranciaRiesgo <= 35)
				|| (horizonteTemporal <= 9 && toleranciaRiesgo <= 28)
				|| (horizonteTemporal <= 12 && toleranciaRiesgo <= 26)
				|| (horizonteTemporal <= 18 && toleranciaRiesgo <= 24)) {
			return TipoPerfilInversor.MODERADO;
		} else
			return TipoPerfilInversor.AGRESIVO;
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
		if (perfilObjetivoDTO.getNivelConocimiento() <= 10) {
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
			TipoPerfilInversor tipoPerfilInversor = evaluarPerfilInversor(perfilInversorDTO.getHorizonteTemporal(),
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
			PerfilInversor persistente = TraductorGenerico.traductorDeDTOaEntidad(perfilInversorDTO, PerfilInversor.class);
			getPerfilInversorRepositorio().save(persistente);
		} catch (Exception e) {
			throw new ServiceException("Error al guardar el perfil inversor", e);
		}
	}

	@Override
	public PerfilInversorDTO obtener(Long id) {
		try {
			return TraductorGenerico.traductorDeEntidadaDTO(getPerfilInversorRepositorio().getReferenceById(id),
					PerfilInversorDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al obtener el perfil inversor", e);
		}
	}

	@Override
	public void borrar(Long id) {
		try {
			getPerfilInversorRepositorio().deleteById(id);
		} catch (Exception e) {
			throw new ServiceException("Error al borrar el perfil inversor", e);
		}
	}

	@Override
	public List<PerfilInversorDTO> listar() {
		try {
			return TraductorGenerico.traductorDeListaEntidadaDTO(getPerfilInversorRepositorio().findAll(),
					PerfilInversorDTO.class);
		} catch (Exception e) {
			throw new ServiceException("Error al listar las preguntas", e);
		}
	}

	public PerfilInversorRepositorio getPerfilInversorRepositorio() {
		return perfilInversorRepositorio;
	}

	public void setPerfilInversorRepositorio(PerfilInversorRepositorio perfilInversorRepositorio) {
		this.perfilInversorRepositorio = perfilInversorRepositorio;
	}

}
