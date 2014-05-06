/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

import java.util.List;

/**
 *
 * @author duarteduarte
 */
public class Actor extends DomainPojo {

    private int idActor;
    private String nomeActor;
    private String dataNascimentoActor;
    private String dataMorteActor;
    private String nacionalidadeActor;
    private int premiosNomeadoActor;
    private int premiosVencidosActor;
    private int oscaresVencidos;
    private String sexo;
    private String imagem;
    private List<Filme> filmesActor;

    public Actor(String nomeActor, String dataNascimentoActor, String dataMorteActor, String nacionalidadeActor, int premiosNomeadoActor, int premiosVencidosActor, int oscaresVencidos, String sexo, String imagem) {
        this.nomeActor = nomeActor;
        this.dataNascimentoActor = dataNascimentoActor;
        this.dataMorteActor = dataMorteActor;
        this.nacionalidadeActor = nacionalidadeActor;
        this.premiosNomeadoActor = premiosNomeadoActor;
        this.premiosVencidosActor = premiosVencidosActor;
        this.oscaresVencidos = oscaresVencidos;
        this.sexo = sexo;
        this.imagem = imagem;
    }

    public Actor() {
        this.idActor = -1;
        this.nomeActor = null;
        this.dataNascimentoActor = null;
        this.dataMorteActor = null;
        this.nacionalidadeActor = null;
        this.premiosNomeadoActor = -1;
        this.premiosVencidosActor = -1;
        this.oscaresVencidos = -1;
        this.sexo = null;
        this.imagem = null;
    }

    @Override
    public int getId() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getNomeActor() {
        return nomeActor;
    }

    public void setNomeActor(String nomeActor) {
        this.nomeActor = nomeActor;
    }

    public String getDataNascimentoActor() {
        return dataNascimentoActor;
    }

    public void setDataNascimentoActor(String dataNascimentoActor) {
        this.dataNascimentoActor = dataNascimentoActor;
    }

    public String getNacionalidadeActor() {
        return nacionalidadeActor;
    }

    public void setNacionalidadeActor(String nacionalidadeActor) {
        this.nacionalidadeActor = nacionalidadeActor;
    }

    public int getPremiosNomeadoActor() {
        return premiosNomeadoActor;
    }

    public void setPremiosNomeadoActor(int premiosNomeadoActor) {
        this.premiosNomeadoActor = premiosNomeadoActor;
    }

    public int getPremiosVencidosActor() {
        return premiosVencidosActor;
    }

    public void setPremiosVencidosActor(int premiosVencidosActor) {
        this.premiosVencidosActor = premiosVencidosActor;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getOscaresVencidos() {
        return oscaresVencidos;
    }

    public void setOscaresVencidos(int oscaresVencidos) {
        this.oscaresVencidos = oscaresVencidos;
    }

    public String getDataMorteActor() {
        return dataMorteActor;
    }

    public void setDataMorteActor(String dataMorteActor) {
        this.dataMorteActor = dataMorteActor;
    }

    public List<Filme> getFilmesActor() {
        return filmesActor;
    }

    public void setFilmesActor(List<Filme> filmesActor) {
        this.filmesActor = filmesActor;
    }

    @Override
    public String toString() {
        return this.nomeActor;
    }
}
