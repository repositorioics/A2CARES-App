package ni.org.ics.a2cares.app.ui.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.CartaConsentimiento;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.core.ObsequioGeneral;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.core.RazonNoData;
import ni.org.ics.a2cares.app.domain.core.Tamizaje;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.Serologia;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.survey.EncuestaParticipante;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.domain.survey.EncuestaSatisfaccionUsuario;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;
import ni.org.ics.a2cares.app.listeners.UploadListener;
import ni.org.ics.a2cares.app.utils.Constants;

public class UploadAllTask extends UploadTask {
	
	private final Context mContext;

	public UploadAllTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadAllTask.class.getSimpleName();
    
	private EstudioDBAdapter estudioAdapter = null;
	private List<VisitaTerrenoParticipante> mVisitasTerreno = new ArrayList<VisitaTerrenoParticipante>();
    private List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
    private List<Casa> mCasas = new ArrayList<Casa>();
    private List<Participante> mParticipantes = new ArrayList<Participante>();
    private List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
    private List<CartaConsentimiento> mCartasConsent = new ArrayList<CartaConsentimiento>();
    private List<EncuestaCasa> mEncuestasCasas = new ArrayList<EncuestaCasa>();
    private List<EncuestaParticipante> mEncuestasParticipante = new ArrayList<EncuestaParticipante>();
    private List<EncuestaPesoTalla> mEncuestasPesoTalla = new ArrayList<EncuestaPesoTalla>();
    private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private List<TelefonoContacto> mTelefonos = new ArrayList<TelefonoContacto>();
    private List<DatosCoordenadas> mCoordenadas = new ArrayList<DatosCoordenadas>();
    private List<RecepcionMuestra> mRecepcionMuestras = new ArrayList<RecepcionMuestra>();
    private List<Serologia> mSerologiasLab = new ArrayList<Serologia>();
    private List<RazonNoData> mNoData = new ArrayList<RazonNoData>();
    private List<PuntoCandidato> mPuntos = new ArrayList<PuntoCandidato>();
    private List<ObsequioGeneral> mObsequiosGeneral = new ArrayList<ObsequioGeneral>();
    private List<OrdenLaboratorio> mOrdenesLab = new ArrayList<OrdenLaboratorio>();
    private List<MuestraEnfermo> mMuestrasEnf = new ArrayList<MuestraEnfermo>();
    private List<RecepcionEnfermo> mRecepcionesEnf = new ArrayList<RecepcionEnfermo>();

    //Encuesta de satisfacción de usuario
    private List<EncuestaSatisfaccionUsuario> mEncSatUsuario = new ArrayList<EncuestaSatisfaccionUsuario>();

	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;

	public static final String VISITA = "1";
    public static final String TAMIZAJE = "2";
    public static final String CASA = "3";
    public static final String PARTICIPANTE = "4";
    public static final String PARTICIPANTE_PRC = "5";
    public static final String CARTAS_CONSENT = "6";
    public static final String TELEFONOS = "7";
    public static final String COORDENADAS = "8";
    public static final String ENCUESTA_CASA = "9";
    public static final String ENCUESTA_PARTICIPANTE = "10";
    public static final String ENCUESTA_PESOTALLA = "11";
    public static final String MUESTRAS = "12";
    public static final String RECEPCION_MUESTRA = "13";
    public static final String RECEPCION_SERO_LAB = "14";
    public static final String RAZON_NO_DATA = "15";
    public static final String PUNTO_DESCARTADO = "16";
    public static final String OBSEQUIOS = "17";
    public static final String ORDENES_LAB = "18";
    public static final String MUESTRAS_ENF = "19";
    public static final String RECEPCIONES_ENF = "20";

    //Encuesta de satisfaccion de usuario
    public static final String ENCUESTA_SATISFACCION_USUARIO = "21";

	private static final String TOTAL_TASK = "21";
	

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];

		try {
			publishProgress("Obteniendo registros de la base de datos", "1", "2");
			estudioAdapter = new EstudioDBAdapter(mContext, password, false,false);
			estudioAdapter.open();
			String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
			mVisitasTerreno = estudioAdapter.getVisitasTerrenoParticipantes(filtro, null);
			mTamizajes = estudioAdapter.getTamizajes(filtro, MainDBConstants.codigo);
            mCasas = estudioAdapter.getCasas(filtro, MainDBConstants.codigo);
            mParticipantes = estudioAdapter.getParticipantes(filtro, MainDBConstants.codigo);
            mParticipantesProc = estudioAdapter.getParticipantesProc(filtro, MainDBConstants.codigo);
            mCartasConsent = estudioAdapter.getCartasConsentimientos(filtro, MainDBConstants.codigo);
            mTelefonos = estudioAdapter.getTelefonosContacto(filtro, null);
            mCoordenadas = estudioAdapter.getDatosCoordenadas(filtro, null);
            mEncuestasCasas = estudioAdapter.getEncuestaCasas(filtro, null);
            mEncuestasParticipante = estudioAdapter.getEncuestasParticipantes(filtro, null);
            mEncuestasPesoTalla = estudioAdapter.getEncuestasPesoTallas(filtro, null);
            mMuestras = estudioAdapter.getMuestras(filtro, null);
            mRecepcionMuestras = estudioAdapter.getRecepcionMuestras(filtro, null);
            mSerologiasLab = estudioAdapter.getSerologias(filtro, null);
            mNoData = estudioAdapter.getRazonNoDatas(filtro, null);
            mPuntos = estudioAdapter.getPuntoCandidatos(filtro, null);
            mObsequiosGeneral = estudioAdapter.getObsequiosGenerales(filtro, null);
            mOrdenesLab = estudioAdapter.getOrdenesLaboratorio(filtro, null);
            mMuestrasEnf = estudioAdapter.getMuestrasEnfermo(filtro, null);
            mRecepcionesEnf = estudioAdapter.getRecepcionesEnfermo(filtro, null);
            //Encuesta satisfaccion de usuario
            mEncSatUsuario = estudioAdapter.getListaEncSatisfaccionUsuarioSinEnviar();

            if (mVisitasTerreno.size() <= 0 &&
                    mTamizajes.size() <= 0 &&
                    mCasas.size() <= 0 &&
                    mParticipantes.size() <= 0 &&
                    mParticipantesProc.size() <= 0 &&
                    mCartasConsent.size() <= 0 &&
                    mTelefonos.size() <= 0 &&
                    mCoordenadas.size() <= 0 &&
                    mEncuestasCasas.size() <= 0 &&
                    mEncuestasParticipante.size() <= 0 &&
                    mEncuestasPesoTalla.size() <= 0 &&
                    mMuestras.size() <= 0 &&
                    mRecepcionMuestras.size() <= 0 &&
                    mSerologiasLab.size() <= 0 &&
                    mNoData.size() <= 0 &&
                    mPuntos.size() <= 0 &&
                    mObsequiosGeneral.size() <= 0 &&
                    mOrdenesLab.size() <= 0 &&
                    mMuestrasEnf.size() <= 0 &&
                    mRecepcionesEnf.size() <= 0 &&
                    mEncSatUsuario.size() <= 0
            ) {
                error = Constants.NO_DATA;
            } else {

                publishProgress("Datos completos!", "2", "2");

                //Enviando datos
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, TAMIZAJE);
                error = cargarTamizajes(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, TAMIZAJE);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, CASA);
                error = cargarCasas(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CASA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTE);
                error = cargarParticipantes(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTE);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, PARTICIPANTE_PRC);
                error = cargarParticipantesProcesos(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PARTICIPANTE_PRC);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, CARTAS_CONSENT);
                error = cargarCartasConsentimientos(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, CARTAS_CONSENT);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, TELEFONOS);
                error = cargarTelefonos(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, TELEFONOS);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, COORDENADAS);
                error = cargarCoordenadas(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, COORDENADAS);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITA);
                error = cargarVisitasTerreno(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_CASA);
                error = cargarEncuestasCasa(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_CASA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_PARTICIPANTE);
                error = cargarEncuestasParticipantes(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_PARTICIPANTE);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_PESOTALLA);
                error = cargarEncuestasPesoTalla(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_PESOTALLA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS);
                error = cargarMuestras(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, RECEPCION_MUESTRA);
                error = cargarRecepcionMuestras(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, RECEPCION_MUESTRA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, RECEPCION_SERO_LAB);
                error = cargarSerologiasLab(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, RECEPCION_SERO_LAB);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, RAZON_NO_DATA);
                error = cargarRazonesNoData(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, RAZON_NO_DATA);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, PUNTO_DESCARTADO);
                error = cargarPuntosDescartados(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PUNTO_DESCARTADO);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, OBSEQUIOS);
                error = cargarObsequioGeneral(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, OBSEQUIOS);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ORDENES_LAB);
                error = cargarOrdenesLab(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ORDENES_LAB);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, MUESTRAS_ENF);
                error = cargarMuestrasEnfermo(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, MUESTRAS_ENF);
                    return error;
                }
                actualizarBaseDatos(Constants.STATUS_SUBMITTED, RECEPCIONES_ENF);
                error = cargarRecepcionesEnfermo(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, RECEPCIONES_ENF);
                    return error;
                }


                actualizarBaseDatos(Constants.STATUS_SUBMITTED, ENCUESTA_SATISFACCION_USUARIO);
                error = cargarEncuestaSatisfaccionUsuario(url, username, password);
                if (!error.matches(Constants.DATOS_RECIBIDOS)) {
                    actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, ENCUESTA_SATISFACCION_USUARIO);
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
	
	private void actualizarBaseDatos(char estado, String opcion) {
		int c;
        if(opcion.equalsIgnoreCase(RECEPCIONES_ENF)){
            c = mRecepcionesEnf.size();
            if(c>0){
                for (RecepcionEnfermo recepcionEnfermo : mRecepcionesEnf) {
                    recepcionEnfermo.setEstado(estado);
                    estudioAdapter.editarRecepcionEnfermo(recepcionEnfermo);
                    publishProgress("Actualizando recepciones mx enfermos en base de datos local", Integer.valueOf(mRecepcionesEnf.indexOf(recepcionEnfermo)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(MUESTRAS_ENF)){
            c = mMuestrasEnf.size();
            if(c>0){
                for (MuestraEnfermo muestraEnfermo : mMuestrasEnf) {
                    muestraEnfermo.setEstado(estado);
                    estudioAdapter.editarMuestraEnfermo(muestraEnfermo);
                    publishProgress("Actualizando muestras enfermos en base de datos local", Integer.valueOf(mMuestrasEnf.indexOf(muestraEnfermo)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ORDENES_LAB)){
            c = mOrdenesLab.size();
            if(c>0){
                for (OrdenLaboratorio ordenLaboratorio : mOrdenesLab) {
                    ordenLaboratorio.setEstado(estado);
                    estudioAdapter.editarOrdenLaboratorio(ordenLaboratorio);
                    publishProgress("Actualizando ordenes laboratorio en base de datos local", Integer.valueOf(mOrdenesLab.indexOf(ordenLaboratorio)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(VISITA)){
            c = mVisitasTerreno.size();
            if(c>0){
                for (VisitaTerrenoParticipante visita : mVisitasTerreno) {
                    visita.setEstado(estado);
                    estudioAdapter.editarVisitaTerrenoParticipante(visita);
                    publishProgress("Actualizando visitas en base de datos local", Integer.valueOf(mVisitasTerreno.indexOf(visita)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
		if(opcion.equalsIgnoreCase(CASA)){
			c = mCasas.size();
			if(c>0){
				for (Casa casa : mCasas) {
					casa.setEstado(estado);
					estudioAdapter.editarCasa(casa);
					publishProgress("Actualizando casas en base de datos local", Integer.valueOf(mCasas.indexOf(casa)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
        if(opcion.equalsIgnoreCase(TAMIZAJE)){
            c = mTamizajes.size();
            if(c>0){
                for (Tamizaje tamizaje : mTamizajes) {
                    tamizaje.setEstado(estado);
                    estudioAdapter.editarTamizaje(tamizaje);
                    publishProgress("Actualizando tamizajes en base de datos local", Integer.valueOf(mTamizajes.indexOf(tamizaje)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTE)){
            c = mParticipantes.size();
            if(c>0){
                for (Participante participante : mParticipantes) {
                    participante.setEstado(estado);
                    estudioAdapter.editarParticipante(participante);
                    publishProgress("Actualizando participantes en base de datos local", Integer.valueOf(mParticipantes.indexOf(participante)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PARTICIPANTE_PRC)){
            c = mParticipantesProc.size();
            if(c>0){
                for (ParticipanteProcesos participanteProcesos : mParticipantesProc) {
                    participanteProcesos.setEstado(estado);
                    estudioAdapter.editarParticipanteProcesos(participanteProcesos);
                    publishProgress("Actualizando procesos participantes en base de datos local", Integer.valueOf(mParticipantesProc.indexOf(participanteProcesos)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(CARTAS_CONSENT)){
            c = mCartasConsent.size();
            if(c>0){
                for (CartaConsentimiento consentimiento : mCartasConsent) {
                    consentimiento.setEstado(estado);
                    estudioAdapter.editarCartaConsentimiento(consentimiento);
                    publishProgress("Actualizando cartas de consentimiento en base de datos local", Integer.valueOf(mCartasConsent.indexOf(consentimiento)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(TELEFONOS)){
            c = mTelefonos.size();
            if(c>0){
                for (TelefonoContacto telef : mTelefonos) {
                    telef.setEstado(estado);
                    estudioAdapter.editarTelefonoContacto(telef);
                    publishProgress("Actualizando telefonos en base de datos local", Integer.valueOf(mTelefonos.indexOf(telef)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(COORDENADAS)){
            c = mCoordenadas.size();
            if(c>0){
                for (DatosCoordenadas coordenadas : mCoordenadas) {
                    coordenadas.setEstado(estado);
                    estudioAdapter.editarDatosCoordenadas(coordenadas);
                    publishProgress("Actualizando coordenadas en base de datos local", Integer.valueOf(mCoordenadas.indexOf(coordenadas)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_CASA)){
            c = mEncuestasCasas.size();
            if(c>0){
                for (EncuestaCasa encuestaCasa : mEncuestasCasas) {
                    encuestaCasa.setEstado(estado);
                    estudioAdapter.editarEncuestaCasa(encuestaCasa);
                    publishProgress("Actualizando encuestas casa en base de datos local", Integer.valueOf(mEncuestasCasas.indexOf(encuestaCasa)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_PARTICIPANTE)){
            c = mEncuestasParticipante.size();
            if(c>0){
                for (EncuestaParticipante encuestaParticipante : mEncuestasParticipante) {
                    encuestaParticipante.setEstado(estado);
                    estudioAdapter.editarEncuestasParticipante(encuestaParticipante);
                    publishProgress("Actualizando encuestas participantes en base de datos local", Integer.valueOf(mEncuestasParticipante.indexOf(encuestaParticipante)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_PESOTALLA)){
            c = mEncuestasPesoTalla.size();
            if(c>0){
                for (EncuestaPesoTalla encuestaPesoTalla : mEncuestasPesoTalla) {
                    encuestaPesoTalla.setEstado(estado);
                    estudioAdapter.editarEncuestasPesoTalla(encuestaPesoTalla);
                    publishProgress("Actualizando encuestas Peso y Talla en base de datos local", Integer.valueOf(mEncuestasPesoTalla.indexOf(encuestaPesoTalla)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(MUESTRAS)){
            c = mMuestras.size();
            if(c>0){
                for (Muestra muestra : mMuestras) {
                    muestra.setEstado(estado);
                    estudioAdapter.editarMuestras(muestra);
                    publishProgress("Actualizando muestras en base de datos local", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(RECEPCION_MUESTRA)){
            c = mRecepcionMuestras.size();
            if(c>0){
                for (RecepcionMuestra recepcionMuestra : mRecepcionMuestras) {
                    recepcionMuestra.setEstado(estado);
                        estudioAdapter.editarRecepcionMuestra(recepcionMuestra);
                        publishProgress("Actualizando recepciones de muestras en base de datos local", Integer.valueOf(mRecepcionMuestras.indexOf(recepcionMuestra)).toString(), Integer
                                .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(RECEPCION_SERO_LAB)){
            c = mSerologiasLab.size();
            if(c>0){
                for (Serologia serologia : mSerologiasLab) {
                    serologia.setEstado(estado);
                    estudioAdapter.editarSerologia(serologia);
                    publishProgress("Actualizando recepciones de Serologias laboratorio en base de datos local", Integer.valueOf(mSerologiasLab.indexOf(serologia)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(RAZON_NO_DATA)){
            c = mNoData.size();
            if(c>0){
                for (RazonNoData razonNoData : mNoData) {
                    razonNoData.setEstado(estado);
                    estudioAdapter.editarRazonNoDatas(razonNoData);
                    publishProgress("Actualizando razones datos pendientes en base de datos local", Integer.valueOf(mNoData.indexOf(razonNoData)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(PUNTO_DESCARTADO)){
            c = mPuntos.size();
            if(c>0){
                for (PuntoCandidato puntoCandidato : mPuntos) {
                    puntoCandidato.setEstado(estado);
                    estudioAdapter.editarPuntoCandidato(puntoCandidato);
                    publishProgress("Actualizando puntos descartados en base de datos local", Integer.valueOf(mPuntos.indexOf(puntoCandidato)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
        if(opcion.equalsIgnoreCase(OBSEQUIOS)){
            c = mObsequiosGeneral.size();
            if(c>0){
                for (ObsequioGeneral obsequio : mObsequiosGeneral) {
                    obsequio.setEstado(estado);
                    try {
                        estudioAdapter.editarObsequioGeneral(obsequio);
                        publishProgress("Actualizando obsequios en base de datos local", Integer.valueOf(mObsequiosGeneral.indexOf(obsequio)).toString(), Integer
                                .valueOf(c).toString());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        if(opcion.equalsIgnoreCase(ENCUESTA_SATISFACCION_USUARIO)){
            c = mEncSatUsuario.size();
            if(c>0){
                for (EncuestaSatisfaccionUsuario encSatUsuario : mEncSatUsuario) {
                    encSatUsuario.setEstado(estado);
                    estudioAdapter.updateEncuestaSatisfaccionUsuario(encSatUsuario);
                    publishProgress("Actualizando encuestas de satisfaccion de usuario en base de datos local", Integer.valueOf(mEncSatUsuario.indexOf(encSatUsuario)).toString(), Integer
                            .valueOf(c).toString());
                }
            }
        }
	}
	
	
	/***************************************************/
	/********************* Casas ************************/
	/***************************************************/
    // url, username, password
    protected String cargarCasas(String url, String username,
    		String password) throws Exception {
    	try {
    		if(mCasas.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando casas!", CASA, TOTAL_TASK);
    			final String urlRequest = url + "/movil/casas";
                Casa[] envio = mCasas.toArray(new Casa[mCasas.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Casa[]> requestEntity =
    					new HttpEntity<Casa[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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

    /***************************************************/
    /********************* Visitas de terreno de participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarVisitasTerreno(String url, String username,
                                     String password) throws Exception {
        try {
            if(mVisitasTerreno.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando tamizaje de personas!", VISITA, TOTAL_TASK);
                final String urlRequest = url + "/movil/visitasterrenoparti";
                VisitaTerrenoParticipante[] envio = mVisitasTerreno.toArray(new VisitaTerrenoParticipante[mVisitasTerreno.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<VisitaTerrenoParticipante[]> requestEntity =
                        new HttpEntity<VisitaTerrenoParticipante[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    
    /***************************************************/
    /********************* Tamizajes participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarTamizajes(String url, String username,
                                        String password) throws Exception {
        try {
            if(mTamizajes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando tamizaje de personas!", TAMIZAJE, TOTAL_TASK);
                final String urlRequest = url + "/movil/tamizajes";
                Tamizaje[] envio = mTamizajes.toArray(new Tamizaje[mTamizajes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Tamizaje[]> requestEntity =
                        new HttpEntity<Tamizaje[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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

    /***************************************************/
    /********************* Participantes ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantes(String url, String username,
                                    String password) throws Exception {
        try {
            if(mParticipantes.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando participantes!", PARTICIPANTE, TOTAL_TASK);
                final String urlRequest = url + "/movil/participantes";
                Participante[] envio = mParticipantes.toArray(new Participante[mParticipantes.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Participante[]> requestEntity =
                        new HttpEntity<Participante[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* ParticipanteProcesos ************************/
    /***************************************************/
    // url, username, password
    protected String cargarParticipantesProcesos(String url, String username,
                                         String password) throws Exception {
        try {
            if(mParticipantesProc.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando procesos participantes!", PARTICIPANTE_PRC, TOTAL_TASK);
                final String urlRequest = url + "/movil/participantesprocesos";
                ParticipanteProcesos[] envio = mParticipantesProc.toArray(new ParticipanteProcesos[mParticipantesProc.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ParticipanteProcesos[]> requestEntity =
                        new HttpEntity<ParticipanteProcesos[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* Cartas de consentimiento ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCartasConsentimientos(String url, String username,
                                            String password) throws Exception {
        try {
            if(mCartasConsent.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando cartas de consentimiento!", CARTAS_CONSENT, TOTAL_TASK);
                final String urlRequest = url + "/movil/cartasConsen";
                CartaConsentimiento[] envio = mCartasConsent.toArray(new CartaConsentimiento[mCartasConsent.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<CartaConsentimiento[]> requestEntity =
                        new HttpEntity<CartaConsentimiento[]>(envio, requestHeaders);
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


    /***************************************************/
    /********************* Telefonos ************************/
    /***************************************************/
    // url, username, password
    protected String cargarTelefonos(String url, String username,String password) throws Exception {
        try {
            if(mTelefonos.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando telefonos!", TELEFONOS, TOTAL_TASK);
                final String urlRequest = url + "/movil/telefonos";
                TelefonoContacto[] envio = mTelefonos.toArray(new TelefonoContacto[mTelefonos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<TelefonoContacto[]> requestEntity =
                        new HttpEntity<TelefonoContacto[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* DatosCoordenadas ************************/
    /***************************************************/
    // url, username, password
    protected String cargarCoordenadas(String url, String username,String password) throws Exception {
        try {
            if(mCoordenadas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando coordenadas!", COORDENADAS, TOTAL_TASK);
                final String urlRequest = url + "/movil/datoscoordenadas";
                DatosCoordenadas[] envio = mCoordenadas.toArray(new DatosCoordenadas[mCoordenadas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<DatosCoordenadas[]> requestEntity =
                        new HttpEntity<DatosCoordenadas[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* EncuestaCasa ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasCasa(String url, String username,String password) throws Exception {
        try {
            if(mEncuestasCasas.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando Encuestas Casa!", ENCUESTA_CASA, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasCasa";
                EncuestaCasa[] envio = mEncuestasCasas.toArray(new EncuestaCasa[mEncuestasCasas.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaCasa[]> requestEntity =
                        new HttpEntity<EncuestaCasa[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* EncuestaCasa ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasParticipantes(String url, String username,String password) throws Exception {
        try {
            if(mEncuestasParticipante.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando Encuestas Participantes!", ENCUESTA_PARTICIPANTE, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasParticipante";
                EncuestaParticipante[] envio = mEncuestasParticipante.toArray(new EncuestaParticipante[mEncuestasParticipante.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaParticipante[]> requestEntity =
                        new HttpEntity<EncuestaParticipante[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* EncuestaPesoTalla ************************/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestasPesoTalla(String url, String username,String password) throws Exception {
        try {
            if(mEncuestasPesoTalla.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando Encuestas PesoTalla!", ENCUESTA_PESOTALLA, TOTAL_TASK);
                final String urlRequest = url + "/movil/encuestasPesoTalla";
                EncuestaPesoTalla[] envio = mEncuestasPesoTalla.toArray(new EncuestaPesoTalla[mEncuestasPesoTalla.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaPesoTalla[]> requestEntity =
                        new HttpEntity<EncuestaPesoTalla[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* Muestras ************************/
    /***************************************************/
    // url, username, password
    protected String cargarMuestras(String url, String username,String password) throws Exception {
        try {
            if(mMuestras.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando muestras!", MUESTRAS, TOTAL_TASK);
                final String urlRequest = url + "/movil/muestras";
                Muestra[] envio = mMuestras.toArray(new Muestra[mMuestras.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Muestra[]> requestEntity =
                        new HttpEntity<Muestra[]>(envio, requestHeaders);
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

    /***************************************************/
    /*********************Recepcion Muestras Supervisor ************************/
    /***************************************************/
    // url, username, password
    protected String cargarRecepcionMuestras(String url, String username,String password) throws Exception {
        try {
            if(mRecepcionMuestras.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando recepciones de muestras!", RECEPCION_MUESTRA, TOTAL_TASK);
                final String urlRequest = url + "/movil/recepcionMuestras";
                RecepcionMuestra[] envio = mRecepcionMuestras.toArray(new RecepcionMuestra[mRecepcionMuestras.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RecepcionMuestra[]> requestEntity =
                        new HttpEntity<RecepcionMuestra[]>(envio, requestHeaders);
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

    /***************************************************/
    /**********Recepcion Muestras Laboratorio **********/
    /***************************************************/
    // url, username, password
    protected String cargarSerologiasLab(String url, String username,String password) throws Exception {
        try {
            if(mSerologiasLab.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando recepciones de muestras laboratorio!", RECEPCION_SERO_LAB, TOTAL_TASK);
                final String urlRequest = url + "/movil/serologiasLab";
                Serologia[] envio = mSerologiasLab.toArray(new Serologia[mSerologiasLab.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<Serologia[]> requestEntity =
                        new HttpEntity<Serologia[]>(envio, requestHeaders);
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

    /***************************************************/
    /**********Recepcion Muestras Laboratorio **********/
    /***************************************************/
    // url, username, password
    protected String cargarRazonesNoData(String url, String username,String password) throws Exception {
        try {
            if(mNoData.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando razones datos pendientes!", RAZON_NO_DATA, TOTAL_TASK);
                final String urlRequest = url + "/movil/razonesDatosPendientes";
                RazonNoData[] envio = mNoData.toArray(new RazonNoData[mNoData.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RazonNoData[]> requestEntity =
                        new HttpEntity<RazonNoData[]>(envio, requestHeaders);
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

    /***************************************************/
    /**********Puntos Candidatos Descartados **********/
    /***************************************************/
    // url, username, password
    protected String cargarPuntosDescartados(String url, String username,String password) throws Exception {
        try {
            if(mPuntos.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando puntos descartados pendientes!", PUNTO_DESCARTADO, TOTAL_TASK);
                final String urlRequest = url + "/movil/puntosCandidatos";
                PuntoCandidato[] envio = mPuntos.toArray(new PuntoCandidato[mPuntos.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<PuntoCandidato[]> requestEntity =
                        new HttpEntity<PuntoCandidato[]>(envio, requestHeaders);
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

    /***************************************************/
    /*************** ObsequioGeneral ************/
    /***************************************************/
    // url, username, password
    protected String cargarObsequioGeneral(String url, String username,
                                           String password) throws Exception {
        try {
            if(mObsequiosGeneral.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando obsequios!", OBSEQUIOS, TOTAL_TASK);
                final String urlRequest = url + "/movil/obsequios";
                ObsequioGeneral[] envio = mObsequiosGeneral.toArray(new ObsequioGeneral[mObsequiosGeneral.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<ObsequioGeneral[]> requestEntity =
                        new HttpEntity<ObsequioGeneral[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /*************** ObsequioGeneral ************/
    /***************************************************/
    // url, username, password
    protected String cargarOrdenesLab(String url, String username,
                                           String password) throws Exception {
        try {
            if(mOrdenesLab.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando ordenes laboratorio!", ORDENES_LAB, TOTAL_TASK);
                final String urlRequest = url + "/movil/ordeneslab";
                OrdenLaboratorio[] envio = mOrdenesLab.toArray(new OrdenLaboratorio[mOrdenesLab.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<OrdenLaboratorio[]> requestEntity =
                        new HttpEntity<OrdenLaboratorio[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
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
    /********************* Muestras de Enfermos************************/
    /***************************************************/
    // url, username, password
    protected String cargarMuestrasEnfermo(String url, String username,String password) throws Exception {
        try {
            if(mMuestrasEnf.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando muestras enfermos!", MUESTRAS_ENF, TOTAL_TASK);
                final String urlRequest = url + "/movil/muestrasenfermo";
                MuestraEnfermo[] envio = mMuestrasEnf.toArray(new MuestraEnfermo[mMuestrasEnf.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<MuestraEnfermo[]> requestEntity =
                        new HttpEntity<MuestraEnfermo[]>(envio, requestHeaders);
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

    /***************************************************/
    /********************* Muestras de Enfermos************************/
    /***************************************************/
    // url, username, password
    protected String cargarRecepcionesEnfermo(String url, String username,String password) throws Exception {
        try {
            if(mRecepcionesEnf.size()>0){
                // La URL de la solicitud POST
                publishProgress("Enviando recepciones enfermos!", RECEPCIONES_ENF, TOTAL_TASK);
                final String urlRequest = url + "/movil/recepcionessenfermo";
                RecepcionEnfermo[] envio = mRecepcionesEnf.toArray(new RecepcionEnfermo[mRecepcionesEnf.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<RecepcionEnfermo[]> requestEntity =
                        new HttpEntity<RecepcionEnfermo[]>(envio, requestHeaders);
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

    /***************************************************/
    /******** Encuesta Satisfaccin de Usuario **********/
    /***************************************************/
    // url, username, password
    protected String cargarEncuestaSatisfaccionUsuario(String url, String username,
                                                       String password) throws Exception {
        try {
            //getEncuestaSatisfaccionUsuarioPendientes();
            if(mEncSatUsuario.size()>0) {
                // La URL de la solicitud POST
                //saveEncuestaSatisfaccionUsuario('1');
                final String urlRequest = url + "/movil/encuestaSatisfaccionUsuario";
                EncuestaSatisfaccionUsuario[] envio = mEncSatUsuario.toArray(new EncuestaSatisfaccionUsuario[mEncSatUsuario.size()]);
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<EncuestaSatisfaccionUsuario[]> requestEntity =
                        new HttpEntity<EncuestaSatisfaccionUsuario[]>(envio, requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                // Hace la solicitud a la red y espera un mensaje de respuesta del servidor
                ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
                        String.class);
                // Regresa la respuesta a mostrar al usuario
                if (!response.getBody().matches("Datos recibidos!")) {
                    //saveEncuestaSatisfaccionUsuario('0');
                }
                return response.getBody();
            } else {
                return Constants.DATOS_RECIBIDOS;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            //saveEncuestaSatisfaccionUsuario('0');
            return e.getMessage();
        }

    }

    /*private void saveEncuestaSatisfaccionUsuario(char estado) {
        int c = mEncSatUsuario.size();
        for (EncuestaSatisfaccionUsuario enSatUsu : mEncSatUsuario) {
            enSatUsu.setEstado(estado);
            estudioAdapter.updateEncuestaSatisfaccionUsuario(enSatUsu);
            publishProgress("Actualizando las encuestas de satisfaccion de usuario", Integer.valueOf(mEncSatUsuario.indexOf(enSatUsu)).toString(), Integer
                    .valueOf(c).toString());
        }
    }*/
}