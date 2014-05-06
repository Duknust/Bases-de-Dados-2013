/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.bsl.exceptions;

import org.bmdb.dao.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public class BslInsertionFailedException extends GenericBslException {

    public BslInsertionFailedException(String failed_insertion_of_Filme, GenericDAOException daoe) {
    }
}
