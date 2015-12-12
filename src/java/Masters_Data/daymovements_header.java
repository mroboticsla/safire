package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class daymovements_header {
    public daymovements_header() {
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
    
    public String Data() {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT cod_residencial,\n" +
                            "    corr_partida,\n" +
                            "    ano_contable,\n" +
                            "    fecha_transaccion,\n" +
                            "    DATE_FORMAT(fecha_transaccion, '%d/%m/%Y') fecha_transaccion_format,\n" + 
                            "    ano_transaccion,\n" +
                            "    mes_transaccion,\n" +
                            "    mes_contable,\n" +
                            "    case when mes_contable = 1" +
                                "           then 'Enero'\n" +
                                "       when mes_contable = 2" +
                                "           then 'Febrero'\n" +
                                "       when mes_contable = 3" +
                                "           then 'Marzo'\n" +
                                "       when mes_contable = 4" +
                                "           then 'Abril'\n" +
                                "       when mes_contable = 5" +
                                "           then 'Mayo'\n" +
                                "       when mes_contable = 6" +
                                "           then 'Junio'\n" +
                                "       when mes_contable = 7" +
                                "           then 'Julio'\n" +
                                "       when mes_contable = 8" +
                                "           then 'Agosto'\n" +
                                "       when mes_contable = 9" +
                                "           then 'Septiembre'\n" +
                                "       when mes_contable = 10" +
                                "           then 'Octubre'\n" +
                                "       when mes_contable = 11" +
                                "           then 'Noviembre'\n" +
                                "       when mes_contable = 12" +
                                "           then 'Diciembre'\n" +
                            "    end nom_mes_contable,\n" +
                            "    fecha_generacion,\n" +
                            "    usuario_creacion,\n" +
                            "    nombre_partida,\n" +
                            "    estado,\n" +
                            "    CONCAT('<a href=\"javascript:void(0)\" class=\"btnDelete\" onclick=\"javascript:DeleteHeader();\"></a>') as logo, \n" + 
                            "    CONCAT('<a href=\"javascript:void(0)\" class=\"btnEdit\" onclick=\"javascript:editRecord();\"></a>') as edit \n" +
                            "from tbl_partida_diaria b \n";
            
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
    
    public void add(String cod_residencial, String corr_partida, String ano_contable, 
                    String fecha_transaccion, String ano_transaccion, String mes_transaccion, 
                    String mes_contable, String usuario_creacion,
                    String nombre_partida){
        InitializeConnection();
        try {
            String sql = "INSERT INTO tbl_partida_diaria\n" +
                    "(cod_residencial,\n" +
                    "corr_partida,\n" +
                    "ano_contable,\n" +
                    "fecha_transaccion,\n" +
                    "ano_transaccion,\n" +
                    "mes_transaccion,\n" +
                    "mes_contable,\n" +
                    "fecha_generacion,\n" +
                    "usuario_creacion,\n" +
                    "nombre_partida,\n" +
                    "estado)\n" +
                "values ('" + cod_residencial + "',"
                    + "'" + corr_partida + "',"
                    + "'" + ano_contable + "',"
                    + "STR_TO_DATE('" + fecha_transaccion + "', '%d/%m/%Y'),"
                    + "'" + ano_transaccion + "',"
                    + "'" + mes_transaccion + "',"
                    + "'" + mes_contable + "',"
                    + "NOW(),"
                    + "'" + usuario_creacion + "',"
                    + "'" + nombre_partida + "', 'D')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String cod_residencial, String corr_partida, String ano_contable, 
                    String fecha_transaccion, String ano_transaccion, String mes_transaccion, 
                    String mes_contable, String nombre_partida){
        InitializeConnection();
        try {
            String sql = "UPDATE tbl_partida_diaria set \n" +
                    "ano_contable = '" + ano_contable + "',\n" +
                    "fecha_transaccion = STR_TO_DATE('" + fecha_transaccion + "', '%d/%m/%Y'),\n" +
                    "ano_transaccion = '" + ano_transaccion + "',\n" +
                    "mes_transaccion = '" + mes_transaccion + "',\n" +
                    "mes_contable = '" + mes_contable + "',\n" +
                    "nombre_partida = '" + nombre_partida + "'\n" +
                "WHERE cod_residencial = '" + cod_residencial + "' and corr_partida = '" + corr_partida + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void apply(String cod_residencial, String corr_partida){
        InitializeConnection();
        try {
            String sql = "update tbl_catalogo_ctas a\n" +
                            "set a.debe = a.debe + (SELECT SUM(b.debe) \n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.cod_cta_conta = a.cod_cta_conta\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		      ),\n" +
                            "a.haber = a.haber + (SELECT SUM(b.haber) \n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.cod_cta_conta = a.cod_cta_conta\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		      )\n" +
                            "where a.cod_residencial = '" + cod_residencial + "'\n" +
                            "and a.cod_cta_conta in (SELECT b.cod_cta_conta\n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		   )";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            
            
            sql = "UPDATE tbl_partida_diaria set estado = 'A' where cod_residencial = '" + cod_residencial + "' AND corr_partida = '" + corr_partida + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void revert(String cod_residencial, String corr_partida){
        InitializeConnection();
        try {
            String sql = "update tbl_catalogo_ctas a\n" +
                            "set a.debe = a.debe - (SELECT SUM(b.debe) \n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.cod_cta_conta = a.cod_cta_conta\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		      ),\n" +
                            "a.haber = a.haber - (SELECT SUM(b.haber) \n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.cod_cta_conta = a.cod_cta_conta\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		      )\n" +
                            "where a.cod_residencial = '" + cod_residencial + "'\n" +
                            "and a.cod_cta_conta in (SELECT b.cod_cta_conta\n" +
                            "			FROM tbl_partida_diaria_mov b \n" +
                            "			WHERE b.cod_residencial = a.cod_residencial\n" +
                            "			AND b.corr_partida = '" + corr_partida + "'\n" +
                            "		   )";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            
            
            sql = "UPDATE tbl_partida_diaria set estado = 'D' where cod_residencial = '" + cod_residencial + "' AND corr_partida = '" + corr_partida + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String residencial, String corr){
        InitializeConnection();
        try {
            String sql = "delete from tbl_partida_diaria where cod_residencial = '" + residencial + "' and corr_partida = '" + corr + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}
