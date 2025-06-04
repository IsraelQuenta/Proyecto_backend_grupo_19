package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PeliculaDTO extends ProductoDTO {

    @NotBlank(message = "El director es obligatorio")
    @Size(max = 100, message = "El director no puede exceder 100 caracteres")
    private String director;

    @NotBlank(message = "El protagonista es obligatorio")
    @Size(max = 100, message = "El protagonista no puede exceder 100 caracteres")
    private String protagonista;

    @NotBlank(message = "La productora es obligatoria")
    @Size(max = 100, message = "La productora no puede exceder 100 caracteres")
    private String productora;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración mínima es 1 minuto")
    private Integer duracionMinutos;

    @NotNull(message = "La fecha de estreno es obligatoria")
    @PastOrPresent(message = "La fecha de estreno no puede ser futura")
    private LocalDate fechaEstreno;
}