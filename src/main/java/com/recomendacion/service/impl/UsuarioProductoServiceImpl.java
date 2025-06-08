package com.recomendacion.service.impl;

import com.recomendacion.dto.UsuarioProductoDTO;
import com.recomendacion.exception.NotFoundException;
import com.recomendacion.model.Producto;
import com.recomendacion.model.UsuarioProducto;
import com.recomendacion.registro.model.Usuario;
import com.recomendacion.registro.repository.UsuarioRepository;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.repository.UsuarioProductoRepository;
import com.recomendacion.service.UsuarioProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioProductoServiceImpl implements UsuarioProductoService {

    private final UsuarioProductoRepository usuarioProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    public UsuarioProductoServiceImpl(UsuarioProductoRepository usuarioProductoRepository,
                                      UsuarioRepository usuarioRepository,
                                      ProductoRepository productoRepository) {
        this.usuarioProductoRepository = usuarioProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProductoDTO> obtenerTodos() {
        return usuarioProductoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioProductoDTO obtenerPorId(Long id) {
        return usuarioProductoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NotFoundException("Interacción no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProductoDTO> obtenerPorUsuario(Long usuarioId) {
        return usuarioProductoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProductoDTO> obtenerPorProducto(Long productoId) {
        return usuarioProductoRepository.findByProductoId(productoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProductoDTO> obtenerPorUsuarioYTipo(Long usuarioId, String tipoInteraccion) {
        return usuarioProductoRepository.findByUsuarioIdAndTipoInteraccion(usuarioId, tipoInteraccion).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioProductoDTO crear(UsuarioProductoDTO usuarioProductoDTO) {
        // Validar y obtener las entidades relacionadas
        Usuario usuario = usuarioRepository.findById(usuarioProductoDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + usuarioProductoDTO.getUsuarioId()));

        Producto producto = productoRepository.findById(usuarioProductoDTO.getProductoId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + usuarioProductoDTO.getProductoId()));

        UsuarioProducto interaccion = new UsuarioProducto();
        interaccion.setUsuario(usuario);
        interaccion.setProducto(producto);
        interaccion.setTipoInteraccion(usuarioProductoDTO.getTipoInteraccion());
        interaccion.setFecha(LocalDateTime.now());

        UsuarioProducto interaccionGuardada = usuarioProductoRepository.save(interaccion);
        return convertToDTO(interaccionGuardada);
    }

    @Override
    @Transactional
    public UsuarioProductoDTO actualizar(UsuarioProductoDTO usuarioProductoDTO) {
        UsuarioProducto interaccion = usuarioProductoRepository.findById(usuarioProductoDTO.getId())
                .orElseThrow(() -> new NotFoundException("Interacción no encontrada con ID: " + usuarioProductoDTO.getId()));

        interaccion.setTipoInteraccion(usuarioProductoDTO.getTipoInteraccion());
        // No actualizamos usuario ni producto para mantener la integridad de la relación
        // No actualizamos la fecha para mantener el registro original

        UsuarioProducto interaccionActualizada = usuarioProductoRepository.save(interaccion);
        return convertToDTO(interaccionActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioProductoRepository.existsById(id)) {
            throw new NotFoundException("Interacción no encontrada con ID: " + id);
        }
        usuarioProductoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UsuarioProductoDTO registrarInteraccion(Long usuarioId, Long productoId, String tipoInteraccion) {
        // Validar y obtener las entidades relacionadas
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + usuarioId));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + productoId));

        UsuarioProducto interaccion = new UsuarioProducto();
        interaccion.setUsuario(usuario);
        interaccion.setProducto(producto);
        interaccion.setTipoInteraccion(tipoInteraccion);
        interaccion.setFecha(LocalDateTime.now());

        UsuarioProducto interaccionGuardada = usuarioProductoRepository.save(interaccion);
        return convertToDTO(interaccionGuardada);
    }

    private UsuarioProductoDTO convertToDTO(UsuarioProducto usuarioProducto) {
        return UsuarioProductoDTO.builder()
                .id(usuarioProducto.getId())
                .usuarioId(usuarioProducto.getUsuario().getId())
                .productoId(usuarioProducto.getProducto().getId())
                .tipoInteraccion(usuarioProducto.getTipoInteraccion())
                .fecha(usuarioProducto.getFecha())
                .build();
    }

    private UsuarioProducto convertToEntity(UsuarioProductoDTO usuarioProductoDTO) {
        return UsuarioProducto.builder()
                .id(usuarioProductoDTO.getId())
                .tipoInteraccion(usuarioProductoDTO.getTipoInteraccion())
                .fecha(usuarioProductoDTO.getFecha())
                // Usuario y Producto se deben establecer desde el controlador o servicio
                // usando sus respectivos repositorios
                .build();
    }
}