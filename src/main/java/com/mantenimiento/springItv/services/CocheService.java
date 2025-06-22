package com.mantenimiento.springItv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.repositories.CocheRepository;

@Service
public class CocheService {
	
	@Autowired
    private CocheRepository cocheRepository;
	
	public List<CocheEntity> listarCoches(){
		return cocheRepository.findAll();
	}
	
	public Optional<CocheEntity> obtenerPorId(String id){
		return cocheRepository.findById(id);
	}

    public CocheEntity guardarCoche(CocheEntity coche) {
        return cocheRepository.save(coche);
    }

	public List<CocheEntity> listarCochesPorUsuario(Long usuarioId) {
		return cocheRepository.findByUsuarioId(usuarioId);
	}


}
