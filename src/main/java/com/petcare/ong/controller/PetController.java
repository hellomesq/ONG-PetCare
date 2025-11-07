package com.petcare.ong.controller;

import com.petcare.ong.model.Pet;
import com.petcare.ong.service.PetService;
import com.petcare.ong.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private AiService aiService;

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

    /**
     * Endpoint para testar a funcionalidade de IA
     * Gera uma recomendação personalizada de cuidados para o pet
     */
    @GetMapping("/{id}/recomendacao")
    public ResponseEntity<Map<String, Object>> gerarRecomendacao(@PathVariable Long id) {
        Pet pet = petService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com ID: " + id));
        
        String recomendacao = aiService.gerarRecomendacao(pet);
        
        Map<String, Object> response = new HashMap<>();
        response.put("petId", pet.getId());
        response.put("petNome", pet.getNome());
        response.put("recomendacao", recomendacao);
        
        return ResponseEntity.ok(response);
    }
}
