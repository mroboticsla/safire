/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.safire.dao.AplicacionesDAO;
import com.safire.dao.ResidenciasDAO;
import com.safire.model.Aplicaciones;
import com.safire.model.Modulos;
import com.safire.model.Poligonos;
import com.safire.model.Residencias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "res")
@SessionScoped
public class ResidenciasBean implements Serializable {

    public ArrayList<Residencias> list_Res;
    public Residencias residencia = new Residencias();
    public String d_pol = "Ningun Poligono Seleccionado";
    public int id_pol = 0;
    public String sub_pol = "";

    public ArrayList<Residencias> getList_Res() {
        return list_Res;
    }

    public void setList_Res(ArrayList<Residencias> list_Res) {
        this.list_Res = list_Res;
    }

    public Residencias getResidencia() {
        return residencia;
    }

    public void setResidencia(Residencias residencia) {
        this.residencia = residencia;
    }

    public String getD_pol() {
        return d_pol;
    }

    public void setD_pol(String d_pol) {
        this.d_pol = d_pol;
    }

    public int getId_pol() {
        return id_pol;
    }

    public void setId_pol(int id_pol) {
        this.id_pol = id_pol;
    }

    public String getSub_pol() {
        return sub_pol;
    }

    public void setSub_pol(String sub_pol) {
        this.sub_pol = sub_pol;
    }
    
    
    //Metodo para obtener las aplicaciones
    public void getResidencias(Poligonos p) {
        try {
            ResidenciasDAO dao;
            dao = new ResidenciasDAO();
            if (dao.getResidencias(p) != null) {
                list_Res = dao.getResidencias(p);
                leer_Poligono(p);
            }
            if (list_Res.isEmpty()) {
                list_Res = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ResidenciasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leer_Poligono(Poligonos p) {
        try {
            ResidenciasDAO dao;
            dao = new ResidenciasDAO();
            if (!"".equals(dao.leer_Pol(p))) {
                id_pol = dao.leer_Pol(p);
                sub_pol = dao.leer_Subpol(p);
                d_pol = "Poligono: "+id_pol+sub_pol;
            } else {
                d_pol = "Ningun Poligono seleccionado";
            }
        } catch (Exception ex) {
            System.out.println("Pol select: " + p.getCod_poligono());
            Logger.getLogger(ResidenciasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add_residencia(int pol,String sub_pol) throws Exception {
        ResidenciasDAO dao2;
        try {
            dao2 = new ResidenciasDAO();
            dao2.add_residencia(residencia, pol, sub_pol);
            this.residencia.setCod_residencia("");
            this.residencia.setNombre_residencia("");
            this.residencia.setEstatus_residencia("");
            list_Res = dao2.getResidencias_s(pol, sub_pol);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Map<String, String> getCuentas() throws Exception {
        try {
            ResidenciasDAO dao;
            dao = new ResidenciasDAO();
            return dao.getcuentas();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void update_res(RowEditEvent event) throws Exception {
        ResidenciasDAO dao3;
        try {
            dao3 = new ResidenciasDAO();
            //dao3.leer_app(((Aplicaciones) event.getObject()).getCod_aplicacion());
            dao3.update_res(((Residencias) event.getObject()).getCod_poligono(),((Residencias) event.getObject()).getCod_sub_poligono(),((Residencias) event.getObject()).getCod_residencia(),((Residencias) event.getObject()).getNombre_residencia(),((Residencias) event.getObject()).getCod_cta_conta_cxp(),((Residencias) event.getObject()).getCod_cta_conta_cxc());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void del_res(int cod_pol,String cod_subpol,String code_res) throws Exception{
        ResidenciasDAO dao;
        try{
            dao = new ResidenciasDAO();
            dao.del_res(cod_pol, cod_subpol, code_res);
            list_Res = dao.getResidencias_s(cod_pol, cod_subpol);
        }catch(Exception e){
            throw e;
        }
    }

}
