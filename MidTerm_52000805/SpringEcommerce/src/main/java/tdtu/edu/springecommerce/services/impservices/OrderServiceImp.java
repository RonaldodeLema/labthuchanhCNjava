package tdtu.edu.springecommerce.services.impservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.services.intservices.OrderService;

@Service
public class OrderServiceImp {
    @Autowired
    private OrderService orderService;
    public void showAll(){
        orderService.findAll().forEach(System.out::println);
    }
}
