package com.mantenimiento.springItv.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Entity
@Table(name = "RECAMBIO")
public class RecambioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recambio_seq_generator")
    @SequenceGenerator(name = "recambio_seq_generator", sequenceName = "recambio_seq", allocationSize = 1)
    @Column(name = "ID_RECAMBIO")
    private int idRecambio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    private CategoriaEntity categoria;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO")
    private BigDecimal precio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_COMPRA")
    private Date fechaCompra;
    
    @Column(name = "CANTIDAD")
    private String cantidad;

    @ManyToOne
    @JoinColumn(name = "USUARIO", referencedColumnName = "id")
    private UsuarioEntity usuario;
}

