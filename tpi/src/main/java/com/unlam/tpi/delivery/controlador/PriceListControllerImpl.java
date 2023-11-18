package com.unlam.tpi.delivery.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.interfaces.PriceListController;

@RestController
@RequestMapping("list")
public class PriceListControllerImpl implements PriceListController {

    @Autowired
    private ListaPreciosServicio priceListService;

    @Override
    @PostMapping("/save/precios/{titulo}")
    //El titulo puede ser accion o bono o cedears (
    public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo) throws JsonProcessingException {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiZjdmNGMzNzQtMmVmMi00M2ZkLTg5MDAtOWU4MGMzODcyMDBkIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE3MDAyNzAxNzEsImV4cCI6MTcwMDI3MTA3MSwiaWF0IjoxNzAwMjcwMTcxLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.IEpsTKpjpB3-02Tsv4MkhOESuih0mkw7GUskcB__z3a_Nu0CDNASgxmRTOfkpqygVRoJuU9Qq3x17LBwVISXwq8Tj_4WcUrfNU4U0OA9KDlJPxfh7v7u-9AxsfEhIVWHvqnGNZ2hfOy7rylnv1c6xUMjHfy2DW2NIsXXxS8akjK2mAnecCp7AenB0uaUSCB4sccRzOHCvbgH-YOFGFTdUXd2_L1GzXJ5g7oxCHmt-KBPNufgKPdlR0OMeVz_MrHtGphAAvX-vLKpZAl6csXqvE0xIApz5O9V9xSjz5CE3igcShL8b_F-IUFhpDR2t1MOERGZxFeOC_q6qLaJVGszFg";
        ResponseEntity<String> res = priceListService.guardarListaPrecios(titulo, token);
        if (res == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TOKEN TIME OUT");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transacción guardada con éxito, se guardaron: " + titulo);
    }

    @Override
    @GetMapping("/precios/{titulo}")
    public ResponseEntity<String> ObtenerPrecios(@PathVariable String titulo) {
        String response = priceListService.getListaPrecioMongo(titulo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
