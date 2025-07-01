package com.example.Proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    private Integer idUsuario;
    private String run;
    private String usuario;
    private String correo;
    private Integer telefono;
    private boolean activo; 
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    private boolean eliminado = false;

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }




}
