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
public class DogBreedDAO implements Serializable {

    public List<DogBreed> getSizeDog(String size) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<DogBreed> listSizeDog = em.createQuery("SELECT d FROM DogBreed d WHERE d.size LIKE :size")
                .setParameter("size", "%" + size + "%").getResultList();
        em.getTransaction().commit();
        em.close();
        return listSizeDog;
    }

    public DogBreed getDogID(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        DogBreed idDog = (DogBreed) em.createQuery("SELECT d FROM DogBreed d WHERE d.iddogBreed = :iddogBreed")
                .setParameter("iddogBreed", id).getSingleResult();
        em.getTransaction().commit();
        em.close();
        return idDog;

    }
}
