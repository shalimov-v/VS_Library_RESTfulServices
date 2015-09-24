package org.oa.vshalimov.library.dao;

import java.util.List;

public interface AbstractDAO<T> {

    List<T> loadAll();

    List<T> findByParameter(String parameter, String queryString);

    T findById(long itemId);

    boolean add(T itemToAdd);

    boolean delete(T itemToDelete);

    boolean update(T itemToUpdate);

}