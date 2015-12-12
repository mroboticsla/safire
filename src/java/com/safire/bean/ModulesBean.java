/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;

import com.safire.dao.ModulosDAO;
import com.safire.model.Modulos;
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
@ManagedBean(name = "module")
@SessionScoped
public class ModulesBean implements Serializable { 
    public Modulos modulo = new Modulos();

    public Modulos getModulo() {
        return modulo;
    }

    public void setModulo(Modulos modulo) {
        this.modulo = modulo;
    }
    
    
    public void add_modulo() throws Exception{
        ModulosDAO dao;
        try{
            dao = new ModulosDAO();
            dao.add_modulo(modulo);
            this.modulo.setCod_modulo(0);
            this.modulo.setDesc_modulo("");
            this.modulo.setOrden_modulo(0);
        }catch(Exception e){
            throw e;
        }
    }
    
    public ArrayList<Modulos> getModules(){
        try {
            ModulosDAO dao;
            dao = new ModulosDAO();
            return dao.getModules();
        } catch (Exception ex) {
            Logger.getLogger(ModulesBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void leer_ap2(){
            System.out.println("APP seleccionada");   
    }
    
}
