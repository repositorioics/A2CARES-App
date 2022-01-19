package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.AbstractWizardModel;
import ni.org.ics.a2cares.app.wizard.model.*;
import ni.org.ics.a2cares.app.wizard.model.PageList;

/**
 * Created by Miguel Salinas on 9/6/2021.
 * V1.0
 */
public class EncuestaCasaForm extends AbstractWizardModel {
    int index = 0;
    private String[] catSiNo;
    private String[] catDentroFuera;
    private String[] catCompartido;
    private String[] catMaterialParedes;
    private String[] catMaterialPiso;
    private String[] catMaterialTecho;
    private String[] catDiaNoche;
    private String[] catServicios;
    private String[] catTipoCocina;
    private String[] catPeriodoCocina;
    private String[] catMuroCerco;
    private String[] catInodoroLetrina;
    private String[] catFrecVaAgua;
    private EstudioDBAdapter estudiosAdapter;
    private EncuestaCasaFormLabels labels;

    public EncuestaCasaForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new EncuestaCasaFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);
        estudiosAdapter.open();
        catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        catDentroFuera = estudiosAdapter.getMessageResourcesForCatalog("CAT_DF");
        catCompartido = estudiosAdapter.getMessageResourcesForCatalog("CAT_COMPARTIDO");
        catMaterialParedes = estudiosAdapter.getMessageResourcesForCatalog("CAT_MAT_PARED");
        catMaterialPiso = estudiosAdapter.getMessageResourcesForCatalog("CAT_MAT_PISO");
        catMaterialTecho = estudiosAdapter.getMessageResourcesForCatalog("CAT_MAT_TECHO");
        catDiaNoche = estudiosAdapter.getMessageResourcesForCatalog("CAT_FUN_AIRE");
        catServicios = estudiosAdapter.getMessageResourcesForCatalog("CAT_OTROS_SERVICIOS");
        catTipoCocina = estudiosAdapter.getMessageResourcesForCatalog("CAT_TIPO_COCINA");
        catPeriodoCocina = estudiosAdapter.getMessageResourcesForCatalog("CAT_FREC_COCINA");
        catMuroCerco = estudiosAdapter.getMessageResourcesForCatalog("CAT_MUROCERCO");
        catInodoroLetrina = estudiosAdapter.getMessageResourcesForCatalog("CAT_INODOROLET");
        catFrecVaAgua = estudiosAdapter.getMessageResourcesForCatalog("CAT_FREC_VA_AGUA");
        estudiosAdapter.close();

        Calendar calendar = Calendar.getInstance();

        Page npPersonas = new NumberPage(this, labels.getCuantasPersonas(), "", Constants.WIZARD, true).setRequired(true);
        Page npMujeres = new NumberPage(this, labels.getCuantasMujeres(), labels.getCuantasMujeresHint(), Constants.WIZARD, true).setRequired(true);
        Page tpEdadM1 = new TextPage(this, labels.getEdadMujer1(), labels.getEdadMujer1Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM2 = new TextPage(this, labels.getEdadMujer2(), labels.getEdadMujer2Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM3 = new TextPage(this, labels.getEdadMujer3(), labels.getEdadMujer3Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM4 = new TextPage(this, labels.getEdadMujer4(), labels.getEdadMujer4Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM5 = new TextPage(this, labels.getEdadMujer5(), labels.getEdadMujer5Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM6 = new TextPage(this, labels.getEdadMujer6(), labels.getEdadMujer6Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM7 = new TextPage(this, labels.getEdadMujer7(), labels.getEdadMujer7Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM8 = new TextPage(this, labels.getEdadMujer8(), labels.getEdadMujer8Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM9 = new TextPage(this, labels.getEdadMujer9(), labels.getEdadMujer9Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadM10 = new TextPage(this, labels.getEdadMujer10(), labels.getEdadMujer10Hint(), Constants.WIZARD, false).setRequired(true);
        Page npHombres = new NumberPage(this, labels.getCuantosHombres(), labels.getCuantosHombresHint(), Constants.WIZARD, true).setRequired(true);
        Page tpEdadH1 = new TextPage(this, labels.getEdadHombre1(), labels.getEdadHombre1Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH2 = new TextPage(this, labels.getEdadHombre2(), labels.getEdadHombre2Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH3 = new TextPage(this, labels.getEdadHombre3(), labels.getEdadHombre3Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH4 = new TextPage(this, labels.getEdadHombre4(), labels.getEdadHombre4Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH5 = new TextPage(this, labels.getEdadHombre5(), labels.getEdadHombre5Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH6 = new TextPage(this, labels.getEdadHombre6(), labels.getEdadHombre6Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH7 = new TextPage(this, labels.getEdadHombre7(), labels.getEdadHombre7Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH8 = new TextPage(this, labels.getEdadHombre8(), labels.getEdadHombre8Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH9 = new TextPage(this, labels.getEdadHombre9(), labels.getEdadHombre9Hint(), Constants.WIZARD, false).setRequired(true);
        Page tpEdadH10 = new TextPage(this, labels.getEdadHombre10(), labels.getEdadHombre10Hint(), Constants.WIZARD, false).setRequired(true);

        Page npCuartos = new NumberPage(this, labels.getCuantoCuartos(), "", Constants.WIZARD, true).setRequired(true);
        Page npCuartosDormir = new NumberPage(this, labels.getCuartosDormir(), "", Constants.WIZARD, true).setRequired(true);
        Page scProblemaAgua = new SingleFixedChoicePage(this, labels.getProblemaAgua(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npHorasSinAgua = new NumberPage(this, labels.getHorasSinAgua(), labels.getHorasSinAguaHint(), Constants.WIZARD, false).setRangeValidation(true, 0, 24).setRequired(true);
        Page scFrecSinAgua = new SingleFixedChoicePage(this, labels.getFrecuenciaSeVaAgua(), labels.getFrecuenciaSeVaAguaHint(), Constants.WIZARD, false).setChoices(catFrecVaAgua).setRequired(true);
        Page tpOtraFrecSinAgua = new TextPage(this, labels.getOtraFrecuenciaSeVaAgua(), labels.getOtraFrecuenciaSeVaAguaHint(), Constants.WIZARD, false).setRequired(true);
        Page scPozo = new SingleFixedChoicePage(this, labels.getTienePozo(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMedidorAgua = new SingleFixedChoicePage(this, labels.getTieneMedidorAgua(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTanque = new SingleFixedChoicePage(this, labels.getTieneTanque(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scLlaveAgua = new SingleFixedChoicePage(this,labels.getLlaveAgua(), "",Constants.WIZARD, true).setChoices(catDentroFuera).setRequired(true);
        Page scLlaveCompartida = new SingleFixedChoicePage(this,labels.getLlaveCompartida(),labels.getLlaveCompartidaHint(),Constants.WIZARD, false).setChoices(catCompartido).setRequired(true);
        Page scAlmacenaAgua = new SingleFixedChoicePage(this, labels.getAlmacenaAgua(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scEnBarriles = new SingleFixedChoicePage(this, labels.getAlmacenaEnBarriles(), labels.getAlmacenaEnBarrilesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumBarriles = new NumberPage(this, labels.getNumBarriles(), labels.getNumBarrilesHint(), Constants.WIZARD, false).setRequired(true);
        Page scBarrilesTapados = new SingleFixedChoicePage(this,labels.getBarrilesTapados(),labels.getBarrilesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scEnTanques = new SingleFixedChoicePage(this, labels.getAlmacenaEnTanques(), labels.getAlmacenaEnTanquesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumTanques = new NumberPage(this, labels.getNumTanques(), labels.getNumTanquesHint(), Constants.WIZARD, false).setRequired(true);
        Page scTanquesTapados = new SingleFixedChoicePage(this,labels.getTanquesTapados(),labels.getTanquesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scEnPilas = new SingleFixedChoicePage(this, labels.getAlmacenaEnPilas(), labels.getAlmacenaEnPilasHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npNumPilas = new NumberPage(this, labels.getNumPilas(), labels.getNumPilasHint(), Constants.WIZARD, false).setRequired(true);
        Page scPilasTapadas = new SingleFixedChoicePage(this,labels.getPilasTapadas(),labels.getPilasTapadasHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scCepillaPilas = new SingleFixedChoicePage(this,labels.getCepillaPilas(), labels.getCepillaPilasHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpFrecCepilla = new TextPage(this, labels.getFrecCepillaPilas(), labels.getFrecCepillaPilasHint(), Constants.WIZARD, false).setRequired(true);
        Page scEnOtrosRec = new SingleFixedChoicePage(this, labels.getAlmacenaOtrosRecipientes(), labels.getAlmacenaOtrosRecipientesHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpDescOtrosRec = new TextPage(this, labels.getOtrosRecipientes(), labels.getOtrosRecipientesHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumOtrosRec = new NumberPage(this, labels.getNumOtrosRecipientes(), labels.getNumOtrosRecipientesHint(), Constants.WIZARD, false).setRequired(true);
        Page scOtrosRecTapados = new SingleFixedChoicePage(this,labels.getOtrosRecipientesTapados(),labels.getOtrosRecipientesTapadosHint(),Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scCambiadoCasa = new SingleFixedChoicePage(this,labels.getCambiadoCasa(),"",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);//en la primera encuesta no se va a mostrar esta pregunta
        Page scRemoCasa = new SingleFixedChoicePage(this,labels.getRemodeladoCasa(),"",Constants.WIZARD, false).setChoices(catSiNo).setRequired(true); //en la primera encuesta no se va a mostrar esta pregunta
        Page scLavandero = new SingleFixedChoicePage(this,labels.getUbicacionLavandero(),"",Constants.WIZARD, true).setChoices(catDentroFuera).setRequired(true);
        Page mcParedes = new MultipleFixedChoicePage(this,labels.getMaterialParedes(),"",Constants.WIZARD, true).setChoices(catMaterialParedes).setRequired(true);
        Page tpParedesOtroDesc = new TextPage(this, labels.getOtroMaterialParedes(), "", Constants.WIZARD, false).setRequired(true);
        Page mcPiso = new MultipleFixedChoicePage(this,labels.getMaterialPiso(),"",Constants.WIZARD, true).setChoices(catMaterialPiso).setRequired(true);
        Page tpPisoOtroDesc = new TextPage(this, labels.getOtroMaterialPiso(), "", Constants.WIZARD, false).setRequired(true);
        Page scTecho = new SingleFixedChoicePage(this,labels.getMaterialTecho(),"",Constants.WIZARD, true).setChoices(catMaterialTecho).setRequired(true);
        Page tpTechoOtroDesc = new TextPage(this, labels.getOtroMaterialTecho(), "", Constants.WIZARD, false).setRequired(true);
        Page scCasaPropia = new SingleFixedChoicePage(this,labels.getCasaPropia(),"",Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scAbanico = new SingleFixedChoicePage(this, labels.getTieneAbanico(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npAbanico = new NumberPage(this, labels.getNumAbanicos(), "", Constants.WIZARD, false).setRangeValidation(true,1,9).setRequired(true);
        Page scTelevisor = new SingleFixedChoicePage(this, labels.getTieneTelevisor(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npNumTelevisor = new NumberPage(this, labels.getNumTelevisores(), "", Constants.WIZARD, false).setRangeValidation(true,1,9).setRequired(true);
        Page scRefrigerador = new SingleFixedChoicePage(this, labels.getTieneRefrigerador(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npNumRefrigerador = new NumberPage(this, labels.getNumRefrigeradores(), "", Constants.WIZARD, false).setRangeValidation(true,1,9).setRequired(true);
        Page scAireAcondicionado = new SingleFixedChoicePage(this, labels.getTieneAireAcondicionado(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npNumAire = new NumberPage(this, labels.getNumAireAcondicionado(), "", Constants.WIZARD, false).setRangeValidation(true,1,9).setRequired(true);
        Page scAireAcondicionadoFun = new SingleFixedChoicePage(this, labels.getAireAcondicionadoFuncionando(), labels.getAireAcondicionadoFuncionandoHint(), Constants.WIZARD, false).setChoices(catDiaNoche).setRequired(true);
        Page scLavadora = new SingleFixedChoicePage(this, labels.getLavadoraFuncionando(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);

        Page scTieneMuro = new SingleFixedChoicePage(this, labels.getTieneMuro(), "", Constants.WIZARD, true).setChoices(catMuroCerco).setRequired(true);
        Page scTieneInodoro = new SingleFixedChoicePage(this, labels.getTieneInodoro(), "", Constants.WIZARD, true).setChoices(catInodoroLetrina).setRequired(true);
        Page npACantInodoro = new NumberPage(this, labels.getCantInodoro(), labels.getCantInodoroHint(), Constants.WIZARD, false).setRangeValidation(true,1, 9).setRequired(true);
        Page npCantLetrina = new NumberPage(this, labels.getCantLetrina(), labels.getCantLetrinaHint(), Constants.WIZARD, false).setRangeValidation(true,1, 9).setRequired(true);
        Page scTieneServicioEnergia = new SingleFixedChoicePage(this, labels.getTieneServicioEnergia(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTieneMedidorEnergia = new SingleFixedChoicePage(this, labels.getTieneMedidorEnergia(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scCasaDosPisos = new SingleFixedChoicePage(this, labels.getCasaDosPisos(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTieneInternet = new SingleFixedChoicePage(this, labels.getTieneInternet(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scTieneOtrosServicios = new MultipleFixedChoicePage(this, labels.getTieneOtrosServicios(), "", Constants.WIZARD, true).setChoices(catServicios).setRequired(false);

        Page scTieneVehiculo = new SingleFixedChoicePage(this, labels.getTieneVehiculo(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scMoto = new SingleFixedChoicePage(this, labels.getTieneMoto(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioMoto = new NumberPage(this, labels.getAnioFabMoto(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaMoto =  new TextPage(this, labels.getMarcaMoto(), "", Constants.WIZARD, false).setRequired(true);
        Page scMTCarro = new SingleFixedChoicePage(this, labels.getTieneCarro(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioFabCarro = new NumberPage(this, labels.getAnioFabCarro(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaCarro =  new TextPage(this, labels.getMarcaCarro(), "", Constants.WIZARD, false).setRequired(true);
        Page scMTMicrobus = new SingleFixedChoicePage(this, labels.getTieneMicrobus(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioFabMicrobus = new NumberPage(this, labels.getAnioFabMicrobus(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaMicrobus =  new TextPage(this, labels.getMarcaMicrobus(), "", Constants.WIZARD, false).setRequired(true);
        Page scMTCamioneta = new SingleFixedChoicePage(this, labels.getTieneCamioneta(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioFabCamioneta = new NumberPage(this, labels.getAnioFabCamioneta(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaCamioneta =  new TextPage(this, labels.getMarcaCamioneta(), "", Constants.WIZARD, false).setRequired(true);
        Page scMTCamion = new SingleFixedChoicePage(this, labels.getTieneCamion(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npAnioFabCamion = new NumberPage(this, labels.getAnioFabCamion(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaCamion =  new TextPage(this, labels.getMarcaCamion(), "", Constants.WIZARD, false).setRequired(true);
        Page scMTOtro = new SingleFixedChoicePage(this, labels.getTieneOtroMedioTransAuto(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page tpMTOtroDesc =  new TextPage(this, labels.getOtroMedioTransAuto(), "", Constants.WIZARD, false).setRequired(true);
        Page npAnioFabOtroMedioTrans = new NumberPage(this, labels.getAnioFabOtroMedioTrans(), "", Constants.WIZARD, false).setRangeValidation(true,1960, calendar.get(Calendar.YEAR)).setRequired(false);
        Page tpMarcaOtroMedioTrans =  new TextPage(this, labels.getMarcaOtroMedioTrans(), "", Constants.WIZARD, false).setRequired(true);

        Page scTipoCocina = new SingleFixedChoicePage(this, labels.getTipoCocina(), "", Constants.WIZARD, true).setChoices(catTipoCocina).setRequired(true);
        Page npQuemadores = new NumberPage(this, labels.getCuantosQuemadores(), "", Constants.WIZARD, true).setRangeValidation(true,1,10).setRequired(true);
        Page scHorno = new SingleFixedChoicePage(this, labels.getTieneHorno(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scCocina = new SingleFixedChoicePage(this, labels.getCocinaConLenia(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scCocinaUbicacion = new SingleFixedChoicePage(this, labels.getUbicacionCocinaLenia(), labels.getUbicacionCocinaLeniaHint(), Constants.WIZARD, false).setChoices(catDentroFuera[0],catDentroFuera[1]).setRequired(true);
        Page scCocinaPeriodicidad = new SingleFixedChoicePage(this, labels.getPeriodicidadCocinaLenia(), "", Constants.WIZARD, false).setChoices(catPeriodoCocina).setRequired(true);
        Page npNumCocinaD = new NumberPage(this, labels.getNumDiarioCocinaLenia(), labels.getNumDiarioCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaS = new NumberPage(this, labels.getNumSemanalCocinaLenia(), labels.getNumSemanalCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaQ = new NumberPage(this, labels.getNumQuincenalCocinaLenia(), labels.getNumQuincenalCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);
        Page npNumCocinaM = new NumberPage(this, labels.getNumMensualCocinaLenia(), labels.getNumMensualCocinaLeniaHint(), Constants.WIZARD, false).setRequired(true);

        Page scAnimales = new SingleFixedChoicePage(this, labels.getTieneAnimales(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scAnimalesGallinas = new SingleFixedChoicePage(this, labels.getTieneGallinas(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantGallinas = new NumberPage(this, labels.getCantidadGallinas(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scGallinasDC =  new SingleFixedChoicePage(this, labels.getGallinasDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesPatos = new SingleFixedChoicePage(this, labels.getTienePatos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantPatos = new NumberPage(this, labels.getCantidadPatos(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scPatosDC = new SingleFixedChoicePage(this, labels.getPatosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesPerros = new SingleFixedChoicePage(this, labels.getTienePerros(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantPerros = new NumberPage(this, labels.getCantidadPerros(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scPerrosDC = new SingleFixedChoicePage(this, labels.getPerrosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesGatos = new SingleFixedChoicePage(this, labels.getTieneGatos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantGatos = new NumberPage(this, labels.getCantidadGatos(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scGatosDC = new SingleFixedChoicePage(this, labels.getGatosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesCerdos = new SingleFixedChoicePage(this, labels.getTieneCerdos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCerdos = new NumberPage(this, labels.getCantidadCerdos(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scCerdosDC = new SingleFixedChoicePage(this, labels.getCerdosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesVacas = new SingleFixedChoicePage(this, labels.getTieneVacas(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantVacas = new NumberPage(this, labels.getCantidadVacas(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scVacasDC = new SingleFixedChoicePage(this, labels.getVacasDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesCabras = new SingleFixedChoicePage(this, labels.getTieneCabras(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCabras = new NumberPage(this, labels.getCantidadCabras(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scCabrasDC = new SingleFixedChoicePage(this, labels.getCabrasDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesCaballos = new SingleFixedChoicePage(this, labels.getTieneCaballos(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantCaballos = new NumberPage(this, labels.getCantidadCaballos(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scCaballosDC = new SingleFixedChoicePage(this, labels.getCaballosDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesPelibueys = new SingleFixedChoicePage(this, labels.getTienePelibueys(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantPelibueys = new NumberPage(this, labels.getCantidadPelibueys(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scPelibueysDC = new SingleFixedChoicePage(this, labels.getPelibueysDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scAnimalesAves = new SingleFixedChoicePage(this, labels.getTieneAves(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantAves = new NumberPage(this, labels.getCantidadAves(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scAvesDC = new SingleFixedChoicePage(this, labels.getAvesDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page scOtrosAnimales = new SingleFixedChoicePage(this, labels.getTieneOtrosAnimales(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page npCantOtrosAnimales = new NumberPage(this, labels.getCantidadOtrosAnimales(), "", Constants.WIZARD, false).setRangeValidation(true,1,20).setRequired(true);
        Page scOtrosAnimalesDC = new SingleFixedChoicePage(this, labels.getOtrosAnimalesDentroCasa(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page scFuman = new SingleFixedChoicePage(this, labels.getPersonaFumaDentroCasa(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page scBasura = new SingleFixedChoicePage(this, labels.getTieneRecoleccionBasura(), "", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page npVecesBasura = new NumberPage(this, labels.getCuantasVecesRecBasura(), "", Constants.WIZARD, false).setRangeValidation(true,1,7).setRequired(true);
        Page tpDondeBasura =  new TextPage(this, labels.getDondePasaRecBasura(), "", Constants.WIZARD, false).setRequired(true);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

        return new PageList(
                npPersonas, npMujeres, tpEdadM1, tpEdadM2, tpEdadM3, tpEdadM4, tpEdadM5, tpEdadM6, tpEdadM7, tpEdadM8, tpEdadM9, tpEdadM10,
                npHombres, tpEdadH1, tpEdadH2, tpEdadH3, tpEdadH4, tpEdadH5, tpEdadH6, tpEdadH7, tpEdadH8, tpEdadH9, tpEdadH10,
                npCuartos, npCuartosDormir, scProblemaAgua, npHorasSinAgua, scFrecSinAgua, tpOtraFrecSinAgua, scPozo, scMedidorAgua, scTanque, scLlaveAgua, scLlaveCompartida, scAlmacenaAgua, scEnBarriles, npNumBarriles, scBarrilesTapados,
                scEnTanques, npNumTanques, scTanquesTapados, scEnPilas, npNumPilas, scPilasTapadas, scCepillaPilas, tpFrecCepilla, scEnOtrosRec, tpDescOtrosRec, npNumOtrosRec, scOtrosRecTapados,
                scCambiadoCasa, scRemoCasa, scLavandero, mcParedes, tpParedesOtroDesc, mcPiso, tpPisoOtroDesc, scTecho, tpTechoOtroDesc, scCasaPropia, scAbanico, npAbanico,
                scTelevisor, npNumTelevisor, scRefrigerador, npNumRefrigerador, scAireAcondicionado, npNumAire, scAireAcondicionadoFun, scLavadora, scTieneMuro, scTieneInodoro, npACantInodoro, npCantLetrina, scTieneServicioEnergia, scTieneMedidorEnergia,
                scCasaDosPisos, scTieneInternet, scTieneOtrosServicios, scTieneVehiculo, scMoto, npAnioMoto, tpMarcaMoto, scMTCarro, npAnioFabCarro, tpMarcaCarro, scMTMicrobus, npAnioFabMicrobus, tpMarcaMicrobus,
                scMTCamioneta, npAnioFabCamioneta, tpMarcaCamioneta, scMTCamion, npAnioFabCamion, tpMarcaCamion, scMTOtro, tpMTOtroDesc, npAnioFabOtroMedioTrans, tpMarcaOtroMedioTrans,
                scTipoCocina, npQuemadores, scHorno, scCocina, scCocinaUbicacion, scCocinaPeriodicidad, npNumCocinaD, npNumCocinaS, npNumCocinaQ, npNumCocinaM,
                scAnimales, scAnimalesGallinas, npCantGallinas, scGallinasDC, scAnimalesPatos, npCantPatos, scPatosDC, scAnimalesPerros, npCantPerros, scPerrosDC, scAnimalesGatos, npCantGatos, scGatosDC,
                scAnimalesCerdos, npCantCerdos, scCerdosDC, scAnimalesVacas, npCantVacas, scVacasDC, scAnimalesCabras, npCantCabras, scCabrasDC, scAnimalesCaballos, npCantCaballos, scCaballosDC,
                scAnimalesPelibueys, npCantPelibueys, scPelibueysDC, scAnimalesAves, npCantAves, scAvesDC, scOtrosAnimales, npCantOtrosAnimales, scOtrosAnimalesDC,
                scFuman, scBasura, npVecesBasura, tpDondeBasura, finTamizajeLabel
                        );
    }
}
