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

    public List<DogSupplies> getBedBySize(String sizeDog) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        List<DogSupplies> suppliesBed = em.createQuery("SELECT d FROM DogSupplies d WHERE d.size LIKE :size AND d.dogSuppliesPK.category = :category")
                .setParameter("size", sizeDog).setParameter("category", 1).getResultList();

        em.getTransaction().commit();
        em.close();
        return suppliesBed;
    }

    public List<DogSupplies> getAnotherSupplies() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        List<DogSupplies> supplies = em.createQuery("SELECT d FROM DogSupplies d WHERE d.dogSuppliesPK.category != :category")
                .setParameter("category", 1).getResultList();

        em.getTransaction().commit();
        em.close();
        return supplies;
    }
}
