package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.a2cares.app.database.helpers.BindStatementHelper;
import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;


/**
 * Created by Miguel Salinas on 29/1/2022.
 */
public class RecepcionEnfermoHelper extends BindStatementHelper {

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
    public static ContentValues crearRecepcionEnfermoContentValues1(RecepcionEnfermomessage recepcionEnfermo) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idRecepcion, recepcionEnfermo.getIdRecepcion());
        cv.put(MainDBConstants.participante, recepcionEnfermo.getParticipante());
        cv.put(MainDBConstants.fechaRecepcion, recepcionEnfermo.getFechaRecepcion().getTime());
        cv.put(MainDBConstants.tipoTubo, recepcionEnfermo.getTipoTubo());
        cv.put(MainDBConstants.volumen, recepcionEnfermo.getVolumen());
        cv.put(MainDBConstants.observacion, recepcionEnfermo.getObservacion());
        if (recepcionEnfermo.getFis() != null) cv.put(MainDBConstants.fis, recepcionEnfermo.getFis().getTime());
        if (recepcionEnfermo.getFif() != null) cv.put(MainDBConstants.fif, recepcionEnfermo.getFif().getTime());
        cv.put(MainDBConstants.categoria, recepcionEnfermo.getCategoria());
        cv.put(MainDBConstants.consulta, recepcionEnfermo.getConsulta());
        cv.put(MainDBConstants.tipoMuestra, recepcionEnfermo.getTipoMuestra());
        cv.put(MainDBConstants.estudiosAct, recepcionEnfermo.getEstudiosAct());

        if (recepcionEnfermo.getRecordDate() != null) cv.put(MainDBConstants.recordDate, recepcionEnfermo.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, recepcionEnfermo.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(recepcionEnfermo.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(recepcionEnfermo.getEstado()));
        cv.put(MainDBConstants.deviceId, recepcionEnfermo.getDeviceid());
        return cv;
    }
    public static RecepcionEnfermo crearRecepcionEnfermo(Cursor cursor) {
        RecepcionEnfermo mRecepcion = new RecepcionEnfermo();
        mRecepcion.setIdRecepcion(cursor.getString(cursor.getColumnIndex(MainDBConstants.idRecepcion)));
        mRecepcion.setParticipante(null) ;
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
        //mRecepcion.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        //mRecepcion.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcion.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcion;
    }
    public static RecepcionEnfermomessage crearRecepcionEnfermo1(Cursor cursor) {
        RecepcionEnfermomessage mRecepcion = new RecepcionEnfermomessage();
        mRecepcion.setIdRecepcion(cursor.getString(cursor.getColumnIndex(MainDBConstants.idRecepcion)));
        mRecepcion.setParticipante(null) ;
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
        //mRecepcion.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        //mRecepcion.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mRecepcion.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mRecepcion;
    }
    public static void fillRecepcionEnfermoStatement(SQLiteStatement stat, RecepcionEnfermomessage recepcionEnfermo) {
        stat.bindString(1, recepcionEnfermo.getIdRecepcion());
        stat.bindString(2, recepcionEnfermo.getParticipante());
        //stat.bindString(3, recepcionEnfermo.getCategoria());
       // stat.bindString(5, recepcionEnfermo.getTipoMuestra());
        bindDate(stat, 3, recepcionEnfermo.getFechaRecepcion());
        stat.bindString(4, recepcionEnfermo.getConsulta());
        stat.bindNull( 5) ;
        //stat.bindString(6, recepcionEnfermo.getDeviceid() );
        //stat.bindString(7, recepcionEnfermo.getTipoTubo() );
        //stat.bindDouble(8, recepcionEnfermo.getVolumen() ) ;
        //stat.bindString(9, recepcionEnfermo.getObservacion() );
        //stat.bindString(10, recepcionEnfermo.getCategoria() );

        bindDate(stat, 6, recepcionEnfermo.getFis() );
        bindDate(stat, 7, recepcionEnfermo.getFif());

        //stat.bindString(13, String.valueOf(recepcionEnfermo.getEstado()));
        //stat.bindString(14, recepcionEnfermo.getRecordUser());
        //stat.bindString(15, recepcionEnfermo.getDeviceid());
        //stat.bindString(7, recepcionEnfermo.getPositivo());





    }
}
