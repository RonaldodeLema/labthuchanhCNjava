package tdtu.edu.springecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tdtu.edu.springecommerce.services.CustomerServiceImp;
import tdtu.edu.springecommerce.services.OrderItemServiceImp;
import tdtu.edu.springecommerce.services.OrderServiceImp;
import tdtu.edu.springecommerce.services.ProductServiceImp;

@SpringBootApplication
public class SpringEcommerceApplication implements CommandLineRunner {
    @Autowired
    private CustomerServiceImp customerServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private OrderItemServiceImp orderItemServiceImp;
    public static void main(String[] args) {
        SpringApplication.run(SpringEcommerceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        customerServiceImp.showAll();
        orderServiceImp.showAll();
        productServiceImp.showAll();
        orderServiceImp.showAll();
    }
}
