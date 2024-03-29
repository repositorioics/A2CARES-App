package ni.org.ics.a2cares.app.entomologia.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;

import ni.org.ics.a2cares.app.database.helpers.BindStatementHelper;
import ni.org.ics.a2cares.app.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogar;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.entomologia.domain.MovilInfo;

import java.util.Date;

/**
 * Created by Miguel Salinas 22/08/2019.
 * V1.0
 */
public class EntomologiaHelper extends BindStatementHelper {

    public static ContentValues crearCuestionarioHogarContentValues(CuestionarioHogar cuest){
        ContentValues cv = new ContentValues();
        cv.put(EntomologiaBConstants.codigoEncuesta, cuest.getCodigoEncuesta());

        if (cuest.getFechaCuestionario() != null) cv.put(EntomologiaBConstants.fechaCuestionario, cuest.getFechaCuestionario().getTime());
        cv.put(EntomologiaBConstants.barrio, cuest.getBarrio());
        cv.put(EntomologiaBConstants.direccion, cuest.getDireccion());
        cv.put(EntomologiaBConstants.latitud, cuest.getLatitud());
        cv.put(EntomologiaBConstants.longitud, cuest.getLongitud());
        cv.put(EntomologiaBConstants.tipoIngresoCodigo, cuest.getTipoIngresoCodigo());
        cv.put(EntomologiaBConstants.codigoVivienda, cuest.getCodigoVivienda());
        cv.put(EntomologiaBConstants.tipoVivienda, cuest.getTipoVivienda());
        cv.put(EntomologiaBConstants.hayAmbientePERI, cuest.getHayAmbientePERI());
        cv.put(EntomologiaBConstants.horaCapturaPERI, cuest.getHoraCapturaPERI());
        cv.put(EntomologiaBConstants.humedadRelativaPERI, cuest.getHumedadRelativaPERI());
        cv.put(EntomologiaBConstants.temperaturaPERI, cuest.getTemperaturaPERI());
        cv.put(EntomologiaBConstants.tipoIngresoCodigoPERI, cuest.getTipoIngresoCodigoPERI());
        cv.put(EntomologiaBConstants.codigoPERI, cuest.getCodigoPERI());
        cv.put(EntomologiaBConstants.hayAmbienteINTRA, cuest.getHayAmbienteINTRA());
        cv.put(EntomologiaBConstants.horaCapturaINTRA, cuest.getHoraCapturaINTRA());
        cv.put(EntomologiaBConstants.humedadRelativaINTRA, cuest.getHumedadRelativaINTRA());
        cv.put(EntomologiaBConstants.temperaturaINTRA, cuest.getTemperaturaINTRA());
        cv.put(EntomologiaBConstants.tipoIngresoCodigoINTRA, cuest.getTipoIngresoCodigoINTRA());
        cv.put(EntomologiaBConstants.codigoINTRA, cuest.getCodigoINTRA());

        cv.put(EntomologiaBConstants.quienContesta, cuest.getQuienContesta());
        cv.put(EntomologiaBConstants.quienContestaOtro, cuest.getQuienContestaOtro());
        cv.put(EntomologiaBConstants.edadContest, cuest.getEdadContest());
        cv.put(EntomologiaBConstants.escolaridadContesta, cuest.getEscolaridadContesta());
        cv.put(EntomologiaBConstants.tiempoVivirBarrio, cuest.getTiempoVivirBarrio());
        cv.put(EntomologiaBConstants.cuantasPersonasViven, cuest.getCuantasPersonasViven());
        cv.put(EntomologiaBConstants.usaronMosquitero, cuest.getUsaronMosquitero());
        cv.put(EntomologiaBConstants.quienesUsaronMosquitero, cuest.getQuienesUsaronMosquitero());
        cv.put(EntomologiaBConstants.usaronRepelente, cuest.getUsaronRepelente());
        cv.put(EntomologiaBConstants.quienesUsaronRepelente, cuest.getQuienesUsaronRepelente());
        cv.put(EntomologiaBConstants.conoceLarvas, cuest.getConoceLarvas());
        cv.put(EntomologiaBConstants.alguienVisEliminarLarvas, cuest.getAlguienVisEliminarLarvas());
        cv.put(EntomologiaBConstants.quienVisEliminarLarvas, cuest.getQuienVisEliminarLarvas());
        cv.put(EntomologiaBConstants.quienVisEliminarLarvasOtro, cuest.getQuienVisEliminarLarvasOtro());
        cv.put(EntomologiaBConstants.alguienDedicaElimLarvasCasa, cuest.getAlguienDedicaElimLarvasCasa());
        cv.put(EntomologiaBConstants.quienDedicaElimLarvasCasa, cuest.getQuienDedicaElimLarvasCasa());
        cv.put(EntomologiaBConstants.tiempoElimanCridaros, cuest.getTiempoElimanCridaros());
        cv.put(EntomologiaBConstants.hayBastanteZancudos, cuest.getHayBastanteZancudos());
        cv.put(EntomologiaBConstants.queFaltaCasaEvitarZancudos, cuest.getQueFaltaCasaEvitarZancudos());
        cv.put(EntomologiaBConstants.queFaltaCasaEvitarZancudosOtros, cuest.getQueFaltaCasaEvitarZancudosOtros());
        cv.put(EntomologiaBConstants.gastaronDineroProductos, cuest.getGastaronDineroProductos());
        cv.put(EntomologiaBConstants.queProductosCompraron, cuest.getQueProductosCompraron());
        cv.put(EntomologiaBConstants.queProductosCompraronOtros, cuest.getQueProductosCompraronOtros());
        cv.put(EntomologiaBConstants.cuantoGastaron, cuest.getCuantoGastaron());
        cv.put(EntomologiaBConstants.ultimaVisitaMinsaBTI, cuest.getUltimaVisitaMinsaBTI());
        cv.put(EntomologiaBConstants.ultimaVezMinsaFumigo, cuest.getUltimaVezMinsaFumigo());
        cv.put(EntomologiaBConstants.riesgoCasaDengue, cuest.getRiesgoCasaDengue());
        cv.put(EntomologiaBConstants.problemasAbastecimiento, cuest.getProblemasAbastecimiento());
        cv.put(EntomologiaBConstants.cadaCuantoVaAgua, cuest.getCadaCuantoVaAgua());
        cv.put(EntomologiaBConstants.cadaCuantoVaAguaOtro, cuest.getCadaCuantoVaAguaOtro());
        cv.put(EntomologiaBConstants.horasSinAguaDia, cuest.getHorasSinAguaDia());
        cv.put(EntomologiaBConstants.queHacenBasuraHogar, cuest.getQueHacenBasuraHogar());
        cv.put(EntomologiaBConstants.queHacenBasuraHogarOtro, cuest.getQueHacenBasuraHogarOtro());
        cv.put(EntomologiaBConstants.riesgoBarrioDengue, cuest.getRiesgoBarrioDengue());
        cv.put(EntomologiaBConstants.accionesCriaderosBarrio, cuest.getAccionesCriaderosBarrio());
        cv.put(EntomologiaBConstants.queAcciones, cuest.getQueAcciones());
        cv.put(EntomologiaBConstants.queAccionesOtro, cuest.getQueAccionesOtro());
        cv.put(EntomologiaBConstants.mayorCriaderoBarrio, cuest.getMayorCriaderoBarrio());
        cv.put(EntomologiaBConstants.edadesFemenino, cuest.getEdadesFemenino());
        cv.put(EntomologiaBConstants.edadesMasculino, cuest.getEdadesMasculino());
        cv.put(EntomologiaBConstants.alguienParticipo, cuest.getAlguienParticipo());
        cv.put(EntomologiaBConstants.quienParticipo, cuest.getQuienParticipo());
        cv.put(EntomologiaBConstants.materialParedes, cuest.getMaterialParedes());
        cv.put(EntomologiaBConstants.materialPiso, cuest.getMaterialPiso());
        cv.put(EntomologiaBConstants.materialTecho, cuest.getMaterialTecho());
        cv.put(EntomologiaBConstants.otroMaterialParedes, cuest.getOtroMaterialParedes());
        cv.put(EntomologiaBConstants.otroMaterialPiso, cuest.getOtroMaterialPiso());
        cv.put(EntomologiaBConstants.otroMaterialTecho, cuest.getOtroMaterialTecho());
        if (cuest.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuest.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, cuest.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(cuest.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(cuest.getEstado()));
        cv.put(MainDBConstants.deviceId, cuest.getDeviceid());
        return cv;
    }

    public static CuestionarioHogar crearCuestionarioHogar(Cursor cursor){
        CuestionarioHogar cuest = new CuestionarioHogar();
        cuest.setCodigoEncuesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoEncuesta)));

        cuest.setFechaCuestionario(new Date(cursor.getLong(cursor.getColumnIndex(EntomologiaBConstants.fechaCuestionario))));
        cuest.setBarrio(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.barrio)));
        cuest.setDireccion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.direccion)));
        cuest.setLatitud(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.latitud)));
        cuest.setLongitud(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.longitud)));
        cuest.setTipoIngresoCodigo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigo)));
        cuest.setCodigoVivienda(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoVivienda)));
        cuest.setTipoVivienda(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoVivienda)));
        cuest.setHayAmbientePERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayAmbientePERI)));
        cuest.setHoraCapturaPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaCapturaPERI)));

        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaPERI)) > 0) cuest.setHumedadRelativaPERI(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaPERI)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaPERI)) > 0) cuest.setTemperaturaPERI(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaPERI)));

        cuest.setTipoIngresoCodigoPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigoPERI)));
        cuest.setCodigoPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoPERI)));
        cuest.setHayAmbienteINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayAmbienteINTRA)));
        cuest.setHoraCapturaINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaCapturaINTRA)));

        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaINTRA)) > 0) cuest.setHumedadRelativaINTRA(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaINTRA)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaINTRA)) > 0) cuest.setTemperaturaINTRA(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaINTRA)));
        cuest.setTipoIngresoCodigoINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigoINTRA)));
        cuest.setCodigoINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoINTRA)));
        cuest.setQuienContesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienContesta)));
        cuest.setQuienContestaOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienContestaOtro)));
        cuest.setEdadContest(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadContest)));
        cuest.setEscolaridadContesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.escolaridadContesta)));
        cuest.setTiempoVivirBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tiempoVivirBarrio)));
        if(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasViven))>0) cuest.setCuantasPersonasViven(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasViven)));
        cuest.setUsaronMosquitero(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.usaronMosquitero)));
        cuest.setQuienesUsaronMosquitero(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienesUsaronMosquitero)));
        cuest.setUsaronRepelente(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.usaronRepelente)));
        cuest.setQuienesUsaronRepelente(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienesUsaronRepelente)));
        cuest.setConoceLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.conoceLarvas)));
        cuest.setAlguienVisEliminarLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienVisEliminarLarvas)));
        cuest.setQuienVisEliminarLarvas(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienVisEliminarLarvas)));
        cuest.setQuienVisEliminarLarvasOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienVisEliminarLarvasOtro)));
        cuest.setAlguienDedicaElimLarvasCasa(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienDedicaElimLarvasCasa)));
        cuest.setQuienDedicaElimLarvasCasa(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienDedicaElimLarvasCasa)));
        cuest.setTiempoElimanCridaros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tiempoElimanCridaros)));
        cuest.setHayBastanteZancudos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayBastanteZancudos)));
        cuest.setQueFaltaCasaEvitarZancudos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queFaltaCasaEvitarZancudos)));
        cuest.setQueFaltaCasaEvitarZancudosOtros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queFaltaCasaEvitarZancudosOtros)));
        cuest.setGastaronDineroProductos(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.gastaronDineroProductos)));
        cuest.setQueProductosCompraron(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queProductosCompraron)));
        cuest.setQueProductosCompraronOtros(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queProductosCompraronOtros)));
        cuest.setCuantoGastaron(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cuantoGastaron)));
        cuest.setUltimaVisitaMinsaBTI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.ultimaVisitaMinsaBTI)));
        cuest.setUltimaVezMinsaFumigo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.ultimaVezMinsaFumigo)));
        cuest.setRiesgoCasaDengue(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.riesgoCasaDengue)));
        cuest.setProblemasAbastecimiento(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.problemasAbastecimiento)));
        cuest.setCadaCuantoVaAgua(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cadaCuantoVaAgua)));
        cuest.setCadaCuantoVaAguaOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.cadaCuantoVaAguaOtro)));
        if(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.horasSinAguaDia))>0) cuest.setHorasSinAguaDia(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.horasSinAguaDia)));
        cuest.setQueHacenBasuraHogar(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queHacenBasuraHogar)));
        cuest.setQueHacenBasuraHogarOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queHacenBasuraHogarOtro)));
        cuest.setRiesgoBarrioDengue(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.riesgoBarrioDengue)));
        cuest.setAccionesCriaderosBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.accionesCriaderosBarrio)));
        cuest.setQueAcciones(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queAcciones)));
        cuest.setQueAccionesOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.queAccionesOtro)));
        cuest.setMayorCriaderoBarrio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.mayorCriaderoBarrio)));
        cuest.setEdadesFemenino(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadesFemenino)));
        cuest.setEdadesMasculino(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edadesMasculino)));
        cuest.setAlguienParticipo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.alguienParticipo)));
        cuest.setQuienParticipo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.quienParticipo)));

        cuest.setMaterialParedes(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.materialParedes)));
        cuest.setMaterialPiso(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.materialPiso)));
        cuest.setMaterialTecho(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.materialTecho)));
        cuest.setOtroMaterialParedes(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.otroMaterialParedes)));
        cuest.setOtroMaterialPiso(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.otroMaterialPiso)));
        cuest.setOtroMaterialTecho(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.otroMaterialTecho)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cuest.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cuest.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cuest.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cuest.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cuest.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cuest;
    }

    public static void fillCuestionarioHogarStatement(SQLiteStatement stat, CuestionarioHogar part){
        bindString(stat,1, part.getCodigoEncuesta());

        bindDate(stat, 2, part.getFechaCuestionario());
        bindInteger(stat, 3, part.getBarrio());
        bindString(stat, 4, part.getDireccion());
        bindDouble(stat, 5, part.getLatitud());
        bindDouble(stat, 6, part.getLongitud());
        bindString(stat, 7, part.getTipoIngresoCodigo());
        bindString(stat, 8, part.getCodigoVivienda());
        bindString(stat, 9, part.getTipoVivienda());
        bindString(stat, 10, part.getHayAmbientePERI());
        bindString(stat, 11, part.getHoraCapturaPERI());
        bindDouble(stat, 12, part.getHumedadRelativaPERI());
        bindDouble(stat, 13, part.getTemperaturaPERI());
        bindString(stat, 14, part.getTipoIngresoCodigoPERI());
        bindString(stat, 15, part.getCodigoPERI());
        bindString(stat, 16, part.getHayAmbienteINTRA());
        bindString(stat, 17, part.getHoraCapturaINTRA());
        bindDouble(stat, 18, part.getHumedadRelativaINTRA());
        bindDouble(stat, 19, part.getTemperaturaINTRA());
        bindString(stat, 20, part.getTipoIngresoCodigoINTRA());
        bindString(stat, 21, part.getCodigoINTRA());

        bindString(stat,22, part.getQuienContesta());
        bindString(stat,23, part.getQuienContestaOtro());
        bindString(stat,24, part.getEdadContest());
        bindString(stat,25, part.getEscolaridadContesta());
        bindString(stat,26, part.getTiempoVivirBarrio());
        bindInteger(stat,27, part.getCuantasPersonasViven());
        bindString(stat,28, part.getUsaronMosquitero());
        bindString(stat,29, part.getQuienesUsaronMosquitero());
        bindString(stat,30, part.getUsaronRepelente());
        bindString(stat,31, part.getQuienesUsaronRepelente());
        bindString(stat,32, part.getConoceLarvas());
        bindString(stat,33, part.getAlguienVisEliminarLarvas());
        bindString(stat,34, part.getQuienVisEliminarLarvas());
        bindString(stat,35, part.getQuienVisEliminarLarvasOtro());
        bindString(stat,36, part.getAlguienDedicaElimLarvasCasa());
        bindString(stat,37, part.getQuienDedicaElimLarvasCasa());
        bindString(stat,38, part.getTiempoElimanCridaros());
        bindString(stat,39, part.getHayBastanteZancudos());
        bindString(stat,40, part.getQueFaltaCasaEvitarZancudos());
        bindString(stat,41, part.getQueFaltaCasaEvitarZancudosOtros());
        bindString(stat,42, part.getGastaronDineroProductos());
        bindString(stat,43, part.getQueProductosCompraron());
        bindString(stat,44, part.getQueProductosCompraronOtros());
        bindString(stat,45, part.getCuantoGastaron());
        bindString(stat,46, part.getUltimaVisitaMinsaBTI());
        bindString(stat,47, part.getUltimaVezMinsaFumigo());
        bindString(stat,48, part.getRiesgoCasaDengue());
        bindString(stat,49, part.getProblemasAbastecimiento());
        bindString(stat,50, part.getCadaCuantoVaAgua());
        bindString(stat,51, part.getCadaCuantoVaAguaOtro());
        bindInteger(stat,52, part.getHorasSinAguaDia());
        bindString(stat,53, part.getQueHacenBasuraHogar());
        bindString(stat,54, part.getQueHacenBasuraHogarOtro());
        bindString(stat,55, part.getRiesgoBarrioDengue());
        bindString(stat,56, part.getAccionesCriaderosBarrio());
        bindString(stat,57, part.getQueAcciones());
        bindString(stat,58, part.getQueAccionesOtro());
        bindString(stat,59, part.getMayorCriaderoBarrio());

        bindString(stat,60, part.getEdadesFemenino());
        bindString(stat,61, part.getEdadesMasculino());
        bindString(stat,62, part.getAlguienParticipo());
        bindString(stat,63, part.getQuienParticipo());

        bindString(stat,64, part.getMaterialParedes());
        bindString(stat,65, part.getMaterialPiso());
        bindString(stat,66, part.getMaterialTecho());
        bindString(stat,67, part.getOtroMaterialParedes());
        bindString(stat,68, part.getOtroMaterialPiso());
        bindString(stat,69, part.getOtroMaterialTecho());

        bindDate(stat,70, part.getRecordDate());
        bindString(stat,71, part.getRecordUser());
        stat.bindString(72, String.valueOf(part.getPasive()));
        bindString(stat,73, part.getDeviceid());
        stat.bindString(74, String.valueOf(part.getEstado()));
    }


    public static ContentValues crearCuestionarioHogarPoblacionContentValues(CuestionarioHogarPoblacion cuest){
        ContentValues cv = new ContentValues();
        cv.put(EntomologiaBConstants.codigoPoblacion, cuest.getCodigoPoblacion());
        cv.put(EntomologiaBConstants.codigoEncuesta, cuest.getCodigoEncuesta());
        cv.put(EntomologiaBConstants.codificado, cuest.getCodificado());
        cv.put(EntomologiaBConstants.edad, cuest.getEdad());
        cv.put(EntomologiaBConstants.sexo, cuest.getSexo());

        if (cuest.getRecordDate() != null) cv.put(MainDBConstants.recordDate, cuest.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, cuest.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(cuest.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(cuest.getEstado()));
        cv.put(MainDBConstants.deviceId, cuest.getDeviceid());
        return cv;
    }

    public static CuestionarioHogarPoblacion crearCuestionarioHogarPoblacion(Cursor cursor){
        CuestionarioHogarPoblacion cuest = new CuestionarioHogarPoblacion();
        cuest.setCodigoPoblacion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoPoblacion)));
        cuest.setCodigoEncuesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoEncuesta)));
        cuest.setCodificado(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codificado)));
        cuest.setEdad(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.edad)));
        cuest.setSexo(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.sexo)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) cuest.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        cuest.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        cuest.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        cuest.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        cuest.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return cuest;
    }

    public static void fillCuestionarioHogarPoblacionStatement(SQLiteStatement stat, CuestionarioHogarPoblacion part){
        bindString(stat,1, part.getCodigoPoblacion());
        bindString(stat,2, part.getCodigoEncuesta());
        bindString(stat,3, part.getCodificado());
        bindString(stat,4, part.getEdad());
        bindString(stat,5, part.getSexo());

        bindDate(stat,6, part.getRecordDate());
        bindString(stat,7, part.getRecordUser());
        stat.bindString(8, String.valueOf(part.getPasive()));
        bindString(stat,9, part.getDeviceid());
        stat.bindString(10, String.valueOf(part.getEstado()));
    }

    public static ContentValues crearCuestionarioPuntoClaveContentValues(CuestionarioPuntoClave cuest){
        ContentValues cv = new ContentValues();
        cv.put(EntomologiaBConstants.codigoCuestionario, cuest.getCodigoCuestionario());
        if (cuest.getFechaCuestionario() != null) cv.put(EntomologiaBConstants.fechaCuestionario, cuest.getFechaCuestionario().getTime());
        cv.put(EntomologiaBConstants.barrio, cuest.getBarrio());
        cv.put(EntomologiaBConstants.nombrePuntoClave, cuest.getNombrePuntoClave());
        cv.put(EntomologiaBConstants.direccionPuntoClave, cuest.getDireccionPuntoClave());
        cv.put(EntomologiaBConstants.tipoPuntoClave, cuest.getTipoPuntoClave());
        cv.put(EntomologiaBConstants.tipoPuntoProductividad, cuest.getTipoPuntoProductividad());
        cv.put(EntomologiaBConstants.tipoPuntoProductividadOtro, cuest.getTipoPuntoProductividadOtro());
        cv.put(EntomologiaBConstants.tipoPuntoAglomeracion, cuest.getTipoPuntoAglomeracion());
        cv.put(EntomologiaBConstants.tipoPuntoAglomeracionOtro, cuest.getTipoPuntoAglomeracionOtro());
        if (cuest.getCuantasPersonasReunen() != null) cv.put(EntomologiaBConstants.cuantasPersonasReunen, cuest.getCuantasPersonasReunen());
        if (cuest.getCuantosDiasSemanaReunen() != null) cv.put(EntomologiaBConstants.cuantosDiasSemanaReunen, cuest.getCuantosDiasSemanaReunen());
        cv.put(EntomologiaBConstants.horaInicioReunion, cuest.getHoraInicioReunion());
        cv.put(EntomologiaBConstants.horaFinReunion, cuest.getHoraFinReunion());
        cv.put(EntomologiaBConstants.puntoGps, cuest.getPuntoGps());
        if (cuest.getLatitud() != null) cv.put(EntomologiaBConstants.latitud, cuest.getLatitud());
        if (cuest.getLongitud() != null) cv.put(EntomologiaBConstants.longitud, cuest.getLongitud());

        cv.put(EntomologiaBConstants.tipoIngresoCodigoSitio, cuest.getTipoIngresoCodigoSitio());
        cv.put(EntomologiaBConstants.codigoSitio, cuest.getCodigoSitio());

        cv.put(EntomologiaBConstants.hayAmbientePERI, cuest.getHayAmbientePERI());
        cv.put(EntomologiaBConstants.horaCapturaPERI, cuest.getHoraCapturaPERI());
        //if (cuest.getHumedadRelativaPERI() != null && cuest.getHumedadRelativaPERI().toString().length() == 2) cv.put(EntomologiaBConstants.humedadRelativaPERI, cuest.getHumedadRelativaPERI());
        if (cuest.getHumedadRelativaPERI() != null) cv.put(EntomologiaBConstants.humedadRelativaPERI, cuest.getHumedadRelativaPERI());
        if (cuest.getTemperaturaPERI() != null) cv.put(EntomologiaBConstants.temperaturaPERI, cuest.getTemperaturaPERI());
        cv.put(EntomologiaBConstants.tipoIngresoCodigoPERI, cuest.getTipoIngresoCodigoPERI());
        cv.put(EntomologiaBConstants.codigoPERI, cuest.getCodigoPERI());

        cv.put(EntomologiaBConstants.hayAmbienteINTRA, cuest.getHayAmbienteINTRA());
        cv.put(EntomologiaBConstants.horaCapturaINTRA, cuest.getHoraCapturaINTRA());
        if (cuest.getHumedadRelativaINTRA() != null) cv.put(EntomologiaBConstants.humedadRelativaINTRA, cuest.getHumedadRelativaINTRA());
        if (cuest.getTemperaturaINTRA() != null) cv.put(EntomologiaBConstants.temperaturaINTRA, cuest.getTemperaturaINTRA());
        cv.put(EntomologiaBConstants.tipoIngresoCodigoINTRA, cuest.getTipoIngresoCodigoINTRA());
        cv.put(EntomologiaBConstants.codigoINTRA, cuest.getCodigoINTRA());

        cv.put(EntomologiaBConstants.nombrePersonaContesta, cuest.getNombrePersonaContesta());

        cv.put(EntomologiaBConstants.ID_INSTANCIA, cuest.getMovilInfo().getIdInstancia());
        cv.put(EntomologiaBConstants.FILE_PATH, cuest.getMovilInfo().getInstancePath());
        cv.put(EntomologiaBConstants.STATUS, cuest.getMovilInfo().getEstado());
        cv.put(EntomologiaBConstants.WHEN_UPDATED, cuest.getMovilInfo().getUltimoCambio());
        cv.put(EntomologiaBConstants.START, cuest.getMovilInfo().getStart());
        cv.put(EntomologiaBConstants.END, cuest.getMovilInfo().getEnd());
        cv.put(EntomologiaBConstants.DEVICE_ID, cuest.getMovilInfo().getDeviceid());
        cv.put(EntomologiaBConstants.SIM_SERIAL, cuest.getMovilInfo().getSimserial());
        cv.put(EntomologiaBConstants.PHONE_NUMBER, cuest.getMovilInfo().getPhonenumber());
        cv.put(EntomologiaBConstants.TODAY, cuest.getMovilInfo().getToday().getTime());
        cv.put(EntomologiaBConstants.USUARIO, cuest.getMovilInfo().getUsername());
        cv.put(EntomologiaBConstants.DELETED, cuest.getMovilInfo().getEliminado());
        
        return cv;
    }

    public static void fillCuestionarioPuntoClaveStatement(SQLiteStatement stat, CuestionarioPuntoClave cuest){

        bindString(stat,1, cuest.getCodigoCuestionario());
        bindDate(stat,2, cuest.getFechaCuestionario());
        bindInteger(stat,3, cuest.getBarrio());
        bindString(stat,4, cuest.getNombrePuntoClave());
        bindString(stat,5, cuest.getDireccionPuntoClave());
        bindString(stat,6, cuest.getTipoPuntoClave());
        bindString(stat,7, cuest.getTipoPuntoProductividad());
        bindString(stat,8, cuest.getTipoPuntoProductividadOtro());
        bindString(stat,9, cuest.getTipoPuntoAglomeracion());
        bindString(stat,10, cuest.getTipoPuntoAglomeracionOtro());
        bindInteger(stat,11, cuest.getCuantasPersonasReunen());
        bindInteger(stat,12, cuest.getCuantosDiasSemanaReunen());
        bindString(stat,13, cuest.getHoraInicioReunion());
        bindString(stat,14, cuest.getHoraFinReunion());
        bindString(stat,15, cuest.getPuntoGps());
        bindDouble(stat,16, cuest.getLatitud());
        bindDouble(stat,17, cuest.getLongitud());
        bindString(stat,18, cuest.getTipoIngresoCodigoSitio());
        bindString(stat,19, cuest.getCodigoSitio());
        bindString(stat,20, cuest.getHayAmbientePERI());
        bindString(stat,21, cuest.getHoraCapturaPERI());
        bindDouble(stat,22, cuest.getHumedadRelativaPERI());
        bindDouble(stat,23, cuest.getTemperaturaPERI());
        bindString(stat,24, cuest.getTipoIngresoCodigoPERI());
        bindString(stat,25, cuest.getCodigoPERI());
        bindString(stat,26, cuest.getHayAmbienteINTRA());
        bindString(stat,27, cuest.getHoraCapturaINTRA());
        bindDouble(stat,28, cuest.getHumedadRelativaINTRA());
        bindDouble(stat,29, cuest.getTemperaturaINTRA());
        bindString(stat,30, cuest.getTipoIngresoCodigoINTRA());
        bindString(stat,31, cuest.getCodigoINTRA());
        bindString(stat,32, cuest.getNombrePersonaContesta());

        bindInteger(stat,33, cuest.getMovilInfo().getIdInstancia());
        bindString(stat,34, cuest.getMovilInfo().getInstancePath());
        bindString(stat,35, cuest.getMovilInfo().getEstado());
        bindString(stat,36, cuest.getMovilInfo().getUltimoCambio());
        bindString(stat,37, cuest.getMovilInfo().getStart());
        bindString(stat,38, cuest.getMovilInfo().getEnd());
        bindString(stat,39, cuest.getMovilInfo().getDeviceid());
        bindString(stat,40, cuest.getMovilInfo().getSimserial());
        bindString(stat,41, cuest.getMovilInfo().getPhonenumber());
        bindDate(stat,42, cuest.getMovilInfo().getToday());
        bindString(stat,43, cuest.getMovilInfo().getUsername());
        bindInteger(stat,44, cuest.getMovilInfo().getEliminado().compareTo(true));
    }

    public static CuestionarioPuntoClave crearCuestionarioPuntoClave(Cursor cursor){
        CuestionarioPuntoClave cuest = new CuestionarioPuntoClave();
        cuest.setCodigoCuestionario(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoCuestionario)));
        if(cursor.getLong(cursor.getColumnIndex(EntomologiaBConstants.fechaCuestionario))>0) cuest.setFechaCuestionario(new Date(cursor.getLong(cursor.getColumnIndex(EntomologiaBConstants.fechaCuestionario))));
        cuest.setBarrio(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.barrio)));
        cuest.setNombrePuntoClave(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.nombrePuntoClave)));
        cuest.setDireccionPuntoClave(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.direccionPuntoClave)));
        cuest.setTipoPuntoClave(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoPuntoClave)));
        cuest.setTipoPuntoProductividad(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoPuntoProductividad)));
        cuest.setTipoPuntoProductividadOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoPuntoProductividadOtro)));
        cuest.setTipoPuntoAglomeracion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoPuntoAglomeracion)));
        cuest.setTipoPuntoAglomeracionOtro(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoPuntoAglomeracionOtro)));
        if (cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasReunen))> 0) cuest.setCuantasPersonasReunen(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantasPersonasReunen)));
        if (cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantosDiasSemanaReunen))> 0) cuest.setCuantosDiasSemanaReunen(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.cuantosDiasSemanaReunen)));
        cuest.setHoraInicioReunion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaInicioReunion)));
        cuest.setHoraFinReunion(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaFinReunion)));
        cuest.setPuntoGps(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.puntoGps)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.latitud))!= 0) cuest.setLatitud(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.latitud)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.longitud))!= 0) cuest.setLongitud(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.longitud)));

        cuest.setTipoIngresoCodigoSitio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigoSitio)));
        cuest.setCodigoSitio(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoSitio)));

        cuest.setHayAmbientePERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayAmbientePERI)));
        cuest.setHoraCapturaPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaCapturaPERI)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaPERI))!= 0) cuest.setHumedadRelativaPERI(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaPERI)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaPERI))!= 0) cuest.setTemperaturaPERI(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaPERI)));
        cuest.setTipoIngresoCodigoPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigoPERI)));
        cuest.setCodigoPERI(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoPERI)));

        cuest.setHayAmbienteINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.hayAmbienteINTRA)));
        cuest.setHoraCapturaINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.horaCapturaINTRA)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaINTRA))!= 0) cuest.setHumedadRelativaINTRA(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.humedadRelativaINTRA)));
        if (cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaINTRA))!= 0) cuest.setTemperaturaINTRA(cursor.getDouble(cursor.getColumnIndex(EntomologiaBConstants.temperaturaINTRA)));
        cuest.setTipoIngresoCodigoINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.tipoIngresoCodigoINTRA)));
        cuest.setCodigoINTRA(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.codigoINTRA)));

        cuest.setNombrePersonaContesta(cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.nombrePersonaContesta)));

        cuest.setMovilInfo(new MovilInfo(cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.ID_INSTANCIA)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.FILE_PATH)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.STATUS)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.WHEN_UPDATED)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.START)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.END)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.DEVICE_ID)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.SIM_SERIAL)),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.PHONE_NUMBER)),
                new Date(cursor.getLong(cursor.getColumnIndex(EntomologiaBConstants.TODAY))),
                cursor.getString(cursor.getColumnIndex(EntomologiaBConstants.USUARIO)),
                cursor.getInt(cursor.getColumnIndex(EntomologiaBConstants.DELETED))>0, null, null));
        return cuest;
    }
}
