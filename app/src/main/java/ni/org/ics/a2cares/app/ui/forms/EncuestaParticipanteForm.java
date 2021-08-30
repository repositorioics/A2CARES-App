package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.LabelPage;
import ni.org.ics.a2cares.app.wizard.model.MultipleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.NewDatePage;
import ni.org.ics.a2cares.app.wizard.model.NumberPage;
import ni.org.ics.a2cares.app.wizard.model.Page;
import ni.org.ics.a2cares.app.wizard.model.PageList;
import ni.org.ics.a2cares.app.wizard.model.SingleFixedChoicePage;
import ni.org.ics.a2cares.app.wizard.model.TextPage;

/**
 * Created by Miguel Salinas on 16/6/2021.
 */
public class EncuestaParticipanteForm extends AbstractWizardModel {

    private EstudioDBAdapter estudiosAdapter;
    private EncuestaParticipanteFormLabels labels;
    private String[] catSiNo;
    private String[] catGradoEdu; 
    private String[] catCuidanNino; 
    private String[] catTurno; 
    private String[] catConQuien;
    private String[] catSiNoDes;
    private String[] catTipoTrab;
    private String[] catFrecFuma;
    private String[] catTmpFiebre;
    private String[] catLugarCons;
    private String[] catAutomed;
    private String[] catNoCS;
    private String[] catCS;
    private String[] catDondeAsiste;
    private String[] catMeses;
    private String[] catEnroje;
    private String[] catRazonEman;
    private String[] catNivelEdu;
    //private String[] catQuienFuma;
    private String[] catPuesto;
    private String[] catLugarDx;
    private String[] catHospital;

    public EncuestaParticipanteForm(Context context, String pass) {
        super(context, pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new EncuestaParticipanteFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catGradoEdu = estudiosAdapter.getMessageResourcesForCatalog("CAT_GRD_EDU");
        catCuidanNino = estudiosAdapter.getMessageResourcesForCatalog("CAT_CUIDAN_NINO");
        catTurno = estudiosAdapter.getMessageResourcesForCatalog("CAT_TURNO");
        catConQuien = estudiosAdapter.getMessageResourcesForCatalog("CAT_VIVE_NINO");
        catSiNoDes = estudiosAdapter.getMessageResourcesForCatalog("CAT_SND");
        catTipoTrab = estudiosAdapter.getMessageResourcesForCatalog("CAT_TIP_TRABAJO");
        catFrecFuma = estudiosAdapter.getMessageResourcesForCatalog("CAT_FREC_FUMA");
        catTmpFiebre = estudiosAdapter.getMessageResourcesForCatalog("CAT_TMP_FIEBRE");
        catLugarCons = estudiosAdapter.getMessageResourcesForCatalog("CAT_LUGAR_CONSULTA");
        catAutomed = estudiosAdapter.getMessageResourcesForCatalog("CAT_AUTOMED_FIEBRE");
        catNoCS = estudiosAdapter.getMessageResourcesForCatalog("CAT_NO_ACUDIO_CS");
        catCS = estudiosAdapter.getMessageResourcesForCatalog("CAT_CENTRO_SALUD");
        catDondeAsiste = estudiosAdapter.getMessageResourcesForCatalog("CAT_DONDEASISTE");
        catMeses = estudiosAdapter.getMessageResourcesForCatalog("CAT_MESES");
        catEnroje = estudiosAdapter.getMessageResourcesForCatalog("CAT_DURO_ENROJECI");
        catRazonEman = estudiosAdapter.getMessageResourcesForCatalog("CAT_RAZON_EMAN");
        catNivelEdu = estudiosAdapter.getMessageResourcesForCatalog("CAT_NIV_EDU");
        //catQuienFuma = estudiosAdapter.getMessageResourcesForCatalog("CAT_QUIEN_FUMA");
        catPuesto = estudiosAdapter.getMessageResourcesForCatalog("CAT_PUESTO_SALUD");
        catLugarDx = estudiosAdapter.getMessageResourcesForCatalog("CAT_LUGAR_DIAG");
        catHospital = estudiosAdapter.getMessageResourcesForCatalog("CAT_HOSPITAL");

        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page emancipado = new SingleFixedChoicePage(this, labels.getEmancipado(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page descEmancipado = new SingleFixedChoicePage(this, labels.getDescEmancipado(), "", Constants.WIZARD, false).setChoices(catRazonEman).setRequired(true);
        Page otraDescEmanc = new TextPage(this, labels.getOtraDescEmanc(), "", Constants.WIZARD, false).setRequired(true);
        Page embarazada = new SingleFixedChoicePage(this, labels.getEmbarazada(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page conoceFur = new SingleFixedChoicePage(this, labels.getConoceFUR(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page fur = new NewDatePage(this, labels.getFur(), "", Constants.WIZARD, false).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);

        Page asisteEscuela = new SingleFixedChoicePage(this, labels.getAsisteEscuela(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page gradoEstudia = new SingleFixedChoicePage(this, labels.getGradoEstudia(), "", Constants.WIZARD, false).setChoices(catGradoEdu).setRequired(true);
        Page nombreEscuela = new TextPage(this, labels.getNombreEscuela(), "", Constants.WIZARD, false).setRequired(true);
        Page turno = new SingleFixedChoicePage(this, labels.getTurno(), "", Constants.WIZARD, false).setChoices(catTurno).setRequired(true);
        Page cuidan = new SingleFixedChoicePage(this, labels.getCuidan(), "", Constants.WIZARD, false).setChoices(catCuidanNino).setRequired(true);
        Page cuantosCuidan = new NumberPage(this, labels.getCuantosCuidan(), "", Constants.WIZARD, false).setRangeValidation(true, 1, 999).setRequired(true);
        Page nombreCDI = new TextPage(this, labels.getNombreCDI(), "", Constants.WIZARD, false).setRequired(true);
        Page direccionCDI = new TextPage(this, labels.getDireccionCDI(), "", Constants.WIZARD, false).setRequired(true);
        Page otroLugarCuidan = new TextPage(this, labels.getOtroLugarCuidan(), "", Constants.WIZARD, false).setRequired(true);

        Page alfabeto = new SingleFixedChoicePage(this, labels.getAlfabeto(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page nivelEducacion = new SingleFixedChoicePage(this, labels.getNivelEducacion(), "", Constants.WIZARD, true).setChoices(catNivelEdu).setRequired(true);

        Page trabaja = new SingleFixedChoicePage(this, labels.getTrabaja(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tipoTrabajo = new SingleFixedChoicePage(this, labels.getTipoTrabajo(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);
        Page ocupacionActual = new TextPage(this, labels.getOcupacionActual(), "", Constants.WIZARD, false).setRequired(true);

        Page personaVive = new SingleFixedChoicePage(this, labels.getPersonaVive(), "", Constants.WIZARD, false).setChoices(catConQuien).setRequired(true);
        Page ordenNac = new NumberPage(this, labels.getOrdenNac(), "", Constants.WIZARD, false).setRangeValidation(true, 1, 14).setRequired(true);
        Page padreAlfabeto = new SingleFixedChoicePage(this, labels.getPadreAlfabeto(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page papaNivel = new SingleFixedChoicePage(this, labels.getNivelEscolarPadre(), "", Constants.WIZARD, false).setChoices(catNivelEdu).setRequired(true);
        Page trabajaPadre = new SingleFixedChoicePage(this, labels.getTrabajaPadre(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page papaTipoTra = new SingleFixedChoicePage(this, labels.getTipoTrabajoPadre(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);
        Page madreAlfabeta = new SingleFixedChoicePage(this, labels.getMadreAlfabeta(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page mamaNivel = new SingleFixedChoicePage(this, labels.getNivelEscolarMadre(), "", Constants.WIZARD, false).setChoices(catNivelEdu).setRequired(true);
        Page trabajaMadre = new SingleFixedChoicePage(this, labels.getTrabajaMadre(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page mamaTipoTra = new SingleFixedChoicePage(this, labels.getTipoTrabajoMadre(), "", Constants.WIZARD, false).setChoices(catTipoTrab).setRequired(true);

        Page comparteHab = new SingleFixedChoicePage(this, labels.getComparteHab(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page hab1 = new NumberPage(this, labels.getHab1(), "", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
        Page hab2 = new NumberPage(this, labels.getHab2(), "", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
        Page hab3 = new NumberPage(this, labels.getHab3(), "", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
        Page hab4 = new NumberPage(this, labels.getHab4(), "", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
        Page hab5 = new NumberPage(this, labels.getHab5(), "", Constants.WIZARD, true).setRangeValidation(true, 0, 20).setRequired(true);
        Page hab6 = new LabelPage(this, labels.getHab6(), "", Constants.WIZARD, true).setRequired(true);
        Page comparteCama = new SingleFixedChoicePage(this, labels.getComparteCama(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page cama1 = new NumberPage(this, labels.getCama1(), "", Constants.WIZARD, true).setRequired(true);
        Page cama2 = new NumberPage(this, labels.getCama2(), "", Constants.WIZARD, true).setRequired(true);
        Page cama3 = new NumberPage(this, labels.getCama3(), "", Constants.WIZARD, true).setRequired(true);
        Page cama4 = new NumberPage(this, labels.getCama4(), "", Constants.WIZARD, true).setRequired(true);
        Page cama5 = new NumberPage(this, labels.getCama5(), "", Constants.WIZARD, true).setRequired(true);
        Page cama6 = new LabelPage(this, labels.getCama6(), "", Constants.WIZARD, true).setRequired(true);

        Page fuma = new SingleFixedChoicePage(this, labels.getFuma(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page periodicidadFuma = new SingleFixedChoicePage(this, labels.getPeriodicidadFuma(), "", Constants.WIZARD, false).setChoices(catFrecFuma).setRequired(true);
        Page cantidadCigarrillos = new NumberPage(this, labels.getCantidadCigarrillos(), "", Constants.WIZARD, false).setRequired(true);
        Page fumaDentroCasa = new SingleFixedChoicePage(this, labels.getFumaDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        /*
        Page alguienFuma = new SingleFixedChoicePage(this, labels.getAlguienFuma(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienFuma = new MultipleFixedChoicePage(this, labels.getQuienFuma(), "", Constants.WIZARD, true).setChoices(catQuienFuma).setRequired(true);
        Page cantCigarrosMadre = new NumberPage(this, labels.getCantCigarrosMadre(), "", Constants.WIZARD, true).setRequired(true);
        Page cantCigarrosPadre = new NumberPage(this, labels.getCantCigarrosPadre(), "", Constants.WIZARD, true).setRequired(true);
        Page cantCigarrosOtros = new NumberPage(this, labels.getCantCigarrosOtros(), "", Constants.WIZARD, true).setRequired(true);
        */
        Page tuberculosisPulmonarActual = new SingleFixedChoicePage(this, labels.getTuberculosisPulmonarActual(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioDiagTubpulActual = new NumberPage(this, labels.getAnioDiagTubpulActual(), "", Constants.WIZARD, false).setRequired(true);
        Page mesDiagTubpulActual = new SingleFixedChoicePage(this, labels.getMesDiagTubpulActual(), "", Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page tratamientoTubpulActual = new SingleFixedChoicePage(this, labels.getTratamientoTubpulActual(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page completoTratamientoTubpulActual = new SingleFixedChoicePage(this, labels.getCompletoTratamientoTubpulActual(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);

        Page tuberculosisPulmonarPasado = new SingleFixedChoicePage(this, labels.getTuberculosisPulmonarPasado(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page fechaDiagTubpulPasadoDes = new SingleFixedChoicePage(this, labels.getFechaDiagTubpulPasadoDes(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page anioDiagTubpulPasado = new NumberPage(this, labels.getAnioDiagTubpulPasado(), "", Constants.WIZARD, false).setRequired(true);
        Page mesDiagTubpulPasado = new SingleFixedChoicePage(this, labels.getMesDiagTubpulPasado(), "", Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page tratamientoTubpulPasado = new SingleFixedChoicePage(this, labels.getTratamientoTubpulPasado(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page completoTratamientoTubpulPas = new SingleFixedChoicePage(this, labels.getCompletoTratamientoTubpulPas(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);

        Page alergiaRespiratoria = new SingleFixedChoicePage(this, labels.getAlergiaRespiratoria(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioAlergia = new NumberPage(this, labels.getAnioAlergiaRespiratoria(), "", Constants.WIZARD, false).setRequired(true);
        Page asma = new SingleFixedChoicePage(this, labels.getAsma(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioAsma = new NumberPage(this, labels.getAnioAsma(), "", Constants.WIZARD, false).setRequired(true);
        Page enfermedadCronica = new SingleFixedChoicePage(this, labels.getEnfermedadCronica(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page cardiopatia = new SingleFixedChoicePage(this, labels.getCardiopatia(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page anioCardio = new NumberPage(this, labels.getAnioCardiopatia(), "", Constants.WIZARD, false).setRequired(true);
        Page enfermPulmonarObstCronica = new SingleFixedChoicePage(this, labels.getEnfermPulmonarObstCronica(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page anioEPOC = new NumberPage(this, labels.getAnioEnfermPulmonarObstCronica(), "", Constants.WIZARD, false).setRequired(true);
        Page diabetes = new SingleFixedChoicePage(this, labels.getDiabetes(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page anioDiabetes = new NumberPage(this, labels.getAnioDiabetes(), "", Constants.WIZARD, false).setRequired(true);
        Page presionAlta = new SingleFixedChoicePage(this, labels.getPresionAlta(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page anioPA = new NumberPage(this, labels.getAnioPresionAlta(), "", Constants.WIZARD, false).setRequired(true);
        Page otrasEnfermedades = new SingleFixedChoicePage(this, labels.getOtrasEnfermedades(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page descOtrasEnfermedades = new TextPage(this, labels.getDescOtrasEnfermedades(), "", Constants.WIZARD, false).setRequired(true);

        Page tenidoDengue = new SingleFixedChoicePage(this, labels.getTenidoDengue(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioDengue = new NumberPage(this, labels.getAnioDengue(), "", Constants.WIZARD, false).setRequired(true);
        Page diagMedicoDengue = new SingleFixedChoicePage(this, labels.getDiagMedicoDengue(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page dondeDengue = new SingleFixedChoicePage(this, labels.getDondeDengue(), "", Constants.WIZARD, false).setChoices(catLugarDx).setRequired(true);

        Page tenidoZika = new SingleFixedChoicePage(this, labels.getTenidoZika(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioZika = new NumberPage(this, labels.getAnioZika(), "", Constants.WIZARD, false).setRequired(true);
        Page diagMedicoZika = new SingleFixedChoicePage(this, labels.getDiagMedicoZika(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page dondeZika = new SingleFixedChoicePage(this, labels.getDondeZika(), "", Constants.WIZARD, false).setChoices(catLugarDx).setRequired(true);

        Page tenidoChik = new SingleFixedChoicePage(this, labels.getTenidoChik(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioChik = new NumberPage(this, labels.getAnioChik(), "", Constants.WIZARD, false).setRequired(true);
        Page diagMedicoChik = new SingleFixedChoicePage(this, labels.getDiagMedicoChik(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page dondeChik = new SingleFixedChoicePage(this, labels.getDondeChik(), "", Constants.WIZARD, false).setChoices(catLugarDx).setRequired(true);

        Page vacunaFiebreAma = new SingleFixedChoicePage(this, labels.getVacunaFiebreAma(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page anioVacunaFiebreAma = new NumberPage(this, labels.getAnioVacunaFiebreAma(), "", Constants.WIZARD, false).setRequired(true);

        Page vacunaCovid = new SingleFixedChoicePage(this, labels.getVacunaCovid(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page anioVacunaCovid = new NumberPage(this, labels.getAnioVacunaCovid(), "", Constants.WIZARD, false).setRequired(true);
        Page mesVacunaCovid = new SingleFixedChoicePage(this, labels.getMesVacunaCovid(), "", Constants.WIZARD, false).setChoices(catMeses).setRequired(true);
        Page tomarFotoTarjetaCovid = new LabelPage(this, labels.getTomarFotoTarjetaCovid(), "", Constants.WIZARD, false).setRequired(true);

        Page tieneTarjetaVacuna = new SingleFixedChoicePage(this, labels.getTieneTarjetaVacuna(), "", Constants.WIZARD, false).setChoices(catSiNoDes).setRequired(true);
        Page tomarFotoTarjeta = new LabelPage(this, labels.getTomarFotoTarjeta(), "", Constants.WIZARD, false).setRequired(true);

        Page fiebre = new SingleFixedChoicePage(this, labels.getFiebre(), "", Constants.WIZARD, true).setChoices(catSiNoDes).setRequired(true);
        Page tiempoFiebre = new SingleFixedChoicePage(this, labels.getTiempoFiebre(), "", Constants.WIZARD, true).setChoices(catTmpFiebre).setRequired(true);
        Page lugarConsFiebre = new SingleFixedChoicePage(this, labels.getLugarConsFiebre(), "", Constants.WIZARD, true).setChoices(catLugarCons).setRequired(true);
        Page noAcudioCs = new SingleFixedChoicePage(this, labels.getNoAcudioCs(), "", Constants.WIZARD, true).setChoices(catNoCS).setRequired(true);
        Page automedicoFiebre = new MultipleFixedChoicePage(this, labels.getAutomedicoFiebre(), "", Constants.WIZARD, true).setChoices(catAutomed).setRequired(true);
        Page tenidoDengueUA = new SingleFixedChoicePage(this, labels.getTenidoDengueUA(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page unidadSaludDengue = new SingleFixedChoicePage(this, labels.getUnidadSaludDengue(), "", Constants.WIZARD, true).setChoices(catDondeAsiste).setRequired(true);
        Page centroSaludDengue = new SingleFixedChoicePage(this, labels.getCentroSaludDengue(), "", Constants.WIZARD, true).setChoices(catCS).setRequired(true);
        Page otroCentroSaludDengue = new TextPage(this, labels.getOtroCentroSaludDengue(), "", Constants.WIZARD, true).setRequired(true);
        Page puestoSaludDengue = new SingleFixedChoicePage(this, labels.getPuestoSaludDengue(), "", Constants.WIZARD, true).setChoices(catPuesto).setRequired(true);
        Page otroPuestoSaludDengue = new TextPage(this, labels.getOtroPuestoSaludDengue(), "", Constants.WIZARD, true).setRequired(true);
        Page hospitalDengue = new SingleFixedChoicePage(this, labels.getHospitalDengue(), "", Constants.WIZARD, true).setChoices(catHospital).setRequired(true);
        Page otroHospitalDengue = new TextPage(this, labels.getOtroHospitalDengue(), "", Constants.WIZARD, true).setRequired(true);
        Page hospitalizadoDengue = new SingleFixedChoicePage(this, labels.getHospitalizadoDengue(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page ambulatorioDengue = new SingleFixedChoicePage(this, labels.getAmbulatorioDengue(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page diagnosticoDengue = new TextPage(this, labels.getDiagnosticoDengue(), "", Constants.WIZARD, true).setRequired(true);
        Page rashUA = new SingleFixedChoicePage(this, labels.getRashUA(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page recuerdaMes = new SingleFixedChoicePage(this, labels.getRecuerdaMesRash(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashMes = new SingleFixedChoicePage(this, labels.getRashMes(), "", Constants.WIZARD, true).setChoices(catMeses).setRequired(true);
        Page rashCara = new SingleFixedChoicePage(this, labels.getRashCara(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashMiembrosSup = new SingleFixedChoicePage(this, labels.getRashMiembrosSup(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashTorax = new SingleFixedChoicePage(this, labels.getRashTorax(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashAbdomen = new SingleFixedChoicePage(this, labels.getRashAbdomen(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashMiembrosInf = new SingleFixedChoicePage(this, labels.getRashMiembrosInf(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page rashDias = new SingleFixedChoicePage(this, labels.getRashDias(), "", Constants.WIZARD, true).setChoices(catEnroje).setRequired(true);
        Page consultaRashUA = new SingleFixedChoicePage(this, labels.getConsultaRashUA(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page unidadSaludRash = new SingleFixedChoicePage(this, labels.getUnidadSaludRash(), "", Constants.WIZARD, true).setChoices(catDondeAsiste).setRequired(true);
        Page centroSaludRash = new SingleFixedChoicePage(this, labels.getCentroSaludRash(), "", Constants.WIZARD, true).setChoices(catCS).setRequired(true);
        Page otroCentroSaludRash = new TextPage(this, labels.getOtroCentroSaludRash(), "", Constants.WIZARD, true).setRequired(true);
        Page puestoSaludRash = new SingleFixedChoicePage(this, labels.getPuestoSaludRash(), "", Constants.WIZARD, true).setChoices(catPuesto).setRequired(true);
        Page otroPuestoSaludRash = new TextPage(this, labels.getOtroPuestoSaludRash(), "", Constants.WIZARD, true).setRequired(true);
        Page diagnosticoRash = new TextPage(this, labels.getDiagnosticoRash(), "", Constants.WIZARD, true).setRequired(true);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

        return new PageList(emancipado, descEmancipado, otraDescEmanc, embarazada, conoceFur, fur, asisteEscuela, gradoEstudia, nombreEscuela, turno, cuidan, cuantosCuidan, nombreCDI, direccionCDI, otroLugarCuidan,
                alfabeto, nivelEducacion, trabaja, tipoTrabajo, ocupacionActual, personaVive, ordenNac, padreAlfabeto, papaNivel, trabajaPadre, papaTipoTra, madreAlfabeta, mamaNivel, trabajaMadre, mamaTipoTra,
                comparteHab, hab1, hab2, hab3, hab4, hab5, comparteCama, cama1, cama2, cama3, cama4, cama5, fuma, periodicidadFuma, cantidadCigarrillos, fumaDentroCasa, tuberculosisPulmonarActual, anioDiagTubpulActual, mesDiagTubpulActual,
                tratamientoTubpulActual, completoTratamientoTubpulActual, tuberculosisPulmonarPasado, fechaDiagTubpulPasadoDes, anioDiagTubpulPasado, mesDiagTubpulPasado, tratamientoTubpulPasado, completoTratamientoTubpulPas,
                alergiaRespiratoria, anioAlergia, asma, anioAsma, enfermedadCronica, cardiopatia, anioCardio, enfermPulmonarObstCronica, anioEPOC, diabetes, anioDiabetes, presionAlta, anioPA, otrasEnfermedades, descOtrasEnfermedades,
                tenidoDengue, anioDengue, diagMedicoDengue, dondeDengue, tenidoZika, anioZika, diagMedicoZika, dondeZika, tenidoChik, anioChik, diagMedicoChik, dondeChik,
                vacunaFiebreAma, anioVacunaFiebreAma, vacunaCovid, anioVacunaCovid, mesVacunaCovid, tomarFotoTarjetaCovid, tieneTarjetaVacuna, tomarFotoTarjeta,
                fiebre, tiempoFiebre, lugarConsFiebre, noAcudioCs, automedicoFiebre, tenidoDengueUA, unidadSaludDengue, centroSaludDengue, otroCentroSaludDengue, puestoSaludDengue, otroPuestoSaludDengue, hospitalDengue, otroHospitalDengue, hospitalizadoDengue, ambulatorioDengue, diagnosticoDengue,
                rashUA, recuerdaMes, rashMes, rashCara, rashMiembrosSup, rashTorax, rashAbdomen, rashMiembrosInf, rashDias, consultaRashUA, unidadSaludRash, centroSaludRash, otroCentroSaludRash, puestoSaludRash, otroPuestoSaludRash, diagnosticoRash,
                finTamizajeLabel);
    }
}
