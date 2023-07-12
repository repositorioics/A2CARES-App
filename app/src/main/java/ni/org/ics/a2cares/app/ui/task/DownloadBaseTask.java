package ni.org.ics.a2cares.app.ui.task;

import android.content.Context;
import android.util.Log;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.Estudio;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.domain.users.Authority;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.domain.users.UserSistema;


public class DownloadBaseTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadBaseTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadBaseTask.class.getSimpleName();
	private EstudioDBAdapter estudioAdapter = null;

    private List<UserSistema> usuarios = null;
    private List<Authority> roles = null;
    private List<UserPermissions> permisos = null;
    private List<Estudio> mEstudios = null;
	private List<Barrio> mBarrios = null;
	private List<Casa> mCasas = null;
    private List<Participante> mParticipantes = null;
    private List<ParticipanteProcesos> mParticipantesProc = null;
    private List<MessageResource> mCatalogos = null;
    private List<TelefonoContacto> mContactosParticipante = null;
    private List<PuntoCandidato> mPuntosCandidatos = null;
    private List<RecepcionEnfermo> mRecepcionEnfermo = null;
    private List<RecepcionEnfermomessage> mRecepcionEnfermo1 = null;
    private List<RecepcionEnfermomessage> mRecepcionEnfermomessage = null;

    public static final String CATALOGOS = "1";
    public static final String USUARIOS = "2";
    public static final String ROLES= "3";
    public static final String USU_PERMISOS = "4";
	public static final String ESTUDIO = "5";
	public static final String BARRIO = "6";
	public static final String CASA = "7";
	public static final String PARTICIPANTE = "8";
    public static final String PARTICIPANTE_PROC = "9";
    public static final String CONTACTOS_PART = "10";
    public static final String PUNTOS_CANDIDATOS = "11";
    public static final String RECEPCION_ENFERMO = "12";

    private static final String TOTAL_TASK_GENERALES = "12";
	
	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int v =0;

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];
		
		try {
            error = descargarCatalogos();
            error = descargarUsuarios();
			error = descargarDatosGenerales();
            error = descargarContactosParticipantes();
            error = descargarPuntosCandidatos();
            error = descargarRecepcionEnfermo();
            if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		estudioAdapter = new EstudioDBAdapter(mContext, password, false,false);
		estudioAdapter.open();
        //Borrar los datos de la base de datos
        estudioAdapter.borrarMessageResource();
        estudioAdapter.borrarUsuarios();
        estudioAdapter.borrarRoles();
        estudioAdapter.borrarPermisos();
		estudioAdapter.borrarEstudios();
		estudioAdapter.borrarBarrios();
		estudioAdapter.borrarCasas();
		estudioAdapter.borrarParticipantes();
        estudioAdapter.borrarTamizajes();
        estudioAdapter.borrarCartasConsentimiento();
        estudioAdapter.borrarTelefonoContacto();
        estudioAdapter.borrarParticipantesProcesos();
        estudioAdapter.borrarPuntoCandidatos();
        estudioAdapter.borrarRecepcionMuestras();
        estudioAdapter.borrarSerologias();
        estudioAdapter.borrarMuestras();
        estudioAdapter.borrarObsequiosGenerales();
        estudioAdapter.borrarEncuestaCasas();
        estudioAdapter.borrarEncuestasPesoTallas();
        estudioAdapter.borrarEncuestasParticipantes();
        estudioAdapter.borrarVisitasTerrenoParticipante();
        estudioAdapter.borrarRazonNoDatas();
        estudioAdapter.borrarDatosCoordenadas();
        estudioAdapter.borrarOrdenesLaboratorio();
        estudioAdapter.borrarMuestrasEnfermo();
        estudioAdapter.borrarRecepcionesEnfermo();
        estudioAdapter.borrarEncuestaSatisfaccionUsuario();
        estudioAdapter.borrarCambioDomicilio();
        try {
            if (mCatalogos != null){
                publishProgress("Insertando catalogos", CATALOGOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertMessageResourceBySql(mCatalogos);
            }
            if (usuarios != null){
                publishProgress("Insertando usuarios", USUARIOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertUsuariosBySql(MainDBConstants.USER_TABLE, usuarios);
             }
            if (roles != null){
                publishProgress("Insertando roles", ROLES, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertUsuariosBySql(MainDBConstants.ROLE_TABLE, roles);
            }
            if (permisos != null){
                publishProgress("Insertando permisos", USU_PERMISOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertUsuariosBySql(MainDBConstants.USER_PERM_TABLE, permisos);
            }
            if (mEstudios != null){
                publishProgress("Insertando estudios", ESTUDIO, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertEstutiosBySql(mEstudios);
            }
            if (mBarrios != null){
                publishProgress("Insertando barrios", BARRIO, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertBarriosBySql(mBarrios);
            }
            if (mCasas != null){
                publishProgress("Insertando casas", CASA, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertCasasBySql(MainDBConstants.CASA_TABLE, mCasas);
            }
            if (mParticipantes != null){
                publishProgress("Insertando participantes", PARTICIPANTE, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesBySql(MainDBConstants.PARTICIPANTE_TABLE, mParticipantes);
            }
            if (mParticipantesProc != null){
                publishProgress("Insertando procesos participantes", PARTICIPANTE_PROC, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertParticipantesBySql(MainDBConstants.PARTICIPANTE_PROC_TABLE, mParticipantesProc);
            }
            if (mContactosParticipante != null){
                publishProgress("Insertando contactos participantes", CONTACTOS_PART, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertCasasBySql(MainDBConstants.TELEFONO_CONTACTO_TABLE, mContactosParticipante);
            }
            if (mPuntosCandidatos != null){
                publishProgress("Insertando puntos candidatos ingreso", PUNTOS_CANDIDATOS, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertPuntosCandidatosBySql(mPuntosCandidatos);
            }
            if (mRecepcionEnfermomessage != null){
                publishProgress("Insertando recepcion enfermo", RECEPCION_ENFERMO, TOTAL_TASK_GENERALES);
                estudioAdapter.bulkInsertRecepcionEnfermoBySql(mRecepcionEnfermomessage);
            }
        } catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}finally {
            estudioAdapter.close();
        }
		return error;
	}

    // url, username, password
    protected String descargarCatalogos() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar catalogos
            urlRequest = url + "/movil/catalogos";
            publishProgress("Solicitando catalogos",CATALOGOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<MessageResource[]> responseEntityMessageResource = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    MessageResource[].class);
            // convert the array to a list and return it
            mCatalogos = Arrays.asList(responseEntityMessageResource.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    // url, username, password
    protected String descargarUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;

            urlRequest = url + "/movil/usuarios";

            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            publishProgress("Solicitando usuarios",USUARIOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserSistema[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserSistema[].class);

            // convert the array to a list and return it
            usuarios = Arrays.asList(responseEntity.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/roles";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Create a new RestTemplate instance
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            publishProgress("Solicitando roles",ROLES,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Authority[]> responseEntityRoles = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Authority[].class);

            // convert the array to a list and return it
            roles = Arrays.asList(responseEntityRoles.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/permisos";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            publishProgress("Solicitando permisos de usuarios",USU_PERMISOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserPermissions[]> responseEntityPerm = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserPermissions[].class);

            // convert the array to a list and return it
            permisos = Arrays.asList(responseEntityPerm.getBody());

            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    // url, username, password
    protected String descargarDatosGenerales() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar estudios
            urlRequest = url + "/movil/estudios/";
            publishProgress("Solicitando estudios",ESTUDIO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Estudio[]> responseEntityEstudio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Estudio[].class);
            // convert the array to a list and return it
            mEstudios = Arrays.asList(responseEntityEstudio.getBody());
            responseEntityEstudio = null;
            //Descargar barrios
            urlRequest = url + "/movil/barrios/";
            publishProgress("Solicitando barrios",BARRIO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Barrio[]> responseEntityBarrio = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Barrio[].class);
            // convert the array to a list and return it
            mBarrios = Arrays.asList(responseEntityBarrio.getBody());
            responseEntityBarrio = null;
            //Descargar casas
            urlRequest = url + "/movil/casas/";
            publishProgress("Solicitando casas",CASA,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Casa[]> responseEntityCasa = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Casa[].class);
            // convert the array to a list and return it
            mCasas = Arrays.asList(responseEntityCasa.getBody());
            responseEntityCasa = null;
            //Descargar participantes
            urlRequest = url + "/movil/participantes/";
            publishProgress("Solicitando participantes",PARTICIPANTE,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Participante[]> responseEntityParticipante = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Participante[].class);
            // convert the array to a list and return it
            mParticipantes = Arrays.asList(responseEntityParticipante.getBody());
            responseEntityParticipante = null;
            // The URL for making the GET request
            urlRequest = url + "/movil/participantesprocesos";
            publishProgress("Solicitando procesos de participantes",PARTICIPANTE_PROC,TOTAL_TASK_GENERALES);

            // Perform the HTTP GET request
            ResponseEntity<ParticipanteProcesos[]> responseEntityPartProc = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteProcesos[].class);

            // convert the array to a list and return it
            mParticipantesProc = Arrays.asList(responseEntityPartProc.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }


    // url, username, password
    protected String descargarContactosParticipantes() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            //Descargar participantes seroprevalencia
            urlRequest = url + "/movil/telefonos/";
            publishProgress("Solicitando teléfonos participantes",CONTACTOS_PART,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<TelefonoContacto[]> responseEntityContactos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    TelefonoContacto[].class);
            // convert the array to a list and return it
            mContactosParticipante = Arrays.asList(responseEntityContactos.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    // url, username, password
    protected String descargarPuntosCandidatos() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            //Descargar participantes seroprevalencia
            urlRequest = url + "/movil/puntosCandidatos/";
            publishProgress("Solicitando puntos candidatos de ingreso",PUNTOS_CANDIDATOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<PuntoCandidato[]> responseEntityContactos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    PuntoCandidato[].class);
            // convert the array to a list and return it
            mPuntosCandidatos = Arrays.asList(responseEntityContactos.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    // url, username, password
    protected String descargarRecepcionEnfermo() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            //Descargar recepcion Enfermos
            urlRequest = url + "/movil/recepcionessenfermo/";
            publishProgress("Solicitando recepcion de enfermos",RECEPCION_ENFERMO,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<RecepcionEnfermomessage[]> responseEntityRecepcionEnfermos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    RecepcionEnfermomessage[].class);
            // convert the array to a list and return it
            mRecepcionEnfermomessage = Arrays.asList(responseEntityRecepcionEnfermos.getBody());
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }


}
