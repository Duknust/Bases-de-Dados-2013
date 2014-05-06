/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.bsl;

import java.util.List;
import org.bmdb.bsl.exceptions.BslInsertionFailedException;
import org.bmdb.bsl.exceptions.GenericBslException;
import org.bmdb.dao.domain.Filme;

/**
 *
 * @author duarteduarte
 */
public abstract class GenericManager<T> implements IGenericManager<T> {

    public GenericManager() {
    }

    @Override
    public abstract boolean insereNovo(T object) throws GenericBslException;

    @Override
    public abstract boolean remove(T object) throws GenericBslException;

    @Override
    public abstract boolean update(T object) throws GenericBslException;

    protected abstract boolean valida(T object);

    public abstract List<T> procuraPorCaracteristicas(T object);

    public abstract List<T> getAll();
}
