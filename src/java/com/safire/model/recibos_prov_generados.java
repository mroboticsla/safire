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
public class recibos_prov_generados {
    private int cod_residencial,corr_generados,recibo_ini_prov,recibo_fin_prov,cantidad_recibos;
    private String cod_usuario,fecha_generados;

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getCorr_generados() {
        return corr_generados;
    }

    public void setCorr_generados(int corr_generados) {
        this.corr_generados = corr_generados;
    }

    public int getRecibo_ini_prov() {
        return recibo_ini_prov;
    }

    public void setRecibo_ini_prov(int recibo_ini_prov) {
        this.recibo_ini_prov = recibo_ini_prov;
    }

    public int getRecibo_fin_prov() {
        return recibo_fin_prov;
    }

    public void setRecibo_fin_prov(int recibo_fin_prov) {
        this.recibo_fin_prov = recibo_fin_prov;
    }

    public String getFecha_generados() {
        return fecha_generados;
    }

    public void setFecha_generados(String fecha_generados) {
        this.fecha_generados = fecha_generados;
    }
    
    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public int getCantidad_recibos() {
        return cantidad_recibos;
    }

    public void setCantidad_recibos(int cantidad_recibos) {
        this.cantidad_recibos = cantidad_recibos;
    }

}
