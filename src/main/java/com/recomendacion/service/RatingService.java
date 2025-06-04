package com.recomendacion.service;

import com.recomendacion.dto.RatingDTO;
import java.util.List;

public interface RatingService {
    List<RatingDTO> obtenerTodos();
    List<RatingDTO> obtenerPorUsuario(Long usuarioId);
    List<RatingDTO> obtenerPorProducto(Long productoId);
    RatingDTO crear(RatingDTO ratingDTO);
    RatingDTO actualizar(Long id, RatingDTO ratingDTO);
    void eliminar(Long id);
}
