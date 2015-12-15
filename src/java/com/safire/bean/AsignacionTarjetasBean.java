package com.safire.bean;

import com.safire.dao.AsignarTarjetaDAO;
import com.safire.model.Asignacion_Tarjetas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
+ *
+ * @author desarrollo03
+ */
@ManagedBean(name = "asignaciontarjetas")
@SessionScoped
public class AsignacionTarjetasBean implements Serializable{
    public Asignacion_Tarjetas asignaciontarjetas = new Asignacion_Tarjetas();
    public ArrayList<Asignacion_Tarjetas> lst_asignacion_tarjetas;

    public Asignacion_Tarjetas getAsignacion_tarjetas() {
        return asignaciontarjetas;
    }

    public void setAsignacion_Tarjetas(Asignacion_Tarjetas color) {
        this.asignaciontarjetas = asignaciontarjetas;
    }

    public ArrayList<Asignacion_Tarjetas> getlst_asignacion_tarjetas() {
        return lst_asignacion_tarjetas;
    }

    public void setLst_marcas(ArrayList<Asignacion_Tarjetas> lst_asignacion_tarjetas) {
        this.lst_asignacion_tarjetas = lst_asignacion_tarjetas;
    }
    
    @PostConstruct
    public void init() {
        try {
            getColores();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getColores(){
        try {
            AsignarTarjetaDAO dao;
            dao = new AsignarTarjetaDAO();
            lst_asignacion_tarjetas = dao.getColores();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void add_color() throws Exception{
        AsignarTarjetaDAO dao;
        try{
            dao = new AsignarTarjetaDAO();
            dao.add_color(color);
            lst_asignacion_tarjetas = dao.getColores();
            this.color.setNombre_color("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void update_color(RowEditEvent event) throws Exception {
        AsignarTarjetaDAO dao;
        try {
            dao = new AsignarTarjetaDAO();
            Asignacion_Tarjetas updateObject = (Asignacion_Tarjetas) event.getObject();
            dao.update_color(updateObject.getCod_color(), updateObject.getNombre_color());
        } catch (Exception e) {
            System.out.println("Error actualizando color : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void del_color(int cod_color) throws Exception{
        AsignarTarjetaDAO dao;
        try{
            dao = new AsignarTarjetaDAO();
            dao.del_color(cod_color);
            lst_asignacion_tarjetas = dao.getColores();
        }catch(Exception e){
            throw e;
        }
    }
}