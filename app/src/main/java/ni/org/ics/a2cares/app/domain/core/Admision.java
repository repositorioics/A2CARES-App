package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;


/**
 * Simple objeto de dominio que representa los datos de la Admision
 * 
 * @author Everts Morales
 **/


public class Admision extends BaseMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Integer id;
	private String  perteneceEstudio;
	private String  codigoParticipante;
	private String edad;
	private String  sexo;
	private String  febril;
	private Integer numeroHoja;

	public Integer getId() {
		return id;
	}

	public void setId (Integer id) {
		this.id = id;
	}

	public String getPerteneceEstudio() {
		return perteneceEstudio;
	}

	public void setPerteneceEstudio(String perteneceEstudio) {
		this.perteneceEstudio = perteneceEstudio;
	}
	public String getCodigoParticipante() {
		return codigoParticipante;
	}

	public void setCodigoParticipante(String codigoParticipante) {
		this.codigoParticipante = codigoParticipante;
	}
	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFebril() {
		return febril;
	}

	public void setFebril(String febril) {
		this.febril = febril;
	}

	public Integer getNumeroHoja() {
		return numeroHoja;
	}

	public void setNumeroHoja(Integer numeroHoja) {
		this.numeroHoja = numeroHoja;
	}

}
