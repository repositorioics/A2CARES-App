package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Admision;
import ni.org.ics.a2cares.app.domain.core.Muestra;

/**
 * Created by Everts Morales 18/12/2023.
 */
public class AdmisionHelper {

    public static ContentValues crearAdmisionContentValues(Admision admision){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, admision.getId() );
        cv.put(MainDBConstants.perteneceEstudio, admision.getPerteneceEstudio());
        cv.put(MainDBConstants.codigoParticipante, admision.getCodigoParticipante());
        cv.put(MainDBConstants.febril, admision.getFebril());
        cv.put(MainDBConstants.sexo, admision.getSexo());
        cv.put(MainDBConstants.edad, admision.getEdad());
        cv.put(MainDBConstants.numeroHoja, admision.getNumeroHoja());

        if (admision.getRecordDate() != null) cv.put(MainDBConstants.recordDate, admision.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, admision.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(admision.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(admision.getEstado()));
        cv.put(MainDBConstants.deviceId, admision.getDeviceid());

        return cv;
    }

    public static Admision crearAdmision(Cursor cursor){
        Admision admision = new Admision();

        admision.setId(cursor.getInt(cursor.getColumnIndex(MainDBConstants.id)));
        admision.setPerteneceEstudio(cursor.getString(cursor.getColumnIndex(MainDBConstants.perteneceEstudio)));
        admision.setCodigoParticipante(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoParticipante)));
        admision.setFebril(cursor.getString(cursor.getColumnIndex(MainDBConstants.febril)));
        admision.setEdad( cursor.getString(cursor.getColumnIndex(MainDBConstants.edad)));
        admision.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        admision.setNumeroHoja(cursor.getInt(cursor.getColumnIndex(MainDBConstants.numeroHoja)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) admision.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        admision.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        admision.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        admision.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        admision.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return admision;
    }
}
