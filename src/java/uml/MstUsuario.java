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
public class MstUsuario {
    private String codigoUsuario;
    private int codigoRol;
    private String password;
    private String estado;

    public MstUsuario() {
    }

    public MstUsuario(int codigoRol, String estado) {
        this.codigoRol = codigoRol;
        this.estado = estado;
    }

    
    
    public MstUsuario(String codigoUsuario, int codigoRol, String password, String estado) {
        this.codigoUsuario = codigoUsuario;
        this.codigoRol = codigoRol;
        this.password = password;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(int codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
