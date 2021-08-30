package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;

public class DatosCoordenadasHelper {
	
	public static ContentValues crearDatosCoordenadaContentValues(DatosCoordenadas datosCoordenadas){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.codigo, datosCoordenadas.getCodigo());
        cv.put(MainDBConstants.casa, datosCoordenadas.getCodigoCasa());
        cv.put(MainDBConstants.estudios, datosCoordenadas.getEstudios());
        cv.put(MainDBConstants.participante, datosCoordenadas.getParticipante().getCodigo());
        cv.put(MainDBConstants.motivo, datosCoordenadas.getMotivo());
		cv.put(MainDBConstants.barrio, datosCoordenadas.getBarrio().getCodigo());
		cv.put(MainDBConstants.direccion, datosCoordenadas.getDireccion());
		cv.put(MainDBConstants.manzana, datosCoordenadas.getManzana());
        cv.put(MainDBConstants.conpunto, datosCoordenadas.getConpunto());
		if (datosCoordenadas.getLatitud() != null) cv.put(MainDBConstants.latitud, datosCoordenadas.getLatitud());
		if (datosCoordenadas.getLongitud() != null) cv.put(MainDBConstants.longitud, datosCoordenadas.getLongitud());
		cv.put(MainDBConstants.otroBarrio, datosCoordenadas.getOtroBarrio());
		cv.put(MainDBConstants.puntoGps, datosCoordenadas.getPuntoGps());
		cv.put(MainDBConstants.razonNoGeoref, datosCoordenadas.getRazonNoGeoref());
		cv.put(MainDBConstants.otraRazonNoGeoref, datosCoordenadas.getOtraRazonNoGeoref());
		cv.put(MainDBConstants.actual, datosCoordenadas.isActual());
		cv.put(MainDBConstants.observacion, datosCoordenadas.getObservacion());

        if (datosCoordenadas.getRecordDate() != null) cv.put(MainDBConstants.recordDate, datosCoordenadas.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, datosCoordenadas.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(datosCoordenadas.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(datosCoordenadas.getEstado()));
        cv.put(MainDBConstants.deviceId, datosCoordenadas.getDeviceid());
		return cv;
	}	
	
	public static DatosCoordenadas crearDatosCoordenada(Cursor cursorCambio){

        DatosCoordenadas mCambio = new DatosCoordenadas();
		mCambio.setCodigo(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.codigo)));
        mCambio.setCodigoCasa(cursorCambio.getInt(cursorCambio.getColumnIndex(MainDBConstants.casa)));
        mCambio.setEstudios(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.estudios)));
		mCambio.setBarrio(null);
        mCambio.setParticipante(null);
        mCambio.setMotivo(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.motivo)));
		mCambio.setDireccion(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.direccion)));
		mCambio.setManzana(cursorCambio.getInt(cursorCambio.getColumnIndex(MainDBConstants.manzana)));
        mCambio.setConpunto(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.conpunto)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud))!= 0) mCambio.setLatitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.latitud)));
		if (cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud))!= 0) mCambio.setLongitud(cursorCambio.getDouble(cursorCambio.getColumnIndex(MainDBConstants.longitud)));
		mCambio.setOtroBarrio(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otroBarrio)));
		mCambio.setPuntoGps(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.puntoGps)));
		mCambio.setRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.razonNoGeoref)));
		mCambio.setOtraRazonNoGeoref(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.otraRazonNoGeoref)));
		mCambio.setActual(cursorCambio.getInt(cursorCambio.getColumnIndex(MainDBConstants.actual))>0);
		mCambio.setObservacion(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.observacion)));

        if(cursorCambio.getLong(cursorCambio.getColumnIndex(MainDBConstants.recordDate))>0) mCambio.setRecordDate(new Date(cursorCambio.getLong(cursorCambio.getColumnIndex(MainDBConstants.recordDate))));
        mCambio.setRecordUser(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.recordUser)));
        mCambio.setPasive(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCambio.setEstado(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCambio.setDeviceid(cursorCambio.getString(cursorCambio.getColumnIndex(MainDBConstants.deviceId)));

        return mCambio;
	}
	
}
