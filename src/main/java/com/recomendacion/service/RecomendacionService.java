package com.recomendacion.service;

import com.recomendacion.dto.RecomendacionDTO;
import java.util.List;

public interface RecomendacionService {
    List<RecomendacionDTO> generarRecomendaciones(Long usuarioId);
    List<RecomendacionDTO> obtenerPorUsuario(Long usuarioId);
    RecomendacionDTO crear(RecomendacionDTO dto); // Método añadido
}