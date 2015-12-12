
package modeladoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import uml.MstGasto;

/**
 *
 * @author Nieto Mendoza
 */
public class MstGastoDAO implements CRUDInteface{
    BDConexion bd = new BDConexion();
    Logger log = Logger.getLogger(MstGastoDAO.class.getName());
    public MstGastoDAO() {
    }

    @Override
    public String create(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        MstGasto gasto = (MstGasto) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "insert into mst_tipo_gastos values (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gasto.getCod_residencial());
            pst.setInt(2, gasto.getCorr_gasto());
            pst.setString(3, gasto.getDesc_gasto() );
            pst.setString(4, gasto.getCod_cta_conta() );
            pst.setDouble(5, gasto.getValor_gasto() );
            pst.setDate(6, gasto.getFecha_creacion());
            pst.setString(7, gasto.getCod_usuario());
            pst.setString(8, gasto.getActivo());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido agregado.";
            pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public List<MstGasto> read() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstGasto> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select * from mst_tipo_gastos order by corr_gasto";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstGasto(
                        rs.getInt("cod_residencial"),
                        rs.getInt("corr_gasto"),
                        rs.getString("desc_gasto"),
                        rs.getString("cod_cta_conta"),
                        rs.getDouble("valor_gasto"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("cod_usuario"),
                        rs.getString("activo")));
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }

    @Override
    public String update(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        MstGasto gasto = (MstGasto) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update mst_tipo_gastos set desc_gasto=?, cod_cta_conta=?, valor_gasto=?, fecha_creacion=? cod_usuario=? activo=? where cod_residencial=? and corr_gasto=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, gasto.getDesc_gasto());
            pst.setString(2, gasto.getCod_cta_conta());
            pst.setDouble(3, gasto.getValor_gasto());
            pst.setDate(4, gasto.getFecha_creacion());
            pst.setString(5, gasto.getCod_usuario());
            pst.setString(6, gasto.getActivo());
            pst.setInt(7, gasto.getCod_residencial());
            pst.setInt(8, gasto.getCorr_gasto());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido modificado.";
            pst.close(); cn.close();
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
        MstGasto gasto = (MstGasto) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from mst_tipo_gastos where cod_residencial=? and corr_gasto=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gasto.getCod_residencial());
            pst.setInt(2, gasto.getCorr_gasto());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido eliminado.";
            pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public List<MstGasto> search(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstGasto> lst = new ArrayList();
        MstGasto gasto = (MstGasto)obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from mst_tipo_gastos where corr_gasto=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, gasto.getCorr_gasto());
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstGasto(
                        rs.getInt("cod_residencial"),
                        rs.getInt("corr_gasto"),
                        rs.getString("desc_gasto"),
                        rs.getString("cod_cta_conta"),
                        rs.getDouble("valor_gasto"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("cod_usuario"),
                        rs.getString("activo")));
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return lst;
    }
}
