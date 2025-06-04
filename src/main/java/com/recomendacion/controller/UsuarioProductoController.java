package com.recomendacion.controller;

import com.recomendacion.dto.UsuarioProductoDTO;
import com.recomendacion.service.UsuarioProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-producto")
@Tag(name = "Interacciones", description = "Historial de interacciones usuario-producto")
public class UsuarioProductoController {

    private final UsuarioProductoService usuarioProductoService;

    public UsuarioProductoController(UsuarioProductoService usuarioProductoService) {
        this.usuarioProductoService = usuarioProductoService;
    }

    @Operation(summary = "Registrar interacción de usuario con producto")
    @PostMapping("/{usuarioId}/interacciones/{productoId}")
    public ResponseEntity<UsuarioProductoDTO> registrarInteraccion(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId,
            @RequestParam String tipoInteraccion) {

        UsuarioProductoDTO interaccion = usuarioProductoService.registrarInteraccion(
                usuarioId, productoId, tipoInteraccion);

        return ResponseEntity.status(HttpStatus.CREATED).body(interaccion);
    }

    @Operation(summary = "Obtener interacciones por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioProductoDTO>> obtenerPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioProductoService.obtenerPorUsuario(usuarioId));
    }
}