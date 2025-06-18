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
@Table(name = "NEUMATICO")
public class NeumaticoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neumatico_seq_generator")
	@SequenceGenerator(name = "neumatico_seq_generator", sequenceName = "neumatico_seq", allocationSize = 1)
	@Column(name = "ID_NEUMATICO")
	private int idNeumatico;

    @Column(name = "MARCA", length = 50)
    private String marca;

    @Column(name = "MODELO", length = 50)
    private String modelo;

    @Column(name = "ANCHO_LLANTA")
    private int anchoLlanta;

    @Column(name = "PERFIL_LLANTA")
    private int perfilLlanta;

    @Column(name = "DIAMETRO_LLANTA", length = 5)
    private String diametroLlanta;

    @Column(name = "INDICE_CARGA")
    private int indiceCarga;

    @Column(name = "INDICE_VELOCIDAD", length = 5)
    private String indiceVelocidad;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "KM_MONTAJE")
    private int kmMontaje;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA_MONTAJE")
    private Date fechaMontaje;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "MS")
    private boolean ms;
    
    @Column(name = "NUMERO")
    private int numero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;

}


