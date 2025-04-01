package com.mantenimiento.springItv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.RecambioEntity;
import com.mantenimiento.springItv.repositories.RecambioRepository;

@Service
public class RecambioService {

	@Autowired
	RecambioRepository recambioRepository;
	
    public RecambioEntity guardarRecambio(RecambioEntity recambio) {
        return recambioRepository.save(recambio);
    }
    
    public List<RecambioEntity> listarRecambios() {
    	return recambioRepository.findAll();
    }
    
    
	
}
