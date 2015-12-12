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
public class pagos_infoadd {
    
    private int cod_banco,num_recibo_prov;
    private String num_cheque_docto,a_nombre_de,observacion;

    public int getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(int cod_banco) {
        this.cod_banco = cod_banco;
    }

    public int getNum_recibo_prov() {
        return num_recibo_prov;
    }

    public void setNum_recibo_prov(int num_recibo_prov) {
        this.num_recibo_prov = num_recibo_prov;
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
    
}
