package com.recomendacion.service.impl;

<<<<<<< HEAD
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
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)

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
<<<<<<< HEAD
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
=======
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return convertToProductoDTO(producto);
    }

    @Override
    @Transactional
    public LibroDTO crearLibro(LibroDTO libroDTO) {
<<<<<<< HEAD
        Libro libro = convertToLibroEntity(libroDTO);
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        Libro libroGuardado = (Libro) productoRepository.save(libro);
        return convertToLibroDTO(libroGuardado);
    }

    @Override
    @Transactional
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO) {
<<<<<<< HEAD
        Pelicula pelicula = convertToPeliculaEntity(peliculaDTO);
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        Pelicula peliculaGuardada = (Pelicula) productoRepository.save(pelicula);
        return convertToPeliculaDTO(peliculaGuardada);
    }

    @Override
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO) {
<<<<<<< HEAD
        Libro libroExistente = (Libro) productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setEdicion(libroDTO.getEdicion());
        libroExistente.setEditorial(libroDTO.getEditorial());
        // Actualizar otros campos según sea necesario
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)

        Libro libroActualizado = (Libro) productoRepository.save(libroExistente);
        return convertToLibroDTO(libroActualizado);
    }

    @Override
    @Transactional
    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
<<<<<<< HEAD
        Pelicula peliculaExistente = (Pelicula) productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        peliculaExistente.setTitulo(peliculaDTO.getTitulo());
        peliculaExistente.setDirector(peliculaDTO.getDirector());
        peliculaExistente.setDuracionMinutos(peliculaDTO.getDuracionMinutos());
        peliculaExistente.setFechaEstreno(peliculaDTO.getFechaEstreno());
        // Actualizar otros campos según sea necesario
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)

        Pelicula peliculaActualizada = (Pelicula) productoRepository.save(peliculaExistente);
        return convertToPeliculaDTO(peliculaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
<<<<<<< HEAD
        productoRepository.deleteById(id);
    }

    // Métodos de conversión
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    private ProductoDTO convertToProductoDTO(Producto producto) {
        if (producto instanceof Libro) {
            return convertToLibroDTO((Libro) producto);
        } else if (producto instanceof Pelicula) {
            return convertToPeliculaDTO((Pelicula) producto);
        }
<<<<<<< HEAD
        return null;
    }

    private LibroDTO convertToLibroDTO(Libro libro) {
        return LibroDTO.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .autor(libro.getAutor())
                .edicion(libro.getEdicion()) 
                .editorial(libro.getEditorial())
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                .build();
    }

    private PeliculaDTO convertToPeliculaDTO(Pelicula pelicula) {
<<<<<<< HEAD
        return PeliculaDTO.builder()
                .id(pelicula.getId())
                .titulo(pelicula.getTitulo())
                .director(pelicula.getDirector())
=======
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
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                .duracionMinutos(pelicula.getDuracionMinutos())
                .fechaEstreno(pelicula.getFechaEstreno())
                .build();
    }

    private Libro convertToLibroEntity(LibroDTO libroDTO) {
        return Libro.builder()
<<<<<<< HEAD
                .id(libroDTO.getId())
                .titulo(libroDTO.getTitulo())
                .autor(libroDTO.getAutor())
                .edicion(libroDTO.getEdicion())
                .editorial(libroDTO.getEditorial())
=======
                .titulo(libroDTO.getTitulo())
                .sinopsis(libroDTO.getSinopsis())
                .urlImagen(libroDTO.getUrlImagen())
                .pais(libroDTO.getPais())
                .estado(libroDTO.getEstado())
                .autor(libroDTO.getAutor())
                .editorial(libroDTO.getEditorial())
                .paginas(libroDTO.getPaginas())
                .edicion(libroDTO.getEdicion())
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                .build();
    }

    private Pelicula convertToPeliculaEntity(PeliculaDTO peliculaDTO) {
        return Pelicula.builder()
<<<<<<< HEAD
                .id(peliculaDTO.getId())
                .titulo(peliculaDTO.getTitulo())
                .director(peliculaDTO.getDirector())
=======
                .titulo(peliculaDTO.getTitulo())
                .sinopsis(peliculaDTO.getSinopsis())
                .urlImagen(peliculaDTO.getUrlImagen())
                .pais(peliculaDTO.getPais())
                .estado(peliculaDTO.getEstado())
                .director(peliculaDTO.getDirector())
                .protagonista(peliculaDTO.getProtagonista())
                .productora(peliculaDTO.getProductora())
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                .duracionMinutos(peliculaDTO.getDuracionMinutos())
                .fechaEstreno(peliculaDTO.getFechaEstreno())
                .build();
    }
}