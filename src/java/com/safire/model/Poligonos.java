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
public class Poligonos {
    
    private int cod_residencial,cod_poligono;
    private String cod_sub_poligono,nombre_poligono,fecha_creacion,cod_usuario,mst_poligonoscol;

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getCod_poligono() {
        return cod_poligono;
    }

    public void setCod_poligono(int cod_poligono) {
        this.cod_poligono = cod_poligono;
    }

    public String getCod_sub_poligono() {
        return cod_sub_poligono;
    }

    public void setCod_sub_poligono(String cod_sub_poligono) {
        this.cod_sub_poligono = cod_sub_poligono;
    }

    public String getNombre_poligono() {
        return nombre_poligono;
    }

    public void setNombre_poligono(String nombre_poligono) {
        this.nombre_poligono = nombre_poligono;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getMst_poligonoscol() {
        return mst_poligonoscol;
    }

    public void setMst_poligonoscol(String mst_poligonoscol) {
        this.mst_poligonoscol = mst_poligonoscol;
    }
    
}
