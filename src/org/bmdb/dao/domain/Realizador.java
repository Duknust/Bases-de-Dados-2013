/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Realizador extends DomainPojo {

    private int idRealizador;
    private String nomeRealizador;
    private String dataNascimentoRealizador;
    private String nacionalidadeRealizador;
    private String sexo;
    private String imagem;

    public Realizador(String nomeRealizador, String dataNascimentoRealizador, String nacionalidadeRealizador, String sexo, String imagem) {
        this.nomeRealizador = nomeRealizador;
        this.dataNascimentoRealizador = dataNascimentoRealizador;
        this.nacionalidadeRealizador = nacionalidadeRealizador;
        this.sexo = sexo;
        this.imagem = imagem;
    }

    public Realizador() {
        this.idRealizador = -1;
        this.nomeRealizador = null;
        this.dataNascimentoRealizador = null;
        this.nacionalidadeRealizador = null;
        this.imagem = null;
    }

    public int getId() {
        return idRealizador;
    }

    public void setIdRealizador(int idRealizador) {
        this.idRealizador = idRealizador;
    }

    public String getNomeRealizador() {
        return nomeRealizador;
    }

    public void setNomeRealizador(String nomeRealizador) {
        this.nomeRealizador = nomeRealizador;
    }

    public String getDataNascimentoRealizador() {
        return dataNascimentoRealizador;
    }

    public void setDataNascimentoRealizador(String dataNascimentoRealizador) {
        this.dataNascimentoRealizador = dataNascimentoRealizador;
    }

    public String getNacionalidadeRealizador() {
        return nacionalidadeRealizador;
    }

    public void setNacionalidadeRealizador(String nacionalidadeRealizador) {
        this.nacionalidadeRealizador = nacionalidadeRealizador;
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

    @Override
    public String toString() {
        return this.nomeRealizador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.idRealizador;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Realizador other = (Realizador) obj;
        if (this.idRealizador != other.idRealizador) {
            return false;
        }
        return true;
    }
}
