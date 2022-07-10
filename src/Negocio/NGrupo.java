/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.Dgrupo;

/**
 *
 * @author Vanessa
 */
public class NGrupo {
    private Dgrupo dGrupo;
    private String respuesta;
    
    public NGrupo(){
        dGrupo = new Dgrupo();
    }
    
    public String registrar(int id, String nombre){
        if(Integer.toString(id).length() != 0){
            if (nombre.length() != 0) {
                dGrupo.setId(id);
                dGrupo.setNombre(nombre);
                if (dGrupo.registrar()) {
                    respuesta = dGrupo.listar("Grupo registrado correctamente");
                } else {
                    respuesta = "No se logro registrar el grupo";
                }
            } else {
                respuesta = "Ingrese un nombre que no sea nulo";
            }
        }else{
            respuesta = "Ingrese un id que no sea nulo";
        }
        return respuesta;
    }
    
    public String eliminar(int id){
        if (Integer.toString(id).length() != 0) {
            if (dGrupo.existe(id)) {
                dGrupo.setId(id);
                if (dGrupo.eliminar()) {
                    respuesta = dGrupo.listar("Grupo eliminado exitosamente");
                } else {
                    respuesta = "No se logro eliminar el grupo";
                }
            } else {
                respuesta = "El grupo que desea eliminar no existe";
            }
        } else {
            respuesta = "Ingrese el id del grupo";
        }
        return respuesta;
    }
    
    public String listar(){
        respuesta = dGrupo.listar("");
        return respuesta;
    }
    
    public String modificar(int id, String nombre){
        if (dGrupo.existe(id)) {
            dGrupo.setNombre(nombre);
            if(dGrupo.modificar()){
                respuesta = dGrupo.listar("Informacion del grupo actualizada");
            } else{
                respuesta = "No se logro actualizar la informacion";
            }
        } else {
            respuesta = "El grupo a actualizar no existe";
        }
        return respuesta;
    }
    
}
