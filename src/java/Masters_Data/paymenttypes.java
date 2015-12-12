package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class paymenttypes {
    public paymenttypes() {
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
            String sql =    "SELECT corr_forma_pago, desc_forma_pago, info_adicional, activo, fecha_creación, \n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord();\" />') as logo \n" + 
                            "from mst_formas_pago where activo = 'S'\n";
            //System.out.println(sql);
            rset = stmt.executeQuery(sql);
            json = ResultsetToJson.convert(rset);
            _result = json.toString().replace("\\\\", "\\");
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        //System.out.println(result);
        return _result;
    } // fin de metodo para busqueda
    
    public void add(String desc, String info, String usr){
        InitializeConnection();
        try {
            String sql = "insert into mst_formas_pago (desc_forma_pago, info_adicional, activo, cod_usuario, fecha_creación) " + 
                         "values ('" + desc + "','" + info + "', 'S','" + usr + "', NOW())";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String id, String desc, String info){
        InitializeConnection();
        try {
            String sql = "update mst_formas_pago set desc_forma_pago = '" + desc + "', info_adicional = '" + info +"' " +
                         "where corr_forma_pago = '" + id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String id){
        InitializeConnection();
        try {
            String sql = "update mst_formas_pago set activo = 'N' where corr_forma_pago = '" + id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}