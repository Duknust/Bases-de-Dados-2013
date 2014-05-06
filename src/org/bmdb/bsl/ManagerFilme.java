/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.bsl;

import java.util.List;
import java.util.Properties;
import org.bmdb.bsl.exceptions.BslInsertionFailedException;
import org.bmdb.bsl.exceptions.BslConnectionBrokerUnavailableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bmdb.dao.FilmeDAO;
import org.bmdb.dao.GeneroDAO;
import org.bmdb.dao.ProdutoraDAO;
import org.bmdb.dao.RealizadorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Filme;
import org.bmdb.dao.domain.Genero;
import org.bmdb.dao.domain.Personagem;
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.domain.Realizador;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class ManagerFilme extends GenericManager<Filme> implements IGenericManager<Filme> {

    private IConnectionBroker cb = null;
    private FilmeDAO filmeDAO = null;

    public ManagerFilme(Properties props) throws BslConnectionBrokerUnavailableException {
        try {
            this.cb = (props != null) ? ConnectionBrokerFactory.giveMeConnectionBrokerByName(props.getProperty("connectionbroker.impl"), props) : ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();
            filmeDAO = new FilmeDAO(cb);
        } catch (GenericDAOException ex) {
            throw new BslConnectionBrokerUnavailableException("Unable to load a connection broker", ex);
        }

    }

    public boolean insereNovo(Filme object) throws BslInsertionFailedException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.filmeDAO.insert(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed insertion of Filme", daoe);
        }
        return result;
    }

    public Filme procuraPorId(int idFilme) {
        Filme filme = null;
        try {
            filme = this.filmeDAO.getById(idFilme);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StatementExecuteDAOException ex) {
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filme;
    }

    @Override
    public boolean update(Filme object) throws BslInsertionFailedException {
        boolean result = false;
        try {
            if (valida(object)) {
                result = this.filmeDAO.update(object);
            }
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed update of Filme", daoe);
        }

        return result;
    }

    @Override
    public boolean remove(Filme object) throws BslInsertionFailedException {
        boolean result = false;
        try {
            result = this.filmeDAO.delete(object);
        } catch (GenericDAOException daoe) {
            throw new BslInsertionFailedException("Failed remove of Filme", daoe);
        }
        return result;
    }

    @Override
    protected boolean valida(Filme object) {
        int aValidarInt = -1;
        RealizadorDAO r = new RealizadorDAO(this.cb);
        ProdutoraDAO p = new ProdutoraDAO(this.cb);
        GeneroDAO generoDAO = new GeneroDAO(this.cb);

        if (object.getTitulo().length() >= 50) {
            return false;
        }
        if (object.getBudget() < 0) {
            return false;
        }
        if (object.getGross() < 0) {
            return false;
        }
        if (object.getPremiosNomeado() < 0) {
            return false;
        }
        if (object.getPremiosVencidos() < 0) {
            return false;
        }
        if (object.getPremiosNomeado() < object.getPremiosVencidos()) {
            return false;
        }
        if (object.getOscares() < 0) {
            return false;
        }
        if (object.getOscares() > object.getPremiosNomeado() || object.getOscares() > object.getPremiosVencidos()) {
            return false;
        }
        if (object.getRating() < 0 || object.getRating() > 21) {
            return false;
        }

        return true;
    }

    @Override
    public List<Filme> procuraPorCaracteristicas(Filme object) {
        List<Filme> filmes = null;
        try {
            filmes = filmeDAO.getByCriteria(object);
        } catch (GenericDAOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ManagerFilme.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }

        return filmes;
    }

    @Override
    public List<Filme> getAll() {
        List<Filme> filmes = null;
        filmeDAO = new FilmeDAO(cb);
        try {
            filmes = filmeDAO.getAll();
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filmes;
    }

    public boolean adicionaPersonagem(Filme filme, Personagem personagem) {
        boolean res = false;
        filmeDAO = new FilmeDAO(cb);
        try {
            res = filmeDAO.adicionaActorFilme(filme, personagem);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public boolean adicionaGenero(Filme filme, Genero genero) {
        boolean res = false;
        filmeDAO = new FilmeDAO(cb);
        try {
            res = filmeDAO.adicionaGeneroFilme(filme, genero);
        } catch (GenericDAOException ex) {
            Logger.getLogger(ManagerActor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
