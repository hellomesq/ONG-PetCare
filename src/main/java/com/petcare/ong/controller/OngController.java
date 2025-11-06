package com.petcare.ong.controller;

import com.petcare.ong.model.Ong;
import com.petcare.ong.service.OngService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ongs")
public class OngController {

    private final OngService service;

    public OngController(OngService service) {
        this.service = service;
    }

    // Página inicial já vai pro cadastro
    @GetMapping("/")
    public String raiz(Model model) {
        model.addAttribute("ong", new Ong());
        return "form-ong";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ong", new Ong());
        return "form-ong";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Ong ong) {
        service.salvar(ong);
        return "redirect:/ongs"; // ou para dashboard futuramente
    }
}
