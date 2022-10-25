package ni.org.ics.a2cares.app.entomologia.forms;

import android.content.res.Resources;
import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

public class CuestionarioHogarFormLabels {

    protected String fechaCuestionario;
    protected String barrio;
    protected String direccion;
    protected String puntoGps;
    protected String tipoIngresoCodigo;
    protected String codigoVivienda;
    protected String codigoViviendaBr;
    protected String tipoVivienda;
    protected String hayAmbientePERI;
    protected String horaCapturaPERI;
    protected String humedadRelativaPERI;
    protected String temperaturaPERI;
    protected String tipoIngresoCodigoPERI;
    protected String codigoPERI;
    protected String codigoPERIBr;
    protected String hayAmbienteINTRA;
    protected String horaCapturaINTRA;
    protected String humedadRelativaINTRA;
    protected String temperaturaINTRA;
    protected String tipoIngresoCodigoINTRA;
    protected String codigoINTRA;
    protected String codigoINTRABr;

    protected String quienContesta;
    protected String quienContestaOtro;
    protected String edadContest;
    protected String escolaridadContesta;
    protected String tiempoVivirBarrio;
    protected String cuantasPersonasViven;
    protected String edadMujeres;
    protected String edadHombres;
    protected String edadHint;
    protected String usaronMosquitero;
    protected String quienesUsaronMosquitero;
    protected String usaronRepelente;
    protected String quienesUsaronRepelente;
    protected String conoceLarvas;
    protected String alguienVisEliminarLarvas;
    protected String quienVisEliminarLarvas;
    protected String quienVisEliminarLarvasOtro;
    protected String alguienDedicaElimLarvasCasa;
    protected String quienDedicaElimLarvasCasa;
    protected String tiempoElimanCridaros;
    protected String hayBastanteZancudos;
    protected String queFaltaCasaEvitarZancudos;
    protected String queFaltaCasaEvitarZancudosOtros;
    protected String gastaronDineroProductos;
    protected String queProductosCompraron;
    protected String queProductosCompraronOtros;
    protected String cuantoGastaron;
    protected String ultimaVisitaMinsaBTI;
    protected String ultimaVezMinsaFumigo;
    protected String riesgoCasaDengue;
    protected String problemasAbastecimiento;
    protected String cadaCuantoVaAgua;
    protected String cadaCuantoVaAguaOtro;
    protected String horasSinAguaDia;
    protected String queHacenBasuraHogar;
    protected String queHacenBasuraHogarOtro;
    protected String riesgoBarrioDengue;
    protected String accionesCriaderosBarrio;
    protected String queAcciones;
    protected String queAccionesOtro;
    protected String mayorCriaderoBarrio;
    protected String alguienParticipo;
    protected String quienParticipo;
    protected String materialParedes;
    protected String materialPiso;
    protected String materialTecho;
    protected String otroMaterialParedes;
    protected String otroMaterialPiso;
    protected String otroMaterialTecho;


    public CuestionarioHogarFormLabels(){
        Resources res = MyIcsApplication.getContext().getResources();

        fechaCuestionario = res.getString(R.string.fechaCuestionario);
        barrio = res.getString(R.string.barrioEnto);
        direccion = res.getString(R.string.direccionEnto);
        puntoGps = res.getString(R.string.puntoGpsEnto);
        tipoIngresoCodigo = res.getString(R.string.tipoIngresoCodigo);
        codigoVivienda = res.getString(R.string.codigoVivienda);
        codigoViviendaBr = res.getString(R.string.codigoViviendaBr);
        tipoVivienda = res.getString(R.string.tipoViviendaEnto);
        hayAmbientePERI = res.getString(R.string.hayAmbientePERI);
        horaCapturaPERI = res.getString(R.string.horaCapturaPERI);
        humedadRelativaPERI = res.getString(R.string.humedadRelativaPERI);
        temperaturaPERI = res.getString(R.string.temperaturaPERI);
        tipoIngresoCodigoPERI = res.getString(R.string.tipoIngresoCodigoPERI);
        codigoPERI = res.getString(R.string.codigoPERI);
        codigoPERIBr = res.getString(R.string.codigoPERIBr);
        hayAmbienteINTRA = res.getString(R.string.hayAmbienteINTRA);
        horaCapturaINTRA = res.getString(R.string.horaCapturaINTRA);
        humedadRelativaINTRA = res.getString(R.string.humedadRelativaINTRA);
        temperaturaINTRA = res.getString(R.string.temperaturaINTRA);
        tipoIngresoCodigoINTRA = res.getString(R.string.tipoIngresoCodigoINTRA);
        codigoINTRA = res.getString(R.string.codigoINTRA);
        codigoINTRABr = res.getString(R.string.codigoINTRABr);

        quienContesta = res.getString(R.string.quienContesta);
        quienContestaOtro = res.getString(R.string.quienContestaOtro);
        edadContest = res.getString(R.string.edadContest);
        escolaridadContesta = res.getString(R.string.escolaridadContesta);
        tiempoVivirBarrio = res.getString(R.string.tiempoVivirBarrio);
        cuantasPersonasViven = res.getString(R.string.cuantasPersonasViven);
        edadMujeres = res.getString(R.string.edadMujeres);
        edadHombres = res.getString(R.string.edadHombres);
        edadHint = res.getString(R.string.edadHint);
        usaronMosquitero = res.getString(R.string.usaronMosquitero);
        quienesUsaronMosquitero = res.getString(R.string.quienesUsaronMosquitero);
        usaronRepelente = res.getString(R.string.usaronRepelente);
        quienesUsaronRepelente = res.getString(R.string.quienesUsaronRepelente);
        conoceLarvas = res.getString(R.string.conoceLarvasEnto);
        alguienVisEliminarLarvas = res.getString(R.string.alguienVisEliminarLarvas);
        quienVisEliminarLarvas = res.getString(R.string.quienVisEliminarLarvas);
        quienVisEliminarLarvasOtro = res.getString(R.string.quienVisEliminarLarvasOtro);
        alguienDedicaElimLarvasCasa = res.getString(R.string.alguienDedicaElimLarvasCasa);
        quienDedicaElimLarvasCasa = res.getString(R.string.quienDedicaElimLarvasCasa);
        tiempoElimanCridaros = res.getString(R.string.tiempoElimanCridaros);
        hayBastanteZancudos = res.getString(R.string.hayBastanteZancudos);
        queFaltaCasaEvitarZancudos = res.getString(R.string.queFaltaCasaEvitarZancudos);
        queFaltaCasaEvitarZancudosOtros = res.getString(R.string.queFaltaCasaEvitarZancudosOtros);
        gastaronDineroProductos = res.getString(R.string.gastaronDineroProductos);
        queProductosCompraron = res.getString(R.string.queProductosCompraron);
        queProductosCompraronOtros = res.getString(R.string.queProductosCompraronOtros);
        cuantoGastaron = res.getString(R.string.cuantoGastaron);
        ultimaVisitaMinsaBTI = res.getString(R.string.ultimaVisitaMinsaBTI);
        ultimaVezMinsaFumigo = res.getString(R.string.ultimaVezMinsaFumigo);
        riesgoCasaDengue = res.getString(R.string.riesgoCasaDengue);
        problemasAbastecimiento = res.getString(R.string.problemasAbastecimiento);
        cadaCuantoVaAgua = res.getString(R.string.cadaCuantoVaAgua);
        cadaCuantoVaAguaOtro = res.getString(R.string.cadaCuantoVaAguaOtro);
        horasSinAguaDia = res.getString(R.string.horasSinAguaDia);
        queHacenBasuraHogar = res.getString(R.string.queHacenBasuraHogar);
        queHacenBasuraHogarOtro = res.getString(R.string.queHacenBasuraHogarOtro);
        riesgoBarrioDengue = res.getString(R.string.riesgoBarrioDengue);
        accionesCriaderosBarrio = res.getString(R.string.accionesCriaderosBarrio);
        queAcciones = res.getString(R.string.queAcciones);
        queAccionesOtro = res.getString(R.string.queAccionesOtro);
        alguienParticipo = res.getString(R.string.alguienParticipo);
        quienParticipo = res.getString(R.string.quienParticipo);
        mayorCriaderoBarrio = res.getString(R.string.mayorCriaderoBarrio);

        materialParedes = res.getString(R.string.materialParedesEnto);
        materialPiso = res.getString(R.string.materialPisoEnto);
        materialTecho = res.getString(R.string.materialTechoEnto);
        otroMaterialParedes = res.getString(R.string.otroMaterialParedesEnto);
        otroMaterialPiso = res.getString(R.string.otroMaterialPisoEnto);
        otroMaterialTecho = res.getString(R.string.otroMaterialTechoEnto);

    }

    public String getFechaCuestionario() {
        return fechaCuestionario;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPuntoGps() {
        return puntoGps;
    }

    public String getTipoIngresoCodigo() {
        return tipoIngresoCodigo;
    }

    public String getCodigoVivienda() {
        return codigoVivienda;
    }

    public String getCodigoViviendaBr() {
        return codigoViviendaBr;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public String getHayAmbientePERI() {
        return hayAmbientePERI;
    }

    public String getHoraCapturaPERI() {
        return horaCapturaPERI;
    }

    public String getHumedadRelativaPERI() {
        return humedadRelativaPERI;
    }

    public String getTemperaturaPERI() {
        return temperaturaPERI;
    }

    public String getTipoIngresoCodigoPERI() {
        return tipoIngresoCodigoPERI;
    }

    public String getCodigoPERI() {
        return codigoPERI;
    }

    public String getCodigoPERIBr() {
        return codigoPERIBr;
    }

    public String getHayAmbienteINTRA() {
        return hayAmbienteINTRA;
    }

    public String getHoraCapturaINTRA() {
        return horaCapturaINTRA;
    }

    public String getHumedadRelativaINTRA() {
        return humedadRelativaINTRA;
    }

    public String getTemperaturaINTRA() {
        return temperaturaINTRA;
    }

    public String getTipoIngresoCodigoINTRA() {
        return tipoIngresoCodigoINTRA;
    }

    public String getCodigoINTRA() {
        return codigoINTRA;
    }

    public String getCodigoINTRABr() {
        return codigoINTRABr;
    }

    public String getQuienContesta() {
        return quienContesta;
    }

    public String getQuienContestaOtro() {
        return quienContestaOtro;
    }

    public String getEdadContest() {
        return edadContest;
    }

    public String getEscolaridadContesta() {
        return escolaridadContesta;
    }

    public String getTiempoVivirBarrio() {
        return tiempoVivirBarrio;
    }

    public String getCuantasPersonasViven() {
        return cuantasPersonasViven;
    }

    public String getEdadMujeres() {
        return edadMujeres;
    }

    public String getEdadHombres() {
        return edadHombres;
    }

    public String getEdadHint() {
        return edadHint;
    }

    public String getUsaronMosquitero() {
        return usaronMosquitero;
    }

    public String getQuienesUsaronMosquitero() {
        return quienesUsaronMosquitero;
    }

    public String getUsaronRepelente() {
        return usaronRepelente;
    }

    public String getQuienesUsaronRepelente() {
        return quienesUsaronRepelente;
    }

    public String getConoceLarvas() {
        return conoceLarvas;
    }

    public String getAlguienVisEliminarLarvas() {
        return alguienVisEliminarLarvas;
    }

    public String getQuienVisEliminarLarvas() {
        return quienVisEliminarLarvas;
    }

    public String getQuienVisEliminarLarvasOtro() {
        return quienVisEliminarLarvasOtro;
    }

    public String getAlguienDedicaElimLarvasCasa() {
        return alguienDedicaElimLarvasCasa;
    }

    public String getQuienDedicaElimLarvasCasa() {
        return quienDedicaElimLarvasCasa;
    }

    public String getTiempoElimanCridaros() {
        return tiempoElimanCridaros;
    }

    public String getHayBastanteZancudos() {
        return hayBastanteZancudos;
    }

    public String getQueFaltaCasaEvitarZancudos() {
        return queFaltaCasaEvitarZancudos;
    }

    public String getQueFaltaCasaEvitarZancudosOtros() {
        return queFaltaCasaEvitarZancudosOtros;
    }

    public String getGastaronDineroProductos() {
        return gastaronDineroProductos;
    }

    public String getQueProductosCompraron() {
        return queProductosCompraron;
    }

    public String getQueProductosCompraronOtros() {
        return queProductosCompraronOtros;
    }

    public String getCuantoGastaron() {
        return cuantoGastaron;
    }

    public String getUltimaVisitaMinsaBTI() {
        return ultimaVisitaMinsaBTI;
    }

    public String getUltimaVezMinsaFumigo() {
        return ultimaVezMinsaFumigo;
    }

    public String getRiesgoCasaDengue() {
        return riesgoCasaDengue;
    }

    public String getProblemasAbastecimiento() {
        return problemasAbastecimiento;
    }

    public String getCadaCuantoVaAgua() {
        return cadaCuantoVaAgua;
    }

    public String getCadaCuantoVaAguaOtro() {
        return cadaCuantoVaAguaOtro;
    }

    public String getHorasSinAguaDia() {
        return horasSinAguaDia;
    }

    public String getQueHacenBasuraHogar() {
        return queHacenBasuraHogar;
    }

    public String getQueHacenBasuraHogarOtro() {
        return queHacenBasuraHogarOtro;
    }

    public String getRiesgoBarrioDengue() {
        return riesgoBarrioDengue;
    }

    public String getAccionesCriaderosBarrio() {
        return accionesCriaderosBarrio;
    }

    public String getQueAcciones() {
        return queAcciones;
    }

    public String getQueAccionesOtro() {
        return queAccionesOtro;
    }

    public String getMayorCriaderoBarrio() {
        return mayorCriaderoBarrio;
    }

    public String getAlguienParticipo() {
        return alguienParticipo;
    }

    public String getQuienParticipo() {
        return quienParticipo;
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
}
