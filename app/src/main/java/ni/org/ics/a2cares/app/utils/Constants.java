package ni.org.ics.a2cares.app.utils;

import android.net.Uri;

/**
 * Created by Miguel Salinas on 13/5/2021.
 */
public class Constants {

    // status for records
    public static final char STATUS_SUBMITTED = '1';
    public static final char STATUS_NOT_SUBMITTED = '0';
    public static final char STATUS_NOT_PASIVE = '0';

    //upload
    public static final String DATOS_RECIBIDOS = "Datos recibidos!";
    public static final String NO_DATA = "-1";
    public static final int RESULT_NO_DATA = 999;

    //Form wizard
    public static final String ROJO = "#db0000";
    public static final String WIZARD = "#ff0099cc";

    //formularios
    public static final String YES = "Si";
    public static final String OTRO = "Otro";
    public static final String NO = "No";
    public static final String NA = "NA";

    public static final String YESKEYSND = "1";
    public static final String NOKEYSND = "0";

    //tamizaje y consentimiento
    public static final String VERSION_CC = "1";
    public static final String CODIGO_ESTUDIO = "1";

    public static final String REL_FAM_MISMO_PART = "8";

    public static final String TIPO_TEL_CEL = "1";
    public static final String TIPO_TEL_CON = "2";

    //entidades
    public static final String PARTICIPANTE = "participante";
    public static final String CASA = "casa";
    public static final String NUEVO_INGRESO = "nuevo_ngreso";
    public static final String PUNTO_GPS = "punto_gps";

    public static final String VISITA_EXITOSA = "visita_exitosa";
    public static final String COD_PARTICIPANTE = "codigo_participante";
    public static final String PEDIR_VISITA = "pedir_visita";

    //Bluetooth
    public static final String ACCION = "accion";
    public static final String SENDING = "enviando";
    public static final String RECEIVING = "recibiendo";
    public static final String REVIEWING = "revisando";
    public static final String ENTERING = "ingresando";

    //codigos
    public static final String ESTUDIO = "A2CARES";

    //recepcion de muestras
    public static final String TIPO_TUBO = "tipo_tubo";
    public static final String TIPO_TUBO_BHC = "BHC";
    public static final String TIPO_TUBO_ROJO= "ROJO";

    //Navegacion
    public static final String DESDE_PARTICIPANTE = "desde_participante";
    public static final String DESDE_MEDICO = "desde_medico";
    public static final String DESDE_LABO = "desde_laboratorio";

    public static final String UBICACION = "ubicacion";
    public static final int UBICACION_NJ = 1;
    public static final int UBICACION_CO = 2;

    //Rango de volumen muestra
    public static final int VOLUMEN_MIN_ROJO = 4;
    public static final int VOLUMEN_MAX_ROJO = 7;

    //Entomologia
    public static final String MENU_ENTO = "menu_ento";
    public static final String FEMENINO = "F";
    public static final String MASCULINO = "M";
    public static final String TITLE = "titulo";
    public static final String OBJECTO = "objeto";
    public static final String AUTHORITY = "org.odk.collect.android.provider.odk.forms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/forms");
}
