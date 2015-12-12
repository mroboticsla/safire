package com.safire.bean;

import com.safire.dao.ColoresDAO;
import com.safire.model.Colores;
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
 *
 * @author desarrollo03
 */
@ManagedBean(name = "colores")
@SessionScoped
public class ColoresBean implements Serializable{
    public Colores color = new Colores();
    public ArrayList<Colores> lst_colores;

    public Colores getColor() {
        return color;
    }

    public void setColor(Colores color) {
        this.color = color;
    }

    public ArrayList<Colores> getLst_colores() {
        return lst_colores;
    }

    public void setLst_marcas(ArrayList<Colores> lst_colores) {
        this.lst_colores = lst_colores;
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
            ColoresDAO dao;
            dao = new ColoresDAO();
            lst_colores = dao.getColores();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_color() throws Exception{
        ColoresDAO dao;
        try{
            dao = new ColoresDAO();
            dao.add_color(color);
            lst_colores = dao.getColores();
            this.color.setNombre_color("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void update_color(RowEditEvent event) throws Exception {
        ColoresDAO dao;
        try {
            dao = new ColoresDAO();
            Colores updateObject = (Colores) event.getObject();
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
        ColoresDAO dao;
        try{
            dao = new ColoresDAO();
            dao.del_color(cod_color);
            lst_colores = dao.getColores();
        }catch(Exception e){
            throw e;
        }
    }
}
