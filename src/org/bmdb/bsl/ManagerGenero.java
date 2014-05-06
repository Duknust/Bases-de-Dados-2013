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
import org.bmdb.dao.GeneroDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Genero;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerGenero extends GenericManager<Genero> implements IGenericManager<Genero> {

    private IConnectionBroker cb = null;
    private GeneroDAO generoDAO = null;

    public ManagerGenero(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            generoDAO = new GeneroDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Genero object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.generoDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Genero", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Genero object) throws GenericBslException {
        boolean result = false;
        try {
            result = this.generoDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Genero", daoe);
        }

        return result;
    }

    @Override
    public boolean update(Genero object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.generoDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Genero", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Genero object) {

        if (object.getNomeGenero().length() > 30) {
            return false;
        }
        return true;
    }

    @Override
    public List<Genero> procuraPorCaracteristicas(Genero object) {
        List<Genero> generos = new ArrayList<>();
        this.generoDAO = new GeneroDAO(cb);
        try {
            generos = generoDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return generos;
    }

    @Override
    public List<Genero> getAll() {
        List<Genero> generos = null;
        generoDAO = new GeneroDAO(cb);
        try {
            generos = generoDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generos;
    }
}
