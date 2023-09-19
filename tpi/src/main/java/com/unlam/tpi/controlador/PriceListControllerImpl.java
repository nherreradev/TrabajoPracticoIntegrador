package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.listaPreciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("list")
public class PriceListControllerImpl implements PriceListController {

    @Autowired
    private listaPreciosServicio priceListService;

    @Override
    @GetMapping("/precios/{titulo}")
    //El titulo puede ser accion o bono (
    public List<String>  MostrarPrecios(@PathVariable String titulo) {
        String token = "";
        List<String> res = priceListService.GetPriceList(titulo, token);
        return res;
    }
}
