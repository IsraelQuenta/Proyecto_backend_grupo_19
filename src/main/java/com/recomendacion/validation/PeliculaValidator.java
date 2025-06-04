package com.recomendacion.validation;

import com.recomendacion.dto.PeliculaDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class PeliculaValidator implements Validator {

    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s.'-]+$");
    private static final int MIN_DURACION = 1;
    private static final int MAX_DURACION = 600; // 10 horas máximo
    private static final int MIN_YEAR = 1888; // Año de la primera película
    private static final int MAX_CHARACTERS = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return PeliculaDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PeliculaDTO pelicula = (PeliculaDTO) target;

        // Validar director
        validateDirector(pelicula.getDirector(), errors);
        
        // Validar protagonista
        validateProtagonista(pelicula.getProtagonista(), errors);
        
        // Validar productora
        validateProductora(pelicula.getProductora(), errors);
        
        // Validar duración
        validateDuracion(pelicula.getDuracionMinutos(), errors);
        
        // Validar fecha de estreno
        validateFechaEstreno(pelicula.getFechaEstreno(), errors);
    }

    private void validateDirector(String director, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "director", 
            "director.required", "El director es obligatorio");
        
        if (director != null) {
            if (director.trim().length() > MAX_CHARACTERS) {
                errors.rejectValue("director", "director.maxlength", 
                    "El director no puede exceder " + MAX_CHARACTERS + " caracteres");
            }
            
            if (!NOMBRE_PATTERN.matcher(director.trim()).matches()) {
                errors.rejectValue("director", "director.invalid", 
                    "El director solo puede contener letras, espacios, puntos, apostrofes y guiones");
            }
            
            if (director.trim().length() < 2) {
                errors.rejectValue("director", "director.minlength", 
                    "El director debe tener al menos 2 caracteres");
            }
        }
    }

    private void validateProtagonista(String protagonista, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "protagonista", 
            "protagonista.required", "El protagonista es obligatorio");
        
        if (protagonista != null) {
            if (protagonista.trim().length() > MAX_CHARACTERS) {
                errors.rejectValue("protagonista", "protagonista.maxlength", 
                    "El protagonista no puede exceder " + MAX_CHARACTERS + " caracteres");
            }
            
            if (!NOMBRE_PATTERN.matcher(protagonista.trim()).matches()) {
                errors.rejectValue("protagonista", "protagonista.invalid", 
                    "El protagonista solo puede contener letras, espacios, puntos, apostrofes y guiones");
            }
            
            if (protagonista.trim().length() < 2) {
                errors.rejectValue("protagonista", "protagonista.minlength", 
                    "El protagonista debe tener al menos 2 caracteres");
            }
        }
    }

    private void validateProductora(String productora, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productora", 
            "productora.required", "La productora es obligatoria");
        
        if (productora != null) {
            if (productora.trim().length() > MAX_CHARACTERS) {
                errors.rejectValue("productora", "productora.maxlength", 
                    "La productora no puede exceder " + MAX_CHARACTERS + " caracteres");
            }
            
            if (productora.trim().length() < 2) {
                errors.rejectValue("productora", "productora.minlength", 
                    "La productora debe tener al menos 2 caracteres");
            }
        }
    }

    private void validateDuracion(Integer duracionMinutos, Errors errors) {
        if (duracionMinutos == null) {
            errors.rejectValue("duracionMinutos", "duracion.required", 
                "La duración es obligatoria");
        } else {
            if (duracionMinutos < MIN_DURACION) {
                errors.rejectValue("duracionMinutos", "duracion.min", 
                    "La duración mínima es " + MIN_DURACION + " minuto");
            }
            
            if (duracionMinutos > MAX_DURACION) {
                errors.rejectValue("duracionMinutos", "duracion.max", 
                    "La duración máxima es " + MAX_DURACION + " minutos");
            }
        }
    }

    private void validateFechaEstreno(LocalDate fechaEstreno, Errors errors) {
        if (fechaEstreno == null) {
            errors.rejectValue("fechaEstreno", "fecha.required", 
                "La fecha de estreno es obligatoria");
        } else {
            LocalDate now = LocalDate.now();
            LocalDate minDate = LocalDate.of(MIN_YEAR, 1, 1);
            
            if (fechaEstreno.isAfter(now)) {
                errors.rejectValue("fechaEstreno", "fecha.future", 
                    "La fecha de estreno no puede ser futura");
            }
            
            if (fechaEstreno.isBefore(minDate)) {
                errors.rejectValue("fechaEstreno", "fecha.tooold", 
                    "La fecha de estreno no puede ser anterior a " + MIN_YEAR);
            }
        }
    }

    /**
     * Método auxiliar para validar múltiples campos relacionados
     */
    public void validateBusinessRules(PeliculaDTO pelicula, Errors errors) {
        // Validar que director y protagonista no sean la misma persona
        if (pelicula.getDirector() != null && pelicula.getProtagonista() != null) {
            if (pelicula.getDirector().trim().equalsIgnoreCase(pelicula.getProtagonista().trim())) {
                errors.rejectValue("protagonista", "protagonista.sameasdirector", 
                    "El protagonista y el director no pueden ser la misma persona");
            }
        }
        
        // Validar duración según el año de estreno (películas muy antiguas tienden a ser más cortas)
        if (pelicula.getFechaEstreno() != null && pelicula.getDuracionMinutos() != null) {
            int year = pelicula.getFechaEstreno().getYear();
            if (year < 1930 && pelicula.getDuracionMinutos() > 180) {
                errors.rejectValue("duracionMinutos", "duracion.inconsistent", 
                    "La duración parece inconsistente para una película tan antigua");
            }
        }
    }
}