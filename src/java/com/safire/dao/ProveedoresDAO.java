package com.safire.dao;

import com.safire.model.Proveedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ProveedoresDAO extends DAO {

    FacesContext local_context = FacesContext.getCurrentInstance();
    private int correlativo;

    public ArrayList<Proveedor> getData() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT cod_residencial,\n"
                    + "    cod_proveedor,\n"
                    + "    nombre_comercial,\n"
                    + "    contacto,\n"
                    + "    telefono,\n"
                    + "    nrc,\n"
                    + "    fecha_creacion,\n"
                    + "    cod_usuario\n"
                    + "FROM safire.mst_proveedores");
            ArrayList<Proveedor> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Proveedor u = new Proveedor();
                u.setCod_residencial(rset.getInt("cod_residencial"));
                u.setCod_proveedor(rset.getInt("cod_proveedor"));
                u.setNombre_comercial(rset.getString("nombre_comercial"));
                u.setContacto(rset.getString("contacto"));
                u.setTelefono(rset.getString("telefono"));
                u.setNrc(rset.getString("nrc"));
                u.setFecha_creacion(rset.getString("fecha_creacion"));
                u.setCod_usuario(rset.getString("cod_usuario"));
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
            System.out.println("Error cargando Proveedor: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

    public void add(Proveedor u) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps_m = this.getCn().prepareStatement("SELECT IFNULL(MAX(cod_proveedor), 0) + 1 correlativo FROM mst_proveedores");
            ResultSet rset_res;
            rset_res = ps_m.executeQuery();
            while (rset_res.next()) {
                correlativo = (rset_res.getInt("correlativo"));
            }
        } catch (Exception ex) {
            System.out.println("Error leyendo el correlativo de Proveedor: " + ex.getMessage());
        } finally {
            this.Cerrar();
        }

        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_proveedores (cod_residencial,\n"
                    + "cod_proveedor,\n"
                    + "nombre_comercial,\n"
                    + "contacto,\n"
                    + "telefono,\n"
                    + "nrc,\n"
                    + "fecha_creacion,\n"
                    + "cod_usuario) VALUES (?,?,?,?,?,?,NOW(),?)");
            ps.setInt(2, correlativo);
            ps.setInt(1, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("residencial").toString()));
            ps.setString(3, u.getNombre_comercial());
            ps.setString(4, u.getContacto());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getNrc());
            ps.setString(7, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getNombre_comercial() + "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Proveedor: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void update(int cod_proveedor, String nombre_comercial, String contacto, String telefono, String nrc) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE mst_proveedores\n"
                    + "SET\n"
                    + "nombre_comercial = ?,\n"
                    + "contacto = ?,\n"
                    + "telefono = ?,\n"
                    + "nrc = ?\n"
                    + "WHERE cod_proveedor = ?");
            ps.setString(1, nombre_comercial);
            ps.setString(2, contacto);
            ps.setString(3, telefono);
            ps.setString(4, nrc);
            ps.setInt(5, cod_proveedor);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error actualizando Proveedor: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void delete(int cod_proveedor) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM mst_proveedores WHERE cod_proveedor=?");
            ps.setInt(1, cod_proveedor);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error Eliminando Proveedor: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}