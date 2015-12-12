/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.safire.dao;
 
import com.safire.model.Ingreso_recibos;
import com.safire.model.pagos_infoadd;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
 
/**
*
* @author Frank2
*/
public class Ingreso_recibosDAO extends DAO {
 
    private Map<String, String> poligonos;
    private Map<String, String> subpoligonos;
    private Map<String, String> formas_pago;
    public float saldo_res;
    FacesContext context2 = FacesContext.getCurrentInstance();
 
    //METODO PARA LISTAR LOS USUARIOS DE LA APLICACION
    public ArrayList<Ingreso_recibos> getRecibos(Ingreso_recibos recibo) throws Exception {
        ArrayList<Ingreso_recibos> al = new ArrayList<>();
        boolean found = false;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_residencial,a.corr_generados,a.fecha_generados from tbl_recibos_prov_generados a where a.corr_generados>=? and a.corr_generados<=? and a.corr_generados not in (select b.num_recibo_prov from tbl_recibos_provi_defini b)");
            ps.setInt(1, recibo.getRecibo_ini());
            ps.setInt(2, recibo.getRecibo_fin());
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                add_recibos(rset.getInt("cod_residencial"), rset.getInt("corr_generados"), rset.getDate("fecha_generados"));
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error buscando recibos provicionales" + ex.getMessage());
            throw ex;
        } finally {
            this.Cerrar();
        }
 
        try {
            this.Conectar();
            String sql = "select b.cod_residencial,b.num_recibo_prov, b.cod_poligono,b.cod_sub_poligono,b.cod_residencia,";
            sql = sql + " ifnull(concat(c.nombre_propietario,' ',c.apellido_propietario),'-') as propietario,b.valor_recibo, ";
            sql = sql + " b.saldo_actual,b.nuevo_saldo,ultima_fecha_abonada, ifnull(a.desc_forma_pago,'') as forma_pago, b.estado_recibo ";
            sql = sql + " from tbl_recibos_provi_defini b left outer join mst_propietarios c ON b.cod_poligono=c.cod_poligono and b.cod_sub_poligono=c.cod_sub_poligono and b.cod_residencia=c.cod_residencia left outer join mst_formas_pago a ON a.corr_forma_pago=b.corr_forma_pago";
            sql = sql + " and b.cod_residencia=c.cod_residencia where b.num_recibo_prov>=? and b.num_recibo_prov<=? order by b.num_recibo_prov asc";
            //System.out.println("Select**** " + sql);
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setInt(1, recibo.getRecibo_ini());
            ps.setInt(2, recibo.getRecibo_fin());
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                Ingreso_recibos r = new Ingreso_recibos();
                r.setCod_residencial(rset.getInt("cod_residencial"));
                r.setNum_recibo_prov(rset.getInt("num_recibo_prov"));
                r.setCod_poligono(rset.getInt("cod_poligono"));
                r.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                r.setCod_poligono2(rset.getInt("cod_poligono") + "-" + rset.getString("cod_sub_poligono"));
                r.setCod_residencia(rset.getString("cod_residencia"));
                r.setValor_recibo(rset.getFloat("valor_recibo"));
                r.setSaldo_actual(rset.getFloat("saldo_actual"));
                r.setNuevo_saldo(rset.getFloat("nuevo_saldo"));
                r.setPropietario(rset.getString("propietario"));
                r.setForma_pago(rset.getString("forma_pago"));
                r.setUltima_fecha_abonada(rset.getDate("ultima_fecha_abonada"));
                r.setFecha_abonada(rset.getString("ultima_fecha_abonada"));
                r.setEstado_recibo(rset.getString("estado_recibo"));
                al.add(r);
                found = true;
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error cargando recibos provicionales" + ex.getMessage());
        } finally {
            this.Cerrar();
        }
        if (found) {
            return al;
        } else {
            return null;
        }
    }
 
    //METODO PARA LISTAR LOS USUARIOS DE LA APLICACION
    public ArrayList<Ingreso_recibos> getRecibos_upd(Ingreso_recibos recibo, int recibo_ini, int recibo_fin) throws Exception {
        ArrayList<Ingreso_recibos> al = new ArrayList<>();
        boolean found = false;
        try {
            this.Conectar();
            String sql = "select b.cod_residencial,b.num_recibo_prov, b.cod_poligono,b.cod_sub_poligono,b.cod_residencia,";
            sql = sql + " ifnull(concat(c.nombre_propietario,' ',c.apellido_propietario),'-') as propietario,b.valor_recibo, ";
            sql = sql + " b.saldo_actual,b.nuevo_saldo,ultima_fecha_abonada, ifnull(a.desc_forma_pago,'') as forma_pago, b.estado_recibo ";
            sql = sql + " from tbl_recibos_provi_defini b left outer join mst_propietarios c ON b.cod_poligono=c.cod_poligono and b.cod_sub_poligono=c.cod_sub_poligono and b.cod_residencia=c.cod_residencia left outer join mst_formas_pago a ON a.corr_forma_pago=b.corr_forma_pago";
            sql = sql + " and b.cod_residencia=c.cod_residencia where b.num_recibo_prov>=? and b.num_recibo_prov<=? order by b.num_recibo_prov asc";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            //System.out.println("Consulta select: "+"select b.cod_residencial,b.num_recibo_prov, b.cod_poligono,b.cod_sub_poligono,b.cod_residencia,b.valor_recibo, b.saldo_actual,b.nuevo_saldo,b.ultima_fecha_abonada from tbl_recibos_provi_defini b where b.num_recibo_prov>="+recibo.getRecibo_ini()+"and b.num_recibo_prov<="+recibo.getRecibo_fin()+" order by b.num_recibo_prov asc");
            ps.setInt(1, recibo_ini);
            ps.setInt(2, recibo_fin);
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                Ingreso_recibos r = new Ingreso_recibos();
                r.setCod_residencial(rset.getInt("cod_residencial"));
                r.setNum_recibo_prov(rset.getInt("num_recibo_prov"));
                r.setCod_poligono(rset.getInt("cod_poligono"));
                r.setCod_sub_poligono(rset.getString("cod_sub_poligono"));
                r.setCod_residencia(rset.getString("cod_residencia"));
                r.setValor_recibo(rset.getFloat("valor_recibo"));
                r.setSaldo_actual(rset.getFloat("saldo_actual"));
                r.setNuevo_saldo(rset.getFloat("nuevo_saldo"));
                r.setPropietario(rset.getString("propietario"));
                r.setForma_pago(rset.getString("forma_pago"));
                r.setUltima_fecha_abonada(rset.getDate("ultima_fecha_abonada"));
                r.setFecha_abonada(rset.getString("ultima_fecha_abonada"));
                r.setEstado_recibo(rset.getString("estado_recibo"));
                al.add(r);
                found = true;
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error cargando recibos provicionales" + ex.getMessage());
        } finally {
            this.Cerrar();
        }
        if (found) {
            return al;
        } else {
            return null;
        }
    }
 
    public void add_recibos(int cod_residencial, int corr_generados, Date fecha_generados) throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("insert into tbl_recibos_provi_defini(cod_residencial,num_recibo_prov,fecha_recibo_prov,valor_recibo,estado_recibo) values(?,?,?,15,'I')");
            ps.setInt(1, cod_residencial);
            ps.setInt(2, corr_generados);
            ps.setDate(3, fecha_generados);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error insertando recibos provicionales: " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }
 
    //metodo para obtener los poligonos
    public Map<String, String> getpoligonos() throws Exception {
        poligonos = new LinkedHashMap<String, String>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select concat(a.cod_poligono,'-',a.cod_sub_poligono) as cod_poligono from mst_poligonos a order by cast(a.cod_poligono as UNSIGNED)");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                poligonos.put(rset.getString("cod_poligono"), rset.getString("cod_poligono"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return poligonos;
    }
 
    public Map<String, String> getsubpoligonos(int poligono) throws Exception {
        subpoligonos = new HashMap<String, String>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_sub_poligono from mst_poligonos a");
            //ps.setInt(1, poligono);
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                subpoligonos.put(rset.getString("cod_sub_poligono"), rset.getString("cod_sub_poligono"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return subpoligonos;
    }
 
    public Map<String, String> getformas_pago() throws Exception {
        formas_pago = new HashMap<String, String>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.corr_forma_pago,a.desc_forma_pago from mst_formas_pago a");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                //formas_pago.put(rset.getString("corr_forma_pago"), rset.getString("corr_forma_pago"));
                formas_pago.put(rset.getString("desc_forma_pago"), rset.getString("desc_forma_pago"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return formas_pago;
    }
 
    public String get_mes(){
        String fecha="";
        int mes=0;
        int anio = 0;
        try{
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.mes_vigente,a.ano_vigente from mst_residencial a");
            ResultSet rset;
            rset = ps.executeQuery();
            while(rset.next()){
                mes = rset.getInt("mes_vigente");
                anio = rset.getInt("ano_vigente");
            }
        }catch(Exception ex){
            System.out.println("Error verificando mes: "+ex.getMessage());
        }
        switch(mes){
            case 0: fecha = "No seleccionada";
                    break;
            case 1: fecha = "Enero/"+anio;
                    break;
            case 2: fecha = "Febrero/"+anio;
                    break;
            case 3: fecha = "Marzo/"+anio;
                    break;
            case 4: fecha = "Abril/"+anio;
                    break;
            case 5: fecha = "Mayo/"+anio;
                    break;
            case 6: fecha = "Junio/"+anio;
                    break;
            case 7: fecha = "Julio/"+anio;
                    break;
            case 8: fecha = "Agosto/"+anio;
                    break;
            case 9: fecha = "Septiembre/"+anio;
                    break;
            case 10: fecha = "Octubre/"+anio;
                    break;
            case 11: fecha = "Noviembre/"+anio;
                    break;
            case 12: fecha = "Diciembre/"+anio;
                    break;
        }
        return fecha;
    }
    
    
    public int get_mes_int(){
        int mes = 0;
        try{
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.mes_vigente,a.ano_vigente from mst_residencial a");
            ResultSet rset;
            rset = ps.executeQuery();
            while(rset.next()){
                mes = rset.getInt("mes_vigente");
            }
        }catch(Exception ex){
            System.out.println("Error verificando mes: "+ex.getMessage());
        }
        return mes;
    }
    
    public int get_anio_int(){
        int anio=0;
        try{
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.mes_vigente,a.ano_vigente from mst_residencial a");
            ResultSet rset;
            rset = ps.executeQuery();
            while(rset.next()){
                anio = rset.getInt("ano_vigente");
            }
        }catch(Exception ex){
            System.out.println("Error verificando mes: "+ex.getMessage());
        }
        return anio;
    }

    public boolean verifica_residencia(int cod_poligono, String cod_sub_poligono, String cod_residencia) {
        boolean residencia_valida = false;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.saldo_residencia from mst_residencias a where a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
            ps.setInt(1, cod_poligono);
            ps.setString(2, cod_sub_poligono);
            ps.setString(3, cod_residencia);
            ResultSet rset1;
            System.out.println("Verificando residencia: select a.saldo_residencia from mst_residencias a where a.cod_poligono=" + cod_poligono + " and a.cod_sub_poligono=" + cod_sub_poligono + " and a.cod_residencia=" + cod_residencia);
            rset1 = ps.executeQuery();
            while (rset1.next()) {
                saldo_res = rset1.getFloat("saldo_residencia");
                residencia_valida = true;
            }
        } catch (Exception ex) {
            System.out.println("Error verificando residencia: " + ex.getMessage());
        }
        
        if (!residencia_valida){
            FacesMessage msg2 = new FacesMessage("Residencia invalida, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }
        
        return residencia_valida;
    }
 
    public boolean verifica_recibo(int corr_recibo) {
        boolean recibo_valido = false;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.estado_recibo from tbl_recibos_provi_defini a where a.num_recibo_prov=?");
            ps.setInt(1, corr_recibo);
            ResultSet rset2;
            System.out.println("Verificando recibo");
            rset2 = ps.executeQuery();
            while (rset2.next()) {
                if (!"P".equals(rset2.getString("estado_recibo"))) {
                    recibo_valido = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error verificando recibo: " + ex.getMessage());
        }
        
        if (!recibo_valido){
            FacesMessage msg2 = new FacesMessage("Recibo no valido, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }
 
        return recibo_valido;
    }
 
    public int ver_forma_pago(String forma_pago) {
        int id_forma_pago = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.corr_forma_pago from mst_formas_pago a where a.desc_forma_pago=?");
            ps.setString(1, forma_pago);
            ResultSet rset_fm;
            System.out.println("Verificando forma de pago");
            rset_fm = ps.executeQuery();
            while (rset_fm.next()) {
                id_forma_pago = rset_fm.getInt("corr_forma_pago");
            }
        } catch (Exception ex) {
            System.out.println("Error verificacndo forma de pago: " + ex.getMessage());
        }
        
        if (id_forma_pago == 0){
            FacesMessage msg2 = new FacesMessage("Forma de pago invalida, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }
        
        return id_forma_pago;
    }
 
    public boolean verifica_info(int corr_recibo,int forma_pago) {
        boolean recibo_valido = false;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.cod_banco,b.info_adicional from tbl_recibos_provi_defini a left outer join mst_formas_pago b ON b.corr_forma_pago = ? where a.num_recibo_prov=?");
            ps.setInt(1, forma_pago);
            ps.setInt(2, corr_recibo);
            ResultSet rset2;
            System.out.println("Verificando informacion");
            rset2 = ps.executeQuery();
            //System.out.print("Corre.: "+corr_recibo);
            while (rset2.next()) {
                if (rset2.getString("cod_banco") != null && !"N".equals(rset2.getString("info_adicional"))) {
                    recibo_valido = true;
                }
                if (rset2.getString("cod_banco") == null && !"S".equals(rset2.getString("info_adicional"))) {
                    recibo_valido = true;
                }
                //System.out.print("Banco: "+rset2.getString("cod_banco"));
                //System.out.print("Info add: "+rset2.getString("info_adicional"));
            }
        } catch (Exception ex) {
            System.out.println("Error verificando informacion: " + ex.getMessage());
        }
        
        if (!recibo_valido){
            FacesMessage msg2 = new FacesMessage("Informacion adicional invalida, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }

        return recibo_valido;
    }

    //Metodo para actualizar un pago
    public boolean update_pago(int corr_recibo, int cod_poligono, String cod_subpoligono, String cod_resedencia, float valor_recibo, String forma_pago,String ultima_fecha_abonada, String estado) throws Exception {
 
        boolean upd_pago = false;
        if (verifica_residencia(cod_poligono, cod_subpoligono, cod_resedencia)) {
            if (verifica_recibo(corr_recibo)) {
                if (verifica_info(corr_recibo,ver_forma_pago(forma_pago))) {
                try {
                    this.Conectar();
                    PreparedStatement ps = this.getCn().prepareStatement("update tbl_recibos_provi_defini a set "
                            + "a.cod_poligono=?, "
                            + "a.cod_sub_poligono=?, "
                            + "a.cod_residencia=?,"
                            + "a.valor_recibo=?,"
                            + "a.nuevo_saldo=?,"
                            + "saldo_actual=?,"
                            + "a.estado_recibo='A',"
                            + "a.corr_forma_pago=?,"
                            + "a.num_recibo_defini=substring(?,6),"
                            + "a.ultima_fecha_abonada=?,"
                            + "fecha_pago=now(),"
                            + "fecha_recibo_prov=?,"
                            + "a.cod_usuario_crea=?, tipo_recibo=1 "
                            + "where a.num_recibo_prov=? "
                            + "and a.estado_recibo<>'P'");
                    ps.setInt(1, cod_poligono);
                    ps.setString(2, cod_subpoligono);
                    ps.setString(3, cod_resedencia);
                    ps.setFloat(4, valor_recibo);
                    if (!estado.equals("A")){
                        ps.setFloat(5, saldo_res - valor_recibo);
                        ps.setFloat(6, saldo_res);
                    } 
                    ps.setInt(7, ver_forma_pago(forma_pago));
                    ps.setInt(8, corr_recibo);
                    ps.setString(9, ultima_fecha_abonada);
                    ps.setString(10, ultima_fecha_abonada);
                    ps.setString(11, context2.getExternalContext().getSessionMap().get("user_cod").toString());
                    ps.setInt(12, corr_recibo);
                    ps.executeUpdate();
                    upd_pago = true;
                } catch (Exception e) {
                    upd_pago = false;
                    System.out.println("Error actualizando pago dao: " + e.getMessage());
                    throw e;
                } finally {
                    this.Cerrar();
                }
                
                if (upd_pago && !estado.equals("A")){
                    try {
                        this.Conectar();
                        PreparedStatement ps3 = this.getCn().prepareStatement("update mst_residencias a set "
                                + "a.saldo_residencia=?,"
                                + "a.fecha_ult_abono=now(),"
                                + "a.ultimo_mes_abonado=?,"
                                + "a.ultimo_ano_abonado=? "
                                + "where a.cod_poligono=? "
                                + "and a.cod_sub_poligono=? "
                                + "and a.cod_residencia=?");
                        ps3.setFloat(1, saldo_res - valor_recibo);
                        ps3.setInt(2, get_mes_int());
                        ps3.setInt(3, get_anio_int());
                        ps3.setInt(4, cod_poligono);
                        ps3.setString(5, cod_subpoligono);
                        ps3.setString(6, cod_resedencia);
                        ps3.executeUpdate();
                    } catch (Exception e) {
                        upd_pago = false;
                        System.out.println("Error actualizando saldo en residencia: " + e.getMessage());
                        throw e;
                    } finally {
                        this.Cerrar();
                    }
                }
            } else {
                    FacesMessage msg2 = new FacesMessage("Necesita informacion adicional, no se puede procesar", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg2);
                upd_pago = false;
            }
        } else {
            upd_pago = false;
        }
        } else {
            upd_pago = false;
        }
        return upd_pago;
    }
 
    public float get_total_dia() {
        float total_dia = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select ifnull(sum(a.valor_recibo),0) as total_dia from tbl_recibos_provi_defini a where a.fecha_pago=DATE_FORMAT(now(),'%y-%m-%d') and tipo_recibo = 1");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                total_dia = rset.getFloat("total_dia");
            }
        } catch (Exception ex) {
            System.out.println("Error calculando total del dia: " + ex.getMessage());
        }
        return total_dia;
    }
 
    public void aprobar_dia() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("update tbl_recibos_provi_defini a set a.estado_recibo='P' where a.estado_recibo='A' and a.fecha_pago=DATE_FORMAT(now(),'%y-%m-%d')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error aprobando recibos: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
        /*
        try {
            this.Conectar();
            PreparedStatement ps2 = this.getCn().prepareStatement("select c.cod_poligono,c.cod_sub_poligono,c.cod_residencia,c.nuevo_saldo,d.cod_cta_conta_cxc,d.cod_cta_conta_cxp from  tbl_recibos_provi_defini c, mst_residencias d where c.cod_poligono=d.cod_poligono and c.cod_sub_poligono=d.cod_sub_poligono and c.cod_residencia=d.cod_residencia and DATE_FORMAT(now(),'%y-%m-%d')");
            ResultSet rset_p;
            rset_p = ps2.executeQuery();
            while (rset_p.next()) {
                PreparedStatement ps3 = this.getCn().prepareStatement("update mst_residencias a set a.saldo_residencia=?,a.fecha_ult_abono=now(),a.ultimo_mes_abonado=?,a.ultimo_ano_abonado=? where a.cod_poligono=? and a.cod_sub_poligono=? and a.cod_residencia=?");
                ps3.setFloat(1, rset_p.getFloat("nuevo_saldo"));
                ps3.setInt(2, Integer.parseInt(get_mes_int()));
                ps3.setInt(3, get_anio_int());
                ps3.setInt(4, rset_p.getInt("cod_poligono"));
                ps3.setString(5, rset_p.getString("cod_sub_poligono"));
                ps3.setString(6, rset_p.getString("cod_residencia"));
                ps3.executeUpdate();
                if (rset_p.getFloat("nuevo_saldo") > 0) {
 
                }
                if (rset_p.getFloat("nuevo_saldo") < 0) {
 
                }
                if (rset_p.getFloat("nuevo_saldo") == 0) {
 
                }
            }
        } catch (Exception e) {
            System.out.println("Error actualizando saldo en residencia: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
        */
    }
    
    public void cancelar_recibo(int num_recibo_prov) throws Exception {
        
        try {
            this.Conectar();
            PreparedStatement ps2 = this.getCn().prepareStatement("select cod_poligono, cod_sub_poligono, cod_residencia, valor_recibo from tbl_recibos_provi_defini where num_recibo_prov =?");
            ps2.setInt(1, num_recibo_prov);
            ResultSet rset_p;
            rset_p = ps2.executeQuery();
            while (rset_p.next()) {
                if (verifica_residencia(rset_p.getInt("cod_poligono"), rset_p.getString("cod_sub_poligono"), rset_p.getString("cod_residencia"))) {
                    this.Conectar();
                    PreparedStatement ps3 = this.getCn().prepareStatement("update mst_residencias a set "
                            + "a.saldo_residencia=? "
                            + "where a.cod_poligono=? "
                            + "and a.cod_sub_poligono=? "
                            + "and a.cod_residencia=?");
                    ps3.setFloat(1, saldo_res + rset_p.getFloat("valor_recibo"));
                    ps3.setInt(2, rset_p.getInt("cod_poligono"));
                    ps3.setString(3, rset_p.getString("cod_sub_poligono"));
                    ps3.setString(4, rset_p.getString("cod_residencia"));
                    ps3.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error actualizando saldo en residencia: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
        
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("delete from tbl_recibos_provi_defini where (estado_recibo='A' or estado_recibo='I') and num_recibo_prov =?");
            ps.setInt(1, num_recibo_prov);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error cancelandos recibos: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
 
    public void cancelar_dia() throws Exception {
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("delete from tbl_recibos_provi_defini where estado_recibo='A' or estado_recibo='I'");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error cancelandos recibos: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
 
    public void info_add(pagos_infoadd pagos_infoadd) throws Exception {
        if (verifica_recibo(pagos_infoadd.getNum_recibo_prov())) {
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("update tbl_recibos_provi_defini a set a.cod_banco=? , a.num_cheque_docto=?, a.a_nombre_de=?,a.observacion=? where a.num_recibo_prov=?");
                ps.setInt(1, pagos_infoadd.getCod_banco());
                ps.setString(2, pagos_infoadd.getNum_cheque_docto());
                ps.setString(3, pagos_infoadd.getA_nombre_de());
                ps.setString(4, pagos_infoadd.getObservacion());
                ps.setInt(5, pagos_infoadd.getNum_recibo_prov());
                ps.executeUpdate();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion agregada", " "));
            } catch (Exception e) {
                System.out.println("Error cancelandos recibos: " + e.getMessage());
                throw e;
            } finally {
                this.Cerrar();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Recibo ya esta procesado", " "));
        }
    }
 
}