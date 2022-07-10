/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Vanessa
 */
public class DResponsable {

    
    private String cod_adm;
    private String nombre;
    
    private ConexionDB conexion;
    
    public DResponsable() {
        conexion = new ConexionDB();
    }
    
    public String getCod_adm() {
        return cod_adm;
    }

    public void setCod_adm(String cod_adm) {
        this.cod_adm = cod_adm;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
        public boolean registrar(){
        PreparedStatement ps = null;
//        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "INSERT INTO responsable (cod_adm, nombre) VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, cod_adm);
            ps.setString(2, nombre);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se registro " + e);
            return false;
        } 
    }
    
    public boolean eliminar(){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "DELETE FROM responsable WHERE cod_adm = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, cod_adm);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se elimino " + e);
            return false;
        }
    }

}
