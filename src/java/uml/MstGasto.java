/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

import java.sql.Date;

/**
 *
 * @author Nieto Mendoza
 */
public class MstGasto {
    private int cod_residencial;
    private int corr_gasto;
    private String desc_gasto;
    private String cod_cta_conta;
    private double valor_gasto;
    private Date fecha_creacion;
    private String cod_usuario;
    private String activo;

    public MstGasto() {
    }

    public MstGasto(int cod_residencial, int corr_gasto, String desc_gasto, String cod_cta_conta, double valor_gasto, Date fecha_creacion, String cod_usuario, String activo) {
        this.cod_residencial = cod_residencial;
        this.corr_gasto = corr_gasto;
        this.desc_gasto = desc_gasto;
        this.cod_cta_conta = cod_cta_conta;
        this.valor_gasto = valor_gasto;
        this.fecha_creacion = fecha_creacion;
        this.cod_usuario = cod_usuario;
        this.activo = activo;
    }

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getCorr_gasto() {
        return corr_gasto;
    }

    public void setCorr_gasto(int corr_gasto) {
        this.corr_gasto = corr_gasto;
    }

    public String getDesc_gasto() {
        return desc_gasto;
    }

    public void setDesc_gasto(String desc_gasto) {
        this.desc_gasto = desc_gasto;
    }

    public String getCod_cta_conta() {
        return cod_cta_conta;
    }

    public void setCod_cta_conta(String cod_cta_conta) {
        this.cod_cta_conta = cod_cta_conta;
    }

    public double getValor_gasto() {
        return valor_gasto;
    }

    public void setValor_gasto(double valor_gasto) {
        this.valor_gasto = valor_gasto;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

   
}
