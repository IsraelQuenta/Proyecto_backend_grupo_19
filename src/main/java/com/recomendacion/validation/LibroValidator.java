package com.recomendacion.validation;

import com.recomendacion.dto.LibroDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class LibroValidator implements Validator {

    private static final Pattern AUTOR_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s.'-]+$");
    private static final Pattern EDITORIAL_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s&.'-]+$");
    private static final int MIN_PAGINAS = 1;
    private static final int MAX_PAGINAS = 10000; // Límite razonable para un libro
    private static final int MAX_AUTOR_CHARACTERS = 100;
    private static final int MAX_EDITORIAL_CHARACTERS = 50;
    private static final int MIN_AUTOR_LENGTH = 2;
    private static final int MIN_EDITORIAL_LENGTH = 2;

    @Override
    public boolean supports(Class<?> clazz) {
        return LibroDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LibroDTO libro = (LibroDTO) target;

        // Validar autor
        validateAutor(libro.getAutor(), errors);
        
        // Validar editorial
        validateEditorial(libro.getEditorial(), errors);
        
        // Validar páginas
        validatePaginas(libro.getPaginas(), errors);
    }

    private void validateAutor(String autor, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autor", 
            "autor.required", "El autor es obligatorio");
        
        if (autor != null) {
            String autorTrimmed = autor.trim();
            
            if (autorTrimmed.length() > MAX_AUTOR_CHARACTERS) {
                errors.rejectValue("autor", "autor.maxlength", 
                    "El autor no puede exceder " + MAX_AUTOR_CHARACTERS + " caracteres");
            }
            
            if (autorTrimmed.length() < MIN_AUTOR_LENGTH) {
                errors.rejectValue("autor", "autor.minlength", 
                    "El autor debe tener al menos " + MIN_AUTOR_LENGTH + " caracteres");
            }
            
            if (!AUTOR_PATTERN.matcher(autorTrimmed).matches()) {
                errors.rejectValue("autor", "autor.invalid", 
                    "El autor solo puede contener letras, espacios, puntos, apostrofes y guiones");
            }
            
            // Validar que no contenga solo espacios o caracteres especiales
            if (autorTrimmed.replaceAll("[\\s.'-]", "").isEmpty()) {
                errors.rejectValue("autor", "autor.empty", 
                    "El autor debe contener al menos una letra");
            }
            
            // Validar formato de nombre (al menos dos palabras para nombre y apellido)
            String[] palabras = autorTrimmed.split("\\s+");
            if (palabras.length < 2) {
                errors.rejectValue("autor", "autor.format", 
                    "El autor debe incluir al menos nombre y apellido");
            }
        }
    }

    private void validateEditorial(String editorial, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "editorial", 
            "editorial.required", "La editorial es obligatoria");
        
        if (editorial != null) {
            String editorialTrimmed = editorial.trim();
            
            if (editorialTrimmed.length() > MAX_EDITORIAL_CHARACTERS) {
                errors.rejectValue("editorial", "editorial.maxlength", 
                    "La editorial no puede exceder " + MAX_EDITORIAL_CHARACTERS + " caracteres");
            }
            
            if (editorialTrimmed.length() < MIN_EDITORIAL_LENGTH) {
                errors.rejectValue("editorial", "editorial.minlength", 
                    "La editorial debe tener al menos " + MIN_EDITORIAL_LENGTH + " caracteres");
            }
            
            if (!EDITORIAL_PATTERN.matcher(editorialTrimmed).matches()) {
                errors.rejectValue("editorial", "editorial.invalid", 
                    "La editorial solo puede contener letras, espacios, ampersand, puntos, apostrofes y guiones");
            }
            
            // Validar que no contenga solo espacios o caracteres especiales
            if (editorialTrimmed.replaceAll("[\\s&.'-]", "").isEmpty()) {
                errors.rejectValue("editorial", "editorial.empty", 
                    "La editorial debe contener al menos una letra");
            }
        }
    }

    private void validatePaginas(Integer paginas, Errors errors) {
        if (paginas == null) {
            errors.rejectValue("paginas", "paginas.required", 
                "El número de páginas es obligatorio");
        } else {
            if (paginas < MIN_PAGINAS) {
                errors.rejectValue("paginas", "paginas.min", 
                    "El libro debe tener al menos " + MIN_PAGINAS + " página");
            }
            
            if (paginas > MAX_PAGINAS) {
                errors.rejectValue("paginas", "paginas.max", 
                    "El número de páginas no puede exceder " + MAX_PAGINAS);
            }
        }
    }

    /**
     * Método auxiliar para validar reglas de negocio específicas
     */
    public void validateBusinessRules(LibroDTO libro, Errors errors) {
        // Validar consistencia entre páginas y tipo de libro
        if (libro.getPaginas() != null) {
            // Libros infantiles suelen tener pocas páginas
            if (libro.getTitulo() != null && 
                (libro.getTitulo().toLowerCase().contains("infantil") || 
                 libro.getTitulo().toLowerCase().contains("niños")) &&
                libro.getPaginas() > 100) {
                errors.rejectValue("paginas", "paginas.infantil", 
                    "Los libros infantiles no suelen tener más de 100 páginas");
            }
            
            // Diccionarios y enciclopedias suelen tener muchas páginas
            if (libro.getTitulo() != null && 
                (libro.getTitulo().toLowerCase().contains("diccionario") || 
                 libro.getTitulo().toLowerCase().contains("enciclopedia")) &&
                libro.getPaginas() < 200) {
                errors.rejectValue("paginas", "paginas.referencia", 
                    "Los diccionarios y enciclopedias suelen tener al menos 200 páginas");
            }
        }
        
        // Validar editoriales conocidas con formato específico
        if (libro.getEditorial() != null) {
            String editorial = libro.getEditorial().toLowerCase().trim();
            if (editorial.contains("penguin") && !editorial.contains("random")) {
                // Ejemplo de validación específica para ciertas editoriales
                if (libro.getPaginas() != null && libro.getPaginas() < 50) {
                    errors.rejectValue("paginas", "paginas.editorial", 
                        "Los libros de esta editorial suelen tener al menos 50 páginas");
                }
            }
        }
        
        // Validar coherencia entre autor y editorial para casos específicos
        if (libro.getAutor() != null && libro.getEditorial() != null) {
            String autor = libro.getAutor().toLowerCase().trim();
            String editorial = libro.getEditorial().toLowerCase().trim();
            
            // Ejemplo: validar que ciertos autores clásicos no aparezcan con editoriales muy modernas
            if ((autor.contains("cervantes") || autor.contains("shakespeare")) && 
                editorial.contains("digital")) {
                errors.rejectValue("editorial", "editorial.anachronism", 
                    "Verifique la coherencia entre el autor clásico y la editorial moderna");
            }
        }
    }

    /**
     * Validación específica para ISBN si se añade en el futuro
     */
    public boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        // Remover guiones y espacios
        String cleanISBN = isbn.replaceAll("[-\\s]", "");
        
        // Validar ISBN-10 o ISBN-13
        return isValidISBN10(cleanISBN) || isValidISBN13(cleanISBN);
    }
    
    private boolean isValidISBN10(String isbn) {
        if (isbn.length() != 10) return false;
        
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        
        char lastChar = isbn.charAt(9);
        if (lastChar == 'X') {
            sum += 10;
        } else if (Character.isDigit(lastChar)) {
            sum += (lastChar - '0');
        } else {
            return false;
        }
        
        return sum % 11 == 0;
    }
    
    private boolean isValidISBN13(String isbn) {
        if (isbn.length() != 13) return false;
        
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        
        return sum % 10 == 0;
    }
}