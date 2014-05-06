/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao;

import java.util.List;
import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public interface IGenericDAO<T> {

    public List<T> getAll() throws GenericDAOException;

    public T getById(int id) throws GenericDAOException;

    public boolean insert(T object) throws GenericDAOException;

    public boolean delete(T object) throws GenericDAOException;

    public boolean update(T object) throws GenericDAOException;

    public boolean exists(T object) throws GenericDAOException;

    public List<T> getByCriteria(T object) throws GenericDAOException;
}
