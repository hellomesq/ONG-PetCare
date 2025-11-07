package com.petcare.ong.controller;

import com.petcare.ong.model.Ong;
import com.petcare.ong.service.OngService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ongs")
public class OngController {

    private final OngService service;

    public OngController(OngService service) {
        this.service = service;
    }

    // Lista todas as ONGs cadastradas (público)
    @GetMapping
    public String listar(Model model) {
        List<Ong> ongs = service.listarTodos();
        model.addAttribute("ongs", ongs);
        return "ongs/lista";
    }

    // Formulário de nova ONG
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ong", new Ong());
        return "formOng";
    }

    // Salvar ONG
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Ong ong, RedirectAttributes redirectAttributes) {
        service.salvar(ong);
        redirectAttributes.addFlashAttribute("sucesso", "ONG cadastrada com sucesso! Faça login para continuar.");
        return "redirect:/ongs/login";
    }

    // Página de login
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "ongs/login";
    }

    // Processar login
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String senha, 
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        Ong ong = service.buscarPorEmail(email);
        
        if (ong == null) {
            redirectAttributes.addFlashAttribute("erro", "Email não encontrado!");
            return "redirect:/ongs/login";
        }
        
        if (!ong.getSenha().equals(senha)) {
            redirectAttributes.addFlashAttribute("erro", "Senha incorreta!");
            return "redirect:/ongs/login";
        }
        
        // Login bem-sucedido - criar sessão
        session.setAttribute("ongLogada", ong);
        return "redirect:/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("sucesso", "Logout realizado com sucesso!");
        return "redirect:/ongs/login";
    }

    // Editar ONG (admin view)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Ong ong = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        model.addAttribute("ong", ong);
        return "formOng";
    }

    // Deletar ONG (admin view)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/ongs";
    }
}
