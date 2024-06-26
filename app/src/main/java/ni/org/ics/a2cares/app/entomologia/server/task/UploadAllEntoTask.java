package ni.org.ics.a2cares.app.entomologia.server.task;

import android.content.Context;
import android.util.Log;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.ControlAsistencia;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogar;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.a2cares.app.listeners.UploadListener;
import ni.org.ics.a2cares.app.ui.task.UploadTask;
import ni.org.ics.a2cares.app.utils.Constants;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UploadAllEntoTask extends UploadTask {
    private final Context mContext;

    public UploadAllEntoTask(Context context) {
        mContext = context;
    }

    protected static final String TAG = UploadAllEntoTask.class.getSimpleName();
    private EstudioDBAdapter estudioAdapter = null;
    private String url = null;
    private String username = null;
    private String password = null;
    private String error = null;
    protected UploadListener mStateListener;
    private List<CuestionarioHogar> mCuestHogar = new ArrayList<CuestionarioHogar>();
    private List<CuestionarioHogarPoblacion> mCuestHogarPob = new ArrayList<CuestionarioHogarPoblacion>();
    private List<CuestionarioPuntoClave> mCuestPuntoClave = new ArrayList<CuestionarioPuntoClave>();
    private List<ControlAsistencia> masistenciaEnto = new ArrayList<ControlAsistencia>();

    public static final String ENTO_CUESTIONARIO_HOGAR = "1";
    public static final String ENTO_CUESTIONARIO_HOGAR_POB = "2";
    public static final String ENTO_CUESTIONARIO_PUNTO_CLAVE = "3";
    public static final String ENTO_CONTROL_ASISTENCIA = "4";

    private static final String TOTAL_TASK_CASOS = "4";

    @Override
    protected String doInBackground(String... values) {
        url = values[0];
        username = values[1];
        password = values[2];
        try{
            publishProgress("Obteniendo registros de la base de datos", "1", "2");
            estudioAdapter = new EstudioDBAdapter(mContext, password, false,false);
            estudioAdapter.open();
            String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
            mCuestHogar = estudioAdapter.getCuestionariosHogar(filtro, null);
            mCuestHogarPob = estudioAdapter.getCuestionariosHogarPoblacion(filtro, null);
            mCuestPuntoClave = estudioAdapter.getCuestionariosPuntoClave(filtro, null);
            masistenciaEnto = estudioAdapter.getControlAsistencial(filtro, null);

            publishProgress("Datos completos!", "2", "2");

            if (noHayDatosEnviar()) {
                error = Constants.NO_DATA;
            } else {

                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CUESTIONARIO_HOGAR);
                error = cargarCuestionarios(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CUESTIONARIO_HOGAR);
                    return error;
                }

                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CUESTIONARIO_HOGAR_POB);
                error = cargarPoblacion(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CUESTIONARIO_HOGAR_POB);
                    return error;
                }

                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CUESTIONARIO_PUNTO_CLAVE);
                error = cargarPuntosClaves(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CUESTIONARIO_PUNTO_CLAVE);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENTO_CONTROL_ASISTENCIA);
                error = cargarControlAsistencia(url, username, password);
                if (!error.matches("Datos recibidos!")) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENTO_CONTROL_ASISTENCIA);
                    return error;
                }
            }
        } catch (Exception e1) {

            e1.printStackTrace();
            return e1.getLocalizedMessage();
        }finally {
            estudioAdapter.close();
        }
        return error;
    }

    private boolean noHayDatosEnviar() {
        return mCuestHogar.size() <= 0 &&
                mCuestHogarPob.size() <= 0 &&
                mCuestPuntoClave.size() <= 0 &&
                masistenciaEnto.size() <= 0;
    }

    private void actualizarBaseDatos(char estado, String opcion) throws Exception {
        int c;
        if(opcion.equalsIgnoreCase(ENTO_CUESTIONARIO_HOGAR)){
            c = mCuestHogar.size();
            if(c>0){
                for (CuestionarioHogar cuestionarioHogar : mCuestHogar) {
                    cuestionarioHogar.setEstado(estado);
                    estudioAdapter.editarCuestionarioHogar(cuestionarioHogar);
                    publishProgress("Actualizando cuestionarios base de datos local", Integer.valueOf(mCuestHogar.indexOf(cuestionarioHogar)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENTO_CUESTIONARIO_HOGAR_POB)){
            c = mCuestHogarPob.size();
            if(c>0){
                for (CuestionarioHogarPoblacion hogarPoblacion : mCuestHogarPob) {
                    hogarPoblacion.setEstado(estado);
                    estudioAdapter.editarCuestionarioHogarPoblacion(hogarPoblacion);
                    publishProgress("Actualizando población en base de datos local", Integer.valueOf(mCuestHogarPob.indexOf(hogarPoblacion)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENTO_CUESTIONARIO_PUNTO_CLAVE)){
            c = mCuestPuntoClave.size();
            if(c>0){
                for (CuestionarioPuntoClave puntoClave : mCuestPuntoClave) {
                    puntoClave.getMovilInfo().setEstado(String.valueOf(estado));
                    estudioAdapter.editarCuestionarioPuntoClave(puntoClave);
                    publishProgress("Actualizando cuestionario punto clave en base de datos local", Integer.valueOf(mCuestPuntoClave.indexOf(puntoClave)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENTO_CONTROL_ASISTENCIA)){
            c = masistenciaEnto.size();
            if(c>0){
                for (ControlAsistencia controlAsistencia : masistenciaEnto) {
                    controlAsistencia.setEstado(estado);
                    estudioAdapter.editarControlAsistencia(controlAsistencia);
                    publishProgress("Actualizando los cambios de Control de Asistencia en base de datos local", Integer.valueOf(masistenciaEnto.indexOf(controlAsistencia)).toString(), Integer.valueOf(c).toString());
                }
            }
        }
    }

    /***************************************************/
    /********************* Cuestionario Hogar ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCuestionarios(String url, String username,
                                         String password) throws Exception {
        try {
            if(mCuestHogar.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando cuestionarios!", ENTO_CUESTIONARIO_HOGAR, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cuestionariosHogar";
                CuestionarioHogar[] envio = mCuestHogar.toArray(new CuestionarioHogar[mCuestHogar.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CuestionarioHogar[]> requestEntity =
                        new HttpEntity<CuestionarioHogar[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }


    /***************************************************/
    /**************** Poblacion ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPoblacion(String url, String username,
                                     String password) throws Exception {
        try {
            if(mCuestHogarPob.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando poblacion!", ENTO_CUESTIONARIO_HOGAR_POB, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cuestionariosHogarPob";
                CuestionarioHogarPoblacion[] envio = mCuestHogarPob.toArray(new CuestionarioHogarPoblacion[mCuestHogarPob.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CuestionarioHogarPoblacion[]> requestEntity =
                        new HttpEntity<CuestionarioHogarPoblacion[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }

    /***************************************************/
    /**************** Puntos Claves ************************/
    /***************************************************/
    // url, username, password
    protected String cargarPuntosClaves(String url, String username,
                                     String password) throws Exception {
        try {
            if(mCuestPuntoClave.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando puntos claves!", ENTO_CUESTIONARIO_PUNTO_CLAVE, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/cuestionariosPuntosClaves";
                CuestionarioPuntoClave[] envio = mCuestPuntoClave.toArray(new CuestionarioPuntoClave[mCuestPuntoClave.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CuestionarioPuntoClave[]> requestEntity =
                        new HttpEntity<CuestionarioPuntoClave[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();
            }
            else{
                return "Datos recibidos!";
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }

    /********************* CONTROL ASISTENCIA ************************/
    // url, username, password
    protected String cargarControlAsistencia(String url, String username,String password) throws Exception {
        try {

            if(masistenciaEnto.size()>0 ){
                // La URL de la solicitud POST
                publishProgress("Enviando Control de Asistencia de Personal!", ENTO_CONTROL_ASISTENCIA, TOTAL_TASK_CASOS);
                final String urlRequest = url + "/movil/controlasistencia";
                ControlAsistencia[] envio = masistenciaEnto.toArray(new ControlAsistencia[masistenciaEnto.size()]);

                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ControlAsistencia[]> requestEntity =
                        new HttpEntity<ControlAsistencia[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone los participantes y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                return response.getBody();

            }
            else{
                return Constants.DATOS_RECIBIDOS;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getMessage();
        }
    }
}
