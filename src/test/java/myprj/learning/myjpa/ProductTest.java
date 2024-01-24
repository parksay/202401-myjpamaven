package myprj.learning.myjpa;

import myprj.learning.myjpa.domain.Album;
import myprj.learning.myjpa.domain.Book;
import myprj.learning.myjpa.domain.Movie;
import myprj.learning.myjpa.domain.Product;
import myprj.learning.myjpa.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ProductTest {


    @Autowired
    private ProductService productService;
//    private static ProductService productService;
//
//    @BeforeAll
//    public static void setup() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        productService = context.getBean("productService", ProductService.class);
//    }

    @Test
    public void saveTest() {
        Movie product1 = new Movie();
        product1.setTitle("movie1");
        product1.setPrice(12345);
        product1.setStock(9);
        product1.setDirector("director1");

        Album product2 = new Album();
        product2.setTitle("album1");
        product2.setPrice(23456);
        product2.setStock(8);
        product2.setArtist("artist1");

        Book product3 = new Book();
        product3.setTitle("book1");
        product3.setPrice(34567);
        product3.setStock(7);
        product3.setAuthor("author1");

        this.productService.saveProduct(product1);
        this.productService.saveProduct(product2);
        this.productService.saveProduct(product3);

        //
        Assertions.assertEquals(product1, this.productService.findById(product1.getSeq()));
        Assertions.assertEquals(product2, this.productService.findById(product2.getSeq()));
        Assertions.assertEquals(product3, this.productService.findById(product3.getSeq()));
    }

    @Test
    public void stockTest() {
        Movie product1 = new Movie();
        int stock = 3;
        product1.setTitle("movie1");
        product1.setPrice(12345);
        product1.setStock(stock);
        product1.setDirector("director1");


        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            product1.plusStock(-1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            product1.minusStock(-1);
        });
        Assertions.assertThrows(Product.NotEnoughStockException.class, ()->{
            product1.minusStock(stock + 1);
        });
        int minus = 1;
        product1.minusStock(minus);
        Assertions.assertEquals(stock-minus, product1.getStock());
    }
}
