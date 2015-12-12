package com.safire.dao;

import com.safire.model.Asignacion_Tarjetas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class AsignarTarjetaDAO extends DAO {
    FacesContext local_context = FacesContext.getCurrentInstance();
    private Map<String, String> colores;
    private int correlativo;
    
    public ArrayList<Asignacion_Tarjetas> getAsignaciones() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT `tbl_tarjeta_acceso_res`.`cod_residencial`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_poligono`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_sub_poligono`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_residencia`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`num_tarjeta`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_marca`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_color`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_modelo`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`num_placa`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`nombre_responsable`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_usuario`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`fecha_creacion`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cod_estatus`,\n"
                    + "    `tbl_tarjeta_acceso_res`.`cardid`\n"
                    + "FROM `safire`.`tbl_tarjeta_acceso_res`");
            ArrayList<Asignacion_Tarjetas> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                Asignacion_Tarjetas u = new Asignacion_Tarjetas();
                u.setCardid(rset.getInt("cardid"));
                u.setCod_color(rset.getInt("cod_color"));
                u.setCod_estatus(rset.getInt("cod_estatus"));
                u.setCod_usuario(rset.getString("cod_usuario"));
                u.setCod_marca(rset.getInt("cod_marca"));
                u.setCod_modelo(rset.getInt("cod_modelo"));
                u.setCod_poligono(rset.getInt("cod_poligono"));
                u.setCod_residencia(rset.getString("cod_residencia"));
                u.setCod_residencial(rset.getInt("cod_residencial"));
                u.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                u.setFecha_creacion(rset.getString("fecha_creacion"));
                u.setNombre_responsable(rset.getString("nombre_responsable"));
                u.setNum_placa(rset.getString("num_placa"));
                u.setNum_tarjeta(rset.getString("num_tarjeta"));
               
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
            System.out.println("Error cargando Asignacion_Tarjetas: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }
    
    public void add_color(Asignacion_Tarjetas u) throws Exception {
        try {
            this.Conectar();
            //System.out.println("Residencial: "+u.getCod_residencial());
            PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO `safire`.`tbl_tarjeta_acceso_res`\n"
                    + "(`cod_residencial`,\n"
                    + "`cod_poligono`,\n"
                    + "`cod_sub_poligono`,\n"
                    + "`cod_residencia`,\n"
                    + "`num_tarjeta`,\n"
                    + "`cod_marca`,\n"
                    + "`cod_color`,\n"
                    + "`cod_modelo`,\n"
                    + "`num_placa`,\n"
                    + "`nombre_responsable`,\n"
                    + "`cod_usuario`,\n"
                    + "`fecha_creacion`,\n"
                    + "`cod_estatus`,\n"
                    + "`cardid`)\n"
                    + "VALUES\n"
                    + "(?,?,?,?,?,?,?,?,?,?,?,now(),?,?);");
            ps.setInt(1, u.getCod_residencial());
            ps.setInt(2, u.getCod_poligono());
            ps.setString(3, u.getCod_sub_poligono());
            ps.setString(4, u.getCod_residencia());
            ps.setString(5, u.getNum_tarjeta());
            ps.setInt(6, u.getCod_marca());
            ps.setInt(7, u.getCod_color());
            ps.setInt(8, u.getCod_modelo());
            ps.setString(9, u.getNum_placa());
            ps.setString(10, u.getNombre_responsable());
            ps.setString(11, u.getCod_usuario());
            ps.setString(12, u.getFecha_creacion());
            ps.setInt(13, u.getCod_estatus());
            ps.setInt(14, u.getCardid());
            ps.setInt(15, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("user_id").toString()));
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getCod_residencial()+ "' al sistema", "");
            FacesMessage msg1 = new FacesMessage("Se ha agregado el registro '" + u.getCod_poligono()+ "' al sistema", "");
            FacesMessage msg2 = new FacesMessage("Se ha agregado el registro '" + u.getCod_sub_poligono()+ "' al sistema", "");
            FacesMessage msg3 = new FacesMessage("Se ha agregado el registro '" + u.getCod_residencia()+ "' al sistema", "");
            FacesMessage msg4 = new FacesMessage("Se ha agregado el registro '" + u.getNum_tarjeta()+ "' al sistema", "");
            FacesMessage msg5 = new FacesMessage("Se ha agregado el registro '" + u.getCod_marca()+ "' al sistema", "");
            FacesMessage msg6 = new FacesMessage("Se ha agregado el registro '" + u.getCod_color()+ "' al sistema", "");
            FacesMessage msg7 = new FacesMessage("Se ha agregado el registro '" + u.getCod_modelo()+ "' al sistema", "");
            FacesMessage msg8 = new FacesMessage("Se ha agregado el registro '" + u.getNum_placa()+ "' al sistema", "");
            FacesMessage msg9 = new FacesMessage("Se ha agregado el registro '" + u.getNombre_responsable()+ "' al sistema", "");
            FacesMessage msg10 = new FacesMessage("Se ha agregado el registro '" + u.getCod_usuario()+ "' al sistema", "");
            FacesMessage msg11 = new FacesMessage("Se ha agregado el registro '" + u.getFecha_creacion()+ "' al sistema", "");
            FacesMessage msg12 = new FacesMessage("Se ha agregado el registro '" + u.getCod_estatus()+ "' al sistema", "");
            FacesMessage msg13 = new FacesMessage("Se ha agregado el registro '" + u.getCardid()+ "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Asignacion_Tarjetas: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    
    public void update_color(int cod_color, String nombre_color) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE `safire`.`tbl_tarjeta_acceso_res`\n"
                    + "SET\n"
                    + "`cod_marca` = ?,\n"
                    + "`cod_color` = ?,\n"
                    + "`cod_modelo` = ?,\n"
                    + "`num_placa` = ?,\n"
                    + "`nombre_responsable` = ?,\n"
                    + "`cod_estatus` = ?,\n"
                    + "`cardid` = ?\n"
                    + "WHERE `cod_residencial` = ? AND `cod_poligono` = ? AND `cod_sub_poligono` = ? AND `cod_residencia` = ? AND `num_tarjeta` = ? ;");
            ps.setInt(2, cod_color);
            ps.setString(1, nombre_color);
            ps.executeUpdate();
            FacesMessage msg = new FacesMessage("Registro actualizado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error actualizando Asignacion_Tarjetas: " + e.getMessage());
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
            System.out.println("Error Eliminando Asignacion_Tarjetas: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
}
