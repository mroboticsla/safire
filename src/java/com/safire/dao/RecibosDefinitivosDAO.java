package com.safire.dao;

/**
 *
 * @author Mauricio Montoya
 */
import com.safire.model.Bancos;
import com.safire.model.OtroIngreso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.context.FacesContext;

import com.safire.model.ReciboDefinitivo;
import com.safire.model.Transaccion;
import com.safire.model.pagos_infoadd;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

public class RecibosDefinitivosDAO extends DAO {

    public float saldo_res;
    FacesContext local_context = FacesContext.getCurrentInstance();

    public ReciboDefinitivo getRecibo(int recibo_prov) throws Exception {
        ReciboDefinitivo r = new ReciboDefinitivo();
        boolean found = false;
        try {
            this.Conectar();
            String sql = "select b.cod_residencial,b.num_recibo_prov, b.cod_poligono,b.cod_sub_poligono,b.cod_residencia,";
            sql = sql + " ifnull(concat(c.nombre_propietario,' ',c.apellido_propietario),'-') as propietario,b.valor_recibo, ";
            sql = sql + " b.saldo_actual,b.nuevo_saldo,ultima_fecha_abonada, ifnull(a.desc_forma_pago,'') as forma_pago, b.estado_recibo ";
            sql = sql + " from tbl_recibos_provi_defini b left outer join mst_propietarios c ON b.cod_poligono=c.cod_poligono and b.cod_sub_poligono=c.cod_sub_poligono and b.cod_residencia=c.cod_residencia left outer join mst_formas_pago a ON a.corr_forma_pago=b.corr_forma_pago";
            sql = sql + " and b.cod_residencia=c.cod_residencia where b.num_recibo_prov=?";
            //System.out.println("Select**** " + sql);
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setInt(1, recibo_prov);
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {

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
                found = true;
            }
            rset.close();
        } catch (Exception ex) {
            System.out.println("Error cargando recibos provicionales" + ex.getMessage());
        } finally {
            this.Cerrar();
        }

        if (found) {
            return r;
        } else {
            return null;
        }
    }

    public List<Bancos> getbancos() throws Exception {
        List<Bancos> bancos = new ArrayList<>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select corr_banco, nombre_banco from mst_bancos");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                Bancos b = new Bancos();
                b.setCorr_banco(rset.getInt(1));
                b.setNombre(rset.getString(2));
                bancos.add(b);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return bancos;
    }

    public Transaccion getTransaccion(int code) throws Exception {
        Transaccion _result = new Transaccion();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT 	cod_transaccion, \n"
                    + "	cod_residencial, \n"
                    + "	corr_forma_pago, \n"
                    + "	desc_transaccion, \n"
                    + "	concepto_transaccion, \n"
                    + "	tipo_ingreso, \n"
                    + "	tipo_egreso, \n"
                    + "	cod_cta_contab_debe, \n"
                    + "	cod_cta_contab_haber,\n"
                    + "	activo\n"
                    + "	 \n"
                    + "	FROM \n"
                    + "	mst_transacciones where activo = 'S' and cod_transaccion=" + code);
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                Transaccion b = new Transaccion();
                b.setCod_transaccion(rset.getInt(1));
                b.setCod_residencial(rset.getInt(2));
                b.setCorr_forma_pago(rset.getInt(3));
                b.setDesc_transaccion(rset.getString(4));
                b.setConcepto_transaccion(rset.getString(5));
                b.setTipo_ingreso(rset.getInt(6));
                b.setTipo_egreso(rset.getInt(7));
                b.setCod_cta_contab_debe(rset.getString(8));
                b.setCod_cta_contab_haber(rset.getString(9));
                b.setActivo(rset.getString(10));
                _result = b;
            }
        } catch (Exception ex) {
            System.out.println("No se puede obtener la lista de transacciones: " + ex.getMessage());
        } finally {
            this.Cerrar();
        }
        return _result;
    }

    public List<Transaccion> getTransacciones(String forma_pago) throws Exception {
        List<Transaccion> _result = new ArrayList<>();
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT 	cod_transaccion, \n"
                    + "	cod_residencial, \n"
                    + "	corr_forma_pago, \n"
                    + "	desc_transaccion, \n"
                    + "	concepto_transaccion, \n"
                    + "	tipo_ingreso, \n"
                    + "	tipo_egreso, \n"
                    + "	cod_cta_contab_debe, \n"
                    + "	cod_cta_contab_haber,\n"
                    + "	activo\n"
                    + "	 \n"
                    + "	FROM \n"
                    + "	mst_transacciones where activo = 'S' and corr_forma_pago = ?");
            ps.setInt(1, ver_forma_pago(forma_pago));
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                Transaccion b = new Transaccion();
                b.setCod_transaccion(rset.getInt(1));
                b.setCod_residencial(rset.getInt(2));
                b.setCorr_forma_pago(rset.getInt(3));
                b.setDesc_transaccion(rset.getString(4));
                b.setConcepto_transaccion(rset.getString(5));
                b.setTipo_ingreso(rset.getInt(6));
                b.setTipo_egreso(rset.getInt(7));
                b.setCod_cta_contab_debe(rset.getString(8));
                b.setCod_cta_contab_haber(rset.getString(9));
                b.setActivo(rset.getString(10));
                _result.add(b);
            }
        } catch (Exception ex) {
            System.out.println("No se puede obtener la lista de transacciones: " + ex.getMessage());
        } finally {
            this.Cerrar();
        }
        return _result;
    }

    public float saldo_actual(int cod_poligono, String cod_subpoligono, String cod_resedencia) throws Exception {
        float _result = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select saldo_residencia from mst_residencias where cod_poligono = " + cod_poligono + "\n"
                    + "and cod_sub_poligono = '" + cod_subpoligono + "'\n"
                    + "and cod_residencia = " + cod_resedencia + "");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getFloat(1);
            }
        } catch (Exception e) {
            System.out.println("No se puede obtener el saldo de la residencia: " + e.getMessage());
        } finally {
            this.Cerrar();
        }
        return _result;
    }

    public String nombre_propietario(int cod_poligono, String cod_subpoligono, String cod_resedencia) throws Exception {
        String _result = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select CONCAT(nombre_propietario, ' ', apellido_propietario) propietario from mst_propietarios where cod_poligono = " + cod_poligono + "\n"
                    + "and cod_sub_poligono = '" + cod_subpoligono + "'\n"
                    + "and cod_residencia = " + cod_resedencia + "");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getString(1);
            }
        } catch (Exception e) {
            System.out.println("No se puede obtener el nombre del propietario: " + e.getMessage());
        } finally {
            this.Cerrar();
        }
        return _result;
    }

    public String nombre_residente(int cod_poligono, String cod_subpoligono, String cod_resedencia) throws Exception {
        String _result = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select CONCAT(nombre_residente, ' ', apellido_residente) propietario from mst_propietarios_residentes where cod_poligono = " + cod_poligono + "\n"
                    + "and cod_sub_poligono = '" + cod_subpoligono + "'\n"
                    + "and cod_residencia = " + cod_resedencia + "");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getString(1);
            }
        } catch (Exception e) {
            System.out.println("No se puede obtener el nombre del residente: " + e.getMessage());
        } finally {
            this.Cerrar();
        }
        return _result;
    }

    public int update_pago(int cod_poligono, String cod_subpoligono, String cod_resedencia, float otros_ingresos, float valor_recibo, float total_recibo, String forma_pago, pagos_infoadd pagos_infoadd, List<OtroIngreso> list_otros, String concepto) throws Exception {

        int corr_recibo = -1;
        boolean upd_pago = false;

        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT IFNULL(MAX(num_recibo_prov), 0) + 1 FROM TBL_RECIBOS_PROVI_DEFINI WHERE tipo_recibo = 2");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                corr_recibo = rset.getInt(1);
            }
            upd_pago = true;
        } catch (Exception e) {
            upd_pago = false;
            System.out.println("Error actualizando pago dao: " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }

        if (corr_recibo > -1) {
            try {
                this.Conectar();
                PreparedStatement ps = this.getCn().prepareStatement("insert into tbl_recibos_provi_defini(cod_residencial,num_recibo_prov,fecha_recibo_prov,valor_recibo,estado_recibo, concepto) values(?,?,NOW(),?,'I', ?)");
                ps.setInt(1, Integer.parseInt(local_context.getExternalContext().getSessionMap().get("residencial").toString()));
                ps.setInt(2, corr_recibo);
                ps.setFloat(3, valor_recibo);
                ps.setString(4, concepto);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error insertando recibos provicionales: " + e.getMessage());
            } finally {
                this.Cerrar();
            }

            if (ver_info_forma_pago(forma_pago)) {
                try {
                    this.Conectar();
                    PreparedStatement ps = this.getCn().prepareStatement("update tbl_recibos_provi_defini a set a.cod_banco=? , a.num_cheque_docto=?, a.a_nombre_de=?,a.observacion=? where a.num_recibo_prov=?");
                    ps.setInt(1, pagos_infoadd.getCod_banco());
                    ps.setString(2, pagos_infoadd.getNum_cheque_docto());
                    ps.setString(3, pagos_infoadd.getA_nombre_de());
                    ps.setString(4, pagos_infoadd.getObservacion());
                    ps.setInt(5, corr_recibo);
                    ps.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Error insertando recibos provicionales: " + e.getMessage());
                } finally {
                    this.Cerrar();
                }
            }
        }

        if (verifica_residencia(cod_poligono, cod_subpoligono, cod_resedencia)) {
            if (verifica_recibo(corr_recibo)) {
                if (verifica_info(corr_recibo, ver_forma_pago(forma_pago))) {
                    try {
                        this.Conectar();
                        PreparedStatement ps = this.getCn().prepareStatement("update tbl_recibos_provi_defini a set "
                                + "a.cod_poligono=?, "
                                + "a.cod_sub_poligono=?, "
                                + "a.cod_residencia=?,"
                                + "a.valor_recibo=?,"
                                + "a.nuevo_saldo=?,"
                                + "a.otros_ingresos=?,"
                                + "saldo_actual=?,"
                                + "a.estado_recibo='P',"
                                + "a.corr_forma_pago=?,"
                                + "a.num_recibo_defini=?,"
                                + "a.ultima_fecha_abonada=NOW(),"
                                + "fecha_pago=now(),"
                                + "fecha_recibo_prov=NOW(),"
                                + "a.cod_usuario_crea=?, tipo_recibo=2 "
                                + "where a.num_recibo_prov=? "
                                + "and a.estado_recibo<>'P'");
                        ps.setInt(1, cod_poligono);
                        ps.setString(2, cod_subpoligono);
                        ps.setString(3, cod_resedencia);
                        ps.setFloat(4, total_recibo);
                        ps.setFloat(5, saldo_res - valor_recibo);

                        ps.setFloat(6, otros_ingresos);
                        ps.setFloat(7, saldo_res);

                        ps.setInt(8, ver_forma_pago(forma_pago));
                        ps.setInt(9, corr_recibo);
                        ps.setString(10, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
                        ps.setInt(11, corr_recibo);
                        ps.executeUpdate();
                        upd_pago = true;
                    } catch (Exception e) {
                        upd_pago = false;
                        System.out.println("Error actualizando pago dao: " + e.getMessage());
                        throw e;
                    } finally {
                        this.Cerrar();
                    }

                    if (upd_pago) {
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
                            ps3.setInt(2, Integer.parseInt(get_mes_int()));
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

                        try {
                            this.Conectar();
                            PreparedStatement ps3 = this.getCn().prepareStatement("INSERT INTO tbl_otros_ingresos \n"
                                    + "	(cod_residencial, \n"
                                    + "	num_recibo, \n"
                                    + "	corr_forma_pago, \n"
                                    + "	cod_transaccion, \n"
                                    + "	cod_cta_contab_debe, \n"
                                    + "	cod_cta_contab_haber, \n"
                                    + "	monto, \n"
                                    + "	cod_usuario, \n"
                                    + "	fecha_creacion\n"
                                    + "	)\n"
                                    + "	VALUES\n"
                                    + "	(?,?,?,?,?,?,?,?,NOW())");

                            for (OtroIngreso o : list_otros) {
                                ps3.setFloat(1, o.getTransaccion().getCod_residencial());
                                ps3.setInt(2, corr_recibo);
                                ps3.setInt(3, o.getTransaccion().getCorr_forma_pago());
                                ps3.setInt(4, o.getTransaccion().getCod_transaccion());
                                ps3.setString(5, o.getTransaccion().getCod_cta_contab_debe());
                                ps3.setString(6, o.getTransaccion().getCod_cta_contab_haber());
                                ps3.setFloat(7, o.getValor());
                                ps3.setString(8, local_context.getExternalContext().getSessionMap().get("user_cod").toString());
                                ps3.executeUpdate();
                            }
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
        return corr_recibo;
    }

    public String get_mes_int() {
        String mes = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.mes_vigente,a.ano_vigente from mst_residencial a");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                mes = rset.getString("mes_vigente");
            }
        } catch (Exception ex) {
            System.out.println("Error verificando mes: " + ex.getMessage());
        }
        return mes;
    }

    public int get_anio_int() {
        int anio = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.mes_vigente,a.ano_vigente from mst_residencial a");
            ResultSet rset;
            rset = ps.executeQuery();
            while (rset.next()) {
                anio = rset.getInt("ano_vigente");
            }
        } catch (Exception ex) {
            System.out.println("Error verificando mes: " + ex.getMessage());
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

        if (!residencia_valida) {
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

        if (!recibo_valido) {
            FacesMessage msg2 = new FacesMessage("Recibo no valido, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }

        return recibo_valido;
    }

    public boolean ver_info_forma_pago(String forma_pago) {
        String id_forma_pago = "";
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("select a.info_adicional from mst_formas_pago a where a.desc_forma_pago=?");
            ps.setString(1, forma_pago);
            ResultSet rset_fm;
            System.out.println("Verificando si forma de pago requiere informacion adicioanl");
            rset_fm = ps.executeQuery();
            while (rset_fm.next()) {
                id_forma_pago = rset_fm.getString("info_adicional");
            }
        } catch (Exception ex) {
            System.out.println("Error verificacndo forma de pago: " + ex.getMessage());
        }

        return id_forma_pago.equals("S");
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

        if (id_forma_pago == 0) {
            FacesMessage msg2 = new FacesMessage("Forma de pago invalida, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }

        return id_forma_pago;
    }

    public boolean verifica_info(int corr_recibo, int forma_pago) {
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

        if (!recibo_valido) {
            FacesMessage msg2 = new FacesMessage("Informacion adicional invalida, no se puede procesar", "");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        }

        return recibo_valido;
    }
}
