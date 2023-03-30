package tdtu.edu.springecommerce.services.intservices;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.models.Cart;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.repostiory.CartRepository;

@Service
public interface CartService extends CartRepository {
    @Query(value = "FROM Cart AS c WHERE c.user.id=:id")
    public Iterable<Cart> findAllByUserId(Long id);
}
