package myprj.learning.myjpa.service;

import myprj.learning.myjpa.domain.Member;
import myprj.learning.myjpa.repository.MemberJpaRepository;
import myprj.learning.myjpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
// 스프링은 @Transactional 어노테이션이 붙어 있는 메소드나 클래스에 트랜잭션을 적용한다.
// 클래스에 붙어 있으면 그 클래스 안에 있는 모든 메소드에 적용한다.
// 외부에서 이 클래스 안에 있는 메소드를 호출할 때 트랜잭션을 시작하고 메소드를 종료할 때 트랜잭션을 커밋한다.
// AOP를 이용하므로 내부에서 부르는 메소드는 트랜잭션 적용이 안 된다.
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;
//    private MemberJpaRepository memberRepository;
//    // SpringDataJpa 를 사용하지 않고 JPA 기본 기술만 사용해서 만든 Repository

    public Member findById(Long seq) {
        Optional<Member> memberFind = this.memberRepository.findById(seq);
        return memberFind.orElse(null);
//        if(memberFind.isPresent()) {
//            return memberFind.get();
//        } else {
//            return null;
//        }
    }

    public Long joinMember(Member member) {
        this.validateDuplicateMember(member);
        this.memberRepository.save(member);
        return member.getSeq();
    }


    private void validateDuplicateMember(Member member) {
        Member member1 = this.memberRepository.findByUserid(member.getUserid());
        if(member1 != null) {
            throw new IllegalStateException("이미 존재하는 아이디입니다");
        }
    }

    public Member findByUserid(String userid) {
        return this.memberRepository.findByUserid(userid);
    }

    public List<Member> findMembers() {
        return this.memberRepository.findAll();
    }

    public boolean login(String userid, String psw) {
        boolean isCorrect = false;
        Member member = this.memberRepository.findByUserid(userid);
        if(member != null && psw.equals(member.getPsw())) {
            isCorrect = true;
        }
        return isCorrect;
    }

    // JpaRepository.findOne() 메소드는 엔티티의 식별자(ID)를 파라미터로 단일 엔티티를 검색하는 데 사용되었다.
    // 하지만 현재는 Deprecated 되었다.
    //따라서 Spring Boot 2.0부터는 findOne 대신 다른 방식을 사용해야 한다.
    // findById() / getOne() / findBy 등으로 대체할 수 있다.
    // 만약 사용했다면 아래처럼.
    public Member findOne(Long seq) {
        Member member1 = new Member();
        member1.setUserid("myId");
        Example<Member> example = Example.of(member1);
        Optional<Member> findMember = this.memberRepository.findOne(example);
        return findMember.get();
    }

}
