package com.unlam.tpi.delivery.controlador;

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
    public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzY4Mjg3IiwiSUQiOiIxNzY4Mjg3IiwianRpIjoiNDRmZmIwOGItZjI4Yy00Y2QyLTk5M2UtMTQyZGVkOWZhMWRjIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE2OTk0OTcxMTEsImV4cCI6MTY5OTQ5ODAxMSwiaWF0IjoxNjk5NDk3MTExLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.PV1XIN54CHBf-xCd5MgXKilSX_lR40cLfbfdj-sB3iEGh0PmQg1Mvp9abzSDpSrfoXFJ7WvRiU_yQwTL5sCV7n2-ihyyHDuyPhewNoZDa_DFeFUvbC5sIXOCPuNrnBTlRHXYXsd3k2kbC5A2FAQZW7zi1q8yR4CW_tM0POUFRxs3v83mrPJeI9fWGC1OPmHkhkbi0qvn-i6fsY_CmlPdC4OpoSEwLR9Y3Wg46vn6KqDczz8Ll-ovejw55cv4mZYnaqZyQ9tKZvgJlMFnKrO4WCzflu0VoZJ9r-5sE6mH8pmDV7zqJWCYzNy32CptBEArpVf47njUZMLX0KhE4xsXNg";
                //"wLuSMjekHltq4Ui00WJchp8dsM137cWYb7UIRV5Pyb2a6XJyyH-ac8qeFv8U0W2jksW0Rg7SOqKBNoGNGxvWX63PGcX-msal5IvMRFPW0l0QVzPKrXHQyAupH42KP92QE8qjzWNFahmUOr69CLlyVvA0yGOarrMd-owWXEgoR9dqTMn4V-5amaOgNJO08Z7s9cdxV4YqQnkSQvlqLV1RUVvX6tV0P7gE4d_A62TQLI8rn1p0-xoYfrsmEtcBn1OWyBvxwRwITUpXPH4Mcad5B1iwkxtrYIlG8MoIEs9zeQl8o8rxAGhCWtDedE3EGU1Rk_S03Ve9B2uT-ldaGagZoWCNa18csfWW5b_XEztvwHUxZ6_IcoHpXEDv4LbI-neajyXTdGP2Ed6nC8hjF_hDtc5S4TscbVDZrO_wCLccdN4";
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
