package com.recomendacion.controller;

import com.recomendacion.dto.RecomendacionDTO;
import com.recomendacion.service.RecomendacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}