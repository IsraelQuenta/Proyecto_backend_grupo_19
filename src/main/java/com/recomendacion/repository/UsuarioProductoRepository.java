package com.recomendacion.repository;

import com.recomendacion.model.UsuarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioProductoRepository extends JpaRepository<UsuarioProducto, Long> {
    // Encuentra interacciones por usuario
    List<UsuarioProducto> findByUsuarioId(Long usuarioId);

    // Encuentra interacciones por producto
    List<UsuarioProducto> findByProductoId(Long productoId);

    // Encuentra interacciones por usuario y tipo de interacción
    List<UsuarioProducto> findByUsuarioIdAndTipoInteraccion(Long usuarioId, String tipoInteraccion);

    // Verifica si existe una interacción específica
    boolean existsByUsuarioIdAndProductoIdAndTipoInteraccion(Long usuarioId, Long productoId, String tipoInteraccion);

    // Cuenta las interacciones de un usuario con un producto
    @Query("SELECT COUNT(up) FROM UsuarioProducto up WHERE up.usuario.id = :usuarioId AND up.producto.id = :productoId")
    Long countInteraccionesUsuarioProducto(Long usuarioId, Long productoId);
}