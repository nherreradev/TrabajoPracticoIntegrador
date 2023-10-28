package com.unlam.tpi.modelo.rest;

import java.time.LocalDate;

public class FechaRequestHistorico {
    private LocalDate fecha_desde;
    private LocalDate meses_atras;
    private String instrumento;
    public LocalDate getFecha_desde() { return fecha_desde; }
    public void setFecha_desde(LocalDate fecha_desde) {this.fecha_desde = fecha_desde; }
    public LocalDate getMeses_atras() { return meses_atras; }
    public void setMeses_atras(LocalDate meses_atras) { this.meses_atras = meses_atras; }
    public String getInstrumento() { return instrumento; }
    public void setInstrumento(String instrumento) { this.instrumento = instrumento; }
}
