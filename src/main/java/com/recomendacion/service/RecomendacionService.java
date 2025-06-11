package com.recomendacion.service;

import com.recomendacion.dto.RecomendacionDTO;
import java.util.List;

public interface RecomendacionService {
<<<<<<< HEAD
    List<RecomendacionDTO> generarRecomendaciones(Long usuarioId);
    List<RecomendacionDTO> obtenerPorUsuario(Long usuarioId);
    RecomendacionDTO crear(RecomendacionDTO dto); // Método añadido
=======
    List<RecomendacionDTO> obtenerTodas();
    RecomendacionDTO obtenerPorId(Long id);
    List<RecomendacionDTO> generarRecomendaciones(Long usuarioId);
    List<RecomendacionDTO> obtenerPorUsuario(Long usuarioId);
    RecomendacionDTO crear(RecomendacionDTO dto);
    RecomendacionDTO actualizar(Long id, RecomendacionDTO dto);
    void eliminar(Long id);
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
}