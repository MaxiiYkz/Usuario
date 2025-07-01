package com.example.Proyecto.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(unique = true, length = 13, nullable = false)
    private String run;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private int telefono;

    private boolean activo;

    // Verificar estado de la cuenta
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    //Eliminar logicamente
    @Column(nullable = false)
    private boolean eliminado = false;

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

}