package com.recomendacion.repository;

import com.recomendacion.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Verifica si existe un género con el mismo nombre
    boolean existsByNombre(String nombre);

    // Encuentra un género por su nombre
    Genero findByNombre(String nombre);
}