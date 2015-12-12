/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safire.bean;

import com.safire.dao.DAO;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author desarrollo01
 */
@ManagedBean(name = "menu")
@ViewScoped
public class build_menu extends DAO {

    private String page = "./faces/welcome.xhtml";

    private MenuModel model;
    String menu;
    String submenu;
    
    RequestContext context = RequestContext.getCurrentInstance();
    FacesContext context2 = FacesContext.getCurrentInstance();

    public void build_menu() throws Exception {
        model = new DefaultMenuModel();
        
        if (!"".equals(context2.getExternalContext().getSessionMap().get("user_rol").toString())) {
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("select DISTINCT c.DESC_MODULO,c.COD_MODULO,a.COD_ROL from mst_roles a,mst_roles_aplicaciones b,mst_modulos c,mst_aplicaciones d where c.COD_MODULO=b.COD_MODULO and d.COD_APLICACION=b.COD_APLICACION and a.COD_ROL=" + context2.getExternalContext().getSessionMap().get("user_rol").toString()+" order by c.COD_MODULO");
                ResultSet rset;
                rset = ps.executeQuery();
                while (rset.next()) {
                    menu = rset.getString("DESC_MODULO");
                    DefaultSubMenu sub_menu = new DefaultSubMenu(menu);
                    PreparedStatement ps2 = this.getCn().prepareStatement("select DISTINCT d.NOMBRE_APLICACION,d.RUTA_APLICACION from mst_roles a,mst_roles_aplicaciones b,mst_modulos c,mst_aplicaciones d where c.COD_MODULO=b.COD_MODULO and d.COD_APLICACION=b.COD_APLICACION and a.COD_ROL=b.COD_ROL and d.COD_MODULO=c.COD_MODULO and b.COD_MODULO=" + rset.getString("COD_MODULO") + " and a.COD_ROL=" + rset.getString("COD_ROL") + " order by d.ORDEN_APLICACION asc");
                    ResultSet rset2;
                    rset2 = ps2.executeQuery();
                    while (rset2.next()) {
                        submenu = rset2.getString("NOMBRE_APLICACION");
                        DefaultMenuItem item = new DefaultMenuItem(submenu);
                        item.setIcon("ui-icon-home");
                        item.setParam("page", rset2.getString("RUTA_APLICACION"));
                        item.setOncomplete("updateContent()");
                        item.setUpdate("@none");
                        item.setCommand("#{menu.navegar}");
                        sub_menu.addElement(item);
                    }
                    model.addElement(sub_menu);
                }
            } catch (Exception e) {

            } finally {
                this.Cerrar();
            }
        }else{
            destroy_session();
            context.execute("location='../'");
        }
    }

    @PostConstruct
    public void init() {
        try {
            build_menu();
        } catch (Exception ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
    public void destroy_session(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../");
        } catch (IOException ex) {
            Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void navegar() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String source = request.getParameter("page");
        page = source;
    }

}
