package ni.org.ics.a2cares.app.database.constants;

/**
 * Created by Miguel Salinas on 7/6/2021.
 */
public class EncuestasDBConstants {
    //tabla EncuestaPesoTalla
    public static final String ENCUESTA_PESOTALLA_TABLE = "encuestas_pesotalla";

    //campos tabla EncuestaPesoTalla
    public static final String codigo= "codigo";
    public static final String participante= "participante";
    public static final String tomoMedidaSn = "tomoMedidaSn";
    public static final String razonNoTomoMedidas = "razonNoTomoMedidas";
    public static final String peso1 = "peso1";
    public static final String peso2 = "peso2";
    public static final String peso3 = "peso3";
    public static final String talla1 = "talla1";
    public static final String talla2 = "talla2";
    public static final String talla3 = "talla3";
    public static final String imc1 = "imc1";
    public static final String imc2 = "imc2";
    public static final String imc3 = "imc3";
    public static final String difPeso = "difPeso";
    public static final String difTalla = "difTalla";

    //crear tabla EncuestaPesoTalla
    public static final String CREATE_ENCUESTA_PESOTALLA_TABLE = "create table if not exists "
            + ENCUESTA_PESOTALLA_TABLE + " ("
            + codigo + " text not null, "
            + participante + " text not null, "
            + tomoMedidaSn + " text, "
            + razonNoTomoMedidas + " text, "
            + peso1 + " real, "
            + peso2 + " real, "
            + peso3 + " real, "
            + talla1 + " real, "
            + talla2 + " real, "
            + talla3 + " real, "
            + imc1 + " real, "
            + imc2 + " real, "
            + imc3 + " real, "
            + difPeso + " real, "
            + difTalla + " real, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";


    //tabla encuesta de casas
    public static final String ENCUESTA_CASA_TABLE = "encuestas_casa";

    //campos encuesta de casa
    public static final String casa = "casa";
    public static final String cuantasPersonas = "cuantasPersonas";
    public static final String cuantasMujeres = "cuantasMujeres";
    public static final String edadMujer1 = "edadMujer1";
    public static final String edadMujer2 = "edadMujer2";
    public static final String edadMujer3 = "edadMujer3";
    public static final String edadMujer4 = "edadMujer4";
    public static final String edadMujer5 = "edadMujer5";
    public static final String edadMujer6 = "edadMujer6";
    public static final String edadMujer7 = "edadMujer7";
    public static final String edadMujer8 = "edadMujer8";
    public static final String edadMujer9 = "edadMujer9";
    public static final String edadMujer10 = "edadMujer10";
    public static final String cuantosHombres = "cuantosHombres";
    public static final String edadHombre1 = "edadHombre1";
    public static final String edadHombre2 = "edadHombre2";
    public static final String edadHombre3 = "edadHombre3";
    public static final String edadHombre4 = "edadHombre4";
    public static final String edadHombre5 = "edadHombre5";
    public static final String edadHombre6 = "edadHombre6";
    public static final String edadHombre7 = "edadHombre7";
    public static final String edadHombre8 = "edadHombre8";
    public static final String edadHombre9 = "edadHombre9";
    public static final String edadHombre10 = "edadHombre10";
    public static final String cantidadCuartos = "cantidadCuartos";
    public static final String cantidadCuartosDormir = "cantidadCuartosDormir";
    public static final String problemaAgua = "problemaAgua";
    public static final String hrsSinServicioAgua = "hrsSinServicioAgua";
    public static final String frecuenciaSeVaAgua = "frecuenciaSeVaAgua";
    public static final String otraFrecuenciaSeVaAgua = "otraFrecuenciaSeVaAgua";
    public static final String tienePozo = "tienePozo";
    public static final String tieneMedidorAgua = "tieneMedidorAgua";
    public static final String tieneTanque = "tieneTanque";
    public static final String ubicacionLlaveAgua = "ubicacionLlaveAgua";
    public static final String llaveCompartida = "llaveCompartida";
    public static final String almacenaAgua = "almacenaAgua";
    public static final String almacenaEnBarriles = "almacenaEnBarriles";
    public static final String almacenaEnTanques = "almacenaEnTanques";
    public static final String almacenaEnPilas = "almacenaEnPilas";
    public static final String almacenaOtrosRecipientes = "almacenaOtrosRecipientes";
    public static final String otrosRecipientes = "otrosRecipientes";
    public static final String numBarriles = "numBarriles";
    public static final String numTanques = "numTanques";
    public static final String numPilas = "numPilas";
    public static final String numOtrosRecipientes = "numOtrosRecipientes";
    public static final String barrilesTapados = "barrilesTapados";
    public static final String tanquesTapados = "tanquesTapados";
    public static final String pilasTapadas = "pilasTapadas";
    public static final String otrosRecipientesTapados = "otrosRecipientesTapados";
    public static final String cepillaPilas = "cepillaPilas";
    public static final String frecCepillaPilas = "frecCepillaPilas";
    public static final String cambiadoCasa = "cambiadoCasa";
    public static final String remodeladoCasa = "remodeladoCasa";
    public static final String ubicacionLavandero = "ubicacionLavandero";
    public static final String materialParedes = "materialParedes";
    public static final String materialPiso = "materialPiso";
    public static final String materialTecho = "materialTecho";
    public static final String otroMaterialParedes = "otroMaterialParedes";
    public static final String otroMaterialPiso = "otroMaterialPiso";
    public static final String otroMaterialTecho = "otroMaterialTecho";
    public static final String casaPropia = "casaPropia";
    public static final String tieneAbanico = "tieneAbanico";
    public static final String tieneTelevisor = "tieneTelevisor";
    public static final String tieneRefrigerador = "tieneRefrigerador";
    public static final String tienAireAcondicionado = "tienAireAcondicionado";
    public static final String numAbanicos = "numAbanicos";
    public static final String numTelevisores = "numTelevisores";
    public static final String numRefrigeradores = "numRefrigeradores";
    public static final String numAireAcondicionado = "numAireAcondicionado";
    public static final String aireAcondicionadoFuncionando = "aireAcondicionadoFuncionando";
    public static final String lavadoraFuncionando = "lavadoraFuncionando";
    public static final String tieneMuro = "tieneMuro";
    public static final String tieneInternet = "tieneInternet";
    public static final String tieneInodoro = "tieneInodoro";
    public static final String cantInodoro = "cantInodoro";
    public static final String cantLetrina = "cantLetrina";
    public static final String tieneServicioEnergia = "tieneServicioEnergia";
    public static final String tieneMedidorEnergia = "tieneMedidorEnergia";
    public static final String casaDosPisos = "casaDosPisos";
    public static final String tieneOtrosServicios = "tieneOtrosServicios";
    public static final String tieneVehiculo = "tieneVehiculo";
    public static final String tieneMoto = "tieneMoto";
    public static final String tieneCarro = "tieneCarro";
    public static final String tieneMicrobus = "tieneMicrobus";
    public static final String tieneCamioneta = "tieneCamioneta";
    public static final String tieneCamion = "tieneCamion";
    public static final String tieneOtroMedioTransAuto = "tieneOtroMedioTransAuto";
    public static final String otroMedioTransAuto = "otroMedioTransAuto";
    public static final String anioFabMoto = "anioFabMoto";
    public static final String anioFabCarro = "anioFabCarro";
    public static final String anioFabMicrobus = "anioFabMicrobus";
    public static final String anioFabCamioneta = "anioFabCamioneta";
    public static final String anioFabCamion = "anioFabCamion";
    public static final String anioFabOtroMedioTrans = "anioFabOtroMedioTrans";
    public static final String marcaMoto = "marcaMoto";
    public static final String marcaCarro = "marcaCarro";
    public static final String marcaMicrobus = "marcaMicrobus";
    public static final String marcaCamioneta = "marcaCamioneta";
    public static final String marcaCamion = "marcaCamion";
    public static final String marcaOtroMedioTrans = "marcaOtroMedioTrans";
    public static final String tipoCocina = "tipoCocina";
    public static final String cuantosQuemadores = "cuantosQuemadores";
    public static final String tieneHorno = "tieneHorno";
    public static final String cocinaConLenia = "cocinaConLenia";
    public static final String ubicacionCocinaLenia = "ubicacionCocinaLenia";
    public static final String periodicidadCocinaLenia = "periodicidadCocinaLenia";
    public static final String numDiarioCocinaLenia = "numDiarioCocinaLenia";
    public static final String numSemanalCocinaLenia = "numSemanalCocinaLenia";
    public static final String numQuincenalCocinaLenia = "numQuincenalCocinaLenia";
    public static final String numMensualCocinaLenia = "numMensualCocinaLenia";
    public static final String tieneAnimales = "tieneAnimales";
    public static final String tieneGallinas = "tieneGallinas";
    public static final String tienePatos = "tienePatos";
    public static final String tienePerros = "tienePerros";
    public static final String tieneGatos = "tieneGatos";
    public static final String tieneCerdos = "tieneCerdos";
    public static final String tieneVacas = "tieneVacas";
    public static final String tieneCabras = "tieneCabras";
    public static final String tieneCaballos = "tieneCaballos";
    public static final String tienePelibueys = "tienePelibueys";
    public static final String tieneAves = "tieneAves";
    public static final String tieneOtrosAnimales = "tieneOtrosAnimales";
    public static final String cantidadGallinas = "cantidadGallinas";
    public static final String cantidadPatos = "cantidadPatos";
    public static final String cantidadPerros = "cantidadPerros";
    public static final String cantidadGatos = "cantidadGatos";
    public static final String cantidadCerdos = "cantidadCerdos";
    public static final String cantidadVacas = "cantidadVacas";
    public static final String cantidadCabras = "cantidadCabras";
    public static final String cantidadCaballos = "cantidadCaballos";
    public static final String cantidadPelibueys = "cantidadPelibueys";
    public static final String cantidadAves = "cantidadAves";
    public static final String cantidadOtrosAnimales = "cantidadOtrosAnimales";
    public static final String gallinasDentroCasa = "gallinasDentroCasa";
    public static final String patosDentroCasa = "patosDentroCasa";
    public static final String perrosDentroCasa = "perrosDentroCasa";
    public static final String gatosDentroCasa = "gatosDentroCasa";
    public static final String cerdosDentroCasa = "cerdosDentroCasa";
    public static final String vacasDentroCasa = "vacasDentroCasa";
    public static final String cabrasDentroCasa = "cabrasDentroCasa";
    public static final String caballosDentroCasa = "caballosDentroCasa";
    public static final String pelibueysDentroCasa = "pelibueysDentroCasa";
    public static final String avesDentroCasa = "avesDentroCasa";
    public static final String otrosAnimalesDentroCasa = "otrosAnimalesDentroCasa";
    public static final String personaFumaDentroCasa = "personaFumaDentroCasa";
    public static final String tieneRecoleccionBasura = "tieneRecoleccionBasura";
    public static final String cuantasVecesRecBasura = "cuantasVecesRecBasura";
    public static final String dondePasaRecBasura = "dondePasaRecBasura";
    public static final String dondePasaRecBasuraOtros = "dondePasaRecBasuraOtros";
    public static final String vistoAnimalesSalvajes = "vistoAnimalesSalvajes";
    public static final String vistoAnimalesSalvajesOtros = "vistoAnimalesSalvajesOtros";

    //crear tabla EncuestaPesoTalla
    public static final String CREATE_ENCUESTA_CASA_TABLE = "create table if not exists "
            + ENCUESTA_CASA_TABLE + " ("
            + codigo + " text not null, "
            + casa + " integer not null, "
            + participante + " text not null, "
            + cuantasPersonas + " integer, "
            + cuantasMujeres + " integer, "
            + edadMujer1 + " integer, "
            + edadMujer2 + " integer, "
            + edadMujer3 + " integer, "
            + edadMujer4 + " integer, "
            + edadMujer5 + " integer, "
            + edadMujer6 + " integer, "
            + edadMujer7 + " integer, "
            + edadMujer8 + " integer, "
            + edadMujer9 + " integer, "
            + edadMujer10 + " integer, "
            + cuantosHombres + " integer, "
            + edadHombre1 + " integer, "
            + edadHombre2 + " integer, "
            + edadHombre3 + " integer, "
            + edadHombre4 + " integer, "
            + edadHombre5 + " integer, "
            + edadHombre6 + " integer, "
            + edadHombre7 + " integer, "
            + edadHombre8 + " integer, "
            + edadHombre9 + " integer, "
            + edadHombre10 + " integer, "
            + cantidadCuartos + " integer, "
            + cantidadCuartosDormir + " integer, "
            + problemaAgua + " text, "
            + hrsSinServicioAgua + " integer, "
            + frecuenciaSeVaAgua + " text, "
            + otraFrecuenciaSeVaAgua + " text, "
            + tienePozo + " text, "
            + tieneMedidorAgua + " text, "
            + tieneTanque + " text, "
            + ubicacionLlaveAgua + " text, "
            + llaveCompartida + " text, "
            + almacenaAgua + " text, "
            + almacenaEnBarriles + " text, "
            + almacenaEnTanques + " text, "
            + almacenaEnPilas + " text, "
            + almacenaOtrosRecipientes + " text, "
            + otrosRecipientes + " text, "
            + numBarriles + " integer, "
            + numTanques + " integer, "
            + numPilas + " integer, "
            + numOtrosRecipientes + " integer, "
            + barrilesTapados + " text, "
            + tanquesTapados + " text, "
            + pilasTapadas + " text, "
            + otrosRecipientesTapados + " text, "
            + cepillaPilas + " text, "
            + frecCepillaPilas + " text, "
            + cambiadoCasa + " text, "
            + remodeladoCasa + " text, "
            + ubicacionLavandero + " text, "
            + materialParedes + " text, "
            + materialPiso + " text, "
            + materialTecho + " text, "
            + otroMaterialParedes + " text, "
            + otroMaterialPiso + " text, "
            + otroMaterialTecho + " text, "
            + casaPropia + " text, "
            + tieneAbanico + " text, "
            + tieneTelevisor + " text, "
            + tieneRefrigerador + " text, "
            + tienAireAcondicionado + " text, "
            + numAbanicos + " integer, "
            + numTelevisores + " integer, "
            + numRefrigeradores + " integer, "
            + numAireAcondicionado + " integer, "
            + aireAcondicionadoFuncionando + " text, "
            + lavadoraFuncionando + " text, "
            + tieneMuro + " text, "
            + tieneInternet + " text, "
            + tieneInodoro + " text, "
            + cantInodoro + " integer, "
            + cantLetrina + " integer, "
            + tieneServicioEnergia + " text, "
            + tieneMedidorEnergia + " text, "
            + casaDosPisos + " text, "
            + tieneOtrosServicios + " text, "
            + tieneVehiculo + " text, "
            + tieneMoto + " text, "
            + tieneCarro + " text, "
            + tieneMicrobus + " text, "
            + tieneCamioneta + " text, "
            + tieneCamion + " text, "
            + tieneOtroMedioTransAuto + " text, "
            + otroMedioTransAuto + " text, "
            + anioFabMoto + " integer, "
            + anioFabCarro + " integer, "
            + anioFabMicrobus + " integer, "
            + anioFabCamioneta + " integer, "
            + anioFabCamion + " integer, "
            + anioFabOtroMedioTrans + " integer, "
            + marcaMoto + " text, "
            + marcaCarro + " text, "
            + marcaMicrobus + " text, "
            + marcaCamioneta + " text, "
            + marcaCamion + " text, "
            + marcaOtroMedioTrans + " text, "
            + tipoCocina + " text, "
            + cuantosQuemadores + " integer, "
            + tieneHorno + " text, "
            + cocinaConLenia + " text, "
            + ubicacionCocinaLenia + " text, "
            + periodicidadCocinaLenia + " text, "
            + numDiarioCocinaLenia + " integer, "
            + numSemanalCocinaLenia + " integer, "
            + numQuincenalCocinaLenia + " integer, "
            + numMensualCocinaLenia + " integer, "
            + tieneAnimales + " text, "
            + tieneGallinas + " text, "
            + tienePatos + " text, "
            + tienePerros + " text, "
            + tieneGatos + " text, "
            + tieneCerdos + " text, "
            + tieneVacas + " text, "
            + tieneCabras + " text, "
            + tieneCaballos + " text, "
            + tienePelibueys + " text, "
            + tieneAves + " text, "
            + tieneOtrosAnimales + " text, "
            + cantidadGallinas + " integer, "
            + cantidadPatos + " integer, "
            + cantidadPerros + " integer, "
            + cantidadGatos + " integer, "
            + cantidadCerdos + " integer, "
            + cantidadVacas + " integer, "
            + cantidadCabras + " integer, "
            + cantidadCaballos + " integer, "
            + cantidadPelibueys + " integer, "
            + cantidadAves + " integer, "
            + cantidadOtrosAnimales + " integer, "
            + gallinasDentroCasa + " text, "
            + patosDentroCasa + " text, "
            + perrosDentroCasa + " text, "
            + gatosDentroCasa + " text, "
            + cerdosDentroCasa + " text, "
            + vacasDentroCasa + " text, "
            + cabrasDentroCasa + " text, "
            + caballosDentroCasa + " text, "
            + pelibueysDentroCasa + " text, "
            + avesDentroCasa + " text, "
            + otrosAnimalesDentroCasa + " text, "
            + personaFumaDentroCasa + " text, "
            + tieneRecoleccionBasura + " text, "
            + cuantasVecesRecBasura + " text, "
            + dondePasaRecBasura + " text, "
            + dondePasaRecBasuraOtros + " text, "
            + vistoAnimalesSalvajes + " text, "
            + vistoAnimalesSalvajesOtros + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";


    //Encuesta participante

    public static final String ENCUESTA_PARTICIPANTE_TABLE = "encuestas_participantes";
    //campos tabla encuesta participante
    public static final String fechaEncuesta = "fechaEncuesta";
    public static final String emancipado = "emancipado";
    public static final String descEmancipado = "descEmancipado";
    public static final String otraDescEmanc = "otraDescEmanc";
    public static final String embarazada = "embarazada";
    public static final String conoceFUR = "conoceFUR";
    public static final String fur = "fur";
    public static final String asisteEscuela = "asisteEscuela";
    public static final String gradoEstudia = "gradoEstudia";
    public static final String nombreEscuela = "nombreEscuela";
    public static final String otraEscuela = "otraEscuela";
    public static final String turno = "turno";
    public static final String cuidan = "cuidan";
    public static final String cuantosCuidan = "cuantosCuidan";
    public static final String nombreCDI = "nombreCDI";
    public static final String direccionCDI = "direccionCDI";
    public static final String otroLugarCuidan = "otroLugarCuidan";
    public static final String alfabeto = "alfabeto";
    public static final String nivelEducacion = "nivelEducacion";
    public static final String trabaja = "trabaja";
    public static final String tipoTrabajo = "tipoTrabajo";
    public static final String ocupacionActual = "ocupacionActual";


    public static final String personaVive = "personaVive";
    public static final String ordenNac = "ordenNac";
    public static final String padreAlfabeto = "padreAlfabeto";
    public static final String papaNivel = "papaNivel";
    public static final String trabajaPadre = "trabajaPadre";
    public static final String papaTipoTra = "papaTipoTra";
    public static final String madreAlfabeta = "madreAlfabeta";
    public static final String mamaNivel = "mamaNivel";
    public static final String trabajaMadre = "trabajaMadre";
    public static final String mamaTipoTra = "mamaTipoTra";
    public static final String comparteHab = "comparteHab";
    public static final String hab1 = "hab1";
    public static final String hab2 = "hab2";
    public static final String hab3 = "hab3";
    public static final String hab4 = "hab4";
    public static final String hab5 = "hab5";
    public static final String comparteCama = "comparteCama";
    public static final String cama1 = "cama1";
    public static final String cama2 = "cama2";
    public static final String cama3 = "cama3";
    public static final String cama4 = "cama4";
    public static final String cama5 = "cama5";
    public static final String fuma = "fuma";
    public static final String periodicidadFuma = "periodicidadFuma";
    public static final String cantidadCigarrillos = "cantidadCigarrillos";
    public static final String fumaDentroCasa = "fumaDentroCasa";
    /*
    public static final String FumaSN = "FumaSN";
    public static final String quienFuma = "quienFuma";
    public static final String cantCigarrosMadre = "cantCigarrosMadre";
    public static final String cantCigarrosPadre = "cantCigarrosPadre";
    public static final String cantCigarrosOtros = "cantCigarrosOtros";
    */
    public static final String tuberculosisPulmonarActual = "tuberculosisPulmonarActual";
    public static final String anioDiagTubpulActual = "anioDiagTubpulActual";
    public static final String mesDiagTubpulActual = "mesDiagTubpulActual";
    public static final String tratamientoTubpulActual = "tratamientoTubpulActual";
    public static final String completoTratamientoTubpulActual = "completoTratamientoTubpulActual";
    public static final String tuberculosisPulmonarPasado = "tuberculosisPulmonarPasado";

    public static final String fechaDiagTubpulPasadoDes = "fechaDiagTubpulPasadoDes";
    public static final String anioDiagTubpulPasado = "anioDiagTubpulPasado";
    public static final String mesDiagTubpulPasado = "mesDiagTubpulPasado";
    public static final String tratamientoTubpulPasado = "tratamientoTubpulPasado";
    public static final String completoTratamientoTubpulPas = "completoTratamientoTubpulPas";

    public static final String alergiaRespiratoria = "alergiaRespiratoria";
    public static final String alergiaRespiratoriaAnio = "alergiaRespiratoriaAnio";
    public static final String asma = "asma";
    public static final String asmaAnio = "asmaAnio";
    public static final String enfermedadCronica = "enfermedadCronica";
    public static final String cardiopatia = "cardiopatia";
    public static final String cardiopatiaAnio = "cardiopatiaAnio";
    public static final String enfermPulmonarObstCronica = "enfermPulmonarObstCronica";
    public static final String enfermPulmonarObstCronicaAnio = "enfermPulmonarObstCronicaAnio";
    public static final String diabetes = "diabetes";
    public static final String diabetesAnio = "diabetesAnio";
    public static final String presionAlta = "presionAlta";
    public static final String presionAltaAnio = "presionAltaAnio";
    public static final String otrasEnfermedades = "otrasEnfermedades";
    public static final String descOtrasEnfermedades = "descOtrasEnfermedades";
    public static final String tenidoDengue = "tenidoDengue";
    public static final String anioDengue = "anioDengue";
    public static final String diagMedicoDengue = "diagMedicoDengue";
    public static final String dondeDengue = "dondeDengue";
    public static final String tenidoZika = "tenidoZika";
    public static final String anioZika = "anioZika";
    public static final String diagMedicoZika = "diagMedicoZika";
    public static final String dondeZika = "dondeZika";
    public static final String tenidoChik = "tenidoChik";
    public static final String anioChik = "anioChik";
    public static final String diagMedicoChik = "diagMedicoChik";
    public static final String dondeChik = "dondeChik";
    public static final String vacunaFiebreAma = "vacunaFiebreAma";
    public static final String anioVacunaFiebreAma = "anioVacunaFiebreAma";
    public static final String vacunaCovid = "vacunaCovid";
    public static final String anioVacunaCovid = "anioVacunaCovid";
    public static final String mesVacunaCovid = "mesVacunaCovid";
    public static final String tieneTarjetaVacuna = "tieneTarjetaVacuna";
    public static final String fiebre = "fiebre";
    public static final String tiempoFiebre = "tiempoFiebre";
    public static final String lugarConsFiebre = "lugarConsFiebre";
    public static final String noAcudioCs = "noAcudioCs";
    public static final String automedicoFiebre = "automedicoFiebre";
    public static final String tenidoDengueUA = "tenidoDengueUA";
    public static final String unidadSaludDengue = "unidadSaludDengue";
    public static final String centroSaludDengue = "centroSaludDengue";
    public static final String otroCentroSaludDengue = "otroCentroSaludDengue";
    public static final String puestoSaludDengue = "puestoSaludDengue";
    public static final String otroPuestoSaludDengue = "otroPuestoSaludDengue";
    public static final String hospitalDengue = "hospitalDengue";
    public static final String otroHospitalDengue = "otroHospitalDengue";
    public static final String hospitalizadoDengue = "hospitalizadoDengue";
    public static final String ambulatorioDengue = "ambulatorioDengue";
    public static final String diagnosticoDengue = "diagnosticoDengue";
    public static final String rashUA = "rashUA";
    public static final String recuerdaMesRash = "recuerdaMesRash";
    public static final String rashMes = "rashMes";
    public static final String rashCara = "rashCara";
    public static final String rashMiembrosSup = "rashMiembrosSup";
    public static final String rashTorax = "rashTorax";
    public static final String rashAbdomen = "rashAbdomen";
    public static final String rashMiembrosInf = "rashMiembrosInf";
    public static final String rashDias = "rashDias";
    public static final String consultaRashUA = "consultaRashUA";
    public static final String unidadSaludRash = "unidadSaludRash";
    public static final String centroSaludRash = "centroSaludRash";
    public static final String otroCentroSaludRash = "otroCentroSaludRash";
    public static final String puestoSaludRash = "puestoSaludRash";
    public static final String otroPuestoSaludRash = "otroPuestoSaludRash";
    public static final String diagnosticoRash = "diagnosticoRash";
    public static final String estudiosAct = "estudiosAct";

    public static final String CREATE_ENCUESTA_PART_TABLE = "create table if not exists "
            + ENCUESTA_PARTICIPANTE_TABLE + " ("
            + codigo + " text not null, "
            + participante + " text not null, "
            + fechaEncuesta + " date, "
            + emancipado + " text, "
            + descEmancipado + " text, "
            + otraDescEmanc + " text, "
            + embarazada + " text, "
            + conoceFUR + " text, "
            + fur + " text, "
            + asisteEscuela + " text, "
            + gradoEstudia + " text, "
            + nombreEscuela + " text, "
            + turno + " text, "
            + cuidan + " text, "
            + cuantosCuidan + " integer, "
            + nombreCDI + " text, "
            + direccionCDI + " text, "
            + otroLugarCuidan + " text, "
            + alfabeto + " text, "
            + nivelEducacion + " text, "
            + trabaja + " text, "
            + tipoTrabajo + " text, "
            + ocupacionActual + " text, "
            + personaVive + " text, "
            + ordenNac + " integer, "
            + padreAlfabeto + " text, "
            + papaNivel + " text, "
            + trabajaPadre + " text, "
            + papaTipoTra + " text, "
            + madreAlfabeta + " text, "
            + mamaNivel + " text, "
            + trabajaMadre + " text, "
            + mamaTipoTra + " text, "
            + comparteHab + " text, "
            + hab1 + " integer, "
            + hab2 + " integer, "
            + hab3 + " integer, "
            + hab4 + " integer, "
            + hab5 + " integer, "
            + comparteCama + " text, "
            + cama1 + " integer, "
            + cama2 + " integer, "
            + cama3 + " integer, "
            + cama4 + " integer, "
            + cama5 + " integer, "
            + fuma + " text, "
            + periodicidadFuma + " text, "
            + cantidadCigarrillos + " integer, "
            + fumaDentroCasa + " text, "
            /*+ alguienFuma + " text, "
            + quienFuma + " text, "
            + cantCigarrosMadre + " text, "
            + cantCigarrosPadre + " text, "
            + cantCigarrosOtros + " text, "*/
            + tuberculosisPulmonarActual + " text, "
            + anioDiagTubpulActual + " integer, "
            + mesDiagTubpulActual + " text, "
            + tratamientoTubpulActual + " text, "
            + completoTratamientoTubpulActual + " text, "
            + tuberculosisPulmonarPasado + " text, "
            + fechaDiagTubpulPasadoDes + " text, "
            + anioDiagTubpulPasado + " integer, "
            + mesDiagTubpulPasado + " text, "
            + tratamientoTubpulPasado + " text, "
            + completoTratamientoTubpulPas + " text, "
            + alergiaRespiratoria + " text, "
            + alergiaRespiratoriaAnio + " integer, "
            + asma + " text, "
            + asmaAnio + " integer, "
            + enfermedadCronica + " text, "
            + cardiopatia + " text, "
            + cardiopatiaAnio + " integer, "
            + enfermPulmonarObstCronica + " text, "
            + enfermPulmonarObstCronicaAnio + " integer, "
            + diabetes + " text, "
            + diabetesAnio + " integer, "
            + presionAlta + " text, "
            + presionAltaAnio + " integer, "
            + otrasEnfermedades + " text, "
            + descOtrasEnfermedades + " text, "
            + tenidoDengue + " text, "
            + anioDengue + " integer, "
            + diagMedicoDengue + " text, "
            + dondeDengue + " text, "
            + tenidoZika + " text, "
            + anioZika + " integer, "
            + diagMedicoZika + " text, "
            + dondeZika + " text, "
            + tenidoChik + " text, "
            + anioChik + " integer, "
            + diagMedicoChik + " text, "
            + dondeChik + " text, "
            + vacunaFiebreAma + " text, "
            + anioVacunaFiebreAma + " integer, "
            + vacunaCovid + " text, "
            + anioVacunaCovid + " integer, "
            + mesVacunaCovid + " text, "
            + tieneTarjetaVacuna + " text, "
            + fiebre + " text, "
            + tiempoFiebre + " text, "
            + lugarConsFiebre + " text, "
            + noAcudioCs + " text, "
            + automedicoFiebre + " text, "
            + tenidoDengueUA + " text, "
            + unidadSaludDengue + " text, "
            + centroSaludDengue + " text, "
            + otroCentroSaludDengue + " text, "
            + puestoSaludDengue + " text, "
            + otroPuestoSaludDengue + " text, "
            + hospitalDengue + " text, "
            + otroHospitalDengue + " text, "
            + hospitalizadoDengue + " text, "
            + ambulatorioDengue + " text, "
            + diagnosticoDengue + " text, "
            + rashUA + " text, "
            + recuerdaMesRash + " text, "
            + rashMes + " text, "
            + rashCara + " text, "
            + rashMiembrosSup + " text, "
            + rashTorax + " text, "
            + rashAbdomen + " text, "
            + rashMiembrosInf + " text, "
            + rashDias + " text, "
            + consultaRashUA + " text, "
            + unidadSaludRash + " text, "
            + centroSaludRash + " text, "
            + otroCentroSaludRash + " text, "
            + puestoSaludRash + " text, "
            + otroPuestoSaludRash + " text, "
            + diagnosticoRash + " text, "
            + estudiosAct + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Nueva encuesta de satisfaccion por usuario

    public static final String ENCUESTA_SATISFACCION_USUARIO_TABLE = "encuestaSatisfaccionUsuario";

    public static final String codigoParticipante = "codigoParticipante";
    public static final String atencionPersonalEstudio_P1 = "atencionPersonalEstudio_P1";
    public static final String tiempoAtencionRecibido_P2 = "tiempoAtencionRecibido_P2";
    public static final String atencionRecibidaEnfermeria_P3 = "atencionRecibidaEnfermeria_P3";
    public static final String atencionRecibidaDoctores_P4 = "atencionRecibidaDoctores_P4";
    public static final String ambienteDondeRecibeAtencion_P5 = "ambienteDondeRecibeAtencion_P5";
    public static final String explicaronDiagnostico_P6 = "explicaronDiagnostico_P6";
    public static final String explicaronTratamiento_P7 = "explicaronTratamiento_P7";
    public static final String tieneArbovirusImportanciaSeg_P8 = "tieneArbovirusImportanciaSeg_P8";
    public static final String explicaronPeligrosArbovirus_P8_1 = "explicaronPeligrosArbovirus_P8_1";
    public static final String medicoDijoCuidados_P8_2 = "medicoDijoCuidados_P8_2";
    public static final String dificultadAtencion_P9 = "dificultadAtencion_P9";
    public static final String centroSaludLejos_P9_1 = "centroSaludLejos_P9_1";
    public static final String costoTrasnporteElevado_P9_2 = "costoTrasnporteElevado_P9_2";
    public static final String trabajoTiempo_P9_3 = "trabajoTiempo_P9_3";
    public static final String noQueriapasarConsulta_P9_4 = "noQueriapasarConsulta_P9_4";
    public static final String otrasEspecifique_P9_5 = "otrasEspecifique_P9_5";
    public static final String recomendariaAmigoFamiliar_P10 = "recomendariaAmigoFamiliar_P10";
    public static final String atencionCalidad_P10_1 = "atencionCalidad_P10_1";
    public static final String respNecesidadesSaludOportuna_P10_1 = "respNecesidadesSaludOportuna_P10_1";
    public static final String tiempoEsperaCorto_P10_1 = "tiempoEsperaCorto_P10_1";
    public static final String mejorAtencionQueSeguro_P10_1 = "mejorAtencionQueSeguro_P10_1";
    public static final String examenLabNecesarios_P10_1 = "examenLabNecesarios_P10_1";
    public static final String pocosRequisitos_P10_1 = "pocosRequisitos_P10_1";
    public static final String otraP_10_1 = "otraP_10_1";
    public static final String atencionPersonalMala_P10_2 = "atencionPersonalMala_P10_2";
    public static final String noDanRespNecesidades_P10_2 = "noDanRespNecesidades_P10_2";
    public static final String tiempoEsperaLargo_P10_2 = "tiempoEsperaLargo_P10_2";
    public static final String mejorAtencionOtraUnidadSalud_P10_2 = "mejorAtencionOtraUnidadSalud_P10_2";
    public static final String solicitaDemasiadaMx_P10_2 = "solicitaDemasiadaMx_P10_2";
    public static final String muchosRequisitos_P10_2 = "muchosRequisitos_P10_2";
    public static final String noExplicanHacenMx_P10_2 = "noExplicanHacenMx_P10_2";
    public static final String noConfianza_P10_2 = "noConfianza_P10_2";
    public static final String otraP_10_2 = "otraP_10_2";
    public static final String comprendeProcedimientos_P11 = "comprendeProcedimientos_P11";
    public static final String noComodoRealizarPreg_P11_1 = "noComodoRealizarPreg_P11_1";
    public static final String noRespondieronPreg_P11_1 = "noRespondieronPreg_P11_1";
    public static final String explicacionRapida_P11_1 = "explicacionRapida_P11_1";
    public static final String noDejaronHacerPreg_P11_1 = "noDejaronHacerPreg_P11_1";
    public static final String otraP_11_1 = "otraP_11_1";

    public static final String identificadoEquipo = "identificadoEquipo";
    public static final String fechaRegistro = "fechaRegistro";
    public static final String creado = "creado";
    public static final String usuarioRegistro = "usuarioRegistro";
    public static final String codigoCasa = "codigoCasa";

    public static final String pasive = "pasive";
    public static final String deviceId = "identificador_equipo";
    public static final String estado = "estado";
    public static final String nombre1Tutor = "nombre1Tutor";
    public static final String nombre2Tutor = "nombre2Tutor";
    public static final String apellido1Tutor = "apellido1Tutor";
    public static final String apellido2Tutor = "apellido2Tutor";
    public static final String estudio = "estudio";

    //Crear tabla encuesta satiscaccion usuario
    public static final String CREATE_ENCUESTA_SATISFACCION_USUARIO_TABLE = "create table if not exists "
            + ENCUESTA_SATISFACCION_USUARIO_TABLE + " ("
            + codigoParticipante + " text not null, "
            + atencionPersonalEstudio_P1 + " text, "
            + tiempoAtencionRecibido_P2 + " text, "
            + atencionRecibidaEnfermeria_P3 + " text, "
            + atencionRecibidaDoctores_P4 + " text, "
            + ambienteDondeRecibeAtencion_P5 + " text, "
            + explicaronDiagnostico_P6 + " text, "
            + explicaronTratamiento_P7 + " text, "
            + tieneArbovirusImportanciaSeg_P8 + " text, "
            + explicaronPeligrosArbovirus_P8_1 + " text, "
            + medicoDijoCuidados_P8_2 + " text, "
            + dificultadAtencion_P9 + " text, "
            + centroSaludLejos_P9_1 + " text, "
            + costoTrasnporteElevado_P9_2 + " text, "
            + trabajoTiempo_P9_3 + " text, "
            + noQueriapasarConsulta_P9_4 + " text, "
            + otrasEspecifique_P9_5 + " text, "
            + recomendariaAmigoFamiliar_P10 + " text, "
            + atencionCalidad_P10_1 + " text, "
            + respNecesidadesSaludOportuna_P10_1 + " text, "
            + tiempoEsperaCorto_P10_1 + " text, "
            + mejorAtencionQueSeguro_P10_1 + " text, "
            + examenLabNecesarios_P10_1 + " text, "
            + pocosRequisitos_P10_1 + " text, "
            + otraP_10_1 + " text, "
            + atencionPersonalMala_P10_2 + " text, "
            + noDanRespNecesidades_P10_2 + " text, "
            + tiempoEsperaLargo_P10_2 + " text, "
            + mejorAtencionOtraUnidadSalud_P10_2 + " text, "
            + solicitaDemasiadaMx_P10_2 + " text, "
            + muchosRequisitos_P10_2 + " text, "
            + noExplicanHacenMx_P10_2 + " text, "
            + noConfianza_P10_2 + " text, "
            + otraP_10_2 + " text, "
            + comprendeProcedimientos_P11 + " text, "
            + noComodoRealizarPreg_P11_1 + " text, "
            + noRespondieronPreg_P11_1 + " text, "
            + explicacionRapida_P11_1 + " text, "
            + noDejaronHacerPreg_P11_1 + " text, "
            + otraP_11_1 + " text, "
            + identificadoEquipo + " text, "
            + pasive + " text, "
            + estado + " text not null, "
            + fechaRegistro + " text, "
            + creado + " text, "
            + usuarioRegistro + " text, "
            + nombre1Tutor + " text, "
            + nombre2Tutor + " text, "
            + apellido1Tutor + " text, "
            + apellido2Tutor + " text, "
            + codigoCasa + " text, "
            //+ casaCHF + " text, "
            + estudio + " text, "
            + "primary key (" + codigoParticipante + "));";
}
