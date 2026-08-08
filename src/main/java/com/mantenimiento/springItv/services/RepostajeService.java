package com.mantenimiento.springItv.services;

import java.util.List;
import java.util.Optional;

import com.mantenimiento.springItv.dto.GastoMensualDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.repositories.CocheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.repositories.RepostajeRepository;

@Service
@RequiredArgsConstructor
public class RepostajeService {

	@Autowired
    private RepostajeRepository repostajeRepository;

	@Autowired
	private CocheRepository cocheRepository;
	
    public void guardarRepostaje(RepostajeEntity repostaje, String matricula) {
		Optional<CocheEntity> coche = cocheRepository.findById(matricula);
		Integer kmNuevo = repostaje.getKmRepostaje();
		repostaje.setCoche(coche.get());
		repostajeRepository.save(repostaje);
		// El km del coche solo avanza si el nuevo dato es más alto (ver CocheRepository.actualizarKilometraje);
		// registros históricos con km menor se guardan igualmente sin tocar el km actual del coche.
		if (kmNuevo != null) {
			cocheRepository.actualizarKilometraje(matricula, kmNuevo);
		}
    }
	
	public List<RepostajeEntity> listarRepostajes(String matricula){
		return repostajeRepository.findByCocheMatricula(matricula);
	}
	
	public void eliminarRepostaje(Integer repostaje) {
		repostajeRepository.deleteByIdRepostaje(repostaje);
	}
	
	public Optional<RepostajeEntity> obtenerPorId(Integer idFactura) {
		return repostajeRepository.findById(idFactura);
	}

	public List<RepostajeEntity> listarTodos(){return repostajeRepository.findAll();}

	public List<GastoPorCocheDto> gastoTotalPorCocheDe(String username) {
		return repostajeRepository.findGastoTotalPorCoche(username);
	}

	public List<GastoMensualDto> gastoMensualPorCocheDe(String username) {
		return repostajeRepository.findGastoMensualPorCoche(username);
	}
}
