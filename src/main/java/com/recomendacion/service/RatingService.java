package com.recomendacion.service;

import com.recomendacion.dto.RatingDTO;
<<<<<<< HEAD
=======
import java.math.BigDecimal;
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<RatingDTO> obtenerTodos();
    List<RatingDTO> obtenerPorUsuario(Long usuarioId);
    List<RatingDTO> obtenerPorProducto(Long productoId);
<<<<<<< HEAD
    Optional<RatingDTO> obtenerPorId(Long id); // Método añadido
    RatingDTO crear(RatingDTO ratingDTO);
    RatingDTO actualizar(Long id, RatingDTO ratingDTO);
    void eliminar(Long id);
    Double obtenerPromedioPorProducto(Long productoId);
=======
    RatingDTO crear(RatingDTO ratingDTO);
    Optional<RatingDTO> obtenerPorId(Long id);
    RatingDTO actualizar(Long id, RatingDTO ratingDTO);
    void eliminar(Long id);
    BigDecimal obtenerPromedioPorProducto(Long productoId); // Cambiado a BigDecimal
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    boolean existeRatingDeUsuarioParaProducto(Long usuarioId, Long productoId);
}