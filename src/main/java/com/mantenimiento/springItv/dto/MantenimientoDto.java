package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MantenimientoDto {

    private Integer idFactura;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    private double precio;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;           // formato yyyy-MM-dd

    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private int kmMantenimiento;

    private boolean pagado;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    private String categoria;
    private String matricula;
}



