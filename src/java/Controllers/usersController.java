/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.DBConnection;
import Model.Users;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Frank2
 */

@ManagedBean
@ViewScoped
public class usersController {

    private Users loginUser = new Users();
    String User_name = "";
    String User_cod = "";
    String User_id = "";
    String User_rol = "";
    String Cod_residencial = "";
    private DBConnection conexion = null;
    private Connection conn = null;
    private Statement stmt = null;
    ResultSet rset = null;

    //Metodo para iniciar la conexion
    private void InitializeConnection() {
        try {
            conexion = new DBConnection();
            conn = conexion.CreateConnection();
            //conexion.DestroyConnection();
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodo para cerar la conexion
    private void DestroyConnection() {
        try {
            rset.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public usersController() {
    }
    
    

    public Users getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Users loginUser) {
        this.loginUser = loginUser;
    }
    
    public String login() {
        boolean validCredentials = false;
        FacesContext context = FacesContext.getCurrentInstance();
        InitializeConnection();
        try {
            String sql = "SELECT cod_residencial, nom_usuario as name,cod_usuario,corr_usuario,cod_rol FROM `mst_usuarios` WHERE `cod_usuario`= '" + loginUser.getUserName() + "' and `contraseña`= '" + loginUser.getPassword() + "' and activo='S'";
            rset = stmt.executeQuery(sql);
            if (rset.next()) {
                validCredentials = true;
                User_name = rset.getString("name");
                User_cod = rset.getString("cod_usuario");
                User_id = rset.getString("corr_usuario");
                User_rol = rset.getString("cod_rol");
                Cod_residencial = rset.getString("cod_residencial");
            }
        } catch (SQLException e) {
            System.out.println("CheckUser error" + e.getMessage());
        }

        DestroyConnection();
        if (validCredentials) {
            context.getExternalContext().getSessionMap().put("user_name", User_name);
            context.getExternalContext().getSessionMap().put("user_cod", User_cod);
            context.getExternalContext().getSessionMap().put("user_id", User_id);
            context.getExternalContext().getSessionMap().put("user_rol", User_rol);
            context.getExternalContext().getSessionMap().put("residencial", Cod_residencial);
            //FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "start.xhtml");
             RequestContext.getCurrentInstance().execute("window.location ='faces/start.xhtml'");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Credenciales Invalidas", "Credenciales Invalidas"));
        }
        return null;
    }
    
    public void save() {
        addMessage("Success", "Data saved");
    }
     
    public void update() {
        addMessage("Success", "Data updated");
    }
     
    public void delete() {
        addMessage("Success", "Data deleted");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
