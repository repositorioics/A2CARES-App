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
}
