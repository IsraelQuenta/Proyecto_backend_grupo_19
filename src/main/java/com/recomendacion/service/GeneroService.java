package com.recomendacion.service;

import com.recomendacion.dto.GeneroDTO;
import java.util.List;

public interface GeneroService {
    List<GeneroDTO> obtenerTodos();
    GeneroDTO crear(GeneroDTO generoDTO);
    void eliminar(Long id);
}