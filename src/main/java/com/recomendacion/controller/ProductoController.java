package com.recomendacion.controller;

import com.recomendacion.dto.*;
import com.recomendacion.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de libros y películas")
public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Obtener todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto (producto duplicado)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/libros")
    public ResponseEntity<?> crearLibro(@Valid @RequestBody LibroDTO libroDTO) {
        try {
            LibroDTO creado = productoService.crearLibro(libroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        }
    }

    @Operation(summary = "Crear película")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Película creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto (producto duplicado)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/peliculas")
    public ResponseEntity<?> crearPelicula(@Valid @RequestBody PeliculaDTO peliculaDTO) {
        try {
            PeliculaDTO creado = productoService.crearPelicula(peliculaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        }
    }

    @Operation(summary = "Actualizar libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/libros/{id}")
    public ResponseEntity<?> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libroDTO) {
        try {
            return ResponseEntity.ok(productoService.actualizarLibro(id, libroDTO));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        }
    }

    @Operation(summary = "Actualizar película")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Película actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/peliculas/{id}")
    public ResponseEntity<?> actualizarPelicula(
            @PathVariable Long id,
            @Valid @RequestBody PeliculaDTO peliculaDTO) {
        try {
            return ResponseEntity.ok(productoService.actualizarPelicula(id, peliculaDTO));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        }
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los libros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/libros")
    public ResponseEntity<List<LibroDTO>> obtenerTodosLibros() {
        return ResponseEntity.ok(productoService.obtenerTodosLibros());
    }

    @Operation(summary = "Obtener todas las películas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de películas obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/peliculas")
    public ResponseEntity<List<PeliculaDTO>> obtenerTodasPeliculas() {
        return ResponseEntity.ok(productoService.obtenerTodasPeliculas());
    }
}