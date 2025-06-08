package com.recomendacion.service;

import com.recomendacion.dto.RecomendacionDTO;
import java.util.List;

public interface RecomendacionService {
    List<RecomendacionDTO> obtenerTodas();
    RecomendacionDTO obtenerPorId(Long id);
    List<RecomendacionDTO> generarRecomendaciones(Long usuarioId);
    List<RecomendacionDTO> obtenerPorUsuario(Long usuarioId);
    RecomendacionDTO crear(RecomendacionDTO dto);
    RecomendacionDTO actualizar(Long id, RecomendacionDTO dto);
    void eliminar(Long id);
}