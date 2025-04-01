package com.mantenimiento.springItv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.List;

@Entity
@Table(name = "COCHE")
public class CocheEntity {

    @Id
    @Column(name = "MATRICULA")
    private String matricula;

    @Column(name = "MARCA")
    private String marca;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "CV")
    private int cv;
    
    @Column(name = "ANO")
    private int ano;
    
    @Column(name = "KILOMETROS")
    private int kilometros;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItvEntity> itvs;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MantenimientoEntity> mantenimientos;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepostajeEntity> repostajes;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NeumaticoEntity> neumaticos;

    // Getters y setters
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getKilometros() {
		return kilometros;
	}

	public void setKilometros(int kilometros) {
		this.kilometros = kilometros;
	}

	public List<ItvEntity> getItvs() {
		return itvs;
	}

	public void setItvs(List<ItvEntity> itvs) {
		this.itvs = itvs;
	}

	public List<MantenimientoEntity> getMantenimientos() {
		return mantenimientos;
	}

	public void setMantenimientos(List<MantenimientoEntity> mantenimientos) {
		this.mantenimientos = mantenimientos;
	}

	public List<RepostajeEntity> getRepostajes() {
		return repostajes;
	}

	public void setRepostajes(List<RepostajeEntity> repostajes) {
		this.repostajes = repostajes;
	}

	public List<NeumaticoEntity> getNeumaticos() {
		return neumaticos;
	}

	public void setNeumaticos(List<NeumaticoEntity> neumaticos) {
		this.neumaticos = neumaticos;
	}   
}
