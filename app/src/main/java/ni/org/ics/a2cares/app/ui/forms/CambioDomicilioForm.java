package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.ui.activities.cambioDomicilio.CambioDomicilioParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.MapaBarriosPage;
import ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

public class CambioDomicilioForm extends AbstractWizardModel {

    int index = 0;
    private CambioDomicilioFormLabels labels;
    private EstudioDBAdapter estudiosAdapter;


    private String[] fillBarrios(boolean incluirFueraSector){
        String[] catalogo;
        List<Barrio> barrios = estudiosAdapter.getBarrios(incluirFueraSector?null: MainDBConstants.nombre+" <> 'Fuera de Sector'", MainDBConstants.codigo);
        catalogo = new String[barrios.size()];
        index = 0;
        for (Barrio message: barrios){
            catalogo[index] = message.getNombre();
            index++;
        }
        return catalogo;
    }

    public CambioDomicilioForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new CambioDomicilioFormLabels();

        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();

        String[] catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        String[] barrios = fillBarrios(false);
        String[] catOperadora = estudiosAdapter.getMessageResourcesForCatalog("CAT_OPER_TEL");
        estudiosAdapter.close();

        Page cambioDomicilio = new SingleFixedChoicePage(this,labels.getCambioDomicilio(), labels.getAceptaCambioDomicilioHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);

        Page codigoNuevaCasaCohorte = new BarcodePage(this,labels.getCodigoNuevaCasaCohorte(),labels.getCodigoCasaCohorteHint(), Constants.WIZARD, false).setRangeValidation(true, 1, 2000).setRequired(true);

        Page otroParticipante = new MultipleFixedChoicePage(this,labels.getMoverOtroParticipante(), labels.getMoverOtroParticipanteHint(), Constants.WIZARD, false).setChoices().setRequired(false);

        Page nombre1JefeFamilia = new TextPage(this,labels.getNombre1JefeFamilia(), labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page apellido1JefeFamilia = new TextPage(this,labels.getApellido1JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page nombre2JefeFamilia = new TextPage(this,labels.getNombre2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page apellido2JefeFamilia = new TextPage(this,labels.getApellido2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(), "", Constants.WIZARD, false).setChoices(barrios).setRequired(true);
        Page direccion = new TextPage(this,labels.getDireccion(),labels.getDireccionHint(), Constants.WIZARD, false).setRequired(true);
        Page ubicacion = new MapaBarriosPage(this, labels.getCoordenadas(), "", Constants.WIZARD, false).setRequired(true);

        Page tieneTelefono = new SingleFixedChoicePage(this,labels.getTieneTelefonoCel(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Page tipoTel1 = new SingleFixedChoicePage(this,labels.getTipoTelefono1(),labels.getTipoTelefono1Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel1 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono1(),labels.getOperadoraTelefono1Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel1 = new NumberPage(this,labels.getNumTelefono1(),labels.getNumTelefono1Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7]{1}\\d{7}$").setRequired(true);
        Page tieneOtroTelefono = new SingleFixedChoicePage(this,labels.getTieneOtroTelefonoCel(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Page tipoTel2 = new SingleFixedChoicePage(this,labels.getTipoTelefono2(),labels.getTipoTelefono2Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel2 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono2(),labels.getOperadoraTelefono2Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel2 = new NumberPage(this,labels.getNumTelefono2(),labels.getNumTelefono2Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7]{1}\\d{7}$").setRequired(true);
        Page tieneTelefonoConv = new SingleFixedChoicePage(this,labels.getTieneTelefonoConv(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page operadoraTel3 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono3(),labels.getOperadoraTelefono3Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel3 = new NumberPage(this,labels.getNumTelefono3(),labels.getNumTelefono3Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[2]{1}\\d{7}$").setRequired(true);

        Page finCambioDomicilioLabel = new LabelPage(this,labels.getFinCambioDomicilioLabel(),"", Constants.WIZARD, false).setRequired(false);

        return new PageList(cambioDomicilio, codigoNuevaCasaCohorte, otroParticipante, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia, apellido2JefeFamilia, barrio, direccion,
                ubicacion, tieneTelefono, operadoraTel1, numeroTel1, tieneOtroTelefono, operadoraTel2, numeroTel2, tieneTelefonoConv,operadoraTel3, numeroTel3,
                finCambioDomicilioLabel);
    }

    public CambioDomicilioFormLabels getLabels() {
        return labels;
    }

    public void setLabels(CambioDomicilioFormLabels labels) {
        this.labels = labels;
    }
}
