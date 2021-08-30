package ni.org.ics.a2cares.app.domain.core;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Objeto que representa un número telefónico asociado a una casa o participante
 * Created by Miguel Salinas on 10/5/2021.
 * V1.0
 */
public class TelefonoContacto extends BaseMetaData {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String numero;
    private String operadora;
    private String tipo;
    private Casa casa;
    private Participante participante;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }
    
    

    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefonoContacto)) return false;

        TelefonoContacto telefonoContacto = (TelefonoContacto) o;

        return  (!id.equals(telefonoContacto.id));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
