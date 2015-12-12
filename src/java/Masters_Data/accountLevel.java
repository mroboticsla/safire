package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class accountLevel {
    public accountLevel() {
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
    
    public String Data() {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "select\n" +
                            "  cod_residencial,\n" +
                            "  (select a.nombre_residencial from mst_residencial a where a.cod_residencial = cod_residencial) nombre_residencial,\n" +
                            "  nivel1,\n" +
                            "  nivel2,\n" +
                            "  nivel3,\n" +
                            "  nivel4,\n" +
                            "  nivel5,\n" +
                            "  nivel6,\n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord();\" />') as logo \n" + 
                            "from tbl_estructura_cat_cta \n";
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            json = ResultsetToJson.convert(rset);
            _result = json.toString().replace("\\\\", "\\");
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        System.out.println(_result);
        return _result;
    } // fin de metodo para busqueda
    
    public void add(String cod_res, String l1, String l2, String l3, String l4, String l5, String l6){
        InitializeConnection();
        try {
            String sql = "insert into tbl_estructura_cat_cta (cod_residencial, nivel1, nivel2, nivel3, nivel4, nivel5,nivel6 ) " + 
                    "values ('" + cod_res + "','" + l1 + "','" + l2 + "','" + l3 + "','" + l4 + "','" + l5 + "','" + l6 + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String cod_res, String l1, String l2, String l3, String l4, String l5, String l6){
        InitializeConnection();
        try {
            String sql = "update tbl_estructura_cat_cta " + 
                            "set nivel1 = '" + l1 + "', " +
                            "nivel2 = '" + l2 + "', " +
                            "nivel3 = '" + l3 + "', " +
                            "nivel4 = '" + l4 + "', " +
                            "nivel5 = '" + l5 + "', " +
                            "nivel6 = '" + l6 + "' " +
                        "where cod_residencial = '" + cod_res +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String cod){
        InitializeConnection();
        try {
            String sql = "delete from tbl_estructura_cat_cta where cod_residencial = '" + cod +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}
