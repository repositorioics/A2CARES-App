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
import ni.org.ics.a2cares.app.database.helpers.BarrioHelper;
import ni.org.ics.a2cares.app.database.helpers.CartaConsentimientoHelper;
import ni.org.ics.a2cares.app.database.helpers.CasaHelper;
import ni.org.ics.a2cares.app.database.helpers.DatosCoordenadasHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaCasaHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaParticipanteHelper;
import ni.org.ics.a2cares.app.database.helpers.EncuestaPesoTallaHelper;
import ni.org.ics.a2cares.app.database.helpers.EstudiosHelper;
import ni.org.ics.a2cares.app.database.helpers.MessageResourceHelper;
import ni.org.ics.a2cares.app.database.helpers.MuestraHelper;
import ni.org.ics.a2cares.app.database.helpers.ParticipanteHelper;
import ni.org.ics.a2cares.app.database.helpers.TamizajeHelper;
import ni.org.ics.a2cares.app.database.helpers.TelefonoContactoHelper;
import ni.org.ics.a2cares.app.database.helpers.UserSistemaHelper;
import ni.org.ics.a2cares.app.database.helpers.VisitaTerrenoHelper;
import ni.org.ics.a2cares.app.domain.core.Barrio;
import ni.org.ics.a2cares.app.domain.core.CartaConsentimiento;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.DatosCoordenadas;
import ni.org.ics.a2cares.app.domain.core.Estudio;
import ni.org.ics.a2cares.app.domain.core.Muestra;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.ParticipanteProcesos;
import ni.org.ics.a2cares.app.domain.core.Tamizaje;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.domain.survey.EncuestaCasa;
import ni.org.ics.a2cares.app.domain.survey.EncuestaParticipante;
import ni.org.ics.a2cares.app.domain.survey.EncuestaPesoTalla;
import ni.org.ics.a2cares.app.domain.users.Authority;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.domain.users.UserSistema;
import ni.org.ics.a2cares.app.domain.visita.VisitaTerrenoParticipante;
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
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TALBE);
            db.execSQL(MainDBConstants.CREATE_DATOS_COORDENADAS_TABLE);
            db.execSQL(MainDBConstants.CREATE_TELEFONO_CONTACTO_TABLE);
            db.execSQL(MainDBConstants.CREATE_VISITA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PESOTALLA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_CASA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PART_TABLE);
            db.execSQL(MainDBConstants.CREATE_MUESTRAS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
            if(oldVersion==0) return;
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
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
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
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE , cv, MainDBConstants.codigo + "="
                + participante.getCodigo(), null) > 0;
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

            //ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
            //mParticipante.setProcesos(procesos);

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

                //ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                //mParticipante.setProcesos(procesos);

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

                /*ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                if (procesos != null && procesos.getEstPart()==1) {
                    mParticipante.setProcesos(procesos);
                    mParticipantes.add(mParticipante);
                }*/
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

                //ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                //mParticipante.setProcesos(procesos);

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

                //ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                //mParticipante.setProcesos(procesos);

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
                Participante part = this.getParticipante(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.participante)), null);
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
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursorVisitaTerreno.getInt(cursorVisitaTerreno.getColumnIndex(MainDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursorVisitasTerreno.getInt(cursorVisitasTerreno.getColumnIndex(MainDBConstants.participante)), null);
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
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCambiosDomicilio.getInt(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.participante)), null);
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
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaParticipante.getParticipante().getCodigo(), null) > 0;
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
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
                if (participante != null) mEncuesta.setParticipante(participante);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
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
            Participante participante = this.getParticipante(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)), null);
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
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(MainDBConstants.participante)), null);
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
        return mDb.update(MainDBConstants.PARTICIPANTE_PROC_TABLE, cv, MainDBConstants.codigo + "="
                + participante.getCodigo(), null) > 0;
    }

    //Limpiar la tabla de Muestras de la base de datos
    public boolean deleteParticipanteProcesos() {
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

    public boolean bulkInsertCasasBySql(List<Casa> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CASA_TABLE);
            mDb.beginTransaction();
            for (Casa remoteAppInfo : list) {
                CasaHelper.fillCasaStatement(stat, remoteAppInfo);
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

    public boolean bulkInsertParticipantesBySql(List<Participante> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_PARTICIPANTE_TABLE);
            mDb.beginTransaction();
            for (Participante remoteAppInfo : list) {
                ParticipanteHelper.fillParticipanteStatement(stat, remoteAppInfo);
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

    public boolean bulkInsertTelefonosBySql(List<TelefonoContacto> list) throws Exception{
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_TELEFONO_CONTACTO_TABLE);
            mDb.beginTransaction();
            for (TelefonoContacto remoteAppInfo : list) {
                TelefonoContactoHelper.fillTelefContactoStatement(stat, remoteAppInfo);
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
