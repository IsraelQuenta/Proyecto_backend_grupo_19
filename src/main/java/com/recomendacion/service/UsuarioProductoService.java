package com.recomendacion.service;

import com.recomendacion.dto.UsuarioProductoDTO;
import java.util.List;

public interface UsuarioProductoService {
    List<UsuarioProductoDTO> obtenerPorUsuario(Long usuarioId);
    UsuarioProductoDTO crear(UsuarioProductoDTO usuarioProductoDTO);
    void eliminar(Long id);
    UsuarioProductoDTO registrarInteraccion(Long usuarioId, Long productoId, String tipoInteraccion);
}