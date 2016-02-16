package Masters_Data;

import Database.Connect;
import Database.ResultsetToJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;

public class ccalendar {

    public ccalendar() {
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

    public String Data(Boolean activeOnly) {
        //variable que me define termino para busqueda

        JSONArray json = new JSONArray();
        String _result = "[]";

        InitializeConnection();
        try {
            String sql = "SELECT cod_residencial,\n"
                    + "    ano_periodo,\n"
                    + "    mes_periodo,\n"
                    + "    case when mes_periodo = 1"
                    + "           then 'Enero'\n"
                    + "       when mes_periodo = 2"
                    + "           then 'Febrero'\n"
                    + "       when mes_periodo = 3"
                    + "           then 'Marzo'\n"
                    + "       when mes_periodo = 4"
                    + "           then 'Abril'\n"
                    + "       when mes_periodo = 5"
                    + "           then 'Mayo'\n"
                    + "       when mes_periodo = 6"
                    + "           then 'Junio'\n"
                    + "       when mes_periodo = 7"
                    + "           then 'Julio'\n"
                    + "       when mes_periodo = 8"
                    + "           then 'Agosto'\n"
                    + "       when mes_periodo = 9"
                    + "           then 'Septiembre'\n"
                    + "       when mes_periodo = 10"
                    + "           then 'Octubre'\n"
                    + "       when mes_periodo = 11"
                    + "           then 'Noviembre'\n"
                    + "       when mes_periodo = 12"
                    + "           then 'Diciembre'\n"
                    + "    end nom_mes_periodo,\n"
                    + "    DATE_FORMAT(fecha_inicio, '%d/%m/%Y') fecha_inicio,\n"
                    + "    DATE_FORMAT(fecha_fin, '%d/%m/%Y') fecha_fin,\n"
                    + "    activo,\n"
                    + "    case when activo = 1 then "
                    + "       CONCAT('<img src=\"../../resources/images/check.png\" border=0 width=\"16\" height=\"16\"/>') "
                    + "    end nom_activo,\n"
                    + "    cerrado,\n"
                    + "    case when cerrado = 1 then "
                    + "       CONCAT('<img src=\"../../resources/images/locked.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:openMonth();\" />') "
                    + "    else "
                    + "       CONCAT('<img src=\"../../resources/images/unlocked.png\" border=0 width=\"16\" height=\"16\" href=\"javascript:void(0);\" onclick=\"javascript:closeMonth();\" />') "
                    + "    end nom_cerrado,\n"
                    + "    fecha_creacion,\n"
                    + "    cod_usuario,\n"
                    + "    CONVERT(CONCAT(cod_residencial,',',ano_periodo,',',mes_periodo), CHAR(50)) combo_cod,\n"
                    + "    CONVERT(CONCAT(ano_periodo,' - ',"
                    + "       case when mes_periodo = 1\n"
                    + "           then 'Enero'\n"
                    + "       when mes_periodo = 2"
                    + "           then 'Febrero'\n"
                    + "       when mes_periodo = 3"
                    + "           then 'Marzo'\n"
                    + "       when mes_periodo = 4"
                    + "           then 'Abril'\n"
                    + "       when mes_periodo = 5"
                    + "           then 'Mayo'\n"
                    + "       when mes_periodo = 6"
                    + "           then 'Junio'\n"
                    + "       when mes_periodo = 7"
                    + "           then 'Julio'\n"
                    + "       when mes_periodo = 8"
                    + "           then 'Agosto'\n"
                    + "       when mes_periodo = 9"
                    + "           then 'Septiembre'\n"
                    + "       when mes_periodo = 10"
                    + "           then 'Octubre'\n"
                    + "       when mes_periodo = 11"
                    + "           then 'Noviembre'\n"
                    + "       when mes_periodo = 12"
                    + "           then 'Diciembre'\n"
                    + "    end), CHAR(50)) combo_nom\n"
                    + "FROM tbl_calendario_contable a\n";

            if (activeOnly) {
                sql += "where activo = 1 and cerrado = 0\n";
            }

            sql += "order by ano_periodo, mes_periodo asc\n";

            System.out.println(sql);
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

    public void openMonth(String cod_residencial, String ano_periodo, String mes_periodo, String cod_usuario) {
        InitializeConnection();
        try {
            String maxValue = "";
            String sql = "select max(corr_partida) + 1 from tbl_partida_diaria";
            System.out.println(sql);
            ResultSet rset = stmt.executeQuery(sql);
            if (rset.next()) {
                maxValue = rset.getString(1);
            }
            rset.close();

            if (!maxValue.equals("")) {
                sql = "INSERT INTO tbl_partida_diaria\n"
                        + "(cod_residencial,\n"
                        + "	    corr_partida,\n"
                        + "	    ano_contable,\n"
                        + "	    fecha_transaccion,\n"
                        + "	    ano_transaccion,\n"
                        + "	    mes_transaccion,\n"
                        + "	    mes_contable,\n"
                        + "	    fecha_generacion,\n"
                        + "	    usuario_creacion,\n"
                        + "	    nombre_partida,\n"
                        + "	    estado)\n"
                        + "SELECT DISTINCT " + cod_residencial + " cod_residencial, \n"
                        + "	" + maxValue + " corr_partida, \n"
                        + "	" + ano_periodo + " ano_contable, \n"
                        + "	NOW() fecha_transaccion,  \n"
                        + "	DATE_FORMAT(NOW(), '%Y') AS ano_transaccion,\n"
                        + "	DATE_FORMAT(NOW(), '%m') AS mes_transaccion,\n"
                        + "	" + mes_periodo + " mes_contable,\n"
                        + "	NOW() fecha_generacion,\n"
                        + "	'" + cod_usuario + "' usuario_creacion,\n"
                        + "	'Apertura " + mes_periodo + "-" + ano_periodo + "' AS nombre_partida,\n"
                        + "	'D' estado\n"
                        + "FROM tbl_partida_diaria";
                System.out.println(sql);
                if (stmt.executeUpdate(sql) > 0) {
                    int counter = 0;
                    Statement stmt2 = conn.createStatement();
                    Statement stmt3 = conn.createStatement();

                    sql = "SELECT cod_cta_conta_cxc FROM mst_residencias b WHERE b.cod_residencial = " + cod_residencial;
                    System.out.println(sql);
                    ResultSet rset2 = stmt2.executeQuery(sql);
                    while (rset2.next()) {
                        counter++;
                        sql = "INSERT INTO tbl_partida_diaria_mov\n"
                                + "                    (cod_residencial,\n"
                                + "                    corr_partida,\n"
                                + "                    ano_contable,\n"
                                + "                    corr_movi,\n"
                                + "                    cod_cta_conta,\n"
                                + "                    debe,\n"
                                + "                    haber,\n"
                                + "                    desc_movimiento,\n"
                                + "                    referencia)\n"
                                + " VALUES (" + cod_residencial + ", " + maxValue + ", " + ano_periodo + ", " + counter + ", " + rset2.getString("cod_cta_conta_cxc")
                                + ", " + 0 + ", (SELECT cuota_vigente FROM  mst_residencial b WHERE cod_residencial = " + cod_residencial + "), 'Cuota " + mes_periodo + "-" + ano_periodo + "', '')";
                        System.out.println(sql);
                        stmt3.executeUpdate(sql);
                    }
                    rset2.close();
                    stmt2.close();
                }
                try {
                    Statement stmt3 = conn.createStatement();
                    sql = "UPDATE mst_residencial SET mes_vigente = " + mes_periodo + ", ano_vigente = " + ano_periodo + " WHERE cod_residencial = " + cod_residencial + "";
                    System.out.println(sql);
                    stmt3.executeUpdate(sql);
                    stmt3.close();
                } catch (Exception ex) {
                    System.err.println("Error al actualizar periodo vigente: " + ex.toString());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }

    public void add(String cod_residencial, String ano_periodo, String mes_periodo, String fecha_inicio, String fecha_fin, String activo, String cod_usuario) {
        InitializeConnection();
        try {
            String sql = "insert into tbl_calendario_contable (cod_residencial, ano_periodo, mes_periodo, fecha_inicio, fecha_fin, activo, cerrado, cod_usuario, fecha_creacion) "
                    + "values ('" + cod_residencial + "','" + ano_periodo + "','" + mes_periodo + "',STR_TO_DATE('" + fecha_inicio + "', '%d/%m/%Y'),STR_TO_DATE('" + fecha_fin + "', '%d/%m/%Y'),'" + activo + "',0,'" + cod_usuario + "', NOW())";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            if (activo.equals("1")) {

                sql = "UPDATE mst_residencias a SET saldo_residencia = saldo_residencia + (SELECT cuota_vigente FROM  mst_residencial b WHERE a.cod_residencial = b.cod_residencial) \n"
                        + "WHERE a.cod_residencial = '" + cod_residencial + "'";
                System.out.println(sql);
                stmt.executeUpdate(sql);

                /*
                 sql = "UPDATE tbl_catalogo_ctas a SET debe = debe + (SELECT cuota_vigente FROM  mst_residencial b WHERE a.cod_residencial = b.cod_residencial) \n" +
                 "WHERE a.cod_residencial = '" + cod_residencial + "' AND cod_cta_conta IN (SELECT cod_cta_conta_cxc FROM mst_residencias b WHERE b.cod_residencial = a.cod_residencial)";
                 System.out.println(sql);
                 stmt.executeUpdate(sql);
                 */
                openMonth(cod_residencial, ano_periodo, mes_periodo, cod_usuario);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }

    public void edit(String cod_residencial, String ano_periodo, String mes_periodo, String activo, String cod_usuario) {
        InitializeConnection();
        try {
            String sql = "update tbl_calendario_contable set activo = '" + activo + "' "
                    + "where cod_residencial = '" + cod_residencial + "' and ano_periodo = '" + ano_periodo + "' and mes_periodo='" + mes_periodo + "'";
            //System.out.println(sql);
            stmt.executeUpdate(sql);

            if (activo.equals("1")) {

                sql = "UPDATE mst_residencias a SET saldo_residencia = saldo_residencia + (SELECT cuota_vigente FROM  mst_residencial b WHERE a.cod_residencial = b.cod_residencial) \n"
                        + "WHERE a.cod_residencial = '" + cod_residencial + "'";
                System.out.println(sql);
                stmt.executeUpdate(sql);

                /*
                 sql = "UPDATE tbl_catalogo_ctas a SET debe = debe + (SELECT cuota_vigente FROM  mst_residencial b WHERE a.cod_residencial = b.cod_residencial) \n" +
                 "WHERE a.cod_residencial = '" + cod_residencial + "' AND cod_cta_conta IN (SELECT cod_cta_conta_cxc FROM mst_residencias b WHERE b.cod_residencial = a.cod_residencial)";
                 System.out.println(sql);
                 stmt.executeUpdate(sql);
                 */
                openMonth(cod_residencial, ano_periodo, mes_periodo, cod_usuario);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }

    public void close(String cod_residencial, String ano_periodo, String mes_periodo) {
        InitializeConnection();
        try {
            String sql = "update tbl_calendario_contable set cerrado = 1 "
                    + "where cod_residencial = '" + cod_residencial + "' and ano_periodo = '" + ano_periodo + "' and mes_periodo='" + mes_periodo + "'";
            //System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DestroyConnection();
    }
}
