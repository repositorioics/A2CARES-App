package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.puntos.PuntoDescartado;

/**
 * Created by Miguel Salinas on 13/12/2021.
 */
public class PuntoDescartadoHelper {

    public static ContentValues crearPuntoDescartadoContentValues(PuntoDescartado obj) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, obj.getCodigo());
        cv.put(MainDBConstants.codigoCandidato, obj.getCandidato().getCodigo());
        cv.put(MainDBConstants.motivo, obj.getMotivo());
        cv.put(MainDBConstants.otroMotivo, obj.getOtroMotivo());
        
        if (obj.getRecordDate() != null) cv.put(MainDBConstants.recordDate, obj.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, obj.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(obj.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(obj.getEstado()));
        cv.put(MainDBConstants.deviceId, obj.getDeviceid());
        return cv;
    }
    
    
}
