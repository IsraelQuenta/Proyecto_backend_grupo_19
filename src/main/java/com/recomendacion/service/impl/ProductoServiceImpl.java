package com.recomendacion.service.impl;

import com.recomendacion.dto.*;
import com.recomendacion.exception.NotFoundException;
import com.recomendacion.model.*;
import com.recomendacion.repository.GeneroRepository;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.service.ProductoService;
import com.recomendacion.validation.ProductoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final GeneroRepository generoRepository;
    private final ProductoValidator productoValidator;

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
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
        return convertToProductoDTO(producto);
    }

    @Override
    @Transactional
    public LibroDTO crearLibro(LibroDTO libroDTO) {
        // Validar el DTO
        List<String> errores = productoValidator.validarParaCreacion(libroDTO);
        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", errores));
        }

        // Verificar duplicados
        if (productoRepository.existsByTituloAndPais(libroDTO.getTitulo(), libroDTO.getPais())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un libro con el mismo título y país");
        }

        // Validar géneros
        Set<Genero> generos = validarYObternerGeneros(libroDTO.getGenerosIds());

        // Convertir y guardar
        Libro libro = convertToLibroEntity(libroDTO);
        libro.setGeneros(generos);
        Libro libroGuardado = (Libro) productoRepository.save(libro);
        return convertToLibroDTO(libroGuardado);
    }

    @Override
    @Transactional
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO) {
        // Validar el DTO
        List<String> errores = productoValidator.validarParaCreacion(peliculaDTO);
        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", errores));
        }

        // Verificar duplicados
        if (productoRepository.existsByTituloAndPais(peliculaDTO.getTitulo(), peliculaDTO.getPais())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una película con el mismo título y país");
        }

        // Validar géneros
        Set<Genero> generos = validarYObternerGeneros(peliculaDTO.getGenerosIds());

        // Convertir y guardar
        Pelicula pelicula = convertToPeliculaEntity(peliculaDTO);
        pelicula.setGeneros(generos);
        Pelicula peliculaGuardada = (Pelicula) productoRepository.save(pelicula);
        return convertToPeliculaDTO(peliculaGuardada);
    }

    @Override
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO) {
        // Validar el DTO
        List<String> errores = productoValidator.validarParaActualizacion(libroDTO);
        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", errores));
        }

        // Obtener el libro existente
        Libro libroExistente = (Libro) productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado con ID: " + id));

        // Validar géneros
        Set<Genero> generos = validarYObternerGeneros(libroDTO.getGenerosIds());

        // Actualizar campos
        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setSinopsis(libroDTO.getSinopsis());
        libroExistente.setUrlImagen(libroDTO.getUrlImagen());
        libroExistente.setPais(libroDTO.getPais());
        libroExistente.setEstado(libroDTO.getEstado());
        libroExistente.setGeneros(generos);
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setEditorial(libroDTO.getEditorial());
        libroExistente.setPaginas(libroDTO.getPaginas());
        libroExistente.setEdicion(libroDTO.getEdicion());

        Libro libroActualizado = (Libro) productoRepository.save(libroExistente);
        return convertToLibroDTO(libroActualizado);
    }

    @Override
    @Transactional
    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
        // Validar el DTO
        List<String> errores = productoValidator.validarParaActualizacion(peliculaDTO);
        if (!errores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", errores));
        }

        // Obtener la película existente
        Pelicula peliculaExistente = (Pelicula) productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Película no encontrada con ID: " + id));

        // Validar géneros
        Set<Genero> generos = validarYObternerGeneros(peliculaDTO.getGenerosIds());

        // Actualizar campos
        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setSinopsis(peliculaDTO.getSinopsis());
        peliculaExistente.setUrlImagen(peliculaDTO.getUrlImagen());
        peliculaExistente.setPais(peliculaDTO.getPais());
        peliculaExistente.setEstado(peliculaDTO.getEstado());
        peliculaExistente.setGeneros(generos);
        peliculaExistente.setDirector(peliculaDTO.getDirector());
        peliculaExistente.setProtagonista(peliculaDTO.getProtagonista());
        peliculaExistente.setProductora(peliculaDTO.getProductora());
        peliculaExistente.setDuracionMinutos(peliculaDTO.getDuracionMinutos());
        peliculaExistente.setFechaEstreno(peliculaDTO.getFechaEstreno());

        Pelicula peliculaActualizada = (Pelicula) productoRepository.save(peliculaExistente);
        return convertToPeliculaDTO(peliculaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerTodosLibros() {
        return productoRepository.findAllLibros().stream()
                .map(this::convertToLibroDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaDTO> obtenerTodasPeliculas() {
        return productoRepository.findAllPeliculas().stream()
                .map(this::convertToPeliculaDTO)
                .collect(Collectors.toList());
    }

    private Set<Genero> validarYObternerGeneros(Set<Long> generosIds) {
        if (generosIds == null || generosIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto debe tener al menos un género");
        }

        List<Genero> generosList = generoRepository.findAllById(generosIds);
        if (generosList.size() != generosIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Algunos IDs de género no existen");
        }

        return new HashSet<>(generosList); // Convertir la List a Set
    }

    // Métodos de conversión actualizados
    private ProductoDTO convertToProductoDTO(Producto producto) {
        if (producto instanceof Libro) {
            return convertToLibroDTO((Libro) producto);
        } else if (producto instanceof Pelicula) {
            return convertToPeliculaDTO((Pelicula) producto);
        }
        throw new IllegalArgumentException("Tipo de producto no soportado");
    }

    private LibroDTO convertToLibroDTO(Libro libro) {
        Set<Long> generosIds = libro.getGeneros() != null ?
                libro.getGeneros().stream().map(Genero::getId).collect(Collectors.toSet()) :
                Set.of();

        return LibroDTO.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .sinopsis(libro.getSinopsis())
                .urlImagen(libro.getUrlImagen())
                .pais(libro.getPais())
                .estado(libro.getEstado())
                .generosIds(generosIds)
                .autor(libro.getAutor())
                .editorial(libro.getEditorial())
                .paginas(libro.getPaginas())
                .edicion(libro.getEdicion())
                .build();
    }

    private PeliculaDTO convertToPeliculaDTO(Pelicula pelicula) {
        Set<Long> generosIds = pelicula.getGeneros() != null ?
                pelicula.getGeneros().stream().map(Genero::getId).collect(Collectors.toSet()) :
                Set.of();

        return PeliculaDTO.builder()
                .id(pelicula.getId())
                .titulo(pelicula.getTitulo())
                .sinopsis(pelicula.getSinopsis())
                .urlImagen(pelicula.getUrlImagen())
                .pais(pelicula.getPais())
                .estado(pelicula.getEstado())
                .generosIds(generosIds)
                .director(pelicula.getDirector())
                .protagonista(pelicula.getProtagonista())
                .productora(pelicula.getProductora())
                .duracionMinutos(pelicula.getDuracionMinutos())
                .fechaEstreno(pelicula.getFechaEstreno())
                .build();
    }

    private Libro convertToLibroEntity(LibroDTO libroDTO) {
        return Libro.builder()
                .titulo(libroDTO.getTitulo())
                .sinopsis(libroDTO.getSinopsis())
                .urlImagen(libroDTO.getUrlImagen())
                .pais(libroDTO.getPais())
                .estado(libroDTO.getEstado())
                .autor(libroDTO.getAutor())
                .editorial(libroDTO.getEditorial())
                .paginas(libroDTO.getPaginas())
                .edicion(libroDTO.getEdicion())
                .build();
    }

    private Pelicula convertToPeliculaEntity(PeliculaDTO peliculaDTO) {
        return Pelicula.builder()
                .titulo(peliculaDTO.getTitulo())
                .sinopsis(peliculaDTO.getSinopsis())
                .urlImagen(peliculaDTO.getUrlImagen())
                .pais(peliculaDTO.getPais())
                .estado(peliculaDTO.getEstado())
                .director(peliculaDTO.getDirector())
                .protagonista(peliculaDTO.getProtagonista())
                .productora(peliculaDTO.getProductora())
                .duracionMinutos(peliculaDTO.getDuracionMinutos())
                .fechaEstreno(peliculaDTO.getFechaEstreno())
                .build();
    }
}