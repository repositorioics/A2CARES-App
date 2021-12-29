package ni.org.ics.a2cares.app.domain.puntos;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Created by Miguel Salinas on 13/12/2021.
 */
public class PuntoDescartado extends BaseMetaData {

    private String codigo;
    private PuntoCandidato candidato;
    private String motivo;
    private String otroMotivo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public PuntoCandidato getCandidato() {
        return candidato;
    }

    public void setCandidato(PuntoCandidato candidato) {
        this.candidato = candidato;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOtroMotivo() {
        return otroMotivo;
    }

    public void setOtroMotivo(String otroMotivo) {
        this.otroMotivo = otroMotivo;
    }
}
