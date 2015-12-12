package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class apps {
    public apps() {
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
    
    public JSONArray SearchData(String jv_search_term, String jv_term_to_search) {
        //variable que me define termino para busqueda
        String jv_search_exp ="";
        JSONArray result = new JSONArray();
        //evalua jv_term_to_search para saber por que termino buscar
        if (jv_term_to_search.equals("all")) {
            jv_search_exp = "";
        }
        if (jv_term_to_search.equals("poligono_id")) {
            jv_search_exp = "where poligono_id like '%" +jv_search_term + "%'";
        }
        if (jv_term_to_search.equals("poligono_nom")) {
            jv_search_exp = "where poligono_nom like '%" + jv_search_term + "%'";
        }
        if (jv_term_to_search.equals("poligono_rep")) {
            jv_search_exp = "where poligono_rep like '%" + jv_search_term + "%'";
        }
        
        InitializeConnection();
        try {
            String sql =    "SELECT \n" +
                            "poligono_id, poligono_nom, poligono_rep, poligono_tel_rep, \n" +
                            "CONCAT('<img src=\"Images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord(\'', poligono_id, '\')\" />') as logo \n" + 
                            "from poligonos \n";
            sql += jv_search_exp;
            System.out.println(sql);
            rset = stmt.executeQuery(sql);
            result = ResultsetToJson.convert(rset);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        //System.out.println(result);
        return result;
    } // fin de metodo para busqueda
    
    public void add(String jv_block_name, String jv_block_rep, String jv_block_phone){
        InitializeConnection();
        try {
            String sql = "select count(*) from poligonos";
            rset = stmt.executeQuery(sql);
            int _count = 0;
            if (rset.next()){
                _count = rset.getInt(1);
            }
            _count++;
            
            sql = "insert into poligonos (poligono_id, poligono_nom, poligono_rep, poligono_tel_rep) " + 
                    "values ('" + _count + "','" + jv_block_name + "','" + jv_block_rep + "','" + jv_block_phone + "')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String jv_block_id, String jv_block_name, String jv_block_rep, String jv_block_phone){
        InitializeConnection();
        try {
            String sql = "update poligonos set poligono_nom = '" + jv_block_name + "', " +
                "poligono_rep = '" + jv_block_rep + "', poligono_nom = '" + jv_block_name + "', poligono_tel_rep = '" + jv_block_phone + "' " +
                "where poligono_id = '" + jv_block_id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String jv_block_id){
        InitializeConnection();
        try {
            String sql = "delete from poligonos where poligono_id = '" + jv_block_id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
}
