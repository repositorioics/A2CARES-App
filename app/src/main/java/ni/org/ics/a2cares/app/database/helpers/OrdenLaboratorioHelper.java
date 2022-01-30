package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;

/**
 * Created by Miguel Salinas on 25/1/2022.
 */
public class OrdenLaboratorioHelper {

    public static ContentValues crearOrdenLaboratorioContentValues(OrdenLaboratorio ordenLaboratorio) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.idOrden, ordenLaboratorio.getIdOrden());
        cv.put(MainDBConstants.participante, ordenLaboratorio.getParticipante().getCodigo());
        cv.put(MainDBConstants.fechaOrden, ordenLaboratorio.getFechaOrden().getTime());
        cv.put(MainDBConstants.tipoOrden, ordenLaboratorio.getTipoOrden());
        if (ordenLaboratorio.getFis() != null) cv.put(MainDBConstants.fis, ordenLaboratorio.getFis().getTime());
        if (ordenLaboratorio.getFif() != null) cv.put(MainDBConstants.fif, ordenLaboratorio.getFif().getTime());
        cv.put(MainDBConstants.categoria, ordenLaboratorio.getCategoria());
        cv.put(MainDBConstants.consulta, ordenLaboratorio.getConsulta());
        cv.put(MainDBConstants.tipoMuestra, ordenLaboratorio.getTipoMuestra());
        cv.put(MainDBConstants.estudiosAct, ordenLaboratorio.getEstudiosAct());
        cv.put(MainDBConstants.observacion, ordenLaboratorio.getObservacion());

        if (ordenLaboratorio.getRecordDate() != null) cv.put(MainDBConstants.recordDate, ordenLaboratorio.getRecordDate().getTime());
        cv.put(MainDBConstants.recordUser, ordenLaboratorio.getRecordUser());
        cv.put(MainDBConstants.pasive, String.valueOf(ordenLaboratorio.getPasive()));
        cv.put(MainDBConstants.estado, String.valueOf(ordenLaboratorio.getEstado()));
        cv.put(MainDBConstants.deviceId, ordenLaboratorio.getDeviceid());
        return cv;
    }

    public static OrdenLaboratorio crearOrdenLaboratorio(Cursor cursor) {
        OrdenLaboratorio mOrden = new OrdenLaboratorio();
        mOrden.setIdOrden(cursor.getString(cursor.getColumnIndex(MainDBConstants.idOrden)));
        mOrden.setParticipante(null);
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaOrden))>0) mOrden.setFechaOrden(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fechaOrden)))));
        mOrden.setTipoOrden(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoOrden)));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fis))>0) mOrden.setFis(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fis)))));
        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fif))>0) mOrden.setFif(new Date(cursor.getLong((cursor.getColumnIndex(MainDBConstants.fif)))));
        mOrden.setCategoria(cursor.getString(cursor.getColumnIndex(MainDBConstants.categoria)));
        mOrden.setConsulta(cursor.getString(cursor.getColumnIndex(MainDBConstants.consulta)));
        mOrden.setTipoMuestra(cursor.getString(cursor.getColumnIndex(MainDBConstants.tipoMuestra)));
        mOrden.setEstudiosAct(cursor.getString(cursor.getColumnIndex(MainDBConstants.estudiosAct)));
        mOrden.setObservacion(cursor.getString(cursor.getColumnIndex(MainDBConstants.observacion)));

        if(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))>0) mOrden.setRecordDate(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.recordDate))));
        mOrden.setRecordUser(cursor.getString(cursor.getColumnIndex(MainDBConstants.recordUser)));
        mOrden.setPasive(cursor.getString(cursor.getColumnIndex(MainDBConstants.pasive)).charAt(0));
        mOrden.setEstado(cursor.getString(cursor.getColumnIndex(MainDBConstants.estado)).charAt(0));
        mOrden.setDeviceid(cursor.getString(cursor.getColumnIndex(MainDBConstants.deviceId)));
        return mOrden;
    }
}
