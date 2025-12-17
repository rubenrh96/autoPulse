package com.mantenimiento.springItv.services;

import java.util.List;
import java.util.Optional;
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

    public List<RecambioEntity> listarRecambiosPorUsuario(Long usuarioId) {
        return recambioRepository.findByUsuarioId(usuarioId);
    }

    public Optional<RecambioEntity> obtenerPorId(Integer idRecambio) {
        return recambioRepository.findById(idRecambio);
    }

    public void eliminarRecambio(Integer idRecambio) {
        recambioRepository.deleteById(idRecambio);
    }

}
