/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import Database.Connect;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Frank2
 */
@ManagedBean(name = "reportes_bean")
@SessionScoped
public class reportes_bean {

    private Date fecha_ini;
    private Date fecha_fin;
    private Date fecha_pago;
    private Date fecha_ingresado;
    private Date fecha_ingresado_prov;
    private Date fecha_ingresado_prov_tipo;
    
    private int residencial;
    private int poligono;
    private String sub_poligono;
    private String residencia;
    
    FacesContext context2 = FacesContext.getCurrentInstance();

    public Date getFecha_ingresado_prov_tipo() {
        return fecha_ingresado_prov_tipo;
    }

    public void setFecha_ingresado_prov_tipo(Date fecha_ingresado_prov_tipo) {
        this.fecha_ingresado_prov_tipo = fecha_ingresado_prov_tipo;
    }

    public Date getFecha_ingresado_prov() {
        return fecha_ingresado_prov;
    }

    public void setFecha_ingresado_prov(Date fecha_ingresado_prov) {
        this.fecha_ingresado_prov = fecha_ingresado_prov;
    }

    public Date getFecha_ingresado() {
        return fecha_ingresado;
    }

    public void setFecha_ingresado(Date fecha_ingresado) {
        this.fecha_ingresado = fecha_ingresado;
    }

    public Date getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(Date fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getResidencial() {
        return residencial;
    }

    public void setResidencial(int residencial) {
        this.residencial = residencial;
    }

    public int getPoligono() {
        return poligono;
    }

    public void setPoligono(int poligono) {
        this.poligono = poligono;
    }

    public String getSub_poligono() {
        return sub_poligono;
    }

    public void setSub_poligono(String sub_poligono) {
        this.sub_poligono = sub_poligono;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }
    
    public void generar_reporte() {
        if (fecha_ini != null || fecha_fin != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("Fecha_ini", format.format(fecha_ini));
            parameters.put("Fecha_fin", format.format(fecha_fin));
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_recibos_prov_rango.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha inicial y final requeridas", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void generar_reporte_mora() {
        System.out.println("Metodo");
        Map parameters = new HashMap();
        parameters.put("Fecha_ini", "");
        Connect obj_connect = new Connect();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("reportes/safire_rep_residencias_en_mora.jasper");
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
            response.setContentType("application/pdf");
            JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
            JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            System.out.println("Error generando reporte" + ex.getMessage());
        }
    }
    
    
    public void generar_reporte_dia() {
        if (fecha_pago != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("fecha_pago", format.format(fecha_pago));
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_recibos_prov_diarios.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha de pago requerida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void generar_reporte_dia_prov() {
        if (fecha_ingresado_prov != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("fecha_pago", format.format(fecha_ingresado_prov));
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_recibos_diarios_provisional.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha de pago requerida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void generar_reporte_dia_prov_tipo() {
        if (fecha_ingresado_prov_tipo != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("fecha_ingreso", format.format(fecha_ingresado_prov_tipo));
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_recibos_dia_provxtipoingre.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=Reporte_Por_Tipo_de_Ingreso.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha de pago requerida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void generar_reporte_ingresados() {
        if (fecha_ingresado != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("fecha", format.format(fecha_ingresado));
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_recibos_diariosxdigitador.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha de pago requerida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void generar_reporte_pagos_adelantados() {
        Map parameters = new HashMap();
        Connect obj_connect = new Connect();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("reportes/safire_rep_residentes_adelantados.jasper");
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
            response.setContentType("application/pdf");
            JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
            JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            System.out.println("Error generando reporte" + ex.getMessage());
        }
    }
    
    public void generar_reporte_saldo_a_la_fecha() {
        Map parameters = new HashMap();
        Connect obj_connect = new Connect();
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("reportes/safire_rep_residentes_saldos.jasper");
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
            response.setContentType("application/pdf");
            JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
            JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();
        } catch (IOException | SQLException | JRException ex) {
            System.out.println("Error generando reporte" + ex.getMessage());
        }
    }
    
    public void generar_estado_cuenta() {
        if (poligono != 0 && sub_poligono!=null && residencia!=null) {
            residencial= 1;
            Map parameters = new HashMap();
            parameters.put("cod_residencial", residencial);
            parameters.put("cod_poligono", poligono);
            parameters.put("cod_sub_poligono", sub_poligono);
            parameters.put("cod_residencia", residencia);
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_estado_cta_residente.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Poligono, Subpoligono y residencia requeridos", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
}
