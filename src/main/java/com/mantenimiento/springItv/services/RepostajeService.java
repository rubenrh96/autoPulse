package com.mantenimiento.springItv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mantenimiento.springItv.exception.KilometrajeInvalidoException;
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
		if (kmNuevo == null || kmNuevo <= coche.get().getKilometros()) {
			throw new KilometrajeInvalidoException(
					"El kilometraje (" + kmNuevo + ") debe ser mayor que el actual (" +
							coche.get().getKilometros() + ")"
			);
		}
		repostaje.setCoche(coche.get());
		repostajeRepository.save(repostaje);
		coche.get().setKilometros(kmNuevo);
		cocheRepository.actualizarKilometraje(matricula, kmNuevo);
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

	public List<GastoPorCocheDto> gastoTotalPorCocheDe(String username) {
		return repostajeRepository.findGastoTotalPorCoche(username);
	}
}
