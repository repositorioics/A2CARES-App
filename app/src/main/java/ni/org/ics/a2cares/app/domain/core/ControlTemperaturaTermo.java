package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;


/**
 * Simple objeto de dominio que representa los datos de temperatura del termo de cada bionalista
 * 
 * @author Everts Morales
 **/


public class ControlTemperaturaTermo extends BaseMetaData implements Serializable  {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	
	//private Integer id;
	private Date fechatemp;
	private String horatomatemp;
	private Double temperaturaTermo;
	private String usuario;





	public Date getFechatemp() {
		return fechatemp;
	}

	public void setFechatemp(Date fechatemp) {
		this.fechatemp = fechatemp;
	}

	public String getHoratomatemp() {
		return horatomatemp;
	}

	public void setHoratomatemp(String horatomatemp) {
		this.horatomatemp = horatomatemp;
	}

	public Double getTemperaturaTermo() {
		return temperaturaTermo;
	}

	public void setTemperaturaTermo(Double temperaturaTermo) {
		this.temperaturaTermo = temperaturaTermo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}





}
