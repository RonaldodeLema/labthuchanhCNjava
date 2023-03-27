package tdtu.edu.springecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImp {
    @Autowired
    private OrderItemService orderItemService;
    public void showAll(){
        orderItemService.findAll().forEach(System.out::println);
    }
}
