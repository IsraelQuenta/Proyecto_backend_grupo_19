package com.recomendacion.controller;

import com.recomendacion.dto.UsuarioProductoDTO;
import com.recomendacion.service.UsuarioProductoService;
<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
=======
import com.recomendacion.validation.UsuarioProductoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-producto")
@Tag(name = "Interacciones", description = "Historial de interacciones usuario-producto")
public class UsuarioProductoController {

    private final UsuarioProductoService usuarioProductoService;
<<<<<<< HEAD

    public UsuarioProductoController(UsuarioProductoService usuarioProductoService) {
        this.usuarioProductoService = usuarioProductoService;
    }

    @Operation(summary = "Registrar interacción de usuario con producto")
=======
    private final UsuarioProductoValidator validator;

    public UsuarioProductoController(UsuarioProductoService usuarioProductoService,
                                     UsuarioProductoValidator validator) {
        this.usuarioProductoService = usuarioProductoService;
        this.validator = validator;
    }

    @Operation(summary = "Obtener todas las interacciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de interacciones obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioProductoService.obtenerTodos());
    }

    @Operation(summary = "Obtener interacción por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interacción encontrada"),
            @ApiResponse(responseCode = "404", description = "Interacción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioProductoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear interacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interacción creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioProductoDTO usuarioProductoDTO) {
        validator.normalizar(usuarioProductoDTO);
        List<String> errores = validator.validar(usuarioProductoDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioProductoService.crear(usuarioProductoDTO));
    }

    @Operation(summary = "Actualizar interacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interacción actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Interacción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioProductoDTO usuarioProductoDTO) {
        usuarioProductoDTO.setId(id);
        validator.normalizar(usuarioProductoDTO);
        List<String> errores = validator.validarParaActualizacion(usuarioProductoDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(usuarioProductoService.actualizar(usuarioProductoDTO));
    }

    @Operation(summary = "Eliminar interacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Interacción eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Interacción no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Registrar interacción de usuario con producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interacción registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
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
<<<<<<< HEAD
=======
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de interacciones obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioProductoDTO>> obtenerPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioProductoService.obtenerPorUsuario(usuarioId));
    }
<<<<<<< HEAD
=======

    @Operation(summary = "Obtener interacciones por producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de interacciones obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<UsuarioProductoDTO>> obtenerPorProducto(
            @PathVariable Long productoId) {
        return ResponseEntity.ok(usuarioProductoService.obtenerPorProducto(productoId));
    }

    @Operation(summary = "Obtener interacciones por usuario y tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de interacciones obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}/tipo/{tipoInteraccion}")
    public ResponseEntity<List<UsuarioProductoDTO>> obtenerPorUsuarioYTipo(
            @PathVariable Long usuarioId,
            @PathVariable String tipoInteraccion) {
        return ResponseEntity.ok(usuarioProductoService.obtenerPorUsuarioYTipo(usuarioId, tipoInteraccion));
    }
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
}