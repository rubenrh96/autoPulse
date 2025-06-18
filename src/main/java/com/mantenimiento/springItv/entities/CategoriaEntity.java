package com.mantenimiento.springItv.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CATEGORIA")
public class CategoriaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq_gen")
    @SequenceGenerator(name = "categoria_seq_gen", sequenceName = "categoria_seq", allocationSize = 1)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @Column(name = "TIPO_CATEGORIA")
    private Integer tipoCategoria;

    @Column(name = "DESCRIPCION", length = 100)
    private String descripcion;

}

