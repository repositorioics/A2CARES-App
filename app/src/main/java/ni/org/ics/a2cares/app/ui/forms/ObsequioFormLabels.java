package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 06/11/2018.
 * V1.0
 */
public class ObsequioFormLabels {

    private String entregoObsequio;
    private String personaRecibe;
    private String observacion;

    public ObsequioFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        entregoObsequio = res.getString(R.string.entregoObsequio);
        personaRecibe = res.getString(R.string.personaRecibe);
        observacion = res.getString(R.string.observacion);
    }

    public String getEntregoObsequio() {
        return entregoObsequio;
    }

    public String getPersonaRecibe() {
        return personaRecibe;
    }

    public String getObservacion() {
        return observacion;
    }
}
