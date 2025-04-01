package com.mantenimiento.springItv.entities;

import javax.persistence.*;
import java.io.Serializable;

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

    // Getters y setters

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(Integer tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

