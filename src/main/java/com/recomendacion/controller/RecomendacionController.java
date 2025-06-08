package com.recomendacion.controller;

import com.recomendacion.dto.RecomendacionDTO;
import com.recomendacion.service.RecomendacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@Tag(name = "Recomendaciones", description = "Sistema de recomendación")
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    public RecomendacionController(RecomendacionService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    @Operation(summary = "Obtener todas las recomendaciones")
    @GetMapping
    public ResponseEntity<List<RecomendacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(recomendacionService.obtenerTodas());
    }

    @Operation(summary = "Obtener recomendación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RecomendacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recomendacionService.obtenerPorId(id));
    }

    @Operation(summary = "Generar recomendaciones para usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RecomendacionDTO>> generarRecomendaciones(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(recomendacionService.generarRecomendaciones(usuarioId));
    }

    @Operation(summary = "Obtener historial de recomendaciones")
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<RecomendacionDTO>> obtenerHistorial(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(recomendacionService.obtenerPorUsuario(usuarioId));
    }

    @Operation(summary = "Crear recomendación manualmente")
    @PostMapping
    public ResponseEntity<RecomendacionDTO> crearRecomendacion(
            @Valid @RequestBody RecomendacionDTO recomendacionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recomendacionService.crear(recomendacionDTO));
    }

    @Operation(summary = "Actualizar recomendación")
    @PutMapping("/{id}")
    public ResponseEntity<RecomendacionDTO> actualizarRecomendacion(
            @PathVariable Long id,
            @Valid @RequestBody RecomendacionDTO recomendacionDTO) {
        return ResponseEntity.ok(recomendacionService.actualizar(id, recomendacionDTO));
    }

    @Operation(summary = "Eliminar recomendación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecomendacion(@PathVariable Long id) {
        recomendacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}