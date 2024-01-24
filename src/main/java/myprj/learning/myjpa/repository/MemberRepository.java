package myprj.learning.myjpa.repository;

import myprj.learning.myjpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public abstract Member findByUserid(String userid);
}
