package tdtu.edu.springecommerce.services.impservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.services.intservices.ProductService;
@Service
public class ProductServiceImp {
    @Autowired
    private ProductService productService;
    public void showAll(){
        productService.findAll().forEach(System.out::println);
    }

    public Iterable<Product> findAll() {
        return productService.findAll();
    }
    public Iterable<Product> searchAdvance(String text){
        return productService.searchAdvance(text);
    }

    public Product findByID(Long id) {
        if(productService.findById(id).isPresent())
            return productService.findById(id).get();
        else
            return new Product();
    }
}
