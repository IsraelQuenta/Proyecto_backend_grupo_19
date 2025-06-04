package com.recomendacion.controller;

import com.recomendacion.dto.*;
import com.recomendacion.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión de libros y películas")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtener todos los productos")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear libro")
    @PostMapping("/libros")
    public ResponseEntity<LibroDTO> crearLibro(@Valid @RequestBody LibroDTO libroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearLibro(libroDTO));
    }

    @Operation(summary = "Crear película")
    @PostMapping("/peliculas")
    public ResponseEntity<PeliculaDTO> crearPelicula(@Valid @RequestBody PeliculaDTO peliculaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearPelicula(peliculaDTO));
    }

    @Operation(summary = "Actualizar libro")
    @PutMapping("/libros/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libroDTO) {
        return ResponseEntity.ok(productoService.actualizarLibro(id, libroDTO));
    }

    @Operation(summary = "Actualizar película")
    @PutMapping("/peliculas/{id}")
    public ResponseEntity<PeliculaDTO> actualizarPelicula(
            @PathVariable Long id,
            @Valid @RequestBody PeliculaDTO peliculaDTO) {
        return ResponseEntity.ok(productoService.actualizarPelicula(id, peliculaDTO));
    }

    @Operation(summary = "Eliminar producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}