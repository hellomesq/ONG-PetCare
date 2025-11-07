package com.petcare.ong.controller;

import com.petcare.ong.model.Ong;
import com.petcare.ong.model.Pet;
import com.petcare.ong.service.AiService;
import com.petcare.ong.service.PetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private PetService petService;

    @Autowired
    private AiService aiService;

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        // Buscar apenas pets dessa ONG
        List<Pet> pets = petService.listarTodos().stream()
                .filter(p -> p.getOng().getId().equals(ongLogada.getId()))
                .toList();

        model.addAttribute("ong", ongLogada);
        model.addAttribute("pets", pets);
        model.addAttribute("totalPets", pets.size());

        return "dashboard/index";
    }

    @GetMapping("/pets/novo")
    public String novoPet(HttpSession session, Model model) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Pet pet = new Pet();
        pet.setOng(ongLogada);
        model.addAttribute("pet", pet);

        return "dashboard/pets/form";
    }

    @PostMapping("/pets")
    public String salvarPet(@ModelAttribute Pet pet, HttpSession session, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        // Garantir que o pet pertence à ONG logada
        pet.setOng(ongLogada);
        petService.salvar(pet);

        redirectAttributes.addFlashAttribute("mensagem", "Pet cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/pets/{id}/editar")
    public String editarPet(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Optional<Pet> petOpt = petService.buscarPorId(id);
        if (petOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:/dashboard";
        }

        Pet pet = petOpt.get();
        // Verificar se o pet pertence à ONG logada
        if (!pet.getOng().getId().equals(ongLogada.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar este pet!");
            return "redirect:/dashboard";
        }

        model.addAttribute("pet", pet);
        return "dashboard/pets/form";
    }

    @PostMapping("/pets/{id}")
    public String atualizarPet(@PathVariable Long id, @ModelAttribute Pet pet, HttpSession session, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Optional<Pet> petExistenteOpt = petService.buscarPorId(id);
        if (petExistenteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:/dashboard";
        }

        Pet petExistente = petExistenteOpt.get();
        // Verificar se o pet pertence à ONG logada
        if (!petExistente.getOng().getId().equals(ongLogada.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para editar este pet!");
            return "redirect:/dashboard";
        }

        // Manter a ONG original
        pet.setId(id);
        pet.setOng(ongLogada);
        petService.salvar(pet);

        redirectAttributes.addFlashAttribute("mensagem", "Pet atualizado com sucesso!");
        return "redirect:/dashboard";
    }

    @PostMapping("/pets/{id}/deletar")
    public String deletarPet(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Optional<Pet> petOpt = petService.buscarPorId(id);
        if (petOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:/dashboard";
        }

        Pet pet = petOpt.get();
        // Verificar se o pet pertence à ONG logada
        if (!pet.getOng().getId().equals(ongLogada.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para deletar este pet!");
            return "redirect:/dashboard";
        }

        petService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Pet removido com sucesso!");
        return "redirect:/dashboard";
    }

    @GetMapping("/pets/{id}")
    public String detalhesPet(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Optional<Pet> petOpt = petService.buscarPorId(id);
        if (petOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:/dashboard";
        }

        Pet pet = petOpt.get();
        // Verificar se o pet pertence à ONG logada
        if (!pet.getOng().getId().equals(ongLogada.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para visualizar este pet!");
            return "redirect:/dashboard";
        }

        model.addAttribute("pet", pet);
        return "dashboard/pets/detalhes";
    }

    @GetMapping("/pets/{id}/recomendacao")
    public String recomendacaoPet(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Ong ongLogada = (Ong) session.getAttribute("ongLogada");
        if (ongLogada == null) {
            return "redirect:/ongs/login";
        }

        Optional<Pet> petOpt = petService.buscarPorId(id);
        if (petOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:/dashboard";
        }

        Pet pet = petOpt.get();
        // Verificar se o pet pertence à ONG logada
        if (!pet.getOng().getId().equals(ongLogada.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para visualizar este pet!");
            return "redirect:/dashboard";
        }

        String recomendacao = aiService.gerarRecomendacao(pet);
        model.addAttribute("pet", pet);
        model.addAttribute("recomendacao", recomendacao);

        return "dashboard/pets/recomendacao";
    }
}
