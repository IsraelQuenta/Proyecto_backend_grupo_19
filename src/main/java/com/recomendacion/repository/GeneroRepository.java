package com.recomendacion.repository;

import com.recomendacion.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Verifica si existe un género con la misma descripción
    boolean existsByDescripcion(String descripcion);

    // Encuentra un género por su descripción
    Genero findByDescripcion(String descripcion);
}