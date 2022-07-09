/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

/**
 *
 * @author Diana Montano
 */
public class DInmueble {
    private String codigo;
    private String descripcion;
    private String cod_asignacion;
    private String tipo;
    private String id_usuario_fk;
    private String id_responsable_fk;
    private int id_estado_fk;
    private int id_direccion_fk;
    private int id_grupo_fk;
    private ConexionDB conexion;

    public DInmueble() {
        conexion = new ConexionDB();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCod_asignacion() {
        return cod_asignacion;
    }

    public void setCod_asignacion(String cod_asignacion) {
        this.cod_asignacion = cod_asignacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId_usuario_fk() {
        return id_usuario_fk;
    }

    public void setId_usuario_fk(String id_usuario_fk) {
        this.id_usuario_fk = id_usuario_fk;
    }

    public String getId_responsable_fk() {
        return id_responsable_fk;
    }

    public void setId_responsable_fk(String id_responsable_fk) {
        this.id_responsable_fk = id_responsable_fk;
    }

    public int getId_estado_fk() {
        return id_estado_fk;
    }

    public void setId_estado_fk(int id_estado_fk) {
        this.id_estado_fk = id_estado_fk;
    }

    public int getId_direccion_fk() {
        return id_direccion_fk;
    }

    public void setId_direccion_fk(int id_direccion_fk) {
        this.id_direccion_fk = id_direccion_fk;
    }

    public int getId_grupo_fk() {
        return id_grupo_fk;
    }

    public void setId_grupo_fk(int id_grupo_fk) {
        this.id_grupo_fk = id_grupo_fk;
    }
    
    
    
    
    
}
