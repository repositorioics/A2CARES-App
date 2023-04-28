package ni.org.ics.a2cares.app.ui.forms;

import android.content.res.Resources;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;

public class CambioDomicilioFormLabels {

    protected String cambioDomicilio;

    protected String aceptaCambioDomicilioHint;
    protected String codigoNuevaCasaCohorte;
    protected String codigoCasaCohorteHint;

    protected String moverOtroParticipante;
    protected String moverOtroParticipanteHint;

    protected String nombre1JefeFamilia;
    protected String nombre2JefeFamilia;
    protected String apellido1JefeFamilia;
    protected String apellido2JefeFamilia;
    protected String jefeFamiliaHint;
    protected String direccion;
    protected String direccionHint;
    protected String barrio;
    protected String coordenadas;

    protected String tieneTelefonoCel;
    protected String numTelefono1;
    protected String numTelefono1Hint;
    protected String operadoraTelefono1;
    protected String operadoraTelefono1Hint;

    protected String tieneOtroTelefonoCel;
    protected String numTelefono2;
    protected String numTelefono2Hint;
    protected String operadoraTelefono2;
    protected String operadoraTelefono2Hint;

    protected String tieneTelefonoConv;
    protected String numTelefono3;
    protected String numTelefono3Hint;
    protected String operadoraTelefono3;
    protected String operadoraTelefono3Hint;

    protected String finCambioDomicilioLabel;

    public CambioDomicilioFormLabels() {
        Resources res = MyIcsApplication.getContext().getResources();

        cambioDomicilio = res.getString(R.string.cd_cambio_domicilio);
        aceptaCambioDomicilioHint = res.getString(R.string.acepta_cambio_domicilioHint);
        codigoNuevaCasaCohorte = res.getString(R.string.cd_codigoCasaCohorte);
        codigoCasaCohorteHint = res.getString(R.string.cd_codigoCasaCohorteHint);

        moverOtroParticipante = res.getString(R.string.cd_participante);
        moverOtroParticipanteHint = res.getString(R.string.otro_participante_Hint);

        nombre1JefeFamilia = res.getString(R.string.cd_nombre1JefeFamilia);
        nombre2JefeFamilia = res.getString(R.string.cd_nombre2JefeFamilia);
        apellido1JefeFamilia = res.getString(R.string.cd_apellido1JefeFamilia);
        apellido2JefeFamilia = res.getString(R.string.cd_apellido2JefeFamilia);
        jefeFamiliaHint = res.getString(R.string.cd_jefeFamiliaHint);
        direccion = res.getString(R.string.cd_direccion);
        direccionHint = res.getString(R.string.cd_direccionHint);
        barrio = res.getString(R.string.cd_barrio);
        coordenadas = res.getString(R.string.cd_coordenadas);

        numTelefono1 = res.getString(R.string.numTelefono1);
        numTelefono1Hint = res.getString(R.string.numTelefono1Hint);
        operadoraTelefono1 = res.getString(R.string.operadoraTelefono1);
        operadoraTelefono1Hint = res.getString(R.string.operadoraTelefono1Hint);
        numTelefono2 = res.getString(R.string.numTelefono2);
        numTelefono2Hint = res.getString(R.string.numTelefono2Hint);
        operadoraTelefono2 = res.getString(R.string.operadoraTelefono2);
        operadoraTelefono2Hint = res.getString(R.string.operadoraTelefono2Hint);
        numTelefono3 = res.getString(R.string.numTelefono3);
        numTelefono3Hint = res.getString(R.string.numTelefono3Hint);
        operadoraTelefono3 = res.getString(R.string.operadoraTelefono3);
        operadoraTelefono3Hint = res.getString(R.string.operadoraTelefono3Hint);
        tieneTelefonoCel = res.getString(R.string.tieneTelefonoCel);
        tieneOtroTelefonoCel = res.getString(R.string.tieneOtroTelefonoCel);
        tieneTelefonoConv = res.getString(R.string.tieneTelefonoConv);

        finCambioDomicilioLabel = res.getString(R.string.finTamizajeLabel);
    }

    public String getCambioDomicilio() {
        return cambioDomicilio;
    }

    public void setCambioDomicilio(String cambioDomicilio) {
        this.cambioDomicilio = cambioDomicilio;
    }

    public String getAceptaCambioDomicilioHint() {
        return aceptaCambioDomicilioHint;
    }

    public void setAceptaCambioDomicilioHint(String aceptaCambioDomicilioHint) {
        this.aceptaCambioDomicilioHint = aceptaCambioDomicilioHint;
    }

    public String getCodigoNuevaCasaCohorte() {
        return codigoNuevaCasaCohorte;
    }

    public void setCodigoNuevaCasaCohorte(String codigoNuevaCasaCohorte) {
        this.codigoNuevaCasaCohorte = codigoNuevaCasaCohorte;
    }

    public String getCodigoCasaCohorteHint() {
        return codigoCasaCohorteHint;
    }

    public void getCodigoCasaCohorteHint(String codigoCasaCohorteHint) {
        this.codigoCasaCohorteHint = codigoCasaCohorteHint;
    }

    public String getNombre1JefeFamilia() {
        return nombre1JefeFamilia;
    }

    public void setNombre1JefeFamilia(String nombre1JefeFamilia) {
        this.nombre1JefeFamilia = nombre1JefeFamilia;
    }

    public String getNombre2JefeFamilia() {
        return nombre2JefeFamilia;
    }

    public void setNombre2JefeFamilia(String nombre2JefeFamilia) {
        this.nombre2JefeFamilia = nombre2JefeFamilia;
    }

    public String getApellido1JefeFamilia() {
        return apellido1JefeFamilia;
    }

    public void setApellido1JefeFamilia(String apellido1JefeFamilia) {
        this.apellido1JefeFamilia = apellido1JefeFamilia;
    }

    public String getApellido2JefeFamilia() {
        return apellido2JefeFamilia;
    }

    public void setApellido2JefeFamilia(String apellido2JefeFamilia) {
        this.apellido2JefeFamilia = apellido2JefeFamilia;
    }

    public String getJefeFamiliaHint() {
        return jefeFamiliaHint;
    }

    public void setJefeFamiliaHint(String jefeFamiliaHint) {
        this.jefeFamiliaHint = jefeFamiliaHint;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionHint() {
        return direccionHint;
    }

    public void setDireccionHint(String direccionHint) {
        this.direccionHint = direccionHint;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getTieneTelefonoCel() {
        return tieneTelefonoCel;
    }

    public void setTieneTelefonoCel(String tieneTelefonoCel) {
        this.tieneTelefonoCel = tieneTelefonoCel;
    }

    public String getNumTelefono1() {
        return numTelefono1;
    }

    public void setNumTelefono1(String numTelefono1) {
        this.numTelefono1 = numTelefono1;
    }

    public String getNumTelefono1Hint() {
        return numTelefono1Hint;
    }

    public void setNumTelefono1Hint(String numTelefono1Hint) {
        this.numTelefono1Hint = numTelefono1Hint;
    }

    public String getOperadoraTelefono1() {
        return operadoraTelefono1;
    }

    public void setOperadoraTelefono1(String operadoraTelefono1) {
        this.operadoraTelefono1 = operadoraTelefono1;
    }

    public String getOperadoraTelefono1Hint() {
        return operadoraTelefono1Hint;
    }

    public void setOperadoraTelefono1Hint(String operadoraTelefono1Hint) {
        this.operadoraTelefono1Hint = operadoraTelefono1Hint;
    }

    public String getTieneOtroTelefonoCel() {
        return tieneOtroTelefonoCel;
    }

    public void setTieneOtroTelefonoCel(String tieneOtroTelefonoCel) {
        this.tieneOtroTelefonoCel = tieneOtroTelefonoCel;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public void setNumTelefono2(String numTelefono2) {
        this.numTelefono2 = numTelefono2;
    }

    public String getNumTelefono2Hint() {
        return numTelefono2Hint;
    }

    public void setNumTelefono2Hint(String numTelefono2Hint) {
        this.numTelefono2Hint = numTelefono2Hint;
    }

    public String getOperadoraTelefono2() {
        return operadoraTelefono2;
    }

    public void setOperadoraTelefono2(String operadoraTelefono2) {
        this.operadoraTelefono2 = operadoraTelefono2;
    }

    public String getOperadoraTelefono2Hint() {
        return operadoraTelefono2Hint;
    }

    public void setOperadoraTelefono2Hint(String operadoraTelefono2Hint) {
        this.operadoraTelefono2Hint = operadoraTelefono2Hint;
    }

    public String getTieneTelefonoConv() {
        return tieneTelefonoConv;
    }

    public void setTieneTelefonoConv(String tieneTelefonoConv) {
        this.tieneTelefonoConv = tieneTelefonoConv;
    }

    public String getNumTelefono3() {
        return numTelefono3;
    }

    public void setNumTelefono3(String numTelefono3) {
        this.numTelefono3 = numTelefono3;
    }

    public String getNumTelefono3Hint() {
        return numTelefono3Hint;
    }

    public void setNumTelefono3Hint(String numTelefono3Hint) {
        this.numTelefono3Hint = numTelefono3Hint;
    }

    public String getOperadoraTelefono3() {
        return operadoraTelefono3;
    }

    public void setOperadoraTelefono3(String operadoraTelefono3) {
        this.operadoraTelefono3 = operadoraTelefono3;
    }

    public String getOperadoraTelefono3Hint() {
        return operadoraTelefono3Hint;
    }

    public void setOperadoraTelefono3Hint(String operadoraTelefono3Hint) {
        this.operadoraTelefono3Hint = operadoraTelefono3Hint;
    }

    public String getFinCambioDomicilioLabel() {
        return finCambioDomicilioLabel;
    }

    public void setFinCambioDomicilioLabel(String finCambioDomicilioLabel) {
        this.finCambioDomicilioLabel = finCambioDomicilioLabel;
    }

    public String getMoverOtroParticipante() {
        return moverOtroParticipante;
    }

    public void setMoverOtroParticipante(String moverOtroParticipante) {
        this.moverOtroParticipante = moverOtroParticipante;
    }

    public String getMoverOtroParticipanteHint() {
        return moverOtroParticipanteHint;
    }

    public void setMoverOtroParticipanteHint(String moverOtroParticipanteHint) {
        this.moverOtroParticipanteHint = moverOtroParticipanteHint;
    }
}
