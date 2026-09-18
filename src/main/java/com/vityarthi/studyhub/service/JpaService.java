package com.vityarthi.studyhub.service;

import com.vityarthi.studyhub.model.Course;
import jakarta.persistence.*;

public class JpaService {
    public void demonstrateLifecycle() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("studyhub-pu");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(new Course("JPA101", "Persistence Concepts", 3));
            manager.getTransaction().commit();
            manager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        } finally { if (manager.getTransaction().isActive()) manager.getTransaction().rollback(); manager.close(); factory.close(); }
    }
}
