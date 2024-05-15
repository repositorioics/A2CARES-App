package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 8/7/2021.
 */
public class AdmisionPacientesFormLabels {

    private String pertenceEstudio;
    private String codigoPart;
    private String edadA;
    private String sexoA;
    private String edadF;
    private String sexoF;
    private String febril;
    private String generaHoja;
    private String anotarHoja;
    private String finAdmision;

    public AdmisionPacientesFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

        pertenceEstudio = res.getString(R.string.perteneceEstudio);
        codigoPart = res.getString(R.string.codigoParticipanteAdmision);
        febril = res.getString(R.string.febrilA);
        generaHoja = res.getString(R.string.generaHoja);
        anotarHoja = res.getString(R.string.anotarHoja);
        edadA = res.getString(R.string.edad);
        sexoA = res.getString(R.string.sexo);
        edadF = res.getString(R.string.edad);
        sexoF = res.getString(R.string.sexo);

    }

    public String getPertenceEstudio() {
        return pertenceEstudio;
    }
    public String getCodigoPart() {
        return codigoPart;
    }

    public String getEdadA() {
        return edadA;
    }

    public String getSexoA() {
        return sexoA;
    }
    public String getEdadF() {
        return edadF;
    }

    public String getSexoF() {
        return sexoF;
    }
    public String getFebril() {
        return febril;
    }

    public String getGeneraHoja() {
        return generaHoja;
    }

    public String getAnotarHoja() {
        return anotarHoja;
    }
    public String getFinAdmision() {
        return finAdmision;
    }
}
