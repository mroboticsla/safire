package Masters_Data;

import Database.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class roleMenu {
    public roleMenu() {
        super();
    }
    
    private Connect conexion = null;
    private Connection conn = null;
    private Statement stmt = null;
    ResultSet rset = null;
    
    private Connect conexion2 = null;
    private Connection conn2 = null;
    private Statement stmt2 = null;
    ResultSet rset2 = null;
    
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
    
    private void InitializeConnection2() {
        try {
            conexion2 = new Connect();
            conn2 = conexion.CreateConnection();
            //conexion.DestroyConnection();
            stmt2 = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void DestroyConnection2() {
        try {
            rset2.close();
            conn2.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String AllData() {
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "select cod_modulo id, desc_modulo text from mst_modulos";
            
            rset = stmt.executeQuery(sql);
            
            _result = "[{" +
                "       \"id\":0,\n" + 
                "	\"text\":\"SAFIRE\",\n" + 
                "	\"children\":[";
            int count = 0;
            while(rset.next()){
                if (count > 0) _result += ",";
                _result += "{ \n";
                _result += "    \"id\":\"" + rset.getString("id") + "\",\n";
                _result += "    \"text\":\"" + rset.getString("text") + "\",\n";
                _result += "    \"children\":[";
                           
                                InitializeConnection2();
                                try {
                                   String sql2 =    "select a.COD_APLICACION id, a.NOMBRE_APLICACION text \n" + 
                                                    "from mst_aplicaciones a where a.COD_MODULO = '" + rset.getString("id") + "' order by a.orden_aplicacion";
                                    
                                    //System.out.println(sql);
                                    rset2 = stmt2.executeQuery(sql2);
                                    
                                    int count2 = 0;
                                    while(rset2.next()){
                                        if (count2 > 0) _result += ",";
                                        _result += "{ \n";
                                        _result += "    \"id\":\"" + rset2.getString("id") + "\",\n";
                                        _result += "    \"text\":\"" + rset2.getString("text") + "\"\n";
                                        _result += "} \n";
                                        count2++;
                                    }
                                    
                                    rset2.close();
                                    
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                DestroyConnection2();
                _result += "    ] \n"; 
                _result += "} \n";
                count++;
            }
            
            _result += "]" +
                "}]";
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        return _result;
    }
    
    public String RoleData(String role) {
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "select cod_modulo id, desc_modulo text from mst_modulos";
            
            rset = stmt.executeQuery(sql);
            
            _result = "[{" +
                "       \"id\":0,\n" + 
                "       \"text\":\"SAFIRE\",\n" + 
                "       \"children\":[";
            int count = 0;
            while(rset.next()){
                if (count > 0) _result += ",";
                _result += "{ \n";
                _result += "    \"id\":\"" + rset.getString("id") + "\",\n";
                _result += "    \"text\":\"" + rset.getString("text") + "\",\n";
                _result += "    \"children\":[";
                           
                                InitializeConnection2();
                                try {
                                   String sql2 =    "select a.COD_APLICACION id, a.NOMBRE_APLICACION text, a.COD_USUARIO usr, \n" + 
                                                    "ifnull((select 'true' from mst_roles_aplicaciones r where r.COD_APLICACION = a.COD_APLICACION and r.COD_ROL = '" + role + "'), 'false') checked\n" + 
                                                    "from mst_aplicaciones a where a.COD_MODULO = '" + rset.getString("id") + "' order by a.orden_aplicacion";
                                    
                                    //System.out.println(sql);
                                    rset2 = stmt2.executeQuery(sql2);
                                    
                                    int count2 = 0;
                                    while(rset2.next()){
                                        if (count2 > 0) _result += ",";
                                        _result += "{ \n";
                                        _result += "    \"id\":\"" + rset2.getString("id") + "\",\n";
                                        _result += "    \"text\":\"" + rset2.getString("text") + "\",\n";
                                        
                                        if (rset2.getString("checked").equalsIgnoreCase("true")){
                                            _result += "    \"checked\":\"" + rset2.getString("checked") + "\",\n";
                                        }
                                        
                                        _result += "    \"role\":\"" + role + "\",\n";
                                        _result += "    \"usr\":\"" + rset2.getString("usr") + "\",\n";
                                        _result += "    \"module\":\"" + rset.getString("id") + "\"\n";
                                        _result += "} \n";
                                        count2++;
                                    }
                                    
                                    rset2.close();
                                    
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                DestroyConnection2();
                _result += "    ] \n"; 
                _result += "} \n";
                count++;
            }
            
            _result += "]" +
                "}]";
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        return _result;
    }
    
    public void add(String role, String module, String application, String user){
        InitializeConnection();
        try {
            String sql = "INSERT INTO mst_roles_aplicaciones\n" + 
                            "(COD_ROL,\n" + 
                            "COD_MODULO,\n" + 
                            "COD_APLICACION,\n" + 
                            "COD_USUARIO,\n" + 
                            "FECHA_CREACION,\n" + 
                            "ACTIVO)" + 
                         "values ('" + role + "','" + module + "','" + application + "','" + user + "', NOW(), 'A')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void delete(String role, String module, String application){
        InitializeConnection();
        try {
            String sql = "delete from mst_roles_aplicaciones where COD_ROL = '" + role +  "' and COD_MODULO = '" + module + "' and COD_APLICACION = '" + application + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
}
