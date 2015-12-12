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
public class Residentes {
    private int cod_residencial,cod_poligono,corr_residente;
    private String cod_sub_poligono,cod_residencia,fecha_fin_residencia;
    private String nombre_residente,apellido_residente,num_dui,telefono,celular,cod_usuario_creacion,fecha_creacion;
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

    public int getCorr_residente() {
        return corr_residente;
    }

    public void setCorr_residente(int corr_residente) {
        this.corr_residente = corr_residente;
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

    public String getFecha_fin_residencia() {
        return fecha_fin_residencia;
    }

    public void setFecha_fin_residencia(String fecha_fin_residencia) {
        this.fecha_fin_residencia = fecha_fin_residencia;
    }

    public String getNombre_residente() {
        return nombre_residente;
    }

    public void setNombre_residente(String nombre_residente) {
        this.nombre_residente = nombre_residente;
    }

    public String getApellido_residente() {
        return apellido_residente;
    }

    public void setApellido_residente(String apellido_residente) {
        this.apellido_residente = apellido_residente;
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

    public Date getFecha_ini_residencia() {
        return fecha_ini_residencia;
    }

    public void setFecha_ini_residencia(Date fecha_ini_residencia) {
        this.fecha_ini_residencia = fecha_ini_residencia;
    }
    
    
}
