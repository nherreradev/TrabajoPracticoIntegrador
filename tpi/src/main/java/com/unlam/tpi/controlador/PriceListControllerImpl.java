package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.listaPreciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("list")
public class PriceListControllerImpl implements PriceListController {

    @Autowired
    private listaPreciosServicio priceListService;

    @Override
    @GetMapping("/precios/{titulo}")
    //El titulo puede ser accion o bono (
    public ResponseEntity<String> MostrarPrecios(@PathVariable String titulo) {
        String token = "xgZFQogI8OlKPDb_35CtqTBXCCbWIcaJyUmWzmgkw7qzHrORAZP96eeexXQ95QXXPlngA3yEvzBcSrV8IomRIj2DrCbE-6ukR42KkzfFfTSWiPMgRWDq21eMawh7vx6POwskM4b2e3b5Xi1a0iNN5xABK8aKtLd6Ma5ksQ8Y0yyUqv6zFPboqW9mQ1Fq814UzxIO7NEJrsL3_b_MPEOIbH5xkaS0y9gd1SMxaAZaDSsJtUNrzYRVFHaWkJUFssYvYWR1ACOK7Vjtm9lvsvkezwjaRpna65UJCpViQbCX1xuBgFmRFd55BjgD4IfTJJLm0yGdTun1XXuKLeRXoy6mJxO0hv67m6WqXhqj_HNsI38KUdEvn8xappbWMExcCzUOX1EvDgq_6vtkmZSx7Vpr61Y268HDLZ-xIrCiIqGz5Hw";
        ResponseEntity<String> res = priceListService.GetPriceList(titulo, token);
        return res;
    }
}
