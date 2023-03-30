package tdtu.edu.springecommerce.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.springecommerce.models.*;
import tdtu.edu.springecommerce.services.impservices.*;

import java.util.*;

@RestController
public class HomeRestController {
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private CustomerServiceImp customerServiceImp;
    @Autowired
    private CartServiceImp cartServiceImp;

    @GetMapping("/api/get-all-cate-prod")
    public Map<String, List<Object>> getAllCateAndProd(){
        Map<String, List<Object>> map = new HashMap<>();
        map.put("products", Collections.singletonList(productServiceImp.findAll()));
        map.put("categories", Collections.singletonList(categoryServiceImp.findAll()));
        return map;
    }
    @GetMapping("/api/product/find/{id}")
    public Product getDetails(@PathVariable("id") Long id) {
        return productServiceImp.findByID(id);
    }

    @PostMapping("/api/cart/add")
    public Cart addCart(@RequestBody Map<String, Long> body) {
        Long user_id = body.get("user_id");
        Long product_id = body.get("product_id");
        User user = userServiceImp.findById(user_id);
        Product product = productServiceImp.findByID(product_id);
        Cart cart = new Cart(product.getName(),product_id,product.getPrice(),product.getImage(),user);
        return cartServiceImp.save(cart);
    }
    @GetMapping("/api/cart/all-by-id/{id}")
    public Iterable<Cart> getCartByUserID(@PathVariable("id") Long id){
        return cartServiceImp.findAllByID(id);
    }
//    @DeleteMapping("/api/cart/delete-by-prod-id/{id}")
//    public Cart<Product> removeCartByID(@PathVariable("id") Long id){
//        return cartServiceImp.deleteByProdID(id);
//    }
    @GetMapping("/api/product/search/{id}")
    public Iterable<Product> getSearchAdvanced(@PathVariable("id")String text) {
        return productServiceImp.searchAdvance(text);
    }
    @PostMapping("/api/auth/login/user-valid")
    public User getValidUser(@RequestBody Map<String,String> body){
        String username = body.get("username");
        String password = body.get("password");
        return userServiceImp.userLoginValid(username,password);
    }
    @PostMapping("/api/user/add")
    public String addUser(@RequestBody String request){
        Gson gson = new Gson();
        User user = gson.fromJson(request,User.class);
        if(userServiceImp.checkUserExist(user.getUsername())){
            return "Username is exist!";
        }
        String rePassword = user.getCustomer().getName();
        if(!rePassword.equals(user.getPassword())){
            return "Again password wrong!";
        }
        // Remove rePassword in name of customer
        user.getCustomer().setName(null);
        //Add Customer
        Customer customer = customerServiceImp.save(user.getCustomer());
        // Add User
        user.setCustomer(customer);
        userServiceImp.save(user);
        return "Sign up success!";
    }
}
