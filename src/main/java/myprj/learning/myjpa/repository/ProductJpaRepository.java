package myprj.learning.myjpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import myprj.learning.myjpa.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductJpaRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Product product) {
        if(product.getSeq() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
        return product.getSeq();
    }

    public Product findOne(Long seq) {
        return em.find(Product.class, seq);
    }

    public List<Product> findAll() {
        TypedQuery query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }
}
