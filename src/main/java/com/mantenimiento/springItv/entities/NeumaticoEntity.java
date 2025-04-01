package com.mantenimiento.springItv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "NEUMATICO")
public class NeumaticoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neumatico_seq_generator")
	@SequenceGenerator(name = "neumatico_seq_generator", sequenceName = "neumatico_seq", allocationSize = 1)
	@Column(name = "ID_NEUMATICO")
	private int idNeumatico;

    @Column(name = "MARCA", length = 50)
    private String marca;

    @Column(name = "MODELO", length = 50)
    private String modelo;

    @Column(name = "ANCHO_LLANTA")
    private int anchoLlanta;

    @Column(name = "PERFIL_LLANTA")
    private int perfilLlanta;

    @Column(name = "DIAMETRO_LLANTA", length = 5)
    private String diametroLlanta;

    @Column(name = "INDICE_CARGA")
    private int indiceCarga;

    @Column(name = "INDICE_VELOCIDAD", length = 5)
    private String indiceVelocidad;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "KM_MONTAJE")
    private int kmMontaje;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_MONTAJE")
    private Date fechaMontaje;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "MS")
    private boolean ms;
    
    @Column(name = "NUMERO")
    private int numero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;
    
 // Getters y setters
	public int getIdNeumatico() {
		return idNeumatico;
	}

	public void setIdNeumatico(int idNeumatico) {
		this.idNeumatico = idNeumatico;
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

	public int getAnchoLlanta() {
		return anchoLlanta;
	}

	public void setAnchoLlanta(int anchoLlanta) {
		this.anchoLlanta = anchoLlanta;
	}

	public int getPerfilLlanta() {
		return perfilLlanta;
	}

	public void setPerfilLlanta(int perfilLlanta) {
		this.perfilLlanta = perfilLlanta;
	}

	public String getDiametroLlanta() {
		return diametroLlanta;
	}

	public void setDiametroLlanta(String diametroLlanta) {
		this.diametroLlanta = diametroLlanta;
	}

	public int getIndiceCarga() {
		return indiceCarga;
	}

	public void setIndiceCarga(int indiceCarga) {
		this.indiceCarga = indiceCarga;
	}

	public String getIndiceVelocidad() {
		return indiceVelocidad;
	}

	public void setIndiceVelocidad(String indiceVelocidad) {
		this.indiceVelocidad = indiceVelocidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getKmMontaje() {
		return kmMontaje;
	}

	public void setKmMontaje(int kmMontaje) {
		this.kmMontaje = kmMontaje;
	}

	public Date getFechaMontaje() {
		return fechaMontaje;
	}

	public void setFechaMontaje(Date fechaMontaje) {
		this.fechaMontaje = fechaMontaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isMs() {
		return ms;
	}

	public void setMs(boolean ms) {
		this.ms = ms;
	}

	public CocheEntity getCoche() {
		return coche;
	}

	public void setCoche(CocheEntity coche) {
		this.coche = coche;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
}


