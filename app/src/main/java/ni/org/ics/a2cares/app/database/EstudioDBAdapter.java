package ni.org.ics.a2cares.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteQueryBuilder;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.database.constants.EncuestasDBConstants;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.database.helpers.AdmisionHelper;
import ni.org.ics.a2cares.app.database.helpers.BarrioHelper;
import ni.org.ics.a2cares.app.database.helpers.CambioDomicilioHelper;
import ni.org.ics.a2cares.app.database.helpers.CartaConsentimientoHelper;
import ni.org.ics.a2cares.app.database.helpers.CasaHelper;
import ni.org.ics.a2cares.app.database.helpers.ControlAsistenciaHelper;
import ni.org.ics.a2cares.app.database.helpers.ControlTemperaturaHelper;
import ni.org.ics.a2cares.app.database.helpers.DatosCoordenadasHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaCasaHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaParticipanteHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaPesoTallaHelper;
import ni.org.ics.a2cares.app.database.helpers.EstudiosHelper;
import ni.org.ics.a2cares.app.database.helpers.InformeFinDiaMedicosHelper;
import ni.org.ics.a2cares.app.database.helpers.MessageResourceHelper;
import ni.org.ics.a2cares.app.database.helpers.MuestraEnfermoHelper;
import ni.org.ics.a2cares.app.database.helpers.MuestraHelper;
import ni.org.ics.a2cares.app.database.helpers.ObsequioHelper;
import ni.org.ics.a2cares.app.database.helpers.OrdenLaboratorioHelper;
import ni.org.ics.a2cares.app.database.helpers.ParticipanteHelper;
import ni.org.ics.a2cares.app.database.helpers.PuntoCandidatoHelper;
import ni.org.ics.a2cares.app.database.helpers.RazonNoDataHelper;
import ni.org.ics.a2cares.app.database.helpers.RecepcionEnfermoHelper;
import ni.org.ics.a2cares.app.database.helpers.RecepcionMuestraHelper;
import ni.org.ics.a2cares.app.database.helpers.SerologiaHelper;
import ni.org.ics.a2cares.app.database.helpers.TamizajeHelper;
import ni.org.ics.a2cares.app.database.helpers.TelefonoContactoHelper;
import ni.org.ics.a2cares.app.database.helpers.UserSistemaHelper;
import ni.org.ics.a2cares.app.database.helpers.VisitaTerrenoHelper;
import ni.org.ics.a2cares.app.domain.core.Admision;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.core.CambioDomicilio;
import ni.org.ics.a2cares.app.domain.core.CartaConsentimiento;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.ControlAsistencia;
import ni.org.ics.a2cares.app.domain.core.ControlTemperaturaTermo;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Estudio;
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;
import ni.org.ics.a2cares.app.domain.core.ObsequioGeneral;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.core.RazonNoData;
import ni.org.ics.a2cares.app.domain.core.Tamizaje;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;
import ni.org.ics.a2cares.app.domain.laboratorio.Serologia;
import ni.org.ics.a2cares.app.domain.medico.InformeFindeDiaMedicos;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.survey.EncuestaParticipante;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.domain.survey.EncuestaSatisfaccionUsuario;
import ni.org.ics.a2cares.app.domain.users.Authority;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.domain.users.UserSistema;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;
import ni.org.ics.a2cares.app.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogar;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.a2cares.app.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.a2cares.app.entomologia.helpers.EntomologiaHelper;
import ni.org.ics.a2cares.app.ui.helpers.EncuestaSatisfaccionUsuarioHelper;
import ni.org.ics.a2cares.app.utils.Constants;
import ni.org.ics.a2cares.app.utils.FileUtils;

/**
 * Created by Miguel Salinas on 16/4/2021.
 */
public class EstudioDBAdapter {

    private DataBaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mContext;
    private final String mPassword;
    private final boolean mFromServer;
    private final boolean mCleanDb;


    public EstudioDBAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
        mContext = context;
        mPassword = password;
        mFromServer = fromServer;
        mCleanDb = cleanDb;
    }




    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context mContext, String mPassword, boolean mFromServer, boolean mCleanDb) {
            super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, mContext,
                    mPassword, mFromServer, mCleanDb);
            createStorage();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MainDBConstants.CREATE_USER_TABLE);
            db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
            db.execSQL(MainDBConstants.CREATE_USER_PERM_TABLE);
            db.execSQL(MainDBConstants.CREATE_BARRIO_TABLE);
            db.execSQL(MainDBConstants.CREATE_ESTUDIO_TABLE);
            db.execSQL(MainDBConstants.CREATE_MESSAGES_TABLE);
            db.execSQL(MainDBConstants.CREATE_TAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CARTACONSENTIMIENTO_TABLE);
            db.execSQL(MainDBConstants.CREATE_CASA_TABLE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TABLE);
            db.execSQL(MainDBConstants.CREATE_DATOS_COORDENADAS_TABLE);
            db.execSQL(MainDBConstants.CREATE_TELEFONO_CONTACTO_TABLE);
            db.execSQL(MainDBConstants.CREATE_VISITA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PESOTALLA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_CASA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PART_TABLE);
            db.execSQL(MainDBConstants.CREATE_MUESTRAS_TABLE);
            db.execSQL(MainDBConstants.CREATE_RECEPCION_MUESTRA_TABLE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_PROC_TALBE);
            db.execSQL(MainDBConstants.CREATE_SEROLOGIA_TABLE);
            db.execSQL(MainDBConstants.CREATE_RAZON_NODATA_TABLE);
            db.execSQL(MainDBConstants.CREATE_PUNTOS_CANDIDATOS_TABLE);
            db.execSQL(MainDBConstants.CREATE_OBSEQUIOS_TABLE);
            db.execSQL(MainDBConstants.CREATE_ORDEN_LAB_TABLE);
            db.execSQL(MainDBConstants.CREATE_MUESTRAS_ENFERMO_TABLE);
            db.execSQL(MainDBConstants.CREATE_RECEPCION_ENFERMO_TABLE);
            db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE);
            db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_TABLE);
            db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_POB_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_SATISFACCION_USUARIO_TABLE);
            db.execSQL(MainDBConstants.CREATE_CAMBIO_DOMICILIO_TABLE);
            db.execSQL(MainDBConstants.CREATE_CONTROL_ASISTENCIA_TABLE);
            db.execSQL(MainDBConstants.CREATE_ADMISION_PACIENTES_TABLE);
            db.execSQL(MainDBConstants.CREATE_INFORME_FIN_DIA_MEDICOS_TABLE);
            db.execSQL(MainDBConstants.CREATE_CONTROL_TEMPERATURA_TERMO_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
            if(oldVersion==0) return;
            if(oldVersion==1){
                db.execSQL(MainDBConstants.CREATE_ORDEN_LAB_TABLE);
                db.execSQL(MainDBConstants.CREATE_MUESTRAS_ENFERMO_TABLE);
                db.execSQL(MainDBConstants.CREATE_RECEPCION_ENFERMO_TABLE);
            }
            if (oldVersion==2) {
                //Entomologia 2022
                db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE);
            }
            if (oldVersion==3) {
                //Entomologia 2022
                db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_TABLE);
                db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_POB_TABLE);
            }
            if (oldVersion==4)  {
                /*Nueva encuesta satisfaccion por usuario 30/03/2023*/
                db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_SATISFACCION_USUARIO_TABLE);
            }
            if (oldVersion==5) {
                /*Cambio de domicilio 24/04/2023*/
                db.execSQL(MainDBConstants.CREATE_CAMBIO_DOMICILIO_TABLE);
            }
        }

    }

    public static boolean createStorage() {
        return FileUtils.createFolder(FileUtils.DATABASE_PATH);
    }

    public EstudioDBAdapter open() throws SQLException {
        mDbHelper = new DataBaseHelper(mContext,mPassword,mFromServer,mCleanDb);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null)
            mDbHelper.close();
    }

    /**
     * Crea un cursor desde la base de datos
     *
     * @return cursor
     */
    public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
        Cursor c = null;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tabla);
        c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
        return c;
    }

    /**
     * Metodos para usuarios en la base de datos
     *
     * @param user
     *            Objeto Usuario que contiene la informacion
     *
     */
    //Crear nuevo usuario en la base de datos
    public void crearUsuario(UserSistema user) {
        ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
        mDb.insertOrThrow(MainDBConstants.USER_TABLE, null, cv);
    }
    //Editar usuario existente en la base de datos
    public boolean editarUsuario(UserSistema user) {
        ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
        return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='"
                + user.getUsername()+"'", null) > 0;
    }
    //Limpiar la tabla de usuarios de la base de datos
    public boolean borrarUsuarios() {
        return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
    }
    //Obtener un usuario de la base de datos
    public UserSistema getUsuario(String filtro, String orden) throws SQLException {
        UserSistema mUser = null;
        Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
        if (cursorUser != null && cursorUser.getCount() > 0) {
            cursorUser.moveToFirst();
            mUser=UserSistemaHelper.crearUserSistema(cursorUser);
        }
        if (!cursorUser.isClosed()) cursorUser.close();
        return mUser;
    }
    //Obtener una lista de usuarios de la base de datos
    public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
        List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
        Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
        if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
            cursorUsuarios.moveToFirst();
            mUsuarios.clear();
            do{
                UserSistema mUser = null;
                mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
                mUsuarios.add(mUser);
            } while (cursorUsuarios.moveToNext());
        }
        if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
        return mUsuarios;
    }

    /**
     * Metodos para roles en la base de datos
     *
     * @param rol
     *            Objeto Authority que contiene la informacion
     *
     */
    //Crear nuevo rol en la base de datos
    public void crearRol(Authority rol) {
        ContentValues cv = UserSistemaHelper.crearRolValues(rol);
        mDb.insertOrThrow(MainDBConstants.ROLE_TABLE, null, cv);
    }
    //Limpiar la tabla de roles de la base de datos
    public boolean borrarRoles() {
        return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
    }
    //Verificar un rol de usuario
    public Boolean buscarRol(String username, String Rol) throws SQLException {
        Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
                MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
        boolean result = c != null && c.getCount()>0;
        c.close();
        return result;
    }

    /**
     * Borra todos los roles de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarPermisos() {
        return mDb.delete(MainDBConstants.USER_PERM_TABLE, null, null) > 0;
    }

    /**
     * Inserta permisos de usuarios en la base de datos
     *
     * @param user
     *            Objeto UserPermissions que contiene la informacion
     *
     */
    public void crearPermisosUsuario(UserPermissions user) {
        ContentValues cv = UserSistemaHelper.crearPermisosUsuario(user);
        mDb.insertOrThrow(MainDBConstants.USER_PERM_TABLE, null, cv);
    }

    //Obtener los permisos de un usuario de la base de datos
    public UserPermissions getPermisosUsuario(String filtro, String orden) throws SQLException {
        UserPermissions mUser = null;
        Cursor cursorUser = crearCursor(MainDBConstants.USER_PERM_TABLE, filtro, null, orden);
        if (cursorUser != null && cursorUser.getCount() > 0) {
            cursorUser.moveToFirst();
            mUser=UserSistemaHelper.crearUserPermissions(cursorUser);
        }
        if (!cursorUser.isClosed()) cursorUser.close();
        return mUser;
    }

    /**
     * Metodos para barrios en la base de datos
     *
     * @param barrio
     *            Objeto Barrio que contiene la informacion
     *
     */
    //Crear nuevo barrio en la base de datos
    public void crearBarrio(Barrio barrio) {
        ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
        mDb.insertOrThrow(MainDBConstants.BARRIO_TABLE, null, cv);
    }
    //Editar barrio existente en la base de datos
    public boolean editarBarrio(Barrio barrio) {
        ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
        return mDb.update(MainDBConstants.BARRIO_TABLE , cv, MainDBConstants.codigo + "="
                + barrio.getCodigo(), null) > 0;
    }

    //Limpiar la tabla de barrios de la base de datos
    public boolean borrarBarrios() {
        return mDb.delete(MainDBConstants.BARRIO_TABLE, null, null) > 0;
    }

    //Obtener un barrio de la base de datos
    public Barrio getBarrio(String filtro, String orden) throws SQLException {
        Barrio mBarrio = null;
        Cursor cursorBarrio = crearCursor(MainDBConstants.BARRIO_TABLE , filtro, null, orden);
        if (cursorBarrio != null && cursorBarrio.getCount() > 0) {
            cursorBarrio.moveToFirst();
            mBarrio=BarrioHelper.crearBarrio(cursorBarrio);
        }
        if (!cursorBarrio.isClosed()) cursorBarrio.close();
        return mBarrio;
    }

    //Obtener una lista de barrios de la base de datos
    public List<Barrio> getBarrios(String filtro, String orden) throws SQLException {
        List<Barrio> mBarrios = new ArrayList<Barrio>();
        Cursor cursorBarrios = crearCursor(MainDBConstants.BARRIO_TABLE, filtro, null, orden);
        if (cursorBarrios != null && cursorBarrios.getCount() > 0) {
            cursorBarrios.moveToFirst();
            mBarrios.clear();
            do{
                Barrio mBarrio = null;
                mBarrio = BarrioHelper.crearBarrio(cursorBarrios);
                mBarrios.add(mBarrio);
            } while (cursorBarrios.moveToNext());
        }
        if (!cursorBarrios.isClosed()) cursorBarrios.close();
        return mBarrios;
    }


    /**
     * Metodos para estudios en la base de datos
     *
     * @param estudio
     *            Objeto Estudio que contiene la informacion
     *
     */
    //Crear nuevo estudio en la base de datos
    public void crearEstudio(Estudio estudio) {
        ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
        mDb.insertOrThrow(MainDBConstants.ESTUDIO_TABLE, null, cv);
    }
    //Editar estudio existente en la base de datos
    public boolean editarEstudio(Estudio estudio) {
        ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
        return mDb.update(MainDBConstants.ESTUDIO_TABLE , cv, MainDBConstants.codigo + "="
                + estudio.getCodigo(), null) > 0;
    }
    //Limpiar la tabla de estudios de la base de datos
    public boolean borrarEstudios() {
        return mDb.delete(MainDBConstants.ESTUDIO_TABLE, null, null) > 0;
    }
    //Obtener un estudio de la base de datos
    public Estudio getEstudio(String filtro, String orden) throws SQLException {
        Estudio mEstudio = null;
        Cursor cursorEstudio = crearCursor(MainDBConstants.ESTUDIO_TABLE , filtro, null, orden);
        if (cursorEstudio != null && cursorEstudio.getCount() > 0) {
            cursorEstudio.moveToFirst();
            mEstudio=EstudiosHelper.crearEstudio(cursorEstudio);
        }
        if (!cursorEstudio.isClosed()) cursorEstudio.close();
        return mEstudio;
    }
    //Obtener una lista de estudios de la base de datos
    public List<Estudio> getEstudios(String filtro, String orden) throws SQLException {
        List<Estudio> mEstudios = new ArrayList<Estudio>();
        Cursor cursorEstudios = crearCursor(MainDBConstants.ESTUDIO_TABLE, filtro, null, orden);
        if (cursorEstudios != null && cursorEstudios.getCount() > 0) {
            cursorEstudios.moveToFirst();
            mEstudios.clear();
            do{
                Estudio mEstudio = null;
                mEstudio = EstudiosHelper.crearEstudio(cursorEstudios);
                mEstudios.add(mEstudio);
            } while (cursorEstudios.moveToNext());
        }
        if (!cursorEstudios.isClosed()) cursorEstudios.close();
        return mEstudios;
    }

    /**
     * Metodos para mensajes en la base de datos
     *
     * @param mensaje
     *            Objeto MessageResource que contiene la informacion
     *
     */
    //Crear nuevo MessageResource en la base de datos
    public void crearMessageResource(MessageResource mensaje) {
        ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
        mDb.insertOrThrow(MainDBConstants.MESSAGES_TABLE, null, cv);
    }
    //Editar MessageResource existente en la base de datos
    public boolean editarMessageResource(MessageResource mensaje) {
        ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
        return mDb.update(MainDBConstants.MESSAGES_TABLE , cv, MainDBConstants.messageKey + "='"
                + mensaje.getMessageKey() + "'", null) > 0;
    }
    //Limpiar la tabla de MessageResource de la base de datos
    public boolean borrarMessageResource() {
        return mDb.delete(MainDBConstants.MESSAGES_TABLE, null, null) > 0;
    }
    //Obtener un MessageResource de la base de datos
    public MessageResource getMessageResource(String filtro, String orden) throws SQLException {
        MessageResource mMessageResource = null;
        Cursor cursorMessageResource = crearCursor(MainDBConstants.MESSAGES_TABLE , filtro, null, orden);
        if (cursorMessageResource != null && cursorMessageResource.getCount() > 0) {
            cursorMessageResource.moveToFirst();
            mMessageResource=MessageResourceHelper.crearMessageResource(cursorMessageResource);
        }
        if (!cursorMessageResource.isClosed()) cursorMessageResource.close();
        return mMessageResource;
    }
    //Obtener una lista de MessageResource de la base de datos
    public List<MessageResource> getMessageResources(String filtro, String orden) throws SQLException {
        List<MessageResource> mMessageResources = new ArrayList<MessageResource>();
        Cursor cursorMessageResources = crearCursor(MainDBConstants.MESSAGES_TABLE, filtro, null, orden);
        if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
            cursorMessageResources.moveToFirst();
            mMessageResources.clear();
            do{
                MessageResource mMessageResource = null;
                mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
                mMessageResources.add(mMessageResource);
            } while (cursorMessageResources.moveToNext());
        }
        if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
        return mMessageResources;
    }

    //Obtener una lista de MessageResource de la base de datos
    public String[] getSpanishMessageResources(String filtro, String orden) throws SQLException {
        Cursor cursorMessageResources = crearCursor(MainDBConstants.MESSAGES_TABLE, filtro, null, orden);
        String[] mMessageResources = null;
        if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
            cursorMessageResources.moveToFirst();
            mMessageResources = new String[cursorMessageResources.getCount()];
            int indice = 0;
            do{
                MessageResource mMessageResource;
                mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
                mMessageResources[indice]= mMessageResource.getSpanish();
                indice++;
            } while (cursorMessageResources.moveToNext());
        }
        if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
        return mMessageResources;
    }

    //Obtener una lista de MessageResource de la base de datos para catalogo de formularios
    public String[] getMessageResourcesForCatalog(String codigoCatalogo) throws SQLException {
        String[] catalogo;
        int index = 0;
        Cursor cursorMessageResources = crearCursor(MainDBConstants.MESSAGES_TABLE, MainDBConstants.catRoot + "='"+codigoCatalogo+"'", null, MainDBConstants.order);
        catalogo = new String[cursorMessageResources.getCount()];
        if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
            cursorMessageResources.moveToFirst();
            do{
                //MessageResource mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
                catalogo[index] = cursorMessageResources.getString(cursorMessageResources.getColumnIndex(MainDBConstants.spanish));
                index++;
            } while (cursorMessageResources.moveToNext());
        }
        if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
        return catalogo;
    }

    /**
     * Metodos para tamizajes en la base de datos
     *
     * @param tamizaje
     *            Objeto Tamizaje que contiene la informacion
     *
     */
    //Crear nuevo Tamizaje en la base de datos
    public void crearTamizaje(Tamizaje tamizaje) {
        ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
        mDb.insertOrThrow(MainDBConstants.TAMIZAJE_TABLE, null, cv);
    }
    //Editar Tamizaje existente en la base de datos
    public boolean editarTamizaje(Tamizaje tamizaje) {
        ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
        return mDb.update(MainDBConstants.TAMIZAJE_TABLE , cv, MainDBConstants.codigo + "='"
                + tamizaje.getCodigo()+ "'", null) > 0;
    }
    //Limpiar la tabla de Tamizaje de la base de datos
    public boolean borrarTamizajes() {
        return mDb.delete(MainDBConstants.TAMIZAJE_TABLE, null, null) > 0;
    }
    //Obtener un Tamizaje de la base de datos
    public Tamizaje getTamizaje(String filtro, String orden) throws SQLException {
        Tamizaje mTamizaje = null;
        Cursor cursorTamizaje = crearCursor(MainDBConstants.TAMIZAJE_TABLE , filtro, null, orden);
        if (cursorTamizaje != null && cursorTamizaje.getCount() > 0) {
            cursorTamizaje.moveToFirst();
            mTamizaje=TamizajeHelper.crearTamizaje(cursorTamizaje);
            Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizaje.getInt(cursorTamizaje.getColumnIndex(MainDBConstants.estudio)), null);
            mTamizaje.setEstudio(estudio);
        }
        if (!cursorTamizaje.isClosed()) cursorTamizaje.close();
        return mTamizaje;
    }
    //Obtener una lista de Tamizaje de la base de datos
    public List<Tamizaje> getTamizajes(String filtro, String orden) throws SQLException {
        List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
        Cursor cursorTamizajes = crearCursor(MainDBConstants.TAMIZAJE_TABLE, filtro, null, orden);
        if (cursorTamizajes != null && cursorTamizajes.getCount() > 0) {
            cursorTamizajes.moveToFirst();
            mTamizajes.clear();
            do{
                Tamizaje mTamizaje = null;
                mTamizaje = TamizajeHelper.crearTamizaje(cursorTamizajes);
                Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizajes.getInt(cursorTamizajes.getColumnIndex(MainDBConstants.estudio)), null);
                mTamizaje.setEstudio(estudio);
                mTamizajes.add(mTamizaje);
            } while (cursorTamizajes.moveToNext());
        }
        if (!cursorTamizajes.isClosed()) cursorTamizajes.close();
        return mTamizajes;
    }

    /**
     * Metodos para carta de consentimiento en la base de datos
     *
     * @param cartaConsentimiento
     *            Objeto CartaConsentimiento que contiene la informacion
     *
     */
    //Crear nuevo CartaConsentimiento en la base de datos
    public void crearCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
        ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
        mDb.insertOrThrow(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, cv);
    }
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
        ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
        return mDb.update(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, cv, MainDBConstants.codigo + "='"
                + cartaConsentimiento.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de cartas consentimiento de la base de datos
    public boolean borrarCartasConsentimiento() {
        return mDb.delete(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, null) > 0;
    }

    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public CartaConsentimiento getCartaConsentimientos(String filtro, String orden) throws SQLException {
        CartaConsentimiento cartaConsentimiento = null;
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE , filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            cartaConsentimiento=CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.participante)) +"'", null);
            cartaConsentimiento.setParticipante(participante);
            Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
            cartaConsentimiento.setTamizaje(tamizaje);
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return cartaConsentimiento;
    }
    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public ArrayList<CartaConsentimiento> getCartasConsentimientos(String filtro, String orden) throws SQLException {
        ArrayList<CartaConsentimiento> mParticipanteCohorteFamilias = new ArrayList<CartaConsentimiento>();
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            mParticipanteCohorteFamilias.clear();
            do{
                CartaConsentimiento cartaConsentimiento = null;
                cartaConsentimiento = CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.participante)) +"'", null);
                cartaConsentimiento.setParticipante(participante);
                Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
                cartaConsentimiento.setTamizaje(tamizaje);
                mParticipanteCohorteFamilias.add(cartaConsentimiento);
            } while (cursorCarta.moveToNext());
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return mParticipanteCohorteFamilias;
    }

    /**
     * Metodos para casas en la base de datos
     *
     * @param casa
     *            Objeto Casa que contiene la informacion
     *
     */
    //Crear nuevo Casa en la base de datos
    public void crearCasa(Casa casa) {
        ContentValues cv = CasaHelper.crearCasaContentValues(casa);
        mDb.insertOrThrow(MainDBConstants.CASA_TABLE, null, cv);
    }
    //Crear nueva Casa en la base de datos desde otro equipo
    public void insertarCasa(String casaSQL) {
        mDb.execSQL(casaSQL);
    }
    //Editar Casa existente en la base de datos
    public boolean editarCasa(Casa casa) {
        ContentValues cv = CasaHelper.crearCasaContentValues(casa);
        return mDb.update(MainDBConstants.CASA_TABLE , cv, MainDBConstants.codigo + "="
                + casa.getCodigo(), null) > 0;
    }
    //Limpiar la tabla de casas de la base de datos
    public boolean borrarCasas() {
        return mDb.delete(MainDBConstants.CASA_TABLE, null, null) > 0;
    }
    //Obtener un casa de la base de datos
    public Casa getCasa(String filtro, String orden) throws SQLException {
        Casa mCasa = null;
        Cursor cursorCasa = crearCursor(MainDBConstants.CASA_TABLE , filtro, null, orden);
        if (cursorCasa != null && cursorCasa.getCount() > 0) {
            cursorCasa.moveToFirst();
            mCasa=CasaHelper.crearCasa(cursorCasa);
            Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasa.getInt(cursorCasa.getColumnIndex(MainDBConstants.barrio)), orden);
            mCasa.setBarrio(barrio);
        }
        if (!cursorCasa.isClosed()) cursorCasa.close();
        return mCasa;
    }
    //Obtener una lista de casas de la base de datos
    public List<Casa> getCasas(String filtro, String orden) throws SQLException {
        List<Casa> mCasas = new ArrayList<Casa>();
        Cursor cursorCasas = crearCursor(MainDBConstants.CASA_TABLE, filtro, null, orden);
        if (cursorCasas != null && cursorCasas.getCount() > 0) {
            cursorCasas.moveToFirst();
            mCasas.clear();
            do{
                Casa mCasa = null;
                mCasa = CasaHelper.crearCasa(cursorCasas);
                Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasas.getInt(cursorCasas.getColumnIndex(MainDBConstants.barrio)), orden);
                mCasa.setBarrio(barrio);
                mCasas.add(mCasa);
            } while (cursorCasas.moveToNext());
        }
        if (!cursorCasas.isClosed()) cursorCasas.close();
        return mCasas;
    }

    /**
     * Metodos para participantes en la base de datos
     *
     * @param participante
     *            Objeto Participante que contiene la informacion
     *
     */
    //Crear nuevo Participante en la base de datos
    public void crearParticipante(Participante participante) {
        ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
        mDb.insertOrThrow(MainDBConstants.PARTICIPANTE_TABLE, null, cv);
    }
    //Crear nuevo Participante en la base de datos desde otro equipo
    public void insertarParticipante(String participanteSQL) {
        mDb.execSQL(participanteSQL);
    }
    //Editar Participante existente en la base de datos
    public boolean editarParticipante(Participante participante) {
        ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE , cv, MainDBConstants.codigo + "='"
                + participante.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de Participante de la base de datos
    public boolean borrarParticipantes() {
        return mDb.delete(MainDBConstants.PARTICIPANTE_TABLE, null, null) > 0;
    }
    //Obtener una Participante de la base de datos
    public Participante getParticipante(String filtro, String orden) throws SQLException {
        Participante mParticipante = null;
        Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE , filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipante=ParticipanteHelper.crearParticipante(cursorParticipante);
            Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);

            ParticipanteProcesos procesos = this.getParticipanteProcesos(MainDBConstants.codigo+"='"+mParticipante.getCodigo()+"'", null);
            mParticipante.setProcesos(procesos);

            mParticipante.setCasa(casa);
        }
        if (!cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipante;
    }
    //Obtener una lista de Participante de la base de datos
    public List<Participante> getParticipantes(String filtro, String orden) throws SQLException {
        List<Participante> mParticipantes = new ArrayList<Participante>();
        Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(MainDBConstants.codigo+"='"+mParticipante.getCodigo() +"'", null);
                mParticipante.setProcesos(procesos);

                mParticipantes.add(mParticipante);
            } while (cursorParticipante.moveToNext());
        }
        if (!cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipantes;
    }

    //Obtener una lista de Participante de la base de datos
    public List<Participante> getParticipantesActivos(String filtro, String orden) throws SQLException {
        List<Participante> mParticipantes = new ArrayList<Participante>();
        Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(MainDBConstants.codigo+"='"+mParticipante.getCodigo()+"'", null);
                if (procesos != null && procesos.getRetirado()==0) {
                    mParticipante.setProcesos(procesos);
                    mParticipantes.add(mParticipante);
                }
            } while (cursorParticipante.moveToNext());
        }
        if (!cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes buscando por nombre
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesName(String name) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();
        participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ MainDBConstants.nombre2 + " LIKE '%" + name + "%'", null, null);
        //participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
        //MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(participantes);

                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(MainDBConstants.codigo+"='"+mParticipante.getCodigo()+"'", null);
                mParticipante.setProcesos(procesos);

                mParticipantes.add(mParticipante);
            } while (participantes.moveToNext());
        }
        if (!participantes.isClosed()) participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes buscando por nombre
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesLastName(String lastname) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();
        participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.apellido1 + " LIKE '%" + lastname + "%' OR "+ MainDBConstants.apellido2 + " LIKE '%" + lastname + "%'", null, null);
        //participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
        //MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(participantes);

                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(MainDBConstants.codigo+"='"+mParticipante.getCodigo()+"'", null);
                mParticipante.setProcesos(procesos);

                mParticipantes.add(mParticipante);
            } while (participantes.moveToNext());
        }
        if (!participantes.isClosed()) participantes.close();
        return mParticipantes;
    }

    /**
     * Metodos para Telefonos en la base de datos
     *
     * @param tel
     *            Objeto TelefonoContacto que contiene la informacion
     *
     */
    //Crear nuevo TelefonoContacto en la base de datos
    public void crearTelefonoContacto(TelefonoContacto tel) {
        ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
        mDb.insertOrThrow(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, cv);
    }
    //Crear nuevo telefono de contacto en la base de datos desde otro equipo
    public void insertTelefonoContacto(String telefonoSQL) {
        mDb.execSQL(telefonoSQL);
    }
    //Editar TelefonoContacto existente en la base de datos
    public boolean editarTelefonoContacto(TelefonoContacto tel) {
        ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
        return mDb.update(MainDBConstants.TELEFONO_CONTACTO_TABLE , cv, MainDBConstants.id + "='"
                + tel.getId()+ "'", null) > 0;
    }
    //Limpiar la tabla de TelefonoContacto de la base de datos
    public boolean borrarTelefonoContacto() {
        return mDb.delete(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, null) > 0;
    }
    //Obtener un TelefonoContacto de la base de datos
    public TelefonoContacto getTelefonoContacto(String filtro, String orden) throws SQLException {
        TelefonoContacto mTelefonoContacto = null;
        Cursor cursorTelefonoContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE , filtro, null, orden);
        if (cursorTelefonoContacto != null && cursorTelefonoContacto.getCount() > 0) {
            cursorTelefonoContacto.moveToFirst();
            mTelefonoContacto=TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonoContacto);
            Casa casa = this.getCasa(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.casa))+"'", null);
            mTelefonoContacto.setCasa(casa);
            Participante part = this.getParticipante(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.participante))+"'", null);
            mTelefonoContacto.setParticipante(part);
        }
        if (!cursorTelefonoContacto.isClosed()) cursorTelefonoContacto.close();
        return mTelefonoContacto;
    }
    //Obtener una lista de TelefonoContacto de la base de datos
    public List<TelefonoContacto> getTelefonosContacto(String filtro, String orden) throws SQLException {
        List<TelefonoContacto> mTelefonosContacto = new ArrayList<TelefonoContacto>();
        Cursor cursorTelefonosContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE, filtro, null, orden);
        if (cursorTelefonosContacto != null && cursorTelefonosContacto.getCount() > 0) {
            cursorTelefonosContacto.moveToFirst();
            mTelefonosContacto.clear();
            do{
                TelefonoContacto mTelefonoContacto = null;
                mTelefonoContacto = TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonosContacto);
                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.casa)), null);
                mTelefonoContacto.setCasa(casa);
                Participante part = this.getParticipante(MainDBConstants.codigo + "='" +cursorTelefonosContacto.getString(cursorTelefonosContacto.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mTelefonoContacto.setParticipante(part);
                mTelefonosContacto.add(mTelefonoContacto);
            } while (cursorTelefonosContacto.moveToNext());
        }
        if (!cursorTelefonosContacto.isClosed()) cursorTelefonosContacto.close();
        return mTelefonosContacto;
    }

    /**
     * Metodos para visitas en la base de datos
     *
     * @param visitaTerrenoParticipante
     *            Objeto VisitaTereno que contiene la informacion
     *
     */
    //Crear nuevo VisitaTerrenoParticipante en la base de datos
    public void crearVisitaTerrenoParticipante(VisitaTerrenoParticipante visitaTerrenoParticipante) {
        ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoPartContentValues(visitaTerrenoParticipante);
        mDb.insertOrThrow(MainDBConstants.VISITA_PARTICIPANTE_TABLE, null, cv);
    }
    //Editar VisitaTerrenoParticipante existente en la base de datos
    public boolean editarVisitaTerrenoParticipante(VisitaTerrenoParticipante visitaTerrenoParticipante) {
        ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoPartContentValues(visitaTerrenoParticipante);
        return mDb.update(MainDBConstants.VISITA_PARTICIPANTE_TABLE , cv, MainDBConstants.codigoVisita + "='"
                + visitaTerrenoParticipante.getCodigoVisita()+ "'", null) > 0;
    }
    //Limpiar la tabla de VisitaTerrenoParticipante de la base de datos
    public boolean borrarVisitasTerrenoParticipante() {
        return mDb.delete(MainDBConstants.VISITA_PARTICIPANTE_TABLE, null, null) > 0;
    }
    //Obtener un VisitaTerrenoParticipante de la base de datos
    public VisitaTerrenoParticipante getVisitaTerrenoParticipante(String filtro, String orden) throws SQLException {
        VisitaTerrenoParticipante mVisitaTerreno = null;
        Cursor cursorVisitaTerreno = crearCursor(MainDBConstants.VISITA_PARTICIPANTE_TABLE , filtro, null, orden);
        if (cursorVisitaTerreno != null && cursorVisitaTerreno.getCount() > 0) {
            cursorVisitaTerreno.moveToFirst();
            mVisitaTerreno=VisitaTerrenoHelper.crearVisitaTerrenoPart(cursorVisitaTerreno);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursorVisitaTerreno.getString(cursorVisitaTerreno.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mVisitaTerreno.setParticipante(participante);
        }
        if (!cursorVisitaTerreno.isClosed()) cursorVisitaTerreno.close();
        return mVisitaTerreno;
    }
    //Obtener una lista de VisitaTerrenoParticipante de la base de datos
    public List<VisitaTerrenoParticipante> getVisitasTerrenoParticipantes(String filtro, String orden) throws SQLException {
        List<VisitaTerrenoParticipante> mVisitasTerreno = new ArrayList<VisitaTerrenoParticipante>();
        Cursor cursorVisitasTerreno = crearCursor(MainDBConstants.VISITA_PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursorVisitasTerreno != null && cursorVisitasTerreno.getCount() > 0) {
            cursorVisitasTerreno.moveToFirst();
            mVisitasTerreno.clear();
            do{
                VisitaTerrenoParticipante mVisitaTerreno = null;
                mVisitaTerreno = VisitaTerrenoHelper.crearVisitaTerrenoPart(cursorVisitasTerreno);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursorVisitasTerreno.getString(cursorVisitasTerreno.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mVisitaTerreno.setParticipante(participante);
                mVisitasTerreno.add(mVisitaTerreno);
            } while (cursorVisitasTerreno.moveToNext());
        }
        if (!cursorVisitasTerreno.isClosed()) cursorVisitasTerreno.close();
        return mVisitasTerreno;
    }

    /**
     * Metodos para cambios de domicilio en la base de datos
     *
     * @param datosCoordenadas
     *            Objeto VisitaTereno que contiene la informacion
     *
     */
    //Crear nuevo DatosCoordenadas en la base de datos
    public void crearDatosCoordenadas(DatosCoordenadas datosCoordenadas) {
        ContentValues cv = DatosCoordenadasHelper.crearDatosCoordenadaContentValues(datosCoordenadas);
        mDb.insertOrThrow(MainDBConstants.DATOS_COORDENADAS_TABLE, null, cv);
    }
    //Editar DatosCoordenadas existente en la base de datos
    public boolean editarDatosCoordenadas(DatosCoordenadas datosCoordenadas) {
        ContentValues cv = DatosCoordenadasHelper.crearDatosCoordenadaContentValues(datosCoordenadas);
        return mDb.update(MainDBConstants.DATOS_COORDENADAS_TABLE , cv, MainDBConstants.codigo + "='"
                + datosCoordenadas.getCodigo()+ "'", null) > 0;
    }
    //Limpiar la tabla de DatosCoordenadas de la base de datos
    public boolean borrarDatosCoordenadas() {
        return mDb.delete(MainDBConstants.DATOS_COORDENADAS_TABLE, null, null) > 0;
    }
    //Obtener un DatosCoordenadas de la base de datos
    public DatosCoordenadas getDatosCoordenada(String filtro, String orden) throws SQLException {
        DatosCoordenadas mDatosCoordenadas = null;
        Cursor cursorCambioDomicilio = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE , filtro, null, orden);
        if (cursorCambioDomicilio != null && cursorCambioDomicilio.getCount() > 0) {
            cursorCambioDomicilio.moveToFirst();
            mDatosCoordenadas = DatosCoordenadasHelper.crearDatosCoordenada(cursorCambioDomicilio);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorCambioDomicilio.getString(cursorCambioDomicilio.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mDatosCoordenadas.setParticipante(participante);
            Barrio barrio = this.getBarrio(MainDBConstants.codigo+"="+cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.barrio)), null);
            mDatosCoordenadas.setBarrio(barrio);
        }
        if (!cursorCambioDomicilio.isClosed()) cursorCambioDomicilio.close();
        return mDatosCoordenadas;
    }
    //Obtener una lista de DatosCoordenadas de la base de datos
    public List<DatosCoordenadas> getDatosCoordenadas(String filtro, String orden) throws SQLException {
        List<DatosCoordenadas> mCambiosDomicilio = new ArrayList<DatosCoordenadas>();
        Cursor cursorCambiosDomicilio = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE, filtro, null, orden);
        if (cursorCambiosDomicilio != null && cursorCambiosDomicilio.getCount() > 0) {
            cursorCambiosDomicilio.moveToFirst();
            mCambiosDomicilio.clear();
            do{
                DatosCoordenadas mDatosCoordenadas = null;
                mDatosCoordenadas = DatosCoordenadasHelper.crearDatosCoordenada(cursorCambiosDomicilio);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorCambiosDomicilio.getString(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mDatosCoordenadas.setParticipante(participante);
                Barrio barrio = this.getBarrio(MainDBConstants.codigo+"="+cursorCambiosDomicilio.getInt(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.barrio)), null);
                mDatosCoordenadas.setBarrio(barrio);
                mCambiosDomicilio.add(mDatosCoordenadas);
            } while (cursorCambiosDomicilio.moveToNext());
        }
        if (!cursorCambiosDomicilio.isClosed()) cursorCambiosDomicilio.close();
        return mCambiosDomicilio;
    }

    /**
     * Metodos para EncuestaPesoTalla en la base de datos
     *
     * @param encuestaPesoTalla
     *            Objeto EncuestasPesoTalla que contiene la informacion
     *
     */
    //Crear nuevo EncuestasPesoTalla en la base de datos
    public void crearEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, cv);
    }
    //Editar EncuestasPesoTalla existente en la base de datos
    public boolean editarEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, cv, EncuestasDBConstants.codigo + "='"
                + encuestaPesoTalla.getCodigo()+ "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasPesoTalla de la base de datos
    public boolean borrarEncuestasPesoTallas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaPesoTalla de la base de datos
    public EncuestaPesoTalla getEncuestasPesoTalla(String filtro, String orden) throws SQLException {
        EncuestaPesoTalla mEncuestasPesoTalla = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasPesoTalla=EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante))+"'", null);
            if (participante != null) mEncuestasPesoTalla.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasPesoTalla;
    }
    //Obtener una lista de EncuestasPesoTalla de la base de datos
    public List<EncuestaPesoTalla> getEncuestasPesoTallas(String filtro, String orden) throws SQLException {
        List<EncuestaPesoTalla> mEncuestas = new ArrayList<EncuestaPesoTalla>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaPesoTalla mEncuesta = null;
                mEncuesta = EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)) +"'", null);
                if (participante != null) mEncuesta.setParticipante(participante);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaCasa en la base de datos
     *
     * @param encuestaCasa
     *            Objeto EncuestasCasa que contiene la informacion
     *
     */
    //Crear nuevo EncuestasCasa en la base de datos
    public void crearEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, cv);
    }
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        return mDb.update(EncuestasDBConstants.ENCUESTA_CASA_TABLE, cv, EncuestasDBConstants.codigo + "='"
                + encuestaCasa.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasCasa de la base de datos
    public boolean borrarEncuestaCasas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaCasa de la base de datos
    public EncuestaCasa getEncuestaCasa(String filtro, String orden) throws SQLException {
        EncuestaCasa mEncuestaCasa = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasa=EncuestaCasaHelper.crearEncuestaCasa(cursor);
            Casa casa = this.getCasa(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa)), null);
            if (casa != null) mEncuestaCasa.setCasa(casa);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasa;
    }
    //Obtener una lista de EncuestasCasa de la base de datos
    public List<EncuestaCasa> getEncuestaCasas(String filtro, String orden) throws SQLException {
        List<EncuestaCasa> mEncuestas = new ArrayList<EncuestaCasa>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaCasa mEncuesta = null;
                mEncuesta = EncuestaCasaHelper.crearEncuestaCasa(cursor);
                Casa casa = this.getCasa(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa)), null);
                if (casa != null) mEncuesta.setCasa(casa);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaParticipante en la base de datos
     *
     * @param encuestaParticipante
     *            Objeto EncuestasParticipante que contiene la informacion
     *
     */
    //Crear nuevo EncuestasParticipante en la base de datos
    public void crearEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, cv);
    }
    //Editar EncuestasParticipante existente en la base de datos
    public boolean editarEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, cv, EncuestasDBConstants.participante + "='"
                + encuestaParticipante.getParticipante().getCodigo()+"'", null) > 0;
    }
    //Limpiar la tabla de EncuestasParticipante de la base de datos
    public boolean borrarEncuestasParticipantes() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaParticipante de la base de datos
    public EncuestaParticipante getEncuestasParticipante(String filtro, String orden) throws SQLException {
        EncuestaParticipante mEncuestasParticipante = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasParticipante=EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)) +"'", null);
            if (participante != null) mEncuestasParticipante.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasParticipante;
    }
    //Obtener una lista de EncuestasParticipante de la base de datos
    public List<EncuestaParticipante> getEncuestasParticipantes(String filtro, String orden) throws SQLException {
        List<EncuestaParticipante> mEncuestas = new ArrayList<EncuestaParticipante>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaParticipante mEncuesta = null;
                mEncuesta = EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)) +"'", null);
                if (participante != null) mEncuesta.setParticipante(participante);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }
    //Crear nueva Admision en la base de datos
    public void crearAdmision(Admision admision) {
        ContentValues cv = AdmisionHelper.crearAdmisionContentValues(admision);
        mDb.insertOrThrow(MainDBConstants.ADMISION_PACIENTES_TABLE, null, cv);
    }
    //Crear nueva Admision en la base de datos
    public void crearInformeFinDiaMedicos(InformeFindeDiaMedicos informeFindeDiaMedicos) {
        ContentValues cv = InformeFinDiaMedicosHelper.crearFinDiaMedicosValues(informeFindeDiaMedicos);
        mDb.insertOrThrow(MainDBConstants.INFORME_FIN_DIA_MEDICOS_TABLE, null, cv);
    }
    /**
     * Metodos para Muestra en la base de datos
     *
     * @param muestra
     *            Objeto Muestras que contiene la informacion
     *
     */
    //Crear nuevo Muestras en la base de datos
    public void crearMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        mDb.insertOrThrow(MainDBConstants.MUESTRAS_TABLE, null, cv);
    }
    //Editar Muestras existente en la base de datos
    public boolean editarMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        return mDb.update(MainDBConstants.MUESTRAS_TABLE, cv, MainDBConstants.idMuestra + "='"
                + muestra.getIdMuestra() + "'", null) > 0;
    }
    //Limpiar la tabla de Muestras de la base de datos
    public boolean borrarMuestras() {
        return mDb.delete(MainDBConstants.MUESTRAS_TABLE, null, null) > 0;
    }
    //Limpiar la tabla de Muestras Transmision de la base de datos
    /*public boolean borrarMuestrasTx() {
        return mDb.delete(MainDBConstants.MUESTRAS_TABLE, MainDBConstants.proposito + "='3'" , null) > 0;
    }*/
    //Obtener una Muestra de la base de datos
    public Muestra getMuestra(String filtro, String orden) throws SQLException {
        Muestra mMuestras = null;
        Cursor cursor = crearCursor(MainDBConstants.MUESTRAS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras=MuestraHelper.crearMuestra(cursor);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)) +"'", null);
            if (participante != null) mMuestras.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }
    //Obtener una lista de Muestras de la base de datos
    public List<Muestra> getMuestras(String filtro, String orden) throws SQLException {
        List<Muestra> mMuestras = new ArrayList<Muestra>();
        Cursor cursor = crearCursor(MainDBConstants.MUESTRAS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras.clear();
            do{
                Muestra mMuestra = null;
                mMuestra = MuestraHelper.crearMuestra(cursor);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" + cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)) +"'", null);
                if (participante != null) mMuestra.setParticipante(participante);
                mMuestras.add(mMuestra);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }

    /**
     * Inserta un participantes_procesos en la base de datos
     *
     * @param participante
     *            Objeto Participantes_procesos que contiene la informacion
     *
     */
    public void crearParticipanteProcesos(ParticipanteProcesos participante) {
        ContentValues cv = new ContentValues();
        cv = ParticipanteHelper.crearParticipanteProcesosContentValues(participante);
        mDb.insertOrThrow(MainDBConstants.PARTICIPANTE_PROC_TABLE, null, cv);
    }

    //Crear nuevo ParticipanteProceso en la base de datos desde otro equipo
    public void insertarParticipanteProcesos(String participanteProcesosSQL) {
        mDb.execSQL(participanteProcesosSQL);
    }

    /**
     * Actualiza un participante en la base de datos.
     *
     * @param participante
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean editarParticipanteProcesos(ParticipanteProcesos participante) {
        ContentValues cv = ParticipanteHelper.crearParticipanteProcesosContentValues(participante);
        return mDb.update(MainDBConstants.PARTICIPANTE_PROC_TABLE, cv, MainDBConstants.codigo + "='"
                + participante.getCodigo()+"'", null) > 0;
    }

    //Limpiar la tabla de Muestras de la base de datos
    public boolean borrarParticipantesProcesos() {
        return mDb.delete(MainDBConstants.PARTICIPANTE_PROC_TABLE, null, null) > 0;
    }

    //Obtener un participante procesos de la base de datos
    public ParticipanteProcesos getParticipanteProcesos(String filtro, String orden) throws SQLException {
        ParticipanteProcesos mPartProc = null;
        Cursor cursor = crearCursor(MainDBConstants.PARTICIPANTE_PROC_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mPartProc=ParticipanteHelper.crearParticipanteProcesos(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mPartProc;
    }

    //Obtener una lista de Participante de la base de datos
    public List<ParticipanteProcesos> getParticipantesProc(String filtro, String orden) throws SQLException {
        List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
        Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_PROC_TABLE, filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipantesProc.clear();
            do{
                ParticipanteProcesos mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipanteProcesos(cursorParticipante);
                mParticipantesProc.add(mParticipante);
            } while (cursorParticipante.moveToNext());
        }
        if (cursorParticipante!=null && !cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipantesProc;
    }

    /***
     * Metodos para recepcion de muestras supervisor en la base de datos
     * */
    //Crear nuevo recepcionMuestra en la base de datos
    public void crearRecepcionMuestra(RecepcionMuestra recepcionMuestra) {
        ContentValues cv = RecepcionMuestraHelper.crearRecepcionMuestraContentValues(recepcionMuestra);
        mDb.insertOrThrow(MainDBConstants.RECEPCION_MUESTRA_TABLE, null, cv);
    }

    //Editar recepcionMuestra existente en la base de datos
    public boolean editarRecepcionMuestra(RecepcionMuestra recepcionMuestra) {
        ContentValues cv = RecepcionMuestraHelper.crearRecepcionMuestraContentValues(recepcionMuestra);
        return mDb.update(MainDBConstants.RECEPCION_MUESTRA_TABLE , cv, MainDBConstants.idRecepcion + "='"
                + recepcionMuestra.getIdRecepcion() + "'", null) > 0;
    }
    //Limpiar la tabla de recepción de muestras de la base de datos
    public boolean borrarRecepcionMuestras() {
        return mDb.delete(MainDBConstants.RECEPCION_MUESTRA_TABLE, null, null) > 0;
    }
    //Obtener un recepcionMuestra de la base de datos
    public RecepcionMuestra getRecepcionMuestra(String filtro, String orden) throws SQLException {
        RecepcionMuestra mRecepcion = null;
        Cursor cursor = crearCursor(MainDBConstants.RECEPCION_MUESTRA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepcion= RecepcionMuestraHelper.crearRecepcionMuestra(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepcion;
    }
    //Obtener una lista de recepción de muestras de la base de datos
    public List<RecepcionMuestra> getRecepcionMuestras(String filtro, String orden) throws SQLException {
        List<RecepcionMuestra> mRecepciones = new ArrayList<RecepcionMuestra>();
        Cursor cursor = crearCursor(MainDBConstants.RECEPCION_MUESTRA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepciones.clear();
            do{
                RecepcionMuestra mRecepcion = null;
                mRecepcion = RecepcionMuestraHelper.crearRecepcionMuestra(cursor);
                mRecepciones.add(mRecepcion);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepciones;
    }

    /**
     * Busca una Recepcion la base de datos
     *
     * @return boolean
     */

    public boolean recepcionRegistrada(String filtro) throws SQLException {
        boolean registrada;
        Cursor cursor = crearCursor(MainDBConstants.RECEPCION_MUESTRA_TABLE, filtro, null, null);
        registrada = cursor != null && cursor.getCount() > 0;
        if (!cursor.isClosed()) cursor.close();
        return registrada;
    }

    /*****
     *
     * Metodos para recepcion serologias laboratorio en la base de datos
     */

    //Crear nueva recepcion serologia laboratorio en la base de datos
    public void crearSerologia(Serologia serologia) {
        ContentValues cv = SerologiaHelper.crearSerologiaLabContentValues(serologia);
        mDb.insertOrThrow(MainDBConstants.SEROLOGIA_TABLE, null, cv);
    }

    //Editar serologia existente en la base de datos
    public boolean editarSerologia(Serologia serologia) {
        ContentValues cv = SerologiaHelper.crearSerologiaLabContentValues(serologia);
        return mDb.update(MainDBConstants.SEROLOGIA_TABLE , cv, MainDBConstants.participante + "='"
                + serologia.getParticipante() + "' and " + MainDBConstants.fecha + " = " + serologia.getFecha().getTime(), null) > 0;
    }

    //Limpiar la tabla de recepcion serologia laboratoriode la base de datos
    public boolean borrarSerologias() {
        return mDb.delete(MainDBConstants.SEROLOGIA_TABLE, null, null) > 0;
    }
    //Obtener un serologia de la base de datos
    public Serologia getSerologia(String filtro, String orden) throws SQLException {
        Serologia mRecepcion = null;
        Cursor cursor = crearCursor(MainDBConstants.SEROLOGIA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepcion= SerologiaHelper.crearSerologiaLab(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepcion;
    }
    //Obtener una lista de recepciones serologia laboratorio de la base de datos
    public List<Serologia> getSerologias(String filtro, String orden) throws SQLException {
        List<Serologia> mRecepciones = new ArrayList<Serologia>();
        Cursor cursor = crearCursor(MainDBConstants.SEROLOGIA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepciones.clear();
            do{
                Serologia mRecepcion = null;
                mRecepcion = SerologiaHelper.crearSerologiaLab(cursor);
                mRecepciones.add(mRecepcion);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepciones;
    }

    /**
     * Busca una Serogolia de la base de datos
     *
     * @return boolean
     */

    public boolean serologiaRegistrada(String filtro) throws SQLException {
        boolean registrada;
        Cursor cursor = crearCursor(MainDBConstants.SEROLOGIA_TABLE, filtro, null, null);
        registrada = cursor != null && cursor.getCount() > 0;
        if (!cursor.isClosed()) cursor.close();
        return registrada;
    }

    /**
     * Metodos para RazonNoData en la base de datos
     *
     * @param razonNoData
     *            Objeto RazonNoDatas que contiene la informacion
     *
     */
    //Crear nuevo RazonNoDatas en la base de datos
    public void crearRazonNoDatas(RazonNoData razonNoData) {
        ContentValues cv = RazonNoDataHelper.crearRazonNoDataContentValues(razonNoData);
        mDb.insertOrThrow(MainDBConstants.RAZON_NODATA_TABLE, null, cv);
    }
    //Editar RazonNoDatas existente en la base de datos
    public boolean editarRazonNoDatas(RazonNoData razonNoData) {
        ContentValues cv = RazonNoDataHelper.crearRazonNoDataContentValues(razonNoData);
        return mDb.update(MainDBConstants.RAZON_NODATA_TABLE, cv, MainDBConstants.codigo + "='"
                + razonNoData.getCodigoParticipante() + "' and "+ MainDBConstants.recordDate + " = " + razonNoData.getRecordDate().getTime() , null) > 0;
    }
    //Limpiar la tabla de RazonNoDatas de la base de datos
    public boolean borrarRazonNoDatas() {
        return mDb.delete(MainDBConstants.RAZON_NODATA_TABLE, null, null) > 0;
    }
    //Limpiar la tabla de RazonNoDatas Transmision de la base de datos
    /*public boolean borrarRazonNoDatasTx() {
        return mDb.delete(MainDBConstants.RAZON_NODATA_TABLE, MainDBConstants.proposito + "='3'" , null) > 0;
    }*/
    //Obtener una RazonNoData de la base de datos
    public RazonNoData getRazonNoData(String filtro, String orden) throws SQLException {
        RazonNoData mRazonNoDatas = null;
        Cursor cursor = crearCursor(MainDBConstants.RAZON_NODATA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRazonNoDatas=RazonNoDataHelper.crearRazonNoData(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mRazonNoDatas;
    }
    //Obtener una lista de RazonNoDatas de la base de datos
    public List<RazonNoData> getRazonNoDatas(String filtro, String orden) throws SQLException {
        List<RazonNoData> mRazonNoDatas = new ArrayList<RazonNoData>();
        Cursor cursor = crearCursor(MainDBConstants.RAZON_NODATA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRazonNoDatas.clear();
            do{
                RazonNoData mRazonNoData = null;
                mRazonNoData = RazonNoDataHelper.crearRazonNoData(cursor);
                mRazonNoDatas.add(mRazonNoData);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mRazonNoDatas;
    }

    /**
     * Metodos para puntoCandidatos en la base de datos
     *
     * @param puntoCandidato
     *            Objeto PuntoCandidato que contiene la informacion
     *
     */
    //Crear nuevo puntoCandidato en la base de datos
    public void crearPuntoCandidato(PuntoCandidato puntoCandidato) {
        ContentValues cv = PuntoCandidatoHelper.crearPuntosCandidatosContenValues(puntoCandidato);
        mDb.insertOrThrow(MainDBConstants.PUNTOS_CANDIDATOS_TABLE, null, cv);
    }
    //Editar puntoCandidato existente en la base de datos
    public boolean editarPuntoCandidato(PuntoCandidato puntoCandidato) {
        ContentValues cv = PuntoCandidatoHelper.crearPuntosCandidatosContenValues(puntoCandidato);
        return mDb.update(MainDBConstants.PUNTOS_CANDIDATOS_TABLE , cv, MainDBConstants.codigo + "='"
                + puntoCandidato.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de puntoCandidatos de la base de datos
    public boolean borrarPuntoCandidatos() {
        return mDb.delete(MainDBConstants.PUNTOS_CANDIDATOS_TABLE, null, null) > 0;
    }
    //Obtener un puntoCandidato de la base de datos
    public PuntoCandidato getPuntoCandidato(String filtro, String orden) throws SQLException {
        PuntoCandidato mPuntoCandidato = null;
        Cursor cursorPuntoCandidato = crearCursor(MainDBConstants.PUNTOS_CANDIDATOS_TABLE , filtro, null, orden);
        if (cursorPuntoCandidato != null && cursorPuntoCandidato.getCount() > 0) {
            cursorPuntoCandidato.moveToFirst();
            mPuntoCandidato=PuntoCandidatoHelper.crearPuntoCandidato(cursorPuntoCandidato);
        }
        if (!cursorPuntoCandidato.isClosed()) cursorPuntoCandidato.close();
        return mPuntoCandidato;
    }
    //Obtener una lista de puntoCandidatos de la base de datos
    public List<PuntoCandidato> getPuntoCandidatos(String filtro, String orden) throws SQLException {
        List<PuntoCandidato> mPuntoCandidatos = new ArrayList<PuntoCandidato>();
        Cursor cursorPuntoCandidatos = crearCursor(MainDBConstants.PUNTOS_CANDIDATOS_TABLE, filtro, null, orden);
        if (cursorPuntoCandidatos != null && cursorPuntoCandidatos.getCount() > 0) {
            cursorPuntoCandidatos.moveToFirst();
            mPuntoCandidatos.clear();
            do{
                PuntoCandidato mPuntoCandidato = null;
                mPuntoCandidato = PuntoCandidatoHelper.crearPuntoCandidato(cursorPuntoCandidatos);
                mPuntoCandidatos.add(mPuntoCandidato);
            } while (cursorPuntoCandidatos.moveToNext());
        }
        if (!cursorPuntoCandidatos.isClosed()) cursorPuntoCandidatos.close();
        return mPuntoCandidatos;
    }

    /**
     * Metodos para entrega de obsequios
     *
     * @param obsequioGeneral
     *            Objeto ObsequioGeneral que contiene la informacion
     *
     */
    //Crear nuevo ObsequioGeneral en la base de datos
    public void crearObsequioGeneral(ObsequioGeneral obsequioGeneral) {
        ContentValues cv = ObsequioHelper.crearObsequioContentValues(obsequioGeneral);
        mDb.insertOrThrow(MainDBConstants.OBSEQUIOS_TABLE, null, cv);
    }
    //Editar ObsequioGeneral existente en la base de datos
    public boolean editarObsequioGeneral(ObsequioGeneral obsequioGeneral) {
        ContentValues cv = ObsequioHelper.crearObsequioContentValues(obsequioGeneral);
        return mDb.update(MainDBConstants.OBSEQUIOS_TABLE , cv, MainDBConstants.id + "='"
                + obsequioGeneral.getId()+ "'", null) > 0;
    }
    //Limpiar la tabla de ObsequioGeneral de la base de datos
    public boolean borrarObsequiosGenerales() {
        return mDb.delete(MainDBConstants.OBSEQUIOS_TABLE, null, null) > 0;
    }
    //Obtener un ObsequioGeneral de la base de datos
    public ObsequioGeneral getObsequioGeneral(String filtro, String orden) throws SQLException {
        ObsequioGeneral mObsequioGeneral = null;
        Cursor cursorObsequio = crearCursor(MainDBConstants.OBSEQUIOS_TABLE , filtro, null, orden);
        if (cursorObsequio != null && cursorObsequio.getCount() > 0) {
            cursorObsequio.moveToFirst();
            mObsequioGeneral= ObsequioHelper.crearObsequio(cursorObsequio);
        }
        if (!cursorObsequio.isClosed()) cursorObsequio.close();
        return mObsequioGeneral;
    }
    //Obtener una lista de ObsequioGeneral de la base de datos
    public List<ObsequioGeneral> getObsequiosGenerales(String filtro, String orden) throws SQLException {
        List<ObsequioGeneral> mObsequios = new ArrayList<ObsequioGeneral>();
        Cursor cursorObsequios = crearCursor(MainDBConstants.OBSEQUIOS_TABLE, filtro, null, orden);
        if (cursorObsequios != null && cursorObsequios.getCount() > 0) {
            cursorObsequios.moveToFirst();
            mObsequios.clear();
            do{
                ObsequioGeneral mObsequioGeneral = null;
                mObsequioGeneral = ObsequioHelper.crearObsequio(cursorObsequios);
                mObsequios.add(mObsequioGeneral);
            } while (cursorObsequios.moveToNext());
        }
        if (!cursorObsequios.isClosed()) cursorObsequios.close();
        return mObsequios;
    }

    /**
     * Metodos para entrega de obsequios
     *
     * @param ordenLaboratorio
     *            Objeto OrdenLaboratorio que contiene la informacion
     *
     */
    //Crear nuevo OrdenLaboratorio en la base de datos
    public void crearOrdenLaboratorio(OrdenLaboratorio ordenLaboratorio) {
        ContentValues cv = OrdenLaboratorioHelper.crearOrdenLaboratorioContentValues(ordenLaboratorio);
        mDb.insertOrThrow(MainDBConstants.ORDEN_LAB_TABLE, null, cv);
    }
    //Editar OrdenLaboratorio existente en la base de datos
    public boolean editarOrdenLaboratorio(OrdenLaboratorio ordenLaboratorio) {
        ContentValues cv = OrdenLaboratorioHelper.crearOrdenLaboratorioContentValues(ordenLaboratorio);
        return mDb.update(MainDBConstants.ORDEN_LAB_TABLE , cv, MainDBConstants.idOrden + "='"
                + ordenLaboratorio.getIdOrden()+ "'", null) > 0;
    }
    //Limpiar la tabla de OrdenLaboratorio de la base de datos
    public boolean borrarOrdenesLaboratorio() {
        return mDb.delete(MainDBConstants.ORDEN_LAB_TABLE, null, null) > 0;
    }
    //Obtener un OrdenLaboratorio de la base de datos
    public OrdenLaboratorio getOrdenLaboratorio(String filtro, String orden) throws SQLException {
        OrdenLaboratorio mOrdenLaboratorio = null;
        Cursor cursorOrdenLaboratorio = crearCursor(MainDBConstants.ORDEN_LAB_TABLE , filtro, null, orden);
        if (cursorOrdenLaboratorio != null && cursorOrdenLaboratorio.getCount() > 0) {
            cursorOrdenLaboratorio.moveToFirst();
            mOrdenLaboratorio= OrdenLaboratorioHelper.crearOrdenLaboratorio(cursorOrdenLaboratorio);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorOrdenLaboratorio.getString(cursorOrdenLaboratorio.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mOrdenLaboratorio.setParticipante(participante);
        }
        if (!cursorOrdenLaboratorio.isClosed()) cursorOrdenLaboratorio.close();
        return mOrdenLaboratorio;
    }
    //Obtener una lista de OrdenLaboratorio de la base de datos
    public List<OrdenLaboratorio> getOrdenesLaboratorio(String filtro, String orden) throws SQLException {
        List<OrdenLaboratorio> mOrdenLaboratorios = new ArrayList<OrdenLaboratorio>();
        Cursor cursorOrdenLaboratorios = crearCursor(MainDBConstants.ORDEN_LAB_TABLE, filtro, null, orden);
        if (cursorOrdenLaboratorios != null && cursorOrdenLaboratorios.getCount() > 0) {
            cursorOrdenLaboratorios.moveToFirst();
            mOrdenLaboratorios.clear();
            do{
                OrdenLaboratorio mOrdenLaboratorio = null;
                mOrdenLaboratorio = OrdenLaboratorioHelper.crearOrdenLaboratorio(cursorOrdenLaboratorios);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorOrdenLaboratorios.getString(cursorOrdenLaboratorios.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mOrdenLaboratorio.setParticipante(participante);
                mOrdenLaboratorios.add(mOrdenLaboratorio);
            } while (cursorOrdenLaboratorios.moveToNext());
        }
        if (!cursorOrdenLaboratorios.isClosed()) cursorOrdenLaboratorios.close();
        return mOrdenLaboratorios;
    }

    /**
     * Metodos para muestras de enfermo
     *
     * @param muestraEnfermo
     *            Objeto MuestraEnfermo que contiene la informacion
     *
     */
    //Crear nuevo MuestraEnfermo en la base de datos
    public void crearMuestraEnfermo(MuestraEnfermo muestraEnfermo) {
        ContentValues cv = MuestraEnfermoHelper.crearMuestraEnfermoContentValues(muestraEnfermo);
        mDb.insertOrThrow(MainDBConstants.MUESTRAS_ENFERMO_TABLE, null, cv);
    }
    //Editar MuestraEnfermo existente en la base de datos
    public boolean editarMuestraEnfermo(MuestraEnfermo muestraEnfermo) {
        ContentValues cv = MuestraEnfermoHelper.crearMuestraEnfermoContentValues(muestraEnfermo);
        return mDb.update(MainDBConstants.MUESTRAS_ENFERMO_TABLE , cv, MainDBConstants.idMuestra + "='"
                + muestraEnfermo.getIdMuestra()+ "'", null) > 0;
    }
    //Limpiar la tabla de MuestraEnfermo de la base de datos
    public boolean borrarMuestrasEnfermo() {
        return mDb.delete(MainDBConstants.MUESTRAS_ENFERMO_TABLE, null, null) > 0;
    }
    //Crear nuevo ControlAsistencia en la base de datos
    public void crearControlAsistencia(ControlAsistencia controlAsistencia) {
        ContentValues cv = ControlAsistenciaHelper.crearControlAsistenciaContentValues(controlAsistencia);
        mDb.insertOrThrow(MainDBConstants.CONTROL_ASISTENCIA_TABLE, null, cv);
    }

    //Crear nuevo Control Temperatura en la base de datos
    public void crearControlTemperatura(ControlTemperaturaTermo controlTemp) {
        ContentValues cv = ControlTemperaturaHelper.crearControlTemperaturaContentValues(controlTemp);
        mDb.insertOrThrow(MainDBConstants.CONTROL_TEMPERATURA_TERMO_TABLE, null, cv);
    }

    //Obtener un ControlAsistencia de la base de datos
    public ControlAsistencia getControlAsistencia(String filtro, String orden) throws SQLException {
        ControlAsistencia mControlAsistencia = null;
        Cursor cursorControlAsistencia = crearCursor(MainDBConstants.CONTROL_ASISTENCIA_TABLE , filtro, null, orden);
        if (cursorControlAsistencia != null && cursorControlAsistencia.getCount() > 0) {
            cursorControlAsistencia.moveToFirst();
            mControlAsistencia = ControlAsistenciaHelper.crearControlAsistencia(cursorControlAsistencia);
        }
        if (!cursorControlAsistencia.isClosed()) cursorControlAsistencia.close();
        return mControlAsistencia;
    }
    //Obtener una lista de CONTROL ASISTENCIA de la base de datos
    public List<ControlAsistencia> getControlAsistencial(String filtro, String orden) throws SQLException {
        List<ControlAsistencia> mControlAsistencia = new ArrayList<ControlAsistencia>();
        Cursor cursorControlAsistencia = crearCursor(MainDBConstants.CONTROL_ASISTENCIA_TABLE, filtro, null, orden);
        if (cursorControlAsistencia != null && cursorControlAsistencia.getCount() > 0) {
            cursorControlAsistencia.moveToFirst();
            mControlAsistencia.clear();
            do{
                ControlAsistencia mCA = null;
                mCA = ControlAsistenciaHelper.crearControlAsistencia(cursorControlAsistencia);
                mControlAsistencia.add(mCA);
            } while (cursorControlAsistencia.moveToNext());
        }
        if (!cursorControlAsistencia.isClosed()) cursorControlAsistencia.close();
        return mControlAsistencia;
    }
    //Obtener una lista de ADMISION de la base de datos
    public List<Admision> getAdmision(String filtro, String orden) throws SQLException {
        List<Admision> mAdmision= new ArrayList<Admision>();
        Cursor cursorAdmision = crearCursor(MainDBConstants.ADMISION_PACIENTES_TABLE, filtro, null, orden);
        if (cursorAdmision != null && cursorAdmision.getCount() > 0) {
            cursorAdmision.moveToFirst();
            mAdmision.clear();
            do{
                Admision mCA = null;
                mCA = AdmisionHelper.crearAdmision(cursorAdmision);
                mAdmision.add(mCA);
            } while (cursorAdmision.moveToNext());
        }
        if (!cursorAdmision.isClosed()) cursorAdmision.close();
        return mAdmision;
    }
    //Obtener una lista de Control de temperatura de la base de datos
    public List<ControlTemperaturaTermo> getControlTemperatura(String filtro, String orden) throws SQLException {
        List<ControlTemperaturaTermo> mControlTemperatura= new ArrayList<ControlTemperaturaTermo>();
        Cursor cursorInforme = crearCursor(MainDBConstants.CONTROL_TEMPERATURA_TERMO_TABLE, filtro, null, orden);
        if (cursorInforme != null && cursorInforme.getCount() > 0) {
            cursorInforme.moveToFirst();
            mControlTemperatura.clear();
            do{
                ControlTemperaturaTermo mCA = null;
                mCA = ControlTemperaturaHelper.crearControlTemperatura(cursorInforme);
                mControlTemperatura.add(mCA);
            } while (cursorInforme.moveToNext());
        }
        if (!cursorInforme.isClosed()) cursorInforme.close();
        return mControlTemperatura;
    }
    //Obtener una lista de ADMISION de la base de datos
    public List<InformeFindeDiaMedicos> getInformeFinDiaMedicos(String filtro, String orden) throws SQLException {
        List<InformeFindeDiaMedicos> mInformeDiaMedicos= new ArrayList<InformeFindeDiaMedicos>();
        Cursor cursorInforme = crearCursor(MainDBConstants.INFORME_FIN_DIA_MEDICOS_TABLE, filtro, null, orden);
        if (cursorInforme != null && cursorInforme.getCount() > 0) {
            cursorInforme.moveToFirst();
            mInformeDiaMedicos.clear();
            do{
                InformeFindeDiaMedicos mCA = null;
                mCA = InformeFinDiaMedicosHelper.crearInformeFinDiaMedicos(cursorInforme);
                mInformeDiaMedicos.add(mCA);
            } while (cursorInforme.moveToNext());
        }
        if (!cursorInforme.isClosed()) cursorInforme.close();
        return mInformeDiaMedicos;
    }
    //Obtener un ControlAsistencia de la base de datos
    public InformeFindeDiaMedicos getInformeFinDiaMedicosU(String filtro, String orden) throws SQLException {
        InformeFindeDiaMedicos mControlAsistencia = null;
        Cursor cursorControlAsistencia = crearCursor(MainDBConstants.INFORME_FIN_DIA_MEDICOS_TABLE , filtro, null, orden);
        if (cursorControlAsistencia != null && cursorControlAsistencia.getCount() > 0) {
            cursorControlAsistencia.moveToFirst();
            mControlAsistencia = InformeFinDiaMedicosHelper.crearInformeFinDiaMedicos(cursorControlAsistencia);
        }
        if (!cursorControlAsistencia.isClosed()) cursorControlAsistencia.close();
        return mControlAsistencia;
    }
    //Obtener un MuestraEnfermo de la base de datos
    public MuestraEnfermo getMuestraEnfermo(String filtro, String orden) throws SQLException {
        MuestraEnfermo mMuestraEnfermo = null;
        Cursor cursorMuestraEnfermo = crearCursor(MainDBConstants.MUESTRAS_ENFERMO_TABLE , filtro, null, orden);
        if (cursorMuestraEnfermo != null && cursorMuestraEnfermo.getCount() > 0) {
            cursorMuestraEnfermo.moveToFirst();
            mMuestraEnfermo= MuestraEnfermoHelper.crearMuestraEnfermo(cursorMuestraEnfermo);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorMuestraEnfermo.getString(cursorMuestraEnfermo.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mMuestraEnfermo.setParticipante(participante);
        }
        if (!cursorMuestraEnfermo.isClosed()) cursorMuestraEnfermo.close();
        return mMuestraEnfermo;
    }
    //Obtener una lista de MuestraEnfermo de la base de datos
    public List<MuestraEnfermo> getMuestrasEnfermo(String filtro, String orden) throws SQLException {
        List<MuestraEnfermo> mMuestraEnfermos = new ArrayList<MuestraEnfermo>();
        Cursor cursorMuestraEnfermos = crearCursor(MainDBConstants.MUESTRAS_ENFERMO_TABLE, filtro, null, orden);
        if (cursorMuestraEnfermos != null && cursorMuestraEnfermos.getCount() > 0) {
            cursorMuestraEnfermos.moveToFirst();
            mMuestraEnfermos.clear();
            do{
                MuestraEnfermo mMuestraEnfermo = null;
                mMuestraEnfermo = MuestraEnfermoHelper.crearMuestraEnfermo(cursorMuestraEnfermos);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorMuestraEnfermos.getString(cursorMuestraEnfermos.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mMuestraEnfermo.setParticipante(participante);
                mMuestraEnfermos.add(mMuestraEnfermo);
            } while (cursorMuestraEnfermos.moveToNext());
        }
        if (!cursorMuestraEnfermos.isClosed()) cursorMuestraEnfermos.close();
        return mMuestraEnfermos;
    }


    /**
     * Metodos para recepcions de enfermo
     *
     * @param recepcionEnfermo
     *            Objeto RecepcionEnfermo que contiene la informacion
     *
     */
    //Crear nuevo RecepcionEnfermo en la base de datos
    public void crearRecepcionEnfermo(RecepcionEnfermo recepcionEnfermo) {
        ContentValues cv = RecepcionEnfermoHelper.crearRecepcionEnfermoContentValues(recepcionEnfermo);
        mDb.insertOrThrow(MainDBConstants.RECEPCION_ENFERMO_TABLE, null, cv);
    }
    public void crearRecepcionEnfermo1(RecepcionEnfermomessage recepcionEnfermo) {
        ContentValues cv = RecepcionEnfermoHelper.crearRecepcionEnfermoContentValues1(recepcionEnfermo);
        mDb.insertOrThrow(MainDBConstants.RECEPCION_ENFERMO_TABLE, null, cv);
    }
    //Editar RecepcionEnfermo existente en la base de datos
    public boolean editarRecepcionEnfermo(RecepcionEnfermo recepcionEnfermo) {
        ContentValues cv = RecepcionEnfermoHelper.crearRecepcionEnfermoContentValues(recepcionEnfermo);
        return mDb.update(MainDBConstants.RECEPCION_ENFERMO_TABLE , cv, MainDBConstants.idRecepcion + "='"
                + recepcionEnfermo.getIdRecepcion()+ "'", null) > 0;
    }
    //Limpiar la tabla de RecepcionEnfermo de la base de datos
    public boolean borrarRecepcionesEnfermo() {
        return mDb.delete(MainDBConstants.RECEPCION_ENFERMO_TABLE, null, null) > 0;
    }
    //Obtener un RecepcionEnfermo de la base de datos
    public RecepcionEnfermo getRecepcionEnfermo(String filtro, String orden) throws SQLException {
        RecepcionEnfermo mRecepcionEnfermo = null;

        Cursor cursorRecepcionEnfermo = crearCursor(MainDBConstants.RECEPCION_ENFERMO_TABLE , filtro, null, null);

        if (cursorRecepcionEnfermo != null && cursorRecepcionEnfermo.getCount() > 0) {

            cursorRecepcionEnfermo.moveToFirst();
            mRecepcionEnfermo= RecepcionEnfermoHelper.crearRecepcionEnfermo(cursorRecepcionEnfermo);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorRecepcionEnfermo.getString(cursorRecepcionEnfermo.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mRecepcionEnfermo.setParticipante(participante);
        }
        if (!cursorRecepcionEnfermo.isClosed()) cursorRecepcionEnfermo.close();
        return mRecepcionEnfermo;
    }
    //Obtener un RecepcionEnfermo de la base de datos
    public RecepcionEnfermomessage getRecepcionEnfermo1(String filtro, String orden) throws SQLException {
        RecepcionEnfermomessage mRecepcionEnfermo = null;

        Cursor cursorRecepcionEnfermo = crearCursor(MainDBConstants.RECEPCION_ENFERMO_TABLE , filtro, null, null);

        if (cursorRecepcionEnfermo != null && cursorRecepcionEnfermo.getCount() > 0) {

            cursorRecepcionEnfermo.moveToFirst();
            mRecepcionEnfermo = RecepcionEnfermoHelper.crearRecepcionEnfermo1(cursorRecepcionEnfermo);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorRecepcionEnfermo.getString(cursorRecepcionEnfermo.getColumnIndex(MainDBConstants.participante)) +"'", null);
            mRecepcionEnfermo.setParticipante(participante.getCodigo());
        }
        if (!cursorRecepcionEnfermo.isClosed()) cursorRecepcionEnfermo.close();
        return mRecepcionEnfermo;
    }
    //Obtener una lista de RecepcionEnfermo de la base de datos
    public List<RecepcionEnfermo> getRecepcionesEnfermo(String filtro, String orden) throws SQLException {
        List<RecepcionEnfermo> mRecepcionEnfermos = new ArrayList<RecepcionEnfermo>();
        Cursor cursorRecepcionEnfermos = crearCursor(MainDBConstants.RECEPCION_ENFERMO_TABLE, filtro, null, orden);
        if (cursorRecepcionEnfermos != null && cursorRecepcionEnfermos.getCount() > 0) {
            cursorRecepcionEnfermos.moveToFirst();
            mRecepcionEnfermos.clear();
            do{
                RecepcionEnfermo mRecepcionEnfermo = null;
                mRecepcionEnfermo = RecepcionEnfermoHelper.crearRecepcionEnfermo(cursorRecepcionEnfermos);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "='" +cursorRecepcionEnfermos.getString(cursorRecepcionEnfermos.getColumnIndex(MainDBConstants.participante)) +"'", null);
                mRecepcionEnfermo.setParticipante(participante);
                mRecepcionEnfermos.add(mRecepcionEnfermo);
            } while (cursorRecepcionEnfermos.moveToNext());
        }
        if (!cursorRecepcionEnfermos.isClosed()) cursorRecepcionEnfermos.close();
        return mRecepcionEnfermos;
    }


    /*****INICIO ENTOMOLOGIA***/
    //Crear nuevo CuestionarioHogar en la base de datos
    public void crearCuestionarioHogar(CuestionarioHogar cuestionarioHogar) throws Exception {
        ContentValues cv = EntomologiaHelper.crearCuestionarioHogarContentValues(cuestionarioHogar);
        mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, null, cv);
    }

    //Editar CuestionarioHogar existente en la base de datos
    public boolean editarCuestionarioHogar(CuestionarioHogar cuestionarioHogar) throws Exception{
        ContentValues cv = EntomologiaHelper.crearCuestionarioHogarContentValues(cuestionarioHogar);
        return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE , cv, EntomologiaBConstants.codigoEncuesta + "='"
                + cuestionarioHogar.getCodigoEncuesta() + "'", null) > 0;
    }
    //Limpiar la tabla de CuestionarioHogar de la base de datos
    public boolean borrarCuestionarioHogar() {
        return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, null, null) > 0;
    }
    //Obtener un CuestionarioHogar de la base de datos
    public CuestionarioHogar getCuestionarioHogar(String filtro, String orden) throws SQLException {
        CuestionarioHogar mCuestionarioHogar = null;
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioHogar=EntomologiaHelper.crearCuestionarioHogar(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioHogar;
    }
    //Obtener una lista de CuestionarioHogar de la base de datos
    public List<CuestionarioHogar> getCuestionariosHogar(String filtro, String orden) throws SQLException {
        List<CuestionarioHogar> mCuestionarioHogars = new ArrayList<CuestionarioHogar>();
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioHogars.clear();
            do{
                CuestionarioHogar mCuestionarioHogar = null;
                mCuestionarioHogar=EntomologiaHelper.crearCuestionarioHogar(cursor);
                mCuestionarioHogars.add(mCuestionarioHogar);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioHogars;
    }

    //Crear nuevo CuestionarioHogarPoblacion en la base de datos
    public void crearCuestionarioHogarPoblacion(CuestionarioHogarPoblacion cuestionarioHogarPoblacion) throws Exception {
        ContentValues cv = EntomologiaHelper.crearCuestionarioHogarPoblacionContentValues(cuestionarioHogarPoblacion);
        mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, null, cv);
    }

    //Editar CuestionarioHogarPoblacion existente en la base de datos
    public boolean editarCuestionarioHogarPoblacion(CuestionarioHogarPoblacion cuestionarioHogarPoblacion) throws Exception{
        ContentValues cv = EntomologiaHelper.crearCuestionarioHogarPoblacionContentValues(cuestionarioHogarPoblacion);
        return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE , cv, EntomologiaBConstants.codigoPoblacion + "='"
                + cuestionarioHogarPoblacion.getCodigoPoblacion() + "' ", null) > 0;
    }
    //Limpiar la tabla de CuestionarioHogarPoblacion de la base de datos
    public boolean borrarCuestionarioHogarPoblacion() {
        return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, null, null) > 0;
    }
    //Obtener un CuestionarioHogarPoblacion de la base de datos
    public CuestionarioHogarPoblacion getCuestionarioHogarPoblacion(String filtro, String orden) throws SQLException {
        CuestionarioHogarPoblacion mCuestionarioHogarPoblacion = null;
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioHogarPoblacion=EntomologiaHelper.crearCuestionarioHogarPoblacion(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioHogarPoblacion;
    }
    //Obtener una lista de CuestionarioHogarPoblacion de la base de datos
    public List<CuestionarioHogarPoblacion> getCuestionariosHogarPoblacion(String filtro, String orden) throws SQLException {
        List<CuestionarioHogarPoblacion> mCuestionarioHogarPoblacions = new ArrayList<CuestionarioHogarPoblacion>();
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioHogarPoblacions.clear();
            do{
                CuestionarioHogarPoblacion mCuestionarioHogarPoblacion = null;
                mCuestionarioHogarPoblacion=EntomologiaHelper.crearCuestionarioHogarPoblacion(cursor);
                mCuestionarioHogarPoblacions.add(mCuestionarioHogarPoblacion);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioHogarPoblacions;
    }

    //Crear nuevo CuestionarioPuntoClave en la base de datos
    public void crearCuestionarioPuntoClave(CuestionarioPuntoClave cuestionarioPuntoClave) throws Exception {
        ContentValues cv = EntomologiaHelper.crearCuestionarioPuntoClaveContentValues(cuestionarioPuntoClave);
        mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, null, cv);
    }

    //Editar CuestionarioPuntoClave existente en la base de datos
    public boolean editarCuestionarioPuntoClave(CuestionarioPuntoClave cuestionarioPuntoClave) throws Exception{
        ContentValues cv = EntomologiaHelper.crearCuestionarioPuntoClaveContentValues(cuestionarioPuntoClave);
        return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE , cv, EntomologiaBConstants.codigoCuestionario + "='"
                + cuestionarioPuntoClave.getCodigoCuestionario() + "' ", null) > 0;
    }
    //Limpiar la tabla de CuestionarioPuntoClave de la base de datos
    public boolean borrarCuestionarioPuntoClave() {
        return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, null, null) > 0;
    }
    //Obtener un CuestionarioPuntoClave de la base de datos
    public CuestionarioPuntoClave getCuestionarioPuntoClave(String filtro, String orden) throws SQLException {
        CuestionarioPuntoClave mCuestionarioPuntoClave = null;
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioPuntoClave= EntomologiaHelper.crearCuestionarioPuntoClave(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioPuntoClave;
    }
    //Obtener una lista de CuestionarioPuntoClave de la base de datos
    public List<CuestionarioPuntoClave> getCuestionariosPuntoClave(String filtro, String orden) throws SQLException {
        List<CuestionarioPuntoClave> mCuestionarioPuntoClaves = new ArrayList<CuestionarioPuntoClave>();
        Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCuestionarioPuntoClaves.clear();
            do{
                CuestionarioPuntoClave mCuestionarioPuntoClave = null;
                mCuestionarioPuntoClave=EntomologiaHelper.crearCuestionarioPuntoClave(cursor);
                mCuestionarioPuntoClaves.add(mCuestionarioPuntoClave);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mCuestionarioPuntoClaves;
    }

    /*-------------ENCUESTA DE SATISFACCION DE USUARIO-------------*/

    public boolean borrarEncuestaSatisfaccionUsuario() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_SATISFACCION_USUARIO_TABLE, null, null) > 0;
    }

    //Crear una nueva encuesta satisfaccion usuario en la base de datos
    public void crearEncuestaSatisfaccionUsuarioValues(EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario) {
        ContentValues cv = EncuestaSatisfaccionUsuarioHelper.crearEncuenstaSatisfaccionUsuarioContentValues(encuestaSatisfaccionUsuario);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_SATISFACCION_USUARIO_TABLE, null, cv);
    }

    public boolean updateEncuestaSatisfaccionUsuario(EncuestaSatisfaccionUsuario encuestaSatisfaccionUsuario) {
        ContentValues cv = EncuestaSatisfaccionUsuarioHelper.crearEncuenstaSatisfaccionUsuarioContentValues(encuestaSatisfaccionUsuario);
        return mDb.update(EncuestasDBConstants.ENCUESTA_SATISFACCION_USUARIO_TABLE, cv, EncuestasDBConstants.codigoParticipante + "='"
                + encuestaSatisfaccionUsuario.getCodigoParticipante() + "'", null) > 0;
    }

    public ArrayList<EncuestaSatisfaccionUsuario> getListaEncuestaSatisfaccionUsuario(Integer codigo) throws SQLException {
        Cursor cEncSatUsu = null;
        ArrayList<EncuestaSatisfaccionUsuario> mEncSatUsu = new ArrayList<EncuestaSatisfaccionUsuario>();
        cEncSatUsu = mDb.query(true, EncuestasDBConstants.ENCUESTA_SATISFACCION_USUARIO_TABLE, null,
                null,  null, null, null, null, null);
        if (cEncSatUsu != null && cEncSatUsu.getCount() > 0) {
            cEncSatUsu.moveToFirst();
            mEncSatUsu.clear();
            do{
                mEncSatUsu.add(EncuestaSatisfaccionUsuarioHelper.crearEncuestaSatisfaccionUsuario(cEncSatUsu));
            } while (cEncSatUsu.moveToNext());
        }
        cEncSatUsu.close();
        return mEncSatUsu;
    }

    public List<EncuestaSatisfaccionUsuario> getListaEncSatisfaccionUsuarioSinEnviar() throws SQLException {
        Cursor cEncSatUsuario = null;
        List<EncuestaSatisfaccionUsuario> mEncSatUsu = new ArrayList<EncuestaSatisfaccionUsuario>();
        cEncSatUsuario = mDb.query(true, EncuestasDBConstants.ENCUESTA_SATISFACCION_USUARIO_TABLE, null,
                MainDBConstants.estado + "= '" + '0' + "'", null, null, null, null, null);
        if (cEncSatUsuario != null && cEncSatUsuario.getCount() > 0) {
            cEncSatUsuario.moveToFirst();
            mEncSatUsu.clear();
            do{
                mEncSatUsu.add(EncuestaSatisfaccionUsuarioHelper.crearEncuestaSatisfaccionUsuario(cEncSatUsuario));
            } while (cEncSatUsuario.moveToNext());
        }
        cEncSatUsuario.close();
        return mEncSatUsu;
    }

    /*-------------------------------------------------------------*/

    public boolean bulkInsertCuestionarioEntoBySql(String tabla, List<?> list) throws Exception {
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = null;
            mDb.beginTransaction();
            switch (tabla) {
                case EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE:
                    stat = mDb.compileStatement(EntomologiaBConstants.INSERT_ENTO_CUESTIONARIO_HOGAR_TABLE);
                    for (Object remoteAppInfo : list) {
                        EntomologiaHelper.fillCuestionarioHogarStatement(stat, (CuestionarioHogar) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                case EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE:
                    stat = mDb.compileStatement(EntomologiaBConstants.INSERT_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE);
                    for (Object remoteAppInfo : list) {
                        EntomologiaHelper.fillCuestionarioPuntoClaveStatement(stat, (CuestionarioPuntoClave) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                default:
                    break;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    /*****FIN ENTOMOLOGIA****/
    public boolean bulkInsertMessageResourceBySql(List<MessageResource> list) throws Exception {
        if (null == list || list.size() <= 0) {
            return false;
        }
        //SQLiteDatabase db = null;
        try {
            //db = openHelper.getWritableDatabase();
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_MESSAGES_TABLE);
            mDb.beginTransaction();
            for (MessageResource remoteAppInfo : list) {
                MessageResourceHelper.fillMessageResourceStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertEstutiosBySql(List<Estudio> list) throws Exception {
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_ESTUDIO_TABLE);
            mDb.beginTransaction();
            for (Estudio remoteAppInfo : list) {
                EstudiosHelper.fillEstudioStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertBarriosBySql(List<Barrio> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_BARRIO_TABLE);
            mDb.beginTransaction();
            for (Barrio remoteAppInfo : list) {
                BarrioHelper.fillBarrioStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertPuntosCandidatosBySql(List<PuntoCandidato> list) throws Exception {
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_PUNTOS_CANDIDATOS_TABLE);
            mDb.beginTransaction();
            for (PuntoCandidato remoteAppInfo : list) {
                PuntoCandidatoHelper.fillPuntoCandidatoStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertRecepcionEnfermoBySql(List<RecepcionEnfermomessage> list) throws Exception {
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_RECEPCION_ENFERMO_TABLE);
            mDb.beginTransaction();
            for (RecepcionEnfermomessage remoteAppInfo : list) {
                RecepcionEnfermoHelper.fillRecepcionEnfermoStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertUsuariosBySql(String tabla, List<?> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            mDb.beginTransaction();
            SQLiteStatement stat = null;
            switch (tabla) {
                case MainDBConstants.USER_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_USER_TABLE);
                    for (Object remoteAppInfo : list) {
                        UserSistemaHelper.fillUserSistemaStatement(stat, (UserSistema) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                case MainDBConstants.ROLE_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_ROLE_TABLE);
                    for (Object remoteAppInfo : list) {
                        UserSistemaHelper.fillRoleStatement(stat, (Authority) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                case MainDBConstants.USER_PERM_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_USER_PERM_TABLE);
                    for (Object remoteAppInfo : list) {
                        UserSistemaHelper.fillUserPermissionStatement(stat, (UserPermissions) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                default:
                    break;
            }

            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public boolean bulkInsertCasasBySql(String tabla, List<?> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = null;
            mDb.beginTransaction();
            switch (tabla) {
                case MainDBConstants.CASA_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_CASA_TABLE);
                    for (Object remoteAppInfo : list) {
                        CasaHelper.fillCasaStatement(stat, (Casa) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }

                    break;
                case MainDBConstants.TELEFONO_CONTACTO_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_TELEFONO_CONTACTO_TABLE);
                    for (Object remoteAppInfo : list) {
                        TelefonoContactoHelper.fillTelefContactoStatement(stat, (TelefonoContacto) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                default: break;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean bulkInsertParticipantesBySql(String tabla, List<?> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = null;
            mDb.beginTransaction();
            switch (tabla) {
                case MainDBConstants.PARTICIPANTE_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_PARTICIPANTE_TABLE);
                    for (Object remoteAppInfo : list) {
                        ParticipanteHelper.fillParticipanteStatement(stat, (Participante) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                case MainDBConstants.PARTICIPANTE_PROC_TABLE:
                    stat = mDb.compileStatement(MainDBConstants.INSERT_PARTICIPANTE_PROC_TABLE);
                    for (Object remoteAppInfo : list) {
                        ParticipanteHelper.fillParticipanteProcesosStatement(stat, (ParticipanteProcesos) remoteAppInfo);
                        long result = stat.executeInsert();
                        if (result < 0) return false;
                    }
                    break;
                default:
                    break;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Boolean verificarData() throws SQLException{
        Cursor c = null;

        c = crearCursor(MainDBConstants.VISITA_PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.TAMIZAJE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.CASA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.PARTICIPANTE_PROC_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.MUESTRAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.RECEPCION_MUESTRA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.SEROLOGIA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.RAZON_NODATA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.PUNTOS_CANDIDATOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.OBSEQUIOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.ORDEN_LAB_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.MUESTRAS_ENFERMO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.RECEPCION_ENFERMO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c = crearCursor(MainDBConstants.CONTROL_ASISTENCIA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        return false;
    }

    public Boolean verificarDataEnto() throws SQLException{
        Cursor c = null;
        c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        return false;
    }

    /**
     * Metodos para cambios de domicilio en la base de datos
     *
     * @param cambioDomicilio
     *            Objeto cambioDomicilio que contiene la informacion
     *
     */
    //Crear nuevo cambio de domicilio en la base de datos
    public void crearCambioDomicilio(CambioDomicilio cambioDomicilio) {
        ContentValues cv = CambioDomicilioHelper.crearCambioDomicilioValues(cambioDomicilio);
        mDb.insertOrThrow(MainDBConstants.CAMBIO_DOMICILIO_TABLE, null, cv);
    }

    //Editar nuevo cambio de domicilio existente en la base de datos
    public boolean editarCambioDomicilio(CambioDomicilio cambioDomicilio) {
        ContentValues cv = CambioDomicilioHelper.crearCambioDomicilioValues(cambioDomicilio);
        return mDb.update(MainDBConstants.CAMBIO_DOMICILIO_TABLE , cv, MainDBConstants.id + "='"
                + cambioDomicilio.getId()+ "'", null) > 0;
    }
    //Editar nuevo control de asistencia existente en la base de datos
    public boolean editarControlAsistencia(ControlAsistencia controlAsistencia) {
        ContentValues cv = ControlAsistenciaHelper.crearControlAsistenciaContentValues(controlAsistencia);
        return mDb.update(MainDBConstants.CONTROL_ASISTENCIA_TABLE , cv, MainDBConstants.id + "='"
                + controlAsistencia.getId()+ "'", null) > 0;
    }
    //Editar nueva ADMISION en la base de datos
    public boolean editarAdmision(Admision admision) {
        ContentValues cv = AdmisionHelper.crearAdmisionContentValues(admision);
        return mDb.update(MainDBConstants.ADMISION_PACIENTES_TABLE , cv, MainDBConstants.id + "='"
                + admision.getId()+ "'", null) > 0;
    }
    //Editar nueva control de temperatura en la base de datos
    public boolean editarControlTemperatura(ControlTemperaturaTermo controlTemperaturaTermo) {
        ContentValues cv = ControlTemperaturaHelper.crearControlTemperaturaContentValues(controlTemperaturaTermo);
        return mDb.update(MainDBConstants.CONTROL_TEMPERATURA_TERMO_TABLE , cv, MainDBConstants.recordUser + "='"
                + controlTemperaturaTermo.getRecordUser() + "'", null) > 0;
    }
    //Editar nueva informe fin de dia medicos en la base de datos
    public boolean editarInformeFinDiaMedicos(InformeFindeDiaMedicos informeFindeDiaMedicos) {
        ContentValues cv = InformeFinDiaMedicosHelper.crearFinDiaMedicosValues(informeFindeDiaMedicos);
        return mDb.update(MainDBConstants.INFORME_FIN_DIA_MEDICOS_TABLE , cv, MainDBConstants.id + "='"
                + informeFindeDiaMedicos.getId()+ "'", null) > 0;
    }
    //Limpiar la tabla de Admision de la base de datos
    public boolean borrarAdmision() {
        return mDb.delete(MainDBConstants.ADMISION_PACIENTES_TABLE, null, null) > 0;
    }
    //Limpiar la tabla de cambio de domicilio de la base de datos
    public boolean borrarCambioDomicilio() {
        return mDb.delete(MainDBConstants.CAMBIO_DOMICILIO_TABLE, null, null) > 0;
    }

    public List<CambioDomicilio> getListaCambioDomicilioSinEnviar(String filtro, String orden) throws SQLException {
        List<CambioDomicilio> mCambiosDomicilio = new ArrayList<CambioDomicilio>();
        Cursor cursorCambiosDomicilio = crearCursor(MainDBConstants.CAMBIO_DOMICILIO_TABLE, filtro, null, orden);
        if (cursorCambiosDomicilio != null && cursorCambiosDomicilio.getCount() > 0) {
            cursorCambiosDomicilio.moveToFirst();
            mCambiosDomicilio.clear();
            do{
                CambioDomicilio mDatosCoordenadas = null;
                mDatosCoordenadas = CambioDomicilioHelper.crearCambioDomicilio(cursorCambiosDomicilio);
                Barrio barrio = this.getBarrio(MainDBConstants.codigo+"="+cursorCambiosDomicilio.getInt(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.barrio)), null);
                mDatosCoordenadas.setBarrio(barrio);
                mCambiosDomicilio.add(mDatosCoordenadas);
            } while (cursorCambiosDomicilio.moveToNext());
        }
        if (!cursorCambiosDomicilio.isClosed()) cursorCambiosDomicilio.close();
        return mCambiosDomicilio;
    }

    //Obtener un cambio de domicilio de la base de datos
    public CambioDomicilio getCambioDomicilio(String filtro, String orden) throws SQLException {
        CambioDomicilio mCambioDomicilio = null;
        Cursor cursorCambioDomicilio = crearCursor(MainDBConstants.CAMBIO_DOMICILIO_TABLE , filtro, null, orden);
        if (cursorCambioDomicilio != null && cursorCambioDomicilio.getCount() > 0) {
            cursorCambioDomicilio.moveToFirst();
            mCambioDomicilio=CambioDomicilioHelper.crearCambioDomicilio(cursorCambioDomicilio);
        }
        if (!cursorCambioDomicilio.isClosed()) cursorCambioDomicilio.close();
        return mCambioDomicilio;
    }

    public boolean bulkInsertCambioDomicilioBySql(List<CambioDomicilio> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CAMBIO_DOMICILIO_TABLE);
            mDb.beginTransaction();
            for (CambioDomicilio remoteAppInfo : list) {
                CambioDomicilioHelper.fillCambioDomicilioStatement(stat, remoteAppInfo);
                long result = stat.executeInsert();
                if (result < 0) return false;
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != mDb) {
                    mDb.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
