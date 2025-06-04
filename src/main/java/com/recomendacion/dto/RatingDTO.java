package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID de producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La valoración es obligatoria")
    @DecimalMin(value = "0.5", message = "La valoración mínima es 0.5")
    @DecimalMax(value = "5.0", message = "La valoración máxima es 5.0")
    private Double valoracion;

    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    private String comentario;

    private LocalDateTime fecha;
}