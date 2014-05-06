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
import org.bmdb.dao.NoticiaDAO;
import org.bmdb.dao.ProdutoraDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Noticia;
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerNoticia extends GenericManager<Noticia> implements IGenericManager<Noticia> {

    private IConnectionBroker cb = null;
    private NoticiaDAO noticiaDAO = null;

    public ManagerNoticia(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            noticiaDAO = new NoticiaDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }
    }

    @Override
    public boolean insereNovo(Noticia object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.noticiaDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Noticia", daoe);
        }
        return result;
    }

    @Override
    public boolean remove(Noticia object) throws GenericBslException {
        boolean result = false;
        try {
            result = this.noticiaDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Noticia", daoe);
        }
        return result;
    }

    @Override
    public boolean update(Noticia object) throws GenericBslException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.noticiaDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Noticia", daoe);
        }

        return result;
    }

    @Override
    protected boolean valida(Noticia object) {
        if (object.getFonte().length() > 40) {
            return false;
        }
        if (object.getData().length() > 10) {
            return false;
        }
        if (object.getTitulo().length() > 100) {
            return false;
        }
        if (object.getSinopse().length() > 1000) {
            return false;
        }
        if (object.getCorpo().length() > 4000) {
            return false;
        }
        if (object.getImagem().length() > 37) {
            return false;
        }
        if (object.getVideo().length() > 20) {
            return false;
        }

        return true;
    }

    @Override
    public List<Noticia> procuraPorCaracteristicas(Noticia object) {
        List<Noticia> noticias = new ArrayList<>();
        this.noticiaDAO = new NoticiaDAO(cb);
        try {
            noticias = noticiaDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return noticias;
    }

    @Override
    public List<Noticia> getAll() {
        List<Noticia> noticias = null;
        noticiaDAO = new NoticiaDAO(cb);
        try {
            noticias = noticiaDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noticias;
    }
}
