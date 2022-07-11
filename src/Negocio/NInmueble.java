/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.DInmueble;

/**
 *
 * @author Vanessa
 */
public class NInmueble {
    private DInmueble dInmueble;
    private String respuesta;
    
    public NInmueble(){
        dInmueble = new DInmueble();
    }
    
    public String registrar(String codigo, String descripcion, String cod_asignacion, String tipo, String id_usuario, String id_responsable, int id_estado, int id_direccion, int id_grupo){
        if (codigo.length() != 0) {
            if (descripcion.length() != 0) {
                if (cod_asignacion.length() != 0) {
                    if (tipo.length() != 0) {
                        if (id_usuario.length() != 0) {
                            if (id_responsable.length() != 0) {
                                if (Integer.toString(id_estado).length() != 0) {
                                    if (Integer.toString(id_direccion).length() != 0) {
                                        if (Integer.toString(id_grupo).length() != 0) {
                                            dInmueble.setCodigo(codigo);
                                            dInmueble.setDescripcion(descripcion);
                                            dInmueble.setCod_asignacion(cod_asignacion);
                                            dInmueble.setTipo(tipo);
                                            dInmueble.setId_usuario_fk(id_usuario);
                                            dInmueble.setId_responsable_fk(id_responsable);
                                            dInmueble.setId_estado_fk(id_estado);
                                            dInmueble.setId_direccion_fk(id_direccion);
                                            dInmueble.setId_grupo_fk(id_grupo);
                                            if (dInmueble.registrar()) {
                                                respuesta = dInmueble.listar("Inmueble registrado exitosamente.");
                                            } else {
                                                respuesta = "No se logro registrar el inmueble.";
                                            }
                                        } else {
                                            respuesta = "Ingrese un id_grupo que no sea nulo.";
                                        }
                                    } else {
                                        respuesta = "Ingrese un id_direccion que no sea nulo.";
                                    }
                                } else {
                                   respuesta = "Ingrese un id_estado que no sea nulo.";
                                }
                            } else {
                                respuesta = "Ingrese un id_responsable que no sea nulo.";
                            }
                        } else {
                            respuesta = "Ingrese un id_usuario valido.";
                        }
                    } else {
                        respuesta = "Ingrese un tipo que no sea nulo.";
                    }
                } else {
                    respuesta = "Ingrese un codigo de asigancion que no sea nulo.";
                }
            } else {
                respuesta = "Ingrese una descripcion que no sea nula.";
            }
        } else {
            respuesta = "Ingrese un codigo que no sea nulo.";
        }
        return respuesta;
    }
    
    public String eliminar(String codigo){
        if (codigo.length() != 0) {
            if (dInmueble.existe(codigo)) {
                dInmueble.setCod_asignacion(codigo);
                if (dInmueble.eliminar()) {
                    respuesta = dInmueble.listar("Inmueble elimina exitosamente.");
                } else {
                    respuesta = "No se logro eliminar el inmueble.";
                }
            } else {
                respuesta = "El inmueble que quiere eliminar no existe.";
            }
        } else {
            respuesta = "No se permite dato nulo.";
        }
        return respuesta;
    }
    
    public String listar() {
        respuesta = dInmueble.listar("");
        return respuesta;
    }
    
    public String modificar(String codigo, String descripcion, String cod_asignacion, String tipo, String id_usuario, String id_responsable, int id_estado, int id_direccion, int id_grupo){
        if (codigo.length() != 0) {
            if (descripcion.length() != 0) {
                if (cod_asignacion.length() != 0) {
                    if (tipo.length() != 0) {
                        if (id_usuario.length() != 0) {
                            if (id_responsable.length() != 0) {
                                if (Integer.toString(id_estado).length() != 0) {
                                    if (Integer.toString(id_direccion).length() != 0) {
                                        if (Integer.toString(id_grupo).length() != 0) {
                                            dInmueble.setCodigo(codigo);
                                            dInmueble.setDescripcion(descripcion);
                                            dInmueble.setCod_asignacion(cod_asignacion);
                                            dInmueble.setTipo(tipo);
                                            dInmueble.setId_usuario_fk(id_usuario);
                                            dInmueble.setId_responsable_fk(id_responsable);
                                            dInmueble.setId_estado_fk(id_estado);
                                            dInmueble.setId_direccion_fk(id_direccion);
                                            dInmueble.setId_grupo_fk(id_grupo);
                                            if (dInmueble.modificar()) {
                                                respuesta = dInmueble.listar("Inmueble modificado exitosamente.");
                                            } else {
                                                respuesta = "No se logro modificar el inmueble.";
                                            }
                                        } else {
                                            respuesta = "Ingrese un id_grupo que no sea nulo.";
                                        }
                                    } else {
                                        respuesta = "Ingrese un id_direccion que no sea nulo.";
                                    }
                                } else {
                                   respuesta = "Ingrese un id_estado que no sea nulo.";
                                }
                            } else {
                                respuesta = "Ingrese un id_responsable que no sea nulo.";
                            }
                        } else {
                            respuesta = "Ingrese un id_usuario valido.";
                        }
                    } else {
                        respuesta = "Ingrese un tipo que no sea nulo.";
                    }
                } else {
                    respuesta = "Ingrese un codigo de asigancion que no sea nulo.";
                }
            } else {
                respuesta = "Ingrese una descripcion que no sea nula.";
            }
        } else {
            respuesta = "Ingrese un codigo que no sea nulo.";
        }
        return respuesta;
    }
    
     
    
    
}
