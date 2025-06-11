package com.recomendacion.repository;

<<<<<<< HEAD
=======
import com.recomendacion.model.Libro;
import com.recomendacion.model.Pelicula;
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
import com.recomendacion.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Encuentra productos por título (búsqueda insensible a mayúsculas)
    List<Producto> findByTituloContainingIgnoreCase(String titulo);

    // Encuentra libros por autor
    @Query("SELECT p FROM Producto p WHERE TYPE(p) = Libro AND p.autor LIKE %:autor%")
    List<Producto> findLibrosByAutor(String autor);

    // Encuentra películas por director
    @Query("SELECT p FROM Producto p WHERE TYPE(p) = Pelicula AND p.director LIKE %:director%")
    List<Producto> findPeliculasByDirector(String director);

    // Encuentra productos por género
    @Query("SELECT p FROM Producto p JOIN p.generos g WHERE g.id = :generoId")
    List<Producto> findByGeneroId(Long generoId);

<<<<<<< HEAD
    // Verifica si existe un producto con el mismo título
    boolean existsByTitulo(String titulo);
=======
    // Verifica si existe un producto con el mismo título y país
    boolean existsByTituloAndPais(String titulo, String pais);

    // Obtener todos los libros
    @Query("SELECT l FROM Producto l WHERE TYPE(l) = Libro")
    List<Libro> findAllLibros();

    // Obtener todas las películas
    @Query("SELECT p FROM Producto p WHERE TYPE(p) = Pelicula")
    List<Pelicula> findAllPeliculas();
>>>>>>> 9e299a9 (Proyecto antes de insertar la seguridad en los endpoints)
}