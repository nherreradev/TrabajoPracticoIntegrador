package com.unlam.tpi.modelo.persistente;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;
import com.unlam.tpi.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "PUNTAS")
public class Puntas extends ObjetoPersistente {

	@OneToOne
	@JoinColumn(name = "INSTRUMENTO_ID")
	private Instrumento instrumento;

	@Column(name = "CANTIDAD_COMPRA")
	@SerializedName("cantidadCompra")
	private int cantidadCompra;

	@Column(name = "PRECIO_COMPRA")
	@SerializedName("precioCompra")
	private BigDecimal precioCompra;

	@Column(name = "CANTIDAD_VENTA")
	@SerializedName("cantidadVenta")
	private int cantidadVenta;

	@Column(name = "PRECIO_VENTA")
	@SerializedName("precioVenta")
	private BigDecimal precioVenta;

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public int getCantidadCompra() {
		return cantidadCompra;
	}

	public void setCantidadCompra(int cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public int getCantidadVenta() {
		return cantidadVenta;
	}

	public void setCantidadVenta(int cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
}
