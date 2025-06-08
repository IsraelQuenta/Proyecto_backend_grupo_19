package com.recomendacion.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pelicula")
@PrimaryKeyJoinColumn(name = "id_producto")  // Relación 1:1 con Producto (herencia JOINED)
public class Pelicula extends Producto {

    @Column(nullable = false, length = 100)
    private String director;

    @Column(nullable = false, length = 100)
    private String protagonista;  // Ej: "Keanu Reeves"

    @Column(nullable = false, length = 100)
    private String productora;    // Ej: "Warner Bros"

    @Column(nullable = false)
    private Integer duracionMinutos;  // Ej: 150 (minutos)

    @Column(name = "fecha_estreno", nullable = false)
    private LocalDate fechaEstreno;  // Ej: 1999-03-31 (para "Matrix")
}