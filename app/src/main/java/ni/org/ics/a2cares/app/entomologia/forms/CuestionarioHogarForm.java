package ni.org.ics.a2cares.app.entomologia.forms;

import android.content.Context;

import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class CuestionarioHogarForm extends AbstractWizardModel {

    private CuestionarioHogarFormLabels labels;
    private EstudioDBAdapter estudiosAdapter;
    int index = 0;
    private String[] catBarrios;
    private String[] catSiNo;
    private String[] catP05;
    private String[] catP06;
    private String[] catP10y15;
    private String[] catP16;
    private String[] catP18;
    private String[] catP19;
    private String[] catP27;
    private String[] catP30;
    private String[] catP32;
    private String[] catP34;
    private String[] catP36y37;
    private String[] catP38y43;
    private String[] catP40;
    private String[] catP42;
    private String[] catP45;
    private String[] catMaterialParedes;
    private String[] catMaterialPiso;
    private String[] catMaterialTecho;

    private String[] fillCatalog(String codigoCatalogo){
        String[] catalogo;
        List<MessageResource> mCatalogo = estudiosAdapter.getMessageResources(MainDBConstants.catRoot + "='"+codigoCatalogo+"'", MainDBConstants.order);
        catalogo = new String[mCatalogo.size()];
        index = 0;
        for (MessageResource message: mCatalogo){
            catalogo[index] = message.getSpanish();
            index++;
        }
        return catalogo;
    }

    private String[] fillBarrios(){
        String[] catalogo;
        List<Barrio> barrios = estudiosAdapter.getBarrios(null, MainDBConstants.nombre);
        catalogo = new String[barrios.size()];
        index = 0;
        for (Barrio message: barrios){
            catalogo[index] = message.getNombre();
            index++;
        }
        return catalogo;
    }

    public CuestionarioHogarForm(Context context, String pass){
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {
        labels = new CuestionarioHogarFormLabels();
        this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);

        estudiosAdapter.open();
        catSiNo = fillCatalog("CAT_SINO");
        //
        catP05 = fillCatalog("ENTO_CAT_P05");
        catP06 = fillCatalog("ENTO_CAT_P06");
        catP10y15 = fillCatalog("ENTO_CAT_P10_P15");
        catP16 = fillCatalog("ENTO_CAT_P16");
        catP18 = fillCatalog("ENTO_CAT_P18");
        catP19 = fillCatalog("ENTO_CAT_P19");
        catP27 = fillCatalog("ENTO_CAT_P27");
        catP30 = fillCatalog("ENTO_CAT_P30");
        catP32 = fillCatalog("ENTO_CAT_P32");
        catP34 = fillCatalog("ENTO_CAT_P34");
        catP36y37 = fillCatalog("ENTO_CAT_P36");
        catP38y43 = fillCatalog("ENTO_CAT_P38");
        catP40 = fillCatalog("ENTO_CAT_P40");
        catP42 = fillCatalog("ENTO_CAT_P42");
        catP45 = fillCatalog("ENTO_CAT_P45");
        catMaterialParedes = fillCatalog("CAT_MAT_PARED");
        catMaterialPiso = fillCatalog("CAT_MAT_PISO");
        catMaterialTecho = fillCatalog("CAT_MAT_TECHO");
        catBarrios = fillBarrios();
        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/10/2022", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page fechaCuestionario = new NewDatePage(this,labels.getFechaCuestionario(),"", Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
        Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(),"", Constants.WIZARD, true).setChoices(catBarrios).setRequired(true);
        Page direccion = new TextPage(this,labels.getDireccion(),"", Constants.WIZARD, true).setRequired(true);
        Page puntoGps = new MapaBarriosPage(this,labels.getPuntoGps(),"", Constants.WIZARD, true).setRequired(true);

        Page tipoIngresoCodigo = new SingleFixedChoicePage(this,labels.getTipoIngresoCodigo(),"", Constants.WIZARD, true).setChoices(catP05).setRequired(true);
        Page codigoVivienda = new NumberPage(this,labels.getCodigoVivienda(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 2000).setRequired(true);
        Page codigoViviendaBr = new BarcodePage(this,labels.getCodigoViviendaBr(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 2000).setRequired(true);
        Page tipoVivienda = new SingleFixedChoicePage(this,labels.getTipoVivienda(),"", Constants.WIZARD, true).setChoices(catP06).setRequired(true);

        Page hayAmbientePERI = new SingleFixedChoicePage(this,labels.getHayAmbientePERI(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page horaCapturaPERI = new TimePage(this,labels.getHoraCapturaPERI(),"", Constants.WIZARD, true).setRequired(true);
        Page humedadRelativaPERI = new NumberPage(this,labels.getHumedadRelativaPERI(),"%", Constants.WIZARD, true).setRequired(true);
        Page temperaturaPERI = new NumberPage(this,labels.getTemperaturaPERI(),"C°", Constants.WIZARD, true).setRequired(true);
        Page tipoIngresoCodigoPERI = new SingleFixedChoicePage(this,labels.getTipoIngresoCodigoPERI(),"", Constants.WIZARD, true).setChoices(catP10y15).setRequired(true);
        Page codigoPERI = new TextPage(this,labels.getCodigoPERI(),"", Constants.WIZARD, true).setPatternValidation(true, "^PERI\\d+$").setRequired(true);
        Page codigoPERIBr = new BarcodePage(this,labels.getCodigoPERIBr(),"", Constants.WIZARD, true).setPatternValidation(true, "^PERI\\d+$").setRequired(true);

        Page hayAmbienteINTRA = new SingleFixedChoicePage(this,labels.getHayAmbienteINTRA(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page horaCapturaINTRA = new TimePage(this,labels.getHoraCapturaINTRA(),"", Constants.WIZARD, true).setRequired(true);
        Page humedadRelativaINTRA = new NumberPage(this,labels.getHumedadRelativaINTRA(),"%", Constants.WIZARD, true).setRequired(true);
        Page temperaturaINTRA = new NumberPage(this,labels.getTemperaturaINTRA(),"C°", Constants.WIZARD, true).setRequired(true);
        Page tipoIngresoCodigoINTRA = new SingleFixedChoicePage(this,labels.getTipoIngresoCodigoINTRA(),"", Constants.WIZARD, true).setChoices(catP10y15).setRequired(true);
        Page codigoINTRA = new TextPage(this,labels.getCodigoINTRA(),"", Constants.WIZARD, true).setPatternValidation(true, "^INTRA\\d+$").setRequired(true);
        Page codigoINTRABr = new BarcodePage(this,labels.getCodigoINTRABr(),"", Constants.WIZARD, true).setPatternValidation(true, "^INTRA\\d+$").setRequired(true);

        Page quienContesta = new SingleFixedChoicePage(this,labels.getQuienContesta(),"", Constants.WIZARD, true).setChoices(catP16).setRequired(true);
        Page quienContestaOtro = new TextPage(this,labels.getQuienContestaOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page edadContest = new TextPage(this,labels.getEdadContest(),"", Constants.WIZARD, true).setPatternValidation(true, "^[M|F]{1}\\d{2}$").setRequired(true);
        Page escolaridadContesta = new SingleFixedChoicePage(this,labels.getEscolaridadContesta(),"", Constants.WIZARD, true).setChoices(catP18).setRequired(true);
        Page tiempoVivirBarrio = new SingleFixedChoicePage(this,labels.getTiempoVivirBarrio(),"", Constants.WIZARD, true).setChoices(catP19).setRequired(true);
        Page cuantasPersonasViven = new NumberPage(this,labels.getCuantasPersonasViven(),"", Constants.WIZARD, true).setRangeValidation(true, 1, 25).setRequired(true);
        Page edadMujeres = new TextPage(this,labels.getEdadMujeres(),labels.getEdadHint(), Constants.WIZARD, true).setRequired(true);
        Page edadHombres = new TextPage(this,labels.getEdadHombres(),labels.getEdadHint(), Constants.WIZARD, true).setRequired(true);

        Page usaronMosquitero = new SingleFixedChoicePage(this,labels.getUsaronMosquitero(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienesUsaronMosquitero = new MultipleFixedChoicePage(this,labels.getQuienesUsaronMosquitero(),"", Constants.WIZARD, true).setRequired(true);
        Page usaronRepelente = new SingleFixedChoicePage(this,labels.getUsaronRepelente(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienesUsaronRepelente = new MultipleFixedChoicePage(this,labels.getQuienesUsaronRepelente(),"", Constants.WIZARD, true).setRequired(true);
        Page conoceLarvas = new SingleFixedChoicePage(this,labels.getConoceLarvas(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page alguienVisEliminarLarvas = new SingleFixedChoicePage(this,labels.getAlguienVisEliminarLarvas(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienVisEliminarLarvas = new SingleFixedChoicePage(this,labels.getQuienVisEliminarLarvas(),"", Constants.WIZARD, true).setChoices(catP27).setRequired(true);
        Page quienVisEliminarLarvasOtro = new TextPage(this,labels.getQuienVisEliminarLarvasOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page alguienDedicaElimLarvasCasa = new SingleFixedChoicePage(this,labels.getAlguienDedicaElimLarvasCasa(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienDedicaElimLarvasCasa = new MultipleFixedChoicePage(this,labels.getQuienDedicaElimLarvasCasa(),"", Constants.WIZARD, true).setRequired(true);
        Page tiempoElimanCridaros = new SingleFixedChoicePage(this,labels.getTiempoElimanCridaros(),"", Constants.WIZARD, true).setChoices(catP30).setRequired(true);
        Page hayBastanteZancudos = new SingleFixedChoicePage(this,labels.getHayBastanteZancudos(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queFaltaCasaEvitarZancudos = new MultipleFixedChoicePage(this,labels.getQueFaltaCasaEvitarZancudos(),"", Constants.WIZARD, true).setChoices(catP32).setRequired(true);
        Page queFaltaCasaEvitarZancudosOtros = new TextPage(this,labels.getQueFaltaCasaEvitarZancudosOtros(),"", Constants.WIZARD, true).setRequired(true);
        Page gastaronDineroProductos = new SingleFixedChoicePage(this,labels.getGastaronDineroProductos(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queProductosCompraron = new MultipleFixedChoicePage(this,labels.getQueProductosCompraron(),"", Constants.WIZARD, true).setChoices(catP34).setRequired(true);
        Page queProductosCompraronOtros = new TextPage(this,labels.getQueProductosCompraronOtros(),"", Constants.WIZARD, true).setRequired(true);
        Page cuantoGastaron = new NumberPage(this,labels.getCuantoGastaron(),"C$", Constants.WIZARD, true).setRequired(true);
        Page ultimaVisitaMinsaBTI = new SingleFixedChoicePage(this,labels.getUltimaVisitaMinsaBTI(),"", Constants.WIZARD, true).setChoices(catP36y37).setRequired(true);
        Page ultimaVezMinsaFumigo = new SingleFixedChoicePage(this,labels.getUltimaVezMinsaFumigo(),"", Constants.WIZARD, true).setChoices(catP36y37).setRequired(true);
        Page riesgoCasaDengue = new SingleFixedChoicePage(this,labels.getRiesgoCasaDengue(),"", Constants.WIZARD, true).setChoices(catP38y43).setRequired(true);
        Page problemasAbastecimiento = new SingleFixedChoicePage(this,labels.getProblemasAbastecimiento(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page cadaCuantoVaAgua = new SingleFixedChoicePage(this,labels.getCadaCuantoVaAgua(),"", Constants.WIZARD, true).setChoices(catP40).setRequired(true);
        Page cadaCuantoVaAguaOtro = new TextPage(this,labels.getCadaCuantoVaAguaOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page horasSinAguaDia = new NumberPage(this,labels.getHorasSinAguaDia(),"", Constants.WIZARD, true).setRangeValidation(true, 0, 24).setRequired(true);
        Page queHacenBasuraHogar = new SingleFixedChoicePage(this,labels.getQueHacenBasuraHogar(),"", Constants.WIZARD, true).setChoices(catP42).setRequired(true);
        Page queHacenBasuraHogarOtro = new TextPage(this,labels.getQueHacenBasuraHogarOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page riesgoBarrioDengue = new SingleFixedChoicePage(this,labels.getRiesgoBarrioDengue(),"", Constants.WIZARD, true).setChoices(catP38y43).setRequired(true);
        Page accionesCriaderosBarrio = new SingleFixedChoicePage(this,labels.getAccionesCriaderosBarrio(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page queAcciones = new MultipleFixedChoicePage(this,labels.getQueAcciones(),"", Constants.WIZARD, true).setChoices(catP45).setRequired(true);
        Page queAccionesOtro = new TextPage(this,labels.getQueAccionesOtro(),"", Constants.WIZARD, true).setRequired(true);
        Page alguienParticipo = new SingleFixedChoicePage(this,labels.getAlguienParticipo(),"", Constants.WIZARD, true).setChoices(catSiNo).setRequired(true);
        Page quienParticipo = new MultipleFixedChoicePage(this,labels.getQuienParticipo(),"", Constants.WIZARD, true).setRequired(true);
        Page mayorCriaderoBarrio = new TextPage(this,labels.getMayorCriaderoBarrio(),"", Constants.WIZARD, true).setRequired(true);

        Page mcParedes = new MultipleFixedChoicePage(this,labels.getMaterialParedes(),"",Constants.WIZARD, true).setChoices(catMaterialParedes).setRequired(true);
        Page tpParedesOtroDesc = new TextPage(this, labels.getOtroMaterialParedes(), "", Constants.WIZARD, true).setRequired(true);
        Page mcPiso = new MultipleFixedChoicePage(this,labels.getMaterialPiso(),"",Constants.WIZARD, true).setChoices(catMaterialPiso).setRequired(true);
        Page tpPisoOtroDesc = new TextPage(this, labels.getOtroMaterialPiso(), "", Constants.WIZARD, true).setRequired(true);
        Page scTecho = new SingleFixedChoicePage(this,labels.getMaterialTecho(),"",Constants.WIZARD, true).setChoices(catMaterialTecho).setRequired(true);
        Page tpTechoOtroDesc = new TextPage(this, labels.getOtroMaterialTecho(), "", Constants.WIZARD, true).setRequired(true);

        return new PageList(fechaCuestionario, barrio, direccion, puntoGps, tipoIngresoCodigo, codigoVivienda, codigoViviendaBr, tipoVivienda,
                hayAmbientePERI, horaCapturaPERI, humedadRelativaPERI, temperaturaPERI, tipoIngresoCodigoPERI, codigoPERI, codigoPERIBr,
                hayAmbienteINTRA, horaCapturaINTRA, humedadRelativaINTRA, temperaturaINTRA, tipoIngresoCodigoINTRA, codigoINTRA, codigoINTRABr,
                quienContesta, quienContestaOtro, edadContest, escolaridadContesta, tiempoVivirBarrio, cuantasPersonasViven, edadMujeres, edadHombres, usaronMosquitero, quienesUsaronMosquitero, usaronRepelente, quienesUsaronRepelente,
                conoceLarvas, alguienVisEliminarLarvas, quienVisEliminarLarvas, quienVisEliminarLarvasOtro, alguienDedicaElimLarvasCasa, quienDedicaElimLarvasCasa, tiempoElimanCridaros,
                hayBastanteZancudos, queFaltaCasaEvitarZancudos, queFaltaCasaEvitarZancudosOtros, gastaronDineroProductos, queProductosCompraron, queProductosCompraronOtros, cuantoGastaron,
                ultimaVisitaMinsaBTI, ultimaVezMinsaFumigo, riesgoCasaDengue, problemasAbastecimiento, cadaCuantoVaAgua, cadaCuantoVaAguaOtro, horasSinAguaDia,
                queHacenBasuraHogar, queHacenBasuraHogarOtro, riesgoBarrioDengue, accionesCriaderosBarrio, queAcciones, queAccionesOtro, alguienParticipo, quienParticipo, mayorCriaderoBarrio,
                mcParedes, tpParedesOtroDesc, mcPiso, tpPisoOtroDesc, scTecho, tpTechoOtroDesc
                );
    }
}
