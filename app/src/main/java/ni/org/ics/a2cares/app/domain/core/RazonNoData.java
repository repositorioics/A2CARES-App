package ni.org.ics.a2cares.app.domain.core;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Simple objeto de dominio que representa los datos de la toma de muestra
 * 
 * Created by Miguel Salinas on 28/12/2021.
 **/

public class RazonNoData extends BaseMetaData {


	/**
	 * 
	 */
	private Integer id;
	private String codigoParticipante;
	private String razon;
	private String otraRazon;

	//en el movil no se usa.. es autoincrement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoParticipante() {
		return codigoParticipante;
	}

	public void setCodigoParticipante(String codigoParticipante) {
		this.codigoParticipante = codigoParticipante;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getOtraRazon() {
		return otraRazon;
	}

	public void setOtraRazon(String otraRazon) {
		this.otraRazon = otraRazon;
	}
}
