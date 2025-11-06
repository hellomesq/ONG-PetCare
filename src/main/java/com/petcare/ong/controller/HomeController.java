package com.petcare.ong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.petcare.ong.model.Ong;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("ong", new Ong());
        return "formOng";
    }
}

