package com.recomendacion.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "libro")
@PrimaryKeyJoinColumn(name = "id_producto") // Hereda ID de Producto
public class Libro extends Producto {

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(nullable = false, length = 50)
    private String editorial;

    @Column(nullable = false)
    private Integer paginas;

    @Column(nullable = false, length = 20)
    private Integer edicion;
}
