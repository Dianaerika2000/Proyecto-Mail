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
import Dato.ConexionDB;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
//import tecno.ConexionDB;
public class ClientePOP {
    String servidor="mail.tecnoweb.org.bo";
    String usuario;
    String contrasena;
    String comando="";
    String linea="";
    int puerto=110;
    ConexionDB conectionDB = null;
    int numeroCorreo = 128;
    String mensaje = "";
    int ultimoCorreo;
    String stat = "";
    
    public ClientePOP(){
        usuario = "grupo21sa";
        contrasena = "grup021grup021";
        conectionDB = new ConexionDB();
    }
    
    public String getEmail(int nroCorreo){
        try{
            //se establece conexion abriendo un socket especificando el servidor y puerto SMTP
            Socket socket=new Socket(servidor,puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream (socket.getOutputStream());
            // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
            if( socket != null && entrada != null && salida != null ){
                System.out.println("S : "+entrada.readLine()+"\r\n");
       
                comando="USER " + usuario + "\r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine()+"\r\n");
               
                comando="PASS " + contrasena + "\r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine()+"\r\n");
                
                comando="STAT\r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );      
                stat = (String) entrada.readLine();
                System.out.println("S : "+ stat +"\r\n");
                
                
                comando="LIST\r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+ getMultiline(entrada)+"\r\n");
                
                comando = "RETR " + nroCorreo + "\r\n";
                System.out.println("C: " + comando);
                salida.writeBytes(comando);
                mensaje = getMultiline(entrada);
                System.out.println("S: "+ mensaje);
                
                
                comando="QUIT\r\n";
                System.out.print("C : "+comando);
                salida.writeBytes( comando );               
                System.out.println("S : "+entrada.readLine()+"\r\n");
                        
            }
            // Cerramos los flujos de salida y de entrada y el socket cliente
            salida.close();
            entrada.close();
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        }catch (IOException e){
            e.printStackTrace();
        }
        return mensaje;
    }
    
    public String getSubject(String mensaje){
        int pos = mensaje.indexOf("SUBJECT");
        System.out.println(pos);
        String coincidencia = mensaje.substring(pos+9);
        return coincidencia.trim();
    }
    
    public void getStat(){
       String nroCorreos = stat.substring(4, 7);
       System.out.println("\n Cantidad de correos: " + nroCorreos + "\n"); 
    }
    
    
    public String createQuery(String coincidencia){
        conectionDB = new ConexionDB();
        ResultSet resultado;
        String salida="nombre, apellido, profesion, direccion \r\n";
        String consulta = "select per_nom, per_appm, per_prof, per_dir " + 
                           "from persona where per_nom LIKE '%" + coincidencia + "%' or " +
                           "per_appm LIKE '%" + coincidencia + "%' or " + 
                           "per_prof LIKE '%" + coincidencia + "%' or " + 
                           "per_dir LIKE '%" + coincidencia + "%'";
        try {
            resultado  = conectionDB.consultaBD(consulta);
            ResultSetMetaData mData = resultado.getMetaData();
            int cantCol = mData.getColumnCount();
            
            while (resultado.next()) {                
                for (int i = 1; i <= cantCol; i++) {
                    salida += resultado.getString(i).trim()+',';
                }
                    salida += "\r\n";
            }
            conectionDB.desconectarBD();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return salida;
    }
    
    
    
    
    
    
    /**
     * Permite leer multiples lineas de respuesta del Protocolo POP
     * @param in
     * @return
     * @throws IOException 
     */
    
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
        String mensaje = "";
        String coincidencia = "";
        String correo = "";
        ClientePOP cp = new ClientePOP();
        mensaje = cp.getEmail(127);
        cp.getStat();
//        coincidencia = cp.getSubject(mensaje);
//        System.out.println(coincidencia);
//        correo = cp.createQuery(coincidencia);
//        System.out.println(correo);
    }
    
    
}
