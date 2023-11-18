package com.unlam.tpi.delivery.controlador;

import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.modelo.HistoricoRequestGET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historico")
public class HistoricoControladorImpl implements HistoricoControlador{

    @Autowired
    HistoricoServicio historicoServicio;

    @Override
    @PostMapping("/guardar")
    public ResponseEntity<String> guardarHistorico(@RequestBody FechaRequestHistorico fechaRequestHistorico) {
        if (fechaRequestHistorico != null) {
            this.historicoServicio.guardarHistorico(fechaRequestHistorico, fechaRequestHistorico.getInstrumento());
            return new ResponseEntity<>("Operación completada", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El objeto fechaRequestHistorico es nulo", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/obtener_historico")
    public ResponseEntity<String> getHistorico(@RequestBody HistoricoRequestGET historicoRequestGET) {
        if (historicoRequestGET.getRango() == null || historicoRequestGET.getInstrumento() == null){

            return new ResponseEntity<>("Request incorrecto", HttpStatus.BAD_REQUEST);
        }
        String response = this.historicoServicio.getHistoricoMongo(historicoRequestGET.getRango(), historicoRequestGET.getInstrumento());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
