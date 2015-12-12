/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Modulos;
import com.safire.model.Poligonos;
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
public class PoligonosDAO extends DAO {
    
    FacesContext context2 = FacesContext.getCurrentInstance();

    public void add_poligono(Poligonos pol) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_poligonos(cod_residencial,cod_poligono,cod_sub_poligono,nombre_poligono,fecha_creacion,cod_usuario) VALUES (1,?,?,?,now(),?)");
            ps.setInt(1, pol.getCod_poligono());
            ps.setString(2, pol.getCod_sub_poligono());
            ps.setString(3, pol.getNombre_poligono());
            ps.setInt(4, Integer.parseInt(context2.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando Poligono: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA LISTAR LOS MODULOS DE LA APLICACION
    public ArrayList<Poligonos> getPoligonos() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_poligono,a.cod_sub_poligono,a.nombre_poligono,DATE_FORMAT(a.fecha_creacion,'%d/%m/%Y') AS fecha_creacion from mst_poligonos a order by a.cod_poligono asc");
            ArrayList<Poligonos> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Poligonos p = new Poligonos();
                p.setCod_poligono(rset.getInt("cod_poligono"));
                p.setCod_sub_poligono(rset.getString("cod_sub_poligono"));          
                p.setNombre_poligono(rset.getString("nombre_poligono"));
                p.setFecha_creacion(rset.getString("fecha_creacion"));
                al.add(p);
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
