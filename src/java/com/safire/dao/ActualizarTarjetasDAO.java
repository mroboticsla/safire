package com.safire.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActualizarTarjetasDAO extends DAO {

    public int get_activas() {
        int _result = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT COUNT(*) FROM tbl_tarjeta_acceso_res WHERE cod_estatus = 0");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getInt(1);
            }
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden obtener las tarjetas activas: " + e.getMessage());
        }
        return _result;
    }

    public int get_inactivas() {
        int _result = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT COUNT(*) FROM tbl_tarjeta_acceso_res WHERE cod_estatus <> 0");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getInt(1);
            }
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden obtener las tarjetas inactivas: " + e.getMessage());
        }
        return _result;
    }
    
    public void actualizar() {
        int _result = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SELECT ACTUALIZAR_TARJETAS() FROM DUAL");
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                _result = rset.getInt(1);
            }
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden actualizar tarjetas: " + e.getMessage());
        }
    }

    public int actualizar_old() {
        int _result = 0;
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE tbl_tarjeta_acceso_res a, mst_residencias b, mst_residencial c\n"
                    + "SET a.cod_estatus = 1\n"
                    + "WHERE a.cod_residencial = b.cod_residencial\n"
                    + "AND a.cod_poligono = b.cod_poligono\n"
                    + "AND a.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND a.cod_residencia = b.cod_residencia\n"
                    + "AND b.cod_residencial = c.cod_residencial\n"
                    + "AND b.saldo_residencia > c.cuota_vigente\n"
                    + "AND a.cod_estatus = 0");
            _result += ps.executeUpdate();
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden actualizar tarjetas inactivas: " + e.getMessage());
        }

        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SET @row_number = 0;\n"
                    + "INSERT INTO cambio_estados_his \n"
                    + "SELECT ((SELECT IFNULL(MAX(id),0) FROM cambio_estados_his) + (@row_number:=@row_number + 1)) id, a.cardid, UPPER(IFNULL(e.nombre_residente, d.nombre_propietario)) nombre, \n"
                    + "UPPER(IFNULL(e.apellido_residente, d.apellido_propietario)) apellido, NOW() fecha, 'I' estado, 0 procesado\n"
                    + "\n"
                    + "FROM tbl_tarjeta_acceso_res a, mst_residencias b, mst_residencial c, \n"
                    + "mst_propietarios d, mst_propietarios_residentes e\n"
                    + "WHERE a.cod_residencial = b.cod_residencial\n"
                    + "AND a.cod_poligono = b.cod_poligono\n"
                    + "AND a.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND a.cod_residencia = b.cod_residencia\n"
                    + "AND d.cod_poligono = b.cod_poligono\n"
                    + "AND d.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND d.cod_residencia = b.cod_residencia\n"
                    + "AND d.cod_poligono = e.cod_poligono\n"
                    + "AND d.cod_sub_poligono = e.cod_sub_poligono\n"
                    + "AND d.cod_residencia = e.cod_residencia\n"
                    + "AND b.cod_residencial = c.cod_residencial\n"
                    + "AND b.saldo_residencia > c.cuota_vigente\n"
                    + "AND a.cod_estatus = 0");
            ps.executeUpdate();
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden actualizar tarjetas inactivas en el historico: " + e.getMessage());
        }

        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("UPDATE tbl_tarjeta_acceso_res a, mst_residencias b, mst_residencial c\n"
                    + "SET a.cod_estatus = 0\n"
                    + "WHERE a.cod_residencial = b.cod_residencial\n"
                    + "AND a.cod_poligono = b.cod_poligono\n"
                    + "AND a.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND a.cod_residencia = b.cod_residencia\n"
                    + "AND b.cod_residencial = c.cod_residencial\n"
                    + "AND b.saldo_residencia <= c.cuota_vigente\n"
                    + "AND a.cod_estatus = 1");
            _result += ps.executeUpdate();
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden actualizar tarjetas activas: " + e.getMessage());
        }
        
        try {
            this.Conectar();
            PreparedStatement ps = this.getCn().prepareStatement("SET @row_number = 0;\n"
                    + "INSERT INTO cambio_estados_his \n"
                    + "SELECT ((SELECT IFNULL(MAX(id),0) FROM cambio_estados_his) + (@row_number:=@row_number + 1)) id, a.cardid, UPPER(IFNULL(e.nombre_residente, d.nombre_propietario)) nombre, \n"
                    + "UPPER(IFNULL(e.apellido_residente, d.apellido_propietario)) apellido, NOW() fecha, 'A' estado, 0 procesado\n"
                    + "\n"
                    + "FROM tbl_tarjeta_acceso_res a, mst_residencias b, mst_residencial c, \n"
                    + "mst_propietarios d, mst_propietarios_residentes e\n"
                    + "WHERE a.cod_residencial = b.cod_residencial\n"
                    + "AND a.cod_poligono = b.cod_poligono\n"
                    + "AND a.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND a.cod_residencia = b.cod_residencia\n"
                    + "AND d.cod_poligono = b.cod_poligono\n"
                    + "AND d.cod_sub_poligono = b.cod_sub_poligono\n"
                    + "AND d.cod_residencia = b.cod_residencia\n"
                    + "AND d.cod_poligono = e.cod_poligono\n"
                    + "AND d.cod_sub_poligono = e.cod_sub_poligono\n"
                    + "AND d.cod_residencia = e.cod_residencia\n"
                    + "AND b.cod_residencial = c.cod_residencial\n"
                    + "AND b.saldo_residencia <= c.cuota_vigente\n"
                    + "AND a.cod_estatus = 1");
            ps.executeUpdate();
            this.Cerrar();
        } catch (Exception e) {
            System.out.println("No se pueden actualizar tarjetas activas en el historico: " + e.getMessage());
        }
        
        return _result;
    }
}
