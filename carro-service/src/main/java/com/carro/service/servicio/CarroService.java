package com.carro.service.servicio;

import com.carro.service.entidades.Carro;
import com.carro.service.repositorio.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getAll() {
        return carroRepository.findAll();
    }

    public Carro getCarroById(Integer id) {
        return carroRepository.findById(id).orElse(null);
    }

    public Carro save(Carro carro) {
        return carroRepository.save(carro);
    }

    public List<Carro> findByUsuarioId(Integer usuarioId){
        return carroRepository.findByUsuarioId(usuarioId);
    }
}
