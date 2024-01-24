package myprj.learning.myjpa.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import myprj.learning.myjpa.domain.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Orders orders) {
        em.persist(orders);
        return orders.getSeq();
    }

    public Orders findOne(Long seq) {
        return em.find(Orders.class, seq);
    }

    public List<Orders> findAll() {
        TypedQuery query = em.createQuery("SELECT o FROM Orders o", Orders.class);
        return query.getResultList();
    }


}


