package ni.org.ics.a2cares.app.database.helpers;

import java.util.Date;

import net.sqlcipher.database.SQLiteStatement;

import android.content.ContentValues;
import android.database.Cursor;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Estudio;

public class EstudiosHelper extends BindStatementHelper {
	
	public static ContentValues crearEstudioContentValues(Estudio estudio){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, estudio.getCodigo());
		cv.put(MainDBConstants.nombre, estudio.getNombre());
		if (estudio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, estudio.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, estudio.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(estudio.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(estudio.getEstado()));
		cv.put(MainDBConstants.deviceId, estudio.getDeviceid());
		return cv; 
	}	
	
	public static Estudio crearEstudio(Cursor cursorEstudio){
		
		Estudio mEstudio = new Estudio();
		mEstudio.setCodigo(cursorEstudio.getInt(cursorEstudio.getColumnIndex(MainDBConstants.codigo)));
		mEstudio.setNombre(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.nombre)));
		if(cursorEstudio.getLong(cursorEstudio.getColumnIndex(MainDBConstants.recordDate))>0) mEstudio.setRecordDate(new Date(cursorEstudio.getLong(cursorEstudio.getColumnIndex(MainDBConstants.recordDate))));
		mEstudio.setRecordUser(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.recordUser)));
		mEstudio.setPasive(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mEstudio.setEstado(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mEstudio.setDeviceid(cursorEstudio.getString(cursorEstudio.getColumnIndex(MainDBConstants.deviceId)));
		return mEstudio;
	}

	public static void fillEstudioStatement(SQLiteStatement stat, Estudio estudio) {
		stat.bindLong(1, estudio.getCodigo());
		stat.bindString(2, estudio.getNombre());
		bindDate(stat,3, estudio.getRecordDate());
		bindString(stat,4, estudio.getRecordUser());
		stat.bindString(5, String.valueOf(estudio.getPasive()));
		stat.bindString(6, String.valueOf(estudio.getEstado()));
		bindString(stat,7, estudio.getDeviceid());

	}
}
