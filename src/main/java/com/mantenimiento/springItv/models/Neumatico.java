package com.mantenimiento.springItv.models;

import lombok.Data;

import java.util.Date;

@Data
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
}
