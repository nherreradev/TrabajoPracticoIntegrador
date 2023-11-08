package com.unlam.tpi.core.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.unlam.tpi.core.modelo.HistoricoRendimientos;

public interface HistoricoRendimientoRepositorioCustomizado {
	List<HistoricoRendimientos> buscarPorSimboloYFecha(String simbolo, LocalDate fecha);
}
