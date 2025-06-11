package com.recomendacion.service;

import com.recomendacion.dto.GeneroDTO;
import java.util.List;

public interface GeneroService {
    List<GeneroDTO> obtenerTodos();
<<<<<<< HEAD
    GeneroDTO crear(GeneroDTO generoDTO);
=======
    GeneroDTO obtenerPorId(Long id);
    GeneroDTO crear(GeneroDTO generoDTO);
    GeneroDTO actualizar(Long id, GeneroDTO generoDTO);
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    void eliminar(Long id);
}