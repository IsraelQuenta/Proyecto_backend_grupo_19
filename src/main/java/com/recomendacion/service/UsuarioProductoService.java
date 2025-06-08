package com.recomendacion.service;

import com.recomendacion.dto.UsuarioProductoDTO;
import java.util.List;

public interface UsuarioProductoService {
    List<UsuarioProductoDTO> obtenerTodos();
    UsuarioProductoDTO obtenerPorId(Long id);
    List<UsuarioProductoDTO> obtenerPorUsuario(Long usuarioId);
    List<UsuarioProductoDTO> obtenerPorProducto(Long productoId);
    List<UsuarioProductoDTO> obtenerPorUsuarioYTipo(Long usuarioId, String tipoInteraccion);
    UsuarioProductoDTO crear(UsuarioProductoDTO usuarioProductoDTO);
    UsuarioProductoDTO actualizar(UsuarioProductoDTO usuarioProductoDTO);
    void eliminar(Long id);
    UsuarioProductoDTO registrarInteraccion(Long usuarioId, Long productoId, String tipoInteraccion);
}