package ni.org.ics.a2cares.app.domain.core;


import java.util.Date;
import java.util.Objects;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Created by Miguel Salinas on 10/5/2021.
 * V1.0
 */
public class Tamizaje extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private String aceptaTamizajePersona;
    private String razonNoAceptaTamizajePersona;
    private String otraRazonNoAceptaTamizajePersona;
    private Estudio estudio;
    private String sexo;
    private Date fechaNacimiento;
    private String tipoVivienda;
    private String tiempoResidencia;
    private String planesMudarse;
    private String asentimientoVerbal;
    private String aceptaParticipar;
    private String razonNoAceptaParticipar;
    private String otraRazonNoAceptaParticipar;
    private String esElegible;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAceptaTamizajePersona() {
        return aceptaTamizajePersona;
    }

    public void setAceptaTamizajePersona(String aceptaTamizajePersona) {
        this.aceptaTamizajePersona = aceptaTamizajePersona;
    }

    public String getRazonNoAceptaTamizajePersona() {
        return razonNoAceptaTamizajePersona;
    }

    public void setRazonNoAceptaTamizajePersona(String razonNoAceptaTamizajePersona) {
        this.razonNoAceptaTamizajePersona = razonNoAceptaTamizajePersona;
    }

    public String getOtraRazonNoAceptaTamizajePersona() {
        return otraRazonNoAceptaTamizajePersona;
    }

    public void setOtraRazonNoAceptaTamizajePersona(String otraRazonNoAceptaTamizajePersona) {
        this.otraRazonNoAceptaTamizajePersona = otraRazonNoAceptaTamizajePersona;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getTiempoResidencia() {
        return tiempoResidencia;
    }

    public void setTiempoResidencia(String tiempoResidencia) {
        this.tiempoResidencia = tiempoResidencia;
    }

    public String getPlanesMudarse() {
        return planesMudarse;
    }

    public void setPlanesMudarse(String planesMudarse) {
        this.planesMudarse = planesMudarse;
    }

    public String getAsentimientoVerbal() {
        return asentimientoVerbal;
    }

    public void setAsentimientoVerbal(String asentimientoVerbal) {
        this.asentimientoVerbal = asentimientoVerbal;
    }

    public String getAceptaParticipar() {
        return aceptaParticipar;
    }

    public void setAceptaParticipar(String aceptaParticipar) {
        this.aceptaParticipar = aceptaParticipar;
    }

    public String getRazonNoAceptaParticipar() {
        return razonNoAceptaParticipar;
    }

    public void setRazonNoAceptaParticipar(String razonNoAceptaParticipar) {
        this.razonNoAceptaParticipar = razonNoAceptaParticipar;
    }

    public String getOtraRazonNoAceptaParticipar() {
        return otraRazonNoAceptaParticipar;
    }

    public void setOtraRazonNoAceptaParticipar(String otraRazonNoAceptaParticipar) {
        this.otraRazonNoAceptaParticipar = otraRazonNoAceptaParticipar;
    }

    public String getEsElegible() {
        return esElegible;
    }

    public void setEsElegible(String esElegible) {
        this.esElegible = esElegible;
    }

    @Override
    public String toString() {
        return "'" + codigo + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tamizaje tamizaje = (Tamizaje) o;
        return codigo.equals(tamizaje.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
