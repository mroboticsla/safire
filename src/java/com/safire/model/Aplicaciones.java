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
public class Aplicaciones {
    private int cod_modulo;
    private int cod_aplicacion;
    private String nombre_aplicacion;
    private String desc_aplicacion;
    private String ruta_aplicacion;
    private int orden_aplicacion;
    private String cod_usuario;
    private Date fecha_creacion;
    private String activo;

    public int getCod_modulo() {
        return cod_modulo;
    }

    public void setCod_modulo(int cod_modulo) {
        this.cod_modulo = cod_modulo;
    }

    public int getCod_aplicacion() {
        return cod_aplicacion;
    }

    public void setCod_aplicacion(int cod_aplicacion) {
        this.cod_aplicacion = cod_aplicacion;
    }

    public String getNombre_aplicacion() {
        return nombre_aplicacion;
    }

    public void setNombre_aplicacion(String nombre_aplicacion) {
        this.nombre_aplicacion = nombre_aplicacion;
    }

    public String getDesc_aplicacion() {
        return desc_aplicacion;
    }

    public void setDesc_aplicacion(String desc_aplicacion) {
        this.desc_aplicacion = desc_aplicacion;
    }

    public String getRuta_aplicacion() {
        return ruta_aplicacion;
    }

    public void setRuta_aplicacion(String ruta_aplicacion) {
        this.ruta_aplicacion = ruta_aplicacion;
    }

    public int getOrden_aplicacion() {
        return orden_aplicacion;
    }

    public void setOrden_aplicacion(int orden_aplicacion) {
        this.orden_aplicacion = orden_aplicacion;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
}
