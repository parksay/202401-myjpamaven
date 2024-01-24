package myprj.learning.myjpa.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@TableGenerator(name = "MY_SEQ_GEN", table = "MY_SEQ_TB", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "ORDERS_SEQ")
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MY_SEQ_GEN")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersProduct> ordersProductList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_SEQ")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<OrdersProduct> getOrdersProductList() {
        return ordersProductList;
    }

    public void setOrdersProductList(List<OrdersProduct> ordersProductList) {
        this.ordersProductList = ordersProductList;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    private Orders() {

    }
    public static Orders createOrders(Member member, Delivery delivery, List<OrdersProduct> ordersProductList) {
        Orders orders = new Orders();
        orders.setMember(member);
        orders.setDelivery(delivery);
        orders.setOrderStatus(OrderStatus.ORDERED);
        for(OrdersProduct ordersProduct : ordersProductList) {
            orders.addOrdersProduct(ordersProduct);
        }
        return orders;
    }

    // 연관관계 편의 메소드
    public void addOrdersProduct(OrdersProduct ordersProduct) {
        if(!this.getOrdersProductList().contains(ordersProduct)) {
            this.getOrdersProductList().add(ordersProduct);
            ordersProduct.fixOrders(this);
        }

    }

    public void cancel() {
        if (this.getDelivery().getDeliveryStatus() == DeliveryStatus.STARTED) {
            throw new RuntimeException("이미 배송이 시작된 주문은 취소할 수 없습니다");
        } else if (this.getDelivery().getDeliveryStatus() == DeliveryStatus.COMPLETED) {
            throw new RuntimeException("이미 배송이 완료된 주문은 취소할 수 없습니다");
        }
        Iterator<OrdersProduct> iter = this.getOrdersProductList().iterator();
        while(iter.hasNext()) {
            OrdersProduct ordersProduct = iter.next();
            ordersProduct.cancel();
        }
        this.setOrderStatus(OrderStatus.CANCELED);
    }

    public int getTotalPrice() {
        Iterator<OrdersProduct> iter = this.getOrdersProductList().iterator();
        int totalPrice = 0;
        while(iter.hasNext()) {
            OrdersProduct item = iter.next();
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "seq=" + seq +
                ", member=" + member +
                ", ordersProductList.get(0).getProduct().getTitle()=" + ordersProductList.get(0).getProduct().getTitle() +
                ", delivery.getAddress()=" + delivery.getAddress() +
                ", delivery.getDeliveryStatus()=" + delivery.getDeliveryStatus() +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
