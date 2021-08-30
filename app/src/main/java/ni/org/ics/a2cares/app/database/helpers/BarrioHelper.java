package ni.org.ics.a2cares.app.database.helpers;

import java.util.Date;

import net.sqlcipher.database.SQLiteStatement;
import android.content.ContentValues;
import android.database.Cursor;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Barrio;

public class BarrioHelper extends BindStatementHelper {
	
	public static ContentValues crearBarrioContentValues(Barrio barrio){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, barrio.getCodigo());
		cv.put(MainDBConstants.nombre, barrio.getNombre());
		if (barrio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, barrio.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, barrio.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(barrio.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(barrio.getEstado()));
		cv.put(MainDBConstants.deviceId, barrio.getDeviceid());
		return cv; 
	}	
	
	public static Barrio crearBarrio(Cursor cursorBarrio){
		Barrio mBarrio = new Barrio();
		mBarrio.setCodigo(cursorBarrio.getInt(cursorBarrio.getColumnIndex(MainDBConstants.codigo)));
		mBarrio.setNombre(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.nombre)));
		if(cursorBarrio.getLong(cursorBarrio.getColumnIndex(MainDBConstants.recordDate))>0) mBarrio.setRecordDate(new Date(cursorBarrio.getLong(cursorBarrio.getColumnIndex(MainDBConstants.recordDate))));
		mBarrio.setRecordUser(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.recordUser)));
		mBarrio.setPasive(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mBarrio.setEstado(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mBarrio.setDeviceid(cursorBarrio.getString(cursorBarrio.getColumnIndex(MainDBConstants.deviceId)));
		return mBarrio;
	}

	public static void fillBarrioStatement(SQLiteStatement stat, Barrio barrio){
		stat.bindLong(1, barrio.getCodigo());
		bindString(stat,2, barrio.getNombre());
		bindDate(stat,3, barrio.getRecordDate());
		bindString(stat,4, barrio.getRecordUser());
		stat.bindString(5, String.valueOf(barrio.getPasive()));
		stat.bindString(6, String.valueOf(barrio.getEstado()));
		bindString(stat,7, barrio.getDeviceid());
	}
}
