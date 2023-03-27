package tdtu.edu.springecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp {
    @Autowired
    private CustomerService customerService;
    public void showAll(){
        customerService.findAll().forEach(System.out::println);
    }
}
