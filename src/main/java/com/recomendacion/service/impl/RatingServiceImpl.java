package com.recomendacion.service.impl;

import com.recomendacion.dto.RatingDTO;
import com.recomendacion.model.Rating;
import com.recomendacion.repository.RatingRepository;
import com.recomendacion.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Rating ratingGuardado = ratingRepository.save(rating);
        return convertToDTO(ratingGuardado);
    }

    @Override
    @Transactional
    public RatingDTO actualizar(Long id, RatingDTO ratingDTO) {
        Rating ratingExistente = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating no encontrado"));

        ratingExistente.setValoracion(ratingDTO.getValoracion());
        ratingExistente.setComentario(ratingDTO.getComentario());
        // Actualizar otros campos según sea necesario

        Rating ratingActualizado = ratingRepository.save(ratingExistente);
        return convertToDTO(ratingActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ratingRepository.deleteById(id);
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
                // Usuario y Producto se deben establecer desde el controlador
                .build();
    }
}