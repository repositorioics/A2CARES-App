package ni.org.ics.a2cares.app.ui.forms;

import android.content.Context;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.wizard.model.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class TamizajeForm extends AbstractWizardModel {
	
	int index = 0;
	private TamizajeFormLabels labels;
	private EstudioDBAdapter estudiosAdapter;

    private String[] fillBarrios(boolean incluirFueraSector){
        String[] catalogo;
        List<Barrio> barrios = estudiosAdapter.getBarrios(incluirFueraSector?null:MainDBConstants.nombre+" <> 'Fuera de Sector'", MainDBConstants.codigo);
        catalogo = new String[barrios.size()];
        index = 0;
        for (Barrio message: barrios){
            catalogo[index] = message.getNombre();
            index++;
        }
        return catalogo;
    }

    public TamizajeForm(Context context, String pass) {
        super(context,pass);
    }

    @Override
    protected PageList onNewRootPageList() {

    	labels = new TamizajeFormLabels();
    	this.estudiosAdapter = new EstudioDBAdapter(mContext,mPass,false,false);

    	estudiosAdapter.open();
    	String[] catSexo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SEXO");
        String[] catSiNo = estudiosAdapter.getMessageResourcesForCatalog("CAT_SINO");
        //String[] catSiNoDes = estudiosAdapter.getMessageResourcesForCatalog("CHF_CAT_SND");
        String[] catRelacionFamiliarTutor = estudiosAdapter.getMessageResourcesForCatalog("CAT_RF_TUTOR");
        String[] barrios = fillBarrios(false);
        /*String[] catDondeAsisteProblemasSalud = estudiosAdapter.getMessageResourcesForCatalog("CHF_CAT_DONDEASISTE");
        String[] catPuestoSalud = estudiosAdapter.getMessageResourcesForCatalog("CHF_CAT_PUESTO");
        String[] catCriteriosInclusion = estudiosAdapter.getMessageResourcesForCatalog("CP_CAT_CI");
        */String[] catTiempoResid = estudiosAdapter.getMessageResourcesForCatalog("CAT_TMP_RES");
        String[] catRazonNoParticipaPersona = estudiosAdapter.getMessageResourcesForCatalog("CAT_NO_TAMIZAJE");
        //String[] catTipo = estudiosAdapter.getMessageResourcesForCatalog("CAT_TIPO_TEL");
        String[] catOperadora = estudiosAdapter.getMessageResourcesForCatalog("CAT_OPER_TEL");
        String[] catTipoViv = estudiosAdapter.getMessageResourcesForCatalog("CAT_TIPO_VIVIENDA");
        String[] catVerificaTutor = estudiosAdapter.getMessageResourcesForCatalog("CAT_VERIFICA");
        String[] catMotivoRechazo = estudiosAdapter.getMessageResourcesForCatalog("CAT_NO_PARTICIPA");
        estudiosAdapter.close();

        DateMidnight dmDesde = DateMidnight.parse("01/01/1900", DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateMidnight dmHasta = new DateMidnight(new Date().getTime());

        Page sexo = new SingleFixedChoicePage(this,labels.getSexo(), labels.getSexoHint(), Constants.WIZARD, true).setChoices(catSexo).setRequired(true);
		Page fechaNacimiento = new NewDatePage(this,labels.getFechaNacimiento(), labels.getFechaNacimientoHint(), Constants.WIZARD, true).setRangeValidation(true, dmDesde, dmHasta).setRequired(true);
		Page edad = new LabelPage(this,labels.getEdad(),"", Constants.WIZARD, true).setRequired(false);

        Page aceptaTamizaje = new SingleFixedChoicePage(this,labels.getAceptaTamizaje(), labels.getAceptaTamizajeHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaTamizaje = new SingleFixedChoicePage(this,labels.getRazonNoAceptaTamizaje(), labels.getRazonNoAceptaTamizajeHint(), Constants.WIZARD, false).setChoices(catRazonNoParticipaPersona).setRequired(true);
        Page otraRazonNoAceptaTamizaje = new TextPage(this,labels.getOtraRazonNoAceptaTamizaje(),labels.getOtraRazonNoAceptaTamizajeHint(),Constants.WIZARD, false).setRequired(true);

        //Page criteriosInclusion = new MultipleFixedChoicePage(this,labels.getCriteriosInclusion(), labels.getCriteriosInclusionHint(), Constants.WIZARD, false).setChoices(catCriteriosInclusion).setRequired(true);
        Page vivienda = new SingleFixedChoicePage(this,labels.getVivienda(), "", Constants.WIZARD, false).setChoices(catTipoViv).setRequired(true);
        Page tiempoResidencia = new SingleFixedChoicePage(this,labels.getTiempoResidencia(), "", Constants.WIZARD, false).setChoices(catTiempoResid).setRequired(true);
        Page planesMudarse = new SingleFixedChoicePage(this,labels.getPlanesMudarse(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
/*
        Page dondeAsisteProblemasSalud = new SingleFixedChoicePage(this,labels.getDondeAsisteProblemasSalud(), labels.getDondeAsisteProblemasSaludHint(), Constants.WIZARD, false).setChoices(catDondeAsisteProblemasSalud).setRequired(true);
        Page otroCentroSalud = new TextPage(this,labels.getOtroCentroSalud(),labels.getOtroCentroSaludHint(),Constants.WIZARD, false).setRequired(true);
        Page puestoSalud = new SingleFixedChoicePage(this,labels.getPuestoSalud(), labels.getPuestoSaludHint(), Constants.WIZARD, false).setChoices(catPuestoSalud).setRequired(true);
        Page aceptaAtenderCentro = new SingleFixedChoicePage(this,labels.getAceptaAtenderCentro(), labels.getAceptaAtenderCentroHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

*/
        Page asentimientoVerbal = new SingleFixedChoicePage(this,labels.getAsentimientoVerbal(), labels.getAsentimientoVerbalHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParticipar = new SingleFixedChoicePage(this,labels.getAceptaParticipar(), labels.getAceptaParticiparHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page razonNoAceptaParticipar = new SingleFixedChoicePage(this,labels.getRazonNoAceptaParticipar(), labels.getRazonNoAceptaParticiparHint(), Constants.WIZARD, false).setChoices(catMotivoRechazo).setRequired(true);
        Page otraRazonNoAceptaParticipar = new TextPage(this,labels.getOtraRazonNoAceptaParticipar(),labels.getOtraRazonNoAceptaParticiparHint(),Constants.WIZARD, false).setRequired(true);

        Page aceptaContactoFuturo = new SingleFixedChoicePage(this,labels.getAceptaContactoFuturo(), labels.getAceptaContactoFuturoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page aceptaParteB = new SingleFixedChoicePage(this,labels.getAceptaParteB(), labels.getAceptaParteBHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page aceptaParteC = new SingleFixedChoicePage(this,labels.getAceptaParteC(), labels.getAceptaParteCHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);

        Page casaPerteneceCohorte = new SingleFixedChoicePage(this,labels.getCasaPerteneceCohorte(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //minimo al momento se hacer el cambio: 24092019
        Page codigoCasaCohorte = new SelectCasaPage(this,labels.getCodigoCasaCohorte(),labels.getCodigoCasaCohorteHint(),Constants.WIZARD, false).setRequired(true);
        Page codigoNuevaCasaCohorte = new BarcodePage(this,labels.getCodigoNuevaCasaCohorte(),"",Constants.WIZARD, false).setRangeValidation(true, 1, 1000).setRequired(true);
        Page nombre1JefeFamilia = new TextPage(this,labels.getNombre1JefeFamilia(), labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page nombre2JefeFamilia = new TextPage(this,labels.getNombre2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page apellido1JefeFamilia = new TextPage(this,labels.getApellido1JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(true);
        Page apellido2JefeFamilia = new TextPage(this,labels.getApellido2JefeFamilia(),labels.getJefeFamiliaHint(), Constants.WIZARD, false).setRequired(false);
        Page barrio = new SingleFixedChoicePage(this,labels.getBarrio(), "", Constants.WIZARD, false).setChoices(barrios).setRequired(true);
        //Page manzana = new NumberPage(this,labels.getManzana(),"",Constants.WIZARD, false).setRangeValidation(true,0,114).setRequired(true);
        Page direccion = new TextPage(this,labels.getDireccion(),labels.getDireccionHint(), Constants.WIZARD, false).setRequired(true);
        Page ubicacion = new MapaBarriosPage(this, labels.getCoordenadas(), "", Constants.WIZARD, true).setRequired(true);

        Page codigoNuevoParticipante = new BarcodePage(this,labels.getCodigoNuevoParticipante(),"",Constants.WIZARD, false).setPatternValidation(true, "^\\d{4}$").setRequired(true);
        Page nombre1 = new TextPage(this,labels.getNombre1(),labels.getNombre1Hint(),Constants.WIZARD, false).setRequired(true);
        Page nombre2 = new TextPage(this,labels.getNombre2(),labels.getNombre1Hint(),Constants.WIZARD, false).setRequired(false);
        Page apellido1 = new TextPage(this,labels.getApellido1(),labels.getApellido1Hint(),Constants.WIZARD, false).setRequired(true);
        Page apellido2 = new TextPage(this,labels.getApellido2(),labels.getApellido1Hint(),Constants.WIZARD, false).setRequired(false);
        Page nombre1Padre = new TextPage(this,labels.getNombre1Padre(),labels.getNombre1PadreHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Padre = new TextPage(this,labels.getNombre2Padre(),labels.getNombre2Padre(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Padre = new TextPage(this,labels.getApellido1Padre(),labels.getApellido1PadreHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Padre = new TextPage(this,labels.getApellido2Padre(),labels.getApellido2PadreHint(),Constants.WIZARD,false).setRequired(false);
        Page nombre1Madre = new TextPage(this,labels.getNombre1Madre(),labels.getNombre1MadreHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Madre = new TextPage(this,labels.getNombre2Madre(),labels.getNombre2Madre(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Madre = new TextPage(this,labels.getApellido1Madre(),labels.getApellido1Madre(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Madre = new TextPage(this,labels.getApellido2Madre(),labels.getApellido2MadreHint(),Constants.WIZARD,false).setRequired(false);

        Page nombre1Tutor = new TextPage(this,labels.getNombre1Tutor(),labels.getNombre1TutorHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Tutor = new TextPage(this,labels.getNombre2Tutor(),labels.getNombre2TutorHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Tutor = new TextPage(this,labels.getApellido1Tutor(),labels.getApellido1TutorHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Tutor = new TextPage(this,labels.getApellido2Tutor(),labels.getApellido2TutorHint(),Constants.WIZARD,false).setRequired(false);
        Page relacionFamiliarTutor = new SingleFixedChoicePage(this,labels.getRelacionFamiliarTutor(), "", Constants.WIZARD, false).setChoices(catRelacionFamiliarTutor).setRequired(true);

        Page participanteOTutorAlfabeto = new SingleFixedChoicePage(this,labels.getParticipanteOTutorAlfabeto(), labels.getParticipanteOTutorAlfabetoHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page testigoPresente = new SingleFixedChoicePage(this,labels.getTestigoPresente(), labels.getTestigoPresenteHint(), Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page nombre1Testigo = new TextPage(this,labels.getNombre1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page nombre2Testigo = new TextPage(this,labels.getNombre2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);
        Page apellido1Testigo = new TextPage(this,labels.getApellido1Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(true);
        Page apellido2Testigo = new TextPage(this,labels.getApellido2Testigo(),labels.getNombre1TestigoHint(),Constants.WIZARD,false).setRequired(false);


        Page tieneTelefono = new SingleFixedChoicePage(this,labels.getTieneTelefonoCel(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Page tipoTel1 = new SingleFixedChoicePage(this,labels.getTipoTelefono1(),labels.getTipoTelefono1Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel1 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono1(),labels.getOperadoraTelefono1Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel1 = new NumberPage(this,labels.getNumTelefono1(),labels.getNumTelefono1Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7]{1}\\d{7}$").setRequired(true);
        Page tieneOtroTelefono = new SingleFixedChoicePage(this,labels.getTieneOtroTelefonoCel(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        //Page tipoTel2 = new SingleFixedChoicePage(this,labels.getTipoTelefono2(),labels.getTipoTelefono2Hint(), Constants.WIZARD, false).setChoices(catTipo).setRequired(true);
        Page operadoraTel2 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono2(),labels.getOperadoraTelefono2Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel2 = new NumberPage(this,labels.getNumTelefono2(),labels.getNumTelefono2Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[3|8|5|7]{1}\\d{7}$").setRequired(true);
        Page tieneTelefonoConv = new SingleFixedChoicePage(this,labels.getTieneTelefonoConv(), "", Constants.WIZARD, false).setChoices(catSiNo).setRequired(true);
        Page operadoraTel3 = new SingleFixedChoicePage(this,labels.getOperadoraTelefono3(),labels.getOperadoraTelefono3Hint(), Constants.WIZARD, false).setChoices(catOperadora).setRequired(true);
        Page numeroTel3 = new NumberPage(this,labels.getNumTelefono3(),labels.getNumTelefono3Hint(), Constants.WIZARD, false).setPatternValidation(true, "^$|^[2]{1}\\d{7}$").setRequired(true);

        Page verifTutor = new MultipleFixedChoicePage(this,labels.getVerifTutor(), "", Constants.WIZARD, false).setChoices(catVerificaTutor).setRequired(true);

        Page finTamizajeLabel = new LabelPage(this,labels.getFinTamizajeLabel(),"", Constants.WIZARD, true).setRequired(false);

		return new PageList(sexo, fechaNacimiento, edad, aceptaTamizaje, razonNoAceptaTamizaje, otraRazonNoAceptaTamizaje,
                vivienda, tiempoResidencia, planesMudarse, asentimientoVerbal, aceptaParticipar, razonNoAceptaParticipar, otraRazonNoAceptaParticipar,
                casaPerteneceCohorte, codigoCasaCohorte, codigoNuevaCasaCohorte, nombre1JefeFamilia, nombre2JefeFamilia, apellido1JefeFamilia, apellido2JefeFamilia, barrio, direccion, ubicacion,
                codigoNuevoParticipante, nombre1, nombre2, apellido1, apellido2,
                nombre1Padre, nombre2Padre, apellido1Padre, apellido2Padre, nombre1Madre, nombre2Madre, apellido1Madre, apellido2Madre,
                nombre1Tutor, nombre2Tutor, apellido1Tutor, apellido2Tutor, relacionFamiliarTutor,
                participanteOTutorAlfabeto, testigoPresente, nombre1Testigo, nombre2Testigo, apellido1Testigo, apellido2Testigo,
                aceptaContactoFuturo, aceptaParteB, aceptaParteC,
                tieneTelefono, numeroTel1, operadoraTel1, tieneOtroTelefono, numeroTel2, operadoraTel2, tieneTelefonoConv, numeroTel3, operadoraTel3,
                verifTutor, finTamizajeLabel);
    }

	public TamizajeFormLabels getLabels() {
		return labels;
	}

	public void setLabels(TamizajeFormLabels labels) {
		this.labels = labels;
	}
    
}
