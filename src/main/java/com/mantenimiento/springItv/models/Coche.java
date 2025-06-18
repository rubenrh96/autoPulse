package com.mantenimiento.springItv.models;

import lombok.Data;

import java.util.List;

@Data
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

}
