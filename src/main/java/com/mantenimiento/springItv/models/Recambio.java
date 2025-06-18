package com.mantenimiento.springItv.models;

import java.math.BigDecimal;
import java.util.Date;
import com.mantenimiento.springItv.entities.CategoriaEntity;
import lombok.Data;

@Data
public class Recambio {

	private int idRecambio;
    private CategoriaEntity categoria;
    private String descripcion;
    private BigDecimal precio;
    private Date fechaCompra;
}
