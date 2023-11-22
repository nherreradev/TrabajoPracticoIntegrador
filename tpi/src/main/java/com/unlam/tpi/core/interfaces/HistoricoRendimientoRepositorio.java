package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.HistoricoRendimientos;

public interface HistoricoRendimientoRepositorio
		extends JpaRepository<HistoricoRendimientos, Long>, HistoricoRendimientoRepositorioCustomizado {

}
