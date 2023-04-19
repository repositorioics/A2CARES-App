package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 2/6/2021.
 */
public class VisitaTerrenoFormLabels {
    protected String fechaVisita;
    protected String fechaVisitaHint;
    protected String visitaExitosa;
    protected String visitaExitosaHint;
    protected String razonVisitaNoExitosa;
    protected String razonVisitaNoExitosaHint;
    protected String otraRazonVisitaNoExitosa;
    protected String otraRazonVisitaNoExitosaHint;
    protected String direccion_cambio_domicilio;
    protected String direccion_cambio_domicilioHint ;
    protected String telefono_cambio_domicilio;
    protected String telefono_cambio_domicilioHint ;

    public VisitaTerrenoFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();
        fechaVisita = res.getString(R.string.fechaVisita);
        fechaVisitaHint = res.getString(R.string.fechaVisitaHint);
        visitaExitosa = res.getString(R.string.visitaExitosa);
        visitaExitosaHint = res.getString(R.string.visitaExitosaHint);
        razonVisitaNoExitosa = res.getString(R.string.razonVisitaNoExitosa);
        razonVisitaNoExitosaHint = res.getString(R.string.razonVisitaNoExitosaHint);
        otraRazonVisitaNoExitosa = res.getString(R.string.otraRazonVisitaNoExitosa);
        otraRazonVisitaNoExitosaHint = res.getString(R.string.otraRazonVisitaNoExitosaHint);
        direccion_cambio_domicilio = res.getString(R.string.direccion_cambio_domicilio );
        telefono_cambio_domicilio = res.getString(R.string.telefono_cambio_domicilio );
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public String getFechaVisitaHint() {
        return fechaVisitaHint;
    }

    public String getVisitaExitosa() {
        return visitaExitosa;
    }

    public String getVisitaExitosaHint() {
        return visitaExitosaHint;
    }

    public String getRazonVisitaNoExitosa() {
        return razonVisitaNoExitosa;
    }

    public String getRazonVisitaNoExitosaHint() {
        return razonVisitaNoExitosaHint;
    }

    public String getOtraRazonVisitaNoExitosa() {
        return otraRazonVisitaNoExitosa;
    }
    public String getOtraRazonVisitaNoExitosaHint() {
        return otraRazonVisitaNoExitosaHint;
    }

    public String getDireccion_cambio_domicilio() {
        return direccion_cambio_domicilio;
    }

    public String getDireccion_cambio_domicilioHint() {
        return direccion_cambio_domicilioHint;
    }
    public String getTelefono_cambio_domicilio() {
        return telefono_cambio_domicilio;
    }

    public String getTelefono_cambio_domicilioHint () {
        return telefono_cambio_domicilio;
    }



}
