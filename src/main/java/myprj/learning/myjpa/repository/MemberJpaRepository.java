package myprj.learning.myjpa.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import myprj.learning.myjpa.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberJpaRepository {
// SpringDataJpa 를 사용하지 않고 JPA 기본 기술만 사용해서 만든 Repository

    @PersistenceContext
    EntityManager em;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long seq) {
        return em.find(Member.class, seq);
    }

    public List<Member> findAll() {
        TypedQuery query = em.createQuery("SELECT m FROM Member m", Member.class);
        return query.getResultList();
    }

    public List<Member> findByUserid(String userid) {
        TypedQuery query = em.createQuery("SELECT m FROM Member as m WHERE m.userid = :userid", Member.class);
        // JPQL 짤 때 주의할 점 - jpql 쿼리는 객체 기준임. 객체 지향 쿼리임.
        // 예를 들어서 member 엔티티의 userid 필드를 이렇게 선언함.
        //    @Column(name = "USER_ID")
        //    private String userid;
        // 그러면 database 기준으로는 "user_id" 가 되겠지만 객체 기준으로는 "userid" 임.
        // 그럼 JPQL 짤 때도 객체 기준으로 "userid" 로 넣어줘야 함.
        // 아니면 아래와 같은 에러가 남.
        // Could not resolve attribute 'user_id' of 'Member'

        query.setParameter("userid", userid);
        return query.getResultList();
    }
}
