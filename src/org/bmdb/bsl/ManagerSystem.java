/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.bsl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bmdb.bsl.exceptions.BslConnectionBrokerUnavailableException;
import org.bmdb.bsl.exceptions.BslInsertionFailedException;
import org.bmdb.bsl.exceptions.GenericBslException;
import org.bmdb.dao.ActorDAO;
import org.bmdb.dao.SystemDAO;
import org.bmdb.dao.UtilizadorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Filme;
import org.bmdb.dao.domain.Utilizador;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerSystem {

    private IConnectionBroker cb = null;
    private SystemDAO systemDAO = null;
    //COMO RESOLVER ISTO ;

    public ManagerSystem(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            systemDAO = new SystemDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    public boolean resetDB() throws GenericBslException {

        systemDAO.resetDB((Connection) this.cb);

        return true;
    }

    public Filme getFilmeMaisOscares() {
        Filme filme = null;
        try {
            filme = systemDAO.queryFilmeMaisOscares();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filme;
    }

    public String bestOfRealizador(int idRealizador) {
        String best = null;
        try {
            best = systemDAO.bestOfRealizador(idRealizador);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return best;
    }

    public void desactivaUsers() {
        try {
            systemDAO.desactivaUsers();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void activaUsers() {
        try {
            systemDAO.activaUsers();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Actor getActorMaisOscares() {
        Actor actor = null;
        try {
            actor = systemDAO.queryActorMaisOscares();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actor;
    }

    public Actor getActrizMaisOscares() {
        Actor actor = null;
        try {
            actor = systemDAO.queryActrizMaisOscares();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actor;
    }

    public Filme getFilmeMaisGerou() {
        Filme filme = null;
        try {
            filme = systemDAO.queryFilmeMaisGerou();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filme;
    }

    public Filme getFilmeMenosGerou() {
        Filme filme = null;
        try {
            filme = systemDAO.queryFilmeMenosGerou();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filme;
    }

    public Filme getFilmeMaisCaro() {
        Filme filme = null;
        try {
            filme = systemDAO.queryFilmeMaisCaro();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filme;
    }

    public List<Actor> getHallOfFame() {
        List<Actor> actores = null;
        try {
            actores = systemDAO.queryHallofFame();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actores;
    }

    public List<Actor> getBadLuckLosers() {
        List<Actor> actores = null;
        try {
            actores = systemDAO.queryBadLuckLosers();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actores;
    }

    public List<Actor> getBlessedWinners() {
        List<Actor> actores = null;
        try {
            actores = systemDAO.queryBlessedWinners();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actores;
    }

    public List<Actor> getTheOtherWorld() {
        List<Actor> actores = null;
        try {
            actores = systemDAO.queryTheOtherWorld();
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actores;
    }
}
