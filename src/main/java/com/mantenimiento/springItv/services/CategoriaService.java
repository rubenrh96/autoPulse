package com.mantenimiento.springItv.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	public List<CategoriaEntity> listarCategorias(){
		return categoriaRepository.findAll();
	}
}
