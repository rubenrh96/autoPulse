package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.models.Neumatico;

public class TransformadorNeumatico {

    public static Neumatico neumaticoEntityToNeumatico(NeumaticoEntity neumaticoEntity) {
        Neumatico neumatico = new Neumatico();
        
        neumatico.setAnchoLlanta(neumaticoEntity.getAnchoLlanta());
        neumatico.setDescripcion(neumaticoEntity.getDescripcion());
        neumatico.setDiametroLlanta(neumaticoEntity.getDiametroLlanta());
        neumatico.setFechaMontaje(neumaticoEntity.getFechaMontaje());
        neumatico.setIndiceCarga(neumaticoEntity.getIndiceCarga());
        neumatico.setIndiceVelocidad(neumaticoEntity.getIndiceVelocidad());
        neumatico.setKmMontaje(neumaticoEntity.getKmMontaje());
        neumatico.setMarca(neumaticoEntity.getMarca());
        neumatico.setModelo(neumaticoEntity.getModelo());
        neumatico.setMs(neumaticoEntity.isMs());
        neumatico.setPerfilLlanta(neumaticoEntity.getPerfilLlanta());
        neumatico.setPrecio(neumaticoEntity.getPrecio());
        neumatico.setIdNeumatico(neumaticoEntity.getIdNeumatico());
        neumatico.setNumero(neumaticoEntity.getNumero());
        
        return neumatico;
    }
}
