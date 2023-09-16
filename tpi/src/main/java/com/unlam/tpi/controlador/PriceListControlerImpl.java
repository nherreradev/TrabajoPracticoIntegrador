package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("list")
public class PriceListControlerImpl implements PriceListController {

    @Autowired
    private PriceListService priceListService;

    @Override
    @GetMapping("/precios")
    public String MostrarPrecios() {
        List<String> res = priceListService.GetPriceList();
        return null;
    }
}
