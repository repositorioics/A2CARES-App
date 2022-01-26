package ni.org.ics.a2cares.app.domain.medico;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;
import ni.org.ics.a2cares.app.domain.core.Participante;


/**
 * Simple objeto de dominio que representa los datos una orden de laboratorio del médico
 * 
 * @author Miguel Salinas
 **/


public class OrdenLaboratorio extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String idOrden;
	private Participante participante;
	private Date fechaOrden;
	private String tipoOrden;
	private Date fis;
	private Date fif;
	private String categoria;
	private String consulta;
	private String tipoMuestra;
	private String estudiosAct;
    private String observacion;

	public String getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(String idOrden) {
		this.idOrden = idOrden;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Date getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	public String getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public Date getFis() {
		return fis;
	}

	public void setFis(Date fis) {
		this.fis = fis;
	}

	public Date getFif() {
		return fif;
	}

	public void setFif(Date fif) {
		this.fif = fif;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getTipoMuestra() {
		return tipoMuestra;
	}

	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}

	public String getEstudiosAct() {
		return estudiosAct;
	}

	public void setEstudiosAct(String estudiosAct) {
		this.estudiosAct = estudiosAct;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
