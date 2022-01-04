package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;

/**
 * Created by Miguel Salinas on 13/12/2021.
 */
public class PuntoCandidatoHelper extends BindStatementHelper{
    
    public static ContentValues crearPuntosCandidatosContenValues(PuntoCandidato punto) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, punto.getCodigo());
        cv.put(MainDBConstants.barrio, punto.getBarrio());
        if (punto.getLatitud() != null) cv.put(MainDBConstants.latitud, punto.getLatitud());
        if (punto.getLongitud() != null) cv.put(MainDBConstants.longitud, punto.getLongitud());
        cv.put(MainDBConstants.descartado, punto.getDescartado());
        cv.put(MainDBConstants.razonDescarte, punto.getRazonDescarte());
        cv.put(MainDBConstants.otraRazonDescarte, punto.getOtraRazonDescarte());
        if (punto.getFechaDescarte() != null) cv.put(MainDBConstants.fechaDescarte, punto.getFechaDescarte().getTime());
        cv.put(MainDBConstants.usuarioDescarte, punto.getUsuarioDescarte());
        
        if (punto.getRecordDate() != null) cv.put(MainDBConstants.recordDate, punto.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, punto.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(punto.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(punto.getEstado()));
        cv.put(MainDBConstants.deviceId, punto.getDeviceid());
        
        return cv;
    }
    
    public static PuntoCandidato crearPuntoCandidato(Cursor cursor) {
        PuntoCandidato punto = new PuntoCandidato();
        punto.setCodigo(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigo)));
        punto.setBarrio(cursor.getString(cursor.getColumnIndex(MainDBConstants.barrio)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.latitud))!= 0) punto.setLatitud(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.latitud)));
        if (cursor.getDouble(cursor.getColumnIndex(MainDBConstants.longitud))!= 0) punto.setLongitud(cursor.getDouble(cursor.getColumnIndex(MainDBConstants.longitud)));
        punto.setDescartado(cursor.getString(cursor.getColumnIndex(MainDBConstants.descartado)));
        punto.setRazonDescarte(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonDescarte)));
        punto.setOtraRazonDescarte(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonDescarte)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaDescarte))>0) punto.setFechaDescarte(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaDescarte))));
        punto.setUsuarioDescarte(cursor.getString(cursor.getColumnIndex(MainDBConstants.usuarioDescarte)));
        
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) punto.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        punto.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        punto.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        punto.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        punto.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return punto;
    }

    public static void fillPuntoCandidatoStatement(SQLiteStatement stat, PuntoCandidato punto) {
        stat.bindLong(1, punto.getCodigo());
        bindString(stat,2, punto.getBarrio());
        bindDouble(stat, 3, punto.getLatitud());
        bindDouble(stat, 4, punto.getLongitud());
        bindString(stat, 5, punto.getDescartado());
        bindString(stat, 6, punto.getRazonDescarte());
        bindString(stat, 7, punto.getOtraRazonDescarte());
        bindDate(stat, 8, punto.getFechaDescarte());
        bindString(stat, 9, punto.getUsuarioDescarte());
        bindDate(stat,10, punto.getRecordDate());
        bindString(stat,11, punto.getRecordUser());
        stat.bindString(12, String.valueOf(punto.getPasive()));
        bindString(stat,13, punto.getDeviceid());
        stat.bindString(14, String.valueOf(punto.getEstado()));
    }
}
