package com.unlam.tpi.delivery.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unlam.tpi.core.interfaces.ListaPreciosServicio;

@RestController
@RequestMapping("list")
public class PriceListControllerImpl implements PriceListController {

    @Autowired
    private ListaPreciosServicio priceListService;

    @Override
    @PostMapping("/save/precios/{titulo}")
    //El titulo puede ser accion o bono (
    public ResponseEntity<String> GuardarPrecios(@PathVariable String titulo) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiNzExYmMwODQtZTZmZS00M2M1LWIzOWMtZjUyMjBlY2MyYjVjIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE2OTgxNzE5MTksImV4cCI6MTY5ODE3MjgxOSwiaWF0IjoxNjk4MTcxOTE5LCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.M-95EL6myNMuo0SpJTBfBPXnd0IzGMPp7qZUy10HbcL9zx5VtkS5BF3B-lnrtLU9jd0lrFEwKWD809d-dhn9GRpgDUrb49O85IZ3UQDKnXVASPV6Deon1QFyZ3-n7YSAZkU1SuBkd8B44FiC2V3CyaQy9xwFp4XsYwd1ktYDagdqILtTR36ZbYNrMen_CvWjV4ytEpNcLlLf85gf-n9R2U9S6JSkDJEjBzsBXF-h2fKqAmlHPaQZoR99Ki67oMqK2HNFjwM8NTwg_3j3rVyY9igGxeQgk8S0NdiGtCZOAoXBctaVr8_ZFUbx_35PWCtonG6oU_Qtjt82fhK9njB_zQ";
                //"wLuSMjekHltq4Ui00WJchp8dsM137cWYb7UIRV5Pyb2a6XJyyH-ac8qeFv8U0W2jksW0Rg7SOqKBNoGNGxvWX63PGcX-msal5IvMRFPW0l0QVzPKrXHQyAupH42KP92QE8qjzWNFahmUOr69CLlyVvA0yGOarrMd-owWXEgoR9dqTMn4V-5amaOgNJO08Z7s9cdxV4YqQnkSQvlqLV1RUVvX6tV0P7gE4d_A62TQLI8rn1p0-xoYfrsmEtcBn1OWyBvxwRwITUpXPH4Mcad5B1iwkxtrYIlG8MoIEs9zeQl8o8rxAGhCWtDedE3EGU1Rk_S03Ve9B2uT-ldaGagZoWCNa18csfWW5b_XEztvwHUxZ6_IcoHpXEDv4LbI-neajyXTdGP2Ed6nC8hjF_hDtc5S4TscbVDZrO_wCLccdN4";
        ResponseEntity<String> res = priceListService.SavePriceList(titulo, token);
        if (res == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TOKEN TIME OUT");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transacción guardada con éxito, se guardaron: " + titulo);
    }

    @Override
    @GetMapping("/precios/{titulo}")
    public ResponseEntity<String> ObtenerPrecios(@PathVariable String titulo) {
        String response = priceListService.GetPriceListMongo(titulo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
