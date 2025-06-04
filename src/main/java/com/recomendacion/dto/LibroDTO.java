package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LibroDTO extends ProductoDTO {

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede exceder 100 caracteres")
    private String autor;

    @NotBlank(message = "La editorial es obligatoria")
    @Size(max = 50, message = "La editorial no puede exceder 50 caracteres")
    private String editorial;

    @NotNull(message = "El número de páginas es obligatorio")
    @Min(value = 1, message = "El libro debe tener al menos 1 página")
    private Integer paginas;

    @NotNull(message = "El número de edicion es obligatorio")
    @Min(value = 1, message = "El libro tiene que ser de alguna edicion")
    private Integer edicion;

}