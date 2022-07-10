/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Vanessa
 */
public class DDireccion {
    private int id;
    private String ubicacion;
    private String lugar;
    private String oficina;
    private String latitud;
    private String longitud;

    private ConexionDB conexion;
    
    public DDireccion() {
        conexion = new ConexionDB();
    }
    
        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    
    public boolean registrar() {
        PreparedStatement ps = null;
        conexion = new ConexionDB();
        Connection con = conexion.getConection();

        String sql = "INSERT INTO direccion (id, ubicacion, lugar, oficina, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, ubicacion);
            ps.setString(3, lugar);
            ps.setString(4, oficina);
            ps.setString(5, latitud);
            ps.setString(6, longitud);
            
            ps.execute();
            conexion.desconectarBD();
            return true;
        } catch (SQLException e) {
            System.out.println("No se registro " + e);
            return false;
        }  
    }
 
    
    public boolean modificar() {
        PreparedStatement ps = null;
        Connection con = conexion.getConexion();

        String sql = "UPDATE direccion SET ubicacion = ?, lugar = ?, oficina = ?, latitud = ?, longitud = ? WHERE Id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ubicacion);
            ps.setString(2, lugar);
            ps.setString(3, oficina);
            ps.setString(4, latitud);
            ps.setString(5, longitud);
            ps.setInt(6, id);
            ps.execute();
            return true;
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
    
    
    public boolean eliminar() {
        PreparedStatement ps = null;
        Connection con = conexion.getConexion();

        String sql = "DELETE FROM direccion WHERE Id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
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
    
    
        public boolean existe(int id) {
        PreparedStatement ps = null;
        Connection con = conexion.getConexion();
        ResultSet resultado = null;
        
        String sql = "SELECT * FROM cliente WHERE Id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
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
        
public static void main(String[] args) {
        
        DDireccion direccion = new DDireccion();
        
        direccion.id = 4;
        direccion.ubicacion = "PARANINFO UNIVERSITARIO";
        direccion.lugar = "PARANINFO UNIVERSITARIO";
        direccion.oficina = "EDIFICIO";
        direccion.latitud = "-17.78191058089551, ";
        direccion.longitud = "-63.182914829261485";
        
        /*
        direccion.codigo = "U0117A01C0114";
        inmueble.descripcion = "EDIFICIO REPUBL. DE 4 PLANTAS CON 2180.87 M2. CONSTRUIDOS. UBICADO FRENTE A LA PLAZA 24 DE SEPTIEMBRE EQUINA JUNIN. MZNO. 7 NRO. INMUEBLE 610324";
        inmueble.tipo = "A";
        */
        
        //System.out.println(inmueble.modificar());
        System.out.println(direccion.registrar());
    }

}



