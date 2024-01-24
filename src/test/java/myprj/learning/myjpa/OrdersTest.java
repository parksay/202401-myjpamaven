package myprj.learning.myjpa;

import myprj.learning.myjpa.domain.*;
import myprj.learning.myjpa.service.MemberService;
import myprj.learning.myjpa.service.OrdersService;
import myprj.learning.myjpa.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
public class OrdersTest {

//    private static OrdersService ordersService;
//    private static MemberService memberService;
//    private static ProductService productService;
//    private static EntityManager em;
//
//    @BeforeAll
//    public static void setup() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        OrdersTest.ordersService = context.getBean("ordersService", OrdersService.class);
//        OrdersTest.memberService = context.getBean("memberService", MemberService.class);
//        OrdersTest.productService = context.getBean("productService", ProductService.class);
//        EntityManagerFactory emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
//        OrdersTest.em = emf.createEntityManager();
//    }


    @Autowired
    private OrdersService ordersService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    private Member member;

    @BeforeEach
    public void setUp() {
        Address address = new Address("seoul", "jongro", "building1");
        this.member = new Member("name1", "userid1", "psw1", address);
        this.memberService.joinMember(this.member);

    }


    private void saveOrder() {
        Book book1 = new Book();
        int totalStock = 10;
        int orderStock = 3;
        book1.setPrice(2000);
        book1.setStock(totalStock);
        book1.setTitle("helloworld");
        OrdersProduct ordersProduct = OrdersProduct.createOrdersProduct(book1, orderStock);
        List<OrdersProduct> ordersProductList = new ArrayList<>();
        ordersProductList.add(ordersProduct);
        Delivery delivery = new Delivery(this.member.getAddress());
        Orders orders = Orders.createOrders(this.member, delivery, ordersProductList);
        this.productService.saveProduct(book1);
        Long ordersSeq = this.ordersService.save(orders);
    }

    @Test
    public void saveTest() {
        Delivery delivery = new Delivery(this.member.getAddress());
        Book book1 = new Book();
        int totalStock = 10;
        int orderStock = 3;
        book1.setPrice(2000);
        book1.setStock(totalStock);
        book1.setTitle("helloworld");
        OrdersProduct ordersProduct = OrdersProduct.createOrdersProduct(book1, orderStock);
        List<OrdersProduct> ordersProductList = new ArrayList<>();
        ordersProductList.add(ordersProduct);
        Orders orders = Orders.createOrders(this.member,delivery, ordersProductList);
        //
        // 순서에도 영향을 받음. 아무래도 DB에 넣고 나서 그 결과로 PK 값을 넣어주는 거니까 순서가 필요할 것 같기도
        // 아 맞네. 우리가 @Service 클래스에다가는 모두 @Transactional 을 붙여 줬으니까 Service 클래스의 메소드 호출 하나당 트랜잭션 하나네.
        // @Transactional 이 붙은 클래스는 알아서 TransactionManager 가 tx.open(); tx.commit(); em.flush(); em.close() 까지 다 알아서 해주겠네.
        // AppConfig.class 보면 TransactionManager 빈을 등록할 때도 JpaTransactionManager.class 를 넣어준 이유가 이런 거 알아서 해달라고.
        // 따라서 ---Service.method() 하나 부를 때마다 모두 다른 영속성 컨텍스를 갖기 때문에 순서가 완전 상관 있지.
        this.productService.saveProduct(book1);
        Long ordersSeq = this.ordersService.save(orders);
        Orders ordersFind = this.ordersService.findById(ordersSeq);
        //
        // LazyInitializationException: failed to lazily initialize a collection of role: myprj.learning.myjpa.domain.Orders.ordersProductList: could not initialize proxy - no Session
        // 이거는 Orders 까지는 조회를 잘 했는데 그 안에 ordersProductList 를 조회해오지 못하는 에러
        // 왜 이런 현상이 발생하냐면 Orders 를 조회할 때는 영속성 컨텍스트가 열려 있었는데 orderProductList 를 조회할 때는 영속성 컨텍스트가 닫혀 있기 때문.
        // ordersProductList 는 fetch 속성을 fetch = FetchType.LAZY 로 줬기 때문에 지연 로딩이 적용 됨
        // Orders 만 조회해오고 나서 ordersProductList 는 일단 프록시로 껍데기만 세워둔 상태
        // 나중에 ordersProductList 내용물이 필요해져서 조회해오려고 보니까 영속성 컨텍스트는 이미 닫힌 상태
        // 이걸 해결하려면 트랜잭션 범위를 더 넓게 잡아야 함. 비지니스 로직을 다 포함해서.
        // fetch 속성을 fetch = FetchType.EAGER 로 변경하는 법도 있지만 성능 이슈가 있을 수 있어서 비추
        Assertions.assertEquals(OrderStatus.ORDERED, ordersFind.getOrderStatus());
        Assertions.assertEquals(1, ordersFind.getOrdersProductList().size());
        Assertions.assertEquals(book1.getPrice() * orderStock, ordersFind.getTotalPrice());
        Assertions.assertEquals(totalStock - orderStock, book1.getStock());
    }

    @Test
    public void cancelTest() {
        Address address = new Address("seoul", "jongro", "building1");
        Member member = new Member("name1", "userid1", "psw1", address);
        Delivery delivery = new Delivery(member.getAddress());
        List<OrdersProduct> ordersProductList = new ArrayList<>();
        Book book1 = new Book();
        int totalStock = 10;
        book1.setTitle("book1");
        book1.setPrice(12345);
        book1.setStock(totalStock);
        book1.setAuthor("author1");
        OrdersProduct ordersProduct = OrdersProduct.createOrdersProduct(book1, 3);
        ordersProductList.add(ordersProduct);
        Orders orders1 = Orders.createOrders(member, delivery, ordersProductList);
        //
        memberService.joinMember(member);
        productService.saveProduct(book1);
        ordersService.save(orders1);
        Assertions.assertEquals(book1.getStock(), 7);
        //
        ordersService.cancelOrder(orders1);
        //
        Assertions.assertEquals(orders1.getOrderStatus(), OrderStatus.CANCELED);
        Assertions.assertEquals(book1.getStock(), totalStock);
    }

    @Test
    public void findByUseridTest() {
        this.saveOrder();
        List<Orders> ordersList = ordersService.findByUserid(this.member.getUserid());
        Iterator<Orders> iter = ordersList.iterator();
        while(iter.hasNext()) {
            Orders orders = iter.next();
            System.out.println("orders = " + orders);
        }
    }
}
