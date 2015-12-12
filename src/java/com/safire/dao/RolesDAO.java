/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Aplicaciones;
import com.safire.model.Modulos;
import com.safire.model.Roles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author desarrollo01
 */
public class RolesDAO extends DAO {
    
    FacesContext context2 = FacesContext.getCurrentInstance();
    
    //METODO PARA LISTAR LOS MODULOS DE LA APLICACION
    public ArrayList<Roles> getRoles() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select cod_rol,desc_rol from mst_roles");
            ArrayList<Roles> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Roles r = new Roles();
                r.setCod_rol(rset.getInt("cod_rol"));
                r.setDesc_rol(rset.getString("desc_rol"));
                al.add(r);
                found = true;
            }
            if (found) {
                return al;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Error cargando Roles" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }
    
    public void add_rol(Roles rol,String user_id) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_roles(COD_ROL,DESC_ROL,COD_USUARIO,FECHA_CREACION) VALUES (0,?,?,?)");
            ps.setString(1, rol.getDesc_rol());
            ps.setInt(2, Integer.parseInt(user_id));
            ps.setString(3, dateFormat.format(date));
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando rol: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
}
