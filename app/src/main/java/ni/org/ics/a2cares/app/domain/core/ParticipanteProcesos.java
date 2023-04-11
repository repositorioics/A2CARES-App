package ni.org.ics.a2cares.app.domain.core;

import java.io.Serializable;

import ni.org.ics.a2cares.app.domain.BaseMetaData;

/**
 * Created by Miguel Salinas on 21/7/2021.
 */
public class ParticipanteProcesos extends BaseMetaData implements Serializable {

    private String codigo;
    private Integer retirado;
    private String pendientePyT;
    private String pendienteEncPart;
    private String pendienteEnCasa;
    private String pendienteMxMA;
    private String pendienteMxTx;
    private String pendienteObseq;
    //Indica realizar encuensta de satisfaccion de usuario
    private String esatUsuario;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getRetirado() {
        return retirado;
    }

    public void setRetirado(Integer retirado) {
        this.retirado = retirado;
    }

    public String getPendientePyT() {
        return pendientePyT;
    }

    public void setPendientePyT(String pendientePyT) {
        this.pendientePyT = pendientePyT;
    }

    public String getPendienteEncPart() {
        return pendienteEncPart;
    }

    public void setPendienteEncPart(String pendienteEncPart) {
        this.pendienteEncPart = pendienteEncPart;
    }

    public String getPendienteEnCasa() {
        return pendienteEnCasa;
    }

    public void setPendienteEnCasa(String pendienteEnCasa) {
        this.pendienteEnCasa = pendienteEnCasa;
    }

    public String getPendienteMxMA() {
        return pendienteMxMA;
    }

    public void setPendienteMxMA(String pendienteMxMA) {
        this.pendienteMxMA = pendienteMxMA;
    }

    public String getPendienteMxTx() {
        return pendienteMxTx;
    }

    public void setPendienteMxTx(String pendienteMxTx) {
        this.pendienteMxTx = pendienteMxTx;
    }

    public String getPendienteObseq() {
        return pendienteObseq;
    }

    public void setPendienteObseq(String pendienteObseq) {
        this.pendienteObseq = pendienteObseq;
    }

    public String getEsatUsuario() {
        return esatUsuario;
    }

    public void setEsatUsuario(String esatUsuario) {
        this.esatUsuario = esatUsuario;
    }
}
