package com.unlam.tpi.core.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoRendimientos;

public interface HistoricoRendimientoRepositorioCustomizado {

	List<HistoricoRendimientos> buscarPorSimboloYFecha(String simbolo, LocalDateTime fecha);

	List<HistoricoRendimientos> obtenerRendimientosHistoricosPorSimbolo(String token,
			String simboloInstrumento);
}
