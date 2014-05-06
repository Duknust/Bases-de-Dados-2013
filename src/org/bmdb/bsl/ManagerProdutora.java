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
import org.bmdb.dao.ProdutoraDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerProdutora extends GenericManager<Produtora> implements IGenericManager<Produtora> {

    private IConnectionBroker cb = null;
    private ProdutoraDAO produtoraDAO = null;

    public ManagerProdutora(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            produtoraDAO = new ProdutoraDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Produtora object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.produtoraDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Produtora", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Produtora object) throws GenericBslException {
        boolean result = false;
        try {
            result = this.produtoraDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Produtora", daoe);
        }
        return result;
    }

    @Override
    public boolean update(Produtora object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.produtoraDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Produtora", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Produtora object) {
        if (object.getNomeProdutora().length() > 25) {
            return false;
        }
        if (object.getNacionalidadeProdutora().length() > 20) {
            return false;
        }
        if (object.getImagem().length() > 30) {
            return false;
        }

        return true;
    }

    @Override
    public List<Produtora> procuraPorCaracteristicas(Produtora object) {
        List<Produtora> produtoras = new ArrayList<>();
        this.produtoraDAO = new ProdutoraDAO(cb);
        try {
            produtoras = produtoraDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return produtoras;
    }

    @Override
    public List<Produtora> getAll() {
        List<Produtora> produtoras = null;
        produtoraDAO = new ProdutoraDAO(cb);
        try {
            produtoras = produtoraDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtoras;
    }
}
