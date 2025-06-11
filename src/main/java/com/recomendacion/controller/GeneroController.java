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

<<<<<<< HEAD
    @Operation(summary = "Crear género")
    @PostMapping
    public ResponseEntity<GeneroDTO> crear(@Valid @RequestBody GeneroDTO generoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(generoService.crear(generoDTO));
    }

    @Operation(summary = "Eliminar género")
=======
    @Operation(summary = "Obtener un género por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo género")
    @PostMapping
    public ResponseEntity<GeneroDTO> crear(@Valid @RequestBody GeneroDTO generoDTO) {
        GeneroDTO generoCreado = generoService.crear(generoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoCreado);
    }

    @Operation(summary = "Actualizar un género existente")
    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody GeneroDTO generoDTO) {
        GeneroDTO generoActualizado = generoService.actualizar(id, generoDTO);
        return ResponseEntity.ok(generoActualizado);
    }

    @Operation(summary = "Eliminar un género")
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        generoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}