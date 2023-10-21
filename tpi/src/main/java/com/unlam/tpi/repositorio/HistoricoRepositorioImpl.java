package com.unlam.tpi.repositorio;

import com.unlam.tpi.servicio.HistoricoServicio;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoricoRepositorioImpl implements HistoricoRepositorio{
    @Override
    public List<String> GetInstrumentoPorRangoFechaSinId(String rango, String instrumento) {
        return null;
    }
}
