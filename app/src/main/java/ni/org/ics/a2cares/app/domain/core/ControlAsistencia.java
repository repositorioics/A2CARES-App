package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;


/**
 * Simple objeto de dominio que representa los datos de asistencia del personal a2cares a sus puestos de trabajo
 * 
 * @author Everts Morales
 **/


public class ControlAsistencia extends BaseMetaData implements Serializable  {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	
	private Integer id;
	private Date fechaasistencia;
	private String horaentrada;
	private String horasalida;
	private Double latitud;
	private Double longitud;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaasistencia() {
		return fechaasistencia;
	}

	public void setFechaasistencia(Date fechaasistencia) {
		this.fechaasistencia = fechaasistencia;
	}

	public String getHoraentrada() {
		return horaentrada;
	}

	public void setHoraentrada(String horaentrada) {
		this.horaentrada = horaentrada;
	}

	public String getHorasalida() {
		return horasalida;
	}

	public void setHorasalida(String horasalida) {
		this.horasalida = horasalida;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}



}
