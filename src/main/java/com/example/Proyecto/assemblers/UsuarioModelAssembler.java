package com.example.Proyecto.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Proyecto.controller.ProyectoController;
import com.example.Proyecto.dto.Usuario;
import com.example.Proyecto.dto.UsuarioModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel> {

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel model = new UsuarioModel();
        model.setIdUsuario(usuario.getIdUsuario());
        model.setRun(usuario.getRun());
        model.setUsuario(usuario.getUsuario());
        model.setCorreo(usuario.getCorreo());
        model.setTelefono(usuario.getTelefono());
        
        model.add(linkTo(methodOn(ProyectoController.class).buscarUsuario(usuario.getIdUsuario())).withSelfRel());
        model.add(linkTo(methodOn(ProyectoController.class).obtenerUsuarios()).withRel("Usuarios"));
        model.add(linkTo(methodOn(ProyectoController.class).eliminarUsuario(usuario.getIdUsuario())).withRel("Eliminar"));    
        model.add(linkTo(methodOn(ProyectoController.class).actualizarUsuario(usuario.getIdUsuario(), usuario)).withRel("Actualizar"));

        return model;
    }

}
