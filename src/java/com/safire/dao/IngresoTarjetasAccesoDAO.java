package com.safire.dao;

import com.safire.model.IngresoTarjetaAcceso;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class IngresoTarjetasAccesoDAO extends DAO {

    FacesContext local_context = FacesContext.getCurrentInstance();

    public ArrayList<IngresoTarjetaAcceso> getList() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT `cod_residencial`, \n"
                    + "	`cod_proveedor`, \n"
                    + "	(select b.nombre_comercial from mst_proveedores b where b.cod_proveedor = a.cod_proveedor) nom_proveedor, \n"
                    + "	`num_factura`, \n"
                    + "	`fecha_factura`, \n"
                    + "	`num_tarjeta_ini`, \n"
                    + "	`num_factura_fin`, \n"
                    + "	`valor_factura`, \n"
                    + "	`cod_usuario_creacion`,  DATE_FORMAT(fecha_creacion,'%d/%m/%Y') AS fecha_creacion FROM mst_tarjetas_acceso a ORDER BY cast(num_tarjeta_ini as unsigned) DESC");
            ArrayList<IngresoTarjetaAcceso> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                IngresoTarjetaAcceso u = new IngresoTarjetaAcceso();
                u.setCod_proveedor(rset.getInt("cod_proveedor"));
                u.setNom_proveedor(rset.getString("nom_proveedor"));
                u.setCod_residencial(rset.getInt("cod_residencial"));
                u.setCod_usuario_creacion(rset.getString("cod_usuario_creacion"));
                u.setFecha_creacion(rset.getString("fecha_creacion"));
                u.setFecha_factura(rset.getDate("fecha_factura"));
                u.setNum_factura(rset.getString("num_factura"));
                u.setNum_tarjeta_fin(rset.getInt("num_factura_fin"));
                u.setNum_tarjeta_ini(rset.getInt("num_tarjeta_ini"));
                u.setValor_factura(rset.getFloat("valor_factura"));
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
            System.out.println("Error cargando Tarjetas de Acceso: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

    public void add(IngresoTarjetaAcceso u) throws Exception {
        try {
            Date d = new Date(u.getFecha_factura().getYear(), u.getFecha_factura().getMonth(), u.getFecha_factura().getDate());

            this.Conectar();
            //System.out.println("Residencial: " + u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO mst_tarjetas_acceso \n"
                    + "	(cod_residencial, \n"
                    + "	cod_proveedor, \n"
                    + "	num_factura, \n"
                    + "	fecha_factura, \n"
                    + "	num_tarjeta_ini, \n"
                    + "	num_factura_fin, \n"
                    + "	valor_factura, \n"
                    + "	cod_usuario_creacion, \n"
                    + "	fecha_creacion) VALUES (?,?,?,?,?,?,?,?,NOW())");
            ps.setInt(1, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("residencial").toString()));
            ps.setInt(2, u.getCod_proveedor());
            ps.setString(3, u.getNum_factura());
            ps.setDate(4, d);
            ps.setInt(5, u.getNum_tarjeta_ini());
            ps.setInt(6, u.getNum_tarjeta_fin());
            ps.setFloat(7, u.getValor_factura());
            ps.setString(8, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getNum_factura() + "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Tarjetas de Acceso: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void addCard(IngresoTarjetaAcceso u, String tarjeta) throws Exception {
        try {
            Date d = new Date(u.getFecha_factura().getYear(), u.getFecha_factura().getMonth(), u.getFecha_factura().getDate());

            this.Conectar();
            //System.out.println("Residencial: " + u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO safire.mst_tarjetas_activas\n"
                    + "(cod_residencial,\n"
                    + "cod_proveedor,\n"
                    + "num_tarjeta,\n"
                    + "cod_estatus,\n"
                    + "fecha_ult_activacion,\n"
                    + "fecha_creacion,\n"
                    + "cod_usuario_creacion)\n"
                    + "VALUES\n"
                    + "(?,?,?,?,?,NOW(),?)");
            ps.setInt(1, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("residencial").toString()));
            ps.setInt(2, u.getCod_proveedor());
            ps.setString(3, tarjeta);
            ps.setInt(4, 5);
            ps.setDate(5, d);
            ps.setString(6, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando Tarjetas de Acceso (" + tarjeta + "): " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}