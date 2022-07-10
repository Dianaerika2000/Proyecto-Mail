/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

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
}
