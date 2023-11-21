package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.HistoricoControlador;
import com.unlam.tpi.core.interfaces.HistoricoServicio;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;

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
}