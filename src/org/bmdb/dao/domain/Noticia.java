/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Noticia extends DomainPojo {

    private int idNoticia;
    private String fonte;
    private String data;
    private String titulo;
    private String sinopse;
    private String corpo;
    private String imagem;
    private String video;
    private int idFilme;
    private Filme filme;

    public Noticia(String fonte, String data, String titulo, String sinopse, String corpo, String imagem, String video, int idFilme, Filme filme) {
        this.fonte = fonte;
        this.data = data;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.corpo = corpo;
        this.imagem = imagem;
        this.video = video;
        this.idFilme = idFilme;
        this.filme = filme;
    }

    public Noticia() {
        this.idNoticia = -1;
        this.fonte = null;
        this.data = null;
        this.titulo = null;
        this.sinopse = null;
        this.corpo = null;
        this.imagem = null;
        this.video = null;
        this.idFilme = -1;
        this.filme = null;
    }

    @Override
    public int getId() {
        return idNoticia;
    }

    public void setIdActor(int idActor) {
        this.idNoticia = idActor;
    }

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
