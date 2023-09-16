package com.unlam.tpi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instrumento")
public class Instrumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "SIMBOLO")
	private String simbolo;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "MONEDA")
	private String moneda;

	@Column(name = "PLAZO")
	private String plazo;

	@Column(name = "PRECIO_COMPRA")
	private BigDecimal precioCompra;

	@Column(name = "CANTIDAD_COMPRA")
	private BigDecimal cantidadCompra;

	@Column(name = "PRECIO_VENTA")
	private BigDecimal precioVenta;

	@Column(name = "CANTIDAD_VENTA")
	private BigDecimal cantidadVenta;

	@Column(name = "ULTIMO_OPERADO")
	private BigDecimal ultimoOperado;

	@Column(name = "VARIACION_DIARIA")
	private BigDecimal variacionDiaria;

	@Column(name = "APERTURA")
	private BigDecimal apertura;

	@Column(name = "MINIMO")
	private BigDecimal minimo;

	@Column(name = "MAXIMO")
	private BigDecimal maximo;

	@Column(name = "ULTIMO_CIERRE")
	private BigDecimal ultimoCierre;

	@Column(name = "MONTO_OPERADO")
	private BigDecimal montoOperado;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getCantidadCompra() {
		return cantidadCompra;
	}

	public void setCantidadCompra(BigDecimal cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public BigDecimal getCantidadVenta() {
		return cantidadVenta;
	}

	public void setCantidadVenta(BigDecimal cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}

	public BigDecimal getUltimoOperado() {
		return ultimoOperado;
	}

	public void setUltimoOperado(BigDecimal ultimoOperado) {
		this.ultimoOperado = ultimoOperado;
	}

	public BigDecimal getVariacionDiaria() {
		return variacionDiaria;
	}

	public void setVariacionDiaria(BigDecimal variacionDiaria) {
		this.variacionDiaria = variacionDiaria;
	}

	public BigDecimal getApertura() {
		return apertura;
	}

	public void setApertura(BigDecimal apertura) {
		this.apertura = apertura;
	}

	public BigDecimal getMinimo() {
		return minimo;
	}

	public void setMinimo(BigDecimal minimo) {
		this.minimo = minimo;
	}

	public BigDecimal getMaximo() {
		return maximo;
	}

	public void setMaximo(BigDecimal maximo) {
		this.maximo = maximo;
	}

	public BigDecimal getUltimoCierre() {
		return ultimoCierre;
	}

	public void setUltimoCierre(BigDecimal ultimoCierre) {
		this.ultimoCierre = ultimoCierre;
	}

	public BigDecimal getMontoOperado() {
		return montoOperado;
	}

	public void setMontoOperado(BigDecimal montoOperado) {
		this.montoOperado = montoOperado;
	}

}
