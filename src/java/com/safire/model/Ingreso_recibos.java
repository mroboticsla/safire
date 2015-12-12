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
public class Ingreso_recibos {
    private int recibo_ini,recibo_fin,cod_residencial,num_recibo_prov,cod_poligono,cod_banco;
    private String mes_cobro,cod_sub_poligono,cod_residencia,num_cheque_docto,a_nombre_de,observacion,num_recibo_defini,reimpresion,cod_usuario_crea,cod_usuario_reimprime;
    private Date fecha_recibo_prov = new Date();
    private Date ultima_fecha_abonada = new Date();
    private float valor_recibo,saldo_actual,nuevo_saldo;
    private String propietario, fecha_abonada,forma_pago,cod_poligono2, estado_recibo;

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getNum_recibo_prov() {
        return num_recibo_prov;
    }

    public void setNum_recibo_prov(int num_recibo_prov) {
        this.num_recibo_prov = num_recibo_prov;
    }

    public int getCod_poligono() {
        return cod_poligono;
    }

    public void setCod_poligono(int cod_poligono) {
        this.cod_poligono = cod_poligono;
    }
    
    public String getCod_poligono2() {
        return cod_poligono2;
    }

    public void setCod_poligono2(String cod_poligono2) {
        this.cod_poligono2 = cod_poligono2;
    }

    public int getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(int cod_banco) {
        this.cod_banco = cod_banco;
    }

    public String getMes_cobro() {
        return mes_cobro;
    }

    public void setMes_cobro(String mes_cobro) {
        this.mes_cobro = mes_cobro;
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

    public String getNum_cheque_docto() {
        return num_cheque_docto;
    }

    public void setNum_cheque_docto(String num_cheque_docto) {
        this.num_cheque_docto = num_cheque_docto;
    }

    public String getA_nombre_de() {
        return a_nombre_de;
    }

    public void setA_nombre_de(String a_nombre_de) {
        this.a_nombre_de = a_nombre_de;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNum_recibo_defini() {
        return num_recibo_defini;
    }

    public void setNum_recibo_defini(String num_recibo_defini) {
        this.num_recibo_defini = num_recibo_defini;
    }

    public String getReimpresion() {
        return reimpresion;
    }

    public void setReimpresion(String reimpresion) {
        this.reimpresion = reimpresion;
    }

    public String getCod_usuario_crea() {
        return cod_usuario_crea;
    }

    public void setCod_usuario_crea(String cod_usuario_crea) {
        this.cod_usuario_crea = cod_usuario_crea;
    }

    public String getCod_usuario_reimprime() {
        return cod_usuario_reimprime;
    }

    public void setCod_usuario_reimprime(String cod_usuario_reimprime) {
        this.cod_usuario_reimprime = cod_usuario_reimprime;
    }

    public Date getFecha_recibo_prov() {
        return fecha_recibo_prov;
    }

    public void setFecha_recibo_prov(Date fecha_recibo_prov) {
        this.fecha_recibo_prov = fecha_recibo_prov;
    }

    public Date getUltima_fecha_abonada() {
        return ultima_fecha_abonada;
    }

    public void setUltima_fecha_abonada(Date ultima_fecha_abonada) {
        this.ultima_fecha_abonada = ultima_fecha_abonada;
    }

    public float getValor_recibo() {
        return valor_recibo;
    }

    public void setValor_recibo(float valor_recibo) {
        this.valor_recibo = valor_recibo;
    }

    public float getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(float saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public float getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(float nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public int getRecibo_ini() {
        return recibo_ini;
    }

    public void setRecibo_ini(int recibo_ini) {
        this.recibo_ini = recibo_ini;
    }

    public int getRecibo_fin() {
        return recibo_fin;
    }

    public void setRecibo_fin(int recibo_fin) {
        this.recibo_fin = recibo_fin;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getFecha_abonada() {
        return fecha_abonada;
    }
    public void setFecha_abonada(String fecha_abonada) {
        this.fecha_abonada = fecha_abonada;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public String getEstado_recibo() {
        return estado_recibo;
    }

    public void setEstado_recibo(String estado_recibo) {
        this.estado_recibo = estado_recibo;
    }

}
