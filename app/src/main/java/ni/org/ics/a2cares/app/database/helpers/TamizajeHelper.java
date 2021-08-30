package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Tamizaje;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class TamizajeHelper {

    public static ContentValues crearTamizajeContentValues(Tamizaje tamizaje){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, tamizaje.getCodigo());
        cv.put(MainDBConstants.estudio, tamizaje.getEstudio().getCodigo());
        cv.put(MainDBConstants.sexo, tamizaje.getSexo());
        if (tamizaje.getFechaNacimiento()!=null) cv.put(MainDBConstants.fechaNacimiento, tamizaje.getFechaNacimiento().getTime());
        cv.put(MainDBConstants.aceptaTamizajePersona, String.valueOf(tamizaje.getAceptaTamizajePersona()));
        cv.put(MainDBConstants.razonNoAceptaTamizajePersona, tamizaje.getRazonNoAceptaTamizajePersona());
        cv.put(MainDBConstants.otraRazonNoAceptaTamizajePersona, tamizaje.getOtraRazonNoAceptaTamizajePersona());
        cv.put(MainDBConstants.tipoVivienda, tamizaje.getTipoVivienda());
        cv.put(MainDBConstants.tiempoResidencia, tamizaje.getTiempoResidencia());
        cv.put(MainDBConstants.planesMudarse, tamizaje.getPlanesMudarse());
        cv.put(MainDBConstants.asentimientoVerbal, tamizaje.getAsentimientoVerbal());
        cv.put(MainDBConstants.aceptaParticipar, tamizaje.getAceptaParticipar());
        cv.put(MainDBConstants.razonNoAceptaParticipar, tamizaje.getRazonNoAceptaParticipar());
        cv.put(MainDBConstants.otraRazonNoAceptaParticipar, tamizaje.getOtraRazonNoAceptaParticipar());
        cv.put(MainDBConstants.esElegible, tamizaje.getEsElegible());

        if (tamizaje.getRecordDate() != null) cv.put(MainDBConstants.recordDate, tamizaje.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, tamizaje.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(tamizaje.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(tamizaje.getEstado()));
        cv.put(MainDBConstants.deviceId, tamizaje.getDeviceid());
        return cv;
    }

    public static Tamizaje crearTamizaje(Cursor cursor){
        Tamizaje mTamizaje = new Tamizaje();

        mTamizaje.setCodigo(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigo)));
        mTamizaje.setEstudio(null);
        mTamizaje.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        //Fechas menores a 1 de enero de 1970 se guardan como negativo
        if (cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento)) != 0) mTamizaje.setFechaNacimiento(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNacimiento))));
        mTamizaje.setAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaTamizajePersona)));
        mTamizaje.setRazonNoAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaTamizajePersona)));
        mTamizaje.setOtraRazonNoAceptaTamizajePersona(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoAceptaTamizajePersona)));
        mTamizaje.setTipoVivienda(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoVivienda)));
        mTamizaje.setTiempoResidencia(cursor.getString(cursor.getColumnIndex(MainDBConstants.tiempoResidencia)));
        mTamizaje.setPlanesMudarse(cursor.getString(cursor.getColumnIndex(MainDBConstants.planesMudarse)));
        mTamizaje.setAsentimientoVerbal(cursor.getString(cursor.getColumnIndex(MainDBConstants.asentimientoVerbal)));
        mTamizaje.setAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.aceptaParticipar)));
        mTamizaje.setRazonNoAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonNoAceptaParticipar)));
        mTamizaje.setOtraRazonNoAceptaParticipar(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonNoAceptaParticipar)));
        mTamizaje.setEsElegible(cursor.getString(cursor.getColumnIndex(MainDBConstants.esElegible)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mTamizaje.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mTamizaje.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mTamizaje.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mTamizaje.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mTamizaje.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));

        return mTamizaje;
    }
}
