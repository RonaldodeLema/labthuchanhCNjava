package tdtu.edu.springecommerce.services.impservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.models.Cart;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.services.intservices.BrandService;
import tdtu.edu.springecommerce.services.intservices.CartService;

import java.util.Collections;

@Service
public class CartServiceImp {
    @Autowired
    private CartService cartService;
    public Cart save(Cart cart){
        return cartService.save(cart);
    }

    public Iterable<Cart> findAllByID(Long id) {
        return cartService.findAllByUserId(id);
    }

//    public Iterable<Cart> deleteByProdID(Long id) {
//        return cartService;
//    }
}
