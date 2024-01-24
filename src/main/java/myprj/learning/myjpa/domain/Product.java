package myprj.learning.myjpa.domain;

import jakarta.persistence.*;

@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "PRODUCTS_SEQ")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    private Long seq;
    private String title;
    private int price;
    private int stock;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void plusStock(int quantity) {
        // 파라미터는 양수여야 한다
        if(quantity < 1) {
            throw new IllegalArgumentException("argument must be positive integer");
        }
        this.stock += quantity;
    }

    public void minusStock(int quantity) {
        // 파라미터는 양수여야 한다
        if(quantity < 1) {
            throw new IllegalArgumentException("argument must be positive integer");
        }
        int remain = this.stock - quantity;
        // Product.stock 은 음수가 될 수 없다
        if(remain < 0) {
            throw new NotEnoughStockException("product needs more stock");
        }
        this.stock = remain;
    }

    public class NotEnoughStockException extends RuntimeException {
        public NotEnoughStockException(String message) {
            super(message);
        }
    }

}
