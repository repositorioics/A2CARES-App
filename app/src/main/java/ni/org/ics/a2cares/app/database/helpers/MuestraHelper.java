package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.EncuestasDBConstants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;

/**
 * Created by Miguel Salinas on 8/7/2021.
 */
public class MuestraHelper {

    public static ContentValues crearMuestraContentValues(Muestra muestra){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idMuestra, muestra.getIdMuestra());
        cv.put(MainDBConstants.participante, muestra.getParticipante().getCodigo());
        if (muestra.getFechaMuestra() != null) cv.put(MainDBConstants.fechaMuestra, muestra.getFechaMuestra().getTime());
        cv.put(MainDBConstants.tuboBHC, muestra.getTuboBHC());
        cv.put(MainDBConstants.codigoBHC, muestra.getCodigoBHC());
        cv.put(MainDBConstants.volumenBHC, muestra.getVolumenBHC());
        cv.put(MainDBConstants.razonNoBHC, muestra.getRazonNoBHC());
        cv.put(MainDBConstants.otraRazonNoBHC, muestra.getOtraRazonNoBHC());
        cv.put(MainDBConstants.tuboRojo, muestra.getTuboRojo());
        cv.put(MainDBConstants.codigoRojo, muestra.getCodigoRojo());
        cv.put(MainDBConstants.volumenRojo, muestra.getVolumenRojo());
        cv.put(MainDBConstants.razonNoRojo, muestra.getRazonNoRojo());
        cv.put(MainDBConstants.otraRazonNoRojo, muestra.getOtraRazonNoRojo());
        cv.put(MainDBConstants.terreno, muestra.getTerreno());
        cv.put(MainDBConstants.pinchazos, muestra.getPinchazos());
        cv.put(MainDBConstants.estudiosAct, muestra.getEstudiosAct());
        cv.put(MainDBConstants.proposito, muestra.getProposito());
        cv.put(MainDBConstants.observacion, muestra.getObservacion());
        cv.put(MainDBConstants.descOtraObservacion, muestra.getDescOtraObservacion());


        if (muestra.getRecordDate() != null) cv.put(MainDBConstants.recordDate, muestra.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, muestra.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(muestra.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(muestra.getEstado()));
        cv.put(MainDBConstants.deviceId, muestra.getDeviceid());

        return cv;
    }

    public static Muestra crearMuestra(Cursor cursor){
        Muestra muestra = new Muestra();

        muestra.setIdMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.idMuestra)));
        muestra.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) muestra.setFechaMuestra(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaMuestra))));
        muestra.setTuboBHC(cursor.getString(cursor.getColumnIndex(MainDBConstants.tuboBHC)));
        muestra.setCodigoBHC(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoBHC)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumenBHC)) > 0) muestra.setVolumenBHC(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumenBHC)));
        muestra.setRazonNoBHC(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoBHC)));
        muestra.setOtraRazonNoBHC(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoBHC)));
        muestra.setTuboRojo(cursor.getString(cursor.getColumnIndex(MainDBConstants.tuboRojo)));
        muestra.setCodigoRojo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoRojo)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumenRojo)) > 0) muestra.setVolumenRojo(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.volumenRojo)));
        muestra.setRazonNoRojo(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoRojo)));
        muestra.setOtraRazonNoRojo(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoRojo)));
        muestra.setTerreno(cursor.getString(cursor.getColumnIndex(MainDBConstants.terreno)));
        muestra.setPinchazos(cursor.getString(cursor.getColumnIndex(MainDBConstants.pinchazos)));
        muestra.setEstudiosAct(cursor.getString(cursor.getColumnIndex(MainDBConstants.estudiosAct)));
        muestra.setProposito(cursor.getString(cursor.getColumnIndex(MainDBConstants.proposito)));
        muestra.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));
        muestra.setDescOtraObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.descOtraObservacion)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) muestra.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        muestra.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        muestra.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        muestra.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        muestra.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return muestra;
    }
}
