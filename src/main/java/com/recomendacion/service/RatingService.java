package com.recomendacion.service;

import com.recomendacion.dto.RatingDTO;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<RatingDTO> obtenerTodos();
    List<RatingDTO> obtenerPorUsuario(Long usuarioId);
    List<RatingDTO> obtenerPorProducto(Long productoId);
    Optional<RatingDTO> obtenerPorId(Long id); // Método añadido
    RatingDTO crear(RatingDTO ratingDTO);
    RatingDTO actualizar(Long id, RatingDTO ratingDTO);
    void eliminar(Long id);
    Double obtenerPromedioPorProducto(Long productoId);
    boolean existeRatingDeUsuarioParaProducto(Long usuarioId, Long productoId);
}