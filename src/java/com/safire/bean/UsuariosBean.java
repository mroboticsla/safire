/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;

import com.safire.dao.UsuariosDAO;
import com.safire.model.Usuarios;
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
@ManagedBean(name = "user")
@SessionScoped
public class UsuariosBean implements Serializable { 
    public Usuarios usuario = new Usuarios();
    
    public ArrayList<Usuarios> lst_users;
    
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Usuarios> getLst_users() {
        return lst_users;
    }

    public void setLst_users(ArrayList<Usuarios> lst_users) {
        this.lst_users = lst_users;
    }
    
    @PostConstruct
    public void init() {
        try {
            getUsers();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getUsers(){
        try {
            UsuariosDAO dao;
            dao = new UsuariosDAO();
            lst_users = dao.getUsers();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Map<String, String> getRoles() throws Exception {
        try {
            UsuariosDAO dao;
            dao = new UsuariosDAO();
            return dao.getRoles();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Map<String, String> getResidenciales() throws Exception {
        try {
            UsuariosDAO dao2;
            dao2 = new UsuariosDAO();
            return dao2.getResideciales();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Map<String, String> getActivo() throws Exception {
        try {
            UsuariosDAO dao;
            dao = new UsuariosDAO();
            return dao.getActivo();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void update_user(RowEditEvent event) throws Exception {
        UsuariosDAO dao3;
        try {
            dao3 = new UsuariosDAO();
            //dao3.leer_app(((Aplicaciones) event.getObject()).getCod_aplicacion());
            dao3.update_user(((Usuarios) event.getObject()).getCorr_usuario(),((Usuarios) event.getObject()).getCod_usuario(),((Usuarios) event.getObject()).getNom_usuario(),((Usuarios) event.getObject()).getContraseña(),((Usuarios) event.getObject()).getRol(),((Usuarios) event.getObject()).getActivo());
        } catch (Exception e) {
            System.out.println("Error actualizando usuario b : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void add_user() throws Exception{
        UsuariosDAO dao;
        try{
            dao = new UsuariosDAO();
            dao.add_user(usuario);
            lst_users = dao.getUsers();
            this.usuario.setNom_usuario("");
            this.usuario.setCod_usuario("");
            this.usuario.setContraseña("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void del_usuario(int corr_us) throws Exception{
        UsuariosDAO dao;
        try{
            dao = new UsuariosDAO();
            dao.del_user(corr_us);
            lst_users = dao.getUsers();
        }catch(Exception e){
            throw e;
        }
    }
    
    
}
