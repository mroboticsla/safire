package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public Connect() {
        super();
    }
    
    @SuppressWarnings({"UseSpecificCatch", "null"})
    public Connection CreateConnection() throws SQLException{
            /*conexion sera nuestra conexion a la bd*/
            Connection conexion=null;

            String mensaje="";

            /*parametros para la conexion*/
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/safire";
            String usuario = "root";
            String clave = "root";
            /*procedimiento de la conexion*/
            try{
                    Class.forName(driver);
                    conexion = DriverManager.getConnection(url,usuario,clave);

                    /*guardando la conexion en la session*/
                    //session.setAttribute("conexion",conexion);
            } catch (Exception ex){
                    mensaje=ex.toString();
            }

            mensaje="conectado";
            if(conexion.isClosed()){
                    mensaje="desconectado";
            }
            
            return conexion;
    }
}
