
package uml;

import java.sql.Date;

/**
 *
 * @author Nieto Mendoza
 */
public class TGastoCC {

    private int cod_residencial;
    private String num_liquidacion;
    private Date fecha_liquidacion;
    private int corr_gasto;
    private String num_docto;
    private Date fecha_docto;
    private double valor_docto;
    private String cod_cta_conta;
    private String desc_gasto;
    private String estado;
    private String cod_usuario;
    private Date fecha_ingreso;
    private double total;
    private double efectivo;
    public TGastoCC() {
    }

    public TGastoCC(double total, double efectivo) {
        this.total = total;
        this.efectivo = efectivo;
    }

    public TGastoCC(String num_liquidacion, Date fecha_liquidacion) {
        this.num_liquidacion = num_liquidacion;
        this.fecha_liquidacion = fecha_liquidacion;
    }

    
    public TGastoCC(int cod_residencial, String num_liquidacion, Date fecha_liquidacion, int corr_gasto, String num_docto, Date fecha_docto, double valor_docto, String cod_cta_conta, String desc_gasto, String estado, String cod_usuario, Date fecha_ingreso) {
        this.cod_residencial = cod_residencial;
        this.num_liquidacion = num_liquidacion;
        this.fecha_liquidacion = fecha_liquidacion;
        this.corr_gasto = corr_gasto;
        this.num_docto = num_docto;
        this.fecha_docto = fecha_docto;
        this.valor_docto = valor_docto;
        this.cod_cta_conta = cod_cta_conta;
        this.desc_gasto = desc_gasto;
        this.estado = estado;
        this.cod_usuario = cod_usuario;
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCod_residencial() {
        return cod_residencial;
    }

    public void setCod_residencial(int cod_residencial) {
        this.cod_residencial = cod_residencial;
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

    public int getCorr_gasto() {
        return corr_gasto;
    }

    public void setCorr_gasto(int corr_gasto) {
        this.corr_gasto = corr_gasto;
    }

    public String getNum_docto() {
        return num_docto;
    }

    public void setNum_docto(String num_docto) {
        this.num_docto = num_docto;
    }

    public Date getFecha_docto() {
        return fecha_docto;
    }

    public void setFecha_docto(Date fecha_docto) {
        this.fecha_docto = fecha_docto;
    }

    public double getValor_docto() {
        return valor_docto;
    }

    public void setValor_docto(double valor_docto) {
        this.valor_docto = valor_docto;
    }

    public String getCod_cta_conta() {
        return cod_cta_conta;
    }

    public void setCod_cta_conta(String cod_cta_conta) {
        this.cod_cta_conta = cod_cta_conta;
    }

    public String getDesc_gasto() {
        return desc_gasto;
    }

    public void setDesc_gasto(String desc_gasto) {
        this.desc_gasto = desc_gasto;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }
    
}
