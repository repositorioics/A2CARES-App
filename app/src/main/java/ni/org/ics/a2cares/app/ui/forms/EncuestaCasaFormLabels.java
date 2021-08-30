package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

/**
 * Created by Miguel Salinas on 9/6/2021.
 * V1.0
 */
public class EncuestaCasaFormLabels {

    protected String cuantasPersonas; //1
    protected String cuantasMujeres; //2
    protected String edadMujer1;
    protected String edadMujer2;
    protected String edadMujer3;
    protected String edadMujer4;
    protected String edadMujer5;
    protected String edadMujer6;
    protected String edadMujer7;
    protected String edadMujer8;
    protected String edadMujer9;
    protected String edadMujer10;
    protected String cuantosHombres; //3
    protected String edadHombre1;
    protected String edadHombre2;
    protected String edadHombre3;
    protected String edadHombre4;
    protected String edadHombre5;
    protected String edadHombre6;
    protected String edadHombre7;
    protected String edadHombre8;
    protected String edadHombre9;
    protected String edadHombre10;
    protected String cuantoCuartos; //4
    protected String cuartosDormir; //5
    protected String problemaAgua; //6
    protected String horasSinAgua; //6.a
    protected String frecuenciaSeVaAgua; //6.b
    protected String tienePozo; //7
    protected String tieneMedidorAgua; //8
    protected String tieneTanque; //10
    protected String llaveAgua; //11
    protected String llaveCompartida; //11.1
    protected String almacenaAgua; //12
    protected String almacenaEnBarriles; //12.a
    protected String almacenaEnTanques; //12.a
    protected String almacenaEnPilas; //12.a
    protected String almacenaOtrosRecipientes; //12.a
    protected String otrosRecipientes; //12.a
    protected String numBarriles; //12.b
    protected String numTanques; //12.b
    protected String numPilas; //12.b
    protected String numOtrosRecipientes; //12.b
    protected String barrilesTapados; //12.c
    protected String tanquesTapados; //12.c
    protected String pilasTapadas; //12.c
    protected String otrosRecipientesTapados; //12.c
    protected String cambiadoCasa; //13
    protected String remodeladoCasa; //14
    protected String ubicacionLavandero; //15
    protected String materialParedes; //16
    protected String materialPiso; //17
    protected String materialTecho; //18
    protected String otroMaterialParedes;//16.1
    protected String otroMaterialPiso;//17.1
    protected String otroMaterialTecho;//18.1
    protected String casaPropia; //19
    protected String tieneAbanico; //20.1
    protected String tieneTelevisor;//20.2
    protected String tieneRefrigerador;//20.3
    protected String tieneAireAcondicionado;//20.4
    //protected String tieneLavadora; //20.5
    protected String numAbanicos; //20.1.1
    protected String numTelevisores; //20.2.1
    protected String numRefrigeradores; //20.3.1
    protected String numAireAcondicionado; //20.4.1
    protected String aireAcondicionadoFuncionando; //20.4.2
    protected String lavadoraFuncionando; //20.5.1
    protected String tieneMuro; //21
    protected String tieneInternet; //22
    protected String tieneInodoro; //23
    protected String tieneServicioEnergia; //24
    protected String tieneMedidorEnergia; //24.a
    protected String casaDosPisos; //25
    protected String tieneOtrosServicios; //26
    protected String tieneVehiculo; //27
    protected String tieneMoto; //28.1
    protected String tieneCarro; //28.1
    protected String tieneMicrobus; //28.1
    protected String tieneCamioneta; //28.1
    protected String tieneCamion; //28.1
    protected String tieneOtroMedioTransAuto;//28.1
    protected String otroMedioTransAuto;//28.1.2
    protected String anioFabMoto; //28.2
    protected String anioFabCarro; //28.2
    protected String anioFabMicrobus; //28.2
    protected String anioFabCamioneta; //28.2
    protected String anioFabCamion; //28.2
    protected String anioFabOtroMedioTrans; //28.2
    protected String marcaMoto; //28.3
    protected String marcaCarro; //28.3
    protected String marcaMicrobus; //28.3
    protected String marcaCamioneta; //28.3
    protected String marcaCamion; //28.3
    protected String marcaOtroMedioTrans; //28.3
    protected String tipoCocina; //29
    protected String cuantosQuemadores; //29.a
    protected String tieneHorno; //30
    protected String cocinaConLenia;//31
    protected String ubicacionCocinaLenia;//31.1
    protected String periodicidadCocinaLenia;//31.a
    protected String numDiarioCocinaLenia;//31.a.1
    protected String numSemanalCocinaLenia;//31.a.2
    protected String numQuincenalCocinaLenia;//31.a.3
    protected String numMensualCocinaLenia;//31.a.4
    protected String tieneAnimales; //32
    protected String tieneGallinas; //33
    protected String tienePatos; //33
    protected String tienePerros; //33
    protected String tieneGatos; //33
    protected String tieneCerdos; //33
    protected String tieneVacas; //33
    protected String tieneCabras; //33
    protected String tieneCaballos; //33
    protected String tienePelibueys; //33
    protected String tieneAves; //33
    protected String tieneOtrosAnimales; //33
    protected String cantidadGallinas; //33.1
    protected String cantidadPatos;
    protected String cantidadPerros;
    protected String cantidadGatos;
    protected String cantidadCerdos;
    protected String cantidadVacas;
    protected String cantidadCabras;
    protected String cantidadCaballos;
    protected String cantidadPelibueys;
    protected String cantidadAves;
    protected String cantidadOtrosAnimales; //33.1
    protected String gallinasDentroCasa;//34
    protected String patosDentroCasa;
    protected String perrosDentroCasa;
    protected String gatosDentroCasa;
    protected String cerdosDentroCasa;
    protected String vacasDentroCasa;
    protected String cabrasDentroCasa;
    protected String caballosDentroCasa;
    protected String pelibueysDentroCasa;
    protected String avesDentroCasa;
    protected String otrosAnimalesDentroCasa; //34
    protected String personaFumaDentroCasa; //35
    protected String tieneRecoleccionBasura; //36
    protected String cuantasVecesRecBasura; //37
    protected String dondePasaRecBasura; //38

    protected String edadMujer1Hint;
    protected String edadMujer2Hint;
    protected String edadMujer3Hint;
    protected String edadMujer4Hint;
    protected String edadMujer5Hint;
    protected String edadMujer6Hint;
    protected String edadMujer7Hint;
    protected String edadMujer8Hint;
    protected String edadMujer9Hint;
    protected String edadMujer10Hint;
    protected String edadHombre1Hint;
    protected String edadHombre2Hint;
    protected String edadHombre3Hint;
    protected String edadHombre4Hint;
    protected String edadHombre5Hint;
    protected String edadHombre6Hint;
    protected String edadHombre7Hint;
    protected String edadHombre8Hint;
    protected String edadHombre9Hint;
    protected String edadHombre10Hint;
    protected String horasSinAguaHint;
    protected String aireAcondicionadoFuncionandoHint;
    protected String lavadoraFuncionandoHint;
    protected String almacenaEnBarrilesHint;
    protected String almacenaEnTanquesHint;
    protected String almacenaEnPilasHint;
    protected String almacenaOtrosRecipientesHint;
    protected String otrosRecipientesHint;
    protected String numBarrilesHint;
    protected String numTanquesHint;
    protected String numPilasHint;
    protected String numOtrosRecipientesHint;
    protected String barrilesTapadosHint;
    protected String tanquesTapadosHint;
    protected String pilasTapadasHint;
    protected String otrosRecipientesTapadosHint;
    protected String llaveCompartidaHint;//7.1
    protected String ubicacionCocinaLeniaHint;
    protected String numDiarioCocinaLeniaHint;
    protected String numSemanalCocinaLeniaHint;
    protected String numQuincenalCocinaLeniaHint;
    protected String numMensualCocinaLeniaHint;
    protected String frecuenciaSeVaAguaHint;

    protected String finTamizajeLabel;

    public EncuestaCasaFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();
        cuantasPersonas = res.getString(R.string.cuantasPersonas);
        cuantasMujeres = res.getString(R.string.cuantasMujeres);
        edadMujer1 = res.getString(R.string.edadMujer1);
        edadMujer2 = res.getString(R.string.edadMujer2);
        edadMujer3 = res.getString(R.string.edadMujer3);
        edadMujer4 = res.getString(R.string.edadMujer4);
        edadMujer5 = res.getString(R.string.edadMujer5);
        edadMujer6 = res.getString(R.string.edadMujer6);
        edadMujer7 = res.getString(R.string.edadMujer7);
        edadMujer8 = res.getString(R.string.edadMujer8);
        edadMujer9 = res.getString(R.string.edadMujer9);
        edadMujer10 = res.getString(R.string.edadMujer10);
        cuantosHombres = res.getString(R.string.cuantosHombres);
        edadHombre1 = res.getString(R.string.edadHombre1);
        edadHombre2 = res.getString(R.string.edadHombre2);
        edadHombre3 = res.getString(R.string.edadHombre3);
        edadHombre4 = res.getString(R.string.edadHombre4);
        edadHombre5 = res.getString(R.string.edadHombre5);
        edadHombre6 = res.getString(R.string.edadHombre6);
        edadHombre7 = res.getString(R.string.edadHombre7);
        edadHombre8 = res.getString(R.string.edadHombre8);
        edadHombre9 = res.getString(R.string.edadHombre9);
        edadHombre10 = res.getString(R.string.edadHombre10);
        cuantoCuartos = res.getString(R.string.cuantoCuartos);
        cuartosDormir = res.getString(R.string.cuartosDormir);
        problemaAgua = res.getString(R.string.problemaAgua);
        horasSinAgua = res.getString(R.string.horasSinAgua);
        frecuenciaSeVaAgua = res.getString(R.string.frecuenciaSeVaAgua);
        tienePozo = res.getString(R.string.tienePozo);
        tieneMedidorAgua = res.getString(R.string.tieneMedidorAgua);
        tieneTanque = res.getString(R.string.tieneTanque);
        llaveAgua = res.getString(R.string.llaveAgua);
        llaveCompartida = res.getString(R.string.llaveCompartida);
        almacenaAgua = res.getString(R.string.almacenaAgua);
        almacenaEnBarriles = res.getString(R.string.almacenaEnBarriles);
        almacenaEnTanques = res.getString(R.string.almacenaEnTanques);
        almacenaEnPilas = res.getString(R.string.almacenaEnPilas);
        almacenaOtrosRecipientes = res.getString(R.string.almacenaOtrosRecipientes);
        otrosRecipientes = res.getString(R.string.otrosRecipientes);
        numBarriles = res.getString(R.string.numBarriles);
        numTanques = res.getString(R.string.numTanques);
        numPilas = res.getString(R.string.numPilas);
        numOtrosRecipientes = res.getString(R.string.numOtrosRecipientes);
        barrilesTapados = res.getString(R.string.barrilesTapados);
        tanquesTapados = res.getString(R.string.tanquesTapados);
        pilasTapadas = res.getString(R.string.pilasTapadas);
        otrosRecipientesTapados = res.getString(R.string.otrosRecipientesTapados);
        cambiadoCasa = res.getString(R.string.cambiadoCasa);
        remodeladoCasa = res.getString(R.string.remodeladoCasa);
        ubicacionLavandero = res.getString(R.string.ubicacionLavandero);
        materialParedes = res.getString(R.string.materialParedes);
        materialPiso = res.getString(R.string.materialPiso);
        materialTecho = res.getString(R.string.materialTecho);
        otroMaterialParedes = res.getString(R.string.otroMaterialParedes);
        otroMaterialPiso = res.getString(R.string.otroMaterialPiso);
        otroMaterialTecho = res.getString(R.string.otroMaterialTecho);
        casaPropia = res.getString(R.string.casaPropia);
        tieneAbanico = res.getString(R.string.tieneAbanico);
        tieneTelevisor = res.getString(R.string.tieneTelevisor);
        tieneRefrigerador = res.getString(R.string.tieneRefrigerador);
        tieneAireAcondicionado = res.getString(R.string.tieneAireAcondicionado);
        //tieneLavadora = res.getString(R.string.tieneLavadora);
        numAbanicos = res.getString(R.string.numAbanicos);
        numTelevisores = res.getString(R.string.numTelevisores);
        numRefrigeradores = res.getString(R.string.numRefrigeradores);
        numAireAcondicionado = res.getString(R.string.numAireAcondicionado);
        aireAcondicionadoFuncionando = res.getString(R.string.aireAcondicionadoFuncionando);
        lavadoraFuncionando = res.getString(R.string.lavadoraFuncionando);
        tieneMuro = res.getString(R.string.tieneMuro);
        tieneInternet = res.getString(R.string.tieneInternet);
        tieneInodoro = res.getString(R.string.tieneInodoro);
        tieneServicioEnergia = res.getString(R.string.tieneServicioEnergia);
        tieneMedidorEnergia = res.getString(R.string.tieneMedidorEnergia);
        casaDosPisos = res.getString(R.string.casaDosPisos);
        tieneOtrosServicios = res.getString(R.string.tieneOtrosServicios);
        tieneVehiculo = res.getString(R.string.tieneVehiculo);
        tieneMoto = res.getString(R.string.tieneMoto);
        tieneCarro = res.getString(R.string.tieneCarro);
        tieneMicrobus = res.getString(R.string.tieneMicrobus);
        tieneCamioneta = res.getString(R.string.tieneCamioneta);
        tieneCamion = res.getString(R.string.tieneCamion);
        tieneOtroMedioTransAuto = res.getString(R.string.tieneOtroMedioTransAuto);
        otroMedioTransAuto = res.getString(R.string.otroMedioTransAuto);
        anioFabMoto = res.getString(R.string.anioFabMoto);
        anioFabCarro = res.getString(R.string.anioFabCarro);
        anioFabMicrobus = res.getString(R.string.anioFabMicrobus);
        anioFabCamioneta = res.getString(R.string.anioFabCamioneta);
        anioFabCamion = res.getString(R.string.anioFabCamion);
        anioFabOtroMedioTrans = res.getString(R.string.anioFabOtroMedioTrans);
        marcaMoto = res.getString(R.string.marcaMoto);
        marcaCarro = res.getString(R.string.marcaCarro);
        marcaMicrobus = res.getString(R.string.marcaMicrobus);
        marcaCamioneta = res.getString(R.string.marcaCamioneta);
        marcaCamion = res.getString(R.string.marcaCamion);
        marcaOtroMedioTrans = res.getString(R.string.marcaOtroMedioTrans);
        tipoCocina = res.getString(R.string.tipoCocina);
        cuantosQuemadores = res.getString(R.string.cuantosQuemadores);
        tieneHorno = res.getString(R.string.tieneHorno);
        cocinaConLenia = res.getString(R.string.cocinaConLenia);
        ubicacionCocinaLenia = res.getString(R.string.ubicacionCocinaLenia);
        periodicidadCocinaLenia = res.getString(R.string.periodicidadCocinaLenia);
        numDiarioCocinaLenia = res.getString(R.string.numDiarioCocinaLenia);
        numSemanalCocinaLenia = res.getString(R.string.numSemanalCocinaLenia);
        numQuincenalCocinaLenia = res.getString(R.string.numQuincenalCocinaLenia);
        numMensualCocinaLenia = res.getString(R.string.numMensualCocinaLenia);
        tieneAnimales = res.getString(R.string.tieneAnimales);
        tieneGallinas = res.getString(R.string.tieneGallinas);
        tienePatos = res.getString(R.string.tienePatos);
        tienePerros = res.getString(R.string.tienePerros);
        tieneGatos = res.getString(R.string.tieneGatos);
        tieneCerdos = res.getString(R.string.tieneCerdos);
        tieneVacas = res.getString(R.string.tieneVacas);
        tieneCabras = res.getString(R.string.tieneCabras);
        tieneCaballos = res.getString(R.string.tieneCaballos);
        tienePelibueys = res.getString(R.string.tienePelibueys);
        tieneAves = res.getString(R.string.tieneAves);
        tieneOtrosAnimales = res.getString(R.string.tieneOtrosAnimales);
        cantidadGallinas = res.getString(R.string.cantidadGallinas);
        cantidadPatos = res.getString(R.string.cantidadPatos);
        cantidadPerros = res.getString(R.string.cantidadPerros);
        cantidadGatos = res.getString(R.string.cantidadGatos);
        cantidadCerdos = res.getString(R.string.cantidadCerdos);
        cantidadVacas = res.getString(R.string.cantidadVacas);
        cantidadCabras = res.getString(R.string.cantidadCabras);
        cantidadCaballos = res.getString(R.string.cantidadCaballos);
        cantidadPelibueys = res.getString(R.string.cantidadPelibueys);
        cantidadAves = res.getString(R.string.cantidadAves);
        cantidadOtrosAnimales = res.getString(R.string.cantidadOtrosAnimales);
        gallinasDentroCasa = res.getString(R.string.gallinasDentroCasa);
        patosDentroCasa = res.getString(R.string.patosDentroCasa);
        perrosDentroCasa = res.getString(R.string.perrosDentroCasa);
        gatosDentroCasa = res.getString(R.string.gatosDentroCasa);
        cerdosDentroCasa = res.getString(R.string.cerdosDentroCasa);
        vacasDentroCasa = res.getString(R.string.vacasDentroCasa);
        cabrasDentroCasa = res.getString(R.string.cabrasDentroCasa);
        caballosDentroCasa = res.getString(R.string.caballosDentroCasa);
        pelibueysDentroCasa = res.getString(R.string.pelibueysDentroCasa);
        avesDentroCasa = res.getString(R.string.avesDentroCasa);
        otrosAnimalesDentroCasa = res.getString(R.string.otrosAnimalesDentroCasa);
        personaFumaDentroCasa = res.getString(R.string.personaFumaDentroCasa);
        tieneRecoleccionBasura = res.getString(R.string.tieneRecoleccionBasura);
        cuantasVecesRecBasura = res.getString(R.string.cuantasVecesRecBasura);
        dondePasaRecBasura = res.getString(R.string.dondePasaRecBasura);

        edadMujer1Hint = res.getString(R.string.edadMujer1Hint);
        edadMujer2Hint = res.getString(R.string.edadMujer2Hint);
        edadMujer3Hint = res.getString(R.string.edadMujer3Hint);
        edadMujer4Hint = res.getString(R.string.edadMujer4Hint);
        edadMujer5Hint = res.getString(R.string.edadMujer5Hint);
        edadMujer6Hint = res.getString(R.string.edadMujer6Hint);
        edadMujer7Hint = res.getString(R.string.edadMujer7Hint);
        edadMujer8Hint = res.getString(R.string.edadMujer8Hint);
        edadMujer9Hint = res.getString(R.string.edadMujer9Hint);
        edadMujer10Hint = res.getString(R.string.edadMujer10Hint);

        edadHombre1Hint = res.getString(R.string.edadHombre1Hint);
        edadHombre2Hint = res.getString(R.string.edadHombre2Hint);
        edadHombre3Hint = res.getString(R.string.edadHombre3Hint);
        edadHombre4Hint = res.getString(R.string.edadHombre4Hint);
        edadHombre5Hint = res.getString(R.string.edadHombre5Hint);
        edadHombre6Hint = res.getString(R.string.edadHombre6Hint);
        edadHombre7Hint = res.getString(R.string.edadHombre7Hint);
        edadHombre8Hint = res.getString(R.string.edadHombre8Hint);
        edadHombre9Hint = res.getString(R.string.edadHombre9Hint);
        edadHombre10Hint = res.getString(R.string.edadHombre10Hint);

        horasSinAguaHint = res.getString(R.string.horasSinAguaHint);
        almacenaEnBarrilesHint = res.getString(R.string.almacenaEnBarrilesHint);
        almacenaEnTanquesHint = res.getString(R.string.almacenaEnTanquesHint);
        almacenaEnPilasHint = res.getString(R.string.almacenaEnPilasHint);
        almacenaOtrosRecipientesHint = res.getString(R.string.almacenaOtrosRecipientesHint);
        otrosRecipientesHint = res.getString(R.string.otrosRecipientesHint);
        numBarrilesHint = res.getString(R.string.numBarrilesHint);
        numTanquesHint = res.getString(R.string.numTanquesHint);
        numPilasHint = res.getString(R.string.numPilasHint);
        numOtrosRecipientesHint = res.getString(R.string.numOtrosRecipientesHint);
        barrilesTapadosHint = res.getString(R.string.barrilesTapadosHint);
        tanquesTapadosHint = res.getString(R.string.tanquesTapadosHint);
        pilasTapadasHint = res.getString(R.string.pilasTapadasHint);
        otrosRecipientesTapadosHint = res.getString(R.string.otrosRecipientesTapadosHint);
        aireAcondicionadoFuncionandoHint = res.getString(R.string.aireAcondicionadoFuncionandoHint);
        lavadoraFuncionandoHint = res.getString(R.string.lavadoraFuncionandoHint);
        llaveCompartidaHint = res.getString(R.string.llaveCompartidaHint);//7.1
        ubicacionCocinaLeniaHint = res.getString(R.string.ubicacionCocinaLeniaHint);
        numDiarioCocinaLeniaHint = res.getString(R.string.numDiarioCocinaLeniaHint);
        numSemanalCocinaLeniaHint = res.getString(R.string.numSemanalCocinaLeniaHint);
        numQuincenalCocinaLeniaHint = res.getString(R.string.numQuincenalCocinaLeniaHint);
        numMensualCocinaLeniaHint = res.getString(R.string.numMensualCocinaLeniaHint);
        frecuenciaSeVaAguaHint = res.getString(R.string.frecuenciaSeVaAguaHint);

        finTamizajeLabel = res.getString(R.string.finTamizajeLabel);
    }

    public String getCuantasPersonas() {
        return cuantasPersonas;
    }

    public String getCuantasMujeres() {
        return cuantasMujeres;
    }

    public String getEdadMujer1() {
        return edadMujer1;
    }

    public String getEdadMujer2() {
        return edadMujer2;
    }

    public String getEdadMujer3() {
        return edadMujer3;
    }

    public String getEdadMujer4() {
        return edadMujer4;
    }

    public String getEdadMujer5() {
        return edadMujer5;
    }

    public String getEdadMujer6() {
        return edadMujer6;
    }

    public String getEdadMujer7() {
        return edadMujer7;
    }

    public String getEdadMujer8() {
        return edadMujer8;
    }

    public String getEdadMujer9() {
        return edadMujer9;
    }

    public String getEdadMujer10() {
        return edadMujer10;
    }

    public String getCuantosHombres() {
        return cuantosHombres;
    }

    public String getEdadHombre1() {
        return edadHombre1;
    }

    public String getEdadHombre2() {
        return edadHombre2;
    }

    public String getEdadHombre3() {
        return edadHombre3;
    }

    public String getEdadHombre4() {
        return edadHombre4;
    }

    public String getEdadHombre5() {
        return edadHombre5;
    }

    public String getEdadHombre6() {
        return edadHombre6;
    }

    public String getEdadHombre7() {
        return edadHombre7;
    }

    public String getEdadHombre8() {
        return edadHombre8;
    }

    public String getEdadHombre9() {
        return edadHombre9;
    }

    public String getEdadHombre10() {
        return edadHombre10;
    }

    public String getCuantoCuartos() {
        return cuantoCuartos;
    }

    public String getCuartosDormir() {
        return cuartosDormir;
    }

    public String getProblemaAgua() {
        return problemaAgua;
    }

    public String getHorasSinAgua() {
        return horasSinAgua;
    }

    public String getFrecuenciaSeVaAgua() {
        return frecuenciaSeVaAgua;
    }

    public String getTienePozo() {
        return tienePozo;
    }

    public String getTieneMedidorAgua() {
        return tieneMedidorAgua;
    }

    public String getTieneTanque() {
        return tieneTanque;
    }

    public String getLlaveAgua() {
        return llaveAgua;
    }

    public String getLlaveCompartida() {
        return llaveCompartida;
    }

    public String getAlmacenaAgua() {
        return almacenaAgua;
    }

    public String getAlmacenaEnBarriles() {
        return almacenaEnBarriles;
    }

    public String getAlmacenaEnTanques() {
        return almacenaEnTanques;
    }

    public String getAlmacenaEnPilas() {
        return almacenaEnPilas;
    }

    public String getAlmacenaOtrosRecipientes() {
        return almacenaOtrosRecipientes;
    }

    public String getOtrosRecipientes() {
        return otrosRecipientes;
    }

    public String getNumBarriles() {
        return numBarriles;
    }

    public String getNumTanques() {
        return numTanques;
    }

    public String getNumPilas() {
        return numPilas;
    }

    public String getNumOtrosRecipientes() {
        return numOtrosRecipientes;
    }

    public String getBarrilesTapados() {
        return barrilesTapados;
    }

    public String getTanquesTapados() {
        return tanquesTapados;
    }

    public String getPilasTapadas() {
        return pilasTapadas;
    }

    public String getOtrosRecipientesTapados() {
        return otrosRecipientesTapados;
    }

    public String getCambiadoCasa() {
        return cambiadoCasa;
    }

    public String getRemodeladoCasa() {
        return remodeladoCasa;
    }

    public String getUbicacionLavandero() {
        return ubicacionLavandero;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public String getOtroMaterialParedes() {
        return otroMaterialParedes;
    }

    public String getOtroMaterialPiso() {
        return otroMaterialPiso;
    }

    public String getOtroMaterialTecho() {
        return otroMaterialTecho;
    }

    public String getCasaPropia() {
        return casaPropia;
    }

    public String getTieneAbanico() {
        return tieneAbanico;
    }

    public String getTieneTelevisor() {
        return tieneTelevisor;
    }

    public String getTieneRefrigerador() {
        return tieneRefrigerador;
    }

    public String getTieneAireAcondicionado() {
        return tieneAireAcondicionado;
    }

    public String getNumAbanicos() {
        return numAbanicos;
    }

    public String getNumTelevisores() {
        return numTelevisores;
    }

    public String getNumRefrigeradores() {
        return numRefrigeradores;
    }

    public String getNumAireAcondicionado() {
        return numAireAcondicionado;
    }

    public String getAireAcondicionadoFuncionando() {
        return aireAcondicionadoFuncionando;
    }

    public String getLavadoraFuncionando() {
        return lavadoraFuncionando;
    }

    public String getTieneMuro() {
        return tieneMuro;
    }

    public String getTieneInternet() {
        return tieneInternet;
    }

    public String getTieneInodoro() {
        return tieneInodoro;
    }

    public String getTieneServicioEnergia() {
        return tieneServicioEnergia;
    }

    public String getTieneMedidorEnergia() {
        return tieneMedidorEnergia;
    }

    public String getCasaDosPisos() {
        return casaDosPisos;
    }

    public String getTieneOtrosServicios() {
        return tieneOtrosServicios;
    }

    public String getTieneVehiculo() {
        return tieneVehiculo;
    }

    public String getTieneMoto() {
        return tieneMoto;
    }

    public String getTieneCarro() {
        return tieneCarro;
    }

    public String getTieneMicrobus() {
        return tieneMicrobus;
    }

    public String getTieneCamioneta() {
        return tieneCamioneta;
    }

    public String getTieneCamion() {
        return tieneCamion;
    }

    public String getTieneOtroMedioTransAuto() {
        return tieneOtroMedioTransAuto;
    }

    public String getOtroMedioTransAuto() {
        return otroMedioTransAuto;
    }

    public String getAnioFabMoto() {
        return anioFabMoto;
    }

    public String getAnioFabCarro() {
        return anioFabCarro;
    }

    public String getAnioFabMicrobus() {
        return anioFabMicrobus;
    }

    public String getAnioFabCamioneta() {
        return anioFabCamioneta;
    }

    public String getAnioFabCamion() {
        return anioFabCamion;
    }

    public String getAnioFabOtroMedioTrans() {
        return anioFabOtroMedioTrans;
    }

    public String getMarcaMoto() {
        return marcaMoto;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public String getMarcaMicrobus() {
        return marcaMicrobus;
    }

    public String getMarcaCamioneta() {
        return marcaCamioneta;
    }

    public String getMarcaCamion() {
        return marcaCamion;
    }

    public String getMarcaOtroMedioTrans() {
        return marcaOtroMedioTrans;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public String getCuantosQuemadores() {
        return cuantosQuemadores;
    }

    public String getTieneHorno() {
        return tieneHorno;
    }

    public String getCocinaConLenia() {
        return cocinaConLenia;
    }

    public String getUbicacionCocinaLenia() {
        return ubicacionCocinaLenia;
    }

    public String getPeriodicidadCocinaLenia() {
        return periodicidadCocinaLenia;
    }

    public String getNumDiarioCocinaLenia() {
        return numDiarioCocinaLenia;
    }

    public String getNumSemanalCocinaLenia() {
        return numSemanalCocinaLenia;
    }

    public String getNumQuincenalCocinaLenia() {
        return numQuincenalCocinaLenia;
    }

    public String getNumMensualCocinaLenia() {
        return numMensualCocinaLenia;
    }

    public String getTieneAnimales() {
        return tieneAnimales;
    }

    public String getTieneGallinas() {
        return tieneGallinas;
    }

    public String getTienePatos() {
        return tienePatos;
    }

    public String getTienePerros() {
        return tienePerros;
    }

    public String getTieneGatos() {
        return tieneGatos;
    }

    public String getTieneCerdos() {
        return tieneCerdos;
    }

    public String getTieneVacas() {
        return tieneVacas;
    }

    public String getTieneCabras() {
        return tieneCabras;
    }

    public String getTieneCaballos() {
        return tieneCaballos;
    }

    public String getTienePelibueys() {
        return tienePelibueys;
    }

    public String getTieneAves() {
        return tieneAves;
    }

    public String getTieneOtrosAnimales() {
        return tieneOtrosAnimales;
    }

    public String getCantidadGallinas() {
        return cantidadGallinas;
    }

    public String getCantidadPatos() {
        return cantidadPatos;
    }

    public String getCantidadPerros() {
        return cantidadPerros;
    }

    public String getCantidadGatos() {
        return cantidadGatos;
    }

    public String getCantidadCerdos() {
        return cantidadCerdos;
    }

    public String getCantidadVacas() {
        return cantidadVacas;
    }

    public String getCantidadCabras() {
        return cantidadCabras;
    }

    public String getCantidadCaballos() {
        return cantidadCaballos;
    }

    public String getCantidadPelibueys() {
        return cantidadPelibueys;
    }

    public String getCantidadAves() {
        return cantidadAves;
    }

    public String getCantidadOtrosAnimales() {
        return cantidadOtrosAnimales;
    }

    public String getGallinasDentroCasa() {
        return gallinasDentroCasa;
    }

    public String getPatosDentroCasa() {
        return patosDentroCasa;
    }

    public String getPerrosDentroCasa() {
        return perrosDentroCasa;
    }

    public String getGatosDentroCasa() {
        return gatosDentroCasa;
    }

    public String getVacasDentroCasa() {
        return vacasDentroCasa;
    }

    public String getCabrasDentroCasa() {
        return cabrasDentroCasa;
    }

    public String getCaballosDentroCasa() {
        return caballosDentroCasa;
    }

    public String getPelibueysDentroCasa() {
        return pelibueysDentroCasa;
    }

    public String getAvesDentroCasa() {
        return avesDentroCasa;
    }

    public String getOtrosAnimalesDentroCasa() {
        return otrosAnimalesDentroCasa;
    }

    public String getCerdosDentroCasa() {
        return cerdosDentroCasa;
    }

    public String getPersonaFumaDentroCasa() {
        return personaFumaDentroCasa;
    }

    public String getTieneRecoleccionBasura() {
        return tieneRecoleccionBasura;
    }

    public String getCuantasVecesRecBasura() {
        return cuantasVecesRecBasura;
    }

    public String getDondePasaRecBasura() {
        return dondePasaRecBasura;
    }

    public String getHorasSinAguaHint() {
        return horasSinAguaHint;
    }

    public String getAireAcondicionadoFuncionandoHint() {
        return aireAcondicionadoFuncionandoHint;
    }

    public String getLavadoraFuncionandoHint() {
        return lavadoraFuncionandoHint;
    }

    public String getAlmacenaEnBarrilesHint() {
        return almacenaEnBarrilesHint;
    }

    public String getAlmacenaEnTanquesHint() {
        return almacenaEnTanquesHint;
    }

    public String getAlmacenaEnPilasHint() {
        return almacenaEnPilasHint;
    }

    public String getAlmacenaOtrosRecipientesHint() {
        return almacenaOtrosRecipientesHint;
    }

    public String getOtrosRecipientesHint() {
        return otrosRecipientesHint;
    }

    public String getNumBarrilesHint() {
        return numBarrilesHint;
    }

    public String getNumTanquesHint() {
        return numTanquesHint;
    }

    public String getNumPilasHint() {
        return numPilasHint;
    }

    public String getNumOtrosRecipientesHint() {
        return numOtrosRecipientesHint;
    }

    public String getBarrilesTapadosHint() {
        return barrilesTapadosHint;
    }

    public String getTanquesTapadosHint() {
        return tanquesTapadosHint;
    }

    public String getPilasTapadasHint() {
        return pilasTapadasHint;
    }

    public String getOtrosRecipientesTapadosHint() {
        return otrosRecipientesTapadosHint;
    }

    public String getLlaveCompartidaHint() {
        return llaveCompartidaHint;
    }

    public String getUbicacionCocinaLeniaHint() {
        return ubicacionCocinaLeniaHint;
    }

    public String getNumDiarioCocinaLeniaHint() {
        return numDiarioCocinaLeniaHint;
    }

    public String getNumSemanalCocinaLeniaHint() {
        return numSemanalCocinaLeniaHint;
    }

    public String getNumQuincenalCocinaLeniaHint() {
        return numQuincenalCocinaLeniaHint;
    }

    public String getNumMensualCocinaLeniaHint() {
        return numMensualCocinaLeniaHint;
    }

    public String getFrecuenciaSeVaAguaHint() {
        return frecuenciaSeVaAguaHint;
    }

    public String getEdadMujer1Hint() {
        return edadMujer1Hint;
    }

    public String getEdadMujer2Hint() {
        return edadMujer2Hint;
    }

    public String getEdadMujer3Hint() {
        return edadMujer3Hint;
    }

    public String getEdadMujer4Hint() {
        return edadMujer4Hint;
    }

    public String getEdadMujer5Hint() {
        return edadMujer5Hint;
    }

    public String getEdadMujer6Hint() {
        return edadMujer6Hint;
    }

    public String getEdadMujer7Hint() {
        return edadMujer7Hint;
    }

    public String getEdadMujer8Hint() {
        return edadMujer8Hint;
    }

    public String getEdadMujer9Hint() {
        return edadMujer9Hint;
    }

    public String getEdadMujer10Hint() {
        return edadMujer10Hint;
    }

    public String getEdadHombre1Hint() {
        return edadHombre1Hint;
    }

    public String getEdadHombre2Hint() {
        return edadHombre2Hint;
    }

    public String getEdadHombre3Hint() {
        return edadHombre3Hint;
    }

    public String getEdadHombre4Hint() {
        return edadHombre4Hint;
    }

    public String getEdadHombre5Hint() {
        return edadHombre5Hint;
    }

    public String getEdadHombre6Hint() {
        return edadHombre6Hint;
    }

    public String getEdadHombre7Hint() {
        return edadHombre7Hint;
    }

    public String getEdadHombre8Hint() {
        return edadHombre8Hint;
    }

    public String getEdadHombre9Hint() {
        return edadHombre9Hint;
    }

    public String getEdadHombre10Hint() {
        return edadHombre10Hint;
    }

    public String getFinTamizajeLabel() {
        return finTamizajeLabel;
    }
}
