package com.mantenimiento.springItv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mantenimiento.springItv.entities.SeguroEntity;
import com.mantenimiento.springItv.repositories.SeguroRepository;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    public SeguroEntity guardarSeguro(SeguroEntity seguro) {
        return seguroRepository.save(seguro);
    }

    public List<SeguroEntity> listarSeguros(String matricula) {
        return seguroRepository.findByCocheMatricula(matricula);
    }

    public void eliminarSeguro(Integer id) {
        seguroRepository.deleteByIdSeguro(id);
    }

    public Optional<SeguroEntity> obtenerPorId(Integer id) {
        return seguroRepository.findById(id);
    }

}
