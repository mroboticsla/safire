package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;

public class mayorizacion {
    public mayorizacion() {
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
    
    public String MesVigente(String cod_residencial){
        String _result = "";
        InitializeConnection();
        try{
            String sql = "select case mes_vigente "
                    + "when 1 then 'Enero' "
                    + "when 2 then 'Febrero' "
                    + "when 3 then 'Marzo' "
                    + "when 4 then 'Abril' "
                    + "when 5 then 'Mayo' "
                    + "when 6 then 'Junio' "
                    + "when 7 then 'Julio' "
                    + "when 8 then 'Agosto' "
                    + "when 9 then 'Septiembre' "
                    + "when 10 then 'Octubre' "
                    + "when 11 then 'Noviembre' "
                    + "when 12 then 'Diciembre' "
                    + "else 'N/D' "
                    + "end mes_vigente "
                    + "from mst_residencial where cod_residencial = '" + cod_residencial + "'";
            rset = stmt.executeQuery(sql);
            if (rset.next()){
                _result = rset.getString("mes_vigente");
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        DestroyConnection();
        return _result;
    }
    
    public String AnoVigente(String cod_residencial){
        String _result = "";
        InitializeConnection();
        try{
            String sql = "select ano_vigente from mst_residencial where cod_residencial = '" + cod_residencial + "'";
            rset = stmt.executeQuery(sql);
            if (rset.next()){
                _result = rset.getString("ano_vigente");
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        DestroyConnection();
        return _result;
    }
    
    public void mayorizar(String cod_residencial){
        InitializeConnection();
        try{
            for (int nivel = 5; nivel >= 1; nivel--)
            {
                try (Statement stmt2 = conn.createStatement()) {
                    String sql = "SELECT cod_residencial, cod_cta_conta, desc_cta_contab, nivel_cuenta, cod_tipo_cta,\n" +
                            "saldo as 'saldo_ant', CASE cod_tipo_cta\n" +
                            "			WHEN 'P' THEN saldo + nuevo_haber - nuevo_debe\n" +
                            "			ELSE saldo + nuevo_debe - nuevo_haber \n" +
                            "		 END saldo, \n" +
                            "debe as 'debe_ant', nuevo_debe debe,\n" +
                            "haber as 'haber_ant', nuevo_haber haber\n" +
                            "FROM\n" +
                            "(\n" +
                            "	SELECT cod_residencial, cod_cta_conta, desc_cta_contab, nivel_cuenta,\n" +
                            "	saldo, cod_tipo_cta,\n" +
                            "	(SELECT SUM(saldo) \n" +
                            "		FROM tbl_catalogo_ctas b \n" +
                            "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                            "		AND b.cod_residencial = a.cod_residencial\n" +
                            "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                            "	) nuevo_saldo,\n" +
                            "	debe, \n" +
                            "	(SELECT SUM(debe) \n" +
                            "		FROM tbl_catalogo_ctas b \n" +
                            "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                            "		AND b.cod_residencial = a.cod_residencial\n" +
                            "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                            "	) nuevo_debe,\n" +
                            "	haber, \n" +
                            "	(SELECT SUM(haber) \n" +
                            "		FROM tbl_catalogo_ctas b \n" +
                            "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                            "		AND b.cod_residencial = a.cod_residencial\n" +
                            "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                            "	) nuevo_haber\n" +
                            "	FROM tbl_catalogo_ctas a\n" +
                            "	WHERE cod_cta_conta IN (SELECT cod_cta_mayor FROM tbl_catalogo_ctas b WHERE b.cod_residencial = a.cod_residencial)\n" +
                            "	AND a.cod_residencial = " + cod_residencial + "\n" +
                            "	AND a.nivel_cuenta = " + nivel + "\n" +
                            ") X";
                    System.out.println(sql);
                    rset = stmt.executeQuery(sql);
                    int updated = 0;
                    while (rset.next()){
                        String sql2 = "update tbl_catalogo_ctas set\n"
                                //+ "saldo_ant = " + rset.getString("saldo_ant") + ",\n"
                                + "debe_ant = " + rset.getString("debe_ant") + ",\n"
                                + "haber_ant = " + rset.getString("haber_ant") + ",\n"
                                //+ "saldo = " + rset.getString("saldo") + ",\n"
                                + "debe = " + rset.getString("debe") + ",\n"
                                + "haber = " + rset.getString("haber") + "\n"
                                + "where cod_residencial = '" + rset.getString("cod_residencial") +"' and cod_cta_conta = '" + rset.getString("cod_cta_conta") + "'";
                        //System.out.println(sql2);
                        stmt2.executeUpdate(sql2);
                        updated++;
                    }
                    System.out.println("Se actualizaron " + updated + " cuentas de nivel " + nivel + ".");
                    rset.close();
                }
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        
        DestroyConnection();
    }
    
    public void desmayorizar(String cod_residencial){
        InitializeConnection();
        try{
            for (int nivel = 5; nivel >= 1; nivel--)
            {
                try (Statement stmt2 = conn.createStatement()) {
                    String sql = "SELECT cod_residencial, cod_cta_conta, desc_cta_contab, nivel_cuenta, cod_tipo_cta,\n" +
                            "saldo_ant, \n" +
                            "debe_ant,\n" +
                            "haber_ant\n" +
                            "FROM\n" +
                            "(\n" +
                            "	SELECT cod_residencial, cod_cta_conta, desc_cta_contab, nivel_cuenta,\n" +
                            "	saldo_ant, cod_tipo_cta,\n" +
                            "	debe_ant,\n" +
                            "	haber_ant\n" +
                            "	FROM tbl_catalogo_ctas a\n" +
                            "	WHERE cod_cta_conta IN (SELECT cod_cta_mayor FROM tbl_catalogo_ctas b WHERE b.cod_residencial = a.cod_residencial)\n" +
                            "	AND a.cod_residencial = " + cod_residencial + "\n" +
                            "	AND a.nivel_cuenta = " + nivel + "\n" +
                            ") X";
                    //System.out.println(sql);
                    rset = stmt.executeQuery(sql);
                    int updated = 0;
                    while (rset.next()){
                        String sql2 = "update tbl_catalogo_ctas set\n"
                                //+ "saldo_ant = 0,\n"
                                + "debe_ant = 0,\n"
                                + "haber_ant = 0,\n"
                                //+ "saldo = " + rset.getString("saldo") + ",\n"
                                + "debe = " + rset.getString("debe_ant") + ",\n"
                                + "haber = " + rset.getString("haber_ant") + "\n"
                                + "where cod_residencial = '" + rset.getString("cod_residencial") +"' and cod_cta_conta = '" + rset.getString("cod_cta_conta") + "'";
                        //System.out.println(sql2);
                        stmt2.executeUpdate(sql2);
                        updated++;
                    }
                    System.out.println("Se actualizaron " + updated + " cuentas de nivel " + nivel + ".");
                    rset.close();
                }
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        
        DestroyConnection();
    }
    
    public void close(String ano_contable, String mes_contable){
        
    }
    
    public String Data(String cod_residencial, String nivel) {
        //variable que me define termino para busqueda
        
        JSONArray json;
        String _result = "[]";
        
        InitializeConnection();
        try {
            String sql =    "SELECT cod_residencial, cod_cta_conta, desc_cta_contab, nivel_cuenta,\n" +
                                "	saldo, cod_tipo_cta,\n" +
                                "	(SELECT SUM(saldo) \n" +
                                "		FROM tbl_catalogo_ctas b \n" +
                                "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                                "		AND b.cod_residencial = a.cod_residencial\n" +
                                "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                                "	) nuevo_saldo,\n" +
                                "	debe, \n" +
                                "	(SELECT SUM(debe) \n" +
                                "		FROM tbl_catalogo_ctas b \n" +
                                "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                                "		AND b.cod_residencial = a.cod_residencial\n" +
                                "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                                "	) nuevo_debe,\n" +
                                "	haber, \n" +
                                "	(SELECT SUM(haber)\n" +
                                "		FROM tbl_catalogo_ctas b \n" +
                                "		WHERE b.cod_cta_mayor LIKE CONCAT(a.cod_cta_conta,'%')\n" +
                                "		AND b.cod_residencial = a.cod_residencial\n" +
                                "		AND CONVERT(b.nivel_cuenta, SIGNED INTEGER) = CONVERT(a.nivel_cuenta, SIGNED INTEGER) + 1\n" +
                                "	) nuevo_haber\n" +
                                "	FROM tbl_catalogo_ctas a\n" +
                                "	WHERE cod_cta_conta IN (SELECT cod_cta_mayor FROM tbl_catalogo_ctas b WHERE b.cod_residencial = a.cod_residencial)\n" +
                                "	AND a.cod_residencial = " + cod_residencial + "\n" +
                                "	AND a.nivel_cuenta = " + nivel;
            
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

}