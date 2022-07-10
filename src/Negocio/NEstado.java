/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DEstado;

/**
 *
 * @author Vanessa
 */
public class NEstado {
    private DEstado dEst;
    private String respuesta = "";


    public NEstado() {
        dEst = new DEstado();
    }
    
    public String registrar(int id, String nombre) {
        if (Integer.toString(id).length() != 0) {
            if (nombre.length() != 0) {
                        dEst.setId(id);
                        dEst.setNombre(nombre);
                        if (dEst.registrar()) {
                            respuesta = dEst.listar("Se registró correctamente el estado");
                        } else {
                            respuesta = "No se pudo registrar el estado";
                        }                
            } else {
                respuesta = "Ingrese un nombre que no sea nulo.";
            }
        } else {
            respuesta = "Ingrese un id que no sea nulo.";
        }
        return respuesta;
    }
    
    
    public String eliminar(int id) {
        if (dEst.existe(id)) {
            dEst.setId(id);
            if (dEst.eliminar()) {
                respuesta = dEst.listar("Se eliminó correctamente el estado.");
            } else {
                respuesta = "No se pudo eliminar el estado";
            }
        } else {
            respuesta = "El estado que quiere eliminar no existe.";
        }
        return respuesta;
    }

    
    
    public String listar() {
        respuesta = dEst.listar("");
        return respuesta;
    }
}
