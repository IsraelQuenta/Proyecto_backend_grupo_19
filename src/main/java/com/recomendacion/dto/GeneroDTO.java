package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneroDTO {
    private Long id;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Pattern(regexp = "^(Femenino|Masculino|Ciencia Ficción|Terror|Drama|Comedia)$",
            message = "Género no válido")
    private String descripcion;
}