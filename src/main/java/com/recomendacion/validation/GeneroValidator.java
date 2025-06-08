
package com.recomendacion.validation;

import com.recomendacion.dto.GeneroDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class GeneroValidator implements Validator {

    // Géneros válidos definidos en el DTO
    private static final Set<String> GENEROS_VALIDOS = new HashSet<>(Arrays.asList(
        "Femenino", "Masculino", "Ciencia Ficción", "Terror", "Drama", "Comedia"
    ));
    
    // Géneros adicionales que podrían ser válidos en el futuro
    private static final Set<String> GENEROS_EXTENDIDOS = new HashSet<>(Arrays.asList(
        "Acción", "Aventura", "Romance", "Thriller", "Fantasía", "Documental", 
        "Animación", "Musical", "Western", "Biografía", "Historia", "Misterio",
        "Crimen", "Guerra", "Deportes", "Familia", "Superhéroes"
    ));

    private static final Pattern DESCRIPCION_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s]+$");
    private static final int MAX_DESCRIPCION_CHARACTERS = 200;
    private static final int MIN_DESCRIPCION_LENGTH = 2;

    @Override
    public boolean supports(Class<?> clazz) {
        return GeneroDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GeneroDTO genero = (GeneroDTO) target;

        // Validar descripción
        validateDescripcion(genero.getDescripcion(), errors);
        
        // Validar ID si es necesario
        validateId(genero.getId(), errors);
    }

    private void validateDescripcion(String descripcion, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", 
            "descripcion.required", "La descripción es obligatoria");
        
        if (descripcion != null) {
            String descripcionTrimmed = descripcion.trim();
            
            // Validar longitud máxima
            if (descripcionTrimmed.length() > MAX_DESCRIPCION_CHARACTERS) {
                errors.rejectValue("descripcion", "descripcion.maxlength", 
                    "La descripción no puede exceder " + MAX_DESCRIPCION_CHARACTERS + " caracteres");
            }
            
            // Validar longitud mínima
            if (descripcionTrimmed.length() < MIN_DESCRIPCION_LENGTH) {
                errors.rejectValue("descripcion", "descripcion.minlength", 
                    "La descripción debe tener al menos " + MIN_DESCRIPCION_LENGTH + " caracteres");
            }
            
            // Validar patrón (solo letras y espacios)
            if (!DESCRIPCION_PATTERN.matcher(descripcionTrimmed).matches()) {
                errors.rejectValue("descripcion", "descripcion.invalid", 
                    "La descripción solo puede contener letras y espacios");
            }
            
            // Validar géneros permitidos
            if (!GENEROS_VALIDOS.contains(descripcionTrimmed)) {
                errors.rejectValue("descripcion", "descripcion.genero.invalid", 
                    "Género no válido. Los géneros permitidos son: " + String.join(", ", GENEROS_VALIDOS));
            }
            
            // Validar que no contenga solo espacios
            if (descripcionTrimmed.replaceAll("\\s", "").isEmpty()) {
                errors.rejectValue("descripcion", "descripcion.empty", 
                    "La descripción no puede contener solo espacios");
            }
            
            // Validar capitalización correcta
            if (!isCorrectlyCapitalized(descripcionTrimmed)) {
                errors.rejectValue("descripcion", "descripcion.capitalization", 
                    "La descripción debe tener la capitalización correcta (primera letra de cada palabra en mayúscula)");
            }
        }
    }

    private void validateId(Long id, Errors errors) {
        // Validar ID solo si está presente (para actualizaciones)
        if (id != null && id <= 0) {
            errors.rejectValue("id", "id.invalid", 
                "El ID debe ser un número positivo");
        }
    }

    /**
     * Valida que la descripción tenga la capitalización correcta
     */
    private boolean isCorrectlyCapitalized(String descripcion) {
        String[] palabras = descripcion.split("\\s+");
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                // Primera letra debe ser mayúscula
                if (!Character.isUpperCase(palabra.charAt(0))) {
                    return false;
                }
                // Resto de letras deben ser minúsculas (excepto para casos especiales como "Ficción")
                for (int i = 1; i < palabra.length(); i++) {
                    char c = palabra.charAt(i);
                    if (Character.isLetter(c) && Character.isUpperCase(c)) {
                        // Permitir mayúsculas en el medio solo para casos específicos
                        if (!palabra.equals("Ficción")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Método auxiliar para validar reglas de negocio específicas
     */
    public void validateBusinessRules(GeneroDTO genero, Errors errors) {
        if (genero.getDescripcion() != null) {
            String descripcion = genero.getDescripcion().trim();
            
            // Validar coherencia en géneros de personas vs. géneros de contenido
            if (isGeneroPersona(descripcion) && genero.getId() != null) {
                // Si es un género de persona, validar que no se mezcle con géneros de contenido
                // Esta lógica dependería de tu modelo de datos específico
            }
            
            // Sugerir géneros similares si no es válido pero está en la lista extendida
            if (!GENEROS_VALIDOS.contains(descripcion) && GENEROS_EXTENDIDOS.contains(descripcion)) {
                errors.rejectValue("descripcion", "descripcion.genero.suggestion", 
                    "El género '" + descripcion + "' no está en la lista actual, pero podría añadirse en el futuro");
            }
            
            // Validar duplicados (esto requeriría acceso a la base de datos)
            // validateDuplicateGenero(descripcion, genero.getId(), errors);
        }
    }

    /**
     * Determina si el género corresponde a una persona (Femenino/Masculino)
     */
    private boolean isGeneroPersona(String descripcion) {
        return "Femenino".equals(descripcion) || "Masculino".equals(descripcion);
    }

    /**
     * Determina si el género corresponde a contenido audiovisual/literario
     */
    private boolean isGeneroContenido(String descripcion) {
        return !isGeneroPersona(descripcion);
    }

    /**
     * Método para obtener sugerencias de géneros válidos
     */
    public Set<String> getGenerosValidos() {
        return new HashSet<>(GENEROS_VALIDOS);
    }

    /**
     * Método para obtener géneros extendidos (para futuras implementaciones)
     */
    public Set<String> getGenerosExtendidos() {
        return new HashSet<>(GENEROS_EXTENDIDOS);
    }

    /**
     * Validación específica para operaciones de creación
     */
    public void validateForCreation(GeneroDTO genero, Errors errors) {
        validate(genero, errors);
        
        // El ID no debe estar presente en creación
        if (genero.getId() != null) {
            errors.rejectValue("id", "id.shouldbenull", 
                "El ID no debe especificarse al crear un nuevo género");
        }
    }

    /**
     * Validación específica para operaciones de actualización
     */
    public void validateForUpdate(GeneroDTO genero, Errors errors) {
        validate(genero, errors);
        
        // El ID debe estar presente en actualización
        if (genero.getId() == null) {
            errors.rejectValue("id", "id.required", 
                "El ID es obligatorio para actualizar un género");
        }
    }
}