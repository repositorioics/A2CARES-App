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
public class MuestraForm extends AbstractWizardModel {

    private EstudioDBAdapter estudiosAdapter;
    private MuestraFormLabels labels;

    private String[] catSiNo;
    private String[] catSinMuestra;
    private String[] catPinchazos;
    private String[] catObservacion;

    public MuestraForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {

        labels = new MuestraFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catSinMuestra = estudiosAdapter.getMessageResourcesForCatalog("CAT_RAZON_NO_MX");
        catPinchazos = estudiosAdapter.getMessageResourcesForCatalog("CAT_PINCHAZOS");
        catObservacion = estudiosAdapter.getMessageResourcesForCatalog("CAT_OBSERV_MX");
        estudiosAdapter.close();

        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page fechaMuestra = new NewDatePage(this, labels.getFechaMuestra(), "", Constants.WIZARD, true).setRangeValidation(true, dmHasta, dmHasta).setRequired(true);
        //Page volumen = new LabelPage(this, "<font color='red'>"+labels.getVolumenRojoSugerido()+ "</font><br/><font color='#B941E0'>"+labels.getVolumenBHCSugerido() + "</font>", "", Constants.WIZARD, true).setRequired(true);
        Page volumen = new LabelPage(this, "<font color='red'>"+labels.getVolumenRojoSugerido()+ "</font>", "", Constants.WIZARD, true).setRequired(true);
        Page tuboRojo = new SingleFixedChoicePage(this, labels.getTuboRojo(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page codigoRojo = new BarcodePage(this, labels.getCodigoRojo(), "", Constants.WIZARD, true).setRequired(true);
        Page volumenRojo = new NumberPage(this, labels.getVolumenRojo(), "", Constants.WIZARD, true).setRangeValidation(true, Constants.VOLUMEN_MIN_ROJO,Constants.VOLUMEN_MAX_ROJO).setRequired(true);
        Page razonNoRojo = new SingleFixedChoicePage(this, labels.getRazonNoRojo(), "", Constants.WIZARD, true).setChoices(catSinMuestra).setRequired(true);
        Page otraRazonNoRojo = new TextPage(this,labels.getOtraRazonNoRojo(),"", Constants.WIZARD, true).setRequired(false);
        /*Page tuboBHC = new SingleFixedChoicePage(this, labels.getTuboBHC(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page codigoBHC = new BarcodePage(this, labels.getCodigoBHC(), "", Constants.WIZARD, true).setRequired(true);
        Page volumenBHC = new NumberPage(this, labels.getVolumenBHC(), "", Constants.WIZARD, true).setRangeValidation(true, 1,3).setRequired(true);
        Page razonNoBHC = new SingleFixedChoicePage(this, labels.getRazonNoBHC(), "", Constants.WIZARD, true).setChoices(catSinMuestra).setRequired(true);
        Page otraRazonNoBHC = new TextPage(this,labels.getOtraRazonNoBHC(),"", Constants.WIZARD, true).setRequired(false);
        */
        Page pinchazos = new SingleFixedChoicePage(this, labels.getPinchazos(), "", Constants.WIZARD, true).setChoices(catPinchazos).setRequired(true);
        Page observacion = new SingleFixedChoicePage(this, labels.getObservacion(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page descOtraObservacion = new TextPage(this, labels.getDescOtraObservacion(), labels.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);
        //return new PageList(fechaMuestra, volumen, tuboRojo, codigoRojo, volumenRojo, razonNoRojo, otraRazonNoRojo, tuboBHC, codigoBHC, volumenBHC, razonNoBHC, otraRazonNoBHC, pinchazos);
        return new PageList(fechaMuestra, volumen, tuboRojo, codigoRojo, volumenRojo, razonNoRojo, otraRazonNoRojo, pinchazos, observacion, descOtraObservacion);
    }
}
