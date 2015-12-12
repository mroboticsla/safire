
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
import utilidades.MySQL;

/**
 *
 * @author Nieto Mendoza
 */
public class MstBancoDAO implements CRUDInteface{
    BDConexion bd = new BDConexion();
    Logger log = Logger.getLogger(MstBancoDAO.class.getName());
    MySQL fecha = new MySQL();
    public MstBancoDAO() {
    }
@Override
    public String create(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        MstBanco banco = (MstBanco) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "insert into mst_bancos values ( ?, ?, ?, ?, ?, ?)";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, banco.getCod_residencial());
            pst.setInt(2, banco.getCorr_banco());
            pst.setString(3, banco.getNombre_banco());
            pst.setDate(4, banco.getFecha_creacion());
            pst.setString(5, banco.getCod_usuario());
            pst.setString(6, banco.getActivo());
            int registro = pst.executeUpdate();
            sqlresp = registro + " registro ha sido agregado";
            pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public List<MstBanco> read() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<MstBanco> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select * from mst_bancos order by corr_banco";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstBanco(
                        rs.getInt("cod_residencial"),
                        rs.getInt("corr_banco"),
                        rs.getString("nombre_banco"),
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
        MstBanco banco = (MstBanco) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update mst_bancos set nombre_banco=?, fecha_creacion=?, cod_usuario=?, activo=? where cod_residencial=?, corr_banco=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, banco.getNombre_banco());
            pst.setDate(2, banco.getFecha_creacion());
            pst.setString(3, banco.getCod_usuario());
            pst.setString(4, banco.getActivo());
            pst.setInt(5, banco.getCod_residencial());
            pst.setInt(6, banco.getCorr_banco());
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
        MstBanco banco = (MstBanco) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from mst_bancos where cod_residencial=?, corr_banco=?";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, banco.getCod_residencial());
            pst.setInt(1, banco.getCorr_banco());
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
    public List<MstBanco> search(Object obj) {
        
        return null;
    }
    
    public int numeroRegistros() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        int sqlresp = 0;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select count(*) registros from mst_bancos";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.first();
            sqlresp = rs.getInt("registros");
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }
    
    public String comprobarBanco(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        String sqlresp = null;
        MstBanco banco = (MstBanco) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select * from mst_bancos where nombre_banco=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, banco.getNombre_banco());
            rs = pst.executeQuery();
            rs.first();
            if (rs.getString("nombre_banco")!=null) {
                sqlresp = "Ya existe un banco con el nombre \""+rs.getString("nombre_banco")+"\" registrado el "+ fecha.dmaSalida(rs.getDate("fecha_creacion").toString()); 
            }
            rs.close(); pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    
}
