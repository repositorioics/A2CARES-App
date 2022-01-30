package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;

/**
 * Created by Miguel Salinas on 28/1/2022.
 */
public class MuestraEnfermoHelper {

    public static ContentValues crearMuestraEnfermoContentValues(MuestraEnfermo ordenLaboratorio) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idMuestra, ordenLaboratorio.getIdMuestra());
        cv.put(MainDBConstants.participante, ordenLaboratorio.getParticipante().getCodigo());
        cv.put(MainDBConstants.fechaMuestra, ordenLaboratorio.getFechaMuestra().getTime());
        cv.put(MainDBConstants.horaMuestra, ordenLaboratorio.getHoraMuestra());
        cv.put(MainDBConstants.tipoTubo, ordenLaboratorio.getTipoTubo());
        cv.put(MainDBConstants.volumen, ordenLaboratorio.getVolumen());
        cv.put(MainDBConstants.observacion, ordenLaboratorio.getObservacion());
        if (ordenLaboratorio.getFis() != null) cv.put(MainDBConstants.fis, ordenLaboratorio.getFis().getTime());
        if (ordenLaboratorio.getFif() != null) cv.put(MainDBConstants.fif, ordenLaboratorio.getFif().getTime());
        cv.put(MainDBConstants.categoria, ordenLaboratorio.getCategoria());
        cv.put(MainDBConstants.consulta, ordenLaboratorio.getConsulta());
        cv.put(MainDBConstants.tipoMuestra, ordenLaboratorio.getTipoMuestra());
        cv.put(MainDBConstants.estudiosAct, ordenLaboratorio.getEstudiosAct());

        if (ordenLaboratorio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, ordenLaboratorio.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, ordenLaboratorio.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(ordenLaboratorio.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(ordenLaboratorio.getEstado()));
        cv.put(MainDBConstants.deviceId, ordenLaboratorio.getDeviceid());
        return cv;
    }

    public static MuestraEnfermo crearMuestraEnfermo(Cursor cursor) {
        MuestraEnfermo mMuestra = new MuestraEnfermo();
        mMuestra.setIdMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.idMuestra)));
        mMuestra.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaMuestra))>0) mMuestra.setFechaMuestra(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fechaMuestra)))));
        mMuestra.setHoraMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.horaMuestra)));
        mMuestra.setTipoTubo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoTubo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)) > 0) mMuestra.setVolumen(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)));
        mMuestra.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fis))>0) mMuestra.setFis(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fis)))));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fif))>0) mMuestra.setFif(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fif)))));
        mMuestra.setCategoria(cursor.getString(cursor.getColumnIndex(MainDBConstants.categoria)));
        mMuestra.setConsulta(cursor.getString(cursor.getColumnIndex(MainDBConstants.consulta)));
        mMuestra.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoMuestra)));
        mMuestra.setEstudiosAct(cursor.getString(cursor.getColumnIndex(MainDBConstants.estudiosAct)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mMuestra;
    }
}
