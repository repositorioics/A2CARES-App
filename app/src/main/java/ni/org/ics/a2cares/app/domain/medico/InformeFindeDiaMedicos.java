package ni.org.ics.a2cares.app.domain.medico;

import java.io.Serializable;
import java.util.Date;

import ni.org.ics.a2cares.app.domain.BaseMetaData;
import ni.org.ics.a2cares.app.domain.core.Participante;


/**
 * Simple objeto de dominio que representa los datos de fin de dia de laboratorio del médico
 * 
 * @author Everts Morales Reyes.
 **/


public class InformeFindeDiaMedicos extends BaseMetaData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */

	private Integer id;
	private Date  fechaConsulta;
	private String puestoSalud;
	private Integer numPartCohorte;

	private String  codPartAtend1;
	private String  codPartAtend2;
	private String  codPartAtend3;
	private String  codPartAtend4;
	private String  codPartAtend5;
	private String  codPartAtend6;
	private String  codPartAtend7;
	private String  codPartAtend8;
	private String  codPartAtend9;
	private String  codPartAtend10;
	private String  codPartAtend11;
	private String  codPartAtend12;
	private String  codPartAtend13;
	private String  codPartAtend14;
	private String  codPartAtend15;
	private String  codPartAtend16;
	private String  codPartAtend17;
	private String  codPartAtend18;
	private String  codPartAtend19;
	private String  codPartAtend20;

	private String  codPartAtend1Diagnostico;
	private String  codPartAtend2Diagnostico;
	private String  codPartAtend3Diagnostico;
	private String  codPartAtend4Diagnostico;
	private String  codPartAtend5Diagnostico;
	private String  codPartAtend6Diagnostico;
	private String  codPartAtend7Diagnostico;
	private String  codPartAtend8Diagnostico;
	private String  codPartAtend9Diagnostico;
	private String  codPartAtend10Diagnostico;
	private String  codPartAtend11Diagnostico;
	private String  codPartAtend12Diagnostico;
	private String  codPartAtend13Diagnostico;
	private String  codPartAtend14Diagnostico;
	private String  codPartAtend15Diagnostico;
	private String  codPartAtend16Diagnostico;
	private String  codPartAtend17Diagnostico;
	private String  codPartAtend18Diagnostico;
	private String  codPartAtend19Diagnostico;
	private String  codPartAtend20Diagnostico;

	private Integer numPartNoCohorte;
	private Integer numTotalAtendidos;
	private Integer numFebrilA;
	private Integer numInRespAgudaA;
	private Integer numEnfDiarreaAgudaA;
	private Integer numETI;
	private Integer numRAG;
	private Integer numConjuntivitis;
	private Integer numControlPrenatal;
	private Integer numNeumonia;
	private Integer numPap;
	private Integer numPlanificacionFam;
	private Integer numGotaGruesa;
	private Integer numCronicos;
	private Integer numTraslados;
	private Integer numCaptacionA;
	private Integer numCaptacionB;
	private Integer numCaptacionC;
	private Integer numCaptacionD;
	private Integer numSeguimientoA;
	private Integer numSeguimientoB;
	private Integer numSeguimientoD;
	private Integer numConvPendPuesto;
	private Integer numVisitaAterreno;
	private Integer numTrasladosDengue;

	private String  codPartTraslado1;
	private String  codPartTraslado2;
	private String  codPartTraslado3;
	private String  codPartTraslado4;
	private String  codPartTraslado5;
	private String  codPartTraslado6;
	private String  codPartTraslado7;
	private String  codPartTraslado8;
	private String  codPartTraslado9;
	private String  codPartTraslado10;

	private String  codPartTraslado1Diagnostico;
	private String  codPartTraslado2Diagnostico;
	private String  codPartTraslado3Diagnostico;
	private String  codPartTraslado4Diagnostico;
	private String  codPartTraslado5Diagnostico;
	private String  codPartTraslado6Diagnostico;
	private String  codPartTraslado7Diagnostico;
	private String  codPartTraslado8Diagnostico;
	private String  codPartTraslado9Diagnostico;
	private String  codPartTraslado10Diagnostico;

	private Integer numNegatTrasladosDengue;
	private String  codNegatPartTraslado1;
	private String  codNegaPartTraslado2;
	private String  codNegaPartTraslado3;
	private String  codNegaPartTraslado4;
	private String  codNegaPartTraslado5;
	private String  codNegaPartTraslado6;
	private String  codNegaPartTraslado7;
	private String  codNegaPartTraslado8;
	private String  codNegaPartTraslado9;
	private String  codNegaPartTraslado10;

	private String  codNegaPartTraslado1Diagnostico;
	private String  codNegaPartTraslado2Diagnostico;
	private String  codNegaPartTraslado3Diagnostico;
	private String  codNegaPartTraslado4Diagnostico;
	private String  codNegaPartTraslado5Diagnostico;
	private String  codNegaPartTraslado6Diagnostico;
	private String  codNegaPartTraslado7Diagnostico;
	private String  codNegaPartTraslado8Diagnostico;
	private String  codNegaPartTraslado9Diagnostico;
	private String  codNegaPartTraslado10Diagnostico;
	private String nomMedico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public String getPuestoSalud() {
		return puestoSalud;
	}
	public void setPuestoSalud(String puestoSalud) {
		this.puestoSalud = puestoSalud;
	}

	public Integer getNumPartCohorte() {
		return numPartCohorte;
	}
	public void setNumPartCohorte(Integer numPartCohorte) {
		this.numPartCohorte = numPartCohorte;
	}

	public String getCodPartAtend1() {
		return codPartAtend1;
	}
	public void setCodPartAtend1(String codPartAtend1) {
		this.codPartAtend1 = codPartAtend1;
	}

	public String getCodPartAtend2 () {
		return codPartAtend2;
	}
	public void setCodPartAtend2(String codPartAtend2) {
		this.codPartAtend2 = codPartAtend2;
	}

	public String getCodPartAtend3() {
		return codPartAtend3;
	}
	public void setCodPartAtend3(String codPartAtend3) {
		this.codPartAtend3 = codPartAtend3;
	}

	public String getCodPartAtend4() {
		return codPartAtend4;
	}
	public void setCodPartAtend4(String codPartAtend4) {
		this.codPartAtend4 = codPartAtend4;
	}

	public String getCodPartAtend5() {
		return codPartAtend5;
	}
	public void setCodPartAtend5(String codPartAtend5) {
		this.codPartAtend5 = codPartAtend5;
	}

	public String getCodPartAtend6() {
		return codPartAtend6;
	}
	public void setCodPartAtend6(String codPartAtend6) {
		this.codPartAtend6 = codPartAtend6;
	}

	public String getCodPartAtend7() {
		return codPartAtend7;
	}
	public void setCodPartAtend7(String codPartAtend7) {
		this.codPartAtend7 = codPartAtend7;
	}

	public String getCodPartAtend8() {
		return codPartAtend8;
	}
	public void setCodPartAtend8(String codPartAtend8) {
		this.codPartAtend8 = codPartAtend8;
	}

	public String getCodPartAtend9() {
		return codPartAtend9;
	}
	public void setCodPartAtend9(String codPartAtend9) {
		this.codPartAtend9 = codPartAtend9;
	}

	public String getCodPartAtend10() {
		return codPartAtend10;
	}
	public void setCodPartAtend10(String codPartAtend10) {
		this.codPartAtend10 = codPartAtend10;
	}

	public String getCodPartAtend11() {
		return codPartAtend11;
	}
	public void setCodPartAtend11(String codPartAtend11) {
		this.codPartAtend11 = codPartAtend11;
	}

	public String getCodPartAtend12() {
		return codPartAtend12;
	}
	public void setCodPartAtend12(String codPartAtend12) {
		this.codPartAtend12 = codPartAtend12;
	}

	public String getCodPartAtend13() {
		return codPartAtend13;
	}
	public void setCodPartAtend13(String codPartAtend13) {
		this.codPartAtend13 = codPartAtend13;
	}

	public String getCodPartAtend14() {
		return codPartAtend14;
	}
	public void setCodPartAtend14(String codPartAtend14) {
		this.codPartAtend14 = codPartAtend14;
	}

	public String getCodPartAtend15() {
		return codPartAtend15;
	}
	public void setCodPartAtend15(String codPartAtend15) {
		this.codPartAtend15 = codPartAtend15;
	}

	public String getCodPartAtend16() {
		return codPartAtend16;
	}
	public void setCodPartAtend16(String codPartAtend16) {
		this.codPartAtend16 = codPartAtend16;
	}

	public String getCodPartAtend17() {
		return codPartAtend17;
	}
	public void setCodPartAtend17(String codPartAtend17) {
		this.codPartAtend17 = codPartAtend17;
	}

	public String getCodPartAtend18() {
		return codPartAtend18;
	}
	public void setCodPartAtend18(String codPartAtend18) {
		this.codPartAtend18 = codPartAtend18;
	}

	public String getCodPartAtend19() {
		return codPartAtend19;
	}
	public void setCodPartAtend19(String codPartAtend19) {
		this.codPartAtend19 = codPartAtend19;
	}

	public String getCodPartAtend20() {
		return codPartAtend20;
	}
	public void setCodPartAtend20(String codPartAtend20) {
		this.codPartAtend20 = codPartAtend20;
	}

	/**/

	public String getCodPartAtend1Diagnostico() {
		return codPartAtend1Diagnostico;
	}
	public void setCodPartAtend1Diagnostico(String codPartAtend1Diagnostico) {
		this.codPartAtend1Diagnostico = codPartAtend1Diagnostico;
	}

	public String getCodPartAtend2Diagnostico() {
		return codPartAtend2Diagnostico;
	}
	public void setCodPartAtend2Diagnostico(String codPartAtend2Diagnostico) {
		this.codPartAtend2Diagnostico = codPartAtend2Diagnostico;
	}

	public String getCodPartAtend3Diagnostico() {
		return codPartAtend3Diagnostico;
	}
	public void setCodPartAtend3Diagnostico(String codPartAtend3Diagnostico) {
		this.codPartAtend3Diagnostico = codPartAtend3Diagnostico;
	}

	public String getCodPartAtend4Diagnostico() {
		return codPartAtend4Diagnostico;
	}
	public void setCodPartAtend4Diagnostico(String codPartAtend4Diagnostico) {
		this.codPartAtend4Diagnostico = codPartAtend4Diagnostico;
	}

	public String getCodPartAtend5Diagnostico() {
		return codPartAtend5Diagnostico;
	}
	public void setCodPartAtend5Diagnostico(String codPartAtend5Diagnostico) {
		this.codPartAtend5Diagnostico = codPartAtend5Diagnostico;
	}

	public String getCodPartAtend6Diagnostico() {
		return codPartAtend6Diagnostico;
	}
	public void setCodPartAtend6Diagnostico(String codPartAtend6Diagnostico) {
		this.codPartAtend6Diagnostico = codPartAtend6Diagnostico;
	}

	public String getCodPartAtend7Diagnostico() {
		return codPartAtend7Diagnostico;
	}
	public void setCodPartAtend7Diagnostico(String codPartAtend7Diagnostico) {
		this.codPartAtend7Diagnostico = codPartAtend7Diagnostico;
	}

	public String getCodPartAtend8Diagnostico() {
		return codPartAtend8Diagnostico;
	}
	public void setCodPartAtend8Diagnostico(String codPartAtend8Diagnostico) {
		this.codPartAtend8Diagnostico = codPartAtend8Diagnostico;
	}

	public String getCodPartAtend9Diagnostico() {
		return codPartAtend9Diagnostico;
	}
	public void setCodPartAtend9Diagnostico(String codPartAtend9Diagnostico) {
		this.codPartAtend9Diagnostico = codPartAtend9Diagnostico;
	}

	public String getCodPartAtend10Diagnostico() {
		return codPartAtend10Diagnostico;
	}
	public void setCodPartAtend10Diagnostico(String codPartAtend10Diagnostico) {
		this.codPartAtend10Diagnostico = codPartAtend10Diagnostico;
	}

	public String getCodPartAtend11Diagnostico() {
		return codPartAtend11Diagnostico;
	}
	public void setCodPartAtend11Diagnostico(String codPartAtend11Diagnostico) {
		this.codPartAtend11Diagnostico = codPartAtend11Diagnostico;
	}

	public String getCodPartAtend12Diagnostico() {
		return codPartAtend12Diagnostico;
	}
	public void setCodPartAtend12Diagnostico(String codPartAtend12Diagnostico) {
		this.codPartAtend12Diagnostico = codPartAtend12Diagnostico;
	}

	public String getCodPartAtend13Diagnostico() {
		return codPartAtend13Diagnostico;
	}
	public void setCodPartAtend13Diagnostico(String codPartAtend13Diagnostico) {
		this.codPartAtend13Diagnostico = codPartAtend13Diagnostico;
	}

	public String getCodPartAtend14Diagnostico() {
		return codPartAtend14Diagnostico;
	}
	public void setCodPartAtend14Diagnostico(String codPartAtend14Diagnostico) {
		this.codPartAtend14Diagnostico = codPartAtend14Diagnostico;
	}

	public String getCodPartAtend15Diagnostico() {
		return codPartAtend15Diagnostico;
	}
	public void setCodPartAtend15Diagnostico(String codPartAtend15Diagnostico) {
		this.codPartAtend15Diagnostico = codPartAtend15Diagnostico;
	}

	public String getCodPartAtend16Diagnostico() {
		return codPartAtend16Diagnostico;
	}
	public void setCodPartAtend16Diagnostico(String codPartAtend16Diagnostico) {
		this.codPartAtend16Diagnostico = codPartAtend16Diagnostico;
	}

	public String getCodPartAtend17Diagnostico() {
		return codPartAtend17Diagnostico;
	}
	public void setCodPartAtend17Diagnostico(String codPartAtend17Diagnostico) {
		this.codPartAtend17Diagnostico = codPartAtend17Diagnostico;
	}

	public String getCodPartAtend18Diagnostico() {
		return codPartAtend18Diagnostico;
	}
	public void setCodPartAtend18Diagnostico(String codPartAtend18Diagnostico) {
		this.codPartAtend18Diagnostico = codPartAtend18Diagnostico;
	}

	public String getCodPartAtend19Diagnostico() {
		return codPartAtend19Diagnostico;
	}
	public void setCodPartAtend19Diagnostico(String codPartAtend19Diagnostico) {
		this.codPartAtend19Diagnostico = codPartAtend19Diagnostico;
	}

	public String getCodPartAtend20Diagnostico() {
		return codPartAtend20Diagnostico;
	}
	public void setCodPartAtend20Diagnostico(String codPartAtend20Diagnostico) {
		this.codPartAtend20Diagnostico = codPartAtend20Diagnostico;
	}
	/**/

	public String getCodPartTraslado1() {
		return codPartTraslado1;
	}
	public void setCodPartTraslado1(String codPartTraslado1) {
		this.codPartTraslado1 = codPartTraslado1;
	}

	public String getCodPartTraslado2() {
		return codPartTraslado2;
	}
	public void setCodPartTraslado2(String codPartTraslado2) {
		this.codPartTraslado2 = codPartTraslado2;
	}

	public String getCodPartTraslado3() {
		return codPartTraslado3;
	}
	public void setCodPartTraslado3(String codPartTraslado3) {
		this.codPartTraslado3 = codPartTraslado3;
	}

	public String getCodPartTraslado4() {
		return codPartTraslado4;
	}
	public void setCodPartTraslado4(String codPartTraslado4) {
		this.codPartTraslado4 = codPartTraslado4;
	}

	public String getCodPartTraslado5() {
		return codPartTraslado5;
	}
	public void setCodPartTraslado5(String codPartTraslado5) {
		this.codPartTraslado5 = codPartTraslado5;
	}

	public String getCodPartTraslado6() {
		return codPartTraslado6;
	}
	public void setCodPartTraslado6(String codPartTraslado6) {
		this.codPartTraslado6 = codPartTraslado6;
	}

	public String getCodPartTraslado7() {
		return codPartTraslado7;
	}
	public void setCodPartTraslado7(String codPartTraslado7) {
		this.codPartTraslado7 = codPartTraslado7;
	}

	public String getCodPartTraslado8() {
		return codPartTraslado8;
	}
	public void setCodPartTraslado8(String codPartTraslado8) {
		this.codPartTraslado8 = codPartTraslado8;
	}

	public String getCodPartTraslado9() {
		return codPartTraslado9;
	}
	public void setCodPartTraslado9(String codPartTraslado9) {
		this.codPartTraslado9 = codPartTraslado9;
	}

	public String getCodPartTraslado10() {
		return codPartTraslado10;
	}
	public void setCodPartTraslado10(String codPartTraslado10) {
		this.codPartTraslado10 = codPartTraslado10;
	}

	/**/

	public String getCodPartTraslado1Diagnostico() {
		return codPartTraslado1Diagnostico;
	}
	public void setCodPartTraslado1Diagnostico(String codPartTraslado1Diagnostico) {
		this.codPartTraslado1Diagnostico = codPartTraslado1Diagnostico;
	}

	public String getCodPartTraslado2Diagnostico() {
		return codPartTraslado2Diagnostico;
	}
	public void setCodPartTraslado2Diagnostico(String codPartTraslado2Diagnostico) {
		this.codPartTraslado2Diagnostico = codPartTraslado2Diagnostico;
	}

	public String getCodPartTraslado3Diagnostico() {
		return codPartTraslado3Diagnostico;
	}
	public void setCodPartTraslado3Diagnostico(String codPartTraslado3Diagnostico) {
		this.codPartTraslado3Diagnostico = codPartTraslado3Diagnostico;
	}

	public String getCodPartTraslado4Diagnostico() {
		return codPartTraslado4Diagnostico;
	}
	public void setCodPartTraslado4Diagnostico(String codPartTraslado4Diagnostico) {
		this.codPartTraslado4Diagnostico = codPartTraslado4Diagnostico;
	}

	public String getCodPartTraslado5Diagnostico() {
		return codPartTraslado5Diagnostico;
	}
	public void setCodPartTraslado5Diagnostico(String codPartTraslado5Diagnostico) {
		this.codPartTraslado5Diagnostico = codPartTraslado5Diagnostico;
	}

	public String getCodPartTraslado6Diagnostico() {
		return codPartTraslado6Diagnostico;
	}
	public void setCodPartTraslado6Diagnostico(String codPartTraslado6Diagnostico) {
		this.codPartTraslado6Diagnostico = codPartTraslado6Diagnostico;
	}

	public String getCodPartTraslado7Diagnostico() {
		return codPartTraslado7Diagnostico;
	}
	public void setCodPartTraslado7Diagnostico(String codPartTraslado7Diagnostico) {
		this.codPartTraslado7Diagnostico = codPartTraslado7Diagnostico;
	}

	public String getCodPartTraslado8Diagnostico() {
		return codPartTraslado8Diagnostico;
	}
	public void setCodPartTraslado8Diagnostico(String codPartTraslado8Diagnostico) {
		this.codPartTraslado8Diagnostico = codPartTraslado8Diagnostico;
	}

	public String getCodPartTraslado9Diagnostico() {
		return codPartTraslado9Diagnostico;
	}
	public void setCodPartTraslado9Diagnostico(String codPartTraslado9Diagnostico) {
		this.codPartTraslado9Diagnostico = codPartTraslado9Diagnostico;
	}

	public String getCodPartTraslado10Diagnostico() {
		return codPartTraslado10Diagnostico;
	}
	public void setCodPartTraslado10Diagnostico(String codPartTraslado10Diagnostico) {
		this.codPartTraslado10Diagnostico = codPartTraslado10Diagnostico;
	}

	/**/


	public String getCodNegatPartTraslado1() {
		return codNegatPartTraslado1;
	}
	public void setCodNegatPartTraslado1(String codNegatPartTraslado1) {
		this.codNegatPartTraslado1 = codNegatPartTraslado1;
	}

	public String getCodNegatPartTraslado2() {
		return codNegaPartTraslado2;
	}
	public void setCodNegatPartTraslado2(String codNegaPartTraslado2) {
		this.codNegaPartTraslado2 = codNegaPartTraslado2;
	}

	public String getCodNegatPartTraslado3() {
		return codNegaPartTraslado3;
	}
	public void setCodNegatPartTraslado3(String codNegaPartTraslado3) {
		this.codNegaPartTraslado3 = codNegaPartTraslado3;
	}

	public String getCodNegatPartTraslado4() {
		return codNegaPartTraslado4;
	}
	public void setCodNegatPartTraslado4(String codNegaPartTraslado4) {
		this.codNegaPartTraslado4 = codNegaPartTraslado4;
	}

	public String getCodNegatPartTraslado5() {
		return codNegaPartTraslado5;
	}
	public void setCodNegatPartTraslado5(String codNegaPartTraslado5) {
		this.codNegaPartTraslado5 = codNegaPartTraslado5;
	}

	public String getCodNegatPartTraslado6() {
		return codNegaPartTraslado6;
	}
	public void setCodNegatPartTraslado6(String codNegaPartTraslado6) {
		this.codNegaPartTraslado6 = codNegaPartTraslado6;
	}

	public String getCodNegatPartTraslado7() {
		return codNegaPartTraslado7;
	}
	public void setCodNegatPartTraslado7(String codNegaPartTraslado7) {
		this.codNegaPartTraslado7 = codNegaPartTraslado7;
	}

	public String getCodNegatPartTraslado8() {
		return codNegaPartTraslado8;
	}
	public void setCodNegatPartTraslado8(String codNegaPartTraslado8) {
		this.codNegaPartTraslado8 = codNegaPartTraslado8;
	}

	public String getCodNegatPartTraslado9() {
		return codNegaPartTraslado9;
	}
	public void setCodNegatPartTraslado9(String codNegaPartTraslado9) {
		this.codNegaPartTraslado9 = codNegaPartTraslado9;
	}
	public String getCodNegatPartTraslado10() {
		return codNegaPartTraslado10;
	}
	public void setCodNegatPartTraslado10(String codNegaPartTraslado10) {
		this.codNegaPartTraslado10 = codNegaPartTraslado10;
	}

	/**/

	public String getCodNegatPartTraslado1Diagnostico() {
		return codNegaPartTraslado1Diagnostico;
	}
	public void setCodNegatPartTraslado1Diagnostico(String codNegaPartTraslado1Diagnostico) {
		this.codNegaPartTraslado1Diagnostico = codNegaPartTraslado1Diagnostico;
	}

	public String getCodNegatPartTraslado2Diagnostico() {
		return codNegaPartTraslado2Diagnostico;
	}
	public void setCodNegatPartTraslado2Diagnostico(String codNegaPartTraslado2Diagnostico) {
		this.codNegaPartTraslado2Diagnostico = codNegaPartTraslado2Diagnostico;
	}

	public String getCodNegatPartTraslado3Diagnostico() {
		return codNegaPartTraslado3Diagnostico;
	}
	public void setCodNegatPartTraslado3Diagnostico(String codNegaPartTraslado3Diagnostico) {
		this.codNegaPartTraslado3Diagnostico = codNegaPartTraslado3Diagnostico;
	}

	public String getCodNegatPartTraslado4Diagnostico() {
		return codNegaPartTraslado4Diagnostico;
	}
	public void setCodNegatPartTraslado4Diagnostico(String codNegaPartTraslado4Diagnostico) {
		this.codNegaPartTraslado4Diagnostico = codNegaPartTraslado4Diagnostico;
	}

	public String getCodNegatPartTraslado5Diagnostico() {
		return codNegaPartTraslado5Diagnostico;
	}
	public void setCodNegatPartTraslado5Diagnostico(String codNegaPartTraslado5Diagnostico) {
		this.codNegaPartTraslado5Diagnostico = codNegaPartTraslado5Diagnostico;
	}

	public String getCodNegatPartTraslado6Diagnostico() {
		return codNegaPartTraslado6Diagnostico;
	}
	public void setCodNegatPartTraslado6Diagnostico(String codNegaPartTraslado6Diagnostico) {
		this.codNegaPartTraslado6Diagnostico = codNegaPartTraslado6Diagnostico;
	}

	public String getCodNegatPartTraslado7Diagnostico() {
		return codNegaPartTraslado7Diagnostico;
	}
	public void setCodNegatPartTraslado7Diagnostico(String codNegaPartTraslado7Diagnostico) {
		this.codNegaPartTraslado7Diagnostico = codNegaPartTraslado7Diagnostico;
	}

	public String getCodNegatPartTraslado8Diagnostico() {
		return codNegaPartTraslado8Diagnostico;
	}
	public void setCodNegatPartTraslado8Diagnostico(String codNegaPartTraslado8Diagnostico) {
		this.codNegaPartTraslado8Diagnostico = codNegaPartTraslado8Diagnostico;
	}

	public String getCodNegatPartTraslado9Diagnostico() {
		return codNegaPartTraslado9Diagnostico;
	}
	public void setCodNegatPartTraslado9Diagnostico(String codNegaPartTraslado9Diagnostico) {
		this.codNegaPartTraslado9Diagnostico = codNegaPartTraslado9Diagnostico;
	}

	public String getCodNegatPartTraslado10Diagnostico() {
		return codNegaPartTraslado10Diagnostico;
	}
	public void setCodNegatPartTraslado10Diagnostico(String codNegaPartTraslado10Diagnostico) {
		this.codNegaPartTraslado10Diagnostico = codNegaPartTraslado10Diagnostico;
	}
	/**/

	public Integer getNumPartNoCohorte() {
		return numPartNoCohorte;
	}
	public void setNumPartNoCohorte(Integer numPartNoCohorte) {
		this.numPartNoCohorte = numPartNoCohorte;
	}

	public Integer getNumTotalAtendidos() {
		return numTotalAtendidos;
	}
	public void setNumTotalAtendidos(Integer numTotalAtendidos) {
		this.numTotalAtendidos = numTotalAtendidos;
	}

	public Integer getNumFebrilA() {
		return numFebrilA;
	}
	public void setNumFebrilA(Integer numFebrilA) {
		this.numFebrilA = numFebrilA;
	}

	public Integer getNumInRespAgudaA() {
		return numInRespAgudaA;
	}
	public void setNumInRespAgudaA(Integer numInRespAgudaA) {
		this.numInRespAgudaA = numInRespAgudaA;
	}

	public Integer getNumEnfDiarreaAgudaA() {
		return numEnfDiarreaAgudaA;
	}
	public void setNumEnfDiarreaAgudaA(Integer numEnfDiarreaAgudaA) {
		this.numEnfDiarreaAgudaA = numEnfDiarreaAgudaA;
	}

	public Integer getNumETI() {
		return numETI;
	}
	public void setNumETI(Integer numETI) {
		this.numETI = numETI;
	}

	public Integer getNumRAG() {
		return numRAG;
	}
	public void setNumRAG(Integer numRAG) {
		this.numRAG = numRAG;
	}

	public Integer getNumConjuntivitis() {
		return numConjuntivitis;
	}
	public void setNumConjuntivitis(Integer numConjuntivitis) {
		this.numConjuntivitis = numConjuntivitis;
	}

	public Integer getNumControlPrenatal() {
		return numControlPrenatal;
	}
	public void setNumControlPrenatal(Integer numControlPrenatal) {
		this.numControlPrenatal = numControlPrenatal;
	}

	public Integer getNumNeumonia() {
		return numNeumonia;
	}
	public void setNumNeumonia(Integer numNeumonia) {
		this.numNeumonia = numNeumonia;
	}

	public Integer getNumPap() {
		return numPap;
	}
	public void setNumPap(Integer numPap) {
		this.numPap = numPap;
	}

	public Integer getNumPlanificacionFam() {
		return numPlanificacionFam;
	}
	public void setNumPlanificacionFam(Integer numPlanificacionFam) {
		this.numPlanificacionFam = numPlanificacionFam;
	}

	public Integer getNumGotaGruesa() {
		return numGotaGruesa;
	}
	public void setNumGotaGruesa(Integer numGotaGruesa) {
		this.numGotaGruesa = numGotaGruesa;
	}

	public Integer getNumCronicos() {
		return numCronicos;
	}
	public void setNumCronicos(Integer numCronicos) {
		this.numCronicos = numCronicos;
	}

	public Integer getNumTraslados() {
		return numTraslados;
	}
	public void setNumTraslados(Integer numTraslados) {
		this.numTraslados = numTraslados;
	}

	public Integer getNumCaptacionA() {
		return numCaptacionA;
	}
	public void setNumCaptacionA(Integer numCaptacionA) {
		this.numCaptacionA = numCaptacionA;
	}

	public Integer getNumCaptacionB() {
		return numCaptacionB;
	}
	public void setNumCaptacionB(Integer numCaptacionB) {
		this.numCaptacionB = numCaptacionB;
	}

	public Integer getNumCaptacionC() {
		return numCaptacionC;
	}
	public void setNumCaptacionC(Integer numCaptacionC) {
		this.numCaptacionC = numCaptacionC;
	}

	public Integer getNumCaptacionD() {
		return numCaptacionD;
	}
	public void setNumCaptacionD(Integer numCaptacionD) {
		this.numCaptacionD = numCaptacionD;
	}

	public Integer getNumSeguimientoA() {
		return numSeguimientoA;
	}
	public void setNumSeguimientoA(Integer numSeguimientoA) {
		this.numSeguimientoA = numSeguimientoA;
	}

	public Integer getNumSeguimientoB() {
		return numSeguimientoB;
	}
	public void setNumSeguimientoB(Integer numSeguimientoB) {
		this.numSeguimientoB = numSeguimientoB;
	}

	public Integer getNumSeguimientoD() {
		return numSeguimientoD;
	}
	public void setNumSeguimientoD(Integer numSeguimientoD) {
		this.numSeguimientoD = numSeguimientoD;
	}

	public Integer getNumConvPendPuesto() {
		return numConvPendPuesto;
	}
	public void setNumConvPendPuesto(Integer numConvPendPuesto) {
		this.numConvPendPuesto = numConvPendPuesto;
	}

	public Integer getNumVisitaAterreno() {
		return numVisitaAterreno;
	}
	public void setNumVisitaAterreno(Integer numVisitaAterreno) {
		this.numVisitaAterreno = numVisitaAterreno;
	}

	public Integer getNumTrasladosDengue() {
		return numTrasladosDengue;
	}
	public void setNumTrasladosDengue(Integer numTrasladosDengue) {
		this.numTrasladosDengue = numTrasladosDengue;
	}


	public Integer getNumNegatTrasladosDengue() {
		return numNegatTrasladosDengue;
	}
	public void setNumNegatTrasladosDengue(Integer numNegatTrasladosDengue) {
		this.numNegatTrasladosDengue = numNegatTrasladosDengue;
	}

	public String getNomMedico() {
		return nomMedico;
	}
	public void setNomMedico(String nomMedico) {
		this.nomMedico = nomMedico;
	}

}