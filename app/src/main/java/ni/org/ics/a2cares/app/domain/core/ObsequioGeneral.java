package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Representa los datos de la entrega de obsequios
 * 
 * @author Miguel Salinas
 **/

public class ObsequioGeneral extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
    private String participante;
    private String casa;
    private String motivo;//1 MA, 2 Seguimiento
	private String entregoObsequio;
	private String personaRecibe;
	private String observacion;
    private String seguimiento;
    private String numVisitaSeguimiento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEntregoObsequio() {
        return entregoObsequio;
    }

    public void setEntregoObsequio(String entregoObsequio) {
        this.entregoObsequio = entregoObsequio;
    }

    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public void setPersonaRecibe(String personaRecibe) {
        this.personaRecibe = personaRecibe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getNumVisitaSeguimiento() {
        return numVisitaSeguimiento;
    }

    public void setNumVisitaSeguimiento(String numVisitaSeguimiento) {
        this.numVisitaSeguimiento = numVisitaSeguimiento;
    }

    @Override
    public String toString() {
        return id;
    }
}
