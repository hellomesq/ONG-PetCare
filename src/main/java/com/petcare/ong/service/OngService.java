package com.petcare.ong.service;

import com.petcare.ong.model.Ong;
import com.petcare.ong.repository.OngRepository;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OngService {

    @Autowired
    private OngRepository repository;

    public List<Ong> listarTodos() {
        return repository.findAll();
    }

    public Optional<Ong> buscarPorId(@NonNull Long id) {
        return repository.findById(id);
    }

    public Ong salvar(@NonNull Ong ong) {
        return repository.save(ong);
    }

    public void deletar(@NonNull Long id) {
        repository.deleteById(id);
    }
}
