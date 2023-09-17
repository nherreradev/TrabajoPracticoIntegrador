package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.listaPreciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("list")
public class listaPreciosControladorImpl implements listaPreciosControlador {

    @Autowired
    private listaPreciosServicio priceListService;

    @Override
    @GetMapping("/precios")
    public String MostrarPrecios() {
        List<String> res = priceListService.getListaPrecios();
        return null;
    }
}
