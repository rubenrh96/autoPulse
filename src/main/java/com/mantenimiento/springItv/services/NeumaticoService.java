package com.mantenimiento.springItv.services;

import java.util.ArrayList;
import java.util.List;
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
		List<NeumaticoEntity> listaNeumaticos = neumaticoRepository.findAll();
		List<NeumaticoEntity> listaNeumaticosCoche = new ArrayList<>();
		for (NeumaticoEntity neumaticoEntity : listaNeumaticos) {
			if(neumaticoEntity.getCoche()!=null && neumaticoEntity.getCoche().getMatricula().equals(matricula)) {
				listaNeumaticosCoche.add(neumaticoEntity);
			}
		}
		return listaNeumaticosCoche;
	}
	
	public void eliminarNeumatico(Integer neumatico) {
		neumaticoRepository.deleteById(neumatico);
	}

	public List<NeumaticoEntity> listarTodos(){return neumaticoRepository.findAll();}

}
