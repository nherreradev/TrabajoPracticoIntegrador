package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "INSTRUMENTO")
public class Instrumento extends ObjetoPersistente {

	private static final long serialVersionUID = 1L;

	@Column(name = "SIMBOLO")
	private String simbolo;

	@Column(name = "CATEGORIA_INSTRUMENTO")
	private String categoriaInstrumento;

	@Column(name = "CATEGORIA_PERFIL")
	private String categoriaPerfil;

	@OneToOne(mappedBy = "instrumento", cascade = CascadeType.ALL)
	private Puntas puntas;

	@Column(name = "ULTIMO_PRECIO")
	private BigDecimal ultimoPrecio;

	@Column(name = "VARIACION_PORCENTUAL")
	private BigDecimal variacionPorcentual;

	@Column(name = "APERTURA")
	private BigDecimal apertura;

	@Column(name = "MAXIMO")
	private BigDecimal maximo;

	@Column(name = "MINIMO")
	private BigDecimal minimo;

	@Column(name = "ULTIMO_CIERRE")
	private BigDecimal ultimoCierre;

	@Column(name = "VOLUMEN")
	private BigDecimal volumen;

	@Column(name = "CANTIDAD_OPERACIONES")
	private int cantidadOperaciones;

	@Column(name = "FECHA")
	private String fecha;

	@Column(name = "TIPO_OPCION")
	private String tipoOpcion;

	@Column(name = "PRECIO_EJERCICIO")
	private BigDecimal precioEjercicio;

	@Column(name = "FECHA_VENCIMIENTO")
	private String fechaVencimiento;

	@Column(name = "MERCADO")
	private String mercado;

	@Column(name = "MONEDA")
	private String moneda;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "PLAZO")
	private String plazo;

	@Column(name = "LAMINA_MINIMA")
	private int laminaMinima;

	@Column(name = "LOTE")
	private int lote;

	private int flashCompra = 0;
	private int flashVenta = 0;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getCategoriaInstrumento() {
		return categoriaInstrumento;
	}

	public void setCategoriaInstrumento(String categoriaInstrumento) {
		this.categoriaInstrumento = categoriaInstrumento;
	}

	public String getCategoriaPerfil() {
		return categoriaPerfil;
	}

	public void setCategoriaPerfil(String categoriaPerfil) {
		this.categoriaPerfil = categoriaPerfil;
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

	public Puntas getPuntas() {
		return puntas;
	}

	public void setPuntas(Puntas puntas) {
		this.puntas = puntas;
	}

	public int getFlashCompra() {
		return flashCompra;
	}

	public void setFlashCompra(int flashCompra) {
		this.flashCompra = flashCompra;
	}

	public int getFlashVenta() {
		return flashVenta;
	}

	public void setFlashVenta(int flashVenta) {
		this.flashVenta = flashVenta;
	}

}
