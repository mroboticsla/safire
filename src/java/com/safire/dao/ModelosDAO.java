package com.safire.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.safire.model.Modelos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio Montoya
 */
public class ModelosDAO extends DAO {
    FacesContext local_context = FacesContext.getCurrentInstance();
    private Map<String, String> modelos;
    private int correlativo;
    
    public ArrayList<Modelos> getModelos(int cod_marca) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT cod_marca, cod_modelo, nombre_modelo, cod_usuario, DATE_FORMAT(fecha_creacion,'%d/%m/%Y') AS fecha_creacion FROM mst_modelo_auto where cod_marca = ? ORDER BY nombre_modelo asc");
            ArrayList<Modelos> al = new ArrayList<>();
            ResultSet rset;
            ps.setInt(1, cod_marca);
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Modelos u = new Modelos();
                u.setCod_marca(rset.getInt("cod_marca"));
                u.setCod_modelo(rset.getInt("cod_modelo"));
                u.setNombre_modelo(rset.getString("nombre_modelo"));
                u.setCod_usuario(rset.getString("cod_usuario"));
                u.setFecha_creacion(rset.getString("fecha_creacion"));
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
            System.out.println("Error cargando modelos: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }
    
    public String obtener_marca(int cod_marca){
        String result = "Ninguna marca seleccionada";
        
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("SELECT nombre_marca FROM mst_marca_auto where cod_marca = ?");
            ResultSet rset_res;
            ps_m.setInt(1, cod_marca);
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                result = rset_res.getString("nombre_marca");
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo el correlativo de Modelos: " + ex.getMessage());
        } finally{
            try {
                this.Cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ModelosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public void add_modelo(int cod_marca, Modelos u) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("SELECT IFNULL(MAX(cod_modelo), 0) + 1 correlativo FROM mst_modelo_auto");
            ResultSet rset_res;
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                correlativo = (rset_res.getInt("correlativo"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo el correlativo de Modelos: " + ex.getMessage());
        } finally{
            this.Cerrar();
        }
        
        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_modelo_auto (cod_marca, cod_modelo, nombre_modelo, cod_usuario, fecha_creacion) VALUES (?,?,?,?,NOW())");
            ps.setInt(1, cod_marca);
            ps.setInt(2, correlativo);
            ps.setString(3, u.getNombre_modelo());
            ps.setInt(4, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getNombre_modelo() + "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Modelo: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void update_modelo(int cod_modelo, String nombre_modelo) throws Exception {
        
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("update mst_modelo_auto a SET a.nombre_modelo=? where a.cod_modelo=?");
            ps.setInt(2, cod_modelo);
            ps.setString(1, nombre_modelo);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error actualizando Modelo: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_modelos(int cod_modelo) throws Exception{
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_modelo_auto WHERE cod_modelo=?");
            ps.setInt(1, cod_modelo);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error Eliminando Modelo: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
