package ni.org.ics.a2cares.app.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Miguel Salinas on 19/1/2022.
 */
public class LogUpdateApk implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codigo;
    private Date fechaActualizacion;
    private String usuarioActualiza;
    private String dispositivo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualiza() {
        return usuarioActualiza;
    }

    public void setUsuarioActualiza(String usuarioActualiza) {
        this.usuarioActualiza = usuarioActualiza;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
}
