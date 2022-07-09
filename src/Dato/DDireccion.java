/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

    public ConexionDB getConexion() {
        return conexion;
    }

    public void setConexion(ConexionDB conexion) {
        this.conexion = conexion;
    }
    
    
 
    
}


