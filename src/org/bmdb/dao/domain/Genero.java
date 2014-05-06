/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Genero extends DomainPojo {

    private int idGenero;
    private String nomeGenero;

    public Genero(String nomeGenero) {
        this.nomeGenero = nomeGenero;
    }

    public Genero() {
        this.idGenero = -1;
        this.nomeGenero = null;
    }

    public int getId() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNomeGenero() {
        return nomeGenero;
    }

    public void setNomeGenero(String nomeGenero) {
        this.nomeGenero = nomeGenero;
    }

    @Override
    public String toString() {
        return this.nomeGenero;
    }
}
