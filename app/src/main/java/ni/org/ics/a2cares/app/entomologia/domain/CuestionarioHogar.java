package ni.org.ics.a2cares.app.entomologia.domain;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by miguel on 15/8/2022.
 */
public class CuestionarioHogar  extends BaseMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoEncuesta;

    private Date fechaCuestionario;
    private Integer barrio;
    private String direccion;
    private Double latitud;
    private Double longitud;

    private String tipoIngresoCodigo;
    private String codigoVivienda;
    private String tipoVivienda;

    private String hayAmbientePERI;
    private String horaCapturaPERI;
    private Double humedadRelativaPERI;
    private Double temperaturaPERI;
    private String tipoIngresoCodigoPERI;
    private String codigoPERI;

    private String hayAmbienteINTRA;
    private String horaCapturaINTRA;
    private Double humedadRelativaINTRA;
    private Double temperaturaINTRA;
    private String tipoIngresoCodigoINTRA;
    private String codigoINTRA;

    //00.	Código de la casa

    //01.	¿Quién está contestando éste cuestionario?
    private String quienContesta;
    private String quienContestaOtro;

    //02.	Me podría decir ¿Qué edad tiene usted?
    private String edadContest;

    //03.	¿Cuál es su último año aprobado?
    private String escolaridadContesta;

    //04.	¿Cuánto tiempo tienen de vivir en este barrio?
    private String tiempoVivirBarrio;

    //05.	 ¿Cuántas personas viven en esta casa?
    private int cuantasPersonasViven;

    //06.	¿Qué edad tienen las niñas y mujeres que viven en esta casa, me    puede decir sus edades de menor a mayor?
    private String edadesFemenino;

    //07.	¿Qué edad tienen los niños y hombres que viven en esta casa, me puede decir sus edades de menor a mayor?
    private String edadesMasculino;

    //08.	De todas las personas que me mencionó, ¿Alguna de ellas usó mosquitero para dormir el día de ayer?
    private String usaronMosquitero;
    private String quienesUsaronMosquitero;

    //09.	De todas las personas que me mencionó, ¿Alguna de ellas usó repelente en su piel, para protegerse de los zancudos el día de ayer?
    private String usaronRepelente;
    private String quienesUsaronRepelente;

    //10.	¿Conoce Usted las larvas o clavitos de los zancudos?
    private String conoceLarvas;

    //11.	¿Alguien les ha visitado para enseñarles como buscar y eliminar las larvas o clavitos de los zancudos?
    private String alguienVisEliminarLarvas;

    //12.	¿Quién?
    private String quienVisEliminarLarvas;
    private String quienVisEliminarLarvasOtro;

    //13. ¿Alguien de esta casa dedica tiempo para buscar y eliminar criaderos de zancudos aquí en su casa?
    private String alguienDedicaElimLarvasCasa;

    //14.	¿Quién?
    private String quienDedicaElimLarvasCasa;

    //15. ¿Cada cuánto tiempo buscan y eliminan criaderos de zancudos aquí en su casa?
    private String tiempoElimanCridaros;

    //16. ¿Hay bastante zancudos aquí en su casa?
    private String hayBastanteZancudos;

    //17.	¿Qué hace falta en esta casa para evitar los criaderos de zancudos?
    private String queFaltaCasaEvitarZancudos;
    private String queFaltaCasaEvitarZancudosOtros;

    //18.	El mes pasado, gastaron dinero en compra de productos para evitar los Zancudos?
    private String gastaronDineroProductos;

    //19.	¿Qué productos compraron?
    private String queProductosCompraron;
    private String queProductosCompraronOtros;

    //20.	¿Cuánto gastaron?
    private String cuantoGastaron;

    //21	¿Cuándo fue la última vez que el MINSA visitó su casa para aplicar BTI en sus recipientes con agua?
    private String ultimaVisitaMinsaBTI;

    //22	¿Cuándo fue la última vez que el MINSA fumigó su casa?
    private String ultimaVezMinsaFumigo;

    //23 ¿Qué tanto riesgo hay en su casa de enfermar por el virus del dengue?
    private String riesgoCasaDengue;

    //24.	¿Hay problemas con el abastecimiento de agua en este barrio?
    private String problemasAbastecimiento;

    //25.	¿Cada cuánto se les va el agua?
    private String cadaCuantoVaAgua;
    private String cadaCuantoVaAguaOtro;

    //26.	¿Cuantas horas al día se les va?
    private int horasSinAguaDia;

    //27.	¿Qué hacen con la basura del hogar?
    private String queHacenBasuraHogar;
    private String queHacenBasuraHogarOtro;

    //28.	¿Qué tanto riesgo hay en este barrio de enfermar por el virus del dengue?
    private String riesgoBarrioDengue;

    //29.	¿En los últimos tres meses, en este barrio han realizado acciones para eliminar criaderos de zancudos del barrio?
    private String accionesCriaderosBarrio;

    //30.	¿Qué acciones realizaron?
    private String queAcciones;
    private String queAccionesOtro;

    //31.	Alguien de la casa participó en esa actividad?
    private String alguienParticipo;

    //32 Quién?
    private String quienParticipo;

    //33.	¿Cuál es el mayor criadero de Zancudos de este barrio?
    private String mayorCriaderoBarrio;

    private String materialParedes;
    private String materialPiso;
    private String materialTecho;
    private String otroMaterialParedes;
    private String otroMaterialPiso;
    private String otroMaterialTecho;

    public Date getFechaCuestionario() {
        return fechaCuestionario;
    }

    public void setFechaCuestionario(Date fechaCuestionario) {
        this.fechaCuestionario = fechaCuestionario;
    }

    public Integer getBarrio() {
        return barrio;
    }

    public void setBarrio(Integer barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getTipoIngresoCodigo() {
        return tipoIngresoCodigo;
    }

    public void setTipoIngresoCodigo(String tipoIngresoCodigo) {
        this.tipoIngresoCodigo = tipoIngresoCodigo;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getHayAmbientePERI() {
        return hayAmbientePERI;
    }

    public void setHayAmbientePERI(String hayAmbientePERI) {
        this.hayAmbientePERI = hayAmbientePERI;
    }

    public String getHoraCapturaPERI() {
        return horaCapturaPERI;
    }

    public void setHoraCapturaPERI(String horaCapturaPERI) {
        this.horaCapturaPERI = horaCapturaPERI;
    }

    public Double getHumedadRelativaPERI() {
        return humedadRelativaPERI;
    }

    public void setHumedadRelativaPERI(Double humedadRelativaPERI) {
        this.humedadRelativaPERI = humedadRelativaPERI;
    }

    public Double getTemperaturaPERI() {
        return temperaturaPERI;
    }

    public void setTemperaturaPERI(Double temperaturaPERI) {
        this.temperaturaPERI = temperaturaPERI;
    }

    public String getTipoIngresoCodigoPERI() {
        return tipoIngresoCodigoPERI;
    }

    public void setTipoIngresoCodigoPERI(String tipoIngresoCodigoPERI) {
        this.tipoIngresoCodigoPERI = tipoIngresoCodigoPERI;
    }

    public String getCodigoPERI() {
        return codigoPERI;
    }

    public void setCodigoPERI(String codigoPERI) {
        this.codigoPERI = codigoPERI;
    }

    public String getHayAmbienteINTRA() {
        return hayAmbienteINTRA;
    }

    public void setHayAmbienteINTRA(String hayAmbienteINTRA) {
        this.hayAmbienteINTRA = hayAmbienteINTRA;
    }

    public String getHoraCapturaINTRA() {
        return horaCapturaINTRA;
    }

    public void setHoraCapturaINTRA(String horaCapturaINTRA) {
        this.horaCapturaINTRA = horaCapturaINTRA;
    }

    public Double getHumedadRelativaINTRA() {
        return humedadRelativaINTRA;
    }

    public void setHumedadRelativaINTRA(Double humedadRelativaINTRA) {
        this.humedadRelativaINTRA = humedadRelativaINTRA;
    }

    public Double getTemperaturaINTRA() {
        return temperaturaINTRA;
    }

    public void setTemperaturaINTRA(Double temperaturaINTRA) {
        this.temperaturaINTRA = temperaturaINTRA;
    }

    public String getTipoIngresoCodigoINTRA() {
        return tipoIngresoCodigoINTRA;
    }

    public void setTipoIngresoCodigoINTRA(String tipoIngresoCodigoINTRA) {
        this.tipoIngresoCodigoINTRA = tipoIngresoCodigoINTRA;
    }

    public String getCodigoINTRA() {
        return codigoINTRA;
    }

    public void setCodigoINTRA(String codigoINTRA) {
        this.codigoINTRA = codigoINTRA;
    }

    public String getCodigoEncuesta() {
        return codigoEncuesta;
    }

    public void setCodigoEncuesta(String codigoEncuesta) {
        this.codigoEncuesta = codigoEncuesta;
    }

    public String getCodigoVivienda() {
        return codigoVivienda;
    }

    public void setCodigoVivienda(String codigoVivienda) {
        this.codigoVivienda = codigoVivienda;
    }

    public String getQuienContesta() {
        return quienContesta;
    }

    public void setQuienContesta(String quienContesta) {
        this.quienContesta = quienContesta;
    }

    public String getQuienContestaOtro() {
        return quienContestaOtro;
    }

    public void setQuienContestaOtro(String quienContestaOtro) {
        this.quienContestaOtro = quienContestaOtro;
    }

    public String getEdadContest() {
        return edadContest;
    }

    public void setEdadContest(String edadContest) {
        this.edadContest = edadContest;
    }

    public String getEscolaridadContesta() {
        return escolaridadContesta;
    }

    public void setEscolaridadContesta(String escolaridadContesta) {
        this.escolaridadContesta = escolaridadContesta;
    }

    public String getTiempoVivirBarrio() {
        return tiempoVivirBarrio;
    }

    public void setTiempoVivirBarrio(String tiempoVivirBarrio) {
        this.tiempoVivirBarrio = tiempoVivirBarrio;
    }

    public int getCuantasPersonasViven() {
        return cuantasPersonasViven;
    }

    public void setCuantasPersonasViven(int cuantasPersonasViven) {
        this.cuantasPersonasViven = cuantasPersonasViven;
    }

    public String getUsaronMosquitero() {
        return usaronMosquitero;
    }

    public void setUsaronMosquitero(String usaronMosquitero) {
        this.usaronMosquitero = usaronMosquitero;
    }

    public String getQuienesUsaronMosquitero() {
        return quienesUsaronMosquitero;
    }

    public void setQuienesUsaronMosquitero(String quienesUsaronMosquitero) {
        this.quienesUsaronMosquitero = quienesUsaronMosquitero;
    }

    public String getUsaronRepelente() {
        return usaronRepelente;
    }

    public void setUsaronRepelente(String usaronRepelente) {
        this.usaronRepelente = usaronRepelente;
    }

    public String getQuienesUsaronRepelente() {
        return quienesUsaronRepelente;
    }

    public void setQuienesUsaronRepelente(String quienesUsaronRepelente) {
        this.quienesUsaronRepelente = quienesUsaronRepelente;
    }

    public String getConoceLarvas() {
        return conoceLarvas;
    }

    public void setConoceLarvas(String conoceLarvas) {
        this.conoceLarvas = conoceLarvas;
    }

    public String getAlguienVisEliminarLarvas() {
        return alguienVisEliminarLarvas;
    }

    public void setAlguienVisEliminarLarvas(String alguienVisEliminarLarvas) {
        this.alguienVisEliminarLarvas = alguienVisEliminarLarvas;
    }

    public String getQuienVisEliminarLarvas() {
        return quienVisEliminarLarvas;
    }

    public void setQuienVisEliminarLarvas(String quienVisEliminarLarvas) {
        this.quienVisEliminarLarvas = quienVisEliminarLarvas;
    }

    public String getQuienVisEliminarLarvasOtro() {
        return quienVisEliminarLarvasOtro;
    }

    public void setQuienVisEliminarLarvasOtro(String quienVisEliminarLarvasOtro) {
        this.quienVisEliminarLarvasOtro = quienVisEliminarLarvasOtro;
    }

    public String getAlguienDedicaElimLarvasCasa() {
        return alguienDedicaElimLarvasCasa;
    }

    public void setAlguienDedicaElimLarvasCasa(String alguienDedicaElimLarvasCasa) {
        this.alguienDedicaElimLarvasCasa = alguienDedicaElimLarvasCasa;
    }

    public String getQuienDedicaElimLarvasCasa() {
        return quienDedicaElimLarvasCasa;
    }

    public void setQuienDedicaElimLarvasCasa(String quienDedicaElimLarvasCasa) {
        this.quienDedicaElimLarvasCasa = quienDedicaElimLarvasCasa;
    }

    public String getTiempoElimanCridaros() {
        return tiempoElimanCridaros;
    }

    public void setTiempoElimanCridaros(String tiempoElimanCridaros) {
        this.tiempoElimanCridaros = tiempoElimanCridaros;
    }

    public String getHayBastanteZancudos() {
        return hayBastanteZancudos;
    }

    public void setHayBastanteZancudos(String hayBastanteZancudos) {
        this.hayBastanteZancudos = hayBastanteZancudos;
    }

    public String getQueFaltaCasaEvitarZancudos() {
        return queFaltaCasaEvitarZancudos;
    }

    public void setQueFaltaCasaEvitarZancudos(String queFaltaCasaEvitarZancudos) {
        this.queFaltaCasaEvitarZancudos = queFaltaCasaEvitarZancudos;
    }

    public String getQueFaltaCasaEvitarZancudosOtros() {
        return queFaltaCasaEvitarZancudosOtros;
    }

    public void setQueFaltaCasaEvitarZancudosOtros(String queFaltaCasaEvitarZancudosOtros) {
        this.queFaltaCasaEvitarZancudosOtros = queFaltaCasaEvitarZancudosOtros;
    }

    public String getGastaronDineroProductos() {
        return gastaronDineroProductos;
    }

    public void setGastaronDineroProductos(String gastaronDineroProductos) {
        this.gastaronDineroProductos = gastaronDineroProductos;
    }

    public String getQueProductosCompraron() {
        return queProductosCompraron;
    }

    public void setQueProductosCompraron(String queProductosCompraron) {
        this.queProductosCompraron = queProductosCompraron;
    }

    public String getQueProductosCompraronOtros() {
        return queProductosCompraronOtros;
    }

    public void setQueProductosCompraronOtros(String queProductosCompraronOtros) {
        this.queProductosCompraronOtros = queProductosCompraronOtros;
    }

    public String getCuantoGastaron() {
        return cuantoGastaron;
    }

    public void setCuantoGastaron(String cuantoGastaron) {
        this.cuantoGastaron = cuantoGastaron;
    }

    public String getUltimaVisitaMinsaBTI() {
        return ultimaVisitaMinsaBTI;
    }

    public void setUltimaVisitaMinsaBTI(String ultimaVisitaMinsaBTI) {
        this.ultimaVisitaMinsaBTI = ultimaVisitaMinsaBTI;
    }

    public String getUltimaVezMinsaFumigo() {
        return ultimaVezMinsaFumigo;
    }

    public void setUltimaVezMinsaFumigo(String ultimaVezMinsaFumigo) {
        this.ultimaVezMinsaFumigo = ultimaVezMinsaFumigo;
    }

    public String getRiesgoCasaDengue() {
        return riesgoCasaDengue;
    }

    public void setRiesgoCasaDengue(String riesgoCasaDengue) {
        this.riesgoCasaDengue = riesgoCasaDengue;
    }

    public String getProblemasAbastecimiento() {
        return problemasAbastecimiento;
    }

    public void setProblemasAbastecimiento(String problemasAbastecimiento) {
        this.problemasAbastecimiento = problemasAbastecimiento;
    }

    public String getCadaCuantoVaAgua() {
        return cadaCuantoVaAgua;
    }

    public void setCadaCuantoVaAgua(String cadaCuantoVaAgua) {
        this.cadaCuantoVaAgua = cadaCuantoVaAgua;
    }

    public String getCadaCuantoVaAguaOtro() {
        return cadaCuantoVaAguaOtro;
    }

    public void setCadaCuantoVaAguaOtro(String cadaCuantoVaAguaOtro) {
        this.cadaCuantoVaAguaOtro = cadaCuantoVaAguaOtro;
    }

    public int getHorasSinAguaDia() {
        return horasSinAguaDia;
    }

    public void setHorasSinAguaDia(int horasSinAguaDia) {
        this.horasSinAguaDia = horasSinAguaDia;
    }

    public String getQueHacenBasuraHogar() {
        return queHacenBasuraHogar;
    }

    public void setQueHacenBasuraHogar(String queHacenBasuraHogar) {
        this.queHacenBasuraHogar = queHacenBasuraHogar;
    }

    public String getQueHacenBasuraHogarOtro() {
        return queHacenBasuraHogarOtro;
    }

    public void setQueHacenBasuraHogarOtro(String queHacenBasuraHogarOtro) {
        this.queHacenBasuraHogarOtro = queHacenBasuraHogarOtro;
    }

    public String getRiesgoBarrioDengue() {
        return riesgoBarrioDengue;
    }

    public void setRiesgoBarrioDengue(String riesgoBarrioDengue) {
        this.riesgoBarrioDengue = riesgoBarrioDengue;
    }

    public String getAccionesCriaderosBarrio() {
        return accionesCriaderosBarrio;
    }

    public void setAccionesCriaderosBarrio(String accionesCriaderosBarrio) {
        this.accionesCriaderosBarrio = accionesCriaderosBarrio;
    }

    public String getQueAcciones() {
        return queAcciones;
    }

    public void setQueAcciones(String queAcciones) {
        this.queAcciones = queAcciones;
    }

    public String getQueAccionesOtro() {
        return queAccionesOtro;
    }

    public void setQueAccionesOtro(String queAccionesOtro) {
        this.queAccionesOtro = queAccionesOtro;
    }

    public String getMayorCriaderoBarrio() {
        return mayorCriaderoBarrio;
    }

    public void setMayorCriaderoBarrio(String mayorCriaderoBarrio) {
        this.mayorCriaderoBarrio = mayorCriaderoBarrio;
    }

    public String getEdadesFemenino() {
        return edadesFemenino;
    }

    public void setEdadesFemenino(String edadesFemenino) {
        this.edadesFemenino = edadesFemenino;
    }

    public String getEdadesMasculino() {
        return edadesMasculino;
    }

    public void setEdadesMasculino(String edadesMasculino) {
        this.edadesMasculino = edadesMasculino;
    }

    public String getAlguienParticipo() {
        return alguienParticipo;
    }

    public void setAlguienParticipo(String alguienParticipo) {
        this.alguienParticipo = alguienParticipo;
    }

    public String getQuienParticipo() {
        return quienParticipo;
    }

    public void setQuienParticipo(String quienParticipo) {
        this.quienParticipo = quienParticipo;
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

    @Override
    public String toString() {
        return "codigoEncuesta='" + codigoEncuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuestionarioHogar)) return false;

        CuestionarioHogar that = (CuestionarioHogar) o;

        if (!codigoEncuesta.equals(that.codigoEncuesta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return codigoEncuesta.hashCode();
    }
}
