package com.unlam.tpi.controlador;

import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import com.unlam.tpi.interfaces.HistoricoServicio;
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
    public ResponseEntity<String> GuardarHistorico(@RequestBody FechaRequestHistorico fechaRequestHistorico) {
        if (fechaRequestHistorico != null) {
            this.historicoServicio.GuardarHistorico(fechaRequestHistorico, fechaRequestHistorico.getInstrumento());
            return new ResponseEntity<>("Operación completada", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El objeto fechaRequestHistorico es nulo", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/obtener_historico")
    public ResponseEntity<String> GetHistorico(String rango, String instrumento) {
        if (rango == null || instrumento == null){
            this.historicoServicio.GetHistoricoMongo(rango, instrumento);
            return new ResponseEntity<>("Request incorrecto", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Operación completada", HttpStatus.OK);
    }
}
