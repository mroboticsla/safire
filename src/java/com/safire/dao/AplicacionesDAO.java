/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Aplicaciones;
import com.safire.model.Modulos;
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

public class AplicacionesDAO extends DAO {

    private boolean id_selec = false;
    public String desc_modulo = "";
    public int id_modulo = 0;
    FacesContext context2 = FacesContext.getCurrentInstance();

    //METODO PARA LISTAR LAS APLICACIONES DE UN MODULO ESTE METODO RECIBE COMO PARAMETRO UN OBJETO DE TIPO MODULOS
    public ArrayList<Aplicaciones> getAplicaciones(Modulos m) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select ORDEN_APLICACION,NOMBRE_APLICACION,DESC_APLICACION,COD_APLICACION,RUTA_APLICACION from mst_aplicaciones where cod_modulo=?");
            ArrayList<Aplicaciones> al = new ArrayList<>();
            ResultSet rset;
            ps.setInt(1, m.getCod_modulo());
            //System.out.println("Modulo seleccionado " + m.getCod_modulo());
            rset = ps.executeQuery();
            while (rset.next()) {
                Aplicaciones app = new Aplicaciones();
                app.setOrden_aplicacion(rset.getInt("ORDEN_APLICACION"));
                app.setNombre_aplicacion(rset.getString("NOMBRE_APLICACION"));
                app.setDesc_aplicacion(rset.getString("DESC_APLICACION"));
                app.setCod_aplicacion(rset.getInt("COD_APLICACION"));
                app.setRuta_aplicacion(rset.getString("RUTA_APLICACION"));
                al.add(app);
            }
            return al;
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //METODO USADO PARA OBTENER EL NOMBRE DEL MODULO SELECCIONADO POR MEDIO DE COD_MODULO
    public String leer_Mod(Modulos m) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select DESC_MODULO,COD_MODULO from mst_modulos where cod_modulo=?");
            ResultSet rset;
            ps.setInt(1, m.getCod_modulo());
            rset = ps.executeQuery();
            while (rset.next()) {
                desc_modulo = (rset.getString("DESC_MODULO"));
                id_modulo = (rset.getInt("COD_MODULO"));
                id_selec = true;
            }
            return desc_modulo;
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA INSERTAR UNA APLICACION PARA UN MODULO, PRIMERO VERIFICA QUE HAYA UN MODULO SELECCIOANADO
    public void add_app(Aplicaciones a, String m) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select COD_MODULO from mst_modulos where DESC_MODULO=?");
            ResultSet rset_m;
            ps_m.setString(1, m);
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                id_modulo = (rset_m.getInt("COD_MODULO"));
                id_selec = true;
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de modulo " + ex.getMessage());
        }

        //SI HAY UN MODULO SELECCIONADO INSERTO LA APLICACION
        try {
            this.Conectar();
            if (id_selec) {
                PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_aplicaciones(COD_MODULO,COD_APLICACION,NOMBRE_APLICACION,DESC_APLICACION,RUTA_APLICACION,ORDEN_APLICACION,COD_USUARIO,FECHA_CREACION,ACTIVO) VALUES (?,?,?,?,?,?,?,NOW(),'S')");
                ps.setInt(1, id_modulo);
                ps.setInt(2, a.getCod_aplicacion());
                ps.setString(3, a.getNombre_aplicacion());
                ps.setString(4, a.getDesc_aplicacion());
                ps.setString(5, a.getRuta_aplicacion());
                ps.setInt(6, a.getOrden_aplicacion());
                ps.setInt(7, Integer.parseInt(context2.getExternalContext().getSessionMap().get("user_id").toString()));
                //ps.setString(6, dateFormat.format(date));
                ps.executeUpdate();
                FacesMessage msg3 = new FacesMessage("Aplicacion Insertada", "");
                FacesContext.getCurrentInstance().addMessage(null, msg3);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debe seleccionar un modulo para agregar aplicaciones", "Debe seleccionar un modulo para agregar aplicaciones"));
            }
        } catch (Exception e) {
            System.out.println("Error insertando aplicacion: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //ESTE METODO TAMBIEN SE USA PARA OBTENER LAS APLICACIONES DE UN MODULO PERO RECIVE COMO PARAMETRO EL NOMBRE DEL MODULO
    public ArrayList<Aplicaciones> getAplicaciones_s(String m) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select COD_MODULO from mst_modulos where DESC_MODULO=?");
            ResultSet rset_m;
            ps_m.setString(1, m);
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                id_modulo = (rset_m.getInt("COD_MODULO"));
                id_selec = true;
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de modulo " + ex.getMessage());
        }
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select ORDEN_APLICACION,NOMBRE_APLICACION,DESC_APLICACION,COD_APLICACION,RUTA_APLICACION from mst_aplicaciones where cod_modulo=?");
            ArrayList<Aplicaciones> al = new ArrayList<>();
            ResultSet rset;
            ps.setInt(1, id_modulo);
            //System.out.println("Modulo seleccionado " + id_modulo);
            rset = ps.executeQuery();
            while (rset.next()) {
                Aplicaciones app = new Aplicaciones();
                app.setOrden_aplicacion(rset.getInt("ORDEN_APLICACION"));
                app.setNombre_aplicacion(rset.getString("NOMBRE_APLICACION"));
                app.setDesc_aplicacion(rset.getString("DESC_APLICACION"));
                app.setCod_aplicacion(rset.getInt("COD_APLICACION"));
                app.setRuta_aplicacion(rset.getString("RUTA_APLICACION"));
                al.add(app);
            }
            return al;
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //Metodo para actualizar una aplicacion
    public void update_app(int code_app, String nombre_app,int orden_app,String ruta_app,String m) throws Exception {
        Aplicaciones a = null;
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select COD_MODULO from mst_modulos where DESC_MODULO=?");
            ResultSet rset_m;
            ps_m.setString(1, m);
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                id_modulo = (rset_m.getInt("COD_MODULO"));
                id_selec = true;
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de modulo " + ex.getMessage());
        }
        try {
            this.Conectar();
            a = new Aplicaciones();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE mst_aplicaciones SET NOMBRE_APLICACION=?,ORDEN_APLICACION = ?,RUTA_APLICACION=? WHERE COD_APLICACION=? and COD_MODULO=?");
            ps.setString(1, nombre_app);
            ps.setInt(2, orden_app);
            ps.setString(3, ruta_app);
            ps.setInt(4, code_app);
            ps.setInt(5, id_modulo);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Aplicacion Actualizada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error Actualizando aplicacion: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_app(String code_app, String m) throws Exception{
        
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select COD_MODULO from mst_modulos where DESC_MODULO=?");
            ResultSet rset_m;
            ps_m.setString(1, m);
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                id_modulo = (rset_m.getInt("COD_MODULO"));
                id_selec = true;
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de modulo " + ex.getMessage());
        }
        
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_aplicaciones WHERE COD_APLICACION=? and COD_MODULO=?");
            ps.setString(1, code_app);
            ps.setInt(2, id_modulo);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Aplicacion eliminada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error Eliminando aplicacion: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
