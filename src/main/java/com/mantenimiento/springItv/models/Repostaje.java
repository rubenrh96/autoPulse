package com.mantenimiento.springItv.models;

import lombok.Data;

import java.util.Date;

@Data
public class Repostaje {
	
	private int idRepostaje;
	private double precio;
	private double litros;
	private double precioLitro;
	private int kmRepostaje;
	private Date fecha;

}
