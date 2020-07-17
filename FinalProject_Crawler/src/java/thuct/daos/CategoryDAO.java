/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import javax.persistence.EntityManager;
import thuct.dtos.Category;
import thuct.utils.JPAUtil;

/**
 *
 * @author katherinecao
 */
public class CategoryDAO {

    public Category findCategoryById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Category category = entityManager.find(Category.class, id);

        entityManager.getTransaction().commit();
        entityManager.close();
        return category;
    }

    public void insertCategory(Category category) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.clear();
        entityManager.getTransaction().begin();

        entityManager.persist(category);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
