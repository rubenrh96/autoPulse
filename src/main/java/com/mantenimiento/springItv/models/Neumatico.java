package com.mantenimiento.springItv.models;

import java.util.Date;

public class Neumatico {

	private int idNeumatico;
	private String marca;
	private String modelo;
	private int anchoLlanta;
	private int perfilLlanta;
	private String diametroLlanta;
	private int indiceCarga;
	private String indiceVelocidad;
	private double precio;
	private int kmMontaje;
	private Date fechaMontaje;
	private String descripcion; //añadir informacion de como se ha hecho el montaje
	private boolean ms;
	private int numero;
	/**
	 * @return the idNeumatico
	 */
	public int getIdNeumatico() {
		return idNeumatico;
	}
	/**
	 * @param idNeumatico the idNeumatico to set
	 */
	public void setIdNeumatico(int idNeumatico) {
		this.idNeumatico = idNeumatico;
	}
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	/**
	 * @return the anchoLlanta
	 */
	public int getAnchoLlanta() {
		return anchoLlanta;
	}
	/**
	 * @param anchoLlanta the anchoLlanta to set
	 */
	public void setAnchoLlanta(int anchoLlanta) {
		this.anchoLlanta = anchoLlanta;
	}
	/**
	 * @return the perfilLlanta
	 */
	public int getPerfilLlanta() {
		return perfilLlanta;
	}
	/**
	 * @param perfilLlanta the perfilLlanta to set
	 */
	public void setPerfilLlanta(int perfilLlanta) {
		this.perfilLlanta = perfilLlanta;
	}
	/**
	 * @return the diametroLlanta
	 */
	public String getDiametroLlanta() {
		return diametroLlanta;
	}
	/**
	 * @param diametroLlanta the diametroLlanta to set
	 */
	public void setDiametroLlanta(String diametroLlanta) {
		this.diametroLlanta = diametroLlanta;
	}
	/**
	 * @return the indiceCarga
	 */
	public int getIndiceCarga() {
		return indiceCarga;
	}
	/**
	 * @param indiceCarga the indiceCarga to set
	 */
	public void setIndiceCarga(int indiceCarga) {
		this.indiceCarga = indiceCarga;
	}
	/**
	 * @return the indiceVelocidad
	 */
	public String getIndiceVelocidad() {
		return indiceVelocidad;
	}
	/**
	 * @param indiceVelocidad the indiceVelocidad to set
	 */
	public void setIndiceVelocidad(String indiceVelocidad) {
		this.indiceVelocidad = indiceVelocidad;
	}
	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * @return the kmMontaje
	 */
	public int getKmMontaje() {
		return kmMontaje;
	}
	/**
	 * @param kmMontaje the kmMontaje to set
	 */
	public void setKmMontaje(int kmMontaje) {
		this.kmMontaje = kmMontaje;
	}
	/**
	 * @return the fechamontaje
	 */
	public Date getFechaMontaje() {
		return fechaMontaje;
	}
	/**
	 * @param fechamontaje the fechamontaje to set
	 */
	public void setFechaMontaje(Date fechaMontaje) {
		this.fechaMontaje = fechaMontaje;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the ms
	 */
	public boolean isMs() {
		return ms;
	}
	/**
	 * @param ms the ms to set
	 */
	public void setMs(boolean ms) {
		this.ms = ms;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	
}
