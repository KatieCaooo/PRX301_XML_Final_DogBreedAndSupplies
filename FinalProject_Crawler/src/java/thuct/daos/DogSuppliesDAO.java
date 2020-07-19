/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import javax.persistence.EntityManager;
import thuct.dtos.DogSupplies;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
 */
public class DogSuppliesDAO {

    public DogSupplies findDogSuppliesById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        DogSupplies supplies = (DogSupplies) entityManager.createQuery("SELECT d FROM DogSupplies d WHERE d.iddogSupplies = :iddogSupplies")
                .setParameter("iddogSupplies", id).getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return supplies;
    }

    public DogSupplies findDogSuppliesByName(String name) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        DogSupplies supplies = (DogSupplies) entityManager.createQuery("SELECT d FROM DogSupplies d WHERE d.name = :name")
                .setParameter("name", name).getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return supplies;
    }

    public DogSupplies findDogSuppliesBySize(String size) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        DogSupplies supplies = (DogSupplies) entityManager.createQuery("SELECT d FROM DogSupplies d WHERE d.size LIKE :size")
                .setParameter("size", size).getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return supplies;
    }

    public void insertDogSupplies(DogSupplies dogSupplies) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.persist(dogSupplies);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
