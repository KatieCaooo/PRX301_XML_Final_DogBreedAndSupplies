/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import thuct.dtos.DogSupplies;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
 */
public class DogSuppliesDAO implements Serializable {

    public List<DogSupplies> getSupplies(String sizeDog) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        List<DogSupplies> suppliesBed = em.createQuery("SELECT d FROM DogSupplies d WHERE d.size IN (:size, 'For all dogs') ORDER BY d.dogSuppliesPK.category ASC")
                .setParameter("size", sizeDog).getResultList();
        em.getTransaction().commit();
        em.close();
        return suppliesBed;
    }
}
