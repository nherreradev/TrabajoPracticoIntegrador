package com.unlam.tpi.core.servicio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unlam.tpi.core.interfaces.HistoricoRendimientoRepositorio;
import com.unlam.tpi.core.interfaces.HistoricoRendimientoServicio;
import com.unlam.tpi.core.modelo.HistoricoRendimientos;

@Service
@Transactional
public class HistoricoRendimientoServicioImpl implements HistoricoRendimientoServicio {

	@Autowired
	HistoricoRendimientoRepositorio historicoRendimientoRepositorio;

	@Override
	public void guardar(HistoricoRendimientos historicoRendimientos) {

		List<HistoricoRendimientos> historicoRendimientoBuscado = historicoRendimientoRepositorio
				.buscarPorSimboloYFecha(historicoRendimientos.getSimbolo(),
						historicoRendimientos.getFecha());
		

		if (historicoRendimientoBuscado != null && historicoRendimientoBuscado.isEmpty()) {
			historicoRendimientoRepositorio.save(historicoRendimientos);
		}

	}

}
