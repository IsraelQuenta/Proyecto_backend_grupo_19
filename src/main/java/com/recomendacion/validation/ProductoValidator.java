package com.recomendacion.validation;

import com.recomendacion.dto.ProductoDTO;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ProductoValidator {

    private static final int TITULO_MIN = 3;
    private static final int TITULO_MAX = 100;
    private static final int SINOPSIS_MIN = 10;
    private static final int SINOPSIS_MAX = 500;
    private static final int PAIS_MAX = 50;

    private static final List<String> ESTADOS_VALIDOS = List.of(
        "Disponible", "Agotado", "En estreno"
    );

    private static final Pattern PATTERN_ESTADO = Pattern.compile(
        "^(Disponible|Agotado|En estreno)$"
    );

    private static final Pattern PATTERN_URL = Pattern.compile(
        "^(https?://)([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?$"
    );

    private static final Pattern PATTERN_TITULO = Pattern.compile(
        "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s:.,!?¿¡\\-()&'\"]+$"
    );

    /**
     * Valida un ProductoDTO completo
     * @param dto El DTO a validar
     * @return Lista de errores de validación
     */
    public List<String> validar(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El ProductoDTO no puede ser nulo");
            return errores;
        }

        // Validar campos requeridos
        errores.addAll(validarCamposRequeridos(dto));
<<<<<<< HEAD
        
        // Validar formato y rangos
        errores.addAll(validarFormatos(dto));
        
=======

        // Validar formato y rangos
        errores.addAll(validarFormatos(dto));

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Validar contenido específico
        errores.addAll(validarContenido(dto));

        return errores;
    }

    /**
     * Valida que los campos requeridos estén presentes
     */
    private List<String> validarCamposRequeridos(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            errores.add("El título es obligatorio");
        }

        if (dto.getSinopsis() == null || dto.getSinopsis().trim().isEmpty()) {
            errores.add("La sinopsis es obligatoria");
        }
<<<<<<< HEAD
/* 
=======
/*
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        if (dto.getUrlImagen() == null || dto.getUrlImagen().trim().isEmpty()) {
            errores.add("La URL de la imagen es obligatoria");
        }
*/
        if (dto.getPais() == null || dto.getPais().trim().isEmpty()) {
            errores.add("El país es obligatorio");
        }

        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            errores.add("El estado es obligatorio");
        }

        return errores;
    }

    /**
     * Valida el formato y rangos de los campos
     */
    private List<String> validarFormatos(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        // Validar longitud de título
        if (dto.getTitulo() != null) {
            String titulo = dto.getTitulo().trim();
            if (titulo.length() < TITULO_MIN) {
                errores.add("El título debe tener entre " + TITULO_MIN + " y " + TITULO_MAX + " caracteres");
            }
            if (titulo.length() > TITULO_MAX) {
                errores.add("El título debe tener entre " + TITULO_MIN + " y " + TITULO_MAX + " caracteres");
            }
<<<<<<< HEAD
            
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            // Validar caracteres permitidos en título
            if (!titulo.isEmpty() && !PATTERN_TITULO.matcher(titulo).matches()) {
                errores.add("El título contiene caracteres no válidos");
            }
        }

        // Validar longitud de sinopsis
        if (dto.getSinopsis() != null) {
            String sinopsis = dto.getSinopsis().trim();
            if (sinopsis.length() < SINOPSIS_MIN) {
                errores.add("La sinopsis debe tener entre " + SINOPSIS_MIN + " y " + SINOPSIS_MAX + " caracteres");
            }
            if (sinopsis.length() > SINOPSIS_MAX) {
                errores.add("La sinopsis debe tener entre " + SINOPSIS_MIN + " y " + SINOPSIS_MAX + " caracteres");
            }
        }

        // Validar URL de imagen
        if (dto.getUrlImagen() != null) {
            String urlImagen = dto.getUrlImagen().trim();
            if (!urlImagen.isEmpty() && !esUrlValida(urlImagen)) {
                errores.add("La URL de imagen no es válida");
            }
            if (!urlImagen.isEmpty() && !esUrlImagen(urlImagen)) {
                errores.add("La URL debe apuntar a un archivo de imagen válido (jpg, jpeg, png, gif, webp)");
            }
        }

        // Validar país
        if (dto.getPais() != null) {
            String pais = dto.getPais().trim();
            if (pais.length() > PAIS_MAX) {
                errores.add("El país no puede exceder " + PAIS_MAX + " caracteres");
            }
            if (!pais.isEmpty() && pais.length() < 2) {
                errores.add("El país debe tener al menos 2 caracteres");
            }
        }

        // Validar estado
        if (dto.getEstado() != null) {
            String estado = dto.getEstado().trim();
            if (!estado.isEmpty() && !PATTERN_ESTADO.matcher(estado).matches()) {
                errores.add("El estado debe ser: Disponible, Agotado o En estreno");
            }
        }

        return errores;
    }

    /**
     * Valida el contenido específico de los campos
     */
    private List<String> validarContenido(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        // Validar géneros IDs
        if (dto.getGenerosIds() != null) {
            errores.addAll(validarGenerosIds(dto.getGenerosIds()));
        }

        // Validar que la sinopsis no sea solo espacios o caracteres repetidos
        if (dto.getSinopsis() != null) {
            String sinopsis = dto.getSinopsis().trim();
            if (!sinopsis.isEmpty()) {
                if (sinopsis.matches("^(.)\\1*$")) {
                    errores.add("La sinopsis no puede consistir en caracteres repetidos");
                }
                if (sinopsis.split("\\s+").length < 5) {
                    errores.add("La sinopsis debe contener al menos 5 palabras");
                }
            }
        }

        // Validar que el título no sea solo números
        if (dto.getTitulo() != null) {
            String titulo = dto.getTitulo().trim();
            if (!titulo.isEmpty() && titulo.matches("^\\d+$")) {
                errores.add("El título no puede consistir únicamente en números");
            }
        }

        return errores;
    }

    /**
     * Valida los IDs de géneros del producto
     */
    private List<String> validarGenerosIds(Set<Long> generosIds) {
        List<String> errores = new ArrayList<>();

        if (generosIds.isEmpty()) {
            errores.add("El producto debe tener al menos un género asignado");
        }

        if (generosIds.size() > 10) {
            errores.add("El producto no puede tener más de 10 géneros asignados");
        }

        // Validar que los IDs de géneros sean positivos
        boolean tieneIdsInvalidos = generosIds.stream()
            .anyMatch(id -> id == null || id <= 0);
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        if (tieneIdsInvalidos) {
            errores.add("Los IDs de géneros deben ser números positivos");
        }

        // Validar que no haya IDs duplicados (aunque Set ya los previene)
        if (generosIds.size() != generosIds.stream().distinct().count()) {
            errores.add("No se permiten géneros duplicados");
        }

        return errores;
    }

    /**
     * Verifica si una URL es válida
     */
    private boolean esUrlValida(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        try {
            new URL(url);
            return PATTERN_URL.matcher(url).matches();
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Verifica si una URL apunta a una imagen
     */
    private boolean esUrlImagen(String url) {
        if (url == null) {
            return false;
        }
<<<<<<< HEAD
        
        String urlLower = url.toLowerCase();
        return urlLower.endsWith(".jpg") || 
               urlLower.endsWith(".jpeg") || 
               urlLower.endsWith(".png") || 
               urlLower.endsWith(".gif") || 
=======

        String urlLower = url.toLowerCase();
        return urlLower.endsWith(".jpg") ||
               urlLower.endsWith(".jpeg") ||
               urlLower.endsWith(".png") ||
               urlLower.endsWith(".gif") ||
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
               urlLower.endsWith(".webp") ||
               urlLower.endsWith(".svg") ||
               urlLower.contains("image") ||
               urlLower.contains("img");
    }

    /**
     * Valida solo para creación (sin ID)
     */
    public List<String> validarParaCreacion(ProductoDTO dto) {
        List<String> errores = validar(dto);
<<<<<<< HEAD
        
        if (dto != null && dto.getId() != null) {
            errores.add("El ID debe ser nulo para crear un nuevo producto");
        }
        
=======

        if (dto != null && dto.getId() != null) {
            errores.add("El ID debe ser nulo para crear un nuevo producto");
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return errores;
    }

    /**
     * Valida solo para actualización (con ID)
     */
    public List<String> validarParaActualizacion(ProductoDTO dto) {
        List<String> errores = validar(dto);
<<<<<<< HEAD
        
        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }
        
=======

        if (dto != null && (dto.getId() == null || dto.getId() <= 0)) {
            errores.add("El ID es requerido y debe ser mayor a 0 para actualizar");
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return errores;
    }

    /**
     * Validación para prevenir productos duplicados (mismo título y país)
     */
    public List<String> validarDuplicado(ProductoDTO dto, boolean existeProductoSimilar) {
        List<String> errores = new ArrayList<>();
<<<<<<< HEAD
        
        if (existeProductoSimilar) {
            errores.add("Ya existe un producto con el mismo título en este país");
        }
        
=======

        if (existeProductoSimilar) {
            errores.add("Ya existe un producto con el mismo título en este país");
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return errores;
    }

    /**
     * Validación de disponibilidad según el estado
     */
    public List<String> validarDisponibilidad(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();
<<<<<<< HEAD
        
        if (dto == null || dto.getEstado() == null) {
            return errores;
        }
        
        String estado = dto.getEstado().trim();
        
=======

        if (dto == null || dto.getEstado() == null) {
            return errores;
        }

        String estado = dto.getEstado().trim();

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Validaciones específicas según el estado
        switch (estado) {
            case "En estreno":
                if (dto.getGenerosIds() == null || dto.getGenerosIds().isEmpty()) {
                    errores.add("Los productos en estreno deben tener géneros definidos");
                }
                break;
            case "Agotado":
                // Los productos agotados podrían tener validaciones especiales
                break;
            case "Disponible":
                if (dto.getGenerosIds() == null || dto.getGenerosIds().isEmpty()) {
                    errores.add("Los productos disponibles deben tener géneros definidos para mejorar las recomendaciones");
                }
                break;
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return errores;
    }

    /**
     * Validación de géneros existentes en el sistema
     */
    public List<String> validarGenerosExistentes(ProductoDTO dto, Set<Long> generosExistentes) {
        List<String> errores = new ArrayList<>();
<<<<<<< HEAD
        
        if (dto == null || dto.getGenerosIds() == null || generosExistentes == null) {
            return errores;
        }
        
=======

        if (dto == null || dto.getGenerosIds() == null || generosExistentes == null) {
            return errores;
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Verificar que todos los géneros especificados existan
        Set<Long> generosInvalidos = dto.getGenerosIds().stream()
            .filter(id -> !generosExistentes.contains(id))
            .collect(java.util.stream.Collectors.toSet());
<<<<<<< HEAD
            
        if (!generosInvalidos.isEmpty()) {
            errores.add("Los siguientes IDs de géneros no existen: " + generosInvalidos);
        }
        
=======

        if (!generosInvalidos.isEmpty()) {
            errores.add("Los siguientes IDs de géneros no existen: " + generosInvalidos);
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return errores;
    }

    /**
     * Verifica si el DTO es válido
     */
    public boolean esValido(ProductoDTO dto) {
        return validar(dto).isEmpty();
    }

    /**
     * Normaliza los datos del DTO
     */
    public void normalizar(ProductoDTO dto) {
        if (dto != null) {
            // Normalizar título
            if (dto.getTitulo() != null) {
                dto.setTitulo(dto.getTitulo().trim());
            }
<<<<<<< HEAD
            
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            // Normalizar sinopsis
            if (dto.getSinopsis() != null) {
                dto.setSinopsis(dto.getSinopsis().trim());
            }
<<<<<<< HEAD
            
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            // Normalizar URL de imagen
            if (dto.getUrlImagen() != null) {
                dto.setUrlImagen(dto.getUrlImagen().trim());
            }
<<<<<<< HEAD
            
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            // Normalizar país (capitalizar primera letra de cada palabra)
            if (dto.getPais() != null) {
                String pais = dto.getPais().trim();
                if (!pais.isEmpty()) {
                    String[] palabras = pais.split("\\s+");
                    StringBuilder paisNormalizado = new StringBuilder();
<<<<<<< HEAD
                    
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
                    for (int i = 0; i < palabras.length; i++) {
                        if (i > 0) paisNormalizado.append(" ");
                        String palabra = palabras[i];
                        if (!palabra.isEmpty()) {
                            paisNormalizado.append(palabra.substring(0, 1).toUpperCase())
                                         .append(palabra.substring(1).toLowerCase());
                        }
                    }
                    dto.setPais(paisNormalizado.toString());
                }
            }
<<<<<<< HEAD
            
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
            // El estado ya debe venir en el formato correcto según el patrón
            if (dto.getEstado() != null) {
                dto.setEstado(dto.getEstado().trim());
            }
        }
    }

    /**
     * Obtiene sugerencias para mejorar el producto
     */
    public List<String> obtenerSugerencias(ProductoDTO dto) {
        List<String> sugerencias = new ArrayList<>();
<<<<<<< HEAD
        
        if (dto == null) {
            return sugerencias;
        }
        
=======

        if (dto == null) {
            return sugerencias;
        }

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Sugerir mejoras en la sinopsis
        if (dto.getSinopsis() != null && dto.getSinopsis().trim().length() < 50) {
            sugerencias.add("Considera expandir la sinopsis para dar más información sobre el producto");
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Sugerir más géneros si tiene pocos
        if (dto.getGenerosIds() != null && dto.getGenerosIds().size() < 2) {
            sugerencias.add("Considera agregar más géneros para mejorar las recomendaciones");
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Sugerir verificar URL de imagen
        if (dto.getUrlImagen() != null && !dto.getUrlImagen().startsWith("https://")) {
            sugerencias.add("Considera usar una URL HTTPS para la imagen por seguridad");
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        // Sugerir estado más específico
        if ("Disponible".equals(dto.getEstado())) {
            sugerencias.add("Si el producto es nuevo, considera usar 'En estreno' para destacarlo");
        }
<<<<<<< HEAD
        
=======

>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
        return sugerencias;
    }
}