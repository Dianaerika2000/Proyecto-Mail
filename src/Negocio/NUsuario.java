/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DUsuario;

/**
 *
 * @author Vanessa
 */
public class NUsuario {
    private DUsuario dUsuario;
    private String respuesta;
    
    public NUsuario(){
        dUsuario = new DUsuario();
    }
    
    public String registrar(String ci, String cod_adm, String nombre, String apellido, String correo ){
        if(ci.length() != 0){
            if (cod_adm.length() != 0) {
                if (nombre.length() != 0) {
                    if(apellido.length() != 0){
                        if(correo.length() != 0){
                            dUsuario.setCi(ci);
                            dUsuario.setCod_adm(cod_adm);
                            dUsuario.setNombre(nombre);
                            dUsuario.setApellido(apellido);
                            dUsuario.setCorreo(correo);
                            if (dUsuario.registrar()) {
                                respuesta = dUsuario.listar("Usuario registrado exitosamente");
                            } else {
                                respuesta = "No se logro registrar el usuario";
                            }
                        }else{
                            respuesta = "Ingrese un correo que no sea nulo";
                        }
                    }else{
                        respuesta = "Ingrese un apellido que no sea nulo";
                    }
                } else {
                    respuesta = "Ingrese un nombre que no sea nulo";
                }
            } else {
                respuesta = "Ingrese un cod_adm que no sea nulo";
            }
        }else{
            respuesta = "Ingrese un ci que no sea nullo";
        }
        return respuesta;
    }
    
    public String eliminar(String ci){
        if (dUsuario.existe(ci)) {
            dUsuario.setCi(ci);
            if(dUsuario.eliminar()){
                respuesta = dUsuario.listar("Usuario eliminado correctamente");
            } else{
                respuesta = "No se logro eliminar al usuario";
            }
        } else {
            respuesta = "El usuario a eliminar no existe";
        }
        return respuesta;
    }
    
    public String listar(){
        respuesta = dUsuario.listar("");
        return respuesta;
    }
    
    public String modificar(String ci, String nombre, String apellido, String correo){
        if (dUsuario.existe(ci)) {
            dUsuario.setCi(ci);
            dUsuario.setNombre(nombre);
            dUsuario.setApellido(apellido);
            dUsuario.setCorreo(correo);
            if(dUsuario.modificar()){
                respuesta = dUsuario.listar("Informacion del usuario actualizada");
            } else{
                respuesta = "No se logro actualizar la informacion";
            }
        } else {
            respuesta = "El usuario a actualizar no existe";
        }
        return respuesta;
    }
}
