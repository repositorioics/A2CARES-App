package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.laboratorio.Serologia;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;

/**
 * Created by Miguel Salinas on 28/12/2021.
 */
public class SerologiaHelper {

    public static ContentValues crearSerologiaLabContentValues(Serologia serologia){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idSerologia, serologia.getIdSerologia());
        cv.put(MainDBConstants.participante, serologia.getParticipante());
        if (serologia.getFecha()!=null) cv.put(MainDBConstants.fecha, serologia.getFecha().getTime());
        cv.put(MainDBConstants.volumen, serologia.getVolumen());
        cv.put(MainDBConstants.observacion, serologia.getObservacion());
        cv.put(MainDBConstants.enviado, String.valueOf(serologia.getEnviado()));
        cv.put(MainDBConstants.codigoCasa, serologia.getCodigo_casa());
        cv.put(MainDBConstants.edadMeses, serologia.getEdadMeses());
        cv.put(MainDBConstants.descripcion, serologia.getDescripcion());

        if (serologia.getRecordDate() != null) cv.put(MainDBConstants.recordDate, serologia.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, serologia.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(serologia.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(serologia.getEstado()));
        cv.put(MainDBConstants.deviceId, serologia.getDeviceid());
        return cv;
    }

    public static Serologia crearSerologiaLab(Cursor cursor){
        Serologia mRecepcionMuestra = new Serologia();
        mRecepcionMuestra.setIdSerologia(null); //siempre null, se va a setear autoincremental
        mRecepcionMuestra.setParticipante(cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fecha))>0) mRecepcionMuestra.setFecha(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fecha))));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen))>0) mRecepcionMuestra.setVolumen(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumen)));
        mRecepcionMuestra.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));
        mRecepcionMuestra.setEnviado(cursor.getString(cursor.getColumnIndex(MainDBConstants.enviado)).charAt(0));
        mRecepcionMuestra.setCodigo_casa(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigoCasa)));
        mRecepcionMuestra.setEdadMeses(cursor.getInt(cursor.getColumnIndex(MainDBConstants.edadMeses)));
        mRecepcionMuestra.setDescripcion(cursor.getString(cursor.getColumnIndex(MainDBConstants.descripcion)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mRecepcionMuestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mRecepcionMuestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mRecepcionMuestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mRecepcionMuestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcionMuestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcionMuestra;
    }
}
