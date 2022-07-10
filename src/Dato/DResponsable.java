/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public String listar(String mensaje) {
        String tabla = "";
        Statement consulta;
        ResultSet resultado = null;
        tabla = "Content-Type: text/html; charset=\"UTF-8\"\n"
                + "\n"
                + "<h3>" + mensaje + "</h3>\n"
                + "\n"
                + "<h1>RESPONSABLES: </h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 2px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">CODIGO_ADM</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">NOMBRE</th>\n"
                + "\n"
                + "  </tr>\n"
                + "\n";

        try {
            String query = "SELECT responsable.cod_adm, responsable.nombre FROM responsable";
            conexion = new ConexionDB();
            Connection con = conexion.getConexion();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                tabla = tabla
                        + "  <tr>\n"
                        + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    tabla = tabla
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid red;\">" + resultado.getString(i + 1) + "</td>\n"
                            + "\n";
                }
                tabla = tabla
                        + "  </tr>\n"
                        + "\n";
            }
            tabla = tabla
                    + "\n"
                    + "</table>";
            consulta.close();

            con.close();
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
        }
        return tabla;
    }    
    
    public boolean existe(String cod_admin) {
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection con = conexion.getConexion();        
        ResultSet resultado = null;
        
        String sql = "SELECT * FROM responsable WHERE cod_adm = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cod_admin);
            resultado = ps.executeQuery();
            if (resultado.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
}
