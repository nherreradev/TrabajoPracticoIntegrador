package com.unlam.tpi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "instrumento")
public class Instrumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "SIMBOLO")
	@SerializedName("simbolo")
	private String simbolo;
	


	@Column(name = "ULTIMO_PRECIO")
	@SerializedName("ultimoPrecio")
	private BigDecimal ultimoPrecio;

	@Column(name = "VALOR_PORCENTUAL")
	@SerializedName("variacionPorcentual")
	private BigDecimal variacionPorcentual;

	@Column(name = "APERTURA")
	@SerializedName("apertura")
	private BigDecimal apertura;

	@Column(name = "MAXIMO")
	@SerializedName("maximo")
	private BigDecimal maximo;

	@Column(name = "MINIMO")
	@SerializedName("minimo")
	private BigDecimal minimo;

	@Column(name = "ULTIMO_CIERRE")
	@SerializedName("ultimoCierre")
	private BigDecimal ultimoCierre;

	@Column(name = "VOLUMEN")
	@SerializedName("volumen")
	private BigDecimal volumen;

	@Column(name = "CANTIDAD_OPERACIONES")
	@SerializedName("cantidadOperaciones")
	private int cantidadOperaciones;

	@Column(name = "FECHA")
	@SerializedName("fecha")
	private String fecha;

	@Column(name = "TIPO_OPCION")
	@SerializedName("tipoOpcion")
	private String tipoOpcion;

	@Column(name = "PRECIO_EJERCICIO")
	@SerializedName("precioEjercicio")
	private BigDecimal precioEjercicio;

	@Column(name = "FECHA_VENCIMIENTO")
	@SerializedName("fechaVencimiento")
	private String fechaVencimiento;

	@Column(name = "MERCADO")
	@SerializedName("mercado")
	private String mercado;

	@Column(name = "MONEDA")
	@SerializedName("moneda")
	private String moneda;

	@Column(name = "DESCRIPCION")
	@SerializedName("descripcion")
	private String descripcion;

	@Column(name = "PLAZO")
	@SerializedName("plazo")
	private String plazo;

	@Column(name = "LAMINA_MINIMA")
	@SerializedName("laminaMinima")
	private int laminaMinima;

	@Column(name = "LOTE")
	@SerializedName("lote")
	private int lote;

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



	public BigDecimal getUltimoPrecio() {
		return ultimoPrecio;
	}

	public void setUltimoPrecio(BigDecimal ultimoPrecio) {
		this.ultimoPrecio = ultimoPrecio;
	}

	public BigDecimal getVariacionPorcentual() {
		return variacionPorcentual;
	}

	public void setVariacionPorcentual(BigDecimal variacionPorcentual) {
		this.variacionPorcentual = variacionPorcentual;
	}

	public BigDecimal getApertura() {
		return apertura;
	}

	public void setApertura(BigDecimal apertura) {
		this.apertura = apertura;
	}

	public BigDecimal getMaximo() {
		return maximo;
	}

	public void setMaximo(BigDecimal maximo) {
		this.maximo = maximo;
	}

	public BigDecimal getMinimo() {
		return minimo;
	}

	public void setMinimo(BigDecimal minimo) {
		this.minimo = minimo;
	}

	public BigDecimal getUltimoCierre() {
		return ultimoCierre;
	}

	public void setUltimoCierre(BigDecimal ultimoCierre) {
		this.ultimoCierre = ultimoCierre;
	}

	public BigDecimal getVolumen() {
		return volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public int getCantidadOperaciones() {
		return cantidadOperaciones;
	}

	public void setCantidadOperaciones(int cantidadOperaciones) {
		this.cantidadOperaciones = cantidadOperaciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipoOpcion() {
		return tipoOpcion;
	}

	public void setTipoOpcion(String tipoOpcion) {
		this.tipoOpcion = tipoOpcion;
	}

	public BigDecimal getPrecioEjercicio() {
		return precioEjercicio;
	}

	public void setPrecioEjercicio(BigDecimal precioEjercicio) {
		this.precioEjercicio = precioEjercicio;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getMercado() {
		return mercado;
	}

	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public int getLaminaMinima() {
		return laminaMinima;
	}

	public void setLaminaMinima(int laminaMinima) {
		this.laminaMinima = laminaMinima;
	}

	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}

}
