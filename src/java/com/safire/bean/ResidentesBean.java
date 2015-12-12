/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.safire.dao.PropietariosDAO;
import com.safire.dao.ResidentesDAO;
import com.safire.model.Residentes;
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
@ManagedBean(name = "residente_bean")
@SessionScoped
public class ResidentesBean implements Serializable {

    public Residentes residente = new Residentes();
    public String cod_res = "";
    public int id_pol = 0;
    public String sub_pol = "";

    public Residentes getResidente() {
        return residente;
    }

    public void setResidente(Residentes residente) {
        this.residente = residente;
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
        residente= new Residentes();
    }

    public void getResidentes(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) {
        try {
            //System.out.println("residencial "+cod_residencial+" Cod Pol "+cod_poligono+" Sub Pol "+cod_sub_poligono+" Casa "+cod_residencia);
            ResidentesDAO dao;
            dao = new ResidentesDAO();
            if (dao.getResidente(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencia) != null) {
                System.out.println("Es un residente existente");
                this.residente=dao.getResidente(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencia);
            }else{
                residente= new Residentes();
                System.out.println("Es un residente nuevo");
            }
            cod_res = cod_residencia;
            id_pol = cod_poligono;
            sub_pol = cod_sub_poligono;
        } catch (Exception ex) {
            System.out.println("Error Obteniendo Residente: " + ex.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void add_residente() throws Exception {
        ResidentesDAO dao;
        try {
            dao = new ResidentesDAO();
            dao.upd_residente(residente, id_pol, sub_pol, cod_res);
        } catch (Exception e) {
            throw e;
        }
    }

}
