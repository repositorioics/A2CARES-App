package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import org.joda.time.DateMidnight;

import java.util.Date;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.NewDatePage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

/**
 * Created by Miguel Salinas on 8/7/2021.
 */
public class AdmisionPacientesForm extends AbstractWizardModel {

    private EstudioDBAdapter estudiosAdapter;
    private AdmisionPacientesFormLabels labels;

    private String[] catSiNo;
    private String[] catSinMuestra;
    private String[] catPinchazos;
    private String[] catObservacion;
    private String[] catSexo;

    public AdmisionPacientesForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {

        labels = new AdmisionPacientesFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catSexo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SEXO");
        catSinMuestra = estudiosAdapter.getMessageResourcesForCatalog("CAT_RAZON_NO_MX");
        catPinchazos = estudiosAdapter.getMessageResourcesForCatalog("CAT_PINCHAZOS");
        catObservacion = estudiosAdapter.getMessageResourcesForCatalog("CAT_OBSERV_MX");
        estudiosAdapter.close();

        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page perteneceEstudio = new SingleFixedChoicePage(this, labels.getPertenceEstudio(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page codigoPart = new TextPage(this, labels.getCodigoPart(), "", Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page febril = new SingleFixedChoicePage(this, labels.getFebril(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);


        Page generarHoja = new LabelPage(this,labels.getGeneraHoja(),"", Constants.WIZARD, false) .setRequired(false);
        //Page anotaHoja = new NumberPage(this, labels.getAnotarHoja(), "", Constants.WIZARD, false).setRangeValidation(true, 0,3000).setRequired(true);
        Page anotaHoja = new BarcodePage(this,labels.getAnotarHoja(),"",Constants.WIZARD, false).setRequired(true);
        Page edad = new NumberPage(this, labels.getEdadA(), "", Constants.WIZARD, true).setRangeValidation(true, 2,80).setRequired(true);
        Page sexo = new SingleFixedChoicePage(this, labels.getSexoA(), "", Constants.WIZARD, true).setChoices(catSexo).setRequired(true);

        Page finAdmision = new LabelPage(this,labels.getFinAdmision(),"", Constants.WIZARD, true).setRequired(false);

        return new PageList(perteneceEstudio,codigoPart,febril, edad, sexo, generarHoja, anotaHoja);
        //return new PageList(fechaMuestra, volumen, tuboRojo, codigoRojo, volumenRojo, razonNoRojo, otraRazonNoRojo, pinchazos, observacion, descOtraObservacion);
    }
}
