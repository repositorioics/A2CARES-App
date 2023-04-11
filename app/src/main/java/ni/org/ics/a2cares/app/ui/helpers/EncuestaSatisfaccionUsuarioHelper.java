package ni.org.ics.a2cares.app.ui.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import ni.org.ics.a2cares.app.database.constants.EncuestasDBConstants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.survey.EncuestaSatisfaccionUsuario;

/**
 * Created by Ing. Santiago Carballo on 30/03/2023.
 */
public class EncuestaSatisfaccionUsuarioHelper {
    public static ContentValues crearEncuenstaSatisfaccionUsuarioContentValues(EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario) {
        ContentValues cv = new ContentValues();

        //cv.put(EncuestasDBConstants.codigo, encuestaSatisfaccionUsuario.getEsId());
        cv.put(EncuestasDBConstants.codigoParticipante, encuestaSatisfaccionUsuario.getCodigoParticipante());
        cv.put(EncuestasDBConstants.atencionMedica, encuestaSatisfaccionUsuario.getAtencionMedica());
        cv.put(EncuestasDBConstants.familaAmistades, encuestaSatisfaccionUsuario.getFamilaAmistades());
        cv.put(EncuestasDBConstants.desicionPropia, encuestaSatisfaccionUsuario.getDesicionPropia());
        cv.put(EncuestasDBConstants.obsequiosOfreceEstudio, encuestaSatisfaccionUsuario.getObsequiosOfreceEstudio());
        cv.put(EncuestasDBConstants.ayudaRecibeComunidad, encuestaSatisfaccionUsuario.getAyudaRecibeComunidad());
        cv.put(EncuestasDBConstants.examenesLaboratorio, encuestaSatisfaccionUsuario.getExamenesLaboratorio());
        cv.put(EncuestasDBConstants.interesCientificoPersonalP1, encuestaSatisfaccionUsuario.getInteresCientificoPersonalP1());
        cv.put(EncuestasDBConstants.informacionAyudaOtros, encuestaSatisfaccionUsuario.getInformacionAyudaOtros());
        cv.put(EncuestasDBConstants.otraP1, encuestaSatisfaccionUsuario.getOtraP1());
        cv.put(EncuestasDBConstants.calidadAtencionMedica, encuestaSatisfaccionUsuario.getCalidadAtencionMedica());
        cv.put(EncuestasDBConstants.atencionOportuna, encuestaSatisfaccionUsuario.getAtencionOportuna());
        cv.put(EncuestasDBConstants.buenaCoordinacionhosp, encuestaSatisfaccionUsuario.getBuenaCoordinacionhosp());
        cv.put(EncuestasDBConstants.infoEstadoSalud, encuestaSatisfaccionUsuario.getInfoEstadoSalud());
        cv.put(EncuestasDBConstants.enseniaPrevEnfermedades, encuestaSatisfaccionUsuario.getEnseniaPrevEnfermedades());
        cv.put(EncuestasDBConstants.infoConsMejoraConocimientos, encuestaSatisfaccionUsuario.getInfoConsMejoraConocimientos());
        cv.put(EncuestasDBConstants.interesCientificoPersonalP2, encuestaSatisfaccionUsuario.getInteresCientificoPersonalP2());
        cv.put(EncuestasDBConstants.mejorarTratamientoDengue, encuestaSatisfaccionUsuario.getMejorarTratamientoDengue());
        cv.put(EncuestasDBConstants.unicaManeraRecibirAtencionMed, encuestaSatisfaccionUsuario.getUnicaManeraRecibirAtencionMed());
        cv.put(EncuestasDBConstants.otraP2, encuestaSatisfaccionUsuario.getOtraP2());
        cv.put(EncuestasDBConstants.difBuscarAtencionMed, encuestaSatisfaccionUsuario.getDifBuscarAtencionMed());
        cv.put(EncuestasDBConstants.centroSaludLejos, encuestaSatisfaccionUsuario.getCentroSaludLejos());
        cv.put(EncuestasDBConstants.costoTrasnporteElevado, encuestaSatisfaccionUsuario.getCostoTrasnporteElevado());
        cv.put(EncuestasDBConstants.trabajoTiempo, encuestaSatisfaccionUsuario.getTrabajoTiempo());
        cv.put(EncuestasDBConstants.noQueriapasarConsulta, encuestaSatisfaccionUsuario.getNoQueriapasarConsulta());
        cv.put(EncuestasDBConstants.horarioClases, encuestaSatisfaccionUsuario.getHorarioClases());
        cv.put(EncuestasDBConstants.temorTomenMx, encuestaSatisfaccionUsuario.getTemorTomenMx());
        cv.put(EncuestasDBConstants.temorInfoPersonal, encuestaSatisfaccionUsuario.getTemorInfoPersonal());
        cv.put(EncuestasDBConstants.otraP3, encuestaSatisfaccionUsuario.getOtraP3());
        cv.put(EncuestasDBConstants.recomendariaAmigoFamiliar, encuestaSatisfaccionUsuario.getRecomendariaAmigoFamiliar());
        cv.put(EncuestasDBConstants.atencionCalidad, encuestaSatisfaccionUsuario.getAtencionCalidad());
        cv.put(EncuestasDBConstants.respNecesidadesSaludOportuna, encuestaSatisfaccionUsuario.getRespNecesidadesSaludOportuna());
        cv.put(EncuestasDBConstants.tiempoEsperaCorto, encuestaSatisfaccionUsuario.getTiempoEsperaCorto());
        cv.put(EncuestasDBConstants.mejorAtencionQueSeguro, encuestaSatisfaccionUsuario.getMejorAtencionQueSeguro());
        cv.put(EncuestasDBConstants.examenLabNecesarios, encuestaSatisfaccionUsuario.getExamenLabNecesarios());
        cv.put(EncuestasDBConstants.conocimientosImportantes, encuestaSatisfaccionUsuario.getConocimientosImportantes());
        cv.put(EncuestasDBConstants.pocosRequisitos, encuestaSatisfaccionUsuario.getPocosRequisitos());
        cv.put(EncuestasDBConstants.otraP_4_1, encuestaSatisfaccionUsuario.getOtraP_4_1());
        cv.put(EncuestasDBConstants.atencionPersonalMala, encuestaSatisfaccionUsuario.getAtencionPersonalMala());
        cv.put(EncuestasDBConstants.noDanRespNecesidades, encuestaSatisfaccionUsuario.getNoDanRespNecesidades());
        cv.put(EncuestasDBConstants.tiempoEsperaLargo, encuestaSatisfaccionUsuario.getTiempoEsperaLargo());
        cv.put(EncuestasDBConstants.mejorAtencionOtraUnidadSalud, encuestaSatisfaccionUsuario.getMejorAtencionOtraUnidadSalud());
        cv.put(EncuestasDBConstants.solicitaDemasiadaMx, encuestaSatisfaccionUsuario.getSolicitaDemasiadaMx());
        cv.put(EncuestasDBConstants.muchosRequisitos, encuestaSatisfaccionUsuario.getMuchosRequisitos());
        cv.put(EncuestasDBConstants.noExplicanHacenMx, encuestaSatisfaccionUsuario.getNoExplicanHacenMx());
        cv.put(EncuestasDBConstants.noConfianza, encuestaSatisfaccionUsuario.getNoConfianza());
        cv.put(EncuestasDBConstants.otraP_4_2, encuestaSatisfaccionUsuario.getOtraP_4_2());
        cv.put(EncuestasDBConstants.comprendeProcedimientos, encuestaSatisfaccionUsuario.getComprendeProcedimientos());
        cv.put(EncuestasDBConstants.noComodoRealizarPreg, encuestaSatisfaccionUsuario.getNoComodoRealizarPreg());
        cv.put(EncuestasDBConstants.noRespondieronPreg, encuestaSatisfaccionUsuario.getNoRespondieronPreg());
        cv.put(EncuestasDBConstants.explicacionRapida, encuestaSatisfaccionUsuario.getExplicacionRapida());
        cv.put(EncuestasDBConstants.noDejaronHacerPreg, encuestaSatisfaccionUsuario.getNoDejaronHacerPreg());
        cv.put(EncuestasDBConstants.otraP_5_1, encuestaSatisfaccionUsuario.getOtraP_5_1());
        cv.put(EncuestasDBConstants.brindaronConsejosPrevencionEnfer, encuestaSatisfaccionUsuario.getBrindaronConsejosPrevencionEnfer());
        cv.put(EncuestasDBConstants.entiendoProcedimientosEstudios, encuestaSatisfaccionUsuario.getEntiendoProcedimientosEstudios());
        cv.put(EncuestasDBConstants.satisfecho, encuestaSatisfaccionUsuario.getSatisfecho());
        cv.put(EncuestasDBConstants.comodoInfoRecolectada, encuestaSatisfaccionUsuario.getComodoInfoRecolectada());
        cv.put(EncuestasDBConstants.noComodoPreguntas, encuestaSatisfaccionUsuario.getNoComodoPreguntas());
        cv.put(EncuestasDBConstants.recomendacionMejorarAtencion, encuestaSatisfaccionUsuario.getRecomendacionMejorarAtencion());
        cv.put(EncuestasDBConstants.importanciaRecibirInformacion, encuestaSatisfaccionUsuario.getImportanciaRecibirInformacion());

        cv.put(EncuestasDBConstants.identificadoEquipo, encuestaSatisfaccionUsuario.getIdentificadoEquipo());
        cv.put(MainDBConstants.estado, String.valueOf(encuestaSatisfaccionUsuario.getEstado()));
        cv.put(MainDBConstants.pasive, String.valueOf(encuestaSatisfaccionUsuario.getPasivo()));
        cv.put(EncuestasDBConstants.fechaRegistro, String.valueOf(encuestaSatisfaccionUsuario.getFechaRegistro()));
        cv.put(EncuestasDBConstants.creado, String.valueOf(encuestaSatisfaccionUsuario.getCreado()));
        cv.put(EncuestasDBConstants.usuarioRegistro, encuestaSatisfaccionUsuario.getUsuarioRegistro());

        cv.put(MainDBConstants.nombre1Tutor, encuestaSatisfaccionUsuario.getNombre1Tutor());
        cv.put(MainDBConstants.nombre2Tutor, encuestaSatisfaccionUsuario.getNombre2Tutor());
        cv.put(MainDBConstants.apellido1Tutor, encuestaSatisfaccionUsuario.getApellido1Tutor());
        cv.put(MainDBConstants.apellido2Tutor, encuestaSatisfaccionUsuario.getApellido2Tutor());
        cv.put(EncuestasDBConstants.codigoCasa, encuestaSatisfaccionUsuario.getCodigoCasa());
        //cv.put(MainDBConstants.casaCHF, encuestaSatisfaccionUsuario.getCasaChf());
        cv.put(MainDBConstants.estudio, encuestaSatisfaccionUsuario.getEstudio());

        return cv;
    }

    public static EncuestaSatisfaccionUsuario crearEncuestaSatisfaccionUsuario(Cursor encuestaSatisfaccion) {

        EncuestaSatisfaccionUsuario mEncSatUsuario = new EncuestaSatisfaccionUsuario();

        //if (encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigo)) > 0) mEncSatUsuario.setEsId(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigo)));
        //if (encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigoParticipante)) > 0) mEncSatUsuario.setCodigoParticipante(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigoParticipante)));
        mEncSatUsuario.setCodigoParticipante(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigoParticipante)));
        mEncSatUsuario.setAtencionMedica(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionMedica)));
        mEncSatUsuario.setFamilaAmistades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.familaAmistades)));
        mEncSatUsuario.setDesicionPropia(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.desicionPropia)));
        mEncSatUsuario.setObsequiosOfreceEstudio(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.obsequiosOfreceEstudio)));
        mEncSatUsuario.setAyudaRecibeComunidad(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.ayudaRecibeComunidad)));
        mEncSatUsuario.setExamenesLaboratorio(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.examenesLaboratorio)));
        mEncSatUsuario.setInteresCientificoPersonalP1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.interesCientificoPersonalP1)));
        mEncSatUsuario.setInformacionAyudaOtros(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.informacionAyudaOtros)));
        mEncSatUsuario.setOtraP1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP1)));
        mEncSatUsuario.setCalidadAtencionMedica(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.calidadAtencionMedica)));
        mEncSatUsuario.setAtencionOportuna(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionOportuna)));
        mEncSatUsuario.setBuenaCoordinacionhosp(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.buenaCoordinacionhosp)));
        mEncSatUsuario.setInfoEstadoSalud(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.infoEstadoSalud)));
        mEncSatUsuario.setEnseniaPrevEnfermedades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.enseniaPrevEnfermedades)));
        mEncSatUsuario.setInfoConsMejoraConocimientos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.infoConsMejoraConocimientos)));
        mEncSatUsuario.setInteresCientificoPersonalP2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.interesCientificoPersonalP2)));
        mEncSatUsuario.setMejorarTratamientoDengue(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.mejorarTratamientoDengue)));
        mEncSatUsuario.setUnicaManeraRecibirAtencionMed(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.unicaManeraRecibirAtencionMed)));
        mEncSatUsuario.setOtraP2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP2)));
        mEncSatUsuario.setDifBuscarAtencionMed(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.difBuscarAtencionMed)));
        mEncSatUsuario.setCentroSaludLejos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.centroSaludLejos)));
        mEncSatUsuario.setCostoTrasnporteElevado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.costoTrasnporteElevado)));
        mEncSatUsuario.setTrabajoTiempo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.trabajoTiempo)));
        mEncSatUsuario.setNoQueriapasarConsulta(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noQueriapasarConsulta)));
        mEncSatUsuario.setHorarioClases(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.horarioClases)));
        mEncSatUsuario.setTemorTomenMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.temorTomenMx)));
        mEncSatUsuario.setTemorInfoPersonal(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.temorInfoPersonal)));
        mEncSatUsuario.setOtraP3(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP3)));
        mEncSatUsuario.setRecomendariaAmigoFamiliar(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.recomendariaAmigoFamiliar)));
        mEncSatUsuario.setAtencionCalidad(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionCalidad)));
        mEncSatUsuario.setRespNecesidadesSaludOportuna(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.respNecesidadesSaludOportuna)));
        mEncSatUsuario.setTiempoEsperaCorto(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tiempoEsperaCorto)));
        mEncSatUsuario.setMejorAtencionQueSeguro(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.mejorAtencionQueSeguro)));
        mEncSatUsuario.setExamenLabNecesarios(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.examenLabNecesarios)));
        mEncSatUsuario.setConocimientosImportantes(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.conocimientosImportantes)));
        mEncSatUsuario.setPocosRequisitos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.pocosRequisitos)));
        mEncSatUsuario.setOtraP_4_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_4_1)));
        mEncSatUsuario.setAtencionPersonalMala(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionPersonalMala)));
        mEncSatUsuario.setNoDanRespNecesidades(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noDanRespNecesidades)));
        mEncSatUsuario.setTiempoEsperaLargo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tiempoEsperaLargo)));
        mEncSatUsuario.setMejorAtencionOtraUnidadSalud(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.mejorAtencionOtraUnidadSalud)));
        mEncSatUsuario.setSolicitaDemasiadaMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.solicitaDemasiadaMx)));
        mEncSatUsuario.setMuchosRequisitos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.muchosRequisitos)));
        mEncSatUsuario.setNoExplicanHacenMx(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noExplicanHacenMx)));
        mEncSatUsuario.setNoConfianza(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noConfianza)));
        mEncSatUsuario.setOtraP_4_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_4_2)));
        mEncSatUsuario.setComprendeProcedimientos(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.comprendeProcedimientos)));
        mEncSatUsuario.setNoComodoRealizarPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noComodoRealizarPreg)));
        mEncSatUsuario.setNoRespondieronPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noRespondieronPreg)));
        mEncSatUsuario.setExplicacionRapida(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.explicacionRapida)));
        mEncSatUsuario.setNoDejaronHacerPreg(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noDejaronHacerPreg)));
        mEncSatUsuario.setOtraP_5_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_5_1)));
        mEncSatUsuario.setBrindaronConsejosPrevencionEnfer(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.brindaronConsejosPrevencionEnfer)));
        mEncSatUsuario.setEntiendoProcedimientosEstudios(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.entiendoProcedimientosEstudios)));
        mEncSatUsuario.setSatisfecho(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.satisfecho)));
        mEncSatUsuario.setComodoInfoRecolectada(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.comodoInfoRecolectada)));
        mEncSatUsuario.setNoComodoPreguntas(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noComodoPreguntas)));
        mEncSatUsuario.setIdentificadoEquipo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.identificadoEquipo)));
        mEncSatUsuario.setEstado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mEncSatUsuario.setPasivo(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mEncSatUsuario.setFechaRegistro(null);
        mEncSatUsuario.setCreado(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.creado)));


        mEncSatUsuario.setUsuarioRegistro(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.usuarioRegistro)));

        mEncSatUsuario.setNombre1Tutor(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.nombre1Tutor)));
        mEncSatUsuario.setNombre2Tutor(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.nombre2Tutor)));
        mEncSatUsuario.setApellido1Tutor(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.apellido1Tutor)));
        mEncSatUsuario.setApellido2Tutor(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.apellido2Tutor)));
        mEncSatUsuario.setCodigoCasa(encuestaSatisfaccion.getInt(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.codigoCasa)));
        //mEncSatUsuario.setCasaChf(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.casaCHF)));
        mEncSatUsuario.setEstudio(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(MainDBConstants.estudio)));

        return mEncSatUsuario;
    }
}
