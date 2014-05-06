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
import org.bmdb.dao.UtilizadorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Utilizador;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerUtilizador extends GenericManager<Utilizador> implements IGenericManager<Utilizador> {

    private IConnectionBroker cb = null;
    private UtilizadorDAO utilizadorDAO = null;

    public ManagerUtilizador(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            utilizadorDAO = new UtilizadorDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Utilizador object) throws GenericBslException {
        boolean result = false;
        if (object == null) {
            throw new IllegalArgumentException("Utilizador não pode ser null");
        }

        try {
            if (valida(object)) {
                result = this.utilizadorDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Utilizador", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Utilizador object) throws GenericBslException {
        boolean result = false;
        if (object == null) {
            throw new IllegalArgumentException("Utilizador não pode ser null");
        }

        try {
            result = this.utilizadorDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Utilizador", daoe);
        }
        return result;
    }

    @Override
    public boolean update(Utilizador object) throws GenericBslException {
        boolean result = false;
        if (object == null) {
            throw new IllegalArgumentException("Utilizador não pode ser null");
        }

        try {
            if (valida(object)) {
                result = this.utilizadorDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Utilizador", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Utilizador object) {
        if (object.getUsername().length() > 20) {
            return false;
        }
        if (object.getEmail().length() > 32) {
            return false;
        }
        if (object.getDataNascimento().length() > 10) {
            return false;
        }
        if (object.getAvatar().length() > 40) {
            return false;
        }
        return true;
    }

    @Override
    public List<Utilizador> procuraPorCaracteristicas(Utilizador object) {
        List<Utilizador> utilizadores = new ArrayList<>();
        this.utilizadorDAO = new UtilizadorDAO(cb);

        try {
            utilizadores = utilizadorDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return utilizadores;
    }

    @Override
    public List<Utilizador> getAll() {
        List<Utilizador> utilizadores = null;
        utilizadorDAO = new UtilizadorDAO(cb);
        try {
            utilizadores = utilizadorDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerUtilizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utilizadores;
    }
}
