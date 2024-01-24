package myprj.learning.myjpa.repository;

import myprj.learning.myjpa.domain.Orders;

import java.util.List;

public interface OrderRepositoryCustom {

    public abstract List<Orders> findByUserid(String userid);
}
