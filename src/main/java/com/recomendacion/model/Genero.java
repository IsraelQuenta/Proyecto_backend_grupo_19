package com.recomendacion.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "genero")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long id;

    @Column(length = 200, nullable = false)
    private String descripcion; // Ej: "Femenino", "Masculino", "Ciencia Ficción", "Terror"
}