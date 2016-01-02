/*Autor: Diego Martí */
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

    public String getResName(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) throws Exception {
        String result = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT CONCAT(nombre_residente, ' ', apellido_residente)\n"
                    + "FROM mst_propietarios_residentes\n"
                    + "WHERE cod_residencial = ?\n"
                    + "AND cod_poligono = ?\n"
                    + "AND cod_sub_poligono = ?\n"
                    + "AND cod_residencia = ?");
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setString(3, cod_sub_poligono);
            ps.setString(4, cod_residencia);
            ResultSet rset;
            rset = ps.executeQuery();
            if (rset.next()) {
                result = rset.getString(1);
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error al consultar el nombre de residente: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
        return result;
    }

    public String verifyStatus(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) throws Exception {
        String result = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT ESTATUS_RESIDENCIA(?,?,?,?);");
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setString(3, cod_sub_poligono);
            ps.setString(4, cod_residencia);
            ResultSet rset;
            rset = ps.executeQuery();
            if (rset.next()) {
                result = rset.getString(1);
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error al consultar el estatus de residente: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
        return result;
    }

    public String verifyCard(int cod_residencial, String num_tarjeta) throws Exception {
        String result = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT cod_estatus FROM mst_tarjetas_activas WHERE cod_residencial = ? AND num_tarjeta = ?;");
            ps.setInt(1, cod_residencial);
            ps.setString(2, num_tarjeta);
            ResultSet rset;
            rset = ps.executeQuery();
            if (rset.next()) {
                result = rset.getString(1);
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error al consultar el estatus de la tarjeta: " + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
        return result;
    }

    public ArrayList<Asignacion_Tarjetas> getList() throws Exception {
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

    public ArrayList<Asignacion_Tarjetas> getDetailList(int cod_residencial, int cod_poligono, String cod_subpoligono, int cod_residencia) throws Exception {
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
                    + "FROM `safire`.`tbl_tarjeta_acceso_res`\n"
                    + "WHERE `tbl_tarjeta_acceso_res`.`cod_residencial` = ?\n"
                    + "AND `tbl_tarjeta_acceso_res`.`cod_poligono` = ?\n"
                    + "AND `tbl_tarjeta_acceso_res`.`cod_sub_poligono` = ?\n"
                    + "AND `tbl_tarjeta_acceso_res`.`cod_residencia` = ?\n"
            );
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setString(3, cod_subpoligono);
            ps.setInt(4, cod_residencia);
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

    public void add(Asignacion_Tarjetas u) throws Exception {
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
            ps.setString(11, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
            ps.setInt(12, u.getCod_estatus());
            ps.setInt(13, u.getCardid());
            ps.executeUpdate();

            ps = this.getCn().prepareStatement("UPDATE mst_tarjetas_activas SET cod_estatus = 0\n"
                    + "WHERE cod_residencial = ? AND num_tarjeta = ?");
            ps.setInt(1, u.getCod_residencial());
            ps.setString(2, u.getNum_tarjeta());
            ps.executeUpdate();

            FacesMessage msg = new FacesMessage("Se ha agregado el registro '" + u.getCod_residencial() + "' al sistema", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Error insertando Asignacion_Tarjetas: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void update(int cod_residencial, int cod_poligono, int cod_sub_poligono, int cod_residencia, String num_tarjeta, int cod_marca, int cod_color, int cod_modelo, String num_placa, String nombre_responsable, String cod_estatus, String cardid) throws Exception {
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
                    + "WHERE `cod_residencial` = ? AND `cod_poligono` = ? AND `cod_sub_poligono` = ? AND `cod_residencia` = ? AND `num_tarjeta` = ?");
            ps.setInt(1, cod_marca);
            ps.setInt(2, cod_marca);
            ps.setInt(3, cod_marca);
            ps.setString(4, num_placa);
            ps.setString(5, nombre_responsable);
            ps.setString(6, cod_estatus);
            ps.setString(7, cardid);
            ps.setInt(8, cod_residencial);
            ps.setInt(9, cod_poligono);
            ps.setInt(10, cod_sub_poligono);
            ps.setInt(11, cod_residencia);
            ps.setString(12, num_tarjeta);
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

    public void delete(int cod_residencial, int cod_poligono, int cod_sub_poligono, int cod_residencia, String num_tarjeta) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("DELETE FROM tbl_tarjeta_acceso_res WHERE `cod_residencial` = ? AND `cod_poligono` = ? AND `cod_sub_poligono` = ? AND `cod_residencia` = ? AND `num_tarjeta` = ?");
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setInt(3, cod_sub_poligono);
            ps.setInt(4, cod_residencia);
            ps.setString(5, num_tarjeta);
            System.out.println("PS: " + ps.toString());
            ps.executeUpdate();
            
            ps = this.getCn().prepareStatement("UPDATE mst_tarjetas_activas SET cod_estatus = 1\n"
                    + "WHERE cod_residencial = ? AND num_tarjeta = ?");
            ps.setInt(1, cod_residencial);
            ps.setString(2, num_tarjeta);
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
