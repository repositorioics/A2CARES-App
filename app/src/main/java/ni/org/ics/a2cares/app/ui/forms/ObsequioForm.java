package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

public class ObsequioForm extends AbstractWizardModel {


	private ObsequioFormLabels labels;
	int index = 0;
    private String[] catSiNo;
    private EstudioDBAdapter estudiosAdapter;

    public ObsequioForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
    	labels = new ObsequioFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        estudiosAdapter.close();
        Page obsequioSN = new SingleFixedChoicePage(this, labels.getEntregoObsequio(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
		Page persona = new TextPage(this,labels.getPersonaRecibe(),"", Constants.WIZARD, false).setRequired(true);
        Page observaciones = new TextPage(this,labels.getObservacion(),"", Constants.WIZARD, true).setRequired(false);
        return new PageList(obsequioSN, persona, observaciones);
    }

	public ObsequioFormLabels getLabels() {
		return labels;
	}

	public void setLabels(ObsequioFormLabels labels) {
		this.labels = labels;
	}
    
}
