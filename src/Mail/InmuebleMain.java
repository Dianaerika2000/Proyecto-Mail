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

/**
 *
 * @author Diana Montano
 */
public class InmuebleMain {
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
    
    
    
    
    
}
