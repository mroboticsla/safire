/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.safire.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author desarrollo01
 */
public class DAO {
    private Connection cn;

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    
    public void Conectar() throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safire?user=root&password=root");
            //System.out.println("Conectado");
        }catch(Exception e){
            System.out.println("Error conectando mudulo: "+e.getMessage());
            throw e;
        }
    }
    
    public void Cerrar() throws Exception{
        try{
            if(cn != null){
                if(cn.isClosed() == false){
                    cn.close();
                }
            }
        }catch(Exception e){
            System.out.println("Error insertando mudulo: "+e.getMessage());
            throw e;
        }
    }
    
}
