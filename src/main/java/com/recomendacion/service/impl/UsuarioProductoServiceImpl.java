package com.recomendacion.service.impl;

import com.recomendacion.dto.UsuarioProductoDTO;
import com.recomendacion.model.UsuarioProducto;
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

    public UsuarioProductoServiceImpl(UsuarioProductoRepository usuarioProductoRepository) {
        this.usuarioProductoRepository = usuarioProductoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProductoDTO> obtenerPorUsuario(Long usuarioId) {
        return usuarioProductoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioProductoDTO crear(UsuarioProductoDTO usuarioProductoDTO) {
        UsuarioProducto interaccion = convertToEntity(usuarioProductoDTO);
        UsuarioProducto interaccionGuardada = usuarioProductoRepository.save(interaccion);
        return convertToDTO(interaccionGuardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioProductoRepository.deleteById(id);
    }


    @Override
    @Transactional
    public UsuarioProductoDTO registrarInteraccion(Long usuarioId, Long productoId, String tipoInteraccion) {
        // Validar que el usuario y producto existen
        // (deberías inyectar UserRepository y ProductoRepository para estas validaciones)

        UsuarioProductoDTO interaccionDTO = new UsuarioProductoDTO();
        interaccionDTO.setUsuarioId(usuarioId);
        interaccionDTO.setProductoId(productoId);
        interaccionDTO.setTipoInteraccion(tipoInteraccion);
        interaccionDTO.setFecha(LocalDateTime.now());

        return this.crear(interaccionDTO);
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
                // Usuario y Producto se deben establecer desde el controlador
                .build();
    }
}