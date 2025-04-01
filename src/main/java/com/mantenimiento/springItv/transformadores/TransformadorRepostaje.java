package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.models.Repostaje;

public class TransformadorRepostaje {
	
	public static Repostaje repostajeEntityToRepostaje(RepostajeEntity repostajeEntity) {
		Repostaje repostaje = new Repostaje();
		
		repostaje.setIdRepostaje(repostajeEntity.getIdRepostaje());
		repostaje.setKmRepostaje(repostajeEntity.getKmRepostaje());
		repostaje.setLitros(repostajeEntity.getLitros());
		repostaje.setPrecio(repostajeEntity.getPrecio());
		repostaje.setPrecioLitro(repostajeEntity.getPrecioLitro());
		repostaje.setFecha(repostajeEntity.getFecha());
		
		return repostaje;
	}

}
