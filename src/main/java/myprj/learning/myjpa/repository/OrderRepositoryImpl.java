package myprj.learning.myjpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import myprj.learning.myjpa.domain.Orders;

import java.util.List;



public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Orders> findByUserid(String userid) {
        // JPQL 쓸 때는 sql 이 아니라 객체지향 문법이기 때문에 항상 대소문자 주의 - 특히 엔티티 지정할 때 => orders 하면 못 찾고 Orders 해야 찾음
        TypedQuery query = em.createQuery("SELECT o FROM Orders as o WHERE o.member.userid = :userid", Orders.class);
        query.setParameter("userid", userid);
        return query.getResultList();
    }
}
