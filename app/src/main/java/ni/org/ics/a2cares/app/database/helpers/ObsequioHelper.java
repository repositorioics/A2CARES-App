package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;
import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.ObsequioGeneral;

/**
 * Created by Miguel Salinas on 06/11/2018.
 * V1.0
 */
public class ObsequioHelper extends BindStatementHelper {

    public static ContentValues crearObsequioContentValues(ObsequioGeneral obsequio){
        ContentValues cv = new ContentValues();

        cv.put(MainDBConstants.id, obsequio.getId());
        cv.put(MainDBConstants.participante, obsequio.getParticipante());
        cv.put(MainDBConstants.casa, obsequio.getCasa());
        cv.put(MainDBConstants.seguimiento, obsequio.getSeguimiento());
        cv.put(MainDBConstants.numVisitaSeguimiento, obsequio.getNumVisitaSeguimiento());
        cv.put(MainDBConstants.motivo, obsequio.getMotivo());
        cv.put(MainDBConstants.entregoObsequio, obsequio.getEntregoObsequio());
        cv.put(MainDBConstants.personaRecibe, obsequio.getPersonaRecibe());
        cv.put(MainDBConstants.observacion, obsequio.getObservacion());

        if (obsequio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, obsequio.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, obsequio.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(obsequio.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(obsequio.getEstado()));
        cv.put(MainDBConstants.deviceId, obsequio.getDeviceid());

        return cv;
    }

    public static ObsequioGeneral crearObsequio(Cursor cursor){
        ObsequioGeneral mObsequio = new ObsequioGeneral();

        mObsequio.setId(cursor.getString(cursor.getColumnIndex(MainDBConstants.id)));
        mObsequio.setParticipante(cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)));
        mObsequio.setCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.casa)));
        mObsequio.setSeguimiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.seguimiento)));
        mObsequio.setNumVisitaSeguimiento(cursor.getString(cursor.getColumnIndex(MainDBConstants.numVisitaSeguimiento)));
        mObsequio.setMotivo(cursor.getString(cursor.getColumnIndex(MainDBConstants.motivo)));
        mObsequio.setEntregoObsequio(cursor.getString(cursor.getColumnIndex(MainDBConstants.entregoObsequio)));
        mObsequio.setPersonaRecibe(cursor.getString(cursor.getColumnIndex(MainDBConstants.personaRecibe)));
        mObsequio.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mObsequio.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mObsequio.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mObsequio.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mObsequio.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mObsequio.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mObsequio;
    }

    public static void fillObsequioStatement(SQLiteStatement stat, ObsequioGeneral obsequio){
        stat.bindString(1, obsequio.getId());
        stat.bindString(2, obsequio.getParticipante());
        bindString(stat,3, obsequio.getCasa());
        bindString(stat,4, obsequio.getSeguimiento());
        bindString(stat,5, obsequio.getNumVisitaSeguimiento());
        bindString(stat,6, obsequio.getMotivo());
        bindString(stat,7, obsequio.getEntregoObsequio());
        bindString(stat,8, obsequio.getPersonaRecibe());
        bindString(stat,9, obsequio.getObservacion());

        bindDate(stat,10, obsequio.getRecordDate());
        bindString(stat,11, obsequio.getRecordUser());
        stat.bindString(12, String.valueOf(obsequio.getPasive()));
        bindString(stat,13, obsequio.getDeviceid());
        stat.bindString(14, String.valueOf(obsequio.getEstado()));
    }
}
