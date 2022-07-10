/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.util.Arrays;

/**
 *
 * @author Diana Montano
 */
public class Prueba {
    public static void prueba(String opcion){
        opcion = opcion.trim();
        String[] partesSubject = opcion.split("\\[");
        String encabezado = partesSubject[0];
        System.out.println(encabezado);
        String cuerpo[] = partesSubject[1].split("\\]");
        System.out.println(Arrays.toString(cuerpo));
        String datos[] = null;
        if (cuerpo.length != 0) {
            datos = cuerpo[0].split("\\,");
            for (int i = 0; i < datos.length; i++) {
                datos[i] = datos[i].trim();
            }
        }
    }
    
    public static void main(String[] args) {
        String opcion;
        opcion = "REGADMIN\\[763970\\,DIANA\\,MONTANO\\,CORREO\\]";
        System.out.println("ingreso = " + opcion);
        prueba(opcion);
    }
}
