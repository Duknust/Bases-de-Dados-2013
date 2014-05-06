/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.DomainPojo;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public abstract class GenericDAO<T extends DomainPojo> implements IGenericDAO<T> {

    protected IConnectionBroker cb;

    /*public GenericDAO(IConnectionBroker cb) {
     this.cb = cb;
     this.cb.init();
     }*/
    protected DataSource executeStatement(Connection conn, String sqlStatement) throws DatabaseConnectionDAOException, StatementExecuteDAOException {

        // Prepare a statement to cleanup the employees table
        try {
            Statement stmt = conn.createStatement();
            try {
                stmt.execute(sqlStatement);
            } finally {
                // Close the statement
                stmt.close();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Unable to execute statement", sqle);
        }

        return null;
    }

    @Override
    public abstract List<T> getAll() throws GenericDAOException;

    @Override
    public abstract T getById(int id) throws GenericDAOException;

    @Override
    public abstract boolean insert(T object) throws GenericDAOException;

    @Override
    public abstract boolean delete(T object) throws GenericDAOException;

    @Override
    public abstract boolean update(T object) throws GenericDAOException;

    @Override
    public abstract boolean exists(T object) throws GenericDAOException;

    @Override
    public abstract List<T> getByCriteria(T object) throws GenericDAOException;
}
