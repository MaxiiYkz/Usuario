package com.example.Proyecto;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Proyecto.assemblers.UsuarioModelAssembler;
import com.example.Proyecto.controller.ProyectoController;
import com.example.Proyecto.dto.Usuario;
import com.example.Proyecto.dto.UsuarioModel;
import com.example.Proyecto.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ProyectoController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @MockitoBean
    private UsuarioModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    public static class DummyUsuarioModel extends UsuarioModel {
        public DummyUsuarioModel(Usuario usu) {
            this.setIdUsuario(usu.getIdUsuario());;
            this.setRun(usu.getRun());
            this.setUsuario(usu.getUsuario());
            this.setCorreo(usu.getCorreo());
            this.setTelefono(usu.getTelefono());
            this.add(Link.of("https://localhost/api/v0/Usuarios/" + usu.getIdUsuario()).withSelfRel());

        }
    }

    @Test
    @DisplayName("GET /api/v0/Usuarios Retorna un 404 si no hay cursos")
    public void testListarUsuariosVacios() throws Exception {
        when(service.obtenerUsuario()).thenReturn(List.of());

        mockMvc.perform(get("/api/v0/Usuario/"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v0/Usuario/{idusuario} Retorna 404 si no existe")
    public void testBuscarPorIdNoExistente() throws Exception {
        when(service.getUsuarioId(99)).thenReturn(null);

        mockMvc.perform(get("/api/v0/Usuario/99")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v0/Usuario/{idusuario} Elimina el usuario existente")
    public void testEliminarUsuario() throws Exception {
        when(service.eliminarUsuario(1)).thenReturn("Usuario no encontrado");

        mockMvc.perform(delete("/api/v0/Usuario/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/v0/Usuario Crea un usuario")
    public void testAgregarUsuario() throws Exception {
        Usuario nuevo = new Usuario(3, "22921848-8", "Jose Perez", "joseperez2025", "jose.perez91@gmail.com", 956434234, true, false);
        Usuario guardado = new Usuario(3, "22921848-8", "Jose Perez", "joseperez2025", "jose.perez91@gmail.com", 956434234, true, false);

        when(service.guardarUsuario(any(Usuario.class))).thenReturn(guardado);
        when(assembler.toModel(any(Usuario.class))).thenReturn(new DummyUsuarioModel(guardado));

        mockMvc.perform(post("/api/v0/Usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andDo(print())    
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idUsuario").value(3))
            .andExpect(jsonPath("$.run").value("22921848-8"))
            .andExpect(jsonPath("$.usuario").value("Jose Perez"))
            .andExpect(jsonPath("$.contraseña").value("joseperez2025"))
            .andExpect(jsonPath("$.correo").value("jose.perez91@gmail.com"))
            .andExpect(jsonPath("$.telefono").value("956434234"))
            .andExpect(jsonPath("$.activo").value(true))
            .andExpect(jsonPath("$.eliminado").value(false));
    }

    @Test
    @DisplayName("GET /api/v0/Usuario/{idusuario} Retorna usuario existente")
    public void testBuscarPorIdExistente() throws Exception {
        Usuario usu = new Usuario(10, "12347384-8", "Jorge Manuel", "jorgemanuel2025", "jorge.manuel25@gmail.com", 947583658, true, false);

        when(service.getUsuarioId(10)).thenReturn(usu);
        when(assembler.toModel(any(Usuario.class))).thenReturn(new DummyUsuarioModel(usu));

        mockMvc.perform(get("/api/v0/Usuario/{idusuario}", 10))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idUsuario").value(10))
        .andExpect(jsonPath("$.run").value("12347384-8"))
        .andExpect(jsonPath("$.usuario").value("Jorge Manuel"))
        .andExpect(jsonPath("$.contraseña").value("jorgemanuel2025"))
        .andExpect(jsonPath("$.correo").value("jorge.manuel25@gmail.com"))
        .andExpect(jsonPath("$.telefono").value(947583658))
        .andExpect(jsonPath("$.activo").value(true))
        .andExpect(jsonPath("$.eliminado").value(false));       

        }

    @Test
    @DisplayName("PUT /api/v0/Usuario/{idusuario} Actualiza usuario existente")
    public void testActualizarUsuario() throws Exception {
        Usuario actualizado = new Usuario(10, "12347384-8", "Jorge Manuel", "jorgemanuel2025", "jorge.manuel25@gmail.com", 947583658, true, false);

        when(service.actualizarUsuario(any(Usuario.class), eq(10))).thenReturn(actualizado);
        when(assembler.toModel(any(Usuario.class))).thenReturn(new DummyUsuarioModel(actualizado));


         mockMvc.perform(put("/api/v0/Usuario/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.usuario").value("Jorge Manuel"));       
        
    }

    @Test
    @DisplayName("PUT /api/v0/Usuario/{idusuario} Retorna un 404 si no se encuentra el Usuario")
    public void testActualizarCursoNoEncontrado() throws Exception {
        when(service.actualizarUsuario(any(Usuario.class) , eq(88))).thenReturn(null);

        mockMvc.perform(put("/api/v0/Usuario/88")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Usuario())))
            .andExpect(status().isNotFound());       
    }


    

}
