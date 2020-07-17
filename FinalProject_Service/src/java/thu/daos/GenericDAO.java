/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thu.daos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import thu.dtos.DogBreed;
import thu.utils.JPAUtil;

/**
 *
 * @author katherinecao
 */
public class GenericDAO<T> implements Serializable {

    private Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<T> result = em.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }

}
