package com.petcare.ong.service;

import com.petcare.ong.model.Pet;
import com.petcare.ong.model.Ong;
import com.petcare.ong.repository.PetRepository;
import com.petcare.ong.repository.OngRepository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OngRepository ongRepository;

    public Pet criarPet(@NonNull Long ongId, Pet pet) {
        Ong ong = ongRepository.findById(ongId)
                .orElseThrow(() -> new RuntimeException("Ong não encontrada"));
        pet.setOng(ong);
        return petRepository.save(pet);
    }

    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    public List<Pet> listarPorOng(@NonNull Long ongId) {
        return petRepository.findByOngId(ongId);
    }

    public Pet atualizarPet(@NonNull Long id, Pet petAtualizado) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        pet.setNome(petAtualizado.getNome());
        pet.setEspecie(petAtualizado.getEspecie());
        pet.setRaca(petAtualizado.getRaca());
        pet.setIdade(petAtualizado.getIdade());
        pet.setDescricao(petAtualizado.getDescricao());
        return petRepository.save(pet);
    }

    public void deletarPet(@NonNull Long id) {
        petRepository.deleteById(id);
    }
}
