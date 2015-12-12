package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class daymovements_details {
    public daymovements_details() {
        super();
    }
    
    private Connect conexion = null;
    private Connection conn = null;
    private Statement stmt = null;
    ResultSet rset = null;
    
    private void InitializeConnection() {
        try {
            conexion = new Connect();
            conn = conexion.CreateConnection();
            //conexion.DestroyConnection();
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void DestroyConnection() {
        try {
            rset.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String CheckMovementNumber(String residencial, String correlativo, String ano_contable) {
        String _result = "false";
        InitializeConnection();
        try {
            String sql = "select corr_partida from tbl_partida_diaria where cod_residencial = '" + residencial + "' and corr_partida = '" + correlativo + "' and ano_contable = '" + ano_contable + "'";
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            if (rset.next()){
                _result = "true";
            }
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
        System.out.println(_result);
        return _result;
    }
    
    public String Data(String cod_residencial, String corr_partida) {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT cod_residencial,\n" +
                                "    corr_partida,\n" +
                                "    ano_contable,\n" +
                                "    corr_movi,\n" +
                                "    cod_cta_conta,\n" + 
                                "    (select desc_cta_contab from tbl_catalogo_ctas b where b.cod_residencial = a.cod_residencial and b.cod_cta_conta = a.cod_cta_conta) desc_conta," +
                                "    debe,\n" +
                                "    haber,\n" +
                                "    (select SUM(debe) from tbl_partida_diaria_mov b where b.cod_residencial = a.cod_residencial and b.corr_partida = a.corr_partida) TD,\n" +
                                "    (select SUM(haber) from tbl_partida_diaria_mov b where b.cod_residencial = a.cod_residencial and b.corr_partida = a.corr_partida) TH,\n" +
                                "    (select SUM(debe) - SUM(haber) from tbl_partida_diaria_mov b where b.cod_residencial = a.cod_residencial and b.corr_partida = a.corr_partida) DIF," +
                                "    desc_movimiento,\n" +
                                "    referencia," +
                                "    CONCAT('<a href=\"javascript:void(0)\" class=\"btnDelete\" onclick=\"javascript:DeleteMov();\"></a>') as logo \n" + 
                                "from tbl_partida_diaria_mov a \n" +
                            "WHERE cod_residencial = '" + cod_residencial + "' and corr_partida = '" + corr_partida + "'";
            
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            json = ResultsetToJson.convert(rset);
            _result = json.toString().replace("\\\\", "\\");;
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        //System.out.println(_result);
        return _result;
    }
    
    public void add(String cod_residencial, String corr_partida, String ano_contable, String cod_cta_conta,
                    String debe, String haber, String desc_movimiento, String referencia){
        InitializeConnection();
        try {
            String corr_movi = "";
            String sql = "select ifnull((select max(corr_movi) from tbl_partida_diaria_mov where cod_residencial = '" + cod_residencial + "' and corr_partida = '" + corr_partida + "'), 0) + 1 maxnum from dual";
            System.out.println(sql);
            ResultSet rset = stmt.executeQuery(sql);
            if (rset.next()){
                corr_movi = rset.getString("maxnum");
            }
            rset.close();
            
            sql = "INSERT INTO tbl_partida_diaria_mov\n" +
                    "(cod_residencial,\n" +
                    "corr_partida,\n" +
                    "ano_contable,\n" +
                    "corr_movi,\n" +
                    "cod_cta_conta,\n" +
                    "debe,\n" +
                    "haber,\n" +
                    "desc_movimiento,\n" +
                    "referencia)\n" +
                "values ('" + cod_residencial + "',"
                    + "" + corr_partida + ","
                    + "" + ano_contable + ","
                    + "" + corr_movi + ","
                    + "'" + cod_cta_conta + "',"
                    + "" + debe + ","
                    + "" + haber + ","
                    + "'" + desc_movimiento + "',"
                    + "'" + referencia + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String cod_residencial, String corr_partida, String ano_contable, 
                    String corr_movi, String cod_cta_conta, String debe, 
                    String haber, String desc_movimiento, String referencia){
        InitializeConnection();
        try {
            String sql = "UPDATE tbl_partida_diaria_mov set \n" +
                            "cod_cta_conta = '" + cod_cta_conta + "',\n" +
                            "debe = " + debe + ",\n" +
                            "haber = " + haber + ",\n" +
                            "desc_movimiento = '" + desc_movimiento + "',\n" +
                            "referencia = '" + referencia + "'\n" +
                        "WHERE cod_residencial = '" + cod_residencial + "' and corr_partida = '" + corr_partida + "' and ano_contable = '" + ano_contable + "' and corr_movi = '" + corr_movi + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String cod_residencial, String corr_partida, String ano_contable, String corr_movi){
        InitializeConnection();
        try {
            String sql = "delete from tbl_partida_diaria_mov where cod_residencial = '" + cod_residencial + "' and corr_partida = '" + corr_partida + "' and ano_contable = '" + ano_contable + "' and corr_movi = '" + corr_movi + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}
