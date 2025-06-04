package com.recomendacion.repository;

import com.recomendacion.model.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    // Encuentra recomendaciones por usuario
    List<Recomendacion> findByUsuarioId(Long usuarioId);

    // Encuentra las mejores recomendaciones para un usuario (ordenadas por puntuación)
    List<Recomendacion> findTop10ByUsuarioIdOrderByPuntuacionDesc(Long usuarioId);

    // Verifica si ya existe una recomendación para este usuario y producto
    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    // Obtiene recomendaciones por género preferido del usuario
    @Query("SELECT r FROM Recomendacion r JOIN r.producto p JOIN p.generos g " +
            "WHERE r.usuario.id = :usuarioId AND g.id = :generoId ORDER BY r.puntuacion DESC")
    List<Recomendacion> findByUsuarioIdAndGenero(Long usuarioId, Long generoId);
}