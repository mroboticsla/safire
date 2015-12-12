/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Modulos;
import com.safire.model.Usuarios;
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
public class UsuariosDAO extends DAO {
    
    FacesContext context2 = FacesContext.getCurrentInstance();
    //Arreglo para obtener Listado de roles
    private Map<String, String> roles;
    private Map<String, String> residenciales;
    private Map<String, String> activo;
    private int rol_id;
    private int residencial;

    public void add_user(Usuarios u) throws Exception {
        String us_activo = u.getActivo();
        String aut_correccion = u.getAutoriza_correccion();
        if(us_activo.equals("false")){
            us_activo="N";
        }else{
            us_activo="S";
        }
        if(aut_correccion.equals("false")){
            aut_correccion="N";
        }else{
            aut_correccion="S";
        }
        
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select a.COD_ROL from mst_roles a where a.DESC_ROL=?");
            ResultSet rset_m;
            ps_m.setString(1, u.getRol());
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                rol_id = (rset_m.getInt("COD_ROL"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de rol " + ex.getMessage());
        }
        
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select a.cod_residencial from mst_residencial a where a.nombre_residencial=?");
            ResultSet rset_res;
            ps_m.setString(1, u.getResidencial());
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                residencial = (rset_res.getInt("cod_residencial"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de residencial " + ex.getMessage());
        }
        
        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_usuarios(corr_usuario,cod_usuario,nom_usuario,apel_usuario,cod_rol,fecha_creacion,contraseña,activo,cod_usuario_creacion,autoriza_correccion,cod_residencial) VALUES (0,?,?,?,?,now(),?,?,?,?,?)");
            ps.setString(1, u.getCod_usuario());
            ps.setString(2, u.getNom_usuario());
            ps.setString(3, u.getNom_usuario());
            ps.setInt(4, rol_id);
            ps.setString(5, u.getContraseña());
            ps.setString(6, us_activo);
            ps.setInt(7, Integer.parseInt(context2.getExternalContext().getSessionMap().get("user_id").toString()));
            //System.out.println("No. us. "+u.getCod_usuario()+" Usuario rol: " + u.getRol()+" Nombre: "+u.getNom_usuario()+"Activo "+activo);
            ps.setString(8, aut_correccion);
            ps.setInt(9, residencial);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando Usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA LISTAR LOS USUARIOS DE LA APLICACION
    public ArrayList<Usuarios> getUsers() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT  corr_usuario,  a.cod_usuario,a.cod_residencial, nom_usuario, apel_usuario,  b.COD_ROL, b.DESC_ROL,  DATE_FORMAT(a.fecha_creacion,'%d/%m/%Y') AS fecha_creacion, contraseña,  `activo`,  `autoriza_correccion`,  `cod_usuario_creacion`,  `cod_usuario_modifica`,  `fecha_modifica` FROM mst_usuarios a ,mst_roles b WHERE a.cod_rol=b.COD_ROL ORDER BY `corr_usuario` asc");
            ArrayList<Usuarios> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Usuarios u = new Usuarios();
                u.setCorr_usuario(rset.getInt("corr_usuario"));
                u.setCod_usuario(rset.getString("cod_usuario"));
                u.setNom_usuario(rset.getString("nom_usuario"));
                u.setContraseña(rset.getString("contraseña"));
                u.setFecha_creacion(rset.getString("fecha_creacion"));
                u.setRol(rset.getString("DESC_ROL"));
                u.setCod_rol(rset.getInt("COD_ROL"));
                u.setActivo(rset.getString("activo"));
                u.setCod_residencial(rset.getInt("cod_residencial"));
                al.add(u);
                found = true;
            }
            rset.close();
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
    
    //metodo para obtener los roles
    public Map<String, String> getRoles() throws Exception {
        roles = new HashMap<String, String>();
        try{
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.COD_ROL,a.DESC_ROL from mst_roles a order by a.COD_ROL asc");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                roles.put(rset.getString("COD_ROL") ,rset.getString("DESC_ROL"));
            }
        }catch(Exception ex){
            throw ex;
        }
        return roles;
    }
    
    //metodo para obtener las residenciales
    public Map<String, String> getResideciales() throws Exception {
        residenciales = new HashMap<String, String>();
        try{
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencial,a.nombre_residencial from mst_residencial a order by a.cod_residencial asc");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                residenciales.put(rset.getString("cod_residencial") ,rset.getString("nombre_residencial"));
            }
        }catch(Exception ex){
            throw ex;
        }
        return residenciales;
    }
    
    
    //metodo llenar combobox si esta activo o no
    public Map<String, String> getActivo() {
        activo = new HashMap<String, String>();
        activo.put("S", "S");
        activo.put("N", "N");
        return activo;
    }
    
    
    //Metodo para actualizar un usuario
    public void update_user(int corr_usuario,String cod_usuario,String nom_usuario,String contraseña, String cod_rol,String activo) throws Exception {
        
        Usuarios u = null;
        
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("select a.COD_ROL from mst_roles a where a.DESC_ROL=?");
            ResultSet rset_m;
            ps_m.setString(1, cod_rol);
            rset_m = ps_m.executeQuery();
            while (rset_m.next()) {
                rol_id = (rset_m.getInt("COD_ROL"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de rol " + ex.getMessage());
        }
        
        try {
            this.Conectar();
            u = new Usuarios();
            PreparedStatement ps = this.getCn().prepareStatement("update mst_usuarios a SET a.cod_usuario=?,a.nom_usuario=?,a.`contraseña`=?,a.cod_rol=?,a.activo=? where a.corr_usuario=?");
            ps.setString(1, cod_usuario);
            ps.setString(2, nom_usuario);
            ps.setString(3, contraseña);
            ps.setInt(4, rol_id);
            ps.setString(5, activo);
            ps.setInt(6, corr_usuario);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Usuario actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_user(int corr_usuario) throws Exception{
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_usuarios WHERE corr_usuario=?");
            ps.setInt(1, corr_usuario);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Usuario eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error Eliminando Usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
