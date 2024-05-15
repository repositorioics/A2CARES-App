package ni.org.ics.a2cares.app.domain.survey;

import ni.org.ics.a2cares.app.domain.BaseMetaData;
import ni.org.ics.a2cares.app.domain.core.Casa;

/**
 * Created by Miguel Salinas on 11/6/2021
 * V1.0
 */
public class EncuestaCasa extends BaseMetaData {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String codigo;
    private Casa casa;
    private String participante; //participantes encuestado
    private int cuantasPersonas; //1
    private int cuantasMujeres; //2
    private Integer edadMujer1;
    private Integer edadMujer2;
    private Integer edadMujer3;
    private Integer edadMujer4;
    private Integer edadMujer5;
    private Integer edadMujer6;
    private Integer edadMujer7;
    private Integer edadMujer8;
    private Integer edadMujer9;
    private Integer edadMujer10;
    private int cuantosHombres; //3
    private Integer edadHombre1;
    private Integer edadHombre2;
    private Integer edadHombre3;
    private Integer edadHombre4;
    private Integer edadHombre5;
    private Integer edadHombre6;
    private Integer edadHombre7;
    private Integer edadHombre8;
    private Integer edadHombre9;
    private Integer edadHombre10;
    private int cantidadCuartos;
    private int cantidadCuartosDormir;
    private String problemaAgua; //6
    private int hrsSinServicioAgua;
    private String frecuenciaSeVaAgua;
    private String otraFrecuenciaSeVaAgua;
    private String tienePozo; //7
    private String tieneMedidorAgua; //8
    private String tieneTanque; //10
    private String ubicacionLlaveAgua;
    private String llaveCompartida;
    private String almacenaAgua;
    private String almacenaEnBarriles;
    private String almacenaEnTanques;
    private String almacenaEnPilas;
    private String almacenaOtrosRecipientes;
    private String otrosRecipientes;
    private Integer numBarriles;
    private Integer numTanques;
    private Integer numPilas;
    private Integer numOtrosRecipientes;
    private String barrilesTapados;
    private String tanquesTapados;
    private String pilasTapadas;
    private String otrosRecipientesTapados;
    private String cepillaPilas;
    private String frecCepillaPilas;
    private String cambiadoCasa; //13
    private String remodeladoCasa; //14
    private String ubicacionLavandero;
    private String materialParedes;
    private String materialPiso;
    private String materialTecho;
    private String otroMaterialParedes;
    private String otroMaterialPiso;
    private String otroMaterialTecho;
    private String casaPropia;
    private String tieneAbanico;
    private String tieneTelevisor;
    private String tieneRefrigerador;
    private String tienAireAcondicionado;
    private Integer numAbanicos;
    private Integer numTelevisores;
    private Integer numRefrigeradores;
    private Integer numAireAcondicionado;
    private String aireAcondicionadoFuncionando;
    private String lavadoraFuncionando; //20.5.1
    private String tieneMuro; //21
    private String tieneInternet; //22
    private String tieneInodoro; //23
    private Integer cantInodoro;
    private Integer cantLetrina;
    private String tieneServicioEnergia; //24
    private String tieneMedidorEnergia; //24.a
    private String casaDosPisos; //25
    private String tieneOtrosServicios; //26
    private String tieneVehiculo; //27
    private String tieneMoto;
    private String tieneCarro;
    private String tieneMicrobus;
    private String tieneCamioneta;
    private String tieneCamion;
    private String tieneOtroMedioTransAuto;
    private String otroMedioTransAuto;
    private Integer anioFabMoto; //28.2
    private Integer anioFabCarro; //28.2
    private Integer anioFabMicrobus; //28.2
    private Integer anioFabCamioneta; //28.2
    private Integer anioFabCamion; //28.2
    private Integer anioFabOtroMedioTrans; //28.2
    private String marcaMoto; //28.3
    private String marcaCarro; //28.3
    private String marcaMicrobus; //28.3
    private String marcaCamioneta; //28.3
    private String marcaCamion; //28.3
    private String marcaOtroMedioTrans; //28.3
    private String tipoCocina; //29
    private Integer cuantosQuemadores; //29.a
    private String tieneHorno; //30
    private String cocinaConLenia;
    private String ubicacionCocinaLenia;
    private String periodicidadCocinaLenia;
    private Integer numDiarioCocinaLenia;   //# de veces que cocina
    private Integer numSemanalCocinaLenia;  //# de veces que cocina semanalmente
    private Integer numQuincenalCocinaLenia;    //# de veces que cocina quincenalmente
    private Integer numMensualCocinaLenia;  //# de veces que cocina al mes
    private String tieneAnimales;
    private String tieneGallinas; //33
    private String tienePatos; //33
    private String tienePerros; //33
    private String tieneGatos; //33
    private String tieneCerdos; //33
    private String tieneVacas; //33
    private String tieneCabras; //33
    private String tieneCaballos; //33
    private String tienePelibueys; //33
    private String tieneAves; //33
    private String tieneOtrosAnimales; //33
    private Integer cantidadGallinas; //33.1
    private Integer cantidadPatos;
    private Integer cantidadPerros;
    private Integer cantidadGatos;
    private Integer cantidadCerdos;
    private Integer cantidadVacas;
    private Integer cantidadCabras;
    private Integer cantidadCaballos;
    private Integer cantidadPelibueys;
    private Integer cantidadAves;
    private Integer cantidadOtrosAnimales; //33.1
    private String gallinasDentroCasa;//34
    private String patosDentroCasa;
    private String perrosDentroCasa;
    private String gatosDentroCasa;
    private String cerdosDentroCasa;
    private String vacasDentroCasa;
    private String cabrasDentroCasa;
    private String caballosDentroCasa;
    private String pelibueysDentroCasa;
    private String avesDentroCasa;
    private String otrosAnimalesDentroCasa; //34
    private String personaFumaDentroCasa; //35
    private String tieneRecoleccionBasura; //36
    private Integer cuantasVecesRecBasura; //37
    private String dondePasaRecBasura; //32
    private String dondePasaRecBasuraOtros; //32
    private String vistoAnimalesSalvajes; //33
    private String vistoAnimalesSalvajesOtros; //33.1

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public int getCuantasPersonas() {
        return cuantasPersonas;
    }

    public void setCuantasPersonas(int cuantasPersonas) {
        this.cuantasPersonas = cuantasPersonas;
    }

    public int getCuantasMujeres() {
        return cuantasMujeres;
    }

    public void setCuantasMujeres(int cuantasMujeres) {
        this.cuantasMujeres = cuantasMujeres;
    }

    public Integer getEdadMujer1() {
        return edadMujer1;
    }

    public void setEdadMujer1(Integer edadMujer1) {
        this.edadMujer1 = edadMujer1;
    }

    public Integer getEdadMujer2() {
        return edadMujer2;
    }

    public void setEdadMujer2(Integer edadMujer2) {
        this.edadMujer2 = edadMujer2;
    }

    public Integer getEdadMujer3() {
        return edadMujer3;
    }

    public void setEdadMujer3(Integer edadMujer3) {
        this.edadMujer3 = edadMujer3;
    }

    public Integer getEdadMujer4() {
        return edadMujer4;
    }

    public void setEdadMujer4(Integer edadMujer4) {
        this.edadMujer4 = edadMujer4;
    }

    public Integer getEdadMujer5() {
        return edadMujer5;
    }

    public void setEdadMujer5(Integer edadMujer5) {
        this.edadMujer5 = edadMujer5;
    }

    public Integer getEdadMujer6() {
        return edadMujer6;
    }

    public void setEdadMujer6(Integer edadMujer6) {
        this.edadMujer6 = edadMujer6;
    }

    public Integer getEdadMujer7() {
        return edadMujer7;
    }

    public void setEdadMujer7(Integer edadMujer7) {
        this.edadMujer7 = edadMujer7;
    }

    public Integer getEdadMujer8() {
        return edadMujer8;
    }

    public void setEdadMujer8(Integer edadMujer8) {
        this.edadMujer8 = edadMujer8;
    }

    public Integer getEdadMujer9() {
        return edadMujer9;
    }

    public void setEdadMujer9(Integer edadMujer9) {
        this.edadMujer9 = edadMujer9;
    }

    public Integer getEdadMujer10() {
        return edadMujer10;
    }

    public void setEdadMujer10(Integer edadMujer10) {
        this.edadMujer10 = edadMujer10;
    }

    public int getCuantosHombres() {
        return cuantosHombres;
    }

    public void setCuantosHombres(int cuantosHombres) {
        this.cuantosHombres = cuantosHombres;
    }

    public Integer getEdadHombre1() {
        return edadHombre1;
    }

    public void setEdadHombre1(Integer edadHombre1) {
        this.edadHombre1 = edadHombre1;
    }

    public Integer getEdadHombre2() {
        return edadHombre2;
    }

    public void setEdadHombre2(Integer edadHombre2) {
        this.edadHombre2 = edadHombre2;
    }

    public Integer getEdadHombre3() {
        return edadHombre3;
    }

    public void setEdadHombre3(Integer edadHombre3) {
        this.edadHombre3 = edadHombre3;
    }

    public Integer getEdadHombre4() {
        return edadHombre4;
    }

    public void setEdadHombre4(Integer edadHombre4) {
        this.edadHombre4 = edadHombre4;
    }

    public Integer getEdadHombre5() {
        return edadHombre5;
    }

    public void setEdadHombre5(Integer edadHombre5) {
        this.edadHombre5 = edadHombre5;
    }

    public Integer getEdadHombre6() {
        return edadHombre6;
    }

    public void setEdadHombre6(Integer edadHombre6) {
        this.edadHombre6 = edadHombre6;
    }

    public Integer getEdadHombre7() {
        return edadHombre7;
    }

    public void setEdadHombre7(Integer edadHombre7) {
        this.edadHombre7 = edadHombre7;
    }

    public Integer getEdadHombre8() {
        return edadHombre8;
    }

    public void setEdadHombre8(Integer edadHombre8) {
        this.edadHombre8 = edadHombre8;
    }

    public Integer getEdadHombre9() {
        return edadHombre9;
    }

    public void setEdadHombre9(Integer edadHombre9) {
        this.edadHombre9 = edadHombre9;
    }

    public Integer getEdadHombre10() {
        return edadHombre10;
    }

    public void setEdadHombre10(Integer edadHombre10) {
        this.edadHombre10 = edadHombre10;
    }

    public int getCantidadCuartos() {
        return cantidadCuartos;
    }

    public void setCantidadCuartos(int cantidadCuartos) {
        this.cantidadCuartos = cantidadCuartos;
    }

    public int getCantidadCuartosDormir() {
        return cantidadCuartosDormir;
    }

    public void setCantidadCuartosDormir(int cantidadCuartosDormir) {
        this.cantidadCuartosDormir = cantidadCuartosDormir;
    }

    public String getProblemaAgua() {
        return problemaAgua;
    }

    public void setProblemaAgua(String problemaAgua) {
        this.problemaAgua = problemaAgua;
    }

    public int getHrsSinServicioAgua() {
        return hrsSinServicioAgua;
    }

    public void setHrsSinServicioAgua(int hrsSinServicioAgua) {
        this.hrsSinServicioAgua = hrsSinServicioAgua;
    }

    public String getFrecuenciaSeVaAgua() {
        return frecuenciaSeVaAgua;
    }

    public void setFrecuenciaSeVaAgua(String frecuenciaSeVaAgua) {
        this.frecuenciaSeVaAgua = frecuenciaSeVaAgua;
    }

    public String getOtraFrecuenciaSeVaAgua() {
        return otraFrecuenciaSeVaAgua;
    }

    public void setOtraFrecuenciaSeVaAgua(String otraFrecuenciaSeVaAgua) {
        this.otraFrecuenciaSeVaAgua = otraFrecuenciaSeVaAgua;
    }

    public String getTienePozo() {
        return tienePozo;
    }

    public void setTienePozo(String tienePozo) {
        this.tienePozo = tienePozo;
    }

    public String getTieneMedidorAgua() {
        return tieneMedidorAgua;
    }

    public void setTieneMedidorAgua(String tieneMedidorAgua) {
        this.tieneMedidorAgua = tieneMedidorAgua;
    }

    public String getTieneTanque() {
        return tieneTanque;
    }

    public void setTieneTanque(String tieneTanque) {
        this.tieneTanque = tieneTanque;
    }

    public String getUbicacionLlaveAgua() {
        return ubicacionLlaveAgua;
    }

    public void setUbicacionLlaveAgua(String ubicacionLlaveAgua) {
        this.ubicacionLlaveAgua = ubicacionLlaveAgua;
    }

    public String getLlaveCompartida() {
        return llaveCompartida;
    }

    public void setLlaveCompartida(String llaveCompartida) {
        this.llaveCompartida = llaveCompartida;
    }

    public String getAlmacenaAgua() {
        return almacenaAgua;
    }

    public void setAlmacenaAgua(String almacenaAgua) {
        this.almacenaAgua = almacenaAgua;
    }

    public String getAlmacenaEnBarriles() {
        return almacenaEnBarriles;
    }

    public void setAlmacenaEnBarriles(String almacenaEnBarriles) {
        this.almacenaEnBarriles = almacenaEnBarriles;
    }

    public String getAlmacenaEnTanques() {
        return almacenaEnTanques;
    }

    public void setAlmacenaEnTanques(String almacenaEnTanques) {
        this.almacenaEnTanques = almacenaEnTanques;
    }

    public String getAlmacenaEnPilas() {
        return almacenaEnPilas;
    }

    public void setAlmacenaEnPilas(String almacenaEnPilas) {
        this.almacenaEnPilas = almacenaEnPilas;
    }

    public String getAlmacenaOtrosRecipientes() {
        return almacenaOtrosRecipientes;
    }

    public void setAlmacenaOtrosRecipientes(String almacenaOtrosRecipientes) {
        this.almacenaOtrosRecipientes = almacenaOtrosRecipientes;
    }

    public String getOtrosRecipientes() {
        return otrosRecipientes;
    }

    public void setOtrosRecipientes(String otrosRecipientes) {
        this.otrosRecipientes = otrosRecipientes;
    }

    public Integer getNumBarriles() {
        return numBarriles;
    }

    public void setNumBarriles(Integer numBarriles) {
        this.numBarriles = numBarriles;
    }

    public Integer getNumTanques() {
        return numTanques;
    }

    public void setNumTanques(Integer numTanques) {
        this.numTanques = numTanques;
    }

    public Integer getNumPilas() {
        return numPilas;
    }

    public void setNumPilas(Integer numPilas) {
        this.numPilas = numPilas;
    }

    public Integer getNumOtrosRecipientes() {
        return numOtrosRecipientes;
    }

    public void setNumOtrosRecipientes(Integer numOtrosRecipientes) {
        this.numOtrosRecipientes = numOtrosRecipientes;
    }

    public String getBarrilesTapados() {
        return barrilesTapados;
    }

    public void setBarrilesTapados(String barrilesTapados) {
        this.barrilesTapados = barrilesTapados;
    }

    public String getTanquesTapados() {
        return tanquesTapados;
    }

    public void setTanquesTapados(String tanquesTapados) {
        this.tanquesTapados = tanquesTapados;
    }

    public String getPilasTapadas() {
        return pilasTapadas;
    }

    public void setPilasTapadas(String pilasTapadas) {
        this.pilasTapadas = pilasTapadas;
    }

    public String getOtrosRecipientesTapados() {
        return otrosRecipientesTapados;
    }

    public void setOtrosRecipientesTapados(String otrosRecipientesTapados) {
        this.otrosRecipientesTapados = otrosRecipientesTapados;
    }

    public String getCepillaPilas() {
        return cepillaPilas;
    }

    public void setCepillaPilas(String cepillaPilas) {
        this.cepillaPilas = cepillaPilas;
    }

    public String getFrecCepillaPilas() {
        return frecCepillaPilas;
    }

    public void setFrecCepillaPilas(String frecCepillaPilas) {
        this.frecCepillaPilas = frecCepillaPilas;
    }

    public String getCambiadoCasa() {
        return cambiadoCasa;
    }

    public void setCambiadoCasa(String cambiadoCasa) {
        this.cambiadoCasa = cambiadoCasa;
    }

    public String getRemodeladoCasa() {
        return remodeladoCasa;
    }

    public void setRemodeladoCasa(String remodeladoCasa) {
        this.remodeladoCasa = remodeladoCasa;
    }

    public String getUbicacionLavandero() {
        return ubicacionLavandero;
    }

    public void setUbicacionLavandero(String ubicacionLavandero) {
        this.ubicacionLavandero = ubicacionLavandero;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public void setMaterialParedes(String materialParedes) {
        this.materialParedes = materialParedes;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public void setMaterialPiso(String materialPiso) {
        this.materialPiso = materialPiso;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public void setMaterialTecho(String materialTecho) {
        this.materialTecho = materialTecho;
    }

    public String getOtroMaterialParedes() {
        return otroMaterialParedes;
    }

    public void setOtroMaterialParedes(String otroMaterialParedes) {
        this.otroMaterialParedes = otroMaterialParedes;
    }

    public String getOtroMaterialPiso() {
        return otroMaterialPiso;
    }

    public void setOtroMaterialPiso(String otroMaterialPiso) {
        this.otroMaterialPiso = otroMaterialPiso;
    }

    public String getOtroMaterialTecho() {
        return otroMaterialTecho;
    }

    public void setOtroMaterialTecho(String otroMaterialTecho) {
        this.otroMaterialTecho = otroMaterialTecho;
    }

    public String getCasaPropia() {
        return casaPropia;
    }

    public void setCasaPropia(String casaPropia) {
        this.casaPropia = casaPropia;
    }

    public String getTieneAbanico() {
        return tieneAbanico;
    }

    public void setTieneAbanico(String tieneAbanico) {
        this.tieneAbanico = tieneAbanico;
    }

    public String getTieneTelevisor() {
        return tieneTelevisor;
    }

    public void setTieneTelevisor(String tieneTelevisor) {
        this.tieneTelevisor = tieneTelevisor;
    }

    public String getTieneRefrigerador() {
        return tieneRefrigerador;
    }

    public void setTieneRefrigerador(String tieneRefrigerador) {
        this.tieneRefrigerador = tieneRefrigerador;
    }

    public String getTienAireAcondicionado() {
        return tienAireAcondicionado;
    }

    public void setTienAireAcondicionado(String tienAireAcondicionado) {
        this.tienAireAcondicionado = tienAireAcondicionado;
    }

    public Integer getNumAbanicos() {
        return numAbanicos;
    }

    public void setNumAbanicos(Integer numAbanicos) {
        this.numAbanicos = numAbanicos;
    }

    public Integer getNumTelevisores() {
        return numTelevisores;
    }

    public void setNumTelevisores(Integer numTelevisores) {
        this.numTelevisores = numTelevisores;
    }

    public Integer getNumRefrigeradores() {
        return numRefrigeradores;
    }

    public void setNumRefrigeradores(Integer numRefrigeradores) {
        this.numRefrigeradores = numRefrigeradores;
    }

    public Integer getNumAireAcondicionado() {
        return numAireAcondicionado;
    }

    public void setNumAireAcondicionado(Integer numAireAcondicionado) {
        this.numAireAcondicionado = numAireAcondicionado;
    }

    public String getAireAcondicionadoFuncionando() {
        return aireAcondicionadoFuncionando;
    }

    public void setAireAcondicionadoFuncionando(String aireAcondicionadoFuncionando) {
        this.aireAcondicionadoFuncionando = aireAcondicionadoFuncionando;
    }

    public String getLavadoraFuncionando() {
        return lavadoraFuncionando;
    }

    public void setLavadoraFuncionando(String lavadoraFuncionando) {
        this.lavadoraFuncionando = lavadoraFuncionando;
    }

    public String getTieneMuro() {
        return tieneMuro;
    }

    public void setTieneMuro(String tieneMuro) {
        this.tieneMuro = tieneMuro;
    }

    public String getTieneInternet() {
        return tieneInternet;
    }

    public void setTieneInternet(String tieneInternet) {
        this.tieneInternet = tieneInternet;
    }

    public String getTieneInodoro() {
        return tieneInodoro;
    }

    public void setTieneInodoro(String tieneInodoro) {
        this.tieneInodoro = tieneInodoro;
    }

    public Integer getCantInodoro() {
        return cantInodoro;
    }

    public void setCantInodoro(Integer cantInodoro) {
        this.cantInodoro = cantInodoro;
    }

    public Integer getCantLetrina() {
        return cantLetrina;
    }

    public void setCantLetrina(Integer cantLetrina) {
        this.cantLetrina = cantLetrina;
    }

    public String getTieneServicioEnergia() {
        return tieneServicioEnergia;
    }

    public void setTieneServicioEnergia(String tieneServicioEnergia) {
        this.tieneServicioEnergia = tieneServicioEnergia;
    }

    public String getTieneMedidorEnergia() {
        return tieneMedidorEnergia;
    }

    public void setTieneMedidorEnergia(String tieneMedidorEnergia) {
        this.tieneMedidorEnergia = tieneMedidorEnergia;
    }

    public String getCasaDosPisos() {
        return casaDosPisos;
    }

    public void setCasaDosPisos(String casaDosPisos) {
        this.casaDosPisos = casaDosPisos;
    }

    public String getTieneOtrosServicios() {
        return tieneOtrosServicios;
    }

    public void setTieneOtrosServicios(String tieneOtrosServicios) {
        this.tieneOtrosServicios = tieneOtrosServicios;
    }

    public String getTieneVehiculo() {
        return tieneVehiculo;
    }

    public void setTieneVehiculo(String tieneVehiculo) {
        this.tieneVehiculo = tieneVehiculo;
    }

    public String getTieneMoto() {
        return tieneMoto;
    }

    public void setTieneMoto(String tieneMoto) {
        this.tieneMoto = tieneMoto;
    }

    public String getTieneCarro() {
        return tieneCarro;
    }

    public void setTieneCarro(String tieneCarro) {
        this.tieneCarro = tieneCarro;
    }

    public String getTieneMicrobus() {
        return tieneMicrobus;
    }

    public void setTieneMicrobus(String tieneMicrobus) {
        this.tieneMicrobus = tieneMicrobus;
    }

    public String getTieneCamioneta() {
        return tieneCamioneta;
    }

    public void setTieneCamioneta(String tieneCamioneta) {
        this.tieneCamioneta = tieneCamioneta;
    }

    public String getTieneCamion() {
        return tieneCamion;
    }

    public void setTieneCamion(String tieneCamion) {
        this.tieneCamion = tieneCamion;
    }

    public String getTieneOtroMedioTransAuto() {
        return tieneOtroMedioTransAuto;
    }

    public void setTieneOtroMedioTransAuto(String tieneOtroMedioTransAuto) {
        this.tieneOtroMedioTransAuto = tieneOtroMedioTransAuto;
    }

    public String getOtroMedioTransAuto() {
        return otroMedioTransAuto;
    }

    public void setOtroMedioTransAuto(String otroMedioTransAuto) {
        this.otroMedioTransAuto = otroMedioTransAuto;
    }

    public Integer getAnioFabMoto() {
        return anioFabMoto;
    }

    public void setAnioFabMoto(Integer anioFabMoto) {
        this.anioFabMoto = anioFabMoto;
    }

    public Integer getAnioFabCarro() {
        return anioFabCarro;
    }

    public void setAnioFabCarro(Integer anioFabCarro) {
        this.anioFabCarro = anioFabCarro;
    }

    public Integer getAnioFabMicrobus() {
        return anioFabMicrobus;
    }

    public void setAnioFabMicrobus(Integer anioFabMicrobus) {
        this.anioFabMicrobus = anioFabMicrobus;
    }

    public Integer getAnioFabCamioneta() {
        return anioFabCamioneta;
    }

    public void setAnioFabCamioneta(Integer anioFabCamioneta) {
        this.anioFabCamioneta = anioFabCamioneta;
    }

    public Integer getAnioFabCamion() {
        return anioFabCamion;
    }

    public void setAnioFabCamion(Integer anioFabCamion) {
        this.anioFabCamion = anioFabCamion;
    }

    public Integer getAnioFabOtroMedioTrans() {
        return anioFabOtroMedioTrans;
    }

    public void setAnioFabOtroMedioTrans(Integer anioFabOtroMedioTrans) {
        this.anioFabOtroMedioTrans = anioFabOtroMedioTrans;
    }

    public String getMarcaMoto() {
        return marcaMoto;
    }

    public void setMarcaMoto(String marcaMoto) {
        this.marcaMoto = marcaMoto;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public void setMarcaCarro(String marcaCarro) {
        this.marcaCarro = marcaCarro;
    }

    public String getMarcaMicrobus() {
        return marcaMicrobus;
    }

    public void setMarcaMicrobus(String marcaMicrobus) {
        this.marcaMicrobus = marcaMicrobus;
    }

    public String getMarcaCamioneta() {
        return marcaCamioneta;
    }

    public void setMarcaCamioneta(String marcaCamioneta) {
        this.marcaCamioneta = marcaCamioneta;
    }

    public String getMarcaCamion() {
        return marcaCamion;
    }

    public void setMarcaCamion(String marcaCamion) {
        this.marcaCamion = marcaCamion;
    }

    public String getMarcaOtroMedioTrans() {
        return marcaOtroMedioTrans;
    }

    public void setMarcaOtroMedioTrans(String marcaOtroMedioTrans) {
        this.marcaOtroMedioTrans = marcaOtroMedioTrans;
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public Integer getCuantosQuemadores() {
        return cuantosQuemadores;
    }

    public void setCuantosQuemadores(Integer cuantosQuemadores) {
        this.cuantosQuemadores = cuantosQuemadores;
    }

    public String getTieneHorno() {
        return tieneHorno;
    }

    public void setTieneHorno(String tieneHorno) {
        this.tieneHorno = tieneHorno;
    }

    public String getCocinaConLenia() {
        return cocinaConLenia;
    }

    public void setCocinaConLenia(String cocinaConLenia) {
        this.cocinaConLenia = cocinaConLenia;
    }

    public String getUbicacionCocinaLenia() {
        return ubicacionCocinaLenia;
    }

    public void setUbicacionCocinaLenia(String ubicacionCocinaLenia) {
        this.ubicacionCocinaLenia = ubicacionCocinaLenia;
    }

    public String getPeriodicidadCocinaLenia() {
        return periodicidadCocinaLenia;
    }

    public void setPeriodicidadCocinaLenia(String periodicidadCocinaLenia) {
        this.periodicidadCocinaLenia = periodicidadCocinaLenia;
    }

    public Integer getNumDiarioCocinaLenia() {
        return numDiarioCocinaLenia;
    }

    public void setNumDiarioCocinaLenia(Integer numDiarioCocinaLenia) {
        this.numDiarioCocinaLenia = numDiarioCocinaLenia;
    }

    public Integer getNumSemanalCocinaLenia() {
        return numSemanalCocinaLenia;
    }

    public void setNumSemanalCocinaLenia(Integer numSemanalCocinaLenia) {
        this.numSemanalCocinaLenia = numSemanalCocinaLenia;
    }

    public Integer getNumQuincenalCocinaLenia() {
        return numQuincenalCocinaLenia;
    }

    public void setNumQuincenalCocinaLenia(Integer numQuincenalCocinaLenia) {
        this.numQuincenalCocinaLenia = numQuincenalCocinaLenia;
    }

    public Integer getNumMensualCocinaLenia() {
        return numMensualCocinaLenia;
    }

    public void setNumMensualCocinaLenia(Integer numMensualCocinaLenia) {
        this.numMensualCocinaLenia = numMensualCocinaLenia;
    }

    public String getTieneAnimales() {
        return tieneAnimales;
    }

    public void setTieneAnimales(String tieneAnimales) {
        this.tieneAnimales = tieneAnimales;
    }

    public String getTieneGallinas() {
        return tieneGallinas;
    }

    public void setTieneGallinas(String tieneGallinas) {
        this.tieneGallinas = tieneGallinas;
    }

    public String getTienePatos() {
        return tienePatos;
    }

    public void setTienePatos(String tienePatos) {
        this.tienePatos = tienePatos;
    }

    public String getTienePerros() {
        return tienePerros;
    }

    public void setTienePerros(String tienePerros) {
        this.tienePerros = tienePerros;
    }

    public String getTieneGatos() {
        return tieneGatos;
    }

    public void setTieneGatos(String tieneGatos) {
        this.tieneGatos = tieneGatos;
    }

    public String getTieneCerdos() {
        return tieneCerdos;
    }

    public void setTieneCerdos(String tieneCerdos) {
        this.tieneCerdos = tieneCerdos;
    }

    public String getTieneVacas() {
        return tieneVacas;
    }

    public void setTieneVacas(String tieneVacas) {
        this.tieneVacas = tieneVacas;
    }

    public String getTieneCabras() {
        return tieneCabras;
    }

    public void setTieneCabras(String tieneCabras) {
        this.tieneCabras = tieneCabras;
    }

    public String getTieneCaballos() {
        return tieneCaballos;
    }

    public void setTieneCaballos(String tieneCaballos) {
        this.tieneCaballos = tieneCaballos;
    }

    public String getTienePelibueys() {
        return tienePelibueys;
    }

    public void setTienePelibueys(String tienePelibueys) {
        this.tienePelibueys = tienePelibueys;
    }

    public String getTieneAves() {
        return tieneAves;
    }

    public void setTieneAves(String tieneAves) {
        this.tieneAves = tieneAves;
    }

    public String getTieneOtrosAnimales() {
        return tieneOtrosAnimales;
    }

    public void setTieneOtrosAnimales(String tieneOtrosAnimales) {
        this.tieneOtrosAnimales = tieneOtrosAnimales;
    }

    public Integer getCantidadGallinas() {
        return cantidadGallinas;
    }

    public void setCantidadGallinas(Integer cantidadGallinas) {
        this.cantidadGallinas = cantidadGallinas;
    }

    public Integer getCantidadPatos() {
        return cantidadPatos;
    }

    public void setCantidadPatos(Integer cantidadPatos) {
        this.cantidadPatos = cantidadPatos;
    }

    public Integer getCantidadPerros() {
        return cantidadPerros;
    }

    public void setCantidadPerros(Integer cantidadPerros) {
        this.cantidadPerros = cantidadPerros;
    }

    public Integer getCantidadGatos() {
        return cantidadGatos;
    }

    public void setCantidadGatos(Integer cantidadGatos) {
        this.cantidadGatos = cantidadGatos;
    }

    public Integer getCantidadCerdos() {
        return cantidadCerdos;
    }

    public void setCantidadCerdos(Integer cantidadCerdos) {
        this.cantidadCerdos = cantidadCerdos;
    }

    public Integer getCantidadVacas() {
        return cantidadVacas;
    }

    public void setCantidadVacas(Integer cantidadVacas) {
        this.cantidadVacas = cantidadVacas;
    }

    public Integer getCantidadCabras() {
        return cantidadCabras;
    }

    public void setCantidadCabras(Integer cantidadCabras) {
        this.cantidadCabras = cantidadCabras;
    }

    public Integer getCantidadCaballos() {
        return cantidadCaballos;
    }

    public void setCantidadCaballos(Integer cantidadCaballos) {
        this.cantidadCaballos = cantidadCaballos;
    }

    public Integer getCantidadPelibueys() {
        return cantidadPelibueys;
    }

    public void setCantidadPelibueys(Integer cantidadPelibueys) {
        this.cantidadPelibueys = cantidadPelibueys;
    }

    public Integer getCantidadAves() {
        return cantidadAves;
    }

    public void setCantidadAves(Integer cantidadAves) {
        this.cantidadAves = cantidadAves;
    }

    public Integer getCantidadOtrosAnimales() {
        return cantidadOtrosAnimales;
    }

    public void setCantidadOtrosAnimales(Integer cantidadOtrosAnimales) {
        this.cantidadOtrosAnimales = cantidadOtrosAnimales;
    }

    public String getGallinasDentroCasa() {
        return gallinasDentroCasa;
    }

    public void setGallinasDentroCasa(String gallinasDentroCasa) {
        this.gallinasDentroCasa = gallinasDentroCasa;
    }

    public String getPatosDentroCasa() {
        return patosDentroCasa;
    }

    public void setPatosDentroCasa(String patosDentroCasa) {
        this.patosDentroCasa = patosDentroCasa;
    }

    public String getPerrosDentroCasa() {
        return perrosDentroCasa;
    }

    public void setPerrosDentroCasa(String perrosDentroCasa) {
        this.perrosDentroCasa = perrosDentroCasa;
    }

    public String getGatosDentroCasa() {
        return gatosDentroCasa;
    }

    public void setGatosDentroCasa(String gatosDentroCasa) {
        this.gatosDentroCasa = gatosDentroCasa;
    }

    public String getCerdosDentroCasa() {
        return cerdosDentroCasa;
    }

    public void setCerdosDentroCasa(String cerdosDentroCasa) {
        this.cerdosDentroCasa = cerdosDentroCasa;
    }

    public String getVacasDentroCasa() {
        return vacasDentroCasa;
    }

    public void setVacasDentroCasa(String vacasDentroCasa) {
        this.vacasDentroCasa = vacasDentroCasa;
    }

    public String getCabrasDentroCasa() {
        return cabrasDentroCasa;
    }

    public void setCabrasDentroCasa(String cabrasDentroCasa) {
        this.cabrasDentroCasa = cabrasDentroCasa;
    }

    public String getCaballosDentroCasa() {
        return caballosDentroCasa;
    }

    public void setCaballosDentroCasa(String caballosDentroCasa) {
        this.caballosDentroCasa = caballosDentroCasa;
    }

    public String getPelibueysDentroCasa() {
        return pelibueysDentroCasa;
    }

    public void setPelibueysDentroCasa(String pelibueysDentroCasa) {
        this.pelibueysDentroCasa = pelibueysDentroCasa;
    }

    public String getAvesDentroCasa() {
        return avesDentroCasa;
    }

    public void setAvesDentroCasa(String avesDentroCasa) {
        this.avesDentroCasa = avesDentroCasa;
    }

    public String getOtrosAnimalesDentroCasa() {
        return otrosAnimalesDentroCasa;
    }

    public void setOtrosAnimalesDentroCasa(String otrosAnimalesDentroCasa) {
        this.otrosAnimalesDentroCasa = otrosAnimalesDentroCasa;
    }

    public String getPersonaFumaDentroCasa() {
        return personaFumaDentroCasa;
    }

    public void setPersonaFumaDentroCasa(String personaFumaDentroCasa) {
        this.personaFumaDentroCasa = personaFumaDentroCasa;
    }

    public String getTieneRecoleccionBasura() {
        return tieneRecoleccionBasura;
    }

    public void setTieneRecoleccionBasura(String tieneRecoleccionBasura) {
        this.tieneRecoleccionBasura = tieneRecoleccionBasura;
    }

    public Integer getCuantasVecesRecBasura() {
        return cuantasVecesRecBasura;
    }

    public void setCuantasVecesRecBasura(Integer cuantasVecesRecBasura) {
        this.cuantasVecesRecBasura = cuantasVecesRecBasura;
    }

    public String getDondePasaRecBasura() {
        return dondePasaRecBasura;
    }

    public void setDondePasaRecBasura(String dondePasaRecBasura) {
        this.dondePasaRecBasura = dondePasaRecBasura;
    }

    public String getDondePasaRecBasuraOtros() {
        return dondePasaRecBasuraOtros;
    }

    public void setDondePasaRecBasuraOtros(String dondePasaRecBasuraOtros) {
        this.dondePasaRecBasuraOtros = dondePasaRecBasuraOtros;
    }

    public String getVistoAnimalesSalvajes() {
        return vistoAnimalesSalvajes;
    }

    public void setVistoAnimalesSalvajes(String vistoAnimalesSalvajes) {
        this.vistoAnimalesSalvajes = vistoAnimalesSalvajes;
    }
    public String getVistoAnimalesSalvajesOtros() {
        return vistoAnimalesSalvajesOtros;
    }

    public void setVistoAnimalesSalvajesOtros(String vistoAnimalesSalvajesOtros) {
        this.vistoAnimalesSalvajesOtros = vistoAnimalesSalvajesOtros;
    }

    @Override
    public String toString() {
        return "EncuestaCasa{" + casa.getCodigo() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaCasa)) return false;

        EncuestaCasa that = (EncuestaCasa) o;

        return  (!casa.equals(that.casa));
    }

    @Override
    public int hashCode() {
        return casa.hashCode();
    }
}
