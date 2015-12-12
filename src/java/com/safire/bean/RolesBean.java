/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;

import com.safire.dao.ModulosDAO;
import com.safire.dao.RolesDAO;
import com.safire.model.Modulos;
import com.safire.model.Roles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "rol")
@SessionScoped
public class RolesBean implements Serializable { 
    public Roles rol = new Roles();

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }
    
    public ArrayList<Roles> getRoles(){
        try {
            RolesDAO dao;
            dao = new RolesDAO();
            return dao.getRoles();
        } catch (Exception ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void add_rol(String user_id) throws Exception{
        RolesDAO dao;
        try{
            dao = new RolesDAO();
            dao.add_rol(rol,user_id);
            this.rol.setCod_rol(0);
            this.rol.setDesc_rol("");
        }catch(Exception e){
            throw e;
        }
    }
    
}
