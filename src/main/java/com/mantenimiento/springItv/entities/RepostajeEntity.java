package com.mantenimiento.springItv.entities;

import java.util.Date;

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

@Entity
@Table(name = "REPOSTAJE")
public class RepostajeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repostaje_seq_generator")
	@SequenceGenerator(name = "repostaje_seq_generator", sequenceName = "repostaje_seq", allocationSize = 1)
	@Column(name = "ID_REPOSTAJE")
	private int idRepostaje;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "LITROS")
    private double litros;

    @Column(name = "PRECIO_LITRO")
    private double precioLitro;

    @Column(name = "KM_REPOSTAJE")
    private int kmRepostaje;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA")
    private Date fecha;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;

 // Getters y setters
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

	public CocheEntity getCoche() {
		return coche;
	}

	public void setCoche(CocheEntity coche) {
		this.coche = coche;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
}

