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
 * @author Diana Montano
 */
public class Dgrupo {
    private int id;
    private String nombre;
    private ConexionDB conexion;
    
    public Dgrupo(){
        conexion = new ConexionDB();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        String query = "INSERT INTO grupo (id, nombre) VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
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
        String query = "DELETE FROM grupo WHERE id = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se elimino " + e);
            return false;
        }
    }
    
    public static void main(String[] args) {
        Dgrupo grupo = new Dgrupo();
        grupo.id = 4;
//        grupo.nombre = "EDIFICIOS PROVINCIALES";
//        System.out.println(grupo.registrar());
        System.out.println(grupo.eliminar());
    }
    
}
