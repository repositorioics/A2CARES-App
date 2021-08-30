package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
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

        return new PageList(visitaExitosa, razonVisitaNoExitosa, otraRazonVisitaNoExitosa);
    }

    public VisitaTerrenoFormLabels getLabels() {
        return labels;
    }

    public void setLabels(VisitaTerrenoFormLabels labels) {
        this.labels = labels;
    }
}
