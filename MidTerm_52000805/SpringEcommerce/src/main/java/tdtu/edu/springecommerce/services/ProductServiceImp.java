package tdtu.edu.springecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp {
    @Autowired
    private ProductService productService;
    public void showAll(){
        productService.findAll().forEach(System.out::println);
    }
}
