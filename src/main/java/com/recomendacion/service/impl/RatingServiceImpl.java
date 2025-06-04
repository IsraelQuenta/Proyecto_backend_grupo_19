package com.recomendacion.service.impl;

import com.recomendacion.dto.RatingDTO;
import com.recomendacion.model.Rating;
import com.recomendacion.repository.RatingRepository;
import com.recomendacion.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerTodos() {
        return ratingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerPorUsuario(Long usuarioId) {
        return ratingRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerPorProducto(Long productoId) {
        return ratingRepository.findByProductoId(productoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RatingDTO crear(RatingDTO ratingDTO) {
        Rating rating = convertToEntity(ratingDTO);
        rating.setFecha(LocalDateTime.now()); // Establecer la fecha actual
        Rating ratingGuardado = ratingRepository.save(rating);
        return convertToDTO(ratingGuardado);
    }

    //
    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDTO> obtenerPorId(Long id) {
        return ratingRepository.findById(id)
                .map(this::convertToDTO);
    }
    @Override
    @Transactional
    public RatingDTO actualizar(Long id, RatingDTO ratingDTO) {
        Rating ratingExistente = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating no encontrado"));

        ratingExistente.setValoracion(ratingDTO.getValoracion());
        ratingExistente.setComentario(ratingDTO.getComentario());
        // No actualizamos la fecha aquí para mantener la fecha original
        // No actualizamos usuario ni producto ya que son relaciones fijas

        Rating ratingActualizado = ratingRepository.save(ratingExistente);
        return convertToDTO(ratingActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Double obtenerPromedioPorProducto(Long productoId) {
        return ratingRepository.calcularPromedioRatingPorProducto(productoId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeRatingDeUsuarioParaProducto(Long usuarioId, Long productoId) {
        return ratingRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    private RatingDTO convertToDTO(Rating rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .valoracion(rating.getValoracion())
                .comentario(rating.getComentario())
                .fecha(rating.getFecha())
                .usuarioId(rating.getUsuario().getId())
                .productoId(rating.getProducto().getId())
                .build();
    }

    private Rating convertToEntity(RatingDTO ratingDTO) {
        return Rating.builder()
                .id(ratingDTO.getId())
                .valoracion(ratingDTO.getValoracion())
                .comentario(ratingDTO.getComentario())
                .fecha(ratingDTO.getFecha())
                // Nota: Usuario y Producto deben establecerse desde el controlador
                // usando sus respectivos servicios/repositorios
                .build();
    }
}