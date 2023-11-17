package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 8/7/2021.
 */
public class ControlAsistenciaFormLabels {

    private String fechaAsistencia;
    private String tuboBHC;
    private String codigoBHC;
    private String razonNoBHC;
    private String otraRazonNoBHC;
    private String horaBHC;
    private String tuboRojo;
    private String codigoRojo;
    private String razonNoRojo;
    private String otraRazonNoRojo;
    private String horaRojo;
    private String latitud;
    private String longitud;
    private String volumenBHC;
    private String volumenRojoSugerido;
    private String volumenBHCSugerido;
    private String observacion;
    private String descOtraObservacion;
    private String descOtraObservacionHint;

    public ControlAsistenciaFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();


    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getCodigoBHC() {
        return codigoBHC;
    }

    public String getRazonNoBHC() {
        return razonNoBHC;
    }

    public String getOtraRazonNoBHC() {
        return otraRazonNoBHC;
    }

    public String getHoraBHC() {
        return horaBHC;
    }

    public String getTuboRojo() {
        return tuboRojo;
    }

    public String getCodigoRojo() {
        return codigoRojo;
    }

    public String getRazonNoRojo() {
        return razonNoRojo;
    }

    public String getOtraRazonNoRojo() {
        return otraRazonNoRojo;
    }

    public String getHoraRojo() {
        return horaRojo;
    }


    public String getObservacion() {
        return observacion;
    }

    public String getDescOtraObservacion() {
        return descOtraObservacion;
    }

    public String getDescOtraObservacionHint() {
        return descOtraObservacionHint;
    }



    public String getVolumenBHC() {
        return volumenBHC;
    }

    public String getVolumenRojoSugerido() {
        return volumenRojoSugerido;
    }

    public String getVolumenBHCSugerido() {
        return volumenBHCSugerido;
    }
}
