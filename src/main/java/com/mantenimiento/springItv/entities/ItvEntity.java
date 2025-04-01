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
@Table(name = "ITV")
public class ItvEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itv_seq_generator")
    @SequenceGenerator(name = "itv_seq_generator", sequenceName = "itv_seq", allocationSize = 1)
    @Column(name = "ID_FACTURA")
    private int idFactura;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "APTO")
    private boolean apto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_APTO")
    private Date fechaApto;

    @Column(name = "KM_REVISION")
    private int kmRevision;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_PROXIMA_ITV")
    private Date fechaProximaItv;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;


	/**
	 * @return the idFactura
	 */
	public int getIdFactura() {
		return idFactura;
	}

	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
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
	 * @return the apto
	 */
	public boolean isApto() {
		return apto;
	}

	/**
	 * @param apto the apto to set
	 */
	public void setApto(boolean apto) {
		this.apto = apto;
	}

	/**
	 * @return the fechaApto
	 */
	public Date getFechaApto() {
		return fechaApto;
	}

	/**
	 * @param fechaApto the fechaApto to set
	 */
	public void setFechaApto(Date fechaApto) {
		this.fechaApto = fechaApto;
	}

	/**
	 * @return the kmRevision
	 */
	public int getKmRevision() {
		return kmRevision;
	}

	/**
	 * @param kmRevision the kmRevision to set
	 */
	public void setKmRevision(int kmRevision) {
		this.kmRevision = kmRevision;
	}

	/**
	 * @return the fechaProximaItv
	 */
	public Date getFechaProximaItv() {
		return fechaProximaItv;
	}

	/**
	 * @param fechaProximaItv the fechaProximaItv to set
	 */
	public void setFechaProximaItv(Date fechaProximaItv) {
		this.fechaProximaItv = fechaProximaItv;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public CocheEntity getCoche() {
		return coche;
	}

	public void setCoche(CocheEntity coche) {
		this.coche = coche;
	}
}
