package myprj.learning.myjpa;

import myprj.learning.myjpa.domain.Address;
import myprj.learning.myjpa.domain.Member;
import myprj.learning.myjpa.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
public class MemberTest {


//    private EntityManager em; // 테스트 클래스 내부에서 직접 영속성 선텍스트 띄울 경우
//    private static MemberService memberService;   // 테스트 클래스 내부에서 직접 스프링 컨테이너 띄울 경우
//    private static final List<Member> memberList = new ArrayList<>(); // 테스트 클래스 내부에서 직접 스프링 컨테이너 띄울 경우
//
//
//    @BeforeAll
//    public static void setUp() {
//        // 테스트 클래스 내부에서 직접 스프링 컨테이너 띄울 경우
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        MemberTest.memberService = context.getBean("memberService", MemberService.class);
//        // 테스트 클래스 내부에서 직접 영속성 선텍스트 띄울 경우
//        EntityManagerFactory emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
//        this.em = emf.createEntityManager();
//
//    }

    @Autowired
    private MemberService memberService;
//    private MemberJpaService memberService;

    private static List<Member> memberList = new ArrayList<>();

    @BeforeEach
    public void each() {

//        MemberTest.memberList.addAll(
//                Arrays.asList(new Member("hello1", "world1", "안녕1"),
//                                new Member("hello2", "world2", "안녕3"))
//        );

        Address address = new Address("seoul", "jongro", "building");
        Member member1 = new Member("hello1", "world1", "안녕1", address);
        Member member2 = new Member("hello2", "world2", "안녕3", address);
        MemberTest.memberList.add(member1);
        MemberTest.memberList.add(member2);
    }


    @Test
    public void joinTest() {
        Member member1 = MemberTest.memberList.get(0);
        Long saveSeq = this.memberService.joinMember(member1);
        Member member2 = this.memberService.findById(saveSeq);
        Assertions.assertEquals(member1, member2);
    }


    @Test
    public void joinDuplicateFailTest() {
        Member member1 = MemberTest.memberList.get(0);
        Member member2 = MemberTest.memberList.get(1);
        member1.setUserid(member2.getUserid());

        Assertions.assertThrows(IllegalStateException.class, ()->{
            this.memberService.joinMember(member1);
            this.memberService.joinMember(member2);
        });
    }

    @Test
    public void loginTest() {
        Member member1 = MemberTest.memberList.get(0);
        this.memberService.joinMember(member1);
        Assertions.assertEquals(false, this.memberService.login(member1.getUserid(), ""));
        Assertions.assertEquals(true, this.memberService.login(member1.getUserid(), member1.getPsw()));
    }
}
