package com.recomendacion.controller;

import com.recomendacion.dto.GeneroDTO;
import com.recomendacion.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//importacion para el PreAuthorize
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
//
@RequestMapping("/api/generos")
@Tag(name = "Géneros", description = "Gestión de géneros")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    //ENDPOINT PROTEGIDO
    @PreAuthorize("hasAnyRole('USER', 'COLABORADOR', 'ADMIN')")
    @Operation(summary = "Obtener todos los géneros")
    @GetMapping
    public ResponseEntity<List<GeneroDTO>> obtenerTodos() {
        return ResponseEntity.ok(generoService.obtenerTodos());
    }

    //ENDPOINT PROTEGIDO
    @PreAuthorize("hasAnyRole('USER', 'COLABORADOR', 'ADMIN')")
    @Operation(summary = "Obtener un género por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.obtenerPorId(id));
    }

    //ENDPOINT PROTEGIDO
    @PreAuthorize("hasAnyRole('COLABORADOR', 'ADMIN')")
    @Operation(summary = "Crear un nuevo género")
    @PostMapping
    public ResponseEntity<GeneroDTO> crear(@Valid @RequestBody GeneroDTO generoDTO) {
        GeneroDTO generoCreado = generoService.crear(generoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoCreado);
    }

    //ENDPOINT PROTEGIDO
    @PreAuthorize("hasAnyRole('COLABORADOR', 'ADMIN')")
    @Operation(summary = "Actualizar un género existente")
    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody GeneroDTO generoDTO) {
        GeneroDTO generoActualizado = generoService.actualizar(id, generoDTO);
        return ResponseEntity.ok(generoActualizado);
    }

    //ENDPOINT PROTEGIDO
    @PreAuthorize("hasAnyRole('COLABORADOR', 'ADMIN')")
    @Operation(summary = "Eliminar un género")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        generoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}