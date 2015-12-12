/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Modulos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;

/**
 *
 * @author desarrollo01
 */
public class ModulosDAO extends DAO {
    
    FacesContext context2 = FacesContext.getCurrentInstance();

    public void add_modulo(Modulos mod) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_modulos(COD_MODULO,DESC_MODULO,ORDEN_MODULO,COD_USUARIO,FECHA_CREACION) VALUES (0,?,?,?,?)");
            ps.setString(1, mod.getDesc_modulo());
            ps.setInt(2, mod.getOrden_modulo());
            ps.setInt(3, Integer.parseInt(context2.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.setString(4, dateFormat.format(date));
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando mudulo: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA LISTAR LOS MODULOS DE LA APLICACION
    public ArrayList<Modulos> getModules() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select COD_MODULO,DESC_MODULO,ORDEN_MODULO from mst_modulos order by ORDEN_MODULO asc");
            ArrayList<Modulos> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Modulos m = new Modulos();
                m.setCod_modulo(rset.getInt("COD_MODULO"));
                m.setDesc_modulo(rset.getString("DESC_MODULO"));
                m.setOrden_modulo(rset.getInt("ORDEN_MODULO"));
                al.add(m);
                found = true;
            }
            if (found) {
                return al;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Error cargando modulos" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

}
