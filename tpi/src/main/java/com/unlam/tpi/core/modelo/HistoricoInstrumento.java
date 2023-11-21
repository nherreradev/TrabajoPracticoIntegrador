package com.unlam.tpi.core.modelo;

public class HistoricoInstrumento {

	private String tiempo;
	private String precioDeApertura;
	private String maximo;
	private String minimo;
	private String precioDeCierre;

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getPrecioDeApertura() {
		return precioDeApertura;
	}

	public void setPrecioDeApertura(String precioDeApertura) {
		this.precioDeApertura = precioDeApertura;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getPrecioDeCierre() {
		return precioDeCierre;
	}

	public void setPrecioDeCierre(String precioDeCierre) {
		this.precioDeCierre = precioDeCierre;
	}

}
