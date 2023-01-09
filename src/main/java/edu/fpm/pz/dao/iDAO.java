package edu.fpm.pz.dao;

import java.util.List;

public interface iDAO<T1, T2> {
    /**
     * insert list of Entities
     */
    void insert(T1 entity);

    /**
     * get all records from Entity
     */
    List<T2> getAll();

    /**
     * get entity by id
     * @param id record id
     * @return
     */
    T1 getById(int id);

    /**
     * update record
     * @param entity entity
     */
    void update(T1 entity);

    /**
     * delete all from Entity
     */
    void deleteAll();

    /**
     * delete where entity_id = id
     *
     * @param id entity_id
     */
    void delete(int id);

    /**
     * get List of Ids
     *
     * @return List of Ids
     */
    List<Integer> getListOfId();
}