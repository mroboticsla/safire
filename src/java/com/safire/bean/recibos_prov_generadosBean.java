/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;


import com.safire.dao.recibos_prov_generadosDAO;
import com.safire.model.recibos_prov_generados;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "recibos_prov")
@ViewScoped
public class recibos_prov_generadosBean implements Serializable { 
    public recibos_prov_generados recibos_prov = new recibos_prov_generados();
    
    public ArrayList<recibos_prov_generados> ultimo_recibo_gen;

    public recibos_prov_generados getRecibos_prov() {
        return recibos_prov;
    }

    public void setRecibos_prov(recibos_prov_generados recibos_prov) {
        this.recibos_prov = recibos_prov;
    }

    public ArrayList<recibos_prov_generados> getUltimo_recibo_gen() {
        return ultimo_recibo_gen;
    }

    public void setUltimo_recibo_gen(ArrayList<recibos_prov_generados> ultimo_recibo_gen) {
        this.ultimo_recibo_gen = ultimo_recibo_gen;
    }
    

    public ArrayList<recibos_prov_generados> getRecibos_prov_gen(){
        try {
            recibos_prov_generadosDAO dao;
            dao = new recibos_prov_generadosDAO();
            return dao.getRecibos_gen();
        } catch (Exception ex) {
            Logger.getLogger(ModulesBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void add_recibos() throws Exception{
        recibos_prov_generadosDAO dao;
        try{
            dao = new recibos_prov_generadosDAO();
            dao.add_recibos(recibos_prov);
            getRecibos_prov_gen();
            this.recibos_prov.setCantidad_recibos(0);
        }catch(Exception e){
            throw e;
        }
    }
    
}
