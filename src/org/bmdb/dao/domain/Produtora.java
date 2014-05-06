/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Produtora extends DomainPojo {

    private int idProdutora;
    private String nomeProdutora;
    private String nacionalidadeProdutora;
    private String imagem;

    public Produtora(String nomeProdutora, String nacionalidadeProdutora, String imagem) {
        this.nomeProdutora = nomeProdutora;
        this.nacionalidadeProdutora = nacionalidadeProdutora;
        this.imagem = imagem;
    }

    public Produtora() {
        this.idProdutora = -1;
        this.nomeProdutora = null;
        this.nacionalidadeProdutora = null;
        this.imagem = null;
    }

    public int getId() {
        return idProdutora;
    }

    public void setIdProdutora(int idProdutora) {
        this.idProdutora = idProdutora;
    }

    public String getNomeProdutora() {
        return nomeProdutora;
    }

    public void setNomeProdutora(String nomeProdutora) {
        this.nomeProdutora = nomeProdutora;
    }

    public String getNacionalidadeProdutora() {
        return nacionalidadeProdutora;
    }

    public void setNacionalidadeProdutora(String nacionalidadeProdutora) {
        this.nacionalidadeProdutora = nacionalidadeProdutora;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return this.nomeProdutora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idProdutora;
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
        final Produtora other = (Produtora) obj;
        if (this.idProdutora != other.idProdutora) {
            return false;
        }
        return true;
    }
}
