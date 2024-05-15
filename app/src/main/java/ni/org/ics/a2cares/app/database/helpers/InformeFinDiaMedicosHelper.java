package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.medico.InformeFindeDiaMedicos;

/**
 * Created by Everts Morales 18/12/2023.
 */
public class InformeFinDiaMedicosHelper {

    public static ContentValues crearFinDiaMedicosValues(InformeFindeDiaMedicos informeFindeDiaMedicos){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, informeFindeDiaMedicos.getId() );
      //  cv.put(MainDBConstants.fechaConsulta, informeFindeDiaMedicos.getRecordDate().getTime());
        cv.put(MainDBConstants.puestoSalud, informeFindeDiaMedicos.getPuestoSalud());
        cv.put(MainDBConstants.numPartCohorte, informeFindeDiaMedicos.getNumPartCohorte());

        cv.put(MainDBConstants.codPartAtend1, informeFindeDiaMedicos.getCodPartAtend1());
        cv.put(MainDBConstants.codPartAtend2, informeFindeDiaMedicos.getCodPartAtend2());
        cv.put(MainDBConstants.codPartAtend3, informeFindeDiaMedicos.getCodPartAtend3());
        cv.put(MainDBConstants.codPartAtend4, informeFindeDiaMedicos.getCodPartAtend4());
        cv.put(MainDBConstants.codPartAtend5, informeFindeDiaMedicos.getCodPartAtend5());
        cv.put(MainDBConstants.codPartAtend6, informeFindeDiaMedicos.getCodPartAtend6());
        cv.put(MainDBConstants.codPartAtend7, informeFindeDiaMedicos.getCodPartAtend7());
        cv.put(MainDBConstants.codPartAtend8, informeFindeDiaMedicos.getCodPartAtend8());
        cv.put(MainDBConstants.codPartAtend9, informeFindeDiaMedicos.getCodPartAtend9());
        cv.put(MainDBConstants.codPartAtend10, informeFindeDiaMedicos.getCodPartAtend10());
        cv.put(MainDBConstants.codPartAtend11, informeFindeDiaMedicos.getCodPartAtend11());
        cv.put(MainDBConstants.codPartAtend12, informeFindeDiaMedicos.getCodPartAtend12());
        cv.put(MainDBConstants.codPartAtend13, informeFindeDiaMedicos.getCodPartAtend13());
        cv.put(MainDBConstants.codPartAtend14, informeFindeDiaMedicos.getCodPartAtend14());
        cv.put(MainDBConstants.codPartAtend15, informeFindeDiaMedicos.getCodPartAtend15());
        cv.put(MainDBConstants.codPartAtend16, informeFindeDiaMedicos.getCodPartAtend16());
        cv.put(MainDBConstants.codPartAtend17, informeFindeDiaMedicos.getCodPartAtend17());
        cv.put(MainDBConstants.codPartAtend18, informeFindeDiaMedicos.getCodPartAtend18());
        cv.put(MainDBConstants.codPartAtend19, informeFindeDiaMedicos.getCodPartAtend19());
        cv.put(MainDBConstants.codPartAtend20, informeFindeDiaMedicos.getCodPartAtend20());

        cv.put(MainDBConstants.codPartAtend1Diagnostico, informeFindeDiaMedicos.getCodPartAtend1Diagnostico());
        cv.put(MainDBConstants.codPartAtend2Diagnostico, informeFindeDiaMedicos.getCodPartAtend2Diagnostico());
        cv.put(MainDBConstants.codPartAtend3Diagnostico, informeFindeDiaMedicos.getCodPartAtend3Diagnostico());
        cv.put(MainDBConstants.codPartAtend4Diagnostico, informeFindeDiaMedicos.getCodPartAtend4Diagnostico());
        cv.put(MainDBConstants.codPartAtend5Diagnostico, informeFindeDiaMedicos.getCodPartAtend5Diagnostico());
        cv.put(MainDBConstants.codPartAtend6Diagnostico, informeFindeDiaMedicos.getCodPartAtend6Diagnostico());
        cv.put(MainDBConstants.codPartAtend7Diagnostico, informeFindeDiaMedicos.getCodPartAtend7Diagnostico());
        cv.put(MainDBConstants.codPartAtend8Diagnostico, informeFindeDiaMedicos.getCodPartAtend8Diagnostico());
        cv.put(MainDBConstants.codPartAtend9Diagnostico, informeFindeDiaMedicos.getCodPartAtend9Diagnostico());
        cv.put(MainDBConstants.codPartAtend10Diagnostico, informeFindeDiaMedicos.getCodPartAtend10Diagnostico());
        cv.put(MainDBConstants.codPartAtend11Diagnostico, informeFindeDiaMedicos.getCodPartAtend11Diagnostico());
        cv.put(MainDBConstants.codPartAtend12Diagnostico, informeFindeDiaMedicos.getCodPartAtend12Diagnostico());
        cv.put(MainDBConstants.codPartAtend13Diagnostico, informeFindeDiaMedicos.getCodPartAtend13Diagnostico());
        cv.put(MainDBConstants.codPartAtend14Diagnostico, informeFindeDiaMedicos.getCodPartAtend14Diagnostico());
        cv.put(MainDBConstants.codPartAtend15Diagnostico, informeFindeDiaMedicos.getCodPartAtend15Diagnostico());
        cv.put(MainDBConstants.codPartAtend16Diagnostico, informeFindeDiaMedicos.getCodPartAtend16Diagnostico());
        cv.put(MainDBConstants.codPartAtend17Diagnostico, informeFindeDiaMedicos.getCodPartAtend17Diagnostico());
        cv.put(MainDBConstants.codPartAtend18Diagnostico, informeFindeDiaMedicos.getCodPartAtend18Diagnostico());
        cv.put(MainDBConstants.codPartAtend19Diagnostico, informeFindeDiaMedicos.getCodPartAtend19Diagnostico());
        cv.put(MainDBConstants.codPartAtend20Diagnostico, informeFindeDiaMedicos.getCodPartAtend20Diagnostico());


        cv.put(MainDBConstants.numPartNoCohorte, informeFindeDiaMedicos.getNumPartNoCohorte());
        cv.put(MainDBConstants.numTotalAtendidos, informeFindeDiaMedicos.getNumTotalAtendidos());
        cv.put(MainDBConstants.numFebrilA, informeFindeDiaMedicos.getNumFebrilA());
        cv.put(MainDBConstants.numInRespAgudaA, informeFindeDiaMedicos.getNumInRespAgudaA());
        cv.put(MainDBConstants.numEnfDiarreaAgudaA, informeFindeDiaMedicos.getNumEnfDiarreaAgudaA());
        cv.put(MainDBConstants.numETI, informeFindeDiaMedicos.getNumETI());
        cv.put(MainDBConstants.numRAG, informeFindeDiaMedicos.getNumRAG());
        cv.put(MainDBConstants.numConjuntivitis, informeFindeDiaMedicos.getNumConjuntivitis());
        cv.put(MainDBConstants.numControlPrenatal, informeFindeDiaMedicos.getNumControlPrenatal());
        cv.put(MainDBConstants.numNeumonia, informeFindeDiaMedicos.getNumNeumonia());
        cv.put(MainDBConstants.numPap, informeFindeDiaMedicos.getNumPap());
        cv.put(MainDBConstants.numPlanificacionFam, informeFindeDiaMedicos.getNumPlanificacionFam());
        cv.put(MainDBConstants.numGotaGruesa, informeFindeDiaMedicos.getNumGotaGruesa());
        cv.put(MainDBConstants.numCronicos, informeFindeDiaMedicos.getNumCronicos());
        cv.put(MainDBConstants.numTraslados, informeFindeDiaMedicos.getNumTraslados());
        cv.put(MainDBConstants.numCaptacionA, informeFindeDiaMedicos.getNumCaptacionA());
        cv.put(MainDBConstants.numCaptacionB, informeFindeDiaMedicos.getNumCaptacionB());
        cv.put(MainDBConstants.numCaptacionC, informeFindeDiaMedicos.getNumCaptacionC());
        cv.put(MainDBConstants.numCaptacionD, informeFindeDiaMedicos.getNumCaptacionD());
        cv.put(MainDBConstants.numSeguimientoA, informeFindeDiaMedicos.getNumSeguimientoA());
        cv.put(MainDBConstants.numSeguimientoB, informeFindeDiaMedicos.getNumSeguimientoB());
        cv.put(MainDBConstants.numSeguimientoD, informeFindeDiaMedicos.getNumSeguimientoD());
        cv.put(MainDBConstants.numConvPendPuesto, informeFindeDiaMedicos.getNumConvPendPuesto());
        cv.put(MainDBConstants.numVisitaAterreno, informeFindeDiaMedicos.getNumVisitaAterreno());
        cv.put(MainDBConstants.numTrasladosDengue, informeFindeDiaMedicos.getNumTrasladosDengue());
        cv.put(MainDBConstants.codPartTraslado1, informeFindeDiaMedicos.getCodPartTraslado1());
        cv.put(MainDBConstants.codPartTraslado2, informeFindeDiaMedicos.getCodPartTraslado2());
        cv.put(MainDBConstants.codPartTraslado3, informeFindeDiaMedicos.getCodPartTraslado3());
        cv.put(MainDBConstants.codPartTraslado4, informeFindeDiaMedicos.getCodPartTraslado4());
        cv.put(MainDBConstants.codPartTraslado5, informeFindeDiaMedicos.getCodPartTraslado5());
        cv.put(MainDBConstants.codPartTraslado6, informeFindeDiaMedicos.getCodPartTraslado6());
        cv.put(MainDBConstants.codPartTraslado7, informeFindeDiaMedicos.getCodPartTraslado7());
        cv.put(MainDBConstants.codPartTraslado8, informeFindeDiaMedicos.getCodPartTraslado8());
        cv.put(MainDBConstants.codPartTraslado9, informeFindeDiaMedicos.getCodPartTraslado9());
        cv.put(MainDBConstants.codPartTraslado10, informeFindeDiaMedicos.getCodPartTraslado10());
        cv.put(MainDBConstants.codPartTraslado1Diagnostico, informeFindeDiaMedicos.getCodPartTraslado1Diagnostico());
        cv.put(MainDBConstants.codPartTraslado2Diagnostico, informeFindeDiaMedicos.getCodPartTraslado2Diagnostico());
        cv.put(MainDBConstants.codPartTraslado3Diagnostico, informeFindeDiaMedicos.getCodPartTraslado3Diagnostico());
        cv.put(MainDBConstants.codPartTraslado4Diagnostico, informeFindeDiaMedicos.getCodPartTraslado4Diagnostico());
        cv.put(MainDBConstants.codPartTraslado5Diagnostico, informeFindeDiaMedicos.getCodPartTraslado5Diagnostico());
        cv.put(MainDBConstants.codPartTraslado6Diagnostico, informeFindeDiaMedicos.getCodPartTraslado6Diagnostico());
        cv.put(MainDBConstants.codPartTraslado7Diagnostico, informeFindeDiaMedicos.getCodPartTraslado7Diagnostico());
        cv.put(MainDBConstants.codPartTraslado8Diagnostico, informeFindeDiaMedicos.getCodPartTraslado8Diagnostico());
        cv.put(MainDBConstants.codPartTraslado9Diagnostico, informeFindeDiaMedicos.getCodPartTraslado9Diagnostico());
        cv.put(MainDBConstants.codPartTraslado10Diagnostico, informeFindeDiaMedicos.getCodPartTraslado10Diagnostico());

        cv.put(MainDBConstants.numNegatTrasladosDengue, informeFindeDiaMedicos.getNumNegatTrasladosDengue());
        cv.put(MainDBConstants.codNegatPartTraslado1, informeFindeDiaMedicos.getCodNegatPartTraslado1());
        cv.put(MainDBConstants.codNegatPartTraslado2, informeFindeDiaMedicos.getCodNegatPartTraslado2());
        cv.put(MainDBConstants.codNegatPartTraslado3, informeFindeDiaMedicos.getCodNegatPartTraslado3());
        cv.put(MainDBConstants.codNegatPartTraslado4, informeFindeDiaMedicos.getCodNegatPartTraslado4());
        cv.put(MainDBConstants.codNegatPartTraslado5, informeFindeDiaMedicos.getCodNegatPartTraslado5());
        cv.put(MainDBConstants.codNegatPartTraslado6, informeFindeDiaMedicos.getCodNegatPartTraslado6());
        cv.put(MainDBConstants.codNegatPartTraslado7, informeFindeDiaMedicos.getCodNegatPartTraslado7());
        cv.put(MainDBConstants.codNegatPartTraslado8, informeFindeDiaMedicos.getCodNegatPartTraslado8());
        cv.put(MainDBConstants.codNegatPartTraslado9, informeFindeDiaMedicos.getCodNegatPartTraslado9());
        cv.put(MainDBConstants.codNegatPartTraslado10, informeFindeDiaMedicos.getCodNegatPartTraslado10());
        cv.put(MainDBConstants.codNegaPartTraslado1Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado1Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado2Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado2Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado3Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado3Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado4Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado4Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado5Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado5Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado6Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado6Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado7Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado7Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado8Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado8Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado9Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado9Diagnostico());
        cv.put(MainDBConstants.codNegaPartTraslado10Diagnostico, informeFindeDiaMedicos.getCodNegatPartTraslado10Diagnostico());

        cv.put(MainDBConstants.nomMedico, informeFindeDiaMedicos.getNomMedico());

        if (informeFindeDiaMedicos.getRecordDate() != null) cv.put(MainDBConstants.recordDate, informeFindeDiaMedicos.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, informeFindeDiaMedicos.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(informeFindeDiaMedicos.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(informeFindeDiaMedicos.getEstado()));
        cv.put(MainDBConstants.deviceId, informeFindeDiaMedicos.getDeviceid());

        return cv;
    }

    public static InformeFindeDiaMedicos crearInformeFinDiaMedicos(Cursor cursor){
        InformeFindeDiaMedicos informeFindeDiaMedicos = new InformeFindeDiaMedicos();

        informeFindeDiaMedicos.setId(cursor.getInt(cursor.getColumnIndex(MainDBConstants.id)));
        informeFindeDiaMedicos.setPuestoSalud(cursor.getString(cursor.getColumnIndex(MainDBConstants.puestoSalud)));
        informeFindeDiaMedicos.setFechaConsulta(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        informeFindeDiaMedicos.setNumPartCohorte(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numPartCohorte)));
        informeFindeDiaMedicos.setCodPartAtend1(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend1)));
        informeFindeDiaMedicos.setCodPartAtend2(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend2)));
        informeFindeDiaMedicos.setCodPartAtend3(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend3)));
        informeFindeDiaMedicos.setCodPartAtend4(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend4)));
        informeFindeDiaMedicos.setCodPartAtend5(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend5)));
        informeFindeDiaMedicos.setCodPartAtend6(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend6)));
        informeFindeDiaMedicos.setCodPartAtend7(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend7)));
        informeFindeDiaMedicos.setCodPartAtend8(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend8)));
        informeFindeDiaMedicos.setCodPartAtend9(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend9)));
        informeFindeDiaMedicos.setCodPartAtend10(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend10)));
        informeFindeDiaMedicos.setCodPartAtend11(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend11)));
        informeFindeDiaMedicos.setCodPartAtend12(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend12)));
        informeFindeDiaMedicos.setCodPartAtend13(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend13)));
        informeFindeDiaMedicos.setCodPartAtend14(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend14)));
        informeFindeDiaMedicos.setCodPartAtend15(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend15)));
        informeFindeDiaMedicos.setCodPartAtend16(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend16)));
        informeFindeDiaMedicos.setCodPartAtend17(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend17)));
        informeFindeDiaMedicos.setCodPartAtend18(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend18)));
        informeFindeDiaMedicos.setCodPartAtend19(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend19)));
        informeFindeDiaMedicos.setCodPartAtend20(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend20)));

        informeFindeDiaMedicos.setCodPartAtend1Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend1Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend2Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend2Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend3Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend3Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend4Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend4Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend5Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend5Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend6Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend6Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend7Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend7Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend8Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend8Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend9Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend9Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend10Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend10Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend11Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend11Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend12Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend12Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend13Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend13Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend14Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend14Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend15Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend15Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend16Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend16Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend17Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend17Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend18Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend18Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend19Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend19Diagnostico)));
        informeFindeDiaMedicos.setCodPartAtend20Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartAtend20Diagnostico)));

        informeFindeDiaMedicos.setNumPartNoCohorte(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numPartNoCohorte))) ;
        informeFindeDiaMedicos.setNumTotalAtendidos(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numTotalAtendidos)));
        informeFindeDiaMedicos.setNumFebrilA(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numFebrilA)));
        informeFindeDiaMedicos.setNumInRespAgudaA(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numInRespAgudaA)));
        informeFindeDiaMedicos.setNumEnfDiarreaAgudaA(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numEnfDiarreaAgudaA)));
        informeFindeDiaMedicos.setNumETI(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numETI)));
        informeFindeDiaMedicos.setNumRAG(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numRAG)));
        informeFindeDiaMedicos.setNumConjuntivitis(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numConjuntivitis)));
        informeFindeDiaMedicos.setNumControlPrenatal(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numControlPrenatal)));
        informeFindeDiaMedicos.setNumNeumonia(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numNeumonia)));
        informeFindeDiaMedicos.setNumPap(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numPap)));
        informeFindeDiaMedicos.setNumPlanificacionFam(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numPlanificacionFam)));
        informeFindeDiaMedicos.setNumGotaGruesa(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numGotaGruesa)));
        informeFindeDiaMedicos.setNumCronicos(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numCronicos)));

        informeFindeDiaMedicos.setNumTraslados(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numTraslados)));
        informeFindeDiaMedicos.setNumCaptacionA(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numCaptacionA)));
        informeFindeDiaMedicos.setNumCaptacionB(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numCaptacionB)));
        informeFindeDiaMedicos.setNumCaptacionC(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numCaptacionC)));
        informeFindeDiaMedicos.setNumCaptacionD(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numCaptacionD)));
        informeFindeDiaMedicos.setNumSeguimientoA(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numSeguimientoA)));
        informeFindeDiaMedicos.setNumSeguimientoB(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numSeguimientoB)));
        informeFindeDiaMedicos.setNumSeguimientoD(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numSeguimientoD)));
        informeFindeDiaMedicos.setNumConvPendPuesto(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numConvPendPuesto)));
        informeFindeDiaMedicos.setNumVisitaAterreno(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numVisitaAterreno)));

        informeFindeDiaMedicos.setNumTrasladosDengue(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numTrasladosDengue)));
        informeFindeDiaMedicos.setCodPartTraslado1(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado1))) ;
        informeFindeDiaMedicos.setCodPartTraslado2(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado2))) ;
        informeFindeDiaMedicos.setCodPartTraslado3(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado3))) ;
        informeFindeDiaMedicos.setCodPartTraslado4(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado4))) ;
        informeFindeDiaMedicos.setCodPartTraslado5(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado5))) ;
        informeFindeDiaMedicos.setCodPartTraslado6(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado6))) ;
        informeFindeDiaMedicos.setCodPartTraslado7(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado7))) ;
        informeFindeDiaMedicos.setCodPartTraslado8(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado8))) ;
        informeFindeDiaMedicos.setCodPartTraslado9(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado9))) ;
        informeFindeDiaMedicos.setCodPartTraslado10(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado10))) ;

        informeFindeDiaMedicos.setCodPartTraslado1Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado1Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado2Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado2Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado3Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado3Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado4Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado4Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado5Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado5Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado6Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado6Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado7Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado7Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado8Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado8Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado9Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado9Diagnostico)));
        informeFindeDiaMedicos.setCodPartTraslado10Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codPartTraslado10Diagnostico)));

        informeFindeDiaMedicos.setNumNegatTrasladosDengue(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numNegatTrasladosDengue)));
        informeFindeDiaMedicos.setCodNegatPartTraslado1(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado1))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado2(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado2))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado3(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado3))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado4(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado4))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado5(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado5))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado6(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado6))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado7(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado7))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado8(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado8))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado9(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado9))) ;
        informeFindeDiaMedicos.setCodNegatPartTraslado10(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegatPartTraslado10))) ;

        informeFindeDiaMedicos.setCodNegatPartTraslado1Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado1Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado2Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado2Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado3Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado3Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado4Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado4Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado5Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado5Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado6Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado6Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado7Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado7Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado8Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado8Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado9Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado9Diagnostico)));
        informeFindeDiaMedicos.setCodNegatPartTraslado10Diagnostico(cursor.getString(cursor.getColumnIndex(MainDBConstants.codNegaPartTraslado10Diagnostico)));


        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) informeFindeDiaMedicos.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        informeFindeDiaMedicos.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        informeFindeDiaMedicos.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        informeFindeDiaMedicos.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        informeFindeDiaMedicos.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return informeFindeDiaMedicos;
    }
}
