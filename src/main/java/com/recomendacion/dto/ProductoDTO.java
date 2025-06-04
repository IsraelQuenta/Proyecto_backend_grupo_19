package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ProductoDTO {
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La sinopsis es obligatoria")
    @Size(min = 10, max = 500, message = "La sinopsis debe tener entre 10 y 500 caracteres")
    private String sinopsis;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String urlImagen;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 50, message = "El país no puede exceder 50 caracteres")
    private String pais;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(Disponible|Agotado|En estreno)$",
            message = "El estado debe ser: Disponible, Agotado o En estreno")
    private String estado;

    private Set<Long> generosIds; // IDs de géneros asociados
}