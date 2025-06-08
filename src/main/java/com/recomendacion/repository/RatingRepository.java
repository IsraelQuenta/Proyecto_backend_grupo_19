package com.recomendacion.repository;

import com.recomendacion.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Encuentra ratings por usuario
    List<Rating> findByUsuarioId(Long usuarioId);

    // Encuentra ratings por producto
    List<Rating> findByProductoId(Long productoId);

    // Encuentra ratings por usuario y producto
    Rating findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    // Calcula el promedio de ratings para un producto
    @Query("SELECT AVG(r.valoracion) FROM Rating r WHERE r.producto.id = :productoId")
    Double calcularPromedioRatingPorProducto(Long productoId);

    // Verifica si un usuario ya ha valorado un producto
    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
}