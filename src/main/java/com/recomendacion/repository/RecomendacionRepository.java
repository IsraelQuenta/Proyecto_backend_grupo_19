package com.recomendacion.repository;

import com.recomendacion.model.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByUsuarioId(Long usuarioId);
    List<Recomendacion> findTop10ByUsuarioIdOrderByFechaDesc(Long usuarioId);
    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    @Query("SELECT r FROM Recomendacion r JOIN r.producto p JOIN p.generos g " +
            "WHERE r.usuario.id = :usuarioId AND g.id = :generoId ORDER BY r.fecha DESC")
    List<Recomendacion> findByUsuarioIdAndGenero(Long usuarioId, Long generoId);
}