package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 15/6/2021.
 */
public class EncuestaParticipanteFormLabels {

    protected String emancipado;
    protected String descEmancipado;
    protected String otraDescEmanc;
    protected String embarazada;
    protected String conoceFUR;
    protected String fur;
    protected String asisteEscuela;
    protected String gradoEstudia;
    protected String nombreEscuela;
    protected String turno;
    protected String cuidan;
    protected String cuantosCuidan;
    protected String nombreCDI;
    protected String direccionCDI;
    protected String otroLugarCuidan;
    protected String alfabeto;
    protected String nivelEducacion;
    protected String trabaja;
    protected String tipoTrabajo;
    protected String ocupacionActual;
    protected String personaVive;
    protected String ordenNac;
    protected String padreAlfabeto;
    protected String nivelEscolarPadre;
    protected String trabajaPadre;
    protected String tipoTrabajoPadre;
    protected String madreAlfabeta;
    protected String nivelEscolarMadre;
    protected String trabajaMadre;
    protected String tipoTrabajoMadre;
    protected String comparteHab;
    protected String hab1;
    protected String hab2;
    protected String hab3;
    protected String hab4;
    protected String hab5;
    protected String hab6;
    protected String comparteCama;
    protected String cama1;
    protected String cama2;
    protected String cama3;
    protected String cama4;
    protected String cama5;
    protected String cama6;
    protected String fuma;
    protected String periodicidadFuma;
    protected String cantidadCigarrillos;
    protected String fumaDentroCasa;
    protected String tuberculosisPulmonarActual;
    protected String anioDiagTubpulActual;
    protected String mesDiagTubpulActual;
    protected String tratamientoTubpulActual;
    protected String completoTratamientoTubpulActual;
    protected String tuberculosisPulmonarPasado;
    protected String fechaDiagTubpulPasadoDes;
    protected String anioDiagTubpulPasado;
    protected String mesDiagTubpulPasado;
    protected String tratamientoTubpulPasado;
    protected String completoTratamientoTubpulPas;
    protected String alergiaRespiratoria;
    protected String anioAlergiaRespiratoria;
    protected String asma;
    protected String anioAsma;
    protected String enfermedadCronica;
    protected String cardiopatia;
    protected String anioCardiopatia;
    protected String enfermPulmonarObstCronica;
    protected String anioEnfermPulmonarObstCronica;
    protected String diabetes;
    protected String anioDiabetes;
    protected String presionAlta;
    protected String anioPresionAlta;
    protected String otrasEnfermedades;
    protected String descOtrasEnfermedades;
    protected String tenidoDengue;
    protected String anioDengue;
    protected String diagMedicoDengue;
    protected String dondeDengue;
    protected String tenidoZika;
    protected String anioZika;
    protected String diagMedicoZika;
    protected String dondeZika;
    protected String tenidoChik;
    protected String anioChik;
    protected String diagMedicoChik;
    protected String dondeChik;
    protected String vacunaFiebreAma;
    protected String anioVacunaFiebreAma;
    protected String vacunaCovid;
    protected String anioVacunaCovid;
    protected String mesVacunaCovid;
    protected String tieneTarjetaVacuna;
    protected String tomarFotoTarjeta;
    protected String tomarFotoTarjetaCovid;
    protected String fiebre;
    protected String tiempoFiebre;
    protected String lugarConsFiebre;
    protected String noAcudioCs;
    protected String automedicoFiebre;
    protected String tenidoDengueUA;
    protected String unidadSaludDengue;
    protected String centroSaludDengue;
    protected String otroCentroSaludDengue;
    protected String puestoSaludDengue;
    protected String otroPuestoSaludDengue;
    protected String hospitalDengue;
    protected String otroHospitalDengue;
    protected String hospitalizadoDengue;
    protected String ambulatorioDengue;
    protected String diagnosticoDengue;
    protected String rashUA;
    protected String recuerdaMesRash;
    protected String rashMes;
    protected String rashCara;
    protected String rashMiembrosSup;
    protected String rashTorax;
    protected String rashAbdomen;
    protected String rashMiembrosInf;
    protected String rashDias;
    protected String consultaRashUA;
    protected String unidadSaludRash;
    protected String centroSaludRash;
    protected String otroCentroSaludRash;
    protected String puestoSaludRash;
    protected String otroPuestoSaludRash;
    protected String diagnosticoRash;

    protected String finTamizajeLabel;


    public EncuestaParticipanteFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

        emancipado = res.getString(R.string.emancipado);
        descEmancipado = res.getString(R.string.descEmancipado);
        otraDescEmanc = res.getString(R.string.otraDescEmanc);
        embarazada = res.getString(R.string.embarazada);
        conoceFUR = res.getString(R.string.conoceFUR);
        fur = res.getString(R.string.fur);
        asisteEscuela = res.getString(R.string.asisteEscuela);
        gradoEstudia = res.getString(R.string.gradoEstudia);
        nombreEscuela = res.getString(R.string.nombreEscuela);
        turno = res.getString(R.string.turno);
        cuidan = res.getString(R.string.cuidan);
        cuantosCuidan = res.getString(R.string.cuantosCuidan);
        nombreCDI = res.getString(R.string.nombreCDI);
        direccionCDI = res.getString(R.string.direccionCDI);
        otroLugarCuidan = res.getString(R.string.otroLugarCuidan);
        alfabeto = res.getString(R.string.alfabeto);
        nivelEducacion = res.getString(R.string.nivelEducacion);
        trabaja = res.getString(R.string.trabaja);
        tipoTrabajo = res.getString(R.string.tipoTrabajo);
        ocupacionActual = res.getString(R.string.ocupacionActual);
        personaVive = res.getString(R.string.personaVive);
        ordenNac = res.getString(R.string.ordenNac);
        padreAlfabeto = res.getString(R.string.padreAlfabeto);
        nivelEscolarPadre = res.getString(R.string.papaNivel);
        trabajaPadre = res.getString(R.string.trabajaPadre);
        tipoTrabajoPadre = res.getString(R.string.papaTipoTra);
        madreAlfabeta = res.getString(R.string.madreAlfabeta);
        nivelEscolarMadre = res.getString(R.string.mamaNivel);
        trabajaMadre = res.getString(R.string.trabajaMadre);
        tipoTrabajoMadre = res.getString(R.string.mamaTipoTra);
        comparteHab = res.getString(R.string.comparteHab);
        hab1 = res.getString(R.string.hab1);
        hab2 = res.getString(R.string.hab2);
        hab3 = res.getString(R.string.hab3);
        hab4 = res.getString(R.string.hab4);
        hab5 = res.getString(R.string.hab5);
        hab6 = res.getString(R.string.hab6);
        comparteCama = res.getString(R.string.comparteCama);
        cama1 = res.getString(R.string.cama1);
        cama2 = res.getString(R.string.cama2);
        cama3 = res.getString(R.string.cama3);
        cama4 = res.getString(R.string.cama4);
        cama5 = res.getString(R.string.cama5);
        cama6 = res.getString(R.string.cama6);
        fuma = res.getString(R.string.fuma);
        periodicidadFuma = res.getString(R.string.periodicidadFuma);
        cantidadCigarrillos = res.getString(R.string.cantidadCigarrillos);
        fumaDentroCasa = res.getString(R.string.fumaDentroCasa);
        tuberculosisPulmonarActual = res.getString(R.string.tuberculosisPulmonarActual);
        anioDiagTubpulActual = res.getString(R.string.anioDiagTubpulActual);
        mesDiagTubpulActual = res.getString(R.string.mesDiagTubpulActual);
        tratamientoTubpulActual = res.getString(R.string.tratamientoTubpulActual);
        completoTratamientoTubpulActual = res.getString(R.string.completoTratamientoTubpulActual);
        tuberculosisPulmonarPasado = res.getString(R.string.tuberculosisPulmonarPasado);
        fechaDiagTubpulPasadoDes = res.getString(R.string.fechaDiagTubpulPasadoDes);
        anioDiagTubpulPasado = res.getString(R.string.anioDiagTubpulPasado);
        mesDiagTubpulPasado = res.getString(R.string.mesDiagTubpulPasado);
        tratamientoTubpulPasado = res.getString(R.string.tratamientoTubpulPasado);
        completoTratamientoTubpulPas = res.getString(R.string.completoTratamientoTubpulPas);
        alergiaRespiratoria = res.getString(R.string.alergiaRespiratoria);
        anioAlergiaRespiratoria = res.getString(R.string.alergiaRespiratoriaAnio);
        asma = res.getString(R.string.asma);
        anioAsma = res.getString(R.string.asmaAnio);
        enfermedadCronica = res.getString(R.string.enfermedadCronica);
        cardiopatia = res.getString(R.string.cardiopatia);
        anioCardiopatia = res.getString(R.string.cardiopatiaAnio);
        enfermPulmonarObstCronica = res.getString(R.string.enfermPulmonarObstCronica);
        anioEnfermPulmonarObstCronica = res.getString(R.string.enfermPulmonarObstCronicaAnio);
        diabetes = res.getString(R.string.diabetes);
        anioDiabetes = res.getString(R.string.diabetesAnio);
        presionAlta = res.getString(R.string.presionAlta);
        anioPresionAlta = res.getString(R.string.presionAltaAnio);
        otrasEnfermedades = res.getString(R.string.otrasEnfermedades);
        descOtrasEnfermedades = res.getString(R.string.descOtrasEnfermedades);
        tenidoDengue = res.getString(R.string.tenidoDengue);
        anioDengue = res.getString(R.string.anioDengue);
        diagMedicoDengue = res.getString(R.string.diagMedicoDengue);
        dondeDengue = res.getString(R.string.dondeDengue);
        tenidoZika = res.getString(R.string.tenidoZika);
        anioZika = res.getString(R.string.anioZika);
        diagMedicoZika = res.getString(R.string.diagMedicoZika);
        dondeZika = res.getString(R.string.dondeZika);
        tenidoChik = res.getString(R.string.tenidoChik);
        anioChik = res.getString(R.string.anioChik);
        diagMedicoChik = res.getString(R.string.diagMedicoChik);
        dondeChik = res.getString(R.string.dondeChik);
        vacunaFiebreAma = res.getString(R.string.vacunaFiebreAma);
        anioVacunaFiebreAma = res.getString(R.string.anioVacunaFiebreAma);
        vacunaCovid = res.getString(R.string.vacunaCovid);
        anioVacunaCovid = res.getString(R.string.anioVacunaCovid);
        mesVacunaCovid = res.getString(R.string.mesVacunaCovid);
        tieneTarjetaVacuna = res.getString(R.string.tieneTarjetaVacuna);
        tomarFotoTarjeta = res.getString(R.string.tomarFotoTarjeta);
        tomarFotoTarjetaCovid = res.getString(R.string.tomarFotoTarjetaCovid);
        fiebre = res.getString(R.string.fiebre);
        tiempoFiebre = res.getString(R.string.tiempoFiebre);
        lugarConsFiebre = res.getString(R.string.lugarConsFiebre);
        noAcudioCs = res.getString(R.string.noAcudioCs);
        automedicoFiebre = res.getString(R.string.automedicoFiebre);
        tenidoDengueUA = res.getString(R.string.tenidoDengueUA);
        unidadSaludDengue = res.getString(R.string.unidadSaludDengue);
        centroSaludDengue = res.getString(R.string.centroSaludDengue);
        otroCentroSaludDengue = res.getString(R.string.otroCentroSaludDengue);
        puestoSaludDengue = res.getString(R.string.puestoSaludDengue);
        otroPuestoSaludDengue = res.getString(R.string.otroPuestoSaludDengue);
        hospitalDengue = res.getString(R.string.hospitalDengue);
        otroHospitalDengue = res.getString(R.string.otroHospitalDengue);
        hospitalizadoDengue = res.getString(R.string.hospitalizadoDengue);
        ambulatorioDengue = res.getString(R.string.ambulatorioDengue);
        diagnosticoDengue = res.getString(R.string.diagnosticoDengue);
        rashUA = res.getString(R.string.rashUA);
        recuerdaMesRash = res.getString(R.string.recuerdaMesRash);
        rashMes = res.getString(R.string.rashMes);
        rashCara = res.getString(R.string.rashCara);
        rashMiembrosSup = res.getString(R.string.rashMiembrosSup);
        rashTorax = res.getString(R.string.rashTorax);
        rashAbdomen = res.getString(R.string.rashAbdomen);
        rashMiembrosInf = res.getString(R.string.rashMiembrosInf);
        rashDias = res.getString(R.string.rashDias);
        consultaRashUA = res.getString(R.string.consultaRashUA);
        unidadSaludRash = res.getString(R.string.unidadSaludRash);
        centroSaludRash = res.getString(R.string.centroSaludRash);
        otroCentroSaludRash = res.getString(R.string.otroCentroSaludRash);
        puestoSaludRash = res.getString(R.string.puestoSaludRash);
        otroPuestoSaludRash = res.getString(R.string.otroPuestoSaludRash);
        diagnosticoRash = res.getString(R.string.diagnosticoRash);

        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);

    }

    public String getEmancipado() {
        return emancipado;
    }

    public String getDescEmancipado() {
        return descEmancipado;
    }

    public String getOtraDescEmanc() {
        return otraDescEmanc;
    }

    public String getEmbarazada() {
        return embarazada;
    }

    public String getConoceFUR() {
        return conoceFUR;
    }

    public String getFur() {
        return fur;
    }

    public String getAsisteEscuela() {
        return asisteEscuela;
    }

    public String getGradoEstudia() {
        return gradoEstudia;
    }

    public String getNombreEscuela() {
        return nombreEscuela;
    }

    public String getTurno() {
        return turno;
    }

    public String getCuidan() {
        return cuidan;
    }

    public String getCuantosCuidan() {
        return cuantosCuidan;
    }

    public String getNombreCDI() {
        return nombreCDI;
    }

    public String getDireccionCDI() {
        return direccionCDI;
    }

    public String getOtroLugarCuidan() {
        return otroLugarCuidan;
    }

    public String getAlfabeto() {
        return alfabeto;
    }

    public String getNivelEducacion() {
        return nivelEducacion;
    }

    public String getTrabaja() {
        return trabaja;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public String getOcupacionActual() {
        return ocupacionActual;
    }

    public String getPersonaVive() {
        return personaVive;
    }

    public String getOrdenNac() {
        return ordenNac;
    }

    public String getPadreAlfabeto() {
        return padreAlfabeto;
    }

    public String getNivelEscolarPadre() {
        return nivelEscolarPadre;
    }

    public String getTrabajaPadre() {
        return trabajaPadre;
    }

    public String getTipoTrabajoPadre() {
        return tipoTrabajoPadre;
    }

    public String getMadreAlfabeta() {
        return madreAlfabeta;
    }

    public String getNivelEscolarMadre() {
        return nivelEscolarMadre;
    }

    public String getTrabajaMadre() {
        return trabajaMadre;
    }

    public String getTipoTrabajoMadre() {
        return tipoTrabajoMadre;
    }

    public String getComparteHab() {
        return comparteHab;
    }

    public String getHab1() {
        return hab1;
    }

    public String getHab2() {
        return hab2;
    }

    public String getHab3() {
        return hab3;
    }

    public String getHab4() {
        return hab4;
    }

    public String getHab5() {
        return hab5;
    }

    public String getHab6() {
        return hab6;
    }

    public String getComparteCama() {
        return comparteCama;
    }

    public String getCama1() {
        return cama1;
    }

    public String getCama2() {
        return cama2;
    }

    public String getCama3() {
        return cama3;
    }

    public String getCama4() {
        return cama4;
    }

    public String getCama5() {
        return cama5;
    }

    public String getCama6() {
        return cama6;
    }

    public String getFuma() {
        return fuma;
    }

    public String getPeriodicidadFuma() {
        return periodicidadFuma;
    }

    public String getCantidadCigarrillos() {
        return cantidadCigarrillos;
    }

    public String getFumaDentroCasa() {
        return fumaDentroCasa;
    }

    public String getTuberculosisPulmonarActual() {
        return tuberculosisPulmonarActual;
    }

    public String getAnioDiagTubpulActual() {
        return anioDiagTubpulActual;
    }

    public String getMesDiagTubpulActual() {
        return mesDiagTubpulActual;
    }

    public String getTratamientoTubpulActual() {
        return tratamientoTubpulActual;
    }

    public String getCompletoTratamientoTubpulActual() {
        return completoTratamientoTubpulActual;
    }

    public String getTuberculosisPulmonarPasado() {
        return tuberculosisPulmonarPasado;
    }

    public String getFechaDiagTubpulPasadoDes() {
        return fechaDiagTubpulPasadoDes;
    }

    public String getAnioDiagTubpulPasado() {
        return anioDiagTubpulPasado;
    }

    public String getMesDiagTubpulPasado() {
        return mesDiagTubpulPasado;
    }

    public String getTratamientoTubpulPasado() {
        return tratamientoTubpulPasado;
    }

    public String getCompletoTratamientoTubpulPas() {
        return completoTratamientoTubpulPas;
    }

    public String getAlergiaRespiratoria() {
        return alergiaRespiratoria;
    }

    public String getAnioAlergiaRespiratoria() {
        return anioAlergiaRespiratoria;
    }

    public String getAsma() {
        return asma;
    }

    public String getAnioAsma() {
        return anioAsma;
    }

    public String getEnfermedadCronica() {
        return enfermedadCronica;
    }

    public String getCardiopatia() {
        return cardiopatia;
    }

    public String getAnioCardiopatia() {
        return anioCardiopatia;
    }

    public String getEnfermPulmonarObstCronica() {
        return enfermPulmonarObstCronica;
    }

    public String getAnioEnfermPulmonarObstCronica() {
        return anioEnfermPulmonarObstCronica;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public String getAnioDiabetes() {
        return anioDiabetes;
    }

    public String getPresionAlta() {
        return presionAlta;
    }

    public String getAnioPresionAlta() {
        return anioPresionAlta;
    }

    public String getOtrasEnfermedades() {
        return otrasEnfermedades;
    }

    public String getDescOtrasEnfermedades() {
        return descOtrasEnfermedades;
    }

    public String getTenidoDengue() {
        return tenidoDengue;
    }

    public String getAnioDengue() {
        return anioDengue;
    }

    public String getDiagMedicoDengue() {
        return diagMedicoDengue;
    }

    public String getDondeDengue() {
        return dondeDengue;
    }

    public String getTenidoZika() {
        return tenidoZika;
    }

    public String getAnioZika() {
        return anioZika;
    }

    public String getDiagMedicoZika() {
        return diagMedicoZika;
    }

    public String getDondeZika() {
        return dondeZika;
    }

    public String getTenidoChik() {
        return tenidoChik;
    }

    public String getAnioChik() {
        return anioChik;
    }

    public String getDiagMedicoChik() {
        return diagMedicoChik;
    }

    public String getDondeChik() {
        return dondeChik;
    }

    public String getVacunaFiebreAma() {
        return vacunaFiebreAma;
    }

    public String getAnioVacunaFiebreAma() {
        return anioVacunaFiebreAma;
    }

    public String getVacunaCovid() {
        return vacunaCovid;
    }

    public String getAnioVacunaCovid() {
        return anioVacunaCovid;
    }

    public String getMesVacunaCovid() {
        return mesVacunaCovid;
    }

    public String getTieneTarjetaVacuna() {
        return tieneTarjetaVacuna;
    }

    public String getFiebre() {
        return fiebre;
    }

    public String getTiempoFiebre() {
        return tiempoFiebre;
    }

    public String getLugarConsFiebre() {
        return lugarConsFiebre;
    }

    public String getNoAcudioCs() {
        return noAcudioCs;
    }

    public String getAutomedicoFiebre() {
        return automedicoFiebre;
    }

    public String getTenidoDengueUA() {
        return tenidoDengueUA;
    }

    public String getUnidadSaludDengue() {
        return unidadSaludDengue;
    }

    public String getCentroSaludDengue() {
        return centroSaludDengue;
    }

    public String getOtroCentroSaludDengue() {
        return otroCentroSaludDengue;
    }

    public String getPuestoSaludDengue() {
        return puestoSaludDengue;
    }

    public String getOtroPuestoSaludDengue() {
        return otroPuestoSaludDengue;
    }

    public String getHospitalDengue() {
        return hospitalDengue;
    }

    public String getOtroHospitalDengue() {
        return otroHospitalDengue;
    }

    public String getHospitalizadoDengue() {
        return hospitalizadoDengue;
    }

    public String getAmbulatorioDengue() {
        return ambulatorioDengue;
    }

    public String getDiagnosticoDengue() {
        return diagnosticoDengue;
    }

    public String getRashUA() {
        return rashUA;
    }

    public String getRecuerdaMesRash() {
        return recuerdaMesRash;
    }

    public String getRashMes() {
        return rashMes;
    }

    public String getRashCara() {
        return rashCara;
    }

    public String getRashMiembrosSup() {
        return rashMiembrosSup;
    }

    public String getRashTorax() {
        return rashTorax;
    }

    public String getRashAbdomen() {
        return rashAbdomen;
    }

    public String getRashMiembrosInf() {
        return rashMiembrosInf;
    }

    public String getRashDias() {
        return rashDias;
    }

    public String getConsultaRashUA() {
        return consultaRashUA;
    }

    public String getUnidadSaludRash() {
        return unidadSaludRash;
    }

    public String getCentroSaludRash() {
        return centroSaludRash;
    }

    public String getOtroCentroSaludRash() {
        return otroCentroSaludRash;
    }

    public String getPuestoSaludRash() {
        return puestoSaludRash;
    }

    public String getOtroPuestoSaludRash() {
        return otroPuestoSaludRash;
    }

    public String getDiagnosticoRash() {
        return diagnosticoRash;
    }

    public String getTomarFotoTarjeta() {
        return tomarFotoTarjeta;
    }

    public String getTomarFotoTarjetaCovid() {
        return tomarFotoTarjetaCovid;
    }

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }
}
