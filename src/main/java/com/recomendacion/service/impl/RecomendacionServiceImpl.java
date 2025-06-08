package com.recomendacion.service.impl;

import com.recomendacion.dto.RecomendacionDTO;
import com.recomendacion.model.Recomendacion;
import com.recomendacion.registro.model.Usuario;
import com.recomendacion.model.Producto;
import com.recomendacion.registro.repository.UsuarioRepository;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.repository.RecomendacionRepository;
import com.recomendacion.service.RecomendacionService;
import com.recomendacion.validation.RecomendacionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacionServiceImpl implements RecomendacionService {

    private final RecomendacionRepository recomendacionRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecomendacionValidator validator;

    public RecomendacionServiceImpl(RecomendacionRepository recomendacionRepository,
                                    ProductoRepository productoRepository,
                                    UsuarioRepository usuarioRepository,
                                    RecomendacionValidator validator) {
        this.recomendacionRepository = recomendacionRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecomendacionDTO> obtenerTodas() {
        return recomendacionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecomendacionDTO obtenerPorId(Long id) {
        Recomendacion recomendacion = recomendacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recomendación no encontrada con ID: " + id));
        return convertToDTO(recomendacion);
    }

    @Override
    @Transactional
    public List<RecomendacionDTO> generarRecomendaciones(Long usuarioId) {
        List<Recomendacion> recomendaciones = recomendacionRepository
                .findTop10ByUsuarioIdOrderByFechaDesc(usuarioId);
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
        // Validación
        validator.normalizar(dto);
        List<String> errores = validator.validarParaCreacion(dto);
        if (!errores.isEmpty()) {
            throw new RuntimeException(String.join("; ", errores));
        }

        // Verificar duplicados
        boolean existeDuplicado = recomendacionRepository.existsByUsuarioIdAndProductoId(
                dto.getUsuarioId(), dto.getProductoId());
        errores = validator.validarDuplicado(dto, existeDuplicado);
        if (!errores.isEmpty()) {
            throw new RuntimeException(String.join("; ", errores));
        }

        // Obtener entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear y guardar la recomendación
        Recomendacion recomendacion = Recomendacion.builder()
                .usuario(usuario)
                .producto(producto)
                .algoritmo(dto.getAlgoritmo())
                .fecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now())
                .build();

        Recomendacion recomendacionGuardada = recomendacionRepository.save(recomendacion);
        return convertToDTO(recomendacionGuardada);
    }

    @Override
    @Transactional
    public RecomendacionDTO actualizar(Long id, RecomendacionDTO dto) {
        // Validación
        validator.normalizar(dto);
        List<String> errores = validator.validarParaActualizacion(dto);
        if (!errores.isEmpty()) {
            throw new RuntimeException(String.join("; ", errores));
        }

        // Verificar existencia
        Recomendacion recomendacionExistente = recomendacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recomendación no encontrada con ID: " + id));

        // Obtener entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar la recomendación
        recomendacionExistente.setUsuario(usuario);
        recomendacionExistente.setProducto(producto);
        recomendacionExistente.setAlgoritmo(dto.getAlgoritmo());
        recomendacionExistente.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());

        Recomendacion recomendacionActualizada = recomendacionRepository.save(recomendacionExistente);
        return convertToDTO(recomendacionActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!recomendacionRepository.existsById(id)) {
            throw new RuntimeException("Recomendación no encontrada con ID: " + id);
        }
        recomendacionRepository.deleteById(id);
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