/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.dao;

import com.safire.model.Modulos;
import com.safire.model.Usuarios;
import com.safire.model.recibos_prov_generados;
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
public class recibos_prov_generadosDAO extends DAO {

    FacesContext context2 = FacesContext.getCurrentInstance();

    //METODO DATOS DE ULTIMA GENERACION DE RECIBOS
    public ArrayList<recibos_prov_generados> getRecibos_gen() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencial,a.corr_generados,DATE_FORMAT(a.fecha_generados,'%d/%m/%Y') as fecha_generados,a.recibo_ini_prov,a.recibo_fin_prov,a.cod_usuario from tbl_recibos_prov_generados a where a.corr_generados in (select max(a.corr_generados) from tbl_recibos_prov_generados a)");
            ArrayList<recibos_prov_generados> al = new ArrayList<>();
            ResultSet rset;
            rset = ps.executeQuery();
            boolean found = false;
            while (rset.next()) {
                recibos_prov_generados u = new recibos_prov_generados();
                u.setCod_residencial(rset.getInt("cod_residencial"));
                u.setCod_usuario(rset.getString("cod_usuario"));
                u.setCorr_generados(rset.getInt("corr_generados"));
                u.setRecibo_fin_prov(rset.getInt("recibo_fin_prov"));
                u.setRecibo_ini_prov(rset.getInt("recibo_ini_prov"));
                u.setFecha_generados(rset.getString("fecha_generados"));
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
            System.out.println("Error cargando recibos provicionales" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
    }

    public void add_recibos(recibos_prov_generados r) throws Exception {

        int c = 0;
        String insert_recibos = "";
        int cod_residencial = 0;
        int corr_generados = 0;

        try {
            this.Conectar();
            PreparedStatement ps2 = this.getCn().prepareStatement("select a.cod_residencial,a.corr_generados,DATE_FORMAT(a.fecha_generados,'%d/%m/%Y') as fecha_generados,a.recibo_ini_prov,a.recibo_fin_prov,a.cod_usuario from tbl_recibos_prov_generados a where a.corr_generados in (select max(a.corr_generados) from tbl_recibos_prov_generados a)");
            ResultSet rset;
            rset = ps2.executeQuery();
            while (rset.next()) {
                cod_residencial = 1;//rset.getInt("cod_residencial");
                corr_generados = rset.getInt("corr_generados");
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error cargando recibos provicionales 2" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }

        //System.out.println("Correlativo " + corr_generados);
        //System.out.println("Cantidad " + r.getCantidad_recibos());
        String insert = "INSERT INTO tbl_recibos_prov_generados (cod_residencial, corr_generados, fecha_generados,recibo_ini_prov,recibo_fin_prov,cod_usuario) VALUES ";

        try {
            this.Conectar();
            for (c = 1; c <= r.getCantidad_recibos(); c++) {
                if (c != r.getCantidad_recibos()) {
                    insert_recibos = insert_recibos + "(1," + (corr_generados+c) + ",now()," + corr_generados + "," + (r.getCantidad_recibos()+corr_generados) + ",'" + context2.getExternalContext().getSessionMap().get("user_cod").toString() + "'),";
                } else {
                    insert_recibos = insert_recibos + "(1," + (corr_generados+c) + ",now()," + corr_generados + "," + (r.getCantidad_recibos()+corr_generados) + ",'" + context2.getExternalContext().getSessionMap().get("user_cod").toString() + "');";
                }
            }
            //System.out.println("Query insert " + insert + insert_recibos);
            PreparedStatement ps = this.getCn().prepareStatement(insert + insert_recibos);
            ps.executeUpdate();
            FacesMessage msg2 = new FacesMessage("Se han generado los recibos provicionales del " + (corr_generados+1) + " al " + (r.getCantidad_recibos()+corr_generados), "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } catch (Exception e) {
            System.out.println("Error insertando Recibos: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

}
