
package uml;

import java.sql.Date;

/**
 *
 * @author Nieto Mendoza
 */
public class MstCuenta {
    private MstBanco corr_banco;
    private String num_cta_banco;
    private String cod_cta_conta;
    private double saldo_cta;
    private Date fecha_creacion;
    private String cod_usuario;
    private String activo;

    public MstCuenta() {
    }

    public MstCuenta(MstBanco corr_banco, String num_cta_banco, String cod_cta_conta, double saldo_cta, Date fecha_creacion, String cod_usuario, String activo) {
        this.corr_banco = corr_banco;
        this.num_cta_banco = num_cta_banco;
        this.cod_cta_conta = cod_cta_conta;
        this.saldo_cta = saldo_cta;
        this.fecha_creacion = fecha_creacion;
        this.cod_usuario = cod_usuario;
        this.activo = activo;
    }

    public MstBanco getCorr_banco() {
        return corr_banco;
    }

    public void setCorr_banco(MstBanco corr_banco) {
        this.corr_banco = corr_banco;
    }

    public String getNum_cta_banco() {
        return num_cta_banco;
    }

    public void setNum_cta_banco(String num_cta_banco) {
        this.num_cta_banco = num_cta_banco;
    }

    public String getCod_cta_conta() {
        return cod_cta_conta;
    }

    public void setCod_cta_conta(String cod_cta_conta) {
        this.cod_cta_conta = cod_cta_conta;
    }

    public double getSaldo_cta() {
        return saldo_cta;
    }

    public void setSaldo_cta(double saldo_cta) {
        this.saldo_cta = saldo_cta;
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
