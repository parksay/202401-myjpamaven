package myprj.learning.myjpa.repository;

import myprj.learning.myjpa.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
