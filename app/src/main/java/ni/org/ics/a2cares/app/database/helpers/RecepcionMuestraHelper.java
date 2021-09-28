package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;

/**
 * Created by Miguel Salinas on 30/8/2021.
 */
public class RecepcionMuestraHelper {

    public static ContentValues crearRecepcionMuestraContentValues(RecepcionMuestra recepcionMuestra){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idRecepcion, recepcionMuestra.getIdRecepcion());
        cv.put(MainDBConstants.codigoMx, recepcionMuestra.getCodigoMx());
        if (recepcionMuestra.getFechaRecepcion()!=null) cv.put(MainDBConstants.fechaRecepcion, recepcionMuestra.getFechaRecepcion().getTime());
        cv.put(MainDBConstants.volumen, recepcionMuestra.getVolumen());
        cv.put(MainDBConstants.observacion, recepcionMuestra.getObservacion());
        cv.put(MainDBConstants.lugar, recepcionMuestra.getLugar());
        cv.put(MainDBConstants.tipoTubo, recepcionMuestra.getTipoTubo());

        if (recepcionMuestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, recepcionMuestra.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, recepcionMuestra.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(recepcionMuestra.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(recepcionMuestra.getEstado()));
        cv.put(MainDBConstants.deviceId, recepcionMuestra.getDeviceid());
        return cv;
    }

    public static RecepcionMuestra crearRecepcionMuestra(Cursor cursor){
        RecepcionMuestra mRecepcionMuestra = new RecepcionMuestra();
        mRecepcionMuestra.setIdRecepcion(cursor.getString(cursor.getColumnIndex(MainDBConstants.idRecepcion)));
        mRecepcionMuestra.setCodigoMx(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoMx)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaRecepcion))>0) mRecepcionMuestra.setFechaRecepcion(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaRecepcion))));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen))>0) mRecepcionMuestra.setVolumen(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)));
        mRecepcionMuestra.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));
        mRecepcionMuestra.setLugar(cursor.getString(cursor.getColumnIndex(MainDBConstants.lugar)));
        mRecepcionMuestra.setTipoTubo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoTubo)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mRecepcionMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mRecepcionMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mRecepcionMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mRecepcionMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcionMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcionMuestra;
    }
}
