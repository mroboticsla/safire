package com.safire.bean;

import Database.Connect;
import com.safire.dao.IngresoTarjetasAccesoDAO;
import com.safire.dao.ProveedoresDAO;
import com.safire.model.IngresoTarjetaAcceso;
import com.safire.model.Proveedor;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo03
 */
@ManagedBean(name = "ingreso_tar")
@SessionScoped
public class IngresoTarjetaAccesoBean implements Serializable{
    public Proveedor proveedor = new Proveedor();
    public IngresoTarjetaAcceso tarjetas = new IngresoTarjetaAcceso();
    public ArrayList<IngresoTarjetaAcceso> lst_tarjetas;
    
    private Double progress;
    String poligono_inicial, poligono_final;

    public String getPoligono_inicial() {
        return poligono_inicial;
    }

    public void setPoligono_inicial(String poligono_inicial) {
        this.poligono_inicial = poligono_inicial;
    }

    public String getPoligono_final() {
        return poligono_final;
    }

    public void setPoligono_final(String poligono_final) {
        this.poligono_final = poligono_final;
    }
    
    public Double getProgress() {
        /*if(progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int)(Math.random() * 70);
             
            if(progress > 100)
                progress = 100;
        }
         */
        return progress;
    }
 
    public void setProgress(Double progress) {
        this.progress = progress;
    }
    
    public void generar_reporte_tarjetas_activas() {
        System.err.println("Estoy aqii");
        if (poligono_inicial != null && poligono_final != null) {
            System.out.println("Metodo");
            Map parameters = new HashMap();
            parameters.put("poligono_ini", poligono_inicial);
            parameters.put("poligono_fin", poligono_final);
            Connect obj_connect = new Connect();
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String ruta = servletContext.getRealPath("reportes/safire_rep_tarjetas_activas.jasper");
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment;filename=Reporte_Tarjetas_Activas.pdf");
                response.setContentType("application/pdf");
                JasperPrint imprimir = JasperFillManager.fillReport(ruta, parameters, obj_connect.CreateConnection());
                JasperExportManager.exportReportToPdfStream(imprimir, response.getOutputStream());
                context.getApplication().getStateManager().saveView(context);
                context.responseComplete();
            } catch (IOException | SQLException | JRException ex) {
                System.out.println("Error generando reporte" + ex.getMessage());
            }
        } else {
            FacesMessage msg = new FacesMessage("Parametros requeridos", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     
    public void onComplete() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('insertCards').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Generación de Tarjetas de Acceso","Proceso finalizado exitosamente"));
        cancel();
    }
     
    public void cancel() {
        progress = null;
    }

    public IngresoTarjetaAcceso getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(IngresoTarjetaAcceso tarjetas) {
        this.tarjetas = tarjetas;
    }

    public ArrayList<IngresoTarjetaAcceso> getLst_tarjetas() {
        return lst_tarjetas;
    }

    public void setLst_tarjetas(ArrayList<IngresoTarjetaAcceso> lst_tarjetas) {
        this.lst_tarjetas = lst_tarjetas;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ArrayList<IngresoTarjetaAcceso> getLst() {
        try {
            getData();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst_tarjetas;
    }

    public void setLst_marcas(ArrayList<IngresoTarjetaAcceso> lst_tarjetas) {
        this.lst_tarjetas = lst_tarjetas;
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public void getData(){
        try {
            IngresoTarjetasAccesoDAO dao = new IngresoTarjetasAccesoDAO();
            lst_tarjetas = dao.getList();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add() throws Exception{
        cancel();
        IngresoTarjetasAccesoDAO dao;
        RequestContext rc = RequestContext.getCurrentInstance();
        String message = "";
        try{
            if (tarjetas.getNum_tarjeta_ini() < tarjetas.getNum_tarjeta_fin()){
                dao = new IngresoTarjetasAccesoDAO();
                dao.add(tarjetas);
                
                for (int i = 0; i <= tarjetas.getNum_tarjeta_fin() - tarjetas.getNum_tarjeta_ini(); i++){
                    dao.addCard(tarjetas, String.valueOf(i + tarjetas.getNum_tarjeta_ini()));
                    progress = (double) (i%(tarjetas.getNum_tarjeta_fin() - tarjetas.getNum_tarjeta_ini())) / 10;
                    if (progress == 0){
                        progress = (double) (i/(tarjetas.getNum_tarjeta_fin() - tarjetas.getNum_tarjeta_ini())) * 100;
                    }
                    System.out.println("Progress(" + i + "): " + progress);
                }

                lst_tarjetas = dao.getList();

                tarjetas = new IngresoTarjetaAcceso();
            }else{
                message = "El correlativo final debe ser mayor al correlativo inicial";
            }
        }catch(Exception e){
            System.err.println(e.toString());
        }finally{
            if (!message.equals("")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Generación de Tarjetas de Acceso",message));
            }
        }
    }
    
    public void update(RowEditEvent event) throws Exception {
        ProveedoresDAO dao;
        try {
            dao = new ProveedoresDAO();
            Proveedor updateObject = (Proveedor) event.getObject();
            dao.update(updateObject.getCod_proveedor(), updateObject.getNombre_comercial(), updateObject.getContacto(), updateObject.getTelefono(), updateObject.getNrc());
        } catch (Exception e) {
            System.out.println("Error actualizando : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void delete(int cod_proveedor) throws Exception{
        IngresoTarjetasAccesoDAO dao;
        try{
            dao = new IngresoTarjetasAccesoDAO();
            
        }catch(Exception e){
            throw e;
        }
    }
}
