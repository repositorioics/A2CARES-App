package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;


/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * @author Miguel Salinas
 **/


public class Muestra extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String idMuestra;
	private Participante participante;
	private Date fechaMuestra;
	private String tuboBHC;
	private String codigoBHC;
	private Double volumenBHC;
	private String razonNoBHC;
	private String otraRazonNoBHC;
	private String tuboRojo;
	private String codigoRojo;
	private Double volumenRojo;
	private String razonNoRojo;
	private String otraRazonNoRojo;
	private String terreno;
	private String pinchazos;
    private String estudiosAct;
    private String proposito;
	private String observacion;
	private String descOtraObservacion;

	public String getIdMuestra() {
		return idMuestra;
	}

	public void setIdMuestra(String idMuestra) {
		this.idMuestra = idMuestra;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Date getFechaMuestra() {
		return fechaMuestra;
	}

	public void setFechaMuestra(Date fechaMuestra) {
		this.fechaMuestra = fechaMuestra;
	}

	public String getTuboBHC() {
		return tuboBHC;
	}

	public void setTuboBHC(String tuboBHC) {
		this.tuboBHC = tuboBHC;
	}

	public Double getVolumenBHC() {
		return volumenBHC;
	}

	public void setVolumenBHC(Double volumenBHC) {
		this.volumenBHC = volumenBHC;
	}

	public String getCodigoBHC() {
		return codigoBHC;
	}

	public void setCodigoBHC(String codigoBHC) {
		this.codigoBHC = codigoBHC;
	}

	public String getRazonNoBHC() {
		return razonNoBHC;
	}

	public void setRazonNoBHC(String razonNoBHC) {
		this.razonNoBHC = razonNoBHC;
	}

	public String getOtraRazonNoBHC() {
		return otraRazonNoBHC;
	}

	public void setOtraRazonNoBHC(String otraRazonNoBHC) {
		this.otraRazonNoBHC = otraRazonNoBHC;
	}

	public String getTuboRojo() {
		return tuboRojo;
	}

	public void setTuboRojo(String tuboRojo) {
		this.tuboRojo = tuboRojo;
	}

	public String getCodigoRojo() {
		return codigoRojo;
	}

	public void setCodigoRojo(String codigoRojo) {
		this.codigoRojo = codigoRojo;
	}

	public Double getVolumenRojo() {
		return volumenRojo;
	}

	public void setVolumenRojo(Double volumenRojo) {
		this.volumenRojo = volumenRojo;
	}

	public String getRazonNoRojo() {
		return razonNoRojo;
	}

	public void setRazonNoRojo(String razonNoRojo) {
		this.razonNoRojo = razonNoRojo;
	}

	public String getOtraRazonNoRojo() {
		return otraRazonNoRojo;
	}

	public void setOtraRazonNoRojo(String otraRazonNoRojo) {
		this.otraRazonNoRojo = otraRazonNoRojo;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public String getPinchazos() {
		return pinchazos;
	}

	public void setPinchazos(String pinchazos) {
		this.pinchazos = pinchazos;
	}

	public String getEstudiosAct() {
		return estudiosAct;
	}

	public void setEstudiosAct(String estudiosAct) {
		this.estudiosAct = estudiosAct;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDescOtraObservacion() {
		return descOtraObservacion;
	}

	public void setDescOtraObservacion(String descOtraObservacion) {
		this.descOtraObservacion = descOtraObservacion;
	}
}
