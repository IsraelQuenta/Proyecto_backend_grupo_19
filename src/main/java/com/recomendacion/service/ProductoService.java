package com.recomendacion.service;

import com.recomendacion.dto.LibroDTO;
import com.recomendacion.dto.PeliculaDTO;
import com.recomendacion.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoDTO> obtenerTodos();
    ProductoDTO obtenerPorId(Long id);
    LibroDTO crearLibro(LibroDTO libroDTO);
    PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO);
    LibroDTO actualizarLibro(Long id, LibroDTO libroDTO);
    PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO);
    void eliminar(Long id);
    List<LibroDTO> obtenerTodosLibros();
    List<PeliculaDTO> obtenerTodasPeliculas();
}