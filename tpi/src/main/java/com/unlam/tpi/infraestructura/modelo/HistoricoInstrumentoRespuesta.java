package com.unlam.tpi.infraestructura.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class HistoricoInstrumentoRespuesta {

	@JsonProperty("tiempo")
	private String tiempo;
	@JsonProperty("precioDeApertura")
	private String precioDeApertura;
	@JsonProperty("maximo")
	private String maximo;
	@JsonProperty("minimo")
	private String minimo;
	@JsonProperty("precioDeCierre")
	private String precioDeCierre;

	@JsonProperty("tiempo")
	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	@JsonProperty("precioDeApertura")
	public String getPrecioDeApertura() {
		return precioDeApertura;
	}

	public void setPrecioDeApertura(String precioDeApertura) {
		this.precioDeApertura = precioDeApertura;
	}

	@JsonProperty("maximo")
	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	@JsonProperty("minimo")
	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	@JsonProperty("precioDeCierre")
	public String getPrecioDeCierre() {
		return precioDeCierre;
	}

	public void setPrecioDeCierre(String precioDeCierre) {
		this.precioDeCierre = precioDeCierre;
	}

}
