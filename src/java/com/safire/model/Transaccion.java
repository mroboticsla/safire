
package com.safire.model;

public class Transaccion {
    int cod_transaccion, cod_residencial, corr_forma_pago, tipo_ingreso, tipo_egreso;
    String desc_transaccion, concepto_transaccion, cod_cta_contab_debe, cod_cta_contab_haber, activo;

    public int getCod_transaccion() {
        return cod_transaccion;
    }

    public void setCod_transaccion(int cod_transaccion) {
        this.cod_transaccion = cod_transaccion;
    }

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
    }

    public int getCorr_forma_pago() {
        return corr_forma_pago;
    }

    public void setCorr_forma_pago(int corr_forma_pago) {
        this.corr_forma_pago = corr_forma_pago;
    }

    public int getTipo_ingreso() {
        return tipo_ingreso;
    }

    public void setTipo_ingreso(int tipo_ingreso) {
        this.tipo_ingreso = tipo_ingreso;
    }

    public int getTipo_egreso() {
        return tipo_egreso;
    }

    public void setTipo_egreso(int tipo_egreso) {
        this.tipo_egreso = tipo_egreso;
    }

    public String getDesc_transaccion() {
        return desc_transaccion;
    }

    public void setDesc_transaccion(String desc_transaccion) {
        this.desc_transaccion = desc_transaccion;
    }

    public String getConcepto_transaccion() {
        return concepto_transaccion;
    }

    public void setConcepto_transaccion(String concepto_transaccion) {
        this.concepto_transaccion = concepto_transaccion;
    }

    public String getCod_cta_contab_debe() {
        return cod_cta_contab_debe;
    }

    public void setCod_cta_contab_debe(String cod_cta_contab_debe) {
        this.cod_cta_contab_debe = cod_cta_contab_debe;
    }

    public String getCod_cta_contab_haber() {
        return cod_cta_contab_haber;
    }

    public void setCod_cta_contab_haber(String cod_cta_contab_haber) {
        this.cod_cta_contab_haber = cod_cta_contab_haber;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
}
