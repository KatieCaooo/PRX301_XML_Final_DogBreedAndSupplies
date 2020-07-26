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

    public void insertDogSupplies(DogSupplies dogSupplies) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.persist(dogSupplies);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
