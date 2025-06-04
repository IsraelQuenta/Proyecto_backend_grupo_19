package com.recomendacion.model;
//para registrar interacciones (ej: "Visto", "Leído", "Favorito").
//Historial: Se deriva de los ratings registrados y/o una tabla adicional Usuario_Producto
//(para guardar interacciones sin rating, como "visto" o "leído").
import com.recomendacion.registro.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private String tipoInteraccion; // "VISTO", "LEIDO", "FAVORITO"

    @Column(nullable = false)
    private LocalDateTime fecha;
}