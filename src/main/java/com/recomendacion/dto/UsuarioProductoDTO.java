package com.recomendacion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProductoDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private String tipoInteraccion;
    private LocalDateTime fecha;
}