
package modeladoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import uml.TGastoCC;
import utilidades.MySQL;

/**
 *
 * @author Nieto Mendoza
 */
public class TGastoCCDAO implements CRUDInteface {
    /*Conversor de fecha */
    MySQL fechaOut = new MySQL();
    BDConexion bd = new BDConexion();
    Logger log = Logger.getLogger(TGastoCCDAO.class.getName());

    public TGastoCCDAO() {
    }

    @Override
    public String create(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "insert into tbl_cajachica_gasto values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gastocaja.getCod_residencial());
            pst.setString(2, gastocaja.getNum_liquidacion());
            pst.setDate(3, gastocaja.getFecha_liquidacion());
            pst.setInt(4, gastocaja.getCorr_gasto());
            pst.setString(5, gastocaja.getNum_docto());
            pst.setDate(6, gastocaja.getFecha_docto());
            pst.setDouble(7, gastocaja.getValor_docto());
            pst.setString(8, gastocaja.getCod_cta_conta());
            pst.setString(9, gastocaja.getDesc_gasto());
            pst.setString(10, gastocaja.getEstado());
            pst.setString(11, gastocaja.getCod_usuario());
            pst.setDate(12, gastocaja.getFecha_ingreso());

            int registro = pst.executeUpdate();
            sqlresp ="Registros agregados: "+registro;
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public List<TGastoCC> read() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto order by num_liquidacion";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TGastoCC(
                        rs.getInt("cod_residencial"),
                        rs.getString("num_liquidacion"),
                        rs.getDate("fecha_liquidacion"),
                        rs.getInt("corr_gasto"),
                        rs.getString("num_docto"),
                        rs.getDate("fecha_docto"),
                        rs.getDouble("valor_docto"),
                        rs.getString("cod_cta_conta"),
                        rs.getString("estado"),
                        rs.getString("desc_gasto"),
                        rs.getString("cod_usuario"),
                        rs.getDate("fecha_ingreso")));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
//Se debe tener cuidado de qué datos se modificaran en la liquidacion, si no se aplicara el cambio a todos los movimientos
    @Override
    public String update(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update tbl_cajachica_gasto set fecha_liquidacion=?, corr_gasto=?, num_docto=?, fecha_docto=?, valor_docto=?, cod_cta_conta=?, desc_gasto=?, estado=?, cod_usuario=?, fecha_ingreso=? where cod_residencial=? and num_liquidacion=? ";
            pst = cn.prepareStatement(sql);
            pst.setDate(1, gastocaja.getFecha_liquidacion());
            pst.setInt(2, gastocaja.getCorr_gasto());
            pst.setString(3, gastocaja.getNum_docto());
            pst.setDate(4, gastocaja.getFecha_docto());
            pst.setDouble(5, gastocaja.getValor_docto());
            pst.setString(6, gastocaja.getCod_cta_conta());
            pst.setString(7, gastocaja.getDesc_gasto());
            pst.setString(8, gastocaja.getCod_usuario());
            pst.setDate(9, gastocaja.getFecha_ingreso());
            pst.setInt(10, gastocaja.getCod_residencial());
            pst.setString(11, gastocaja.getNum_liquidacion());
            int registro = pst.executeUpdate();
            sqlresp ="Registros modificados: "+registro;
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public String delete(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from tbl_cajachica_gasto where cod_residencial=? and num_liquidacion=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gastocaja.getCod_residencial());
            pst.setString(2, gastocaja.getNum_liquidacion());
            int registro = pst.executeUpdate();
            sqlresp ="Registros eliminados: "+registro;
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

     /* FUNCIONES Y PROCESOS ESPECIFICOS FUERA DEL CRUD DE LA TABLA GASTOS DE CAJA CHICA*/
    
    @Override
    public List<TGastoCC> search(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        TGastoCC gastocaja = (TGastoCC)obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where num_liquidacion like concat('%',?, '%')";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_liquidacion());
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TGastoCC(
                        rs.getInt("cod_residencial"),
                        rs.getString("num_liquidacion"),
                        rs.getDate("fecha_liquidacion"),
                        rs.getInt("corr_gasto"),
                        rs.getString("num_docto"),
                        rs.getDate("fecha_docto"),
                        rs.getDouble("valor_docto"),
                        rs.getString("cod_cta_conta"),
                        rs.getString("desc_gasto"),
                        rs.getString("estado"),
                        rs.getString("cod_usuario"),
                        rs.getDate("fecha_ingreso")));
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
    
    /*Verificacion de datos en la tabla con almacenamiento fijo */
    
    public String comprobarLiquidacionNueva(Object obj){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp=null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where `num_liquidacion`=? and estado='P' or estado='C'";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_liquidacion());
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("num_liquidacion")!=null) {
                sqlresp = "Liquidación número: "+rs.getString("num_liquidacion") + " fue procesada el " + fechaOut.dmaSalida(rs.getDate("fecha_ingreso").toString());
            
            }
            rs.close(); pst.close();
            
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    /*Verifica que el numero de liquidacion sea congruente al que se esta trantado como nueva liquidacion*/
    public String comprobarLiquidacion(Object obj){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp=null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver()); //PENDIENTE DE VERIFICAR SU FUNCION
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select num_liquidacion from tbl_cajachica_gasto where `num_liquidacion`!=? and estado='N'"; 
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_liquidacion());
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("num_liquidacion")!=null) {
                sqlresp = "Liquidación número "+rs.getString("num_liquidacion") + " todavía no ha sido guardada.";
            }
            rs.close(); pst.close();
            
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public String comprobarDocumento(Object obj){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where `num_docto`=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_docto());
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("num_docto")!=null) {
                sqlresp = "El documento: "+rs.getString("num_docto")+" fue registrado en la liquidación "+rs.getString("num_liquidacion") + " el " + fechaOut.dmaSalida(rs.getDate("fecha_ingreso").toString());
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
      
    public List<TGastoCC> cargarMovimientos() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where estado='N' ";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TGastoCC(
                        rs.getInt("cod_residencial"),
                        rs.getString("num_liquidacion"),
                        rs.getDate("fecha_liquidacion"),
                        rs.getInt("corr_gasto"),
                        rs.getString("num_docto"),
                        rs.getDate("fecha_docto"),
                        rs.getDouble("valor_docto"),
                        rs.getString("cod_cta_conta"),
                        rs.getString("desc_gasto"),
                        rs.getString("estado"),
                        rs.getString("cod_usuario"),
                        rs.getDate("fecha_ingreso")));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }

    public List<TGastoCC> totalMovimientos() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select sum(`valor_docto`) as total,  100 - sum(`valor_docto`) as efectivo from tbl_cajachica_gasto where estado='N' ";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TGastoCC(rs.getDouble("total"), rs.getDouble("efectivo")));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
    public double efectivoActualCajaChica (){ //VERIDIFICAR SU FUNCION
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        double sqlresp = 0;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select sum(valor_docto) as ValorCheque from tbl_cajachica_cheques where estado='P'";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.first();
            if (rs.getDouble("valorCheque")!=0) {
                sqlresp = rs.getDouble("valorCheque");
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    //Comprobar liquidacion pendientes de liquidar con cheque
    public String comprobarLiquidacionPendiente(){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp = null;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where estado='P'";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("estado")!=null) {
                sqlresp = "Aún no ha agregado el cheque para la liquidación "+rs.getString("num_liquidacion")+".";
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public String grabarLiquidacion(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update tbl_cajachica_gasto set estado='P' where num_liquidacion=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_liquidacion());
            int registro = pst.executeUpdate();
            if (registro!=0) {
                sqlresp ="Liquidación ha sido guardada.";
            }else{
                sqlresp="No se guardó liquidación.";
            }
            
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    public List<TGastoCC> cargarMovimiento(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        TGastoCC gastocaja = (TGastoCC)obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from tbl_cajachica_gasto where num_docto=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_docto());
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TGastoCC(
                        rs.getInt("cod_residencial"),
                        rs.getString("num_liquidacion"),
                        rs.getDate("fecha_liquidacion"),
                        rs.getInt("corr_gasto"),
                        rs.getString("num_docto"),
                        rs.getDate("fecha_docto"),
                        rs.getDouble("valor_docto"),
                        rs.getString("cod_cta_conta"),
                        rs.getString("desc_gasto"),
                        rs.getString("estado"),
                        rs.getString("cod_usuario"),
                        rs.getDate("fecha_ingreso")));
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
    
    public String modificarMovimiento(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update tbl_cajachica_gasto set cod_residencial=?, fecha_liquidacion=?, corr_gasto=?, fecha_docto=?, valor_docto=?, cod_cta_conta=?, desc_gasto=?,  estado=?, cod_usuario=?, fecha_ingreso=? where num_liquidacion=? and num_docto=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gastocaja.getCod_residencial());
            pst.setDate(2, gastocaja.getFecha_liquidacion());
            pst.setInt(3, gastocaja.getCorr_gasto());
            pst.setDate(4, gastocaja.getFecha_docto());
            pst.setDouble(5, gastocaja.getValor_docto());
            pst.setString(6, gastocaja.getCod_cta_conta());
            pst.setString(7, gastocaja.getDesc_gasto());
            pst.setString(8, gastocaja.getEstado());
            pst.setString(9, gastocaja.getCod_usuario());
            pst.setDate(10, gastocaja.getFecha_ingreso());
            pst.setString(11, gastocaja.getNum_liquidacion());
            pst.setString(12, gastocaja.getNum_docto());
            int registro = pst.executeUpdate();
            sqlresp ="Movimientos modificados: "+registro;
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public String borrarMovimiento(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from tbl_cajachica_gasto where num_docto=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_docto());
            int registro = pst.executeUpdate();
            sqlresp ="Movimientos eliminados: "+registro;
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public List<TGastoCC> datosChequeLiquidacionPendiente(){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TGastoCC> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select num_liquidacion,fecha_liquidacion from tbl_cajachica_gasto where estado='P'";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                lst.add(new TGastoCC(rs.getString("num_liquidacion"), rs.getDate("fecha_liquidacion")));
            }
            
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
    
    public String cambiarEstadoLiquidacion(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TGastoCC gastocaja = (TGastoCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update tbl_cajachica_gasto set estado='C' where num_liquidacion=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gastocaja.getNum_liquidacion());
            int registro = pst.executeUpdate();
            if (registro!=0) {
                sqlresp ="Liquidación pendiente de cheque actualizada. ";
            }else{
                sqlresp="No se guardó liquidación.";
            }
            pst.close();
            cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
}
