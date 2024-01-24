package myprj.learning.myjpa.service;


import myprj.learning.myjpa.domain.*;
import myprj.learning.myjpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;
//    private OrdersRepository orderRepository;
//    // SpringDataJpa 를 사용하지 않고 JPA 기본 기술만 사용해서 만든 Repository

    public Long save(Orders orders) {
        orderRepository.save(orders);
        return orders.getSeq();
    }

    public Orders findById(Long seq) {
        Optional<Orders> orderFind = this.orderRepository.findById(seq);
        return orderFind.orElse(null);
    }

    public List<Orders> findByUserid(String userid) {
        return this.orderRepository.findByUserid(userid);
    }

    public List<Orders> findALl() {
        return this.orderRepository.findAll();
    }

    public Orders createOrders(Member member, Product product, int count) {
        Delivery delivery = new Delivery(member.getAddress());
        List<OrdersProduct> ordersProductList = new ArrayList<>();
        ordersProductList.add(OrdersProduct.createOrdersProduct(product, count));
        Orders orders = Orders.createOrders(member, delivery, ordersProductList);
        this.orderRepository.save(orders);
        return orders;
    }

    public void cancelOrder(Orders orders) {
        orders.cancel();
    }
}
