package com.mantenimiento.springItv.models;

import java.util.Date;

public class Itv {

	private int idFactura;
	private double precio;
	private boolean apto;
	private Date fechaApto;
	private int kmRevision;
	private Date fechaProximaItv;
	private String observaciones;
	private Coche coche;
	
	
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isApto() {
		return apto;
	}
	public void setApto(boolean apto) {
		this.apto = apto;
	}
	public Date getFechaApto() {
		return fechaApto;
	}
	public void setFechaApto(Date fechaApto) {
		this.fechaApto = fechaApto;
	}
	public int getKmRevision() {
		return kmRevision;
	}
	public void setKmRevision(int kmRevision) {
		this.kmRevision = kmRevision;
	}
	public Date getFechaProximaItv() {
		return fechaProximaItv;
	}
	public void setFechaProximaItv(Date fechaProximaItv) {
		this.fechaProximaItv = fechaProximaItv;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Coche getCoche() {
		return coche;
	}
	public void setCoche(Coche coche) {
		this.coche = coche;
	}
}
