package com.safire.bean;

import com.safire.dao.AsignarTarjetaDAO;
import com.safire.dao.ModelosDAO;
import com.safire.model.Asignacion_Tarjetas;
import com.safire.model.Modelos;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 * + *
 * + * @author desarrollo03 +
 */
@ManagedBean(name = "asignaciontarjetas")
@ViewScoped
public class AsignacionTarjetasBean implements Serializable {

    Date fecha_tar;
    boolean resident = false;
    public ArrayList<Modelos> lst_modelos;
    String poligono, nombre, residencia, estatus, no_tarjeta, no_placa, marca, color, modelo, responsable;
    public ArrayList<Asignacion_Tarjetas> lst_asignacion_tarjetas;

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getPoligono() {
        return poligono;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPoligono(String poligono) {
        this.poligono = poligono;
    }

    public ArrayList<Asignacion_Tarjetas> getLst_asignacion_tarjetas() {
        return lst_asignacion_tarjetas;
    }

    public void setLst_marcas(ArrayList<Asignacion_Tarjetas> lst_asignacion_tarjetas) {
        this.lst_asignacion_tarjetas = lst_asignacion_tarjetas;
    }

    public String getNo_tarjeta() {
        return no_tarjeta;
    }

    public void setNo_tarjeta(String no_tarjeta) {
        this.no_tarjeta = no_tarjeta;
    }

    public Date getFecha_tar() {
        return fecha_tar;
    }

    public void setFecha_tar(Date fecha_tar) {
        this.fecha_tar = fecha_tar;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public String getNo_placa() {
        return no_placa;
    }

    public void setNo_placa(String no_placa) {
        this.no_placa = no_placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        getModelos(Integer.parseInt(marca));
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public ArrayList<Modelos> getLst_modelos() {
        return lst_modelos;
    }

    public void setLst_modelos(ArrayList<Modelos> lst_modelos) {
        this.lst_modelos = lst_modelos;
    }

    @PostConstruct
    public void init() {
        nombre = "N/D";
        estatus = "N/D";
        fecha_tar = Date.from(Instant.now());
        lst_asignacion_tarjetas = new ArrayList<>();
        getModelos(1);
    }

    public void consultarEstatusResidente() {
        try {
            System.out.println("Consultando...");
            System.out.println("Poligono: " + poligono);
            System.out.println("Residencia: " + residencia);
            String[] parts = getPoligono().split("-");
            AsignarTarjetaDAO dao;
            dao = new AsignarTarjetaDAO();
            estatus = dao.verifyStatus(1, Integer.parseInt(parts[0]), parts[1], residencia);

            try {
                lst_asignacion_tarjetas = (new AsignarTarjetaDAO()).getDetailList(1, Integer.parseInt(parts[0]), parts[1], Integer.parseInt(residencia));
            } catch (Exception ex) {
                System.err.println("Listado de tarjetas asignadas: " + ex.getMessage());
            }

        } catch (Exception ex) {
            //Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error al consultar el estatus del residente: " + ex.getMessage());
            estatus = "N/D";
        }
    }

    public void cleanForm() {
        nombre = "N/D";
        estatus = "N/D";
        residencia = "";
    }

    public void getModelos(int cod_marca) {
        try {
            ModelosDAO dao;
            dao = new ModelosDAO();
            lst_modelos = dao.getModelos(cod_marca);
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarNombreResidente() {
        try {
            System.out.println("Consultando...");
            System.out.println("Poligono: " + poligono);
            System.out.println("Residencia: " + residencia);
            String[] parts = getPoligono().split("-");
            AsignarTarjetaDAO dao;
            dao = new AsignarTarjetaDAO();
            nombre = dao.getResName(1, Integer.parseInt(parts[0]), parts[1], residencia);
            consultarEstatusResidente();
        } catch (Exception ex) {
            //Logger.getLogger(build_menu.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error al consultar el nombre del residente: " + ex.getMessage());
            nombre = "N/D";
        }
    }

    public void getAsignacion_Tarjetas() {
        try {
            AsignarTarjetaDAO dao;
            dao = new AsignarTarjetaDAO();
            lst_asignacion_tarjetas = dao.getList();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAsignacion_Tarjetas() throws Exception {
        AsignarTarjetaDAO dao;
        try {
            dao = new AsignarTarjetaDAO();

            if (dao.verifyCard(1, no_tarjeta).equals("5")) {

                Asignacion_Tarjetas reg = new Asignacion_Tarjetas();
                String[] parts = getPoligono().split("-");
                String cardID = "1" + ((parts[0].length() == 1) ? "0" + parts[0] : parts[0]);
                switch (parts[1]) {
                    case "0":
                        cardID += "0";
                        break;
                    case "A":
                        cardID += "1";
                        break;
                    case "B":
                        cardID += "2";
                        break;
                }
                cardID += "3";
                cardID += ((residencia.length() == 1) ? "0" + residencia : residencia);

                reg.setCardid(Integer.parseInt(cardID));
                reg.setCod_color(Integer.parseInt(color));
                reg.setCod_estatus(0);
                reg.setCod_marca(Integer.parseInt(marca));
                reg.setCod_modelo(Integer.parseInt(modelo));
                reg.setCod_poligono(Integer.parseInt(parts[0]));
                reg.setCod_residencia(residencia);
                reg.setCod_residencial(1);
                reg.setCod_sub_poligono(parts[1]);
                reg.setNombre_responsable(responsable);
                reg.setNum_placa(no_placa);
                reg.setNum_tarjeta(no_tarjeta);
                dao.add(reg);

                try {
                    lst_asignacion_tarjetas = (new AsignarTarjetaDAO()).getDetailList(1, Integer.parseInt(parts[0]), parts[1], Integer.parseInt(residencia));
                } catch (Exception ex) {
                    System.err.println("Listado de tarjetas asignadas: " + ex.getMessage());
                }
                
                FacesMessage msg = new FacesMessage("Finalizado", "La tarjeta se asignó correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else{
                FacesMessage msg = new FacesMessage("Advertencia", "La tarjeta ingresada no está disponible para asignación.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            //this.asignaciontarjetas.setNum_tarjeta("");
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateAsignacion_Tarjetas(RowEditEvent event) throws Exception {
        AsignarTarjetaDAO dao;
        try {
            dao = new AsignarTarjetaDAO();
            Asignacion_Tarjetas updateObject = (Asignacion_Tarjetas) event.getObject();
            dao.update(updateObject.getCod_residencial(), updateObject.getCod_poligono(), updateObject.getCod_marca(), updateObject.getCod_color(), updateObject.getCod_sub_poligono(), updateObject.getCod_modelo(), updateObject.getCod_estatus(), updateObject.getCardid(), updateObject.getCod_residencia(), updateObject.getNum_tarjeta(), updateObject.getNum_placa(), updateObject.getNombre_responsable());
        } catch (Exception e) {
            System.out.println("Error actualizando color : " + e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delAsignacion_Tarjetas(int cod_residencial, int cod_poligono, int cod_sub_poligono, int cod_residencia, String num_tarjeta) throws Exception {
        AsignarTarjetaDAO dao;
        try {
            String[] parts = getPoligono().split("-");
            dao = new AsignarTarjetaDAO();
            dao.delete(cod_residencial, cod_poligono, cod_sub_poligono, cod_residencia, num_tarjeta);
            try {
                lst_asignacion_tarjetas = (new AsignarTarjetaDAO()).getDetailList(1, Integer.parseInt(parts[0]), parts[1], Integer.parseInt(residencia));
            } catch (Exception ex) {
                System.err.println("Listado de tarjetas asignadas: " + ex.getMessage());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
