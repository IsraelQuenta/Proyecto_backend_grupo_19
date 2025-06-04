package com.recomendacion.service.impl;

import com.recomendacion.dto.LibroDTO;
import com.recomendacion.dto.PeliculaDTO;
import com.recomendacion.dto.ProductoDTO;
import com.recomendacion.model.Libro;
import com.recomendacion.model.Pelicula;
import com.recomendacion.model.Producto;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertToProductoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertToProductoDTO(producto);
    }

    @Override
    @Transactional
    public LibroDTO crearLibro(LibroDTO libroDTO) {
        Libro libro = convertToLibroEntity(libroDTO);
        Libro libroGuardado = (Libro) productoRepository.save(libro);
        return convertToLibroDTO(libroGuardado);
    }

    @Override
    @Transactional
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO) {
        Pelicula pelicula = convertToPeliculaEntity(peliculaDTO);
        Pelicula peliculaGuardada = (Pelicula) productoRepository.save(pelicula);
        return convertToPeliculaDTO(peliculaGuardada);
    }

    @Override
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO) {
        Libro libroExistente = (Libro) productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setEdicion(libroDTO.getEdicion());
        libroExistente.setEditorial(libroDTO.getEditorial());
        // Actualizar otros campos según sea necesario

        Libro libroActualizado = (Libro) productoRepository.save(libroExistente);
        return convertToLibroDTO(libroActualizado);
    }

    @Override
    @Transactional
    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
        Pelicula peliculaExistente = (Pelicula) productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setDirector(peliculaDTO.getDirector());
        peliculaExistente.setDuracionMinutos(peliculaDTO.getDuracionMinutos());
        peliculaExistente.setFechaEstreno(peliculaDTO.getFechaEstreno());
        // Actualizar otros campos según sea necesario

        Pelicula peliculaActualizada = (Pelicula) productoRepository.save(peliculaExistente);
        return convertToPeliculaDTO(peliculaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // Métodos de conversión
    private ProductoDTO convertToProductoDTO(Producto producto) {
        if (producto instanceof Libro) {
            return convertToLibroDTO((Libro) producto);
        } else if (producto instanceof Pelicula) {
            return convertToPeliculaDTO((Pelicula) producto);
        }
        return null;
    }

    private LibroDTO convertToLibroDTO(Libro libro) {
        return LibroDTO.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .autor(libro.getAutor())
                .edicion(libro.getEdicion()) 
                .editorial(libro.getEditorial())
                .build();
    }

    private PeliculaDTO convertToPeliculaDTO(Pelicula pelicula) {
        return PeliculaDTO.builder()
                .id(pelicula.getId())
                .titulo(pelicula.getTitulo())
                .director(pelicula.getDirector())
                .duracionMinutos(pelicula.getDuracionMinutos())
                .fechaEstreno(pelicula.getFechaEstreno())
                .build();
    }

    private Libro convertToLibroEntity(LibroDTO libroDTO) {
        return Libro.builder()
                .id(libroDTO.getId())
                .titulo(libroDTO.getTitulo())
                .autor(libroDTO.getAutor())
                .edicion(libroDTO.getEdicion())
                .editorial(libroDTO.getEditorial())
                .build();
    }

    private Pelicula convertToPeliculaEntity(PeliculaDTO peliculaDTO) {
        return Pelicula.builder()
                .id(peliculaDTO.getId())
                .titulo(peliculaDTO.getTitulo())
                .director(peliculaDTO.getDirector())
                .duracionMinutos(peliculaDTO.getDuracionMinutos())
                .fechaEstreno(peliculaDTO.getFechaEstreno())
                .build();
    }
}