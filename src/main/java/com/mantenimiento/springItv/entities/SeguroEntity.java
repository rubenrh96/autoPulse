package com.mantenimiento.springItv.entities;

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

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "SEGURO")
public class SeguroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seguro_seq_generator")
    @SequenceGenerator(name = "seguro_seq_generator", sequenceName = "seguro_seq", allocationSize = 1)
    @Column(name = "ID_SEGURO")
    private int idSeguro;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "COMPANIA")
    private String compania;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_RENOVACION")
    private LocalDate fechaRenovacion;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;

}
