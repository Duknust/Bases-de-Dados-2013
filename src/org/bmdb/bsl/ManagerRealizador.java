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
import org.bmdb.dao.RealizadorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Realizador;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerRealizador extends GenericManager<Realizador> implements IGenericManager<Realizador> {

    private IConnectionBroker cb = null;
    private RealizadorDAO realizadorDAO = null;

    public ManagerRealizador(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            realizadorDAO = new RealizadorDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Realizador object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.realizadorDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Realizador", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Realizador object) throws GenericBslException {
        boolean result = false;
        try {
            result = this.realizadorDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Realizador", daoe);
        }

        return result;
    }

    @Override
    public boolean update(Realizador object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.realizadorDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Realizador", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Realizador object) {
        if (object.getNomeRealizador().length() > 25) {
            return false;
        }
        if (object.getDataNascimentoRealizador().length() > 10) {
            return false;
        }
        if (object.getNacionalidadeRealizador().length() > 20) {
            return false;
        }
        if (object.getSexo().length() > 10) {
            return false;
        }
        if (object.getImagem().length() > 30) {
            return false;
        }

        return true;
    }

    @Override
    public List<Realizador> procuraPorCaracteristicas(Realizador object) {
        List<Realizador> realizadores = new ArrayList<>();
        this.realizadorDAO = new RealizadorDAO(cb);
        try {
            realizadores = realizadorDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return realizadores;
    }

    @Override
    public List<Realizador> getAll() {
        List<Realizador> realizadores = null;
        realizadorDAO = new RealizadorDAO(cb);
        try {
            realizadores = realizadorDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return realizadores;
    }
}
