package com.mantenimiento.springItv.models;

import java.util.List;

public class Coche {

	private String matricula;
	private String marca;
	private String modelo;
	private String color;
	private int cv;
	private int ano;
	private int kilometros;
	private List<Itv> itvs;
	private List<Mantenimiento> mantenimientos;
	private List<Repostaje> respostajes;
	private List<Neumatico> neumaticos;
	
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getCv() {
		return cv;
	}
	public void setCv(int cv) {
		this.cv = cv;
	}
	
	public int getKilometros() {
		return kilometros;
	}
	public void setKilometros(int kilometros) {
		this.kilometros = kilometros;
	}
	public List<Mantenimiento> getMantenimientos() {
		return mantenimientos;
	}
	public void setMantenimientos(List<Mantenimiento> mantenimientos) {
		this.mantenimientos = mantenimientos;
	}
	public List<Repostaje> getRespostajes() {
		return respostajes;
	}
	public void setRespostajes(List<Repostaje> respostajes) {
		this.respostajes = respostajes;
	}
	public List<Itv> getItvs() {
		return itvs;
	}
	public void setItvs(List<Itv> itvs) {
		this.itvs = itvs;
	}
	public List<Mantenimiento> getMantenimiento() {
		return mantenimientos;
	}
	public void setMantenimiento(List<Mantenimiento> mantenimientos) {
		this.mantenimientos = mantenimientos;
	}
	public List<Repostaje> getRespostaje() {
		return respostajes;
	}
	public void setRespostaje(List<Repostaje> respostajes) {
		this.respostajes = respostajes;
	}
	public List<Neumatico> getNeumaticos() {
		return neumaticos;
	}
	public void setNeumaticos(List<Neumatico> neumaticos) {
		this.neumaticos = neumaticos;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	
	
	
}
