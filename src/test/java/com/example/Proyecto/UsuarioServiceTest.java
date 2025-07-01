package com.example.Proyecto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.Proyecto.dto.Usuario;
import com.example.Proyecto.repository.RepositorioUsuario;
import com.example.Proyecto.services.UsuarioService;


public class UsuarioServiceTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioEjemplo = new Usuario(1, "12347384-8", "Jorge Manuel", "jorgemanuel2025", "jorge.manuel25@gmail.com", 947583658, true, false);
    }

    @Test
    public void testObtenerUsuarios() {
        when(repositorioUsuario.findAll()).thenReturn(List.of(usuarioEjemplo));
        List<Usuario> usuarios = usuarioService.obtenerUsuario();
        assertEquals(1, usuarios.size());
        assertEquals("Jorge Manuel", usuarios.get(0).getUsuario());
    }

    @Test
    public void testGuardarUsuario() {
        when(repositorioUsuario.save(usuarioEjemplo)).thenReturn(usuarioEjemplo);
        Usuario result = usuarioService.guardarUsuario(usuarioEjemplo);
        assertEquals("Jorge Manuel", result.getUsuario());
    }

    @Test
    void testEliminarUsuario_Existente() {
        Usuario usuario = new Usuario(1, "12347384-8", "Jorge Manuel", "jorgemanuel2025", "jorge.manuel25@gmail.com", 947583658, true, false);
    
        when(repositorioUsuario.findById(1)).thenReturn(Optional.of(usuario));

        String resultado = usuarioService.eliminarUsuario(1);

        assertEquals("Usuario eliminado lógicamente y marcado como inactivo", resultado);
        assertFalse(usuario.isActivo());
        assertTrue(usuario.isEliminado());

        verify(repositorioUsuario).findById(1);
        verify(repositorioUsuario).save(usuario);
    }

}
