package ni.org.ics.a2cares.app.database.helpers;

import static ni.org.ics.a2cares.app.database.helpers.BindStatementHelper.bindDate;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.ControlTemperaturaTermo;

/**
 * Created by Everts Morales
 */
public class ControlTemperaturaHelper {

    public static ContentValues crearControlTemperaturaContentValues(ControlTemperaturaTermo controlTemperatura){
        ContentValues cv = new ContentValues();
       // cv.put(MainDBConstants.id, controlTemperatura.getId());
      //  cv.put(MainDBConstants.usuarioregistro, controlAsistencia.getUsuarioregistro());
        if (controlTemperatura.getFechatemp() != null) cv.put(MainDBConstants.fechaTomaTemperatura, controlTemperatura.getFechatemp().getTime());
        cv.put(MainDBConstants.horaTomaTemperatura , controlTemperatura.getHoratomatemp());
        cv.put(MainDBConstants.temperaturaTermo, controlTemperatura.getTemperaturaTermo());
        cv.put(MainDBConstants.usuarioregistro, controlTemperatura.getRecordUser());

        //metadata
        if (controlTemperatura.getRecordDate() != null) cv.put(MainDBConstants.recordDate, controlTemperatura.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, controlTemperatura.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(controlTemperatura.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(controlTemperatura.getEstado()));
        cv.put(MainDBConstants.deviceId, controlTemperatura.getDeviceid());


        return cv;
    }

    public static ControlTemperaturaTermo crearControlTemperatura(Cursor cursortemperatura){
        ControlTemperaturaTermo mcontrolTemperatura = new ControlTemperaturaTermo();

        //mcontrolAsistencia.setId(cursorasistencia.getInt(cursorasistencia.getColumnIndex(MainDBConstants.id))); ;
        //mcontrolAsistencia.setId(1);
        if(cursortemperatura.getLong(cursortemperatura.getColumnIndex(MainDBConstants.fechaTomaTemperatura))>0) mcontrolTemperatura.setFechatemp(new Date(cursortemperatura.getLong(cursortemperatura.getColumnIndex(MainDBConstants.fechaTomaTemperatura))));
       //mcontrolAsistencia.setUsuarioregistro(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.usuarioregistro)));
        mcontrolTemperatura.setHoratomatemp(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.horaTomaTemperatura)));
        mcontrolTemperatura.setTemperaturaTermo(cursortemperatura.getDouble(cursortemperatura.getColumnIndex(MainDBConstants.temperaturaTermo)));
        mcontrolTemperatura.setUsuario(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.recordUser)));

        if(cursortemperatura.getLong(cursortemperatura.getColumnIndex(MainDBConstants.recordDate))>0) mcontrolTemperatura.setRecordDate(new Date(cursortemperatura.getLong(cursortemperatura.getColumnIndex(MainDBConstants.recordDate))));
        mcontrolTemperatura.setRecordUser(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.recordUser)));
        mcontrolTemperatura.setPasive(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mcontrolTemperatura.setEstado(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mcontrolTemperatura.setDeviceid(cursortemperatura.getString(cursortemperatura.getColumnIndex(MainDBConstants.deviceId)));
        return mcontrolTemperatura;
    }

}
