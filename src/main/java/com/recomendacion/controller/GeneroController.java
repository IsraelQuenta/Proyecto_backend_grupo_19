package com.recomendacion.controller;

import com.recomendacion.dto.GeneroDTO;
import com.recomendacion.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@Tag(name = "Géneros", description = "Gestión de géneros")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @Operation(summary = "Obtener todos los géneros")
    @GetMapping
    public ResponseEntity<List<GeneroDTO>> obtenerTodos() {
        return ResponseEntity.ok(generoService.obtenerTodos());
    }

    @Operation(summary = "Crear género")
    @PostMapping
    public ResponseEntity<GeneroDTO> crear(@Valid @RequestBody GeneroDTO generoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(generoService.crear(generoDTO));
    }

    @Operation(summary = "Eliminar género")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        generoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}