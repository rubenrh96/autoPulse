package com.mantenimiento.springItv.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COCHE")
public class CocheEntity {

    @Id
    @Column(name = "MATRICULA")
    private String matricula;

    @Column(name = "MARCA")
    private String marca;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "CV")
    private int cv;
    
    @Column(name = "ANO")
    private int ano;
    
    @Column(name = "KILOMETROS")
    private int kilometros;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ItvEntity> itvs;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MantenimientoEntity> mantenimientos;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RepostajeEntity> repostajes;

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NeumaticoEntity> neumaticos;

}
