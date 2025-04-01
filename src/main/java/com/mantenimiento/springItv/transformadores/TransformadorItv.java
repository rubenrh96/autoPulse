package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.models.Itv;

public class TransformadorItv {
	
	public static  Itv itvEntityToItv(ItvEntity itvEntity) {
		Itv itv = new Itv();
        
		itv.setApto(itvEntity.isApto());
		itv.setFechaApto(itvEntity.getFechaApto());
		itv.setFechaProximaItv(itvEntity.getFechaProximaItv());
		itv.setIdFactura(itvEntity.getIdFactura());
		itv.setKmRevision(itvEntity.getKmRevision());
		itv.setObservaciones(itvEntity.getObservaciones());
		itv.setPrecio(itvEntity.getPrecio());
        
        return itv;
	}

}
