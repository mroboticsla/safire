
package modeladoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import uml.MstUsuario;
import uml.TChequeCC;

/**
 *
 * @author Nieto Mendoza
 */
public class TChequeCCDAO implements CRUDInteface{
    BDConexion bd = new BDConexion();
    Logger log = Logger.getLogger(TChequeCCDAO.class.getName());
    public TChequeCCDAO() {
    }

    @Override
    public String create(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TChequeCC cheque = (TChequeCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "insert into tbl_cajachica_cheques values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = cn.prepareStatement(sql);
            pst.setString(1, cheque.getNum_liquidacion());
            pst.setDate(2, cheque.getFecha_liquidacion());
            pst.setInt(3, cheque.getCorr_banco());
            pst.setString(4, cheque.getNum_cta_banco());
            pst.setDate(5, cheque.getFecha_cheque());
            pst.setString(6,cheque.getNum_cheque());
            pst.setString(7, cheque.getNombre_cheque());
            pst.setDouble(8, cheque.getValor_cheque() );
            pst.setString(9, cheque.getCod_usuario());
            pst.setDate(10, cheque.getFecha_cheque());
            int registro = pst.executeUpdate();
            sqlresp = registro + " cheque ha sido agregado.";
            pst.close(); cn.close();
        } catch (SQLException e) {
            log.severe(e.toString());
        } catch (Exception e) {
            log.severe(e.toString());
        }
        return sqlresp;
    }

    @Override
    public List<TChequeCC> read() {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        List<TChequeCC> lst = new ArrayList();
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql ="select * from tbl_cajachica_cheques order by num_liquidacion";
            pst = cn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new TChequeCC(
                        rs.getString("num_liquidacion"),
                        rs.getDate("fecha_liquidacion"),
                        rs.getInt("corr_banco"),
                        rs.getString("num_cta_banco"),
                        rs.getDate("fecha_cheque"),
                        rs.getString("num_cheque"),
                        rs.getString("nombre_cheque"),
                        rs.getDouble("valor_cheque"),
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

    @Override
    public String update(Object obj) {
        Connection cn;
        PreparedStatement pst;
        String sql;
        String sqlresp = null;
        TChequeCC cheque = (TChequeCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "update tbl_cajachica_cheques set fecha_liquidacion=?, corr_banco=?, num_cta_banco=?, fecha_cheque=?, num_cheque=?, nombre_cheque=?, valor_cheque=?, cod_usuario=?, fecha_ingreso=? where num_liquidacion=?";
            pst = cn.prepareStatement(sql);
            pst.setDate(1, cheque.getFecha_liquidacion());
            pst.setInt(2, cheque.getCorr_banco());
            pst.setString(3, cheque.getNum_cta_banco());
            pst.setDate(4, cheque.getFecha_cheque());
            pst.setString(5,cheque.getNum_cheque());
            pst.setString(6, cheque.getNombre_cheque());
            pst.setDouble(7, cheque.getValor_cheque() );
            pst.setString(8, cheque.getCod_usuario());
            pst.setDate(9, cheque.getFecha_cheque());
            pst.setString(10, cheque.getNum_liquidacion());
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
        TChequeCC cheque = (TChequeCC) obj;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "delete from tbl_cajachica_cheques where num_liquidacion=?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, cheque.getNum_liquidacion());
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
    public List<?> search(Object obj) {
        
        return null;
    }
    /*PROCESOS FUERA DEL CRUD O MANTENIMIENTO DE LA TABLA CHEQUES */
    public double chequePendiente(){
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        double sqlresp = 0;
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select sum(valor_docto) as ValorCheque from tbl_cajachica_gasto where estado='P'";
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
    
    public List<MstUsuario> autorizarCheque(Object obj) {
        Connection cn;
        PreparedStatement pst;
        ResultSet rs;
        String sql;
        int rol=0;
        List<MstUsuario> lst = new ArrayList();
        MstUsuario usuario = (MstUsuario) obj;
        
        try {
            Class.forName(bd.getDriver());
            cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
            sql = "select * from mst_usuarios where cod_usuario = ? and contraseña = ?";
            pst = cn.prepareStatement(sql);
            pst.setString(1, usuario.getCodigoUsuario());
            pst.setString(2, usuario.getPassword());
            rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(new MstUsuario(rs.getInt("cod_rol"), rs.getString("activo")));
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
