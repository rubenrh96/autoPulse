package com.mantenimiento.springItv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.repositories.ItvRepository;

@Service
public class ItvService {

	@Autowired
    private ItvRepository itvRepository;
	
    public ItvEntity guardarItv(ItvEntity itv) {
        return itvRepository.save(itv);
    }
	
	public List<ItvEntity> listarItvs(String matricula){
		List<ItvEntity> listaItv = itvRepository.findAll();
		List<ItvEntity> listaItvCoche = new ArrayList<>();
		for (ItvEntity repostajeEntity : listaItv) {
			if(repostajeEntity.getCoche()!=null && repostajeEntity.getCoche().getMatricula().equals(matricula)) {
				listaItvCoche.add(repostajeEntity);
			}
		}
		return listaItvCoche;
	}
	
	public void eliminarItv(Integer itv) {
		itvRepository.deleteById(itv);
	}
	
	public Optional<ItvEntity> obtenerPorId(Integer id){
		return itvRepository.findById(id);
	}

	public List<ItvEntity> listarTodos(){return itvRepository.findAll();}
	
}
