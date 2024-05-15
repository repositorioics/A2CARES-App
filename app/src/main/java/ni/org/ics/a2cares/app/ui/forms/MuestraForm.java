package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import org.joda.time.DateMidnight;

import java.util.Date;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.DatePage;
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
    private MuestraForm1Labels labels1;

    private String[] catSiNo;
    private String[] catSinMuestra;
    private String[] catPinchazos;
    private String[] catObservacion;

    public MuestraForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {

        labels1 = new MuestraForm1Labels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catSinMuestra = estudiosAdapter.getMessageResourcesForCatalog("CAT_RAZON_NO_MX");
        catPinchazos = estudiosAdapter.getMessageResourcesForCatalog("CAT_PINCHAZOS");
        catObservacion = estudiosAdapter.getMessageResourcesForCatalog("CAT_OBSERV_MX");
        estudiosAdapter.close();

        //DateMidnight dmHasta = new DateMidnight(new Date().getTime());
       // Date dmHasta1 = new Date (new Date().getTime());

      //  Page fechaMuestra = new NewDatePage(this, labels.getFechaMuestra(), "", Constants.WIZARD, false).setRangeValidation(true, dmHasta, dmHasta).setRequired(false);
      //  Page fechaMuestra = new TextPage(this, labels.getFechaMuestra(), "", Constants.WIZARD, true).setRequired(false);
       // Page fechaMuestra = new TextPage(this,labels.getFechaMuestra(),"", Constants.WIZARD, true).setRequired(false);

        //Page volumen = new LabelPage(this, "<font color='red'>"+labels.getVolumenRojoSugerido()+ "</font><br/><font color='#B941E0'>"+labels.getVolumenBHCSugerido() + "</font>", "", Constants.WIZARD, true).setRequired(true);
        Page volumen1 = new LabelPage(this, "<font color='red'>"+labels1.getVolumenRojoSugerido()+ "</font>", "", Constants.WIZARD, true).setRequired(false);
        Page tuboRojo1 = new SingleFixedChoicePage(this, labels1.getTuboRojo(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(false);
        Page codigoRojo1 = new BarcodePage(this, labels1.getCodigoRojo(), "", Constants.WIZARD, true).setRequired(true);
        Page volumenRojo1 = new NumberPage(this, labels1.getVolumenRojo(), "", Constants.WIZARD, true).setRangeValidation(true, Constants.VOLUMEN_MIN_ROJO,Constants.VOLUMEN_MAX_ROJO).setRequired(true);
        Page razonNoRojo1 = new SingleFixedChoicePage(this, labels1.getRazonNoRojo(), "", Constants.WIZARD, true).setChoices(catSinMuestra).setRequired(true);
        Page otraRazonNoRojo1= new TextPage(this,labels1.getOtraRazonNoRojo(),"", Constants.WIZARD, true).setRequired(false);
        Page tuboBHC1 = new SingleFixedChoicePage(this, labels1.getTuboBHC(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page codigoBHC1 = new BarcodePage(this, labels1.getCodigoBHC(), "", Constants.WIZARD, true).setRequired(true);
        Page volumenBHC1 = new NumberPage(this, labels1.getVolumenBHC(), "", Constants.WIZARD, true).setRangeValidation(true, 1,3).setRequired(true);
        Page razonNoBHC1 = new SingleFixedChoicePage(this, labels1.getRazonNoBHC(), "", Constants.WIZARD, true).setChoices(catSinMuestra).setRequired(true);
        Page otraRazonNoBHC1 = new TextPage(this,labels1.getOtraRazonNoBHC(),"", Constants.WIZARD, true).setRequired(false);

        Page pinchazos = new SingleFixedChoicePage(this, labels1.getPinchazos(), "", Constants.WIZARD, true).setChoices(catPinchazos).setRequired(true);
        Page observacion = new SingleFixedChoicePage(this, labels1.getObservacion(), "", Constants.WIZARD, false).setChoices(catObservacion).setRequired(true);
        Page descOtraObservacion = new TextPage(this, labels1.getDescOtraObservacion(), labels1.getDescOtraObservacionHint(), Constants.WIZARD, false).setRequired(true);
       // return new PageList(tuboRojo, codigoRojo, volumenRojo, razonNoRojo, otraRazonNoRojo, tuboBHC, codigoBHC, volumenBHC, razonNoBHC, otraRazonNoBHC, pinchazos, observacion, descOtraObservacion);
        return new PageList(volumen1, tuboRojo1, codigoRojo1, volumenRojo1, razonNoRojo1, otraRazonNoRojo1, tuboBHC1, codigoBHC1, volumenBHC1, razonNoBHC1, otraRazonNoBHC1, pinchazos, observacion, descOtraObservacion);
        //return new PageList(fechaMuestra, volumen, tuboRojo, codigoRojo, volumenRojo, razonNoRojo, otraRazonNoRojo, pinchazos, observacion, descOtraObservacion);
    }
}
