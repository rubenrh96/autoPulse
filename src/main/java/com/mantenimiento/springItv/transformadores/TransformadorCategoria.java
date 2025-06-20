package com.mantenimiento.springItv.transformadores;

import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.models.Categoria;

public class TransformadorCategoria {

    public static Categoria categoriaEntityToCategoria(CategoriaEntity categoriaEntity) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(categoriaEntity.getIdCategoria());
        categoria.setTipoCategoria(categoriaEntity.getTipoCategoria());
        categoria.setDescripcion(categoriaEntity.getDescripcion());

        return categoria;
    }
}
