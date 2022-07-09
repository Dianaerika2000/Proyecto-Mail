/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.PreparedStatement;

/**
 *
 * @author Diana Montano
 */
public class DUsuario {
    private String ci;
    private String cod_adm;
    private String nombre;
    private String correo;
    ConexionDB conexion;
    
    public DUsuario(){
        conexion = new ConexionDB();
//        conexion.conectarBD();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    
}
