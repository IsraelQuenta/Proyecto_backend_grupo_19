package com.universidad.recomendador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class SaludoController {
    @GetMapping
    public String saludo() {
        return "Hola, bienvenido al sistema de recomendacion de libros y peliculas!";
    }
}