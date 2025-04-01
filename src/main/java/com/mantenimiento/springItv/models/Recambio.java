package com.mantenimiento.springItv.models;

import java.math.BigDecimal;
import java.util.Date;
import com.mantenimiento.springItv.entities.CategoriaEntity;

public class Recambio {

	private int idRecambio;
    private CategoriaEntity categoria;
    private String descripcion;
    private BigDecimal precio;
    private Date fechaCompra;
    
	public int getIdRecambio() {
		return idRecambio;
	}
	public void setIdRecambio(int idRecambio) {
		this.idRecambio = idRecambio;
	}
	public CategoriaEntity getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}	
}
