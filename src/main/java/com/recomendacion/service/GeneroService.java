package com.recomendacion.service;

import com.recomendacion.dto.GeneroDTO;
import java.util.List;

public interface GeneroService {
    List<GeneroDTO> obtenerTodos();
    GeneroDTO obtenerPorId(Long id);
    GeneroDTO crear(GeneroDTO generoDTO);
    GeneroDTO actualizar(Long id, GeneroDTO generoDTO);
    void eliminar(Long id);
}