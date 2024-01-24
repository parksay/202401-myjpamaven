package myprj.learning.myjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import myprj.learning.myjpa.domain.Address;
import myprj.learning.myjpa.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Iterator;

public class MyTest {

    @Test
    public void hello() {
        System.out.println("hello world");
    }


    @Test
    public void runJPA() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        this.emfTest(emf);
    }

    @Test
    public void springRun() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EntityManagerFactory emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
        System.out.println("emf = " + emf);

        this.emfTest(emf);

    }

    @Test
    public void beanTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//         등록된 Bean 전체 조회
        String[] beanNames = context.getBeanNamesForType(Object.class);
        Iterator<String> iter = Arrays.stream(beanNames).iterator();
        while(iter.hasNext()) {
            String beanName = iter.next();
            System.out.println("beanName = " + beanName);
        }


    }

    private void emfTest(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx =  em.getTransaction();

        try {
            tx.begin();
            //
            Address address = new Address("city1", "street1", "detail1");
            Member member1 = new Member("myName", "myId", "myPsw", address);

            em.persist(member1);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("e = " + e);
            e.printStackTrace();
            Assertions.fail();
        } finally {
            em.close();
            emf.close();
        }
    }

    @Test
    public void nullTest() {
        System.out.println(null == null);
    }
}
