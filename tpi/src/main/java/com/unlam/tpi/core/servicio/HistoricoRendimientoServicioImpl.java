package com.unlam.tpi.core.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoRendimientoRepositorio;
import com.unlam.tpi.core.interfaces.HistoricoRendimientoServicio;
import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;

@Service
@Transactional
public class HistoricoRendimientoServicioImpl implements HistoricoRendimientoServicio {

	@Autowired
	HistoricoRendimientoRepositorio historicoRendimientoRepositorio;

	@Override
	public void guardar(HistoricoRendimientos historicoRendimientos) {
		List<HistoricoRendimientos> historicoRendimientoBuscado = historicoRendimientoRepositorio
				.buscarPorSimboloYFecha(historicoRendimientos.getSimbolo(), historicoRendimientos.getFecha());
		if (historicoRendimientoBuscado != null && historicoRendimientoBuscado.isEmpty()) {
			historicoRendimientoRepositorio.save(historicoRendimientos);
		}
	}

	@Override
	public List<HistoricoRendimientosResponse> obtenerRendimientosHistoricosPorSimbolo(String simboloInstrumento,
			Long usuarioOid) {
		List<HistoricoRendimientosResponse> listaHistoricoRendimientosResponse = new ArrayList<>();
		List<HistoricoRendimientos> listaHistoricoRendimientos = historicoRendimientoRepositorio
				.obtenerRendimientosHistoricosPorSimbolo(simboloInstrumento, usuarioOid);
		if (listaHistoricoRendimientos != null && !listaHistoricoRendimientos.isEmpty()) {
			for (HistoricoRendimientos historicoRendimientos : listaHistoricoRendimientos) {
				HistoricoRendimientosResponse historicoRendimientosResponse = crearHistoricoRendimientoResponse(
						listaHistoricoRendimientosResponse, historicoRendimientos);
				listaHistoricoRendimientosResponse.add(historicoRendimientosResponse);
			}
		}
		return listaHistoricoRendimientosResponse;
	}

	private HistoricoRendimientosResponse crearHistoricoRendimientoResponse(
			List<HistoricoRendimientosResponse> listaHistoricoRendimientosResponse,
			HistoricoRendimientos historicoRendimientos) {
		HistoricoRendimientosResponse historicoRendimientosResponse = new HistoricoRendimientosResponse();
		historicoRendimientosResponse.setSimbolo(historicoRendimientos.getSimbolo());
		historicoRendimientosResponse.setRendimientoTotal(historicoRendimientos.getRendimientoTotal());
		historicoRendimientosResponse
				.setRendimientoTotalPorcentaje(historicoRendimientos.getRendimientoTotalPorcentaje());
		historicoRendimientosResponse.setFecha(historicoRendimientos.getFecha());
		historicoRendimientosResponse.setCantidadDeTitulos(historicoRendimientos.getCantidadDeTitulos());
		historicoRendimientosResponse.setValorInversion(historicoRendimientos.getValorInversion());
		return historicoRendimientosResponse;
	}

}
