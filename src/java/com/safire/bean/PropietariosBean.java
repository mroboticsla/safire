/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.safire.dao.PropietariosDAO;
import com.safire.dao.UsuariosDAO;
import com.safire.model.Propietarios;
import com.safire.model.Usuarios;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "prop_bean")
@SessionScoped
public class PropietariosBean implements Serializable {

    public Propietarios propietario = new Propietarios();
    public String cod_res = "";
    public int id_pol = 0;
    public String sub_pol = "";

    public Propietarios getPropietario() {
        if(propietario==null){
            propietario = new Propietarios();
        }
        return propietario;
    }

    public void setPropietario(Propietarios propietario) {
        this.propietario = propietario;
    }

    public String getCod_res() {
        return cod_res;
    }

    public void setCod_res(String cod_res) {
        this.cod_res = cod_res;
    }

    public int getId_pol() {
        return id_pol;
    }

    public void setId_pol(int id_pol) {
        this.id_pol = id_pol;
    }

    public String getSub_pol() {
        return sub_pol;
    }

    public void setSub_pol(String sub_pol) {
        this.sub_pol = sub_pol;
    }
    
    @PostConstruct
    public void init() {
        propietario= new Propietarios();
    }

    public void getPropietarios(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) {
        try {
            //System.out.println("residencial "+cod_residencial+" Cod Pol "+cod_poligono+" Sub Pol "+cod_sub_poligono+" Casa "+cod_residencia);
            PropietariosDAO dao;
            dao = new PropietariosDAO();
            if (dao.getPropietario(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencia) != null) {
                System.out.println("Es un propietario existente");
                this.propietario=dao.getPropietario(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencia);
            }else{
                propietario= new Propietarios();
                System.out.println("Es un propietario nuevo");
            }
            cod_res = cod_residencia;
            id_pol = cod_poligono;
            sub_pol = cod_sub_poligono;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Poligono: "+cod_poligono+"-"+cod_sub_poligono+" Residencia: "+cod_residencia, " "));
        } catch (Exception ex) {
            System.out.println("Error actualizando usuario b : " + ex.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void add_propietario() throws Exception {
        PropietariosDAO dao;
        try {
            dao = new PropietariosDAO();
            dao.upd_propietario(propietario,id_pol,sub_pol,cod_res);
        } catch (Exception e) {
            throw e;
        }
    }

}
