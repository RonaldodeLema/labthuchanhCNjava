package tdtu.edu.springecommerce.services.impservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.services.intservices.CustomerService;

@Service
public class CustomerServiceImp {
    @Autowired
    private CustomerService customerService;
    public void showAll(){
        customerService.findAll().forEach(System.out::println);
    }
}
