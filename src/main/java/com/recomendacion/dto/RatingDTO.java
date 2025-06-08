package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
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
    @DecimalMin(value = "1.0", message = "La valoración mínima es 1")
    @DecimalMax(value = "5.0", message = "La valoración máxima es 5")
    private BigDecimal valoracion;  // Cambiado de Double a BigDecimal

    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    private String comentario;

    private LocalDateTime fecha;
}