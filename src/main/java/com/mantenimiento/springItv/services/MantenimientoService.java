package com.mantenimiento.springItv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import com.mantenimiento.springItv.dto.CostePorCategoriaDto;
import com.mantenimiento.springItv.repositories.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.repositories.MantenimientoRepository;

@Service
public class MantenimientoService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
    private MantenimientoRepository mantenimientoRepository;

	@Autowired
	private CocheRepository cocheRepository;
	
    public MantenimientoEntity guardarMantenimiento(MantenimientoEntity mantenimiento) {
        MantenimientoEntity mantenimientoGuardado = mantenimientoRepository.save(mantenimiento);
		if (mantenimientoGuardado.getCoche() != null) {
			cocheRepository.actualizarKilometraje(
					mantenimientoGuardado.getCoche().getMatricula(),
					mantenimientoGuardado.getKmMantenimiento()
			);
		}
        return mantenimientoGuardado;
    }
	
	public List<MantenimientoEntity> listarMantenimientos(String matricula){
		List<MantenimientoEntity> listaMantenimiento = mantenimientoRepository.findAll();
		List<MantenimientoEntity> listaMantenimientoCoche = new ArrayList<>();
		for (MantenimientoEntity mantenimientoEntity : listaMantenimiento) {
			if(mantenimientoEntity.getCoche()!=null && mantenimientoEntity.getCoche().getMatricula().equals(matricula)) {
				listaMantenimientoCoche.add(mantenimientoEntity);
			}
		}
		return listaMantenimientoCoche;
	}
	
	public Optional<MantenimientoEntity> obtenerPorId(Integer id){
		return mantenimientoRepository.findById(id);
	}
	
	public void eliminarMantenimiento(Integer neumatico) {
		mantenimientoRepository.deleteById(neumatico);
	}
	

	public Object buscarMantenimientosConFiltros(String matricula, Long idCategoria, Double precioDesde, Double precioHasta, Integer ano) {
	    return mantenimientoRepository.findAll((Specification<MantenimientoEntity>) (root, query, criteriaBuilder) -> {
	        List<Predicate> predicates = new ArrayList<>();
	        
	        if (matricula != null && !matricula.isEmpty()) {
	            predicates.add(criteriaBuilder.equal(root.join("coche").get("matricula"), matricula));
	        }

	        if (precioDesde != null && precioDesde > 0) {
	            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioDesde));
	        }
	        if (precioHasta != null && precioHasta > 0) {
	            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioHasta));
	        }

	        if (idCategoria != null) {
	            predicates.add(criteriaBuilder.equal(root.join("categoria").get("idCategoria"), idCategoria));
	        }

	        if (ano != null && ano > 0) {
	            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("fecha")), ano));
	        }
	        
	        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	    });
	}

	public List<MantenimientoEntity> listarTodos(){return mantenimientoRepository.findAll();}

	public Map<String,Object> costePorCategoria(String username) {
		List<CostePorCategoriaDto> lista = mantenimientoRepository.findCostePorCategoria(username);

		// ► labels
		List<String> labels = lista.stream()
				.map(CostePorCategoriaDto::getCategoria)
				.toList();

		// ► data
		List<Double> data   = lista.stream()
				.map(CostePorCategoriaDto::getTotal)
				.toList();

		return Map.of("labels", labels, "data", data);
	}
}
