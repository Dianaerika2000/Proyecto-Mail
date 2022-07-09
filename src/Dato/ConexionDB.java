/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;
import java.sql.*;
import java.sql.Connection;

/**
 *
 * @author Diana Montano
 */
public class ConexionDB {
    /*
    Conexion a la bd 
    */
    String driver;
    String bd;
    String url;
    String user;
    String pass;
    Connection conexion;
    Statement sentencia = null;
        

    public ConexionDB(){
        driver = "org.postgresql.Driver";
        bd   = "db_agenda";
        url = "jdbc:postgresql://37.187.122.232:5432/db_grupo03sc";
        user = "grupo03sc";
        pass = "grup003grup003";
        conexion = null;
    }

    public void conectarBD(){
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Error al conectar la bd " + e);
        }
    }
    
    public Connection getConnection() {
        try {
            Class.forName(driver);
            conexion = (Connection) DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Error al conectar la bd " + e);
        }
        return conexion;
    }
    


    public void desconectarBD(){
        try {
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error al desconectarse de la bd");
        }
    }
    
    

    /**
     * Metodo para verificar si la bd esta conectada
     * @return  true = desconectada, false = conectada
     * @throws SQLException 
     */

    public boolean getEstado() throws SQLException{
        return conexion.isClosed();
    }
    
    /**
     * 
     * @param args 
     */
    
    public ResultSet consultaBD(String consulta) throws Exception{
        ResultSet resultado;
        try {
            conectarBD();
            sentencia = conexion.createStatement(); 
            resultado = sentencia.executeQuery(consulta);
            return resultado;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            if(getEstado() == false){
                desconectarBD();
            }
        }
        return null;
    }
    
    
    
    
    public static void main(String[] args) {
        
        ConexionDB db = new ConexionDB();
        ResultSet resultado;
        try {
            resultado  = db.consultaBD("select* from usuario");
            while (resultado.next()) {
                String ci = resultado.getString(1);
                String cod_adm = resultado.getString(2);
                String nombre = resultado.getString(3);
                String apellido = resultado.getString(4);
                String correo = resultado.getString(5);
                System.out.println("ci: " + ci);
                System.out.println("cod_adm: " + cod_adm);
                System.out.println("nombre: " + nombre);
                System.out.println("correo: " + correo);
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println("No se logro realizar la consulta " + e.getMessage());
        }
//        try {
//            db.conectarBD();
//            if(db.getEstado())
//                System.out.println("Base de datos desconectada");
//            else
//                System.out.println("Base de datos conectada");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
