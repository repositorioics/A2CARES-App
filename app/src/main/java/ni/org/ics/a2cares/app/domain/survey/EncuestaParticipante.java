package ni.org.ics.a2cares.app.domain.survey;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;
import ni.org.ics.a2cares.app.domain.core.Participante;


/**
 * Simple objeto de dominio que representa los datos de la encuesta de los
 * participantes en el estudio
 * @author Miguel Salinas on 15/6/2021
 *
 **/

public class EncuestaParticipante extends BaseMetaData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String codigo;
	private Participante participante;
	private Date fechaEncuesta;
	//adultos
	private String emancipado;
	private String descEmancipado;
	private String otraDescEmanc;
	private String embarazada;
	private String conoceFUR;
	private Date fur;
	//niños
	private String asisteEscuela;
	private String gradoEstudia;
	private String nombreEscuela;
	private String turno;
	private String cuidan;
	private Integer cuantosCuidan;
	private String nombreCDI;
	private String direccionCDI;
	private String otroLugarCuidan;

	private String alfabeto;
	private String nivelEducacion;
	private String trabaja;
	private String tipoTrabajo;
	private String ocupacionActual;

	private String personaVive;
	private Integer ordenNac;
	private String padreAlfabeto;
	private String nivelEscolarPadre;
	private String trabajaPadre;
	private String tipoTrabajoPadre;
	private String madreAlfabeta;
	private String nivelEscolarMadre;
	private String trabajaMadre;
	private String tipoTrabajoMadre;
	private String comparteHab;
	private Integer hab1;
	private Integer hab2;
	private Integer hab3;
	private Integer hab4;
	private Integer hab5;
	private String comparteCama;
	private Integer cama1;
	private Integer cama2;
	private Integer cama3;
	private Integer cama4;
	private Integer cama5;
	//MAYORES DE 12
	private String fuma;
	private String periodicidadFuma;
	private Integer cantidadCigarrillos;
	private String fumaDentroCasa;
	//TODOS
	/*
	private String alguienFuma;
	private String quienFuma;
	private Integer cantCigarrosMadre;
	private Integer cantCigarrosPadre;
	private Integer cantCigarrosOtros;
	*/
	//NIÑOS??
	private String tuberculosisPulmonarActual;
	private Integer anioDiagTubpulActual;
	private String mesDiagTubpulActual;
	private String tratamientoTubpulActual;
	private String completoTratamientoTubpulActual;
	private String tuberculosisPulmonarPasado;
	private String fechaDiagTubpulPasadoDes;
	private Integer anioDiagTubpulPasado;
	private String mesDiagTubpulPasado;
	private String tratamientoTubpulPasado;
	private String completoTratamientoTubpulPas;
	//TODOS
	private String alergiaRespiratoria;
	private Integer anioAlergiaRespiratoria;
	private String asma;
	private Integer anioAsma;
	private String enfermedadCronica;
	private String cardiopatia;
	private Integer anioCardiopatia;
	private String enfermPulmonarObstCronica;
	private Integer anioEnfermPulmonarObstCronica;
	private String diabetes;
	private Integer anioDiabetes;
	private String presionAlta;
	private Integer anioPresionAlta;
	private String otrasEnfermedades;
	private String descOtrasEnfermedades;

	private String tenidoDengue;
	private Integer anioDengue;
	private String diagMedicoDengue;
	private String dondeDengue;
	private String tenidoZika;
	private Integer anioZika;
	private String diagMedicoZika;
	private String dondeZika;
	private String tenidoChik;
	private Integer anioChik;
	private String diagMedicoChik;
	private String dondeChik;
	private String vacunaFiebreAma;
	private Integer anioVacunaFiebreAma;
	private String vacunaCovid;
	private Integer anioVacunaCovid;
	private String mesVacunaCovid;
	private String tieneTarjetaVacuna;

	private String fiebre;
	private String tiempoFiebre;
	private String lugarConsFiebre;
	private String noAcudioCs;
	private String automedicoFiebre;
	private String tenidoDengueUA;
	private String unidadSaludDengue;
	private String centroSaludDengue;
	private String otroCentroSaludDengue;
	private String puestoSaludDengue;
	private String otroPuestoSaludDengue;
	private String hospitalDengue;
	private String otroHospitalDengue;
	private String hospitalizadoDengue;
	private String ambulatorioDengue;
	private String diagnosticoDengue;
	private String rashUA;
	private String recuerdaMesRash;
	private String mesRash;
	private String caraRash;
	private String miembrosSupRash;
	private String toraxRash;
	private String abdomenRash;
	private String miembrosInfRash;
	private String diasRash;
	private String consultaRashUA;
	private String unidadSaludRash;
	private String centroSaludRash;
	private String otroCentroSaludRash;
	private String puestoSaludRash;
	private String otroPuestoSaludRash;
	private String diagnosticoRash;
    private String estudiosAct; // estudios actuales al momento de llenar la encuesta

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Date getFechaEncuesta() {
		return fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}

	public String getEmancipado() {
		return emancipado;
	}

	public void setEmancipado(String emancipado) {
		this.emancipado = emancipado;
	}

	public String getDescEmancipado() {
		return descEmancipado;
	}

	public void setDescEmancipado(String descEmancipado) {
		this.descEmancipado = descEmancipado;
	}

	public String getOtraDescEmanc() {
		return otraDescEmanc;
	}

	public void setOtraDescEmanc(String otraDescEmanc) {
		this.otraDescEmanc = otraDescEmanc;
	}

	public String getEmbarazada() {
		return embarazada;
	}

	public void setEmbarazada(String embarazada) {
		this.embarazada = embarazada;
	}

	public String getConoceFUR() {
		return conoceFUR;
	}

	public void setConoceFUR(String conoceFUR) {
		this.conoceFUR = conoceFUR;
	}

	public Date getFur() {
		return fur;
	}

	public void setFur(Date fur) {
		this.fur = fur;
	}

	public String getAsisteEscuela() {
		return asisteEscuela;
	}

	public void setAsisteEscuela(String asisteEscuela) {
		this.asisteEscuela = asisteEscuela;
	}

	public String getGradoEstudia() {
		return gradoEstudia;
	}

	public void setGradoEstudia(String gradoEstudia) {
		this.gradoEstudia = gradoEstudia;
	}

	public String getNombreEscuela() {
		return nombreEscuela;
	}

	public void setNombreEscuela(String nombreEscuela) {
		this.nombreEscuela = nombreEscuela;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCuidan() {
		return cuidan;
	}

	public void setCuidan(String cuidan) {
		this.cuidan = cuidan;
	}

	public Integer getCuantosCuidan() {
		return cuantosCuidan;
	}

	public void setCuantosCuidan(Integer cuantosCuidan) {
		this.cuantosCuidan = cuantosCuidan;
	}

	public String getNombreCDI() {
		return nombreCDI;
	}

	public void setNombreCDI(String nombreCDI) {
		this.nombreCDI = nombreCDI;
	}

	public String getDireccionCDI() {
		return direccionCDI;
	}

	public void setDireccionCDI(String direccionCDI) {
		this.direccionCDI = direccionCDI;
	}

	public String getOtroLugarCuidan() {
		return otroLugarCuidan;
	}

	public void setOtroLugarCuidan(String otroLugarCuidan) {
		this.otroLugarCuidan = otroLugarCuidan;
	}

	public String getAlfabeto() {
		return alfabeto;
	}

	public void setAlfabeto(String alfabeto) {
		this.alfabeto = alfabeto;
	}

	public String getNivelEducacion() {
		return nivelEducacion;
	}

	public void setNivelEducacion(String nivelEducacion) {
		this.nivelEducacion = nivelEducacion;
	}

	public String getTrabaja() {
		return trabaja;
	}

	public void setTrabaja(String trabaja) {
		this.trabaja = trabaja;
	}

	public String getTipoTrabajo() {
		return tipoTrabajo;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	public String getOcupacionActual() {
		return ocupacionActual;
	}

	public void setOcupacionActual(String ocupacionActual) {
		this.ocupacionActual = ocupacionActual;
	}

	public String getPersonaVive() {
		return personaVive;
	}

	public void setPersonaVive(String personaVive) {
		this.personaVive = personaVive;
	}

	public Integer getOrdenNac() {
		return ordenNac;
	}

	public void setOrdenNac(Integer ordenNac) {
		this.ordenNac = ordenNac;
	}

	public String getPadreAlfabeto() {
		return padreAlfabeto;
	}

	public void setPadreAlfabeto(String padreAlfabeto) {
		this.padreAlfabeto = padreAlfabeto;
	}

	public String getNivelEscolarPadre() {
		return nivelEscolarPadre;
	}

	public void setNivelEscolarPadre(String nivelEscolarPadre) {
		this.nivelEscolarPadre = nivelEscolarPadre;
	}

	public String getTrabajaPadre() {
		return trabajaPadre;
	}

	public void setTrabajaPadre(String trabajaPadre) {
		this.trabajaPadre = trabajaPadre;
	}

	public String getTipoTrabajoPadre() {
		return tipoTrabajoPadre;
	}

	public void setTipoTrabajoPadre(String tipoTrabajoPadre) {
		this.tipoTrabajoPadre = tipoTrabajoPadre;
	}

	public String getMadreAlfabeta() {
		return madreAlfabeta;
	}

	public void setMadreAlfabeta(String madreAlfabeta) {
		this.madreAlfabeta = madreAlfabeta;
	}

	public String getNivelEscolarMadre() {
		return nivelEscolarMadre;
	}

	public void setNivelEscolarMadre(String nivelEscolarMadre) {
		this.nivelEscolarMadre = nivelEscolarMadre;
	}

	public String getTrabajaMadre() {
		return trabajaMadre;
	}

	public void setTrabajaMadre(String trabajaMadre) {
		this.trabajaMadre = trabajaMadre;
	}

	public String getTipoTrabajoMadre() {
		return tipoTrabajoMadre;
	}

	public void setTipoTrabajoMadre(String tipoTrabajoMadre) {
		this.tipoTrabajoMadre = tipoTrabajoMadre;
	}

	public String getComparteHab() {
		return comparteHab;
	}

	public void setComparteHab(String comparteHab) {
		this.comparteHab = comparteHab;
	}

	public Integer getHab1() {
		return hab1;
	}

	public void setHab1(Integer hab1) {
		this.hab1 = hab1;
	}

	public Integer getHab2() {
		return hab2;
	}

	public void setHab2(Integer hab2) {
		this.hab2 = hab2;
	}

	public Integer getHab3() {
		return hab3;
	}

	public void setHab3(Integer hab3) {
		this.hab3 = hab3;
	}

	public Integer getHab4() {
		return hab4;
	}

	public void setHab4(Integer hab4) {
		this.hab4 = hab4;
	}

	public Integer getHab5() {
		return hab5;
	}

	public void setHab5(Integer hab5) {
		this.hab5 = hab5;
	}

	public String getComparteCama() {
		return comparteCama;
	}

	public void setComparteCama(String comparteCama) {
		this.comparteCama = comparteCama;
	}

	public Integer getCama1() {
		return cama1;
	}

	public void setCama1(Integer cama1) {
		this.cama1 = cama1;
	}

	public Integer getCama2() {
		return cama2;
	}

	public void setCama2(Integer cama2) {
		this.cama2 = cama2;
	}

	public Integer getCama3() {
		return cama3;
	}

	public void setCama3(Integer cama3) {
		this.cama3 = cama3;
	}

	public Integer getCama4() {
		return cama4;
	}

	public void setCama4(Integer cama4) {
		this.cama4 = cama4;
	}

	public Integer getCama5() {
		return cama5;
	}

	public void setCama5(Integer cama5) {
		this.cama5 = cama5;
	}

	public String getFuma() {
		return fuma;
	}

	public void setFuma(String fuma) {
		this.fuma = fuma;
	}

	public String getPeriodicidadFuma() {
		return periodicidadFuma;
	}

	public void setPeriodicidadFuma(String periodicidadFuma) {
		this.periodicidadFuma = periodicidadFuma;
	}

	public Integer getCantidadCigarrillos() {
		return cantidadCigarrillos;
	}

	public void setCantidadCigarrillos(Integer cantidadCigarrillos) {
		this.cantidadCigarrillos = cantidadCigarrillos;
	}

	public String getFumaDentroCasa() {
		return fumaDentroCasa;
	}

	public void setFumaDentroCasa(String fumaDentroCasa) {
		this.fumaDentroCasa = fumaDentroCasa;
	}

	public String getTuberculosisPulmonarActual() {
		return tuberculosisPulmonarActual;
	}

	public void setTuberculosisPulmonarActual(String tuberculosisPulmonarActual) {
		this.tuberculosisPulmonarActual = tuberculosisPulmonarActual;
	}

	public Integer getAnioDiagTubpulActual() {
		return anioDiagTubpulActual;
	}

	public void setAnioDiagTubpulActual(Integer anioDiagTubpulActual) {
		this.anioDiagTubpulActual = anioDiagTubpulActual;
	}

	public String getMesDiagTubpulActual() {
		return mesDiagTubpulActual;
	}

	public void setMesDiagTubpulActual(String mesDiagTubpulActual) {
		this.mesDiagTubpulActual = mesDiagTubpulActual;
	}

	public String getTratamientoTubpulActual() {
		return tratamientoTubpulActual;
	}

	public void setTratamientoTubpulActual(String tratamientoTubpulActual) {
		this.tratamientoTubpulActual = tratamientoTubpulActual;
	}

	public String getCompletoTratamientoTubpulActual() {
		return completoTratamientoTubpulActual;
	}

	public void setCompletoTratamientoTubpulActual(String completoTratamientoTubpulActual) {
		this.completoTratamientoTubpulActual = completoTratamientoTubpulActual;
	}

	public String getTuberculosisPulmonarPasado() {
		return tuberculosisPulmonarPasado;
	}

	public void setTuberculosisPulmonarPasado(String tuberculosisPulmonarPasado) {
		this.tuberculosisPulmonarPasado = tuberculosisPulmonarPasado;
	}

	public String getFechaDiagTubpulPasadoDes() {
		return fechaDiagTubpulPasadoDes;
	}

	public void setFechaDiagTubpulPasadoDes(String fechaDiagTubpulPasadoDes) {
		this.fechaDiagTubpulPasadoDes = fechaDiagTubpulPasadoDes;
	}

	public Integer getAnioDiagTubpulPasado() {
		return anioDiagTubpulPasado;
	}

	public void setAnioDiagTubpulPasado(Integer anioDiagTubpulPasado) {
		this.anioDiagTubpulPasado = anioDiagTubpulPasado;
	}

	public String getMesDiagTubpulPasado() {
		return mesDiagTubpulPasado;
	}

	public void setMesDiagTubpulPasado(String mesDiagTubpulPasado) {
		this.mesDiagTubpulPasado = mesDiagTubpulPasado;
	}

	public String getTratamientoTubpulPasado() {
		return tratamientoTubpulPasado;
	}

	public void setTratamientoTubpulPasado(String tratamientoTubpulPasado) {
		this.tratamientoTubpulPasado = tratamientoTubpulPasado;
	}

	public String getCompletoTratamientoTubpulPas() {
		return completoTratamientoTubpulPas;
	}

	public void setCompletoTratamientoTubpulPas(String completoTratamientoTubpulPas) {
		this.completoTratamientoTubpulPas = completoTratamientoTubpulPas;
	}

	public String getAlergiaRespiratoria() {
		return alergiaRespiratoria;
	}

	public void setAlergiaRespiratoria(String alergiaRespiratoria) {
		this.alergiaRespiratoria = alergiaRespiratoria;
	}

	public Integer getAnioAlergiaRespiratoria() {
		return anioAlergiaRespiratoria;
	}

	public void setAnioAlergiaRespiratoria(Integer anioAlergiaRespiratoria) {
		this.anioAlergiaRespiratoria = anioAlergiaRespiratoria;
	}

	public String getAsma() {
		return asma;
	}

	public void setAsma(String asma) {
		this.asma = asma;
	}

	public Integer getAnioAsma() {
		return anioAsma;
	}

	public void setAnioAsma(Integer anioAsma) {
		this.anioAsma = anioAsma;
	}

	public String getEnfermedadCronica() {
		return enfermedadCronica;
	}

	public void setEnfermedadCronica(String enfermedadCronica) {
		this.enfermedadCronica = enfermedadCronica;
	}

	public String getCardiopatia() {
		return cardiopatia;
	}

	public void setCardiopatia(String cardiopatia) {
		this.cardiopatia = cardiopatia;
	}

	public Integer getAnioCardiopatia() {
		return anioCardiopatia;
	}

	public void setAnioCardiopatia(Integer anioCardiopatia) {
		this.anioCardiopatia = anioCardiopatia;
	}

	public String getEnfermPulmonarObstCronica() {
		return enfermPulmonarObstCronica;
	}

	public void setEnfermPulmonarObstCronica(String enfermPulmonarObstCronica) {
		this.enfermPulmonarObstCronica = enfermPulmonarObstCronica;
	}

	public Integer getAnioEnfermPulmonarObstCronica() {
		return anioEnfermPulmonarObstCronica;
	}

	public void setAnioEnfermPulmonarObstCronica(Integer anioEnfermPulmonarObstCronica) {
		this.anioEnfermPulmonarObstCronica = anioEnfermPulmonarObstCronica;
	}

	public String getDiabetes() {
		return diabetes;
	}

	public void setDiabetes(String diabetes) {
		this.diabetes = diabetes;
	}

	public Integer getAnioDiabetes() {
		return anioDiabetes;
	}

	public void setAnioDiabetes(Integer anioDiabetes) {
		this.anioDiabetes = anioDiabetes;
	}

	public String getPresionAlta() {
		return presionAlta;
	}

	public void setPresionAlta(String presionAlta) {
		this.presionAlta = presionAlta;
	}

	public Integer getAnioPresionAlta() {
		return anioPresionAlta;
	}

	public void setAnioPresionAlta(Integer anioPresionAlta) {
		this.anioPresionAlta = anioPresionAlta;
	}

	public String getOtrasEnfermedades() {
		return otrasEnfermedades;
	}

	public void setOtrasEnfermedades(String otrasEnfermedades) {
		this.otrasEnfermedades = otrasEnfermedades;
	}

	public String getDescOtrasEnfermedades() {
		return descOtrasEnfermedades;
	}

	public void setDescOtrasEnfermedades(String descOtrasEnfermedades) {
		this.descOtrasEnfermedades = descOtrasEnfermedades;
	}

	public String getTenidoDengue() {
		return tenidoDengue;
	}

	public void setTenidoDengue(String tenidoDengue) {
		this.tenidoDengue = tenidoDengue;
	}

	public Integer getAnioDengue() {
		return anioDengue;
	}

	public void setAnioDengue(Integer anioDengue) {
		this.anioDengue = anioDengue;
	}

	public String getDiagMedicoDengue() {
		return diagMedicoDengue;
	}

	public void setDiagMedicoDengue(String diagMedicoDengue) {
		this.diagMedicoDengue = diagMedicoDengue;
	}

	public String getDondeDengue() {
		return dondeDengue;
	}

	public void setDondeDengue(String dondeDengue) {
		this.dondeDengue = dondeDengue;
	}

	public String getTenidoZika() {
		return tenidoZika;
	}

	public void setTenidoZika(String tenidoZika) {
		this.tenidoZika = tenidoZika;
	}

	public Integer getAnioZika() {
		return anioZika;
	}

	public void setAnioZika(Integer anioZika) {
		this.anioZika = anioZika;
	}

	public String getDiagMedicoZika() {
		return diagMedicoZika;
	}

	public void setDiagMedicoZika(String diagMedicoZika) {
		this.diagMedicoZika = diagMedicoZika;
	}

	public String getDondeZika() {
		return dondeZika;
	}

	public void setDondeZika(String dondeZika) {
		this.dondeZika = dondeZika;
	}

	public String getTenidoChik() {
		return tenidoChik;
	}

	public void setTenidoChik(String tenidoChik) {
		this.tenidoChik = tenidoChik;
	}

	public Integer getAnioChik() {
		return anioChik;
	}

	public void setAnioChik(Integer anioChik) {
		this.anioChik = anioChik;
	}

	public String getDiagMedicoChik() {
		return diagMedicoChik;
	}

	public void setDiagMedicoChik(String diagMedicoChik) {
		this.diagMedicoChik = diagMedicoChik;
	}

	public String getDondeChik() {
		return dondeChik;
	}

	public void setDondeChik(String dondeChik) {
		this.dondeChik = dondeChik;
	}

	public String getVacunaFiebreAma() {
		return vacunaFiebreAma;
	}

	public void setVacunaFiebreAma(String vacunaFiebreAma) {
		this.vacunaFiebreAma = vacunaFiebreAma;
	}

	public Integer getAnioVacunaFiebreAma() {
		return anioVacunaFiebreAma;
	}

	public void setAnioVacunaFiebreAma(Integer anioVacunaFiebreAma) {
		this.anioVacunaFiebreAma = anioVacunaFiebreAma;
	}

	public String getVacunaCovid() {
		return vacunaCovid;
	}

	public void setVacunaCovid(String vacunaCovid) {
		this.vacunaCovid = vacunaCovid;
	}

	public Integer getAnioVacunaCovid() {
		return anioVacunaCovid;
	}

	public void setAnioVacunaCovid(Integer anioVacunaCovid) {
		this.anioVacunaCovid = anioVacunaCovid;
	}

	public String getMesVacunaCovid() {
		return mesVacunaCovid;
	}

	public void setMesVacunaCovid(String mesVacunaCovid) {
		this.mesVacunaCovid = mesVacunaCovid;
	}

	public String getTieneTarjetaVacuna() {
		return tieneTarjetaVacuna;
	}

	public void setTieneTarjetaVacuna(String tieneTarjetaVacuna) {
		this.tieneTarjetaVacuna = tieneTarjetaVacuna;
	}

	public String getFiebre() {
		return fiebre;
	}

	public void setFiebre(String fiebre) {
		this.fiebre = fiebre;
	}

	public String getTiempoFiebre() {
		return tiempoFiebre;
	}

	public void setTiempoFiebre(String tiempoFiebre) {
		this.tiempoFiebre = tiempoFiebre;
	}

	public String getLugarConsFiebre() {
		return lugarConsFiebre;
	}

	public void setLugarConsFiebre(String lugarConsFiebre) {
		this.lugarConsFiebre = lugarConsFiebre;
	}

	public String getNoAcudioCs() {
		return noAcudioCs;
	}

	public void setNoAcudioCs(String noAcudioCs) {
		this.noAcudioCs = noAcudioCs;
	}

	public String getAutomedicoFiebre() {
		return automedicoFiebre;
	}

	public void setAutomedicoFiebre(String automedicoFiebre) {
		this.automedicoFiebre = automedicoFiebre;
	}

	public String getTenidoDengueUA() {
		return tenidoDengueUA;
	}

	public void setTenidoDengueUA(String tenidoDengueUA) {
		this.tenidoDengueUA = tenidoDengueUA;
	}

	public String getUnidadSaludDengue() {
		return unidadSaludDengue;
	}

	public void setUnidadSaludDengue(String unidadSaludDengue) {
		this.unidadSaludDengue = unidadSaludDengue;
	}

	public String getCentroSaludDengue() {
		return centroSaludDengue;
	}

	public void setCentroSaludDengue(String centroSaludDengue) {
		this.centroSaludDengue = centroSaludDengue;
	}

	public String getOtroCentroSaludDengue() {
		return otroCentroSaludDengue;
	}

	public void setOtroCentroSaludDengue(String otroCentroSaludDengue) {
		this.otroCentroSaludDengue = otroCentroSaludDengue;
	}

	public String getPuestoSaludDengue() {
		return puestoSaludDengue;
	}

	public void setPuestoSaludDengue(String puestoSaludDengue) {
		this.puestoSaludDengue = puestoSaludDengue;
	}

	public String getOtroPuestoSaludDengue() {
		return otroPuestoSaludDengue;
	}

	public void setOtroPuestoSaludDengue(String otroPuestoSaludDengue) {
		this.otroPuestoSaludDengue = otroPuestoSaludDengue;
	}

	public String getHospitalDengue() {
		return hospitalDengue;
	}

	public void setHospitalDengue(String hospitalDengue) {
		this.hospitalDengue = hospitalDengue;
	}

	public String getOtroHospitalDengue() {
		return otroHospitalDengue;
	}

	public void setOtroHospitalDengue(String otroHospitalDengue) {
		this.otroHospitalDengue = otroHospitalDengue;
	}

	public String getHospitalizadoDengue() {
		return hospitalizadoDengue;
	}

	public void setHospitalizadoDengue(String hospitalizadoDengue) {
		this.hospitalizadoDengue = hospitalizadoDengue;
	}

	public String getAmbulatorioDengue() {
		return ambulatorioDengue;
	}

	public void setAmbulatorioDengue(String ambulatorioDengue) {
		this.ambulatorioDengue = ambulatorioDengue;
	}

	public String getDiagnosticoDengue() {
		return diagnosticoDengue;
	}

	public void setDiagnosticoDengue(String diagnosticoDengue) {
		this.diagnosticoDengue = diagnosticoDengue;
	}

	public String getRashUA() {
		return rashUA;
	}

	public void setRashUA(String rashUA) {
		this.rashUA = rashUA;
	}

	public String getRecuerdaMesRash() {
		return recuerdaMesRash;
	}

	public void setRecuerdaMesRash(String recuerdaMesRash) {
		this.recuerdaMesRash = recuerdaMesRash;
	}

	public String getMesRash() {
		return mesRash;
	}

	public void setMesRash(String mesRash) {
		this.mesRash = mesRash;
	}

	public String getCaraRash() {
		return caraRash;
	}

	public void setCaraRash(String caraRash) {
		this.caraRash = caraRash;
	}

	public String getMiembrosSupRash() {
		return miembrosSupRash;
	}

	public void setMiembrosSupRash(String miembrosSupRash) {
		this.miembrosSupRash = miembrosSupRash;
	}

	public String getToraxRash() {
		return toraxRash;
	}

	public void setToraxRash(String toraxRash) {
		this.toraxRash = toraxRash;
	}

	public String getAbdomenRash() {
		return abdomenRash;
	}

	public void setAbdomenRash(String abdomenRash) {
		this.abdomenRash = abdomenRash;
	}

	public String getMiembrosInfRash() {
		return miembrosInfRash;
	}

	public void setMiembrosInfRash(String miembrosInfRash) {
		this.miembrosInfRash = miembrosInfRash;
	}

	public String getDiasRash() {
		return diasRash;
	}

	public void setDiasRash(String diasRash) {
		this.diasRash = diasRash;
	}

	public String getConsultaRashUA() {
		return consultaRashUA;
	}

	public void setConsultaRashUA(String consultaRashUA) {
		this.consultaRashUA = consultaRashUA;
	}

	public String getUnidadSaludRash() {
		return unidadSaludRash;
	}

	public void setUnidadSaludRash(String unidadSaludRash) {
		this.unidadSaludRash = unidadSaludRash;
	}

	public String getCentroSaludRash() {
		return centroSaludRash;
	}

	public void setCentroSaludRash(String centroSaludRash) {
		this.centroSaludRash = centroSaludRash;
	}

	public String getOtroCentroSaludRash() {
		return otroCentroSaludRash;
	}

	public void setOtroCentroSaludRash(String otroCentroSaludRash) {
		this.otroCentroSaludRash = otroCentroSaludRash;
	}

	public String getPuestoSaludRash() {
		return puestoSaludRash;
	}

	public void setPuestoSaludRash(String puestoSaludRash) {
		this.puestoSaludRash = puestoSaludRash;
	}

	public String getOtroPuestoSaludRash() {
		return otroPuestoSaludRash;
	}

	public void setOtroPuestoSaludRash(String otroPuestoSaludRash) {
		this.otroPuestoSaludRash = otroPuestoSaludRash;
	}

	public String getDiagnosticoRash() {
		return diagnosticoRash;
	}

	public void setDiagnosticoRash(String diagnosticoRash) {
		this.diagnosticoRash = diagnosticoRash;
	}

	public String getEstudiosAct() {
		return estudiosAct;
	}

	public void setEstudiosAct(String estudiosAct) {
		this.estudiosAct = estudiosAct;
	}
}
