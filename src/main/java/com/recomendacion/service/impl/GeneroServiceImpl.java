package com.recomendacion.service.impl;

import com.recomendacion.dto.GeneroDTO;
import com.recomendacion.model.Genero;
import com.recomendacion.repository.GeneroRepository;
import com.recomendacion.service.GeneroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroServiceImpl implements GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeneroDTO> obtenerTodos() {
        return generoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GeneroDTO crear(GeneroDTO generoDTO) {
        // Verificar si ya existe un género con la misma descripción
        if (generoRepository.existsByDescripcion(generoDTO.getDescripcion())) {
            throw new RuntimeException("Ya existe un género con esta descripción");
        }

        Genero genero = convertToEntity(generoDTO);
        Genero generoGuardado = generoRepository.save(genero);
        return convertToDTO(generoGuardado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        generoRepository.deleteById(id);
    }

    private GeneroDTO convertToDTO(Genero genero) {
        return GeneroDTO.builder()
                .id(genero.getId())
                .descripcion(genero.getDescripcion())
                .build();
    }

    private Genero convertToEntity(GeneroDTO generoDTO) {
        return Genero.builder()
                .id(generoDTO.getId())
                .descripcion(generoDTO.getDescripcion())
                .build();
    }
}