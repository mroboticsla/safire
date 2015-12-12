/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.model;

/**
 *
 * @author desarrollo01
 */
public class Usuarios {
    private int corr_usuario,cod_rol,cod_residencial;
    private String residencial,cod_usuario,nom_usuario,contraseña,activo,cod_usuario_creacion,fecha_creacion,rol,autoriza_correccion;

    public int getCorr_usuario() {
        return corr_usuario;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }
    
    public void setCorr_usuario(int corr_usuario) {
        this.corr_usuario = corr_usuario;
    }

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }
    
    public int getCod_rol() {
        return cod_rol;
    }

    public void setCod_rol(int cod_rol) {
        this.cod_rol = cod_rol;
    }

    public String getNom_usuario() {
        return nom_usuario;
    }

    public void setNom_usuario(String nom_usuario) {
        this.nom_usuario = nom_usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getCod_usuario_creacion() {
        return cod_usuario_creacion;
    }

    public void setCod_usuario_creacion(String cod_usuario_creacion) {
        this.cod_usuario_creacion = cod_usuario_creacion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAutoriza_correccion() {
        return autoriza_correccion;
    }

    public void setAutoriza_correccion(String autoriza_correccion) {
        this.autoriza_correccion = autoriza_correccion;
    }

    public String getResidencial() {
        return residencial;
    }

    public void setResidencial(String residencial) {
        this.residencial = residencial;
    }
    
}
