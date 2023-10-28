package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoricoRepositorioImpl implements HistoricoRepositorio{
    @Override
    public List<String> GetInstrumentoPorRangoFechaSinId(String rango, String instrumento) {
        return null;
    }

    @Override
    public void GuardarHistoricoInstrumento(String rango, String instrumento) {

    }
}
