/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.model;

import java.util.Date;

/**
 *
 * @author desarrollo01
 */
public class Roles {
    
    private int cod_rol;
    private String desc_rol, cod_usuario;
    private Date fecha_creacion;

    public int getCod_rol() {
        return cod_rol;
    }

    public void setCod_rol(int cod_rol) {
        this.cod_rol = cod_rol;
    }

    public String getDesc_rol() {
        return desc_rol;
    }

    public void setDesc_rol(String desc_rol) {
        this.desc_rol = desc_rol;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    
    
}
