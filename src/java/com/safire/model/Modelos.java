package com.safire.model;

/**
 *
 * @author Mauricio Montoya
 */
public class Modelos {
    private int cod_marca, cod_modelo;
    private String nombre_modelo, cod_usuario, fecha_creacion;
    
    @Override
    public String toString(){
        return nombre_modelo;
    }
    
    public int getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(int cod_marca) {
        this.cod_marca = cod_marca;
    }

    public int getCod_modelo() {
        return cod_modelo;
    }

    public void setCod_modelo(int cod_modelo) {
        this.cod_modelo = cod_modelo;
    }

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
