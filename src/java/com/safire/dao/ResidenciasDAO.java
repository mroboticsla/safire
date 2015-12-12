/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Aplicaciones;
import com.safire.model.Modulos;
import com.safire.model.Poligonos;
import com.safire.model.Residencias;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author desarrollo01
 */

public class ResidenciasDAO extends DAO {

    private boolean id_selec = false;
    private int id_modulo = 0;
    public String cod_sub_pol = "";
    public int cod_pol = 0;
    FacesContext context2 = FacesContext.getCurrentInstance();
    private Map<String, String> cuentas;

    //METODO PARA LISTAR LAS APLICACIONES DE UN MODULO ESTE METODO RECIBE COMO PARAMETRO UN OBJETO DE TIPO MODULOS
    public ArrayList<Residencias> getResidencias(Poligonos p) throws Exception {
        try {
            this.Conectar();
            //System.out.println("Poligono: "+p.getCod_poligono()+" Sub pol. "+p.getCod_sub_poligono());
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencia,a.nombre_residencia,a.cod_poligono,a.cod_sub_poligono,a.cod_cta_conta_cxc,a.cod_cta_conta_cxp from mst_residencias a where a.cod_poligono=? and a.cod_sub_poligono=?");
            ArrayList<Residencias> al = new ArrayList<>();
            ResultSet rset;
            ps.setInt(1, p.getCod_poligono());
            ps.setString(2, p.getCod_sub_poligono());
            //System.out.println("Modulo seleccionado " + m.getCod_modulo());
            rset = ps.executeQuery();
            while (rset.next()) {
                Residencias res = new Residencias();
                res.setCod_poligono(rset.getInt("cod_poligono"));
                res.setCod_residencia(rset.getString("cod_residencia"));
                res.setNombre_residencia(rset.getString("nombre_residencia"));
                res.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                res.setCod_cta_conta_cxc(rset.getString("cod_cta_conta_cxc"));
                res.setCod_cta_conta_cxp(rset.getString("cod_cta_conta_cxp"));
                al.add(res);
            }
            return al;
        } catch (Exception ex) {
            System.out.println("Error cargando Residencias " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //METODO USADO PARA OBTENER EL NOMBRE DEL MODULO SELECCIONADO POR MEDIO DE COD_MODULO
    public int leer_Pol(Poligonos p) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_poligono from mst_poligonos a where a.cod_poligono=? and a.cod_sub_poligono=?");
            ResultSet rset;
            ps.setInt(1, p.getCod_poligono());
            ps.setString(2, p.getCod_sub_poligono());
            rset = ps.executeQuery();
            while (rset.next()) {
                cod_pol = (rset.getInt("cod_poligono"));
                id_selec = true;
            }
            return cod_pol;
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
            return 0;
        } finally {
            this.Cerrar();
        }
    }
    
    //METODO USADO PARA OBTENER EL NOMBRE DEL MODULO SELECCIONADO POR MEDIO DE COD_MODULO
    public String leer_Subpol(Poligonos p) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_sub_poligono from mst_poligonos a where a.cod_poligono=? and a.cod_sub_poligono=?");
            ResultSet rset;
            ps.setInt(1, p.getCod_poligono());
            ps.setString(2, p.getCod_sub_poligono());
            rset = ps.executeQuery();
            while (rset.next()) {
                cod_sub_pol = (rset.getString("cod_sub_poligono"));
                id_selec = true;
            }
            return cod_sub_pol;
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA INSERTAR UNA RESIDENCIA
    public void add_residencia(Residencias res, int pol,String sub_pol) throws Exception {
       
        //SI HAY UN MODULO SELECCIONADO INSERTO LA APLICACION
        
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_poligono from mst_poligonos a where a.cod_poligono=? and a.cod_sub_poligono=?");
            ResultSet rset;
            ps.setInt(1, pol);
            ps.setString(2, sub_pol);
            rset = ps.executeQuery();
            while (rset.next()) {
                id_selec = true;
            }
        } catch (Exception ex) {
            System.out.println("Error cargando App " + ex.getMessage());
        } finally {
            this.Cerrar();
        }
        
        try {
            this.Conectar();
            //System.out.println("Modulo seleccionado " + id_selec);
            if (id_selec) {
                PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_residencias(cod_residencial,cod_poligono,cod_sub_poligono,cod_residencia,nombre_residencia,cod_usuario,fecha_creacion,estatus_residencia,cod_cta_conta_cxc,cod_cta_conta_cxp) VALUES (1,?,?,?,?,?,now(),?,?,?)");
                ps.setInt(1, pol);
                ps.setString(2, sub_pol);
                ps.setString(3, res.getCod_residencia());
                ps.setString(4, res.getNombre_residencia());
                ps.setString(5, context2.getExternalContext().getSessionMap().get("user_id").toString());
                ps.setString(6, res.getEstatus_residencia());
                ps.setString(7, res.getCod_cta_conta_cxc());
                ps.setString(8, res.getCod_cta_conta_cxp());
                ps.executeUpdate();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debe seleccionar un modulo para agregar aplicaciones", "Debe seleccionar un modulo para agregar aplicaciones"));
            }
        } catch (Exception e) {
            System.out.println("Error insertando residencia: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public ArrayList<Residencias> getResidencias_s(int pol,String sub_pol) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencia,a.nombre_residencia,a.cod_poligono,a.cod_sub_poligono from mst_residencias a where a.cod_poligono=? and a.cod_sub_poligono=?");
            ArrayList<Residencias> al = new ArrayList<>();
            ResultSet rset;
            ps.setInt(1, pol);
            ps.setString(2, sub_pol);
            //System.out.println("Modulo seleccionado " + m.getCod_modulo());
            rset = ps.executeQuery();
            while (rset.next()) {
                Residencias res = new Residencias();
                res.setCod_poligono(rset.getInt("cod_poligono"));
                res.setCod_residencia(rset.getString("cod_residencia"));
                res.setNombre_residencia(rset.getString("nombre_residencia"));
                res.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                res.setCod_cta_conta_cxc(rset.getString("cod_cta_conta_cxc"));
                res.setCod_cta_conta_cxp(rset.getString("cod_cta_conta_cxp"));
                al.add(res);
            }
            return al;
        } catch (Exception ex) {
            System.out.println("Error cargando Residencias " + ex.getMessage());
            return null;
        } finally {
            this.Cerrar();
        }
    }

    //Metodo para actualizar una residencia
    public void update_res(int cod_pol,String cod_subpol,String code_res, String nombre_res,String cuentaxpagar,String cuentaxcobrar) throws Exception {
        Aplicaciones a = null;
        try {
            this.Conectar();
            a = new Aplicaciones();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE mst_residencias SET nombre_residencia = ?,cod_cta_conta_cxp=?,cod_cta_conta_cxc=?,cod_usuario=? WHERE cod_residencia=? and cod_sub_poligono=? and cod_poligono=?");
            ps.setString(1, nombre_res);
            ps.setString(2, cuentaxpagar);
            ps.setString(3, cuentaxcobrar);
            ps.setString(4, context2.getExternalContext().getSessionMap().get("user_id").toString());
            ps.setString(5, code_res);
            ps.setString(6, cod_subpol);
            ps.setInt(7, cod_pol);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Residencia Actualizada", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error Actualizando residencia: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_res(int cod_pol,String cod_subpol,String code_res) throws Exception{
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_residencias WHERE cod_residencia=? and cod_sub_poligono=? and cod_poligono=?");
            ps.setString(1, code_res);
            ps.setString(2, cod_subpol);
            ps.setInt(3, cod_pol);
            ps.executeUpdate();
            PreparedStatement ps2 = this.getCn().prepareStatement("DELETE FROM mst_propietarios WHERE cod_residencia=? and cod_sub_poligono=? and cod_poligono=?");
            ps2.setString(1, code_res);
            ps2.setString(2, cod_subpol);
            ps2.setInt(3, cod_pol);
            ps2.executeUpdate();
            PreparedStatement ps3 = this.getCn().prepareStatement("DELETE FROM mst_propietarios_residentes WHERE cod_residencia=? and cod_sub_poligono=? and cod_poligono=?");
            ps3.setString(1, code_res);
            ps3.setString(2, cod_subpol);
            ps3.setInt(3, cod_pol);
            ps3.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Residencia, Pripietario y Residente eliminados", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error Eliminando Usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    //metodo para obtener los poligonos
    public Map<String, String> getcuentas() throws Exception {
        cuentas = new HashMap<String, String>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_cta_conta, a.desc_cta_contab from tbl_catalogo_ctas a where a.acepta_movs='S' limit 10");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                cuentas.put(rset.getString("cod_cta_conta"), rset.getString("cod_cta_conta"));
            }
        } catch (Exception ex) {
            throw ex;
        }finally{
            this.Cerrar();
        }
        return cuentas;
    }

}
