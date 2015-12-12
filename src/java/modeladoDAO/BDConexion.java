
package modeladoDAO;

/**
 *
 * @author Nieto Mendoza
 */
public class BDConexion {
    private String driver;
    private String url;
    private String user;
    private String passwd;

    /*Si se necesita que esto sea dinamico por cambio de direccion de servidor o usuario administrador, 
      tiene que generarse los metodos SET para ser asignados externamente.
    */
    public BDConexion() {
        this.driver = "com.mysql.jdbc.Driver"; //Driver que conecta a la base de datos. 
        this.url = "jdbc:mysql://localhost:3306/safire"; //Direccion del servidor de la base de datos.
        this.user = "root"; //Usuario que tiene acceso al servidor de datos.
        this.passwd = "root"; //Clave del usuario.
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }
}
