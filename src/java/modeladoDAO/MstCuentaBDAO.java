package modeladoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import uml.MstBanco;
import uml.MstCuenta;
import uml.TCCuenta;
import utilidades.MySQL;

/**
 *
 * @author Nieto Mendoza
 */
public class MstCuentaBDAO implements CRUDInteface {

    BDConexion bd = new BDConexion();
    Logger log = Logger.getLogger(MstCuentaBDAO.class.getName());
    MySQL fecha = new MySQL();
    public MstCuentaBDAO() {
    }

    @Override
    public String create(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        MstCuenta cuenta = (MstCuenta) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "insert into mst_ctas_bancarias values (?, ?, ?, ?, ?, ?, ?)";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, cuenta.getCorr_banco().getCorr_banco());
            pst.setString(2, cuenta.getNum_cta_banco());
            pst.setString(3, cuenta.getCod_cta_conta());
            pst.setDouble(4, cuenta.getSaldo_cta());
            pst.setDate(5, cuenta.getFecha_creacion());
            pst.setString(6, cuenta.getCod_usuario());
            pst.setString(7, cuenta.getActivo());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido agregado.";
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
    public List<MstCuenta> read() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstCuenta> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from mst_ctas_bancarias order by corr_banco";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstCuenta(
                        new MstBanco (rs.getInt("corr_banco")),
                        rs.getString("num_cta_banco"),
                        rs.getString("cod_cta_conta"),
                        rs.getDouble("saldo_cta"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("cod_usuario"),
                        rs.getString("activo")));
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

    @Override
    public String update(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        MstCuenta cuenta = (MstCuenta) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update mst_ctas_bancarias set  num_cta_banco=?, cod_cta_conta=?, saldo_cta=?, fecha_creacion=?, cod_usuario=?, activo=? where corr_banco=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, cuenta.getNum_cta_banco());
            pst.setString(2, cuenta.getCod_cta_conta());
            pst.setDouble(3, cuenta.getSaldo_cta());
            pst.setDate(4, cuenta.getFecha_creacion());
            pst.setString(5, cuenta.getCod_usuario());
            pst.setString(6, cuenta.getActivo());
            pst.setInt(7, cuenta.getCorr_banco().getCorr_banco());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido modificado.";
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
        MstCuenta cuenta = (MstCuenta) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from mst_ctas_bancarias where corr_banco=? and num_cta_banco=? ";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, cuenta.getCorr_banco().getCorr_banco());
            pst.setString(2, cuenta.getNum_cta_banco());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido eliminado.";
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
    public List<MstCuenta> search(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstCuenta> lst = new ArrayList();
        MstCuenta cuenta = (MstCuenta) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from mst_ctas_bancarias where num_cta_banco like concat('%',?, '%')";
            pst = cn.prepareStatement(sql);
            pst.setString(1, cuenta.getNum_cta_banco());
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstCuenta(
                        new MstBanco (rs.getInt("corr_banco")),
                        rs.getString("num_cta_banco"),
                        rs.getString("cod_cta_conta"),
                        rs.getDouble("saldo_cta"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("cod_usuario"),
                        rs.getString("activo")));
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
    
    public List<MstCuenta> cargarBancoCuenta() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstCuenta> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select cbancos.corr_banco, bancos.nombre_banco, cbancos.num_cta_banco, cbancos.cod_cta_conta,"
                    + " cbancos.saldo_cta, cbancos.fecha_creacion, cbancos.cod_usuario, cbancos.activo from"
                    + " mst_ctas_bancarias cbancos join mst_bancos bancos on cbancos.corr_banco = bancos.corr_banco";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) { 
                lst.add(new MstCuenta(
                        new MstBanco(rs.getInt("corr_banco"), rs.getString("nombre_banco")),                        
                        rs.getString("num_cta_banco"),
                        rs.getString("cod_cta_conta"),
                        rs.getDouble("saldo_cta"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("cod_usuario"),
                        rs.getString("activo")));
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
    
    public String comprobarCuenta(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp = null;
        MstCuenta cuenta = (MstCuenta) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select bancos.corr_banco, bancos.nombre_banco, cbancos.num_cta_banco,cbancos.fecha_creacion from \n" +
" mst_ctas_bancarias cbancos join mst_bancos bancos on cbancos.corr_banco = bancos.corr_banco where cbancos.corr_banco=? and cbancos.num_cta_banco=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, cuenta.getCorr_banco().getCorr_banco());
            pst.setString(2, cuenta.getNum_cta_banco());
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("num_cta_banco")!=null) {
                sqlresp = "Ya existe un número de cuenta \""+rs.getString("num_cta_banco")+"\" asociada al "+rs.getString("nombre_banco") + " registrada el " +fecha.dmaSalida(rs.getDate("fecha_creacion").toString()); 
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public List<TCCuenta> cargarCuentaCuenta() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TCCuenta> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select cod_residencial, cod_cta_conta, desc_cta_contab, saldo from tbl_catalogo_ctas";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) { 
                lst.add(new TCCuenta( rs.getInt("cod_residencial"), rs.getString("cod_cta_conta"), rs.getString("desc_cta_contab"), rs.getDouble("saldo")));
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
    
}
