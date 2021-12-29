package ni.org.ics.a2cares.app.domain.puntos;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Created by Miguel Salinas on 13/12/2021.
 */
public class PuntoCandidato  extends BaseMetaData {

    private static final long serialVersionUID = 1L;

    private Integer codigo;
    private String Barrio;
    private Double latitud;
    private Double longitud;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String barrio) {
        Barrio = barrio;
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
