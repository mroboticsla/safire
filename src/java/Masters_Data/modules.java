package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class modules {
    public modules() {
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
            String sql =    "SELECT \n" +
                            "cod_modulo, desc_modulo, orden_modulo, cod_usuario, fecha_creacion, \n" +
                            "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord(\'', poligono_id, '\')\" />') as logo \n" + 
                            "from mst_modulos \n";
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            json = ResultsetToJson.convert(rset);
            _result = json.toString().replace("\\\\", "\\");;
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        //System.out.println(result);
        return _result;
    } // fin de metodo para busqueda
    
    public void add(String name, String order, String usr){
        InitializeConnection();
        try {
            String sql = "select count(*) from mst_modulos";
            rset = stmt.executeQuery(sql);
            int _count = 0;
            if (rset.next()){
                _count = rset.getInt(1);
            }
            _count++;
            
            sql = "insert into mst_modulos (cod_modulo, desc_modulo, orden_modulo, cod_usuario, fecha_creacion) " + 
                    "values ('" + _count + "','" + name + "','" + order + "','" + usr + "', NOW())";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String id, String name, String order){
        InitializeConnection();
        try {
            String sql = "update mst_modulos set desc_modulo = '" + name + "', " +
                "orden_modulo = '" + order + "' " +
                "where cod_modulo = '" + id +  "'";
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
            String sql = "delete from mst_modulos where cod_modulo = '" + id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
}
