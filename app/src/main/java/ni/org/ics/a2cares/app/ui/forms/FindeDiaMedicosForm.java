package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import org.joda.time.DateMidnight;

import java.util.Date;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.BarcodePage;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

/**
 * Created by Everts Morales Reyes.
 */
public class FindeDiaMedicosForm extends AbstractWizardModel {

    private EstudioDBAdapter estudiosAdapter;
    private FinDiaMedicosFormLabels labels;

    private String[] catSiNo;
    private String[] CAT_PUESTO_SALUD;
    private String[] catPinchazos;
    private String[] catObservacion;
    private String[] catSexo;

    public FindeDiaMedicosForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {

        labels = new FinDiaMedicosFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catSexo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SEXO");
        CAT_PUESTO_SALUD = estudiosAdapter.getMessageResourcesForCatalog("CAT_PUESTO_SALUD");
        catPinchazos = estudiosAdapter.getMessageResourcesForCatalog("CAT_PINCHAZOS");
        catObservacion = estudiosAdapter.getMessageResourcesForCatalog("CAT_OBSERV_MX");
        estudiosAdapter.close();

        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page puestoSalud = new SingleFixedChoicePage(this, labels.getPuestoSalud(), "", Constants.WIZARD, true).setChoices(CAT_PUESTO_SALUD).setRequired(true);
        Page numeroPartAtendidos = new NumberPage(this, labels.getNumeroPartAtendidos(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);

        Page codPartAtend1 = new NumberPage(this, labels.getCodPartAtend1(), labels.getCodPartAtend1(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend1Diagnostico = new TextPage(this, labels.getCodPartAtend1Diagnostico(), labels.getCodPartAtend1Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend2 = new NumberPage(this, labels.getCodPartAtend2(), labels.getCodPartAtend2(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend2Diagnostico = new TextPage(this, labels.getCodPartAtend2Diagnostico(), labels.getCodPartAtend2Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend3 = new NumberPage(this, labels.getCodPartAtend3(), labels.getCodPartAtend3(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend3Diagnostico = new TextPage(this, labels.getCodPartAtend3Diagnostico(), labels.getCodPartAtend3Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend4 = new NumberPage(this, labels.getCodPartAtend4(), labels.getCodPartAtend4(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend4Diagnostico = new TextPage(this, labels.getCodPartAtend4Diagnostico(), labels.getCodPartAtend4Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend5 = new NumberPage(this, labels.getCodPartAtend5(), labels.getCodPartAtend5(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend6 = new NumberPage(this, labels.getCodPartAtend6(), labels.getCodPartAtend6(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend7 = new NumberPage(this, labels.getCodPartAtend7(), labels.getCodPartAtend7(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend8 = new NumberPage(this, labels.getCodPartAtend8(), labels.getCodPartAtend8(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend9 = new NumberPage(this, labels.getCodPartAtend9(), labels.getCodPartAtend9(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend10 = new NumberPage(this, labels.getCodPartAtend10(), labels.getCodPartAtend10(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend11 = new NumberPage(this, labels.getCodPartAtend11(), labels.getCodPartAtend11(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend12 = new NumberPage(this, labels.getCodPartAtend12(), labels.getCodPartAtend12(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend13 = new NumberPage(this, labels.getCodPartAtend13(), labels.getCodPartAtend13(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend14 = new NumberPage(this, labels.getCodPartAtend14(), labels.getCodPartAtend14(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend15 = new NumberPage(this, labels.getCodPartAtend15(), labels.getCodPartAtend15(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend16 = new NumberPage(this, labels.getCodPartAtend16(), labels.getCodPartAtend16(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend17 = new NumberPage(this, labels.getCodPartAtend17(), labels.getCodPartAtend17(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend18 = new NumberPage(this, labels.getCodPartAtend18(), labels.getCodPartAtend18(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend19 = new NumberPage(this, labels.getCodPartAtend19(), labels.getCodPartAtend19(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartAtend20 = new NumberPage(this, labels.getCodPartAtend20(), labels.getCodPartAtend20(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);





        Page codPartAtend5Diagnostico = new TextPage(this, labels.getCodPartAtend5Diagnostico(), labels.getCodPartAtend5Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend6Diagnostico = new TextPage(this, labels.getCodPartAtend6Diagnostico(), labels.getCodPartAtend6Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend7Diagnostico = new TextPage(this, labels.getCodPartAtend7Diagnostico(), labels.getCodPartAtend7Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend8Diagnostico = new TextPage(this, labels.getCodPartAtend8Diagnostico(), labels.getCodPartAtend8Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend9Diagnostico = new TextPage(this, labels.getCodPartAtend9Diagnostico(), labels.getCodPartAtend9Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend10Diagnostico = new TextPage(this, labels.getCodPartAtend10Diagnostico(), labels.getCodPartAtend10Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend11Diagnostico = new TextPage(this, labels.getCodPartAtend11Diagnostico(), labels.getCodPartAtend11Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend12Diagnostico = new TextPage(this, labels.getCodPartAtend12Diagnostico(), labels.getCodPartAtend12Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend13Diagnostico = new TextPage(this, labels.getCodPartAtend13Diagnostico(), labels.getCodPartAtend13Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend14Diagnostico = new TextPage(this, labels.getCodPartAtend14Diagnostico(), labels.getCodPartAtend14Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend15Diagnostico = new TextPage(this, labels.getCodPartAtend15Diagnostico(), labels.getCodPartAtend15Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend16Diagnostico = new TextPage(this, labels.getCodPartAtend16Diagnostico(), labels.getCodPartAtend16Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend17Diagnostico = new TextPage(this, labels.getCodPartAtend17Diagnostico(), labels.getCodPartAtend17Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend18Diagnostico = new TextPage(this, labels.getCodPartAtend18Diagnostico(), labels.getCodPartAtend18Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend19Diagnostico = new TextPage(this, labels.getCodPartAtend19Diagnostico(), labels.getCodPartAtend19Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartAtend20Diagnostico = new TextPage(this, labels.getCodPartAtend20Diagnostico(), labels.getCodPartAtend20Diagnostico(), Constants.WIZARD, false).setRequired(true);

        Page numPartNoCohorteAtendidos = new NumberPage(this, labels.getNumPartNoCohorteAtendidos(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page totalPartAtendidos = new NumberPage(this, labels.getTotalPartAtendidos(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numPacienFebrilAtendidos = new NumberPage(this, labels.getNumPacienFebrilAtendidos(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numInfeccionRespAguda = new NumberPage(this, labels.getNumInfeccionRespAguda(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numEnfermedadDiarrAguda = new NumberPage(this, labels.getNumEnfermedadDiarrAguda(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numEti = new NumberPage(this, labels.getNumEti(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numRag = new NumberPage(this, labels.getNumRag(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numConjuntivitis = new NumberPage(this, labels.getNumConjuntivitis(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numControlParental = new NumberPage(this, labels.getNumControlParental(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numNeumonia = new NumberPage(this, labels.getNumNeumonia(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numPap = new NumberPage(this, labels.getNumPap(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numPlanifFamiliar = new NumberPage(this, labels.getNumPlanifFamiliar(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numGotaGruesa = new NumberPage(this, labels.getNumGotaGruesa(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numPacientesCronicos = new NumberPage(this, labels.getNumPacientesCronicos(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numTraslados = new NumberPage(this, labels.getNumTraslados(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numCaptacionA = new NumberPage(this, labels.getNumCaptacionA(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numCaptacionB = new NumberPage(this, labels.getNumCaptacionB(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numCaptacionC = new NumberPage(this, labels.getNumCaptacionC(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numCaptacionD = new NumberPage(this, labels.getNumCaptacionD(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numSeguimientoA = new NumberPage(this, labels.getNumSeguimientoA(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numSeguimientoB = new NumberPage(this, labels.getNumSeguimientoB(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numSeguimientoD = new NumberPage(this, labels.getNumSeguimientoD(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numConvaPendientes = new NumberPage(this, labels.getNumConvaPendientes(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numVisitaTerreno = new NumberPage(this, labels.getNumVisitaTerreno(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page numPartTrasladados = new NumberPage(this, labels.getNumPartTrasladados(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page codPartTraslado1Diagnostico = new TextPage(this, labels.getCodPartTraslado1Diagnostico(), labels.getCodPartTraslado1Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado2Diagnostico = new TextPage(this, labels.getCodPartTraslado2Diagnostico(), labels.getCodPartTraslado2Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado3Diagnostico = new TextPage(this, labels.getCodPartTraslado3Diagnostico(), labels.getCodPartTraslado3Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado4Diagnostico = new TextPage(this, labels.getCodPartTraslado4Diagnostico(), labels.getCodPartTraslado4Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado5Diagnostico = new TextPage(this, labels.getCodPartTraslado5Diagnostico(), labels.getCodPartTraslado5Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado6Diagnostico = new TextPage(this, labels.getCodPartTraslado6Diagnostico(), labels.getCodPartTraslado6Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado7Diagnostico = new TextPage(this, labels.getCodPartTraslado7Diagnostico(), labels.getCodPartTraslado7Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado8Diagnostico = new TextPage(this, labels.getCodPartTraslado8Diagnostico(), labels.getCodPartTraslado8Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado9Diagnostico = new TextPage(this, labels.getCodPartTraslado9Diagnostico(), labels.getCodPartTraslado9Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado10Diagnostico = new TextPage(this, labels.getCodPartTraslado10Diagnostico(), labels.getCodPartTraslado10Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codPartTraslado1 = new NumberPage(this, labels.getCodPartTraslado1(), labels.getCodPartTraslado1(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado2 = new NumberPage(this, labels.getCodPartTraslado2(), labels.getCodPartTraslado2(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado3 = new NumberPage(this, labels.getCodPartTraslado3(), labels.getCodPartTraslado3(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado4 = new NumberPage(this, labels.getCodPartTraslado4(), labels.getCodPartTraslado4(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado5 = new NumberPage(this, labels.getCodPartTraslado5(), labels.getCodPartTraslado5(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado6 = new NumberPage(this, labels.getCodPartTraslado6(), labels.getCodPartTraslado6(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado7 = new NumberPage(this, labels.getCodPartTraslado7(), labels.getCodPartTraslado7(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado8 = new NumberPage(this, labels.getCodPartTraslado8(), labels.getCodPartTraslado8(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado9 = new NumberPage(this, labels.getCodPartTraslado9(), labels.getCodPartTraslado9(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codPartTraslado10 = new NumberPage(this, labels.getCodPartTraslado10(), labels.getCodPartTraslado10(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);



        Page numPartNegatTraslado = new NumberPage(this, labels.getNumPartNegatTraslado(), "", Constants.WIZARD, true).setRangeValidation(true, 0,100).setRequired(true);
        Page codNegaPartTraslado1Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado1Diagnostico(), labels.getCodNegaPartTraslado1Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado2Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado2Diagnostico(), labels.getCodNegaPartTraslado2Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado3Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado3Diagnostico(), labels.getCodNegaPartTraslado3Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado4Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado4Diagnostico(), labels.getCodNegaPartTraslado4Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado5Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado5Diagnostico(), labels.getCodNegaPartTraslado5Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado6Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado6Diagnostico(), labels.getCodNegaPartTraslado6Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado7Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado7Diagnostico(), labels.getCodNegaPartTraslado7Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado8Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado8Diagnostico(), labels.getCodNegaPartTraslado8Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado9Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado9Diagnostico(), labels.getCodNegaPartTraslado9Diagnostico(), Constants.WIZARD, false).setRequired(true);
        Page codNegaPartTraslado10Diagnostico = new TextPage(this, labels.getCodNegaPartTraslado10Diagnostico(), labels.getCodNegaPartTraslado10Diagnostico(), Constants.WIZARD, false).setRequired(true);

        Page codNegatPartTraslado1 = new NumberPage(this, labels.getCodNegatPartTraslado1(), labels.getCodNegatPartTraslado1(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado2 = new NumberPage(this, labels.getCodNegatPartTraslado2(), labels.getCodNegatPartTraslado2(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado3 = new NumberPage(this, labels.getCodNegatPartTraslado3(), labels.getCodNegatPartTraslado3(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado4 = new NumberPage(this, labels.getCodNegatPartTraslado4(), labels.getCodNegatPartTraslado4(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado5 = new NumberPage(this, labels.getCodNegatPartTraslado5(), labels.getCodNegatPartTraslado5(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado6 = new NumberPage(this, labels.getCodNegatPartTraslado6(), labels.getCodNegatPartTraslado6(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado7 = new NumberPage(this, labels.getCodNegatPartTraslado7(), labels.getCodNegatPartTraslado7(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado8 = new NumberPage(this, labels.getCodNegatPartTraslado8(), labels.getCodNegatPartTraslado8(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado9 = new NumberPage(this, labels.getCodNegatPartTraslado9(), labels.getCodNegatPartTraslado9(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page codNegatPartTraslado10 = new NumberPage(this, labels.getCodNegatPartTraslado10(), labels.getCodNegatPartTraslado10(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);

        Page codNegatPartTraslado11 = new NumberPage(this, labels.getCodNegatPartTraslado10(), labels.getCodNegatPartTraslado10(), Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);

        return new PageList(puestoSalud,numeroPartAtendidos,codPartAtend1,codPartAtend2,codPartAtend3,codPartAtend4,codPartAtend5,
                codPartAtend6,codPartAtend7,codPartAtend8,codPartAtend9,codPartAtend10,codPartAtend11,codPartAtend12,codPartAtend13,codPartAtend14,codPartAtend15,
                codPartAtend16,codPartAtend17,codPartAtend18,codPartAtend19,codPartAtend20,
                codPartAtend1Diagnostico,codPartAtend2Diagnostico,codPartAtend3Diagnostico,codPartAtend4Diagnostico,codPartAtend5Diagnostico,
                codPartAtend6Diagnostico,codPartAtend7Diagnostico,codPartAtend8Diagnostico,codPartAtend9Diagnostico,codPartAtend10Diagnostico,codPartAtend11Diagnostico,codPartAtend12Diagnostico,codPartAtend13Diagnostico,codPartAtend14Diagnostico,codPartAtend15Diagnostico,
                codPartAtend16Diagnostico,codPartAtend17Diagnostico,codPartAtend18Diagnostico,codPartAtend19Diagnostico,codPartAtend20Diagnostico,
                numPartNoCohorteAtendidos, totalPartAtendidos, numPacienFebrilAtendidos, numInfeccionRespAguda,numEnfermedadDiarrAguda,numEti,numRag,numConjuntivitis,numControlParental,
                numNeumonia,numPap,numPlanifFamiliar,numGotaGruesa,numPacientesCronicos,numTraslados,numCaptacionA,numCaptacionB,numCaptacionC,
                numCaptacionD,numSeguimientoA,numSeguimientoB,numSeguimientoD,numConvaPendientes,numVisitaTerreno,numPartTrasladados,
                codPartTraslado1,codPartTraslado2,codPartTraslado3,codPartTraslado4,codPartTraslado5,codPartTraslado6,codPartTraslado7,codPartTraslado8,codPartTraslado9,codPartTraslado10,
                codPartTraslado1Diagnostico,codPartTraslado2Diagnostico,codPartTraslado3Diagnostico,codPartTraslado4Diagnostico,codPartTraslado5Diagnostico,codPartTraslado6Diagnostico,
                codPartTraslado7Diagnostico,codPartTraslado8Diagnostico,codPartTraslado9Diagnostico,codPartTraslado10Diagnostico,
                numPartNegatTraslado,codNegatPartTraslado1,codNegatPartTraslado2,codNegatPartTraslado3,codNegatPartTraslado4,codNegatPartTraslado5,codNegatPartTraslado6,codNegatPartTraslado7,
                codNegatPartTraslado8,codNegatPartTraslado9,codNegatPartTraslado10,
                codNegaPartTraslado1Diagnostico,codNegaPartTraslado2Diagnostico,codNegaPartTraslado3Diagnostico,codNegaPartTraslado4Diagnostico,codNegaPartTraslado5Diagnostico,codNegaPartTraslado6Diagnostico,
                codNegaPartTraslado7Diagnostico,codNegaPartTraslado8Diagnostico,codNegaPartTraslado9Diagnostico,codNegaPartTraslado10Diagnostico
                );

    }
}
