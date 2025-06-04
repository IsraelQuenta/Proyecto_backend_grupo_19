package com.recomendacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionDTO {
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID de producto es obligatorio")
    private Long productoId;

    @NotBlank(message = "El algoritmo es obligatorio")
    @Pattern(regexp = "^(COLABORATIVO|CONTENIDO|HIBRIDO)$",
            message = "Algoritmo no válido")
    private String algoritmo;

    private LocalDateTime fecha;
}