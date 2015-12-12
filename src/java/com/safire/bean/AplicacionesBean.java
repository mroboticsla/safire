/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.safire.dao.AplicacionesDAO;
import com.safire.model.Aplicaciones;
import com.safire.model.Modulos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "app")
@SessionScoped
public class AplicacionesBean implements Serializable {

    public ArrayList<Aplicaciones> list_App;
    public Aplicaciones aplicacion = new Aplicaciones();
    public String d_mod = "Ningun modulo seleccionado";

    public Aplicaciones getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicaciones aplicacion) {
        this.aplicacion = aplicacion;
    }

    public ArrayList<Aplicaciones> getList_App() {
        return list_App;
    }

    public void setList_App(ArrayList<Aplicaciones> list_App) {
        this.list_App = list_App;
    }

    public String getD_mod() {
        return d_mod;
    }

    public void setD_mod(String d_mod) {
        this.d_mod = d_mod;
    }

    //Metodo para obtener las aplicaciones
    public void getAplications(Modulos m) {
        try {
            AplicacionesDAO dao;
            dao = new AplicacionesDAO();
            if (dao.getAplicaciones(m) != null) {
                list_App = dao.getAplicaciones(m);
                leer_Modulo(m);
            }
            if (list_App.isEmpty()) {
                list_App = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(AplicacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leer_Modulo(Modulos m) {
        try {
            AplicacionesDAO dao;
            dao = new AplicacionesDAO();
            if (!"".equals(dao.leer_Mod(m))) {
                d_mod = dao.leer_Mod(m);
            } else {
                d_mod = "Ningun modulo seleccionado";
            }
        } catch (Exception ex) {
            Logger.getLogger(AplicacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add_aplicacion(String m) throws Exception {
        AplicacionesDAO dao2;
        try {
            dao2 = new AplicacionesDAO();
            dao2.add_app(aplicacion, m);
            this.aplicacion.setOrden_aplicacion(0);
            this.aplicacion.setNombre_aplicacion("");
            this.aplicacion.setDesc_aplicacion("");
            this.aplicacion.setCod_aplicacion(0);
            list_App = dao2.getAplicaciones_s(m);
        } catch (Exception e) {
            throw e;
        }
    }

    public void update_app(RowEditEvent event) throws Exception {
        AplicacionesDAO dao3;
        try {
            dao3 = new AplicacionesDAO();
            //dao3.leer_app(((Aplicaciones) event.getObject()).getCod_aplicacion());
            dao3.update_app(((Aplicaciones) event.getObject()).getCod_aplicacion(),((Aplicaciones) event.getObject()).getNombre_aplicacion(),((Aplicaciones) event.getObject()).getOrden_aplicacion(),((Aplicaciones) event.getObject()).getRuta_aplicacion(),d_mod);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void del_app(String code_app, String m) throws Exception{
        AplicacionesDAO dao;
        try{
            dao = new AplicacionesDAO();
            dao.del_app(code_app,m);
            System.out.println("App a eliminar: " + code_app);
            list_App = dao.getAplicaciones_s(m);
        }catch(Exception e){
            System.out.println("Error Eliminando aplicacion: " + e.getMessage());
            throw e;
        }
    }

}
