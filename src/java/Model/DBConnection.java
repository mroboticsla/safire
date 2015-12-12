
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public DBConnection() {
        super();
    }
    
    private Connection conexion = null;
    static String server = "jdbc:mysql://localhost:3306/safire";
    static String user = "root";
    static String pass = "root";
   
   
   //Metodo que usaremos para crear la conexion a la base de datos en todas las clases 
    public Connection CreateConnection() throws SQLException{
            /*conexion sera nuestra conexion a la bd*/
            
            String jva_mensaje="";

            /*parametros para la conexion*/
            
            String sUrl="";  
           try{
               Class.forName("com.mysql.jdbc.Driver"); 
           }catch (ClassNotFoundException e){
               System.out.println("error driver"+e.getMessage());
               e.printStackTrace();    
           }
            
        try{
            Connection mySqlcon = (Connection) DriverManager.getConnection(server, user, pass);
            return mySqlcon;
        } catch (Exception ex){
            System.out.println("error conector"+ex.getMessage());
            jva_mensaje = ex.toString();
            
        }
            jva_mensaje="conectado";
            if(conexion.isClosed()){
                jva_mensaje="desconectado";
            }
            
            //System.out.println(mensaje);
            return conexion;
    }
    
    public void DestroyConnection(){
        if (conexion != null) {
            try
            {
                System.out.println("Conexion DBConecction");
                conexion.close();
                conexion=null;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}