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
        cv.put(EncuestasDBConstants.atencionPersonalEstudio_P1, encuestaSatisfaccionUsuario.getAtencionPersonalEstudio_P1());
        cv.put(EncuestasDBConstants.tiempoAtencionRecibido_P2, encuestaSatisfaccionUsuario.getTiempoAtencionRecibido_P2());
        cv.put(EncuestasDBConstants.atencionRecibidaEnfermeria_P3, encuestaSatisfaccionUsuario.getAtencionRecibidaEnfermeria_P3());
        cv.put(EncuestasDBConstants.atencionRecibidaDoctores_P4, encuestaSatisfaccionUsuario.getAtencionRecibidaDoctores_P4());
        cv.put(EncuestasDBConstants.ambienteDondeRecibeAtencion_P5, encuestaSatisfaccionUsuario.getAmbienteDondeRecibeAtencion_P5());
        cv.put(EncuestasDBConstants.explicaronDiagnostico_P6, encuestaSatisfaccionUsuario.getExplicaronDiagnostico_P6());
        cv.put(EncuestasDBConstants.explicaronTratamiento_P7, encuestaSatisfaccionUsuario.getExplicaronTratamiento_P7());
        cv.put(EncuestasDBConstants.tieneArbovirusImportanciaSeg_P8, encuestaSatisfaccionUsuario.getTieneArbovirusImportanciaSeg_P8());
        cv.put(EncuestasDBConstants.explicaronPeligrosArbovirus_P8_1, encuestaSatisfaccionUsuario.getExplicaronPeligrosArbovirus_P8_1());
        cv.put(EncuestasDBConstants.medicoDijoCuidados_P8_2, encuestaSatisfaccionUsuario.getMedicoDijoCuidados_P8_2());
        cv.put(EncuestasDBConstants.dificultadAtencion_P9, encuestaSatisfaccionUsuario.getDificultadAtencion_P9());
        cv.put(EncuestasDBConstants.centroSaludLejos_P9_1, encuestaSatisfaccionUsuario.getCentroSaludLejos_P9_1());
        cv.put(EncuestasDBConstants.costoTrasnporteElevado_P9_2, encuestaSatisfaccionUsuario.getCostoTrasnporteElevado_P9_2());
        cv.put(EncuestasDBConstants.trabajoTiempo_P9_3, encuestaSatisfaccionUsuario.geTrabajoTiempo_P9_3());
        cv.put(EncuestasDBConstants.noQueriapasarConsulta_P9_4, encuestaSatisfaccionUsuario.getNoQueriapasarConsulta_P9_4());
        cv.put(EncuestasDBConstants.otrasEspecifique_P9_5, encuestaSatisfaccionUsuario.getOtrasEspecifique_P9_5());
        cv.put(EncuestasDBConstants.recomendariaAmigoFamiliar_P10, encuestaSatisfaccionUsuario.getRecomendariaAmigoFamiliar_P10());
        cv.put(EncuestasDBConstants.atencionCalidad_P10_1, encuestaSatisfaccionUsuario.getAtencionCalidad_P10_1());
        cv.put(EncuestasDBConstants.respNecesidadesSaludOportuna_P10_1, encuestaSatisfaccionUsuario.getRespNecesidadesSaludOportuna_P10_1());
        cv.put(EncuestasDBConstants.tiempoEsperaCorto_P10_1, encuestaSatisfaccionUsuario.getTiempoEsperaCorto_P10_1());
        cv.put(EncuestasDBConstants.mejorAtencionQueSeguro_P10_1, encuestaSatisfaccionUsuario.getMejorAtencionQueSeguro_P10_1());
        cv.put(EncuestasDBConstants.examenLabNecesarios_P10_1, encuestaSatisfaccionUsuario.getExamenLabNecesarios_P10_1());
        cv.put(EncuestasDBConstants.pocosRequisitos_P10_1, encuestaSatisfaccionUsuario.getPocosRequisitos_P10_1());
        cv.put(EncuestasDBConstants.otraP_10_1, encuestaSatisfaccionUsuario.getOtraP_10_1());
        cv.put(EncuestasDBConstants.atencionPersonalMala_P10_2, encuestaSatisfaccionUsuario.getAtencionPersonalMala_P10_2());
        cv.put(EncuestasDBConstants.noDanRespNecesidades_P10_2, encuestaSatisfaccionUsuario.getNoDanRespNecesidades_P10_2());
        cv.put(EncuestasDBConstants.tiempoEsperaLargo_P10_2, encuestaSatisfaccionUsuario.getTiempoEsperaLargo_P10_2());
        cv.put(EncuestasDBConstants.mejorAtencionOtraUnidadSalud_P10_2, encuestaSatisfaccionUsuario.getMejorAtencionOtraUnidadSalud_P10_2());
        cv.put(EncuestasDBConstants.solicitaDemasiadaMx_P10_2, encuestaSatisfaccionUsuario.getSolicitaDemasiadaMx_P10_2());
        cv.put(EncuestasDBConstants.muchosRequisitos_P10_2, encuestaSatisfaccionUsuario.getMuchosRequisitos_P10_2());
        cv.put(EncuestasDBConstants.noExplicanHacenMx_P10_2, encuestaSatisfaccionUsuario.getNoExplicanHacenMx_P10_2());
        cv.put(EncuestasDBConstants.noConfianza_P10_2, encuestaSatisfaccionUsuario.getNoConfianza_P10_2());
        cv.put(EncuestasDBConstants.otraP_10_2, encuestaSatisfaccionUsuario.getOtraP_10_2());
        cv.put(EncuestasDBConstants.comprendeProcedimientos_P11, encuestaSatisfaccionUsuario.getComprendeProcedimientos_P11());
        cv.put(EncuestasDBConstants.noComodoRealizarPreg_P11_1, encuestaSatisfaccionUsuario.getNoComodoRealizarPreg_P11_1());
        cv.put(EncuestasDBConstants.noRespondieronPreg_P11_1, encuestaSatisfaccionUsuario.getNoRespondieronPreg_P11_1());
        cv.put(EncuestasDBConstants.explicacionRapida_P11_1, encuestaSatisfaccionUsuario.getExplicacionRapida_P11_1());
        cv.put(EncuestasDBConstants.noDejaronHacerPreg_P11_1, encuestaSatisfaccionUsuario.getNoDejaronHacerPreg_P11_1());
        cv.put(EncuestasDBConstants.otraP_11_1, encuestaSatisfaccionUsuario.getOtraP_11_1());


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
        mEncSatUsuario.setAtencionPersonalEstudio_P1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionPersonalEstudio_P1)));
        mEncSatUsuario.setTiempoAtencionRecibido_P2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tiempoAtencionRecibido_P2)));
        mEncSatUsuario.setAtencionRecibidaEnfermeria_P3(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionRecibidaEnfermeria_P3)));
        mEncSatUsuario.setAtencionRecibidaDoctores_P4(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionRecibidaDoctores_P4)));
        mEncSatUsuario.setAmbienteDondeRecibeAtencion_P5(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.ambienteDondeRecibeAtencion_P5)));
        mEncSatUsuario.setExplicaronDiagnostico_P6(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.explicaronDiagnostico_P6)));
        mEncSatUsuario.setExplicaronTratamiento_P7(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.explicaronTratamiento_P7)));
        mEncSatUsuario.setTieneArbovirusImportanciaSeg_P8(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tieneArbovirusImportanciaSeg_P8)));
        mEncSatUsuario.setExplicaronPeligrosArbovirus_P8_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.explicaronPeligrosArbovirus_P8_1)));
        mEncSatUsuario.setMedicoDijoCuidados_P8_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.medicoDijoCuidados_P8_2)));
        mEncSatUsuario.setDificultadAtencion_P9(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.dificultadAtencion_P9)));
        mEncSatUsuario.setCentroSaludLejos_P9_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.centroSaludLejos_P9_1)));
        mEncSatUsuario.setCostoTrasnporteElevado_P9_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.costoTrasnporteElevado_P9_2)));
        mEncSatUsuario.setTrabajoTiempo_P9_3(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.trabajoTiempo_P9_3)));
        mEncSatUsuario.setNoQueriapasarConsulta_P9_4(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noQueriapasarConsulta_P9_4)));
        mEncSatUsuario.setOtrasEspecifique_P9_5(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otrasEspecifique_P9_5)));
        mEncSatUsuario.setRecomendariaAmigoFamiliar_P10(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.recomendariaAmigoFamiliar_P10)));
        mEncSatUsuario.setAtencionCalidad_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionCalidad_P10_1)));
        mEncSatUsuario.setRespNecesidadesSaludOportuna_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.respNecesidadesSaludOportuna_P10_1)));
        mEncSatUsuario.setTiempoEsperaCorto_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tiempoEsperaCorto_P10_1)));
        mEncSatUsuario.setMejorAtencionQueSeguro_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.mejorAtencionQueSeguro_P10_1)));
        mEncSatUsuario.setExamenLabNecesarios_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.examenLabNecesarios_P10_1)));
        mEncSatUsuario.setPocosRequisitos_P10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.pocosRequisitos_P10_1)));
        mEncSatUsuario.setOtraP_10_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_10_1)));
        mEncSatUsuario.setAtencionPersonalMala_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.atencionPersonalMala_P10_2)));
        mEncSatUsuario.setNoDanRespNecesidades_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noDanRespNecesidades_P10_2)));
        mEncSatUsuario.setTiempoEsperaLargo_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.tiempoEsperaLargo_P10_2)));
        mEncSatUsuario.setMejorAtencionOtraUnidadSalud_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.mejorAtencionOtraUnidadSalud_P10_2)));
        mEncSatUsuario.setSolicitaDemasiadaMx_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.solicitaDemasiadaMx_P10_2)));
        mEncSatUsuario.setMuchosRequisitos_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.muchosRequisitos_P10_2)));
        mEncSatUsuario.setNoExplicanHacenMx_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noExplicanHacenMx_P10_2)));
        mEncSatUsuario.setNoConfianza_P10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noConfianza_P10_2)));
        mEncSatUsuario.setOtraP_10_2(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_10_2)));
        mEncSatUsuario.setComprendeProcedimientos_P11(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.comprendeProcedimientos_P11)));
        mEncSatUsuario.setNoComodoRealizarPreg_P11_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noComodoRealizarPreg_P11_1)));
        mEncSatUsuario.setNoRespondieronPreg_P11_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noRespondieronPreg_P11_1)));
        mEncSatUsuario.setExplicacionRapida_P11_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.explicacionRapida_P11_1)));
        mEncSatUsuario.setNoDejaronHacerPreg_P11_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.noDejaronHacerPreg_P11_1)));
        mEncSatUsuario.setOtraP_11_1(encuestaSatisfaccion.getString(encuestaSatisfaccion.getColumnIndex(EncuestasDBConstants.otraP_11_1)));

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
