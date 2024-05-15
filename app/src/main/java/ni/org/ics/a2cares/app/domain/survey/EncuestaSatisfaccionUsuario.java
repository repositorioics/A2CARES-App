package ni.org.ics.a2cares.app.domain.survey;

import java.io.Serializable;
import java.util.Date;

public class EncuestaSatisfaccionUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    //private Integer esId;
    private String codigoParticipante;
    private String atencionPersonalEstudio_P1;
    private String tiempoAtencionRecibido_P2;
    private String atencionRecibidaEnfermeria_P3;
    private String atencionRecibidaDoctores_P4;
    private String ambienteDondeRecibeAtencion_P5;
    private String explicaronDiagnostico_P6;
    private String explicaronTratamiento_P7;
    private String tieneArbovirusImportanciaSeg_P8;
    private String explicaronPeligrosArbovirus_P8_1;
    private String medicoDijoCuidados_P8_2;
    private String dificultadAtencion_P9;
    private String centroSaludLejos_P9_1;
    private String costoTrasnporteElevado_P9_2;
    private String trabajoTiempo_P9_3;
    private String noQueriapasarConsulta_P9_4;
    private String otrasEspecifique_P9_5;
    private String recomendariaAmigoFamiliar_P10;
    private String atencionCalidad_P10_1;
    private String respNecesidadesSaludOportuna_P10_1;
    private String tiempoEsperaCorto_P10_1;
    private String mejorAtencionQueSeguro_P10_1;
    private String examenLabNecesarios_P10_1;
    private String pocosRequisitos_P10_1;
    private String otraP_10_1;
    private String atencionPersonalMala_P10_2;
    private String noDanRespNecesidades_P10_2;
    private String tiempoEsperaLargo_P10_2;
    private String mejorAtencionOtraUnidadSalud_P10_2;
    private String solicitaDemasiadaMx_P10_2;
    private String muchosRequisitos_P10_2;
    private String noExplicanHacenMx_P10_2;
    private String noConfianza_P10_2;
    private String otraP_10_2;
    private String comprendeProcedimientos_P11;
    private String noComodoRealizarPreg_P11_1;
    private String noRespondieronPreg_P11_1;
    private String explicacionRapida_P11_1;
    private String noDejaronHacerPreg_P11_1;
    private String otraP_11_1;


    /* private String desicionPropia;
     private String obsequiosOfreceEstudio;
     private String ayudaRecibeComunidad;
     private String examenesLaboratorio;
     private String interesCientificoPersonalP1;
     private String informacionAyudaOtros;
     private String otraP1;
     private String calidadAtencionMedica;
     private String atencionOportuna;
     private String buenaCoordinacionhosp;
     private String infoEstadoSalud;
     private String enseniaPrevEnfermedades;
     private String infoConsMejoraConocimientos;
     private String interesCientificoPersonalP2;
     private String mejorarTratamientoDengue;
     private String unicaManeraRecibirAtencionMed;
     private String otraP2;
     private String difBuscarAtencionMed;
     private String centroSaludLejos;
     private String costoTrasnporteElevado;
     private String trabajoTiempo;
     private String noQueriapasarConsulta;
     private String horarioClases;
     private String temorTomenMx;
     private String temorInfoPersonal;
     private String otraP3;
     private String recomendariaAmigoFamiliar;
     private String atencionCalidad;
     private String respNecesidadesSaludOportuna;
     private String tiempoEsperaCorto;
     private String mejorAtencionQueSeguro;
     private String examenLabNecesarios;
     private String conocimientosImportantes;
     private String pocosRequisitos;
     private String otraP_4_1;
     private String atencionPersonalMala;
     private String noDanRespNecesidades;
     private String tiempoEsperaLargo;
     private String mejorAtencionOtraUnidadSalud;
     private String solicitaDemasiadaMx;
     private String muchosRequisitos;
     private String noExplicanHacenMx;
     private String noConfianza;
     private String otraP_4_2;
     private String comprendeProcedimientos;
     private String noComodoRealizarPreg;
     private String noRespondieronPreg;
     private String explicacionRapida;
     private String noDejaronHacerPreg;
     private String otraP_5_1;
     private String brindaronConsejosPrevencionEnfer;
     private String entiendoProcedimientosEstudios;
     private String satisfecho;
     private String comodoInfoRecolectada;
     private String noComodoPreguntas;
     private String recomendacionMejorarAtencion;
     private String importanciaRecibirInformacion;*/
    private String identificadoEquipo;
    private char estado;
    private char pasivo;
    private Date fechaRegistro;
    private String creado;
    private String usuarioRegistro;

    //Nuevos campos agregados 06/03/2023
    private String nombre1Tutor;
    private String nombre2Tutor;
    private String apellido1Tutor;
    private String apellido2Tutor;
    private Integer codigoCasa;
    //private String casaChf;
    private String estudio;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /*public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }*/

    public String getAtencionPersonalEstudio_P1() {
        return atencionPersonalEstudio_P1;
    }

    public void setAtencionPersonalEstudio_P1(String atencionPersonalEstudio_P1) {
        this.atencionPersonalEstudio_P1 = atencionPersonalEstudio_P1;
    }

    public String getTiempoAtencionRecibido_P2() {
        return tiempoAtencionRecibido_P2;
    }

    public void setTiempoAtencionRecibido_P2(String tiempoAtencionRecibido_P2) {
        this.tiempoAtencionRecibido_P2 = tiempoAtencionRecibido_P2;
    }

    public String getAtencionRecibidaEnfermeria_P3() {
        return atencionRecibidaEnfermeria_P3;
    }

    public void setAtencionRecibidaEnfermeria_P3(String atencionRecibidaEnfermeria_P3) {
        this.atencionRecibidaEnfermeria_P3 = atencionRecibidaEnfermeria_P3;
    }

    public String getAtencionRecibidaDoctores_P4() {
        return atencionRecibidaDoctores_P4;
    }

    public void setAtencionRecibidaDoctores_P4(String atencionRecibidaDoctores_P4) {
        this.atencionRecibidaDoctores_P4 = atencionRecibidaDoctores_P4;
    }

    public String getAmbienteDondeRecibeAtencion_P5() {
        return ambienteDondeRecibeAtencion_P5;
    }

    public void setAmbienteDondeRecibeAtencion_P5(String ambienteDondeRecibeAtencion_P5) {
        this.ambienteDondeRecibeAtencion_P5 = ambienteDondeRecibeAtencion_P5;
    }

    public String getExplicaronDiagnostico_P6() {
        return explicaronDiagnostico_P6;
    }

    public void setExplicaronDiagnostico_P6(String explicaronDiagnostico_P6) {
        this.explicaronDiagnostico_P6 = explicaronDiagnostico_P6;
    }

    public String getExplicaronTratamiento_P7() {
        return explicaronTratamiento_P7;
    }

    public void setExplicaronTratamiento_P7(String explicaronTratamiento_P7) {
        this.explicaronTratamiento_P7 = explicaronTratamiento_P7;
    }

    public String getTieneArbovirusImportanciaSeg_P8() {
        return tieneArbovirusImportanciaSeg_P8;
    }

    public void setTieneArbovirusImportanciaSeg_P8(String tieneArbovirusImportanciaSeg_P8) {
        this.tieneArbovirusImportanciaSeg_P8 = tieneArbovirusImportanciaSeg_P8;
    }

    public String getExplicaronPeligrosArbovirus_P8_1() {
        return explicaronPeligrosArbovirus_P8_1;
    }

    public void setExplicaronPeligrosArbovirus_P8_1(String explicaronPeligrosArbovirus_P8_1) {
        this.explicaronPeligrosArbovirus_P8_1 = explicaronPeligrosArbovirus_P8_1;
    }

    public String getMedicoDijoCuidados_P8_2() {
        return medicoDijoCuidados_P8_2;
    }

    public void setMedicoDijoCuidados_P8_2(String medicoDijoCuidados_P8_2) {
        this.medicoDijoCuidados_P8_2 = medicoDijoCuidados_P8_2;
    }

    public String getDificultadAtencion_P9() {
        return dificultadAtencion_P9;
    }

    public void setDificultadAtencion_P9(String dificultadAtencion_P9) {
        this.dificultadAtencion_P9 = dificultadAtencion_P9;
    }

    public String getCentroSaludLejos_P9_1() {
        return centroSaludLejos_P9_1;
    }

    public void setCentroSaludLejos_P9_1(String centroSaludLejos_P9_1) {
        this.centroSaludLejos_P9_1 = centroSaludLejos_P9_1;
    }

    public String getCostoTrasnporteElevado_P9_2() {
        return costoTrasnporteElevado_P9_2;
    }

    public void setCostoTrasnporteElevado_P9_2(String costoTrasnporteElevado_P9_2) {
        this.costoTrasnporteElevado_P9_2 = costoTrasnporteElevado_P9_2;
    }

    public String geTrabajoTiempo_P9_3() {
        return trabajoTiempo_P9_3;
    }

    public void setTrabajoTiempo_P9_3(String trabajoTiempo_P9_3) {
        this.trabajoTiempo_P9_3 = trabajoTiempo_P9_3;
    }

    public String getNoQueriapasarConsulta_P9_4() {
        return noQueriapasarConsulta_P9_4;
    }

    public void setNoQueriapasarConsulta_P9_4(String noQueriapasarConsulta_P9_4) {
        this.noQueriapasarConsulta_P9_4 = noQueriapasarConsulta_P9_4;
    }

    public String getOtrasEspecifique_P9_5() {
        return otrasEspecifique_P9_5;
    }

    public void setOtrasEspecifique_P9_5(String otrasEspecifique_P9_5) {
        this.otrasEspecifique_P9_5 = otrasEspecifique_P9_5;
    }

    public String getRecomendariaAmigoFamiliar_P10() {
        return recomendariaAmigoFamiliar_P10;
    }

    public void setRecomendariaAmigoFamiliar_P10(String recomendariaAmigoFamiliar_P10) {
        this.recomendariaAmigoFamiliar_P10 = recomendariaAmigoFamiliar_P10;
    }

    public String getAtencionCalidad_P10_1() {
        return atencionCalidad_P10_1;
    }

    public void setAtencionCalidad_P10_1(String atencionCalidad_P10_1) {
        this.atencionCalidad_P10_1 = atencionCalidad_P10_1;
    }

    public String getRespNecesidadesSaludOportuna_P10_1() {
        return respNecesidadesSaludOportuna_P10_1;
    }

    public void setRespNecesidadesSaludOportuna_P10_1(String respNecesidadesSaludOportuna_P10_1) {
        this.respNecesidadesSaludOportuna_P10_1 = respNecesidadesSaludOportuna_P10_1;
    }

    public String getTiempoEsperaCorto_P10_1() {
        return tiempoEsperaCorto_P10_1;
    }

    public void setTiempoEsperaCorto_P10_1(String tiempoEsperaCorto_P10_1) {
        this.tiempoEsperaCorto_P10_1 = tiempoEsperaCorto_P10_1;
    }

    public String getMejorAtencionQueSeguro_P10_1() {
        return mejorAtencionQueSeguro_P10_1;
    }

    public void setMejorAtencionQueSeguro_P10_1(String mejorAtencionQueSeguro_P10_1) {
        this.mejorAtencionQueSeguro_P10_1 = mejorAtencionQueSeguro_P10_1;
    }

    public String getExamenLabNecesarios_P10_1() {
        return examenLabNecesarios_P10_1;
    }

    public void setExamenLabNecesarios_P10_1(String examenLabNecesarios_P10_1) {
        this.examenLabNecesarios_P10_1 = examenLabNecesarios_P10_1;
    }

    public String getPocosRequisitos_P10_1() {
        return pocosRequisitos_P10_1;
    }

    public void setPocosRequisitos_P10_1(String pocosRequisitos_P10_1) {
        this.pocosRequisitos_P10_1 = pocosRequisitos_P10_1;
    }

    public String getOtraP_10_1() {
        return otraP_10_1;
    }

    public void setOtraP_10_1(String otraP_10_1) {
        this.otraP_10_1 = otraP_10_1;
    }

    public String getAtencionPersonalMala_P10_2() {
        return atencionPersonalMala_P10_2;
    }

    public void setAtencionPersonalMala_P10_2(String atencionPersonalMala_P10_2) {
        this.atencionPersonalMala_P10_2 = atencionPersonalMala_P10_2;
    }

    public String getNoDanRespNecesidades_P10_2() {
        return noDanRespNecesidades_P10_2;
    }

    public void setNoDanRespNecesidades_P10_2(String noDanRespNecesidades_P10_2) {
        this.noDanRespNecesidades_P10_2 = noDanRespNecesidades_P10_2;
    }

    public String getTiempoEsperaLargo_P10_2() {
        return tiempoEsperaLargo_P10_2;
    }

    public void setTiempoEsperaLargo_P10_2(String tiempoEsperaLargo_P10_2) {
        this.tiempoEsperaLargo_P10_2 = tiempoEsperaLargo_P10_2;
    }

    public String getMejorAtencionOtraUnidadSalud_P10_2() {
        return mejorAtencionOtraUnidadSalud_P10_2;
    }

    public void setMejorAtencionOtraUnidadSalud_P10_2(String mejorAtencionOtraUnidadSalud_P10_2) {
        this.mejorAtencionOtraUnidadSalud_P10_2 = mejorAtencionOtraUnidadSalud_P10_2;
    }

    public String getSolicitaDemasiadaMx_P10_2() {
        return solicitaDemasiadaMx_P10_2;
    }

    public void setSolicitaDemasiadaMx_P10_2(String solicitaDemasiadaMx_P10_2) {
        this.solicitaDemasiadaMx_P10_2 = solicitaDemasiadaMx_P10_2;
    }

    public String getMuchosRequisitos_P10_2() {
        return muchosRequisitos_P10_2;
    }

    public void setMuchosRequisitos_P10_2(String muchosRequisitos_P10_2) {
        this.muchosRequisitos_P10_2 = muchosRequisitos_P10_2;
    }

    public String getNoExplicanHacenMx_P10_2() {
        return noExplicanHacenMx_P10_2;
    }

    public void setNoExplicanHacenMx_P10_2(String noExplicanHacenMx_P10_2) {
        this.noExplicanHacenMx_P10_2 = noExplicanHacenMx_P10_2;
    }

    public String getNoConfianza_P10_2() {
        return noConfianza_P10_2;
    }

    public void setNoConfianza_P10_2(String noConfianza_P10_2) {
        this.noConfianza_P10_2 = noConfianza_P10_2;
    }

    public String getOtraP_10_2() {
        return otraP_10_2;
    }

    public void setOtraP_10_2(String otraP_10_2) {
        this.otraP_10_2 = otraP_10_2;
    }

    public String getComprendeProcedimientos_P11() {
        return comprendeProcedimientos_P11;
    }

    public void setComprendeProcedimientos_P11(String comprendeProcedimientos_P11) {
        this.comprendeProcedimientos_P11 = comprendeProcedimientos_P11;
    }

    public String getNoComodoRealizarPreg_P11_1() {
        return noComodoRealizarPreg_P11_1;
    }

    public void setNoComodoRealizarPreg_P11_1(String noComodoRealizarPreg_P11_1) {
        this.noComodoRealizarPreg_P11_1 = noComodoRealizarPreg_P11_1;
    }

    public String getNoRespondieronPreg_P11_1() {
        return noRespondieronPreg_P11_1;
    }

    public void setNoRespondieronPreg_P11_1(String noRespondieronPreg_P11_1) {
        this.noRespondieronPreg_P11_1 = noRespondieronPreg_P11_1;
    }

    public String getExplicacionRapida_P11_1() {
        return explicacionRapida_P11_1;
    }

    public void setExplicacionRapida_P11_1(String explicacionRapida_P11_1) {
        this.explicacionRapida_P11_1 = explicacionRapida_P11_1;
    }

    public String getNoDejaronHacerPreg_P11_1() {
        return noDejaronHacerPreg_P11_1;
    }

    public void setNoDejaronHacerPreg_P11_1(String noDejaronHacerPreg_P11_1) {
        this.noDejaronHacerPreg_P11_1 = noDejaronHacerPreg_P11_1;
    }

    public String getOtraP_11_1() {
        return otraP_11_1;
    }

    public void setOtraP_11_1(String otraP_11_1) {
        this.otraP_11_1 = otraP_11_1;
    }



    public String getIdentificadoEquipo() {
        return identificadoEquipo;
    }

    public void setIdentificadoEquipo(String identificadoEquipo) {
        this.identificadoEquipo = identificadoEquipo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public char getPasivo() {
        return pasivo;
    }

    public void setPasivo(char pasivo) {
        this.pasivo = pasivo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getNombre1Tutor() {
        return nombre1Tutor;
    }

    public void setNombre1Tutor(String nombre1Tutor) {
        this.nombre1Tutor = nombre1Tutor;
    }

    public String getNombre2Tutor() {
        return nombre2Tutor;
    }

    public void setNombre2Tutor(String nombre2Tutor) {
        this.nombre2Tutor = nombre2Tutor;
    }

    public String getApellido1Tutor() {
        return apellido1Tutor;
    }

    public void setApellido1Tutor(String apellido1Tutor) {
        this.apellido1Tutor = apellido1Tutor;
    }

    public String getApellido2Tutor() {
        return apellido2Tutor;
    }

    public void setApellido2Tutor(String apellido2Tutor) {
        this.apellido2Tutor = apellido2Tutor;
    }

    public Integer getCodigoCasa() {
        return codigoCasa;
    }

    public void setCodigoCasa(Integer codigoCasa) {
        this.codigoCasa = codigoCasa;
    }

    /*public String getCasaChf() {
        return casaChf;
    }

    public void setCasaChf(String casaChf) {
        this.casaChf = casaChf;
    }*/

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }
    public String getCodigoParticipante() {
        return codigoParticipante;
    }

    public void setCodigoParticipante(String codigoParticipante) {
        this.codigoParticipante = codigoParticipante;
    }
}
