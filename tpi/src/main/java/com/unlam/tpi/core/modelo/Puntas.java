package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "PUNTAS")
public class Puntas extends ObjetoPersistente {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "INSTRUMENTO_ID", referencedColumnName = "oid_")
	private Instrumento instrumento;

	@Column(name = "CANTIDAD_COMPRA")
	private int cantidadCompra;

	@Column(name = "PRECIO_COMPRA")
	private BigDecimal precioCompra;

	@Column(name = "CANTIDAD_VENTA")
	private int cantidadVenta;

	@Column(name = "PRECIO_VENTA")
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
