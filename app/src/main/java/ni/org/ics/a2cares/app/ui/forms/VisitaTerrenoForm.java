package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

/**
 * Created by Miguel Salinas on 2/6/2021.
 */
public class VisitaTerrenoForm extends AbstractWizardModel {

    int index = 0;
    private VisitaTerrenoFormLabels labels;
    private EstudioDBAdapter estudiosAdapter;
    private String[] catSiNo;
    private String[] catRazonVisitaNoExitosa;

    public VisitaTerrenoForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new VisitaTerrenoFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catRazonVisitaNoExitosa = estudiosAdapter.getMessageResourcesForCatalog("CAT_NO_VISITA");
        estudiosAdapter.close();

        Page visitaExitosa = new SingleFixedChoicePage(this,labels.getVisitaExitosa(),labels.getVisitaExitosaHint(), Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);

        Page razonVisitaNoExitosa = new SingleFixedChoicePage(this,labels.getRazonVisitaNoExitosa(),labels.getRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setChoices(catRazonVisitaNoExitosa).setRequired(true);
        Page otraRazonVisitaNoExitosa = new TextPage(this,labels.getOtraRazonVisitaNoExitosa(),labels.getOtraRazonVisitaNoExitosaHint(), Constants.WIZARD, false).setRequired(true);
        Page direccion_cambio_domicilio = new TextPage(this,labels.getDireccion_cambio_domicilio(),labels.getDireccion_cambio_domicilioHint(), Constants.WIZARD, false).setRequired(false);
        Page telefono_cambio_domicilio = new NumberPage(this,labels.getTelefono_cambio_domicilio(),labels.getTelefono_cambio_domicilioHint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7]{1}\\d{7}$").setRequired(false);
        Page telefono1  = new NumberPage(this,labels.getTelefono_1_Actualizado(),labels.getTelefono_1_Actualizado(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[2|3|8|5|7]{1}\\d{7}$").setRequired(false);
        Page telefono2  = new NumberPage(this,labels.getTelefono_2_Actualizado(),labels.getTelefono_2_Actualizado(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[2|3|8|5|7]{1}\\d{7}$").setRequired(false);

        return new PageList(visitaExitosa, razonVisitaNoExitosa, otraRazonVisitaNoExitosa,direccion_cambio_domicilio,telefono_cambio_domicilio,telefono1,telefono2);
    }

    public VisitaTerrenoFormLabels getLabels() {
        return labels;
    }

    public void setLabels(VisitaTerrenoFormLabels labels) {
        this.labels = labels;
    }
}
