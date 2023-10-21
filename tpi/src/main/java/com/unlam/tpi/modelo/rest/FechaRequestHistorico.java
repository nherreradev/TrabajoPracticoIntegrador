package com.unlam.tpi.modelo.rest;

import java.time.LocalDate;

public class FechaRequestHistorico {
    private LocalDate fecha_desde;
    private int meses_atras;

    public LocalDate getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(LocalDate fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public int getMeses_atras() {
        return meses_atras;
    }

    public void setMeses_atras(int meses_atras) {
        this.meses_atras = meses_atras;
    }
}
