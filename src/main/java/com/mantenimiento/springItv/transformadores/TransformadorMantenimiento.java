package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.models.Categoria;
import com.mantenimiento.springItv.models.Coche;
import com.mantenimiento.springItv.models.Mantenimiento;

public class TransformadorMantenimiento {

	public static  Mantenimiento mantenimientoEntityToMantenimiento(MantenimientoEntity mantenimientoEntity) {
        Mantenimiento mantenimiento = new Mantenimiento();
        
        mantenimiento.setDescripcion(mantenimientoEntity.getDescripcion());
        mantenimiento.setFecha(mantenimientoEntity.getFecha());
        mantenimiento.setIdFactura(mantenimientoEntity.getIdFactura());
        mantenimiento.setKmMantenimiento(mantenimientoEntity.getKmMantenimiento());
        mantenimiento.setPagado(mantenimiento.isPagado());
        mantenimiento.setPrecio(mantenimientoEntity.getPrecio());

        if (mantenimientoEntity.getCoche() != null) {
            Coche cocheModel = TransformadorCoche.cocheEntityToCoche(mantenimientoEntity.getCoche());
            mantenimiento.setCoche(cocheModel);
        }

        if (mantenimientoEntity.getCategoria() != null) {
            Categoria categoriaModel = TransformadorCategoria.categoriaEntityToCategoria(mantenimientoEntity.getCategoria());
            mantenimiento.setCategoria(categoriaModel);
        }
        return mantenimiento;
	}  
}
