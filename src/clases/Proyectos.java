package clases;

import java.sql.Date;

public class Proyectos {
    private String codigo_proyecto;
    private String proyecto;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String proyecto_codigo_tipo;
    private String proyecto_codigo_estado;
    private String proyecto_codigo_responsable;
    
    public Proyectos (){
        
    }
    
    public Proyectos(String codigo_proyecto, String proyecto, String descripcion,
            Date fecha_inicio, Date fecha_fin, String proyecto_codigo_tipo, 
            String proyecto_codigo_estado, String proyecto_codigo_responsable){
        this.codigo_proyecto = codigo_proyecto;
        this.proyecto = proyecto;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.proyecto_codigo_tipo = proyecto_codigo_tipo;
        this.proyecto_codigo_estado = proyecto_codigo_estado;
        this.proyecto_codigo_responsable = proyecto_codigo_responsable;
    }

    public String getCodigo_proyecto() {
        return codigo_proyecto;
    }

    public void setCodigo_proyecto(String codproy) {
        this.codigo_proyecto = codproy;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proy) {
        this.proyecto = proy;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String des) {
        this.descripcion = des;
    }
    
    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date f_inic) {
        this.fecha_inicio = f_inic;
    }
    
    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date f_fin) {
        this.fecha_fin = f_fin;
    }

    public String getProyecto_codigo_tipo() {
        return proyecto_codigo_tipo;
    }

    public void setProyecto_codigo_tipo(String cod_tp) {
        this.proyecto_codigo_tipo = cod_tp;
    }
    
    public String getProyecto_codigo_estado() {
        return proyecto_codigo_estado;
    }

    public void setProyecto_codigo_estado(String cod_es) {
        this.proyecto_codigo_estado = cod_es;
    }
    
    public String getProyecto_codigo_responsable() {
        return proyecto_codigo_responsable;
    }

    public void setProyecto_codigo_responsable(String cod_rs) {
        this.proyecto_codigo_responsable = cod_rs;
    }
    
    
}
