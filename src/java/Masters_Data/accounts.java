package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class accounts {
    public accounts() {
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
    
    public String CheckLevelLenght(String residencial, String nivel) {
        String _result = "0";
        InitializeConnection();
        try {
            String sql = "select nivel" + nivel + " nl from tbl_estructura_cat_cta where cod_residencial = '" + residencial + "'";
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            if (rset.next()){
                _result = rset.getString("nl");
            }
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
        System.out.println(_result);
        return _result;
    }
    
    public String CheckAccountNumber(String residencial, String account) {
        String _result = "false";
        InitializeConnection();
        try {
            String sql = "select cod_cta_conta from tbl_catalogo_ctas where cod_residencial = '" + residencial + "' and cod_cta_conta = '" + account + "'";
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
    
    public String Data(String level) {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT \n" +
                            "(select a.nombre_residencial from mst_residencial a where a.cod_residencial = cod_residencial) nombre_residencial, cod_residencial, \n" +
                            "cod_cta_conta, desc_cta_contab, cod_tipo_cta, fecha_creacion, acepta_movs, estado_balance, \n" +
                            "aplica_debe, aplica_haber, nivel_cuenta, cod_cta_mayor, tamano_cod_cta, saldo, \n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord();\" />') as logo, \n" + 
                            "CONCAT('<img src=\"../../resources/images/edit_account.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:nextLevel();\" />') as edit, \n" + 
                            "(select count(*) from tbl_catalogo_ctas a where a.cod_residencial = b.cod_residencial and a.cod_cta_mayor = b.cod_cta_conta and a.estado = 'A') cta_menor \n" +
                            "from tbl_catalogo_ctas b \n";
            
            sql+="where nivel_cuenta = '" + level + "' and estado = 'A'";
            
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
    
    public String DataNL(String residencial, String level, String cta_mayor) {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT \n" +
                            "(select a.nombre_residencial from mst_residencial a where a.cod_residencial = cod_residencial) nombre_residencial, cod_residencial, \n" +
                            "cod_cta_conta, desc_cta_contab, cod_tipo_cta, fecha_creacion, acepta_movs, estado_balance, \n" +
                            "aplica_debe, aplica_haber, nivel_cuenta, cod_cta_mayor, tamano_cod_cta, \n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord();\" />') as logo, \n";
                            if (!level.equals("5")){
                                sql += "CONCAT('<img src=\"../../resources/images/edit_account.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:nextLevel();\" />') as edit, \n";
                            }
                            sql += "(select count(*) from tbl_catalogo_ctas a where a.cod_residencial = b.cod_residencial and a.cod_cta_mayor = b.cod_cta_conta and a.estado = 'A') cta_menor \n";
                            sql += "from tbl_catalogo_ctas b \n";
            
            sql+="where cod_residencial = '" + residencial + "' and nivel_cuenta = '" + level + "' and cod_cta_mayor = '" + cta_mayor +"' and estado = 'A'";
            
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
    
    public void add(String cod_residencial, String cod_cta_conta, String desc_cta_contab, 
                    String cod_tipo_cta, String acepta_movs, String aplica_debe, 
                    String aplica_haber, String nivel_cuenta, String cod_cta_mayor,
                    String tamano_cod_cta, String Estado_balance, String cod_usuario){
        InitializeConnection();
        try {
            String sql = "INSERT INTO tbl_catalogo_ctas\n" +
                    "(cod_residencial,\n" +
                    "cod_cta_conta,\n" +
                    "desc_cta_contab,\n" +
                    "cod_tipo_cta,\n" +
                    "acepta_movs,\n" +
                    "saldo,\n" +
                    "Debe,\n" +
                    "Haber,\n" +
                    "aplica_debe,\n" +
                    "aplica_haber,\n" +
                    "nivel_cuenta,\n" +
                    "cod_cta_mayor,\n" +
                    "tamano_cod_cta,\n" +
                    "Estado_balance,\n" +
                    "fecha_creacion,\n" +
                    "cod_usuario, estado)\n" +
                "values ('" + cod_residencial + "',"
                    + "'" + cod_cta_conta + "',"
                    + "'" + desc_cta_contab + "',"
                    + "'" + cod_tipo_cta + "',"
                    + "'" + acepta_movs + "',"
                    + "'0',"
                    + "'0',"
                    + "'0',"
                    + "'" + aplica_debe + "',"
                    + "'" + aplica_haber + "',"
                    + "'" + nivel_cuenta + "',"
                    + "'" + cod_cta_mayor + "',"
                    + "(SELECT nivel" + nivel_cuenta + " FROM tbl_estructura_cat_cta WHERE cod_residencial = '" + cod_residencial + "'),"
                    + "'" + Estado_balance + "',"
                    + "NOW(),"
                    + "'" + cod_usuario + "', 'A')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            
            if (!nivel_cuenta.equals("1")){
                sql = "INSERT INTO `safire`.`tbl_contab_ctas_mayor`\n" +
                        "(`cod_residencial`,\n" +
                        "`cod_cta_contab`,\n" +
                        "`cod_cta_mayor`)\n" +
                        "VALUES\n" +
                        "('" + cod_residencial + "','" + cod_cta_conta + "','" + cod_cta_mayor + "')";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String cod_residencial, String cod_cta_conta, String desc_cta_contab, 
                    String cod_tipo_cta, String acepta_movs, String aplica_debe, 
                    String aplica_haber, String Estado_balance){
        InitializeConnection();
        try {
            String sql = "UPDATE tbl_catalogo_ctas set \n" +
                    "desc_cta_contab = '" + desc_cta_contab + "',\n" +
                    "cod_tipo_cta = '" + cod_tipo_cta + "',\n" +
                    "acepta_movs = '" + acepta_movs + "',\n" +
                    "aplica_debe = '" + aplica_debe + "',\n" +
                    "aplica_haber = '" + aplica_haber + "',\n" +
                    "Estado_balance = '" + Estado_balance + "'\n" +
                "WHERE cod_residencial = '" + cod_residencial + "' and cod_cta_conta = '" + cod_cta_conta + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String residencial, String cta){
        InitializeConnection();
        try {
            String sql = "update tbl_catalogo_ctas set estado = 'I' where cod_residencial = '" + residencial + "' and cod_cta_conta = '" + cta + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
}
