package tdtu.edu.springecommerce.services.impservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.springecommerce.models.User;
import tdtu.edu.springecommerce.services.intservices.UserService;

@Service
public class UserServiceImp {
    @Autowired
    private UserService userService;

    public User userLoginValid(User user) {
        return userService.getUserValid(user.getUsername(), user.getPassword());
    }
}
