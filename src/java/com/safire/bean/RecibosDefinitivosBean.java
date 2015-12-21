package com.safire.bean;

import Database.Connect;
import com.safire.dao.RecibosDefinitivosDAO;
import com.safire.model.Bancos;
import com.safire.model.OtroIngreso;
import com.safire.model.ReciboDefinitivo;
import com.safire.model.Transaccion;
import com.safire.model.pagos_infoadd;
import static groovy.inspect.Inspector.print;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.context.RequestContext;

/**
 *
 * @author desarrollo03
 */
@ManagedBean(name = "recibos_d")
@SessionScoped
public class RecibosDefinitivosBean implements Serializable {

    public int recibo_prov = 0;
    public boolean recibo_valido = false;
    ReciboDefinitivo recibo = new ReciboDefinitivo();
    String forma_pago;
    String cod_poligono, cod_subpoligono, cod_resedencia, propietario, residente, concepto;
    float otros_ingresos, valor_recibo, saldo_anterior, nuevo_saldo;
    boolean requiereInfo = true;

    int trans_code;
    Transaccion tr = new Transaccion();
    float otro_valor, total_recibo;

    List<OtroIngreso> list_otros = new ArrayList<>();

    public pagos_infoadd pagos_infoadd = new pagos_infoadd();

    public float getTotal_recibo() {
        total_recibo = valor_recibo + getOtros_ingresos();
        return total_recibo;
    }

    public void setTotal_recibo(float total_recibo) {
        this.total_recibo = total_recibo;
    }

    public float getOtro_valor() {
        return otro_valor;
    }

    public void setOtro_valor(float otro_valor) {
        this.otro_valor = otro_valor;
    }

    public int getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(int trans_code) {
        this.trans_code = trans_code;
    }

    public Transaccion getTr() {
        return tr;
    }

    public void setTr(Transaccion tr) {
        this.tr = tr;
    }

    public List<OtroIngreso> getList_otros() {
        return list_otros;
    }

    public void setList_otros(List<OtroIngreso> list_otros) {
        this.list_otros = list_otros;
    }

    public pagos_infoadd getPagos_infoadd() {
        return pagos_infoadd;
    }

    public void setPagos_infoadd(pagos_infoadd pagos_infoadd) {
        this.pagos_infoadd = pagos_infoadd;
    }

    public boolean isRequiereInfo() {
        return requiereInfo;
    }

    public void setRequiereInfo(boolean requiereInfo) {
        this.requiereInfo = requiereInfo;
    }

    public int getRecibo_prov() {
        return recibo_prov;
    }

    public void setRecibo_prov(int recibo_prov) {
        this.recibo_prov = recibo_prov;
    }

    public ReciboDefinitivo getRecibo() {
        return recibo;
    }

    public void setRecibo(ReciboDefinitivo recibo) {
        this.recibo = recibo;
    }

    public boolean isRecibo_valido() {
        return recibo_valido;
    }

    public void setRecibo_valido(boolean recibo_valido) {
        this.recibo_valido = recibo_valido;
    }

    public String getCod_poligono() {
        return cod_poligono;
    }

    public void setCod_poligono(String cod_poligono) {
        this.cod_poligono = cod_poligono;
    }

    public String getCod_subpoligono() {
        return cod_subpoligono;
    }

    public void setCod_subpoligono(String cod_subpoligono) {
        this.cod_subpoligono = cod_subpoligono;
    }

    public String getCod_resedencia() {
        return cod_resedencia;
    }

    public void setCod_resedencia(String cod_resedencia) {
        this.cod_resedencia = cod_resedencia;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public float getOtros_ingresos() {
        otros_ingresos = 0;
        for (OtroIngreso oi : list_otros) {
            otros_ingresos += oi.getValor();
        }
        return otros_ingresos;
    }

    public void setOtros_ingresos(float otros_ingresos) {
        this.otros_ingresos = otros_ingresos;
    }

    public float getValor_recibo() {
        return valor_recibo;
    }

    public void setValor_recibo(float valor_recibo) {
        this.valor_recibo = valor_recibo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getResidente() {
        return residente;
    }

    public void setResidente(String residente) {
        this.residente = residente;
    }

    public float getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(float saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public float getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(float nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @PostConstruct
    public void init() {
        try {
            //getMarcas();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Transaccion> getTransacciones() throws Exception {
        try {
            RecibosDefinitivosDAO dao = new RecibosDefinitivosDAO();
            return dao.getTransacciones(getForma_pago());
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Bancos> getBancos() throws Exception {
        try {
            RecibosDefinitivosDAO dao = new RecibosDefinitivosDAO();
            return dao.getbancos();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void checkPaymentForm() {
        if (forma_pago != null) {
            System.out.println("Forma de Pago: " + forma_pago);
            RecibosDefinitivosDAO dao = new RecibosDefinitivosDAO();
            requiereInfo = dao.ver_info_forma_pago(forma_pago);
        }
    }

    public void reciboDefinitivo() {
        try {
            recibo_valido = true;
            RecibosDefinitivosDAO dao = new RecibosDefinitivosDAO();
            recibo = dao.getRecibo(recibo_prov);
            if (recibo == null) {
                FacesMessage msg = new FacesMessage("Recibo no encontrado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                recibo_valido = false;
            } else if (!recibo.getEstado_recibo().equals("P")) {
                FacesMessage msg = new FacesMessage("Transaccion no realizada, El recibo seleccionado no ha sido aplicado", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                recibo = null;
                recibo_valido = false;
            }
            System.out.println("Recibo provisional: " + recibo.getNum_recibo_prov());
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiar() {
        try {
            recibo = new ReciboDefinitivo();
            cod_poligono = "";
            cod_subpoligono = "";
            cod_resedencia = "";
            valor_recibo = 0;
            otro_valor = 0;
            otros_ingresos = 0;
            total_recibo = 0;
            residente = "";
            propietario = residente = "";
            saldo_anterior = 0;
            nuevo_saldo = 0;
            pagos_infoadd = new pagos_infoadd();
            list_otros = new ArrayList<>();
            concepto = "";
        } catch (Exception e) {
            System.out.println("Error al limpiar el formulario: " + e.getMessage());
        }
    }

    public void delete_otro_ingreso(OtroIngreso otro_ingreso) {
        try {
            list_otros.remove(otro_ingreso);
        } catch (Exception e) {
            System.out.println("Error add_otro_ingreso: " + e.getMessage());
        }
    }

    public void add_otro_ingreso() {
        try {
            RecibosDefinitivosDAO dao = new RecibosDefinitivosDAO();
            tr = dao.getTransaccion(trans_code);
            OtroIngreso otro_ingreso = new OtroIngreso();
            otro_ingreso.setTransaccion(tr);
            otro_ingreso.setValor(otro_valor);
            list_otros.add(otro_ingreso);
            System.err.println("Codigo Transaccion: " + trans_code);
        } catch (Exception e) {
            System.out.println("Error add_otro_ingreso: " + e.getMessage());
        }
    }

    public void ver_otros_ingresos() {
        try {
            RecibosDefinitivosDAO dao3;

            dao3 = new RecibosDefinitivosDAO();
            RequestContext rc = RequestContext.getCurrentInstance();

            rc.execute("PF('otros_dlg').show()");
        } catch (Exception e) {
            System.out.println("Error actualizando pago bean: " + e.getMessage());
        }
    }

    public void consultar_datos() {
        try {
            RecibosDefinitivosDAO dao3;

            dao3 = new RecibosDefinitivosDAO();
            String pol_subpol = getCod_poligono();
            System.out.println(pol_subpol);
            String[] parts = pol_subpol.split("-");
            int pol = Integer.parseInt(parts[0]); // 004
            String subpol = parts[1];
            residente = dao3.nombre_residente(pol, subpol, getCod_resedencia());
            propietario = residente = dao3.nombre_propietario(pol, subpol, getCod_resedencia());

            if (!propietario.equals("")) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('confirm_dlg').show()");

                saldo_anterior = dao3.saldo_actual(pol, subpol, getCod_resedencia());
                nuevo_saldo = saldo_anterior - valor_recibo;
            } else {
                FacesMessage msg2 = new FacesMessage(FacesMessage.SEVERITY_WARN, "La residencia No existe para el poligono seleccionado ó No posee información del propietario.", "");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
            }
        } catch (Exception e) {
            System.out.println("Error actualizando pago bean: " + e.getMessage());
        }
    }

    public void update_pago() {
        Boolean _continue = true;
        if (_continue) {
            try {
                RecibosDefinitivosDAO dao3;

                dao3 = new RecibosDefinitivosDAO();
                String pol_subpol = getCod_poligono();
                System.out.println(pol_subpol);
                String[] parts = pol_subpol.split("-");
                int pol = Integer.parseInt(parts[0]); // 004
                String subpol = parts[1];
                int new_rec = dao3.update_pago(pol, subpol, getCod_resedencia(), getOtros_ingresos(), getValor_recibo(), getTotal_recibo(), getForma_pago(), pagos_infoadd, getList_otros(), concepto);
                System.out.println("Nuevo recibo: " + new_rec);
                if (new_rec != -1) {
                    limpiar();
                    FacesMessage msg2 = new FacesMessage("Se ha efectuado el pago exitosamente", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                    recibo_prov = new_rec;
                    reciboDefinitivo();
                } else {
                    FacesMessage msg2 = new FacesMessage("Transaccion no realizada, Residencia invalida", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                }

                //registro_pagos();
            } catch (Exception e) {
                System.out.println("Error actualizando pago bean: " + e.getMessage());
            }
        }
    }

    public void generar_reporte() {
        if (recibo_valido) {
            //System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("recibo_ini", String.valueOf(recibo_prov));
            parameters.put("recibo_fin", String.valueOf(recibo_prov));
            parameters.put("copy", "ORIGINAL RESIDENTE");
            
            Map parametersCopy = new HashMap();
            parametersCopy.put("recibo_ini", String.valueOf(recibo_prov));
            parametersCopy.put("recibo_fin", String.valueOf(recibo_prov));
            parametersCopy.put("copy", "ORIGINAL RESIDENTE");
            
            Connect obj_connect = new Connect();
            try {
                System.err.println("Generando reporte...");
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String reportName = "recibo_definitivo_detalle.jasper";
                if (recibo.getSaldo_actual() == recibo.getNuevo_saldo()) {
                    reportName = "recibo_definitivo_detalle_cero.jasper";
                }
                String ruta = servletContext.getRealPath("reportes/" + reportName);
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                //JasperPrint imprimir2 = JasperFillManager.fillReport(ruta, parametersCopy, obj_connect.CreateConnection());
                //obtengo el numero de paginas 
                //int total = imprimir.getPages().size();
                /*
                int total1 = imprimir2.getPages().size();
                int i = 1;
                for (int count = 0; count < (total1); count++) {
                    imprimir.addPage(total, (JRPrintPage) imprimir2.getPages().get(count));
                    i++;
                }
                */

                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                //context.getApplication().getStateManager().saveView(context);
                //context.responseComplete();
                System.err.println("Generado!");
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("No ha ingresado información válida", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
