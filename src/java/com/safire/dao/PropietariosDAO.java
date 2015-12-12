/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Propietarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author desarrollo01
 */
public class PropietariosDAO extends DAO {

    FacesContext context2 = FacesContext.getCurrentInstance();
    private boolean found=false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int corr_propietario = 0;

    //METODO PARA OBTENER PROPIETARIO POR MEDIO DE POLIGONO, SUBPOLIGONO Y RESIDENCIA
    public Propietarios getPropietario(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) throws Exception {
        Propietarios p = null;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencial,a.cod_poligono,a.cod_sub_poligono,a.cod_residencia,a.corr_propietario,a.nombre_propietario,a.apellido_propietario,a.num_dui,a.telefono,a.celular,a.fecha_ini_residencia from mst_propietarios a where a.cod_residencial=? and a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
            ResultSet rset;
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setString(3, cod_sub_poligono);
            ps.setString(4, cod_residencia);
            rset = ps.executeQuery();
            while (rset.next()) {
                p = new Propietarios();
                p.setCod_residencial(rset.getInt("cod_residencial"));
                p.setCod_poligono(rset.getInt("cod_poligono"));
                p.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                p.setCorr_propietario(rset.getInt("corr_propietario"));
                p.setNombre_propietario(rset.getString("nombre_propietario"));
                p.setApellido_propietario(rset.getString("apellido_propietario"));
                p.setNum_dui(rset.getString("num_dui"));
                p.setTelefono(rset.getString("telefono"));
                p.setCelular(rset.getString("celular"));
                p.setCod_residencia(rset.getString("cod_residencia"));
                p.setFecha_ini_residencia(rset.getDate("fecha_ini_residencia"));
                found=true;
            }
            rset.close();
            if(found){
                return p;
            }else{
                return null;
            }

        } catch (Exception ex) {
            System.out.println("Error cargando propietario" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA MODIFICAR UN PROPIETARIO O AGREGARLO SI NO EXISTE
    public void upd_propietario(Propietarios p,int id_pol,String sub_pol,String cod_res) throws Exception {

        //VERICANDO SI EXISTE EL PROPIETARIO
        try {
            this.Conectar();
            PreparedStatement ps2 = this.getCn().prepareStatement("select a.cod_residencial,a.cod_poligono,a.cod_sub_poligono,a.cod_residencia,a.corr_propietario,a.nombre_propietario,a.apellido_propietario,a.num_dui,a.telefono,a.celular from mst_propietarios a where a.cod_residencial=? and a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
            ResultSet rset_m;
            ps2.setInt(1, p.getCod_residencial());
            ps2.setInt(2, p.getCod_poligono());
            ps2.setString(3, p.getCod_sub_poligono());
            ps2.setString(4, p.getCod_residencia());
            rset_m = ps2.executeQuery();
            while (rset_m.next()) {
                corr_propietario = (rset_m.getInt("corr_propietario"));
            }
            rset_m.close();
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de propietario " + ex.getMessage());
        } finally {
            this.Cerrar();
        }

        //SI EXISTE LO MODIFICO SI NO LO INSERTO
        if (corr_propietario != 0) {
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("update mst_propietarios a SET a.nombre_propietario=?, a.apellido_propietario=?, a.num_dui=?, a.telefono=?,a.celular=? where a.corr_propietario=?");
                ps.setString(1, p.getNombre_propietario());
                ps.setString(2, p.getApellido_propietario());
                ps.setString(3, p.getNum_dui());
                ps.setString(4, p.getTelefono());
                ps.setString(5, p.getCelular());
                ps.setInt(6, corr_propietario);
                ps.executeUpdate();
                FacesMessage msg2 = new FacesMessage("Propietario Modificado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
            } catch (Exception e) {
                System.out.println("Error actualizando Propietario: " + e.getMessage());
                throw e;
            } finally {
                this.Cerrar();
            }
        }else{
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("insert into mst_propietarios(cod_residencial,cod_poligono,cod_sub_poligono,cod_residencia,corr_propietario,fecha_ini_residencia,nombre_propietario,apellido_propietario,telefono,celular,cod_usuario_creacion,fecha_creacion,num_dui) values(1,?,?,?,0,?,?,?,?,?,?,now(),?)");
                ps.setInt(1, id_pol);
                ps.setString(2, sub_pol);
                ps.setString(3, cod_res);
                ps.setString(4, dateFormat.format(p.getFecha_ini_residencia()));
                ps.setString(5, p.getNombre_propietario());
                ps.setString(6, p.getApellido_propietario());
                ps.setString(7, p.getTelefono());
                ps.setString(8, p.getCelular());
                ps.setString(9, "1");
                ps.setString(10, p.getNum_dui());
                ps.executeUpdate();
                FacesMessage msg2 = new FacesMessage("Propietario Agregado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
            } catch (Exception e) {
                System.out.println("Error insertando Propietario: " + e.getMessage());
                throw e;
            } finally {
                this.Cerrar();
            }
        }
    }

}
