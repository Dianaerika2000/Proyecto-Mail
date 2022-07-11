/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DDireccion;

/**
 *
 * @author Vanessa
 */
public class NDireccion {
    private DDireccion dDirec;
    private String respuesta;
    
    public NDireccion() {
        dDirec = new DDireccion();
    }    
    
    public String registrar(int id, String ubicacion, String lugar, String oficina, String latitud, String longitud){
        if (Integer.toString(id).length() != 0) {
            if (ubicacion.length() != 0) {
                if (lugar.length() != 0) {
                    if (oficina.length() != 0) {
                        if (latitud.length() != 0) {
                            if (longitud.length() !=0) {
                                dDirec.setId(id);
                                dDirec.setUbicacion(ubicacion);
                                dDirec.setLugar(lugar);
                                dDirec.setOficina(oficina);
                                dDirec.setLatitud(latitud);
                                dDirec.setLongitud(longitud);
                                if (dDirec.registrar()) {
                                    respuesta = dDirec.listar("Se registro Correctamente la Direccion.");
                                } else {
                                    respuesta = "No se pudo registrar la direccion";
                                }
                            } else {
                                respuesta = "Ingrese una longitud que no sea nula";
                            }
                        } else {
                            respuesta = "Ingrese una latitud que no sea nula";
                        }
                    } else {
                        respuesta = "Ingrese una oficina que no sea nula";
                    }
                } else {
                    respuesta = "Ingrese un lugar que no sea nulo";
                }
            } else {
                respuesta = "Ingrese una direccion que no sea nula";
            }
        } else {
            respuesta = "Ingrese un id que no sea nulo";
        }
        return respuesta;
    }
    
    public String modificar(String ubicacion, String lugar, String oficina, String latitud, String longitud, int id){
        if (Integer.toString(id).length() != 0) {
            if (ubicacion.length() != 0) {
                if (lugar.length() != 0) {
                    if (oficina.length() != 0) {
                        if (latitud.length() != 0) {
                            if (longitud.length() !=0) {
                                dDirec.setUbicacion(ubicacion);
                                dDirec.setLugar(lugar);
                                dDirec.setOficina(oficina);
                                dDirec.setLatitud(latitud);
                                dDirec.setLongitud(longitud);
                                
                                dDirec.setId(id);
                                if (dDirec.modificar()) {
                                    respuesta = dDirec.listar("Se registro Correctamente la Direccion.");
                                } else {
                                    respuesta = "No se pudo registrar la direccion";
                                }
                            } else {
                                respuesta = "Ingrese una longitud que no sea nula";
                            }
                        } else {
                            respuesta = "Ingrese una latitud que no sea nula";
                        }
                    } else {
                        respuesta = "Ingrese una oficina que no sea nula";
                    }
                } else {
                    respuesta = "Ingrese un lugar que no sea nulo";
                }
            } else {
                respuesta = "Ingrese una direccion que no sea nula";
            }
        } else {
            respuesta = "Ingrese un id que no sea nulo.";
        }
        
        return respuesta;
    }
    
    
    public String eliminar(int id) {
        if (Integer.toString(id).length() != 0) {
            if (dDirec.existe(id)) {
                dDirec.setId(id);
                if (dDirec.eliminar()) {
                    respuesta = dDirec.listar("Se eliminó correctamente el evento.");
                } else {
                    respuesta = "No se pudo eliminar el evento.";
                }
            } else {
                respuesta = "El evento que quiere eliminar no existe.";
            }
        } else {
            respuesta = "No se permiten datos vacios o nulos.";
        }
        return respuesta;
    }
    
    public String listar() {
        respuesta = dDirec.listar("");
        return respuesta;
    }
}
