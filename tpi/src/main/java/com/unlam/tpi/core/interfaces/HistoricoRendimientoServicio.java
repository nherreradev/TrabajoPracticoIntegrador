package com.unlam.tpi.core.interfaces;

import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;

public interface HistoricoRendimientoServicio {

	void guardar(HistoricoRendimientos historicoRendimiento);

	List<HistoricoRendimientosResponse> obtenerRendimientosHistoricosPorSimbolo(String simboloInstrumento,
			Long usuarioOid);

}
