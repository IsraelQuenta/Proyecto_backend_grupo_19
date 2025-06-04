package com.recomendacion.controller;

import com.recomendacion.dto.RatingDTO;
import com.recomendacion.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings", description = "Gestión de valoraciones")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Obtener todos los ratings")
    @GetMapping
    public ResponseEntity<List<RatingDTO>> obtenerTodos() {
        return ResponseEntity.ok(ratingService.obtenerTodos());
    }

    @Operation(summary = "Obtener ratings por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RatingDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ratingService.obtenerPorUsuario(usuarioId));
    }

    @Operation(summary = "Obtener ratings por producto")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<RatingDTO>> obtenerPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(ratingService.obtenerPorProducto(productoId));
    }

    @Operation(summary = "Crear rating")
    @PostMapping
    public ResponseEntity<RatingDTO> crear(@Valid @RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingService.crear(ratingDTO));
    }

    @Operation(summary = "Actualizar rating")
    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.actualizar(id, ratingDTO));
    }

    @Operation(summary = "Eliminar rating")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ratingService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}