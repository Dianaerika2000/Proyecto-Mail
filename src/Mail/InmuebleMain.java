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
    
    //LLAMDAS AL NEGOCIO
    
    //CU1: Gestionar Usuario

    
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

    
    
    
    
}
