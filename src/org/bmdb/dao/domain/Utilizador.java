/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.domain;

/**
 *
 * @author duarteduarte
 */
public class Utilizador extends DomainPojo {

    private int idUtilizador;
    private String username;
    private String email;
    private String password;
    private String dataNascimento;
    private String avatar;
    private int estado;
    //estado: 0-reservado 1-activo 2-inactivo

    public Utilizador(String username, String email, String dataNascimento, String avatar, int estado) {
        this.username = username;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.avatar = avatar;
        this.estado = estado;
    }

    public Utilizador() {
        this.idUtilizador = -1;
        this.username = null;
        this.email = null;
        this.password = null;
        this.dataNascimento = null;
        this.avatar = null;
        this.estado = -1;
    }

    public int getId() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
