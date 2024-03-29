package ni.org.ics.a2cares.app.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteStatement;

import java.util.Date;

import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.users.Authority;
import ni.org.ics.a2cares.app.domain.users.AuthorityId;
import ni.org.ics.a2cares.app.domain.users.UserPermissions;
import ni.org.ics.a2cares.app.domain.users.UserSistema;

/**
 * Created by Miguel Salinas on 16/4/2021.
 */
public class UserSistemaHelper extends BindStatementHelper {
    public static ContentValues crearUserSistemaContentValues(UserSistema user){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.username, user.getUsername());
        if (user.getCreated() != null) cv.put(MainDBConstants.created, user.getCreated().getTime());
        if (user.getModified() != null) cv.put(MainDBConstants.modified, user.getModified().getTime());
        if (user.getLastAccess() != null) cv.put(MainDBConstants.lastAccess, user.getLastAccess().getTime());
        cv.put(MainDBConstants.password, user.getPassword());
        cv.put(MainDBConstants.completeName, user.getCompleteName());
        cv.put(MainDBConstants.email, user.getEmail());
        cv.put(MainDBConstants.enabled, user.getEnabled());
        cv.put(MainDBConstants.accountNonExpired, user.getAccountNonExpired());
        cv.put(MainDBConstants.credentialsNonExpired, user.getCredentialsNonExpired());
        if (user.getLastCredentialChange() != null) cv.put(MainDBConstants.lastCredentialChange, user.getLastCredentialChange().getTime());
        cv.put(MainDBConstants.accountNonLocked, user.getAccountNonLocked());
        cv.put(MainDBConstants.createdBy, user.getCreatedBy());
        cv.put(MainDBConstants.modifiedBy, user.getModifiedBy());
        return cv;
    }

    public static UserSistema crearUserSistema(Cursor cursorUser){

        UserSistema mUser = new UserSistema();
        mUser.setUsername(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.username)));
        if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.created))>0) mUser.setCreated(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.created))));
        if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.modified))>0) mUser.setModified(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.modified))));
        if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastAccess))>0) mUser.setLastAccess(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastAccess))));
        mUser.setPassword(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.password)));
        mUser.setCompleteName(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.completeName)));
        mUser.setEmail(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.email)));
        mUser.setEnabled(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.enabled))>0);
        mUser.setAccountNonExpired(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.accountNonExpired))>0);
        mUser.setCredentialsNonExpired(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.credentialsNonExpired))>0);
        if(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastCredentialChange))>0) mUser.setLastCredentialChange(new Date(cursorUser.getLong(cursorUser.getColumnIndex(MainDBConstants.lastCredentialChange))));
        mUser.setAccountNonLocked(cursorUser.getInt(cursorUser.getColumnIndex(MainDBConstants.accountNonLocked))>0);
        mUser.setCreatedBy(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.createdBy)));
        mUser.setModifiedBy(cursorUser.getString(cursorUser.getColumnIndex(MainDBConstants.modifiedBy)));
        return mUser;
    }

    public static ContentValues crearRolValues(Authority rol){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.username, rol.getAuthId().getUsername());
        cv.put(MainDBConstants.role, rol.getAuthId().getAuthority());
        return cv;
    }

    public static Authority crearRol(Cursor cursorRol){

        Authority mRol = new Authority();
        mRol.setAuthId(new AuthorityId(cursorRol.getString(cursorRol.getColumnIndex(MainDBConstants.username)),cursorRol.getString(cursorRol.getColumnIndex(MainDBConstants.role))));
        return mRol;
    }

    /**
     * Inserta un usuario en la base de datos
     *
     * @param userPermissions
     *            Objeto UserPermissions que contiene la informacion
     *
     */
    public static ContentValues crearPermisosUsuario(UserPermissions userPermissions) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.USERNAME, userPermissions.getUsername());
        cv.put(MainDBConstants.U_ECASA, userPermissions.getEncuestaCasa());
        cv.put(MainDBConstants.U_EPART, userPermissions.getEncuestaParticipante());
        cv.put(MainDBConstants.U_ELACT, userPermissions.getEncuestaLactancia());
        cv.put(MainDBConstants.U_ESAT, userPermissions.getEncuestaSatisfaccion());
        cv.put(MainDBConstants.U_MUESTRA, userPermissions.getMuestra());
        cv.put(MainDBConstants.U_OBSEQUIO, userPermissions.getObsequio());
        cv.put(MainDBConstants.U_PYT, userPermissions.getPesoTalla());
        cv.put(MainDBConstants.U_VAC, userPermissions.getVacunas());
        cv.put(MainDBConstants.U_VISITA, userPermissions.getVisitas());
        cv.put(MainDBConstants.U_RECEPCION, userPermissions.getRecepcion());
        cv.put(MainDBConstants.U_CONS, userPermissions.getConsentimiento());
        cv.put(MainDBConstants.U_CASAZIKA, userPermissions.getCasazika());
        cv.put(MainDBConstants.U_TAMZIKA, userPermissions.getTamizajezika());
        cv.put(MainDBConstants.U_PARTO, userPermissions.getDatosparto());
        cv.put(MainDBConstants.U_ENCSATUSUARIO, userPermissions.getEncSatUsu());
        return cv;
    }

    /**
     * Obtiene un usuario
     *
     * @return User
     */
    public static UserPermissions crearUserPermissions(Cursor usuarios) {
        UserPermissions mUser = new UserPermissions();
        mUser.setUsername(usuarios.getString(usuarios.getColumnIndex(MainDBConstants.username)));
        Boolean enCasa = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_ECASA)) > 0;
        Boolean enPart = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_EPART)) > 0;
        Boolean enLact = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_ELACT)) > 0;
        Boolean pesoTalla = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_PYT)) > 0;
        Boolean muestra = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_MUESTRA)) > 0;
        Boolean obsequio = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_OBSEQUIO)) > 0;
        Boolean vacuna = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_VAC)) > 0;
        Boolean terreno = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_VISITA)) > 0;
        Boolean recepcion = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_RECEPCION)) > 0;
        Boolean consentimiento = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_CONS)) > 0;
        Boolean tamizajezika = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_TAMZIKA)) > 0;
        Boolean casazika = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_CASAZIKA)) > 0;
        Boolean parto = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_PARTO)) > 0;
        Boolean enSatUsuario = usuarios.getInt(usuarios.getColumnIndex(MainDBConstants.U_ENCSATUSUARIO)) > 0;
        mUser.setEncuestaCasa(enCasa);
        mUser.setEncuestaParticipante(enPart);
        mUser.setEncuestaLactancia(enLact);
        mUser.setPesoTalla(pesoTalla);
        mUser.setMuestra(muestra);
        mUser.setObsequio(obsequio);
        mUser.setVacunas(vacuna);
        mUser.setVisitas(terreno);
        mUser.setRecepcion(recepcion);
        mUser.setConsentimiento(consentimiento);
        mUser.setTamizajezika(tamizajezika);
        mUser.setCasazika(casazika);
        mUser.setDatosparto(parto);
        mUser.setEncSatUsu(enSatUsuario);
        return mUser;
    }

    public static void fillUserSistemaStatement(SQLiteStatement stat, UserSistema userSistema) {
        stat.bindString(1, userSistema.getUsername());
        bindDate(stat, 2, userSistema.getCreated());
        bindDate(stat, 3, userSistema.getModified());
        stat.bindString(4, String.valueOf(userSistema.getLastAccess()));
        bindString(stat,5, userSistema.getPassword());
        bindString(stat,6, userSistema.getCompleteName());
        bindString(stat, 7, userSistema.getEmail());
        stat.bindString(8, String.valueOf(userSistema.getEnabled()));
        stat.bindLong(9, (userSistema.getAccountNonExpired()? 1 : 0));
        stat.bindLong(10, (userSistema.getCredentialsNonExpired()? 1 : 0));
        stat.bindLong(11, (userSistema.getAccountNonLocked()? 1 : 0));
        bindString(stat,12, userSistema.getCreatedBy());
        bindString(stat,13, userSistema.getModifiedBy());
    }

    public static void fillRoleStatement(SQLiteStatement stat, Authority remoteappinfo) {
        bindString(stat,1, remoteappinfo.getAuthId().getUsername());
        bindString(stat,2, remoteappinfo.getAuthId().getAuthority());
    }

    public static void fillUserPermissionStatement(SQLiteStatement stat, UserPermissions userPermissions) {
        stat.bindString(1, userPermissions.getUsername());
        stat.bindLong(2, (userPermissions.getMuestra()?1:0));
        stat.bindLong(3, (userPermissions.getEncuestaCasa()?1:0));
        stat.bindLong(4, (userPermissions.getEncuestaParticipante()?1:0));
        stat.bindLong(5, (userPermissions.getEncuestaLactancia()?1:0));
        stat.bindLong(6, (userPermissions.getEncuestaSatisfaccion()?1:0));
        stat.bindLong(7, (userPermissions.getObsequio()?1:0));
        stat.bindLong(8, (userPermissions.getPesoTalla()?1:0));
        stat.bindLong(9, (userPermissions.getVacunas()?1:0));
        stat.bindLong(10, (userPermissions.getVisitas()?1:0));
        stat.bindLong(11, (userPermissions.getRecepcion()?1:0));
        stat.bindLong(12, (userPermissions.getConsentimiento()?1:0));
        stat.bindLong(13, (userPermissions.getCasazika()?1:0));
        stat.bindLong(14, (userPermissions.getTamizajezika()?1:0));
        stat.bindLong(15, (userPermissions.getDatosparto()?1:0));
        stat.bindLong(16, (userPermissions.getEncSatUsu()?1:0));
    }

}
