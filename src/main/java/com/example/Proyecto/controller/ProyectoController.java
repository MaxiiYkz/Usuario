package com.example.Proyecto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.dto.Usuario;
import com.example.Proyecto.services.UsuarioService;

@RestController
@RequestMapping("/api/v0/Usuario")
public class ProyectoController {
    @Autowired
    private UsuarioService usuarioService;

    //Obtener Todos los Usuarios
    @GetMapping()
    public ResponseEntity<?> obtenerUsuarios(){
    List<Usuario> usuarios = usuarioService.obtenerUsuario();

    if (usuarios.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontraron usuarios en la base de datos");
        }

    return ResponseEntity.ok(usuarios);
    }
    //Guardar Usuario
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usu) {
    if (usu == null || usu.getUsuario() == null || usu.getUsuario().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("Datos de usuario inválidos o incompletos");
    }

    try {
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usu);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al guardar usuario");
        }
    }

    //Buscar Usuario por ID
    @GetMapping("{idUsuario}")
    public ResponseEntity<?> buscarUsuario(@PathVariable int idUsuario){
    Usuario usuario = usuarioService.getUsuarioId(idUsuario);

    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario con ID " + idUsuario + " no encontrado");
        }

    return ResponseEntity.ok(usuario);
    }

    //Actualizar Usuario
    @PutMapping("{idUsuario}")
    public ResponseEntity<Usuario>  actualizarUsuario(@PathVariable int idUsuario, @RequestBody Usuario usu) {
        Usuario aux = usuarioService.actualizarUsuario(usu, idUsuario);
            if(aux == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(aux); 
    }

    //Eliminar Logicamente el Usuario
    @DeleteMapping("{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int idUsuario) {
    usuarioService.eliminarUsuario(idUsuario);
    return ResponseEntity.noContent().build();
    }

    //Total de Usuarios en la Base de Datos
    @GetMapping("/total")
    public ResponseEntity<String> obtenerTotalUsuarios() {
    long total = usuarioService.obtenerCantidadUsuarios();

    if (total == 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existen usuarios registrados en la base de datos");
        }

    return ResponseEntity.ok("Cantidad total de usuarios: " + total);
    }

    //Estadisticas de los usuarios
    @GetMapping("/estadisticas")
    public ResponseEntity<?> estadisticasUsuarios() {
    Map<String, Object> estadisticas = usuarioService.obtenerEstadisticas();

    if (estadisticas == null || estadisticas.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existen usuarios registrados en la base de datos");
        }

    return ResponseEntity.ok(estadisticas);
    }

    //Eliminar totalmente
    @DeleteMapping("/eliminarpermanente/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarioTotalmente(@PathVariable int idUsuario) {
    if (!usuarioService.existeUsuario(idUsuario)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario con ID " + idUsuario + " no encontrado");
        }

    usuarioService.eliminarUsuarioTotalmente(idUsuario);
    return ResponseEntity.ok("Usuario con ID " + idUsuario + " eliminado permanentemente correctamente");
    }

}

