
package com.safire.bean;

import com.safire.dao.ActualizarTarjetasDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "actualizar_tarjetas")
@ViewScoped
public class ActualizarTarjetasBean implements Serializable {
    private int activas, inactivas;

    public int getActivas() {
        ActualizarTarjetasDAO dao = new ActualizarTarjetasDAO();
        activas = dao.get_activas();
        return activas;
    }

    public int getInactivas() {
        ActualizarTarjetasDAO dao = new ActualizarTarjetasDAO();
        inactivas = dao.get_inactivas();
        return inactivas;
    }
    
    public void actualizar(){
        ActualizarTarjetasDAO dao = new ActualizarTarjetasDAO();
        int tarjetas = dao.actualizar();
        FacesMessage msg2 = new FacesMessage("Se han actualizado " + tarjetas + " tarjetas", "");
        FacesContext.getCurrentInstance().addMessage(null, msg2);
    }
}
