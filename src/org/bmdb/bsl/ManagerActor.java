/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.bsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bmdb.bsl.exceptions.BslConnectionBrokerUnavailableException;
import org.bmdb.bsl.exceptions.BslInsertionFailedException;
import org.bmdb.bsl.exceptions.GenericBslException;
import org.bmdb.dao.ActorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerActor extends GenericManager<Actor> implements IGenericManager<Actor> {

    private IConnectionBroker cb = null;
    private ActorDAO actorDAO = null;

    public ManagerActor(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            actorDAO = new ActorDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Actor object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.actorDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Actor", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Actor object) throws GenericBslException {
        boolean result = false;
        try {
            result = this.actorDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Actor", daoe);
        }
        return result;
    }

    @Override
    public boolean update(Actor object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.actorDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Actor", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Actor object) {
        if (object.getNomeActor().length() > 25) {
            return false;
        }
        if (object.getDataNascimentoActor().length() > 10) {
            return false;
        }
        if (object.getDataMorteActor() != null) {
            if (object.getDataMorteActor().length() > 10) {
                return false;
            }
        }
        if (object.getPremiosNomeadoActor() < 0) {
            return false;
        }
        if (object.getPremiosVencidosActor() < 0) {
            return false;
        }
        if (object.getPremiosNomeadoActor() < object.getPremiosVencidosActor()) {
            return false;
        }
        if (object.getOscaresVencidos() < 0) {
            return false;
        }
        if (object.getOscaresVencidos() > object.getPremiosNomeadoActor() || object.getOscaresVencidos() > object.getPremiosVencidosActor()) {
            return false;
        }
        if (object.getNacionalidadeActor().length() > 20) {
            return false;
        }
        if (object.getImagem().length() > 30) {
            return false;
        }
        return true;
    }

    @Override
    public List<Actor> procuraPorCaracteristicas(Actor object) {
        List<Actor> actores = new ArrayList<>();
        this.actorDAO = new ActorDAO(cb);
        try {
            actores = actorDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return actores;
    }

    @Override
    public List<Actor> getAll() {
        List<Actor> actores = null;
        actorDAO = new ActorDAO(cb);
        try {
            actores = actorDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actores;
    }
}
