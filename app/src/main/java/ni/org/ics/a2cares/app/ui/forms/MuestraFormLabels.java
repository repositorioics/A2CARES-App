package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import java.util.Date;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 8/7/2021.
 */
public class MuestraFormLabels {

  //  private String fechaMuestra;
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
    private String pinchazos;
    private String volumenRojo;
    private String volumenBHC;
    private String volumenRojoSugerido;
    private String volumenBHCSugerido;
    private String observacion;
    private String descOtraObservacion;
    private String descOtraObservacionHint;

    public MuestraFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

     //   fechaMuestra = res.getString(R.string.fechaMuestra);
        tuboBHC = res.getString(R.string.tuboBHC);
        codigoBHC = res.getString(R.string.codigoBHC);
        razonNoBHC = res.getString(R.string.razonNoBHC);
        otraRazonNoBHC = res.getString(R.string.otraRazonNoBHC);
        horaBHC = res.getString(R.string.horaBHC);
        tuboRojo = res.getString(R.string.tuboRojo);
        codigoRojo = res.getString(R.string.codigoRojo);
        razonNoRojo = res.getString(R.string.razonNoRojo);
        otraRazonNoRojo = res.getString(R.string.otraRazonNoRojo);
        horaRojo = res.getString(R.string.horaRojo);
        pinchazos = res.getString(R.string.pinchazos);
        observacion = res.getString(R.string.observacionMx);
        descOtraObservacion = res.getString(R.string.descOtraObservacion);
        descOtraObservacionHint = res.getString(R.string.descOtraObservacionHint);
        volumenRojo = res.getString(R.string.volumenRojo);
        volumenBHC = res.getString(R.string.volumenBHC);
        volumenRojoSugerido = res.getString(R.string.volumenRojoSugerido);
        volumenBHCSugerido = res.getString(R.string.volumenBHCSugerido);
    }

  //  public String getFechaMuestra() {
        //return fechaMuestra;
    //}

    public String getTuboBHC() {
        return tuboBHC;
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

    public String getPinchazos() {
        return pinchazos;
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

    public String getVolumenRojo() {
        return volumenRojo;
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
