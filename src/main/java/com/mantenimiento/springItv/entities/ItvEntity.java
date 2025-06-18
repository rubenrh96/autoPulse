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

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ITV")
public class ItvEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itv_seq_generator")
    @SequenceGenerator(name = "itv_seq_generator", sequenceName = "itv_seq", allocationSize = 1)
    @Column(name = "ID_FACTURA")
    private int idFactura;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "APTO")
    private boolean apto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_APTO")
    private Date fechaApto;

    @Column(name = "KM_REVISION")
    private int kmRevision;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_PROXIMA_ITV")
    private Date fechaProximaItv;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;

}
