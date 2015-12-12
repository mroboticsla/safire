package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class roles {
    public roles() {
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
            String sql =    "SELECT COD_ROL, DESC_ROL, COD_USUARIO, FECHA_CREACION, \n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord(\'', COD_ROL, '\')\" />') as logo \n" + 
                            "from mst_roles t\n";
            System.out.println(sql);
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
    
    public void add(String name, String usr){
        InitializeConnection();
        try {
            String sql = "select count(*) from mst_roles";
            rset = stmt.executeQuery(sql);
            int _count = 0;
            if (rset.next()){
                _count = rset.getInt(1);
            }
            _count++;
            
            sql = "insert into mst_roles (DESC_ROL, COD_USUARIO, FECHA_CREACION) " + 
                  "values ('" + name + "','" + usr + "', NOW())";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String id, String name){
        InitializeConnection();
        try {
            String sql = "update mst_roles set DESC_ROL = '" + name + "' " +
                         "where COD_ROL = '" + id +  "'";
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
            String sql = "delete from mst_roles where COD_ROL = '" + id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
}
