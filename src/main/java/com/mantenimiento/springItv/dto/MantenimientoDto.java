package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDto {

    private Integer idFactura;
    private String descripcion;
    private double precio;
    private String fecha;           // formato yyyy-MM-dd
    private int kmMantenimiento;
    private boolean pagado;
    private Long idCategoria;
    private String categoria;
    private String matricula;
}



