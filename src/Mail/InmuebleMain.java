/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mail;

import Negocio.NUsuario;
import Negocio.NGrupo;
import Negocio.NEstado;
import Negocio.NDireccion;
import Negocio.NResponsable;
import Negocio.NInmueble;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diana Montano
 */
public class InmuebleMain {
    /**
     * VARIABLES GLOBALES
     */
    private String servidor = "mail.tecnoweb.org.bo";
    private String mailReceptor = "grupo03sc@tecnoweb.org.bo";
    private String usuario = "grupo03sc";
    private String contrasena = "grup003grup003";
    private String mailEmisor;

    
    private ClienteSMTP clienteSMTP;
    private ClientePOP clientePOP;
    
    
    
    //1.1 Usuario
    private NUsuario nUsuario;
    
    //1.2 Inmueble
    private NInmueble nInmueble;
    
    //1.3 Grupo
    private NGrupo nGrupo;
    
    //1.4 Direccion
    private NDireccion nDireccion;
    
    //1.5 Estado
    private NEstado nEstado;
    
    //1.6 Responsable
    private NResponsable nResponsable;
    
    
    
    
    
    
    
    
    
    public InmuebleMain(){
        this.nUsuario = new NUsuario();
        this.nResponsable = new NResponsable();
        this.nGrupo = new NGrupo();
        this.nEstado = new NEstado();
        this.nDireccion = new NDireccion();
        this.nInmueble = new NInmueble();
    }
    
    public void Opciones(String opcion) {
        opcion = opcion.trim();
        String[] partesSubject = opcion.split("\\[");
        String encabezado = partesSubject[0];
        String cuerpo[] = partesSubject[1].split("\\]");
        String datos[] = null;
        if (cuerpo.length != 0) {
            datos = cuerpo[0].split("\\,");
            for (int i = 0; i < datos.length; i++) {
                datos[i] = datos[i].trim();
            }
        }

        switch (encabezado) {
            // CU1: Gestionar Usuarios
            
            // 1.1: GESTIONAR USUARIO - USER
            case "REGUSER":
                this.RegistrarUsuario(datos);
                break;
            case "DELUSER":
                this.EliminarUsuario(datos);
                break;
            case "LISTUSER":
                this.ListarUsuario();
                break;
            case "EDITUSER":
                this.ModificarDir(datos);
               break;
               
            // CU2: GESTIONAR INMUEBLE - INM
            case "REGINM":
                this.RegistrarInmueble(datos);
                break;
            case "DELINM":
                this.EliminarInmueble(datos);
                break;
            case "LISTINM":
                this.EliminarInmueble(datos);
                break;
            case "EDITINM":
                this.EliminarInmueble(datos);
                break;
            
                       
            // CU3: GESTIONAR GRUPO - GRUP
            case "REGGRUP":
                this.RegistrarGrupo(datos);
                break;
            case "DELGRUP":
                this.EliminarGrupo(datos);
                break;
            case "LISTGRUP":
                this.ListarGrupo();
                break;
            case "EDITGRUP":
                this.ModificarGrupo(datos);
                break;
            
            // CU4: GESTIONAR DIRECCION - DIR
            case "REGDIR":
                this.RegistrarDir(datos);
                break;
            case "DELDIR":
                this.EliminarDir(datos);
                break;
            case "LISTDIR":
                this.EliminarDir(datos);
                break;
            case "EDITDIR":
                this.ModificarDir(datos);
                break;
            
             // CU5: GESTIONAR ESTADO - EST
            case "REGEST":
                this.RegistrarEstado(datos);
                break;
            case "DELEST":
                this.EliminarEstado(datos);
                break;
            case "LISTEST":
                this.ListarEstado();
                break;
            case "EDITEST":
                this.ModificarEstado(datos);
                break;  
            
            // CU6: GESTIONAR RESPONSABLE - RES
            case "REGRES":
                this.RegistrarResponsable(datos);
                break;
            case "DELRES":
                this.EliminarResponsable(datos);
                break;
            case "LISTRES":
                this.ListarResponsable();
                break;
            case "EDITRES":
                this.ModificarResponsable(datos);
                break;
            default:
                String s = "La petición '" + encabezado + "' es incorrecta.";
                this.sendMail(s);
                break;
        }
    }
    
    /**
     * LLAMADAS AL NEGOCIO
     */
    
    
    //CU1: Gestionar Usuario
    private void RegistrarUsuario(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 5 || datos.length > 5) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 1 ? "CI no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Cod_adm no valido \n" : "";
            respuesta += datos[2].length() < 1 ? "Nombre no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "Apellido no valido \n" : "";
            respuesta += datos[4].length() < 1 ? "Correo no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nUsuario.registrar(datos[0], datos[1], datos[2], datos[3], datos[4]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void EliminarUsuario(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += datos[0].length() < 5? "CI no valido" : "";
                if (respuesta.length() == 0) {
                    respuesta = this.nUsuario.eliminar(datos[0]);
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void ListarUsuario() {
        String respuesta = "";   
            try {
                if (respuesta.length() == 0) {
                    respuesta =this.nUsuario.listar();
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void ModificarUsuario(String[] datos) {
        if (datos == null) {
            sendMail("No se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 5 || datos.length > 5) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 1 ? "CI no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "cod_adm no valido \n" : "";
            respuesta += datos[2].length() < 1 ? "nombre no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "apellido no valido \n" : "";
            respuesta += datos[4].length() < 0 ? "correo no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nUsuario.modificar(datos[0], datos[1], datos[2], datos[3], datos[4]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    //CU2: Gestionar Inmueble
    private void RegistrarInmueble(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 9 || datos.length > 9) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 12 ? "Codigo no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Descripcion no valido \n" : "";
            respuesta += datos[2].length() < 1 ? "Cod. Asignacion no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "Tipo no valido \n" : "";
            respuesta += datos[4].length() < 1 ? "ID_usuario no valido \n" : "";
            respuesta += datos[5].length() < 1 ? "ID_responsable no valido \n" : "";
            respuesta += !esNumero(datos[6]) ? "ID_estado no valido \n" : "";
            respuesta += !esNumero(datos[7]) ? "ID_direccion no valido \n" : "";
            respuesta += !esNumero(datos[8]) ? "ID_grupo no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nInmueble.registrar(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], Integer.parseInt(datos[6]), Integer.parseInt(datos[7]), Integer.parseInt(datos[8]));
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ModificarInmueble(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 9 || datos.length > 9) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 13 ? "Codigo no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Descripcion no valido \n" : "";
            respuesta += datos[2].length() < 1 ? "Cod. Asignacion no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "Tipo no valido \n" : "";
            respuesta += datos[4].length() < 1 ? "ID_usuario no valido \n" : "";
            respuesta += datos[5].length() < 1 ? "ID_responsable no valido \n" : "";
            respuesta += !esNumero(datos[6]) ? "ID_estado no valido \n" : "";
            respuesta += !esNumero(datos[7]) ? "ID_direccion no valido \n" : "";
            respuesta += !esNumero(datos[8]) ? "ID_grupo no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nInmueble.modificar(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], Integer.parseInt(datos[6]), Integer.parseInt(datos[7]), Integer.parseInt(datos[8]));
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void EliminarInmueble(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += datos[0].length() < 13 ? "Codigo no valido" : "";
                if (respuesta.length() == 0) {
                    respuesta = this.nInmueble.eliminar(datos[0]);
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void ListarInmueble() {
        String respuesta = "";
        
            try {
                if (respuesta.length() == 0) {
                    respuesta = this.nInmueble.listar();
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    //CU3: Gestionar Grupo
    private void RegistrarGrupo(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 2 || datos.length > 2) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nGrupo.registrar(Integer.parseInt(datos[0]), datos[1]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void EliminarGrupo(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += !esNumero(datos[0]) ? "Id no valido" : "";
                if (respuesta.length() == 0) {
                    respuesta = this.nGrupo.eliminar(Integer.parseInt(datos[0]));
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ModificarGrupo(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 2 || datos.length > 2) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nGrupo.modificar(Integer.parseInt(datos[0]), datos[1]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ListarGrupo() {
        String respuesta = "";   
            try {
                if (respuesta.length() == 0) {
                    respuesta = this.nGrupo.listar();
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    //CU4: Gestionar DIRECCION
    private void RegistrarDir(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 6 || datos.length > 6) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Ubicacion no valida \n" : "";
            respuesta += datos[2].length() < 1 ? "Lugar no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "Oficina no valida \n" : "";
            respuesta += datos[4].length() < 1 ? "Latitud no valida \n" : "";
            respuesta += datos[5].length() < 1 ? "Longitud no valida \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nDireccion.registrar(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], datos[4], datos[5]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ModificarDir(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 6 || datos.length > 6) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "ubicacion no valida \n" : "";
            respuesta += datos[2].length() < 1 ? "lugar no valido \n" : "";
            respuesta += datos[3].length() < 1 ? "oficina no valida \n" : "";
            respuesta += datos[4].length() < 1 ? "latitud no valida \n" : "";
            respuesta += datos[5].length() < 1 ? "longitud no valida \n" : "";
            

            if (respuesta.length() == 0) {
//                respuesta = this.nDireccion.modificar(Integer.parseInt(datos[5]), datos[0], datos[1], datos[2], datos[3], datos[4]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void EliminarDir(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += !esNumero(datos[0]) ? "Id no valido" : "";
                if (respuesta.length() == 0) {
//                    respuesta = this.nDireccion.eliminar(Integer.parseInt(datos[0]));
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    //CU5: GESTIONAR ESTADO
    private void RegistrarEstado(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 2 || datos.length > 2) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nEstado.registrar(Integer.parseInt(datos[0]), datos[1]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void EliminarEstado(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += !esNumero(datos[0]) ? "Id no valido" : "";
                if (respuesta.length() == 0) {
                    respuesta = this.nEstado.eliminar(Integer.parseInt(datos[0]));
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ListarEstado() {
        String respuesta = "";   
            try {
                if (respuesta.length() == 0) {
                    respuesta = this.nEstado.listar();
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void ModificarEstado(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 5 || datos.length > 5) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += !esNumero(datos[0]) ? "ID no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
//                respuesta = this.ninvitados.modificar(Integer.parseInt(datos[2]), datos[1]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    //CU6: GESTIONAR RESPONSABLE
    private void RegistrarResponsable(String[] datos) {
        if (datos == null) {
            sendMail("no se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 2 || datos.length > 2) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 1 ? "Cod_adm no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
                respuesta = this.nResponsable.registrar(datos[0], datos[1]); 
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    private void EliminarResponsable(String[] datos) {
        String respuesta = "";
        if (datos == null) {
            respuesta = "Los datos no deben ser nulos o vacíos.";
        } else {
            if (datos.length < 1 || datos.length > 1) {
                sendMail("Cantidad de parametros incorrecta");
                return;
            }
            try {
                respuesta += datos[0].length() < 5? "CI no valido" : "";
                if (respuesta.length() == 0) {
                    respuesta = this.nResponsable.eliminar(datos[0]);
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void ListarResponsable() {
        String respuesta = "";   
            try {
                if (respuesta.length() == 0) {
                    respuesta =this.nResponsable.listar();
                }
            } catch (Exception e) {
                sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
                return;
            }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    private void ModificarResponsable(String[] datos) {
        if (datos == null) {
            sendMail("No se aceptan datos nulos");
            return;
        }
        String respuesta = "";
        if (datos.length < 2 || datos.length > 2) {
            sendMail("Cantidad de parametros incorrecta");
            return;
        }
        try {
            respuesta += datos[0].length() < 1 ? "cod_adm no valido \n" : "";
            respuesta += datos[1].length() < 1 ? "Nombre no valido \n" : "";
            if (respuesta.length() == 0) {
//                respuesta = this.nResponsable.modificar(datos[0], datos[1]);
            }
        } catch (Exception e) {
            sendMail("Valor de Parametro(s) no organizados, llamar a HELP para conocer el orden y el tipo de dato por valor");
            return;
        }
        System.out.println(respuesta);
        sendMail(respuesta);
    }
    
    /**
     * FUNCIONES DE VALIDACION
     */
    public static boolean esNumero(String strNum) {
        String regex = "[0-9]+";
        return strNum.matches(regex);
    }

    private boolean esCaracter(String strCar) {
        return strCar.matches("[a-zA-Z\\s]*");
    }

    private boolean esEmail(String email) {
        email = email.trim();

        if (email == null || email.equals("")) {
            return false;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return false;
        }

        return true;
    }

    public static boolean esFecha(String text) {
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d")) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
    
    
    /**
     * 
     * HELP
     * 
     */
    public String help() {
        String help = ContenidoHelp();
        return help;
    }
    public String ContenidoHelp() {
        return "Content-Type: text/html; charset=\"UTF-8\" \n \n"
                + "<h1>SISTEMA BIEN INMUEBLE - HELP</h1>"
                + "<table style=\" border-collapse: collapse; width: 100%; border: 1px solid red;\"> \n \n"
                + "<tr> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #f28080; color: white; border: 1px solid red;\"> Caso de Uso </th> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #f28080; color: white; border: 1px solid red;\"> Metodo </th> \n \n"
                + "<th style=\"text-align: center; padding: 8px; background-color: #f28080; color: white; border: 1px solid red;\"> Comando </th> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (administrador)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Administrador</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGADMIN[id, nombre, password];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (administrador)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Administrador</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELADMIN[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (administrador)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Administradores</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTADMIN[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (cliente)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Cliente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGCLIENTE[id, nombre, password, telefono, ci, fecha_nac[yyyy-MM-dd], direccion, tipo[char], id_administrador];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (cliente)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Cliente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODCLIENTE[nombre, password, telefono, ci, fecha_nac[yyyy-MM-dd], direccion, tipo[char], id_administrador, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (cliente)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Cliente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELCLIENTE[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (cliente)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Cliente</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTCLIENTE[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (invitado)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Invitado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGINVITADO[id, nombre, correo, id_notificacion, id_cliente];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (invitado)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Invitado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODINVITADO[nombre, correo, id_notificacion, id_cliente, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (invitado)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Invitado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELINVITADO[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU1. Gestionar Usuarios (invitado)</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Invitado</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTINVITADO[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU2. Gestionar Eventos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Evento</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGEVENTO[id, fecha_evento[yyyy-MM-dd], ubicacion, descripcion, id_paquete];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU2. Gestionar Eventos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Evento</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODEVENTO[fecha_evento[yyyy-MM-dd], ubicacion, descripcion, id_paquete, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU2. Gestionar Eventos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Evento</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELEVENTO[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU2. Gestionar Eventos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Eventos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTEVENTO[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU3. Gestionar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGSERVICIO[id, tipo, descripcion, precio];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU3. Gestionar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Servicio</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODSERVICIO[tipo, descripcion, precio, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU3. Gestionar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Servicio</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELSERVICIO[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU3. Gestionar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Servicios</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTPAQUETE[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU4. Gestionar Paquetes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Paquete</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGPAQUETE[id, tipo, descripcion, precio_paquete, id_servicio, id_decorativo];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU4. Gestionar Paquetes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Paquete</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODPAQUETE[tipo, descripcion, precio_paquete, id_servicio, id_decorativo, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU4. Gestionar Paquetes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Paquete</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELPAQUETE[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU4. Gestionar Paquetes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Paquetes</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTPAQUETE[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU5. Gestionar Reservas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Reserva</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGRESERVA[id, fecha[yyyy-MM-dd], hora[00:00], descripcion, monto, id_evento, id_cliente];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU5. Gestionar Reservas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Reserva</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODRESERVA[fecha[yyyy-MM-dd], hora[00:00], descripcion, monto, id_evento, id_cliente, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU5. Gestionar Reservas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Reserva</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELRESERVA[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU5. Gestionar Reservas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Reservas</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTRESERVA[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU6. Gestionar Notificaciones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Notificacion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGNOTI[id, fecha[yyyy-MM-dd], hora[00:00], id_evento];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU6. Gestionar Notificaciones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Notificacion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODNOTI[fecha[yyyy-MM-dd], hora[00:00], id_evento, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU6. Gestionar Notificaciones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Notificacion</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELNOTI[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU6. Gestionar Notificaciones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Notificaciones</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTNOTI[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU7. Gestionar Decorativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Registrar Decorativo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">REGDECO[id, nombre, descripcion, tamaño, precio];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU7. Gestionar Decorativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Modificar Decorativo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">MODDECO[nombre, descripcion, tamaño, precio, id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU7. Gestionar Decorativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Eliminar Decorativo</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">DELDECO[id];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU7. Gestionar Decorativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Listar Decorativos</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LISTDECO[];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU8. Gestionar Reportes y Estadistica</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Cantidad Usuario</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CANTUSR[int ci (ADMIN)];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">CU8. Gestionar Reportes y Estadistica</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">Lista Cliente por Cant. de Viaje</td> \n \n"
                + "<td style=\"text-align: left; padding: 8px; border: 1px solid red;\">LCLIENTEVIAJE[int ci (ADMIN)];</td> \n \n"
                + "</tr> \n \n"
                + "<tr> \n \n"
                + "</table>";
    }
    
    
    
    /**
     * FUNCIONES PARA ENVIAR CORREO (SMTP)
     * 
     */   
    public void sendMail(String body) {
        String line;
        String comando = "";
        int puerto = 25;

        try {
            Socket socket = new Socket(servidor, puerto);

            BufferedReader entrada
                    = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            DataOutputStream salida
                    = new DataOutputStream(socket.getOutputStream());

            if (socket != null && entrada != null && salida != null) {

                System.out.println("S : " + entrada.readLine());

                comando = "EHLO " + this.servidor + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + getMultilineSMTP(entrada));

                comando = "MAIL FROM: " + this.mailReceptor + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "RCPT TO: " + this.mailEmisor + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "DATA\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + getMultilineSMTP(entrada));

                comando = "Subject: Respuesta"
                        + "\r\n" + body + "\r\n.\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "QUIT\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

            }

            salida.close();
            entrada.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : No se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMultilineSMTP(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.charAt(3) == ' ') {
                lines = lines + "\n" + line;
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }

    public String getEncodeMime(BufferedReader in) throws IOException {
        String respuesta = "";
        String linea_anterior = "";
        String linea_actual = "";
        boolean flag = false;
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            linea_anterior = linea_actual;
            linea_actual = line;
            if (linea_anterior.contains("Content-Type:") && linea_actual.contains("name=") && flag == false) {
                flag = true;
            }
            if (linea_anterior.contains("Content-Type:") && linea_anterior.contains("name=") && flag == false) {
                flag = true;
            }
            if (linea_actual.contains("--") && flag) {
                respuesta += linea_anterior;
                flag = false;
            }
            if (flag) {
                respuesta += linea_anterior + "\n";
            }
        }
        return respuesta;
    }

    public String getNameFromMime(BufferedReader in) throws IOException {
        String respuesta = "";
        String linea_anterior = "";
        String linea_actual = "";
        boolean flag = false;
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            linea_anterior = linea_actual;
            linea_actual = line;
            if (linea_anterior.contains("Content-Type:") && linea_actual.contains("name=") && flag == false) {
                respuesta = linea_actual;
                respuesta = respuesta.replaceAll("name=", "");
                respuesta = respuesta.replaceAll("Content-Disposition: attachment; file", "");
                respuesta = respuesta.replaceAll("\"", "");
                respuesta = respuesta.trim();
                return respuesta;
            }
            if (linea_anterior.contains("Content-Type:") && linea_anterior.contains("name=") && flag == false) {
                respuesta = linea_anterior;
                respuesta = respuesta.substring(respuesta.indexOf("name="), respuesta.length() - 1);
                respuesta = respuesta.replaceAll("name=", "");
                respuesta = respuesta.replaceAll("Content-Disposition: attachment; file", "");
                respuesta = respuesta.replaceAll("\"", "");
                respuesta = respuesta.trim();
                return respuesta;
            }

        }
        return respuesta;
    }
    
    /**
     * 
     * FUNCIONES PARA OBTENER CORREO POP3
     * 
     */
    
    public int getCantidadMails() {

        String comando = "";
        int puerto = 110;
        String subject = "";
        String number = "";

        try {
            Socket socket = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {
                //System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "USER " + usuario + "\r\n";
                //System.out.print("C : " + comando);
                salida.writeBytes(comando);
                //System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "PASS " + contrasena + "\r\n";
                //System.out.print("C : " + comando);
                salida.writeBytes(comando);
                //System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "LIST \r\n";
                //System.out.print("C : " + comando);
                salida.writeBytes(comando);
                number = getLastMail(entrada);

                comando = "RETR " + number + "\n";
                //System.out.print("C : " + comando);
                salida.writeBytes(comando);
                //this.subject = getSubject(entrada);                

                comando = "QUIT\r\n";
                //System.out.print("C : " + comando);
                salida.writeBytes(comando);
                //System.out.println("S : " + entrada.readLine() + "\r\n");
            }

            salida.close();
            entrada.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(number);
    }

    public void getMail() {

        String comando = "";
        int puerto = 110;
        String subject = "";
        String number = "";

        try {
            Socket socket = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {
                System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "USER " + usuario + "\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "PASS " + contrasena + "\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine() + "\r\n");

                comando = "LIST \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                number = getLastMail(entrada);

                comando = "RETR " + number + "\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                this.mailEmisor = getEmisorMail(entrada);
                subject = getSubject(entrada);
                if (subject.equals("")) {
                    return;
                }

                comando = "QUIT\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine() + "\r\n");
            }

            salida.close();
            entrada.close();
            socket.close();
            VerificarSubject(subject);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastMail(BufferedReader in) throws IOException {
        String number = "";
        String line = "";
        String anteriorLine = "";
        while (true) {
            anteriorLine = line;
            line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            //System.out.println(line);
        }

        number = anteriorLine.substring(0, anteriorLine.indexOf(" "));
        number = number.trim();
        //System.out.println(number);
        return number;
    }

    public String getEmisorMail(BufferedReader in) throws IOException {
        String emisor = "";
        while (true) {

            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            String cadenaDondeBuscar = line;
            String loQueQuieroBuscar = "From:";
            loQueQuieroBuscar = loQueQuieroBuscar.trim();
            String primerasLetras = cadenaDondeBuscar.substring(0, 5);
            if (primerasLetras.equals(loQueQuieroBuscar)) {
                emisor = cadenaDondeBuscar.substring((cadenaDondeBuscar.indexOf("<")) + 1, (cadenaDondeBuscar.indexOf(">")));
                break;
            }
        }
        return emisor;
    }

    public String getSubject(BufferedReader in) throws IOException {
        String subject = "";
        boolean flag = false;
        int contador = 0;
        while (true) {

            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            String cadenaDondeBuscar = line;
            cadenaDondeBuscar = cadenaDondeBuscar.trim();
            String loQueQuieroBuscar = "Subject:";
            loQueQuieroBuscar = loQueQuieroBuscar.trim();

            if (cadenaDondeBuscar.contains(loQueQuieroBuscar) || flag) {
                contador++;
                if (cadenaDondeBuscar.equalsIgnoreCase("Subject: help")) {
                    flag = false;
                    subject = cadenaDondeBuscar;
                    subject = subject.trim();
                } else {
                    if (contador == 6) {
                        this.sendMail("Formato del comando erroneo");
                        return "";
                    }
                    if (cadenaDondeBuscar.contains("];")) {
                        flag = false;
                        subject = subject + " " + cadenaDondeBuscar.substring(0, cadenaDondeBuscar.length() - 1);
                        subject = subject.trim();

                    } else {
                        System.out.println("Encontrado");
                        subject = subject + cadenaDondeBuscar;
                        subject = subject.trim();
                        flag = true;
                    }
                }
            }

            System.out.println(line);
        }
        subject = subject.replaceAll("Subject:", "");
        subject = subject.trim();
        return subject;
    }

    public void VerificarSubject(String opcion) {
        String errorFormato = "";
        // Solo lo uso para verificar el help
        String subjecthelp = opcion.toLowerCase();
        subjecthelp = subjecthelp.trim();
        //String trimear = subjecthelp.trim();
        if (subjecthelp.equals("help")) {
            String help = this.help();
            this.sendMail(help);
        } else {

            int index1 = opcion.indexOf("[");
            int index2 = opcion.indexOf("]");
            if (index1 != -1 && index2 != -1) {
                if (index1 < index2) {
                    if (index1 > 0) {
                        this.Opciones(opcion);
                    } else {
                        errorFormato = "No se reconoce el formato indicado. Verifique que está enviando los datos dentro de un encabezado.";
                        this.sendMail(errorFormato);
                        System.out.println(errorFormato);
                    }
                } else {
                    errorFormato = "No se reconoce el formato indicado. Verifique que está utilizando los corchetes([]) de forma ordenada.";
                    this.sendMail(errorFormato);
                    System.out.println(errorFormato);
                }
            } else {
                errorFormato = "No se reconoce el formato indicado. Verifique que está utilizando los corchetes([]) para realizar la petición.";
                this.sendMail(errorFormato);
                System.out.println(errorFormato);
            }
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        InmuebleMain manage = new InmuebleMain();

        int cantMails = manage.getCantidadMails();

        while (true) {
            int cantMailsNew = manage.getCantidadMails();
            if (cantMails != cantMailsNew) {
                cantMails = cantMailsNew;
                manage.getMail();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InmuebleMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    

    
    
    
    
}
