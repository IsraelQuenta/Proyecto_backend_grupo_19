package com.recomendacion.validation;

import com.recomendacion.dto.RecomendacionDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class RecomendacionValidator {

    private static final List<String> ALGORITMOS_VALIDOS = List.of(
        "COLABORATIVO", "CONTENIDO", "HIBRIDO"
    );

    private static final Pattern PATTERN_ALGORITMO = Pattern.compile("^(COLABORATIVO|CONTENIDO|HIBRIDO)$");

    /**
     * Valida un RecomendacionDTO completo
     * @param dto El DTO a validar
     * @return Lista de errores de validación
     */
    public List<String> validar(RecomendacionDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El RecomendacionDTO no puede ser nulo");
            return errores;
        }

        // Validar campos requeridos
        errores.addAll(validarCamposRequeridos(dto));
        
        // Validar formato y rangos
        errores.addAll(validarFormatos(dto));

        return errores;
    }

    /**
     * Valida que los campos requeridos estén presentes
     */
    private List<String> validarCamposRequeridos(RecomendacionDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getUsuarioId() == null) {
            errores.add("El ID de usuario es obligatorio");
        }

        if (dto.getProductoId() == null) {
            errores.add("El ID de producto es obligatorio");
        }

        if (dto.getAlgoritmo() == null || dto.getAlgoritmo().trim().isEmpty()) {
            errores.add("El algoritmo es obligatorio");
        }

        return errores;
    }

    /**
     * Valida el formato y rangos de los campos
     */
    private List<String> validarFormatos(RecomendacionDTO dto) {
        List<String> errores = new ArrayList<>();

        // Validar IDs positivos
        if (dto.getUsuarioId() != null && dto.getUsuarioId() <= 0) {
            errores.add("El ID del usuario debe ser mayor a 0");
        }

        if (dto.getProductoId() != null && dto.getProductoId() <= 0) {
            errores.add("El ID del producto debe ser mayor a 0");
        }

        // Validar algoritmo
        if (dto.getAlgoritmo() != null && !dto.getAlgoritmo().trim().isEmpty()) {
            String algoritmo = dto.getAlgoritmo().trim().toUpperCase();
            if (!ALGORITMOS_VALIDOS.contains(algoritmo)) {
                errores.add("Algoritmo no válido. Valores permitidos: " + 
                           String.join(", ", ALGORITMOS_VALIDOS));
            }
        }

        // Validar fecha
        if (dto.getFecha() != null) {
            LocalDateTime ahora = LocalDateTime.now();
            if (dto.getFecha().isAfter(ahora)) {
                errores.add("La fecha de recomendación no puede ser futura");
            }
            
            // Validar que la fecha no sea muy antigua (ej: más de 5 años para recomendaciones)
            LocalDateTime fechaMinima = ahora.minusYears(5);
            if (dto.getFecha().isBefore(fechaMinima)) {
                errores.add("La fecha de recomendación no puede ser anterior a " + fechaMinima.toLocalDate());
            }
        }

        return errores;
    }

    /**
     * Valida solo para creación (sin ID)
     */
    public List<String> validarParaCreacion(RecomendacionDTO dto) {
        List<String> errores = validar(dto);
        
        if (dto != null && dto.getId() != null) {
            errores.add("El ID debe ser nulo para crear una nueva recomendación");
        }
        
        // Para creación, si no se proporciona fecha, se puede asignar automáticamente
        if (dto != null && dto.getFecha() == null) {
            dto.setFecha(LocalDateTime.now());
        }
        
        return errores;
    }

    /**
     * Valida solo para actualización (con ID)
     */
    public List<String> validarParaActualizacion(RecomendacionDTO dto) {
        List<String> errores = validar(dto);
        
        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }
        
        return errores;
    }

    /**
     * Validación específica para verificar que no existe duplicado
     * (mismo usuario, producto y algoritmo)
     */
    public List<String> validarDuplicado(RecomendacionDTO dto, boolean existeDuplicado) {
        List<String> errores = new ArrayList<>();
        
        if (existeDuplicado) {
            errores.add("Ya existe una recomendación para este usuario, producto y algoritmo");
        }
        
        return errores;
    }

    /**
     * Validación de negocio: un usuario no debería tener la misma recomendación 
     * con diferentes algoritmos en un período muy corto
     */
    public List<String> validarReglaDeNegocio(RecomendacionDTO dto, 
                                            List<RecomendacionDTO> recomendacionesRecientes) {
        List<String> errores = new ArrayList<>();
        
        if (dto == null || recomendacionesRecientes == null) {
            return errores;
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime hace24Horas = ahora.minusHours(24);
        
        long recomendacionesMismoProducto = recomendacionesRecientes.stream()
            .filter(r -> r.getProductoId().equals(dto.getProductoId()))
            .filter(r -> r.getFecha() != null && r.getFecha().isAfter(hace24Horas))
            .count();
            
        if (recomendacionesMismoProducto > 0) {
            errores.add("Ya se generó una recomendación para este producto en las últimas 24 horas");
        }
        
        return errores;
    }

    /**
     * Verifica si el DTO es válido
     */
    public boolean esValido(RecomendacionDTO dto) {
        return validar(dto).isEmpty();
    }

    /**
     * Normaliza los datos del DTO
     */
    public void normalizar(RecomendacionDTO dto) {
        if (dto != null) {
            // Normalizar algoritmo a mayúsculas
            if (dto.getAlgoritmo() != null) {
                dto.setAlgoritmo(dto.getAlgoritmo().trim().toUpperCase());
            }
            
            // Si no hay fecha, asignar la actual
            if (dto.getFecha() == null) {
                dto.setFecha(LocalDateTime.now());
            }
        }
    }

    /**
     * Valida que el algoritmo sea compatible con los datos disponibles
     */
    public List<String> validarCompatibilidadAlgoritmo(RecomendacionDTO dto, 
                                                      boolean tieneHistorialUsuario,
                                                      boolean tieneMetadatosProducto) {
        List<String> errores = new ArrayList<>();
        
        if (dto == null || dto.getAlgoritmo() == null) {
            return errores;
        }
        
        String algoritmo = dto.getAlgoritmo().trim().toUpperCase();
        
        switch (algoritmo) {
            case "COLABORATIVO":
                if (!tieneHistorialUsuario) {
                    errores.add("El algoritmo COLABORATIVO requiere historial del usuario");
                }
                break;
            case "CONTENIDO":
                if (!tieneMetadatosProducto) {
                    errores.add("El algoritmo CONTENIDO requiere metadatos del producto");
                }
                break;
            case "HIBRIDO":
                if (!tieneHistorialUsuario && !tieneMetadatosProducto) {
                    errores.add("El algoritmo HIBRIDO requiere al menos historial del usuario o metadatos del producto");
                }
                break;
        }
        
        return errores;
    }
}