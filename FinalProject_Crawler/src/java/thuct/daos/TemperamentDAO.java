/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import thuct.dtos.Temperament;
import thuct.utils.JPAUtil;

/**
 *
 * @author katherinecao
 */
public class TemperamentDAO {

    public Temperament findTemperamentById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        Temperament temperament = entityManager.find(Temperament.class, id);

        entityManager.getTransaction().commit();
        entityManager.close();
        return temperament;
    }

    public Temperament findTemperamentByContent(String content) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();
        Temperament temperament=null;
        try {
            temperament = (Temperament) entityManager.createQuery("SELECT t FROM Temperament t WHERE t.content = :content")
                .setParameter("content", content).getSingleResult();
        } catch (NoResultException e) {
           return temperament;
        }
        

        entityManager.getTransaction().commit();
        entityManager.close();
        return temperament;
    }

    public void insertTemperament(Temperament temperament) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();
        if (findTemperamentByContent(temperament.getContent()) != null) {
            return;
        } else {
            entityManager.persist(temperament);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
