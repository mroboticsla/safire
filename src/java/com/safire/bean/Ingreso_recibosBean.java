/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.safire.dao.Ingreso_recibosDAO;
import com.safire.model.Ingreso_recibos;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo01
 * 
*/
@ManagedBean(name = "ingreso_recibos")
@ViewScoped
public class Ingreso_recibosBean implements Serializable {

    public Ingreso_recibos recibos = new Ingreso_recibos();

    public ArrayList<Ingreso_recibos> lst_recibos;

    public Ingreso_recibos getRecibos() {
        return recibos;
    }

    public void setRecibos(Ingreso_recibos recibos) {
        this.recibos = recibos;
    }

    public ArrayList<Ingreso_recibos> getLst_recibos() {
        return lst_recibos;
    }

    public void setLst_recibos(ArrayList<Ingreso_recibos> lst_recibos) {
        this.lst_recibos = lst_recibos;
    }

    public String getCurrent_date() {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(recibos.getUltima_fecha_abonada());
    }

    //Devuelve mes y año de cobro a partir de fecha actual
    public String get_mes() {
        Ingreso_recibosDAO dao = new Ingreso_recibosDAO();
        recibos.setRecibo_ini(1);
        recibos.setRecibo_fin(2);
        return dao.get_mes();
    }

    public void registro_pagos() {
        Ingreso_recibosDAO dao;
        dao = new Ingreso_recibosDAO();
        Date date = new Date();
        Format format = new SimpleDateFormat("M");

        boolean pass = true;

        //System.out.println("Fecha pago: "+format.format(recibos.getUltima_fecha_abonada()));
        if (recibos.getRecibo_ini() > recibos.getRecibo_fin()) {
            FacesMessage msg2 = new FacesMessage("Recibo Final debe ser mayor que el inicial", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
            pass = false;
        } else if (!format.format(recibos.getUltima_fecha_abonada()).equals(dao.get_mes_int())) {
            if (recibos.getUltima_fecha_abonada().getYear() + 1900 > dao.get_anio_int()) {
                FacesMessage msg2 = new FacesMessage("Fecha de pago excede el año vigente de cobro", "");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
                pass = false;
            } else if (recibos.getUltima_fecha_abonada().getYear() + 1900 == dao.get_anio_int()) {
                if (recibos.getUltima_fecha_abonada().getMonth() + 1 > dao.get_mes_int()) {
                    FacesMessage msg2 = new FacesMessage("Fecha de pago excede el mes vigente de cobro", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                    pass = false;
                }
            }
        }
        
        System.err.println("Mes en proceso: " + dao.get_mes_int());
        System.err.println("Año en proceso: " + dao.get_anio_int());
        System.err.println("Fecha ingresada: " + (recibos.getUltima_fecha_abonada().getMonth() + 1) + " de " + (recibos.getUltima_fecha_abonada().getYear() + 1900));

        if (pass) {
            try {
                lst_recibos = dao.getRecibos(recibos);
            } catch (Exception ex) {
                System.out.println("Error leyendo recibos: " + ex.getMessage());
            }
        }else{
            lst_recibos = new ArrayList<>();
        }
    }

    public Map<String, String> getPoligonos() throws Exception {
        try {
            Ingreso_recibosDAO dao;
            dao = new Ingreso_recibosDAO();
            return dao.getpoligonos();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Map<String, String> getSubpoligonos() throws Exception {
        try {
            Ingreso_recibosDAO dao2;
            dao2 = new Ingreso_recibosDAO();
            return dao2.getsubpoligonos(recibos.getCod_poligono());
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Map<String, String> getFormas_pago() throws Exception {
        try {
            Ingreso_recibosDAO dao2;
            dao2 = new Ingreso_recibosDAO();
            return dao2.getformas_pago();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void onPoligonoSelect() {
        try {
            getSubpoligonos();
        } catch (Exception ex) {
            Logger.getLogger(Ingreso_recibosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_poligono(RowEditEvent event) throws Exception {
        ((Ingreso_recibos) event.getObject()).setCod_poligono2(((Ingreso_recibos) event.getObject()).getCod_poligono() + "-" + ((Ingreso_recibos) event.getObject()).getCod_sub_poligono());
    }

    public void set_current_date(RowEditEvent event) throws Exception {
        if (!((Ingreso_recibos) event.getObject()).getEstado_recibo().equals("A")) {
            ((Ingreso_recibos) event.getObject()).setUltima_fecha_abonada(recibos.getUltima_fecha_abonada());
        }
    }

    public void update_pago(RowEditEvent event) throws Exception {
        Ingreso_recibosDAO dao;
        dao = new Ingreso_recibosDAO();
        Format format = new SimpleDateFormat("M");

        Boolean _continue = true;

        if ((((Ingreso_recibos) event.getObject()).getEstado_recibo()).equals("A")) {
            if (!format.format(((Ingreso_recibos) event.getObject()).getUltima_fecha_abonada()).equals(dao.get_mes_int())) {
                if (recibos.getUltima_fecha_abonada().getYear() + 1900 > dao.get_anio_int()) {
                    FacesMessage msg2 = new FacesMessage("Fecha de pago excede el año vigente de cobro", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                    _continue = false;
                } else if (recibos.getUltima_fecha_abonada().getYear() + 1900 == dao.get_anio_int()) {
                    if (recibos.getUltima_fecha_abonada().getMonth() + 1 > dao.get_mes_int()) {
                        FacesMessage msg2 = new FacesMessage("Fecha de pago excede el mes vigente de cobro", "");
                        FacesContext.getCurrentInstance().addMessage(null, msg2);
                        _continue = false;
                    }
                }
            }
        }

        if (_continue) {
            try {
                Ingreso_recibosDAO dao3;
                format = new SimpleDateFormat("yyyy-MM-dd");
                String ultima_fecha_abonada = "";

                if (!(((Ingreso_recibos) event.getObject()).getEstado_recibo()).equals("A")) {
                    ultima_fecha_abonada = format.format(recibos.getUltima_fecha_abonada());
                } else {
                    ultima_fecha_abonada = format.format(((Ingreso_recibos) event.getObject()).getUltima_fecha_abonada());
                }

                dao3 = new Ingreso_recibosDAO();
                String pol_subpol = ((Ingreso_recibos) event.getObject()).getCod_poligono2();
                System.out.println(pol_subpol);
                String[] parts = pol_subpol.split("-");
                int pol = Integer.parseInt(parts[0]); // 004
                String subpol = parts[1];

                if (dao3.update_pago(((Ingreso_recibos) event.getObject()).getNum_recibo_prov(), pol, subpol, ((Ingreso_recibos) event.getObject()).getCod_residencia(), ((Ingreso_recibos) event.getObject()).getValor_recibo(), ((Ingreso_recibos) event.getObject()).getForma_pago(), ultima_fecha_abonada, ((Ingreso_recibos) event.getObject()).getEstado_recibo())) {
                    FacesMessage msg2 = new FacesMessage("Pago actualizado", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                    lst_recibos = dao3.getRecibos(recibos);
                } else {
                    FacesMessage msg2 = new FacesMessage("Transaccion no realizada, Residencia invalida o pago ya procesado", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                    //lst_recibos = dao3.getRecibos_upd(recibos);
                }

                //registro_pagos();
            } catch (Exception e) {
                System.out.println("Error actualizando pago bean: " + e.getMessage());
            }
        }
    }

    public float get_total_dia() {
        Ingreso_recibosDAO dao3 = new Ingreso_recibosDAO();
        return dao3.get_total_dia();
    }

    public void aprobar_dia() {
        Ingreso_recibosDAO dao4 = new Ingreso_recibosDAO();
        try {
            dao4.aprobar_dia();
            lst_recibos = dao4.getRecibos(recibos);
        } catch (Exception ex) {
            System.out.println("Error aprobando pagos: " + ex.getMessage());
        }
    }

    public void cancelar_dia(int recibo_ini, int recibo_fin) {
        Ingreso_recibosDAO dao5 = new Ingreso_recibosDAO();
        try {
            dao5.cancelar_dia();
            //System.out.println("Recibo ini: " + recibo_ini + "Fin: "+recibo_fin);
            lst_recibos = dao5.getRecibos_upd(recibos, recibo_ini, recibo_fin);
        } catch (Exception ex) {
            System.out.println("Error cancelandos recibos: " + ex.getMessage());
        }
    }

    public void cancelar_recibo(int num_recibo) {
        Ingreso_recibosDAO dao5 = new Ingreso_recibosDAO();
        try {
            dao5.cancelar_recibo(num_recibo);
            //System.out.println("Recibo ini: " + recibo_ini + "Fin: "+recibo_fin);
            lst_recibos = dao5.getRecibos(recibos);
        } catch (Exception ex) {
            System.out.println("Error cancelandos recibos: " + ex.getMessage());
        }
    }

    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
    }

    public void call_report() {

    }

}