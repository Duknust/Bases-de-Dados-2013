/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao.db;

import java.sql.Connection;
import java.util.Properties;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;

/**
 *
 * @author duarteduarte
 */
public interface IConnectionBroker {

    Connection getConnection() throws DatabaseConnectionDAOException;

    boolean destroy();

    boolean init();

    boolean setProperties(Properties ps);
}
