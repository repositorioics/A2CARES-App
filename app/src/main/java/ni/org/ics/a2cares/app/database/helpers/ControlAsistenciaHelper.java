package ni.org.ics.a2cares.app.database.helpers;

import static ni.org.ics.a2cares.app.database.helpers.BindStatementHelper.bindDate;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.CambioDomicilio;
import ni.org.ics.a2cares.app.domain.core.ControlAsistencia;

/**
 * Created by Everts Morales
 */
public class ControlAsistenciaHelper {

    public static ContentValues crearControlAsistenciaContentValues(ControlAsistencia controlAsistencia){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, controlAsistencia.getId());
      //  cv.put(MainDBConstants.usuarioregistro, controlAsistencia.getUsuarioregistro());
        if (controlAsistencia.getFechaasistencia() != null) cv.put(MainDBConstants.fechaasistencia, controlAsistencia.getFechaasistencia().getTime());
        cv.put(MainDBConstants.horaentrada , controlAsistencia.getHoraentrada());
        cv.put(MainDBConstants.horasalida, controlAsistencia.getHorasalida());
        cv.put(MainDBConstants.latitud, controlAsistencia.getLatitud());
        cv.put(MainDBConstants.longitud, controlAsistencia.getLongitud());


        //metadata
        if (controlAsistencia.getRecordDate() != null) cv.put(MainDBConstants.recordDate, controlAsistencia.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, controlAsistencia.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(controlAsistencia.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(controlAsistencia.getEstado()));
        cv.put(MainDBConstants.deviceId, controlAsistencia.getDeviceid());


        return cv;
    }

    public static ControlAsistencia crearControlAsistencia(Cursor cursorasistencia){
        ControlAsistencia mcontrolAsistencia = new ControlAsistencia();

        mcontrolAsistencia.setId(cursorasistencia.getInt(cursorasistencia.getColumnIndex(MainDBConstants.id))); ;
        //mcontrolAsistencia.setId(1);
        if(cursorasistencia.getLong(cursorasistencia.getColumnIndex(MainDBConstants.fechaasistencia))>0) mcontrolAsistencia.setFechaasistencia(new Date(cursorasistencia.getLong(cursorasistencia.getColumnIndex(MainDBConstants.fechaasistencia))));
       // mcontrolAsistencia.setUsuarioregistro(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.usuarioregistro)));
        mcontrolAsistencia.setHoraentrada(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.horaentrada)));
        mcontrolAsistencia.setHorasalida(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.horasalida)));
        if (cursorasistencia.getDouble(cursorasistencia.getColumnIndex(MainDBConstants.latitud)) > 0) mcontrolAsistencia.setLatitud(cursorasistencia.getDouble(cursorasistencia.getColumnIndex(MainDBConstants.latitud)));
        if (cursorasistencia.getDouble(cursorasistencia.getColumnIndex(MainDBConstants.longitud)) != 0) mcontrolAsistencia.setLongitud(cursorasistencia.getDouble(cursorasistencia.getColumnIndex(MainDBConstants.longitud)));


        if(cursorasistencia.getLong(cursorasistencia.getColumnIndex(MainDBConstants.recordDate))>0) mcontrolAsistencia.setRecordDate(new Date(cursorasistencia.getLong(cursorasistencia.getColumnIndex(MainDBConstants.recordDate))));
        mcontrolAsistencia.setRecordUser(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.recordUser)));
        mcontrolAsistencia.setPasive(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mcontrolAsistencia.setEstado(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mcontrolAsistencia.setDeviceid(cursorasistencia.getString(cursorasistencia.getColumnIndex(MainDBConstants.deviceId)));
        return mcontrolAsistencia;
    }
    public static void fillCambioDomicilioStatement(SQLiteStatement stat, ControlAsistencia controlAsistencia) {
        stat.bindLong(1, controlAsistencia.getId());
        bindDate(stat, 2, controlAsistencia.getFechaasistencia());
        stat.bindString(3, controlAsistencia.getHoraentrada());
        stat.bindString(4, controlAsistencia.getHorasalida());
        stat.bindDouble(5, controlAsistencia.getLatitud());
        stat.bindDouble(6, controlAsistencia.getLongitud());
       // stat.bindString(7, controlAsistencia.getUsuarioregistro());

    }
}
