package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteStatement;

import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class ParticipanteHelper extends BindStatementHelper {

    public static ContentValues crearParticipanteContentValues(Participante participante){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, participante.getCodigo());
        cv.put(MainDBConstants.nombre1, participante.getNombre1());
        cv.put(MainDBConstants.nombre2, participante.getNombre2());
        cv.put(MainDBConstants.apellido1, participante.getApellido1());
        cv.put(MainDBConstants.apellido2, participante.getApellido2());
        cv.put(MainDBConstants.sexo, participante.getSexo());
        if (participante.getFechaNac()!=null) cv.put(MainDBConstants.fechaNac, participante.getFechaNac().getTime());
        cv.put(MainDBConstants.nombre1Padre, participante.getNombre1Padre());
        cv.put(MainDBConstants.nombre2Padre, participante.getNombre2Padre());
        cv.put(MainDBConstants.apellido1Padre, participante.getApellido1Padre());
        cv.put(MainDBConstants.apellido2Padre, participante.getApellido2Padre());
        cv.put(MainDBConstants.nombre1Madre, participante.getNombre1Madre());
        cv.put(MainDBConstants.nombre2Madre, participante.getNombre2Madre());
        cv.put(MainDBConstants.apellido1Madre, participante.getApellido1Madre());
        cv.put(MainDBConstants.apellido2Madre, participante.getApellido2Madre());
        if (participante.getCasa() != null) cv.put(MainDBConstants.casa, participante.getCasa().getCodigo());
        cv.put(MainDBConstants.nombre1Tutor, participante.getNombre1Tutor());
        cv.put(MainDBConstants.nombre2Tutor, participante.getNombre2Tutor());
        cv.put(MainDBConstants.apellido1Tutor, participante.getApellido1Tutor());
        cv.put(MainDBConstants.apellido2Tutor, participante.getApellido2Tutor());
        cv.put(MainDBConstants.relacionFamiliarTutor, participante.getRelacionFamiliarTutor());

        if (participante.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participante.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participante.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participante.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participante.getEstado()));
        cv.put(MainDBConstants.deviceId, participante.getDeviceid());
        return cv;
    }

    public static Participante crearParticipante(Cursor cursor){
        Participante mParticipante = new Participante();
        mParticipante.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mParticipante.setNombre1(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1)));
        mParticipante.setNombre2(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2)));
        mParticipante.setApellido1(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1)));
        mParticipante.setApellido2(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2)));
        mParticipante.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        mParticipante.setFechaNac(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNac))));
        mParticipante.setNombre1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Padre)));
        mParticipante.setNombre2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Padre)));
        mParticipante.setApellido1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Padre)));
        mParticipante.setApellido2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Padre)));
        mParticipante.setNombre1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Madre)));
        mParticipante.setNombre2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Madre)));
        mParticipante.setApellido1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Madre)));
        mParticipante.setApellido2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Madre)));
        mParticipante.setCasa(null);
        mParticipante.setNombre1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Tutor)));
        mParticipante.setNombre2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Tutor)));
        mParticipante.setApellido1Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Tutor)));
        mParticipante.setApellido2Tutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Tutor)));
        mParticipante.setRelacionFamiliarTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.relacionFamiliarTutor)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mParticipante.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mParticipante.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mParticipante.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mParticipante.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mParticipante.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mParticipante;
    }

    public static void fillParticipanteStatement(SQLiteStatement stat, Participante participante){
        bindString(stat,1, participante.getCodigo());
        bindString(stat,2, participante.getNombre1());
        bindString(stat, 3, participante.getNombre2()); //if (participante.getNombre2() != null)	stat.bindString(3, participante.getNombre2());
        bindString(stat,4, participante.getApellido1());
        bindString(stat, 5, participante.getApellido2());//if (participante.getApellido2() != null) stat.bindString(5, participante.getApellido2());
        bindString(stat,6, participante.getSexo());
        bindDate(stat,7, participante.getFechaNac());
        bindString(stat,8, participante.getNombre1Padre());
        bindString(stat,9, participante.getNombre2Padre());
        bindString(stat,10, participante.getApellido1Padre());
        bindString(stat,11, participante.getApellido2Padre());
        bindString(stat,12, participante.getNombre1Madre());
        bindString(stat,13, participante.getNombre2Madre());
        bindString(stat,14, participante.getApellido1Madre());
        bindString(stat,15, participante.getApellido2Madre());
        bindInteger(stat,16, participante.getCasa().getCodigo());
        bindString(stat,17, participante.getNombre1Tutor());
        bindString(stat,18, participante.getNombre2Tutor());
        bindString(stat,19, participante.getApellido1Tutor());
        bindString(stat,20, participante.getApellido2Tutor());
        bindString(stat,21, participante.getRelacionFamiliarTutor());

        bindLong(stat,22, participante.getRecordDate().getTime());
        bindString(stat,23, participante.getRecordUser());
        bindString(stat,24, String.valueOf(participante.getPasive()));
        bindString(stat,25, participante.getDeviceid());
        bindString(stat,26, String.valueOf(participante.getEstado()));
    }


    public static ParticipanteProcesos crearParticipanteProcesos(Cursor cursor){
        ParticipanteProcesos procesos = new ParticipanteProcesos();
        procesos.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        procesos.setPendienteEnCasa(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendienteEnCasa)));
        procesos.setPendienteEncPart(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendienteEncPart)));
        procesos.setPendientePyT(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendientePyT)));
        procesos.setPendienteMxMA(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendienteMxMA)));
        procesos.setPendienteMxTx(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendienteMxTx)));
        procesos.setRetirado(cursor.getInt(cursor.getColumnIndex(MainDBConstants.retirado)));
        procesos.setPendienteObseq(cursor.getString(cursor.getColumnIndex(MainDBConstants.pendienteObseq)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) procesos.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        procesos.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        procesos.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        procesos.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        procesos.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return procesos;
    }

    /**
     * Inserta un participantes_procesos en la base de datos
     *
     * @param participante
     *            Objeto Participantes_procesos que contiene la informacion
     *
     */
    public static ContentValues crearParticipanteProcesosContentValues(ParticipanteProcesos participante) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, participante.getCodigo());
        cv.put(MainDBConstants.pendienteEnCasa, participante.getPendienteEnCasa());
        cv.put(MainDBConstants.pendienteEncPart, participante.getPendienteEncPart());
        cv.put(MainDBConstants.pendientePyT, participante.getPendientePyT());
        cv.put(MainDBConstants.pendienteMxMA, participante.getPendienteMxMA());
        cv.put(MainDBConstants.pendienteMxTx, participante.getPendienteMxTx());
        cv.put(MainDBConstants.retirado, participante.getRetirado());
        cv.put(MainDBConstants.pendienteObseq, participante.getPendienteObseq());

        if (participante.getRecordDate() != null) cv.put(MainDBConstants.recordDate, participante.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, participante.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(participante.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(participante.getEstado()));
        cv.put(MainDBConstants.deviceId, participante.getDeviceid());
        return cv;
    }

    public static void fillParticipanteProcesosStatement(SQLiteStatement stat, ParticipanteProcesos participante){
        bindString(stat,1, participante.getCodigo());
        bindInteger(stat,2, participante.getRetirado());
        bindString(stat, 3, participante.getPendientePyT()); //if (participante.getNombre2() != null)	stat.bindString(3, participante.getNombre2());
        bindString(stat,4, participante.getPendienteEncPart());
        bindString(stat, 5, participante.getPendienteEnCasa());//if (participante.getApellido2() != null) stat.bindString(5, participante.getApellido2());
        bindString(stat,6, participante.getPendienteMxMA());
        bindString(stat,7, participante.getPendienteMxTx());
        bindString(stat,8, participante.getPendienteObseq());

        stat.bindLong(9, participante.getRecordDate().getTime());
        bindString(stat,10, participante.getRecordUser());
        stat.bindString(11, String.valueOf(participante.getPasive()));
        bindString(stat,12, participante.getDeviceid());
        stat.bindString(13, String.valueOf(participante.getEstado()));
    }

}
