package com.petcare.ong.controller;

import com.petcare.ong.model.Pet;
import com.petcare.ong.repository.PetRepository;
import com.petcare.ong.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OngRepository ongRepository;

    // Criar um novo pet vinculado a uma ONG
    @PostMapping("/{ongId}")
    public Pet criarPet(@PathVariable Long ongId, @RequestBody Pet pet) {
        var ong = ongRepository.findById(ongId)
                .orElseThrow(() -> new RuntimeException("Ong não encontrada"));
        pet.setOng(ong);
        return petRepository.save(pet);
    }

    // Listar todos os pets
    @GetMapping
    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    // Listar pets por ONG
    @GetMapping("/ong/{ongId}")
    public List<Pet> listarPorOng(@PathVariable Long ongId) {
        return petRepository.findByOngId(ongId);
    }

    // Atualizar pet
    @PutMapping("/{id}")
    public Pet atualizarPet(@PathVariable Long id, @RequestBody Pet petAtualizado) {
        var pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        pet.setNome(petAtualizado.getNome());
        pet.setEspecie(petAtualizado.getEspecie());
        pet.setRaca(petAtualizado.getRaca());
        pet.setIdade(petAtualizado.getIdade());
        pet.setDescricao(petAtualizado.getDescricao());
        return petRepository.save(pet);
    }

    // Deletar pet
    @DeleteMapping("/{id}")
    public void deletarPet(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}
