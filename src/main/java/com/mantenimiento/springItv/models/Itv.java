package com.mantenimiento.springItv.models;

import lombok.Data;

import java.util.Date;

@Data
public class Itv {

	private int idFactura;
	private double precio;
	private boolean apto;
	private Date fechaApto;
	private int kmRevision;
	private Date fechaProximaItv;
	private String observaciones;
	private Coche coche;

}
