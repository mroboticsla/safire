/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.model;

/**
 *
 * @author Frank2
 */
public class Residencias {
    private int cod_residencial,cod_poligono,ultimo_mes_abonado,ultimo_ano_abonado;
    private String cod_sub_poligono,cod_residencia,nombre_residencia,cod_usuario,fecha_creacion,estatus_residencia,fecha_ult_abono;
    private String cod_cta_conta_cxc,cod_cta_conta_cxp;
    
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

    public int getUltimo_mes_abonado() {
        return ultimo_mes_abonado;
    }

    public void setUltimo_mes_abonado(int ultimo_mes_abonado) {
        this.ultimo_mes_abonado = ultimo_mes_abonado;
    }

    public int getUltimo_ano_abonado() {
        return ultimo_ano_abonado;
    }

    public void setUltimo_ano_abonado(int ultimo_ano_abonado) {
        this.ultimo_ano_abonado = ultimo_ano_abonado;
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

    public String getNombre_residencia() {
        return nombre_residencia;
    }

    public void setNombre_residencia(String nombre_residencia) {
        this.nombre_residencia = nombre_residencia;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getEstatus_residencia() {
        return estatus_residencia;
    }

    public void setEstatus_residencia(String estatus_residencia) {
        this.estatus_residencia = estatus_residencia;
    }

    public String getFecha_ult_abono() {
        return fecha_ult_abono;
    }

    public void setFecha_ult_abono(String fecha_ult_abono) {
        this.fecha_ult_abono = fecha_ult_abono;
    }

    public String getCod_cta_conta_cxc() {
        return cod_cta_conta_cxc;
    }

    public void setCod_cta_conta_cxc(String cod_cta_conta_cxc) {
        this.cod_cta_conta_cxc = cod_cta_conta_cxc;
    }

    public String getCod_cta_conta_cxp() {
        return cod_cta_conta_cxp;
    }

    public void setCod_cta_conta_cxp(String cod_cta_conta_cxp) {
        this.cod_cta_conta_cxp = cod_cta_conta_cxp;
    }
    
}
