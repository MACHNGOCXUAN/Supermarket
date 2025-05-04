package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory em;

    static {
        em = Persistence.createEntityManagerFactory("mariadb");
    }

    public static EntityManager getEntityManager(){
        return em.createEntityManager();
    }
}
