/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.bean;

import com.safire.dao.Ingreso_recibosDAO;
import com.safire.dao.UsuariosDAO;
import com.safire.model.Usuarios;
import com.safire.model.pagos_infoadd;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "pago_addinfo")
@SessionScoped
public class pagos_infoadd_bean implements Serializable { 
    public pagos_infoadd pagos_infoadd = new pagos_infoadd();

    public pagos_infoadd getPagos_infoadd() {
        return pagos_infoadd;
    }

    public void setPagos_infoadd(pagos_infoadd pagos_infoadd) {
        this.pagos_infoadd = pagos_infoadd;
    }
    
    public void set_num_recibo(int num_recibo){
        this.pagos_infoadd.setNum_recibo_prov(num_recibo);
        this.pagos_infoadd.setCod_banco(1);
        this.pagos_infoadd.setNum_cheque_docto("");
        this.pagos_infoadd.setA_nombre_de("");
        this.pagos_infoadd.setObservacion("");
    }
    
    public void add_info(){
        Ingreso_recibosDAO dao = new Ingreso_recibosDAO();
        try {
            dao.info_add(pagos_infoadd);
        } catch (Exception ex) {
            System.out.println("Error agregando info: " + ex.getMessage());
        }
    }
    
}
