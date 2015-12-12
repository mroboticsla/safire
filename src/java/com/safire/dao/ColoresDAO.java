package com.safire.dao;

import com.safire.model.Colores;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ColoresDAO extends DAO {
    FacesContext local_context = FacesContext.getCurrentInstance();
    private Map<String, String> colores;
    private int correlativo;
    
    public ArrayList<Colores> getColores() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT cod_color, nombre_color, cod_usuario, DATE_FORMAT(fecha_creacion,'%d/%m/%Y') AS fecha_creacion FROM mst_colores ORDER BY nombre_color asc");
            ArrayList<Colores> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Colores u = new Colores();
                u.setCod_color(rset.getInt("cod_color"));
                u.setNombre_color(rset.getString("nombre_color"));
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
            System.out.println("Error cargando Colores: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }
    
    public void add_color(Colores u) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("SELECT IFNULL(MAX(cod_color), 0) + 1 correlativo FROM mst_colores");
            ResultSet rset_res;
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                correlativo = (rset_res.getInt("correlativo"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo el correlativo de Colores: " + ex.getMessage());
        } finally{
            this.Cerrar();
        }
        
        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_colores (cod_color, nombre_color, cod_usuario, fecha_creacion) VALUES (?,?,?,NOW())");
            ps.setInt(1, correlativo);
            ps.setString(2, u.getNombre_color());
            ps.setInt(3, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getNombre_color()+ "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Colores: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void update_color(int cod_color,String nombre_color) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("update mst_colores a SET a.nombre_color=? where a.cod_color=?");
            ps.setInt(2, cod_color);
            ps.setString(1, nombre_color);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error actualizando Colores: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_color(int cod_color) throws Exception{
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_colores WHERE cod_color=?");
            ps.setInt(1, cod_color);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error Eliminando Colores: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
