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
public class Filme extends DomainPojo {

    private int idFilme;
    private String titulo;
    private List<Genero> generos; //deixou de existir aqui
    private Realizador realizador;
    private Produtora produtora;
    private int ano;
    //private double pontuacao; //deixou de existir aqui
    //private int pontuacaoMax; //deixou de existir aqui
    //private int pontuacaoMin; //deixou de existir aqui
    //private int numeroVotos; //deixou de existir aqui
    private int budget;
    private long gross;
    private int premiosNomeado;
    private int premiosVencidos;
    private List<Personagem> personagens; //deixou de existir aqui
    private int oscares;
    private int rating;
    private String sinopse;
    private String trailerUri;
    private String posterUri;

    public Filme(String titulo, List<Genero> generos, Realizador realizador, Produtora produtora, int ano, int budget, long gross, int premiosNomeado, int premiosVencidos, int oscares, List<Personagem> personagens, int rating, String sinopse, String trailerUri, String posterUri) {
        this.titulo = titulo;
        this.generos = generos;
        this.realizador = realizador;
        this.produtora = produtora;
        this.ano = ano;
        this.budget = budget;
        this.gross = gross;
        this.premiosNomeado = premiosNomeado;
        this.premiosVencidos = premiosVencidos;
        this.oscares = oscares;
        this.personagens = personagens;
        this.rating = rating;
        this.sinopse = sinopse;
        this.trailerUri = trailerUri;
        this.posterUri = posterUri;
    }

    public Filme() {
        this.idFilme = -1;
        this.titulo = null;
        this.realizador = null;
        this.produtora = null;
        this.ano = -1;
        this.budget = -1;
        this.gross = -1;
        this.premiosNomeado = -1;
        this.premiosVencidos = -1;
        this.oscares = -1;
        this.rating = -1;
        this.sinopse = null;
        this.trailerUri = null;
        this.posterUri = null;
    }

    public int getId() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Realizador getRealizador() {
        return realizador;
    }

    public void setRealizador(Realizador realizador) {
        this.realizador = realizador;
    }

    public Produtora getProdutora() {
        return produtora;
    }

    public void setProdutora(Produtora produtora) {
        this.produtora = produtora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public long getGross() {
        return gross;
    }

    public void setGross(long gross) {
        this.gross = gross;
    }

    public int getPremiosNomeado() {
        return premiosNomeado;
    }

    public void setPremiosNomeado(int premiosNomeado) {
        this.premiosNomeado = premiosNomeado;
    }

    public int getPremiosVencidos() {
        return premiosVencidos;
    }

    public void setPremiosVencidos(int premiosVencidos) {
        this.premiosVencidos = premiosVencidos;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTrailerUri() {
        return trailerUri;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public int getOscares() {
        return oscares;
    }

    public void setOscares(int oscares) {
        this.oscares = oscares;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Personagem> getPersonagens() {
        return personagens;
    }

    public void setPersonagens(List<Personagem> personagens) {
        this.personagens = personagens;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setTrailerUri(String trailerUri) {
        this.trailerUri = trailerUri;
    }

    public void setPosterUri(String posterUri) {
        this.posterUri = posterUri;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Filme other = (Filme) obj;
        if (this.idFilme != other.idFilme) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.ano + " - " + this.titulo;
    }
}
