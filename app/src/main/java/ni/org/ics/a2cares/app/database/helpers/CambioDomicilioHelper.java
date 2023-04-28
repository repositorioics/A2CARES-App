package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;

import ni.org.ics.a2cares.app.database.constants.EncuestasDBConstants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.CambioDomicilio;
import ni.org.ics.a2cares.app.domain.core.Casa;

public class CambioDomicilioHelper extends BindStatementHelper {
    public static ContentValues crearCambioDomicilioValues(CambioDomicilio cambioDomicilio) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.id, cambioDomicilio.getId());
        cv.put(MainDBConstants.codigoNuevaCasaCohorte, cambioDomicilio.getCodigoNuevaCasaCohorte());
        cv.put(MainDBConstants.codigoCasa, cambioDomicilio.getCodigoCasa());
        cv.put(MainDBConstants.nombre1JefeFamilia, cambioDomicilio.getNombre1JefeFamilia());
        cv.put(MainDBConstants.nombre2JefeFamilia, cambioDomicilio.getNombre2JefeFamilia());
        cv.put(MainDBConstants.apellido1JefeFamilia, cambioDomicilio.getApellido1JefeFamilia());
        cv.put(MainDBConstants.apellido2JefeFamilia, cambioDomicilio.getApellido2JefeFamilia());
        cv.put(MainDBConstants.direccion, cambioDomicilio.getDireccion());
        cv.put(MainDBConstants.barrio, cambioDomicilio.getBarrio().getCodigo());
        cv.put(MainDBConstants.puntoGps, cambioDomicilio.getCoordenadas());
        cv.put(MainDBConstants.latitud, cambioDomicilio.getLatitud());
        cv.put(MainDBConstants.longitud, cambioDomicilio.getLongitud());
        cv.put(MainDBConstants.numTelefono1, cambioDomicilio.getNumTelefono1());
        cv.put(MainDBConstants.operadoraTelefono1, cambioDomicilio.getOperadoraTelefono1());
        cv.put(MainDBConstants.numTelefono2, cambioDomicilio.getNumTelefono2());
        cv.put(MainDBConstants.operadoraTelefono2, cambioDomicilio.getOperadoraTelefono2());
        cv.put(MainDBConstants.numTelefono3, cambioDomicilio.getNumTelefono3());
        cv.put(MainDBConstants.operadoraTelefono3, cambioDomicilio.getOperadoraTelefono3());
        cv.put(MainDBConstants.codigoMovimiento, cambioDomicilio.getCodigoMovimiento());
        cv.put(MainDBConstants.identificadoEquipo, cambioDomicilio.getIdentificadoEquipo());
        cv.put(MainDBConstants.pasive, String.valueOf(cambioDomicilio.getPasivo()));
        cv.put(MainDBConstants.estado, String.valueOf(cambioDomicilio.getEstado()));
        cv.put(MainDBConstants.fechaRegistro, String.valueOf(cambioDomicilio.getFechaRegistro()));
        cv.put(MainDBConstants.creado, String.valueOf(cambioDomicilio.getCreado()));
        cv.put(MainDBConstants.usuarioRegistro, cambioDomicilio.getUsuarioRegistro());
        cv.put(MainDBConstants.actual, cambioDomicilio.isActual());
        cv.put(MainDBConstants.codigoParticipante, cambioDomicilio.getCodigoParticipante());
        return cv;
    }

    public static CambioDomicilio crearCambioDomicilio(Cursor cursorCambioDomicilio) {
        CambioDomicilio mCambioDomicilio = new CambioDomicilio();
        mCambioDomicilio.setId(cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.id)));
        mCambioDomicilio.setCodigoNuevaCasaCohorte(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.codigoNuevaCasaCohorte)));
        mCambioDomicilio.setCodigoCasa(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.codigoCasa)));
        mCambioDomicilio.setNombre1JefeFamilia(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.nombre1JefeFamilia)));
        mCambioDomicilio.setNombre2JefeFamilia(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.nombre2JefeFamilia)));
        mCambioDomicilio.setApellido1JefeFamilia(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.apellido1JefeFamilia)));
        mCambioDomicilio.setApellido2JefeFamilia(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.apellido2JefeFamilia)));
        mCambioDomicilio.setDireccion(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.direccion)));
        mCambioDomicilio.setBarrio(null); //Revisar despues
        mCambioDomicilio.setCoordenadas(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.puntoGps)));
        if (cursorCambioDomicilio.getDouble(cursorCambioDomicilio.getColumnIndex(MainDBConstants.latitud))!= 0)
            mCambioDomicilio.setLatitud(cursorCambioDomicilio.getDouble(cursorCambioDomicilio.getColumnIndex(MainDBConstants.latitud)));
        if (cursorCambioDomicilio.getDouble(cursorCambioDomicilio.getColumnIndex(MainDBConstants.longitud))!= 0)
            mCambioDomicilio.setLongitud(cursorCambioDomicilio.getDouble(cursorCambioDomicilio.getColumnIndex(MainDBConstants.longitud)));
        mCambioDomicilio.setNumTelefono1(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.numTelefono1)));
        mCambioDomicilio.setOperadoraTelefono1(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.operadoraTelefono1)));
        mCambioDomicilio.setNumTelefono2(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.numTelefono2)));
        mCambioDomicilio.setOperadoraTelefono2(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.operadoraTelefono2)));
        mCambioDomicilio.setNumTelefono3(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.numTelefono3)));
        mCambioDomicilio.setOperadoraTelefono3(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.operadoraTelefono3)));
        mCambioDomicilio.setCodigoMovimiento(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.codigoMovimiento)));
        mCambioDomicilio.setIdentificadoEquipo(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.identificadoEquipo)));
        mCambioDomicilio.setEstado(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mCambioDomicilio.setPasivo(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mCambioDomicilio.setFechaRegistro(null);
        mCambioDomicilio.setCreado(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.creado)));
        mCambioDomicilio.setUsuarioRegistro(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.usuarioRegistro)));
        mCambioDomicilio.setActual(cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.actual))>0);
        mCambioDomicilio.setCodigoParticipante(cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.codigoParticipante)));
        return  mCambioDomicilio;
    }

    public static void fillCambioDomicilioStatement(SQLiteStatement stat, CambioDomicilio cambioDomicilio) {
        stat.bindLong(1, cambioDomicilio.getId());
        stat.bindString(2, cambioDomicilio.getCodigoNuevaCasaCohorte());
        stat.bindString(3, cambioDomicilio.getCodigoCasa());
        stat.bindString(4, cambioDomicilio.getNombre1JefeFamilia());
        stat.bindString(5, cambioDomicilio.getNombre2JefeFamilia());
        stat.bindString(6, cambioDomicilio.getApellido1JefeFamilia());
        stat.bindString(7, cambioDomicilio.getApellido2JefeFamilia());
        stat.bindString(8, cambioDomicilio.getDireccion());
        stat.bindLong(9, cambioDomicilio.getBarrio().getCodigo());
        stat.bindString(10, cambioDomicilio.getCoordenadas());
        stat.bindDouble(11, cambioDomicilio.getLatitud());
        stat.bindDouble(12, cambioDomicilio.getLongitud());
        stat.bindString(13, cambioDomicilio.getNumTelefono1());
        stat.bindString(14, cambioDomicilio.getOperadoraTelefono1());
        stat.bindString(15, cambioDomicilio.getNumTelefono2());
        stat.bindString(16, cambioDomicilio.getOperadoraTelefono2());
        stat.bindString(17, cambioDomicilio.getNumTelefono3());
        stat.bindString(18, cambioDomicilio.getOperadoraTelefono3());
        stat.bindString(19, cambioDomicilio.getCodigoMovimiento());
        stat.bindString(20, cambioDomicilio.getIdentificadoEquipo());
        stat.bindString(21, String.valueOf(cambioDomicilio.getEstado()));
        stat.bindString(22, String.valueOf(cambioDomicilio.getPasivo()));
        stat.bindString(23, String.valueOf(cambioDomicilio.getFechaRegistro()));
        stat.bindString(24, cambioDomicilio.getCreado());
        stat.bindString(25, cambioDomicilio.getUsuarioRegistro());
        stat.bindString(26, String.valueOf(cambioDomicilio.isActual()));
        stat.bindString(27, String.valueOf(cambioDomicilio.getCodigoParticipante()));

    }
}
