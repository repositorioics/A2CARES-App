package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.RazonNoData;

/**
 * Created by Miguel Salinas on 28/12/2021.
 */
public class RazonNoDataHelper {

    public static ContentValues crearRazonNoDataContentValues(RazonNoData razonNoData){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, razonNoData.getId());
        cv.put(MainDBConstants.codigo, razonNoData.getCodigoParticipante());
        cv.put(MainDBConstants.razon, razonNoData.getRazon());
        cv.put(MainDBConstants.otraRazon, razonNoData.getOtraRazon());

        if (razonNoData.getRecordDate() != null) cv.put(MainDBConstants.recordDate, razonNoData.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, razonNoData.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(razonNoData.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(razonNoData.getEstado()));
        cv.put(MainDBConstants.deviceId, razonNoData.getDeviceid());
        return cv;
    }

    public static RazonNoData crearRazonNoData(Cursor cursor){
        RazonNoData razonNoData = new RazonNoData();
        razonNoData.setId(null);
        razonNoData.setCodigoParticipante(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        razonNoData.setRazon(cursor.getString(cursor.getColumnIndex(MainDBConstants.razon)));
        razonNoData.setOtraRazon(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazon)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) razonNoData.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        razonNoData.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        razonNoData.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        razonNoData.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        razonNoData.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return razonNoData;
    }
}
