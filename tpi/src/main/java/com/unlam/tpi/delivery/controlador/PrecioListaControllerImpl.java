package com.unlam.tpi.delivery.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unlam.tpi.core.interfaces.ListaPreciosServicio;
import com.unlam.tpi.core.interfaces.PriceListController;
import com.unlam.tpi.core.modelo.Instrumento;

@RestController
@RequestMapping("list")

public class PrecioListaControllerImpl implements PriceListController {
    @Autowired
    private ListaPreciosServicio listaPreciosServicio;

	@Override
	@PostMapping("/save/precios/{titulo}")
	public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo) throws JsonProcessingException {
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiMjA4Yzc3NGEtMTBhMS00NTNkLWE1NDUtYjExNjQ3ODg1NzcwIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE3MDAyNzEzNDAsImV4cCI6MTcwMDI3MjI0MCwiaWF0IjoxNzAwMjcxMzQwLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.W4udJxc3aYOfi-RiLT53sSI59xr-1jDHIcbrJrlFzuQJNtG0viKvlOioKhjkeanSE62xRpu3SLPIqY9mk-5wUWfrPrZf-UJr1AmRj4EzNpDsJI1Jq3cGaqK4WTGdpFoELZFJPnIFnUl2M19U7piwMOBDk-lPU9N0BjO-CR7cM65ieMSAw8tt3IuHOS1DybzMEMPq8JqnbscR4_34TeU3ZulSa5M_fOKIhYpOPpqdSLcRx4d3XPWMbjxiDMapzyVaRrInZl-1Sg4K5_3pxmR_nhswcTkTJLAphRk-CWZ5tweDN1_N88KX_Muk8yFrAmJkhVBx9Og8ImJ6OKDKAuDAkg";
		listaPreciosServicio.guardarListaPrecios(titulo, token);
		return ResponseEntity.status(HttpStatus.OK).body("Transacción guardada con éxito, se guardaron: " + titulo);
	}

    @Override
    @GetMapping("/precios/{titulo}")
    public ResponseEntity<List<Instrumento>> ObtenerPrecios(@PathVariable String titulo) {
    	List<Instrumento> response = listaPreciosServicio.getListaPrecio(titulo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
