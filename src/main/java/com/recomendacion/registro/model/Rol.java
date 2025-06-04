package com.recomendacion.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING) // Almacena el nombre del rol como una cadena en la base de datos
    @Column(length = 20) // Longitud máxima de 20 caracteres para el nombre del rol
    private NombreRol nombre;
    
    public enum NombreRol { // Enum para definir los nombres de los roles
        ROL_USER,
        ROL_COLABORADOR, 
        ROL_ADMIN
    }
    //Cambios efectuados 
    /*Se conservaran los 3 roles tradicionales para el login 
     * USER = USUARIO CORRIENTE QUE SOLO EXISTE PARA CONSUMIR LAS FUNCIONALIDADES DEL SISTEMA
     * COLABORADOR = ELLOS ESTAN DESTINADOS A CALIBRAR EL ALGORITMO DANDOLE ACCESO SOLAMENTE AL CRUD PARA PELICULAS Y LIBROS
     * ADMIN = /GAMEMODE 1
     */
}
