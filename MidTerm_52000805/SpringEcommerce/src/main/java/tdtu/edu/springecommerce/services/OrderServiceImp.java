package tdtu.edu.springecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp {
    @Autowired
    private OrderService orderService;
    public void showAll(){
        orderService.findAll().forEach(System.out::println);
    }
}