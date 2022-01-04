package ni.org.ics.a2cares.app.domain.puntos;

import java.util.Date;

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
    private String descartado;
    private String razonDescarte;
    private String otraRazonDescarte;
    private Date fechaDescarte;
    private String usuarioDescarte;

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

    public String getDescartado() {
        return descartado;
    }

    public void setDescartado(String descartado) {
        this.descartado = descartado;
    }

    public String getRazonDescarte() {
        return razonDescarte;
    }

    public void setRazonDescarte(String razonDescarte) {
        this.razonDescarte = razonDescarte;
    }

    public String getOtraRazonDescarte() {
        return otraRazonDescarte;
    }

    public void setOtraRazonDescarte(String otraRazonDescarte) {
        this.otraRazonDescarte = otraRazonDescarte;
    }

    public Date getFechaDescarte() {
        return fechaDescarte;
    }

    public void setFechaDescarte(Date fechaDescarte) {
        this.fechaDescarte = fechaDescarte;
    }

    public String getUsuarioDescarte() {
        return usuarioDescarte;
    }

    public void setUsuarioDescarte(String usuarioDescarte) {
        this.usuarioDescarte = usuarioDescarte;
    }
}
