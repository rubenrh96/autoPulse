package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.models.Coche;

public class TransformadorCoche {

    public static Coche cocheEntityToCoche(CocheEntity cocheEntity) {
        Coche coche = new Coche();
        coche.setMatricula(cocheEntity.getMatricula());
        coche.setMarca(cocheEntity.getMarca());
        coche.setModelo(cocheEntity.getModelo());
        coche.setColor(cocheEntity.getColor());
        coche.setCv(cocheEntity.getCv());
        coche.setAno(cocheEntity.getAno());
        coche.setKilometros(cocheEntity.getKilometros());
        
        return coche;
    }
}
