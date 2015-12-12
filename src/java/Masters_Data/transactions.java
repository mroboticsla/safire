package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class transactions {
    public transactions() {
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
    
    public String AccountsData() {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT \n" +
                            "(select a.nombre_residencial from mst_residencial a where a.cod_residencial = cod_residencial) nombre_residencial, cod_residencial, \n" +
                            "cod_cta_conta, concat(cod_cta_conta, ' - ', desc_cta_contab) desc_cta_contab, cod_tipo_cta, fecha_creacion, acepta_movs, estado_balance, \n" +
                            "aplica_debe, aplica_haber, nivel_cuenta, cod_cta_mayor, tamano_cod_cta \n" +
                            "from tbl_catalogo_ctas where acepta_movs = 'S' order by nivel_cuenta";
            
            //System.out.println(sql);
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
    
    public String Data() {
        //variable que me define termino para busqueda
        
        JSONArray json = new JSONArray();
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT cod_transaccion,\n" +
                                "    LPAD(cod_transaccion, 6, 0) cod_transaccion_mask,\n" +
                                "    cod_residencial,\n" +
                                "    corr_forma_pago,\n" +
                                "    (select desc_forma_pago from mst_formas_pago b where b.corr_forma_pago = a.corr_forma_pago) nom_forma_pago,\n" +
                                "    desc_transaccion,\n" +
                                "    concepto_transaccion,\n" +
                                "    tipo_ingreso,\n" +
                                "    tipo_egreso,\n" +
                                "    case when tipo_ingreso = 1 then 'Ingreso' else 'Egreso' end movimiento,\n" +
                                "    cod_cta_contab_debe,\n" +
                                "    cod_cta_contab_haber,\n" +
                                "    (select b.desc_cta_contab from tbl_catalogo_ctas b where b.cod_residencial = a.cod_residencial and b.cod_cta_conta = a.cod_cta_contab_debe) nom_cuenta_debe,\n" +
                                "    (select b.desc_cta_contab from tbl_catalogo_ctas b where b.cod_residencial = a.cod_residencial and b.cod_cta_conta = a.cod_cta_contab_haber) nom_cuenta_haber,\n" +
                                "    cod_usuario,\n" +
                                "    fecha_creacion,\n" +
                                "    activo,\n" +
                                "CONCAT('<img src=\"../../resources/images/delete.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:deleteRecord();\" />') as logo \n" +                     
                            "FROM mst_transacciones a where activo = 'S'";
            //System.out.println(sql);
            rset = stmt.executeQuery(sql);
            json = ResultsetToJson.convert(rset);
            _result = json.toString().replace("\\\\", "\\");
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        DestroyConnection();
        //System.out.println(_result);
        return _result;
    } // fin de metodo para busqueda
    
    public void add(String cod_residencial, String corr_forma_pago, String desc_transaccion, String concepto_transaccion, String tipo_ingreso, String tipo_egreso, String cod_cta_contab_debe, String cod_cta_contab_haber, String cod_usuario){
        InitializeConnection();
        try {
            String sql = "insert into mst_transacciones (cod_residencial, corr_forma_pago, desc_transaccion, concepto_transaccion, tipo_ingreso, tipo_egreso, cod_cta_contab_debe, cod_cta_contab_haber, cod_usuario, fecha_creacion, activo) " + 
                         "values ('" + cod_residencial + "','" + corr_forma_pago + "','" + desc_transaccion + "','" + concepto_transaccion + "','" + tipo_ingreso + "','" + tipo_egreso + "','" + cod_cta_contab_debe + "','" + cod_cta_contab_haber + "','" + cod_usuario + "', NOW(),'S')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rset.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
    
    public void edit(String id, String corr_forma_pago, String desc_transaccion, String concepto_transaccion, String tipo_ingreso, String tipo_egreso, String cod_cta_contab_debe, String cod_cta_contab_haber){
        InitializeConnection();
        try {
            String sql = "update mst_transacciones set corr_forma_pago = '" + corr_forma_pago
                            + "',desc_transaccion = '" + desc_transaccion
                            + "',concepto_transaccion = '" + concepto_transaccion
                            + "',tipo_ingreso = '" + tipo_ingreso
                            + "',tipo_egreso = '" + tipo_egreso
                            + "',cod_cta_contab_debe = '" + cod_cta_contab_debe
                            + "',cod_cta_contab_haber = '" + cod_cta_contab_haber + "' "
                            + "where cod_transaccion = '" + id +  "'";
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
            String sql = "update mst_transacciones set activo = 'N' where cod_transaccion = '" + id +  "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}