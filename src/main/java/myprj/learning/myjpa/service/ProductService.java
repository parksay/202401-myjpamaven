package myprj.learning.myjpa.service;


import myprj.learning.myjpa.domain.Product;
import myprj.learning.myjpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional  
// try-catch 블록 깔고, transaction 열고, 로직 돌리고, 예외 나면 rollback 해주고, 이거를 AOP로 자동화 해준 게 @Transactional 어노테이션이잖아
// @Transactional 어노테이션 없으면 네가 지금 트랜잭션을 어디에서 열고 있어. 어디긴 어디야 안 열고 있지.
// JPA 는 PersistenceContext 생명 주기가 보통 transaction 과 동일한데 트랜잭션이 없으니 JPA 도 못 열게 됨. 아래처럼 에러 남.
// No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
//    private ProductJpaRepository productRepository;
//    // SpringDataJpa 를 사용하지 않고 JPA 기본 기술만 사용해서 만든 Repository

    public Long saveProduct(Product product) {
        Product productSave = this.productRepository.save(product);
        return productSave.getSeq();
    }

    public Product findById(Long seq) {
        Optional<Product> productFind = this.productRepository.findById(seq);
        return productFind.orElse(null);

    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }


}
