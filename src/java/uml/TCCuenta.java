/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

/**
 *
 * @author Nieto Mendoza
 */
public class TCCuenta {
    private int cod_residencial;
    private String cod_cta_conta;
    private String desc_cta_contab;
    private double saldo;

    public TCCuenta() {
    }

    public TCCuenta(int cod_residencial, String cod_cta_conta, String desc_cta_contab, double saldo) {
        this.cod_residencial = cod_residencial;
        this.cod_cta_conta = cod_cta_conta;
        this.desc_cta_contab = desc_cta_contab;
        this.saldo = saldo;
    }

    public int getCod_residencial() {
        return cod_residencial;
    }

    public String getCod_cta_conta() {
        return cod_cta_conta;
    }

    public String getDesc_cta_contab() {
        return desc_cta_contab;
    }

    public double getSaldo() {
        return saldo;
    }
    
    
}
