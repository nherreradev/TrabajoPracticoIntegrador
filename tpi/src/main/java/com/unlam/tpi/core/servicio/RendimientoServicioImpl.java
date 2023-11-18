package com.unlam.tpi.core.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.RendimientoServicio;
import com.unlam.tpi.core.interfaces.UsuarioServicio;
import com.unlam.tpi.core.modelo.RendimientoActualResponse;
import com.unlam.tpi.core.modelo.Usuario;

@Service
public class RendimientoServicioImpl implements RendimientoServicio {

	@Autowired
	PosicionServicio posicionServicio;

	@Autowired
	UsuarioServicio usuarioServicio;

	@Scheduled(cron = "0 00 17 * * *", zone = "America/Argentina/Buenos_Aires")
	@Override
	public void persistirCierresDiariosTarea() {

		List<Usuario> listaDeUsuarios = usuarioServicio.obtenerTodosLosUsuarios();
		if (listaDeUsuarios != null && !listaDeUsuarios.isEmpty()) {
			for (Usuario usuario : listaDeUsuarios) {
				RendimientoActualResponse rendimientoActualResponse = posicionServicio
						.calcularRendimientoActual(usuario.getOid());
				if (rendimientoActualResponse != null && rendimientoActualResponse.getRendimientosActuales() != null) {
					posicionServicio.guardarCierresDiarios(rendimientoActualResponse.getRendimientosActuales(),
							usuario.getOid());
				}
			}
		}

	}

}
