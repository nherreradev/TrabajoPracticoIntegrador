package com.unlam.tpi.core.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.HistoricoRendimientos;
import com.unlam.tpi.core.modelo.HistoricoRendimientosResponse;

public interface HistoricoRendimientoRepositorio
		extends JpaRepository<HistoricoRendimientos, Long>, HistoricoRendimientoRepositorioCustomizado {

}
