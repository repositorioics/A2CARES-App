package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;


/**
 * Constantes usadas en formulario de tamizaje
 * 
 * @author William Aviles
 * 
 */
public class TamizajeFormLabels {

    protected String fechaNacimiento;
    protected String fechaNacimientoHint;
    protected String edad;
    protected String sexo;
    protected String sexoHint;

    protected String aceptaTamizaje;
    protected String aceptaTamizajeHint;
    protected String razonNoAceptaTamizaje;
    protected String razonNoAceptaTamizajeHint;
    protected String otraRazonNoAceptaTamizaje;
    protected String otraRazonNoAceptaTamizajeHint;

    protected String aceptaParticipar;
    protected String aceptaParticiparHint;
    protected String razonNoAceptaParticipar;
    protected String razonNoAceptaParticiparHint;
    protected String otraRazonNoAceptaParticipar;
    protected String otraRazonNoAceptaParticiparHint;

    protected String asentimientoVerbal;
    protected String asentimientoVerbalHint;

    protected String tiempoResidencia;
    protected String vivienda;
    protected String planesMudarse;

    /*protected String enfermedad;
    protected String enfermedadHint;
    protected String dondeAsisteProblemasSalud;
    protected String dondeAsisteProblemasSaludHint;
    protected String otroCentroSalud;
    protected String otroCentroSaludHint;
    protected String puestoSalud;
    protected String puestoSaludHint;
    protected String aceptaAtenderCentro;
    protected String aceptaAtenderCentroHint;*/

    private String aceptaContactoFuturo;
    private String aceptaContactoFuturoHint;
    protected String aceptaParteB;
    protected String aceptaParteBHint;
    protected String aceptaParteC;
    protected String aceptaParteCHint;

    protected String casaPerteneceCohorte;
    protected String codigoCasaCohorte;
    protected String codigoCasaCohorteHint;
    protected String codigoNuevaCasaCohorte;
    protected String nombre1JefeFamilia;
    protected String nombre2JefeFamilia;
    protected String apellido1JefeFamilia;
    protected String apellido2JefeFamilia;
    protected String jefeFamiliaHint;
    protected String direccion;
    protected String direccionHint;
    protected String barrio;
    //protected String manzana;
    protected String coordenadas;

    protected String codigoNuevoParticipante;
    protected String nombre1;
    protected String nombre1Hint;
    protected String nombre2;
    protected String nombre2Hint;
    protected String apellido1;
    protected String apellido1Hint;
    protected String apellido2;
    protected String apellido2Hint;
    protected String nombre1Padre;
    protected String nombre1PadreHint;
    protected String nombre2Padre;
    protected String nombre2PadreHint;
    protected String apellido1Padre;
    protected String apellido1PadreHint;
    protected String apellido2Padre;
    protected String apellido2PadreHint;
    protected String nombre1Madre;
    protected String nombre1MadreHint;
    protected String nombre2Madre;
    protected String nombre2MadreHint;
    protected String apellido1Madre;
    protected String apellido1MadreHint;
    protected String apellido2Madre;
    protected String apellido2MadreHint;
    protected String nombre1Tutor;
    protected String nombre1TutorHint;
    protected String nombre2Tutor;
    protected String nombre2TutorHint;
    protected String apellido1Tutor;
    protected String apellido1TutorHint;
    protected String apellido2Tutor;
    protected String apellido2TutorHint;
    protected String relacionFamiliarTutor;
    protected String participanteOTutorAlfabeto;
    protected String participanteOTutorAlfabetoHint;
    protected String testigoPresente;
    protected String testigoPresenteHint;
    protected String nombre1Testigo;
    protected String nombre1TestigoHint;
    protected String nombre2Testigo;
    protected String nombre2TestigoHint;
    protected String apellido1Testigo;
    protected String apellido1TestigoHint;
    protected String apellido2Testigo;
    protected String apellido2TestigoHint;
    /*
    protected String nombreContacto;
    protected String nombreContactoHint;
    protected String barrioContacto;
    protected String barrioContactoHint;
    protected String direccionContacto;
    protected String direccionContactoHint;
    */
    protected String tieneTelefonoCel;
    //protected String tipoTelefono1;
    //protected String tipoTelefono1Hint;
    protected String numTelefono1;
    protected String numTelefono1Hint;
    protected String operadoraTelefono1;
    protected String operadoraTelefono1Hint;

    protected String tieneOtroTelefonoCel;
    //protected String tipoTelefono2;
    //protected String tipoTelefono2Hint;
    protected String numTelefono2;
    protected String numTelefono2Hint;
    protected String operadoraTelefono2;
    protected String operadoraTelefono2Hint;

    protected String tieneTelefonoConv;
    protected String numTelefono3;
    protected String numTelefono3Hint;
    protected String operadoraTelefono3;
    protected String operadoraTelefono3Hint;


    protected String verifTutor;
    protected String finTamizajeLabel;


    public TamizajeFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

        fechaNacimiento = res.getString(R.string.fechaNacimiento);
        fechaNacimientoHint = res.getString(R.string.fechaNacimientoHint);
        edad = res.getString(R.string.edad);
        sexo = res.getString(R.string.sexo);
        sexoHint = res.getString(R.string.sexoHint);

        aceptaTamizaje = res.getString(R.string.aceptaTamizaje);
        aceptaTamizajeHint = res.getString(R.string.aceptaTamizajeHint);
        razonNoAceptaTamizaje = res.getString(R.string.razonNoAceptaTamizaje);
        razonNoAceptaTamizajeHint = res.getString(R.string.razonNoAceptaTamizajeHint);
        otraRazonNoAceptaTamizaje = res.getString(R.string.otraRazonNoAceptaTamizaje);
        otraRazonNoAceptaTamizajeHint = res.getString(R.string.otraRazonNoAceptaTamizajeHint);

        tiempoResidencia = res.getString(R.string.tiempoResidencia);
        vivienda = res.getString(R.string.tipoVivienda);
        planesMudarse = res.getString(R.string.planesMudarse);

        aceptaParticipar = res.getString(R.string.aceptaParticipar);
        aceptaParticiparHint = res.getString(R.string.aceptaParticiparHint);
        razonNoAceptaParticipar = res.getString(R.string.razonNoAceptaParticipar);
        razonNoAceptaParticiparHint = res.getString(R.string.razonNoAceptaParticiparHint);
        otraRazonNoAceptaParticipar = res.getString(R.string.otraRazonNoAceptaParticipar);
        otraRazonNoAceptaParticiparHint = res.getString(R.string.otraRazonNoAceptaParticiparHint);

        asentimientoVerbal = res.getString(R.string.asentimientoVerbal);
        asentimientoVerbalHint = res.getString(R.string.asentimientoVerbalHint);

        aceptaContactoFuturo = res.getString(R.string.aceptaContactoFuturo);
        aceptaContactoFuturoHint = res.getString(R.string.aceptaContactoFuturoHint);
        aceptaParteB = res.getString(R.string.aceptaParteB);
        aceptaParteBHint = res.getString(R.string.aceptaParteBHint);
        aceptaParteC = res.getString(R.string.aceptaParteC);
        aceptaParteCHint = res.getString(R.string.aceptaParteCHint);

        //criteriosInclusion = res.getString(R.string.criteriosInclusion);
        //criteriosInclusionHint = res.getString(R.string.criteriosInclusionHint);

/*
        dondeAsisteProblemasSalud = res.getString(R.string.dondeAsisteProblemasSalud);
        dondeAsisteProblemasSaludHint = res.getString(R.string.dondeAsisteProblemasSaludHint);
        otroCentroSalud = res.getString(R.string.otroCentroSalud);
        otroCentroSaludHint = res.getString(R.string.otroCentroSaludHint);
        puestoSalud = res.getString(R.string.puestoSalud);
        puestoSaludHint = res.getString(R.string.puestoSaludHint);

        aceptaAtenderCentro = res.getString(R.string.aceptaAtenderCentro);
        aceptaAtenderCentroHint = res.getString(R.string.aceptaAtenderCentroHint);
*/

        casaPerteneceCohorte = res.getString(R.string.casaPerteneceCohorte);
        codigoCasaCohorte = res.getString(R.string.codigoCasaCohorte);
        codigoCasaCohorteHint = res.getString(R.string.codigoCasaCohorteHint);
        codigoNuevaCasaCohorte = res.getString(R.string.codigoNuevaCasaCohorte);
        nombre1JefeFamilia = res.getString(R.string.nombre1JefeFamilia);
        nombre2JefeFamilia = res.getString(R.string.nombre2JefeFamilia);
        apellido1JefeFamilia = res.getString(R.string.apellido1JefeFamilia);
        apellido2JefeFamilia = res.getString(R.string.apellido2JefeFamilia);
        jefeFamiliaHint = res.getString(R.string.jefeFamiliaHint);
        direccion = res.getString(R.string.direccion);
        direccionHint = res.getString(R.string.direccionHint);
        barrio = res.getString(R.string.barrio);
        coordenadas = res.getString(R.string.coordenadas);
        //manzana = res.getString(R.string.manzana);

        codigoNuevoParticipante = res.getString(R.string.codigoNuevoParticipante);
        nombre1 = res.getString(R.string.nombre1);
        nombre1Hint = res.getString(R.string.nombre1Hint);
        nombre2 = res.getString(R.string.nombre2);
        nombre2Hint = res.getString(R.string.nombre2Hint);
        apellido1 = res.getString(R.string.apellido1);
        apellido1Hint = res.getString(R.string.apellido1Hint);
        apellido2 = res.getString(R.string.apellido2);
        apellido2Hint = res.getString(R.string.apellido2Hint);
        nombre1Padre = res.getString(R.string.nombre1Padre);
        nombre1PadreHint = res.getString(R.string.nombre1PadreHint);
        nombre2Padre = res.getString(R.string.nombre2Padre);
        nombre2PadreHint = res.getString(R.string.nombre2PadreHint);
        apellido1Padre = res.getString(R.string.apellido1Padre);
        apellido1PadreHint = res.getString(R.string.apellido1PadreHint);
        apellido2Padre = res.getString(R.string.apellido2Padre);
        apellido2PadreHint = res.getString(R.string.apellido2PadreHint);
        nombre1Madre = res.getString(R.string.nombre1Madre);
        nombre1MadreHint = res.getString(R.string.nombre1MadreHint);
        nombre2Madre = res.getString(R.string.nombre2Madre);
        nombre2MadreHint = res.getString(R.string.nombre2MadreHint);
        apellido1Madre = res.getString(R.string.apellido1Madre);
        apellido1MadreHint = res.getString(R.string.apellido1MadreHint);
        apellido2Madre = res.getString(R.string.apellido2Madre);
        apellido2MadreHint = res.getString(R.string.apellido2MadreHint);
        nombre1Tutor = res.getString(R.string.nombre1Tutor);
        nombre1TutorHint = res.getString(R.string.nombre1TutorHint);
        nombre2Tutor = res.getString(R.string.nombre2Tutor);
        nombre2TutorHint = res.getString(R.string.nombre2TutorHint);
        apellido1Tutor = res.getString(R.string.apellido1Tutor);
        apellido1TutorHint = res.getString(R.string.apellido1TutorHint);
        apellido2Tutor = res.getString(R.string.apellido2Tutor);
        apellido2TutorHint = res.getString(R.string.apellido2TutorHint);
        relacionFamiliarTutor = res.getString(R.string.relacionFamiliarTutor);

        participanteOTutorAlfabeto = res.getString(R.string.participanteOTutorAlfabeto);
        participanteOTutorAlfabetoHint = res.getString(R.string.participanteOTutorAlfabetoHint);
        testigoPresente = res.getString(R.string.testigoPresente);
        testigoPresenteHint = res.getString(R.string.testigoPresenteHint);
        nombre1Testigo = res.getString(R.string.nombre1Testigo);
        nombre1TestigoHint = res.getString(R.string.nombre1TestigoHint);
        nombre2Testigo = res.getString(R.string.nombre2Testigo);
        nombre2TestigoHint = res.getString(R.string.nombre2TestigoHint);
        apellido1Testigo = res.getString(R.string.apellido1Testigo);
        apellido1TestigoHint = res.getString(R.string.apellido1TestigoHint);
        apellido2Testigo = res.getString(R.string.apellido2Testigo);
        apellido2TestigoHint = res.getString(R.string.apellido2TestigoHint);

        /*nombreContacto = res.getString(R.string.nombreContacto);
        nombreContactoHint = res.getString(R.string.nombreContactoHint);
        barrioContacto = res.getString(R.string.barrioContacto);
        barrioContactoHint = res.getString(R.string.barrioContactoHint);
        direccionContacto = res.getString(R.string.direccionContacto);
        direccionContactoHint = res.getString(R.string.direccionContactoHint);
        */
        numTelefono1 = res.getString(R.string.numTelefono1);
        numTelefono1Hint = res.getString(R.string.numTelefono1Hint);
        operadoraTelefono1 = res.getString(R.string.operadoraTelefono1);
        operadoraTelefono1Hint = res.getString(R.string.operadoraTelefono1Hint);
        numTelefono2 = res.getString(R.string.numTelefono2);
        numTelefono2Hint = res.getString(R.string.numTelefono2Hint);
        operadoraTelefono2 = res.getString(R.string.operadoraTelefono2);
        operadoraTelefono2Hint = res.getString(R.string.operadoraTelefono2Hint);
        numTelefono3 = res.getString(R.string.numTelefono3);
        numTelefono3Hint = res.getString(R.string.numTelefono3Hint);
        operadoraTelefono3 = res.getString(R.string.operadoraTelefono3);
        operadoraTelefono3Hint = res.getString(R.string.operadoraTelefono3Hint);
        //tipoTelefono1 = res.getString(R.string.tipoTelefono1);
        //tipoTelefono1Hint = res.getString(R.string.tipoTelefono1Hint);
        //tipoTelefono2 = res.getString(R.string.tipoTelefono2);
        //tipoTelefono2Hint = res.getString(R.string.tipoTelefono2Hint);
        tieneTelefonoCel = res.getString(R.string.tieneTelefonoCel);
        tieneOtroTelefonoCel = res.getString(R.string.tieneOtroTelefonoCel);
        tieneTelefonoConv = res.getString(R.string.tieneTelefonoConv);

        verifTutor = res.getString(R.string.verifTutor);
        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaNacimientoHint() {
        return fechaNacimientoHint;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSexoHint() {
        return sexoHint;
    }

    public String getAceptaTamizaje() {
        return aceptaTamizaje;
    }

    public String getAceptaTamizajeHint() {
        return aceptaTamizajeHint;
    }

    public String getRazonNoAceptaTamizaje() {
        return razonNoAceptaTamizaje;
    }

    public String getRazonNoAceptaTamizajeHint() {
        return razonNoAceptaTamizajeHint;
    }

    public String getOtraRazonNoAceptaTamizaje() {
        return otraRazonNoAceptaTamizaje;
    }

    public String getOtraRazonNoAceptaTamizajeHint() {
        return otraRazonNoAceptaTamizajeHint;
    }

    public String getAceptaParticipar() {
        return aceptaParticipar;
    }

    public String getAceptaParticiparHint() {
        return aceptaParticiparHint;
    }

    public String getRazonNoAceptaParticipar() {
        return razonNoAceptaParticipar;
    }

    public String getRazonNoAceptaParticiparHint() {
        return razonNoAceptaParticiparHint;
    }

    public String getOtraRazonNoAceptaParticipar() {
        return otraRazonNoAceptaParticipar;
    }

    public String getOtraRazonNoAceptaParticiparHint() {
        return otraRazonNoAceptaParticiparHint;
    }

    public String getAsentimientoVerbal() {
        return asentimientoVerbal;
    }

    public String getAsentimientoVerbalHint() {
        return asentimientoVerbalHint;
    }

    public String getTiempoResidencia() {
        return tiempoResidencia;
    }

    public String getVivienda() {
        return vivienda;
    }

    public String getPlanesMudarse() {
        return planesMudarse;
    }

    public String getAceptaContactoFuturo() {
        return aceptaContactoFuturo;
    }

    public String getAceptaContactoFuturoHint() {
        return aceptaContactoFuturoHint;
    }

    public String getAceptaParteB() {
        return aceptaParteB;
    }

    public String getAceptaParteBHint() {
        return aceptaParteBHint;
    }

    public String getAceptaParteC() {
        return aceptaParteC;
    }

    public String getAceptaParteCHint() {
        return aceptaParteCHint;
    }

    public String getCasaPerteneceCohorte() {
        return casaPerteneceCohorte;
    }

    public String getCodigoCasaCohorte() {
        return codigoCasaCohorte;
    }

    public String getCodigoCasaCohorteHint() {
        return codigoCasaCohorteHint;
    }

    public String getCodigoNuevaCasaCohorte() {
        return codigoNuevaCasaCohorte;
    }

    public String getNombre1JefeFamilia() {
        return nombre1JefeFamilia;
    }

    public String getNombre2JefeFamilia() {
        return nombre2JefeFamilia;
    }

    public String getApellido1JefeFamilia() {
        return apellido1JefeFamilia;
    }

    public String getApellido2JefeFamilia() {
        return apellido2JefeFamilia;
    }

    public String getJefeFamiliaHint() {
        return jefeFamiliaHint;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccionHint() {
        return direccionHint;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public String getCodigoNuevoParticipante() {
        return codigoNuevoParticipante;
    }

    public String getNombre1() {
        return nombre1;
    }

    public String getNombre1Hint() {
        return nombre1Hint;
    }

    public String getNombre2() {
        return nombre2;
    }

    public String getNombre2Hint() {
        return nombre2Hint;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido1Hint() {
        return apellido1Hint;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getApellido2Hint() {
        return apellido2Hint;
    }

    public String getNombre1Padre() {
        return nombre1Padre;
    }

    public String getNombre1PadreHint() {
        return nombre1PadreHint;
    }

    public String getNombre2Padre() {
        return nombre2Padre;
    }

    public String getNombre2PadreHint() {
        return nombre2PadreHint;
    }

    public String getApellido1Padre() {
        return apellido1Padre;
    }

    public String getApellido1PadreHint() {
        return apellido1PadreHint;
    }

    public String getApellido2Padre() {
        return apellido2Padre;
    }

    public String getApellido2PadreHint() {
        return apellido2PadreHint;
    }

    public String getNombre1Madre() {
        return nombre1Madre;
    }

    public String getNombre1MadreHint() {
        return nombre1MadreHint;
    }

    public String getNombre2Madre() {
        return nombre2Madre;
    }

    public String getNombre2MadreHint() {
        return nombre2MadreHint;
    }

    public String getApellido1Madre() {
        return apellido1Madre;
    }

    public String getApellido1MadreHint() {
        return apellido1MadreHint;
    }

    public String getApellido2Madre() {
        return apellido2Madre;
    }

    public String getApellido2MadreHint() {
        return apellido2MadreHint;
    }

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public String getNombre1TutorHint() {
        return nombre1TutorHint;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public String getNombre2TutorHint() {
        return nombre2TutorHint;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public String getApellido1TutorHint() {
        return apellido1TutorHint;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public String getApellido2TutorHint() {
        return apellido2TutorHint;
    }

    public String getRelacionFamiliarTutor() {
        return relacionFamiliarTutor;
    }

    public String getParticipanteOTutorAlfabeto() {
        return participanteOTutorAlfabeto;
    }

    public String getParticipanteOTutorAlfabetoHint() {
        return participanteOTutorAlfabetoHint;
    }

    public String getTestigoPresente() {
        return testigoPresente;
    }

    public String getTestigoPresenteHint() {
        return testigoPresenteHint;
    }

    public String getNombre1Testigo() {
        return nombre1Testigo;
    }

    public String getNombre1TestigoHint() {
        return nombre1TestigoHint;
    }

    public String getNombre2Testigo() {
        return nombre2Testigo;
    }

    public String getNombre2TestigoHint() {
        return nombre2TestigoHint;
    }

    public String getApellido1Testigo() {
        return apellido1Testigo;
    }

    public String getApellido1TestigoHint() {
        return apellido1TestigoHint;
    }

    public String getApellido2Testigo() {
        return apellido2Testigo;
    }

    public String getApellido2TestigoHint() {
        return apellido2TestigoHint;
    }

    public String getTieneTelefonoCel() {
        return tieneTelefonoCel;
    }

    public String getNumTelefono1() {
        return numTelefono1;
    }

    public String getNumTelefono1Hint() {
        return numTelefono1Hint;
    }

    public String getOperadoraTelefono1() {
        return operadoraTelefono1;
    }

    public String getOperadoraTelefono1Hint() {
        return operadoraTelefono1Hint;
    }

    public String getTieneOtroTelefonoCel() {
        return tieneOtroTelefonoCel;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public String getNumTelefono2Hint() {
        return numTelefono2Hint;
    }

    public String getOperadoraTelefono2() {
        return operadoraTelefono2;
    }

    public String getOperadoraTelefono2Hint() {
        return operadoraTelefono2Hint;
    }

    public String getTieneTelefonoConv() {
        return tieneTelefonoConv;
    }

    public String getNumTelefono3() {
        return numTelefono3;
    }

    public String getNumTelefono3Hint() {
        return numTelefono3Hint;
    }

    public String getOperadoraTelefono3() {
        return operadoraTelefono3;
    }

    public String getOperadoraTelefono3Hint() {
        return operadoraTelefono3Hint;
    }

    public String getVerifTutor() {
        return verifTutor;
    }

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }

    public String getEdad() {
        return edad;
    }
}
