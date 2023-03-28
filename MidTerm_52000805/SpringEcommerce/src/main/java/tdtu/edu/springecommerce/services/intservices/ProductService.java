package tdtu.edu.springecommerce.services.intservices;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.repostiory.ProductRepository;

@Service
public interface ProductService extends ProductRepository {
    @Query(value = "FROM Product AS p WHERE p.name LIKE %:text% OR p.category.name like %:text% " +
            "OR p.brand.name like %:text% ORDER BY p.price DESC")
    public Iterable<Product> searchAdvance(String text);
}
