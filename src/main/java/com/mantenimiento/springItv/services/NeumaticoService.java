package com.mantenimiento.springItv.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.repositories.NeumaticoRepository;

@Service
public class NeumaticoService {

	@Autowired
    private NeumaticoRepository neumaticoRepository;

    public NeumaticoEntity guardarNeumatico(NeumaticoEntity neumatico) {
        return neumaticoRepository.save(neumatico);
    }

	public List<NeumaticoEntity> listarNeumaticos(String matricula){
		return neumaticoRepository.findByCocheMatricula(matricula);
	}

	public void eliminarNeumatico(Integer neumatico) {
		neumaticoRepository.deleteById(neumatico);
	}

	public Optional<NeumaticoEntity> obtenerPorId(Integer id) {
		return neumaticoRepository.findById(id);
	}

	public List<NeumaticoEntity> listarTodos(){return neumaticoRepository.findAll();}

}
