package com.recomendacion.service.impl;

import com.recomendacion.dto.RecomendacionDTO;
import com.recomendacion.model.Recomendacion;
import com.recomendacion.registro.model.Usuario;
import com.recomendacion.model.Producto;
import com.recomendacion.registro.repository.UsuarioRepository;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.repository.RecomendacionRepository;
import com.recomendacion.service.RecomendacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacionServiceImpl implements RecomendacionService {

//    private final RecomendacionRepository recomendacionRepository;
//
//    public RecomendacionServiceImpl(RecomendacionRepository recomendacionRepository) {
//        this.recomendacionRepository = recomendacionRepository;
//    }

    private final RecomendacionRepository recomendacionRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public RecomendacionServiceImpl(RecomendacionRepository recomendacionRepository,
                                    ProductoRepository productoRepository,
                                    UsuarioRepository usuarioRepository) {
        this.recomendacionRepository = recomendacionRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    @Transactional
    public List<RecomendacionDTO> generarRecomendaciones(Long usuarioId) {
        // Lógica para generar recomendaciones
        List<Recomendacion> recomendaciones = recomendacionRepository
                .findTop10ByUsuarioIdOrderByFechaDesc(usuarioId); // Cambiado de puntuacion a fecha

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

    @Override
    @Transactional
    public RecomendacionDTO crear(RecomendacionDTO dto) {
        // Obtener las entidades completas desde los repositorios
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Recomendacion recomendacion = Recomendacion.builder()
                .usuario(usuario)
                .producto(producto)
                .algoritmo(dto.getAlgoritmo())
                .fecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now())
                .build();

        Recomendacion recomendacionGuardada = recomendacionRepository.save(recomendacion);
        return convertToDTO(recomendacionGuardada);
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