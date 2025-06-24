package com.mantenimiento.springItv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.repositories.RepostajeRepository;

@Service
public class RepostajeService {

	@Autowired
    private RepostajeRepository repostajeRepository;
	
    public RepostajeEntity guardarRepostaje(RepostajeEntity repostaje) {
        return repostajeRepository.save(repostaje);
    }
	
	public List<RepostajeEntity> listarRepostajes(String matricula){
		List<RepostajeEntity> listaRepostaje = repostajeRepository.findAll();
		List<RepostajeEntity> listaRepostajeCoche = new ArrayList<>();
		for (RepostajeEntity repostajeEntity : listaRepostaje) {
			if(repostajeEntity.getCoche()!=null && repostajeEntity.getCoche().getMatricula().equals(matricula)) {
				listaRepostajeCoche.add(repostajeEntity);
			}
		}
		return listaRepostajeCoche;
	}
	
	public void eliminarRepostaje(Integer repostaje) {
		repostajeRepository.deleteById(repostaje);
	}
	
	public Optional<RepostajeEntity> obtenerPorId(Integer idFactura) {
		return repostajeRepository.findById(idFactura);
	}

	public List<RepostajeEntity> listarTodos(){return repostajeRepository.findAll();}
	
}
