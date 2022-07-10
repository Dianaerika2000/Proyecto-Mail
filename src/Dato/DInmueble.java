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
public class DInmueble {
    private String codigo;
    private String descripcion;
    private String cod_asignacion;
    private String tipo;
    private String id_usuario_fk;
    private String id_responsable_fk;
    private int id_estado_fk;
    private int id_direccion_fk;
    private int id_grupo_fk;
    private ConexionDB conexion;

    public DInmueble() {
        conexion = new ConexionDB();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCod_asignacion() {
        return cod_asignacion;
    }

    public void setCod_asignacion(String cod_asignacion) {
        this.cod_asignacion = cod_asignacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId_usuario_fk() {
        return id_usuario_fk;
    }

    public void setId_usuario_fk(String id_usuario_fk) {
        this.id_usuario_fk = id_usuario_fk;
    }

    public String getId_responsable_fk() {
        return id_responsable_fk;
    }

    public void setId_responsable_fk(String id_responsable_fk) {
        this.id_responsable_fk = id_responsable_fk;
    }

    public int getId_estado_fk() {
        return id_estado_fk;
    }

    public void setId_estado_fk(int id_estado_fk) {
        this.id_estado_fk = id_estado_fk;
    }

    public int getId_direccion_fk() {
        return id_direccion_fk;
    }

    public void setId_direccion_fk(int id_direccion_fk) {
        this.id_direccion_fk = id_direccion_fk;
    }

    public int getId_grupo_fk() {
        return id_grupo_fk;
    }

    public void setId_grupo_fk(int id_grupo_fk) {
        this.id_grupo_fk = id_grupo_fk;
    }
    
    public boolean registrar(){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "INSERT INTO inmueble (codigo, descripcion, cod_asignacion, tipo, id_usuario_fk, id_responsable_fk, id_estado_fk, id_direccion_fk, id_grupo_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, codigo);
            ps.setString(2, descripcion);
            ps.setString(3, cod_asignacion);
            ps.setString(4, tipo);
            ps.setString(5, id_usuario_fk);
            ps.setString(6, id_responsable_fk);
            ps.setInt(7, id_estado_fk);
            ps.setInt(8, id_direccion_fk);
            ps.setInt(9, id_grupo_fk);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se registro " + e);
            return false;
        } 
    }
    
    public boolean modificar(){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        String query = "UPDATE inmueble set descripcion = ?, cod_asignacion = ?, tipo = ?, id_usuario_fk = ?, id_responsable_fk = ?, id_estado_fk = ?, id_direccion_fk = ?, id_grupo_fk = ?  WHERE codigo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, descripcion);
            ps.setString(2, cod_asignacion);
            ps.setString(3, tipo);
            ps.setString(4, id_usuario_fk);
            ps.setString(5, id_responsable_fk);
            ps.setInt(6, id_estado_fk);
            ps.setInt(7, id_direccion_fk);
            ps.setInt(8, id_grupo_fk);
            ps.setString(9, codigo);
            ps.executeUpdate();
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
        String query = "DELETE FROM inmueble WHERE codigo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, codigo);
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se elimino " + e);
            return false;
        }
    }
    public boolean existe(String codigo){
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection connection = conexion.getConection();
        ResultSet resultado = null;
        String query = "SELECT * FROM inmueble WHERE codigo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, codigo);
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
                + "<h1>INMUEBLE: </h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 2px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">CODIGO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">DESCRIPCION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">COD_ASIG</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">TIPO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">ID_USUARIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">ID_RESPONSABLE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">ID_ESTADO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">ID_DIRECCION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #c96969; color: white; border: 1px solid red;\">ID_GRUPO</th>\n"
                + "\n"
                + "  </tr>\n"
                + "\n";
        try {
            String query = "SELECT * FROM inmueble";
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
        
        DInmueble inmueble = new DInmueble();
        /*
        inmueble.codigo = "U0117A01C0114";
        inmueble.descripcion = "EDIFICIO REPUBL. DE 4 PLANTAS CON 2180.87 M2. CONSTRUIDOS. UBICADO FRENTE A LA PLAZA 24 DE SEPTIEMBRE EQUINA JUNIN. MZNO. 7 NRO. INMUEBLE 610324";
        inmueble.cod_asignacion = "6896";
        inmueble.tipo = "A";
        inmueble.id_usuario_fk = "9039006";
        inmueble.id_responsable_fk = "9039";
        inmueble.id_estado_fk = 1;
        inmueble.id_direccion_fk = 1;
        inmueble.id_grupo_fk = 1;
        */
        inmueble.codigo = "U0117A01C0114";
        inmueble.descripcion = "EDIFICIO REPUBL. DE 4 PLANTAS CON 2180.87 M2. CONSTRUIDOS. UBICADO FRENTE A LA PLAZA 24 DE SEPTIEMBRE EQUINA JUNIN. MZNO. 7 NRO. INMUEBLE 610324";
        inmueble.tipo = "A";
        
        System.out.println(inmueble.modificar());
//        System.out.println(inmueble.registrar());
    }
    
    
    
    
    
}
