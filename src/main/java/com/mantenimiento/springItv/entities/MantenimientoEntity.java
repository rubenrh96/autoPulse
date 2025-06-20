package com.mantenimiento.springItv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MANTENIMIENTO")
@NamedQueries({@NamedQuery(name = "Mantenimiento.buscarPorMatriculaYCategoria",
                query = "SELECT m.coche.matricula, SUM(m.precio) FROM MantenimientoEntity m JOIN m.categoria c "
                		+ "WHERE m.coche.matricula = :matricula AND c.idCategoria IN (:idCategoria) "
                		+ "GROUP BY m.coche.matricula")
})
public class MantenimientoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mantenimiento_seq_generator")
	@SequenceGenerator(name = "mantenimiento_seq_generator", sequenceName = "mantenimiento_seq", allocationSize = 1)
	@Column(name = "ID_FACTURA")
	private int idFactura;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "PRECIO")
    private double precio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "KM_MANTENIMIENTO")
    private int kmMantenimiento;

    @Column(name = "PAGADO")
    private boolean pagado;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    private CategoriaEntity categoria;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATRICULA", referencedColumnName = "MATRICULA")
    private CocheEntity coche;

}

