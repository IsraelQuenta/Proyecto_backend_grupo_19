package com.recomendacion.controller;

import com.recomendacion.dto.RatingDTO;
import com.recomendacion.service.RatingService;
import com.recomendacion.validation.RatingValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings", description = "Gestión de valoraciones")
public class RatingController {

    private final RatingService ratingService;
    private final RatingValidator ratingValidator;

    public RatingController(RatingService ratingService, RatingValidator ratingValidator) {
        this.ratingService = ratingService;
        this.ratingValidator = ratingValidator;
    }

    @Operation(summary = "Obtener todos los ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ratings obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<RatingDTO>> obtenerTodos() {
        return ResponseEntity.ok(ratingService.obtenerTodos());
    }

    @Operation(summary = "Obtener rating por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating encontrado"),
            @ApiResponse(responseCode = "404", description = "Rating no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> obtenerPorId(@PathVariable Long id) {
        return ratingService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener ratings por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ratings obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RatingDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ratingService.obtenerPorUsuario(usuarioId));
    }

    @Operation(summary = "Obtener ratings por producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ratings obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<RatingDTO>> obtenerPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(ratingService.obtenerPorProducto(productoId));
    }

    @Operation(summary = "Crear rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "El usuario ya ha valorado este producto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody RatingDTO ratingDTO) {
        ratingValidator.normalizar(ratingDTO);

        List<String> errores = ratingValidator.validarParaCreacion(ratingDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }

        boolean existeRating = ratingService.existeRatingDeUsuarioParaProducto(
                ratingDTO.getUsuarioId(),
                ratingDTO.getProductoId()
        );

        errores = ratingValidator.validarDuplicado(ratingDTO, existeRating);
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errores);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingService.crear(ratingDTO));
    }

    @Operation(summary = "Actualizar rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Rating no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RatingDTO ratingDTO) {

        ratingDTO.setId(id);
        ratingValidator.normalizar(ratingDTO);

        List<String> errores = ratingValidator.validarParaActualizacion(ratingDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }

        return ResponseEntity.ok(ratingService.actualizar(id, ratingDTO));
    }

    @Operation(summary = "Eliminar rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rating eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rating no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ratingService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener promedio de ratings por producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio calculado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/promedio/producto/{productoId}")
    public ResponseEntity<BigDecimal> obtenerPromedio(@PathVariable Long productoId) {
        BigDecimal promedio = ratingService.obtenerPromedioPorProducto(productoId);
        return ResponseEntity.ok(promedio);
    }

    @Operation(summary = "Verificar si usuario ha valorado un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/existe/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<Boolean> existeRating(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(
                ratingService.existeRatingDeUsuarioParaProducto(usuarioId, productoId)
        );
    }
}