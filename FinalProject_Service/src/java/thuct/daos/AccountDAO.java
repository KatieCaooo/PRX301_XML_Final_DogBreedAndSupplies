/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.daos;

import java.io.Serializable;
import javax.persistence.EntityManager;
import thuct.dtos.Account;
import thuct.utils.JPAUtil;

/**
 *
 * @author kloecao
 */
public class AccountDAO implements Serializable {

    public Account login(String id, String pwd) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        Account account = (Account) em.createQuery("SELECT a FROM Account a WHERE a.idaccount = :id AND a.pwd = :pwd")
                .setParameter("id", id).setParameter("pwd", pwd).getSingleResult();

        em.getTransaction().commit();
        em.close();
        return account;
    }

    public void register(Account account) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
        em.close();
    }

    public boolean checkDuplicate(String id) {
        boolean check = false;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Account account = em.find(Account.class, id);
        if (account == null) {
            check = true;
        }
        em.getTransaction().commit();
        em.close();
        return check;
    }

}
