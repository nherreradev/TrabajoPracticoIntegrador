package com.unlam.tpi.controlador;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PriceListController {
    public List<String> MostrarPrecios(@PathVariable String titulo);
}
