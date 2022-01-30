package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;

/**
 * Created by Miguel Salinas on 29/1/2022.
 */
public class RecepcionEnfermoHelper {

    public static ContentValues crearRecepcionEnfermoContentValues(RecepcionEnfermo ordenLaboratorio) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idRecepcion, ordenLaboratorio.getIdRecepcion());
        cv.put(MainDBConstants.participante, ordenLaboratorio.getParticipante().getCodigo());
        cv.put(MainDBConstants.fechaRecepcion, ordenLaboratorio.getFechaRecepcion().getTime());
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

    public static RecepcionEnfermo crearRecepcionEnfermo(Cursor cursor) {
        RecepcionEnfermo mRecepcion = new RecepcionEnfermo();
        mRecepcion.setIdRecepcion(cursor.getString(cursor.getColumnIndex(MainDBConstants.idRecepcion)));
        mRecepcion.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaRecepcion))>0) mRecepcion.setFechaRecepcion(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fechaRecepcion)))));
        mRecepcion.setTipoTubo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoTubo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)) > 0) mRecepcion.setVolumen(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)));
        mRecepcion.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fis))>0) mRecepcion.setFis(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fis)))));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fif))>0) mRecepcion.setFif(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fif)))));
        mRecepcion.setCategoria(cursor.getString(cursor.getColumnIndex(MainDBConstants.categoria)));
        mRecepcion.setConsulta(cursor.getString(cursor.getColumnIndex(MainDBConstants.consulta)));
        mRecepcion.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoMuestra)));
        mRecepcion.setEstudiosAct(cursor.getString(cursor.getColumnIndex(MainDBConstants.estudiosAct)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mRecepcion.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mRecepcion.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mRecepcion.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mRecepcion.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcion.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcion;
    }
}
