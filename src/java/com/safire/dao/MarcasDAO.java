package com.safire.dao;

/**
 *
 * @author Mauricio Montoya
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.safire.model.Marcas;

public class MarcasDAO extends DAO {
    
    FacesContext local_context = FacesContext.getCurrentInstance();
    private Map<String, String> marcas;
    private int correlativo;
    
    public ArrayList<Marcas> getMarcas() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT cod_marca, nombre_marca, cod_usuario, DATE_FORMAT(fecha_creacion,'%d/%m/%Y') AS fecha_creacion FROM mst_marca_auto ORDER BY nombre_marca asc");
            ArrayList<Marcas> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Marcas u = new Marcas();
                u.setCod_marca(rset.getInt("cod_marca"));
                u.setNombre_marca(rset.getString("nombre_marca"));
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
            System.out.println("Error cargando marcas: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }
    
    public void add_marca(Marcas u) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("SELECT IFNULL(MAX(cod_marca), 0) + 1 correlativo FROM mst_marca_auto");
            ResultSet rset_res;
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                correlativo = (rset_res.getInt("correlativo"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo el correlativo de Marcas: " + ex.getMessage());
        } finally{
            this.Cerrar();
        }
        
        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_marca_auto (cod_marca, nombre_marca, cod_usuario, fecha_creacion) VALUES (?,?,?,NOW())");
            ps.setInt(1, correlativo);
            ps.setString(2, u.getNombre_marca());
            ps.setInt(3, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getNombre_marca() + "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Marca: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void update_marca(int cod_marca,String nombre_marca) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("update mst_marca_auto a SET a.nombre_marca=? where a.cod_marca=?");
            ps.setInt(2, cod_marca);
            ps.setString(1, nombre_marca);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void del_marcas(int cod_marca) throws Exception{
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_marca_auto WHERE cod_marca=?");
            ps.setInt(1, cod_marca);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error Eliminando Usuario: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
