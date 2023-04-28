package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

public class CambioDomicilio {
    private Integer id;
    private String codigoParticipante;
    private String codigoNuevaCasaCohorte;
    private String codigoCasa;
    private String nombre1JefeFamilia;
    private String nombre2JefeFamilia;
    private String apellido1JefeFamilia;
    private String apellido2JefeFamilia;
    private String direccion;
    private Barrio barrio;
    private String coordenadas;
    private Double latitud;
    private Double longitud;
    private String numTelefono1;
    private String operadoraTelefono1;
    private String numTelefono2;
    private String operadoraTelefono2;
    private String numTelefono3;
    private String operadoraTelefono3;
    private String codigoMovimiento;
    private String identificadoEquipo;
    private char estado;
    private char pasivo;
    private Date fechaRegistro;
    private String creado;
    private String usuarioRegistro;
    private boolean actual;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoNuevaCasaCohorte() {
        return codigoNuevaCasaCohorte;
    }

    public void setCodigoNuevaCasaCohorte(String codigoNuevaCasaCohorte) {
        this.codigoNuevaCasaCohorte = codigoNuevaCasaCohorte;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getNumTelefono1() {
        return numTelefono1;
    }

    public void setNumTelefono1(String numTelefono1) {
        this.numTelefono1 = numTelefono1;
    }

    public String getOperadoraTelefono1() {
        return operadoraTelefono1;
    }

    public void setOperadoraTelefono1(String operadoraTelefono1) {
        this.operadoraTelefono1 = operadoraTelefono1;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public void setNumTelefono2(String numTelefono2) {
        this.numTelefono2 = numTelefono2;
    }

    public String getOperadoraTelefono2() {
        return operadoraTelefono2;
    }

    public void setOperadoraTelefono2(String operadoraTelefono2) {
        this.operadoraTelefono2 = operadoraTelefono2;
    }

    public String getNumTelefono3() {
        return numTelefono3;
    }

    public void setNumTelefono3(String numTelefono3) {
        this.numTelefono3 = numTelefono3;
    }

    public String getOperadoraTelefono3() {
        return operadoraTelefono3;
    }

    public void setOperadoraTelefono3(String operadoraTelefono3) {
        this.operadoraTelefono3 = operadoraTelefono3;
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

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getCodigoCasa() {
        return codigoCasa;
    }

    public void setCodigoCasa(String codigoCasa) {
        this.codigoCasa = codigoCasa;
    }

    public String getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public void setCodigoMovimiento(String codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public String getCodigoParticipante() {
        return codigoParticipante;
    }

    public void setCodigoParticipante(String codigoParticipante) {
        this.codigoParticipante = codigoParticipante;
    }
}
