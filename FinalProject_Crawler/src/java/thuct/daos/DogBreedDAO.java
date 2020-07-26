/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import javax.persistence.EntityManager;
import thuct.dtos.DogBreed;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
 */
public class DogBreedDAO {

    public void insertDogBreed(DogBreed dogBreed) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.persist(dogBreed);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
