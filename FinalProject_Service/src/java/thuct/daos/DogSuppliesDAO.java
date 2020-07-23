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

    public DogSupplies getSuppliesForPrice(String sizeDog, int category, Float price) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        DogSupplies priceSupplies = (DogSupplies) em.createQuery(
                "SELECT d FROM DogSupplies d "
                + "WHERE d.size IN (:size, 'For all dogs') "
                + "AND d.dogSuppliesPK.category = :category "
                + "AND d.price <= :price"
                + "ORDER BY d.price DESC "
                + "LIMIT 1")
                .setParameter("size", sizeDog)
                .setParameter("category", category)
                .setParameter("price", price)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        return priceSupplies;
    }

    public DogSupplies getMaxPrice(String sizeDog, int category) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        DogSupplies maxPrice = (DogSupplies) em.createQuery(
                "SELECT d FROM DogSupplies d "
                + "WHERE d.size IN (:size, 'For all dogs') "
                + "AND d.dogSuppliesPK.category = :category "
                + "ORDER BY d.price DESC "
                + "LIMIT 1")
                .setParameter("size", sizeDog).setParameter("category", category).getSingleResult();
        em.getTransaction().commit();
        em.close();
        return maxPrice;
    }

    public DogSupplies getMinPrice(String sizeDog, int category) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        DogSupplies minPrice = (DogSupplies) em.createQuery(
                "SELECT d FROM DogSupplies d "
                + "WHERE d.size IN (:size, 'For all dogs') "
                + "AND d.dogSuppliesPK.category = :category "
                + "ORDER BY d.price ASC "
                + "LIMIT 1")
                .setParameter("size", sizeDog).setParameter("category", category).getSingleResult();
        em.getTransaction().commit();
        em.close();
        return minPrice;
    }

}
