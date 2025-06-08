package com.recomendacion.registro.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600) // Permite el acceso desde cualquier origen (CORS)
// maxAge = 3600 significa que la configuración de CORS se mantendrá durante 1 hora
@RestController
@RequestMapping("/api")
public class UsuarioController { // Controlador para manejar las peticiones relacionadas con los usuarios
    // Aquí puedes inyectar los servicios necesarios para manejar la lógica de negocio relacionada con los usuarios
    

    @GetMapping("/public/test") // Endpoint público accesible sin autenticación
    @PreAuthorize("permitAll()") // Permite el acceso a todos los usuarios, sin importar su rol
    public String allAccess() {
        return "Contenido público";
    }

    // Endpoint protegido que requiere autenticación y autorización
    // Permite el acceso a usuarios con los roles 'USER', 'COLABORADOR' o 'ADMIN'
    @GetMapping("/users/test")
    @PreAuthorize("hasRole('USER') or hasRole('COLABORADOR') or hasRole('ADMIN')") 
    public String userAccess() { //cambio aplicado aqui anteriormete era estudianteAccess
        return "Contenido para usuarios";
    }

    // Endpoint protegido que requiere autenticación y autorización
    // Permite el acceso a usuarios con los roles 'DOCENTE' o 'ADMIN'
    @GetMapping("/colaborador/test")
    @PreAuthorize("hasRole('COLABORADOR') or hasRole('ADMIN')")
    public String docenteAccess() {
        return "Contenido para colaboradores";
    }

    // Endpoint protegido que requiere autenticación y autorización
    // Permite el acceso solo a usuarios con el rol 'ADMIN'
    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Contenido para administradores";
    }
}
