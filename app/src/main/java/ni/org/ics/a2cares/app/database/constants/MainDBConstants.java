package ni.org.ics.a2cares.app.database.constants;

import java.util.Date;

import ni.org.ics.a2cares.app.domain.core.Participante;

/**
 * Created by Miguel Salinas on 16/4/2021.
 */
public class MainDBConstants {

    //Base de datos y tablas
    public static final String DATABASE_NAME = "a2carescryp.sqlite3";
    public static final int DATABASE_VERSION = 5;

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
    public static final String U_RECONSENTIMIENTO = "reconsent";

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
            + U_RECONSENTIMIENTO + " boolean, "
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
            + U_ENCSATUSUARIO + ","
            + U_RECONSENTIMIENTO
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
    public static final String pendienteReconsentir = "reconsent";

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
            + pendienteReconsentir + " text, "
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
            + pendienteEsatUsuario + ","
            + pendienteReconsentir
            + ") " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
    public static final String direccion_cambio_domicilio = "direccion_cambio_domicilio";
    public static final String telefono_cambio_domicilio = "telefono_cambio_domicilio";
    public static final String telefono_1_actualizado = "telefono_1_actualizado";
    public static final String telefono_2_actualizado = "telefono_2_actualizado";

    //crear tabla Visitas
    public static final String CREATE_VISITA_TABLE = "create table if not exists "
            + VISITA_PARTICIPANTE_TABLE + " ("
            + codigoVisita + " text not null, "
            + fechaVisita + " date not null, "
            + visitaExitosa + " text, "
            + participante + " text not null, "
            + razonVisitaNoExitosa + " text, "
            + otraRazonVisitaNoExitosa + " text, "
            + direccion_cambio_domicilio + " text, "
            + telefono_cambio_domicilio + " text, "
            + telefono_1_actualizado + " text, "
            + telefono_2_actualizado + " text, "
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
    //public static final String identificador_Equipo = "identificador_Equipo";
    public static final String RECEPCION_ENFERMO_TABLE = "recepciones_enfermo";

    //Crear tabla muestras de enfermos
    public static final String CREATE_RECEPCION_ENFERMO_TABLE = "create table if not exists "
            + RECEPCION_ENFERMO_TABLE + " ("
            + idRecepcion + " text not null, "

            + participante + " text, "
            + fechaRecepcion + " date, "
            + tipoTubo + " text null, "
            + volumen + " real, "
            + observacion + " text null, "
            + fis + " date, "
            + fif + " date, "
            + categoria + " text null, "
            + consulta + " text null, "
            + tipoMuestra + " text null, "
            + estudiosAct + " text null, "
            + recordDate + " date, "
            + recordUser + " text null, "
            + pasive + " text null, "
            + deviceId + " text null, "
            + estado + " text null, "
            + "primary key (" + idRecepcion + "));";

    public static final String INSERT_RECEPCION_ENFERMO_TABLE = "insert into "
            + RECEPCION_ENFERMO_TABLE + " ("
            + idRecepcion + ","

            + participante + ","
            + fechaRecepcion + ","
            + tipoTubo + ","
            + volumen + ", "
            + observacion + ", "
            + fis + ", "
            + fif + ", "
            + categoria + ", "
            + consulta + ","
            + tipoMuestra + ","
            + estudiosAct + ","
            + observacion + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId+ ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Crear tabla cambio_domicilio
    public static final String CAMBIO_DOMICILIO_TABLE = "cambio_domicilio";

    //Campos tabla cambio_domicilio
    public static final String codigoNuevaCasaCohorte = "codigoNuevaCasaCohorte";
    public static final String numTelefono1 = "numTelefono1";
    public static final String operadoraTelefono1 = "operadoraTelefono1";
    public static final String numTelefono2 = "numTelefono2";
    public static final String operadoraTelefono2 = "operadoraTelefono2";
    public static final String numTelefono3 = "numTelefono3";
    public static final String operadoraTelefono3 = "operadoraTelefono3";
    public static final String codigoMovimiento = "codigoMovimiento";
    public static final String identificadoEquipo = "identificadoEquipo";
    public static final String fechaRegistro = "fechaRegistro";
    public static final String creado = "creado";
    public static final String usuarioRegistro = "usuarioRegistro";
    public static final String codigoParticipante = "codigoParticipante";

    //crear tabla cambio_domicilio
    public static final String CREATE_CAMBIO_DOMICILIO_TABLE = "create table if not exists "
            + CAMBIO_DOMICILIO_TABLE + " ("
            + id + " integer not null, "
            + codigoNuevaCasaCohorte + " text not null, "
            + codigoCasa + " text not null, "
            + nombre1JefeFamilia + " text not null, "
            + nombre2JefeFamilia + " text, "
            + apellido1JefeFamilia + " text not null, "
            + apellido2JefeFamilia + " text, "
            + direccion + " text not null, "
            + barrio + " text, "
            + puntoGps + " text, "
            + latitud + " real not null, "
            + longitud + " real not null, "
            + numTelefono1 + " text, "
            + operadoraTelefono1 + " text, "
            + numTelefono2 + " text, "
            + operadoraTelefono2 + " text, "
            + numTelefono3 + " text, "
            + operadoraTelefono3 + " text, "
            + codigoMovimiento + " text not null, "
            + identificadoEquipo + " text, "
            + estado + " text not null, "
            + pasive + " text not null, "
            + fechaRegistro + " text not null, "
            + creado + " text not null, "
            + usuarioRegistro + " text not null, "
            + actual + " boolean, "
            + codigoParticipante + " text not null, "
            + "primary key (" + id + "));";
    //+ "primary key AUTOINCREMENT (" + id + "));";

    public static final String INSERT_CAMBIO_DOMICILIO_TABLE = "insert into "
            + CAMBIO_DOMICILIO_TABLE + " ("
            + id + ","
            + codigoNuevaCasaCohorte + ","
            + codigoCasa + ","
            + nombre1JefeFamilia + ","
            + nombre2JefeFamilia + ","
            + apellido1JefeFamilia + ","
            + apellido2JefeFamilia + ","
            + direccion + ","
            + barrio + ","
            + puntoGps + ","
            + latitud + ","
            + longitud + ","
            + numTelefono1 + ","
            + operadoraTelefono1 + ","
            + numTelefono2 + ","
            + operadoraTelefono2 + ","
            + numTelefono3 + ","
            + operadoraTelefono3 + ","
            + codigoMovimiento + ","
            + identificadoEquipo + ","
            + estado + ","
            + pasive + ","
            + fechaRegistro + ","
            + creado + ","
            + usuarioRegistro + ","
            + actual + ","
            + codigoParticipante
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Tabla Control de Temperatura Termo
    public static final String CONTROL_TEMPERATURA_TERMO_TABLE = "control_temperatura_termo";

    //Campos tabla CONTROL_TEMPERATURA_TERMO
    public static final String horaTomaTemperatura  = "horaTomaTemperatura";
    public static final String fechaTomaTemperatura = "fechaTomaTemperatura";
    public static final String usuarioregistroT = "usuarioRegistro";
    public static final String temperaturaTermo = "temperaturaTermo";
    //Crear tabla CONTROL_TEMPERATURA_TERMO
    public static final String CREATE_CONTROL_TEMPERATURA_TERMO_TABLE = "create table if not exists "
            + CONTROL_TEMPERATURA_TERMO_TABLE + " ("
            + id + " integer not null,  "
            + fechaTomaTemperatura + " date, "
            + horaTomaTemperatura + " text, "
            + usuarioregistroT + " text, "
            + temperaturaTermo + " real, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";

    //Tabla Control de Asistencia personal A2CARES
    public static final String CONTROL_ASISTENCIA_TABLE = "control_asistencia";

    //Campos tabla CONTROL_ASISTENCIA
    public static final String horaentrada  = "horaEntrada";
    public static final String horasalida  = "horaSalida";
    public static final String usuarioregistro = "usuarioRegistro";
   // public static final Integer idasistencia = ;
    public static final String fechaasistencia = "fechaAsistencia";

    //Crear tabla CONTROL_ASISTENCIA
    public static final String CREATE_CONTROL_ASISTENCIA_TABLE = "create table if not exists "
            + CONTROL_ASISTENCIA_TABLE + " ("
            + id + " integer not null,  "
            + fechaasistencia + " date, "
            + horaentrada + " text, "
            + horasalida + " text, "
            + usuarioregistro + " text, "
            + latitud +" real, "
            + longitud + " real, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";


    public static final String INSERT_CONTROL_ASISTENCIA_TABLE = "insert into "
            + CONTROL_ASISTENCIA_TABLE + " ("
            + id + ","
            + fechaasistencia + ","
            + horaentrada + ","
            + horasalida + ","
            + usuarioregistro + ","
            + latitud + ","
            + longitud + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?)";
    //Tabla Control de Asistencia personal A2CARES
    public static final String ADMISION_PACIENTES_TABLE = "admision_pacientes";

    //Campos tabla ADMISION PACIENTES
    public static final String perteneceEstudio  = "perteneceEstudio";
    public static final String febril  = "febril";
    public static final String edad = "edad";
    public static final String numeroHoja = "numeroHoja";

    //Crear tabla ADMISION PACIENTES
    public static final String CREATE_ADMISION_PACIENTES_TABLE = "create table if not exists "
            + ADMISION_PACIENTES_TABLE + " ("
            + id + " integer not null,  "
            + perteneceEstudio + " text, "
            + codigoParticipante + " text, "
            + febril + " text, "
            + edad + " integer not null,  "
            + sexo + " text, "
            + numeroHoja +" integer not null,  "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";


    public static final String INSERT_ADMISION_PACIENTES_TABLE = "insert into "
            + ADMISION_PACIENTES_TABLE + " ("
            + id + ","
            + perteneceEstudio + ","
            + codigoParticipante + ","
            + febril + ","
            + edad + ","
            + sexo + ","
            + numeroHoja + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INFORME_FIN_DIA_MEDICOS_TABLE = "informe_fin_dia_medicos";

    //Campos tabla ADMISION PACIENTES
    public static final String puestoSalud  = "puestoSalud";
    public static final String fechaConsulta  = "fechaConsulta";
    public static final String numPartCohorte = "numPartCohorte";

    public static final String codPartAtend1 = "codPartAtend1";
    public static final String codPartAtend2 = "codPartAtend2";
    public static final String codPartAtend3 = "codPartAtend3";
    public static final String codPartAtend4 = "codPartAtend4";
    public static final String codPartAtend5 = "codPartAtend5";
    public static final String codPartAtend6 = "codPartAtend6";
    public static final String codPartAtend7 = "codPartAtend7";
    public static final String codPartAtend8 = "codPartAtend8";
    public static final String codPartAtend9 = "codPartAtend9";
    public static final String codPartAtend10 = "codPartAtend10";
    public static final String codPartAtend11 = "codPartAtend11";
    public static final String codPartAtend12 = "codPartAtend12";
    public static final String codPartAtend13 = "codPartAtend13";
    public static final String codPartAtend14 = "codPartAtend14";
    public static final String codPartAtend15 = "codPartAtend15";
    public static final String codPartAtend16 = "codPartAtend16";
    public static final String codPartAtend17 = "codPartAtend17";
    public static final String codPartAtend18 = "codPartAtend18";
    public static final String codPartAtend19 = "codPartAtend19";
    public static final String codPartAtend20 = "codPartAtend20";

    public static final String codPartAtend1Diagnostico = "codPartAtend1Diagnostico";
    public static final String codPartAtend2Diagnostico = "codPartAtend2Diagnostico";
    public static final String codPartAtend3Diagnostico = "codPartAtend3Diagnostico";
    public static final String codPartAtend4Diagnostico = "codPartAtend4Diagnostico";
    public static final String codPartAtend5Diagnostico = "codPartAtend5Diagnostico";
    public static final String codPartAtend6Diagnostico = "codPartAtend6Diagnostico";
    public static final String codPartAtend7Diagnostico = "codPartAtend7Diagnostico";
    public static final String codPartAtend8Diagnostico = "codPartAtend8Diagnostico";
    public static final String codPartAtend9Diagnostico = "codPartAtend9Diagnostico";
    public static final String codPartAtend10Diagnostico = "codPartAtend10Diagnostico";
    public static final String codPartAtend11Diagnostico = "codPartAtend11Diagnostico";
    public static final String codPartAtend12Diagnostico = "codPartAtend12Diagnostico";
    public static final String codPartAtend13Diagnostico = "codPartAtend13Diagnostico";
    public static final String codPartAtend14Diagnostico = "codPartAtend14Diagnostico";
    public static final String codPartAtend15Diagnostico = "codPartAtend15Diagnostico";
    public static final String codPartAtend16Diagnostico = "codPartAtend16Diagnostico";
    public static final String codPartAtend17Diagnostico = "codPartAtend17Diagnostico";
    public static final String codPartAtend18Diagnostico = "codPartAtend18Diagnostico";
    public static final String codPartAtend19Diagnostico = "codPartAtend19Diagnostico";
    public static final String codPartAtend20Diagnostico = "codPartAtend20Diagnostico";

    public static final String numPartNoCohorte = "numPartNoCohorte";
    public static final String numTotalAtendidos = "numTotalAtendidos";
    public static final String numFebrilA = "numFebrilA";
    public static final String numInRespAgudaA = "numInRespAgudaA";

    public static final String numEnfDiarreaAgudaA = "numEnfDiarreaAgudaA";
    public static final String numETI = "numETI";
    public static final String numRAG = "numRAG";
    public static final String numConjuntivitis = "numConjuntivitis";
    public static final String numControlPrenatal = "numControlPrenatal";
    public static final String numNeumonia = "numNeumonia";
    public static final String numPap = "numPap";
    public static final String numPlanificacionFam = "numPlanificacionFam";
    public static final String numGotaGruesa = "numGotaGruesa";
    public static final String numCronicos = "numCronicos";
    public static final String numTraslados = "numTraslados";
    public static final String numCaptacionA = "numCaptacionA";
    public static final String numCaptacionB = "numCaptacionB";
    public static final String numCaptacionC = "numCaptacionC";
    public static final String numCaptacionD = "numCaptacionD";
    public static final String numSeguimientoA = "numSeguimientoA";
    public static final String numSeguimientoB = "numSeguimientoB";
    public static final String numSeguimientoD = "numSeguimientoD";
    public static final String numConvPendPuesto = "numConvPendPuesto";
    public static final String numVisitaAterreno = "numVisitaAterreno";
    public static final String numTrasladosDengue = "numTrasladosDengue";

    public static final String codPartTraslado1 = "codPartTraslado1";
    public static final String codPartTraslado2 = "codPartTraslado2";
    public static final String codPartTraslado3 = "codPartTraslado3";
    public static final String codPartTraslado4 = "codPartTraslado4";
    public static final String codPartTraslado5 = "codPartTraslado5";
    public static final String codPartTraslado6 = "codPartTraslado6";
    public static final String codPartTraslado7 = "codPartTraslado7";
    public static final String codPartTraslado8 = "codPartTraslado8";
    public static final String codPartTraslado9 = "codPartTraslado9";
    public static final String codPartTraslado10 = "codPartTraslado10";

    public static final String codPartTraslado1Diagnostico = "codPartTraslado1Diagnostico";
    public static final String codPartTraslado2Diagnostico = "codPartTraslado2Diagnostico";
    public static final String codPartTraslado3Diagnostico = "codPartTraslado3Diagnostico";
    public static final String codPartTraslado4Diagnostico = "codPartTraslado4Diagnostico";
    public static final String codPartTraslado5Diagnostico = "codPartTraslado5Diagnostico";
    public static final String codPartTraslado6Diagnostico = "codPartTraslado6Diagnostico";
    public static final String codPartTraslado7Diagnostico = "codPartTraslado7Diagnostico";
    public static final String codPartTraslado8Diagnostico = "codPartTraslado8Diagnostico";
    public static final String codPartTraslado9Diagnostico = "codPartTraslado9Diagnostico";
    public static final String codPartTraslado10Diagnostico = "codPartTraslado10Diagnostico";

    public static final String numNegatTrasladosDengue = "numNegatTrasladosDengue";

    public static final String codNegatPartTraslado1 = "codNegatPartTraslado1";
    public static final String codNegatPartTraslado2 = "codNegatPartTraslado2";
    public static final String codNegatPartTraslado3 = "codNegatPartTraslado3";
    public static final String codNegatPartTraslado4 = "codNegatPartTraslado4";
    public static final String codNegatPartTraslado5 = "codNegatPartTraslado5";
    public static final String codNegatPartTraslado6 = "codNegatPartTraslado6";
    public static final String codNegatPartTraslado7 = "codNegatPartTraslado7";
    public static final String codNegatPartTraslado8 = "codNegatPartTraslado8";
    public static final String codNegatPartTraslado9 = "codNegatPartTraslado9";
    public static final String codNegatPartTraslado10 = "codNegatPartTraslado10";

    public static final String codNegaPartTraslado1Diagnostico = "codNegaPartTraslado1Diagnostico";
    public static final String codNegaPartTraslado2Diagnostico = "codNegaPartTraslado2Diagnostico";
    public static final String codNegaPartTraslado3Diagnostico = "codNegaPartTraslado3Diagnostico";
    public static final String codNegaPartTraslado4Diagnostico = "codNegaPartTraslado4Diagnostico";
    public static final String codNegaPartTraslado5Diagnostico = "codNegaPartTraslado5Diagnostico";
    public static final String codNegaPartTraslado6Diagnostico = "codNegaPartTraslado6Diagnostico";
    public static final String codNegaPartTraslado7Diagnostico = "codNegaPartTraslado7Diagnostico";
    public static final String codNegaPartTraslado8Diagnostico = "codNegaPartTraslado8Diagnostico";
    public static final String codNegaPartTraslado9Diagnostico = "codNegaPartTraslado9Diagnostico";
    public static final String codNegaPartTraslado10Diagnostico = "codNegaPartTraslado10Diagnostico";

    public static final String nomMedico = "nomMedico";

    //Crear tabla ADMISION PACIENTES
    public static final String CREATE_INFORME_FIN_DIA_MEDICOS_TABLE = "create table if not exists "
            + INFORME_FIN_DIA_MEDICOS_TABLE + " ("
            + id + " integer not null,  "
            + fechaConsulta + " date, "
            + puestoSalud + " text, "
            + numPartCohorte + " integer not null,  "
            + codPartAtend1 + " text, "
            + codPartAtend2 + " text, "
            + codPartAtend3 + " text, "
            + codPartAtend4 + " text, "
            + codPartAtend5 + " text, "
            + codPartAtend6 + " text, "
            + codPartAtend7 + " text, "
            + codPartAtend8 + " text, "
            + codPartAtend9 + " text, "
            + codPartAtend10 + " text, "
            + codPartAtend11 + " text, "
            + codPartAtend12 + " text, "
            + codPartAtend13 + " text, "
            + codPartAtend14 + " text, "
            + codPartAtend15 + " text, "
            + codPartAtend16 + " text, "
            + codPartAtend17 + " text, "
            + codPartAtend18 + " text, "
            + codPartAtend19 + " text, "
            + codPartAtend20 + " text, "
            + codPartAtend1Diagnostico + " text, "
            + codPartAtend2Diagnostico + " text, "
            + codPartAtend3Diagnostico + " text, "
            + codPartAtend4Diagnostico + " text, "
            + codPartAtend5Diagnostico + " text, "
            + codPartAtend6Diagnostico + " text, "
            + codPartAtend7Diagnostico + " text, "
            + codPartAtend8Diagnostico + " text, "
            + codPartAtend9Diagnostico + " text, "
            + codPartAtend10Diagnostico + " text, "
            + codPartAtend11Diagnostico + " text, "
            + codPartAtend12Diagnostico + " text, "
            + codPartAtend13Diagnostico + " text, "
            + codPartAtend14Diagnostico + " text, "
            + codPartAtend15Diagnostico + " text, "
            + codPartAtend16Diagnostico + " text, "
            + codPartAtend17Diagnostico + " text, "
            + codPartAtend18Diagnostico + " text, "
            + codPartAtend19Diagnostico + " text, "
            + codPartAtend20Diagnostico + " text, "
            + numPartNoCohorte + " integer not null,  "
            + numTotalAtendidos + " integer not null,  "
            + numFebrilA + " integer not null,  "
            + numInRespAgudaA + " integer not null,  "
            + numEnfDiarreaAgudaA + " integer not null,  "
            + numETI + " integer not null,  "
            + numRAG + " integer not null,  "
            + numConjuntivitis + " integer not null,  "
            + numControlPrenatal + " integer not null,  "
            + numNeumonia + " integer not null,  "
            + numPap + " integer not null,  "
            + numPlanificacionFam + " integer not null,  "
            + numGotaGruesa + " integer not null,  "
            + numCronicos + " integer not null,  "
            + numTraslados + " integer not null,  "
            + numCaptacionA + " integer not null,  "
            + numCaptacionB + " integer not null,  "
            + numCaptacionC + " integer not null,  "
            + numCaptacionD + " integer not null,  "
            + numSeguimientoA + " integer not null,  "
            + numSeguimientoB + " integer not null,  "
            + numSeguimientoD + " integer not null,  "
            + numConvPendPuesto + " integer not null,  "
            + numVisitaAterreno + " integer not null,  "
            + numTrasladosDengue + " integer not null,  "
            + codPartTraslado1 + " text, "
            + codPartTraslado2 + " text, "
            + codPartTraslado3 + " text, "
            + codPartTraslado4 + " text, "
            + codPartTraslado5 + " text, "
            + codPartTraslado6 + " text, "
            + codPartTraslado7 + " text, "
            + codPartTraslado8 + " text, "
            + codPartTraslado9 + " text, "
            + codPartTraslado10 + " text, "
            + codPartTraslado1Diagnostico + " text, "
            + codPartTraslado2Diagnostico + " text, "
            + codPartTraslado3Diagnostico + " text, "
            + codPartTraslado4Diagnostico + " text, "
            + codPartTraslado5Diagnostico + " text, "
            + codPartTraslado6Diagnostico + " text, "
            + codPartTraslado7Diagnostico + " text, "
            + codPartTraslado8Diagnostico + " text, "
            + codPartTraslado9Diagnostico + " text, "
            + codPartTraslado10Diagnostico + " text, "
            + numNegatTrasladosDengue + " integer not null,  "
            + codNegatPartTraslado1 + " text, "
            + codNegatPartTraslado2 + " text, "
            + codNegatPartTraslado3 + " text, "
            + codNegatPartTraslado4 + " text, "
            + codNegatPartTraslado5 + " text, "
            + codNegatPartTraslado6 + " text, "
            + codNegatPartTraslado7 + " text, "
            + codNegatPartTraslado8 + " text, "
            + codNegatPartTraslado9 + " text, "
            + codNegatPartTraslado10 + " text, "
            + codNegaPartTraslado1Diagnostico + " text, "
            + codNegaPartTraslado2Diagnostico + " text, "
            + codNegaPartTraslado3Diagnostico + " text, "
            + codNegaPartTraslado4Diagnostico + " text, "
            + codNegaPartTraslado5Diagnostico + " text, "
            + codNegaPartTraslado6Diagnostico + " text, "
            + codNegaPartTraslado7Diagnostico + " text, "
            + codNegaPartTraslado8Diagnostico + " text, "
            + codNegaPartTraslado9Diagnostico + " text, "
            + codNegaPartTraslado10Diagnostico + " text, "
            + nomMedico + " text, "
            + recordDate + " date, "
            + recordUser + " text, "
            + pasive + " text, "
            + deviceId + " text, "
            + estado + " text not null, "
            + "primary key (" + id + "));";


    public static final String INSERT_INFORME_FIN_DIA_MEDICOS_TABLE = "insert into "
            + INFORME_FIN_DIA_MEDICOS_TABLE + " ("
            + id + ","
            + fechaConsulta + ","
            + puestoSalud + ","
            + numPartCohorte + ","
            + codPartAtend1 + ","
            + codPartAtend2 + ","
            + codPartAtend3 + ","
            + codPartAtend4 + ","
            + codPartAtend5 + ","
            + codPartAtend6 + ","
            + codPartAtend7 + ","
            + codPartAtend8 + ","
            + codPartAtend9 + ","
            + codPartAtend10 + ","
            + codPartAtend11 + ","
            + codPartAtend12 + ","
            + codPartAtend13 + ","
            + codPartAtend14 + ","
            + codPartAtend15 + ","
            + codPartAtend16 + ","
            + codPartAtend17 + ","
            + codPartAtend18 + ","
            + codPartAtend19 + ","
            + codPartAtend20 + ","
            + codPartAtend1Diagnostico + ","
            + codPartAtend2Diagnostico + ","
            + codPartAtend3Diagnostico + ","
            + codPartAtend4Diagnostico + ","
            + codPartAtend5Diagnostico + ","
            + codPartAtend6Diagnostico + ","
            + codPartAtend7Diagnostico + ","
            + codPartAtend8Diagnostico + ","
            + codPartAtend9Diagnostico + ","
            + codPartAtend10Diagnostico + ","
            + codPartAtend11Diagnostico + ","
            + codPartAtend12Diagnostico + ","
            + codPartAtend13Diagnostico + ","
            + codPartAtend14Diagnostico + ","
            + codPartAtend15Diagnostico + ","
            + codPartAtend16Diagnostico + ","
            + codPartAtend17Diagnostico + ","
            + codPartAtend18Diagnostico + ","
            + codPartAtend19Diagnostico + ","
            + codPartAtend20Diagnostico + ","
            + numPartNoCohorte + ","
            + numTotalAtendidos + ","
            + numFebrilA + ","
            + numInRespAgudaA + ","
            + numEnfDiarreaAgudaA + ","
            + numETI + ","
            + numRAG + ","
            + numConjuntivitis + ","
            + numControlPrenatal + ","
            + numNeumonia + ","
            + numPap + ","
            + numPlanificacionFam + ","
            + numGotaGruesa + ","
            + numCronicos + ","
            + numTraslados + ","
            + numCaptacionA + ","
            + numCaptacionB + ","
            + numCaptacionC + ","
            + numCaptacionD + ","
            + numVisitaAterreno + ","
            + numTrasladosDengue + ","
            + codPartTraslado1 + ","
            + codPartTraslado2 + ","
            + codPartTraslado3 + ","
            + codPartTraslado4 + ","
            + codPartTraslado5 + ","
            + codPartTraslado6 + ","
            + codPartTraslado7 + ","
            + codPartTraslado8 + ","
            + codPartTraslado9 + ","
            + codPartTraslado10 + ","
            + codPartTraslado1Diagnostico + ","
            + codPartTraslado2Diagnostico + ","
            + codPartTraslado3Diagnostico + ","
            + codPartTraslado4Diagnostico + ","
            + codPartTraslado5Diagnostico + ","
            + codPartTraslado6Diagnostico + ","
            + codPartTraslado7Diagnostico + ","
            + codPartTraslado8Diagnostico + ","
            + codPartTraslado9Diagnostico + ","
            + codPartTraslado10Diagnostico + ","
            + numNegatTrasladosDengue + ","
            + codNegatPartTraslado1 + ","
            + codNegatPartTraslado2 + ","
            + codNegatPartTraslado3 + ","
            + codNegatPartTraslado4 + ","
            + codNegatPartTraslado5 + ","
            + codNegatPartTraslado6 + ","
            + codNegatPartTraslado7 + ","
            + codNegatPartTraslado8 + ","
            + codNegatPartTraslado9 + ","
            + codNegatPartTraslado10 + ","
            + codNegaPartTraslado1Diagnostico + ","
            + codNegaPartTraslado2Diagnostico + ","
            + codNegaPartTraslado3Diagnostico + ","
            + codNegaPartTraslado4Diagnostico + ","
            + codNegaPartTraslado5Diagnostico + ","
            + codNegaPartTraslado6Diagnostico + ","
            + codNegaPartTraslado7Diagnostico + ","
            + codNegaPartTraslado8Diagnostico + ","
            + codNegaPartTraslado9Diagnostico + ","
            + codNegaPartTraslado10Diagnostico + ","
            + nomMedico + ","
            + recordDate + ","
            + recordUser + ","
            + pasive + ","
            + deviceId + ","
            + estado
            + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

}
