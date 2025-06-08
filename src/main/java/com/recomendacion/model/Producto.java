package com.recomendacion.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "producto")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, length = 100)
    @Length(min = 3, max = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    @Length(min = 10, max = 500)
    private String sinopsis;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(nullable = false, length = 50)
    private String pais; // Nuevo atributo

    @Column(nullable = false, length = 20)
    private String estado; // Nuevo atributo: "Disponible", "Agotado", "En estreno"

    //Cómo funciona: Un producto (libro/película) puede tener varios géneros, y un género puede estar en varios productos.
    //Implementación: Se usa una tabla intermedia llamada producto_genero con las claves foráneas producto_id y genero_id.
    //Ejemplo:
    //El libro "Dune" tiene géneros: "Ciencia Ficción" y "Aventura".
    //El género "Ciencia Ficción" está en: "Dune" (libro) y "Matrix" (película).
    @ManyToMany
    @JoinTable(
            name = "producto_genero",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos;
}