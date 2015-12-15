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
            getAsignacion_tarjetas();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getAsignacion_Tarjetas(){
        try {
            AsignarTarjetaDAO dao;
            dao = new AsignarTarjetaDAO();
            lst_asignacion_tarjetas = dao.getList();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void addAsignacion_Tarjetas() throws Exception{
        AsignarTarjetaDAO dao;
        try{
            dao = new AsignarTarjetaDAO();
            dao.add(asignaciontarjetas);
            lst_asignacion_tarjetas = dao.getList();
            this.asignaciontarjetas.setNum_tarjeta("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void updateAsignacion_Tarjetas(RowEditEvent event) throws Exception {
        AsignarTarjetaDAO dao;
        try {
            dao = new AsignarTarjetaDAO();
            Asignacion_Tarjetas updateObject = (Asignacion_Tarjetas) event.getObject();
            dao.update(updateObject.getCod_residencial(), updateObject.getCod_poligono(), updateObject.getCod_marca(), updateObject.getCod_color(), updateObject.getCod_sub_poligono(), updateObject.getCod_modelo(), updateObject.getCod_estatus(), updateObject.getCardid(), updateObject.getCod_residencia(), updateObject.getNum_tarjeta(), updateObject.getNum_placa(), updateObject.getNombre_responsable());
        } catch (Exception e) {
            System.out.println("Error actualizando color : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void delAsignacion_Tarjetas(int cod_residencial, int cod_poligono, int cod_sub_poligono, int cod_residencia, String num_tarjeta) throws Exception{
        AsignarTarjetaDAO dao;
        try{
            dao = new AsignarTarjetaDAO();
            dao.delete(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencial, num_tarjeta);
            lst_asignacion_tarjetas = dao.getList();
        }catch(Exception e){
            throw e;
        }
    }
}