package com.mantenimiento.springItv.models;

import lombok.Data;

import java.util.Date;

@Data
public class Mantenimiento {

	private int idFactura;
	private String descripcion;
	private double precio;
	private Date fecha;
	private int kmMantenimiento;
	private boolean pagado;
	private Coche coche;
	private Categoria categoria;
}
