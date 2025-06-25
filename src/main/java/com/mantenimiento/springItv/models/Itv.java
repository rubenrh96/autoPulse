package com.mantenimiento.springItv.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Itv {

	private int idFactura;
	private double precio;
	private boolean apto;
	private Date fechaApto;
	private int kmRevision;
	private LocalDate fechaProximaItv;
	private String observaciones;
	private Coche coche;

}
