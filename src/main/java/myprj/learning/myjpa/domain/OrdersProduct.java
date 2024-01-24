package myprj.learning.myjpa.domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "ORDERS_PRODUCT")
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "ORDERS_PRODUCT_SEQ")
public class OrdersProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_SEQ")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(nullable = false)
    private int count;


    public static OrdersProduct createOrdersProduct(Product product, int count) {
        OrdersProduct ordersProduct = new OrdersProduct();
        ordersProduct.setProduct(product);
        ordersProduct.setCount(count);
        product.minusStock(count);
        return ordersProduct;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // 연관관계 편의 메소드
    public void fixOrders(Orders orders) {
        if(this.getOrders() == null) {
            // OrdersProduct 에 연결돼 있는 orders 가 없다면
            this.setOrders(orders);
        } else {
            // OrdersProduct 에 연결돼 있는 orders 가 이미 있다면
            // 기존 orders 로부터 ordersProductList 안에 있는 ordersProduct 를 제거하고
            if(this.getOrders() != orders) {
                this.getOrders().getOrdersProductList().remove(this);
            }
        }
        // 새 orders 의 ordersProductList 에 ordersProduct 를 넣어줌
        if(!orders.getOrdersProductList().contains(this)) {
            orders.addOrdersProduct(this);
        }

    }

    public int getTotalPrice() {
        return this.count * this.getProduct().getPrice();
    }

    public void cancel() {
        this.getProduct().plusStock(this.getCount());
    }
}
