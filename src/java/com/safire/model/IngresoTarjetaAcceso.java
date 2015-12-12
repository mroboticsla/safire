/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.model;

import java.util.Date;

/**
 *
 * @author Mauricio Montoya
 */
public class IngresoTarjetaAcceso {
    int cod_residencial, cod_proveedor, num_tarjeta_ini, num_tarjeta_fin;
    String num_factura, cod_usuario_creacion, fecha_creacion, nom_proveedor;
    Date fecha_factura;
    float valor_factura;

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getCod_proveedor() {
        return cod_proveedor;
    }

    public void setCod_proveedor(int cod_proveedor) {
        this.cod_proveedor = cod_proveedor;
    }

    public String getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(String num_factura) {
        this.num_factura = num_factura;
    }

    public Date getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(Date fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public int getNum_tarjeta_ini() {
        return num_tarjeta_ini;
    }

    public void setNum_tarjeta_ini(int num_tarjeta_ini) {
        this.num_tarjeta_ini = num_tarjeta_ini;
    }

    public int getNum_tarjeta_fin() {
        return num_tarjeta_fin;
    }

    public void setNum_tarjeta_fin(int num_tarjeta_fin) {
        this.num_tarjeta_fin = num_tarjeta_fin;
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

    public float getValor_factura() {
        return valor_factura;
    }

    public void setValor_factura(float valor_factura) {
        this.valor_factura = valor_factura;
    }

    public String getNom_proveedor() {
        return nom_proveedor;
    }

    public void setNom_proveedor(String nom_proveedor) {
        this.nom_proveedor = nom_proveedor;
    }
    
}
