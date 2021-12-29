package ni.org.ics.a2cares.app.domain.laboratorio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Created by ICS on 14/10/2020.
 */

public class Serologia extends BaseMetaData implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Integer idSerologia;
    private String participante;
    private Date fecha;
    private double volumen;
    private String observacion;
    private char enviado = '0';
    private Integer codigo_casa;
    private Integer edadMeses;
    private String descripcion;


    public Integer getIdSerologia() {
        return idSerologia;
    }

    public void setIdSerologia(Integer idSerologia) {
        this.idSerologia = idSerologia;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public char getEnviado() {
        return enviado;
    }

    public void setEnviado(char enviado) {
        this.enviado = enviado;
    }

    public Integer getCodigo_casa() {
        return codigo_casa;
    }

    public void setCodigo_casa(Integer codigo_casa) {
        this.codigo_casa = codigo_casa;
    }

    public Integer getEdadMeses() {
        return edadMeses;
    }

    public void setEdadMeses(Integer edadMeses) {
        this.edadMeses = edadMeses;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
