/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.model;

import java.util.Date;

/**
 *
 * @author Frank2
 */
public class Propietarios {
    private int cod_residencial,cod_poligono,corr_propietario;
    private String cod_sub_poligono,cod_residencia,fecha_fin_residencia;
    private String nombre_propietario,apellido_propietario,num_dui,telefono,celular,cod_usuario_creacion,fecha_creacion;
    private Date fecha_ini_residencia = new Date();

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

    public int getCorr_propietario() {
        return corr_propietario;
    }

    public void setCorr_propietario(int corr_propietario) {
        this.corr_propietario = corr_propietario;
    }

    public String getCod_sub_poligono() {
        return cod_sub_poligono;
    }

    public void setCod_sub_poligono(String cod_sub_poligono) {
        this.cod_sub_poligono = cod_sub_poligono;
    }

    public String getCod_residencia() {
        return cod_residencia;
    }

    public void setCod_residencia(String cod_residencia) {
        this.cod_residencia = cod_residencia;
    }

    public Date getFecha_ini_residencia() {
        return fecha_ini_residencia;
    }

    public void setFecha_ini_residencia(Date fecha_ini_residencia) {
        this.fecha_ini_residencia = fecha_ini_residencia;
    }

    public String getFecha_fin_residencia() {
        return fecha_fin_residencia;
    }

    public void setFecha_fin_residencia(String fecha_fin_residencia) {
        this.fecha_fin_residencia = fecha_fin_residencia;
    }

    public String getNombre_propietario() {
        return nombre_propietario;
    }

    public void setNombre_propietario(String nombre_propietario) {
        this.nombre_propietario = nombre_propietario;
    }

    public String getApellido_propietario() {
        return apellido_propietario;
    }

    public void setApellido_propietario(String apellido_propietario) {
        this.apellido_propietario = apellido_propietario;
    }

    public String getNum_dui() {
        return num_dui;
    }

    public void setNum_dui(String num_dui) {
        this.num_dui = num_dui;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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
    
}
