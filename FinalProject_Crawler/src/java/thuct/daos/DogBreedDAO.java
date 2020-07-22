/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import thuct.dtos.DogBreed;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
 */
public class DogBreedDAO {

    public DogBreed findDogBreedById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

//        DogBreed dog = entityManager.find(DogBreed.class, idList);
        DogBreed dog = (DogBreed) entityManager.createQuery("SELECT d FROM DogBreed d WHERE d.iddogBreed = :iddogBreed")
                .setParameter("iddogBreed", id).getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return dog;
    }

    public void insertDogBreed(DogBreed dogBreed) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.persist(dogBreed);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateDogBreed(DogBreed dogBreed) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.merge(dogBreed);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateDogBreedById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        DogBreed breed = entityManager.find(DogBreed.class, id);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void removeDogBreedById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        DogBreed dogBreed = entityManager.find(DogBreed.class, id);
        entityManager.remove(dogBreed);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

//    public void removeAllDogBreedNotFull(int id) {
//        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
//        entityManager.clear();
//        entityManager.getTransaction().begin();
//
//        Query query = entityManager.createQuery("DELETE FROM DogBreed d"
//                + " WHERE d.iddogBreed=:iddogBreed").setParameter("iddogBreed", id);
//        int executeUpdate = query.executeUpdate();
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }
//
//    public List getIdDogBreedNotFull() {
//        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
//        entityManager.clear();
//        entityManager.getTransaction().begin();
//
//        List id = (List) entityManager.createQuery("SELECT d.iddogBreed"
//                + " FROM DogBreed d"
//                + " WHERE d.apartmentFriendly + d.adaptability + d.barkingTendency "
//                + " + d.catFriendly + d.childFriendly + d.dogFriendly "
//                + " + d.exerciseNeed + d.grooming + d.healthIssuse "
//                + " + d.intelligence + d.playfulness + d.sheddingLevel "
//                + " + d.strangerFriendly + d.trainability + d.watchdogAbility = :number "
//                + " OR d.price = :number").setParameter("number", 0F).getResultList();
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        return id;
//    }
}
