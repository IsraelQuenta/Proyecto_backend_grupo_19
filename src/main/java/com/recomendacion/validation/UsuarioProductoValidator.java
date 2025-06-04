package com.recomendacion.validation;

import com.recomendacion.dto.UsuarioProductoDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioProductoValidator {

    private static final List<String> TIPOS_INTERACCION_VALIDOS = List.of(
        "VIEW", "LIKE", "PURCHASE", "CART", "WISHLIST", "REVIEW", "SHARE"
    );

    /**
     * Valida un UsuarioProductoDTO completo
     * @param dto El DTO a validar
     * @return Lista de errores de validación
     */
    public List<String> validar(UsuarioProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El UsuarioProductoDTO no puede ser nulo");
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
    private List<String> validarCamposRequeridos(UsuarioProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getUsuarioId() == null) {
            errores.add("El ID del usuario es requerido");
        }

        if (dto.getProductoId() == null) {
            errores.add("El ID del producto es requerido");
        }

        if (dto.getTipoInteraccion() == null || dto.getTipoInteraccion().trim().isEmpty()) {
            errores.add("El tipo de interacción es requerido");
        }

        if (dto.getFecha() == null) {
            errores.add("La fecha es requerida");
        }

        return errores;
    }

    /**
     * Valida el formato y rangos de los campos
     */
    private List<String> validarFormatos(UsuarioProductoDTO dto) {
        List<String> errores = new ArrayList<>();


        // Validar tipo de interacción
        if (dto.getTipoInteraccion() != null && !dto.getTipoInteraccion().trim().isEmpty()) {
            String tipoInteraccion = dto.getTipoInteraccion().trim().toUpperCase();
            if (!TIPOS_INTERACCION_VALIDOS.contains(tipoInteraccion)) {
                errores.add("Tipo de interacción inválido. Valores permitidos: " + 
                           String.join(", ", TIPOS_INTERACCION_VALIDOS));
            }
        }

        // Validar fecha
        if (dto.getFecha() != null) {
            LocalDateTime ahora = LocalDateTime.now();
            if (dto.getFecha().isAfter(ahora)) {
                errores.add("La fecha no puede ser futura");
            }
            
            // Validar que la fecha no sea muy antigua (ej: más de 10 años)
            LocalDateTime fechaMinima = ahora.minusYears(10);
            if (dto.getFecha().isBefore(fechaMinima)) {
                errores.add("La fecha no puede ser anterior a " + fechaMinima.toLocalDate());
            }
        }

        return errores;
    }


    /**
     * Valida solo para actualización (con ID)
     */
    public List<String> validarParaActualizacion(UsuarioProductoDTO dto) {
        List<String> errores = validar(dto);
        
        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }
        
        return errores;
    }

    /**
     * Verifica si el DTO es válido
     */
    public boolean esValido(UsuarioProductoDTO dto) {
        return validar(dto).isEmpty();
    }

    /**
     * Normaliza los datos del DTO (convierte tipo de interacción a mayúsculas)
     */
    public void normalizar(UsuarioProductoDTO dto) {
        if (dto != null && dto.getTipoInteraccion() != null) {
            dto.setTipoInteraccion(dto.getTipoInteraccion().trim().toUpperCase());
        }
    }
}