package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

/**
 * Created by Miguel Salinas on 07/06/2021.
 * V1.0
 */
public class EncuestaPesoTallaForm extends AbstractWizardModel {

    int index = 0;
    private String[] catSiNo;

    private EstudioDBAdapter estudiosAdapter;
    private EncuestaPesoTallaFormLabels labels;

    public EncuestaPesoTallaForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new EncuestaPesoTallaFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        estudiosAdapter.close();

        Page scTomoMedidaSna = new SingleFixedChoicePage(this, labels.getTomoMedidaSn(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page tpRazonNoTomoMedidas = new TextPage(this, labels.getRazonNoTomoMedidas(), "", Constants.WIZARD, false).setRequired(true);
        Page npPeso1 = new NumberPage(this, labels.getPeso1(), labels.getPesoHint(), Constants.WIZARD, false).setRangeValidation(true, 10, 200).setRequired(true);
        Page npTalla1 = new NumberPage(this, labels.getTalla1(), labels.getTallaHint(), Constants.WIZARD, false).setRangeValidation(true, 60, 200).setRequired(true);
        Page imc1 = new LabelPage(this, labels.getImc1(), "", Constants.WIZARD, false).setRequired(true);
        Page npPeso2 = new NumberPage(this, labels.getPeso2(), labels.getPesoHint(), Constants.WIZARD, false).setRangeValidation(true, 10, 200).setRequired(true);
        Page npTalla2 = new NumberPage(this, labels.getTalla2(), labels.getTallaHint(), Constants.WIZARD, false).setRangeValidation(true, 60, 200).setRequired(true);
        Page imc2 = new LabelPage(this, labels.getImc2(), "", Constants.WIZARD, false).setRequired(true);
        Page npPeso3 = new NumberPage(this, labels.getPeso3(), labels.getPesoHint(), Constants.WIZARD, false).setRangeValidation(true, 10, 200).setRequired(true);
        Page npTalla3 = new NumberPage(this, labels.getTalla3(), labels.getTallaHint(), Constants.WIZARD, false).setRangeValidation(true, 60, 200).setRequired(true);
        Page imc3 = new LabelPage(this, labels.getImc3(), "", Constants.WIZARD, false).setRequired(false);
        //Page difPeso = new NumberPage(this, labels.getDifPeso(), "", Constants.WIZARD, false).setRequired(true);
        //Page difTalla = new NumberPage(this, labels.getDifTalla(), "", Constants.WIZARD, false).setRequired(true);
        Page difMediciones = new LabelPage(this, labels.getDifMediciones(), "", Constants.ROJO, false).setRequired(true);

        return new PageList(scTomoMedidaSna, tpRazonNoTomoMedidas, npPeso1,npTalla1,imc1, npPeso2, npTalla2, imc2, difMediciones, npPeso3, npTalla3, imc3);
    }
}
