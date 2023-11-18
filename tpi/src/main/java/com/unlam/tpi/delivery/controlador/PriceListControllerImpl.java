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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiMjA4Yzc3NGEtMTBhMS00NTNkLWE1NDUtYjExNjQ3ODg1NzcwIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE3MDAyNzEzNDAsImV4cCI6MTcwMDI3MjI0MCwiaWF0IjoxNzAwMjcxMzQwLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.W4udJxc3aYOfi-RiLT53sSI59xr-1jDHIcbrJrlFzuQJNtG0viKvlOioKhjkeanSE62xRpu3SLPIqY9mk-5wUWfrPrZf-UJr1AmRj4EzNpDsJI1Jq3cGaqK4WTGdpFoELZFJPnIFnUl2M19U7piwMOBDk-lPU9N0BjO-CR7cM65ieMSAw8tt3IuHOS1DybzMEMPq8JqnbscR4_34TeU3ZulSa5M_fOKIhYpOPpqdSLcRx4d3XPWMbjxiDMapzyVaRrInZl-1Sg4K5_3pxmR_nhswcTkTJLAphRk-CWZ5tweDN1_N88KX_Muk8yFrAmJkhVBx9Og8ImJ6OKDKAuDAkg";
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
