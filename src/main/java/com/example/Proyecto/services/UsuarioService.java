package com.example.Proyecto.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Proyecto.dto.Usuario;
import com.example.Proyecto.repository.RepositorioUsuario;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UsuarioService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public List<Usuario> obtenerUsuario() {
        return repositorioUsuario.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    public Usuario getUsuarioId(int idUsuario) {
        return repositorioUsuario.findById(idUsuario).orElse(null);
    }

    //Actualizar Usuarios
    public Usuario actualizarUsuario(Usuario usu, Integer idUsuario) {
        Usuario aux = getUsuarioId(idUsuario);
        if (aux != null) {
            aux.setRun(usu.getRun());
            aux.setUsuario(usu.getUsuario());
            aux.setContraseña(usu.getContraseña());
            aux.setCorreo(usu.getCorreo());
            aux.setTelefono(usu.getTelefono());
            repositorioUsuario.save(aux);
        }
        return aux;
    }

    public long contarUsuarios() {
        return repositorioUsuario.count();
    }

    public long obtenerCantidadUsuarios() {
    return repositorioUsuario.count();
    }

    //Estadisticas
    public Map<String, Object> obtenerEstadisticas() {
    long total = repositorioUsuario.count();

    if (total == 0) {
        return Collections.emptyMap();
    }

    Map<String, Object> estadisticas = new HashMap<>();
    estadisticas.put("totalUsuarios", total);
    return estadisticas;
    }

    //Eliminar logicamente
    public String eliminarUsuario(int idUsuario) {
    Optional<Usuario> optionalUsuario = repositorioUsuario.findById(idUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setEliminado(true);
            usuario.setActivo(false);  //Aquí marcamos que ya no está activo
            repositorioUsuario.save(usuario);
            return "Usuario eliminado lógicamente y marcado como inactivo";
        } 
        else {
            return "Usuario no encontrado";
        }
    }

    //Eliminar totalmente, si el usuario lo desea
    public void eliminarUsuarioTotalmente(int idUsuario) {
    if (!repositorioUsuario.existsById(idUsuario)) {
        throw new NoSuchElementException("Usuario con ID " + idUsuario + " no encontrado");
    }
    repositorioUsuario.deleteById(idUsuario);
    }

    public boolean existeUsuario(int idUsuario) {
    return repositorioUsuario.existsById(idUsuario);
    }


}
