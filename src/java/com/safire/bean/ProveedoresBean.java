package com.safire.bean;

import com.safire.dao.ProveedoresDAO;
import com.safire.model.Proveedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desarrollo03
 */
@ManagedBean(name = "proveedores")
@SessionScoped
public class ProveedoresBean implements Serializable{
    public Proveedor proveedor = new Proveedor();
    public ArrayList<Proveedor> lst_proveedores;
    
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ArrayList<Proveedor> getLst() {
        return lst_proveedores;
    }

    public void setLst_marcas(ArrayList<Proveedor> lst_proveedores) {
        this.lst_proveedores = lst_proveedores;
    }
    
    @PostConstruct
    public void init() {
        try {
            getData();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getData(){
        try {
            ProveedoresDAO dao = new ProveedoresDAO();
            lst_proveedores = dao.getData();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add() throws Exception{
        ProveedoresDAO dao;
        try{
            dao = new ProveedoresDAO();
            dao.add(proveedor);
            lst_proveedores = dao.getData();
            this.proveedor.setCod_residencial(0);
            this.proveedor.setCod_proveedor(0);
            this.proveedor.setNombre_comercial("");
            this.proveedor.setContacto("");
            this.proveedor.setTelefono("");
            this.proveedor.setNrc("");
        }catch(Exception e){
            throw e;
        }
    }
    
    public void update(RowEditEvent event) throws Exception {
        ProveedoresDAO dao;
        try {
            dao = new ProveedoresDAO();
            Proveedor updateObject = (Proveedor) event.getObject();
            dao.update(updateObject.getCod_proveedor(), updateObject.getNombre_comercial(), updateObject.getContacto(), updateObject.getTelefono(), updateObject.getNrc());
        } catch (Exception e) {
            System.out.println("Error actualizando color : " + e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void delete(int cod_proveedor) throws Exception{
        ProveedoresDAO dao;
        try{
            dao = new ProveedoresDAO();
            dao.delete(cod_proveedor);
            lst_proveedores = dao.getData();
        }catch(Exception e){
            throw e;
        }
    }
}
