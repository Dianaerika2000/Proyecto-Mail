/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mail;

/**
 *
 * @author Diana Montano
 */
import java.io.*;
import java.net.*;
public class ClienteSMTP {
    String servidor="mail.tecnoweb.org.bo";
    //String servidor="172.20.172.254";
    String user_receptor = "dianaerika.montano16@gmail.com";
    String user_receptor2 = "she.is.vane.vane@gmail.com";
    String user_emisor="grupo03sc@tecnoweb.org.bo";
    String line;
    String comando="";
    int puerto=25;
    
    public ClienteSMTP(String user_emisor,String user_receptor) { 
        this.user_emisor = user_emisor;
        this.user_receptor = user_receptor;
    }
    
    public boolean sentMail(String message) { 
       try{
            //se establece conexion abriendo un socket especificando el servidor y puerto SMTP
            Socket socket = new Socket(servidor,puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream (socket.getOutputStream());
            // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
            if( socket != null && entrada != null && salida != null ){
                
                System.out.println("S : " + entrada.readLine());
                
                comando="HELO " + servidor + " \r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
                comando="MAIL FROM: " + user_emisor + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
                comando="RCPT TO: " + user_receptor + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
                comando="DATA" + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
                comando = "SUBJECT: "+  message + " \r\n";
//                comando += "Hola JAVA.\r\n";
                comando += ".\r\n";
                System.out.print("C : " + comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
                comando="QUIT" + " \r\n";
                System.out.print("C : " + comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine());
                
            }
            
        // Cerramos los flujos de salida y de entrada y el socket cliente
            salida.close();
            entrada.close();
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.out.println(" S : No se pudo conectar con el servidor indicado");
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
       return true;
    }
    
    static protected String getMultiline(BufferedReader in) throws IOException{
        String lines = "";
        while (true){
            String line = in.readLine();
            if (line == null){
               // Server closed connection
               throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")){
                // No more lines in the server response
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')){
                // The line starts with a "." - strip it off.
                line = line.substring(1);
            }
            // Add read line to the list of lines
            lines=lines+"\n"+line;
        }       
        return lines;
    }
    
    public static void main(String[] args) {
        String user_receptor="grupo21sa@tecnoweb.org.bo";
        String user_emisor="dianaerika.montano16@gmail.com";
        ClienteSMTP cliente =  new ClienteSMTP(user_emisor, user_receptor);
        cliente.sentMail("ant");
        
        
    }
    
    
}
