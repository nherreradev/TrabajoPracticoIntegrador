package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "POSICION_")
public class Posicion extends ObjetoPersistente {
	/**
	 * Usuario al cual pertenece la posición
	 */

	@Column(name = "USUARIO_OID")
	private Long usuarioOid;

	/**
	 * Si es true indica si es una posición de efectivo (cash) o de titulos (false).
	 */
	@Column(name = "ES_EFECTIVO")
	private Boolean esEfectivo;

	/**
	 * Activo para el cual se refleja la posición, por ejemplo GGAL
	 */
	@Column(name = "INSTRUMENTO_OID")
	private Long instrumentoOid;

	/**
	 * Moneda en el caso de que sea movimiento de cash.
	 */
	@Column(name = "MONEDA_OID")
	private Long monedaOid;

	/**
	 * Fecha de creación de la posición
	 */
	@Column(name = "FECHA_POSICION")
	private LocalDateTime fecha_posicion;

	/**
	 * Cantidad de titulos en posición
	 */
	@Column(name = "CANTIDAD")
	private BigDecimal cantidad;

	@Column(name = "PRECIO_ACTUAL_DE_VENTA")
	private BigDecimal precioActualDeVenta;

	/**
	 * Referencia la OID de la orden
	 */
	@Column(name = "ORDEN_OID")
	private Long ordenOID;

	/**
	 * Campo de texto libre, como texto de referencia.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "SIMBOLO_INSTRUMENTO")
	private String simboloInstrumento;

	@Column(name = "CONCEPTO")
	private String concepto;

	@Column(name = "PRECIO_AL_MOMENTO_DE_COMPRA")
	private BigDecimal precioAlMomentoDeCompra;
	
	@Column(name = "LIQUIDO_EXISTENCIA_DEL_SIMBOLO")
	private Boolean liquidoExistenciaDelSimbolo;

	public Long getUsuarioOid() {
		return usuarioOid;
	}

	public void setUsuarioOid(Long usuarioOid) {
		this.usuarioOid = usuarioOid;
	}

	public Boolean getEsEfectivo() {
		return esEfectivo;
	}

	public void setEsEfectivo(Boolean esEfectivo) {
		this.esEfectivo = esEfectivo;
	}

	public Long getInstrumentoOid() {
		return instrumentoOid;
	}

	public void setInstrumentoOid(Long instrumentoOid) {
		this.instrumentoOid = instrumentoOid;
	}

	public Long getMonedaOid() {
		return monedaOid;
	}

	public void setMonedaOid(Long monedaOid) {
		this.monedaOid = monedaOid;
	}

	public LocalDateTime getFecha_posicion() {
		return fecha_posicion;
	}

	public void setFecha_posicion(LocalDateTime fecha_posicion) {
		this.fecha_posicion = fecha_posicion;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioActualDeVenta() {
		return precioActualDeVenta;
	}

	public void setPrecioActualDeVenta(BigDecimal precioActualDeVenta) {
		this.precioActualDeVenta = precioActualDeVenta;
	}

	public Long getOrdenOID() {
		return ordenOID;
	}

	public void setOrdenOID(Long ordenOID) {
		this.ordenOID = ordenOID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSimboloInstrumento() {
		return simboloInstrumento;
	}

	public void setSimboloInstrumento(String simboloInstrumento) {
		this.simboloInstrumento = simboloInstrumento;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getPrecioAlMomentoDeCompra() {
		return precioAlMomentoDeCompra;
	}

	public void setPrecioAlMomentoDeCompra(BigDecimal precioAlMomentoDeCompra) {
		this.precioAlMomentoDeCompra = precioAlMomentoDeCompra;
	}

	public Boolean liquidoExistenciaDelSimbolo() {
		return liquidoExistenciaDelSimbolo;
	}

	public void setLiquidoExistenciaDelSimbolo(Boolean liquidoExistenciaDelSimbolo) {
		this.liquidoExistenciaDelSimbolo = liquidoExistenciaDelSimbolo;
	}
	
	

}
