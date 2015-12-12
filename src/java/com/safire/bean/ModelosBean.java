package com.safire.bean;

import com.safire.dao.ModelosDAO;
import com.safire.model.Modelos;
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
 * @author Mauricio Montoya
 */
@ManagedBean(name = "modelos")
@SessionScoped
public class ModelosBean implements Serializable{
    public Modelos modelo = new Modelos();
    public ArrayList<Modelos> lst_modelos;
    public String nombre_marca = "";
    public int cod_marca = -1;
    public boolean showModels = false;

    public boolean isShowModels() {
        return showModels;
    }

    public void setShowModels(boolean showModels) {
        this.showModels = showModels;
    }
    
    public int getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(int cod_marca) {
        ModelosDAO dao;
        dao = new ModelosDAO();
        this.cod_marca = cod_marca;
        nombre_marca = dao.obtener_marca(cod_marca);
        showModels = this.cod_marca > 0;
    }
    
    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }
    
    public Modelos getModelo() {
        return modelo;
    }

    public void setModelo(Modelos modelo) {
        this.modelo = modelo;
    }

    public ArrayList<Modelos> getLst_modelos() {
        return lst_modelos;
    }

    public void setLst_modelos(ArrayList<Modelos> lst_modelos) {
        this.lst_modelos = lst_modelos;
    }
    
    @PostConstruct
    public void init() {
        try {
            getModelos(0);
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getModelos(int cod_marca){
        try {
            ModelosDAO dao;
            dao = new ModelosDAO();
            lst_modelos = dao.getModelos(cod_marca);
            
            this.cod_marca = cod_marca;
            nombre_marca = dao.obtener_marca(cod_marca);
            
            showModels = this.cod_marca > 0;
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_modelo() throws Exception{
        ModelosDAO dao;
        try{
            dao = new ModelosDAO();
            dao.add_modelo(cod_marca, modelo);
            lst_modelos = dao.getModelos(cod_marca);
            this.modelo.setNombre_modelo("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void update_modelo(RowEditEvent event) throws Exception {
        ModelosDAO dao;
        try {
            dao = new ModelosDAO();
            Modelos updateObject = (Modelos) event.getObject();
            dao.update_modelo(updateObject.getCod_modelo(), updateObject.getNombre_modelo());
        } catch (Exception e) {
            System.out.println("Error actualizando marca : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void del_modelo(int cod_modelo) throws Exception{
        ModelosDAO dao;
        try{
            dao = new ModelosDAO();
            dao.del_modelos(cod_modelo);
            lst_modelos = dao.getModelos(cod_marca);
        }catch(Exception e){
            System.out.println(e.toString());
            throw e;
        }
    }
}
