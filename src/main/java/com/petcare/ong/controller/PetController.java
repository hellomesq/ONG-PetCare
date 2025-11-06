package com.petcare.ong.controller;

import com.petcare.ong.model.Pet;
import com.petcare.ong.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/{ongId}")
    public Pet criarPet(@PathVariable Long ongId, @RequestBody Pet pet) {
        return petService.criarPet(ongId, pet);
    }

    @GetMapping
    public List<Pet> listarTodos() {
        return petService.listarTodos();
    }

    @GetMapping("/ong/{ongId}")
    public List<Pet> listarPorOng(@PathVariable Long ongId) {
        return petService.listarPorOng(ongId);
    }

    @PutMapping("/{id}")
    public Pet atualizarPet(@PathVariable Long id, @RequestBody Pet petAtualizado) {
        return petService.atualizarPet(id, petAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarPet(@PathVariable Long id) {
        petService.deletarPet(id);
    }
}
