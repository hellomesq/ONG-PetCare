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

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ongs", service.listarTodos());
        return "ongs";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ong", new Ong());
        return "form-ong";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Ong ong) {
        service.salvar(ong);
        return "redirect:/ongs";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("ong", service.buscarPorId(id).orElse(new Ong()));
        return "form-ong";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/ongs";
    }
}
