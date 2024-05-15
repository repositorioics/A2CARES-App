package ni.org.ics.a2cares.app.database.helpers;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;

/**
 * Created by Miguel Salinas on 5/9/2017.
 * V1.0
 */
public class VisitaTerrenoHelper {


    public static ContentValues crearVisitaTerrenoPartContentValues(VisitaTerrenoParticipante visitaTerreno){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigoVisita, visitaTerreno.getCodigoVisita());
        cv.put(MainDBConstants.participante, visitaTerreno.getParticipante().getCodigo());
        if (visitaTerreno.getFechaVisita() != null) cv.put(MainDBConstants.fechaVisita, visitaTerreno.getFechaVisita().getTime());
        cv.put(MainDBConstants.visitaExitosa, visitaTerreno.getVisitaExitosa());
        cv.put(MainDBConstants.razonVisitaNoExitosa, visitaTerreno.getRazonVisitaNoExitosa());
        cv.put(MainDBConstants.otraRazonVisitaNoExitosa, visitaTerreno.getOtraRazonVisitaNoExitosa());
        cv.put(MainDBConstants.direccion_cambio_domicilio, visitaTerreno.getDireccion_cambio_domicilio());
        cv.put(MainDBConstants.telefono_cambio_domicilio, visitaTerreno.getTelefono_cambio_domicilio());
        cv.put(MainDBConstants.telefono_1_actualizado, visitaTerreno.getTelefono_1_Actualizado());
        cv.put(MainDBConstants.telefono_2_actualizado, visitaTerreno.getTelefono_2_Actualizado());
        if (visitaTerreno.getRecordDate() != null) cv.put(MainDBConstants.recordDate, visitaTerreno.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, visitaTerreno.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(visitaTerreno.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(visitaTerreno.getEstado()));
        cv.put(MainDBConstants.deviceId, visitaTerreno.getDeviceid());

        return cv;
    }

    public static VisitaTerrenoParticipante crearVisitaTerrenoPart(Cursor cursor){
        VisitaTerrenoParticipante visitaTerreno = new VisitaTerrenoParticipante();
        visitaTerreno.setCodigoVisita(cursor.getString(cursor.getColumnIndex(MainDBConstants.codigoVisita)));
        visitaTerreno.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))>0) visitaTerreno.setFechaVisita(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaVisita))));
        visitaTerreno.setVisitaExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.visitaExitosa)));
        visitaTerreno.setRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.razonVisitaNoExitosa)));
        visitaTerreno.setOtraRazonVisitaNoExitosa(cursor.getString(cursor.getColumnIndex(MainDBConstants.otraRazonVisitaNoExitosa)));
        visitaTerreno.setDireccion_cambio_domicilio(cursor.getString(cursor.getColumnIndex(MainDBConstants.direccion_cambio_domicilio))) ;
        visitaTerreno.setTelefono_cambio_domicilio(cursor.getString(cursor.getColumnIndex(MainDBConstants.telefono_cambio_domicilio))) ;
        visitaTerreno.setTelefono_1_Actualizado(cursor.getString(cursor.getColumnIndex(MainDBConstants.telefono_1_actualizado)));
        visitaTerreno.setTelefono_2_Actualizado(cursor.getString(cursor.getColumnIndex(MainDBConstants.telefono_2_actualizado)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) visitaTerreno.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        visitaTerreno.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        visitaTerreno.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        visitaTerreno.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        visitaTerreno.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return visitaTerreno;
    }
}
