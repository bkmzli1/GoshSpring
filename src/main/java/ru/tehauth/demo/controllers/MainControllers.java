package ru.tehauth.demo.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainControllers {
    @GetMapping("/")
    public String greeting(Model model){
        model.addAttribute("title" , "Главная Страница");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title" , "О нас");
        return "home";
    }

}
