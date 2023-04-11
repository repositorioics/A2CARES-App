package ni.org.ics.a2cares.app.database.constants;

import java.util.Date;

import ni.org.ics.a2cares.app.domain.core.Participante;

/**
 * Created by Miguel Salinas on 16/4/2021.
 */
public class MainDBConstants {

    //Base de datos y tablas
    public static final String DATABASE_NAME = "a2carescryp.sqlite3";
    public static final int DATABASE_VERSION = 3;

    //Campos metadata
    public static final String recordDate = "recordDate";
    public static final String recordUser = "recordUser";
    public static final String pasive = "pasive";
    public static final String deviceId = "identificador_equipo";
    public static final String estado = "estado";


    //Tabla usuarios
    public static final String USER_TABLE = "users";
    //Campos usuarios
    public static final String username = "username";
    public static final String created = "created";
    public static final String modified = "modified";
    public static final String lastAccess = "lastaccess";
    public static final String password = "password";
    public static final String completeName = "completename";
    public static final String email = "email";
    public static final String enabled = "enabled";
    public static final String accountNonExpired = "accountnonexpired";
    public static final String credentialsNonExpired = "credentialsnonexpired";
    public static final String lastCredentialChange = "lastcredentialchange";
    public static final String accountNonLocked = "accountnonlocked";
    public static final String createdBy = "createdby";
    public static final String modifiedBy = "modifiedby";
    //Crear tabla usuarios
    public static final String CREATE_USER_TABLE = "create table if not exists "
            + USER_TABLE + " ("
            + username + " text not null, "
            + created + " date, "
            + modified + " date, "
            + lastAccess + " date, "
            + password + " text not null, "
            + completeName + " text, "
            + email + " text, "
            + enabled  + " boolean, "
            + accountNonExpired  + " boolean, "
            + credentialsNonExpired  + " boolean, "
            + lastCredentialChange + " date, "
            + accountNonLocked  + " boolean, "
            + createdBy + " text, "
            + modifiedBy + " text, "
            + "primary key (" + username + "));";

    public static final String INSERT_USER_TABLE = "insert into "
            + USER_TABLE + " ("
            + username + ","
            + created + ","
            + modified + ","
            + lastAccess + ","
            + password + ","
            + completeName + ","
            + email + ","
            + enabled  + ","
            + accountNonExpired  + ","
            + credentialsNonExpired  + ","
            + lastCredentialChange + ","
            + accountNonLocked  + ","
            + createdBy + ","
            + modifiedBy
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Tabla roles
    public static final String ROLE_TABLE = "roles";
    //Campos roles
    public static final String role = "role";
    //Crear tabla roles
    public static final String CREATE_ROLE_TABLE = "create table if not exists "
            + ROLE_TABLE + " ("
            + username + " text not null, "
            + role + " text not null, "
            + "primary key (" + username + "," + role + "));";

    public static final String INSERT_ROLE_TABLE = "insert into "
            + ROLE_TABLE + " ("
            + username + ","
            + role
            + ") values (?,?)";

    //Tabla permisos usuarios
    public static final String USER_PERM_TABLE = "usuarios_permisos";

    //Campos permisos usuarios
    public static final String USERNAME = "username";
    public static final String ENABLED = "enabled";
    public static final String PASSWORD = "password";
    public static final String U_MUESTRA = "muestra";
    public static final String U_ECASA = "ecasa";
    public static final String U_EPART = "eparticipante";
    public static final String U_ELACT = "elactancia";
    public static final String U_ESAT = "esatisfaccion";
    public static final String U_OBSEQUIO = "obsequio";
    public static final String U_PYT = "pesotalla";
    public static final String U_VAC = "vacunas";
    public static final String U_VISITA = "visitas";
    public static final String U_RECEPCION = "recepcion";
    public static final String U_CONS = "consentimiento";
    public static final String U_CASAZIKA = "casazika";
    public static final String U_TAMZIKA = "tamizajezika";
    public static final String U_PARTO = "datosparto";
    //Usuario encuesta satisfaccion usuario
    public static final String U_ENCSATUSUARIO = "encSatUsu";

    public static final String CREATE_USER_PERM_TABLE = "create table if not exists "
            + USER_PERM_TABLE + " ("
            + USERNAME + " text not null, "
            + U_MUESTRA + " boolean, "
            + U_ECASA + " boolean, "
            + U_EPART + " boolean, "
            + U_ELACT + " boolean, "
            + U_ESAT + " boolean, "
            + U_OBSEQUIO + " boolean, "
            + U_PYT + " boolean, "
            + U_VAC + " boolean, "
            + U_VISITA + " boolean, "
            + U_RECEPCION + " boolean, "
            + U_CONS + " boolean, "
            + U_CASAZIKA + " boolean, "
            + U_TAMZIKA + " boolean, "
            + U_PARTO + " boolean, "
            + U_ENCSATUSUARIO + " boolean, "
            + "primary key (" + USERNAME + "));";

    public static final String INSERT_USER_PERM_TABLE = "insert into "
            + USER_PERM_TABLE + " ("
            + USERNAME + ","
            + U_MUESTRA + ","
            + U_ECASA + ","
            + U_EPART + ","
            + U_ELACT + ","
            + U_ESAT + ","
            + U_OBSEQUIO + ","
            + U_PYT + ","
            + U_VAC + ","
            + U_VISITA + ","
            + U_RECEPCION + ","
            + U_CONS + ","
            + U_CASAZIKA + ","
            + U_TAMZIKA + ","
            + U_PARTO + ","
            + U_ENCSATUSUARIO
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Tabla mensajes
    public static final String MESSAGES_TABLE = "mensajes";
    //Campos mensajes
    public static final String messageKey = "messageKey";
    public static final String catRoot = "catRoot";
    public static final String catKey = "catKey";
    public static final String isCat = "isCat";
    public static final String order = "orden";
    public static final String spanish = "spanish";
    public static final String english = "english";

    //Crear tabla mensajes
    public static final String CREATE_MESSAGES_TABLE = "create table if not exists "
            + MESSAGES_TABLE + " ("
            + messageKey + " text not null, "
            + catRoot + " text , "
            + catKey + " text, "
            + isCat + " text , "
            + order + " integer , "
            + spanish + " text not null, "
            + english + " text , "
            + MainDBConstants.pasive + " text, "
            + "primary key (" + messageKey + "));";

    /**BULLCK INSERT**/
    public static final String INSERT_MESSAGES_TABLE = "insert into " + MESSAGES_TABLE + "("
            + messageKey + ","
            + catRoot + ","
            + catKey + ","
            + isCat + ","
            + order + ","
            + spanish + ","
            + english + ","
            + MainDBConstants.pasive
            + ") values(?,?,?,?,?,?,?,?)";

    //Tabla barrios
    public static final String BARRIO_TABLE = "barrios";
    //Campos barrios
    public static final String codigo = "codigo";
    public static final String nombre = "nombre";
    //Crear tabla barrios
    public static final String CREATE_BARRIO_TABLE = "create table if not exists "
            + BARRIO_TABLE + " ("
            + codigo + " integer not null, "
            + nombre + " text not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.estado + " text, "
            + MainDBConstants.deviceId + " text, "
            + "primary key (" + codigo + "));";

    public static final String INSERT_BARRIO_TABLE = "insert into "
            + BARRIO_TABLE + " ("
            + codigo + ","
            + nombre + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser + ","
            + MainDBConstants.pasive + ","
            + MainDBConstants.estado + ","
            + MainDBConstants.deviceId
            + ") values (?,?,?,?,?,?,?)";

    //Tabla estudios
    public static final String ESTUDIO_TABLE = "estudios";
    //Campos estudios
    //Crear tabla estudios
    public static final String CREATE_ESTUDIO_TABLE = "create table if not exists "
            + ESTUDIO_TABLE + " ("
            + codigo + " integer not null, "
            + nombre + " text not null, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.estado + " text, "
            + MainDBConstants.deviceId + " text, "
            + "primary key (" + codigo + "));";

    public static final String INSERT_ESTUDIO_TABLE = "insert into "
            + ESTUDIO_TABLE + " ("
            + codigo + ","
            + nombre + ","
            + MainDBConstants.recordDate + ","
            + MainDBConstants.recordUser +  ","
            + MainDBConstants.pasive +  ","
            + MainDBConstants.estado +  ","
            + MainDBConstants.deviceId
            + ") values (?,?,?,?,?,?,?)";

    //Tabla casas
    public static final String CASA_TABLE = "casas";
    //Campos casas
    public static final String barrio = "barrio";
    public static final String direccion = "direccion";
    public static final String manzana = "manzana";
    public static final String latitud = "latitud";
    public static final String longitud = "longitud";
    public static final String nombre1JefeFamilia = "nombre1JefeFamilia";
    public static final String nombre2JefeFamilia = "nombre2JefeFamilia";
    public static final String apellido1JefeFamilia = "apellido1JefeFamilia";
    public static final String apellido2JefeFamilia = "apellido2JefeFamilia";

    //Crear tabla casas
    public static final String CREATE_CASA_TABLE = "create table if not exists "
            + CASA_TABLE + " ("
            + codigo + " integer not null, "
            + barrio + " integer not null, "
            + direccion + " text not null, "
            + manzana + " text, "
            + latitud + " real, "
            + longitud + " real, "
            + nombre1JefeFamilia + " text not null, "
            + nombre2JefeFamilia + " text , "
            + apellido1JefeFamilia + " text not null, "
            + apellido2JefeFamilia + " text , "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    public static final String INSERT_CASA_TABLE = "insert into "
            + CASA_TABLE + " ("
            + codigo + ","
            + barrio + ","
            + direccion + ","
            + manzana + ","
            + latitud + ","
            + longitud + ","
            + nombre1JefeFamilia + ","
            + nombre2JefeFamilia + ","
            + apellido1JefeFamilia + ","
            + apellido2JefeFamilia + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Tabla Participantes
    public static final String PARTICIPANTE_TABLE = "participantes";

    // Campos Participantes
    public static final String nombre1 = "nombre1";
    public static final String nombre2 = "nombre2";
    public static final String apellido1 = "apellido1";
    public static final String apellido2 = "apellido2";
    public static final String sexo = "sexo";
    public static final String fechaNac = "fechaNac";
    public static final String nombre1Padre = "nombre1Padre";
    public static final String nombre2Padre = "nombre2Padre";
    public static final String apellido1Padre = "apellido1Padre";
    public static final String apellido2Padre = "apellido2Padre";
    public static final String nombre1Madre = "nombre1Madre";
    public static final String nombre2Madre = "nombre2Madre";
    public static final String apellido1Madre = "apellido1Madre";
    public static final String apellido2Madre = "apellido2Madre";
    public static final String casa = "casa";
    public static final String nombre1Tutor = "nombre1Tutor";
    public static final String nombre2Tutor = "nombre2Tutor";
    public static final String apellido1Tutor = "apellido1Tutor";
    public static final String apellido2Tutor = "apellido2Tutor";
    public static final String relacionFamiliarTutor = "relacionFamiliarTutor";

    //Crear tabla participantes
    public static final String CREATE_PARTICIPANTE_TABLE = "create table if not exists "
            + PARTICIPANTE_TABLE + " ("
            + codigo + " text not null, "
            + nombre1 + " text not null, "
            + nombre2 + " text, "
            + apellido1 + " text not null, "
            + apellido2 + " text, "
            + sexo + " text, "
            + fechaNac + " date, "
            + nombre1Padre + " text not null, "
            + nombre2Padre + " text, "
            + apellido1Padre + " text not null, "
            + apellido2Padre + " text, "
            + nombre1Madre + " text not null, "
            + nombre2Madre + " text, "
            + apellido1Madre + " text not null, "
            + apellido2Madre + " text, "
            + casa + " integer not null, "
            + nombre1Tutor + " text, "
            + nombre2Tutor + " text, "
            + apellido1Tutor + " text, "
            + apellido2Tutor + " text, "
            + relacionFamiliarTutor + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    public static final String INSERT_PARTICIPANTE_TABLE = "insert into " + PARTICIPANTE_TABLE + "("
            + codigo + ","
            + nombre1 + ","
            + nombre2 + ","
            + apellido1 + ","
            + apellido2 + ","
            + sexo + ","
            + fechaNac + ","
            + nombre1Padre + ","
            + nombre2Padre + ","
            + apellido1Padre + ","
            + apellido2Padre + ","
            + nombre1Madre + ","
            + nombre2Madre + ","
            + apellido1Madre + ","
            + apellido2Madre + ","
            + casa + ","
            + nombre1Tutor + ","
            + nombre2Tutor + ","
            + apellido1Tutor + ","
            + apellido2Tutor + ","
            + relacionFamiliarTutor + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    //Tabla Participantes
    public static final String PARTICIPANTE_PROC_TABLE = "participantes_procesos";

    // Campos Participantes
    public static final String retirado = "retirado";
    public static final String pendientePyT = "pendientePyT";
    public static final String pendienteEncPart = "pendienteEncPart";
    public static final String pendienteEnCasa = "pendienteEnCasa";
    public static final String pendienteMxMA = "pendienteMxMA";
    public static final String pendienteMxTx = "pendienteMxTx";
    public static final String pendienteObseq = "pendienteObseq";
    public static final String pendienteEsatUsuario = "esatUsuario";

    //Crear tabla participantes
    public static final String CREATE_PARTICIPANTE_PROC_TALBE = "create table if not exists "
            + PARTICIPANTE_PROC_TABLE + " ("
            + codigo + " text not null, "
            + retirado + " text, "
            + pendientePyT + " text, "
            + pendienteEncPart + " text, "
            + pendienteEnCasa + " text, "
            + pendienteMxMA + " text, "
            + pendienteMxTx + " text, "
            + pendienteObseq + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + pendienteEsatUsuario + " text, "
            + "primary key (" + codigo + "));";

    public static final String INSERT_PARTICIPANTE_PROC_TABLE = "insert into " + PARTICIPANTE_PROC_TABLE + "("
            + codigo + ","
            + retirado + ","
            + pendientePyT + ","
            + pendienteEncPart + ","
            + pendienteEnCasa + ","
            + pendienteMxMA + ","
            + pendienteMxTx + ","
            + pendienteObseq + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado + ","
            + pendienteEsatUsuario
            + ") " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    // Tabla Tamizaje
    public static final String TAMIZAJE_TABLE = "tamizajes";

    //Campos tabla tamizaje
    public static final String fechaNacimiento = "fechaNacimiento";
    public static final String aceptaTamizajePersona = "aceptaTamizajePersona";
    public static final String razonNoAceptaTamizajePersona = "razonNoAceptaTamizajePersona";
    public static final String otraRazonNoAceptaTamizajePersona = "otraRazonNoAceptaTamizajePersona";
    public static final String estudio = "estudio";
    public static final String tiempoResidencia = "tiempoResidencia";
    public static final String tipoVivienda = "tipoVivienda";
    public static final String planesMudarse = "planesMudarse";
    public static final String asentimientoVerbal = "asentimientoVerbal";
    public static final String aceptaParticipar = "aceptaParticipar";
    public static final String razonNoAceptaParticipar = "razonNoAceptaParticipar";
    public static final String otraRazonNoAceptaParticipar = "otraRazonNoAceptaParticipar";
    public static final String esElegible = "esElegible";

    //crear tabla tamizaje
    public static final String CREATE_TAMIZAJE_TABLE = "create table if not exists "
            + TAMIZAJE_TABLE + " ("
            + codigo + " text not null, "
            + estudio + " integer not null, "
            + sexo + " text, "
            + fechaNacimiento + " date, "
            + aceptaTamizajePersona + " text, "
            + razonNoAceptaTamizajePersona + " text, "
            + otraRazonNoAceptaTamizajePersona + " text, "
            + tipoVivienda + " text, "
            + tiempoResidencia + " text, "
            + planesMudarse + " text, "
            + asentimientoVerbal + " text, "
            + aceptaParticipar + " text, "
            + razonNoAceptaParticipar + " text, "
            + otraRazonNoAceptaParticipar + " text, "
            + esElegible + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla Carta consentimiento
    public static final String CARTA_CONSENTIMIENTO_TABLE = "cartas_consentimientos";
    //Campos cartas_consentimientos
    public static final String fechaFirma = "fechaFirma";
    public static final String tamizaje = "tamizaje";
    public static final String participante= "participante";
    public static final String participanteOTutorAlfabeto = "participanteOTutorAlfabeto";
    public static final String testigoPresente = "testigoPresente";
    public static final String nombre1Testigo = "nombre1Testigo";
    public static final String nombre2Testigo = "nombre2Testigo";
    public static final String apellido1Testigo = "apellido1Testigo";
    public static final String apellido2Testigo = "apellido2Testigo";
    public static final String aceptaParteA = "aceptaParteA";
    public static final String motivoRechazoParteA = "motivoRechazoParteA";
    public static final String otroMotivoRechazoParteA = "otroMotivoRechazoParteA";
    public static final String aceptaContactoFuturo = "aceptaContactoFuturo";
    public static final String aceptaParteB = "aceptaParteB";
    public static final String aceptaParteC = "aceptaParteC";
    public static final String version = "version"; //Indicar la versión actual al momento de registrar la carta
    public static final String otraRelacionFamTutor = "otraRelacionFamTutor";
    public static final String verifTutor = "verifTutor";

    //crear tabla cartas_consentimientos
    public static final String CREATE_CARTACONSENTIMIENTO_TABLE = "create table if not exists "
            + CARTA_CONSENTIMIENTO_TABLE + " ("
            + codigo + " text not null, "
            + fechaFirma + " date not null, "
            + tamizaje + " text not null, "
            + participante + " text, "
            + nombre1Tutor + " text, "
            + nombre2Tutor + " text, "
            + apellido1Tutor + " text, "
            + apellido2Tutor + " text, "
            + relacionFamiliarTutor + " text, "
            + otraRelacionFamTutor + " text, "
            + participanteOTutorAlfabeto + " text, "
            + testigoPresente + " text, "
            + nombre1Testigo + " text, "
            + nombre2Testigo + " text, "
            + apellido1Testigo + " text, "
            + apellido2Testigo + " text, "
            + aceptaParteA + " text, "
            + motivoRechazoParteA + " text, "
            + otroMotivoRechazoParteA + " text, "
            + aceptaContactoFuturo + " text, "
            + aceptaParteB + " text, "
            + aceptaParteC + " text, "
            + version + " text, "
            + verifTutor + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla TelefonoContacto
    public static final String TELEFONO_CONTACTO_TABLE = "telefonos_contacto";

    //Campos tabla telefonocontacto
    public static final String id = "id";
    public static final String numero = "numero";
    public static final String operadora = "operadora";
    public static final String tipotel = "tipotel";

    //crear tabla telefonocontacto
    public static final String CREATE_TELEFONO_CONTACTO_TABLE = "create table if not exists "
            + TELEFONO_CONTACTO_TABLE + " ("
            + id + " text not null, "
            + numero + " text not null, "
            + operadora + " text, "
            + tipotel + " text, "
            + casa + " integer, "
            + participante + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";

    public static final String INSERT_TELEFONO_CONTACTO_TABLE = "insert into "
            + TELEFONO_CONTACTO_TABLE + " ("
            + id + ","
            + numero + ","
            + operadora + ","
            + tipotel + ","
            + casa + ","
            + participante + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?)";


    //Tabla DatosCoordenadas
    public static final String DATOS_COORDENADAS_TABLE = "datos_coordenadas";

    //Campos CambioDomicilio
    public static final String estudios = "estudios";
    public static final String motivo = "motivo";
    public static final String conpunto = "conpunto";
    public static final String otroBarrio = "otroBarrio";
    public static final String puntoGps = "puntoGps";
    public static final String razonNoGeoref = "razonNoGeoref";
    public static final String otraRazonNoGeoref = "otraRazonNoGeoref";
    public static final String actual = "actual";
    public static final String observacion = "observacion";

    //Crear tabla casas
    public static final String CREATE_DATOS_COORDENADAS_TABLE = "create table if not exists "
            + DATOS_COORDENADAS_TABLE + " ("
            + codigo + " text not null, "
            + casa + " text, "
            + estudios + " text, "
            + participante + " text, "
            + motivo + " text, "
            + barrio + " integer, "
            + otroBarrio + " text, "
            + direccion + " text, "
            + manzana + " text, "
            + conpunto + " text, "
            + latitud + " real, "
            + longitud + " real, "
            + puntoGps + " text, "
            + razonNoGeoref + " text, "
            + otraRazonNoGeoref + " text, "
            + actual + " boolean, "
            + observacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo + "));";

    //Tabla Visitas
    public static final String VISITA_PARTICIPANTE_TABLE = "visitas_participante";

    //Campos tabla Visitas
    public static final String codigoVisita = "codigoVisita";
    public static final String fechaVisita = "fechaVisita";
    public static final String visitaExitosa = "visitaExitosa";
    public static final String razonVisitaNoExitosa = "razonVisitaNoExitosa";
    public static final String otraRazonVisitaNoExitosa = "otraRazonVisitaNoExitosa";

    //crear tabla Visitas
    public static final String CREATE_VISITA_TABLE = "create table if not exists "
            + VISITA_PARTICIPANTE_TABLE + " ("
            + codigoVisita + " text not null, "
            + fechaVisita + " date not null, "
            + visitaExitosa + " text, "
            + participante + " text not null, "
            + razonVisitaNoExitosa + " text, "
            + otraRazonVisitaNoExitosa + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigoVisita + "));";


    //Tabla muestras
    public static final String MUESTRAS_TABLE = "muestras";

    //Campos Tabla Visitas
    public static final String idMuestra  = "idMuestra";
    public static final String fechaMuestra  = "fechaMuestra";
    public static final String tuboBHC  = "tuboBHC";
    public static final String codigoBHC  = "codigoBHC";
    public static final String volumenBHC  = "volumenBHC";
    public static final String razonNoBHC  = "razonNoBHC";
    public static final String otraRazonNoBHC  = "otraRazonNoBHC";
    public static final String tuboRojo  = "tuboRojo";
    public static final String codigoRojo  = "codigoRojo";
    public static final String volumenRojo  = "volumenRojo";
    public static final String razonNoRojo  = "razonNoRojo";
    public static final String otraRazonNoRojo  = "otraRazonNoRojo";
    public static final String terreno  = "terreno";
    public static final String pinchazos  = "pinchazos";
    public static final String estudiosAct  = "estudiosAct";
    public static final String proposito  = "proposito";
    public static final String descOtraObservacion = "descOtraObservacion";

    //crear tabla Visitas
    public static final String CREATE_MUESTRAS_TABLE = "create table if not exists "
            + MUESTRAS_TABLE + " ("
            + idMuestra + " text not null, "
            + participante + " text not null, "
            + fechaMuestra + " date not null, "
            + tuboBHC + " text, "
            + codigoBHC + " text, "
            + volumenBHC + " real, "
            + razonNoBHC + " text, "
            + otraRazonNoBHC + " text, "
            + tuboRojo + " text, "
            + codigoRojo + " text, "
            + volumenRojo + " real, "
            + razonNoRojo + " text, "
            + otraRazonNoRojo + " text, "
            + terreno + " text, "
            + pinchazos + " text, "
            + observacion + " text, "
            + descOtraObservacion + " text, "
            + estudiosAct + " text, "
            + proposito + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + idMuestra + "));";

    //Tabla Recepción de muestras supervisor
    public static final String RECEPCION_MUESTRA_TABLE = "recepcion_muestras";

    //campos recepcion de muestras
    public static final String idRecepcion = "idRecepcion";
    public static final String fechaRecepcion = "fechaRecepcion";
    public static final String lugar = "lugar";
    public static final String volumen  = "volumen";
    public static final String tipoTubo  = "tipoTubo";
    public static final String codigoMx  = "codigoMx";

    //Crear tabla Recepción de muestra
    public static final String CREATE_RECEPCION_MUESTRA_TABLE = "create table if not exists "
            + RECEPCION_MUESTRA_TABLE + " ("
            + idRecepcion + " text not null, "
            + codigoMx + " text not null, "
            + fechaRecepcion + " date not null, "
            + volumen + " real null, "
            + lugar + " text, "
            + observacion + " text, "
            + tipoTubo + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + idRecepcion +"));";

    //Tabla Recepción de muestras laboratorio
    public static final String SEROLOGIA_TABLE = "serologia";

    public static final String idSerologia = "idSerologia";
    public static final String fecha = "fecha";
    public static final String enviado = "enviado";
    public static final String codigoCasa = "codigoCasa";
    public static final String edadMeses = "edadMeses";
    public static final String descripcion = "descripcion";

    //Crear tabla Recepción de muestra
    public static final String CREATE_SEROLOGIA_TABLE = "create table if not exists "
            + SEROLOGIA_TABLE + " ("
            + idSerologia + " integer null, "
            + participante + " text not null, "
            + fecha + " date not null, "
            + volumen + " real null, "
            + observacion + " text, "
            + enviado + " text, "
            + codigoCasa + " integer, "
            + edadMeses + " integer, "
            + descripcion + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + participante + ","+ fecha +"));";

    //Tabla Recepción de muestras laboratorio
    public static final String RAZON_NODATA_TABLE = "razones_datos_pendientes";

    public static final String razon = "razon";
    public static final String otraRazon = "otraRazon";

    //Crear tabla Recepción de muestra
    public static final String CREATE_RAZON_NODATA_TABLE = "create table if not exists "
            + RAZON_NODATA_TABLE + " ("
            + id + " integer, "
            + codigo + " text not null, "
            + razon + " text, "
            + otraRazon + " text, "
            + MainDBConstants.recordDate + " date, "
            + MainDBConstants.recordUser + " text, "
            + MainDBConstants.pasive + " text, "
            + MainDBConstants.deviceId + " text, "
            + MainDBConstants.estado + " text not null, "
            + "primary key (" + codigo + ","+ recordDate +"));";

    //Tabla puntos gps candidatos para ingresos
    public static final String PUNTOS_CANDIDATOS_TABLE = "puntos_candidatos";

    public static final String descartado = "descartado";
    public static final String razonDescarte = "razonDescarte";
    public static final String otraRazonDescarte = "otraRazonDescarte";
    public static final String fechaDescarte = "fechaDescarte";
    public static final String usuarioDescarte = "usuarioDescarte";

    //Crear tabla untos gps candidatos para ingresos
    public static final String CREATE_PUNTOS_CANDIDATOS_TABLE = "create table if not exists "
            + PUNTOS_CANDIDATOS_TABLE + " ("
            + codigo + " integer not null, "
            + barrio + " text, "
            + latitud + " real not null, "
            + longitud + " real not null, "
            + descartado + " text, "
            + razonDescarte + " text, "
            + otraRazonDescarte + " text, "
            + fechaDescarte + " date, "
            + usuarioDescarte + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + codigo +"));";

    public static final String INSERT_PUNTOS_CANDIDATOS_TABLE = "insert into "
            + PUNTOS_CANDIDATOS_TABLE + " ("
            + codigo + ","
            + barrio + ","
            + latitud + ","
            + longitud + ","
            + descartado + ", "
            + razonDescarte + ", "
            + otraRazonDescarte + ", "
            + fechaDescarte + ", "
            + usuarioDescarte + ", "
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String seguimiento="seguimiento";
    public static final String numVisitaSeguimiento="numVisitaSeguimiento";
    public static final String entregoObsequio="entregoObsequio";
    public static final String personaRecibe="personaRecibe";

    public static final String OBSEQUIOS_TABLE = "obsequios";

    public static final String CREATE_OBSEQUIOS_TABLE = "create table if not exists "
            + OBSEQUIOS_TABLE + " ("
            + id + " text not null, "
            + participante + " text, "
            + casa + " text, "
            + motivo + " text, "
            + entregoObsequio + " text, "
            + personaRecibe + " text, "
            + observacion + " text, "
            + seguimiento + " text, "
            + numVisitaSeguimiento + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";

    //Tabla ordenes de laboratorio para muestras de enfermos
    public static final String ORDEN_LAB_TABLE = "orden_laboratorio";

    //Campos tabla ordenes de laboratorio para muestras de enfermos
    public static final String idOrden="idOrden";
    public static final String fechaOrden="fechaOrden";
    public static final String tipoOrden="tipoOrden";
    public static final String fis="fis";
    public static final String fif="fif";
    public static final String categoria="categoria";
    public static final String consulta="consulta";
    public static final String tipoMuestra="tipoMuestra";

    //Crear tabla ordenes de laboratorio para muestras de enfermos
    public static final String CREATE_ORDEN_LAB_TABLE = "create table if not exists "
            + ORDEN_LAB_TABLE + " ("
            + idOrden + " text not null, "
            + participante + " text, "
            + fechaOrden + " date, "
            + tipoOrden + " text, "
            + fis + " date, "
            + fif + " date, "
            + categoria + " text, "
            + consulta + " text, "
            + tipoMuestra + " text, "
            + estudiosAct + " text, "
            + observacion + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + idOrden + "));";

    //Tabla muestras de enfermos
    public static final String MUESTRAS_ENFERMO_TABLE = "muestras_enfermo";

    //Campos tabla muestras de enfermoe
    public static final String horaMuestra  = "horaMuestra";

    //Crear tabla muestras de enfermos
    public static final String CREATE_MUESTRAS_ENFERMO_TABLE = "create table if not exists "
            + MUESTRAS_ENFERMO_TABLE + " ("
            + idMuestra + " text not null, "
            + participante + " text, "
            + fechaMuestra + " date, "
            + horaMuestra + " text, "
            + tipoTubo + " text, "
            + volumen + " real, "
            + observacion + " text, "
            + fis + " date, "
            + fif + " date, "
            + categoria + " text, "
            + consulta + " text, "
            + tipoMuestra + " text, "
            + estudiosAct + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + idMuestra + "));";

    //Tabla muestras de enfermos
    public static final String RECEPCION_ENFERMO_TABLE = "recepciones_enfermo";

    //Crear tabla muestras de enfermos
    public static final String CREATE_RECEPCION_ENFERMO_TABLE = "create table if not exists "
            + RECEPCION_ENFERMO_TABLE + " ("
            + idRecepcion + " text not null, "
            + participante + " text, "
            + fechaRecepcion + " date, "
            + tipoTubo + " text, "
            + volumen + " real, "
            + observacion + " text, "
            + fis + " date, "
            + fif + " date, "
            + categoria + " text, "
            + consulta + " text, "
            + tipoMuestra + " text, "
            + estudiosAct + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + idRecepcion + "));";


}
