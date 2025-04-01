package com.mantenimiento.springItv.models;

public class Categoria {

	private Long idCategoria;
	private int tipoCategoria;
	private String descripcion;
	
	
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public int getTipoCategoria() {
		return tipoCategoria;
	}
	public void setTipoCategoria(int tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
