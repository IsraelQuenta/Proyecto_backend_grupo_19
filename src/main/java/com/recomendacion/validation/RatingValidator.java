package com.recomendacion.validation;

import com.recomendacion.dto.RatingDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class RatingValidator {

<<<<<<< HEAD
    private static final double VALORACION_MINIMA = 0.5;
    private static final double VALORACION_MAXIMA = 5.0;
    private static final int LONGITUD_MAXIMA_COMENTARIO = 500;
    private static final double[] VALORACIONES_VALIDAS = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
    
    // Pattern para detectar contenido ofensivo básico
    private static final Pattern PATTERN_CONTENIDO_OFENSIVO = Pattern.compile(
        "(?i).*(idiota|estúpido|basura|mierda|odio|horrible|terrible|pésimo|malo|fatal).*"
    );

    /**
     * Valida un RatingDTO completo
     * @param dto El DTO a validar
     * @return Lista de errores de validación
     */
=======
    private static final BigDecimal VALORACION_MINIMA = BigDecimal.ONE;
    private static final BigDecimal VALORACION_MAXIMA = new BigDecimal("5.0");
    private static final int LONGITUD_MAXIMA_COMENTARIO = 500;
    private static final BigDecimal[] VALORACIONES_VALIDAS = {
            BigDecimal.ONE,
            new BigDecimal("2.0"),
            new BigDecimal("3.0"),
            new BigDecimal("4.0"),
            new BigDecimal("5.0")
    };

    private static final Pattern PATTERN_CONTENIDO_OFENSIVO = Pattern.compile(
            "(?i).*(idiota|estúpido|basura|mierda|odio|horrible|terrible|pésimo|malo|fatal).*"
    );

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    public List<String> validar(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El RatingDTO no puede ser nulo");
            return errores;
        }

<<<<<<< HEAD
        // Validar campos requeridos
        errores.addAll(validarCamposRequeridos(dto));
        
        // Validar formato y rangos
        errores.addAll(validarFormatos(dto));
        
        // Validar contenido del comentario
=======
        errores.addAll(validarCamposRequeridos(dto));
        errores.addAll(validarFormatos(dto));
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        errores.addAll(validarComentario(dto));

        return errores;
    }

<<<<<<< HEAD
    /**
     * Valida que los campos requeridos estén presentes
     */
=======
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    private List<String> validarCamposRequeridos(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getUsuarioId() == null) {
            errores.add("El ID de usuario es obligatorio");
        }

        if (dto.getProductoId() == null) {
            errores.add("El ID de producto es obligatorio");
        }

        if (dto.getValoracion() == null) {
            errores.add("La valoración es obligatoria");
        }

        return errores;
    }

<<<<<<< HEAD
    /**
     * Valida el formato y rangos de los campos
     */
    private List<String> validarFormatos(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

        // Validar IDs positivos
=======
    private List<String> validarFormatos(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        if (dto.getUsuarioId() != null && dto.getUsuarioId() <= 0) {
            errores.add("El ID del usuario debe ser mayor a 0");
        }

        if (dto.getProductoId() != null && dto.getProductoId() <= 0) {
            errores.add("El ID del producto debe ser mayor a 0");
        }

<<<<<<< HEAD
        // Validar valoración
        if (dto.getValoracion() != null) {
            if (dto.getValoracion() < VALORACION_MINIMA) {
                errores.add("La valoración mínima es " + VALORACION_MINIMA);
            }
            if (dto.getValoracion() > VALORACION_MAXIMA) {
                errores.add("La valoración máxima es " + VALORACION_MAXIMA);
            }
            
            // Validar que la valoración sea en incrementos de 0.5
            if (!esValoracionValida(dto.getValoracion())) {
                errores.add("La valoración debe ser en incrementos de 0.5 (ej: 1.0, 1.5, 2.0, etc.)");
            }
        }

        // Validar fecha
=======
        if (dto.getValoracion() != null) {
            if (dto.getValoracion().compareTo(VALORACION_MINIMA) < 0) {
                errores.add("La valoración mínima es " + VALORACION_MINIMA);
            }
            if (dto.getValoracion().compareTo(VALORACION_MAXIMA) > 0) {
                errores.add("La valoración máxima es " + VALORACION_MAXIMA);
            }

            if (!esValoracionValida(dto.getValoracion())) {
                errores.add("La valoración debe ser un número entero entre 1 y 5");
            }
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        if (dto.getFecha() != null) {
            LocalDateTime ahora = LocalDateTime.now();
            if (dto.getFecha().isAfter(ahora)) {
                errores.add("La fecha del rating no puede ser futura");
            }
<<<<<<< HEAD
            
            // Validar que la fecha no sea muy antigua (ej: más de 10 años)
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            LocalDateTime fechaMinima = ahora.minusYears(10);
            if (dto.getFecha().isBefore(fechaMinima)) {
                errores.add("La fecha del rating no puede ser anterior a " + fechaMinima.toLocalDate());
            }
        }

        return errores;
    }

<<<<<<< HEAD
    /**
     * Valida el contenido del comentario
     */
=======
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    private List<String> validarComentario(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getComentario() != null) {
            String comentario = dto.getComentario().trim();
<<<<<<< HEAD
            
            // Validar longitud
            if (comentario.length() > LONGITUD_MAXIMA_COMENTARIO) {
                errores.add("El comentario no puede exceder " + LONGITUD_MAXIMA_COMENTARIO + " caracteres");
            }
            
            // Validar contenido mínimo si hay comentario
            if (!comentario.isEmpty() && comentario.length() < 10) {
                errores.add("El comentario debe tener al menos 10 caracteres si se proporciona");
            }
            
            // Validar que no sea solo espacios o caracteres especiales
            if (!comentario.isEmpty() && !comentario.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*")) {
                errores.add("El comentario debe contener al menos una letra");
            }
            
            // Validar contenido ofensivo básico
=======

            if (comentario.length() > LONGITUD_MAXIMA_COMENTARIO) {
                errores.add("El comentario no puede exceder " + LONGITUD_MAXIMA_COMENTARIO + " caracteres");
            }

            if (!comentario.isEmpty() && comentario.length() < 10) {
                errores.add("El comentario debe tener al menos 10 caracteres si se proporciona");
            }

            if (!comentario.isEmpty() && !comentario.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*")) {
                errores.add("El comentario debe contener al menos una letra");
            }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            if (PATTERN_CONTENIDO_OFENSIVO.matcher(comentario).matches()) {
                errores.add("El comentario contiene lenguaje inapropiado");
            }
        }

        return errores;
    }

<<<<<<< HEAD
    /**
     * Verifica si la valoración es válida (incrementos de 0.5)
     */
    private boolean esValoracionValida(Double valoracion) {
        for (double valorValida : VALORACIONES_VALIDAS) {
            if (Math.abs(valoracion - valorValida) < 0.01) {
=======
    private boolean esValoracionValida(BigDecimal valoracion) {
        for (BigDecimal valorValida : VALORACIONES_VALIDAS) {
            if (valoracion.compareTo(valorValida) == 0) {
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                return true;
            }
        }
        return false;
    }

<<<<<<< HEAD
    /**
     * Valida solo para creación (sin ID)
     */
    public List<String> validarParaCreacion(RatingDTO dto) {
        List<String> errores = validar(dto);
        
        if (dto != null && dto.getId() != null) {
            errores.add("El ID debe ser nulo para crear un nuevo rating");
        }
        
        // Para creación, si no se proporciona fecha, se asigna automáticamente
        if (dto != null && dto.getFecha() == null) {
            dto.setFecha(LocalDateTime.now());
        }
        
        return errores;
    }

    /**
     * Valida solo para actualización (con ID)
     */
    public List<String> validarParaActualizacion(RatingDTO dto) {
        List<String> errores = validar(dto);
        
        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }
        
        return errores;
    }

    /**
     * Validación para prevenir ratings duplicados del mismo usuario para el mismo producto
     */
    public List<String> validarDuplicado(RatingDTO dto, boolean existeRatingPrevio) {
        List<String> errores = new ArrayList<>();
        
        if (existeRatingPrevio) {
            errores.add("El usuario ya ha valorado este producto");
        }
        
        return errores;
    }

    /**
     * Validación de coherencia entre valoración y comentario
     */
    public List<String> validarCoherencia(RatingDTO dto) {
        List<String> errores = new ArrayList<>();
        
        if (dto == null || dto.getValoracion() == null || dto.getComentario() == null) {
            return errores;
        }
        
        String comentario = dto.getComentario().trim().toLowerCase();
        Double valoracion = dto.getValoracion();
        
        // Validar coherencia básica
        if (valoracion >= 4.0 && comentario.contains("malo")) {
            errores.add("La valoración alta no es coherente con un comentario negativo");
        }
        
        if (valoracion <= 2.0 && (comentario.contains("excelente") || comentario.contains("perfecto"))) {
            errores.add("La valoración baja no es coherente con un comentario muy positivo");
        }
        
        return errores;
    }

    /**
     * Validación de límite de ratings por usuario en un período
     */
    public List<String> validarLimiteRatings(RatingDTO dto, int ratingsHoy, int limiteRatingsDiarios) {
        List<String> errores = new ArrayList<>();
        
        if (ratingsHoy >= limiteRatingsDiarios) {
            errores.add("Ha alcanzado el límite de " + limiteRatingsDiarios + " valoraciones por día");
        }
        
        return errores;
    }

    /**
     * Verifica si el DTO es válido
     */
=======
    public List<String> validarParaCreacion(RatingDTO dto) {
        List<String> errores = validar(dto);

        if (dto != null && dto.getId() != null) {
            errores.add("El ID debe ser nulo para crear un nuevo rating");
        }

        if (dto != null && dto.getFecha() == null) {
            dto.setFecha(LocalDateTime.now());
        }

        return errores;
    }

    public List<String> validarParaActualizacion(RatingDTO dto) {
        List<String> errores = validar(dto);

        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }

        return errores;
    }

    public List<String> validarDuplicado(RatingDTO dto, boolean existeRatingPrevio) {
        List<String> errores = new ArrayList<>();

        if (existeRatingPrevio) {
            errores.add("El usuario ya ha valorado este producto");
        }

        return errores;
    }

    public List<String> validarCoherencia(RatingDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null || dto.getValoracion() == null || dto.getComentario() == null) {
            return errores;
        }

        String comentario = dto.getComentario().trim().toLowerCase();
        BigDecimal valoracion = dto.getValoracion();

        if (valoracion.compareTo(new BigDecimal("4.0")) >= 0 && comentario.contains("malo")) {
            errores.add("La valoración alta no es coherente con un comentario negativo");
        }

        if (valoracion.compareTo(new BigDecimal("2.0")) <= 0 &&
                (comentario.contains("excelente") || comentario.contains("perfecto"))) {
            errores.add("La valoración baja no es coherente con un comentario muy positivo");
        }

        return errores;
    }

    public List<String> validarLimiteRatings(RatingDTO dto, int ratingsHoy, int limiteRatingsDiarios) {
        List<String> errores = new ArrayList<>();

        if (ratingsHoy >= limiteRatingsDiarios) {
            errores.add("Ha alcanzado el límite de " + limiteRatingsDiarios + " valoraciones por día");
        }

        return errores;
    }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
    public boolean esValido(RatingDTO dto) {
        return validar(dto).isEmpty();
    }

<<<<<<< HEAD
    /**
     * Normaliza los datos del DTO
     */
    public void normalizar(RatingDTO dto) {
        if (dto != null) {
            // Redondear valoración a incrementos de 0.5
            if (dto.getValoracion() != null) {
                BigDecimal valoracion = BigDecimal.valueOf(dto.getValoracion());
                valoracion = valoracion.multiply(BigDecimal.valueOf(2))
                                     .setScale(0, RoundingMode.HALF_UP)
                                     .divide(BigDecimal.valueOf(2));
                dto.setValoracion(valoracion.doubleValue());
            }
            
            // Limpiar comentario
            if (dto.getComentario() != null) {
                dto.setComentario(dto.getComentario().trim());
                // Si el comentario queda vacío después del trim, establecerlo como null
=======
    public void normalizar(RatingDTO dto) {
        if (dto != null) {
            if (dto.getValoracion() != null) {
                // Redondear a valor entero (1, 2, 3, 4, 5)
                dto.setValoracion(new BigDecimal(dto.getValoracion().intValue()));
            }

            if (dto.getComentario() != null) {
                dto.setComentario(dto.getComentario().trim());
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                if (dto.getComentario().isEmpty()) {
                    dto.setComentario(null);
                }
            }
<<<<<<< HEAD
            
            // Si no hay fecha, asignar la actual
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            if (dto.getFecha() == null) {
                dto.setFecha(LocalDateTime.now());
            }
        }
    }

<<<<<<< HEAD
    /**
     * Obtiene sugerencias para mejorar el rating
     */
    public List<String> obtenerSugerencias(RatingDTO dto) {
        List<String> sugerencias = new ArrayList<>();
        
        if (dto == null) {
            return sugerencias;
        }
        
        // Sugerir comentario si la valoración es extrema
        if (dto.getValoracion() != null && dto.getComentario() == null) {
            if (dto.getValoracion() <= 2.0) {
                sugerencias.add("Considera agregar un comentario explicando por qué la valoración es baja");
            } else if (dto.getValoracion() >= 4.5) {
                sugerencias.add("Considera agregar un comentario destacando lo que más te gustó");
            }
        }
        
        // Sugerir más detalle en comentarios muy cortos
        if (dto.getComentario() != null && dto.getComentario().trim().length() < 20) {
            sugerencias.add("Considera agregar más detalles a tu comentario para ayudar a otros usuarios");
        }
        
=======
    public List<String> obtenerSugerencias(RatingDTO dto) {
        List<String> sugerencias = new ArrayList<>();

        if (dto == null) {
            return sugerencias;
        }

        if (dto.getValoracion() != null && dto.getComentario() == null) {
            if (dto.getValoracion().compareTo(new BigDecimal("2.0")) <= 0) {
                sugerencias.add("Considera agregar un comentario explicando por qué la valoración es baja");
            } else if (dto.getValoracion().compareTo(new BigDecimal("4.5")) >= 0) {
                sugerencias.add("Considera agregar un comentario destacando lo que más te gustó");
            }
        }

        if (dto.getComentario() != null && dto.getComentario().trim().length() < 20) {
            sugerencias.add("Considera agregar más detalles a tu comentario para ayudar a otros usuarios");
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return sugerencias;
    }
}