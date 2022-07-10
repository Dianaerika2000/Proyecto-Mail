/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DResponsable;

/**
 *
 * @author Vanessa
 */
public class NResponsable {
    private DResponsable dResp;
    private String respuesta = "";
    
    public NResponsable() {
        dResp = new DResponsable();
    }
    
    public String registrar(String cod_adm, String nombre) {
        if (cod_adm.length() != 0) {
            if (nombre.length() != 0) {
                        dResp.setCod_adm(cod_adm);
                        dResp.setNombre(nombre);
                        if (dResp.registrar()) {
                            respuesta = dResp.listar("Se registró correctamente.");
                        } else {
                            respuesta = "No se pudo registrar.";
                        }                
            } else {
                respuesta = "Ingrese un nombre que no sea nulo.";
            }
        } else {
            respuesta = "Ingrese un cod_adm que no sea nulo.";
        }
        return respuesta;
    }
    
    
    public String eliminar(String cod_adm) {
        if (dResp.existe(cod_adm)) {
            dResp.setCod_adm(cod_adm);
            if (dResp.eliminar()) {
                respuesta = dResp.listar("Se eliminó correctamente.");
            } else {
                respuesta = "No se pudo eliminar al responsable";
            }
        } else {
            respuesta = "El responsable que quiere eliminar no existe.";
        }
        return respuesta;
    }

    
    
    public String listar() {
        respuesta = dResp.listar("");
        return respuesta;
    }
}
