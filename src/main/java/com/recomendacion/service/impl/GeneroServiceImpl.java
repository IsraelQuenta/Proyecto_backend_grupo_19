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

    public GeneroServiceImpl(GeneroRepository generoRepository) {  // Antes decía GeneroRepository
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
    @Transactional(readOnly = true)
    public GeneroDTO obtenerPorId(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado con id: " + id));
        return convertToDTO(genero);
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
    public GeneroDTO actualizar(Long id, GeneroDTO generoDTO) {
        Genero generoExistente = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado con id: " + id));

        // Verificar si la nueva descripción ya existe en otro género
        if (!generoExistente.getDescripcion().equals(generoDTO.getDescripcion())) {  // Aquí faltaba el paréntesis de cierre
            if (generoRepository.existsByDescripcion(generoDTO.getDescripcion())) {
                throw new RuntimeException("Ya existe un género con esta descripción");
            }
        }

        generoExistente.setDescripcion(generoDTO.getDescripcion());
        Genero generoActualizado = generoRepository.save(generoExistente);
        return convertToDTO(generoActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado con id: " + id));
        generoRepository.delete(genero);
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