package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecambioDto {

    private Integer idRecambio;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotBlank(message = "La fecha de compra es obligatoria")
    private String fechaCompra; // yyyy-MM-dd

    @NotBlank(message = "La cantidad es obligatoria")
    private String cantidad;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    private String categoria;
}



