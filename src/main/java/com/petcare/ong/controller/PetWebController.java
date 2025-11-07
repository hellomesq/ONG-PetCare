package com.petcare.ong.controller;

import com.petcare.ong.model.Pet;
import com.petcare.ong.service.PetService;
import com.petcare.ong.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/pets")
public class PetWebController {

    @Autowired
    private PetService petService;

    @Autowired
    private AiService aiService;

    /**
     * Lista todos os pets cadastrados
     */
    @GetMapping
    public String listarPets(Model model) {
        List<Pet> pets = petService.listarTodos();
        model.addAttribute("pets", pets);
        return "pets/lista";
    }

    /**
     * Exibe detalhes de um pet específico
     */
    @GetMapping("/{id}")
    public String detalhesPet(@PathVariable Long id, Model model) {
        Pet pet = petService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        model.addAttribute("pet", pet);
        return "pets/detalhes";
    }

    /**
     * Gera e exibe recomendação de IA para um pet
     */
    @GetMapping("/{id}/recomendacao")
    public String gerarRecomendacao(@PathVariable Long id, Model model) {
        Pet pet = petService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        String recomendacao = aiService.gerarRecomendacao(pet);
        
        model.addAttribute("pet", pet);
        model.addAttribute("recomendacao", recomendacao);
        return "pets/recomendacao";
    }
}
