/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Propietarios;
import com.safire.model.Residentes;
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
public class ResidentesDAO extends DAO {

    FacesContext context2 = FacesContext.getCurrentInstance();
    private boolean found=false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int corr_residente = 0;

    //METODO PARA OBTENER RESIDENTE POR MEDIO DE POLIGONO, SUBPOLIGONO Y RESIDENCIA
    public Residentes getResidente(int cod_residencial, int cod_poligono, String cod_sub_poligono, String cod_residencia) throws Exception {
        Residentes p = null;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencial,a.cod_poligono,a.cod_sub_poligono,a.cod_residencia,a.corr_residente,a.nombre_residente,a.apellido_residente,a.num_dui,a.telefono,a.celular,a.fecha_ini_residencia from mst_propietarios_residentes a where a.cod_residencial=? and a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
            ResultSet rset;
            ps.setInt(1, cod_residencial);
            ps.setInt(2, cod_poligono);
            ps.setString(3, cod_sub_poligono);
            ps.setString(4, cod_residencia);
            rset = ps.executeQuery();
            while (rset.next()) {
                p = new Residentes();
                p.setCod_residencial(rset.getInt("cod_residencial"));
                p.setCod_poligono(rset.getInt("cod_poligono"));
                p.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                p.setCorr_residente(rset.getInt("corr_residente"));
                p.setNombre_residente(rset.getString("nombre_residente"));
                p.setApellido_residente(rset.getString("apellido_residente"));
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
            System.out.println("Error cargando residente" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

    //METODO PARA MODIFICAR UN RESIDENTE O AGREGARLO SI NO EXISTE
    public void upd_residente(Residentes p,int id_pol,String sub_pol,String cod_res) throws Exception {

        //VERICANDO SI EXISTE EL RESIDENTE
        try {
            this.Conectar();
            PreparedStatement ps2 = this.getCn().prepareStatement("select a.cod_residencial,a.cod_poligono,a.cod_sub_poligono,a.cod_residencia,a.corr_residente,a.nombre_residente,a.apellido_residente,a.num_dui,a.telefono,a.celular from mst_propietarios_residentes a where a.cod_residencial=? and a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
            ResultSet rset_m;
            ps2.setInt(1, p.getCod_residencial());
            ps2.setInt(2, p.getCod_poligono());
            ps2.setString(3, p.getCod_sub_poligono());
            ps2.setString(4, p.getCod_residencia());
            rset_m = ps2.executeQuery();
            while (rset_m.next()) {
                corr_residente = (rset_m.getInt("corr_residente"));
            }
            rset_m.close();
        } catch (Exception ex) {
            System.out.println("Error leyendo ID de propietario " + ex.getMessage());
        } finally {
            this.Cerrar();
        }

        //SI EXISTE LO MODIFICO SI NO LO INSERTO
        if (corr_residente != 0) {
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("update mst_propietarios_residentes a SET a.nombre_residente=?, a.apellido_residente=?, a.num_dui=?, a.telefono=?,a.celular=? where a.corr_residente=?");
                ps.setString(1, p.getNombre_residente());
                ps.setString(2, p.getApellido_residente());
                ps.setString(3, p.getNum_dui());
                ps.setString(4, p.getTelefono());
                ps.setString(5, p.getCelular());
                ps.setInt(6, corr_residente);
                ps.executeUpdate();
                FacesMessage msg2 = new FacesMessage("Residente Modificado", "");
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
                PreparedStatement ps = this.getCn().prepareStatement("insert into mst_propietarios_residentes(cod_residencial,cod_poligono,cod_sub_poligono,cod_residencia,corr_residente,fecha_ini_residencia,nombre_residente,apellido_residente,telefono,celular,cod_usuario_creacion,fecha_creacion,num_dui) values(1,?,?,?,0,?,?,?,?,?,?,now(),?)");
                ps.setInt(1, id_pol);
                ps.setString(2, sub_pol);
                ps.setString(3, cod_res);
                ps.setString(4, dateFormat.format(p.getFecha_ini_residencia()));
                ps.setString(5, p.getNombre_residente());
                ps.setString(6, p.getApellido_residente());
                ps.setString(7, p.getTelefono());
                ps.setString(8, p.getCelular());
                ps.setString(9, "1");
                ps.setString(10, p.getNum_dui());
                ps.executeUpdate();
                FacesMessage msg2 = new FacesMessage("Residente Agregado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
            } catch (Exception e) {
                System.out.println("Error insertando Residente: " + e.getMessage());
                throw e;
            } finally {
                this.Cerrar();
            }
        }
    }

}
