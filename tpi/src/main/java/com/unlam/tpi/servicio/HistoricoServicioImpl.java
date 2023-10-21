package com.unlam.tpi.servicio;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import com.unlam.tpi.repositorio.HistoricoRepositorio;
import com.unlam.tpi.repositorio.ListaPreciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoServicioImpl implements HistoricoServicio{

    @Autowired
    HistoricoRepositorio historicoRepositorio;

    //1 será un mes,
    //2 serán 3 meses
    //3 serán 6 meses
    @Override
    public String GetHistorico(String rango, String instrumento) {
        List<String> response = null;
        Integer index = null;
        try{
            switch (rango){
                case "mensual":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("mensual", "");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case "trimestral":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("trimestral", "");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case "semestral":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("semestral", "");
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
            }
            return null;
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo"+ e);
            e.printStackTrace();
            return null;
        }
    }

    private Integer DeterminarIndexRandomDelArray(List<String> response) {
        return null;
    }

    @Override
    public void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico) {

    }
}
