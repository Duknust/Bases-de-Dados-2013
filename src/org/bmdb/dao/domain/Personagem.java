/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Personagem {

    private String nome;
    private Actor actor;

    public Personagem(String nome, Actor actor) {
        this.nome = nome;
        this.actor = actor;
    }

    public Personagem() {
        this.nome = null;
        this.actor = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return this.actor.toString() + " as " + this.nome;
    }
}
