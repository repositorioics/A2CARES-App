package ni.org.ics.a2cares.app.dto;

import java.util.Date;

import ni.org.ics.a2cares.app.domain.core.Participante;

/**
 * Created by Miguel Salinas on 9/7/2021.
 */
public class MuestraDTO {

    private String idMuestra;
    private Participante participante;
    private Date fechaMuestra;
    private String tipoTubo;
    private String tomoMuestra;
    private int color;
    private String codigoMx;
    private Double volumen;
    private String razonNoToma;
    private String otraRazonNoToma;
    private String proposito;

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

    public String getTipoTubo() {
        return tipoTubo;
    }

    public void setTipoTubo(String tipoTubo) {
        this.tipoTubo = tipoTubo;
    }

    public String getTomoMuestra() {
        return tomoMuestra;
    }

    public void setTomoMuestra(String tomoMuestra) {
        this.tomoMuestra = tomoMuestra;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCodigoMx() {
        return codigoMx;
    }

    public void setCodigoMx(String codigoMx) {
        this.codigoMx = codigoMx;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getRazonNoToma() {
        return razonNoToma;
    }

    public void setRazonNoToma(String razonNoToma) {
        this.razonNoToma = razonNoToma;
    }

    public String getOtraRazonNoToma() {
        return otraRazonNoToma;
    }

    public void setOtraRazonNoToma(String otraRazonNoToma) {
        this.otraRazonNoToma = otraRazonNoToma;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }
}
