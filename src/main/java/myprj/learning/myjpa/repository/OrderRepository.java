package myprj.learning.myjpa.repository;

import myprj.learning.myjpa.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {
    // 사용자 정의 Repository 구현하기
    // 엔티티에 @NamedQuery 어노테이션을 달아서 미리 쿼리 작성해 놓고 메소드에 적용시키기
    // 메소드에 @Query 어노테이션 달고 직접 쿼리 넣어주기
    public abstract List<Orders> findByUserid(String userid);
}
