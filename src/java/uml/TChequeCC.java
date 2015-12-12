
package uml;

import java.sql.Date;

/**
 *
 * @author Nieto Mendoza
 */
public class TChequeCC {

    private String num_liquidacion;
    private Date fecha_liquidacion;
    private int corr_banco;
    private String num_cta_banco;
    private Date fecha_cheque;
    private String num_cheque;
    private String nombre_cheque;
    private double valor_cheque;
    private String cod_usuario;
    private Date fecha_ingreso;

    public TChequeCC() {
    }

    public TChequeCC(String num_liquidacion, Date fecha_liquidacion, int corr_banco, String num_cta_banco, Date fecha_cheque, String num_cheque, String nombre_cheque, double valor_cheque, String cod_usuario, Date fecha_ingreso) {
        this.num_liquidacion = num_liquidacion;
        this.fecha_liquidacion = fecha_liquidacion;
        this.corr_banco = corr_banco;
        this.num_cta_banco = num_cta_banco;
        this.fecha_cheque = fecha_cheque;
        this.num_cheque = num_cheque;
        this.nombre_cheque = nombre_cheque;
        this.valor_cheque = valor_cheque;
        this.cod_usuario = cod_usuario;
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getNum_liquidacion() {
        return num_liquidacion;
    }

    public void setNum_liquidacion(String num_liquidacion) {
        this.num_liquidacion = num_liquidacion;
    }

    public Date getFecha_liquidacion() {
        return fecha_liquidacion;
    }

    public void setFecha_liquidacion(Date fecha_liquidacion) {
        this.fecha_liquidacion = fecha_liquidacion;
    }

    public int getCorr_banco() {
        return corr_banco;
    }

    public void setCorr_banco(int corr_banco) {
        this.corr_banco = corr_banco;
    }

    public String getNum_cta_banco() {
        return num_cta_banco;
    }

    public void setNum_cta_banco(String num_cta_banco) {
        this.num_cta_banco = num_cta_banco;
    }

    public Date getFecha_cheque() {
        return fecha_cheque;
    }

    public void setFecha_cheque(Date fecha_cheque) {
        this.fecha_cheque = fecha_cheque;
    }

    public String getNum_cheque() {
        return num_cheque;
    }

    public void setNum_cheque(String num_cheque) {
        this.num_cheque = num_cheque;
    }

    public String getNombre_cheque() {
        return nombre_cheque;
    }

    public void setNombre_cheque(String nombre_cheque) {
        this.nombre_cheque = nombre_cheque;
    }

    public double getValor_cheque() {
        return valor_cheque;
    }

    public void setValor_cheque(double valor_cheque) {
        this.valor_cheque = valor_cheque;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }
    
}
