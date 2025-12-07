package com.mantenimiento.springItv.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.repositories.CocheRepository;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.repositories.ItvRepository;

@Service
public class ItvService {

	@Autowired
    private ItvRepository itvRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CocheRepository cocheRepository;
	
    public ItvEntity guardarItv(ItvEntity itv) {
        ItvEntity itvGuardada = itvRepository.save(itv);
        if (itvGuardada.getCoche() != null) {
        	cocheRepository.actualizarKilometraje(
        			itvGuardada.getCoche().getMatricula(),
					itvGuardada.getKmRevision()
			);
		}
        return itvGuardada;
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

	public List<ItvEntity> listarTodos(){
		return itvRepository.findAll();
	}

	public List<LocalDate> listarItvPorUsuario(Long usuario){
		List<CocheEntity> listaCoches = cocheRepository.findByUsuarioId(usuario);
		List<LocalDate> listaFechas = new ArrayList<>();
		for (CocheEntity coche : listaCoches){
			for (ItvEntity itv : coche.getItvs()){
				listaFechas.add(itv.getFechaProximaItv());
			}
		}
		return listaFechas;
	}
	
}
