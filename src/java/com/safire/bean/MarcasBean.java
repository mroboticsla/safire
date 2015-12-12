package com.safire.bean;

import com.safire.dao.MarcasDAO;
import com.safire.model.Marcas;
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
@ManagedBean(name = "marcas")
@SessionScoped
public class MarcasBean implements Serializable{
    public Marcas marca = new Marcas();
    public ArrayList<Marcas> lst_marcas;

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    public ArrayList<Marcas> getLst_marcas() {
        return lst_marcas;
    }

    public void setLst_marcas(ArrayList<Marcas> lst_marcas) {
        this.lst_marcas = lst_marcas;
    }
    
    @PostConstruct
    public void init() {
        try {
            getMarcas();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getMarcas(){
        try {
            MarcasDAO dao;
            dao = new MarcasDAO();
            lst_marcas = dao.getMarcas();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_marca() throws Exception{
        MarcasDAO dao;
        try{
            dao = new MarcasDAO();
            dao.add_marca(marca);
            lst_marcas = dao.getMarcas();
            this.marca.setNombre_marca("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void update_marca(RowEditEvent event) throws Exception {
        MarcasDAO dao;
        try {
            dao = new MarcasDAO();
            Marcas updateObject = (Marcas) event.getObject();
            dao.update_marca(updateObject.getCod_marca(), updateObject.getNombre_marca());
        } catch (Exception e) {
            System.out.println("Error actualizando marca : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void del_marca(int cod_marca) throws Exception{
        MarcasDAO dao;
        try{
            dao = new MarcasDAO();
            dao.del_marcas(cod_marca);
            lst_marcas = dao.getMarcas();
        }catch(Exception e){
            throw e;
        }
    }
}
