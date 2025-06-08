package com.recomendacion.service;

import com.recomendacion.dto.RatingDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<RatingDTO> obtenerTodos();
    List<RatingDTO> obtenerPorUsuario(Long usuarioId);
    List<RatingDTO> obtenerPorProducto(Long productoId);
    RatingDTO crear(RatingDTO ratingDTO);
    Optional<RatingDTO> obtenerPorId(Long id);
    RatingDTO actualizar(Long id, RatingDTO ratingDTO);
    void eliminar(Long id);
    BigDecimal obtenerPromedioPorProducto(Long productoId); // Cambiado a BigDecimal
    boolean existeRatingDeUsuarioParaProducto(Long usuarioId, Long productoId);
}