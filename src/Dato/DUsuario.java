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
 * @author Diana Montano
 */
public class DUsuario {
    private String ci;
    private String cod_adm;
    private String nombre;
    private String apellido;
    private String correo;
    ConexionDB conexion;
    
    public DUsuario(String ci, String cod_adm, String nombre, String apellido, String correo){
        this.ci = ci;
        this.cod_adm = cod_adm;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
//        conexion = new ConexionDB();
        
    }
    public DUsuario(){
        conexion = new ConexionDB();
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public boolean registrar(){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "INSERT INTO usuario (ci, cod_adm, nombre, apellido, correo) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, ci);
            ps.setString(2, cod_adm);
            ps.setString(3, nombre);
            ps.setString(4, apellido);
            ps.setString(5, correo);
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
        String query = "DELETE FROM usuario WHERE ci = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, ci);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se elimino " + e);
            return false;
        }
    }
    
    public boolean modificar(){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "UPDATE usuario set nombre = ?, apellido = ?, correo = ? WHERE ci = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, ci);
            ps.executeUpdate();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se registro " + e);
            return false;
        } 
    }
    public boolean existe(String ci){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        ResultSet resultado = null;
        String query = "SELECT * FROM usuario WHERE ci = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, ci);
            resultado = ps.executeQuery();
            if (resultado.next()) {
                connection.close();
                return true;
            } else {
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public String listar(String mensaje){
        conexion = new ConexionDB();
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "Content-Type: text/html; charset=\"UTF-8\"\n"
                + "\n"
                + "<h3>" + mensaje + "</h3>\n"
                + "\n"
                + "<h1>USUARIOS: </h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 2px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">CI</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">COD_ADM</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">APELLIDO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">CORREO</th>\n"
                + "\n"
                + "  </tr>\n"
                + "\n";
        try {
            String query = "SELECT * FROM usuario";
            Connection connection = conexion.getConection();
            Statement stmt = connection.createStatement();
            resultado = stmt.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantCol = rsmd.getColumnCount();
            while (resultado.next()) {                
                tabla = tabla
                        + " <tr>\n"
                        + "\n";
                for(int i = 0; i< cantCol; i++){
                    tabla = tabla = tabla
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
            
            connection.close();
        } catch (SQLException e) {
            tabla = "No se puedieron listat los datos";
        }
        return tabla;
    }
    
    
    
    
    
    public static void main(String[] args) {
//        DUsuario usuario = new DUsuario("7639771", "76397", "Dayly", "Montero", "daylymontero@gmail.com");
        DUsuario usuario = new DUsuario();
        usuario.ci = "7639770";
        usuario.nombre = "Diana";
        usuario.apellido = "Montano";
        usuario.correo = "dianaerika.montano@gmail.com";
        System.out.println(usuario.modificar());
        
    }

    
    
}
