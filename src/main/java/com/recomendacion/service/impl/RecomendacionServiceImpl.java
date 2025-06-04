package com.recomendacion.service.impl;

import com.recomendacion.dto.RecomendacionDTO;
import com.recomendacion.model.Recomendacion;
import com.recomendacion.repository.RecomendacionRepository;
import com.recomendacion.service.RecomendacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacionServiceImpl implements RecomendacionService {

    private final RecomendacionRepository recomendacionRepository;

    public RecomendacionServiceImpl(RecomendacionRepository recomendacionRepository) {
        this.recomendacionRepository = recomendacionRepository;
    }

    @Override
    @Transactional
    public List<RecomendacionDTO> generarRecomendaciones(Long usuarioId) {
        // Lógica para generar recomendaciones basadas en:
        // - Historial de ratings del usuario
        // - Géneros preferidos
        // - Productos similares
        // - Popularidad

        // Esto es un ejemplo simplificado
        List<Recomendacion> recomendaciones = recomendacionRepository
                .findTop10ByUsuarioIdOrderByPuntuacionDesc(usuarioId);

        return recomendaciones.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecomendacionDTO> obtenerPorUsuario(Long usuarioId) {
        return recomendacionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RecomendacionDTO convertToDTO(Recomendacion recomendacion) {
        return RecomendacionDTO.builder()
                .id(recomendacion.getId())
                .usuarioId(recomendacion.getUsuario().getId())
                .productoId(recomendacion.getProducto().getId())
                .algoritmo(recomendacion.getAlgoritmo())
                .fecha(recomendacion.getFecha())
                .build();
    }
}