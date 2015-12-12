/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;

import com.safire.dao.ModulosDAO;
import com.safire.dao.PoligonosDAO;
import com.safire.model.Modulos;
import com.safire.model.Poligonos;
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
@ManagedBean(name = "poligono")
@SessionScoped
public class PoligonosBean implements Serializable { 
    public Poligonos poligono = new Poligonos();

    public Poligonos getPoligono() {
        return poligono;
    }

    public void setPoligono(Poligonos poligono) {
        this.poligono = poligono;
    }
    
    
    public void add_poligono() throws Exception{
        PoligonosDAO dao;
        try{
            dao = new PoligonosDAO();
            dao.add_poligono(poligono);
            this.poligono.setCod_poligono(0);
            this.poligono.setCod_sub_poligono("");
            this.poligono.setNombre_poligono("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public ArrayList<Poligonos> getPoligonos(){
        try {
            PoligonosDAO dao;
            dao = new PoligonosDAO();
            return dao.getPoligonos();
        } catch (Exception ex) {
            Logger.getLogger(PoligonosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void leer_ap2(){
            System.out.println("APP seleccionada");   
    }
    
}
