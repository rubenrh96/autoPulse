package com.mantenimiento.springItv.models;

import java.util.Date;

public class Repostaje {
	
	private int idRepostaje;
	private double precio;
	private double litros;
	private double precioLitro;
	private int kmRepostaje;
	private Date fecha;
	
	public int getIdRepostaje() {
		return idRepostaje;
	}
	public void setIdRepostaje(int idRepostaje) {
		this.idRepostaje = idRepostaje;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getLitros() {
		return litros;
	}
	public void setLitros(double litros) {
		this.litros = litros;
	}
	public double getPrecioLitro() {
		return precioLitro;
	}
	public void setPrecioLitro(double precioLitro) {
		this.precioLitro = precioLitro;
	}
	public int getKmRepostaje() {
		return kmRepostaje;
	}
	public void setKmRepostaje(int kmRepostaje) {
		this.kmRepostaje = kmRepostaje;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
