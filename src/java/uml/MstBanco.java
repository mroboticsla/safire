
package uml;

import java.sql.Date;

/**
 *
 * @author Nieto Mendoza
 */
public class MstBanco {
    private int cod_residencial;
    private int corr_banco;
    private String nombre_banco;
    private Date fecha_creacion;
    private String cod_usuario;
    private String activo;

    public MstBanco() {
    }

    public MstBanco(int corr_banco, String nombre_banco) {
        this.corr_banco = corr_banco;
        this.nombre_banco = nombre_banco;
    }

    public MstBanco(int corr_banco) {
        this.corr_banco = corr_banco;
    }

    
    
    public MstBanco(int cod_residencial, int corr_banco, String nombre_banco, Date fecha_creacion, String cod_usuario, String activo) {
        this.cod_residencial = cod_residencial;
        this.corr_banco = corr_banco;
        this.nombre_banco = nombre_banco;
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

    public int getCorr_banco() {
        return corr_banco;
    }

    public void setCorr_banco(int corr_banco) {
        this.corr_banco = corr_banco;
    }

    public String getNombre_banco() {
        return nombre_banco;
    }

    public void setNombre_banco(String nombre_banco) {
        this.nombre_banco = nombre_banco;
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


