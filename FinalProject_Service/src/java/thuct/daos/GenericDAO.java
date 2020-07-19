/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import thuct.dtos.DogBreed;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
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
